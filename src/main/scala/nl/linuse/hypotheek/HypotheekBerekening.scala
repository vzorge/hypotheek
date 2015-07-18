package nl.linuse.hypotheek

import nl.linuse.hypotheek.Main._
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
    val jaarLastenPerDeel: Seq[JaarLasten] = hypotheken.flatMap(v => getAflosVorm(v).calculateLasten())
    val values: Seq[JaarLasten] = jaarLastenPerDeel
        .groupBy(_.year)
        .mapValues(_.foldLeft(new JaarLasten(0, 0, 0, 0, 0))((acc: JaarLasten, last: JaarLasten) => acc.combine(last)))
        .toList.map(_._2)
    values.map(l => toLasten(l)).sortBy(_.year).asJava
  }

  private def getAflosVorm(v: HypotheekPropertiesModel): Aflosvorm = {
    val properties: AflosvormParameters = createHypotheekProperties(v)
    v.aflosvorm match {
      case "Annuiteit" => new Annuiteit(properties)
      case "Lineair" => new Lineair(properties)
      case _ => new Aflossingsvrij(properties)
    }
  }

  private def createHypotheekProperties(v: HypotheekPropertiesModel): AflosvormParameters = {
    new AflosvormParameters(v.getHypotheekSom, v.getRente / 100.0, v.getLooptijdMaanden, monthOfYear, year)
  }

  private def toLasten(last: JaarLasten): Lasten = new Lasten(last.year, round(last.brutoMaandBedrag()),
        round(last.nettoBedrag(belastingSchijf / 100.0, wozWaarde * forfaitPercentage)), round(last.restantHoofdSom))

  private def round(bedrag: Bedrag): Bedrag = BigDecimal(bedrag).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble





}
