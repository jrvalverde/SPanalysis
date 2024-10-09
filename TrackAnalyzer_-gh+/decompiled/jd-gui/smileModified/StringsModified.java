/*     */ package smileModified;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface StringsModified
/*     */ {
/*  30 */   public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.####");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isNullOrEmpty(String s) {
/*  38 */     return !(s != null && !s.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String unescape(String s) {
/*  47 */     StringBuilder sb = new StringBuilder(s.length());
/*     */     
/*  49 */     for (int i = 0; i < s.length(); i++) {
/*  50 */       char ch = s.charAt(i);
/*  51 */       if (ch == '\\')
/*  52 */       { char nextChar = (i == s.length() - 1) ? '\\' : s.charAt(i + 1);
/*     */         
/*  54 */         if (nextChar >= '0' && nextChar <= '7')
/*  55 */         { String code = String.valueOf(nextChar);
/*  56 */           i++;
/*  57 */           if (i < s.length() - 1 && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '7') {
/*  58 */             code = String.valueOf(code) + s.charAt(i + 1);
/*  59 */             i++;
/*  60 */             if (i < s.length() - 1 && s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '7') {
/*  61 */               code = String.valueOf(code) + s.charAt(i + 1);
/*  62 */               i++;
/*     */             } 
/*     */           } 
/*  65 */           sb.append((char)Integer.parseInt(code, 8)); }
/*     */         
/*     */         else
/*     */         
/*  69 */         { switch (nextChar)
/*     */           { case '\\':
/*  71 */               ch = '\\';
/*     */             
/*     */             case 'b':
/*  74 */               ch = '\b';
/*     */             
/*     */             case 'f':
/*  77 */               ch = '\f';
/*     */             
/*     */             case 'n':
/*  80 */               ch = '\n';
/*     */             
/*     */             case 'r':
/*  83 */               ch = '\r';
/*     */             
/*     */             case 't':
/*  86 */               ch = '\t';
/*     */             
/*     */             case '"':
/*  89 */               ch = '"';
/*     */             
/*     */             case '\'':
/*  92 */               ch = '\'';
/*     */ 
/*     */             
/*     */             case 'u':
/*  96 */               if (i >= s.length() - 5) {
/*  97 */                 ch = 'u';
/*     */               } else {
/*     */                 
/* 100 */                 int code = Integer.parseInt(s.substring(i + 2, i + 6), 16);
/* 101 */                 sb.append(Character.toChars(code));
/* 102 */                 i += 5; break;
/*     */               } 
/*     */             default:
/* 105 */               i++;
/*     */               
/* 107 */               sb.append(ch); break; }  }  continue; }  sb.append(ch); break;
/*     */     } 
/* 109 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String ordinal(int i) {
/* 118 */     String[] suffixes = { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
/* 119 */     switch (i % 100) {
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/* 123 */         return String.valueOf(i) + "th";
/*     */     } 
/* 125 */     return String.valueOf(i) + suffixes[i % 10];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String leftPad(String s, int size, char padChar) {
/* 139 */     if (s == null) {
/* 140 */       return null;
/*     */     }
/* 142 */     int pads = size - s.length();
/* 143 */     if (pads <= 0) {
/* 144 */       return s;
/*     */     }
/* 146 */     return fill(padChar, pads).concat(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String rightPad(String s, int size, char padChar) {
/* 159 */     if (s == null) {
/* 160 */       return null;
/*     */     }
/* 162 */     int pads = size - s.length();
/* 163 */     if (pads <= 0) {
/* 164 */       return s;
/*     */     }
/* 166 */     return s.concat(fill(padChar, pads));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String fill(char ch, int len) {
/* 176 */     char[] chars = new char[len];
/* 177 */     Arrays.fill(chars, ch);
/* 178 */     return new String(chars);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String format(float x) {
/* 187 */     return format(x, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String format(float x, boolean trailingZeros) {
/* 197 */     if (MathExModified.isZero(x, 1.0E-7F)) {
/* 198 */       return trailingZeros ? "0.0000" : "0";
/*     */     }
/*     */     
/* 201 */     float ax = Math.abs(x);
/* 202 */     if (ax >= 0.001F && ax < 1.0E7F) {
/* 203 */       return trailingZeros ? String.format("%.4f", new Object[] { Float.valueOf(x) }) : DECIMAL_FORMAT.format(x);
/*     */     }
/*     */     
/* 206 */     return String.format("%.4e", new Object[] { Float.valueOf(x) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String format(double x) {
/* 215 */     return format(x, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String format(double x, boolean trailingZeros) {
/* 225 */     if (MathExModified.isZero(x, 1.0E-14D)) {
/* 226 */       return trailingZeros ? "0.0000" : "0";
/*     */     }
/*     */     
/* 229 */     double ax = Math.abs(x);
/* 230 */     if (ax >= 0.001D && ax < 1.0E7D) {
/* 231 */       return trailingZeros ? String.format("%.4f", new Object[] { Double.valueOf(x) }) : DECIMAL_FORMAT.format(x);
/*     */     }
/*     */     
/* 234 */     return String.format("%.4e", new Object[] { Double.valueOf(x) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int[] parseIntArray(String s) {
/* 244 */     if (isNullOrEmpty(s)) return null;
/*     */     
/* 246 */     s = s.trim();
/* 247 */     if (!s.startsWith("[") || !s.endsWith("]")) {
/* 248 */       throw new IllegalArgumentException("Invalid string: " + s);
/*     */     }
/*     */     
/* 251 */     String[] tokens = s.substring(1, s.length() - 1).split(",");
/* 252 */     return Arrays.<String>stream(tokens).map(String::trim).mapToInt(Integer::parseInt).toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static double[] parseDoubleArray(String s) {
/* 262 */     if (isNullOrEmpty(s)) return null;
/*     */     
/* 264 */     s = s.trim();
/* 265 */     if (!s.startsWith("[") || !s.endsWith("]")) {
/* 266 */       throw new IllegalArgumentException("Invalid string: " + s);
/*     */     }
/*     */     
/* 269 */     String[] tokens = s.substring(1, s.length() - 1).split(",");
/* 270 */     return Arrays.<String>stream(tokens).map(String::trim).mapToDouble(Double::parseDouble).toArray();
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/StringsModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */