/**
 *
 */
package info.jabara.sandbox.payara_sample.entity;

import javax.persistence.Entity;

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

    @Getter
    @Setter
    String                    name;
}
