/*    */ package vecmath;
/*    */ 
/*    */ import java.util.MissingResourceException;
/*    */ import java.util.ResourceBundle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class VecMathI18N
/*    */ {
/*    */   static String getString(String key) {
/*    */     String s;
/*    */     try {
/* 37 */       s = ResourceBundle.getBundle("org.scijava.vecmath.ExceptionStrings").getString(key);
/*    */     }
/* 39 */     catch (MissingResourceException e) {
/* 40 */       System.err.println("VecMathI18N: Error looking up: " + key);
/* 41 */       s = key;
/*    */     } 
/* 43 */     return s;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/vecmath/VecMathI18N.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */