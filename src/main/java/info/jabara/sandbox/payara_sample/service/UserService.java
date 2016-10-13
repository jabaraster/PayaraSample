/**
 *
 */
package info.jabara.sandbox.payara_sample.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import info.jabara.sandbox.payara_sample.entity.EUser;

/**
 * @author jabaraster
 */
@Singleton
public class UserService {

    @Inject
    EntityManager em;

    /**
     * @return
     */
    @Transactional
    public List<EUser> getAll() {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        final CriteriaQuery<EUser> query = builder.createQuery(EUser.class);
        query.from(EUser.class);
        return em.createQuery(query).getResultList();
    }
}
