/**
 *
 */
package info.jabara.sandbox.payara_sample.web.api;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import info.jabara.sandbox.payara_sample.model.Hello;
import info.jabara.sandbox.payara_sample.service.TimeService;

/**
 * @author jabaraster
 */
@Path("/echo")
@Dependent
public class EchoResource {

    @Inject
    TimeService timeService;

    /**
     * @param pToken
     * @return -
     */
    @Path("/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Hello get(@PathParam("token") final String pToken) {
        final String msg = "Hello, " + pToken + "! >>> " + this.timeService.getNow(); //$NON-NLS-1$//$NON-NLS-2$
        return new Hello(msg, this.timeService.getNow());
    }
}
