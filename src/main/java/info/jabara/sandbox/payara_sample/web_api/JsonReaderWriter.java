/**
 *
 */
package info.jabara.sandbox.payara_sample.web_api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import info.jabara.sandbox.payara_sample.entity.EntityBase;

/**
 * @author jabaraster
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JsonReaderWriter implements MessageBodyReader<Object>, MessageBodyWriter<Object> {

    /**
     * @see javax.ws.rs.ext.MessageBodyWriter#getSize(java.lang.Object, java.lang.Class, java.lang.reflect.Type,
     *      java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
     */
    @Override
    public long getSize(final Object pArg0, final Class<?> pArg1, final Type pArg2, final Annotation[] pArg3,
            final MediaType pArg4) {
        return -1;
    }

    /**
     * @see javax.ws.rs.ext.MessageBodyReader#isReadable(java.lang.Class, java.lang.reflect.Type,
     *      java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
     */
    @Override
    public boolean isReadable(final Class<?> pArg0, final Type pArg1, final Annotation[] pArg2,
            final MediaType pMediaType) {
        return MediaType.APPLICATION_JSON_TYPE.isCompatible(pMediaType);
    }

    /**
     * @see javax.ws.rs.ext.MessageBodyWriter#isWriteable(java.lang.Class, java.lang.reflect.Type,
     *      java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
     */
    @Override
    public boolean isWriteable(final Class<?> pArg0, final Type pArg1, final Annotation[] pArg2,
            final MediaType pMediaType) {
        return MediaType.APPLICATION_JSON_TYPE.isCompatible(pMediaType);
    }

    /**
     * @see javax.ws.rs.ext.MessageBodyReader#readFrom(java.lang.Class, java.lang.reflect.Type,
     *      java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap,
     *      java.io.InputStream)
     */
    @Override
    public Object readFrom(final Class<Object> pValueType, final Type pArg1, final Annotation[] pArg2,
            final MediaType pArg3, final MultivaluedMap<String, String> pArg4, final InputStream pData)
            throws IOException, WebApplicationException {
        return new EntityBase.JsonConverter().parse(pData, pValueType);
    }

    /**
     * @see javax.ws.rs.ext.MessageBodyWriter#writeTo(java.lang.Object, java.lang.Class, java.lang.reflect.Type,
     *      java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap,
     *      java.io.OutputStream)
     */
    @Override
    public void writeTo(final Object pValue, final Class<?> pArg1, final Type pArg2, final Annotation[] pArg3,
            final MediaType pArg4, final MultivaluedMap<String, Object> pArg5, final OutputStream pStream)
            throws IOException, WebApplicationException {
        new EntityBase.JsonConverter().format(pValue, pStream);
    }

}
