// 
// Decompiled by Procyon v0.5.36
// 

package vecmath;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

class VecMathI18N
{
    static String getString(final String key) {
        String s;
        try {
            s = ResourceBundle.getBundle("org.scijava.vecmath.ExceptionStrings").getString(key);
        }
        catch (MissingResourceException e) {
            System.err.println("VecMathI18N: Error looking up: " + key);
            s = key;
        }
        return s;
    }
}
