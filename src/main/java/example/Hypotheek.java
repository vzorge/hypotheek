package example;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("lasten")
public class Hypotheek {
    @GET
    @Produces("application/json")
    public List<Lasten> getLasten(@QueryParam("hypotheeksom") double hypotheekSom,
                                  @QueryParam("wozwaarde") double wozWaarde) {
        return new Annuiteit(hypotheekSom, wozWaarde, 0.029, 360, 0.0075, 0.42).calculateLasten();
    }

}
