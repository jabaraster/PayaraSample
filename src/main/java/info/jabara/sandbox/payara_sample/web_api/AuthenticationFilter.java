/**
 *
 */
package info.jabara.sandbox.payara_sample.web_api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

/**
 * @author jabaraster
 */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    HttpServletRequest request;

    /**
     * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
     */
    @Override
    public void filter(final ContainerRequestContext pContext) throws IOException {
        // @SuppressWarnings("resource")
        // final InputStream in = pContext.getEntityStream();
        // final ByteArrayOutputStream mem = new ByteArrayOutputStream();
        // final byte[] buf = new byte[4096];
        // for (int d = in.read(buf); d != -1; d = in.read(buf)) {
        // mem.write(buf, 0, d);
        // }
        // System.out.println(mem.toString(StandardCharsets.UTF_8.name()));
    }

}
