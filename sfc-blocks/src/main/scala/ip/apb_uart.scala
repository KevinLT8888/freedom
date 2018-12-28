package sfc.blocks.ip.apbuart
import Chisel._

class apb_uart (blackboxName: String) extends BlackBox {

  override def desiredName = blackboxName
  val io = new Bundle{
  
    val PCLK = Clock(INPUT)       // APB Bus Clock
    val UARTCLK = Clock(INPUT)    // Main UART Clock
    val PRESETn = Bool(INPUT)     // AMBA Bus reset
    val nUARTRST = Bool(INPUT)    // UART Reset
    val PSEL = Bool(INPUT)        //APB periph select
    val PENABLE = Bool(INPUT)     //APB enable

    val PADDR = Bits(INPUT,10)    //APB address bus
    val PWRITE = Bool(INPUT)      //APB write
    val PWDATA = Bits(INPUT,16)   //APB write data

    val nUARTCTS = Bool(INPUT)
    val nUARTDCD = Bool(INPUT)
    val nUARTDSR = Bool(INPUT)
    val nUARTRI = Bool(INPUT)
    val UARTRXD = Bool(INPUT)
    val SIRIN = Bool(INPUT)
    val UARTTXDMACLR = Bool(INPUT)
    val UARTRXDMACLR = Bool(INPUT)



    val UARTMSINTR = Bool(OUTPUT)
    val UARTRXINTR = Bool(OUTPUT)
    val UARTTXINTR = Bool(OUTPUT)
    val UARTRTINTR = Bool(OUTPUT) 
    val UARTEINTR = Bool(OUTPUT)
    val UARTINTR = Bool(OUTPUT)

    val PRDATA = Bits(OUTPUT,16)

    val UARTTXD = Bool(OUTPUT)
    val nSIROUT = Bool(OUTPUT)
    val nUARTOut2 = Bool(OUTPUT)
    val nUARTOut1 = Bool(OUTPUT)
    val nUARTRTS = Bool(OUTPUT)
    val nUARTDTR = Bool(OUTPUT)
    val UARTTXDMASREQ = Bool(OUTPUT)
    val UARTTXDMABREQ = Bool(OUTPUT)
    val UARTRXDMASREQ = Bool(OUTPUT)
    val UARTRXDMABREQ = Bool(OUTPUT)

  }

}
