package sfc.blocks.ip.ahbslaveram

import Chisel._

class ahb_ram (blackboxName: String,ADDR_WIDTH: Int,MEM_DEPTH : Int) extends BlackBox{
  override def desiredName = blackboxName
  val io = new Bundle() {
    val hclk = Clock(INPUT)
    val hresetn = Bool(INPUT)

    //input ahb slv int
    val haddr = Bits(INPUT,ADDR_WIDTH)//memory address wires
    val htrans = Bits(INPUT,2)
    val hwrite = Bool(INPUT)
    val hwdata = Bits(INPUT,32)
    val hsize  = Bits(INPUT,3)
    val hsel   = Bool(INPUT)
    val hready = Bool(INPUT)

    //output ahb slv inf 
    val hrdata = Bits(OUTPUT,32)
    val hreadyout = Bool(OUTPUT)
    val hresp = Bool(OUTPUT)

    //output mem inf
    val ram_csn = Bool(OUTPUT)
    val ram_wrn = Bits(OUTPUT,4)
    val ram_addr = Bits(OUTPUT,MEM_DEPTH)
    val ram_din = Bits(OUTPUT,32)

    //input memory inf
    val ram_dout = Bits(INPUT,32)

  }
}
