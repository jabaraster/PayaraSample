/**
 *
 */
package info.jabara.sandbox.payara_sample.service;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import info.jabara.sandbox.payara_sample.entity.EUser;
import info.jabara.sandbox.payara_sample.model.Duplicate;

/**
 * @author jabaraster
 */
public class UserServiceTest {

    static EntityManagerFactory _emf;
    EntityManager               em;
    UserService                 sut;

    /**
     *
     */
    @Test
    public void _getAll() {
        System.out.println(this.sut.getAll());
    }

    /**
     * @throws Duplicate
     */
    @Test
    public void _persist_nameの重複() throws Duplicate {
        final String name = "hoge";

        this.sut.persistOrUpdate(createUser(name));
        this.em.flush();
        this.em.clear();

        try {
            this.sut.persistOrUpdate(createUser(name));
            fail();
        } catch (@SuppressWarnings("unused") final Duplicate e) {
            // OK!
        }
    }

    /**
     * @throws Duplicate
     */
    @Test
    public void _update_nameの重複() throws Duplicate {
        final String name1 = "hoge";
        final String name2 = "piyo";

        this.sut.persistOrUpdate(createUser(name1));

        final EUser u = createUser(name2);
        this.sut.persistOrUpdate(u);
        this.em.flush();
        this.em.clear();

        // テスト実施
        try {
            this.sut.persistOrUpdate(u); // これは例外なし
            this.em.flush();
            this.em.clear();

            u.setName(name1);
            this.sut.persistOrUpdate(u); // ここで例外
            fail();
        } catch (@SuppressWarnings("unused") final Duplicate e) {
            // OK
        }
    }

    /**
     *
     */
    @After
    public void after() {
        if (em != null && em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    /**
     *
     */
    @Before
    public void before() {
        this.em = _emf.createEntityManager();
        this.em.getTransaction().begin();
        this.sut = new UserService(this.em);
    }

    /**
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        _emf = Persistence.createEntityManagerFactory("pu-for-ut");
    }

    /**
     */
    @AfterClass
    public static void tearDownAfterClass() {
        if (_emf != null) {
            _emf.close();
        }
    }

    private static EUser createUser(final String pName) {
        final EUser u = new EUser();
        u.setName(pName);
        return u;
    }

}
