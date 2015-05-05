package example;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.joda.time.DateTime;

@Path("lasten")
public class Hypotheek {
    @GET
    @Produces("application/json")
    public List<Lasten> getLasten(@QueryParam("hypotheeksom") double hypotheekSom,
                                  @QueryParam("wozwaarde") double wozWaarde,
                                  @QueryParam("rente") double rente,
                                  @QueryParam("looptijdMaanden") int looptijdMaanden,
                                  @QueryParam("belastingSchijf") double belastingSchijf,
                                  @QueryParam("startYear") String startDate
                                  ) {
        DateTime dateTime = new DateTime(startDate);
        return new Annuiteit(hypotheekSom, wozWaarde, rente/100.0, looptijdMaanden, 0.0075, belastingSchijf/100.0, dateTime.getMonthOfYear(), dateTime.getYear()).calculateLasten();
    }

}
