package sfc.blocks.ip.timer
import Chisel._

class timer(blackboxName: String) extends BlackBox {

  override def desiredName = blackboxName
  val io = new Bundle{
    //Inputs
    val PCLK = Clock(INPUT)  //APB clock
    val PRESETn = Bool(INPUT)  //APB reset
    val PENABLE = Bool(INPUT)  //APB enable
    val PSEL = Bool(INPUT)  //APB periph select
    val PADDR = Bits(INPUT,10)  //APB address bus    original wire[11:2]//???????
    val PWRITE = Bool(INPUT)  //APB write
    val PWDATA = Bits(INPUT,32)  //APB write data

    val TIMCLK = Clock(INPUT)  //Timer clock
    val TIMCLKEN1 = Bool(INPUT)  //Timer clock enable 1
    val TIMCLKEN2 = Bool(INPUT)  //Timer clock enable 2
    val TIMCLKEN3 = Bool(INPUT)  //Timer clock enable 3
    val TIMCLKEN4 = Bool(INPUT)  //Timer clock enable 4

    //Outputs
    val PRDATA = Bits(OUTPUT,32)  //APB read data

    val TIMINT1 = Bool(OUTPUT)  //Counter 1 interrupt
    val TIMINT2 = Bool(OUTPUT)  //Counter 2 interrupt
    val TIMINT3 = Bool(OUTPUT)  //Counter 3 interrupt
    val TIMINT4 = Bool(OUTPUT)  //Counter 4 interrupt
    val TIMINTC = Bool(OUTPUT)  //Counter combined interrupt
  }
}