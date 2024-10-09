/*     */ package smileModified;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.nio.DoubleBuffer;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymmMatrixModified
/*     */   extends IMatrixModified
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*  31 */   private static final Logger logger = LoggerFactory.getLogger(SymmMatrixModified.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final double[] AP;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final int n;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final UPLOModified uplo;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SymmMatrixModified(UPLOModified uplo, int n) {
/*  52 */     if (uplo == null) {
/*  53 */       throw new NullPointerException("UPLO is null");
/*     */     }
/*     */     
/*  56 */     this.uplo = uplo;
/*  57 */     this.n = n;
/*  58 */     this.AP = new double[n * (n + 1) / 2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SymmMatrixModified(UPLOModified uplo, double[][] AP) {
/*  67 */     this(uplo, AP.length);
/*     */     
/*  69 */     if (uplo == UPLOModified.LOWER) {
/*  70 */       for (int i = 0; i < this.n; i++) {
/*  71 */         for (int j = 0; j <= i; j++) {
/*  72 */           this.AP[i + (2 * this.n - j - 1) * j / 2] = AP[i][j];
/*     */         }
/*     */       } 
/*     */     } else {
/*  76 */       for (int i = 0; i < this.n; i++) {
/*  77 */         for (int j = i; j < this.n; j++) {
/*  78 */           this.AP[i + j * (j + 1) / 2] = AP[i][j];
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SymmMatrixModified clone() {
/*  86 */     SymmMatrixModified matrix = new SymmMatrixModified(this.uplo, this.n);
/*  87 */     System.arraycopy(this.AP, 0, matrix.AP, 0, this.AP.length);
/*  88 */     return matrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public int nrow() {
/*  93 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public int ncol() {
/*  98 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public long size() {
/* 103 */     return this.AP.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LayoutModified layout() {
/* 111 */     return LayoutModified.COL_MAJOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UPLOModified uplo() {
/* 119 */     return this.uplo;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 124 */     if (!(o instanceof SymmMatrixModified)) {
/* 125 */       return false;
/*     */     }
/*     */     
/* 128 */     return equals((SymmMatrixModified)o, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(SymmMatrixModified o, double epsilon) {
/* 139 */     if (this.n != o.n) {
/* 140 */       return false;
/*     */     }
/*     */     
/* 143 */     for (int j = 0; j < this.n; j++) {
/* 144 */       for (int i = 0; i < this.n; i++) {
/* 145 */         if (!MathExModified.isZero(get(i, j) - o.get(i, j), epsilon)) {
/* 146 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int i, int j) {
/* 156 */     if (this.uplo == UPLOModified.LOWER) {
/* 157 */       if (j > i) {
/* 158 */         int tmp = i;
/* 159 */         i = j;
/* 160 */         j = tmp;
/*     */       } 
/* 162 */       return this.AP[i + (2 * this.n - j - 1) * j / 2];
/*     */     } 
/* 164 */     if (i > j) {
/* 165 */       int tmp = i;
/* 166 */       i = j;
/* 167 */       j = tmp;
/*     */     } 
/* 169 */     return this.AP[i + j * (j + 1) / 2];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int i, int j, double x) {
/* 175 */     if (this.uplo == UPLOModified.LOWER) {
/* 176 */       if (j > i) {
/* 177 */         int tmp = i;
/* 178 */         i = j;
/* 179 */         j = tmp;
/*     */       } 
/* 181 */       this.AP[i + (2 * this.n - j - 1) * j / 2] = x;
/*     */     } else {
/* 183 */       if (i > j) {
/* 184 */         int tmp = i;
/* 185 */         i = j;
/* 186 */         j = tmp;
/*     */       } 
/* 188 */       this.AP[i + j * (j + 1) / 2] = x;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mv(TransposeModified trans, double alpha, double[] x, double beta, double[] y) {
/* 194 */     BLASModified.engine.spmv(layout(), this.uplo, this.n, alpha, this.AP, x, 1, beta, y, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void mv(double[] work, int inputOffset, int outputOffset) {
/* 199 */     DoubleBuffer xb = DoubleBuffer.wrap(work, inputOffset, this.n);
/* 200 */     DoubleBuffer yb = DoubleBuffer.wrap(work, outputOffset, this.n);
/* 201 */     BLASModified.engine.spmv(layout(), this.uplo, this.n, 1.0D, DoubleBuffer.wrap(this.AP), xb, 1, 0.0D, yb, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tv(double[] work, int inputOffset, int outputOffset) {
/* 206 */     mv(work, inputOffset, outputOffset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BunchKaufman bk() {
/* 214 */     SymmMatrixModified lu = clone();
/* 215 */     int[] ipiv = new int[this.n];
/* 216 */     int info = LAPACKModified.engine.sptrf(lu.layout(), lu.uplo, lu.n, lu.AP, ipiv);
/* 217 */     if (info < 0) {
/* 218 */       logger.error("LAPACK SPTRF error code: {}", Integer.valueOf(info));
/* 219 */       throw new ArithmeticException("LAPACK SPTRF error code: " + info);
/*     */     } 
/*     */     
/* 222 */     return new BunchKaufman(lu, ipiv, info);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cholesky cholesky() {
/* 232 */     if (this.uplo == null) {
/* 233 */       throw new IllegalArgumentException("The matrix is not symmetric");
/*     */     }
/*     */     
/* 236 */     SymmMatrixModified lu = clone();
/* 237 */     int info = LAPACKModified.engine.pptrf(lu.layout(), lu.uplo, lu.n, lu.AP);
/* 238 */     if (info != 0) {
/* 239 */       logger.error("LAPACK PPTRF error code: {}", Integer.valueOf(info));
/* 240 */       throw new ArithmeticException("LAPACK PPTRF error code: " + info);
/*     */     } 
/*     */     
/* 243 */     return new Cholesky(lu);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class BunchKaufman
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 2L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final SymmMatrixModified lu;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int[] ipiv;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int info;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BunchKaufman(SymmMatrixModified lu, int[] ipiv, int info) {
/* 287 */       this.lu = lu;
/* 288 */       this.ipiv = ipiv;
/* 289 */       this.info = info;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSingular() {
/* 297 */       return (this.info > 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double det() {
/* 305 */       int n = this.lu.n;
/* 306 */       double d = 1.0D; int j;
/* 307 */       for (j = 0; j < n; j++) {
/* 308 */         d *= this.lu.get(j, j);
/*     */       }
/*     */       
/* 311 */       for (j = 0; j < n; j++) {
/* 312 */         if (j + 1 != this.ipiv[j]) {
/* 313 */           d = -d;
/*     */         }
/*     */       } 
/*     */       
/* 317 */       return d;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MatrixModified inverse() {
/* 325 */       MatrixModified inv = MatrixModified.eye(this.lu.n);
/* 326 */       solve(inv);
/* 327 */       return inv;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] solve(double[] b) {
/* 337 */       MatrixModified x = MatrixModified.column(b);
/* 338 */       solve(x);
/* 339 */       return x.A;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void solve(MatrixModified B) {
/* 349 */       if (B.m != this.lu.n) {
/* 350 */         throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", new Object[] { Integer.valueOf(this.lu.n), Integer.valueOf(this.lu.n), Integer.valueOf(B.m), Integer.valueOf(B.n) }));
/*     */       }
/*     */       
/* 353 */       if (this.lu.layout() != B.layout()) {
/* 354 */         throw new IllegalArgumentException("The matrix layout is inconsistent.");
/*     */       }
/*     */       
/* 357 */       if (this.info > 0) {
/* 358 */         throw new RuntimeException("The matrix is singular.");
/*     */       }
/*     */       
/* 361 */       int ret = LAPACKModified.engine.sptrs(this.lu.layout(), this.lu.uplo, this.lu.n, B.n, this.lu.AP, this.ipiv, B.A, B.ld);
/* 362 */       if (ret != 0) {
/* 363 */         SymmMatrixModified.logger.error("LAPACK GETRS error code: {}", Integer.valueOf(ret));
/* 364 */         throw new ArithmeticException("LAPACK GETRS error code: " + ret);
/*     */       } 
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
/*     */ 
/*     */   
/*     */   public static class Cholesky
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 2L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final SymmMatrixModified lu;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Cholesky(SymmMatrixModified lu) {
/* 405 */       if (lu.nrow() != lu.ncol()) {
/* 406 */         throw new UnsupportedOperationException("Cholesky constructor on a non-square matrix");
/*     */       }
/* 408 */       this.lu = lu;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double det() {
/* 416 */       double d = 1.0D;
/* 417 */       for (int i = 0; i < this.lu.n; i++) {
/* 418 */         d *= this.lu.get(i, i);
/*     */       }
/*     */       
/* 421 */       return d * d;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double logdet() {
/* 429 */       int n = this.lu.n;
/* 430 */       double d = 0.0D;
/* 431 */       for (int i = 0; i < n; i++) {
/* 432 */         d += Math.log(this.lu.get(i, i));
/*     */       }
/*     */       
/* 435 */       return 2.0D * d;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MatrixModified inverse() {
/* 443 */       MatrixModified inv = MatrixModified.eye(this.lu.n);
/* 444 */       solve(inv);
/* 445 */       return inv;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] solve(double[] b) {
/* 454 */       MatrixModified x = MatrixModified.column(b);
/* 455 */       solve(x);
/* 456 */       return x.A;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void solve(MatrixModified B) {
/* 465 */       if (B.m != this.lu.n) {
/* 466 */         throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", new Object[] { Integer.valueOf(this.lu.n), Integer.valueOf(this.lu.n), Integer.valueOf(B.m), Integer.valueOf(B.n) }));
/*     */       }
/*     */       
/* 469 */       int info = LAPACKModified.engine.pptrs(this.lu.layout(), this.lu.uplo, this.lu.n, B.n, this.lu.AP, B.A, B.ld);
/* 470 */       if (info != 0) {
/* 471 */         SymmMatrixModified.logger.error("LAPACK POTRS error code: {}", Integer.valueOf(info));
/* 472 */         throw new ArithmeticException("LAPACK POTRS error code: " + info);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/SymmMatrixModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */