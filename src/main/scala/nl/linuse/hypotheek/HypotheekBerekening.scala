package nl.linuse.hypotheek

import org.joda.time.DateTime
import scala.collection.JavaConverters._

class HypotheekBerekening(viewModel: ViewModel) {
  val forfaitPercentage = 0.0075
  val hypotheken: Seq[HypotheekPropertiesModel] = viewModel.getHypotheken.asScala
  val wozWaarde = viewModel.wozWaarde
  val belastingSchijf = viewModel.belastingSchijf
  val monthOfYear = new DateTime(viewModel.startDate).getMonthOfYear
  val year = new DateTime(viewModel.startDate).getYear

  def calculate(): java.util.List[Lasten] = {
    val values: Map[Int, Lasten] = hypotheken.flatMap(v => getAflosVorm(v).calculateLasten()).groupBy(_.year)
      .mapValues(_.foldLeft(new Lasten(0, 0, 0))((acc: Lasten, last: Lasten) => combineLasten(acc, last)))
    values.toList.map(_._2).sortBy(_.year).asJava
  }

  private def getAflosVorm(v: HypotheekPropertiesModel): Aflosvorm = {
    if (v.aflosvorm == "Annuiteit") {
      new Annuiteit(createHypotheekProperties(v))
    }
    else {
      new Lineair(createHypotheekProperties(v))
    }
  }

  private def createHypotheekProperties(v: HypotheekPropertiesModel): AflosvormParameters = {
    new AflosvormParameters(v.getHypotheekSom, v.getRente / 100.0, v.getLooptijdMaanden, wozWaarde * forfaitPercentage, belastingSchijf / 100.0, monthOfYear, year)
  }

  private def combineLasten(acc: Lasten, last: Lasten): Lasten = new Lasten(last.year, last.bruto + acc.bruto, last.netto + acc.netto)
}
