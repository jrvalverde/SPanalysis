// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.nio.FloatBuffer;
import org.bytedeco.javacpp.DoublePointer;
import java.nio.DoubleBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface BLASModified
{
    public static final BLASModified engine = getInstance();
    
    default BLASModified getInstance() {
        final BLASModified mkl = MKL();
        return (mkl != null) ? mkl : new OpenBLASModified();
    }
    
    default BLASModified MKL() {
        final Logger logger = LoggerFactory.getLogger((Class)BLASModified.class);
        try {
            final Class<?> clazz = Class.forName("smile.math.blas.mkl.MKL");
            logger.info("smile-mkl module is available.");
            return (BLASModified)clazz.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            logger.debug("Failed to create MKL instance: ", (Throwable)e);
            return null;
        }
    }
    
    double asum(final int p0, final double[] p1, final int p2);
    
    float asum(final int p0, final float[] p1, final int p2);
    
    default double asum(final double[] x) {
        return this.asum(x.length, x, 1);
    }
    
    default float asum(final float[] x) {
        return this.asum(x.length, x, 1);
    }
    
    void axpy(final int p0, final double p1, final double[] p2, final int p3, final double[] p4, final int p5);
    
    void axpy(final int p0, final float p1, final float[] p2, final int p3, final float[] p4, final int p5);
    
    default void axpy(final double alpha, final double[] x, final double[] y) {
        this.axpy(x.length, alpha, x, 1, y, 1);
    }
    
    default void axpy(final float alpha, final float[] x, final float[] y) {
        this.axpy(x.length, alpha, x, 1, y, 1);
    }
    
    double dot(final int p0, final double[] p1, final int p2, final double[] p3, final int p4);
    
    float dot(final int p0, final float[] p1, final int p2, final float[] p3, final int p4);
    
    default double dot(final double[] x, final double[] y) {
        return this.dot(x.length, x, 1, y, 1);
    }
    
    default float dot(final float[] x, final float[] y) {
        return this.dot(x.length, x, 1, y, 1);
    }
    
    double nrm2(final int p0, final double[] p1, final int p2);
    
    float nrm2(final int p0, final float[] p1, final int p2);
    
    default double nrm2(final double[] x) {
        return this.nrm2(x.length, x, 1);
    }
    
    default float nrm2(final float[] x) {
        return this.nrm2(x.length, x, 1);
    }
    
    void scal(final int p0, final double p1, final double[] p2, final int p3);
    
    void scal(final int p0, final float p1, final float[] p2, final int p3);
    
    default void scal(final double alpha, final double[] x) {
        this.scal(x.length, alpha, x, 1);
    }
    
    default void scal(final float alpha, final float[] x) {
        this.scal(x.length, alpha, x, 1);
    }
    
    void swap(final int p0, final double[] p1, final int p2, final double[] p3, final int p4);
    
    void swap(final int p0, final float[] p1, final int p2, final float[] p3, final int p4);
    
    default void swap(final double[] x, final double[] y) {
        this.swap(x.length, x, 1, y, 1);
    }
    
    default void swap(final float[] x, final float[] y) {
        this.swap(x.length, x, 1, y, 1);
    }
    
    long iamax(final int p0, final double[] p1, final int p2);
    
    long iamax(final int p0, final float[] p1, final int p2);
    
    default long iamax(final double[] x) {
        return this.iamax(x.length, x, 1);
    }
    
    default long iamax(final float[] x) {
        return this.iamax(x.length, x, 1);
    }
    
    void gemv(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final double p4, final double[] p5, final int p6, final double[] p7, final int p8, final double p9, final double[] p10, final int p11);
    
    void gemv(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final double p4, final DoubleBuffer p5, final int p6, final DoubleBuffer p7, final int p8, final double p9, final DoubleBuffer p10, final int p11);
    
    void gemv(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final double p4, final DoublePointer p5, final int p6, final DoublePointer p7, final int p8, final double p9, final DoublePointer p10, final int p11);
    
    void gemv(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final float p4, final float[] p5, final int p6, final float[] p7, final int p8, final float p9, final float[] p10, final int p11);
    
    void gemv(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final float p4, final FloatBuffer p5, final int p6, final FloatBuffer p7, final int p8, final float p9, final FloatBuffer p10, final int p11);
    
    void symv(final LayoutModified p0, final UPLOModified p1, final int p2, final double p3, final double[] p4, final int p5, final double[] p6, final int p7, final double p8, final double[] p9, final int p10);
    
    void symv(final LayoutModified p0, final UPLOModified p1, final int p2, final double p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6, final int p7, final double p8, final DoubleBuffer p9, final int p10);
    
    void symv(final LayoutModified p0, final UPLOModified p1, final int p2, final double p3, final DoublePointer p4, final int p5, final DoublePointer p6, final int p7, final double p8, final DoublePointer p9, final int p10);
    
    void symv(final LayoutModified p0, final UPLOModified p1, final int p2, final float p3, final float[] p4, final int p5, final float[] p6, final int p7, final float p8, final float[] p9, final int p10);
    
    void symv(final LayoutModified p0, final UPLOModified p1, final int p2, final float p3, final FloatBuffer p4, final int p5, final FloatBuffer p6, final int p7, final float p8, final FloatBuffer p9, final int p10);
    
    void spmv(final LayoutModified p0, final UPLOModified p1, final int p2, final double p3, final double[] p4, final double[] p5, final int p6, final double p7, final double[] p8, final int p9);
    
    void spmv(final LayoutModified p0, final UPLOModified p1, final int p2, final double p3, final DoubleBuffer p4, final DoubleBuffer p5, final int p6, final double p7, final DoubleBuffer p8, final int p9);
    
    void spmv(final LayoutModified p0, final UPLOModified p1, final int p2, final float p3, final float[] p4, final float[] p5, final int p6, final float p7, final float[] p8, final int p9);
    
    void spmv(final LayoutModified p0, final UPLOModified p1, final int p2, final float p3, final FloatBuffer p4, final FloatBuffer p5, final int p6, final float p7, final FloatBuffer p8, final int p9);
    
    void trmv(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final double[] p5, final int p6, final double[] p7, final int p8);
    
    void trmv(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final DoubleBuffer p5, final int p6, final DoubleBuffer p7, final int p8);
    
    void trmv(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final DoublePointer p5, final int p6, final DoublePointer p7, final int p8);
    
    void trmv(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final float[] p5, final int p6, final float[] p7, final int p8);
    
    void trmv(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final FloatBuffer p5, final int p6, final FloatBuffer p7, final int p8);
    
    void tpmv(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final double[] p5, final double[] p6, final int p7);
    
    void tpmv(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final DoubleBuffer p5, final DoubleBuffer p6, final int p7);
    
    void tpmv(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final float[] p5, final float[] p6, final int p7);
    
    void tpmv(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final FloatBuffer p5, final FloatBuffer p6, final int p7);
    
    void gbmv(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final int p4, final int p5, final double p6, final double[] p7, final int p8, final double[] p9, final int p10, final double p11, final double[] p12, final int p13);
    
    void gbmv(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final int p4, final int p5, final double p6, final DoubleBuffer p7, final int p8, final DoubleBuffer p9, final int p10, final double p11, final DoubleBuffer p12, final int p13);
    
    void gbmv(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final int p4, final int p5, final float p6, final float[] p7, final int p8, final float[] p9, final int p10, final float p11, final float[] p12, final int p13);
    
    void gbmv(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final int p4, final int p5, final float p6, final FloatBuffer p7, final int p8, final FloatBuffer p9, final int p10, final float p11, final FloatBuffer p12, final int p13);
    
    void sbmv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final double p4, final double[] p5, final int p6, final double[] p7, final int p8, final double p9, final double[] p10, final int p11);
    
    void sbmv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final double p4, final DoubleBuffer p5, final int p6, final DoubleBuffer p7, final int p8, final double p9, final DoubleBuffer p10, final int p11);
    
    void sbmv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final float p4, final float[] p5, final int p6, final float[] p7, final int p8, final float p9, final float[] p10, final int p11);
    
    void sbmv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final float p4, final FloatBuffer p5, final int p6, final FloatBuffer p7, final int p8, final float p9, final FloatBuffer p10, final int p11);
    
    void ger(final LayoutModified p0, final int p1, final int p2, final double p3, final double[] p4, final int p5, final double[] p6, final int p7, final double[] p8, final int p9);
    
    void ger(final LayoutModified p0, final int p1, final int p2, final double p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6, final int p7, final DoubleBuffer p8, final int p9);
    
    void ger(final LayoutModified p0, final int p1, final int p2, final double p3, final DoublePointer p4, final int p5, final DoublePointer p6, final int p7, final DoublePointer p8, final int p9);
    
    void ger(final LayoutModified p0, final int p1, final int p2, final float p3, final float[] p4, final int p5, final float[] p6, final int p7, final float[] p8, final int p9);
    
    void ger(final LayoutModified p0, final int p1, final int p2, final float p3, final FloatBuffer p4, final int p5, final FloatBuffer p6, final int p7, final FloatBuffer p8, final int p9);
    
    void syr(final LayoutModified p0, final UPLOModified p1, final int p2, final double p3, final double[] p4, final int p5, final double[] p6, final int p7);
    
    void syr(final LayoutModified p0, final UPLOModified p1, final int p2, final double p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6, final int p7);
    
    void syr(final LayoutModified p0, final UPLOModified p1, final int p2, final double p3, final DoublePointer p4, final int p5, final DoublePointer p6, final int p7);
    
    void syr(final LayoutModified p0, final UPLOModified p1, final int p2, final float p3, final float[] p4, final int p5, final float[] p6, final int p7);
    
    void syr(final LayoutModified p0, final UPLOModified p1, final int p2, final float p3, final FloatBuffer p4, final int p5, final FloatBuffer p6, final int p7);
    
    void spr(final LayoutModified p0, final UPLOModified p1, final int p2, final double p3, final double[] p4, final int p5, final double[] p6);
    
    void spr(final LayoutModified p0, final UPLOModified p1, final int p2, final double p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6);
    
    void spr(final LayoutModified p0, final UPLOModified p1, final int p2, final float p3, final float[] p4, final int p5, final float[] p6);
    
    void spr(final LayoutModified p0, final UPLOModified p1, final int p2, final float p3, final FloatBuffer p4, final int p5, final FloatBuffer p6);
    
    void gemm(final LayoutModified p0, final TransposeModified p1, final TransposeModified p2, final int p3, final int p4, final int p5, final double p6, final double[] p7, final int p8, final double[] p9, final int p10, final double p11, final double[] p12, final int p13);
    
    void gemm(final LayoutModified p0, final TransposeModified p1, final TransposeModified p2, final int p3, final int p4, final int p5, final double p6, final DoubleBuffer p7, final int p8, final DoubleBuffer p9, final int p10, final double p11, final DoubleBuffer p12, final int p13);
    
    void gemm(final LayoutModified p0, final TransposeModified p1, final TransposeModified p2, final int p3, final int p4, final int p5, final double p6, final DoublePointer p7, final int p8, final DoublePointer p9, final int p10, final double p11, final DoublePointer p12, final int p13);
    
    void gemm(final LayoutModified p0, final TransposeModified p1, final TransposeModified p2, final int p3, final int p4, final int p5, final float p6, final float[] p7, final int p8, final float[] p9, final int p10, final float p11, final float[] p12, final int p13);
    
    void gemm(final LayoutModified p0, final TransposeModified p1, final TransposeModified p2, final int p3, final int p4, final int p5, final float p6, final FloatBuffer p7, final int p8, final FloatBuffer p9, final int p10, final float p11, final FloatBuffer p12, final int p13);
    
    void symm(final LayoutModified p0, final SideModified p1, final UPLOModified p2, final int p3, final int p4, final double p5, final double[] p6, final int p7, final double[] p8, final int p9, final double p10, final double[] p11, final int p12);
    
    void symm(final LayoutModified p0, final SideModified p1, final UPLOModified p2, final int p3, final int p4, final double p5, final DoubleBuffer p6, final int p7, final DoubleBuffer p8, final int p9, final double p10, final DoubleBuffer p11, final int p12);
    
    void symm(final LayoutModified p0, final SideModified p1, final UPLOModified p2, final int p3, final int p4, final double p5, final DoublePointer p6, final int p7, final DoublePointer p8, final int p9, final double p10, final DoublePointer p11, final int p12);
    
    void symm(final LayoutModified p0, final SideModified p1, final UPLOModified p2, final int p3, final int p4, final float p5, final float[] p6, final int p7, final float[] p8, final int p9, final float p10, final float[] p11, final int p12);
    
    void symm(final LayoutModified p0, final SideModified p1, final UPLOModified p2, final int p3, final int p4, final float p5, final FloatBuffer p6, final int p7, final FloatBuffer p8, final int p9, final float p10, final FloatBuffer p11, final int p12);
}
