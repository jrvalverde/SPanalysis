/*     */ package smileModified;
/*     */ 
/*     */ public class GaussianDistributionModified
/*     */   extends AbstractDistributionModified implements ExponentialFamilyModified {
/*     */   private static final long serialVersionUID = 2L;
/*   6 */   private static final double LOG2PIE_2 = Math.log(17.079468445347132D) / 2.0D;
/*   7 */   private static final double LOG2PI_2 = Math.log(6.283185307179586D) / 2.0D;
/*   8 */   private static final GaussianDistributionModified singleton = new GaussianDistributionModified(0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double mu;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double sigma;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double variance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double entropy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double pdfConstant;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double z1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GaussianDistributionModified fit(double[] data) {
/*     */     double mu = MathExModified.mean(data);
/*     */     double sigma = MathExModified.sd(data);
/*     */     return new GaussianDistributionModified(mu, sigma);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GaussianDistributionModified getInstance() {
/*     */     return singleton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int length() {
/*     */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double mean() {
/*     */     return this.mu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GaussianDistributionModified(double mu, double sigma) {
/*  88 */     this.z1 = Double.NaN;
/*     */     this.mu = mu;
/*     */     this.sigma = sigma;
/*     */     this.variance = sigma * sigma;
/*     */     this.entropy = Math.log(sigma) + LOG2PIE_2;
/*     */     this.pdfConstant = Math.log(sigma) + LOG2PI_2; } public double variance() {
/*     */     return this.variance;
/*     */   } public double rand() {
/*     */     double z0;
/*  97 */     if (Double.isNaN(this.z1)) {
/*     */       double x, y, r; do {
/*  99 */         x = MathExModified.random(-1.0D, 1.0D);
/* 100 */         y = MathExModified.random(-1.0D, 1.0D);
/* 101 */         r = x * x + y * y;
/* 102 */       } while (r >= 1.0D);
/*     */       
/* 104 */       double z = Math.sqrt(-2.0D * Math.log(r) / r);
/* 105 */       this.z1 = x * z;
/* 106 */       z0 = y * z;
/*     */     } else {
/* 108 */       z0 = this.z1;
/* 109 */       this.z1 = Double.NaN;
/*     */     } 
/*     */     
/* 112 */     return this.mu + this.sigma * z0;
/*     */   }
/*     */   
/*     */   public double sd() {
/*     */     return this.sigma;
/*     */   }
/*     */   
/*     */   public double inverseCDF() {
/* 120 */     double x, a0 = 2.50662823884D;
/* 121 */     double a1 = -18.61500062529D;
/* 122 */     double a2 = 41.39119773534D;
/* 123 */     double a3 = -25.44106049637D;
/* 124 */     double b0 = -8.4735109309D;
/* 125 */     double b1 = 23.08336743743D;
/* 126 */     double b2 = -21.06224101826D;
/* 127 */     double b3 = 3.13082909833D;
/* 128 */     double c0 = 0.3374754822726147D;
/* 129 */     double c1 = 0.9761690190917186D;
/* 130 */     double c2 = 0.1607979714918209D;
/* 131 */     double c3 = 0.0276438810333863D;
/* 132 */     double c4 = 0.0038405729373609D;
/* 133 */     double c5 = 3.951896511919E-4D;
/* 134 */     double c6 = 3.21767881768E-5D;
/* 135 */     double c7 = 2.888167364E-7D;
/* 136 */     double c8 = 3.960315187E-7D;
/*     */ 
/*     */ 
/*     */     
/* 140 */     double u = MathExModified.random();
/* 141 */     while (u == 0.0D) {
/* 142 */       u = MathExModified.random();
/*     */     }
/*     */     
/* 145 */     double y = u - 0.5D;
/*     */     
/* 147 */     if (Math.abs(y) < 0.42D) {
/* 148 */       double r = y * y;
/* 149 */       x = y * (((-25.44106049637D * r + 41.39119773534D) * r + -18.61500062529D) * r + 2.50662823884D) / ((((
/* 150 */         3.13082909833D * r + -21.06224101826D) * r + 23.08336743743D) * r + -8.4735109309D) * r + 1.0D);
/*     */     } else {
/*     */       
/* 153 */       double r = u;
/* 154 */       if (y > 0.0D) {
/* 155 */         r = 1.0D - u;
/*     */       }
/* 157 */       r = Math.log(-Math.log(r));
/* 158 */       x = 0.3374754822726147D + r * (0.9761690190917186D + r * (0.1607979714918209D + r * (0.0276438810333863D + r * (0.0038405729373609D + r * (3.951896511919E-4D + r * (3.21767881768E-5D + r * (2.888167364E-7D + r * 3.960315187E-7D)))))));
/* 159 */       if (y < 0.0D) {
/* 160 */         x = -x;
/*     */       }
/*     */     } 
/*     */     
/* 164 */     return this.mu + this.sigma * x;
/*     */   } public double entropy() {
/*     */     return this.entropy;
/*     */   }
/*     */   public double p(double x) {
/* 169 */     if (this.sigma == 0.0D) {
/* 170 */       if (x == this.mu) {
/* 171 */         return 1.0D;
/*     */       }
/* 173 */       return 0.0D;
/*     */     } 
/*     */ 
/*     */     
/* 177 */     return Math.exp(logp(x));
/*     */   } public String toString() {
/*     */     return String.format("Gaussian Distribution(%.4f, %.4f)", new Object[] { Double.valueOf(this.mu), Double.valueOf(this.sigma) });
/*     */   }
/*     */   public double logp(double x) {
/* 182 */     if (this.sigma == 0.0D) {
/* 183 */       if (x == this.mu) {
/* 184 */         return 0.0D;
/*     */       }
/* 186 */       return Double.NEGATIVE_INFINITY;
/*     */     } 
/*     */ 
/*     */     
/* 190 */     double d = x - this.mu;
/* 191 */     return -0.5D * d * d / this.variance - this.pdfConstant;
/*     */   }
/*     */ 
/*     */   
/*     */   public double cdf(double x) {
/* 196 */     if (this.sigma == 0.0D) {
/* 197 */       if (x < this.mu) {
/* 198 */         return 0.0D;
/*     */       }
/* 200 */       return 1.0D;
/*     */     } 
/*     */ 
/*     */     
/* 204 */     return 0.5D * ErfModified.erfc(-0.7071067811865476D * (x - this.mu) / this.sigma);
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
/*     */   public double quantile(double p) {
/* 216 */     if (p < 0.0D || p > 1.0D) {
/* 217 */       throw new IllegalArgumentException("Invalid p: " + p);
/*     */     }
/*     */     
/* 220 */     if (this.sigma == 0.0D) {
/* 221 */       if (p < 1.0D) {
/* 222 */         return this.mu - 1.0E-10D;
/*     */       }
/* 224 */       return this.mu;
/*     */     } 
/*     */     
/* 227 */     return -1.4142135623730951D * this.sigma * ErfModified.inverfc(2.0D * p) + this.mu;
/*     */   }
/*     */ 
/*     */   
/*     */   public MixtureModified.Component M(double[] x, double[] posteriori) {
/* 232 */     double alpha = 0.0D;
/* 233 */     double mean = 0.0D;
/* 234 */     double sd = 0.0D;
/*     */     int i;
/* 236 */     for (i = 0; i < x.length; i++) {
/* 237 */       alpha += posteriori[i];
/* 238 */       mean += x[i] * posteriori[i];
/*     */     } 
/*     */     
/* 241 */     mean /= alpha;
/*     */     
/* 243 */     for (i = 0; i < x.length; i++) {
/* 244 */       double d = x[i] - mean;
/* 245 */       sd += d * d * posteriori[i];
/*     */     } 
/*     */     
/* 248 */     sd = Math.sqrt(sd / alpha);
/*     */     
/* 250 */     return new MixtureModified.Component(alpha, new GaussianDistributionModified(mean, sd));
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/GaussianDistributionModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */