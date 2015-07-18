package nl.linuse.hypotheek

import nl.linuse.hypotheek.Main.Bedrag

class Lineair(parameters: AflosvormParameters) extends Aflosvorm(parameters) {
  val maandAflossing = hoofdSom / looptijdMaanden
  override def calculateAflosBedrag(renteBedrag: Bedrag): Bedrag = maandAflossing
}
