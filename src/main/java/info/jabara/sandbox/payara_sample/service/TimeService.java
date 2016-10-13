/**
 *
 */
package info.jabara.sandbox.payara_sample.service;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Singleton;

/**
 * @author jabaraster
 */
@Singleton
public class TimeService {

    /**
     *
     */
    public TimeService() {
        Logger.getLogger(TimeService.class.getName()).log(Level.WARNING,
                TimeService.class.getSimpleName() + " is constructed."); //$NON-NLS-1$
    }

    /**
     * @return -
     */
    @SuppressWarnings("static-method")
    public Date getNow() {
        return Calendar.getInstance().getTime();
    }
}
