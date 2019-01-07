package sfc.blocks.APBSlaveUart

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
import sifive.blocks.devices.uart._

case object PeripheryAPBSlaveUartKey extends Field[Seq[APBSlaveUartParams]]

trait HasPeripheryAPBSlaveUart { this: BaseSubsystem =>
  val apbUartNodes = p(PeripheryAPBSlaveUartKey).map { params =>
    val apbUart = LazyModule(new APBUART(params))
    sbus.control_bus.toFixedWidthSingleBeatSlave(4, Some("sfcApbUart")){apbUart.cfg_tl_node}
    apbUart.ioNode.makeSink
  }
}

trait HasPeripheryAPBSlaveUartImp extends LazyModuleImp with HasPeripheryUARTBundle {
  val outer: HasPeripheryAPBSlaveUart
  val apbuart = outer.apbUartNodes.zipWithIndex.map{case (n,i) => n.makeIO()(ValName(s"apbUart_$i"))}
}