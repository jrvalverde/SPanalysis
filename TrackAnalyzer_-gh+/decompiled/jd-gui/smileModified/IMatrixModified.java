/*     */ package smileModified;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.nio.file.Path;
/*     */ import java.text.ParseException;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class IMatrixModified
/*     */   implements Cloneable, Serializable
/*     */ {
/*  17 */   private static final Logger logger = LoggerFactory.getLogger(IMatrixModified.class);
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
/*     */   private String[] rowNames;
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
/*     */   private String[] colNames;
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
/*     */   public String[] rowNames() {
/*  53 */     return this.rowNames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rowNames(String[] names) {
/*  61 */     if (names != null && names.length != nrow()) {
/*  62 */       throw new IllegalArgumentException(String.format("Invalid row names length: %d != %d", new Object[] { Integer.valueOf(names.length), Integer.valueOf(nrow()) }));
/*     */     }
/*  64 */     this.rowNames = names;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String rowName(int i) {
/*  73 */     return this.rowNames[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] colNames() {
/*  81 */     return this.colNames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void colNames(String[] names) {
/*  89 */     if (names != null && names.length != ncol()) {
/*  90 */       throw new IllegalArgumentException(String.format("Invalid column names length: %d != %d", new Object[] { Integer.valueOf(names.length), Integer.valueOf(ncol()) }));
/*     */     }
/*  92 */     this.colNames = names;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String colName(int i) {
/* 101 */     return this.colNames[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 106 */     return toString(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(boolean full) {
/* 116 */     return full ? toString(nrow(), ncol()) : toString(7, 7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(int m, int n) {
/* 126 */     StringBuilder sb = new StringBuilder(String.valueOf(nrow()) + " x " + ncol() + "\n");
/* 127 */     m = Math.min(m, nrow());
/* 128 */     n = Math.min(n, ncol());
/*     */     
/* 130 */     String newline = (n < ncol()) ? "  ...\n" : "\n";
/*     */     
/* 132 */     if (this.colNames != null) {
/* 133 */       if (this.rowNames != null) sb.append("            ");
/*     */       
/* 135 */       for (int j = 0; j < n; j++) {
/* 136 */         sb.append(String.format(" %12.12s", new Object[] { this.colNames[j] }));
/*     */       } 
/* 138 */       sb.append(newline);
/*     */     } 
/*     */     
/* 141 */     for (int i = 0; i < m; i++) {
/* 142 */       if (this.rowNames != null) sb.append(String.format("%-12.12s", new Object[] { this.rowNames[i] }));
/*     */       
/* 144 */       for (int j = 0; j < n; j++) {
/* 145 */         sb.append(String.format(" %12.12s", new Object[] { str(i, j) }));
/*     */       } 
/* 147 */       sb.append(newline);
/*     */     } 
/*     */     
/* 150 */     if (m < nrow()) {
/* 151 */       sb.append("  ...\n");
/*     */     }
/*     */     
/* 154 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String str(int i, int j) {
/* 164 */     return StringsModified.format(get(i, j), true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] mv(double[] x) {
/* 190 */     double[] y = new double[nrow()];
/* 191 */     mv(TransposeModified.NO_TRANSPOSE, 1.0D, x, 0.0D, y);
/* 192 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mv(double[] x, double[] y) {
/* 201 */     mv(TransposeModified.NO_TRANSPOSE, 1.0D, x, 0.0D, y);
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
/*     */   public void mv(double alpha, double[] x, double beta, double[] y) {
/* 217 */     mv(TransposeModified.NO_TRANSPOSE, alpha, x, beta, y);
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
/*     */   public double[] tv(double[] x) {
/* 234 */     double[] y = new double[ncol()];
/* 235 */     mv(TransposeModified.TRANSPOSE, 1.0D, x, 0.0D, y);
/* 236 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tv(double[] x, double[] y) {
/* 245 */     mv(TransposeModified.TRANSPOSE, 1.0D, x, 0.0D, y);
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
/*     */   public void tv(double alpha, double[] x, double beta, double[] y) {
/* 261 */     mv(TransposeModified.TRANSPOSE, alpha, x, beta, y);
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
/*     */   static int ld(int n) {
/* 297 */     int elementSize = 4;
/* 298 */     if (n <= 256 / elementSize) return n;
/*     */     
/* 300 */     return ((n * elementSize + 511) / 512 * 512 + 64) / elementSize;
/*     */   }
/*     */ 
/*     */   
/*     */   static TransposeModified flip(TransposeModified trans) {
/* 305 */     return (trans == TransposeModified.NO_TRANSPOSE) ? TransposeModified.TRANSPOSE : TransposeModified.NO_TRANSPOSE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int i, int j, double x) {
/* 315 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(int i, int j, double x) {
/* 325 */     set(i, j, x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int i, int j) {
/* 335 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double apply(int i, int j) {
/* 345 */     return get(i, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] diag() {
/* 353 */     int n = Math.min(nrow(), ncol());
/*     */     
/* 355 */     double[] d = new double[n];
/* 356 */     for (int i = 0; i < n; i++) {
/* 357 */       d[i] = get(i, i);
/*     */     }
/*     */     
/* 360 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double trace() {
/* 368 */     int n = Math.min(nrow(), ncol());
/*     */     
/* 370 */     double t = 0.0D;
/* 371 */     for (int i = 0; i < n; i++) {
/* 372 */       t += get(i, i);
/*     */     }
/*     */     
/* 375 */     return t;
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
/*     */   public double eigen(double[] v) {
/* 389 */     return eigen(v, 0.0D, Math.max(1.0E-6D, nrow() * MathExModified.EPSILON), Math.max(20, 2 * nrow()));
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
/*     */   public double eigen(double[] v, double p, double tol, int maxIter) {
/* 416 */     if (nrow() != ncol()) {
/* 417 */       throw new IllegalArgumentException("Matrix is not square.");
/*     */     }
/*     */     
/* 420 */     if (tol <= 0.0D) {
/* 421 */       throw new IllegalArgumentException("Invalid tolerance: " + tol);
/*     */     }
/*     */     
/* 424 */     if (maxIter <= 0) {
/* 425 */       throw new IllegalArgumentException("Invalid maximum number of iterations: " + maxIter);
/*     */     }
/*     */     
/* 428 */     int n = nrow();
/* 429 */     tol = Math.max(tol, MathExModified.EPSILON * n);
/*     */     
/* 431 */     double[] z = new double[n];
/* 432 */     double lambda = power(v, z, p);
/*     */     
/* 434 */     for (int iter = 1; iter <= maxIter; iter++) {
/* 435 */       double l = lambda;
/* 436 */       lambda = power(v, z, p);
/*     */       
/* 438 */       double eps = Math.abs(lambda - l);
/* 439 */       if (iter % 10 == 0) {
/* 440 */         logger.trace(String.format("Largest eigenvalue after %3d power iterations: %.4f", new Object[] { Integer.valueOf(iter), Double.valueOf(lambda + p) }));
/*     */       }
/*     */       
/* 443 */       if (eps < tol) {
/* 444 */         logger.info(String.format("Largest eigenvalue after %3d power iterations: %.4f", new Object[] { Integer.valueOf(iter), Double.valueOf(lambda + p) }));
/* 445 */         return lambda + p;
/*     */       } 
/*     */     } 
/*     */     
/* 449 */     logger.info(String.format("Largest eigenvalue after %3d power iterations: %.4f", new Object[] { Integer.valueOf(maxIter), Double.valueOf(lambda + p) }));
/* 450 */     logger.error("Power iteration exceeded the maximum number of iterations.");
/* 451 */     return lambda + p;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double power(double[] x, double[] y, double p) {
/* 459 */     mv(x, y);
/*     */     
/* 461 */     if (p != 0.0D) {
/* 462 */       for (int j = 0; j < y.length; j++) {
/* 463 */         y[j] = y[j] - p * x[j];
/*     */       }
/*     */     }
/*     */     
/* 467 */     double lambda = y[0]; int i;
/* 468 */     for (i = 1; i < y.length; i++) {
/* 469 */       if (Math.abs(y[i]) > Math.abs(lambda)) {
/* 470 */         lambda = y[i];
/*     */       }
/*     */     } 
/*     */     
/* 474 */     for (i = 0; i < y.length; i++) {
/* 475 */       x[i] = y[i] / lambda;
/*     */     }
/*     */     
/* 478 */     return lambda;
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
/*     */   public static IMatrixModified market(Path path) throws IOException, ParseException {
/* 494 */     Exception exception1 = null, exception2 = null;
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
/*     */     try {
/*     */     
/*     */     } finally {
/* 660 */       exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Preconditioner
/*     */   {
/*     */     void asolve(double[] param1ArrayOfdouble1, double[] param1ArrayOfdouble2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class Square
/*     */     extends IMatrixModified
/*     */   {
/*     */     private final IMatrixModified A;
/*     */ 
/*     */     
/*     */     private final int m;
/*     */ 
/*     */     
/*     */     private final int n;
/*     */ 
/*     */     
/*     */     private final double[] Ax;
/*     */ 
/*     */ 
/*     */     
/*     */     public Square(IMatrixModified A) {
/* 691 */       this.A = A;
/* 692 */       this.m = Math.max(A.nrow(), A.ncol());
/* 693 */       this.n = Math.min(A.nrow(), A.ncol());
/* 694 */       this.Ax = new double[this.m + this.n];
/*     */     }
/*     */ 
/*     */     
/*     */     public int nrow() {
/* 699 */       return this.n;
/*     */     }
/*     */ 
/*     */     
/*     */     public int ncol() {
/* 704 */       return this.n;
/*     */     }
/*     */ 
/*     */     
/*     */     public long size() {
/* 709 */       return this.A.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public void mv(TransposeModified trans, double alpha, double[] x, double beta, double[] y) {
/* 714 */       if (this.A.nrow() >= this.A.ncol()) {
/* 715 */         this.A.mv(x, this.Ax);
/* 716 */         this.A.tv(alpha, this.Ax, beta, y);
/*     */       } else {
/* 718 */         this.A.tv(x, this.Ax);
/* 719 */         this.A.mv(alpha, this.Ax, beta, y);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void mv(double[] work, int inputOffset, int outputOffset) {
/* 725 */       System.arraycopy(work, inputOffset, this.Ax, 0, this.n);
/*     */       
/* 727 */       if (this.A.nrow() >= this.A.ncol()) {
/* 728 */         this.A.mv(this.Ax, 0, this.n);
/* 729 */         this.A.tv(this.Ax, this.n, 0);
/*     */       } else {
/* 731 */         this.A.tv(this.Ax, 0, this.n);
/* 732 */         this.A.mv(this.Ax, this.n, 0);
/*     */       } 
/*     */       
/* 735 */       System.arraycopy(this.Ax, 0, work, outputOffset, this.n);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void tv(double[] work, int inputOffset, int outputOffset) {
/* 741 */       mv(work, inputOffset, outputOffset);
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
/*     */   
/*     */   public IMatrixModified square() {
/* 754 */     return new Square(this);
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
/*     */   public Preconditioner Jacobi() {
/* 787 */     double[] diag = diag();
/* 788 */     return (b, x) -> {
/*     */         int n = paramArrayOfdouble1.length;
/*     */         for (int i = 0; i < n; i++) {
/*     */           x[i] = (paramArrayOfdouble1[i] != 0.0D) ? (b[i] / paramArrayOfdouble1[i]) : b[i];
/*     */         }
/*     */       };
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
/*     */   public double solve(double[] b, double[] x) {
/* 807 */     return solve(b, x, Jacobi(), 1.0E-6D, 1, 2 * Math.max(nrow(), ncol()));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double solve(double[] b, double[] x, Preconditioner P, double tol, int itol, int maxIter) {
/*     */     double bnrm;
/* 831 */     if (tol <= 0.0D) {
/* 832 */       throw new IllegalArgumentException("Invalid tolerance: " + tol);
/*     */     }
/*     */     
/* 835 */     if (itol < 1 || itol > 4) {
/* 836 */       throw new IllegalArgumentException("Invalid itol: " + itol);
/*     */     }
/*     */     
/* 839 */     if (maxIter <= 0) {
/* 840 */       throw new IllegalArgumentException("Invalid maximum iterations: " + maxIter);
/*     */     }
/*     */     
/* 843 */     double err = 0.0D;
/* 844 */     double bkden = 1.0D, znrm = 0.0D;
/* 845 */     int n = b.length;
/*     */     
/* 847 */     double[] p = new double[n];
/* 848 */     double[] pp = new double[n];
/* 849 */     double[] r = new double[n];
/* 850 */     double[] rr = new double[n];
/* 851 */     double[] z = new double[n];
/* 852 */     double[] zz = new double[n];
/*     */     
/* 854 */     mv(x, r); int j;
/* 855 */     for (j = 0; j < n; j++) {
/* 856 */       r[j] = b[j] - r[j];
/* 857 */       rr[j] = r[j];
/*     */     } 
/*     */     
/* 860 */     if (itol == 1) {
/* 861 */       bnrm = norm(b, itol);
/* 862 */       P.asolve(r, z);
/* 863 */     } else if (itol == 2) {
/* 864 */       P.asolve(b, z);
/* 865 */       bnrm = norm(z, itol);
/* 866 */       P.asolve(r, z);
/* 867 */     } else if (itol == 3 || itol == 4) {
/* 868 */       P.asolve(b, z);
/* 869 */       bnrm = norm(z, itol);
/* 870 */       P.asolve(r, z);
/* 871 */       znrm = norm(z, itol);
/*     */     } else {
/* 873 */       throw new IllegalArgumentException(String.format("Illegal itol: %d", new Object[] { Integer.valueOf(itol) }));
/*     */     } 
/*     */     
/* 876 */     for (int iter = 1; iter <= maxIter; iter++) {
/* 877 */       P.asolve(rr, zz); double bknum;
/* 878 */       for (bknum = 0.0D, j = 0; j < n; j++) {
/* 879 */         bknum += z[j] * rr[j];
/*     */       }
/* 881 */       if (iter == 1) {
/* 882 */         for (j = 0; j < n; j++) {
/* 883 */           p[j] = z[j];
/* 884 */           pp[j] = zz[j];
/*     */         } 
/*     */       } else {
/* 887 */         double bk = bknum / bkden;
/* 888 */         for (j = 0; j < n; j++) {
/* 889 */           p[j] = bk * p[j] + z[j];
/* 890 */           pp[j] = bk * pp[j] + zz[j];
/*     */         } 
/*     */       } 
/* 893 */       bkden = bknum;
/* 894 */       mv(p, z); double akden;
/* 895 */       for (akden = 0.0D, j = 0; j < n; j++) {
/* 896 */         akden += z[j] * pp[j];
/*     */       }
/* 898 */       double ak = bknum / akden;
/* 899 */       tv(pp, zz);
/* 900 */       for (j = 0; j < n; j++) {
/* 901 */         x[j] = x[j] + ak * p[j];
/* 902 */         r[j] = r[j] - ak * z[j];
/* 903 */         rr[j] = rr[j] - ak * zz[j];
/*     */       } 
/* 905 */       P.asolve(r, z);
/* 906 */       if (itol == 1) {
/* 907 */         err = norm(r, itol) / bnrm;
/* 908 */       } else if (itol == 2) {
/* 909 */         err = norm(z, itol) / bnrm;
/* 910 */       } else if (itol == 3 || itol == 4) {
/* 911 */         double zm1nrm = znrm;
/* 912 */         znrm = norm(z, itol);
/* 913 */         if (Math.abs(zm1nrm - znrm) > MathExModified.EPSILON * znrm) {
/* 914 */           double dxnrm = Math.abs(ak) * norm(p, itol);
/* 915 */           err = znrm / Math.abs(zm1nrm - znrm) * dxnrm;
/*     */         } else {
/* 917 */           err = znrm / bnrm;
/*     */           iter++;
/*     */         } 
/* 920 */         double xnrm = norm(x, itol);
/* 921 */         if (err <= 0.5D * xnrm) {
/* 922 */           err /= xnrm;
/*     */         } else {
/* 924 */           err = znrm / bnrm;
/*     */           
/*     */           iter++;
/*     */         } 
/*     */       } 
/* 929 */       if (iter % 10 == 0) {
/* 930 */         logger.info(String.format("BCG: the error after %3d iterations: %.5g", new Object[] { Integer.valueOf(iter), Double.valueOf(err) }));
/*     */       }
/*     */       
/* 933 */       if (err <= tol) {
/* 934 */         logger.info(String.format("BCG: the error after %3d iterations: %.5g", new Object[] { Integer.valueOf(iter), Double.valueOf(err) }));
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 939 */     return err;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double norm(double[] x, int itol) {
/* 946 */     int n = x.length;
/*     */     
/* 948 */     if (itol <= 3) {
/* 949 */       double ans = 0.0D; byte b; int j; double[] arrayOfDouble;
/* 950 */       for (j = (arrayOfDouble = x).length, b = 0; b < j; ) { double v = arrayOfDouble[b];
/* 951 */         ans += v * v; b++; }
/*     */       
/* 953 */       return Math.sqrt(ans);
/*     */     } 
/* 955 */     int isamax = 0;
/* 956 */     for (int i = 0; i < n; i++) {
/* 957 */       if (Math.abs(x[i]) > Math.abs(x[isamax])) {
/* 958 */         isamax = i;
/*     */       }
/*     */     } 
/*     */     
/* 962 */     return Math.abs(x[isamax]);
/*     */   }
/*     */   
/*     */   public abstract int nrow();
/*     */   
/*     */   public abstract int ncol();
/*     */   
/*     */   public abstract long size();
/*     */   
/*     */   public abstract void mv(TransposeModified paramTransposeModified, double paramDouble1, double[] paramArrayOfdouble1, double paramDouble2, double[] paramArrayOfdouble2);
/*     */   
/*     */   public abstract void mv(double[] paramArrayOfdouble, int paramInt1, int paramInt2);
/*     */   
/*     */   public abstract void tv(double[] paramArrayOfdouble, int paramInt1, int paramInt2);
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/IMatrixModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */