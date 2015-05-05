package nl.linuse.hypotheek;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("lasten")
public class HypotheekEndPoint {
    private static final double FORFAIT = 0.0075;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Lasten> getLasten(ViewModel viewModel) {
        return new HypotheekBerekening(viewModel).calculate();
    }

}
