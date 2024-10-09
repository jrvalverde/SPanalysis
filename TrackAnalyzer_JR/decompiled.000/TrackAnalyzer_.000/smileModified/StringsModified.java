package smileModified;

import java.text.DecimalFormat;
import java.util.Arrays;

public interface StringsModified {
   DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.####");

   static boolean isNullOrEmpty(String s) {
      return s == null || s.isEmpty();
   }

   static String unescape(String s) {
      StringBuilder sb = new StringBuilder(s.length());

      for(int i = 0; i < s.length(); ++i) {
         char ch = s.charAt(i);
         if (ch == '\\') {
            char nextChar = i == s.length() - 1 ? 92 : s.charAt(i + 1);
            if (nextChar >= '0' && nextChar <= '7') {
               String code = String.valueOf(nextChar);
               ++i;
               if (i < s.length() - 1 && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '7') {
                  code = code + s.charAt(i + 1);
                  ++i;
                  if (i < s.length() - 1 && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '7') {
                     code = code + s.charAt(i + 1);
                     ++i;
                  }
               }

               sb.append((char)Integer.parseInt(code, 8));
               continue;
            }

            switch(nextChar) {
            case '"':
               ch = '"';
               break;
            case '\'':
               ch = '\'';
               break;
            case '\\':
               ch = '\\';
               break;
            case 'b':
               ch = '\b';
               break;
            case 'f':
               ch = '\f';
               break;
            case 'n':
               ch = '\n';
               break;
            case 'r':
               ch = '\r';
               break;
            case 't':
               ch = '\t';
               break;
            case 'u':
               if (i < s.length() - 5) {
                  int code = Integer.parseInt(s.substring(i + 2, i + 6), 16);
                  sb.append(Character.toChars(code));
                  i += 5;
                  continue;
               }

               ch = 'u';
            }

            ++i;
         }

         sb.append(ch);
      }

      return sb.toString();
   }

   static String ordinal(int i) {
      String[] suffixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
      switch(i % 100) {
      case 11:
      case 12:
      case 13:
         return i + "th";
      default:
         return i + suffixes[i % 10];
      }
   }

   static String leftPad(String s, int size, char padChar) {
      if (s == null) {
         return null;
      } else {
         int pads = size - s.length();
         return pads <= 0 ? s : fill(padChar, pads).concat(s);
      }
   }

   static String rightPad(String s, int size, char padChar) {
      if (s == null) {
         return null;
      } else {
         int pads = size - s.length();
         return pads <= 0 ? s : s.concat(fill(padChar, pads));
      }
   }

   static String fill(char ch, int len) {
      char[] chars = new char[len];
      Arrays.fill(chars, ch);
      return new String(chars);
   }

   static String format(float x) {
      return format(x, false);
   }

   static String format(float x, boolean trailingZeros) {
      if (MathExModified.isZero(x, 1.0E-7F)) {
         return trailingZeros ? "0.0000" : "0";
      } else {
         float ax = Math.abs(x);
         if (ax >= 0.001F && ax < 1.0E7F) {
            return trailingZeros ? String.format("%.4f", x) : DECIMAL_FORMAT.format((double)x);
         } else {
            return String.format("%.4e", x);
         }
      }
   }

   static String format(double x) {
      return format(x, false);
   }

   static String format(double x, boolean trailingZeros) {
      if (MathExModified.isZero(x, 1.0E-14D)) {
         return trailingZeros ? "0.0000" : "0";
      } else {
         double ax = Math.abs(x);
         if (ax >= 0.001D && ax < 1.0E7D) {
            return trailingZeros ? String.format("%.4f", x) : DECIMAL_FORMAT.format(x);
         } else {
            return String.format("%.4e", x);
         }
      }
   }

   static int[] parseIntArray(String s) {
      if (isNullOrEmpty(s)) {
         return null;
      } else {
         s = s.trim();
         if (s.startsWith("[") && s.endsWith("]")) {
            String[] tokens = s.substring(1, s.length() - 1).split(",");
            return Arrays.stream(tokens).map(String::trim).mapToInt(Integer::parseInt).toArray();
         } else {
            throw new IllegalArgumentException("Invalid string: " + s);
         }
      }
   }

   static double[] parseDoubleArray(String s) {
      if (isNullOrEmpty(s)) {
         return null;
      } else {
         s = s.trim();
         if (s.startsWith("[") && s.endsWith("]")) {
            String[] tokens = s.substring(1, s.length() - 1).split(",");
            return Arrays.stream(tokens).map(String::trim).mapToDouble(Double::parseDouble).toArray();
         } else {
            throw new IllegalArgumentException("Invalid string: " + s);
         }
      }
   }
}
