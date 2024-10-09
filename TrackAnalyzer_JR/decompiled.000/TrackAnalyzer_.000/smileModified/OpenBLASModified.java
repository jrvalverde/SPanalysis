package smileModified;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.openblas.global.openblas;

public class OpenBLASModified implements BLASModified, LAPACKModified {
   public double asum(int n, double[] x, int incx) {
      return openblas.cblas_dasum(n, x, incx);
   }

   public float asum(int n, float[] x, int incx) {
      return openblas.cblas_sasum(n, x, incx);
   }

   public void axpy(int n, double alpha, double[] x, int incx, double[] y, int incy) {
      openblas.cblas_daxpy(n, alpha, x, incx, y, incy);
   }

   public void axpy(int n, float alpha, float[] x, int incx, float[] y, int incy) {
      openblas.cblas_saxpy(n, alpha, x, incx, y, incy);
   }

   public double dot(int n, double[] x, int incx, double[] y, int incy) {
      return openblas.cblas_ddot(n, x, incx, y, incy);
   }

   public float dot(int n, float[] x, int incx, float[] y, int incy) {
      return openblas.cblas_sdot(n, x, incx, y, incy);
   }

   public double nrm2(int n, double[] x, int incx) {
      return openblas.cblas_dnrm2(n, x, incx);
   }

   public float nrm2(int n, float[] x, int incx) {
      return openblas.cblas_snrm2(n, x, incx);
   }

   public void scal(int n, double alpha, double[] x, int incx) {
      openblas.cblas_dscal(n, alpha, x, incx);
   }

   public void scal(int n, float alpha, float[] x, int incx) {
      openblas.cblas_sscal(n, alpha, x, incx);
   }

   public void swap(int n, double[] x, int incx, double[] y, int incy) {
      openblas.cblas_dswap(n, x, incx, y, incy);
   }

   public void swap(int n, float[] x, int incx, float[] y, int incy) {
      openblas.cblas_sswap(n, x, incx, y, incy);
   }

   public long iamax(int n, double[] x, int incx) {
      return openblas.cblas_idamax(n, x, incx);
   }

   public long iamax(int n, float[] x, int incx) {
      return openblas.cblas_isamax(n, x, incx);
   }

