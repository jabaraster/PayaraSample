/**
 *
 */
package info.jabara.sandbox.payara_sample.web.api;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.junit.Test;

import info.jabara.sandbox.payara_sample.entity.EUser;
import info.jabara.sandbox.payara_sample.web_api.EntityJsonMessageBodyReaderWriter;
import info.jabara.sandbox.payara_sample.web_api.ListJsonMessageBodyReaderWriter;
import info.jabara.sandbox.payara_sample.web_api.ObjectJsonMessageBodyReaderWriter;
import info.jabara.sandbox.payara_sample.web_api.resource.UserResource;

/**
 * @author jabaraster
 */
public class UserResourceTest {

    /**
     *
     */
    @SuppressWarnings({ "static-method", "boxing", "nls" })
    @Test
    public void _空データを登録できない() {
        final EUser u = new EUser();
        u.setName("");

        final String uri = "http://localhost:8081/api" //
                + UriBuilder.fromResource(UserResource.class).build() //
                + UriBuilder.fromMethod(UserResource.class, "putIndex").build();
        final Response response = ClientBuilder.newClient() //
                .target(uri) //
                .register(ObjectJsonMessageBodyReaderWriter.class, ListJsonMessageBodyReaderWriter.class,
                        EntityJsonMessageBodyReaderWriter.class)
                .request() //
                .buildPut(Entity.entity(u, MediaType.APPLICATION_JSON_TYPE)) //
                .invoke();

        assertThat(Status.BAD_REQUEST.getStatusCode(), is(response.getStatus()));
    }

    /**
     *
     */
    @SuppressWarnings({ "static-method", "boxing", "nls" })
    @Test
    public void _正常にデータ登録() {
        final EUser u = new EUser();
        u.setName("jabaraster");

        final String uri = "http://localhost:8081/api" //
                + UriBuilder.fromResource(UserResource.class).build() //
                + UriBuilder.fromMethod(UserResource.class, "putIndex").build();
        final Response response = ClientBuilder.newClient() //
                .target(uri) //
                .register(ObjectJsonMessageBodyReaderWriter.class, ListJsonMessageBodyReaderWriter.class,
                        EntityJsonMessageBodyReaderWriter.class)
                .request() //
                .buildPut(Entity.entity(u, MediaType.APPLICATION_JSON_TYPE)) //
                .invoke();

        assertThat(Status.CREATED.getStatusCode(), is(response.getStatus()));
    }
}
