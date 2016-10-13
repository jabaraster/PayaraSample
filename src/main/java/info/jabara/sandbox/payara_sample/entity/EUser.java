/**
 *
 */
package info.jabara.sandbox.payara_sample.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jabaraster
 */
@Data
@NoArgsConstructor
@Entity
public class EUser implements Serializable {
    private static final long serialVersionUID = 2512747791807206116L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long                      id;

    String                    name;
}
