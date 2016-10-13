/**
 *
 */
package info.jabara.sandbox.payara_sample.web_api.resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import info.jabara.sandbox.payara_sample.entity.EUser;
import info.jabara.sandbox.payara_sample.entity.IdValue;
import info.jabara.sandbox.payara_sample.model.NotFound;
import info.jabara.sandbox.payara_sample.service.UserService;

/**
 *
 */
@Path("/user")
@Singleton
public class UserResource {

    @Inject
    UserService userService;

    /**
     * @param pId
     * @return
     */
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public EUser getById(@PathParam("id") final long pId) {
        try {
            return this.userService.getById(new IdValue<>(pId));
        } catch (@SuppressWarnings("unused") final NotFound _) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }
    }

    /**
     * @return
     */
    @Path("/index")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EUser> index() {
        return this.userService.getAll();
    }

    /**
     * @param pUser
     * @return
     */
    @Path("/index")
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    public Response putIndex(final EUser pUser) {
        this.userService.persist(pUser);
        return Response.status(Status.CREATED).build();
    }
}
