package nl.linuse.hypotheek

import scala.beans.BeanProperty

class ViewModel() {
  @BeanProperty var hypotheken : java.util.List[HypotheekPropertiesModel] = null
  @BeanProperty var wozWaarde: Double = 0
  @BeanProperty var belastingSchijf : Double = 0
  @BeanProperty var startDate : String = ""
}
