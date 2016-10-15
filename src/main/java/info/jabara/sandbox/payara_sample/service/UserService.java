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
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;

import info.jabara.sandbox.payara_sample.entity.EUser;
import info.jabara.sandbox.payara_sample.entity.EUser_;
import info.jabara.sandbox.payara_sample.entity.EntityBase_;
import info.jabara.sandbox.payara_sample.entity.IdValue;
import info.jabara.sandbox.payara_sample.model.Duplicate;
import info.jabara.sandbox.payara_sample.model.NotFound;
import info.jabara.sandbox.payara_sample.util.Args;

/**
 *
 */
@RequestScoped
public class UserService {

    private final EntityManager em;

    /**
     * @param pEm
     */
    @Inject
    public UserService(final EntityManager pEm) {
        this.em = pEm;
    }

    /**
     * @return
     */
    public List<EUser> getAll() {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<EUser> query = builder.createQuery(EUser.class);
        query.from(EUser.class);
        return this.em.createQuery(query).getResultList();
    }

    /**
     * @param pId
     * @return
     * @throws NotFound
     */
    public EUser getById(final IdValue<EUser> pId) throws NotFound {
        Args.checkNull(pId, "pId"); //$NON-NLS-1$
        final EUser ret = this.em.find(EUser.class, Long.valueOf(pId.getValue()));
        if (ret == null) {
            throw NotFound.INSTANCE;
        }
        return ret;
    }

    /**
     * @param pUser
     * @throws ConstraintViolationException
     * @throws Duplicate
     */
    public void persistOrUpdate(final EUser pUser) throws ConstraintViolationException, Duplicate {
        Args.checkNull(pUser, "pUser"); //$NON-NLS-1$
        if (pUser.isPersisted()) {
            checkUniqueOnUpdate(pUser);
            final EUser inDb = this.em.merge(pUser);
            inDb.setName(pUser.getName());
        } else {
            checkUniqueOnPersist(pUser);
            this.em.persist(pUser);
        }
        this.em.flush();
    }

    private void checkUniqueOnPersist(final EUser pUser) throws Duplicate {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);
        final Root<EUser> root = query.from(EUser.class);
        query.where(builder.equal(root.get(EUser_.name), pUser.getName()));
        query.select(builder.count(root));

        if (em.createQuery(query).getSingleResult().longValue() > 0) {
            throw Duplicate.INSTANCE;
        }
    }

    private void checkUniqueOnUpdate(final EUser pUser) throws Duplicate {
        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Long> query = builder.createQuery(Long.class);
        final Root<EUser> root = query.from(EUser.class);
        query.where( //
                builder.equal(root.get(EUser_.name), pUser.getName()) //
                , builder.not(builder.equal(root.get(EntityBase_.id), Long.valueOf(pUser.getId().getValue()))) //
        );
        query.select(builder.count(root));

        if (em.createQuery(query).getSingleResult().longValue() > 0) {
            throw Duplicate.INSTANCE;
        }
    }
}
