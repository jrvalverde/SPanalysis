/*      */ package smileModified;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.nio.DoubleBuffer;
/*      */ import java.util.Arrays;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MatrixModified
/*      */   extends IMatrixModified
/*      */ {
/*      */   private static final long serialVersionUID = 3L;
/*   32 */   private static final Logger logger = LoggerFactory.getLogger(MatrixModified.class);
/*      */   double[] A;
/*      */   int ld;
/*      */   int m;
/*      */   int n;
/*      */   UPLOModified uplo;
/*      */   DiagModified diag;
/*      */   
/*      */   private static class RowMajor
/*      */     extends MatrixModified
/*      */   {
/*      */     RowMajor(int m, int n, int ld, double[] A) {
/*   44 */       super(m, n, ld, A);
/*      */     }
/*      */ 
/*      */     
/*      */     public LayoutModified layout() {
/*   49 */       return LayoutModified.ROW_MAJOR;
/*      */     }
/*      */ 
/*      */     
/*      */     protected int index(int i, int j) {
/*   54 */       return i * this.ld + j;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified(int m, int n) {
/*   90 */     this(m, n, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified(int m, int n, double a) {
/*  100 */     if (m <= 0 || n <= 0) {
/*  101 */       throw new IllegalArgumentException(String.format("Invalid matrix size: %d x %d", new Object[] { Integer.valueOf(m), Integer.valueOf(n) }));
/*      */     }
/*      */     
/*  104 */     this.m = m;
/*  105 */     this.n = n;
/*  106 */     this.ld = ld(m);
/*      */     
/*  108 */     this.A = new double[this.ld * n];
/*  109 */     if (a != 0.0D) Arrays.fill(this.A, a);
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified(int m, int n, int ld, double[] A) {
/*  120 */     if (layout() == LayoutModified.COL_MAJOR && ld < m) {
/*  121 */       throw new IllegalArgumentException(String.format("Invalid leading dimension for COL_MAJOR: %d < %d", new Object[] { Integer.valueOf(ld), Integer.valueOf(m) }));
/*      */     }
/*      */     
/*  124 */     if (layout() == LayoutModified.ROW_MAJOR && ld < n) {
/*  125 */       throw new IllegalArgumentException(String.format("Invalid leading dimension for ROW_MAJOR: %d < %d", new Object[] { Integer.valueOf(ld), Integer.valueOf(n) }));
/*      */     }
/*      */     
/*  128 */     this.m = m;
/*  129 */     this.n = n;
/*  130 */     this.ld = ld;
/*  131 */     this.A = A;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified of(double[][] A) {
/*  140 */     int m = A.length;
/*  141 */     int n = (A[0]).length;
/*  142 */     MatrixModified matrix = new MatrixModified(m, n);
/*      */     
/*  144 */     for (int i = 0; i < m; i++) {
/*  145 */       for (int j = 0; j < n; j++) {
/*  146 */         matrix.set(i, j, A[i][j]);
/*      */       }
/*      */     } 
/*      */     
/*  150 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified column(double[] A) {
/*  159 */     return column(A, 0, A.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified column(double[] A, int offset, int length) {
/*  172 */     MatrixModified matrix = new MatrixModified(length, 1, length, new double[length]);
/*  173 */     System.arraycopy(A, offset, matrix.A, 0, length);
/*  174 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified row(double[] A) {
/*  183 */     return row(A, 0, A.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified row(double[] A, int offset, int length) {
/*  196 */     MatrixModified matrix = new MatrixModified(1, length, 1, new double[length]);
/*  197 */     System.arraycopy(A, offset, matrix.A, 0, length);
/*  198 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified rand(int m, int n, DistributionModified distribution) {
/*  210 */     MatrixModified matrix = new MatrixModified(m, n);
/*      */     
/*  212 */     for (int j = 0; j < n; j++) {
/*  213 */       for (int i = 0; i < m; i++) {
/*  214 */         matrix.set(i, j, distribution.rand());
/*      */       }
/*      */     } 
/*      */     
/*  218 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified randn(int m, int n) {
/*  228 */     return rand(m, n, GaussianDistributionModified.getInstance());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified rand(int m, int n) {
/*  239 */     MatrixModified matrix = new MatrixModified(m, n);
/*      */     
/*  241 */     for (int j = 0; j < n; j++) {
/*  242 */       for (int i = 0; i < m; i++) {
/*  243 */         matrix.set(i, j, MathExModified.random());
/*      */       }
/*      */     } 
/*      */     
/*  247 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified rand(int m, int n, double lo, double hi) {
/*  260 */     MatrixModified matrix = new MatrixModified(m, n);
/*      */     
/*  262 */     for (int j = 0; j < n; j++) {
/*  263 */       for (int i = 0; i < m; i++) {
/*  264 */         matrix.set(i, j, MathExModified.random(lo, hi));
/*      */       }
/*      */     } 
/*      */     
/*  268 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified eye(int n) {
/*  277 */     return diag(n, 1.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified eye(int m, int n) {
/*  287 */     return diag(m, n, 1.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified diag(int n, double diag) {
/*  298 */     return diag(n, n, diag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified diag(int m, int n, double diag) {
/*  310 */     MatrixModified D = new MatrixModified(m, n);
/*  311 */     int k = Math.min(m, n);
/*  312 */     for (int i = 0; i < k; i++) {
/*  313 */       D.set(i, i, diag);
/*      */     }
/*  315 */     return D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified diag(double[] diag) {
/*  325 */     int n = diag.length;
/*  326 */     MatrixModified D = new MatrixModified(n, n);
/*  327 */     for (int i = 0; i < n; i++) {
/*  328 */       D.set(i, i, diag[i]);
/*      */     }
/*  330 */     return D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified toeplitz(double[] a) {
/*  341 */     int n = a.length;
/*  342 */     MatrixModified toeplitz = new MatrixModified(n, n);
/*  343 */     toeplitz.uplo(UPLOModified.LOWER);
/*      */     
/*  345 */     for (int i = 0; i < n; i++) {
/*  346 */       int j; for (j = 0; j < i; j++) {
/*  347 */         toeplitz.set(i, j, a[i - j]);
/*      */       }
/*      */       
/*  350 */       for (j = i; j < n; j++) {
/*  351 */         toeplitz.set(i, j, a[j - i]);
/*      */       }
/*      */     } 
/*      */     
/*  355 */     return toeplitz;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified toeplitz(double[] kl, double[] ku) {
/*  367 */     if (kl.length != ku.length - 1) {
/*  368 */       throw new IllegalArgumentException(String.format("Invalid sub-diagonals and super-diagonals size: %d != %d - 1", new Object[] { Integer.valueOf(kl.length), Integer.valueOf(ku.length) }));
/*      */     }
/*      */     
/*  371 */     int n = kl.length;
/*  372 */     MatrixModified toeplitz = new MatrixModified(n, n);
/*      */     
/*  374 */     for (int i = 0; i < n; i++) {
/*  375 */       int j; for (j = 0; j < i; j++) {
/*  376 */         toeplitz.set(i, j, kl[i - j]);
/*      */       }
/*      */       
/*  379 */       for (j = i; j < n; j++) {
/*  380 */         toeplitz.set(i, j, ku[j - i]);
/*      */       }
/*      */     } 
/*      */     
/*  384 */     return toeplitz;
/*      */   }
/*      */ 
/*      */   
/*      */   public int nrow() {
/*  389 */     return this.m;
/*      */   }
/*      */ 
/*      */   
/*      */   public int ncol() {
/*  394 */     return this.n;
/*      */   }
/*      */ 
/*      */   
/*      */   public long size() {
/*  399 */     return (this.m * this.n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LayoutModified layout() {
/*  407 */     return LayoutModified.COL_MAJOR;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int ld() {
/*  415 */     return this.ld;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSymmetric() {
/*  423 */     return (this.uplo != null && this.diag == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified uplo(UPLOModified uplo) {
/*  432 */     if (this.m != this.n) {
/*  433 */       throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", new Object[] { Integer.valueOf(this.m), Integer.valueOf(this.n) }));
/*      */     }
/*      */     
/*  436 */     this.uplo = uplo;
/*  437 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UPLOModified uplo() {
/*  445 */     return this.uplo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified triangular(DiagModified diag) {
/*  454 */     if (this.m != this.n) {
/*  455 */       throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", new Object[] { Integer.valueOf(this.m), Integer.valueOf(this.n) }));
/*      */     }
/*      */     
/*  458 */     this.diag = diag;
/*  459 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DiagModified triangular() {
/*  468 */     return this.diag;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified clone() {
/*      */     MatrixModified matrix;
/*  475 */     if (layout() == LayoutModified.COL_MAJOR) {
/*  476 */       matrix = new MatrixModified(this.m, this.n, this.ld, (double[])this.A.clone());
/*      */     } else {
/*  478 */       matrix = new MatrixModified(this.m, this.n);
/*  479 */       for (int j = 0; j < this.n; j++) {
/*  480 */         for (int i = 0; i < this.m; i++) {
/*  481 */           matrix.set(i, j, get(i, j));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  486 */     if (this.m == this.n) {
/*  487 */       matrix.uplo(this.uplo);
/*  488 */       matrix.triangular(this.diag);
/*      */     } 
/*      */     
/*  491 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[][] toArray() {
/*  499 */     double[][] array = new double[this.m][this.n];
/*  500 */     for (int i = 0; i < this.m; i++) {
/*  501 */       for (int j = 0; j < this.n; j++) {
/*  502 */         array[i][j] = get(i, j);
/*      */       }
/*      */     } 
/*  505 */     return array;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified set(MatrixModified b) {
/*  515 */     this.m = b.m;
/*  516 */     this.n = b.n;
/*  517 */     this.diag = b.diag;
/*  518 */     this.uplo = b.uplo;
/*      */     
/*  520 */     if (layout() == b.layout()) {
/*  521 */       this.A = b.A;
/*  522 */       this.ld = b.ld;
/*      */     }
/*  524 */     else if (layout() == LayoutModified.COL_MAJOR) {
/*  525 */       this.ld = ld(this.m);
/*  526 */       this.A = new double[this.ld * this.n];
/*      */       
/*  528 */       for (int j = 0; j < this.n; j++) {
/*  529 */         for (int i = 0; i < this.m; i++) {
/*  530 */           set(i, j, get(i, j));
/*      */         }
/*      */       } 
/*      */     } else {
/*  534 */       this.ld = ld(this.n);
/*  535 */       this.A = new double[this.ld * this.m];
/*      */       
/*  537 */       for (int i = 0; i < this.m; i++) {
/*  538 */         for (int j = 0; j < this.n; j++) {
/*  539 */           set(i, j, get(i, j));
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  545 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int index(int i, int j) {
/*  555 */     return j * this.ld + i;
/*      */   }
/*      */ 
/*      */   
/*      */   public double get(int i, int j) {
/*  560 */     return this.A[index(i, j)];
/*      */   }
/*      */ 
/*      */   
/*      */   public void set(int i, int j, double x) {
/*  565 */     this.A[index(i, j)] = x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified get(int[] rows, int[] cols) {
/*  577 */     MatrixModified sub = new MatrixModified(rows.length, cols.length);
/*  578 */     for (int j = 0; j < cols.length; j++) {
/*  579 */       int col = cols[j];
/*  580 */       if (col < 0) col = this.n + col; 
/*  581 */       for (int i = 0; i < rows.length; i++) {
/*  582 */         int row = rows[i];
/*  583 */         if (row < 0) row = this.m + row; 
/*  584 */         sub.set(i, j, get(row, col));
/*      */       } 
/*      */     } 
/*      */     
/*  588 */     return sub;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] row(int i) {
/*  597 */     double[] x = new double[this.n];
/*  598 */     if (i < 0) i = this.m + i;
/*      */     
/*  600 */     if (layout() == LayoutModified.COL_MAJOR) {
/*  601 */       for (int j = 0; j < this.n; j++) {
/*  602 */         x[j] = get(i, j);
/*      */       }
/*      */     } else {
/*  605 */       System.arraycopy(this.A, index(i, 0), x, 0, this.n);
/*      */     } 
/*      */     
/*  608 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] col(int j) {
/*  617 */     double[] x = new double[this.m];
/*  618 */     if (j < 0) j = this.n + j;
/*      */     
/*  620 */     if (layout() == LayoutModified.COL_MAJOR) {
/*  621 */       System.arraycopy(this.A, index(0, j), x, 0, this.m);
/*      */     } else {
/*  623 */       for (int i = 0; i < this.m; i++) {
/*  624 */         x[i] = get(i, j);
/*      */       }
/*      */     } 
/*      */     
/*  628 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified rows(int... rows) {
/*  637 */     MatrixModified x = new MatrixModified(rows.length, this.n);
/*      */     
/*  639 */     for (int i = 0; i < rows.length; i++) {
/*  640 */       int row = rows[i];
/*  641 */       if (row < 0) row = this.m + row; 
/*  642 */       if (layout() == LayoutModified.COL_MAJOR) {
/*  643 */         for (int j = 0; j < this.n; j++) {
/*  644 */           x.set(i, j, get(row, j));
/*      */         }
/*      */       } else {
/*  647 */         System.arraycopy(this.A, index(row, 0), x.A, x.index(i, 0), this.n);
/*      */       } 
/*      */     } 
/*      */     
/*  651 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified cols(int... cols) {
/*  660 */     MatrixModified x = new MatrixModified(this.m, cols.length);
/*      */     
/*  662 */     for (int j = 0; j < cols.length; j++) {
/*  663 */       int col = cols[j];
/*  664 */       if (col < 0) col = this.n + col; 
/*  665 */       if (layout() == LayoutModified.COL_MAJOR) {
/*  666 */         System.arraycopy(this.A, index(0, col), x.A, x.index(0, j), this.m);
/*      */       } else {
/*  668 */         for (int i = 0; i < this.m; i++) {
/*  669 */           x.set(i, j, get(i, col));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  674 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified submatrix(int i, int j, int k, int l) {
/*  687 */     if (i < 0 || i >= this.m || k < i || k >= this.m || j < 0 || j >= this.n || l < j || l >= this.n) {
/*  688 */       throw new IllegalArgumentException(String.format("Invalid submatrix range (%d:%d, %d:%d) of %d x %d", new Object[] { Integer.valueOf(i), Integer.valueOf(k), Integer.valueOf(j), Integer.valueOf(l), Integer.valueOf(this.m), Integer.valueOf(this.n) }));
/*      */     }
/*      */     
/*  691 */     MatrixModified sub = new MatrixModified(k - i + 1, l - j + 1);
/*  692 */     for (int jj = j; jj <= l; jj++) {
/*  693 */       for (int ii = i; ii <= k; ii++) {
/*  694 */         sub.set(ii - i, jj - j, get(ii, jj));
/*      */       }
/*      */     } 
/*      */     
/*  698 */     return sub;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fill(double x) {
/*  706 */     Arrays.fill(this.A, x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified transpose() {
/*  717 */     return transpose(true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified transpose(boolean share) {
/*      */     MatrixModified matrix;
/*  729 */     if (share) {
/*  730 */       if (layout() == LayoutModified.ROW_MAJOR) {
/*  731 */         matrix = new MatrixModified(this.n, this.m, this.ld, this.A);
/*      */       } else {
/*  733 */         matrix = new RowMajor(this.n, this.m, this.ld, this.A);
/*      */       } 
/*      */     } else {
/*  736 */       matrix = new MatrixModified(this.n, this.m);
/*  737 */       for (int j = 0; j < this.m; j++) {
/*  738 */         for (int i = 0; i < this.n; i++) {
/*  739 */           matrix.set(i, j, get(j, i));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  744 */     if (this.m == this.n) {
/*  745 */       matrix.uplo(this.uplo);
/*  746 */       matrix.triangular(this.diag);
/*      */     } 
/*      */     
/*  749 */     return matrix;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/*  754 */     if (!(o instanceof MatrixModified)) {
/*  755 */       return false;
/*      */     }
/*      */     
/*  758 */     return equals((MatrixModified)o, 1.0E-10D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(MatrixModified o, double epsilon) {
/*  769 */     if (this.m != o.m || this.n != o.n) {
/*  770 */       return false;
/*      */     }
/*      */     
/*  773 */     for (int j = 0; j < this.n; j++) {
/*  774 */       for (int i = 0; i < this.m; i++) {
/*  775 */         if (!MathExModified.isZero(get(i, j) - o.get(i, j), epsilon)) {
/*  776 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  781 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double add(int i, int j, double b) {
/*  792 */     this.A[index(i, j)] = this.A[index(i, j)] + b; return this.A[index(i, j)] + b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double sub(int i, int j, double b) {
/*  803 */     this.A[index(i, j)] = this.A[index(i, j)] - b; return this.A[index(i, j)] - b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double mul(int i, int j, double b) {
/*  814 */     this.A[index(i, j)] = this.A[index(i, j)] * b; return this.A[index(i, j)] * b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double div(int i, int j, double b) {
/*  825 */     this.A[index(i, j)] = this.A[index(i, j)] / b; return this.A[index(i, j)] / b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified addDiag(double b) {
/*  834 */     int l = Math.min(this.m, this.n);
/*  835 */     for (int i = 0; i < l; i++) {
/*  836 */       this.A[index(i, i)] = this.A[index(i, i)] + b;
/*      */     }
/*      */     
/*  839 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified addDiag(double[] b) {
/*  848 */     int l = Math.min(this.m, this.n);
/*  849 */     if (b.length != l) {
/*  850 */       throw new IllegalArgumentException("Invalid diagonal array size: " + b.length);
/*      */     }
/*      */     
/*  853 */     for (int i = 0; i < l; i++) {
/*  854 */       this.A[index(i, i)] = this.A[index(i, i)] + b[i];
/*      */     }
/*      */     
/*  857 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified add(double b) {
/*  866 */     for (int i = 0; i < this.A.length; i++) {
/*  867 */       this.A[i] = this.A[i] + b;
/*      */     }
/*      */     
/*  870 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified sub(double b) {
/*  880 */     for (int i = 0; i < this.A.length; i++) {
/*  881 */       this.A[i] = this.A[i] - b;
/*      */     }
/*      */     
/*  884 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified mul(double b) {
/*  893 */     for (int i = 0; i < this.A.length; i++) {
/*  894 */       this.A[i] = this.A[i] * b;
/*      */     }
/*      */     
/*  897 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified div(double b) {
/*  906 */     for (int i = 0; i < this.A.length; i++) {
/*  907 */       this.A[i] = this.A[i] / b;
/*      */     }
/*      */     
/*  910 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified add(MatrixModified B) {
/*  919 */     if (this.m != B.m || this.n != B.n) {
/*  920 */       throw new IllegalArgumentException("Matrix is not of same size.");
/*      */     }
/*      */     
/*  923 */     if (layout() == B.layout() && this.ld == B.ld) {
/*  924 */       for (int i = 0; i < this.A.length; i++) {
/*  925 */         this.A[i] = this.A[i] + B.A[i];
/*      */       }
/*      */     } else {
/*  928 */       for (int j = 0; j < this.n; j++) {
/*  929 */         for (int i = 0; i < this.m; i++) {
/*  930 */           add(i, j, B.get(i, j));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  935 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified sub(MatrixModified B) {
/*  944 */     if (this.m != B.m || this.n != B.n) {
/*  945 */       throw new IllegalArgumentException("Matrix is not of same size.");
/*      */     }
/*      */     
/*  948 */     if (layout() == B.layout() && this.ld == B.ld) {
/*  949 */       for (int i = 0; i < this.A.length; i++) {
/*  950 */         this.A[i] = this.A[i] - B.A[i];
/*      */       }
/*      */     } else {
/*  953 */       for (int j = 0; j < this.n; j++) {
/*  954 */         for (int i = 0; i < this.m; i++) {
/*  955 */           sub(i, j, B.get(i, j));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  960 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified mul(MatrixModified B) {
/*  969 */     if (this.m != B.m || this.n != B.n) {
/*  970 */       throw new IllegalArgumentException("Matrix is not of same size.");
/*      */     }
/*      */     
/*  973 */     if (layout() == B.layout() && this.ld == B.ld) {
/*  974 */       for (int i = 0; i < this.A.length; i++) {
/*  975 */         this.A[i] = this.A[i] * B.A[i];
/*      */       }
/*      */     } else {
/*  978 */       for (int j = 0; j < this.n; j++) {
/*  979 */         for (int i = 0; i < this.m; i++) {
/*  980 */           mul(i, j, B.get(i, j));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  985 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified div(MatrixModified B) {
/*  994 */     if (this.m != B.m || this.n != B.n) {
/*  995 */       throw new IllegalArgumentException("Matrix is not of same size.");
/*      */     }
/*      */     
/*  998 */     if (layout() == B.layout() && this.ld == B.ld) {
/*  999 */       for (int i = 0; i < this.A.length; i++) {
/* 1000 */         this.A[i] = this.A[i] / B.A[i];
/*      */       }
/*      */     } else {
/* 1003 */       for (int j = 0; j < this.n; j++) {
/* 1004 */         for (int i = 0; i < this.m; i++) {
/* 1005 */           div(i, j, B.get(i, j));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1010 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified add(double beta, MatrixModified B) {
/* 1020 */     if (this.m != B.m || this.n != B.n) {
/* 1021 */       throw new IllegalArgumentException("Matrix is not of same size.");
/*      */     }
/*      */     
/* 1024 */     if (layout() == B.layout() && this.ld == B.ld) {
/* 1025 */       for (int i = 0; i < this.A.length; i++) {
/* 1026 */         this.A[i] = this.A[i] + beta * B.A[i];
/*      */       }
/*      */     } else {
/* 1029 */       for (int j = 0; j < this.n; j++) {
/* 1030 */         for (int i = 0; i < this.m; i++) {
/* 1031 */           add(i, j, beta * B.get(i, j));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1036 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified add(double alpha, double beta, MatrixModified B) {
/* 1047 */     if (this.m != B.m || this.n != B.n) {
/* 1048 */       throw new IllegalArgumentException("Matrix B is not of same size.");
/*      */     }
/*      */     
/* 1051 */     if (layout() == B.layout() && this.ld == B.ld) {
/* 1052 */       for (int i = 0; i < this.A.length; i++) {
/* 1053 */         this.A[i] = alpha * this.A[i] + beta * B.A[i];
/*      */       }
/*      */     } else {
/* 1056 */       for (int j = 0; j < this.n; j++) {
/* 1057 */         for (int i = 0; i < this.m; i++) {
/* 1058 */           set(i, j, alpha * get(i, j) + beta * B.get(i, j));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1063 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified add2(double alpha, double beta, MatrixModified B) {
/* 1074 */     if (this.m != B.m || this.n != B.n) {
/* 1075 */       throw new IllegalArgumentException("Matrix B is not of same size.");
/*      */     }
/*      */     
/* 1078 */     if (layout() == B.layout() && this.ld == B.ld) {
/* 1079 */       for (int i = 0; i < this.A.length; i++) {
/* 1080 */         this.A[i] = alpha * this.A[i] + beta * B.A[i] * B.A[i];
/*      */       }
/*      */     } else {
/* 1083 */       for (int j = 0; j < this.n; j++) {
/* 1084 */         for (int i = 0; i < this.m; i++) {
/* 1085 */           set(i, j, alpha * get(i, j) + beta * B.get(i, j) * B.get(i, j));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1090 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified add(double alpha, MatrixModified A, double beta, MatrixModified B) {
/* 1102 */     if (this.m != A.m || this.n != A.n) {
/* 1103 */       throw new IllegalArgumentException("Matrix A is not of same size.");
/*      */     }
/*      */     
/* 1106 */     if (this.m != B.m || this.n != B.n) {
/* 1107 */       throw new IllegalArgumentException("Matrix B is not of same size.");
/*      */     }
/*      */     
/* 1110 */     if (layout() == A.layout() && layout() == B.layout() && this.ld == A.ld && this.ld == B.ld) {
/* 1111 */       for (int i = 0; i < this.A.length; i++) {
/* 1112 */         this.A[i] = alpha * A.A[i] + beta * B.A[i];
/*      */       }
/*      */     } else {
/* 1115 */       for (int j = 0; j < this.n; j++) {
/* 1116 */         for (int i = 0; i < this.m; i++) {
/* 1117 */           set(i, j, alpha * A.get(i, j) + beta * B.get(i, j));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1122 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified add(double alpha, double[] x, double[] y) {
/* 1133 */     if (this.m != x.length || this.n != y.length) {
/* 1134 */       throw new IllegalArgumentException("Matrix is not of same size.");
/*      */     }
/*      */     
/* 1137 */     if (isSymmetric() && x == y) {
/* 1138 */       BLASModified.engine.syr(layout(), this.uplo, this.m, alpha, x, 1, this.A, this.ld);
/*      */     } else {
/* 1140 */       BLASModified.engine.ger(layout(), this.m, this.n, alpha, x, 1, y, 1, this.A, this.ld);
/*      */     } 
/*      */     
/* 1143 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified replaceNaN(double x) {
/* 1152 */     for (int i = 0; i < this.A.length; i++) {
/* 1153 */       if (Double.isNaN(this.A[i])) {
/* 1154 */         this.A[i] = x;
/*      */       }
/*      */     } 
/*      */     
/* 1158 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double sum() {
/* 1166 */     double s = 0.0D;
/* 1167 */     for (int j = 0; j < this.n; j++) {
/* 1168 */       for (int i = 0; i < this.m; i++) {
/* 1169 */         s += get(i, j);
/*      */       }
/*      */     } 
/*      */     
/* 1173 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double norm1() {
/* 1181 */     double f = 0.0D;
/* 1182 */     for (int j = 0; j < this.n; j++) {
/* 1183 */       double s = 0.0D;
/* 1184 */       for (int i = 0; i < this.m; i++) {
/* 1185 */         s += Math.abs(get(i, j));
/*      */       }
/* 1187 */       f = Math.max(f, s);
/*      */     } 
/*      */     
/* 1190 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double norm2() {
/* 1198 */     return (svd(false, false)).s[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double norm() {
/* 1206 */     return norm2();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double normInf() {
/* 1214 */     double[] f = new double[this.m];
/* 1215 */     for (int j = 0; j < this.n; j++) {
/* 1216 */       for (int i = 0; i < this.m; i++) {
/* 1217 */         f[i] = f[i] + Math.abs(get(i, j));
/*      */       }
/*      */     } 
/*      */     
/* 1221 */     return MathExModified.max(f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double normFro() {
/* 1229 */     double f = 0.0D;
/* 1230 */     for (int j = 0; j < this.n; j++) {
/* 1231 */       for (int i = 0; i < this.m; i++) {
/* 1232 */         f = Math.hypot(f, get(i, j));
/*      */       }
/*      */     } 
/*      */     
/* 1236 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double xAx(double[] x) {
/* 1247 */     if (this.m != this.n) {
/* 1248 */       throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", new Object[] { Integer.valueOf(this.m), Integer.valueOf(this.n) }));
/*      */     }
/*      */     
/* 1251 */     if (this.n != x.length) {
/* 1252 */       throw new IllegalArgumentException(String.format("Matrix: %d x %d, Vector: %d", new Object[] { Integer.valueOf(this.m), Integer.valueOf(this.n), Integer.valueOf(x.length) }));
/*      */     }
/*      */     
/* 1255 */     double[] Ax = mv(x);
/* 1256 */     return MathExModified.dot(x, Ax);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] rowSums() {
/* 1264 */     double[] x = new double[this.m];
/*      */     
/* 1266 */     for (int j = 0; j < this.n; j++) {
/* 1267 */       for (int i = 0; i < this.m; i++) {
/* 1268 */         x[i] = x[i] + get(i, j);
/*      */       }
/*      */     } 
/*      */     
/* 1272 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] rowMeans() {
/* 1280 */     double[] x = rowSums();
/*      */     
/* 1282 */     for (int i = 0; i < this.m; i++) {
/* 1283 */       x[i] = x[i] / this.n;
/*      */     }
/*      */     
/* 1286 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] rowSds() {
/* 1294 */     double[] x = new double[this.m];
/* 1295 */     double[] x2 = new double[this.m];
/*      */     
/* 1297 */     for (int j = 0; j < this.n; j++) {
/* 1298 */       for (int k = 0; k < this.m; k++) {
/* 1299 */         double a = get(k, j);
/* 1300 */         x[k] = x[k] + a;
/* 1301 */         x2[k] = x2[k] + a * a;
/*      */       } 
/*      */     } 
/*      */     
/* 1305 */     for (int i = 0; i < this.m; i++) {
/* 1306 */       double mu = x[i] / this.n;
/* 1307 */       x[i] = Math.sqrt(x2[i] / this.n - mu * mu);
/*      */     } 
/*      */     
/* 1310 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] colSums() {
/* 1318 */     double[] x = new double[this.n];
/*      */     
/* 1320 */     for (int j = 0; j < this.n; j++) {
/* 1321 */       for (int i = 0; i < this.m; i++) {
/* 1322 */         x[j] = x[j] + get(i, j);
/*      */       }
/*      */     } 
/*      */     
/* 1326 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] colMeans() {
/* 1334 */     double[] x = colSums();
/*      */     
/* 1336 */     for (int j = 0; j < this.n; j++) {
/* 1337 */       x[j] = x[j] / this.m;
/*      */     }
/*      */     
/* 1340 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[] colSds() {
/* 1348 */     double[] x = new double[this.n];
/*      */     
/* 1350 */     for (int j = 0; j < this.n; j++) {
/* 1351 */       double mu = 0.0D;
/* 1352 */       double sumsq = 0.0D;
/* 1353 */       for (int i = 0; i < this.m; i++) {
/* 1354 */         double a = get(i, j);
/* 1355 */         mu += a;
/* 1356 */         sumsq += a * a;
/*      */       } 
/* 1358 */       mu /= this.m;
/* 1359 */       x[j] = Math.sqrt(sumsq / this.m - mu * mu);
/*      */     } 
/*      */     
/* 1362 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified standardize() {
/* 1370 */     double[] center = colMeans();
/* 1371 */     double[] scale = colSds();
/* 1372 */     return scale(center, scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified scale(double[] center, double[] scale) {
/* 1382 */     if (center == null && scale == null) {
/* 1383 */       throw new IllegalArgumentException("Both center and scale are null");
/*      */     }
/*      */     
/* 1386 */     MatrixModified matrix = new MatrixModified(this.m, this.n);
/*      */     
/* 1388 */     if (center == null) {
/* 1389 */       for (int j = 0; j < this.n; j++) {
/* 1390 */         for (int i = 0; i < this.m; i++) {
/* 1391 */           matrix.set(i, j, get(i, j) / scale[j]);
/*      */         }
/*      */       } 
/* 1394 */     } else if (scale == null) {
/* 1395 */       for (int j = 0; j < this.n; j++) {
/* 1396 */         for (int i = 0; i < this.m; i++) {
/* 1397 */           matrix.set(i, j, get(i, j) - center[j]);
/*      */         }
/*      */       } 
/*      */     } else {
/* 1401 */       for (int j = 0; j < this.n; j++) {
/* 1402 */         for (int i = 0; i < this.m; i++) {
/* 1403 */           matrix.set(i, j, (get(i, j) - center[j]) / scale[j]);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1408 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified inverse() {
/* 1416 */     if (this.m != this.n) {
/* 1417 */       throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", new Object[] { Integer.valueOf(this.m), Integer.valueOf(this.n) }));
/*      */     }
/*      */     
/* 1420 */     MatrixModified lu = clone();
/* 1421 */     MatrixModified inv = eye(this.n);
/* 1422 */     int[] ipiv = new int[this.n];
/* 1423 */     if (isSymmetric()) {
/* 1424 */       int info = LAPACKModified.engine.sysv(lu.layout(), this.uplo, this.n, this.n, lu.A, lu.ld, ipiv, inv.A, inv.ld);
/* 1425 */       if (info != 0) {
/* 1426 */         throw new ArithmeticException("SYSV fails: " + info);
/*      */       }
/*      */     } else {
/* 1429 */       int info = LAPACKModified.engine.gesv(lu.layout(), this.n, this.n, lu.A, lu.ld, ipiv, inv.A, inv.ld);
/* 1430 */       if (info != 0) {
/* 1431 */         throw new ArithmeticException("GESV fails: " + info);
/*      */       }
/*      */     } 
/*      */     
/* 1435 */     return inv;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void mv(TransposeModified trans, double alpha, DoubleBuffer x, double beta, DoubleBuffer y) {
/* 1451 */     DoubleBuffer A = DoubleBuffer.wrap(this.A);
/* 1452 */     if (this.uplo != null) {
/* 1453 */       if (this.diag != null) {
/* 1454 */         if (alpha == 1.0D && beta == 0.0D && x == y) {
/* 1455 */           BLASModified.engine.trmv(layout(), this.uplo, trans, this.diag, this.m, A, this.ld, y, 1);
/*      */         } else {
/* 1457 */           BLASModified.engine.gemv(layout(), trans, this.m, this.n, alpha, A, this.ld, x, 1, beta, y, 1);
/*      */         } 
/*      */       } else {
/* 1460 */         BLASModified.engine.symv(layout(), this.uplo, this.m, alpha, A, this.ld, x, 1, beta, y, 1);
/*      */       } 
/*      */     } else {
/* 1463 */       BLASModified.engine.gemv(layout(), trans, this.m, this.n, alpha, A, this.ld, x, 1, beta, y, 1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void mv(TransposeModified trans, double alpha, double[] x, double beta, double[] y) {
/* 1469 */     if (this.uplo != null) {
/* 1470 */       if (this.diag != null) {
/* 1471 */         if (alpha == 1.0D && beta == 0.0D && x == y) {
/* 1472 */           BLASModified.engine.trmv(layout(), this.uplo, trans, this.diag, this.m, this.A, this.ld, y, 1);
/*      */         } else {
/* 1474 */           BLASModified.engine.gemv(layout(), trans, this.m, this.n, alpha, this.A, this.ld, x, 1, beta, y, 1);
/*      */         } 
/*      */       } else {
/* 1477 */         BLASModified.engine.symv(layout(), this.uplo, this.m, alpha, this.A, this.ld, x, 1, beta, y, 1);
/*      */       } 
/*      */     } else {
/* 1480 */       BLASModified.engine.gemv(layout(), trans, this.m, this.n, alpha, this.A, this.ld, x, 1, beta, y, 1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void mv(double[] work, int inputOffset, int outputOffset) {
/* 1486 */     DoubleBuffer xb = DoubleBuffer.wrap(work, inputOffset, this.n);
/* 1487 */     DoubleBuffer yb = DoubleBuffer.wrap(work, outputOffset, this.m);
/* 1488 */     mv(TransposeModified.NO_TRANSPOSE, 1.0D, xb, 0.0D, yb);
/*      */   }
/*      */ 
/*      */   
/*      */   public void tv(double[] work, int inputOffset, int outputOffset) {
/* 1493 */     DoubleBuffer xb = DoubleBuffer.wrap(work, inputOffset, this.m);
/* 1494 */     DoubleBuffer yb = DoubleBuffer.wrap(work, outputOffset, this.n);
/* 1495 */     mv(TransposeModified.TRANSPOSE, 1.0D, xb, 0.0D, yb);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified mm(TransposeModified transA, MatrixModified A, TransposeModified transB, MatrixModified B) {
/* 1512 */     return mm(transA, A, transB, B, 1.0D, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified mm(TransposeModified transA, MatrixModified A, TransposeModified transB, MatrixModified B, double alpha, double beta) {
/* 1531 */     if (A.isSymmetric() && transB == TransposeModified.NO_TRANSPOSE && B.layout() == layout()) {
/* 1532 */       BLASModified.engine.symm(layout(), SideModified.LEFT, A.uplo, this.m, this.n, alpha, A.A, A.ld, B.A, B.ld, beta, this.A, this.ld);
/* 1533 */     } else if (B.isSymmetric() && transA == TransposeModified.NO_TRANSPOSE && A.layout() == layout()) {
/* 1534 */       BLASModified.engine.symm(layout(), SideModified.RIGHT, B.uplo, this.m, this.n, alpha, B.A, B.ld, A.A, A.ld, beta, this.A, this.ld);
/*      */     } else {
/* 1536 */       if (layout() != A.layout()) {
/* 1537 */         transA = flip(transA);
/* 1538 */         A = A.transpose();
/*      */       } 
/* 1540 */       if (layout() != B.layout()) {
/* 1541 */         transB = flip(transB);
/* 1542 */         B = B.transpose();
/*      */       } 
/* 1544 */       int k = (transA == TransposeModified.NO_TRANSPOSE) ? A.n : A.m;
/*      */       
/* 1546 */       BLASModified.engine.gemm(layout(), transA, transB, this.m, this.n, k, alpha, A.A, A.ld, B.A, B.ld, beta, this.A, this.ld);
/*      */     } 
/* 1548 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified ata() {
/* 1556 */     MatrixModified C = new MatrixModified(this.n, this.n);
/* 1557 */     C.mm(TransposeModified.TRANSPOSE, this, TransposeModified.NO_TRANSPOSE, this);
/* 1558 */     C.uplo(UPLOModified.LOWER);
/* 1559 */     return C;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified aat() {
/* 1567 */     MatrixModified C = new MatrixModified(this.m, this.m);
/* 1568 */     C.mm(TransposeModified.NO_TRANSPOSE, this, TransposeModified.TRANSPOSE, this);
/* 1569 */     C.uplo(UPLOModified.LOWER);
/* 1570 */     return C;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified adb(TransposeModified transA, MatrixModified A, double[] D, TransposeModified transB, MatrixModified B) {
/*      */     MatrixModified AD;
/* 1586 */     int m = A.m, n = A.n;
/* 1587 */     if (transA == TransposeModified.NO_TRANSPOSE) {
/* 1588 */       AD = new MatrixModified(m, n);
/* 1589 */       for (int j = 0; j < n; j++) {
/* 1590 */         double dj = D[j];
/* 1591 */         for (int i = 0; i < m; i++) {
/* 1592 */           AD.set(i, j, dj * A.get(i, j));
/*      */         }
/*      */       } 
/*      */     } else {
/* 1596 */       AD = new MatrixModified(n, m);
/* 1597 */       for (int j = 0; j < m; j++) {
/* 1598 */         double dj = D[j];
/* 1599 */         for (int i = 0; i < n; i++) {
/* 1600 */           AD.set(i, j, dj * A.get(j, i));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1605 */     return (transB == TransposeModified.NO_TRANSPOSE) ? AD.mm(B) : AD.mt(B);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified mm(MatrixModified B) {
/* 1614 */     if (this.n != B.m) {
/* 1615 */       throw new IllegalArgumentException(String.format("Matrix multiplication A * B: %d x %d vs %d x %d", new Object[] { Integer.valueOf(this.m), Integer.valueOf(this.n), Integer.valueOf(B.m), Integer.valueOf(B.n) }));
/*      */     }
/*      */     
/* 1618 */     MatrixModified C = new MatrixModified(this.m, B.n);
/* 1619 */     C.mm(TransposeModified.NO_TRANSPOSE, this, TransposeModified.NO_TRANSPOSE, B);
/* 1620 */     return C;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified mt(MatrixModified B) {
/* 1629 */     if (this.n != B.n) {
/* 1630 */       throw new IllegalArgumentException(String.format("Matrix multiplication A * B': %d x %d vs %d x %d", new Object[] { Integer.valueOf(this.m), Integer.valueOf(this.n), Integer.valueOf(B.m), Integer.valueOf(B.n) }));
/*      */     }
/*      */     
/* 1633 */     MatrixModified C = new MatrixModified(this.m, B.m);
/* 1634 */     C.mm(TransposeModified.NO_TRANSPOSE, this, TransposeModified.TRANSPOSE, B);
/* 1635 */     return C;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified tm(MatrixModified B) {
/* 1644 */     if (this.m != B.m) {
/* 1645 */       throw new IllegalArgumentException(String.format("Matrix multiplication A' * B: %d x %d vs %d x %d", new Object[] { Integer.valueOf(this.m), Integer.valueOf(this.n), Integer.valueOf(B.m), Integer.valueOf(B.n) }));
/*      */     }
/*      */     
/* 1648 */     MatrixModified C = new MatrixModified(this.n, B.n);
/* 1649 */     C.mm(TransposeModified.TRANSPOSE, this, TransposeModified.NO_TRANSPOSE, B);
/* 1650 */     return C;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MatrixModified tt(MatrixModified B) {
/* 1659 */     if (this.m != B.n) {
/* 1660 */       throw new IllegalArgumentException(String.format("Matrix multiplication A' * B': %d x %d vs %d x %d", new Object[] { Integer.valueOf(this.m), Integer.valueOf(this.n), Integer.valueOf(B.m), Integer.valueOf(B.n) }));
/*      */     }
/*      */     
/* 1663 */     MatrixModified C = new MatrixModified(this.n, B.m);
/* 1664 */     C.mm(TransposeModified.TRANSPOSE, this, TransposeModified.TRANSPOSE, B);
/* 1665 */     return C;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LU lu() {
/* 1673 */     return lu(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LU lu(boolean overwrite) {
/* 1683 */     MatrixModified lu = overwrite ? this : clone();
/* 1684 */     int[] ipiv = new int[Math.min(this.m, this.n)];
/* 1685 */     int info = LAPACKModified.engine.getrf(lu.layout(), lu.m, lu.n, lu.A, lu.ld, ipiv);
/* 1686 */     if (info < 0) {
/* 1687 */       logger.error("LAPACK GETRF error code: {}", Integer.valueOf(info));
/* 1688 */       throw new ArithmeticException("LAPACK GETRF error code: " + info);
/*      */     } 
/*      */     
/* 1691 */     lu.uplo = null;
/* 1692 */     return new LU(lu, ipiv, info);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Cholesky cholesky() {
/* 1702 */     return cholesky(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Cholesky cholesky(boolean overwrite) {
/* 1713 */     if (this.uplo == null) {
/* 1714 */       throw new IllegalArgumentException("The matrix is not symmetric");
/*      */     }
/*      */     
/* 1717 */     MatrixModified lu = overwrite ? this : clone();
/* 1718 */     int info = LAPACKModified.engine.potrf(lu.layout(), lu.uplo, lu.n, lu.A, lu.ld);
/* 1719 */     if (info != 0) {
/* 1720 */       logger.error("LAPACK GETRF error code: {}", Integer.valueOf(info));
/* 1721 */       throw new ArithmeticException("LAPACK GETRF error code: " + info);
/*      */     } 
/*      */     
/* 1724 */     return new Cholesky(lu);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QR qr() {
/* 1732 */     return qr(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public QR qr(boolean overwrite) {
/* 1742 */     MatrixModified qr = overwrite ? this : clone();
/* 1743 */     double[] tau = new double[Math.min(this.m, this.n)];
/* 1744 */     int info = LAPACKModified.engine.geqrf(qr.layout(), qr.m, qr.n, qr.A, qr.ld, tau);
/* 1745 */     if (info != 0) {
/* 1746 */       logger.error("LAPACK GEQRF error code: {}", Integer.valueOf(info));
/* 1747 */       throw new ArithmeticException("LAPACK GEQRF error code: " + info);
/*      */     } 
/*      */     
/* 1750 */     qr.uplo = null;
/* 1751 */     return new QR(qr, tau);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SVD svd() {
/* 1770 */     return svd(true, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SVD svd(boolean vectors, boolean overwrite) {
/* 1792 */     int k = Math.min(this.m, this.n);
/* 1793 */     double[] s = new double[k];
/*      */     
/* 1795 */     MatrixModified W = overwrite ? this : clone();
/* 1796 */     if (vectors) {
/* 1797 */       MatrixModified matrixModified1 = new MatrixModified(this.m, k);
/* 1798 */       MatrixModified matrixModified2 = new MatrixModified(k, this.n);
/*      */       
/* 1800 */       int i = LAPACKModified.engine.gesdd(W.layout(), SVDJobModified.COMPACT, W.m, W.n, W.A, W.ld, s, matrixModified1.A, matrixModified1.ld, matrixModified2.A, matrixModified2.ld);
/* 1801 */       if (i != 0) {
/* 1802 */         logger.error("LAPACK GESDD error code: {}", Integer.valueOf(i));
/* 1803 */         throw new ArithmeticException("LAPACK GESDD error code: " + i);
/*      */       } 
/*      */       
/* 1806 */       return new SVD(s, matrixModified1, matrixModified2.transpose());
/*      */     } 
/* 1808 */     MatrixModified U = new MatrixModified(1, 1);
/* 1809 */     MatrixModified VT = new MatrixModified(1, 1);
/*      */     
/* 1811 */     int info = LAPACKModified.engine.gesdd(W.layout(), SVDJobModified.NO_VECTORS, W.m, W.n, W.A, W.ld, s, U.A, U.ld, VT.A, VT.ld);
/* 1812 */     if (info != 0) {
/* 1813 */       logger.error("LAPACK GESDD error code: {}", Integer.valueOf(info));
/* 1814 */       throw new ArithmeticException("LAPACK GESDD error code: " + info);
/*      */     } 
/*      */     
/* 1817 */     return new SVD(this.m, this.n, s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EVD eigen() {
/* 1832 */     return eigen(false, true, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EVD eigen(boolean vl, boolean vr, boolean overwrite) {
/* 1850 */     if (this.m != this.n) {
/* 1851 */       throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", new Object[] { Integer.valueOf(this.m), Integer.valueOf(this.n) }));
/*      */     }
/*      */     
/* 1854 */     MatrixModified eig = overwrite ? this : clone();
/* 1855 */     if (isSymmetric()) {
/* 1856 */       double[] w = new double[this.n];
/* 1857 */       int i = LAPACKModified.engine.syevd(eig.layout(), vr ? EVDJobModified.VECTORS : EVDJobModified.NO_VECTORS, eig.uplo, this.n, eig.A, eig.ld, w);
/* 1858 */       if (i != 0) {
/* 1859 */         logger.error("LAPACK SYEV error code: {}", Integer.valueOf(i));
/* 1860 */         throw new ArithmeticException("LAPACK SYEV error code: " + i);
/*      */       } 
/*      */       
/* 1863 */       eig.uplo = null;
/* 1864 */       return new EVD(w, vr ? eig : null);
/*      */     } 
/* 1866 */     double[] wr = new double[this.n];
/* 1867 */     double[] wi = new double[this.n];
/* 1868 */     MatrixModified Vl = vl ? new MatrixModified(this.n, this.n) : new MatrixModified(1, 1);
/* 1869 */     MatrixModified Vr = vr ? new MatrixModified(this.n, this.n) : new MatrixModified(1, 1);
/* 1870 */     int info = LAPACKModified.engine.geev(eig.layout(), vl ? EVDJobModified.VECTORS : EVDJobModified.NO_VECTORS, vr ? EVDJobModified.VECTORS : EVDJobModified.NO_VECTORS, this.n, eig.A, eig.ld, wr, wi, Vl.A, Vl.ld, Vr.A, Vr.ld);
/* 1871 */     if (info != 0) {
/* 1872 */       logger.error("LAPACK GEEV error code: {}", Integer.valueOf(info));
/* 1873 */       throw new ArithmeticException("LAPACK GEEV error code: " + info);
/*      */     } 
/*      */     
/* 1876 */     return new EVD(wr, wi, vl ? Vl : null, vr ? Vr : null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class SVD
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int m;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int n;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final double[] s;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final MatrixModified U;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final MatrixModified V;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private transient MatrixModified Ur;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SVD(int m, int n, double[] s) {
/* 1945 */       this.m = m;
/* 1946 */       this.n = n;
/* 1947 */       this.s = s;
/* 1948 */       this.U = null;
/* 1949 */       this.V = null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public SVD(double[] s, MatrixModified U, MatrixModified V) {
/* 1959 */       this.m = U.m;
/* 1960 */       this.n = V.m;
/* 1961 */       this.s = s;
/* 1962 */       this.U = U;
/* 1963 */       this.V = V;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MatrixModified diag() {
/* 1971 */       MatrixModified S = new MatrixModified(this.U.m, this.V.m);
/*      */       
/* 1973 */       for (int i = 0; i < this.s.length; i++) {
/* 1974 */         S.set(i, i, this.s[i]);
/*      */       }
/*      */       
/* 1977 */       return S;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double norm() {
/* 1985 */       return this.s[0];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private double rcond() {
/* 1994 */       return 0.5D * Math.sqrt((this.m + this.n + 1)) * this.s[0] * MathExModified.EPSILON;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int rank() {
/* 2003 */       if (this.s.length != Math.min(this.m, this.n)) {
/* 2004 */         throw new UnsupportedOperationException("The operation cannot be called on a partial SVD.");
/*      */       }
/*      */       
/* 2007 */       int r = 0;
/* 2008 */       double tol = rcond(); byte b; int i;
/*      */       double[] arrayOfDouble;
/* 2010 */       for (i = (arrayOfDouble = this.s).length, b = 0; b < i; ) { double si = arrayOfDouble[b];
/* 2011 */         if (si > tol)
/* 2012 */           r++; 
/*      */         b++; }
/*      */       
/* 2015 */       return r;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int nullity() {
/* 2024 */       return Math.min(this.m, this.n) - rank();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double condition() {
/* 2045 */       if (this.s.length != Math.min(this.m, this.n)) {
/* 2046 */         throw new UnsupportedOperationException("The operation cannot be called on a partial SVD.");
/*      */       }
/*      */       
/* 2049 */       return (this.s[0] <= 0.0D || this.s[this.s.length - 1] <= 0.0D) ? Double.POSITIVE_INFINITY : (this.s[0] / this.s[this.s.length - 1]);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MatrixModified range() {
/* 2058 */       if (this.s.length != Math.min(this.m, this.n)) {
/* 2059 */         throw new UnsupportedOperationException("The operation cannot be called on a partial SVD.");
/*      */       }
/*      */       
/* 2062 */       if (this.U == null) {
/* 2063 */         throw new IllegalStateException("The left singular vectors are not available.");
/*      */       }
/*      */       
/* 2066 */       int r = rank();
/*      */       
/* 2068 */       if (r == 0) {
/* 2069 */         return null;
/*      */       }
/*      */       
/* 2072 */       MatrixModified R = new MatrixModified(this.m, r);
/* 2073 */       for (int j = 0; j < r; j++) {
/* 2074 */         for (int i = 0; i < this.m; i++) {
/* 2075 */           R.set(i, j, this.U.get(i, j));
/*      */         }
/*      */       } 
/*      */       
/* 2079 */       return R;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MatrixModified nullspace() {
/* 2088 */       if (this.s.length != Math.min(this.m, this.n)) {
/* 2089 */         throw new UnsupportedOperationException("The operation cannot be called on a partial SVD.");
/*      */       }
/*      */       
/* 2092 */       if (this.V == null) {
/* 2093 */         throw new IllegalStateException("The right singular vectors are not available.");
/*      */       }
/*      */       
/* 2096 */       int nr = nullity();
/*      */       
/* 2098 */       if (nr == 0) {
/* 2099 */         return null;
/*      */       }
/*      */       
/* 2102 */       MatrixModified N = new MatrixModified(this.n, nr);
/* 2103 */       for (int j = 0; j < nr; j++) {
/* 2104 */         for (int i = 0; i < this.n; i++) {
/* 2105 */           N.set(i, j, this.V.get(i, this.n - j - 1));
/*      */         }
/*      */       } 
/* 2108 */       return N;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MatrixModified pinv() {
/* 2116 */       int k = this.s.length;
/* 2117 */       double[] sigma = new double[k];
/* 2118 */       int r = rank();
/* 2119 */       for (int i = 0; i < r; i++) {
/* 2120 */         sigma[i] = 1.0D / this.s[i];
/*      */       }
/*      */       
/* 2123 */       return MatrixModified.adb(TransposeModified.NO_TRANSPOSE, this.V, sigma, TransposeModified.TRANSPOSE, this.U);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] solve(double[] b) {
/* 2133 */       if (this.U == null || this.V == null) {
/* 2134 */         throw new IllegalStateException("The singular vectors are not available.");
/*      */       }
/*      */       
/* 2137 */       if (b.length != this.m) {
/* 2138 */         throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x 1", new Object[] { Integer.valueOf(this.m), Integer.valueOf(this.n), Integer.valueOf(b.length) }));
/*      */       }
/*      */       
/* 2141 */       int r = rank();
/* 2142 */       if (this.Ur == null) {
/* 2143 */         this.Ur = (r == this.U.ncol()) ? this.U : this.U.submatrix(0, 0, this.m - 1, r - 1);
/*      */       }
/*      */       
/* 2146 */       double[] Utb = new double[this.s.length];
/* 2147 */       this.Ur.tv(b, Utb);
/* 2148 */       for (int i = 0; i < r; i++) {
/* 2149 */         Utb[i] = Utb[i] / this.s[i];
/*      */       }
/* 2151 */       return this.V.mv(Utb);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EVD
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final double[] wr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final double[] wi;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final MatrixModified Vl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final MatrixModified Vr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public EVD(double[] w, MatrixModified V) {
/* 2246 */       this.wr = w;
/* 2247 */       this.wi = null;
/* 2248 */       this.Vl = V;
/* 2249 */       this.Vr = V;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public EVD(double[] wr, double[] wi, MatrixModified Vl, MatrixModified Vr) {
/* 2261 */       this.wr = wr;
/* 2262 */       this.wi = wi;
/* 2263 */       this.Vl = Vl;
/* 2264 */       this.Vr = Vr;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MatrixModified diag() {
/* 2274 */       MatrixModified D = MatrixModified.diag(this.wr);
/*      */       
/* 2276 */       if (this.wi != null) {
/* 2277 */         int n = this.wr.length;
/* 2278 */         for (int i = 0; i < n; i++) {
/* 2279 */           if (this.wi[i] > 0.0D) {
/* 2280 */             D.set(i, i + 1, this.wi[i]);
/* 2281 */           } else if (this.wi[i] < 0.0D) {
/* 2282 */             D.set(i, i - 1, this.wi[i]);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2287 */       return D;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public EVD sort() {
/* 2296 */       int n = this.wr.length;
/* 2297 */       double[] w = new double[n];
/* 2298 */       if (this.wi != null) {
/* 2299 */         for (int i = 0; i < n; i++) {
/* 2300 */           w[i] = -(this.wr[i] * this.wr[i] + this.wi[i] * this.wi[i]);
/*      */         }
/*      */       } else {
/* 2303 */         for (int i = 0; i < n; i++) {
/* 2304 */           w[i] = -(this.wr[i] * this.wr[i]);
/*      */         }
/*      */       } 
/*      */       
/* 2308 */       int[] index = QuickSortModified.sort(w);
/* 2309 */       double[] wr2 = new double[n];
/* 2310 */       for (int j = 0; j < n; j++) {
/* 2311 */         wr2[j] = this.wr[index[j]];
/*      */       }
/*      */       
/* 2314 */       double[] wi2 = null;
/* 2315 */       if (this.wi != null) {
/* 2316 */         wi2 = new double[n];
/* 2317 */         for (int i = 0; i < n; i++) {
/* 2318 */           wi2[i] = this.wi[index[i]];
/*      */         }
/*      */       } 
/*      */       
/* 2322 */       MatrixModified Vl2 = null;
/* 2323 */       if (this.Vl != null) {
/* 2324 */         int m = this.Vl.m;
/* 2325 */         Vl2 = new MatrixModified(m, n);
/* 2326 */         for (int i = 0; i < n; i++) {
/* 2327 */           for (int k = 0; k < m; k++) {
/* 2328 */             Vl2.set(k, i, this.Vl.get(k, index[i]));
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 2333 */       MatrixModified Vr2 = null;
/* 2334 */       if (this.Vr != null) {
/* 2335 */         int m = this.Vr.m;
/* 2336 */         Vr2 = new MatrixModified(m, n);
/* 2337 */         for (int i = 0; i < n; i++) {
/* 2338 */           for (int k = 0; k < m; k++) {
/* 2339 */             Vr2.set(k, i, this.Vr.get(k, index[i]));
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 2344 */       return new EVD(wr2, wi2, Vl2, Vr2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class LU
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final MatrixModified lu;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int[] ipiv;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final int info;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LU(MatrixModified lu, int[] ipiv, int info) {
/* 2389 */       this.lu = lu;
/* 2390 */       this.ipiv = ipiv;
/* 2391 */       this.info = info;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isSingular() {
/* 2399 */       return (this.info > 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double det() {
/* 2407 */       int m = this.lu.m;
/* 2408 */       int n = this.lu.n;
/*      */       
/* 2410 */       if (m != n) {
/* 2411 */         throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", new Object[] { Integer.valueOf(m), Integer.valueOf(n) }));
/*      */       }
/*      */       
/* 2414 */       double d = 1.0D; int j;
/* 2415 */       for (j = 0; j < n; j++) {
/* 2416 */         d *= this.lu.get(j, j);
/*      */       }
/*      */       
/* 2419 */       for (j = 0; j < n; j++) {
/* 2420 */         if (j + 1 != this.ipiv[j]) {
/* 2421 */           d = -d;
/*      */         }
/*      */       } 
/*      */       
/* 2425 */       return d;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MatrixModified inverse() {
/* 2433 */       MatrixModified inv = MatrixModified.eye(this.lu.n);
/* 2434 */       solve(inv);
/* 2435 */       return inv;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] solve(double[] b) {
/* 2445 */       MatrixModified x = MatrixModified.column(b);
/* 2446 */       solve(x);
/* 2447 */       return x.A;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void solve(MatrixModified B) {
/* 2457 */       if (this.lu.m != this.lu.n) {
/* 2458 */         throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", new Object[] { Integer.valueOf(this.lu.m), Integer.valueOf(this.lu.n) }));
/*      */       }
/*      */       
/* 2461 */       if (B.m != this.lu.m) {
/* 2462 */         throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", new Object[] { Integer.valueOf(this.lu.m), Integer.valueOf(this.lu.n), Integer.valueOf(B.m), Integer.valueOf(B.n) }));
/*      */       }
/*      */       
/* 2465 */       if (this.lu.layout() != B.layout()) {
/* 2466 */         throw new IllegalArgumentException("The matrix layout is inconsistent.");
/*      */       }
/*      */       
/* 2469 */       if (this.info > 0) {
/* 2470 */         throw new RuntimeException("The matrix is singular.");
/*      */       }
/*      */       
/* 2473 */       int ret = LAPACKModified.engine.getrs(this.lu.layout(), TransposeModified.NO_TRANSPOSE, this.lu.n, B.n, this.lu.A, this.lu.ld, this.ipiv, B.A, B.ld);
/* 2474 */       if (ret != 0) {
/* 2475 */         MatrixModified.logger.error("LAPACK GETRS error code: {}", Integer.valueOf(ret));
/* 2476 */         throw new ArithmeticException("LAPACK GETRS error code: " + ret);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class Cholesky
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final MatrixModified lu;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Cholesky(MatrixModified lu) {
/* 2517 */       if (lu.nrow() != lu.ncol()) {
/* 2518 */         throw new UnsupportedOperationException("Cholesky constructor on a non-square matrix");
/*      */       }
/* 2520 */       this.lu = lu;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double det() {
/* 2528 */       int n = this.lu.n;
/* 2529 */       double d = 1.0D;
/* 2530 */       for (int i = 0; i < n; i++) {
/* 2531 */         d *= this.lu.get(i, i);
/*      */       }
/*      */       
/* 2534 */       return d * d;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double logdet() {
/* 2542 */       int n = this.lu.n;
/* 2543 */       double d = 0.0D;
/* 2544 */       for (int i = 0; i < n; i++) {
/* 2545 */         d += Math.log(this.lu.get(i, i));
/*      */       }
/*      */       
/* 2548 */       return 2.0D * d;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MatrixModified inverse() {
/* 2556 */       MatrixModified inv = MatrixModified.eye(this.lu.n);
/* 2557 */       solve(inv);
/* 2558 */       return inv;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] solve(double[] b) {
/* 2567 */       MatrixModified x = MatrixModified.column(b);
/* 2568 */       solve(x);
/* 2569 */       return x.A;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void solve(MatrixModified B) {
/* 2578 */       if (B.m != this.lu.m) {
/* 2579 */         throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", new Object[] { Integer.valueOf(this.lu.m), Integer.valueOf(this.lu.n), Integer.valueOf(B.m), Integer.valueOf(B.n) }));
/*      */       }
/*      */       
/* 2582 */       int info = LAPACKModified.engine.potrs(this.lu.layout(), this.lu.uplo, this.lu.n, B.n, this.lu.A, this.lu.ld, B.A, B.ld);
/* 2583 */       if (info != 0) {
/* 2584 */         MatrixModified.logger.error("LAPACK POTRS error code: {}", Integer.valueOf(info));
/* 2585 */         throw new ArithmeticException("LAPACK POTRS error code: " + info);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class QR
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final MatrixModified qr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final double[] tau;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public QR(MatrixModified qr, double[] tau) {
/* 2618 */       this.qr = qr;
/* 2619 */       this.tau = tau;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MatrixModified.Cholesky CholeskyOfAtA() {
/* 2627 */       int n = this.qr.n;
/* 2628 */       MatrixModified L = new MatrixModified(n, n);
/* 2629 */       for (int i = 0; i < n; i++) {
/* 2630 */         for (int j = 0; j <= i; j++) {
/* 2631 */           L.set(i, j, this.qr.get(j, i));
/*      */         }
/*      */       } 
/*      */       
/* 2635 */       L.uplo(UPLOModified.LOWER);
/* 2636 */       return new MatrixModified.Cholesky(L);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MatrixModified R() {
/* 2644 */       int n = this.qr.n;
/* 2645 */       MatrixModified R = MatrixModified.diag(this.tau);
/* 2646 */       for (int i = 0; i < n; i++) {
/* 2647 */         for (int j = i; j < n; j++) {
/* 2648 */           R.set(i, j, this.qr.get(i, j));
/*      */         }
/*      */       } 
/*      */       
/* 2652 */       return R;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MatrixModified Q() {
/* 2660 */       int m = this.qr.m;
/* 2661 */       int n = this.qr.n;
/* 2662 */       int k = Math.min(m, n);
/* 2663 */       MatrixModified Q = this.qr.clone();
/* 2664 */       int info = LAPACKModified.engine.orgqr(this.qr.layout(), m, n, k, Q.A, this.qr.ld, this.tau);
/* 2665 */       if (info != 0) {
/* 2666 */         MatrixModified.logger.error("LAPACK ORGRQ error code: {}", Integer.valueOf(info));
/* 2667 */         throw new ArithmeticException("LAPACK ORGRQ error code: " + info);
/*      */       } 
/* 2669 */       return Q;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public double[] solve(double[] b) {
/* 2679 */       if (b.length != this.qr.m) {
/* 2680 */         throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x 1", new Object[] { Integer.valueOf(this.qr.m), Integer.valueOf(this.qr.n), Integer.valueOf(b.length) }));
/*      */       }
/*      */       
/* 2683 */       MatrixModified x = MatrixModified.column(b);
/* 2684 */       solve(x);
/* 2685 */       return Arrays.copyOf(x.A, this.qr.n);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void solve(MatrixModified B) {
/* 2695 */       if (B.m != this.qr.m) {
/* 2696 */         throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", new Object[] { Integer.valueOf(this.qr.nrow()), Integer.valueOf(this.qr.nrow()), Integer.valueOf(B.nrow()), Integer.valueOf(B.ncol()) }));
/*      */       }
/*      */       
/* 2699 */       int m = this.qr.m;
/* 2700 */       int n = this.qr.n;
/* 2701 */       int k = Math.min(m, n);
/*      */       
/* 2703 */       int info = LAPACKModified.engine.ormqr(this.qr.layout(), SideModified.LEFT, TransposeModified.TRANSPOSE, B.nrow(), B.ncol(), k, this.qr.A, this.qr.ld, this.tau, B.A, B.ld);
/* 2704 */       if (info != 0) {
/* 2705 */         MatrixModified.logger.error("LAPACK ORMQR error code: {}", Integer.valueOf(info));
/* 2706 */         throw new IllegalArgumentException("LAPACK ORMQR error code: " + info);
/*      */       } 
/*      */       
/* 2709 */       info = LAPACKModified.engine.trtrs(this.qr.layout(), UPLOModified.UPPER, TransposeModified.NO_TRANSPOSE, DiagModified.NON_UNIT, this.qr.n, B.n, this.qr.A, this.qr.ld, B.A, B.ld);
/*      */       
/* 2711 */       if (info != 0) {
/* 2712 */         MatrixModified.logger.error("LAPACK TRTRS error code: {}", Integer.valueOf(info));
/* 2713 */         throw new IllegalArgumentException("LAPACK TRTRS error code: " + info);
/*      */       } 
/*      */     }
/*      */   }
/*      */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/MatrixModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */