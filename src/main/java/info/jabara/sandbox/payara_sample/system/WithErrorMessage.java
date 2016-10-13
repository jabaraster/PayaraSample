/**
 *
 */
package info.jabara.sandbox.payara_sample.system;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Priority;
import javax.interceptor.Interceptor;
import javax.interceptor.InterceptorBinding;

/**
 * @author jabaraster
 */
@InterceptorBinding
@Target(value = { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Priority(Interceptor.Priority.APPLICATION + 20)
public @interface WithErrorMessage {
    // nodef
}