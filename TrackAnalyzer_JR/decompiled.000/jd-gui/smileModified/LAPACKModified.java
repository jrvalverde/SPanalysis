/*    */ package smileModified;
/*    */ 
/*    */ import java.nio.DoubleBuffer;
/*    */ import java.nio.FloatBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import org.bytedeco.javacpp.DoublePointer;
/*    */ import org.bytedeco.javacpp.IntPointer;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface LAPACKModified
/*    */ {
/* 37 */   public static final LAPACKModified engine = getInstance();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static LAPACKModified getInstance() {
/* 44 */     LAPACKModified mkl = MKL();
/* 45 */     return (mkl != null) ? mkl : new OpenBLASModified();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static LAPACKModified MKL() {
/* 53 */     Logger logger = LoggerFactory.getLogger(LAPACKModified.class);
/*    */     
/*    */     try {
/* 56 */       Class<?> clazz = Class.forName("smile.math.blas.mkl.MKL");
/* 57 */       logger.info("smile-mkl module is available.");
/* 58 */       return clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/* 59 */     } catch (Exception e) {
/* 60 */       logger.debug("Failed to create MKL instance: ", e);
/*    */ 
/*    */       
/* 63 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   int gesv(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, int paramInt3, int[] paramArrayOfint, double[] paramArrayOfdouble2, int paramInt4);
/*    */   
/*    */   int gesv(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, int paramInt3, IntBuffer paramIntBuffer, DoubleBuffer paramDoubleBuffer2, int paramInt4);
/*    */   
/*    */   int gesv(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, DoublePointer paramDoublePointer1, int paramInt3, IntPointer paramIntPointer, DoublePointer paramDoublePointer2, int paramInt4);
/*    */   
/*    */   int gesv(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, int[] paramArrayOfint, float[] paramArrayOffloat2, int paramInt4);
/*    */   
/*    */   int gesv(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, int paramInt3, IntBuffer paramIntBuffer, FloatBuffer paramFloatBuffer2, int paramInt4);
/*    */   
/*    */   int sysv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, int paramInt3, int[] paramArrayOfint, double[] paramArrayOfdouble2, int paramInt4);
/*    */   
/*    */   int sysv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, int paramInt3, IntBuffer paramIntBuffer, DoubleBuffer paramDoubleBuffer2, int paramInt4);
/*    */   
/*    */   int sysv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, DoublePointer paramDoublePointer1, int paramInt3, IntPointer paramIntPointer, DoublePointer paramDoublePointer2, int paramInt4);
/*    */   
/*    */   int sysv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, int[] paramArrayOfint, float[] paramArrayOffloat2, int paramInt4);
/*    */   
/*    */   int sysv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, int paramInt3, IntBuffer paramIntBuffer, FloatBuffer paramFloatBuffer2, int paramInt4);
/*    */   
/*    */   int spsv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, int[] paramArrayOfint, double[] paramArrayOfdouble2, int paramInt3);
/*    */   
/*    */   int spsv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, IntBuffer paramIntBuffer, DoubleBuffer paramDoubleBuffer2, int paramInt3);
/*    */   
/*    */   int spsv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, float[] paramArrayOffloat1, int[] paramArrayOfint, float[] paramArrayOffloat2, int paramInt3);
/*    */   
/*    */   int spsv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, IntBuffer paramIntBuffer, FloatBuffer paramFloatBuffer2, int paramInt3);
/*    */   
/*    */   int posv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, int paramInt3, double[] paramArrayOfdouble2, int paramInt4);
/*    */   
/*    */   int posv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, int paramInt3, DoubleBuffer paramDoubleBuffer2, int paramInt4);
/*    */   
/*    */   int posv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, int paramInt4);
/*    */   
/*    */   int posv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, int paramInt3, FloatBuffer paramFloatBuffer2, int paramInt4);
/*    */   
/*    */   int ppsv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, int paramInt3);
/*    */   
/*    */   int ppsv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, DoubleBuffer paramDoubleBuffer2, int paramInt3);
/*    */   
/*    */   int ppsv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, float[] paramArrayOffloat1, float[] paramArrayOffloat2, int paramInt3);
/*    */   
/*    */   int ppsv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, FloatBuffer paramFloatBuffer2, int paramInt3);
/*    */   
/*    */   int gbsv(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double[] paramArrayOfdouble1, int paramInt5, int[] paramArrayOfint, double[] paramArrayOfdouble2, int paramInt6);
/*    */   
/*    */   int gbsv(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, DoubleBuffer paramDoubleBuffer1, int paramInt5, IntBuffer paramIntBuffer, DoubleBuffer paramDoubleBuffer2, int paramInt6);
/*    */   
/*    */   int gbsv(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat1, int paramInt5, int[] paramArrayOfint, float[] paramArrayOffloat2, int paramInt6);
/*    */   
/*    */   int gbsv(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, FloatBuffer paramFloatBuffer1, int paramInt5, IntBuffer paramIntBuffer, FloatBuffer paramFloatBuffer2, int paramInt6);
/*    */   
/*    */   int gels(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfdouble1, int paramInt4, double[] paramArrayOfdouble2, int paramInt5);
/*    */   
/*    */   int gels(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, DoubleBuffer paramDoubleBuffer1, int paramInt4, DoubleBuffer paramDoubleBuffer2, int paramInt5);
/*    */   
/*    */   int gels(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat1, int paramInt4, float[] paramArrayOffloat2, int paramInt5);
/*    */   
/*    */   int gels(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, FloatBuffer paramFloatBuffer1, int paramInt4, FloatBuffer paramFloatBuffer2, int paramInt5);
/*    */   
/*    */   int gelsy(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfdouble1, int paramInt4, double[] paramArrayOfdouble2, int paramInt5, int[] paramArrayOfint1, double paramDouble, int[] paramArrayOfint2);
/*    */   
/*    */   int gelsy(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, DoubleBuffer paramDoubleBuffer1, int paramInt4, DoubleBuffer paramDoubleBuffer2, int paramInt5, IntBuffer paramIntBuffer1, double paramDouble, IntBuffer paramIntBuffer2);
/*    */   
/*    */   int gelsy(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat1, int paramInt4, float[] paramArrayOffloat2, int paramInt5, int[] paramArrayOfint1, float paramFloat, int[] paramArrayOfint2);
/*    */   
/*    */   int gelsy(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, FloatBuffer paramFloatBuffer1, int paramInt4, FloatBuffer paramFloatBuffer2, int paramInt5, IntBuffer paramIntBuffer1, float paramFloat, IntBuffer paramIntBuffer2);
/*    */   
/*    */   int gelss(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfdouble1, int paramInt4, double[] paramArrayOfdouble2, int paramInt5, double[] paramArrayOfdouble3, double paramDouble, int[] paramArrayOfint);
/*    */   
/*    */   int gelss(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, DoubleBuffer paramDoubleBuffer1, int paramInt4, DoubleBuffer paramDoubleBuffer2, int paramInt5, DoubleBuffer paramDoubleBuffer3, double paramDouble, IntBuffer paramIntBuffer);
/*    */   
/*    */   int gelss(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat1, int paramInt4, float[] paramArrayOffloat2, int paramInt5, float[] paramArrayOffloat3, float paramFloat, int[] paramArrayOfint);
/*    */   
/*    */   int gelss(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, FloatBuffer paramFloatBuffer1, int paramInt4, FloatBuffer paramFloatBuffer2, int paramInt5, FloatBuffer paramFloatBuffer3, float paramFloat, IntBuffer paramIntBuffer);
/*    */   
/*    */   int gelsd(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfdouble1, int paramInt4, double[] paramArrayOfdouble2, int paramInt5, double[] paramArrayOfdouble3, double paramDouble, int[] paramArrayOfint);
/*    */   
/*    */   int gelsd(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, DoubleBuffer paramDoubleBuffer1, int paramInt4, DoubleBuffer paramDoubleBuffer2, int paramInt5, DoubleBuffer paramDoubleBuffer3, double paramDouble, IntBuffer paramIntBuffer);
/*    */   
/*    */   int gelsd(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat1, int paramInt4, float[] paramArrayOffloat2, int paramInt5, float[] paramArrayOffloat3, float paramFloat, int[] paramArrayOfint);
/*    */   
/*    */   int gelsd(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, FloatBuffer paramFloatBuffer1, int paramInt4, FloatBuffer paramFloatBuffer2, int paramInt5, FloatBuffer paramFloatBuffer3, float paramFloat, IntBuffer paramIntBuffer);
/*    */   
/*    */   int gglse(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfdouble1, int paramInt4, double[] paramArrayOfdouble2, int paramInt5, double[] paramArrayOfdouble3, double[] paramArrayOfdouble4, double[] paramArrayOfdouble5);
/*    */   
/*    */   int gglse(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, DoubleBuffer paramDoubleBuffer1, int paramInt4, DoubleBuffer paramDoubleBuffer2, int paramInt5, DoubleBuffer paramDoubleBuffer3, DoubleBuffer paramDoubleBuffer4, DoubleBuffer paramDoubleBuffer5);
/*    */   
/*    */   int gglse(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat1, int paramInt4, float[] paramArrayOffloat2, int paramInt5, float[] paramArrayOffloat3, float[] paramArrayOffloat4, float[] paramArrayOffloat5);
/*    */   
/*    */   int gglse(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, FloatBuffer paramFloatBuffer1, int paramInt4, FloatBuffer paramFloatBuffer2, int paramInt5, FloatBuffer paramFloatBuffer3, FloatBuffer paramFloatBuffer4, FloatBuffer paramFloatBuffer5);
/*    */   
/*    */   int ggglm(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfdouble1, int paramInt4, double[] paramArrayOfdouble2, int paramInt5, double[] paramArrayOfdouble3, double[] paramArrayOfdouble4, double[] paramArrayOfdouble5);
/*    */   
/*    */   int ggglm(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, DoubleBuffer paramDoubleBuffer1, int paramInt4, DoubleBuffer paramDoubleBuffer2, int paramInt5, DoubleBuffer paramDoubleBuffer3, DoubleBuffer paramDoubleBuffer4, DoubleBuffer paramDoubleBuffer5);
/*    */   
/*    */   int ggglm(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat1, int paramInt4, float[] paramArrayOffloat2, int paramInt5, float[] paramArrayOffloat3, float[] paramArrayOffloat4, float[] paramArrayOffloat5);
/*    */   
/*    */   int ggglm(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, FloatBuffer paramFloatBuffer1, int paramInt4, FloatBuffer paramFloatBuffer2, int paramInt5, FloatBuffer paramFloatBuffer3, FloatBuffer paramFloatBuffer4, FloatBuffer paramFloatBuffer5);
/*    */   
/*    */   int geev(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified1, EVDJobModified paramEVDJobModified2, int paramInt1, double[] paramArrayOfdouble1, int paramInt2, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3, double[] paramArrayOfdouble4, int paramInt3, double[] paramArrayOfdouble5, int paramInt4);
/*    */   
/*    */   int geev(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified1, EVDJobModified paramEVDJobModified2, int paramInt1, DoubleBuffer paramDoubleBuffer1, int paramInt2, DoubleBuffer paramDoubleBuffer2, DoubleBuffer paramDoubleBuffer3, DoubleBuffer paramDoubleBuffer4, int paramInt3, DoubleBuffer paramDoubleBuffer5, int paramInt4);
/*    */   
/*    */   int geev(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified1, EVDJobModified paramEVDJobModified2, int paramInt1, DoublePointer paramDoublePointer1, int paramInt2, DoublePointer paramDoublePointer2, DoublePointer paramDoublePointer3, DoublePointer paramDoublePointer4, int paramInt3, DoublePointer paramDoublePointer5, int paramInt4);
/*    */   
/*    */   int geev(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified1, EVDJobModified paramEVDJobModified2, int paramInt1, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2, float[] paramArrayOffloat3, float[] paramArrayOffloat4, int paramInt3, float[] paramArrayOffloat5, int paramInt4);
/*    */   
/*    */   int geev(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified1, EVDJobModified paramEVDJobModified2, int paramInt1, FloatBuffer paramFloatBuffer1, int paramInt2, FloatBuffer paramFloatBuffer2, FloatBuffer paramFloatBuffer3, FloatBuffer paramFloatBuffer4, int paramInt3, FloatBuffer paramFloatBuffer5, int paramInt4);
/*    */   
/*    */   int syev(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, UPLOModified paramUPLOModified, int paramInt1, double[] paramArrayOfdouble1, int paramInt2, double[] paramArrayOfdouble2);
/*    */   
/*    */   int syev(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, UPLOModified paramUPLOModified, int paramInt1, DoubleBuffer paramDoubleBuffer1, int paramInt2, DoubleBuffer paramDoubleBuffer2);
/*    */   
/*    */   int syev(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, UPLOModified paramUPLOModified, int paramInt1, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2);
/*    */   
/*    */   int syev(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, UPLOModified paramUPLOModified, int paramInt1, FloatBuffer paramFloatBuffer1, int paramInt2, FloatBuffer paramFloatBuffer2);
/*    */   
/*    */   int syevd(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, UPLOModified paramUPLOModified, int paramInt1, double[] paramArrayOfdouble1, int paramInt2, double[] paramArrayOfdouble2);
/*    */   
/*    */   int syevd(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, UPLOModified paramUPLOModified, int paramInt1, DoubleBuffer paramDoubleBuffer1, int paramInt2, DoubleBuffer paramDoubleBuffer2);
/*    */   
/*    */   int syevd(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, UPLOModified paramUPLOModified, int paramInt1, DoublePointer paramDoublePointer1, int paramInt2, DoublePointer paramDoublePointer2);
/*    */   
/*    */   int syevd(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, UPLOModified paramUPLOModified, int paramInt1, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2);
/*    */   
/*    */   int syevd(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, UPLOModified paramUPLOModified, int paramInt1, FloatBuffer paramFloatBuffer1, int paramInt2, FloatBuffer paramFloatBuffer2);
/*    */   
/*    */   int syevr(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, EigenRangeModified paramEigenRangeModified, UPLOModified paramUPLOModified, int paramInt1, double[] paramArrayOfdouble1, int paramInt2, double paramDouble1, double paramDouble2, int paramInt3, int paramInt4, double paramDouble3, int[] paramArrayOfint1, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3, int paramInt5, int[] paramArrayOfint2);
/*    */   
/*    */   int syevr(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, EigenRangeModified paramEigenRangeModified, UPLOModified paramUPLOModified, int paramInt1, DoubleBuffer paramDoubleBuffer1, int paramInt2, double paramDouble1, double paramDouble2, int paramInt3, int paramInt4, double paramDouble3, IntBuffer paramIntBuffer1, DoubleBuffer paramDoubleBuffer2, DoubleBuffer paramDoubleBuffer3, int paramInt5, IntBuffer paramIntBuffer2);
/*    */   
/*    */   int syevr(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, EigenRangeModified paramEigenRangeModified, UPLOModified paramUPLOModified, int paramInt1, float[] paramArrayOffloat1, int paramInt2, float paramFloat1, float paramFloat2, int paramInt3, int paramInt4, float paramFloat3, int[] paramArrayOfint1, float[] paramArrayOffloat2, float[] paramArrayOffloat3, int paramInt5, int[] paramArrayOfint2);
/*    */   
/*    */   int syevr(LayoutModified paramLayoutModified, EVDJobModified paramEVDJobModified, EigenRangeModified paramEigenRangeModified, UPLOModified paramUPLOModified, int paramInt1, FloatBuffer paramFloatBuffer1, int paramInt2, float paramFloat1, float paramFloat2, int paramInt3, int paramInt4, float paramFloat3, IntBuffer paramIntBuffer1, FloatBuffer paramFloatBuffer2, FloatBuffer paramFloatBuffer3, int paramInt5, IntBuffer paramIntBuffer2);
/*    */   
/*    */   int gesvd(LayoutModified paramLayoutModified, SVDJobModified paramSVDJobModified1, SVDJobModified paramSVDJobModified2, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, int paramInt3, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3, int paramInt4, double[] paramArrayOfdouble4, int paramInt5, double[] paramArrayOfdouble5);
/*    */   
/*    */   int gesvd(LayoutModified paramLayoutModified, SVDJobModified paramSVDJobModified1, SVDJobModified paramSVDJobModified2, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, int paramInt3, DoubleBuffer paramDoubleBuffer2, DoubleBuffer paramDoubleBuffer3, int paramInt4, DoubleBuffer paramDoubleBuffer4, int paramInt5, DoubleBuffer paramDoubleBuffer5);
/*    */   
/*    */   int gesvd(LayoutModified paramLayoutModified, SVDJobModified paramSVDJobModified1, SVDJobModified paramSVDJobModified2, int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, float[] paramArrayOffloat3, int paramInt4, float[] paramArrayOffloat4, int paramInt5, float[] paramArrayOffloat5);
/*    */   
/*    */   int gesvd(LayoutModified paramLayoutModified, SVDJobModified paramSVDJobModified1, SVDJobModified paramSVDJobModified2, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, int paramInt3, FloatBuffer paramFloatBuffer2, FloatBuffer paramFloatBuffer3, int paramInt4, FloatBuffer paramFloatBuffer4, int paramInt5, FloatBuffer paramFloatBuffer5);
/*    */   
/*    */   int gesdd(LayoutModified paramLayoutModified, SVDJobModified paramSVDJobModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, int paramInt3, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3, int paramInt4, double[] paramArrayOfdouble4, int paramInt5);
/*    */   
/*    */   int gesdd(LayoutModified paramLayoutModified, SVDJobModified paramSVDJobModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, int paramInt3, DoubleBuffer paramDoubleBuffer2, DoubleBuffer paramDoubleBuffer3, int paramInt4, DoubleBuffer paramDoubleBuffer4, int paramInt5);
/*    */   
/*    */   int gesdd(LayoutModified paramLayoutModified, SVDJobModified paramSVDJobModified, int paramInt1, int paramInt2, DoublePointer paramDoublePointer1, int paramInt3, DoublePointer paramDoublePointer2, DoublePointer paramDoublePointer3, int paramInt4, DoublePointer paramDoublePointer4, int paramInt5);
/*    */   
/*    */   int gesdd(LayoutModified paramLayoutModified, SVDJobModified paramSVDJobModified, int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, float[] paramArrayOffloat3, int paramInt4, float[] paramArrayOffloat4, int paramInt5);
/*    */   
/*    */   int gesdd(LayoutModified paramLayoutModified, SVDJobModified paramSVDJobModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, int paramInt3, FloatBuffer paramFloatBuffer2, FloatBuffer paramFloatBuffer3, int paramInt4, FloatBuffer paramFloatBuffer4, int paramInt5);
/*    */   
/*    */   int getrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble, int paramInt3, int[] paramArrayOfint);
/*    */   
/*    */   int getrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer, int paramInt3, IntBuffer paramIntBuffer);
/*    */   
/*    */   int getrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, DoublePointer paramDoublePointer, int paramInt3, IntPointer paramIntPointer);
/*    */   
/*    */   int getrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3, int[] paramArrayOfint);
/*    */   
/*    */   int getrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer, int paramInt3, IntBuffer paramIntBuffer);
/*    */   
/*    */   int getrf2(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble, int paramInt3, int[] paramArrayOfint);
/*    */   
/*    */   int getrf2(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer, int paramInt3, IntBuffer paramIntBuffer);
/*    */   
/*    */   int getrf2(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3, int[] paramArrayOfint);
/*    */   
/*    */   int getrf2(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer, int paramInt3, IntBuffer paramIntBuffer);
/*    */   
/*    */   int gbtrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double[] paramArrayOfdouble, int paramInt5, int[] paramArrayOfint);
/*    */   
/*    */   int gbtrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, DoubleBuffer paramDoubleBuffer, int paramInt5, IntBuffer paramIntBuffer);
/*    */   
/*    */   int gbtrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat, int paramInt5, int[] paramArrayOfint);
/*    */   
/*    */   int gbtrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, FloatBuffer paramFloatBuffer, int paramInt5, IntBuffer paramIntBuffer);
/*    */   
/*    */   int sptrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt, double[] paramArrayOfdouble, int[] paramArrayOfint);
/*    */   
/*    */   int sptrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt, DoubleBuffer paramDoubleBuffer, IntBuffer paramIntBuffer);
/*    */   
/*    */   int sptrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt, float[] paramArrayOffloat, int[] paramArrayOfint);
/*    */   
/*    */   int sptrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt, FloatBuffer paramFloatBuffer, IntBuffer paramIntBuffer);
/*    */   
/*    */   int getrs(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, int paramInt3, int[] paramArrayOfint, double[] paramArrayOfdouble2, int paramInt4);
/*    */   
/*    */   int getrs(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, int paramInt3, IntBuffer paramIntBuffer, DoubleBuffer paramDoubleBuffer2, int paramInt4);
/*    */   
/*    */   int getrs(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, DoublePointer paramDoublePointer1, int paramInt3, IntPointer paramIntPointer, DoublePointer paramDoublePointer2, int paramInt4);
/*    */   
/*    */   int getrs(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, int[] paramArrayOfint, float[] paramArrayOffloat2, int paramInt4);
/*    */   
/*    */   int getrs(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, int paramInt3, IntBuffer paramIntBuffer, FloatBuffer paramFloatBuffer2, int paramInt4);
/*    */   
/*    */   int gbtrs(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double[] paramArrayOfdouble1, int paramInt5, int[] paramArrayOfint, double[] paramArrayOfdouble2, int paramInt6);
/*    */   
/*    */   int gbtrs(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, DoubleBuffer paramDoubleBuffer1, int paramInt5, IntBuffer paramIntBuffer, DoubleBuffer paramDoubleBuffer2, int paramInt6);
/*    */   
/*    */   int gbtrs(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOffloat1, int paramInt5, int[] paramArrayOfint, float[] paramArrayOffloat2, int paramInt6);
/*    */   
/*    */   int gbtrs(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, FloatBuffer paramFloatBuffer1, int paramInt5, IntBuffer paramIntBuffer, FloatBuffer paramFloatBuffer2, int paramInt6);
/*    */   
/*    */   int sptrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, int[] paramArrayOfint, double[] paramArrayOfdouble2, int paramInt3);
/*    */   
/*    */   int sptrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, IntBuffer paramIntBuffer, DoubleBuffer paramDoubleBuffer2, int paramInt3);
/*    */   
/*    */   int sptrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, IntBuffer paramIntBuffer, FloatBuffer paramFloatBuffer2, int paramInt3);
/*    */   
/*    */   int sptrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, float[] paramArrayOffloat1, int[] paramArrayOfint, float[] paramArrayOffloat2, int paramInt3);
/*    */   
/*    */   int potrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, double[] paramArrayOfdouble, int paramInt2);
/*    */   
/*    */   int potrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, DoubleBuffer paramDoubleBuffer, int paramInt2);
/*    */   
/*    */   int potrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, DoublePointer paramDoublePointer, int paramInt2);
/*    */   
/*    */   int potrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, float[] paramArrayOffloat, int paramInt2);
/*    */   
/*    */   int potrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, FloatBuffer paramFloatBuffer, int paramInt2);
/*    */   
/*    */   int potrf2(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, double[] paramArrayOfdouble, int paramInt2);
/*    */   
/*    */   int potrf2(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, DoubleBuffer paramDoubleBuffer, int paramInt2);
/*    */   
/*    */   int potrf2(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, float[] paramArrayOffloat, int paramInt2);
/*    */   
/*    */   int potrf2(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, FloatBuffer paramFloatBuffer, int paramInt2);
/*    */   
/*    */   int pbtrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble, int paramInt3);
/*    */   
/*    */   int pbtrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer, int paramInt3);
/*    */   
/*    */   int pbtrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, float[] paramArrayOffloat, int paramInt3);
/*    */   
/*    */   int pbtrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer, int paramInt3);
/*    */   
/*    */   int pptrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt, double[] paramArrayOfdouble);
/*    */   
/*    */   int pptrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt, DoubleBuffer paramDoubleBuffer);
/*    */   
/*    */   int pptrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt, float[] paramArrayOffloat);
/*    */   
/*    */   int pptrf(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt, FloatBuffer paramFloatBuffer);
/*    */   
/*    */   int potrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, int paramInt3, double[] paramArrayOfdouble2, int paramInt4);
/*    */   
/*    */   int potrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, int paramInt3, DoubleBuffer paramDoubleBuffer2, int paramInt4);
/*    */   
/*    */   int potrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, DoublePointer paramDoublePointer1, int paramInt3, DoublePointer paramDoublePointer2, int paramInt4);
/*    */   
/*    */   int potrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, int paramInt4);
/*    */   
/*    */   int potrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, int paramInt3, FloatBuffer paramFloatBuffer2, int paramInt4);
/*    */   
/*    */   int pbtrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfdouble1, int paramInt4, double[] paramArrayOfdouble2, int paramInt5);
/*    */   
/*    */   int pbtrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, int paramInt3, DoubleBuffer paramDoubleBuffer1, int paramInt4, DoubleBuffer paramDoubleBuffer2, int paramInt5);
/*    */   
/*    */   int pbtrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat1, int paramInt4, float[] paramArrayOffloat2, int paramInt5);
/*    */   
/*    */   int pbtrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, int paramInt3, FloatBuffer paramFloatBuffer1, int paramInt4, FloatBuffer paramFloatBuffer2, int paramInt5);
/*    */   
/*    */   int pptrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, int paramInt3);
/*    */   
/*    */   int pptrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, DoubleBuffer paramDoubleBuffer2, int paramInt3);
/*    */   
/*    */   int pptrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, float[] paramArrayOffloat1, float[] paramArrayOffloat2, int paramInt3);
/*    */   
/*    */   int pptrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, FloatBuffer paramFloatBuffer2, int paramInt3);
/*    */   
/*    */   int geqrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, int paramInt3, double[] paramArrayOfdouble2);
/*    */   
/*    */   int geqrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, int paramInt3, DoubleBuffer paramDoubleBuffer2);
/*    */   
/*    */   int geqrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, DoublePointer paramDoublePointer1, int paramInt3, DoublePointer paramDoublePointer2);
/*    */   
/*    */   int geqrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2);
/*    */   
/*    */   int geqrf(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, int paramInt3, FloatBuffer paramFloatBuffer2);
/*    */   
/*    */   int ormqr(LayoutModified paramLayoutModified, SideModified paramSideModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfdouble1, int paramInt4, double[] paramArrayOfdouble2, double[] paramArrayOfdouble3, int paramInt5);
/*    */   
/*    */   int ormqr(LayoutModified paramLayoutModified, SideModified paramSideModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, DoubleBuffer paramDoubleBuffer1, int paramInt4, DoubleBuffer paramDoubleBuffer2, DoubleBuffer paramDoubleBuffer3, int paramInt5);
/*    */   
/*    */   int ormqr(LayoutModified paramLayoutModified, SideModified paramSideModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, DoublePointer paramDoublePointer1, int paramInt4, DoublePointer paramDoublePointer2, DoublePointer paramDoublePointer3, int paramInt5);
/*    */   
/*    */   int ormqr(LayoutModified paramLayoutModified, SideModified paramSideModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat1, int paramInt4, float[] paramArrayOffloat2, float[] paramArrayOffloat3, int paramInt5);
/*    */   
/*    */   int ormqr(LayoutModified paramLayoutModified, SideModified paramSideModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, FloatBuffer paramFloatBuffer1, int paramInt4, FloatBuffer paramFloatBuffer2, FloatBuffer paramFloatBuffer3, int paramInt5);
/*    */   
/*    */   int orgqr(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfdouble1, int paramInt4, double[] paramArrayOfdouble2);
/*    */   
/*    */   int orgqr(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, DoubleBuffer paramDoubleBuffer1, int paramInt4, DoubleBuffer paramDoubleBuffer2);
/*    */   
/*    */   int orgqr(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, DoublePointer paramDoublePointer1, int paramInt4, DoublePointer paramDoublePointer2);
/*    */   
/*    */   int orgqr(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOffloat1, int paramInt4, float[] paramArrayOffloat2);
/*    */   
/*    */   int orgqr(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, int paramInt3, FloatBuffer paramFloatBuffer1, int paramInt4, FloatBuffer paramFloatBuffer2);
/*    */   
/*    */   int trtrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, int paramInt2, double[] paramArrayOfdouble1, int paramInt3, double[] paramArrayOfdouble2, int paramInt4);
/*    */   
/*    */   int trtrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, int paramInt2, DoubleBuffer paramDoubleBuffer1, int paramInt3, DoubleBuffer paramDoubleBuffer2, int paramInt4);
/*    */   
/*    */   int trtrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, int paramInt2, DoublePointer paramDoublePointer1, int paramInt3, DoublePointer paramDoublePointer2, int paramInt4);
/*    */   
/*    */   int trtrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, int paramInt2, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, int paramInt4);
/*    */   
/*    */   int trtrs(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, int paramInt2, FloatBuffer paramFloatBuffer1, int paramInt3, FloatBuffer paramFloatBuffer2, int paramInt4);
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/LAPACKModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */