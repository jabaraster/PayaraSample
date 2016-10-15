package info.jabara.sandbox.payara_sample.system;

import java.lang.reflect.Type;

import javax.validation.ConstraintViolation;

import info.jabara.sandbox.payara_sample.entity.EntityBase;
import info.jabara.sandbox.payara_sample.entity.IdValue;
import net.arnx.jsonic.JSON;

/**
 * {@link EntityBase}、{@link ConstraintViolation}をJSONに変換するためのクラス.
 */
public class JsonConverter extends JSON {
    /**
     * @see net.arnx.jsonic.JSON#postparse(net.arnx.jsonic.JSON.Context, java.lang.Object, java.lang.Class,
     *      java.lang.reflect.Type)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected <T extends Object> T postparse( //
            final Context context //
            , final Object value //
            , final Class<? extends T> cls //
            , final Type type //
    ) throws Exception {

        if ("id".equals(context.getKey())) { //$NON-NLS-1$
            if (value == null) {
                return null;
            }
            if (value instanceof Long) {
                return (T) value;
            }
            if (value instanceof Number) {
                return (T) Long.valueOf(((Number) value).longValue());
            }
        }
        return super.postparse(context, value, cls, type);
    }

    /**
     * @see net.arnx.jsonic.JSON#preformat(net.arnx.jsonic.JSON.Context, java.lang.Object)
     */
    @Override
    protected Object preformat(final Context context, final Object value) throws Exception {
        if ("id".equals(context.getKey()) && value instanceof IdValue<?>) { //$NON-NLS-1$
            final IdValue<?> id = (IdValue<?>) value;
            return Long.valueOf(id.getValue());
        }
        return super.preformat(context, value);
    }

}