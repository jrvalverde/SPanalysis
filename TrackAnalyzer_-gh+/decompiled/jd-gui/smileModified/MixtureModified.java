/*     */ package smileModified;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.stream.Collectors;
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
/*     */ public class MixtureModified
/*     */   extends AbstractDistributionModified
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*     */   public final Component[] components;
/*     */   
/*     */   public static class Component
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 2L;
/*     */     public final double priori;
/*     */     public final DistributionModified distribution;
/*     */     
/*     */     public Component(double priori, DistributionModified distribution) {
/*  49 */       this.priori = priori;
/*  50 */       this.distribution = distribution;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixtureModified(Component... components) {
/*  62 */     if (components.length == 0) {
/*  63 */       throw new IllegalStateException("Empty mixture!");
/*     */     }
/*     */     
/*  66 */     double sum = 0.0D; byte b; int i; Component[] arrayOfComponent;
/*  67 */     for (i = (arrayOfComponent = components).length, b = 0; b < i; ) { Component component = arrayOfComponent[b];
/*  68 */       sum += component.priori;
/*     */       b++; }
/*     */     
/*  71 */     if (Math.abs(sum - 1.0D) > 0.001D) {
/*  72 */       throw new IllegalArgumentException("The sum of priori is not equal to 1.");
/*     */     }
/*     */     
/*  75 */     this.components = components;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] posteriori(double x) {
/*  84 */     int k = this.components.length;
/*  85 */     double[] prob = new double[k];
/*  86 */     for (int i = 0; i < k; i++) {
/*  87 */       Component c = this.components[i];
/*  88 */       prob[i] = c.priori * c.distribution.p(x);
/*     */     } 
/*     */     
/*  91 */     double p = MathExModified.sum(prob);
/*  92 */     for (int j = 0; j < k; j++) {
/*  93 */       prob[j] = prob[j] / p;
/*     */     }
/*  95 */     return prob;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int map(double x) {
/* 104 */     int k = this.components.length;
/* 105 */     double[] prob = new double[k];
/* 106 */     for (int i = 0; i < k; i++) {
/* 107 */       Component c = this.components[i];
/* 108 */       prob[i] = c.priori * c.distribution.p(x);
/*     */     } 
/*     */     
/* 111 */     return MathExModified.whichMax(prob);
/*     */   }
/*     */ 
/*     */   
/*     */   public double mean() {
/* 116 */     double mu = 0.0D; byte b; int i;
/*     */     Component[] arrayOfComponent;
/* 118 */     for (i = (arrayOfComponent = this.components).length, b = 0; b < i; ) { Component c = arrayOfComponent[b];
/* 119 */       mu += c.priori * c.distribution.mean(); b++; }
/*     */     
/* 121 */     return mu;
/*     */   }
/*     */ 
/*     */   
/*     */   public double variance() {
/* 126 */     double variance = 0.0D; byte b; int i;
/*     */     Component[] arrayOfComponent;
/* 128 */     for (i = (arrayOfComponent = this.components).length, b = 0; b < i; ) { Component c = arrayOfComponent[b];
/* 129 */       variance += c.priori * c.priori * c.distribution.variance(); b++; }
/*     */     
/* 131 */     return variance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double entropy() {
/* 139 */     throw new UnsupportedOperationException("Mixture does not support entropy()");
/*     */   }
/*     */ 
/*     */   
/*     */   public double p(double x) {
/* 144 */     double p = 0.0D; byte b; int i;
/*     */     Component[] arrayOfComponent;
/* 146 */     for (i = (arrayOfComponent = this.components).length, b = 0; b < i; ) { Component c = arrayOfComponent[b];
/* 147 */       p += c.priori * c.distribution.p(x); b++; }
/*     */     
/* 149 */     return p;
/*     */   }
/*     */ 
/*     */   
/*     */   public double logp(double x) {
/* 154 */     return Math.log(p(x));
/*     */   }
/*     */ 
/*     */   
/*     */   public double cdf(double x) {
/* 159 */     double p = 0.0D; byte b; int i;
/*     */     Component[] arrayOfComponent;
/* 161 */     for (i = (arrayOfComponent = this.components).length, b = 0; b < i; ) { Component c = arrayOfComponent[b];
/* 162 */       p += c.priori * c.distribution.cdf(x); b++; }
/*     */     
/* 164 */     return p;
/*     */   }
/*     */ 
/*     */   
/*     */   public double rand() {
/* 169 */     double r = MathExModified.random();
/*     */     
/* 171 */     double p = 0.0D; byte b; int i; Component[] arrayOfComponent;
/* 172 */     for (i = (arrayOfComponent = this.components).length, b = 0; b < i; ) { Component g = arrayOfComponent[b];
/* 173 */       p += g.priori;
/* 174 */       if (r <= p) {
/* 175 */         return g.distribution.rand();
/*     */       }
/*     */       b++; }
/*     */     
/* 179 */     throw new IllegalStateException();
/*     */   }
/*     */   
/*     */   public double quantile(double p) {
/*     */     double xl, xu;
/* 184 */     if (p < 0.0D || p > 1.0D) {
/* 185 */       throw new IllegalArgumentException("Invalid p: " + p);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 190 */     double inc = 1.0D;
/* 191 */     double x = (int)mean();
/* 192 */     if (p < cdf(x)) {
/*     */       do {
/* 194 */         x -= inc;
/* 195 */         inc *= 2.0D;
/* 196 */       } while (p < cdf(x));
/* 197 */       xl = x;
/* 198 */       xu = x + inc / 2.0D;
/*     */     } else {
/*     */       do {
/* 201 */         x += inc;
/* 202 */         inc *= 2.0D;
/* 203 */       } while (p > cdf(x));
/* 204 */       xu = x;
/* 205 */       xl = x - inc / 2.0D;
/*     */     } 
/*     */     
/* 208 */     return quantile(p, xl, xu);
/*     */   }
/*     */ 
/*     */   
/*     */   public int length() {
/* 213 */     int length = this.components.length - 1; byte b; int i; Component[] arrayOfComponent;
/* 214 */     for (i = (arrayOfComponent = this.components).length, b = 0; b < i; ) { Component component = arrayOfComponent[b];
/* 215 */       length += component.distribution.length(); b++; }
/*     */     
/* 217 */     return length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 225 */     return this.components.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double bic(double[] data) {
/* 234 */     int n = data.length;
/*     */     
/* 236 */     double logLikelihood = 0.0D; byte b; int i; double[] arrayOfDouble;
/* 237 */     for (i = (arrayOfDouble = data).length, b = 0; b < i; ) { double x = arrayOfDouble[b];
/* 238 */       double p = p(x);
/* 239 */       if (p > 0.0D) logLikelihood += Math.log(p); 
/*     */       b++; }
/*     */     
/* 242 */     return logLikelihood - 0.5D * length() * Math.log(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 247 */     return Arrays.<Component>stream(this.components)
/* 248 */       .map(component -> String.format("%.2f x %s", new Object[] { Double.valueOf(component.priori), component.distribution
/* 249 */           })).collect(Collectors.joining(" + ", String.format("Mixture(%d)[", new Object[] { Integer.valueOf(this.components.length) }), "]"));
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/MixtureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */