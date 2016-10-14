/**
 *
 */
package info.jabara.sandbox.payara_sample.web_api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 *
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ListJsonMessageBodyReaderWriter extends JsonMessageBodyReaderWriterBase<List<?>> {
    // nodef
}
