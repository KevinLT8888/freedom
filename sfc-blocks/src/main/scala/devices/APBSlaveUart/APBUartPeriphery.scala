package src.main.scala.devices.APBSlaveUart

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
import sfc.blocks.ip.apbuart._
case object AHBSlaveRamKey extends Field[Option[APBSlaveUartParams]](None)

trait HasPeripheryAPBSlaveUart { this: BaseSubsystem =>
  p(AHBSlaveRamKey).map { params =>
    val APBSlaveUart = LazyModule(new APBUART(params))

    sbus.control_bus.toFixedWidthSingleBeatSlave(2, Some("AHBSlaveRam")) { APBSlaveUart.cfg_tl_node }
    
  }
}
trait HasPeripheryAPBSlaveUartImp