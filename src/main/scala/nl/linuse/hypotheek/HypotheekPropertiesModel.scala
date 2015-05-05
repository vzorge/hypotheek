package nl.linuse.hypotheek

import scala.beans.BeanProperty

class HypotheekPropertiesModel() {
  @BeanProperty var hypotheekSom : Double = 0
  @BeanProperty var rente: Double = 0
  @BeanProperty var looptijdMaanden: Int = 0
}
