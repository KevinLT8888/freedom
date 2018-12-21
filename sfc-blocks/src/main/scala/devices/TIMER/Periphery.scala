package sfc.blocks.timer
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
case object TIMERKey extends Field[Option[TIMERParams]](None)

trait HasPeripheryTIMER { this: BaseSubsystem =>
  p(TIMERKey).map { params =>
    val timer = LazyModule(new TIMER(params))

    sbus.control_bus.toFixedWidthSingleBeatSlave(4, Some("sfctimer_cfg")) { timer.cfg_tl_node }

    ibus.fromSync := timer.int_node
  }
}