package nl.linuse.hypotheek

import nl.linuse.hypotheek.Main.Bedrag

class Aflossingsvrij(parameters: AflosvormParameters) extends Aflosvorm(parameters) {
  override def calculateAflosBedrag(renteBedrag: Bedrag): Bedrag = 0
}
