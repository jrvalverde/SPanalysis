// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import org.bytedeco.javacpp.IntPointer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;
import org.bytedeco.javacpp.DoublePointer;
import java.nio.DoubleBuffer;
import org.bytedeco.openblas.global.openblas;

public class OpenBLASModified implements BLASModified, LAPACKModified
{
    @Override
    public double asum(final int n, final double[] x, final int incx) {
        return openblas.cblas_dasum(n, x, incx);
    }
    
    @Override
    public float asum(final int n, final float[] x, final int incx) {
        return openblas.cblas_sasum(n, x, incx);
    }
    
    @Override
    public void axpy(final int n, final double alpha, final double[] x, final int incx, final double[] y, final int incy) {
        openblas.cblas_daxpy(n, alpha, x, incx, y, incy);
    }
    
    @Override
    public void axpy(final int n, final float alpha, final float[] x, final int incx, final float[] y, final int incy) {
        openblas.cblas_saxpy(n, alpha, x, incx, y, incy);
    }
    
    @Override
    public double dot(final int n, final double[] x, final int incx, final double[] y, final int incy) {
        return openblas.cblas_ddot(n, x, incx, y, incy);
    }
    
    @Override
    public float dot(final int n, final float[] x, final int incx, final float[] y, final int incy) {
        return openblas.cblas_sdot(n, x, incx, y, incy);
    }
    
    @Override
    public double nrm2(final int n, final double[] x, final int incx) {
        return openblas.cblas_dnrm2(n, x, incx);
    }
    
    @Override
    public float nrm2(final int n, final float[] x, final int incx) {
        return openblas.cblas_snrm2(n, x, incx);
    }
    
    @Override
    public void scal(final int n, final double alpha, final double[] x, final int incx) {
        openblas.cblas_dscal(n, alpha, x, incx);
    }
    
    @Override
    public void scal(final int n, final float alpha, final float[] x, final int incx) {
        openblas.cblas_sscal(n, alpha, x, incx);
    }
    
    @Override
    public void swap(final int n, final double[] x, final int incx, final double[] y, final int incy) {
        openblas.cblas_dswap(n, x, incx, y, incy);
    }
    
    @Override
    public void swap(final int n, final float[] x, final int incx, final float[] y, final int incy) {
        openblas.cblas_sswap(n, x, incx, y, incy);
    }
    
    @Override
    public long iamax(final int n, final double[] x, final int incx) {
        return openblas.cblas_idamax(n, x, incx);
    }
    
    @Override
    public long iamax(final int n, final float[] x, final int incx) {
        return openblas.cblas_isamax(n, x, incx);
    }
    
