package sfc.blocks.timer
import Chisel._
import freechips.rocketchip.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.amba.apb._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.interrupts._
import freechips.rocketchip.subsystem._
import sfc.blocks.ip.timer._

case class TIMERParams(
  config: String,
  raddress: BigInt,
  lenth: BigInt,
  enableNum: Int
)

class TIMER(params: TIMERParams )(implicit p: Parameters) extends LazyModule {
  //this is a Wrapper for timer and handle all the nodes
  val blackboxName =  params.config

  val dtsdevice = new SimpleDevice("Timer",Seq("sfc,sfctimer_2"))

  val cfg_apb_node = APBSlaveNode(  //instantiate a node of APBSlave
    Seq(
      APBSlavePortParameters(
        slaves = Seq(APBSlaveParameters(
          address       = Seq(AddressSet(params.raddress, params.lenth)),//0x8000L-1L
          resources     = dtsdevice.reg("control"),
          executable    = false,
          supportsWrite = true,
          supportsRead  = true)),
        beatBytes = 4)))

  val cfg_tl_node = cfg_apb_node := LazyModule(new TLToAPB).node

  val int_node = IntSourceNode(IntSourcePortSimple(num = 1, resources = dtsdevice.int))


  lazy val module = new LazyModuleImp(this){

    val u_timer = Module(new timer(blackboxName))

    u_timer.io.PCLK := clock
    u_timer.io.TIMCLK := clock
    u_timer.io.PRESETn := ~reset

    val (cfg, _) = cfg_apb_node.in(0)
    u_timer.io.PSEL         := cfg.psel
    u_timer.io.PENABLE      := cfg.penable
    u_timer.io.PWRITE       := cfg.pwrite
    u_timer.io.PADDR        := cfg.paddr(11,2)
    u_timer.io.PWDATA       := cfg.pwdata
    u_timer.io.TIMCLKEN1    := Bool(true)

    u_timer.io.TIMCLKEN2    := {if(params.enableNum>1) Bool(true)
                                else Bool(false)}


    cfg.prdata              := u_timer.io.PRDATA
    cfg.pready              := Bool(true)
    cfg.pslverr             := Bool(false)

    val (io_int, _) = int_node.out(0)
    //u_timer.io.TIMCLKEN1   := Bool(true)
    io_int(0)   := u_timer.io.TIMINTC

  }
}