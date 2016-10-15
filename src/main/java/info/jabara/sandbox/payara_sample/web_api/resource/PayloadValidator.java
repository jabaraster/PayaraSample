/**
 *
 */
package info.jabara.sandbox.payara_sample.web_api.resource;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import info.jabara.sandbox.payara_sample.util.Args;

/**
 * @author jabaraster
 */
@Dependent
public class PayloadValidator {

    @Inject
    Validator validator;

    @SuppressWarnings("static-method")
    ResponseBuilder badRequest(final Set<ConstraintViolation<?>> pViolations) {
        return Response.status(Status.BAD_REQUEST) //
                .entity(pViolations.stream().map((v) -> {
                    return String.join(":", String.valueOf(v.getPropertyPath()), v.getMessage());
                }) //
                        .collect(Collectors.toList())) //
        ;
    }

    void validate(final Object pEntity) throws WebApplicationException {
        Args.checkNull(pEntity, "pEntity");

        final Set<ConstraintViolation<Object>> violations = this.validator.validate(pEntity);
        if (violations.isEmpty()) {
            return;
        }

        throw new WebApplicationException(Response.status(Status.BAD_REQUEST) //
                .entity(cnv(violations)) //
                .build());
    }

    private static <E> List<String> cnv(final Set<ConstraintViolation<E>> violations) {
        return violations.stream().map((v) -> {
            return String.join(":", String.valueOf(v.getPropertyPath()), v.getMessage());
        }).collect(Collectors.toList());
    }
}
