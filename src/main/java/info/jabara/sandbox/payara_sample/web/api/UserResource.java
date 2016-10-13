/**
 *
 */
package info.jabara.sandbox.payara_sample.web.api;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import info.jabara.sandbox.payara_sample.entity.EUser;
import info.jabara.sandbox.payara_sample.service.UserService;

/**
 * @author jabaraster
 */
@Path("/user")
@Singleton
public class UserResource {

    @Inject
    UserService userService;

    /**
     * @return
     */
    @Path("/index")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EUser> index() {
        return this.userService.getAll();
    }
}
