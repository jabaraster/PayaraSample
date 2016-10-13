/**
 *
 */
package info.jabara.sandbox.payara_sample.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jabaraster
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hello {
    String message;
    Date   date;
}