   public void gemv(LayoutModified layout, TransposeModified trans, int m, int n, double alpha, double[] A, int lda, double[] x, int incx, double beta, double[] y, int incy) {
      openblas.cblas_dgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void gemv(LayoutModified layout, TransposeModified trans, int m, int n, double alpha, DoubleBuffer A, int lda, DoubleBuffer x, int incx, double beta, DoubleBuffer y, int incy) {
      openblas.cblas_dgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void gemv(LayoutModified layout, TransposeModified trans, int m, int n, double alpha, DoublePointer A, int lda, DoublePointer x, int incx, double beta, DoublePointer y, int incy) {
      openblas.cblas_dgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void gemv(LayoutModified layout, TransposeModified trans, int m, int n, float alpha, float[] A, int lda, float[] x, int incx, float beta, float[] y, int incy) {
      openblas.cblas_sgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void gemv(LayoutModified layout, TransposeModified trans, int m, int n, float alpha, FloatBuffer A, int lda, FloatBuffer x, int incx, float beta, FloatBuffer y, int incy) {
      openblas.cblas_sgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void symv(LayoutModified layout, UPLOModified uplo, int n, double alpha, double[] A, int lda, double[] x, int incx, double beta, double[] y, int incy) {
      openblas.cblas_dsymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void symv(LayoutModified layout, UPLOModified uplo, int n, double alpha, DoubleBuffer A, int lda, DoubleBuffer x, int incx, double beta, DoubleBuffer y, int incy) {
      openblas.cblas_dsymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void symv(LayoutModified layout, UPLOModified uplo, int n, double alpha, DoublePointer A, int lda, DoublePointer x, int incx, double beta, DoublePointer y, int incy) {
      openblas.cblas_dsymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void symv(LayoutModified layout, UPLOModified uplo, int n, float alpha, float[] A, int lda, float[] x, int incx, float beta, float[] y, int incy) {
      openblas.cblas_ssymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void symv(LayoutModified layout, UPLOModified uplo, int n, float alpha, FloatBuffer A, int lda, FloatBuffer x, int incx, float beta, FloatBuffer y, int incy) {
      openblas.cblas_ssymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void spmv(LayoutModified layout, UPLOModified uplo, int n, double alpha, double[] A, double[] x, int incx, double beta, double[] y, int incy) {
      openblas.cblas_dspmv(layout.blas(), uplo.blas(), n, alpha, A, x, incx, beta, y, incy);
   }

   public void spmv(LayoutModified layout, UPLOModified uplo, int n, double alpha, DoubleBuffer A, DoubleBuffer x, int incx, double beta, DoubleBuffer y, int incy) {
      openblas.cblas_dspmv(layout.blas(), uplo.blas(), n, alpha, A, x, incx, beta, y, incy);
   }

   public void spmv(LayoutModified layout, UPLOModified uplo, int n, float alpha, float[] A, float[] x, int incx, float beta, float[] y, int incy) {
      openblas.cblas_sspmv(layout.blas(), uplo.blas(), n, alpha, A, x, incx, beta, y, incy);
   }

   public void spmv(LayoutModified layout, UPLOModified uplo, int n, float alpha, FloatBuffer A, FloatBuffer x, int incx, float beta, FloatBuffer y, int incy) {
      openblas.cblas_sspmv(layout.blas(), uplo.blas(), n, alpha, A, x, incx, beta, y, incy);
   }

   public void trmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, double[] A, int lda, double[] x, int incx) {
      openblas.cblas_dtrmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
   }

   public void trmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, DoubleBuffer A, int lda, DoubleBuffer x, int incx) {
      openblas.cblas_dtrmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
   }

   public void trmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, DoublePointer A, int lda, DoublePointer x, int incx) {
      openblas.cblas_dtrmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
   }

   public void trmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, float[] A, int lda, float[] x, int incx) {
      openblas.cblas_strmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
   }

   public void trmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, FloatBuffer A, int lda, FloatBuffer x, int incx) {
      openblas.cblas_strmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
   }

   public void tpmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, double[] A, double[] x, int incx) {
      openblas.cblas_dtpmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, x, incx);
   }

   public void tpmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, DoubleBuffer A, DoubleBuffer x, int incx) {
      openblas.cblas_dtpmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, x, incx);
   }

   public void tpmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, float[] A, float[] x, int incx) {
      openblas.cblas_stpmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, x, incx);
   }

   public void tpmv(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, FloatBuffer A, FloatBuffer x, int incx) {
      openblas.cblas_stpmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, x, incx);
   }

   public void gbmv(LayoutModified layout, TransposeModified trans, int m, int n, int kl, int ku, double alpha, double[] A, int lda, double[] x, int incx, double beta, double[] y, int incy) {
      openblas.cblas_dgbmv(layout.blas(), trans.blas(), m, n, kl, ku, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void gbmv(LayoutModified layout, TransposeModified trans, int m, int n, int kl, int ku, double alpha, DoubleBuffer A, int lda, DoubleBuffer x, int incx, double beta, DoubleBuffer y, int incy) {
      openblas.cblas_dgbmv(layout.blas(), trans.blas(), m, n, kl, ku, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void gbmv(LayoutModified layout, TransposeModified trans, int m, int n, int kl, int ku, float alpha, float[] A, int lda, float[] x, int incx, float beta, float[] y, int incy) {
      openblas.cblas_sgbmv(layout.blas(), trans.blas(), m, n, kl, ku, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void gbmv(LayoutModified layout, TransposeModified trans, int m, int n, int kl, int ku, float alpha, FloatBuffer A, int lda, FloatBuffer x, int incx, float beta, FloatBuffer y, int incy) {
      openblas.cblas_sgbmv(layout.blas(), trans.blas(), m, n, kl, ku, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void sbmv(LayoutModified layout, UPLOModified uplo, int n, int k, double alpha, double[] A, int lda, double[] x, int incx, double beta, double[] y, int incy) {
      openblas.cblas_dsbmv(layout.blas(), uplo.blas(), n, k, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void sbmv(LayoutModified layout, UPLOModified uplo, int n, int k, double alpha, DoubleBuffer A, int lda, DoubleBuffer x, int incx, double beta, DoubleBuffer y, int incy) {
      openblas.cblas_dsbmv(layout.blas(), uplo.blas(), n, k, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void sbmv(LayoutModified layout, UPLOModified uplo, int n, int k, float alpha, float[] A, int lda, float[] x, int incx, float beta, float[] y, int incy) {
      openblas.cblas_ssbmv(layout.blas(), uplo.blas(), n, k, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void sbmv(LayoutModified layout, UPLOModified uplo, int n, int k, float alpha, FloatBuffer A, int lda, FloatBuffer x, int incx, float beta, FloatBuffer y, int incy) {
      openblas.cblas_ssbmv(layout.blas(), uplo.blas(), n, k, alpha, A, lda, x, incx, beta, y, incy);
   }

   public void ger(LayoutModified layout, int m, int n, double alpha, double[] x, int incx, double[] y, int incy, double[] A, int lda) {
      openblas.cblas_dger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
   }

   public void ger(LayoutModified layout, int m, int n, double alpha, DoubleBuffer x, int incx, DoubleBuffer y, int incy, DoubleBuffer A, int lda) {
      openblas.cblas_dger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
   }

   public void ger(LayoutModified layout, int m, int n, double alpha, DoublePointer x, int incx, DoublePointer y, int incy, DoublePointer A, int lda) {
      openblas.cblas_dger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
   }

   public void ger(LayoutModified layout, int m, int n, float alpha, float[] x, int incx, float[] y, int incy, float[] A, int lda) {
      openblas.cblas_sger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
   }

   public void ger(LayoutModified layout, int m, int n, float alpha, FloatBuffer x, int incx, FloatBuffer y, int incy, FloatBuffer A, int lda) {
      openblas.cblas_sger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
   }

   public void syr(LayoutModified layout, UPLOModified uplo, int n, double alpha, double[] x, int incx, double[] A, int lda) {
      openblas.cblas_dsyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
   }

   public void syr(LayoutModified layout, UPLOModified uplo, int n, double alpha, DoubleBuffer x, int incx, DoubleBuffer A, int lda) {
      openblas.cblas_dsyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
   }

   public void syr(LayoutModified layout, UPLOModified uplo, int n, double alpha, DoublePointer x, int incx, DoublePointer A, int lda) {
      openblas.cblas_dsyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
   }

   public void syr(LayoutModified layout, UPLOModified uplo, int n, float alpha, float[] x, int incx, float[] A, int lda) {
      openblas.cblas_ssyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
   }

   public void syr(LayoutModified layout, UPLOModified uplo, int n, float alpha, FloatBuffer x, int incx, FloatBuffer A, int lda) {
      openblas.cblas_ssyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
   }

   public void spr(LayoutModified layout, UPLOModified uplo, int n, double alpha, double[] x, int incx, double[] A) {
      openblas.cblas_dspr(layout.blas(), uplo.blas(), n, alpha, x, incx, A);
   }

   public void spr(LayoutModified layout, UPLOModified uplo, int n, double alpha, DoubleBuffer x, int incx, DoubleBuffer A) {
      openblas.cblas_dspr(layout.blas(), uplo.blas(), n, alpha, x, incx, A);
   }

   public void spr(LayoutModified layout, UPLOModified uplo, int n, float alpha, float[] x, int incx, float[] A) {
      openblas.cblas_sspr(layout.blas(), uplo.blas(), n, alpha, x, incx, A);
   }

   public void spr(LayoutModified layout, UPLOModified uplo, int n, float alpha, FloatBuffer x, int incx, FloatBuffer A) {
      openblas.cblas_sspr(layout.blas(), uplo.blas(), n, alpha, x, incx, A);
   }

   public void gemm(LayoutModified layout, TransposeModified transA, TransposeModified transB, int m, int n, int k, double alpha, double[] A, int lda, double[] B, int ldb, double beta, double[] C, int ldc) {
      openblas.cblas_dgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
   }

   public void gemm(LayoutModified layout, TransposeModified transA, TransposeModified transB, int m, int n, int k, double alpha, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, double beta, DoubleBuffer C, int ldc) {
      openblas.cblas_dgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
   }

   public void gemm(LayoutModified layout, TransposeModified transA, TransposeModified transB, int m, int n, int k, double alpha, DoublePointer A, int lda, DoublePointer B, int ldb, double beta, DoublePointer C, int ldc) {
      openblas.cblas_dgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
   }

   public void gemm(LayoutModified layout, TransposeModified transA, TransposeModified transB, int m, int n, int k, float alpha, float[] A, int lda, float[] B, int ldb, float beta, float[] C, int ldc) {
      openblas.cblas_sgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
   }

   public void gemm(LayoutModified layout, TransposeModified transA, TransposeModified transB, int m, int n, int k, float alpha, FloatBuffer A, int lda, FloatBuffer B, int ldb, float beta, FloatBuffer C, int ldc) {
      openblas.cblas_sgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
   }

   public void symm(LayoutModified layout, SideModified side, UPLOModified uplo, int m, int n, double alpha, double[] A, int lda, double[] B, int ldb, double beta, double[] C, int ldc) {
      openblas.cblas_dsymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
   }

   public void symm(LayoutModified layout, SideModified side, UPLOModified uplo, int m, int n, double alpha, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, double beta, DoubleBuffer C, int ldc) {
      openblas.cblas_dsymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
   }

   public void symm(LayoutModified layout, SideModified side, UPLOModified uplo, int m, int n, double alpha, DoublePointer A, int lda, DoublePointer B, int ldb, double beta, DoublePointer C, int ldc) {
      openblas.cblas_dsymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
   }

   public void symm(LayoutModified layout, SideModified side, UPLOModified uplo, int m, int n, float alpha, float[] A, int lda, float[] B, int ldb, float beta, float[] C, int ldc) {
      openblas.cblas_ssymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
   }

   public void symm(LayoutModified layout, SideModified side, UPLOModified uplo, int m, int n, float alpha, FloatBuffer A, int lda, FloatBuffer B, int ldb, float beta, FloatBuffer C, int ldc) {
      openblas.cblas_ssymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
   }

   public int gesv(LayoutModified layout, int n, int nrhs, double[] A, int lda, int[] ipiv, double[] B, int ldb) {
      return openblas.LAPACKE_dgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int gesv(LayoutModified layout, int n, int nrhs, DoubleBuffer A, int lda, IntBuffer ipiv, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int gesv(LayoutModified layout, int n, int nrhs, DoublePointer A, int lda, IntPointer ipiv, DoublePointer B, int ldb) {
      return openblas.LAPACKE_dgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int gesv(LayoutModified layout, int n, int nrhs, float[] A, int lda, int[] ipiv, float[] B, int ldb) {
      return openblas.LAPACKE_sgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int gesv(LayoutModified layout, int n, int nrhs, FloatBuffer A, int lda, IntBuffer ipiv, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_sgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int sysv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] A, int lda, int[] ipiv, double[] B, int ldb) {
      return openblas.LAPACKE_dsysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int sysv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer A, int lda, IntBuffer ipiv, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dsysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int sysv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoublePointer A, int lda, IntPointer ipiv, DoublePointer B, int ldb) {
      return openblas.LAPACKE_dsysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int sysv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] A, int lda, int[] ipiv, float[] B, int ldb) {
      return openblas.LAPACKE_ssysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int sysv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer A, int lda, IntBuffer ipiv, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_ssysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int spsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] A, int[] ipiv, double[] B, int ldb) {
      return openblas.LAPACKE_dspsv(layout.lapack(), uplo.lapack(), n, nrhs, A, ipiv, B, ldb);
   }

   public int spsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer A, IntBuffer ipiv, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dspsv(layout.lapack(), uplo.lapack(), n, nrhs, A, ipiv, B, ldb);
   }

   public int spsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] A, int[] ipiv, float[] B, int ldb) {
      return openblas.LAPACKE_sspsv(layout.lapack(), uplo.lapack(), n, nrhs, A, ipiv, B, ldb);
   }

   public int spsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer A, IntBuffer ipiv, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_sspsv(layout.lapack(), uplo.lapack(), n, nrhs, A, ipiv, B, ldb);
   }

   public int posv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] A, int lda, double[] B, int ldb) {
      return openblas.LAPACKE_dposv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int posv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dposv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int posv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] A, int lda, float[] B, int ldb) {
      return openblas.LAPACKE_sposv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int posv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_sposv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int ppsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] A, double[] B, int ldb) {
      return openblas.LAPACKE_dppsv(layout.lapack(), uplo.lapack(), n, nrhs, A, B, ldb);
   }

   public int ppsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer A, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dppsv(layout.lapack(), uplo.lapack(), n, nrhs, A, B, ldb);
   }

   public int ppsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] A, float[] B, int ldb) {
      return openblas.LAPACKE_sppsv(layout.lapack(), uplo.lapack(), n, nrhs, A, B, ldb);
   }

   public int ppsv(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer A, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_sppsv(layout.lapack(), uplo.lapack(), n, nrhs, A, B, ldb);
   }

   public int gbsv(LayoutModified layout, int n, int kl, int ku, int nrhs, double[] A, int lda, int[] ipiv, double[] B, int ldb) {
      return openblas.LAPACKE_dgbsv(layout.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
   }

   public int gbsv(LayoutModified layout, int n, int kl, int ku, int nrhs, DoubleBuffer A, int lda, IntBuffer ipiv, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dgbsv(layout.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
   }

   public int gbsv(LayoutModified layout, int n, int kl, int ku, int nrhs, float[] A, int lda, int[] ipiv, float[] B, int ldb) {
      return openblas.LAPACKE_sgbsv(layout.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
   }

   public int gbsv(LayoutModified layout, int n, int kl, int ku, int nrhs, FloatBuffer A, int lda, IntBuffer ipiv, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_sgbsv(layout.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
   }

   public int gels(LayoutModified layout, TransposeModified trans, int m, int n, int nrhs, double[] A, int lda, double[] B, int ldb) {
      return openblas.LAPACKE_dgels(layout.lapack(), trans.lapack(), m, n, nrhs, A, lda, B, ldb);
   }

   public int gels(LayoutModified layout, TransposeModified trans, int m, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dgels(layout.lapack(), trans.lapack(), m, n, nrhs, A, lda, B, ldb);
   }

   public int gels(LayoutModified layout, TransposeModified trans, int m, int n, int nrhs, float[] A, int lda, float[] B, int ldb) {
      return openblas.LAPACKE_sgels(layout.lapack(), trans.lapack(), m, n, nrhs, A, lda, B, ldb);
   }

   public int gels(LayoutModified layout, TransposeModified trans, int m, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_sgels(layout.lapack(), trans.lapack(), m, n, nrhs, A, lda, B, ldb);
   }

   public int gelsy(LayoutModified layout, int m, int n, int nrhs, double[] A, int lda, double[] B, int ldb, int[] jpvt, double rcond, int[] rank) {
      return openblas.LAPACKE_dgelsy(layout.lapack(), m, n, nrhs, A, lda, B, ldb, jpvt, rcond, rank);
   }

   public int gelsy(LayoutModified layout, int m, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, IntBuffer jpvt, double rcond, IntBuffer rank) {
      return openblas.LAPACKE_dgelsy(layout.lapack(), m, n, nrhs, A, lda, B, ldb, jpvt, rcond, rank);
   }

   public int gelsy(LayoutModified layout, int m, int n, int nrhs, float[] A, int lda, float[] B, int ldb, int[] jpvt, float rcond, int[] rank) {
      return openblas.LAPACKE_sgelsy(layout.lapack(), m, n, nrhs, A, lda, B, ldb, jpvt, rcond, rank);
   }

   public int gelsy(LayoutModified layout, int m, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb, IntBuffer jpvt, float rcond, IntBuffer rank) {
      return openblas.LAPACKE_sgelsy(layout.lapack(), m, n, nrhs, A, lda, B, ldb, jpvt, rcond, rank);
   }

   public int gelss(LayoutModified layout, int m, int n, int nrhs, double[] A, int lda, double[] B, int ldb, double[] s, double rcond, int[] rank) {
      return openblas.LAPACKE_dgelss(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
   }

   public int gelss(LayoutModified layout, int m, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, DoubleBuffer s, double rcond, IntBuffer rank) {
      return openblas.LAPACKE_dgelss(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
   }

   public int gelss(LayoutModified layout, int m, int n, int nrhs, float[] A, int lda, float[] B, int ldb, float[] s, float rcond, int[] rank) {
      return openblas.LAPACKE_sgelss(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
   }

   public int gelss(LayoutModified layout, int m, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb, FloatBuffer s, float rcond, IntBuffer rank) {
      return openblas.LAPACKE_sgelss(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
   }

   public int gelsd(LayoutModified layout, int m, int n, int nrhs, double[] A, int lda, double[] B, int ldb, double[] s, double rcond, int[] rank) {
      return openblas.LAPACKE_dgelsd(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
   }

   public int gelsd(LayoutModified layout, int m, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, DoubleBuffer s, double rcond, IntBuffer rank) {
      return openblas.LAPACKE_dgelsd(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
   }

   public int gelsd(LayoutModified layout, int m, int n, int nrhs, float[] A, int lda, float[] B, int ldb, float[] s, float rcond, int[] rank) {
      return openblas.LAPACKE_sgelsd(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
   }

   public int gelsd(LayoutModified layout, int m, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb, FloatBuffer s, float rcond, IntBuffer rank) {
      return openblas.LAPACKE_sgelsd(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
   }

   public int gglse(LayoutModified layout, int m, int n, int p, double[] A, int lda, double[] B, int ldb, double[] c, double[] d, double[] x) {
      return openblas.LAPACKE_dgglse(layout.lapack(), m, n, p, A, lda, B, ldb, c, d, x);
   }

   public int gglse(LayoutModified layout, int m, int n, int p, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, DoubleBuffer c, DoubleBuffer d, DoubleBuffer x) {
      return openblas.LAPACKE_dgglse(layout.lapack(), m, n, p, A, lda, B, ldb, c, d, x);
   }

   public int gglse(LayoutModified layout, int m, int n, int p, float[] A, int lda, float[] B, int ldb, float[] c, float[] d, float[] x) {
      return openblas.LAPACKE_sgglse(layout.lapack(), m, n, p, A, lda, B, ldb, c, d, x);
   }

   public int gglse(LayoutModified layout, int m, int n, int p, FloatBuffer A, int lda, FloatBuffer B, int ldb, FloatBuffer c, FloatBuffer d, FloatBuffer x) {
      return openblas.LAPACKE_sgglse(layout.lapack(), m, n, p, A, lda, B, ldb, c, d, x);
   }

   public int ggglm(LayoutModified layout, int n, int m, int p, double[] A, int lda, double[] B, int ldb, double[] d, double[] x, double[] y) {
      return openblas.LAPACKE_dggglm(layout.lapack(), n, m, p, A, lda, B, ldb, d, x, y);
   }

   public int ggglm(LayoutModified layout, int n, int m, int p, DoubleBuffer A, int lda, DoubleBuffer B, int ldb, DoubleBuffer d, DoubleBuffer x, DoubleBuffer y) {
      return openblas.LAPACKE_dggglm(layout.lapack(), n, m, p, A, lda, B, ldb, d, x, y);
   }

   public int ggglm(LayoutModified layout, int n, int m, int p, float[] A, int lda, float[] B, int ldb, float[] d, float[] x, float[] y) {
      return openblas.LAPACKE_sggglm(layout.lapack(), n, m, p, A, lda, B, ldb, d, x, y);
   }

   public int ggglm(LayoutModified layout, int n, int m, int p, FloatBuffer A, int lda, FloatBuffer B, int ldb, FloatBuffer d, FloatBuffer x, FloatBuffer y) {
      return openblas.LAPACKE_sggglm(layout.lapack(), n, m, p, A, lda, B, ldb, d, x, y);
   }

   public int geev(LayoutModified layout, EVDJobModified jobvl, EVDJobModified jobvr, int n, double[] A, int lda, double[] wr, double[] wi, double[] Vl, int ldvl, double[] Vr, int ldvr) {
      return openblas.LAPACKE_dgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
   }

   public int geev(LayoutModified layout, EVDJobModified jobvl, EVDJobModified jobvr, int n, DoubleBuffer A, int lda, DoubleBuffer wr, DoubleBuffer wi, DoubleBuffer Vl, int ldvl, DoubleBuffer Vr, int ldvr) {
      return openblas.LAPACKE_dgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
   }

   public int geev(LayoutModified layout, EVDJobModified jobvl, EVDJobModified jobvr, int n, DoublePointer A, int lda, DoublePointer wr, DoublePointer wi, DoublePointer Vl, int ldvl, DoublePointer Vr, int ldvr) {
      return openblas.LAPACKE_dgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
   }

   public int geev(LayoutModified layout, EVDJobModified jobvl, EVDJobModified jobvr, int n, float[] A, int lda, float[] wr, float[] wi, float[] Vl, int ldvl, float[] Vr, int ldvr) {
      return openblas.LAPACKE_sgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
   }

   public int geev(LayoutModified layout, EVDJobModified jobvl, EVDJobModified jobvr, int n, FloatBuffer A, int lda, FloatBuffer wr, FloatBuffer wi, FloatBuffer Vl, int ldvl, FloatBuffer Vr, int ldvr) {
      return openblas.LAPACKE_sgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
   }

   public int syev(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, double[] A, int lda, double[] w) {
      return openblas.LAPACKE_dsyev(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
   }

   public int syev(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, DoubleBuffer A, int lda, DoubleBuffer w) {
      return openblas.LAPACKE_dsyev(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
   }

   public int syev(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, float[] A, int lda, float[] w) {
      return openblas.LAPACKE_ssyev(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
   }

   public int syev(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, FloatBuffer A, int lda, FloatBuffer w) {
      return openblas.LAPACKE_ssyev(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
   }

   public int syevd(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, double[] A, int lda, double[] w) {
      return openblas.LAPACKE_dsyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
   }

   public int syevd(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, DoubleBuffer A, int lda, DoubleBuffer w) {
      return openblas.LAPACKE_dsyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
   }

   public int syevd(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, DoublePointer A, int lda, DoublePointer w) {
      return openblas.LAPACKE_dsyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
   }

   public int syevd(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, float[] A, int lda, float[] w) {
      return openblas.LAPACKE_ssyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
   }

   public int syevd(LayoutModified layout, EVDJobModified jobz, UPLOModified uplo, int n, FloatBuffer A, int lda, FloatBuffer w) {
      return openblas.LAPACKE_ssyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
   }

   public int syevr(LayoutModified layout, EVDJobModified jobz, EigenRangeModified range, UPLOModified uplo, int n, double[] A, int lda, double vl, double vu, int il, int iu, double abstol, int[] m, double[] w, double[] Z, int ldz, int[] isuppz) {
      return openblas.LAPACKE_dsyevr(layout.lapack(), jobz.lapack(), range.lapack(), uplo.lapack(), n, A, lda, vl, vu, il, iu, abstol, m, w, Z, ldz, isuppz);
   }

   public int syevr(LayoutModified layout, EVDJobModified jobz, EigenRangeModified range, UPLOModified uplo, int n, DoubleBuffer A, int lda, double vl, double vu, int il, int iu, double abstol, IntBuffer m, DoubleBuffer w, DoubleBuffer Z, int ldz, IntBuffer isuppz) {
      return openblas.LAPACKE_dsyevr(layout.lapack(), jobz.lapack(), range.lapack(), uplo.lapack(), n, A, lda, vl, vu, il, iu, abstol, m, w, Z, ldz, isuppz);
   }

   public int syevr(LayoutModified layout, EVDJobModified jobz, EigenRangeModified range, UPLOModified uplo, int n, float[] A, int lda, float vl, float vu, int il, int iu, float abstol, int[] m, float[] w, float[] Z, int ldz, int[] isuppz) {
      return openblas.LAPACKE_ssyevr(layout.lapack(), jobz.lapack(), range.lapack(), uplo.lapack(), n, A, lda, vl, vu, il, iu, abstol, m, w, Z, ldz, isuppz);
   }

   public int syevr(LayoutModified layout, EVDJobModified jobz, EigenRangeModified range, UPLOModified uplo, int n, FloatBuffer A, int lda, float vl, float vu, int il, int iu, float abstol, IntBuffer m, FloatBuffer w, FloatBuffer Z, int ldz, IntBuffer isuppz) {
      return openblas.LAPACKE_ssyevr(layout.lapack(), jobz.lapack(), range.lapack(), uplo.lapack(), n, A, lda, vl, vu, il, iu, abstol, m, w, Z, ldz, isuppz);
   }

   public int gesvd(LayoutModified layout, SVDJobModified jobu, SVDJobModified jobvt, int m, int n, double[] A, int lda, double[] s, double[] U, int ldu, double[] VT, int ldvt, double[] superb) {
      return openblas.LAPACKE_dgesvd(layout.lapack(), jobu.lapack(), jobvt.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt, superb);
   }

   public int gesvd(LayoutModified layout, SVDJobModified jobu, SVDJobModified jobvt, int m, int n, DoubleBuffer A, int lda, DoubleBuffer s, DoubleBuffer U, int ldu, DoubleBuffer VT, int ldvt, DoubleBuffer superb) {
      return openblas.LAPACKE_dgesvd(layout.lapack(), jobu.lapack(), jobvt.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt, superb);
   }

   public int gesvd(LayoutModified layout, SVDJobModified jobu, SVDJobModified jobvt, int m, int n, float[] A, int lda, float[] s, float[] U, int ldu, float[] VT, int ldvt, float[] superb) {
      return openblas.LAPACKE_sgesvd(layout.lapack(), jobu.lapack(), jobvt.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt, superb);
   }

   public int gesvd(LayoutModified layout, SVDJobModified jobu, SVDJobModified jobvt, int m, int n, FloatBuffer A, int lda, FloatBuffer s, FloatBuffer U, int ldu, FloatBuffer VT, int ldvt, FloatBuffer superb) {
      return openblas.LAPACKE_sgesvd(layout.lapack(), jobu.lapack(), jobvt.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt, superb);
   }

   public int gesdd(LayoutModified layout, SVDJobModified jobz, int m, int n, double[] A, int lda, double[] s, double[] U, int ldu, double[] VT, int ldvt) {
      return openblas.LAPACKE_dgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
   }

   public int gesdd(LayoutModified layout, SVDJobModified jobz, int m, int n, DoubleBuffer A, int lda, DoubleBuffer s, DoubleBuffer U, int ldu, DoubleBuffer VT, int ldvt) {
      return openblas.LAPACKE_dgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
   }

   public int gesdd(LayoutModified layout, SVDJobModified jobz, int m, int n, DoublePointer A, int lda, DoublePointer s, DoublePointer U, int ldu, DoublePointer VT, int ldvt) {
      return openblas.LAPACKE_dgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
   }

   public int gesdd(LayoutModified layout, SVDJobModified jobz, int m, int n, float[] A, int lda, float[] s, float[] U, int ldu, float[] VT, int ldvt) {
      return openblas.LAPACKE_sgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
   }

   public int gesdd(LayoutModified layout, SVDJobModified jobz, int m, int n, FloatBuffer A, int lda, FloatBuffer s, FloatBuffer U, int ldu, FloatBuffer VT, int ldvt) {
      return openblas.LAPACKE_sgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
   }

   public int getrf(LayoutModified layout, int m, int n, double[] A, int lda, int[] ipiv) {
      return openblas.LAPACKE_dgetrf(layout.lapack(), m, n, A, lda, ipiv);
   }

   public int getrf(LayoutModified layout, int m, int n, DoubleBuffer A, int lda, IntBuffer ipiv) {
      return openblas.LAPACKE_dgetrf(layout.lapack(), m, n, A, lda, ipiv);
   }

   public int getrf(LayoutModified layout, int m, int n, DoublePointer A, int lda, IntPointer ipiv) {
      return openblas.LAPACKE_dgetrf(layout.lapack(), m, n, A, lda, ipiv);
   }

   public int getrf(LayoutModified layout, int m, int n, float[] A, int lda, int[] ipiv) {
      return openblas.LAPACKE_sgetrf(layout.lapack(), m, n, A, lda, ipiv);
   }

   public int getrf(LayoutModified layout, int m, int n, FloatBuffer A, int lda, IntBuffer ipiv) {
      return openblas.LAPACKE_sgetrf(layout.lapack(), m, n, A, lda, ipiv);
   }

   public int getrf2(LayoutModified layout, int m, int n, double[] A, int lda, int[] ipiv) {
      return openblas.LAPACKE_dgetrf2(layout.lapack(), m, n, A, lda, ipiv);
   }

   public int getrf2(LayoutModified layout, int m, int n, DoubleBuffer A, int lda, IntBuffer ipiv) {
      return openblas.LAPACKE_dgetrf2(layout.lapack(), m, n, A, lda, ipiv);
   }

   public int getrf2(LayoutModified layout, int m, int n, float[] A, int lda, int[] ipiv) {
      return openblas.LAPACKE_sgetrf2(layout.lapack(), m, n, A, lda, ipiv);
   }

   public int getrf2(LayoutModified layout, int m, int n, FloatBuffer A, int lda, IntBuffer ipiv) {
      return openblas.LAPACKE_sgetrf2(layout.lapack(), m, n, A, lda, ipiv);
   }

   public int gbtrf(LayoutModified layout, int m, int n, int kl, int ku, double[] AB, int ldab, int[] ipiv) {
      return openblas.LAPACKE_dgbtrf(layout.lapack(), m, n, kl, ku, AB, ldab, ipiv);
   }

   public int gbtrf(LayoutModified layout, int m, int n, int kl, int ku, DoubleBuffer AB, int ldab, IntBuffer ipiv) {
      return openblas.LAPACKE_dgbtrf(layout.lapack(), m, n, kl, ku, AB, ldab, ipiv);
   }

   public int gbtrf(LayoutModified layout, int m, int n, int kl, int ku, float[] AB, int ldab, int[] ipiv) {
      return openblas.LAPACKE_sgbtrf(layout.lapack(), m, n, kl, ku, AB, ldab, ipiv);
   }

   public int gbtrf(LayoutModified layout, int m, int n, int kl, int ku, FloatBuffer AB, int ldab, IntBuffer ipiv) {
      return openblas.LAPACKE_sgbtrf(layout.lapack(), m, n, kl, ku, AB, ldab, ipiv);
   }

   public int sptrf(LayoutModified layout, UPLOModified uplo, int n, double[] AP, int[] ipiv) {
      return openblas.LAPACKE_dsptrf(layout.lapack(), uplo.lapack(), n, AP, ipiv);
   }

   public int sptrf(LayoutModified layout, UPLOModified uplo, int n, DoubleBuffer AP, IntBuffer ipiv) {
      return openblas.LAPACKE_dsptrf(layout.lapack(), uplo.lapack(), n, AP, ipiv);
   }

   public int sptrf(LayoutModified layout, UPLOModified uplo, int n, float[] AP, int[] ipiv) {
      return openblas.LAPACKE_ssptrf(layout.lapack(), uplo.lapack(), n, AP, ipiv);
   }

   public int sptrf(LayoutModified layout, UPLOModified uplo, int n, FloatBuffer AP, IntBuffer ipiv) {
      return openblas.LAPACKE_ssptrf(layout.lapack(), uplo.lapack(), n, AP, ipiv);
   }

   public int getrs(LayoutModified layout, TransposeModified trans, int n, int nrhs, double[] A, int lda, int[] ipiv, double[] B, int ldb) {
      return openblas.LAPACKE_dgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int getrs(LayoutModified layout, TransposeModified trans, int n, int nrhs, DoubleBuffer A, int lda, IntBuffer ipiv, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int getrs(LayoutModified layout, TransposeModified trans, int n, int nrhs, DoublePointer A, int lda, IntPointer ipiv, DoublePointer B, int ldb) {
      return openblas.LAPACKE_dgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int getrs(LayoutModified layout, TransposeModified trans, int n, int nrhs, float[] A, int lda, int[] ipiv, float[] B, int ldb) {
      return openblas.LAPACKE_sgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int getrs(LayoutModified layout, TransposeModified trans, int n, int nrhs, FloatBuffer A, int lda, IntBuffer ipiv, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_sgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
   }

   public int gbtrs(LayoutModified layout, TransposeModified trans, int n, int kl, int ku, int nrhs, double[] A, int lda, int[] ipiv, double[] B, int ldb) {
      return openblas.LAPACKE_dgbtrs(layout.lapack(), trans.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
   }

   public int gbtrs(LayoutModified layout, TransposeModified trans, int n, int kl, int ku, int nrhs, DoubleBuffer A, int lda, IntBuffer ipiv, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dgbtrs(layout.lapack(), trans.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
   }

   public int gbtrs(LayoutModified layout, TransposeModified trans, int n, int kl, int ku, int nrhs, float[] A, int lda, int[] ipiv, float[] B, int ldb) {
      return openblas.LAPACKE_sgbtrs(layout.lapack(), trans.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
   }

   public int gbtrs(LayoutModified layout, TransposeModified trans, int n, int kl, int ku, int nrhs, FloatBuffer A, int lda, IntBuffer ipiv, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_sgbtrs(layout.lapack(), trans.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
   }

   public int sptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] AP, int[] ipiv, double[] B, int ldb) {
      return openblas.LAPACKE_dsptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, ipiv, B, ldb);
   }

   public int sptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer AP, IntBuffer ipiv, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dsptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, ipiv, B, ldb);
   }

   public int sptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] AP, int[] ipiv, float[] B, int ldb) {
      return openblas.LAPACKE_ssptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, ipiv, B, ldb);
   }

   public int sptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer AP, IntBuffer ipiv, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_ssptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, ipiv, B, ldb);
   }

   public int potrf(LayoutModified layout, UPLOModified uplo, int n, double[] A, int lda) {
      return openblas.LAPACKE_dpotrf(layout.lapack(), uplo.lapack(), n, A, lda);
   }

   public int potrf(LayoutModified layout, UPLOModified uplo, int n, DoubleBuffer A, int lda) {
      return openblas.LAPACKE_dpotrf(layout.lapack(), uplo.lapack(), n, A, lda);
   }

   public int potrf(LayoutModified layout, UPLOModified uplo, int n, DoublePointer A, int lda) {
      return openblas.LAPACKE_dpotrf(layout.lapack(), uplo.lapack(), n, A, lda);
   }

   public int potrf(LayoutModified layout, UPLOModified uplo, int n, float[] A, int lda) {
      return openblas.LAPACKE_spotrf(layout.lapack(), uplo.lapack(), n, A, lda);
   }

   public int potrf(LayoutModified layout, UPLOModified uplo, int n, FloatBuffer A, int lda) {
      return openblas.LAPACKE_spotrf(layout.lapack(), uplo.lapack(), n, A, lda);
   }

   public int potrf2(LayoutModified layout, UPLOModified uplo, int n, double[] A, int lda) {
      return openblas.LAPACKE_dpotrf2(layout.lapack(), uplo.lapack(), n, A, lda);
   }

   public int potrf2(LayoutModified layout, UPLOModified uplo, int n, DoubleBuffer A, int lda) {
      return openblas.LAPACKE_dpotrf2(layout.lapack(), uplo.lapack(), n, A, lda);
   }

   public int potrf2(LayoutModified layout, UPLOModified uplo, int n, float[] A, int lda) {
      return openblas.LAPACKE_spotrf2(layout.lapack(), uplo.lapack(), n, A, lda);
   }

   public int potrf2(LayoutModified layout, UPLOModified uplo, int n, FloatBuffer A, int lda) {
      return openblas.LAPACKE_spotrf2(layout.lapack(), uplo.lapack(), n, A, lda);
   }

   public int pbtrf(LayoutModified layout, UPLOModified uplo, int n, int kd, double[] AB, int ldab) {
      return openblas.LAPACKE_dpbtrf(layout.lapack(), uplo.lapack(), n, kd, AB, ldab);
   }

   public int pbtrf(LayoutModified layout, UPLOModified uplo, int n, int kd, DoubleBuffer AB, int ldab) {
      return openblas.LAPACKE_dpbtrf(layout.lapack(), uplo.lapack(), n, kd, AB, ldab);
   }

   public int pbtrf(LayoutModified layout, UPLOModified uplo, int n, int kd, float[] AB, int ldab) {
      return openblas.LAPACKE_spbtrf(layout.lapack(), uplo.lapack(), n, kd, AB, ldab);
   }

   public int pbtrf(LayoutModified layout, UPLOModified uplo, int n, int kd, FloatBuffer AB, int ldab) {
      return openblas.LAPACKE_spbtrf(layout.lapack(), uplo.lapack(), n, kd, AB, ldab);
   }

   public int pptrf(LayoutModified layout, UPLOModified uplo, int n, double[] AP) {
      return openblas.LAPACKE_dpptrf(layout.lapack(), uplo.lapack(), n, AP);
   }

   public int pptrf(LayoutModified layout, UPLOModified uplo, int n, DoubleBuffer AP) {
      return openblas.LAPACKE_dpptrf(layout.lapack(), uplo.lapack(), n, AP);
   }

   public int pptrf(LayoutModified layout, UPLOModified uplo, int n, float[] AP) {
      return openblas.LAPACKE_spptrf(layout.lapack(), uplo.lapack(), n, AP);
   }

   public int pptrf(LayoutModified layout, UPLOModified uplo, int n, FloatBuffer AP) {
      return openblas.LAPACKE_spptrf(layout.lapack(), uplo.lapack(), n, AP);
   }

   public int potrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] A, int lda, double[] B, int ldb) {
      return openblas.LAPACKE_dpotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int potrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dpotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int potrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoublePointer A, int lda, DoublePointer B, int ldb) {
      return openblas.LAPACKE_dpotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int potrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] A, int lda, float[] B, int ldb) {
      return openblas.LAPACKE_spotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int potrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_spotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int pbtrs(LayoutModified layout, UPLOModified uplo, int n, int kd, int nrhs, double[] AB, int ldab, double[] B, int ldb) {
      return openblas.LAPACKE_dpbtrs(layout.lapack(), uplo.lapack(), n, kd, nrhs, AB, ldab, B, ldb);
   }

   public int pbtrs(LayoutModified layout, UPLOModified uplo, int n, int kd, int nrhs, DoubleBuffer AB, int ldab, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dpbtrs(layout.lapack(), uplo.lapack(), n, kd, nrhs, AB, ldab, B, ldb);
   }

   public int pbtrs(LayoutModified layout, UPLOModified uplo, int n, int kd, int nrhs, float[] AB, int ldab, float[] B, int ldb) {
      return openblas.LAPACKE_spbtrs(layout.lapack(), uplo.lapack(), n, kd, nrhs, AB, ldab, B, ldb);
   }

   public int pbtrs(LayoutModified layout, UPLOModified uplo, int n, int kd, int nrhs, FloatBuffer AB, int ldab, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_spbtrs(layout.lapack(), uplo.lapack(), n, kd, nrhs, AB, ldab, B, ldb);
   }

   public int pptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, double[] AP, double[] B, int ldb) {
      return openblas.LAPACKE_dpptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, B, ldb);
   }

   public int pptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, DoubleBuffer AP, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dpptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, B, ldb);
   }

   public int pptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, float[] AP, float[] B, int ldb) {
      return openblas.LAPACKE_spptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, B, ldb);
   }

   public int pptrs(LayoutModified layout, UPLOModified uplo, int n, int nrhs, FloatBuffer AP, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_spptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, B, ldb);
   }

   public int geqrf(LayoutModified layout, int m, int n, double[] A, int lda, double[] tau) {
      return openblas.LAPACKE_dgeqrf(layout.lapack(), m, n, A, lda, tau);
   }

   public int geqrf(LayoutModified layout, int m, int n, DoubleBuffer A, int lda, DoubleBuffer tau) {
      return openblas.LAPACKE_dgeqrf(layout.lapack(), m, n, A, lda, tau);
   }

   public int geqrf(LayoutModified layout, int m, int n, DoublePointer A, int lda, DoublePointer tau) {
      return openblas.LAPACKE_dgeqrf(layout.lapack(), m, n, A, lda, tau);
   }

   public int geqrf(LayoutModified layout, int m, int n, float[] A, int lda, float[] tau) {
      return openblas.LAPACKE_sgeqrf(layout.lapack(), m, n, A, lda, tau);
   }

   public int geqrf(LayoutModified layout, int m, int n, FloatBuffer A, int lda, FloatBuffer tau) {
      return openblas.LAPACKE_sgeqrf(layout.lapack(), m, n, A, lda, tau);
   }

   public int orgqr(LayoutModified layout, int m, int n, int k, double[] A, int lda, double[] tau) {
      return openblas.LAPACKE_dorgqr(layout.lapack(), m, n, k, A, lda, tau);
   }

   public int orgqr(LayoutModified layout, int m, int n, int k, DoubleBuffer A, int lda, DoubleBuffer tau) {
      return openblas.LAPACKE_dorgqr(layout.lapack(), m, n, k, A, lda, tau);
   }

   public int orgqr(LayoutModified layout, int m, int n, int k, DoublePointer A, int lda, DoublePointer tau) {
      return openblas.LAPACKE_dorgqr(layout.lapack(), m, n, k, A, lda, tau);
   }

   public int orgqr(LayoutModified layout, int m, int n, int k, float[] A, int lda, float[] tau) {
      return openblas.LAPACKE_sorgqr(layout.lapack(), m, n, k, A, lda, tau);
   }

   public int orgqr(LayoutModified layout, int m, int n, int k, FloatBuffer A, int lda, FloatBuffer tau) {
      return openblas.LAPACKE_sorgqr(layout.lapack(), m, n, k, A, lda, tau);
   }

   public int ormqr(LayoutModified layout, SideModified side, TransposeModified trans, int m, int n, int k, double[] A, int lda, double[] tau, double[] C, int ldc) {
      return openblas.LAPACKE_dormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
   }

   public int ormqr(LayoutModified layout, SideModified side, TransposeModified trans, int m, int n, int k, DoubleBuffer A, int lda, DoubleBuffer tau, DoubleBuffer C, int ldc) {
      return openblas.LAPACKE_dormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
   }

   public int ormqr(LayoutModified layout, SideModified side, TransposeModified trans, int m, int n, int k, DoublePointer A, int lda, DoublePointer tau, DoublePointer C, int ldc) {
      return openblas.LAPACKE_dormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
   }

   public int ormqr(LayoutModified layout, SideModified side, TransposeModified trans, int m, int n, int k, float[] A, int lda, float[] tau, float[] C, int ldc) {
      return openblas.LAPACKE_sormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
   }

   public int ormqr(LayoutModified layout, SideModified side, TransposeModified trans, int m, int n, int k, FloatBuffer A, int lda, FloatBuffer tau, FloatBuffer C, int ldc) {
      return openblas.LAPACKE_sormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
   }

   public int trtrs(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, int nrhs, double[] A, int lda, double[] B, int ldb) {
      return openblas.LAPACKE_dtrtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int trtrs(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, int nrhs, DoubleBuffer A, int lda, DoubleBuffer B, int ldb) {
      return openblas.LAPACKE_dtrtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int trtrs(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, int nrhs, DoublePointer A, int lda, DoublePointer B, int ldb) {
      return openblas.LAPACKE_dtrtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int trtrs(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, int nrhs, float[] A, int lda, float[] B, int ldb) {
      return openblas.LAPACKE_strtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
   }

   public int trtrs(LayoutModified layout, UPLOModified uplo, TransposeModified trans, DiagModified diag, int n, int nrhs, FloatBuffer A, int lda, FloatBuffer B, int ldb) {
      return openblas.LAPACKE_strtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
   }
}
