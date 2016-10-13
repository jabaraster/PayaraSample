/**
 *
 */
package info.jabara.sandbox.payara_sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author jabaraster
 */
@NoArgsConstructor
@Entity
@ToString(callSuper = true)
public class EUser extends EntityBase<EUser> {
    private static final long serialVersionUID = 2512747791807206116L;

    static final int          MAX_CHAR_COUNT   = 100;

    @Getter
    @Setter
    @NotNull
    @Size(min = 1, max = MAX_CHAR_COUNT)
    @Column(length = MAX_CHAR_COUNT * 3)
    String                    name;
}
