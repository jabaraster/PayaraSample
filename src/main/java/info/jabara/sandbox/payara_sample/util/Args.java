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
            throw new IllegalArgumentException(String.join(" ", "引数", pArgName, "がnullです.")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        return pArg;
    }
}