    @Override
    public void gemv(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final double alpha, final double[] A, final int lda, final double[] x, final int incx, final double beta, final double[] y, final int incy) {
        openblas.cblas_dgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void gemv(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final double alpha, final DoubleBuffer A, final int lda, final DoubleBuffer x, final int incx, final double beta, final DoubleBuffer y, final int incy) {
        openblas.cblas_dgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void gemv(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final double alpha, final DoublePointer A, final int lda, final DoublePointer x, final int incx, final double beta, final DoublePointer y, final int incy) {
        openblas.cblas_dgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void gemv(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final float alpha, final float[] A, final int lda, final float[] x, final int incx, final float beta, final float[] y, final int incy) {
        openblas.cblas_sgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void gemv(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final float alpha, final FloatBuffer A, final int lda, final FloatBuffer x, final int incx, final float beta, final FloatBuffer y, final int incy) {
        openblas.cblas_sgemv(layout.blas(), trans.blas(), m, n, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void symv(final LayoutModified layout, final UPLOModified uplo, final int n, final double alpha, final double[] A, final int lda, final double[] x, final int incx, final double beta, final double[] y, final int incy) {
        openblas.cblas_dsymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void symv(final LayoutModified layout, final UPLOModified uplo, final int n, final double alpha, final DoubleBuffer A, final int lda, final DoubleBuffer x, final int incx, final double beta, final DoubleBuffer y, final int incy) {
        openblas.cblas_dsymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void symv(final LayoutModified layout, final UPLOModified uplo, final int n, final double alpha, final DoublePointer A, final int lda, final DoublePointer x, final int incx, final double beta, final DoublePointer y, final int incy) {
        openblas.cblas_dsymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void symv(final LayoutModified layout, final UPLOModified uplo, final int n, final float alpha, final float[] A, final int lda, final float[] x, final int incx, final float beta, final float[] y, final int incy) {
        openblas.cblas_ssymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void symv(final LayoutModified layout, final UPLOModified uplo, final int n, final float alpha, final FloatBuffer A, final int lda, final FloatBuffer x, final int incx, final float beta, final FloatBuffer y, final int incy) {
        openblas.cblas_ssymv(layout.blas(), uplo.blas(), n, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void spmv(final LayoutModified layout, final UPLOModified uplo, final int n, final double alpha, final double[] A, final double[] x, final int incx, final double beta, final double[] y, final int incy) {
        openblas.cblas_dspmv(layout.blas(), uplo.blas(), n, alpha, A, x, incx, beta, y, incy);
    }
    
    @Override
    public void spmv(final LayoutModified layout, final UPLOModified uplo, final int n, final double alpha, final DoubleBuffer A, final DoubleBuffer x, final int incx, final double beta, final DoubleBuffer y, final int incy) {
        openblas.cblas_dspmv(layout.blas(), uplo.blas(), n, alpha, A, x, incx, beta, y, incy);
    }
    
    @Override
    public void spmv(final LayoutModified layout, final UPLOModified uplo, final int n, final float alpha, final float[] A, final float[] x, final int incx, final float beta, final float[] y, final int incy) {
        openblas.cblas_sspmv(layout.blas(), uplo.blas(), n, alpha, A, x, incx, beta, y, incy);
    }
    
    @Override
    public void spmv(final LayoutModified layout, final UPLOModified uplo, final int n, final float alpha, final FloatBuffer A, final FloatBuffer x, final int incx, final float beta, final FloatBuffer y, final int incy) {
        openblas.cblas_sspmv(layout.blas(), uplo.blas(), n, alpha, A, x, incx, beta, y, incy);
    }
    
    @Override
    public void trmv(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final double[] A, final int lda, final double[] x, final int incx) {
        openblas.cblas_dtrmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
    }
    
    @Override
    public void trmv(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final DoubleBuffer A, final int lda, final DoubleBuffer x, final int incx) {
        openblas.cblas_dtrmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
    }
    
    @Override
    public void trmv(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final DoublePointer A, final int lda, final DoublePointer x, final int incx) {
        openblas.cblas_dtrmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
    }
    
    @Override
    public void trmv(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final float[] A, final int lda, final float[] x, final int incx) {
        openblas.cblas_strmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
    }
    
    @Override
    public void trmv(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final FloatBuffer A, final int lda, final FloatBuffer x, final int incx) {
        openblas.cblas_strmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, lda, x, incx);
    }
    
    @Override
    public void tpmv(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final double[] A, final double[] x, final int incx) {
        openblas.cblas_dtpmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, x, incx);
    }
    
    @Override
    public void tpmv(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final DoubleBuffer A, final DoubleBuffer x, final int incx) {
        openblas.cblas_dtpmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, x, incx);
    }
    
    @Override
    public void tpmv(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final float[] A, final float[] x, final int incx) {
        openblas.cblas_stpmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, x, incx);
    }
    
    @Override
    public void tpmv(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final FloatBuffer A, final FloatBuffer x, final int incx) {
        openblas.cblas_stpmv(layout.blas(), uplo.blas(), trans.blas(), diag.blas(), n, A, x, incx);
    }
    
    @Override
    public void gbmv(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final int kl, final int ku, final double alpha, final double[] A, final int lda, final double[] x, final int incx, final double beta, final double[] y, final int incy) {
        openblas.cblas_dgbmv(layout.blas(), trans.blas(), m, n, kl, ku, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void gbmv(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final int kl, final int ku, final double alpha, final DoubleBuffer A, final int lda, final DoubleBuffer x, final int incx, final double beta, final DoubleBuffer y, final int incy) {
        openblas.cblas_dgbmv(layout.blas(), trans.blas(), m, n, kl, ku, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void gbmv(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final int kl, final int ku, final float alpha, final float[] A, final int lda, final float[] x, final int incx, final float beta, final float[] y, final int incy) {
        openblas.cblas_sgbmv(layout.blas(), trans.blas(), m, n, kl, ku, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void gbmv(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final int kl, final int ku, final float alpha, final FloatBuffer A, final int lda, final FloatBuffer x, final int incx, final float beta, final FloatBuffer y, final int incy) {
        openblas.cblas_sgbmv(layout.blas(), trans.blas(), m, n, kl, ku, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void sbmv(final LayoutModified layout, final UPLOModified uplo, final int n, final int k, final double alpha, final double[] A, final int lda, final double[] x, final int incx, final double beta, final double[] y, final int incy) {
        openblas.cblas_dsbmv(layout.blas(), uplo.blas(), n, k, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void sbmv(final LayoutModified layout, final UPLOModified uplo, final int n, final int k, final double alpha, final DoubleBuffer A, final int lda, final DoubleBuffer x, final int incx, final double beta, final DoubleBuffer y, final int incy) {
        openblas.cblas_dsbmv(layout.blas(), uplo.blas(), n, k, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void sbmv(final LayoutModified layout, final UPLOModified uplo, final int n, final int k, final float alpha, final float[] A, final int lda, final float[] x, final int incx, final float beta, final float[] y, final int incy) {
        openblas.cblas_ssbmv(layout.blas(), uplo.blas(), n, k, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void sbmv(final LayoutModified layout, final UPLOModified uplo, final int n, final int k, final float alpha, final FloatBuffer A, final int lda, final FloatBuffer x, final int incx, final float beta, final FloatBuffer y, final int incy) {
        openblas.cblas_ssbmv(layout.blas(), uplo.blas(), n, k, alpha, A, lda, x, incx, beta, y, incy);
    }
    
    @Override
    public void ger(final LayoutModified layout, final int m, final int n, final double alpha, final double[] x, final int incx, final double[] y, final int incy, final double[] A, final int lda) {
        openblas.cblas_dger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
    }
    
    @Override
    public void ger(final LayoutModified layout, final int m, final int n, final double alpha, final DoubleBuffer x, final int incx, final DoubleBuffer y, final int incy, final DoubleBuffer A, final int lda) {
        openblas.cblas_dger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
    }
    
    @Override
    public void ger(final LayoutModified layout, final int m, final int n, final double alpha, final DoublePointer x, final int incx, final DoublePointer y, final int incy, final DoublePointer A, final int lda) {
        openblas.cblas_dger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
    }
    
    @Override
    public void ger(final LayoutModified layout, final int m, final int n, final float alpha, final float[] x, final int incx, final float[] y, final int incy, final float[] A, final int lda) {
        openblas.cblas_sger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
    }
    
    @Override
    public void ger(final LayoutModified layout, final int m, final int n, final float alpha, final FloatBuffer x, final int incx, final FloatBuffer y, final int incy, final FloatBuffer A, final int lda) {
        openblas.cblas_sger(layout.blas(), m, n, alpha, x, incx, y, incy, A, lda);
    }
    
    @Override
    public void syr(final LayoutModified layout, final UPLOModified uplo, final int n, final double alpha, final double[] x, final int incx, final double[] A, final int lda) {
        openblas.cblas_dsyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
    }
    
    @Override
    public void syr(final LayoutModified layout, final UPLOModified uplo, final int n, final double alpha, final DoubleBuffer x, final int incx, final DoubleBuffer A, final int lda) {
        openblas.cblas_dsyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
    }
    
    @Override
    public void syr(final LayoutModified layout, final UPLOModified uplo, final int n, final double alpha, final DoublePointer x, final int incx, final DoublePointer A, final int lda) {
        openblas.cblas_dsyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
    }
    
    @Override
    public void syr(final LayoutModified layout, final UPLOModified uplo, final int n, final float alpha, final float[] x, final int incx, final float[] A, final int lda) {
        openblas.cblas_ssyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
    }
    
    @Override
    public void syr(final LayoutModified layout, final UPLOModified uplo, final int n, final float alpha, final FloatBuffer x, final int incx, final FloatBuffer A, final int lda) {
        openblas.cblas_ssyr(layout.blas(), uplo.blas(), n, alpha, x, incx, A, lda);
    }
    
    @Override
    public void spr(final LayoutModified layout, final UPLOModified uplo, final int n, final double alpha, final double[] x, final int incx, final double[] A) {
        openblas.cblas_dspr(layout.blas(), uplo.blas(), n, alpha, x, incx, A);
    }
    
    @Override
    public void spr(final LayoutModified layout, final UPLOModified uplo, final int n, final double alpha, final DoubleBuffer x, final int incx, final DoubleBuffer A) {
        openblas.cblas_dspr(layout.blas(), uplo.blas(), n, alpha, x, incx, A);
    }
    
    @Override
    public void spr(final LayoutModified layout, final UPLOModified uplo, final int n, final float alpha, final float[] x, final int incx, final float[] A) {
        openblas.cblas_sspr(layout.blas(), uplo.blas(), n, alpha, x, incx, A);
    }
    
    @Override
    public void spr(final LayoutModified layout, final UPLOModified uplo, final int n, final float alpha, final FloatBuffer x, final int incx, final FloatBuffer A) {
        openblas.cblas_sspr(layout.blas(), uplo.blas(), n, alpha, x, incx, A);
    }
    
    @Override
    public void gemm(final LayoutModified layout, final TransposeModified transA, final TransposeModified transB, final int m, final int n, final int k, final double alpha, final double[] A, final int lda, final double[] B, final int ldb, final double beta, final double[] C, final int ldc) {
        openblas.cblas_dgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
    }
    
    @Override
    public void gemm(final LayoutModified layout, final TransposeModified transA, final TransposeModified transB, final int m, final int n, final int k, final double alpha, final DoubleBuffer A, final int lda, final DoubleBuffer B, final int ldb, final double beta, final DoubleBuffer C, final int ldc) {
        openblas.cblas_dgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
    }
    
    @Override
    public void gemm(final LayoutModified layout, final TransposeModified transA, final TransposeModified transB, final int m, final int n, final int k, final double alpha, final DoublePointer A, final int lda, final DoublePointer B, final int ldb, final double beta, final DoublePointer C, final int ldc) {
        openblas.cblas_dgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
    }
    
    @Override
    public void gemm(final LayoutModified layout, final TransposeModified transA, final TransposeModified transB, final int m, final int n, final int k, final float alpha, final float[] A, final int lda, final float[] B, final int ldb, final float beta, final float[] C, final int ldc) {
        openblas.cblas_sgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
    }
    
    @Override
    public void gemm(final LayoutModified layout, final TransposeModified transA, final TransposeModified transB, final int m, final int n, final int k, final float alpha, final FloatBuffer A, final int lda, final FloatBuffer B, final int ldb, final float beta, final FloatBuffer C, final int ldc) {
        openblas.cblas_sgemm(layout.blas(), transA.blas(), transB.blas(), m, n, k, alpha, A, lda, B, ldb, beta, C, ldc);
    }
    
    @Override
    public void symm(final LayoutModified layout, final SideModified side, final UPLOModified uplo, final int m, final int n, final double alpha, final double[] A, final int lda, final double[] B, final int ldb, final double beta, final double[] C, final int ldc) {
        openblas.cblas_dsymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
    }
    
    @Override
    public void symm(final LayoutModified layout, final SideModified side, final UPLOModified uplo, final int m, final int n, final double alpha, final DoubleBuffer A, final int lda, final DoubleBuffer B, final int ldb, final double beta, final DoubleBuffer C, final int ldc) {
        openblas.cblas_dsymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
    }
    
    @Override
    public void symm(final LayoutModified layout, final SideModified side, final UPLOModified uplo, final int m, final int n, final double alpha, final DoublePointer A, final int lda, final DoublePointer B, final int ldb, final double beta, final DoublePointer C, final int ldc) {
        openblas.cblas_dsymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
    }
    
    @Override
    public void symm(final LayoutModified layout, final SideModified side, final UPLOModified uplo, final int m, final int n, final float alpha, final float[] A, final int lda, final float[] B, final int ldb, final float beta, final float[] C, final int ldc) {
        openblas.cblas_ssymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
    }
    
    @Override
    public void symm(final LayoutModified layout, final SideModified side, final UPLOModified uplo, final int m, final int n, final float alpha, final FloatBuffer A, final int lda, final FloatBuffer B, final int ldb, final float beta, final FloatBuffer C, final int ldc) {
        openblas.cblas_ssymm(layout.blas(), side.blas(), uplo.blas(), m, n, alpha, A, lda, B, ldb, beta, C, ldc);
    }
    
    @Override
    public int gesv(final LayoutModified layout, final int n, final int nrhs, final double[] A, final int lda, final int[] ipiv, final double[] B, final int ldb) {
        return openblas.LAPACKE_dgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int gesv(final LayoutModified layout, final int n, final int nrhs, final DoubleBuffer A, final int lda, final IntBuffer ipiv, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int gesv(final LayoutModified layout, final int n, final int nrhs, final DoublePointer A, final int lda, final IntPointer ipiv, final DoublePointer B, final int ldb) {
        return openblas.LAPACKE_dgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int gesv(final LayoutModified layout, final int n, final int nrhs, final float[] A, final int lda, final int[] ipiv, final float[] B, final int ldb) {
        return openblas.LAPACKE_sgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int gesv(final LayoutModified layout, final int n, final int nrhs, final FloatBuffer A, final int lda, final IntBuffer ipiv, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_sgesv(layout.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int sysv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final double[] A, final int lda, final int[] ipiv, final double[] B, final int ldb) {
        return openblas.LAPACKE_dsysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int sysv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final DoubleBuffer A, final int lda, final IntBuffer ipiv, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dsysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int sysv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final DoublePointer A, final int lda, final IntPointer ipiv, final DoublePointer B, final int ldb) {
        return openblas.LAPACKE_dsysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int sysv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final float[] A, final int lda, final int[] ipiv, final float[] B, final int ldb) {
        return openblas.LAPACKE_ssysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int sysv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final FloatBuffer A, final int lda, final IntBuffer ipiv, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_ssysv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int spsv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final double[] A, final int[] ipiv, final double[] B, final int ldb) {
        return openblas.LAPACKE_dspsv(layout.lapack(), uplo.lapack(), n, nrhs, A, ipiv, B, ldb);
    }
    
    @Override
    public int spsv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final DoubleBuffer A, final IntBuffer ipiv, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dspsv(layout.lapack(), uplo.lapack(), n, nrhs, A, ipiv, B, ldb);
    }
    
    @Override
    public int spsv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final float[] A, final int[] ipiv, final float[] B, final int ldb) {
        return openblas.LAPACKE_sspsv(layout.lapack(), uplo.lapack(), n, nrhs, A, ipiv, B, ldb);
    }
    
    @Override
    public int spsv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final FloatBuffer A, final IntBuffer ipiv, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_sspsv(layout.lapack(), uplo.lapack(), n, nrhs, A, ipiv, B, ldb);
    }
    
    @Override
    public int posv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final double[] A, final int lda, final double[] B, final int ldb) {
        return openblas.LAPACKE_dposv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int posv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final DoubleBuffer A, final int lda, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dposv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int posv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final float[] A, final int lda, final float[] B, final int ldb) {
        return openblas.LAPACKE_sposv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int posv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final FloatBuffer A, final int lda, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_sposv(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int ppsv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final double[] A, final double[] B, final int ldb) {
        return openblas.LAPACKE_dppsv(layout.lapack(), uplo.lapack(), n, nrhs, A, B, ldb);
    }
    
    @Override
    public int ppsv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final DoubleBuffer A, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dppsv(layout.lapack(), uplo.lapack(), n, nrhs, A, B, ldb);
    }
    
    @Override
    public int ppsv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final float[] A, final float[] B, final int ldb) {
        return openblas.LAPACKE_sppsv(layout.lapack(), uplo.lapack(), n, nrhs, A, B, ldb);
    }
    
    @Override
    public int ppsv(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final FloatBuffer A, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_sppsv(layout.lapack(), uplo.lapack(), n, nrhs, A, B, ldb);
    }
    
    @Override
    public int gbsv(final LayoutModified layout, final int n, final int kl, final int ku, final int nrhs, final double[] A, final int lda, final int[] ipiv, final double[] B, final int ldb) {
        return openblas.LAPACKE_dgbsv(layout.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int gbsv(final LayoutModified layout, final int n, final int kl, final int ku, final int nrhs, final DoubleBuffer A, final int lda, final IntBuffer ipiv, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dgbsv(layout.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int gbsv(final LayoutModified layout, final int n, final int kl, final int ku, final int nrhs, final float[] A, final int lda, final int[] ipiv, final float[] B, final int ldb) {
        return openblas.LAPACKE_sgbsv(layout.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int gbsv(final LayoutModified layout, final int n, final int kl, final int ku, final int nrhs, final FloatBuffer A, final int lda, final IntBuffer ipiv, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_sgbsv(layout.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int gels(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final int nrhs, final double[] A, final int lda, final double[] B, final int ldb) {
        return openblas.LAPACKE_dgels(layout.lapack(), trans.lapack(), m, n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int gels(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final int nrhs, final DoubleBuffer A, final int lda, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dgels(layout.lapack(), trans.lapack(), m, n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int gels(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final int nrhs, final float[] A, final int lda, final float[] B, final int ldb) {
        return openblas.LAPACKE_sgels(layout.lapack(), trans.lapack(), m, n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int gels(final LayoutModified layout, final TransposeModified trans, final int m, final int n, final int nrhs, final FloatBuffer A, final int lda, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_sgels(layout.lapack(), trans.lapack(), m, n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int gelsy(final LayoutModified layout, final int m, final int n, final int nrhs, final double[] A, final int lda, final double[] B, final int ldb, final int[] jpvt, final double rcond, final int[] rank) {
        return openblas.LAPACKE_dgelsy(layout.lapack(), m, n, nrhs, A, lda, B, ldb, jpvt, rcond, rank);
    }
    
    @Override
    public int gelsy(final LayoutModified layout, final int m, final int n, final int nrhs, final DoubleBuffer A, final int lda, final DoubleBuffer B, final int ldb, final IntBuffer jpvt, final double rcond, final IntBuffer rank) {
        return openblas.LAPACKE_dgelsy(layout.lapack(), m, n, nrhs, A, lda, B, ldb, jpvt, rcond, rank);
    }
    
    @Override
    public int gelsy(final LayoutModified layout, final int m, final int n, final int nrhs, final float[] A, final int lda, final float[] B, final int ldb, final int[] jpvt, final float rcond, final int[] rank) {
        return openblas.LAPACKE_sgelsy(layout.lapack(), m, n, nrhs, A, lda, B, ldb, jpvt, rcond, rank);
    }
    
    @Override
    public int gelsy(final LayoutModified layout, final int m, final int n, final int nrhs, final FloatBuffer A, final int lda, final FloatBuffer B, final int ldb, final IntBuffer jpvt, final float rcond, final IntBuffer rank) {
        return openblas.LAPACKE_sgelsy(layout.lapack(), m, n, nrhs, A, lda, B, ldb, jpvt, rcond, rank);
    }
    
    @Override
    public int gelss(final LayoutModified layout, final int m, final int n, final int nrhs, final double[] A, final int lda, final double[] B, final int ldb, final double[] s, final double rcond, final int[] rank) {
        return openblas.LAPACKE_dgelss(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
    }
    
    @Override
    public int gelss(final LayoutModified layout, final int m, final int n, final int nrhs, final DoubleBuffer A, final int lda, final DoubleBuffer B, final int ldb, final DoubleBuffer s, final double rcond, final IntBuffer rank) {
        return openblas.LAPACKE_dgelss(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
    }
    
    @Override
    public int gelss(final LayoutModified layout, final int m, final int n, final int nrhs, final float[] A, final int lda, final float[] B, final int ldb, final float[] s, final float rcond, final int[] rank) {
        return openblas.LAPACKE_sgelss(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
    }
    
    @Override
    public int gelss(final LayoutModified layout, final int m, final int n, final int nrhs, final FloatBuffer A, final int lda, final FloatBuffer B, final int ldb, final FloatBuffer s, final float rcond, final IntBuffer rank) {
        return openblas.LAPACKE_sgelss(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
    }
    
    @Override
    public int gelsd(final LayoutModified layout, final int m, final int n, final int nrhs, final double[] A, final int lda, final double[] B, final int ldb, final double[] s, final double rcond, final int[] rank) {
        return openblas.LAPACKE_dgelsd(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
    }
    
    @Override
    public int gelsd(final LayoutModified layout, final int m, final int n, final int nrhs, final DoubleBuffer A, final int lda, final DoubleBuffer B, final int ldb, final DoubleBuffer s, final double rcond, final IntBuffer rank) {
        return openblas.LAPACKE_dgelsd(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
    }
    
    @Override
    public int gelsd(final LayoutModified layout, final int m, final int n, final int nrhs, final float[] A, final int lda, final float[] B, final int ldb, final float[] s, final float rcond, final int[] rank) {
        return openblas.LAPACKE_sgelsd(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
    }
    
    @Override
    public int gelsd(final LayoutModified layout, final int m, final int n, final int nrhs, final FloatBuffer A, final int lda, final FloatBuffer B, final int ldb, final FloatBuffer s, final float rcond, final IntBuffer rank) {
        return openblas.LAPACKE_sgelsd(layout.lapack(), m, n, nrhs, A, lda, B, ldb, s, rcond, rank);
    }
    
    @Override
    public int gglse(final LayoutModified layout, final int m, final int n, final int p, final double[] A, final int lda, final double[] B, final int ldb, final double[] c, final double[] d, final double[] x) {
        return openblas.LAPACKE_dgglse(layout.lapack(), m, n, p, A, lda, B, ldb, c, d, x);
    }
    
    @Override
    public int gglse(final LayoutModified layout, final int m, final int n, final int p, final DoubleBuffer A, final int lda, final DoubleBuffer B, final int ldb, final DoubleBuffer c, final DoubleBuffer d, final DoubleBuffer x) {
        return openblas.LAPACKE_dgglse(layout.lapack(), m, n, p, A, lda, B, ldb, c, d, x);
    }
    
    @Override
    public int gglse(final LayoutModified layout, final int m, final int n, final int p, final float[] A, final int lda, final float[] B, final int ldb, final float[] c, final float[] d, final float[] x) {
        return openblas.LAPACKE_sgglse(layout.lapack(), m, n, p, A, lda, B, ldb, c, d, x);
    }
    
    @Override
    public int gglse(final LayoutModified layout, final int m, final int n, final int p, final FloatBuffer A, final int lda, final FloatBuffer B, final int ldb, final FloatBuffer c, final FloatBuffer d, final FloatBuffer x) {
        return openblas.LAPACKE_sgglse(layout.lapack(), m, n, p, A, lda, B, ldb, c, d, x);
    }
    
    @Override
    public int ggglm(final LayoutModified layout, final int n, final int m, final int p, final double[] A, final int lda, final double[] B, final int ldb, final double[] d, final double[] x, final double[] y) {
        return openblas.LAPACKE_dggglm(layout.lapack(), n, m, p, A, lda, B, ldb, d, x, y);
    }
    
    @Override
    public int ggglm(final LayoutModified layout, final int n, final int m, final int p, final DoubleBuffer A, final int lda, final DoubleBuffer B, final int ldb, final DoubleBuffer d, final DoubleBuffer x, final DoubleBuffer y) {
        return openblas.LAPACKE_dggglm(layout.lapack(), n, m, p, A, lda, B, ldb, d, x, y);
    }
    
    @Override
    public int ggglm(final LayoutModified layout, final int n, final int m, final int p, final float[] A, final int lda, final float[] B, final int ldb, final float[] d, final float[] x, final float[] y) {
        return openblas.LAPACKE_sggglm(layout.lapack(), n, m, p, A, lda, B, ldb, d, x, y);
    }
    
    @Override
    public int ggglm(final LayoutModified layout, final int n, final int m, final int p, final FloatBuffer A, final int lda, final FloatBuffer B, final int ldb, final FloatBuffer d, final FloatBuffer x, final FloatBuffer y) {
        return openblas.LAPACKE_sggglm(layout.lapack(), n, m, p, A, lda, B, ldb, d, x, y);
    }
    
    @Override
    public int geev(final LayoutModified layout, final EVDJobModified jobvl, final EVDJobModified jobvr, final int n, final double[] A, final int lda, final double[] wr, final double[] wi, final double[] Vl, final int ldvl, final double[] Vr, final int ldvr) {
        return openblas.LAPACKE_dgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
    }
    
    @Override
    public int geev(final LayoutModified layout, final EVDJobModified jobvl, final EVDJobModified jobvr, final int n, final DoubleBuffer A, final int lda, final DoubleBuffer wr, final DoubleBuffer wi, final DoubleBuffer Vl, final int ldvl, final DoubleBuffer Vr, final int ldvr) {
        return openblas.LAPACKE_dgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
    }
    
    @Override
    public int geev(final LayoutModified layout, final EVDJobModified jobvl, final EVDJobModified jobvr, final int n, final DoublePointer A, final int lda, final DoublePointer wr, final DoublePointer wi, final DoublePointer Vl, final int ldvl, final DoublePointer Vr, final int ldvr) {
        return openblas.LAPACKE_dgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
    }
    
    @Override
    public int geev(final LayoutModified layout, final EVDJobModified jobvl, final EVDJobModified jobvr, final int n, final float[] A, final int lda, final float[] wr, final float[] wi, final float[] Vl, final int ldvl, final float[] Vr, final int ldvr) {
        return openblas.LAPACKE_sgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
    }
    
    @Override
    public int geev(final LayoutModified layout, final EVDJobModified jobvl, final EVDJobModified jobvr, final int n, final FloatBuffer A, final int lda, final FloatBuffer wr, final FloatBuffer wi, final FloatBuffer Vl, final int ldvl, final FloatBuffer Vr, final int ldvr) {
        return openblas.LAPACKE_sgeev(layout.lapack(), jobvl.lapack(), jobvr.lapack(), n, A, lda, wr, wi, Vl, ldvl, Vr, ldvr);
    }
    
    @Override
    public int syev(final LayoutModified layout, final EVDJobModified jobz, final UPLOModified uplo, final int n, final double[] A, final int lda, final double[] w) {
        return openblas.LAPACKE_dsyev(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
    }
    
    @Override
    public int syev(final LayoutModified layout, final EVDJobModified jobz, final UPLOModified uplo, final int n, final DoubleBuffer A, final int lda, final DoubleBuffer w) {
        return openblas.LAPACKE_dsyev(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
    }
    
    @Override
    public int syev(final LayoutModified layout, final EVDJobModified jobz, final UPLOModified uplo, final int n, final float[] A, final int lda, final float[] w) {
        return openblas.LAPACKE_ssyev(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
    }
    
    @Override
    public int syev(final LayoutModified layout, final EVDJobModified jobz, final UPLOModified uplo, final int n, final FloatBuffer A, final int lda, final FloatBuffer w) {
        return openblas.LAPACKE_ssyev(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
    }
    
    @Override
    public int syevd(final LayoutModified layout, final EVDJobModified jobz, final UPLOModified uplo, final int n, final double[] A, final int lda, final double[] w) {
        return openblas.LAPACKE_dsyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
    }
    
    @Override
    public int syevd(final LayoutModified layout, final EVDJobModified jobz, final UPLOModified uplo, final int n, final DoubleBuffer A, final int lda, final DoubleBuffer w) {
        return openblas.LAPACKE_dsyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
    }
    
    @Override
    public int syevd(final LayoutModified layout, final EVDJobModified jobz, final UPLOModified uplo, final int n, final DoublePointer A, final int lda, final DoublePointer w) {
        return openblas.LAPACKE_dsyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
    }
    
    @Override
    public int syevd(final LayoutModified layout, final EVDJobModified jobz, final UPLOModified uplo, final int n, final float[] A, final int lda, final float[] w) {
        return openblas.LAPACKE_ssyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
    }
    
    @Override
    public int syevd(final LayoutModified layout, final EVDJobModified jobz, final UPLOModified uplo, final int n, final FloatBuffer A, final int lda, final FloatBuffer w) {
        return openblas.LAPACKE_ssyevd(layout.lapack(), jobz.lapack(), uplo.lapack(), n, A, lda, w);
    }
    
    @Override
    public int syevr(final LayoutModified layout, final EVDJobModified jobz, final EigenRangeModified range, final UPLOModified uplo, final int n, final double[] A, final int lda, final double vl, final double vu, final int il, final int iu, final double abstol, final int[] m, final double[] w, final double[] Z, final int ldz, final int[] isuppz) {
        return openblas.LAPACKE_dsyevr(layout.lapack(), jobz.lapack(), range.lapack(), uplo.lapack(), n, A, lda, vl, vu, il, iu, abstol, m, w, Z, ldz, isuppz);
    }
    
    @Override
    public int syevr(final LayoutModified layout, final EVDJobModified jobz, final EigenRangeModified range, final UPLOModified uplo, final int n, final DoubleBuffer A, final int lda, final double vl, final double vu, final int il, final int iu, final double abstol, final IntBuffer m, final DoubleBuffer w, final DoubleBuffer Z, final int ldz, final IntBuffer isuppz) {
        return openblas.LAPACKE_dsyevr(layout.lapack(), jobz.lapack(), range.lapack(), uplo.lapack(), n, A, lda, vl, vu, il, iu, abstol, m, w, Z, ldz, isuppz);
    }
    
    @Override
    public int syevr(final LayoutModified layout, final EVDJobModified jobz, final EigenRangeModified range, final UPLOModified uplo, final int n, final float[] A, final int lda, final float vl, final float vu, final int il, final int iu, final float abstol, final int[] m, final float[] w, final float[] Z, final int ldz, final int[] isuppz) {
        return openblas.LAPACKE_ssyevr(layout.lapack(), jobz.lapack(), range.lapack(), uplo.lapack(), n, A, lda, vl, vu, il, iu, abstol, m, w, Z, ldz, isuppz);
    }
    
    @Override
    public int syevr(final LayoutModified layout, final EVDJobModified jobz, final EigenRangeModified range, final UPLOModified uplo, final int n, final FloatBuffer A, final int lda, final float vl, final float vu, final int il, final int iu, final float abstol, final IntBuffer m, final FloatBuffer w, final FloatBuffer Z, final int ldz, final IntBuffer isuppz) {
        return openblas.LAPACKE_ssyevr(layout.lapack(), jobz.lapack(), range.lapack(), uplo.lapack(), n, A, lda, vl, vu, il, iu, abstol, m, w, Z, ldz, isuppz);
    }
    
    @Override
    public int gesvd(final LayoutModified layout, final SVDJobModified jobu, final SVDJobModified jobvt, final int m, final int n, final double[] A, final int lda, final double[] s, final double[] U, final int ldu, final double[] VT, final int ldvt, final double[] superb) {
        return openblas.LAPACKE_dgesvd(layout.lapack(), jobu.lapack(), jobvt.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt, superb);
    }
    
    @Override
    public int gesvd(final LayoutModified layout, final SVDJobModified jobu, final SVDJobModified jobvt, final int m, final int n, final DoubleBuffer A, final int lda, final DoubleBuffer s, final DoubleBuffer U, final int ldu, final DoubleBuffer VT, final int ldvt, final DoubleBuffer superb) {
        return openblas.LAPACKE_dgesvd(layout.lapack(), jobu.lapack(), jobvt.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt, superb);
    }
    
    @Override
    public int gesvd(final LayoutModified layout, final SVDJobModified jobu, final SVDJobModified jobvt, final int m, final int n, final float[] A, final int lda, final float[] s, final float[] U, final int ldu, final float[] VT, final int ldvt, final float[] superb) {
        return openblas.LAPACKE_sgesvd(layout.lapack(), jobu.lapack(), jobvt.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt, superb);
    }
    
    @Override
    public int gesvd(final LayoutModified layout, final SVDJobModified jobu, final SVDJobModified jobvt, final int m, final int n, final FloatBuffer A, final int lda, final FloatBuffer s, final FloatBuffer U, final int ldu, final FloatBuffer VT, final int ldvt, final FloatBuffer superb) {
        return openblas.LAPACKE_sgesvd(layout.lapack(), jobu.lapack(), jobvt.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt, superb);
    }
    
    @Override
    public int gesdd(final LayoutModified layout, final SVDJobModified jobz, final int m, final int n, final double[] A, final int lda, final double[] s, final double[] U, final int ldu, final double[] VT, final int ldvt) {
        return openblas.LAPACKE_dgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
    }
    
    @Override
    public int gesdd(final LayoutModified layout, final SVDJobModified jobz, final int m, final int n, final DoubleBuffer A, final int lda, final DoubleBuffer s, final DoubleBuffer U, final int ldu, final DoubleBuffer VT, final int ldvt) {
        return openblas.LAPACKE_dgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
    }
    
    @Override
    public int gesdd(final LayoutModified layout, final SVDJobModified jobz, final int m, final int n, final DoublePointer A, final int lda, final DoublePointer s, final DoublePointer U, final int ldu, final DoublePointer VT, final int ldvt) {
        return openblas.LAPACKE_dgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
    }
    
    @Override
    public int gesdd(final LayoutModified layout, final SVDJobModified jobz, final int m, final int n, final float[] A, final int lda, final float[] s, final float[] U, final int ldu, final float[] VT, final int ldvt) {
        return openblas.LAPACKE_sgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
    }
    
    @Override
    public int gesdd(final LayoutModified layout, final SVDJobModified jobz, final int m, final int n, final FloatBuffer A, final int lda, final FloatBuffer s, final FloatBuffer U, final int ldu, final FloatBuffer VT, final int ldvt) {
        return openblas.LAPACKE_sgesdd(layout.lapack(), jobz.lapack(), m, n, A, lda, s, U, ldu, VT, ldvt);
    }
    
    @Override
    public int getrf(final LayoutModified layout, final int m, final int n, final double[] A, final int lda, final int[] ipiv) {
        return openblas.LAPACKE_dgetrf(layout.lapack(), m, n, A, lda, ipiv);
    }
    
    @Override
    public int getrf(final LayoutModified layout, final int m, final int n, final DoubleBuffer A, final int lda, final IntBuffer ipiv) {
        return openblas.LAPACKE_dgetrf(layout.lapack(), m, n, A, lda, ipiv);
    }
    
    @Override
    public int getrf(final LayoutModified layout, final int m, final int n, final DoublePointer A, final int lda, final IntPointer ipiv) {
        return openblas.LAPACKE_dgetrf(layout.lapack(), m, n, A, lda, ipiv);
    }
    
    @Override
    public int getrf(final LayoutModified layout, final int m, final int n, final float[] A, final int lda, final int[] ipiv) {
        return openblas.LAPACKE_sgetrf(layout.lapack(), m, n, A, lda, ipiv);
    }
    
    @Override
    public int getrf(final LayoutModified layout, final int m, final int n, final FloatBuffer A, final int lda, final IntBuffer ipiv) {
        return openblas.LAPACKE_sgetrf(layout.lapack(), m, n, A, lda, ipiv);
    }
    
    @Override
    public int getrf2(final LayoutModified layout, final int m, final int n, final double[] A, final int lda, final int[] ipiv) {
        return openblas.LAPACKE_dgetrf2(layout.lapack(), m, n, A, lda, ipiv);
    }
    
    @Override
    public int getrf2(final LayoutModified layout, final int m, final int n, final DoubleBuffer A, final int lda, final IntBuffer ipiv) {
        return openblas.LAPACKE_dgetrf2(layout.lapack(), m, n, A, lda, ipiv);
    }
    
    @Override
    public int getrf2(final LayoutModified layout, final int m, final int n, final float[] A, final int lda, final int[] ipiv) {
        return openblas.LAPACKE_sgetrf2(layout.lapack(), m, n, A, lda, ipiv);
    }
    
    @Override
    public int getrf2(final LayoutModified layout, final int m, final int n, final FloatBuffer A, final int lda, final IntBuffer ipiv) {
        return openblas.LAPACKE_sgetrf2(layout.lapack(), m, n, A, lda, ipiv);
    }
    
    @Override
    public int gbtrf(final LayoutModified layout, final int m, final int n, final int kl, final int ku, final double[] AB, final int ldab, final int[] ipiv) {
        return openblas.LAPACKE_dgbtrf(layout.lapack(), m, n, kl, ku, AB, ldab, ipiv);
    }
    
    @Override
    public int gbtrf(final LayoutModified layout, final int m, final int n, final int kl, final int ku, final DoubleBuffer AB, final int ldab, final IntBuffer ipiv) {
        return openblas.LAPACKE_dgbtrf(layout.lapack(), m, n, kl, ku, AB, ldab, ipiv);
    }
    
    @Override
    public int gbtrf(final LayoutModified layout, final int m, final int n, final int kl, final int ku, final float[] AB, final int ldab, final int[] ipiv) {
        return openblas.LAPACKE_sgbtrf(layout.lapack(), m, n, kl, ku, AB, ldab, ipiv);
    }
    
    @Override
    public int gbtrf(final LayoutModified layout, final int m, final int n, final int kl, final int ku, final FloatBuffer AB, final int ldab, final IntBuffer ipiv) {
        return openblas.LAPACKE_sgbtrf(layout.lapack(), m, n, kl, ku, AB, ldab, ipiv);
    }
    
    @Override
    public int sptrf(final LayoutModified layout, final UPLOModified uplo, final int n, final double[] AP, final int[] ipiv) {
        return openblas.LAPACKE_dsptrf(layout.lapack(), uplo.lapack(), n, AP, ipiv);
    }
    
    @Override
    public int sptrf(final LayoutModified layout, final UPLOModified uplo, final int n, final DoubleBuffer AP, final IntBuffer ipiv) {
        return openblas.LAPACKE_dsptrf(layout.lapack(), uplo.lapack(), n, AP, ipiv);
    }
    
    @Override
    public int sptrf(final LayoutModified layout, final UPLOModified uplo, final int n, final float[] AP, final int[] ipiv) {
        return openblas.LAPACKE_ssptrf(layout.lapack(), uplo.lapack(), n, AP, ipiv);
    }
    
    @Override
    public int sptrf(final LayoutModified layout, final UPLOModified uplo, final int n, final FloatBuffer AP, final IntBuffer ipiv) {
        return openblas.LAPACKE_ssptrf(layout.lapack(), uplo.lapack(), n, AP, ipiv);
    }
    
    @Override
    public int getrs(final LayoutModified layout, final TransposeModified trans, final int n, final int nrhs, final double[] A, final int lda, final int[] ipiv, final double[] B, final int ldb) {
        return openblas.LAPACKE_dgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int getrs(final LayoutModified layout, final TransposeModified trans, final int n, final int nrhs, final DoubleBuffer A, final int lda, final IntBuffer ipiv, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int getrs(final LayoutModified layout, final TransposeModified trans, final int n, final int nrhs, final DoublePointer A, final int lda, final IntPointer ipiv, final DoublePointer B, final int ldb) {
        return openblas.LAPACKE_dgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int getrs(final LayoutModified layout, final TransposeModified trans, final int n, final int nrhs, final float[] A, final int lda, final int[] ipiv, final float[] B, final int ldb) {
        return openblas.LAPACKE_sgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int getrs(final LayoutModified layout, final TransposeModified trans, final int n, final int nrhs, final FloatBuffer A, final int lda, final IntBuffer ipiv, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_sgetrs(layout.lapack(), trans.lapack(), n, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int gbtrs(final LayoutModified layout, final TransposeModified trans, final int n, final int kl, final int ku, final int nrhs, final double[] A, final int lda, final int[] ipiv, final double[] B, final int ldb) {
        return openblas.LAPACKE_dgbtrs(layout.lapack(), trans.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int gbtrs(final LayoutModified layout, final TransposeModified trans, final int n, final int kl, final int ku, final int nrhs, final DoubleBuffer A, final int lda, final IntBuffer ipiv, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dgbtrs(layout.lapack(), trans.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int gbtrs(final LayoutModified layout, final TransposeModified trans, final int n, final int kl, final int ku, final int nrhs, final float[] A, final int lda, final int[] ipiv, final float[] B, final int ldb) {
        return openblas.LAPACKE_sgbtrs(layout.lapack(), trans.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int gbtrs(final LayoutModified layout, final TransposeModified trans, final int n, final int kl, final int ku, final int nrhs, final FloatBuffer A, final int lda, final IntBuffer ipiv, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_sgbtrs(layout.lapack(), trans.lapack(), n, kl, ku, nrhs, A, lda, ipiv, B, ldb);
    }
    
    @Override
    public int sptrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final double[] AP, final int[] ipiv, final double[] B, final int ldb) {
        return openblas.LAPACKE_dsptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, ipiv, B, ldb);
    }
    
    @Override
    public int sptrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final DoubleBuffer AP, final IntBuffer ipiv, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dsptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, ipiv, B, ldb);
    }
    
    @Override
    public int sptrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final float[] AP, final int[] ipiv, final float[] B, final int ldb) {
        return openblas.LAPACKE_ssptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, ipiv, B, ldb);
    }
    
    @Override
    public int sptrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final FloatBuffer AP, final IntBuffer ipiv, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_ssptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, ipiv, B, ldb);
    }
    
    @Override
    public int potrf(final LayoutModified layout, final UPLOModified uplo, final int n, final double[] A, final int lda) {
        return openblas.LAPACKE_dpotrf(layout.lapack(), uplo.lapack(), n, A, lda);
    }
    
    @Override
    public int potrf(final LayoutModified layout, final UPLOModified uplo, final int n, final DoubleBuffer A, final int lda) {
        return openblas.LAPACKE_dpotrf(layout.lapack(), uplo.lapack(), n, A, lda);
    }
    
    @Override
    public int potrf(final LayoutModified layout, final UPLOModified uplo, final int n, final DoublePointer A, final int lda) {
        return openblas.LAPACKE_dpotrf(layout.lapack(), uplo.lapack(), n, A, lda);
    }
    
    @Override
    public int potrf(final LayoutModified layout, final UPLOModified uplo, final int n, final float[] A, final int lda) {
        return openblas.LAPACKE_spotrf(layout.lapack(), uplo.lapack(), n, A, lda);
    }
    
    @Override
    public int potrf(final LayoutModified layout, final UPLOModified uplo, final int n, final FloatBuffer A, final int lda) {
        return openblas.LAPACKE_spotrf(layout.lapack(), uplo.lapack(), n, A, lda);
    }
    
    @Override
    public int potrf2(final LayoutModified layout, final UPLOModified uplo, final int n, final double[] A, final int lda) {
        return openblas.LAPACKE_dpotrf2(layout.lapack(), uplo.lapack(), n, A, lda);
    }
    
    @Override
    public int potrf2(final LayoutModified layout, final UPLOModified uplo, final int n, final DoubleBuffer A, final int lda) {
        return openblas.LAPACKE_dpotrf2(layout.lapack(), uplo.lapack(), n, A, lda);
    }
    
    @Override
    public int potrf2(final LayoutModified layout, final UPLOModified uplo, final int n, final float[] A, final int lda) {
        return openblas.LAPACKE_spotrf2(layout.lapack(), uplo.lapack(), n, A, lda);
    }
    
    @Override
    public int potrf2(final LayoutModified layout, final UPLOModified uplo, final int n, final FloatBuffer A, final int lda) {
        return openblas.LAPACKE_spotrf2(layout.lapack(), uplo.lapack(), n, A, lda);
    }
    
    @Override
    public int pbtrf(final LayoutModified layout, final UPLOModified uplo, final int n, final int kd, final double[] AB, final int ldab) {
        return openblas.LAPACKE_dpbtrf(layout.lapack(), uplo.lapack(), n, kd, AB, ldab);
    }
    
    @Override
    public int pbtrf(final LayoutModified layout, final UPLOModified uplo, final int n, final int kd, final DoubleBuffer AB, final int ldab) {
        return openblas.LAPACKE_dpbtrf(layout.lapack(), uplo.lapack(), n, kd, AB, ldab);
    }
    
    @Override
    public int pbtrf(final LayoutModified layout, final UPLOModified uplo, final int n, final int kd, final float[] AB, final int ldab) {
        return openblas.LAPACKE_spbtrf(layout.lapack(), uplo.lapack(), n, kd, AB, ldab);
    }
    
    @Override
    public int pbtrf(final LayoutModified layout, final UPLOModified uplo, final int n, final int kd, final FloatBuffer AB, final int ldab) {
        return openblas.LAPACKE_spbtrf(layout.lapack(), uplo.lapack(), n, kd, AB, ldab);
    }
    
    @Override
    public int pptrf(final LayoutModified layout, final UPLOModified uplo, final int n, final double[] AP) {
        return openblas.LAPACKE_dpptrf(layout.lapack(), uplo.lapack(), n, AP);
    }
    
    @Override
    public int pptrf(final LayoutModified layout, final UPLOModified uplo, final int n, final DoubleBuffer AP) {
        return openblas.LAPACKE_dpptrf(layout.lapack(), uplo.lapack(), n, AP);
    }
    
    @Override
    public int pptrf(final LayoutModified layout, final UPLOModified uplo, final int n, final float[] AP) {
        return openblas.LAPACKE_spptrf(layout.lapack(), uplo.lapack(), n, AP);
    }
    
    @Override
    public int pptrf(final LayoutModified layout, final UPLOModified uplo, final int n, final FloatBuffer AP) {
        return openblas.LAPACKE_spptrf(layout.lapack(), uplo.lapack(), n, AP);
    }
    
    @Override
    public int potrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final double[] A, final int lda, final double[] B, final int ldb) {
        return openblas.LAPACKE_dpotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int potrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final DoubleBuffer A, final int lda, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dpotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int potrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final DoublePointer A, final int lda, final DoublePointer B, final int ldb) {
        return openblas.LAPACKE_dpotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int potrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final float[] A, final int lda, final float[] B, final int ldb) {
        return openblas.LAPACKE_spotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int potrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final FloatBuffer A, final int lda, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_spotrs(layout.lapack(), uplo.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int pbtrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int kd, final int nrhs, final double[] AB, final int ldab, final double[] B, final int ldb) {
        return openblas.LAPACKE_dpbtrs(layout.lapack(), uplo.lapack(), n, kd, nrhs, AB, ldab, B, ldb);
    }
    
    @Override
    public int pbtrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int kd, final int nrhs, final DoubleBuffer AB, final int ldab, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dpbtrs(layout.lapack(), uplo.lapack(), n, kd, nrhs, AB, ldab, B, ldb);
    }
    
    @Override
    public int pbtrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int kd, final int nrhs, final float[] AB, final int ldab, final float[] B, final int ldb) {
        return openblas.LAPACKE_spbtrs(layout.lapack(), uplo.lapack(), n, kd, nrhs, AB, ldab, B, ldb);
    }
    
    @Override
    public int pbtrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int kd, final int nrhs, final FloatBuffer AB, final int ldab, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_spbtrs(layout.lapack(), uplo.lapack(), n, kd, nrhs, AB, ldab, B, ldb);
    }
    
    @Override
    public int pptrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final double[] AP, final double[] B, final int ldb) {
        return openblas.LAPACKE_dpptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, B, ldb);
    }
    
    @Override
    public int pptrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final DoubleBuffer AP, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dpptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, B, ldb);
    }
    
    @Override
    public int pptrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final float[] AP, final float[] B, final int ldb) {
        return openblas.LAPACKE_spptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, B, ldb);
    }
    
    @Override
    public int pptrs(final LayoutModified layout, final UPLOModified uplo, final int n, final int nrhs, final FloatBuffer AP, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_spptrs(layout.lapack(), uplo.lapack(), n, nrhs, AP, B, ldb);
    }
    
    @Override
    public int geqrf(final LayoutModified layout, final int m, final int n, final double[] A, final int lda, final double[] tau) {
        return openblas.LAPACKE_dgeqrf(layout.lapack(), m, n, A, lda, tau);
    }
    
    @Override
    public int geqrf(final LayoutModified layout, final int m, final int n, final DoubleBuffer A, final int lda, final DoubleBuffer tau) {
        return openblas.LAPACKE_dgeqrf(layout.lapack(), m, n, A, lda, tau);
    }
    
    @Override
    public int geqrf(final LayoutModified layout, final int m, final int n, final DoublePointer A, final int lda, final DoublePointer tau) {
        return openblas.LAPACKE_dgeqrf(layout.lapack(), m, n, A, lda, tau);
    }
    
    @Override
    public int geqrf(final LayoutModified layout, final int m, final int n, final float[] A, final int lda, final float[] tau) {
        return openblas.LAPACKE_sgeqrf(layout.lapack(), m, n, A, lda, tau);
    }
    
    @Override
    public int geqrf(final LayoutModified layout, final int m, final int n, final FloatBuffer A, final int lda, final FloatBuffer tau) {
        return openblas.LAPACKE_sgeqrf(layout.lapack(), m, n, A, lda, tau);
    }
    
    @Override
    public int orgqr(final LayoutModified layout, final int m, final int n, final int k, final double[] A, final int lda, final double[] tau) {
        return openblas.LAPACKE_dorgqr(layout.lapack(), m, n, k, A, lda, tau);
    }
    
    @Override
    public int orgqr(final LayoutModified layout, final int m, final int n, final int k, final DoubleBuffer A, final int lda, final DoubleBuffer tau) {
        return openblas.LAPACKE_dorgqr(layout.lapack(), m, n, k, A, lda, tau);
    }
    
    @Override
    public int orgqr(final LayoutModified layout, final int m, final int n, final int k, final DoublePointer A, final int lda, final DoublePointer tau) {
        return openblas.LAPACKE_dorgqr(layout.lapack(), m, n, k, A, lda, tau);
    }
    
    @Override
    public int orgqr(final LayoutModified layout, final int m, final int n, final int k, final float[] A, final int lda, final float[] tau) {
        return openblas.LAPACKE_sorgqr(layout.lapack(), m, n, k, A, lda, tau);
    }
    
    @Override
    public int orgqr(final LayoutModified layout, final int m, final int n, final int k, final FloatBuffer A, final int lda, final FloatBuffer tau) {
        return openblas.LAPACKE_sorgqr(layout.lapack(), m, n, k, A, lda, tau);
    }
    
    @Override
    public int ormqr(final LayoutModified layout, final SideModified side, final TransposeModified trans, final int m, final int n, final int k, final double[] A, final int lda, final double[] tau, final double[] C, final int ldc) {
        return openblas.LAPACKE_dormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
    }
    
    @Override
    public int ormqr(final LayoutModified layout, final SideModified side, final TransposeModified trans, final int m, final int n, final int k, final DoubleBuffer A, final int lda, final DoubleBuffer tau, final DoubleBuffer C, final int ldc) {
        return openblas.LAPACKE_dormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
    }
    
    @Override
    public int ormqr(final LayoutModified layout, final SideModified side, final TransposeModified trans, final int m, final int n, final int k, final DoublePointer A, final int lda, final DoublePointer tau, final DoublePointer C, final int ldc) {
        return openblas.LAPACKE_dormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
    }
    
    @Override
    public int ormqr(final LayoutModified layout, final SideModified side, final TransposeModified trans, final int m, final int n, final int k, final float[] A, final int lda, final float[] tau, final float[] C, final int ldc) {
        return openblas.LAPACKE_sormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
    }
    
    @Override
    public int ormqr(final LayoutModified layout, final SideModified side, final TransposeModified trans, final int m, final int n, final int k, final FloatBuffer A, final int lda, final FloatBuffer tau, final FloatBuffer C, final int ldc) {
        return openblas.LAPACKE_sormqr(layout.lapack(), side.lapack(), trans.lapack(), m, n, k, A, lda, tau, C, ldc);
    }
    
    @Override
    public int trtrs(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final int nrhs, final double[] A, final int lda, final double[] B, final int ldb) {
        return openblas.LAPACKE_dtrtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int trtrs(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final int nrhs, final DoubleBuffer A, final int lda, final DoubleBuffer B, final int ldb) {
        return openblas.LAPACKE_dtrtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int trtrs(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final int nrhs, final DoublePointer A, final int lda, final DoublePointer B, final int ldb) {
        return openblas.LAPACKE_dtrtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int trtrs(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final int nrhs, final float[] A, final int lda, final float[] B, final int ldb) {
        return openblas.LAPACKE_strtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
    }
    
    @Override
    public int trtrs(final LayoutModified layout, final UPLOModified uplo, final TransposeModified trans, final DiagModified diag, final int n, final int nrhs, final FloatBuffer A, final int lda, final FloatBuffer B, final int ldb) {
        return openblas.LAPACKE_strtrs(layout.lapack(), uplo.lapack(), trans.lapack(), diag.lapack(), n, nrhs, A, lda, B, ldb);
    }
}
