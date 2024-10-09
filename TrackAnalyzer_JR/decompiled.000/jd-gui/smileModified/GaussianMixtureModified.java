/*     */ package smileModified;
/*     */ 
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GaussianMixtureModified
/*     */   extends ExponentialFamilyMixtureModified
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*  12 */   private static final Logger logger = LoggerFactory.getLogger(GaussianMixtureModified.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GaussianMixtureModified(MixtureModified.Component... components) {
/*  19 */     this(0.0D, 1, components);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private GaussianMixtureModified(double L, int n, MixtureModified.Component... components) {
/*  29 */     super(L, n, components); byte b; int i;
/*     */     MixtureModified.Component[] arrayOfComponent;
/*  31 */     for (i = (arrayOfComponent = components).length, b = 0; b < i; ) { MixtureModified.Component component = arrayOfComponent[b];
/*  32 */       if (!(component.distribution instanceof GaussianDistributionModified)) {
/*  33 */         throw new IllegalArgumentException("Component " + component + " is not of Gaussian distribution.");
/*     */       }
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GaussianMixtureModified fit(int k, double[] x) {
/*  45 */     if (k < 2) {
/*  46 */       throw new IllegalArgumentException("Invalid number of components in the mixture.");
/*     */     }
/*  48 */     double min = MathExModified.min(x);
/*  49 */     double max = MathExModified.max(x);
/*  50 */     double step = (max - min) / (k + 1);
/*     */     
/*  52 */     MixtureModified.Component[] components = new MixtureModified.Component[k];
/*  53 */     for (int i = 0; i < k; i++) {
/*  54 */       components[i] = new MixtureModified.Component(1.0D / k, new GaussianDistributionModified(min += step, step));
/*     */     }
/*     */     
/*  57 */     ExponentialFamilyMixtureModified model = fit(x, components);
/*  58 */     return new GaussianMixtureModified(model.L, x.length, model.components);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GaussianMixtureModified fit(double[] x) {
/*  68 */     if (x.length < 20) {
/*  69 */       throw new IllegalArgumentException("Too few samples.");
/*     */     }
/*     */     
/*  72 */     GaussianMixtureModified mixture = new GaussianMixtureModified(new MixtureModified.Component[] { new MixtureModified.Component(1.0D, GaussianDistributionModified.fit(x)) });
/*  73 */     double bic = mixture.bic(x);
/*  74 */     logger.info(String.format("The BIC of %s = %.4f", new Object[] { mixture, Double.valueOf(bic) }));
/*     */     
/*  76 */     for (int k = 2; k < x.length / 10; k++) {
/*  77 */       ExponentialFamilyMixtureModified model = fit(k, x);
/*  78 */       logger.info(String.format("The BIC of %s = %.4f", new Object[] { model, Double.valueOf(model.bic) }));
/*     */       
/*  80 */       if (model.bic <= bic)
/*     */         break; 
/*  82 */       mixture = new GaussianMixtureModified(model.L, x.length, model.components);
/*  83 */       bic = model.bic;
/*     */     } 
/*     */     
/*  86 */     return mixture;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MixtureModified.Component[] split(MixtureModified.Component... components) {
/*  94 */     int k = components.length;
/*  95 */     int index = -1;
/*  96 */     double maxSigma = Double.NEGATIVE_INFINITY;
/*  97 */     for (int i = 0; i < k; i++) {
/*  98 */       MixtureModified.Component c = components[i];
/*  99 */       if (c.distribution.sd() > maxSigma) {
/* 100 */         maxSigma = c.distribution.sd();
/* 101 */         index = i;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 106 */     MixtureModified.Component component = components[index];
/* 107 */     double priori = component.priori / 2.0D;
/* 108 */     double delta = component.distribution.sd();
/* 109 */     double mu = component.distribution.mean();
/*     */     
/* 111 */     MixtureModified.Component[] mixture = new MixtureModified.Component[k + 1];
/* 112 */     System.arraycopy(components, 0, mixture, 0, k);
/* 113 */     mixture[index] = new MixtureModified.Component(priori, new GaussianDistributionModified(mu + delta / 2.0D, delta));
/* 114 */     mixture[k] = new MixtureModified.Component(priori, new GaussianDistributionModified(mu - delta / 2.0D, delta));
/* 115 */     return mixture;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/GaussianMixtureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */