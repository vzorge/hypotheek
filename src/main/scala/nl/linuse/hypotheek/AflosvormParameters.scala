package nl.linuse.hypotheek

import nl.linuse.hypotheek.Main.Bedrag

class AflosvormParameters(val hoofdSom: Bedrag, val jaarRente: Double, val looptijdMaanden: Int, val forfaitBedrag: Double,
                          val belastingSchaal: Double, val startMonth: Int, val startYear: Int)