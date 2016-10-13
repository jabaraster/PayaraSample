/**
 *
 */
package info.jabara.sandbox.payara_sample.web_api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import info.jabara.sandbox.payara_sample.web_api.resource.EchoResource;
import info.jabara.sandbox.payara_sample.web_api.resource.UserResource;

/**
 * @author jabaraster
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
    /**
     * @see javax.ws.rs.core.Application#getClasses()
     */
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.asList( //
                JsonReaderWriter.class //
                , EchoResource.class //
                , UserResource.class //
        ));
    }
}
