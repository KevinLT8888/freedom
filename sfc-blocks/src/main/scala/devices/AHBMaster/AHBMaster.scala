package sfc.blocks.AHBMaster
import Chisel._
import freechips.rocketchip.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.amba.ahb._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.subsystem._
import sfc.blocks.ip.ahbmaster._
trait HasAHBMaster {this: BaseSubsystem =>

  val masterAhb = LazyModule(new AHBMaster())

  fbus.fromMaster(name = Some("AHB_Master_Model"),buffer = BufferParams.default){
    TLBuffer.chainNode(0)
  } := masterAhb.master_tl_node
}
class AHBMaster()(implicit p: Parameters) extends LazyModule
{
  val masterDevice = new SimpleDevice("AHBMaster",Seq("AHBMaster"))
  val master_tl_node = TLIdentityNode()
  val master_ahb_node = AHBMasterNode(Seq(
      AHBMasterPortParameters(
        masters = Seq(AHBMasterParameters(
          name = "AHBMaster")))))
  (master_tl_node
    := TLBuffer()
    := TLWidthWidget(4)
    := AHBToTL()
    := master_ahb_node)
  lazy val module = new LazyModuleImp(this){
    val u_ahb_master = Module(new ahb_master_model)
    u_ahb_master.io.HCLK  :=  clock
    u_ahb_master.io.HRESETn  :=  ~reset
    val (ahb, _) = master_ahb_node.out(0)
    //input to master
    u_ahb_master.io.HREADY    :=  ahb.hreadyout
    u_ahb_master.io.HRESP     :=  ahb.hresp
    u_ahb_master.io.HRDATA    :=  ahb.hrdata
    u_ahb_master.io.EXRESP    :=  Bool(false)

    ahb.hsel     :=    Bool(true)
    ahb.hready   :=    Bool(true)
    //output from master
    ahb.htrans   :=    u_ahb_master.io.HTRANS
    ahb.hburst   :=    u_ahb_master.io.HBURST
    ahb.hprot    :=    u_ahb_master.io.HPROT
    ahb.hsize    :=    u_ahb_master.io.HSIZE
    ahb.hwrite   :=    u_ahb_master.io.HWRITE
    ahb.hwdata   :=    u_ahb_master.io.HWDATA
    ahb.hmastlock :=   u_ahb_master.io.HMASTLOCK
    ahb.haddr    :=    u_ahb_master.io.HADDR
  }

}
