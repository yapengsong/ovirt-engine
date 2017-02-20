package org.ovirt.engine.core.compat;

public class LongCompat {

    /**
     * Try parse an long, return null if failed.
     * @param value
     *            the string format of the number
     * @return Long or null
     */
    public static Long tryParse(final String value) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            // eat it, return null
            return null;
        }
    }
}
