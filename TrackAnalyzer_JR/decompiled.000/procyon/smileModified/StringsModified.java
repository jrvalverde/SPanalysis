// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.Function;
import java.util.Arrays;
import java.text.DecimalFormat;

public interface StringsModified
{
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.####");
    
    default boolean isNullOrEmpty(final String s) {
        return s == null || s.isEmpty();
    }
    
    default String unescape(final String s) {
        final StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            if (ch == '\\') {
                final char nextChar = (i == s.length() - 1) ? '\\' : s.charAt(i + 1);
                if (nextChar >= '0' && nextChar <= '7') {
                    String code = String.valueOf(nextChar);
                    if (++i < s.length() - 1 && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '7') {
                        code = String.valueOf(code) + s.charAt(i + 1);
                        if (++i < s.length() - 1 && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '7') {
                            code = String.valueOf(code) + s.charAt(i + 1);
                            ++i;
                        }
                    }
                    sb.append((char)Integer.parseInt(code, 8));
                    continue;
                }
                switch (nextChar) {
                    case '\\': {
                        ch = '\\';
                        break;
                    }
                    case 'b': {
                        ch = '\b';
                        break;
                    }
                    case 'f': {
                        ch = '\f';
                        break;
                    }
                    case 'n': {
                        ch = '\n';
                        break;
                    }
                    case 'r': {
                        ch = '\r';
                        break;
                    }
                    case 't': {
                        ch = '\t';
                        break;
                    }
                    case '\"': {
                        ch = '\"';
                        break;
                    }
                    case '\'': {
                        ch = '\'';
                        break;
                    }
                    case 'u': {
                        if (i >= s.length() - 5) {
                            ch = 'u';
                            break;
                        }
                        final int code2 = Integer.parseInt(s.substring(i + 2, i + 6), 16);
                        sb.append(Character.toChars(code2));
                        i += 5;
                        continue;
                    }
                }
                ++i;
            }
            sb.append(ch);
        }
        return sb.toString();
    }
    
    default String ordinal(final int i) {
        final String[] suffixes = { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
        switch (i % 100) {
            case 11:
            case 12:
            case 13: {
                return String.valueOf(i) + "th";
            }
            default: {
                return String.valueOf(i) + suffixes[i % 10];
            }
        }
    }
    
    default String leftPad(final String s, final int size, final char padChar) {
        if (s == null) {
            return null;
        }
        final int pads = size - s.length();
        if (pads <= 0) {
            return s;
        }
        return fill(padChar, pads).concat(s);
    }
    
    default String rightPad(final String s, final int size, final char padChar) {
        if (s == null) {
            return null;
        }
        final int pads = size - s.length();
        if (pads <= 0) {
            return s;
        }
        return s.concat(fill(padChar, pads));
    }
    
    default String fill(final char ch, final int len) {
        final char[] chars = new char[len];
        Arrays.fill(chars, ch);
        return new String(chars);
    }
    
    default String format(final float x) {
        return format(x, false);
    }
    
    default String format(final float x, final boolean trailingZeros) {
        if (MathExModified.isZero(x, 1.0E-7f)) {
            return trailingZeros ? "0.0000" : "0";
        }
        final float ax = Math.abs(x);
        if (ax >= 0.001f && ax < 1.0E7f) {
            return trailingZeros ? String.format("%.4f", x) : StringsModified.DECIMAL_FORMAT.format(x);
        }
        return String.format("%.4e", x);
    }
    
    default String format(final double x) {
        return format(x, false);
    }
    
    default String format(final double x, final boolean trailingZeros) {
        if (MathExModified.isZero(x, 1.0E-14)) {
            return trailingZeros ? "0.0000" : "0";
        }
        final double ax = Math.abs(x);
        if (ax >= 0.001 && ax < 1.0E7) {
            return trailingZeros ? String.format("%.4f", x) : StringsModified.DECIMAL_FORMAT.format(x);
        }
        return String.format("%.4e", x);
    }
    
    default int[] parseIntArray(String s) {
        if (isNullOrEmpty(s)) {
            return null;
        }
        s = s.trim();
        if (!s.startsWith("[") || !s.endsWith("]")) {
            throw new IllegalArgumentException("Invalid string: " + s);
        }
        final String[] tokens = s.substring(1, s.length() - 1).split(",");
        return Arrays.<String>stream(tokens).<Object>map((Function<? super String, ?>)String::trim).mapToInt((ToIntFunction<? super Object>)Integer::parseInt).toArray();
    }
    
    default double[] parseDoubleArray(String s) {
        if (isNullOrEmpty(s)) {
            return null;
        }
        s = s.trim();
        if (!s.startsWith("[") || !s.endsWith("]")) {
            throw new IllegalArgumentException("Invalid string: " + s);
        }
        final String[] tokens = s.substring(1, s.length() - 1).split(",");
        return Arrays.<String>stream(tokens).<Object>map((Function<? super String, ?>)String::trim).mapToDouble((ToDoubleFunction<? super Object>)Double::parseDouble).toArray();
    }
}
