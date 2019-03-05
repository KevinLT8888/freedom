////////////////////////////////////
//This is a black box for automatic generated AMBA bus matrix
//Inside the box is the verilog codes of the bus
//Only IOs are showing themselves
/////////////////////////////////////
package sfc.blocks.AMBABus
import Chisel._
import freechips.rocketchip.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.interrupts._
import freechips.rocketchip.subsystem._

import freechips.rocketchip.amba.ahb._


case class AHBSlave(//this param contains a single AHB slave device
                   devName : String

                    )
case class APBSlave(//this param contains a single APB slave device
                   devName : String

                   )
//any AMBA type device listed here
case class AMBABusMatrixParams(
                              AHBSlaves: Seq[AHBSlave],
                              APBSlaves: Seq[APBSlave]
                              )


//case object AMBABusMatrixKey extends Field[Seq[AMBABusMatrixParams]]
class AMBABusMatrix(memparam: MemoryPortParams,busparam:AMBABusMatrixParams)  (implicit p: Parameters)  extends LazyModule {

  busparam.APBSlaves.map( apbslave => {} )  //instantiate each apbSlaveNodes here
  busparam.AHBSlaves.map( ahbslave => {} )


  val memdevice = new MemoryDevice
  val cfg_ahb_node = AHBSlaveNode(  //instantiate a node of AHBSlave
    Seq(
      AHBSlavePortParameters(
        slaves = Seq(AHBSlaveParameters(
          address       = Seq(AddressSet(memparam.master.base, memparam.master.size - 1L)),
          resources     = memdevice.reg("mem"),
          regionType    = RegionType.UNCACHED,
          executable    = true,
          supportsWrite = TransferSizes(1,64),
          supportsRead  = TransferSizes(1,64))),
        beatBytes = 4)))

  val cfg_tl_node = cfg_ahb_node := LazyModule(new TLToAHB).node


  lazy val module = new LazyModuleImp(this) {
    val u_BusMatrix = new AMBABlackBox(busparam)//instantiate the black box
    busparam.APBSlaves.map( slave => {})  //connect each wire between diplomacy node and the blackbox
    busparam.AHBSlaves.map( slave => {})

  }

}

case class AHBPerMasterNode(portParams: Seq[AHBMasterPortParameters])(implicit valName: ValName) extends SourceNode(AHBImp)(portParams)
