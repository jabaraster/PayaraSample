package info.jabara.sandbox.payara_sample.system;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.WebApplicationException;

/**
 * @author jabaraster
 */
@Interceptor
@Dependent
@WithErrorMessage
public class ErrorMessageInterceptor implements Serializable {
    private static final long serialVersionUID = -226241703513422035L;

    @SuppressWarnings("static-method")
    @AroundInvoke
    Object invoke(final InvocationContext ic) throws Exception {
        try {
            return ic.proceed();

        } catch (final WebApplicationException e) {
            throw e;

        } catch (final Exception e) {
            final Logger logger = Logger.getLogger(ic.getTarget().getClass().getSuperclass().getName());
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw e;
        }
    }
}
