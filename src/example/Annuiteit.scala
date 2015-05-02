package example;

import example.Main.Bedrag
import scala.beans.BeanProperty
import scala.collection.JavaConverters._

class Annuiteit(hoofdSom : Bedrag, wozWaarde : Bedrag, jaarRente : Double, looptijdMaanden: Int, forfait: Double, belastingSchaal: Double) {
    val forfaitPerMaand = (wozWaarde * forfait) / 12
    val maandRente : Bedrag = {
      Math.pow(1.0 + jaarRente, 1.0 / 12.0) - 1.0
    }

    val brutoMaandBedrag: Bedrag = {
      hoofdSom * (maandRente / (1.0 - Math.pow(1.0 + maandRente, -looptijdMaanden)))
    }

    def renteAflos() : Seq[(Bedrag, Bedrag)] = {
      def renteAflosSub ( bedrag: Bedrag, acc: Seq[(Bedrag, Bedrag)]): Seq[(Bedrag, Bedrag)] = {
        if ( bedrag <= 0.01 ) {
          acc
        } else {
          val renteBedrag = maandRente * bedrag
          val aflosBedrag: Bedrag = brutoMaandBedrag - renteBedrag
          renteAflosSub(bedrag - aflosBedrag, acc :+ (renteBedrag, aflosBedrag))
        }
      }
      renteAflosSub(hoofdSom, List())
    }

    def nettoList(brutoList : Seq[(Bedrag, Bedrag)]) : Seq[Lasten] = {
      brutoList.map(t => new Lasten(round(t._1 + t._2), round(netto(t._1, t._2))))
    }

    def netto(brutoRente : Bedrag, aflosBedrag: Bedrag) : Bedrag = {
      val belastingVoordeel: Bedrag = brutoRente * belastingSchaal - forfaitPerMaand
      if (belastingVoordeel > 0) {
        aflosBedrag + (brutoRente - belastingVoordeel)
      }
      else {
        aflosBedrag + brutoRente
      }
    }

    def round (bedrag : Bedrag) : Bedrag = {
      BigDecimal(bedrag).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
    }

    def calculateLasten() : java.util.List[Lasten] = {
      nettoList(renteAflos()).asJava
    }
}

class Lasten(@BeanProperty var bruto : Bedrag,
             @BeanProperty var netto : Bedrag) {

}