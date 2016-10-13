/**
 *
 */
package info.jabara.sandbox.payara_sample.util;

/**
 * @author jabaraster
 */
public final class Args {

    private Args() {
        // noop
    }

    /**
     * @param <A>
     * @param pArg
     * @param pArgName
     * @return
     */
    public static <A> A checkNull(final A pArg, final String pArgName) {
        if (pArg == null) {
            throw new IllegalArgumentException("引数 " + pArgName + " がnullです.");
        }
        return pArg;
    }
}
