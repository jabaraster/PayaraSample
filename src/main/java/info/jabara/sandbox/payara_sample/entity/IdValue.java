/**
 *
 */
package info.jabara.sandbox.payara_sample.entity;

import java.io.Serializable;

/**
 * @param <E> -
 */
public class IdValue<E extends EntityBase<E>> implements Serializable {
    private static final long serialVersionUID = 3664139031231941425L;

    private final long        value;

    /**
     * @param pValue -
     */
    public IdValue(final long pValue) {
        this.value = pValue;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final IdValue<?> other = (IdValue<?>) obj;
        if (this.value != other.value)
            return false;
        return true;
    }

    /**
     * @return
     */
    public long getValue() {
        return this.value;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (this.value ^ (this.value >>> 32));
        return result;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "IdValue [value=" + this.value + "]";
    }
}
