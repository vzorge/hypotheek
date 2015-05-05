package nl.linuse.hypotheek

import Main.Bedrag

class Annuiteit(parameters: AflosvormParameters) extends Aflosvorm(parameters) {
  val brutoMaandBedrag: Bedrag = {
    hoofdSom * (maandRente / (1.0 - Math.pow(1.0 + maandRente, -looptijdMaanden)))
  }

  override def calculateAflosBedrag(renteBedrag: Bedrag): Bedrag = brutoMaandBedrag - renteBedrag
}