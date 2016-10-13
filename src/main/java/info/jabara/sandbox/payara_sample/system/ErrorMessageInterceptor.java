package info.jabara.sandbox.payara_sample.system;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

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
        } catch (final Exception ex) {
            final Logger logger = Logger.getLogger(ic.getTarget().getClass().getSuperclass().getName());
            logger.log(Level.SEVERE, "エラーです", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "システムエラーが発生しました", "システムエラーが発生しました"));
            throw ex;
        }
    }
}
