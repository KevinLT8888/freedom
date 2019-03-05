//Connect the bus to main system looks like a normal slave mem
//
//
package sfc.blocks.AMBABus
import freechips.rocketchip.config._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.amba.apb._
import freechips.rocketchip.tilelink._
import freechips.rocketchip.interrupts._
import freechips.rocketchip.subsystem._

import freechips.rocketchip.config.Field
import freechips.rocketchip.subsystem.BaseSubsystem
import freechips.rocketchip.diplomacy.{BufferParams, LazyModule}
import freechips.rocketchip.tilelink.{TLBuffer, TLIdentityNode}
import sfc.blocks.ip.timer._
case object AMBABusMatrixKey extends Field[Option[AMBABusMatrixParams]](None)
trait HasPeripheryAHBSlaveRam { this: BaseSubsystem =>
  p(ExtMem).map {  memory =>

    p(AMBABusMatrixKey).map { bus =>  val ambaBusMatrix =  LazyModule(new AMBABusMatrix(memory,bus))

                              mbus.toDRAMController(Some("AHBMBABusMatrix"))(ambaBusMatrix.cfg_tl_node)
                            }


    //sbus.control_bus.toFixedWidthSingleBeatSlave(4, Some("AHBSlaveRam")) { AHBSlaveRam.cfg_tl_node }
   // mbus.toDRAMController(Some("AHBMBABusMatrix"))(ambaBusMatrix.cfg_tl_node)

  }
}


