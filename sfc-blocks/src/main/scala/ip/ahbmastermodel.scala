package sfc.blocks.ip.ahbmaster

import Chisel._

class ahb_master_model  extends BlackBox{
  override def desiredName = "cmsdk_ahb_fileread_master32"
  val io = new Bundle(){
  
  val    HCLK      =    Clock(INPUT)    
  val    HRESETn   =    Bool(INPUT)
  
  val    HREADY    =    Bool(INPUT)
  val    HRESP     =    Bool(INPUT)
  val    HRDATA    =    Bits(INPUT,32)
  val    EXRESP    =    Bool(INPUT)


  val    HTRANS    =    Bits(OUTPUT,2)
  val    HBURST    =    Bits(OUTPUT,3)
  val    HPROT     =    Bits(OUTPUT,4)
  val    EXREQ     =    Bool(OUTPUT)
  val    MEMATTR   =    Bits(OUTPUT,2)
  val    HSIZE     =    Bits(OUTPUT,3)
  val    HWRITE    =    Bool(OUTPUT) 
  val    HMASTLOCK =    Bool(OUTPUT)
  val    HADDR     =    Bits(OUTPUT,32)
  val    HWDATA    =    Bits(OUTPUT,32)

  val    LINENUM   =    Bits(OUTPUT,32)

  }

}
