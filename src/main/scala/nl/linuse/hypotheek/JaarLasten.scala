package nl.linuse.hypotheek

import nl.linuse.hypotheek.Main._

class JaarLasten(val year: Int, val nrMonths: Int, val totalRente: Bedrag, val totalAflos: Bedrag, val restantHoofdSom: Bedrag) {

  def addMaand(maandLast: MaandLasten) = {
    new JaarLasten(maandLast.year, nrMonths + 1, totalRente + maandLast.rente, totalAflos + maandLast.aflos, math.min(restantHoofdSom, maandLast.restantHoofdSom))
  }

  def combine(jaarLast: JaarLasten) = {
    new JaarLasten(jaarLast.year, math.max(nrMonths, jaarLast.nrMonths), totalRente + jaarLast.totalRente, totalAflos + jaarLast.totalAflos, restantHoofdSom + jaarLast.restantHoofdSom)
  }

  def brutoMaandBedrag(): Bedrag = (totalAflos + totalRente) / nrMonths

  def nettoBedrag(belastingSchaal: Double, forfaitBedrag: Bedrag): Bedrag = (totalAflos + (totalRente - terugvanBelasting(belastingSchaal, forfaitBedrag))) / nrMonths

  private def terugvanBelasting(belastingSchaal: Double, forfaitBedrag: Bedrag): Bedrag = {
    val b: Bedrag = (totalRente - calcForfaitBedrag(forfaitBedrag)) * belastingSchaal
    if (b > 0.0) b else 0
  }

  private def calcForfaitBedrag(forfaitBedrag: Bedrag): Bedrag = if (nrMonths == 12) forfaitBedrag else forfaitBedrag * (nrMonths / 12.0)
}
