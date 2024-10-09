// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.nio.FloatBuffer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.DoublePointer;
import java.nio.IntBuffer;
import java.nio.DoubleBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LAPACKModified
{
    public static final LAPACKModified engine = getInstance();
    
    default LAPACKModified getInstance() {
        final LAPACKModified mkl = MKL();
        return (mkl != null) ? mkl : new OpenBLASModified();
    }
    
    default LAPACKModified MKL() {
        final Logger logger = LoggerFactory.getLogger((Class)LAPACKModified.class);
        try {
            final Class<?> clazz = Class.forName("smile.math.blas.mkl.MKL");
            logger.info("smile-mkl module is available.");
            return (LAPACKModified)clazz.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            logger.debug("Failed to create MKL instance: ", (Throwable)e);
            return null;
        }
    }
    
    int gesv(final LayoutModified p0, final int p1, final int p2, final double[] p3, final int p4, final int[] p5, final double[] p6, final int p7);
    
    int gesv(final LayoutModified p0, final int p1, final int p2, final DoubleBuffer p3, final int p4, final IntBuffer p5, final DoubleBuffer p6, final int p7);
    
    int gesv(final LayoutModified p0, final int p1, final int p2, final DoublePointer p3, final int p4, final IntPointer p5, final DoublePointer p6, final int p7);
    
    int gesv(final LayoutModified p0, final int p1, final int p2, final float[] p3, final int p4, final int[] p5, final float[] p6, final int p7);
    
    int gesv(final LayoutModified p0, final int p1, final int p2, final FloatBuffer p3, final int p4, final IntBuffer p5, final FloatBuffer p6, final int p7);
    
    int sysv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final double[] p4, final int p5, final int[] p6, final double[] p7, final int p8);
    
    int sysv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final DoubleBuffer p4, final int p5, final IntBuffer p6, final DoubleBuffer p7, final int p8);
    
    int sysv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final DoublePointer p4, final int p5, final IntPointer p6, final DoublePointer p7, final int p8);
    
    int sysv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final float[] p4, final int p5, final int[] p6, final float[] p7, final int p8);
    
    int sysv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final FloatBuffer p4, final int p5, final IntBuffer p6, final FloatBuffer p7, final int p8);
    
    int spsv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final double[] p4, final int[] p5, final double[] p6, final int p7);
    
    int spsv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final DoubleBuffer p4, final IntBuffer p5, final DoubleBuffer p6, final int p7);
    
    int spsv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final float[] p4, final int[] p5, final float[] p6, final int p7);
    
    int spsv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final FloatBuffer p4, final IntBuffer p5, final FloatBuffer p6, final int p7);
    
    int posv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final double[] p4, final int p5, final double[] p6, final int p7);
    
    int posv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6, final int p7);
    
    int posv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final float[] p4, final int p5, final float[] p6, final int p7);
    
    int posv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final FloatBuffer p4, final int p5, final FloatBuffer p6, final int p7);
    
    int ppsv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final double[] p4, final double[] p5, final int p6);
    
    int ppsv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final DoubleBuffer p4, final DoubleBuffer p5, final int p6);
    
    int ppsv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final float[] p4, final float[] p5, final int p6);
    
    int ppsv(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final FloatBuffer p4, final FloatBuffer p5, final int p6);
    
    int gbsv(final LayoutModified p0, final int p1, final int p2, final int p3, final int p4, final double[] p5, final int p6, final int[] p7, final double[] p8, final int p9);
    
    int gbsv(final LayoutModified p0, final int p1, final int p2, final int p3, final int p4, final DoubleBuffer p5, final int p6, final IntBuffer p7, final DoubleBuffer p8, final int p9);
    
    int gbsv(final LayoutModified p0, final int p1, final int p2, final int p3, final int p4, final float[] p5, final int p6, final int[] p7, final float[] p8, final int p9);
    
    int gbsv(final LayoutModified p0, final int p1, final int p2, final int p3, final int p4, final FloatBuffer p5, final int p6, final IntBuffer p7, final FloatBuffer p8, final int p9);
    
    int gels(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final int p4, final double[] p5, final int p6, final double[] p7, final int p8);
    
    int gels(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final int p4, final DoubleBuffer p5, final int p6, final DoubleBuffer p7, final int p8);
    
    int gels(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final int p4, final float[] p5, final int p6, final float[] p7, final int p8);
    
    int gels(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final int p4, final FloatBuffer p5, final int p6, final FloatBuffer p7, final int p8);
    
    int gelsy(final LayoutModified p0, final int p1, final int p2, final int p3, final double[] p4, final int p5, final double[] p6, final int p7, final int[] p8, final double p9, final int[] p10);
    
    int gelsy(final LayoutModified p0, final int p1, final int p2, final int p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6, final int p7, final IntBuffer p8, final double p9, final IntBuffer p10);
    
    int gelsy(final LayoutModified p0, final int p1, final int p2, final int p3, final float[] p4, final int p5, final float[] p6, final int p7, final int[] p8, final float p9, final int[] p10);
    
    int gelsy(final LayoutModified p0, final int p1, final int p2, final int p3, final FloatBuffer p4, final int p5, final FloatBuffer p6, final int p7, final IntBuffer p8, final float p9, final IntBuffer p10);
    
    int gelss(final LayoutModified p0, final int p1, final int p2, final int p3, final double[] p4, final int p5, final double[] p6, final int p7, final double[] p8, final double p9, final int[] p10);
    
    int gelss(final LayoutModified p0, final int p1, final int p2, final int p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6, final int p7, final DoubleBuffer p8, final double p9, final IntBuffer p10);
    
    int gelss(final LayoutModified p0, final int p1, final int p2, final int p3, final float[] p4, final int p5, final float[] p6, final int p7, final float[] p8, final float p9, final int[] p10);
    
    int gelss(final LayoutModified p0, final int p1, final int p2, final int p3, final FloatBuffer p4, final int p5, final FloatBuffer p6, final int p7, final FloatBuffer p8, final float p9, final IntBuffer p10);
    
    int gelsd(final LayoutModified p0, final int p1, final int p2, final int p3, final double[] p4, final int p5, final double[] p6, final int p7, final double[] p8, final double p9, final int[] p10);
    
    int gelsd(final LayoutModified p0, final int p1, final int p2, final int p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6, final int p7, final DoubleBuffer p8, final double p9, final IntBuffer p10);
    
    int gelsd(final LayoutModified p0, final int p1, final int p2, final int p3, final float[] p4, final int p5, final float[] p6, final int p7, final float[] p8, final float p9, final int[] p10);
    
    int gelsd(final LayoutModified p0, final int p1, final int p2, final int p3, final FloatBuffer p4, final int p5, final FloatBuffer p6, final int p7, final FloatBuffer p8, final float p9, final IntBuffer p10);
    
    int gglse(final LayoutModified p0, final int p1, final int p2, final int p3, final double[] p4, final int p5, final double[] p6, final int p7, final double[] p8, final double[] p9, final double[] p10);
    
    int gglse(final LayoutModified p0, final int p1, final int p2, final int p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6, final int p7, final DoubleBuffer p8, final DoubleBuffer p9, final DoubleBuffer p10);
    
    int gglse(final LayoutModified p0, final int p1, final int p2, final int p3, final float[] p4, final int p5, final float[] p6, final int p7, final float[] p8, final float[] p9, final float[] p10);
    
    int gglse(final LayoutModified p0, final int p1, final int p2, final int p3, final FloatBuffer p4, final int p5, final FloatBuffer p6, final int p7, final FloatBuffer p8, final FloatBuffer p9, final FloatBuffer p10);
    
    int ggglm(final LayoutModified p0, final int p1, final int p2, final int p3, final double[] p4, final int p5, final double[] p6, final int p7, final double[] p8, final double[] p9, final double[] p10);
    
    int ggglm(final LayoutModified p0, final int p1, final int p2, final int p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6, final int p7, final DoubleBuffer p8, final DoubleBuffer p9, final DoubleBuffer p10);
    
    int ggglm(final LayoutModified p0, final int p1, final int p2, final int p3, final float[] p4, final int p5, final float[] p6, final int p7, final float[] p8, final float[] p9, final float[] p10);
    
    int ggglm(final LayoutModified p0, final int p1, final int p2, final int p3, final FloatBuffer p4, final int p5, final FloatBuffer p6, final int p7, final FloatBuffer p8, final FloatBuffer p9, final FloatBuffer p10);
    
    int geev(final LayoutModified p0, final EVDJobModified p1, final EVDJobModified p2, final int p3, final double[] p4, final int p5, final double[] p6, final double[] p7, final double[] p8, final int p9, final double[] p10, final int p11);
    
    int geev(final LayoutModified p0, final EVDJobModified p1, final EVDJobModified p2, final int p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6, final DoubleBuffer p7, final DoubleBuffer p8, final int p9, final DoubleBuffer p10, final int p11);
    
    int geev(final LayoutModified p0, final EVDJobModified p1, final EVDJobModified p2, final int p3, final DoublePointer p4, final int p5, final DoublePointer p6, final DoublePointer p7, final DoublePointer p8, final int p9, final DoublePointer p10, final int p11);
    
    int geev(final LayoutModified p0, final EVDJobModified p1, final EVDJobModified p2, final int p3, final float[] p4, final int p5, final float[] p6, final float[] p7, final float[] p8, final int p9, final float[] p10, final int p11);
    
    int geev(final LayoutModified p0, final EVDJobModified p1, final EVDJobModified p2, final int p3, final FloatBuffer p4, final int p5, final FloatBuffer p6, final FloatBuffer p7, final FloatBuffer p8, final int p9, final FloatBuffer p10, final int p11);
    
    int syev(final LayoutModified p0, final EVDJobModified p1, final UPLOModified p2, final int p3, final double[] p4, final int p5, final double[] p6);
    
    int syev(final LayoutModified p0, final EVDJobModified p1, final UPLOModified p2, final int p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6);
    
    int syev(final LayoutModified p0, final EVDJobModified p1, final UPLOModified p2, final int p3, final float[] p4, final int p5, final float[] p6);
    
    int syev(final LayoutModified p0, final EVDJobModified p1, final UPLOModified p2, final int p3, final FloatBuffer p4, final int p5, final FloatBuffer p6);
    
    int syevd(final LayoutModified p0, final EVDJobModified p1, final UPLOModified p2, final int p3, final double[] p4, final int p5, final double[] p6);
    
    int syevd(final LayoutModified p0, final EVDJobModified p1, final UPLOModified p2, final int p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6);
    
    int syevd(final LayoutModified p0, final EVDJobModified p1, final UPLOModified p2, final int p3, final DoublePointer p4, final int p5, final DoublePointer p6);
    
    int syevd(final LayoutModified p0, final EVDJobModified p1, final UPLOModified p2, final int p3, final float[] p4, final int p5, final float[] p6);
    
    int syevd(final LayoutModified p0, final EVDJobModified p1, final UPLOModified p2, final int p3, final FloatBuffer p4, final int p5, final FloatBuffer p6);
    
    int syevr(final LayoutModified p0, final EVDJobModified p1, final EigenRangeModified p2, final UPLOModified p3, final int p4, final double[] p5, final int p6, final double p7, final double p8, final int p9, final int p10, final double p11, final int[] p12, final double[] p13, final double[] p14, final int p15, final int[] p16);
    
    int syevr(final LayoutModified p0, final EVDJobModified p1, final EigenRangeModified p2, final UPLOModified p3, final int p4, final DoubleBuffer p5, final int p6, final double p7, final double p8, final int p9, final int p10, final double p11, final IntBuffer p12, final DoubleBuffer p13, final DoubleBuffer p14, final int p15, final IntBuffer p16);
    
    int syevr(final LayoutModified p0, final EVDJobModified p1, final EigenRangeModified p2, final UPLOModified p3, final int p4, final float[] p5, final int p6, final float p7, final float p8, final int p9, final int p10, final float p11, final int[] p12, final float[] p13, final float[] p14, final int p15, final int[] p16);
    
    int syevr(final LayoutModified p0, final EVDJobModified p1, final EigenRangeModified p2, final UPLOModified p3, final int p4, final FloatBuffer p5, final int p6, final float p7, final float p8, final int p9, final int p10, final float p11, final IntBuffer p12, final FloatBuffer p13, final FloatBuffer p14, final int p15, final IntBuffer p16);
    
    int gesvd(final LayoutModified p0, final SVDJobModified p1, final SVDJobModified p2, final int p3, final int p4, final double[] p5, final int p6, final double[] p7, final double[] p8, final int p9, final double[] p10, final int p11, final double[] p12);
    
    int gesvd(final LayoutModified p0, final SVDJobModified p1, final SVDJobModified p2, final int p3, final int p4, final DoubleBuffer p5, final int p6, final DoubleBuffer p7, final DoubleBuffer p8, final int p9, final DoubleBuffer p10, final int p11, final DoubleBuffer p12);
    
    int gesvd(final LayoutModified p0, final SVDJobModified p1, final SVDJobModified p2, final int p3, final int p4, final float[] p5, final int p6, final float[] p7, final float[] p8, final int p9, final float[] p10, final int p11, final float[] p12);
    
    int gesvd(final LayoutModified p0, final SVDJobModified p1, final SVDJobModified p2, final int p3, final int p4, final FloatBuffer p5, final int p6, final FloatBuffer p7, final FloatBuffer p8, final int p9, final FloatBuffer p10, final int p11, final FloatBuffer p12);
    
    int gesdd(final LayoutModified p0, final SVDJobModified p1, final int p2, final int p3, final double[] p4, final int p5, final double[] p6, final double[] p7, final int p8, final double[] p9, final int p10);
    
    int gesdd(final LayoutModified p0, final SVDJobModified p1, final int p2, final int p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6, final DoubleBuffer p7, final int p8, final DoubleBuffer p9, final int p10);
    
    int gesdd(final LayoutModified p0, final SVDJobModified p1, final int p2, final int p3, final DoublePointer p4, final int p5, final DoublePointer p6, final DoublePointer p7, final int p8, final DoublePointer p9, final int p10);
    
    int gesdd(final LayoutModified p0, final SVDJobModified p1, final int p2, final int p3, final float[] p4, final int p5, final float[] p6, final float[] p7, final int p8, final float[] p9, final int p10);
    
    int gesdd(final LayoutModified p0, final SVDJobModified p1, final int p2, final int p3, final FloatBuffer p4, final int p5, final FloatBuffer p6, final FloatBuffer p7, final int p8, final FloatBuffer p9, final int p10);
    
    int getrf(final LayoutModified p0, final int p1, final int p2, final double[] p3, final int p4, final int[] p5);
    
    int getrf(final LayoutModified p0, final int p1, final int p2, final DoubleBuffer p3, final int p4, final IntBuffer p5);
    
    int getrf(final LayoutModified p0, final int p1, final int p2, final DoublePointer p3, final int p4, final IntPointer p5);
    
    int getrf(final LayoutModified p0, final int p1, final int p2, final float[] p3, final int p4, final int[] p5);
    
    int getrf(final LayoutModified p0, final int p1, final int p2, final FloatBuffer p3, final int p4, final IntBuffer p5);
    
    int getrf2(final LayoutModified p0, final int p1, final int p2, final double[] p3, final int p4, final int[] p5);
    
    int getrf2(final LayoutModified p0, final int p1, final int p2, final DoubleBuffer p3, final int p4, final IntBuffer p5);
    
    int getrf2(final LayoutModified p0, final int p1, final int p2, final float[] p3, final int p4, final int[] p5);
    
    int getrf2(final LayoutModified p0, final int p1, final int p2, final FloatBuffer p3, final int p4, final IntBuffer p5);
    
    int gbtrf(final LayoutModified p0, final int p1, final int p2, final int p3, final int p4, final double[] p5, final int p6, final int[] p7);
    
    int gbtrf(final LayoutModified p0, final int p1, final int p2, final int p3, final int p4, final DoubleBuffer p5, final int p6, final IntBuffer p7);
    
    int gbtrf(final LayoutModified p0, final int p1, final int p2, final int p3, final int p4, final float[] p5, final int p6, final int[] p7);
    
    int gbtrf(final LayoutModified p0, final int p1, final int p2, final int p3, final int p4, final FloatBuffer p5, final int p6, final IntBuffer p7);
    
    int sptrf(final LayoutModified p0, final UPLOModified p1, final int p2, final double[] p3, final int[] p4);
    
    int sptrf(final LayoutModified p0, final UPLOModified p1, final int p2, final DoubleBuffer p3, final IntBuffer p4);
    
    int sptrf(final LayoutModified p0, final UPLOModified p1, final int p2, final float[] p3, final int[] p4);
    
    int sptrf(final LayoutModified p0, final UPLOModified p1, final int p2, final FloatBuffer p3, final IntBuffer p4);
    
    int getrs(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final double[] p4, final int p5, final int[] p6, final double[] p7, final int p8);
    
    int getrs(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final DoubleBuffer p4, final int p5, final IntBuffer p6, final DoubleBuffer p7, final int p8);
    
    int getrs(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final DoublePointer p4, final int p5, final IntPointer p6, final DoublePointer p7, final int p8);
    
    int getrs(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final float[] p4, final int p5, final int[] p6, final float[] p7, final int p8);
    
    int getrs(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final FloatBuffer p4, final int p5, final IntBuffer p6, final FloatBuffer p7, final int p8);
    
    int gbtrs(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final int p4, final int p5, final double[] p6, final int p7, final int[] p8, final double[] p9, final int p10);
    
    int gbtrs(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final int p4, final int p5, final DoubleBuffer p6, final int p7, final IntBuffer p8, final DoubleBuffer p9, final int p10);
    
    int gbtrs(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final int p4, final int p5, final float[] p6, final int p7, final int[] p8, final float[] p9, final int p10);
    
    int gbtrs(final LayoutModified p0, final TransposeModified p1, final int p2, final int p3, final int p4, final int p5, final FloatBuffer p6, final int p7, final IntBuffer p8, final FloatBuffer p9, final int p10);
    
    int sptrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final double[] p4, final int[] p5, final double[] p6, final int p7);
    
    int sptrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final DoubleBuffer p4, final IntBuffer p5, final DoubleBuffer p6, final int p7);
    
    int sptrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final FloatBuffer p4, final IntBuffer p5, final FloatBuffer p6, final int p7);
    
    int sptrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final float[] p4, final int[] p5, final float[] p6, final int p7);
    
    int potrf(final LayoutModified p0, final UPLOModified p1, final int p2, final double[] p3, final int p4);
    
    int potrf(final LayoutModified p0, final UPLOModified p1, final int p2, final DoubleBuffer p3, final int p4);
    
    int potrf(final LayoutModified p0, final UPLOModified p1, final int p2, final DoublePointer p3, final int p4);
    
    int potrf(final LayoutModified p0, final UPLOModified p1, final int p2, final float[] p3, final int p4);
    
    int potrf(final LayoutModified p0, final UPLOModified p1, final int p2, final FloatBuffer p3, final int p4);
    
    int potrf2(final LayoutModified p0, final UPLOModified p1, final int p2, final double[] p3, final int p4);
    
    int potrf2(final LayoutModified p0, final UPLOModified p1, final int p2, final DoubleBuffer p3, final int p4);
    
    int potrf2(final LayoutModified p0, final UPLOModified p1, final int p2, final float[] p3, final int p4);
    
    int potrf2(final LayoutModified p0, final UPLOModified p1, final int p2, final FloatBuffer p3, final int p4);
    
    int pbtrf(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final double[] p4, final int p5);
    
    int pbtrf(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final DoubleBuffer p4, final int p5);
    
    int pbtrf(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final float[] p4, final int p5);
    
    int pbtrf(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final FloatBuffer p4, final int p5);
    
    int pptrf(final LayoutModified p0, final UPLOModified p1, final int p2, final double[] p3);
    
    int pptrf(final LayoutModified p0, final UPLOModified p1, final int p2, final DoubleBuffer p3);
    
    int pptrf(final LayoutModified p0, final UPLOModified p1, final int p2, final float[] p3);
    
    int pptrf(final LayoutModified p0, final UPLOModified p1, final int p2, final FloatBuffer p3);
    
    int potrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final double[] p4, final int p5, final double[] p6, final int p7);
    
    int potrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6, final int p7);
    
    int potrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final DoublePointer p4, final int p5, final DoublePointer p6, final int p7);
    
    int potrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final float[] p4, final int p5, final float[] p6, final int p7);
    
    int potrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final FloatBuffer p4, final int p5, final FloatBuffer p6, final int p7);
    
    int pbtrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final int p4, final double[] p5, final int p6, final double[] p7, final int p8);
    
    int pbtrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final int p4, final DoubleBuffer p5, final int p6, final DoubleBuffer p7, final int p8);
    
    int pbtrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final int p4, final float[] p5, final int p6, final float[] p7, final int p8);
    
    int pbtrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final int p4, final FloatBuffer p5, final int p6, final FloatBuffer p7, final int p8);
    
    int pptrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final double[] p4, final double[] p5, final int p6);
    
    int pptrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final DoubleBuffer p4, final DoubleBuffer p5, final int p6);
    
    int pptrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final float[] p4, final float[] p5, final int p6);
    
    int pptrs(final LayoutModified p0, final UPLOModified p1, final int p2, final int p3, final FloatBuffer p4, final FloatBuffer p5, final int p6);
    
    int geqrf(final LayoutModified p0, final int p1, final int p2, final double[] p3, final int p4, final double[] p5);
    
    int geqrf(final LayoutModified p0, final int p1, final int p2, final DoubleBuffer p3, final int p4, final DoubleBuffer p5);
    
    int geqrf(final LayoutModified p0, final int p1, final int p2, final DoublePointer p3, final int p4, final DoublePointer p5);
    
    int geqrf(final LayoutModified p0, final int p1, final int p2, final float[] p3, final int p4, final float[] p5);
    
    int geqrf(final LayoutModified p0, final int p1, final int p2, final FloatBuffer p3, final int p4, final FloatBuffer p5);
    
    int ormqr(final LayoutModified p0, final SideModified p1, final TransposeModified p2, final int p3, final int p4, final int p5, final double[] p6, final int p7, final double[] p8, final double[] p9, final int p10);
    
    int ormqr(final LayoutModified p0, final SideModified p1, final TransposeModified p2, final int p3, final int p4, final int p5, final DoubleBuffer p6, final int p7, final DoubleBuffer p8, final DoubleBuffer p9, final int p10);
    
    int ormqr(final LayoutModified p0, final SideModified p1, final TransposeModified p2, final int p3, final int p4, final int p5, final DoublePointer p6, final int p7, final DoublePointer p8, final DoublePointer p9, final int p10);
    
    int ormqr(final LayoutModified p0, final SideModified p1, final TransposeModified p2, final int p3, final int p4, final int p5, final float[] p6, final int p7, final float[] p8, final float[] p9, final int p10);
    
    int ormqr(final LayoutModified p0, final SideModified p1, final TransposeModified p2, final int p3, final int p4, final int p5, final FloatBuffer p6, final int p7, final FloatBuffer p8, final FloatBuffer p9, final int p10);
    
    int orgqr(final LayoutModified p0, final int p1, final int p2, final int p3, final double[] p4, final int p5, final double[] p6);
    
    int orgqr(final LayoutModified p0, final int p1, final int p2, final int p3, final DoubleBuffer p4, final int p5, final DoubleBuffer p6);
    
    int orgqr(final LayoutModified p0, final int p1, final int p2, final int p3, final DoublePointer p4, final int p5, final DoublePointer p6);
    
    int orgqr(final LayoutModified p0, final int p1, final int p2, final int p3, final float[] p4, final int p5, final float[] p6);
    
    int orgqr(final LayoutModified p0, final int p1, final int p2, final int p3, final FloatBuffer p4, final int p5, final FloatBuffer p6);
    
    int trtrs(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final int p5, final double[] p6, final int p7, final double[] p8, final int p9);
    
    int trtrs(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final int p5, final DoubleBuffer p6, final int p7, final DoubleBuffer p8, final int p9);
    
    int trtrs(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final int p5, final DoublePointer p6, final int p7, final DoublePointer p8, final int p9);
    
    int trtrs(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final int p5, final float[] p6, final int p7, final float[] p8, final int p9);
    
    int trtrs(final LayoutModified p0, final UPLOModified p1, final TransposeModified p2, final DiagModified p3, final int p4, final int p5, final FloatBuffer p6, final int p7, final FloatBuffer p8, final int p9);
}
