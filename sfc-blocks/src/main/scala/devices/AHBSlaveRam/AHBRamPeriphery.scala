package sfc.blocks.AHBRam
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
case object AHBSlaveRamKey extends Field[Option[AHBSlaveRamParams]](None)

trait HasPeripheryAHBSlaveRam { this: BaseSubsystem =>
  p(AHBSlaveRamKey).map { params =>
    val AHBSlaveRam = LazyModule(new AHBSlaveRam(params))

    sbus.control_bus.toFixedWidthSingleBeatSlave(4, Some("AHBSlaveRam")) { AHBSlaveRam.cfg_tl_node }
    
  }
}