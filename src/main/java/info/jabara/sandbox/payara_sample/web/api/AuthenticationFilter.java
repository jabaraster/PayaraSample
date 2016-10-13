/**
 *
 */
package info.jabara.sandbox.payara_sample.web.api;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

/**
 * @author jabaraster
 */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    /**
     * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
     */
    @SuppressWarnings("unused")
    @Override
    public void filter(final ContainerRequestContext pContext) throws IOException {
        System.out.println(pContext.getUriInfo().getPath());
        return;
    }

}
