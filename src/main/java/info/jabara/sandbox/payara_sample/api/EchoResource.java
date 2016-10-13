/**
 *
 */
package info.jabara.sandbox.payara_sample.api;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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
    @GET
    public String get(@PathParam("token") final String pToken) {
        return "Hello, " + pToken + "! >>> " + this.timeService.getNow(); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
