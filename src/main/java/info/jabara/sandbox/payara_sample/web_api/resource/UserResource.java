/**
 *
 */
package info.jabara.sandbox.payara_sample.web_api.resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
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
import javax.ws.rs.core.UriBuilder;

import info.jabara.sandbox.payara_sample.entity.EUser;
import info.jabara.sandbox.payara_sample.entity.IdValue;
import info.jabara.sandbox.payara_sample.model.Duplicate;
import info.jabara.sandbox.payara_sample.model.NotFound;
import info.jabara.sandbox.payara_sample.service.UserService;
import info.jabara.sandbox.payara_sample.system.WithErrorMessage;

/**
 *
 */
@Path("/user")
@Singleton
@WithErrorMessage
public class UserResource {

    @Inject
    UserService      userService;

    @Inject
    PayloadValidator validator;

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
        } catch (@SuppressWarnings("unused") final NotFound e) {
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
    @Transactional(dontRollbackOn = { ConstraintViolationException.class, Duplicate.class })
    public Response putIndex(final EUser pUser) {
        if (pUser == null) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        // this.validator.validate(pUser);

        if (pUser.isPersisted()) {
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST) //
                    .entity("persisted.") //
                    .build());
        }
        try {
            this.userService.persistOrUpdate(pUser);
        } catch (final ConstraintViolationException e) {
            throw new WebApplicationException(this.validator.badRequest(e.getConstraintViolations()).build());
        } catch (@SuppressWarnings("unused") final Duplicate e) {
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST) //
                    .entity("name:duplicated.") //
                    .build());
        }

        final String uri = "/api" // //$NON-NLS-1$
                + UriBuilder.fromResource(UserResource.class) //
                        .build().toString() //
                + UriBuilder.fromMethod(UserResource.class, "getById") // //$NON-NLS-1$
                        .build(Long.valueOf(pUser.getId().getValue())).toString();
        return Response.created(UriBuilder.fromPath(uri).build()) //
                .entity(pUser) //
                .build();
    }
}
