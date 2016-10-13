/**
 *
 */
package info.jabara.sandbox.payara_sample.service;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import info.jabara.sandbox.payara_sample.entity.EUser;
import info.jabara.sandbox.payara_sample.entity.IdValue;
import info.jabara.sandbox.payara_sample.model.NotFound;

/**
 *
 */
@RequestScoped
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

    /**
     * @param pId
     * @return
     * @throws NotFound
     */
    public EUser getById(final IdValue<EUser> pId) throws NotFound {
        final EUser ret = em.find(EUser.class, Long.valueOf(pId.getValue()));
        if (ret == null) {
            throw NotFound.INSTANCE;
        }
        return ret;
    }

    /**
     * @param pUser
     */
    @Transactional
    public void persist(final EUser pUser) {
        this.em.persist(pUser);
        this.em.flush();
    }
}
