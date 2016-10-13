/**
 *
 */
package info.jabara.sandbox.payara_sample.web.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author jabaraster
 */
@ApplicationPath("/api")
public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<>(Arrays.<Class<?>>asList( //
                JsonReaderWriter.class //
                , EchoResource.class //
        ));
    }
}
