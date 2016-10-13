/**
 *
 */
package info.jabara.sandbox.payara_sample.web.api;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Calendar;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import info.jabara.sandbox.payara_sample.WebAppContainer;
import info.jabara.sandbox.payara_sample.entity.EUser;
import info.jabara.sandbox.payara_sample.entity.EntityBase;
import info.jabara.sandbox.payara_sample.web_api.resource.UserResource;
import net.arnx.jsonic.JSON;

/**
 * @author jabaraster
 */
public class UserResourceTest {

    static final int       PORT = 8082;

    static WebAppContainer _container;

    /**
     *
     */
    @SuppressWarnings({ "static-method", "boxing", "deprecation" })
    @Test
    public void _test() {
        final EUser u = new EUser();
        u.setName("jabaraster");
        u.setIdNotForUse(1L);
        u.setCreatedNotForUse(Calendar.getInstance().getTime());
        u.setUpdatedNotForUse(u.getCreated());

        final String uri = "http://localhost:8081/api" //
                + UriBuilder.fromResource(UserResource.class).build() //
                + UriBuilder.fromMethod(UserResource.class, "putIndex").build();
        final Response response = ClientBuilder.newClient() //
                .target(uri) //
                .request() //
                .buildPut(Entity.entity(new EntityBase.JsonConverter().format(u), MediaType.APPLICATION_JSON_TYPE)) //
                .invoke();

        assertThat(Status.CREATED.getStatusCode(), is(response.getStatus()));

        final String location = "http://localhost:8081/api" //
                + UriBuilder.fromResource(UserResource.class).build() //
                + UriBuilder.fromMethod(UserResource.class, "getById").build(Long.valueOf(1));
        assertThat(response.getLocation().toString(), is(location));
    }

    /**
     *
     */
    @SuppressWarnings("static-method")
    @Before
    public void setUp() {
        JSON.prototype = EntityBase.JsonConverter.class;
    }

    /**
     *
     */
    @SuppressWarnings("static-method")
    @After
    public void tearDown() {
        JSON.prototype = JSON.class;
    }

    /**
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        // _container = new
        // WebAppContainer("serverName=localhost:portNumber=5432:databaseName=app:user=app:password=xxx",
        // PORT);
        // _container.start();
    }

    /**
     */
    @AfterClass
    public static void tearDownAfterClass() {
        if (_container != null) {
            _container.stop();
        }
    }

}
