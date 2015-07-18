package nl.linuse.hypotheek

import nl.linuse.hypotheek.Main.Bedrag
import org.joda.time.DateTime

abstract class Aflosvorm(parameters: AflosvormParameters) {
  val hoofdSom: Bedrag = parameters.hoofdSom
  val looptijdMaanden: Int = parameters.looptijdMaanden
  val startMonth: Int = parameters.startMonth
  val startYear: Int = parameters.startYear
  val maandRente: Bedrag = Math.pow(1.0 + parameters.jaarRente, 1.0 / 12.0) - 1.0
  val endDate: DateTime = new DateTime().withMonthOfYear(startMonth).withYear(startYear).plusMonths(looptijdMaanden)
  def calculateAflosBedrag(renteBedrag: Bedrag): Bedrag

  def renteAflos(): Seq[MaandLasten] = {
    def renteAflosSub(month: Int, year: Int, bedrag: Bedrag, acc: Seq[MaandLasten]): Seq[MaandLasten] = {
      if (bedrag <= 0.01 || (endDate.getYear == year && endDate.getMonthOfYear == month)) {
        acc
      } else {
        val renteBedrag = maandRente * bedrag
        val aflosBedrag: Bedrag = calculateAflosBedrag(renteBedrag)
        val restantHoofdSom: Bedrag = bedrag - aflosBedrag
        val maandLasten: MaandLasten = new MaandLasten(month, year, renteBedrag, aflosBedrag, restantHoofdSom)
        val newDate = calcNewDate(month, year)
        renteAflosSub(newDate._1, newDate._2, restantHoofdSom, acc :+ maandLasten)
      }
    }
    renteAflosSub(startMonth, startYear, hoofdSom, List())
  }

  def calcNewDate(month: Int, year: Int): (Int, Int) = {
    if (month == 12) (1, year + 1) else (month + 1, year)
  }

  def jaarLasten(brutoList: Seq[MaandLasten]): Seq[JaarLasten] = {
    brutoList.groupBy(_.year).mapValues(seq => seq.foldLeft(new JaarLasten(0, 0, 0, 0, hoofdSom))((jaarLast, maand) => jaarLast.addMaand(maand))).toList.map(_._2)
  }

  def calculateLasten(): Seq[JaarLasten] = {
    jaarLasten(renteAflos())
  }

}