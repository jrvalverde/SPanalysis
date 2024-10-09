/*     */ package smileModified;
/*     */ 
/*     */ public class ExponentialFamilyMixtureModified extends MixtureModified {
/*   4 */   private static final Logger logger = LoggerFactory.getLogger(ExponentialFamilyMixtureModified.class);
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 2L;
/*     */ 
/*     */   
/*     */   public final double L;
/*     */   
/*     */   public final double bic;
/*     */ 
/*     */   
/*     */   public ExponentialFamilyMixtureModified(MixtureModified.Component... components) {
/*  16 */     this(0.0D, 1, components);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ExponentialFamilyMixtureModified(double L, int n, MixtureModified.Component... components) {
/*  26 */     super(components); byte b; int i;
/*     */     MixtureModified.Component[] arrayOfComponent;
/*  28 */     for (i = (arrayOfComponent = components).length, b = 0; b < i; ) { MixtureModified.Component component = arrayOfComponent[b];
/*  29 */       if (!(component.distribution instanceof ExponentialFamilyModified)) {
/*  30 */         throw new IllegalArgumentException("Component " + component + " is not of exponential family.");
/*     */       }
/*     */       b++; }
/*     */     
/*  34 */     this.L = L;
/*  35 */     this.bic = L - 0.5D * length() * Math.log(n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ExponentialFamilyMixtureModified fit(double[] x, MixtureModified.Component... components) {
/*  46 */     return fit(x, components, 0.0D, 500, 1.0E-4D);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static ExponentialFamilyMixtureModified fit(double[] x, MixtureModified.Component[] components, double gamma, int maxIter, double tol) {
/*  63 */     if (x.length < components.length / 2) {
/*  64 */       throw new IllegalArgumentException("Too many components");
/*     */     }
/*     */     
/*  67 */     if (gamma < 0.0D || gamma > 0.2D) {
/*  68 */       throw new IllegalArgumentException("Invalid regularization factor gamma.");
/*     */     }
/*     */     
/*  71 */     int n = x.length;
/*  72 */     int k = components.length;
/*     */     
/*  74 */     double[][] posteriori = new double[k][n];
/*     */ 
/*     */     
/*  77 */     double L = 0.0D;
/*     */ 
/*     */     
/*  80 */     double diff = Double.MAX_VALUE;
/*  81 */     for (int iter = 1; iter <= maxIter && diff > tol; iter++) {
/*     */       
/*  83 */       for (int i = 0; i < k; i++) {
/*  84 */         MixtureModified.Component c = components[i];
/*     */         
/*  86 */         for (int i2 = 0; i2 < n; i2++) {
/*  87 */           posteriori[i][i2] = c.priori * c.distribution.p(x[i2]);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  92 */       for (int j = 0; j < n; j++) {
/*  93 */         double p = 0.0D;
/*     */         int i2;
/*  95 */         for (i2 = 0; i2 < k; i2++) {
/*  96 */           p += posteriori[i2][j];
/*     */         }
/*     */         
/*  99 */         for (i2 = 0; i2 < k; i2++) {
/* 100 */           posteriori[i2][j] = posteriori[i2][j] / p;
/*     */         }
/*     */ 
/*     */         
/* 104 */         if (gamma > 0.0D) {
/* 105 */           for (i2 = 0; i2 < k; i2++) {
/* 106 */             posteriori[i2][j] = posteriori[i2][j] * (1.0D + gamma * MathExModified.log2(posteriori[i2][j]));
/* 107 */             if (Double.isNaN(posteriori[i2][j]) || posteriori[i2][j] < 0.0D) {
/* 108 */               posteriori[i2][j] = 0.0D;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 115 */       double Z = 0.0D; int m;
/* 116 */       for (m = 0; m < k; m++) {
/* 117 */         components[m] = ((ExponentialFamilyModified)(components[m]).distribution).M(x, posteriori[m]);
/* 118 */         Z += (components[m]).priori;
/*     */       } 
/*     */       
/* 121 */       for (m = 0; m < k; m++) {
/* 122 */         components[m] = new MixtureModified.Component((components[m]).priori / Z, (components[m]).distribution);
/*     */       }
/*     */       
/* 125 */       double loglikelihood = 0.0D; byte b; int i1; double[] arrayOfDouble;
/* 126 */       for (i1 = (arrayOfDouble = x).length, b = 0; b < i1; ) { double xi = arrayOfDouble[b];
/* 127 */         double p = 0.0D; byte b1; int i2; MixtureModified.Component[] arrayOfComponent;
/* 128 */         for (i2 = (arrayOfComponent = components).length, b1 = 0; b1 < i2; ) { MixtureModified.Component c = arrayOfComponent[b1];
/* 129 */           p += c.priori * c.distribution.p(xi); b1++; }
/*     */         
/* 131 */         if (p > 0.0D) loglikelihood += Math.log(p); 
/*     */         b++; }
/*     */       
/* 134 */       diff = loglikelihood - L;
/* 135 */       L = loglikelihood;
/*     */       
/* 137 */       if (iter % 10 == 0) {
/* 138 */         logger.info(String.format("The log-likelihood after %d iterations: %.4f", new Object[] { Integer.valueOf(iter), Double.valueOf(L) }));
/*     */       }
/*     */     } 
/*     */     
/* 142 */     return new ExponentialFamilyMixtureModified(L, x.length, components);
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/ExponentialFamilyMixtureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */