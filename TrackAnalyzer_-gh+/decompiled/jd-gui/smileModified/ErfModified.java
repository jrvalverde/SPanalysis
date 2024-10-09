/*     */ package smileModified;
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
/*     */ public class ErfModified
/*     */ {
/*  42 */   private static final double[] cof = new double[] { 
/*  43 */       -1.3026537197817094D, 0.6419697923564902D, 
/*  44 */       0.019476473204185836D, -0.00956151478680863D, -9.46595344482036E-4D, 
/*  45 */       3.66839497852761E-4D, 4.2523324806907E-5D, -2.0278578112534E-5D, 
/*  46 */       -1.624290004647E-6D, 1.30365583558E-6D, 1.5626441722E-8D, -8.5238095915E-8D, 
/*  47 */       6.529054439E-9D, 5.059343495E-9D, -9.91364156E-10D, -2.27365122E-10D, 
/*  48 */       9.6467911E-11D, 2.394038E-12D, -6.886027E-12D, 8.94487E-13D, 3.13092E-13D, 
/*  49 */       -1.12708E-13D, 3.81E-16D, 7.106E-15D, -1.523E-15D, -9.4E-17D, 1.21E-16D, -2.8E-17D };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double erf(double x) {
/*  58 */     if (x >= 0.0D) {
/*  59 */       return 1.0D - erfccheb(x);
/*     */     }
/*  61 */     return erfccheb(-x) - 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double erfc(double x) {
/*  71 */     if (x >= 0.0D) {
/*  72 */       return erfccheb(x);
/*     */     }
/*  74 */     return 2.0D - erfccheb(-x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double erfcc(double x) {
/*  85 */     double z = Math.abs(x);
/*  86 */     double t = 2.0D / (2.0D + z);
/*  87 */     double ans = t * Math.exp(-z * z - 1.26551223D + t * (1.00002368D + t * (0.37409196D + t * (0.09678418D + 
/*  88 */         t * (-0.18628806D + t * (0.27886807D + t * (-1.13520398D + t * (1.48851587D + 
/*  89 */         t * (-0.82215223D + t * 0.17087277D)))))))));
/*     */     
/*  91 */     return (x >= 0.0D) ? ans : (2.0D - ans);
/*     */   }
/*     */   
/*     */   private static double erfccheb(double z) {
/*  95 */     double d = 0.0D, dd = 0.0D;
/*  96 */     if (z < 0.0D) {
/*  97 */       throw new IllegalArgumentException("erfccheb requires non-negative argument");
/*     */     }
/*  99 */     double t = 2.0D / (2.0D + z);
/* 100 */     double ty = 4.0D * t - 2.0D;
/* 101 */     for (int j = cof.length - 1; j > 0; j--) {
/* 102 */       double tmp = d;
/* 103 */       d = ty * d - dd + cof[j];
/* 104 */       dd = tmp;
/*     */     } 
/* 106 */     return t * Math.exp(-z * z + 0.5D * (cof[0] + ty * d) - dd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double inverfc(double p) {
/* 116 */     if (p >= 2.0D) {
/* 117 */       return -100.0D;
/*     */     }
/* 119 */     if (p <= 0.0D) {
/* 120 */       return 100.0D;
/*     */     }
/* 122 */     double pp = (p < 1.0D) ? p : (2.0D - p);
/* 123 */     double t = Math.sqrt(-2.0D * Math.log(pp / 2.0D));
/* 124 */     double x = -0.70711D * ((2.30753D + t * 0.27061D) / (1.0D + t * (0.99229D + t * 0.04481D)) - t);
/* 125 */     for (int j = 0; j < 2; j++) {
/* 126 */       double err = erfc(x) - pp;
/* 127 */       x += err / (1.1283791670955126D * Math.exp(-x * x) - x * err);
/*     */     } 
/* 129 */     return (p < 1.0D) ? x : -x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double inverf(double p) {
/* 138 */     return inverfc(1.0D - p);
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/ErfModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */