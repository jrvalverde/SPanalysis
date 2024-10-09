/*     */ package smileModified;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.stream.Stream;
/*     */ import java.util.stream.StreamSupport;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SparseMatrixModified
/*     */   extends IMatrixModified
/*     */   implements Iterable<SparseMatrixModified.Entry>
/*     */ {
/*  61 */   private static final Logger logger = LoggerFactory.getLogger(SparseMatrixModified.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 2L;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int m;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int n;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int[] colIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int[] rowIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double[] nonzeros;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class Entry
/*     */   {
/*     */     public final int i;
/*     */ 
/*     */ 
/*     */     
/*     */     public final int j;
/*     */ 
/*     */ 
/*     */     
/*     */     public final double x;
/*     */ 
/*     */ 
/*     */     
/*     */     public final int index;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Entry(int i, int j, int index) {
/* 110 */       this.i = i;
/* 111 */       this.j = j;
/* 112 */       this.x = SparseMatrixModified.this.nonzeros[index];
/* 113 */       this.index = index;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void update(double value) {
/* 122 */       SparseMatrixModified.this.nonzeros[this.index] = value;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 127 */       return String.format("(%d, %d):%s", new Object[] { Integer.valueOf(this.i), Integer.valueOf(this.j), StringsModified.format(this.x) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SparseMatrixModified(int m, int n, int nvals) {
/* 138 */     this.m = m;
/* 139 */     this.n = n;
/* 140 */     this.rowIndex = new int[nvals];
/* 141 */     this.colIndex = new int[n + 1];
/* 142 */     this.nonzeros = new double[nvals];
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
/*     */   public SparseMatrixModified(int m, int n, double[] nonzeros, int[] rowIndex, int[] colIndex) {
/* 154 */     this.m = m;
/* 155 */     this.n = n;
/* 156 */     this.rowIndex = rowIndex;
/* 157 */     this.colIndex = colIndex;
/* 158 */     this.nonzeros = nonzeros;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseMatrixModified(double[][] A) {
/* 166 */     this(A, 100.0D * MathExModified.EPSILON);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseMatrixModified(double[][] A, double tol) {
/* 175 */     this.m = A.length;
/* 176 */     this.n = (A[0]).length;
/*     */     
/* 178 */     int nvals = 0;
/* 179 */     for (int i = 0; i < this.m; i++) {
/* 180 */       for (int m = 0; m < this.n; m++) {
/* 181 */         if (Math.abs(A[i][m]) >= tol) {
/* 182 */           nvals++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     this.nonzeros = new double[nvals];
/* 188 */     this.rowIndex = new int[nvals];
/* 189 */     this.colIndex = new int[this.n + 1];
/* 190 */     this.colIndex[this.n] = nvals;
/*     */     
/* 192 */     int k = 0;
/* 193 */     for (int j = 0; j < this.n; j++) {
/* 194 */       this.colIndex[j] = k;
/* 195 */       for (int m = 0; m < this.m; m++) {
/* 196 */         if (Math.abs(A[m][j]) >= tol) {
/* 197 */           this.rowIndex[k] = m;
/* 198 */           this.nonzeros[k] = A[m][j];
/* 199 */           k++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SparseMatrixModified clone() {
/* 207 */     return new SparseMatrixModified(this.m, this.n, (double[])this.nonzeros.clone(), (int[])this.rowIndex.clone(), (int[])this.colIndex.clone());
/*     */   }
/*     */ 
/*     */   
/*     */   public int nrow() {
/* 212 */     return this.m;
/*     */   }
/*     */ 
/*     */   
/*     */   public int ncol() {
/* 217 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public long size() {
/* 222 */     return this.colIndex[this.n];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stream<Entry> nonzeros() {
/* 230 */     Spliterator<Entry> spliterator = Spliterators.spliterator(iterator(), size(), 1104);
/* 231 */     return StreamSupport.stream(spliterator, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stream<Entry> nonzeros(int beginColumn, int endColumn) {
/* 242 */     Spliterator<Entry> spliterator = Spliterators.spliterator(iterator(beginColumn, endColumn), (this.colIndex[endColumn] - this.colIndex[beginColumn]), 1104);
/* 243 */     return StreamSupport.stream(spliterator, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Entry> iterator() {
/* 252 */     return iterator(0, this.n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<Entry> iterator(int beginColumn, final int endColumn) {
/* 262 */     if (beginColumn < 0 || beginColumn >= this.n) {
/* 263 */       throw new IllegalArgumentException("Invalid begin column: " + beginColumn);
/*     */     }
/*     */     
/* 266 */     if (endColumn <= beginColumn || endColumn > this.n) {
/* 267 */       throw new IllegalArgumentException("Invalid end column: " + endColumn);
/*     */     }
/*     */     
/* 270 */     return new Iterator<Entry>(beginColumn)
/*     */       {
/*     */         int k;
/*     */         int j;
/*     */         
/*     */         public boolean hasNext() {
/* 276 */           return (this.k < SparseMatrixModified.this.colIndex[endColumn]);
/*     */         }
/*     */ 
/*     */         
/*     */         public SparseMatrixModified.Entry next() {
/* 281 */           int i = SparseMatrixModified.this.rowIndex[this.k];
/* 282 */           for (; this.k >= SparseMatrixModified.this.colIndex[this.j + 1]; this.j++);
/* 283 */           return new SparseMatrixModified.Entry(i, this.j, this.k++, null);
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
/*     */   public void forEachNonZero(DoubleConsumerModified consumer) {
/* 299 */     for (int j = 0; j < this.n; j++) {
/* 300 */       for (int k = this.colIndex[j]; k < this.colIndex[j + 1]; k++) {
/* 301 */         int i = this.rowIndex[k];
/* 302 */         consumer.accept(i, j, this.nonzeros[k]);
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
/*     */ 
/*     */   
/*     */   public void forEachNonZero(int beginColumn, int endColumn, DoubleConsumerModified consumer) {
/* 320 */     if (beginColumn < 0 || beginColumn >= this.n) {
/* 321 */       throw new IllegalArgumentException("Invalid begin column: " + beginColumn);
/*     */     }
/*     */     
/* 324 */     if (endColumn <= beginColumn || endColumn > this.n) {
/* 325 */       throw new IllegalArgumentException("Invalid end column: " + endColumn);
/*     */     }
/*     */     
/* 328 */     for (int j = beginColumn; j < endColumn; j++) {
/* 329 */       for (int k = this.colIndex[j]; k < this.colIndex[j + 1]; k++) {
/* 330 */         int i = this.rowIndex[k];
/* 331 */         consumer.accept(i, j, this.nonzeros[k]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int index) {
/* 342 */     return this.nonzeros[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int index, double value) {
/* 351 */     this.nonzeros[index] = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public double get(int i, int j) {
/* 356 */     if (i < 0 || i >= this.m || j < 0 || j >= this.n) {
/* 357 */       throw new IllegalArgumentException("Invalid index: row = " + i + " col = " + j);
/*     */     }
/*     */     
/* 360 */     for (int k = this.colIndex[j]; k < this.colIndex[j + 1]; k++) {
/* 361 */       if (this.rowIndex[k] == i) {
/* 362 */         return this.nonzeros[k];
/*     */       }
/*     */     } 
/*     */     
/* 366 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mv(TransposeModified trans, double alpha, double[] x, double beta, double[] y) {
/* 371 */     int k = (trans == TransposeModified.NO_TRANSPOSE) ? this.m : this.n;
/* 372 */     double[] ax = y;
/* 373 */     if (beta == 0.0D) {
/* 374 */       Arrays.fill(y, 0.0D);
/*     */     } else {
/* 376 */       ax = new double[k];
/*     */     } 
/*     */     
/* 379 */     if (trans == TransposeModified.NO_TRANSPOSE) {
/* 380 */       for (int j = 0; j < this.n; j++) {
/* 381 */         for (int i = this.colIndex[j]; i < this.colIndex[j + 1]; i++) {
/* 382 */           ax[this.rowIndex[i]] = ax[this.rowIndex[i]] + this.nonzeros[i] * x[j];
/*     */         }
/*     */       } 
/*     */     } else {
/* 386 */       for (int i = 0; i < this.n; i++) {
/* 387 */         for (int j = this.colIndex[i]; j < this.colIndex[i + 1]; j++) {
/* 388 */           ax[i] = ax[i] + this.nonzeros[j] * x[this.rowIndex[j]];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 393 */     if (beta != 0.0D || alpha != 1.0D) {
/* 394 */       for (int i = 0; i < k; i++) {
/* 395 */         y[i] = alpha * ax[i] + beta * y[i];
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mv(double[] work, int inputOffset, int outputOffset) {
/* 402 */     Arrays.fill(work, outputOffset, outputOffset + this.m, 0.0D);
/*     */     
/* 404 */     for (int j = 0; j < this.n; j++) {
/* 405 */       for (int i = this.colIndex[j]; i < this.colIndex[j + 1]; i++) {
/* 406 */         work[outputOffset + this.rowIndex[i]] = work[outputOffset + this.rowIndex[i]] + this.nonzeros[i] * work[inputOffset + j];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tv(double[] work, int inputOffset, int outputOffset) {
/* 413 */     Arrays.fill(work, outputOffset, outputOffset + this.n, 0.0D);
/*     */     
/* 415 */     for (int i = 0; i < this.n; i++) {
/* 416 */       for (int j = this.colIndex[i]; j < this.colIndex[i + 1]; j++) {
/* 417 */         work[outputOffset + i] = work[outputOffset + i] + this.nonzeros[j] * work[inputOffset + this.rowIndex[j]];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseMatrixModified transpose() {
/* 427 */     SparseMatrixModified trans = new SparseMatrixModified(this.n, this.m, this.nonzeros.length);
/*     */     
/* 429 */     int[] count = new int[this.m];
/* 430 */     for (int k = 0; k < this.n; k++) {
/* 431 */       for (int m = this.colIndex[k]; m < this.colIndex[k + 1]; m++) {
/* 432 */         int n = this.rowIndex[m];
/* 433 */         count[n] = count[n] + 1;
/*     */       } 
/*     */     } 
/*     */     
/* 437 */     for (int j = 0; j < this.m; j++) {
/* 438 */       trans.colIndex[j + 1] = trans.colIndex[j] + count[j];
/*     */     }
/*     */     
/* 441 */     Arrays.fill(count, 0);
/* 442 */     for (int i = 0; i < this.n; i++) {
/* 443 */       for (int m = this.colIndex[i]; m < this.colIndex[i + 1]; m++) {
/* 444 */         int n = this.rowIndex[m];
/* 445 */         int index = trans.colIndex[n] + count[n];
/* 446 */         trans.rowIndex[index] = i;
/* 447 */         trans.nonzeros[index] = this.nonzeros[m];
/* 448 */         count[n] = count[n] + 1;
/*     */       } 
/*     */     } 
/*     */     
/* 452 */     return trans;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseMatrixModified mm(SparseMatrixModified B) {
/* 461 */     if (this.n != B.m) {
/* 462 */       throw new IllegalArgumentException(String.format("Matrix dimensions do not match for matrix multiplication: %d x %d vs %d x %d", new Object[] { Integer.valueOf(nrow()), Integer.valueOf(ncol()), Integer.valueOf(B.nrow()), Integer.valueOf(B.ncol()) }));
/*     */     }
/*     */     
/* 465 */     int n = B.n;
/* 466 */     int anz = this.colIndex[n];
/* 467 */     int[] Bp = B.colIndex;
/* 468 */     int[] Bi = B.rowIndex;
/* 469 */     double[] Bx = B.nonzeros;
/* 470 */     int bnz = Bp[n];
/*     */     
/* 472 */     int[] w = new int[this.m];
/* 473 */     double[] abj = new double[this.m];
/*     */     
/* 475 */     int nzmax = Math.max(anz + bnz, this.m);
/*     */     
/* 477 */     SparseMatrixModified C = new SparseMatrixModified(this.m, n, nzmax);
/* 478 */     int[] Cp = C.colIndex;
/* 479 */     int[] Ci = C.rowIndex;
/* 480 */     double[] Cx = C.nonzeros;
/*     */     
/* 482 */     int nz = 0;
/* 483 */     for (int j = 0; j < n; j++) {
/* 484 */       if (nz + this.m > nzmax) {
/* 485 */         nzmax = 2 * nzmax + this.m;
/* 486 */         double[] Cx2 = new double[nzmax];
/* 487 */         int[] Ci2 = new int[nzmax];
/* 488 */         System.arraycopy(Ci, 0, Ci2, 0, nz);
/* 489 */         System.arraycopy(Cx, 0, Cx2, 0, nz);
/* 490 */         Ci = Ci2;
/* 491 */         Cx = Cx2;
/* 492 */         C = new SparseMatrixModified(this.m, n, Cx2, Ci2, Cp);
/*     */       } 
/*     */ 
/*     */       
/* 496 */       Cp[j] = nz;
/*     */       int p;
/* 498 */       for (p = Bp[j]; p < Bp[j + 1]; p++) {
/* 499 */         nz = scatter(this, Bi[p], Bx[p], w, abj, j + 1, C, nz);
/*     */       }
/*     */       
/* 502 */       for (p = Cp[j]; p < nz; p++) {
/* 503 */         Cx[p] = abj[Ci[p]];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 508 */     Cp[n] = nz;
/*     */     
/* 510 */     return C;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int scatter(SparseMatrixModified A, int j, double beta, int[] w, double[] x, int mark, SparseMatrixModified C, int nz) {
/* 517 */     int[] Ap = A.colIndex;
/* 518 */     int[] Ai = A.rowIndex;
/* 519 */     double[] Ax = A.nonzeros;
/*     */     
/* 521 */     int[] Ci = C.rowIndex;
/* 522 */     for (int p = Ap[j]; p < Ap[j + 1]; p++) {
/* 523 */       int i = Ai[p];
/* 524 */       if (w[i] < mark) {
/* 525 */         w[i] = mark;
/* 526 */         Ci[nz++] = i;
/* 527 */         x[i] = beta * Ax[p];
/*     */       } else {
/* 529 */         x[i] = x[i] + beta * Ax[p];
/*     */       } 
/*     */     } 
/*     */     
/* 533 */     return nz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseMatrixModified ata() {
/* 541 */     SparseMatrixModified AT = transpose();
/* 542 */     return AT.aat(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseMatrixModified aat() {
/* 550 */     SparseMatrixModified AT = transpose();
/* 551 */     return aat(AT);
/*     */   }
/*     */ 
/*     */   
/*     */   private SparseMatrixModified aat(SparseMatrixModified AT) {
/* 556 */     int[] done = new int[this.m];
/* 557 */     for (int i = 0; i < this.m; i++) {
/* 558 */       done[i] = -1;
/*     */     }
/*     */ 
/*     */     
/* 562 */     int nvals = 0;
/*     */     
/* 564 */     for (int j = 0; j < this.m; j++) {
/* 565 */       for (int i1 = AT.colIndex[j]; i1 < AT.colIndex[j + 1]; i1++) {
/* 566 */         int i2 = AT.rowIndex[i1];
/* 567 */         for (int l = this.colIndex[i2]; l < this.colIndex[i2 + 1]; l++) {
/* 568 */           int h = this.rowIndex[l];
/*     */           
/* 570 */           if (done[h] != j) {
/* 571 */             done[h] = j;
/* 572 */             nvals++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 578 */     SparseMatrixModified aat = new SparseMatrixModified(this.m, this.m, nvals);
/*     */     
/* 580 */     nvals = 0;
/* 581 */     for (int m = 0; m < this.m; m++) {
/* 582 */       done[m] = -1;
/*     */     }
/*     */     
/*     */     int k;
/*     */     
/* 587 */     for (k = 0; k < this.m; k++) {
/* 588 */       aat.colIndex[k] = nvals;
/* 589 */       for (int i1 = AT.colIndex[k]; i1 < AT.colIndex[k + 1]; i1++) {
/* 590 */         int i2 = AT.rowIndex[i1];
/* 591 */         for (int l = this.colIndex[i2]; l < this.colIndex[i2 + 1]; l++) {
/* 592 */           int h = this.rowIndex[l];
/* 593 */           if (done[h] != k) {
/* 594 */             done[h] = k;
/* 595 */             aat.rowIndex[nvals] = h;
/* 596 */             nvals++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 603 */     aat.colIndex[this.m] = nvals;
/*     */ 
/*     */     
/* 606 */     for (k = 0; k < this.m; k++) {
/* 607 */       if (aat.colIndex[k + 1] - aat.colIndex[k] > 1) {
/* 608 */         Arrays.sort(aat.rowIndex, aat.colIndex[k], aat.colIndex[k + 1]);
/*     */       }
/*     */     } 
/*     */     
/* 612 */     double[] temp = new double[this.m];
/* 613 */     for (int n = 0; n < this.m; n++) {
/* 614 */       int i1; for (i1 = AT.colIndex[n]; i1 < AT.colIndex[n + 1]; i1++) {
/* 615 */         int i2 = AT.rowIndex[i1];
/* 616 */         for (int l = this.colIndex[i2]; l < this.colIndex[i2 + 1]; l++) {
/* 617 */           int h = this.rowIndex[l];
/* 618 */           temp[h] = temp[h] + AT.nonzeros[i1] * this.nonzeros[l];
/*     */         } 
/*     */       } 
/*     */       
/* 622 */       for (i1 = aat.colIndex[n]; i1 < aat.colIndex[n + 1]; i1++) {
/* 623 */         int i2 = aat.rowIndex[i1];
/* 624 */         aat.nonzeros[i1] = temp[i2];
/* 625 */         temp[i2] = 0.0D;
/*     */       } 
/*     */     } 
/*     */     
/* 629 */     return aat;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] diag() {
/* 634 */     int n = Math.min(nrow(), ncol());
/* 635 */     double[] d = new double[n];
/*     */     
/* 637 */     for (int i = 0; i < n; i++) {
/* 638 */       for (int j = this.colIndex[i]; j < this.colIndex[i + 1]; j++) {
/* 639 */         if (this.rowIndex[j] == i) {
/* 640 */           d[i] = this.nonzeros[j];
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 646 */     return d;
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
/*     */   public static SparseMatrixModified harwell(Path path) throws IOException {
/* 662 */     logger.info("Reads sparse matrix file '{}'", path.toAbsolutePath());
/* 663 */     Exception exception1 = null, exception2 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 707 */       exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */     
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SparseMatrixModified rutherford(Path path) throws IOException {
/* 729 */     return harwell(path);
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
/*     */   public static SparseMatrixModified text(Path path) throws IOException {
/* 748 */     Exception exception1 = null, exception2 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 768 */       exception2 = null; if (exception1 == null) { exception1 = exception2; } else if (exception1 != exception2) { exception1.addSuppressed(exception2); }
/*     */     
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/SparseMatrixModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */