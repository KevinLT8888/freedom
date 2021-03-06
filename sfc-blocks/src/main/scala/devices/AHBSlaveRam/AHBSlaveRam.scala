package sfc.blocks.AHBRam
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
class AHBSlaveRam( param : MemoryPortParams , cacheBlockBytes: Int )(implicit p: Parameters) extends LazyModule
//   with HasAHBMemNode
{
  //this is a Wrapper for an AHBRam and handle all the nodes
  val blackboxName = "AHBRam"
  val cacheBlockBs = cacheBlockBytes
  val memPortParam = param
  ////val dtsdevice = new SimpleDevice("AHBSlaveRam",Seq("AHBSlaveRam_2"))
  val memdevice = new MemoryDevice
  val cfg_ahb_node = AHBSlaveNode(  //instantiate a node of AHBSlave
   Seq(
     AHBSlavePortParameters(
       slaves = Seq(AHBSlaveParameters(
         address       = Seq(AddressSet(param.master.base, param.master.size - 1L)),
         resources     = memdevice.reg("mem"),
         regionType    = RegionType.UNCACHED,
         executable    = true,
         supportsWrite = TransferSizes(1,cacheBlockBytes),
         supportsRead  = TransferSizes(1,cacheBlockBytes))),
       beatBytes = 4)))

 val cfg_tl_node = cfg_ahb_node := LazyModule(new TLToAHB).node

  lazy val module = new LazyModuleImp(this){

    val u_ahb_ram = Module(new ahb_ram("ahb_ram",17,15))
    //val u_ram_model = Module(new fpga_xilinx_sp_1024x32m8("fpga_xilinx_sp_1024x32m8"))
    val u_ram_model = Module(new fpga_xilinx_sp_32768x32m8("fpga_xilinx_sp_32768x32m8"))
    u_ahb_ram.io.hclk := clock
    u_ahb_ram.io.hresetn := ~reset
    u_ram_model.io.clka := clock

    //link AHB and Ram
    u_ahb_ram.io.ram_dout := u_ram_model.io.douta

    u_ram_model.io.ena    := ~u_ahb_ram.io.ram_csn
    u_ram_model.io.wea    := ~u_ahb_ram.io.ram_wrn
    u_ram_model.io.addra  := u_ahb_ram.io.ram_addr
    u_ram_model.io.dina   := u_ahb_ram.io.ram_din

    val (ahb, _) = cfg_ahb_node.in(0)
    u_ahb_ram.io.haddr    := ahb.haddr
    u_ahb_ram.io.htrans   := ahb.htrans
    u_ahb_ram.io.hwrite   := ahb.hwrite
    u_ahb_ram.io.hwdata   := ahb.hwdata
    u_ahb_ram.io.hsize    := ahb.hsize
    u_ahb_ram.io.hsel     := ahb.hsel
    u_ahb_ram.io.hready   := ahb.hready

    ahb.hrdata            := u_ahb_ram.io.hrdata
    ahb.hreadyout         := u_ahb_ram.io.hreadyout
    ahb.hresp             := u_ahb_ram.io.hresp

  }
}

//trait HasAHBMemNode { this : AHBSlaveRam =>
// //this trait can not pass the compiler, javaNullPointerExeptions
//val memdevice = new MemoryDevice
//val cfg_ahb_node  = AHBSlaveNode(
//    Seq(
//      AHBSlavePortParameters(
//        slaves = Seq(AHBSlaveParameters(
//          address = Seq(AddressSet(memPortParam.master.base, memPortParam.master.size - 1L)),
//          resources = memdevice.reg("mem"),
//          regionType = RegionType.UNCACHED,
//          executable = true,
//          supportsWrite = TransferSizes(1, cacheBlockBs),
//          supportsRead = TransferSizes(1, cacheBlockBs))),
//        beatBytes = 4)))
//  //val cfg_tl_node = cfg_ahb_node := LazyModule(new TLToAHB).node

//}