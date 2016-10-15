/**
 *
 */
package info.jabara.sandbox.payara_sample.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.ToString;
import net.arnx.jsonic.JSONHint;

/**
 * @param <E> -
 */
@MappedSuperclass
@ToString(doNotUseGetters = true)
public abstract class EntityBase<E extends EntityBase<E>> implements Serializable {
    private static final long serialVersionUID = -7982026607239255072L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long                      id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    Date                      created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    Date                      updated;

    /**
     * @return -
     */
    public Date getCreated() {
        return this.created == null ? null : new Date(this.created.getTime());
    }

    /**
     * @return
     */
    public IdValue<E> getId() {
        return this.id == null ? null : new IdValue<>(this.id.longValue());
    }

    /**
     * @return -
     */
    public Date getUpdated() {
        return this.updated == null ? null : new Date(this.updated.getTime());
    }

    /**
     * @return -
     */
    @JSONHint(ignore = true)
    public boolean isPersisted() {
        return this.id != null;
    }

    /**
     * @deprecated JSON変換用のメソッドであり、プログラムからは使わないで下さい.
     * @param pCreated
     */
    @Deprecated
    @JSONHint(name = "updated")
    public void setCreatedNotForUse(final Date pCreated) {
        this.created = pCreated == null ? null : new Date(pCreated.getTime());
    }

    /**
     * @deprecated JSON変換用のメソッドであり、プログラムからは使わないで下さい.
     * @param pId
     */
    @Deprecated
    @JSONHint(name = "id")
    public void setIdNotForUse(final Long pId) {
        this.id = pId;
    }

    /**
     * @deprecated JSON変換用のメソッドであり、プログラムからは使わないで下さい.
     * @param pUpdated
     */
    @Deprecated
    @JSONHint(name = "created")
    public void setUpdatedNotForUse(final Date pUpdated) {
        this.updated = pUpdated == null ? null : new Date(pUpdated.getTime());
    }

    @PrePersist
    void prePersist() {
        this.created = Calendar.getInstance().getTime();
        this.updated = this.created;
    }

    @PreUpdate
    void preUpdate() {
        this.updated = Calendar.getInstance().getTime();
    }
}
