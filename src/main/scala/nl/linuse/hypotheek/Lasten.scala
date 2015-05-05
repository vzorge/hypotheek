package nl.linuse.hypotheek

import nl.linuse.hypotheek.Main.Bedrag

import scala.beans.BeanProperty

class Lasten(@BeanProperty var year : Int,
             @BeanProperty var bruto : Bedrag,
             @BeanProperty var netto : Bedrag)
