package sfc.blocks.timer
import Chisel._
import freechips.rocketchip.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.amba.ahb._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.interrupts._
import freechips.rocketchip.subsystem._
import sfc.blocks.ip.timer._
import sfc.blocks.ip.ahbslaveram._
case class AHBSlaveRamParams(
  config: String,
  raddress: BigInt,
  lenth: BigInt
)

class AHBSlaveRam(params: AHBSlaveRamParams )(implicit p: Parameters) extends LazyModule {
  //this is a Wrapper for timer and handle all the nodes
  val blackboxName =  params.config

  val dtsdevice = new SimpleDevice("AHBSlaveRam",Seq("AHBSlaveRam_2"))

  val cfg_ahb_node = AHBSlaveNode(  //instantiate a node of APBSlave
    Seq(
      AHBSlavePortParameters(
        slaves = Seq(AHBSlaveParameters(
          address       = Seq(AddressSet(params.raddress, params.lenth)),//0x8000L-1L
          resources     = dtsdevice.reg("control"),
          executable    = false,
          supportsWrite = TransferSizes(1,4),
          supportsRead  = TransferSizes(1,4))),
        beatBytes = 4)))

  val cfg_tl_node = cfg_ahb_node := LazyModule(new TLToAPB).node

  val int_node = IntSourceNode(IntSourcePortSimple(num = 1, resources = dtsdevice.int))


  lazy val module = new LazyModuleImp(this){

    val u_ahb_ram = Module(new ahb_ram("ahb_ram"))
    val u_ram_model = Module(new fpga_xilinx_sp_1024x32m8("fpga_xilinx_sp_1024x32m8"))
    u_ahb_ram.io.hclk := clock
    u_ahb_ram.io.hresetn := reset
    u_ram_model.io.clka := clock


    val (cfg, _) = cfg_ahb_node.in(0)
    u_timer.io.PSEL         := cfg.psel
    u_timer.io.PENABLE      := cfg.penable
    u_timer.io.PWRITE       := cfg.pwrite
    u_timer.io.PADDR        := cfg.paddr
    u_timer.io.PWDATA       := cfg.pwdata
    cfg.prdata              := u_timer.io.PRDATA
    cfg.pready              := Bool(true)
    cfg.pslverr             := Bool(false)

    val (io_int, _) = int_node.out(0)
    //u_timer.io.TIMCLKEN1   := Bool(true)
    io_int(0)   := u_timer.io.TIMINTC

  }
}