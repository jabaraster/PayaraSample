/**
 *
 */
package info.jabara.sandbox.payara_sample.system;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author jabaraster
 */
@Dependent
public class EntityManagerProvider {
    @PersistenceContext(unitName = "pu")
    @Produces
    EntityManager em;
}
