package sfc.blocks.AMBABus
import Chisel._


class AMBABlackBox (param : AMBABusMatrixParams)extends BlackBox {
    override def desiredName: String = "AMBA_Bus"
    val io = new Bundle {
        param.AHBSlaves.map( slave => new AHBSlaveBundle )
        param.APBSlaves.map( slave => {})
    }
}






class AHBSlaveBundle extends Bundle{
    val hmastlock = Bool(OUTPUT)
   // val htrans    = UInt(OUTPUT, width = params.transBits)
    val hsel      = Bool(OUTPUT)
    val hready    = Bool(OUTPUT) // on a master, drive this from readyout

    // Payload signals
    val hwrite    = Bool(OUTPUT)
   // val haddr     = UInt(OUTPUT, width = params.addrBits)
 //   val hsize     = UInt(OUTPUT, width = params.sizeBits)
 //   val hburst    = UInt(OUTPUT, width = params.burstBits)
 //   val hprot     = UInt(OUTPUT, width = params.protBits)
 //   val hwdata    = UInt(OUTPUT, width = params.dataBits)

    val hreadyout = Bool(INPUT)
    val hresp     = Bool(INPUT)
 //   val hrdata    = UInt(INPUT, width = params.dataBits)
}