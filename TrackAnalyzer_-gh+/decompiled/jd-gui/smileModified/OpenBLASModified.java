/*      */ package smileModified;
/*      */ 
/*      */ import java.nio.DoubleBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import org.bytedeco.javacpp.DoublePointer;
/*      */ import org.bytedeco.javacpp.IntPointer;
/*      */ import org.bytedeco.openblas.global.openblas;
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
/*      */ public class OpenBLASModified
/*      */   implements BLASModified, LAPACKModified
/*      */ {
/*      */   public double asum(int n, double[] x, int incx) {
/*   35 */     return openblas.cblas_dasum(n, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public float asum(int n, float[] x, int incx) {
/*   40 */     return openblas.cblas_sasum(n, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void axpy(int n, double alpha, double[] x, int incx, double[] y, int incy) {
/*   45 */     openblas.cblas_daxpy(n, alpha, x, incx, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void axpy(int n, float alpha, float[] x, int incx, float[] y, int incy) {
/*   50 */     openblas.cblas_saxpy(n, alpha, x, incx, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public double dot(int n, double[] x, int incx, double[] y, int incy) {
/*   55 */     return openblas.cblas_ddot(n, x, incx, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public float dot(int n, float[] x, int incx, float[] y, int incy) {
/*   60 */     return openblas.cblas_sdot(n, x, incx, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public double nrm2(int n, double[] x, int incx) {
/*   65 */     return openblas.cblas_dnrm2(n, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public float nrm2(int n, float[] x, int incx) {
/*   70 */     return openblas.cblas_snrm2(n, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void scal(int n, double alpha, double[] x, int incx) {
/*   75 */     openblas.cblas_dscal(n, alpha, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void scal(int n, float alpha, float[] x, int incx) {
/*   80 */     openblas.cblas_sscal(n, alpha, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void swap(int n, double[] x, int incx, double[] y, int incy) {
/*   85 */     openblas.cblas_dswap(n, x, incx, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void swap(int n, float[] x, int incx, float[] y, int incy) {
/*   90 */     openblas.cblas_sswap(n, x, incx, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public long iamax(int n, double[] x, int incx) {
/*   95 */     return openblas.cblas_idamax(n, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public long iamax(int n, float[] x, int incx) {
/*  100 */     return openblas.cblas_isamax(n, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gemv(LayoutModified layout, TransposeModified trans, int m, int n, double alpha, double[] A, int lda, double[] x, int incx, double beta, double[] y, int incy) {
/*  105 */     openblas.cblas_dgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gemv(LayoutModified layout, TransposeModified trans, int m, int n, double alpha, DoubleBuffer A, int lda, DoubleBuffer x, int incx, double beta, DoubleBuffer y, int incy) {
/*  110 */     openblas.cblas_dgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gemv(LayoutModified layout, TransposeModified trans, int m, int n, double alpha, DoublePointer A, int lda, DoublePointer x, int incx, double beta, DoublePointer y, int incy) {
/*  115 */     openblas.cblas_dgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gemv(LayoutModified layout, TransposeModified trans, int m, int n, float alpha, float[] A, int lda, float[] x, int incx, float beta, float[] y, int incy) {
/*  120 */     openblas.cblas_sgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gemv(LayoutModified layout, TransposeModified trans, int m, int n, float alpha, FloatBuffer A, int lda, FloatBuffer x, int incx, float beta, FloatBuffer y, int incy) {
/*  125 */     openblas.cblas_sgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void symv(LayoutModified layout, UPLOModified uplo, int n, double alpha, double[] A, int lda, double[] x, int incx, double beta, double[] y, int incy) {
/*  130 */     openblas.cblas_dsymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void symv(LayoutModified layout, UPLOModified uplo, int n, double alpha, DoubleBuffer A, int lda, DoubleBuffer x, int incx, double beta, DoubleBuffer y, int incy) {
/*  135 */     openblas.cblas_dsymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void symv(LayoutModified layout, UPLOModified uplo, int n, double alpha, DoublePointer A, int lda, DoublePointer x, int incx, double beta, DoublePointer y, int incy) {
/*  140 */     openblas.cblas_dsymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void symv(LayoutModified layout, UPLOModified uplo, int n, float alpha, float[] A, int lda, float[] x, int incx, float beta, float[] y, int incy) {
/*  145 */     openblas.cblas_ssymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void symv(LayoutModified layout, UPLOModified uplo, int n, float alpha, FloatBuffer A, int lda, FloatBuffer x, int incx, float beta, FloatBuffer y, int incy) {
/*  150 */     openblas.cblas_ssymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void spmv(LayoutModified layout, UPLOModified uplo, int n, double alpha, double[] A, double[] x, int incx, double beta, double[] y, int incy) {
/*  155 */     openblas.cblas_dspmv(layout.blas(), uplo.blas(), n, alpha, A, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void spmv(LayoutModified layout, UPLOModified uplo, int n, double alpha, DoubleBuffer A, DoubleBuffer x, int incx, double beta, DoubleBuffer y, int incy) {
/*  160 */     openblas.cblas_dspmv(layout.blas(), uplo.blas(), n, alpha, A, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void spmv(LayoutModified layout, UPLOModified uplo, int n, float alpha, float[] A, float[] x, int incx, float beta, float[] y, int incy) {
/*  165 */     openblas.cblas_sspmv(layout.blas(), uplo.blas(), n, alpha, A, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void spmv(LayoutModified layout, UPLOModified uplo, int n, float alpha, FloatBuffer A, FloatBuffer x, int incx, float beta, FloatBuffer y, int incy) {
/*  170 */     openblas.cblas_sspmv(layout.blas(), uplo.blas(), n, alpha, A, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void trmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, double[] A, int lda, double[] x, int incx) {
/*  175 */     openblas.cblas_dtrmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void trmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, DoubleBuffer A, int lda, DoubleBuffer x, int incx) {
/*  180 */     openblas.cblas_dtrmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void trmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, DoublePointer A, int lda, DoublePointer x, int incx) {
/*  185 */     openblas.cblas_dtrmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void trmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, float[] A, int lda, float[] x, int incx) {
/*  190 */     openblas.cblas_strmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void trmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, FloatBuffer A, int lda, FloatBuffer x, int incx) {
/*  195 */     openblas.cblas_strmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void tpmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, double[] A, double[] x, int incx) {
/*  200 */     openblas.cblas_dtpmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void tpmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, DoubleBuffer A, DoubleBuffer x, int incx) {
/*  205 */     openblas.cblas_dtpmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void tpmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, float[] A, float[] x, int incx) {
/*  210 */     openblas.cblas_stpmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void tpmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, FloatBuffer A, FloatBuffer x, int incx) {
/*  215 */     openblas.cblas_stpmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, x, incx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gbmv(LayoutModified layout, TransposeModified trans, int m, int n, int kl, int ku, double alpha, double[] A, int lda, double[] x, int incx, double beta, double[] y, int incy) {
/*  220 */     openblas.cblas_dgbmv(layout.blas(), trans.blas(), m, n, kl, ku, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gbmv(LayoutModified layout, TransposeModified trans, int m, int n, int kl, int ku, double alpha, DoubleBuffer A, int lda, DoubleBuffer x, int incx, double beta, DoubleBuffer y, int incy) {
/*  225 */     openblas.cblas_dgbmv(layout.blas(), trans.blas(), m, n, kl, ku, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gbmv(LayoutModified layout, TransposeModified trans, int m, int n, int kl, int ku, float alpha, float[] A, int lda, float[] x, int incx, float beta, float[] y, int incy) {
/*  230 */     openblas.cblas_sgbmv(layout.blas(), trans.blas(), m, n, kl, ku, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gbmv(LayoutModified layout, TransposeModified trans, int m, int n, int kl, int ku, float alpha, FloatBuffer A, int lda, FloatBuffer x, int incx, float beta, FloatBuffer y, int incy) {
/*  235 */     openblas.cblas_sgbmv(layout.blas(), trans.blas(), m, n, kl, ku, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void sbmv(LayoutModified layout, UPLOModified uplo, int n, int k, double alpha, double[] A, int lda, double[] x, int incx, double beta, double[] y, int incy) {
/*  240 */     openblas.cblas_dsbmv(layout.blas(), uplo.blas(), n, k, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void sbmv(LayoutModified layout, UPLOModified uplo, int n, int k, double alpha, DoubleBuffer A, int lda, DoubleBuffer x, int incx, double beta, DoubleBuffer y, int incy) {
/*  245 */     openblas.cblas_dsbmv(layout.blas(), uplo.blas(), n, k, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void sbmv(LayoutModified layout, UPLOModified uplo, int n, int k, float alpha, float[] A, int lda, float[] x, int incx, float beta, float[] y, int incy) {
/*  250 */     openblas.cblas_ssbmv(layout.blas(), uplo.blas(), n, k, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void sbmv(LayoutModified layout, UPLOModified uplo, int n, int k, float alpha, FloatBuffer A, int lda, FloatBuffer x, int incx, float beta, FloatBuffer y, int incy) {
/*  255 */     openblas.cblas_ssbmv(layout.blas(), uplo.blas(), n, k, alpha, A, lda, x, incx, beta, y, incy);
/*      */   }
/*      */ 
/*      */   
/*      */   public void ger(LayoutModified layout, int m, int n, double alpha, double[] x, int incx, double[] y, int incy, double[] A, int lda) {
/*  260 */     openblas.cblas_dger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public void ger(LayoutModified layout, int m, int n, double alpha, DoubleBuffer x, int incx, DoubleBuffer y, int incy, DoubleBuffer A, int lda) {
/*  265 */     openblas.cblas_dger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public void ger(LayoutModified layout, int m, int n, double alpha, DoublePointer x, int incx, DoublePointer y, int incy, DoublePointer A, int lda) {
/*  270 */     openblas.cblas_dger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public void ger(LayoutModified layout, int m, int n, float alpha, float[] x, int incx, float[] y, int incy, float[] A, int lda) {
/*  275 */     openblas.cblas_sger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public void ger(LayoutModified layout, int m, int n, float alpha, FloatBuffer x, int incx, FloatBuffer y, int incy, FloatBuffer A, int lda) {
/*  280 */     openblas.cblas_sger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public void syr(LayoutModified layout, UPLOModified uplo, int n, double alpha, double[] x, int incx, double[] A, int lda) {
/*  285 */     openblas.cblas_dsyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public void syr(LayoutModified layout, UPLOModified uplo, int n, double alpha, DoubleBuffer x, int incx, DoubleBuffer A, int lda) {
/*  290 */     openblas.cblas_dsyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public void syr(LayoutModified layout, UPLOModified uplo, int n, double alpha, DoublePointer x, int incx, DoublePointer A, int lda) {
/*  295 */     openblas.cblas_dsyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public void syr(LayoutModified layout, UPLOModified uplo, int n, float alpha, float[] x, int incx, float[] A, int lda) {
/*  300 */     openblas.cblas_ssyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public void syr(LayoutModified layout, UPLOModified uplo, int n, float alpha, FloatBuffer x, int incx, FloatBuffer A, int lda) {
/*  305 */     openblas.cblas_ssyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public void spr(LayoutModified layout, UPLOModified uplo, int n, double alpha, double[] x, int incx, double[] A) {
/*  310 */     openblas.cblas_dspr(layout.blas(), uplo.blas(), n, alpha, x, incx, A);
/*      */   }
/*      */ 
/*      */   
/*      */   public void spr(LayoutModified layout, UPLOModified uplo, int n, double alpha, DoubleBuffer x, int incx, DoubleBuffer A) {
/*  315 */     openblas.cblas_dspr(layout.blas(), uplo.blas(), n, alpha, x, incx, A);
/*      */   }
/*      */ 
/*      */   
/*      */   public void spr(LayoutModified layout, UPLOModified uplo, int n, float alpha, float[] x, int incx, float[] A) {
/*  320 */     openblas.cblas_sspr(layout.blas(), uplo.blas(), n, alpha, x, incx, A);
/*      */   }
/*      */ 
/*      */   
/*      */   public void spr(LayoutModified layout, UPLOModified uplo, int n, float alpha, FloatBuffer x, int incx, FloatBuffer A) {
/*  325 */     openblas.cblas_sspr(layout.blas(), uplo.blas(), n, alpha, x, incx, A);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gemm(LayoutModified layout, TransposeModified transA, TransposeModified transB, int m, int n, int k, double alpha, double[] A, int lda, double[] B, int ldb, double beta, double[] C, int ldc) {
/*  330 */     openblas.cblas_dgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gemm(LayoutModified layout, TransposeModified transA, TransposeModified transB, int m, int n, int k, double alpha, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, double beta, DoubleBuffer C, int ldc) {
/*  335 */     openblas.cblas_dgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gemm(LayoutModified layout, TransposeModified transA, TransposeModified transB, int m, int n, int k, double alpha, DoublePointer A, int lda, DoublePointer B, int ldb, double beta, DoublePointer C, int ldc) {
/*  340 */     openblas.cblas_dgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gemm(LayoutModified layout, TransposeModified transA, TransposeModified transB, int m, int n, int k, float alpha, float[] A, int lda, float[] B, int ldb, float beta, float[] C, int ldc) {
/*  345 */     openblas.cblas_sgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public void gemm(LayoutModified layout, TransposeModified transA, TransposeModified transB, int m, int n, int k, float alpha, FloatBuffer A, int lda, FloatBuffer B, int ldb, float beta, FloatBuffer C, int ldc) {
/*  350 */     openblas.cblas_sgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public void symm(LayoutModified layout, SideModified side, UPLOModified uplo, int m, int n, double alpha, double[] A, int lda, double[] B, int ldb, double beta, double[] C, int ldc) {
/*  355 */     openblas.cblas_dsymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public void symm(LayoutModified layout, SideModified side, UPLOModified uplo, int m, int n, double alpha, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, double beta, DoubleBuffer C, int ldc) {
/*  360 */     openblas.cblas_dsymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public void symm(LayoutModified layout, SideModified side, UPLOModified uplo, int m, int n, double alpha, DoublePointer A, int lda, DoublePointer B, int ldb, double beta, DoublePointer C, int ldc) {
/*  365 */     openblas.cblas_dsymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public void symm(LayoutModified layout, SideModified side, UPLOModified uplo, int m, int n, float alpha, float[] A, int lda, float[] B, int ldb, float beta, float[] C, int ldc) {
/*  370 */     openblas.cblas_ssymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public void symm(LayoutModified layout, SideModified side, UPLOModified uplo, int m, int n, float alpha, FloatBuffer A, int lda, FloatBuffer B, int ldb, float beta, FloatBuffer C, int ldc) {
/*  375 */     openblas.cblas_ssymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesv(LayoutModified layout, int n, int nrhs, double[] A, int lda, int[] ipiv, double[] B, int ldb) {
/*  380 */     return openblas.LAPACKE_dgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesv(LayoutModified layout, int n, int nrhs, DoubleBuffer A, int lda, IntBuffer ipiv, DoubleBuffer B, int ldb) {
/*  385 */     return openblas.LAPACKE_dgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesv(LayoutModified layout, int n, int nrhs, DoublePointer A, int lda, IntPointer ipiv, DoublePointer B, int ldb) {
/*  390 */     return openblas.LAPACKE_dgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesv(LayoutModified layout, int n, int nrhs, float[] A, int lda, int[] ipiv, float[] B, int ldb) {
/*  395 */     return openblas.LAPACKE_sgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesv(LayoutModified layout, int n, int nrhs, FloatBuffer A, int lda, IntBuffer ipiv, FloatBuffer B, int ldb) {
/*  400 */     return openblas.LAPACKE_sgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sysv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] A, int lda, int[] ipiv, double[] B, int ldb) {
/*  405 */     return openblas.LAPACKE_dsysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sysv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer A, int lda, IntBuffer ipiv, DoubleBuffer B, int ldb) {
/*  410 */     return openblas.LAPACKE_dsysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sysv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoublePointer A, int lda, IntPointer ipiv, DoublePointer B, int ldb) {
/*  415 */     return openblas.LAPACKE_dsysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sysv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] A, int lda, int[] ipiv, float[] B, int ldb) {
/*  420 */     return openblas.LAPACKE_ssysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sysv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer A, int lda, IntBuffer ipiv, FloatBuffer B, int ldb) {
/*  425 */     return openblas.LAPACKE_ssysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int spsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] A, int[] ipiv, double[] B, int ldb) {
/*  430 */     return openblas.LAPACKE_dspsv(layout.lapack(), uplo.lapack(), n, nrhs, A, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int spsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer A, IntBuffer ipiv, DoubleBuffer B, int ldb) {
/*  435 */     return openblas.LAPACKE_dspsv(layout.lapack(), uplo.lapack(), n, nrhs, A, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int spsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] A, int[] ipiv, float[] B, int ldb) {
/*  440 */     return openblas.LAPACKE_sspsv(layout.lapack(), uplo.lapack(), n, nrhs, A, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int spsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer A, IntBuffer ipiv, FloatBuffer B, int ldb) {
/*  445 */     return openblas.LAPACKE_sspsv(layout.lapack(), uplo.lapack(), n, nrhs, A, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int posv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] A, int lda, double[] B, int ldb) {
/*  450 */     return openblas.LAPACKE_dposv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int posv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb) {
/*  455 */     return openblas.LAPACKE_dposv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int posv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] A, int lda, float[] B, int ldb) {
/*  460 */     return openblas.LAPACKE_sposv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int posv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb) {
/*  465 */     return openblas.LAPACKE_sposv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ppsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] A, double[] B, int ldb) {
/*  470 */     return openblas.LAPACKE_dppsv(layout.lapack(), uplo.lapack(), n, nrhs, A, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ppsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer A, DoubleBuffer B, int ldb) {
/*  475 */     return openblas.LAPACKE_dppsv(layout.lapack(), uplo.lapack(), n, nrhs, A, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ppsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] A, float[] B, int ldb) {
/*  480 */     return openblas.LAPACKE_sppsv(layout.lapack(), uplo.lapack(), n, nrhs, A, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ppsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer A, FloatBuffer B, int ldb) {
/*  485 */     return openblas.LAPACKE_sppsv(layout.lapack(), uplo.lapack(), n, nrhs, A, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gbsv(LayoutModified layout, int n, int kl, int ku, int nrhs, double[] A, int lda, int[] ipiv, double[] B, int ldb) {
/*  490 */     return openblas.LAPACKE_dgbsv(layout.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gbsv(LayoutModified layout, int n, int kl, int ku, int nrhs, DoubleBuffer A, int lda, IntBuffer ipiv, DoubleBuffer B, int ldb) {
/*  495 */     return openblas.LAPACKE_dgbsv(layout.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gbsv(LayoutModified layout, int n, int kl, int ku, int nrhs, float[] A, int lda, int[] ipiv, float[] B, int ldb) {
/*  500 */     return openblas.LAPACKE_sgbsv(layout.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gbsv(LayoutModified layout, int n, int kl, int ku, int nrhs, FloatBuffer A, int lda, IntBuffer ipiv, FloatBuffer B, int ldb) {
/*  505 */     return openblas.LAPACKE_sgbsv(layout.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gels(LayoutModified layout, TransposeModified trans, int m, int n, int nrhs, double[] A, int lda, double[] B, int ldb) {
/*  510 */     return openblas.LAPACKE_dgels(layout.lapack(), trans.lapack(), m, n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gels(LayoutModified layout, TransposeModified trans, int m, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb) {
/*  515 */     return openblas.LAPACKE_dgels(layout.lapack(), trans.lapack(), m, n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gels(LayoutModified layout, TransposeModified trans, int m, int n, int nrhs, float[] A, int lda, float[] B, int ldb) {
/*  520 */     return openblas.LAPACKE_sgels(layout.lapack(), trans.lapack(), m, n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gels(LayoutModified layout, TransposeModified trans, int m, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb) {
/*  525 */     return openblas.LAPACKE_sgels(layout.lapack(), trans.lapack(), m, n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gelsy(LayoutModified layout, int m, int n, int nrhs, double[] A, int lda, double[] B, int ldb, int[] jpvt, double rcond, int[] rank) {
/*  530 */     return openblas.LAPACKE_dgelsy(layout.lapack(), m, n, nrhs, A, lda, B, ldb, jpvt, rcond, rank);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gelsy(LayoutModified layout, int m, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, IntBuffer jpvt, double rcond, IntBuffer rank) {
/*  535 */     return openblas.LAPACKE_dgelsy(layout.lapack(), m, n, nrhs, A, lda, B, ldb, jpvt, rcond, rank);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gelsy(LayoutModified layout, int m, int n, int nrhs, float[] A, int lda, float[] B, int ldb, int[] jpvt, float rcond, int[] rank) {
/*  540 */     return openblas.LAPACKE_sgelsy(layout.lapack(), m, n, nrhs, A, lda, B, ldb, jpvt, rcond, rank);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gelsy(LayoutModified layout, int m, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb, IntBuffer jpvt, float rcond, IntBuffer rank) {
/*  545 */     return openblas.LAPACKE_sgelsy(layout.lapack(), m, n, nrhs, A, lda, B, ldb, jpvt, rcond, rank);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gelss(LayoutModified layout, int m, int n, int nrhs, double[] A, int lda, double[] B, int ldb, double[] s, double rcond, int[] rank) {
/*  550 */     return openblas.LAPACKE_dgelss(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gelss(LayoutModified layout, int m, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, DoubleBuffer s, double rcond, IntBuffer rank) {
/*  555 */     return openblas.LAPACKE_dgelss(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gelss(LayoutModified layout, int m, int n, int nrhs, float[] A, int lda, float[] B, int ldb, float[] s, float rcond, int[] rank) {
/*  560 */     return openblas.LAPACKE_sgelss(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gelss(LayoutModified layout, int m, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb, FloatBuffer s, float rcond, IntBuffer rank) {
/*  565 */     return openblas.LAPACKE_sgelss(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gelsd(LayoutModified layout, int m, int n, int nrhs, double[] A, int lda, double[] B, int ldb, double[] s, double rcond, int[] rank) {
/*  570 */     return openblas.LAPACKE_dgelsd(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gelsd(LayoutModified layout, int m, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, DoubleBuffer s, double rcond, IntBuffer rank) {
/*  575 */     return openblas.LAPACKE_dgelsd(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gelsd(LayoutModified layout, int m, int n, int nrhs, float[] A, int lda, float[] B, int ldb, float[] s, float rcond, int[] rank) {
/*  580 */     return openblas.LAPACKE_sgelsd(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gelsd(LayoutModified layout, int m, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb, FloatBuffer s, float rcond, IntBuffer rank) {
/*  585 */     return openblas.LAPACKE_sgelsd(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gglse(LayoutModified layout, int m, int n, int p, double[] A, int lda, double[] B, int ldb, double[] c, double[] d, double[] x) {
/*  590 */     return openblas.LAPACKE_dgglse(layout.lapack(), m, n, p, A, lda, B, ldb, c, d, x);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gglse(LayoutModified layout, int m, int n, int p, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, DoubleBuffer c, DoubleBuffer d, DoubleBuffer x) {
/*  595 */     return openblas.LAPACKE_dgglse(layout.lapack(), m, n, p, A, lda, B, ldb, c, d, x);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gglse(LayoutModified layout, int m, int n, int p, float[] A, int lda, float[] B, int ldb, float[] c, float[] d, float[] x) {
/*  600 */     return openblas.LAPACKE_sgglse(layout.lapack(), m, n, p, A, lda, B, ldb, c, d, x);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gglse(LayoutModified layout, int m, int n, int p, FloatBuffer A, int lda, FloatBuffer B, int ldb, FloatBuffer c, FloatBuffer d, FloatBuffer x) {
/*  605 */     return openblas.LAPACKE_sgglse(layout.lapack(), m, n, p, A, lda, B, ldb, c, d, x);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ggglm(LayoutModified layout, int n, int m, int p, double[] A, int lda, double[] B, int ldb, double[] d, double[] x, double[] y) {
/*  610 */     return openblas.LAPACKE_dggglm(layout.lapack(), n, m, p, A, lda, B, ldb, d, x, y);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ggglm(LayoutModified layout, int n, int m, int p, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, DoubleBuffer d, DoubleBuffer x, DoubleBuffer y) {
/*  615 */     return openblas.LAPACKE_dggglm(layout.lapack(), n, m, p, A, lda, B, ldb, d, x, y);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ggglm(LayoutModified layout, int n, int m, int p, float[] A, int lda, float[] B, int ldb, float[] d, float[] x, float[] y) {
/*  620 */     return openblas.LAPACKE_sggglm(layout.lapack(), n, m, p, A, lda, B, ldb, d, x, y);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ggglm(LayoutModified layout, int n, int m, int p, FloatBuffer A, int lda, FloatBuffer B, int ldb, FloatBuffer d, FloatBuffer x, FloatBuffer y) {
/*  625 */     return openblas.LAPACKE_sggglm(layout.lapack(), n, m, p, A, lda, B, ldb, d, x, y);
/*      */   }
/*      */ 
/*      */   
/*      */   public int geev(LayoutModified layout, EVDJobModified jobvl, EVDJobModified jobvr, int n, double[] A, int lda, double[] wr, double[] wi, double[] Vl, int ldvl, double[] Vr, int ldvr) {
/*  630 */     return openblas.LAPACKE_dgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
/*      */   }
/*      */ 
/*      */   
/*      */   public int geev(LayoutModified layout, EVDJobModified jobvl, EVDJobModified jobvr, int n, DoubleBuffer A, int lda, DoubleBuffer wr, DoubleBuffer wi, DoubleBuffer Vl, int ldvl, DoubleBuffer Vr, int ldvr) {
/*  635 */     return openblas.LAPACKE_dgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
/*      */   }
/*      */ 
/*      */   
/*      */   public int geev(LayoutModified layout, EVDJobModified jobvl, EVDJobModified jobvr, int n, DoublePointer A, int lda, DoublePointer wr, DoublePointer wi, DoublePointer Vl, int ldvl, DoublePointer Vr, int ldvr) {
/*  640 */     return openblas.LAPACKE_dgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
/*      */   }
/*      */ 
/*      */   
/*      */   public int geev(LayoutModified layout, EVDJobModified jobvl, EVDJobModified jobvr, int n, float[] A, int lda, float[] wr, float[] wi, float[] Vl, int ldvl, float[] Vr, int ldvr) {
/*  645 */     return openblas.LAPACKE_sgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
/*      */   }
/*      */ 
/*      */   
/*      */   public int geev(LayoutModified layout, EVDJobModified jobvl, EVDJobModified jobvr, int n, FloatBuffer A, int lda, FloatBuffer wr, FloatBuffer wi, FloatBuffer Vl, int ldvl, FloatBuffer Vr, int ldvr) {
/*  650 */     return openblas.LAPACKE_sgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syev(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, double[] A, int lda, double[] w) {
/*  655 */     return openblas.LAPACKE_dsyev(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syev(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, DoubleBuffer A, int lda, DoubleBuffer w) {
/*  660 */     return openblas.LAPACKE_dsyev(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syev(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, float[] A, int lda, float[] w) {
/*  665 */     return openblas.LAPACKE_ssyev(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syev(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, FloatBuffer A, int lda, FloatBuffer w) {
/*  670 */     return openblas.LAPACKE_ssyev(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syevd(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, double[] A, int lda, double[] w) {
/*  675 */     return openblas.LAPACKE_dsyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syevd(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, DoubleBuffer A, int lda, DoubleBuffer w) {
/*  680 */     return openblas.LAPACKE_dsyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syevd(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, DoublePointer A, int lda, DoublePointer w) {
/*  685 */     return openblas.LAPACKE_dsyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syevd(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, float[] A, int lda, float[] w) {
/*  690 */     return openblas.LAPACKE_ssyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syevd(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, FloatBuffer A, int lda, FloatBuffer w) {
/*  695 */     return openblas.LAPACKE_ssyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syevr(LayoutModified layout, EVDJobModified jobz, EigenRangeModified range, UPLOModified uplo, int n, double[] A, int lda, double vl, double vu, int il, int iu, double abstol, int[] m, double[] w, double[] Z, int ldz, int[] isuppz) {
/*  700 */     return openblas.LAPACKE_dsyevr(layout.lapack(), jobz.lapack(), range.lapack(), uplo.lapack(), n, A, lda, vl, vu, il, iu, abstol, m, w, Z, ldz, isuppz);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syevr(LayoutModified layout, EVDJobModified jobz, EigenRangeModified range, UPLOModified uplo, int n, DoubleBuffer A, int lda, double vl, double vu, int il, int iu, double abstol, IntBuffer m, DoubleBuffer w, DoubleBuffer Z, int ldz, IntBuffer isuppz) {
/*  705 */     return openblas.LAPACKE_dsyevr(layout.lapack(), jobz.lapack(), range.lapack(), uplo.lapack(), n, A, lda, vl, vu, il, iu, abstol, m, w, Z, ldz, isuppz);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syevr(LayoutModified layout, EVDJobModified jobz, EigenRangeModified range, UPLOModified uplo, int n, float[] A, int lda, float vl, float vu, int il, int iu, float abstol, int[] m, float[] w, float[] Z, int ldz, int[] isuppz) {
/*  710 */     return openblas.LAPACKE_ssyevr(layout.lapack(), jobz.lapack(), range.lapack(), uplo.lapack(), n, A, lda, vl, vu, il, iu, abstol, m, w, Z, ldz, isuppz);
/*      */   }
/*      */ 
/*      */   
/*      */   public int syevr(LayoutModified layout, EVDJobModified jobz, EigenRangeModified range, UPLOModified uplo, int n, FloatBuffer A, int lda, float vl, float vu, int il, int iu, float abstol, IntBuffer m, FloatBuffer w, FloatBuffer Z, int ldz, IntBuffer isuppz) {
/*  715 */     return openblas.LAPACKE_ssyevr(layout.lapack(), jobz.lapack(), range.lapack(), uplo.lapack(), n, A, lda, vl, vu, il, iu, abstol, m, w, Z, ldz, isuppz);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesvd(LayoutModified layout, SVDJobModified jobu, SVDJobModified jobvt, int m, int n, double[] A, int lda, double[] s, double[] U, int ldu, double[] VT, int ldvt, double[] superb) {
/*  720 */     return openblas.LAPACKE_dgesvd(layout.lapack(), jobu.lapack(), jobvt.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt, superb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesvd(LayoutModified layout, SVDJobModified jobu, SVDJobModified jobvt, int m, int n, DoubleBuffer A, int lda, DoubleBuffer s, DoubleBuffer U, int ldu, DoubleBuffer VT, int ldvt, DoubleBuffer superb) {
/*  725 */     return openblas.LAPACKE_dgesvd(layout.lapack(), jobu.lapack(), jobvt.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt, superb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesvd(LayoutModified layout, SVDJobModified jobu, SVDJobModified jobvt, int m, int n, float[] A, int lda, float[] s, float[] U, int ldu, float[] VT, int ldvt, float[] superb) {
/*  730 */     return openblas.LAPACKE_sgesvd(layout.lapack(), jobu.lapack(), jobvt.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt, superb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesvd(LayoutModified layout, SVDJobModified jobu, SVDJobModified jobvt, int m, int n, FloatBuffer A, int lda, FloatBuffer s, FloatBuffer U, int ldu, FloatBuffer VT, int ldvt, FloatBuffer superb) {
/*  735 */     return openblas.LAPACKE_sgesvd(layout.lapack(), jobu.lapack(), jobvt.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt, superb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesdd(LayoutModified layout, SVDJobModified jobz, int m, int n, double[] A, int lda, double[] s, double[] U, int ldu, double[] VT, int ldvt) {
/*  740 */     return openblas.LAPACKE_dgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesdd(LayoutModified layout, SVDJobModified jobz, int m, int n, DoubleBuffer A, int lda, DoubleBuffer s, DoubleBuffer U, int ldu, DoubleBuffer VT, int ldvt) {
/*  745 */     return openblas.LAPACKE_dgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesdd(LayoutModified layout, SVDJobModified jobz, int m, int n, DoublePointer A, int lda, DoublePointer s, DoublePointer U, int ldu, DoublePointer VT, int ldvt) {
/*  750 */     return openblas.LAPACKE_dgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesdd(LayoutModified layout, SVDJobModified jobz, int m, int n, float[] A, int lda, float[] s, float[] U, int ldu, float[] VT, int ldvt) {
/*  755 */     return openblas.LAPACKE_sgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gesdd(LayoutModified layout, SVDJobModified jobz, int m, int n, FloatBuffer A, int lda, FloatBuffer s, FloatBuffer U, int ldu, FloatBuffer VT, int ldvt) {
/*  760 */     return openblas.LAPACKE_sgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrf(LayoutModified layout, int m, int n, double[] A, int lda, int[] ipiv) {
/*  765 */     return openblas.LAPACKE_dgetrf(layout.lapack(), m, n, A, lda, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrf(LayoutModified layout, int m, int n, DoubleBuffer A, int lda, IntBuffer ipiv) {
/*  770 */     return openblas.LAPACKE_dgetrf(layout.lapack(), m, n, A, lda, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrf(LayoutModified layout, int m, int n, DoublePointer A, int lda, IntPointer ipiv) {
/*  775 */     return openblas.LAPACKE_dgetrf(layout.lapack(), m, n, A, lda, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrf(LayoutModified layout, int m, int n, float[] A, int lda, int[] ipiv) {
/*  780 */     return openblas.LAPACKE_sgetrf(layout.lapack(), m, n, A, lda, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrf(LayoutModified layout, int m, int n, FloatBuffer A, int lda, IntBuffer ipiv) {
/*  785 */     return openblas.LAPACKE_sgetrf(layout.lapack(), m, n, A, lda, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrf2(LayoutModified layout, int m, int n, double[] A, int lda, int[] ipiv) {
/*  790 */     return openblas.LAPACKE_dgetrf2(layout.lapack(), m, n, A, lda, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrf2(LayoutModified layout, int m, int n, DoubleBuffer A, int lda, IntBuffer ipiv) {
/*  795 */     return openblas.LAPACKE_dgetrf2(layout.lapack(), m, n, A, lda, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrf2(LayoutModified layout, int m, int n, float[] A, int lda, int[] ipiv) {
/*  800 */     return openblas.LAPACKE_sgetrf2(layout.lapack(), m, n, A, lda, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrf2(LayoutModified layout, int m, int n, FloatBuffer A, int lda, IntBuffer ipiv) {
/*  805 */     return openblas.LAPACKE_sgetrf2(layout.lapack(), m, n, A, lda, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gbtrf(LayoutModified layout, int m, int n, int kl, int ku, double[] AB, int ldab, int[] ipiv) {
/*  810 */     return openblas.LAPACKE_dgbtrf(layout.lapack(), m, n, kl, ku, AB, ldab, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gbtrf(LayoutModified layout, int m, int n, int kl, int ku, DoubleBuffer AB, int ldab, IntBuffer ipiv) {
/*  815 */     return openblas.LAPACKE_dgbtrf(layout.lapack(), m, n, kl, ku, AB, ldab, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gbtrf(LayoutModified layout, int m, int n, int kl, int ku, float[] AB, int ldab, int[] ipiv) {
/*  820 */     return openblas.LAPACKE_sgbtrf(layout.lapack(), m, n, kl, ku, AB, ldab, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gbtrf(LayoutModified layout, int m, int n, int kl, int ku, FloatBuffer AB, int ldab, IntBuffer ipiv) {
/*  825 */     return openblas.LAPACKE_sgbtrf(layout.lapack(), m, n, kl, ku, AB, ldab, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sptrf(LayoutModified layout, UPLOModified uplo, int n, double[] AP, int[] ipiv) {
/*  830 */     return openblas.LAPACKE_dsptrf(layout.lapack(), uplo.lapack(), n, AP, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sptrf(LayoutModified layout, UPLOModified uplo, int n, DoubleBuffer AP, IntBuffer ipiv) {
/*  835 */     return openblas.LAPACKE_dsptrf(layout.lapack(), uplo.lapack(), n, AP, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sptrf(LayoutModified layout, UPLOModified uplo, int n, float[] AP, int[] ipiv) {
/*  840 */     return openblas.LAPACKE_ssptrf(layout.lapack(), uplo.lapack(), n, AP, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sptrf(LayoutModified layout, UPLOModified uplo, int n, FloatBuffer AP, IntBuffer ipiv) {
/*  845 */     return openblas.LAPACKE_ssptrf(layout.lapack(), uplo.lapack(), n, AP, ipiv);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrs(LayoutModified layout, TransposeModified trans, int n, int nrhs, double[] A, int lda, int[] ipiv, double[] B, int ldb) {
/*  850 */     return openblas.LAPACKE_dgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrs(LayoutModified layout, TransposeModified trans, int n, int nrhs, DoubleBuffer A, int lda, IntBuffer ipiv, DoubleBuffer B, int ldb) {
/*  855 */     return openblas.LAPACKE_dgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrs(LayoutModified layout, TransposeModified trans, int n, int nrhs, DoublePointer A, int lda, IntPointer ipiv, DoublePointer B, int ldb) {
/*  860 */     return openblas.LAPACKE_dgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrs(LayoutModified layout, TransposeModified trans, int n, int nrhs, float[] A, int lda, int[] ipiv, float[] B, int ldb) {
/*  865 */     return openblas.LAPACKE_sgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getrs(LayoutModified layout, TransposeModified trans, int n, int nrhs, FloatBuffer A, int lda, IntBuffer ipiv, FloatBuffer B, int ldb) {
/*  870 */     return openblas.LAPACKE_sgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gbtrs(LayoutModified layout, TransposeModified trans, int n, int kl, int ku, int nrhs, double[] A, int lda, int[] ipiv, double[] B, int ldb) {
/*  875 */     return openblas.LAPACKE_dgbtrs(layout.lapack(), trans.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gbtrs(LayoutModified layout, TransposeModified trans, int n, int kl, int ku, int nrhs, DoubleBuffer A, int lda, IntBuffer ipiv, DoubleBuffer B, int ldb) {
/*  880 */     return openblas.LAPACKE_dgbtrs(layout.lapack(), trans.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gbtrs(LayoutModified layout, TransposeModified trans, int n, int kl, int ku, int nrhs, float[] A, int lda, int[] ipiv, float[] B, int ldb) {
/*  885 */     return openblas.LAPACKE_sgbtrs(layout.lapack(), trans.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int gbtrs(LayoutModified layout, TransposeModified trans, int n, int kl, int ku, int nrhs, FloatBuffer A, int lda, IntBuffer ipiv, FloatBuffer B, int ldb) {
/*  890 */     return openblas.LAPACKE_sgbtrs(layout.lapack(), trans.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] AP, int[] ipiv, double[] B, int ldb) {
/*  895 */     return openblas.LAPACKE_dsptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer AP, IntBuffer ipiv, DoubleBuffer B, int ldb) {
/*  900 */     return openblas.LAPACKE_dsptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] AP, int[] ipiv, float[] B, int ldb) {
/*  905 */     return openblas.LAPACKE_ssptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int sptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer AP, IntBuffer ipiv, FloatBuffer B, int ldb) {
/*  910 */     return openblas.LAPACKE_ssptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, ipiv, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrf(LayoutModified layout, UPLOModified uplo, int n, double[] A, int lda) {
/*  915 */     return openblas.LAPACKE_dpotrf(layout.lapack(), uplo.lapack(), n, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrf(LayoutModified layout, UPLOModified uplo, int n, DoubleBuffer A, int lda) {
/*  920 */     return openblas.LAPACKE_dpotrf(layout.lapack(), uplo.lapack(), n, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrf(LayoutModified layout, UPLOModified uplo, int n, DoublePointer A, int lda) {
/*  925 */     return openblas.LAPACKE_dpotrf(layout.lapack(), uplo.lapack(), n, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrf(LayoutModified layout, UPLOModified uplo, int n, float[] A, int lda) {
/*  930 */     return openblas.LAPACKE_spotrf(layout.lapack(), uplo.lapack(), n, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrf(LayoutModified layout, UPLOModified uplo, int n, FloatBuffer A, int lda) {
/*  935 */     return openblas.LAPACKE_spotrf(layout.lapack(), uplo.lapack(), n, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrf2(LayoutModified layout, UPLOModified uplo, int n, double[] A, int lda) {
/*  940 */     return openblas.LAPACKE_dpotrf2(layout.lapack(), uplo.lapack(), n, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrf2(LayoutModified layout, UPLOModified uplo, int n, DoubleBuffer A, int lda) {
/*  945 */     return openblas.LAPACKE_dpotrf2(layout.lapack(), uplo.lapack(), n, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrf2(LayoutModified layout, UPLOModified uplo, int n, float[] A, int lda) {
/*  950 */     return openblas.LAPACKE_spotrf2(layout.lapack(), uplo.lapack(), n, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrf2(LayoutModified layout, UPLOModified uplo, int n, FloatBuffer A, int lda) {
/*  955 */     return openblas.LAPACKE_spotrf2(layout.lapack(), uplo.lapack(), n, A, lda);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pbtrf(LayoutModified layout, UPLOModified uplo, int n, int kd, double[] AB, int ldab) {
/*  960 */     return openblas.LAPACKE_dpbtrf(layout.lapack(), uplo.lapack(), n, kd, AB, ldab);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pbtrf(LayoutModified layout, UPLOModified uplo, int n, int kd, DoubleBuffer AB, int ldab) {
/*  965 */     return openblas.LAPACKE_dpbtrf(layout.lapack(), uplo.lapack(), n, kd, AB, ldab);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pbtrf(LayoutModified layout, UPLOModified uplo, int n, int kd, float[] AB, int ldab) {
/*  970 */     return openblas.LAPACKE_spbtrf(layout.lapack(), uplo.lapack(), n, kd, AB, ldab);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pbtrf(LayoutModified layout, UPLOModified uplo, int n, int kd, FloatBuffer AB, int ldab) {
/*  975 */     return openblas.LAPACKE_spbtrf(layout.lapack(), uplo.lapack(), n, kd, AB, ldab);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pptrf(LayoutModified layout, UPLOModified uplo, int n, double[] AP) {
/*  980 */     return openblas.LAPACKE_dpptrf(layout.lapack(), uplo.lapack(), n, AP);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pptrf(LayoutModified layout, UPLOModified uplo, int n, DoubleBuffer AP) {
/*  985 */     return openblas.LAPACKE_dpptrf(layout.lapack(), uplo.lapack(), n, AP);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pptrf(LayoutModified layout, UPLOModified uplo, int n, float[] AP) {
/*  990 */     return openblas.LAPACKE_spptrf(layout.lapack(), uplo.lapack(), n, AP);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pptrf(LayoutModified layout, UPLOModified uplo, int n, FloatBuffer AP) {
/*  995 */     return openblas.LAPACKE_spptrf(layout.lapack(), uplo.lapack(), n, AP);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] A, int lda, double[] B, int ldb) {
/* 1000 */     return openblas.LAPACKE_dpotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb) {
/* 1005 */     return openblas.LAPACKE_dpotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoublePointer A, int lda, DoublePointer B, int ldb) {
/* 1010 */     return openblas.LAPACKE_dpotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] A, int lda, float[] B, int ldb) {
/* 1015 */     return openblas.LAPACKE_spotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int potrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb) {
/* 1020 */     return openblas.LAPACKE_spotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pbtrs(LayoutModified layout, UPLOModified uplo, int n, int kd, int nrhs, double[] AB, int ldab, double[] B, int ldb) {
/* 1025 */     return openblas.LAPACKE_dpbtrs(layout.lapack(), uplo.lapack(), n, kd, nrhs, AB, ldab, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pbtrs(LayoutModified layout, UPLOModified uplo, int n, int kd, int nrhs, DoubleBuffer AB, int ldab, DoubleBuffer B, int ldb) {
/* 1030 */     return openblas.LAPACKE_dpbtrs(layout.lapack(), uplo.lapack(), n, kd, nrhs, AB, ldab, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pbtrs(LayoutModified layout, UPLOModified uplo, int n, int kd, int nrhs, float[] AB, int ldab, float[] B, int ldb) {
/* 1035 */     return openblas.LAPACKE_spbtrs(layout.lapack(), uplo.lapack(), n, kd, nrhs, AB, ldab, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pbtrs(LayoutModified layout, UPLOModified uplo, int n, int kd, int nrhs, FloatBuffer AB, int ldab, FloatBuffer B, int ldb) {
/* 1040 */     return openblas.LAPACKE_spbtrs(layout.lapack(), uplo.lapack(), n, kd, nrhs, AB, ldab, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] AP, double[] B, int ldb) {
/* 1045 */     return openblas.LAPACKE_dpptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer AP, DoubleBuffer B, int ldb) {
/* 1050 */     return openblas.LAPACKE_dpptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] AP, float[] B, int ldb) {
/* 1055 */     return openblas.LAPACKE_spptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int pptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer AP, FloatBuffer B, int ldb) {
/* 1060 */     return openblas.LAPACKE_spptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int geqrf(LayoutModified layout, int m, int n, double[] A, int lda, double[] tau) {
/* 1065 */     return openblas.LAPACKE_dgeqrf(layout.lapack(), m, n, A, lda, tau);
/*      */   }
/*      */ 
/*      */   
/*      */   public int geqrf(LayoutModified layout, int m, int n, DoubleBuffer A, int lda, DoubleBuffer tau) {
/* 1070 */     return openblas.LAPACKE_dgeqrf(layout.lapack(), m, n, A, lda, tau);
/*      */   }
/*      */ 
/*      */   
/*      */   public int geqrf(LayoutModified layout, int m, int n, DoublePointer A, int lda, DoublePointer tau) {
/* 1075 */     return openblas.LAPACKE_dgeqrf(layout.lapack(), m, n, A, lda, tau);
/*      */   }
/*      */ 
/*      */   
/*      */   public int geqrf(LayoutModified layout, int m, int n, float[] A, int lda, float[] tau) {
/* 1080 */     return openblas.LAPACKE_sgeqrf(layout.lapack(), m, n, A, lda, tau);
/*      */   }
/*      */ 
/*      */   
/*      */   public int geqrf(LayoutModified layout, int m, int n, FloatBuffer A, int lda, FloatBuffer tau) {
/* 1085 */     return openblas.LAPACKE_sgeqrf(layout.lapack(), m, n, A, lda, tau);
/*      */   }
/*      */ 
/*      */   
/*      */   public int orgqr(LayoutModified layout, int m, int n, int k, double[] A, int lda, double[] tau) {
/* 1090 */     return openblas.LAPACKE_dorgqr(layout.lapack(), m, n, k, A, lda, tau);
/*      */   }
/*      */ 
/*      */   
/*      */   public int orgqr(LayoutModified layout, int m, int n, int k, DoubleBuffer A, int lda, DoubleBuffer tau) {
/* 1095 */     return openblas.LAPACKE_dorgqr(layout.lapack(), m, n, k, A, lda, tau);
/*      */   }
/*      */ 
/*      */   
/*      */   public int orgqr(LayoutModified layout, int m, int n, int k, DoublePointer A, int lda, DoublePointer tau) {
/* 1100 */     return openblas.LAPACKE_dorgqr(layout.lapack(), m, n, k, A, lda, tau);
/*      */   }
/*      */ 
/*      */   
/*      */   public int orgqr(LayoutModified layout, int m, int n, int k, float[] A, int lda, float[] tau) {
/* 1105 */     return openblas.LAPACKE_sorgqr(layout.lapack(), m, n, k, A, lda, tau);
/*      */   }
/*      */ 
/*      */   
/*      */   public int orgqr(LayoutModified layout, int m, int n, int k, FloatBuffer A, int lda, FloatBuffer tau) {
/* 1110 */     return openblas.LAPACKE_sorgqr(layout.lapack(), m, n, k, A, lda, tau);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ormqr(LayoutModified layout, SideModified side, TransposeModified trans, int m, int n, int k, double[] A, int lda, double[] tau, double[] C, int ldc) {
/* 1115 */     return openblas.LAPACKE_dormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ormqr(LayoutModified layout, SideModified side, TransposeModified trans, int m, int n, int k, DoubleBuffer A, int lda, DoubleBuffer tau, DoubleBuffer C, int ldc) {
/* 1120 */     return openblas.LAPACKE_dormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ormqr(LayoutModified layout, SideModified side, TransposeModified trans, int m, int n, int k, DoublePointer A, int lda, DoublePointer tau, DoublePointer C, int ldc) {
/* 1125 */     return openblas.LAPACKE_dormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ormqr(LayoutModified layout, SideModified side, TransposeModified trans, int m, int n, int k, float[] A, int lda, float[] tau, float[] C, int ldc) {
/* 1130 */     return openblas.LAPACKE_sormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ormqr(LayoutModified layout, SideModified side, TransposeModified trans, int m, int n, int k, FloatBuffer A, int lda, FloatBuffer tau, FloatBuffer C, int ldc) {
/* 1135 */     return openblas.LAPACKE_sormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
/*      */   }
/*      */ 
/*      */   
/*      */   public int trtrs(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, int nrhs, double[] A, int lda, double[] B, int ldb) {
/* 1140 */     return openblas.LAPACKE_dtrtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int trtrs(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb) {
/* 1145 */     return openblas.LAPACKE_dtrtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int trtrs(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, int nrhs, DoublePointer A, int lda, DoublePointer B, int ldb) {
/* 1150 */     return openblas.LAPACKE_dtrtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int trtrs(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, int nrhs, float[] A, int lda, float[] B, int ldb) {
/* 1155 */     return openblas.LAPACKE_strtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ 
/*      */   
/*      */   public int trtrs(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb) {
/* 1160 */     return openblas.LAPACKE_strtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
/*      */   }
/*      */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/OpenBLASModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */