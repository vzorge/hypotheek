package example;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("/lasten")
public class HelloWorld {
    @GET
    @Produces("application/json")
    public List<Lasten> getLasten() {
        // Return some cliched textual content
        return new Annuiteit(262250, 250000, 0.029, 360, 0.0075, 0.42).calculateLasten();
    }

}
