package nl.linuse.hypotheek

import Main.Bedrag

class Annuiteit(hoofdSom : Bedrag, jaarRente : Double, looptijdMaanden: Int, forfaitBedrag: Double,
                belastingSchaal: Double, startMonth : Int, startYear : Int) {
    val maandRente : Bedrag = {
      Math.pow(1.0 + jaarRente, 1.0 / 12.0) - 1.0
    }

    val brutoMaandBedrag: Bedrag = {
      hoofdSom * (maandRente / (1.0 - Math.pow(1.0 + maandRente, -looptijdMaanden)))
    }

    def renteAflos() : Seq[MaandLasten] = {
      def renteAflosSub (month : Int, year : Int, bedrag: Bedrag, acc: Seq[MaandLasten]): Seq[MaandLasten] = {
        if ( bedrag <= 0.01 ) {
          acc
        } else {
          val renteBedrag = maandRente * bedrag
          val aflosBedrag: Bedrag = brutoMaandBedrag - renteBedrag
          val maandLasten: MaandLasten = new MaandLasten(month, year, renteBedrag, aflosBedrag)
          val newDate = calcNewDate(month, year)
          renteAflosSub(newDate._1, newDate._2, bedrag - aflosBedrag, acc :+ maandLasten)
        }
      }
      renteAflosSub(startMonth, startYear, hoofdSom, List())
    }

    def calcNewDate(month : Int, year : Int): (Int, Int) = {
      if (month == 12) (1, year + 1) else (month + 1, year)
    }

    def nettoList(brutoList : Seq[MaandLasten]) : Seq[Lasten] = {
      val values: Map[Int, JaarLasten] = brutoList.groupBy(_.year).mapValues(seq => seq.foldLeft(new JaarLasten(0,0,0))((jaarLast, maand) => jaarLast.addMaand(maand)))
      values.toList.map(tuple => new Lasten(tuple._1, tuple._2.brutoBedrag(), tuple._2.nettoBedrag())).sortBy(_.year)
    }


    def calculateLasten() : Seq[Lasten] = {
      nettoList(renteAflos())
    }

    class MaandLasten(val month : Int, val year : Int, val rente : Bedrag, val aflos : Bedrag)

    class JaarLasten(nrMonths : Int, totalRente : Bedrag, totalAflos : Bedrag) {

      def addMaand(maandLast : MaandLasten) = {
        new JaarLasten(nrMonths + 1, totalRente + maandLast.rente, totalAflos + maandLast.aflos)
      }

      def brutoBedrag() : Bedrag = round((totalAflos + totalRente) / nrMonths)
      def nettoBedrag() : Bedrag = round((totalAflos + (totalRente - terugvanBelasting)) / nrMonths)

      private def terugvanBelasting: Bedrag = {
        val b: Bedrag = (totalRente - calcForfaitBedrag()) * belastingSchaal
        if (b > 0.0) b else 0
      }

      private def calcForfaitBedrag(): Bedrag = if (nrMonths == 12) forfaitBedrag else forfaitBedrag * (nrMonths / 12.0)

      def round (bedrag : Bedrag) : Bedrag = {
        BigDecimal(bedrag).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
      }
    }
}