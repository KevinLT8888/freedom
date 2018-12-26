package sfc.blocks.ip.ahbslaveram
import Chisel._
class fpga_xilinx_sp_32768x32m8 (blackboxName: String)extends BlackBox {
  override def desiredName = blackboxName
  val io = new Bundle() {
    val clka = Clock(INPUT)
    val ena = Bool(INPUT)
    val wea = Bits(INPUT,4)
    val addra = Bits(INPUT,15)
    val dina = Bits(INPUT,32)
    val douta = Bits(OUTPUT,32)
  }
}

