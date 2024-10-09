/*     */ package smileModified;
/*     */ 
/*     */ import java.nio.DoubleBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import org.bytedeco.javacpp.DoublePointer;
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
/*     */ public interface BLASModified
/*     */ {
/*  35 */   public static final BLASModified engine = getInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static BLASModified getInstance() {
/*  42 */     BLASModified mkl = MKL();
/*  43 */     return (mkl != null) ? mkl : new OpenBLASModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static BLASModified MKL() {
/*  51 */     Logger logger = LoggerFactory.getLogger(BLASModified.class);
/*     */     
/*     */     try {
/*  54 */       Class<?> clazz = Class.forName("smile.math.blas.mkl.MKL");
/*  55 */       logger.info("smile-mkl module is available.");
/*  56 */       return clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
/*  57 */     } catch (Exception e) {
/*  58 */       logger.debug("Failed to create MKL instance: ", e);
/*     */ 
/*     */       
/*  61 */       return null;
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
/*     */   double asum(int paramInt1, double[] paramArrayOfdouble, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float asum(int paramInt1, float[] paramArrayOffloat, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default double asum(double[] x) {
/* 108 */     return asum(x.length, x, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default float asum(float[] x) {
/* 119 */     return asum(x.length, x, 1);
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
/*     */   void axpy(int paramInt1, double paramDouble, double[] paramArrayOfdouble1, int paramInt2, double[] paramArrayOfdouble2, int paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void axpy(int paramInt1, float paramFloat, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2, int paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void axpy(double alpha, double[] x, double[] y) {
/* 200 */     axpy(x.length, alpha, x, 1, y, 1);
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
/*     */   default void axpy(float alpha, float[] x, float[] y) {
/* 219 */     axpy(x.length, alpha, x, 1, y, 1);
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
/*     */   double dot(int paramInt1, double[] paramArrayOfdouble1, int paramInt2, double[] paramArrayOfdouble2, int paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float dot(int paramInt1, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2, int paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default double dot(double[] x, double[] y) {
/* 282 */     return dot(x.length, x, 1, y, 1);
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
/*     */   default float dot(float[] x, float[] y) {
/* 295 */     return dot(x.length, x, 1, y, 1);
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
/*     */   double nrm2(int paramInt1, double[] paramArrayOfdouble, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   float nrm2(int paramInt1, float[] paramArrayOffloat, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default double nrm2(double[] x) {
/* 336 */     return nrm2(x.length, x, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default float nrm2(float[] x) {
/* 347 */     return nrm2(x.length, x, 1);
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
/*     */   void scal(int paramInt1, double paramDouble, double[] paramArrayOfdouble, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void scal(int paramInt1, float paramFloat, float[] paramArrayOffloat, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void scal(double alpha, double[] x) {
/* 388 */     scal(x.length, alpha, x, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void scal(float alpha, float[] x) {
/* 399 */     scal(x.length, alpha, x, 1);
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
/*     */   void swap(int paramInt1, double[] paramArrayOfdouble1, int paramInt2, double[] paramArrayOfdouble2, int paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void swap(int paramInt1, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2, int paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void swap(double[] x, double[] y) {
/* 456 */     swap(x.length, x, 1, y, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void swap(float[] x, float[] y) {
/* 467 */     swap(x.length, x, 1, y, 1);
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
/*     */   long iamax(int paramInt1, double[] paramArrayOfdouble, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long iamax(int paramInt1, float[] paramArrayOffloat, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default long iamax(double[] x) {
/* 513 */     return iamax(x.length, x, 1);
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
/*     */   default long iamax(float[] x) {
/* 525 */     return iamax(x.length, x, 1);
/*     */   }
/*     */   
/*     */   void gemv(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, double paramDouble1, double[] paramArrayOfdouble1, int paramInt3, double[] paramArrayOfdouble2, int paramInt4, double paramDouble2, double[] paramArrayOfdouble3, int paramInt5);
/*     */   
/*     */   void gemv(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, double paramDouble1, DoubleBuffer paramDoubleBuffer1, int paramInt3, DoubleBuffer paramDoubleBuffer2, int paramInt4, double paramDouble2, DoubleBuffer paramDoubleBuffer3, int paramInt5);
/*     */   
/*     */   void gemv(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, double paramDouble1, DoublePointer paramDoublePointer1, int paramInt3, DoublePointer paramDoublePointer2, int paramInt4, double paramDouble2, DoublePointer paramDoublePointer3, int paramInt5);
/*     */   
/*     */   void gemv(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, float paramFloat1, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, int paramInt4, float paramFloat2, float[] paramArrayOffloat3, int paramInt5);
/*     */   
/*     */   void gemv(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, float paramFloat1, FloatBuffer paramFloatBuffer1, int paramInt3, FloatBuffer paramFloatBuffer2, int paramInt4, float paramFloat2, FloatBuffer paramFloatBuffer3, int paramInt5);
/*     */   
/*     */   void symv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, double paramDouble1, double[] paramArrayOfdouble1, int paramInt2, double[] paramArrayOfdouble2, int paramInt3, double paramDouble2, double[] paramArrayOfdouble3, int paramInt4);
/*     */   
/*     */   void symv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, double paramDouble1, DoubleBuffer paramDoubleBuffer1, int paramInt2, DoubleBuffer paramDoubleBuffer2, int paramInt3, double paramDouble2, DoubleBuffer paramDoubleBuffer3, int paramInt4);
/*     */   
/*     */   void symv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, double paramDouble1, DoublePointer paramDoublePointer1, int paramInt2, DoublePointer paramDoublePointer2, int paramInt3, double paramDouble2, DoublePointer paramDoublePointer3, int paramInt4);
/*     */   
/*     */   void symv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, float paramFloat1, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2, int paramInt3, float paramFloat2, float[] paramArrayOffloat3, int paramInt4);
/*     */   
/*     */   void symv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, float paramFloat1, FloatBuffer paramFloatBuffer1, int paramInt2, FloatBuffer paramFloatBuffer2, int paramInt3, float paramFloat2, FloatBuffer paramFloatBuffer3, int paramInt4);
/*     */   
/*     */   void spmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, double paramDouble1, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, int paramInt2, double paramDouble2, double[] paramArrayOfdouble3, int paramInt3);
/*     */   
/*     */   void spmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, double paramDouble1, DoubleBuffer paramDoubleBuffer1, DoubleBuffer paramDoubleBuffer2, int paramInt2, double paramDouble2, DoubleBuffer paramDoubleBuffer3, int paramInt3);
/*     */   
/*     */   void spmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, float paramFloat1, float[] paramArrayOffloat1, float[] paramArrayOffloat2, int paramInt2, float paramFloat2, float[] paramArrayOffloat3, int paramInt3);
/*     */   
/*     */   void spmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, float paramFloat1, FloatBuffer paramFloatBuffer1, FloatBuffer paramFloatBuffer2, int paramInt2, float paramFloat2, FloatBuffer paramFloatBuffer3, int paramInt3);
/*     */   
/*     */   void trmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, double[] paramArrayOfdouble1, int paramInt2, double[] paramArrayOfdouble2, int paramInt3);
/*     */   
/*     */   void trmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, DoubleBuffer paramDoubleBuffer1, int paramInt2, DoubleBuffer paramDoubleBuffer2, int paramInt3);
/*     */   
/*     */   void trmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, DoublePointer paramDoublePointer1, int paramInt2, DoublePointer paramDoublePointer2, int paramInt3);
/*     */   
/*     */   void trmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2, int paramInt3);
/*     */   
/*     */   void trmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, FloatBuffer paramFloatBuffer1, int paramInt2, FloatBuffer paramFloatBuffer2, int paramInt3);
/*     */   
/*     */   void tpmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, int paramInt2);
/*     */   
/*     */   void tpmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, DoubleBuffer paramDoubleBuffer1, DoubleBuffer paramDoubleBuffer2, int paramInt2);
/*     */   
/*     */   void tpmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, float[] paramArrayOffloat1, float[] paramArrayOffloat2, int paramInt2);
/*     */   
/*     */   void tpmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, TransposeModified paramTransposeModified, DiagModified paramDiagModified, int paramInt1, FloatBuffer paramFloatBuffer1, FloatBuffer paramFloatBuffer2, int paramInt2);
/*     */   
/*     */   void gbmv(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double[] paramArrayOfdouble1, int paramInt5, double[] paramArrayOfdouble2, int paramInt6, double paramDouble2, double[] paramArrayOfdouble3, int paramInt7);
/*     */   
/*     */   void gbmv(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, DoubleBuffer paramDoubleBuffer1, int paramInt5, DoubleBuffer paramDoubleBuffer2, int paramInt6, double paramDouble2, DoubleBuffer paramDoubleBuffer3, int paramInt7);
/*     */   
/*     */   void gbmv(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float[] paramArrayOffloat1, int paramInt5, float[] paramArrayOffloat2, int paramInt6, float paramFloat2, float[] paramArrayOffloat3, int paramInt7);
/*     */   
/*     */   void gbmv(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, FloatBuffer paramFloatBuffer1, int paramInt5, FloatBuffer paramFloatBuffer2, int paramInt6, float paramFloat2, FloatBuffer paramFloatBuffer3, int paramInt7);
/*     */   
/*     */   void sbmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double paramDouble1, double[] paramArrayOfdouble1, int paramInt3, double[] paramArrayOfdouble2, int paramInt4, double paramDouble2, double[] paramArrayOfdouble3, int paramInt5);
/*     */   
/*     */   void sbmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double paramDouble1, DoubleBuffer paramDoubleBuffer1, int paramInt3, DoubleBuffer paramDoubleBuffer2, int paramInt4, double paramDouble2, DoubleBuffer paramDoubleBuffer3, int paramInt5);
/*     */   
/*     */   void sbmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, float paramFloat1, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, int paramInt4, float paramFloat2, float[] paramArrayOffloat3, int paramInt5);
/*     */   
/*     */   void sbmv(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, float paramFloat1, FloatBuffer paramFloatBuffer1, int paramInt3, FloatBuffer paramFloatBuffer2, int paramInt4, float paramFloat2, FloatBuffer paramFloatBuffer3, int paramInt5);
/*     */   
/*     */   void ger(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, double paramDouble, double[] paramArrayOfdouble1, int paramInt3, double[] paramArrayOfdouble2, int paramInt4, double[] paramArrayOfdouble3, int paramInt5);
/*     */   
/*     */   void ger(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, double paramDouble, DoubleBuffer paramDoubleBuffer1, int paramInt3, DoubleBuffer paramDoubleBuffer2, int paramInt4, DoubleBuffer paramDoubleBuffer3, int paramInt5);
/*     */   
/*     */   void ger(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, double paramDouble, DoublePointer paramDoublePointer1, int paramInt3, DoublePointer paramDoublePointer2, int paramInt4, DoublePointer paramDoublePointer3, int paramInt5);
/*     */   
/*     */   void ger(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, float paramFloat, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, int paramInt4, float[] paramArrayOffloat3, int paramInt5);
/*     */   
/*     */   void ger(LayoutModified paramLayoutModified, int paramInt1, int paramInt2, float paramFloat, FloatBuffer paramFloatBuffer1, int paramInt3, FloatBuffer paramFloatBuffer2, int paramInt4, FloatBuffer paramFloatBuffer3, int paramInt5);
/*     */   
/*     */   void syr(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, double paramDouble, double[] paramArrayOfdouble1, int paramInt2, double[] paramArrayOfdouble2, int paramInt3);
/*     */   
/*     */   void syr(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, double paramDouble, DoubleBuffer paramDoubleBuffer1, int paramInt2, DoubleBuffer paramDoubleBuffer2, int paramInt3);
/*     */   
/*     */   void syr(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, double paramDouble, DoublePointer paramDoublePointer1, int paramInt2, DoublePointer paramDoublePointer2, int paramInt3);
/*     */   
/*     */   void syr(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, float paramFloat, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2, int paramInt3);
/*     */   
/*     */   void syr(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, float paramFloat, FloatBuffer paramFloatBuffer1, int paramInt2, FloatBuffer paramFloatBuffer2, int paramInt3);
/*     */   
/*     */   void spr(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, double paramDouble, double[] paramArrayOfdouble1, int paramInt2, double[] paramArrayOfdouble2);
/*     */   
/*     */   void spr(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, double paramDouble, DoubleBuffer paramDoubleBuffer1, int paramInt2, DoubleBuffer paramDoubleBuffer2);
/*     */   
/*     */   void spr(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, float paramFloat, float[] paramArrayOffloat1, int paramInt2, float[] paramArrayOffloat2);
/*     */   
/*     */   void spr(LayoutModified paramLayoutModified, UPLOModified paramUPLOModified, int paramInt1, float paramFloat, FloatBuffer paramFloatBuffer1, int paramInt2, FloatBuffer paramFloatBuffer2);
/*     */   
/*     */   void gemm(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified1, TransposeModified paramTransposeModified2, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double[] paramArrayOfdouble1, int paramInt4, double[] paramArrayOfdouble2, int paramInt5, double paramDouble2, double[] paramArrayOfdouble3, int paramInt6);
/*     */   
/*     */   void gemm(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified1, TransposeModified paramTransposeModified2, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, DoubleBuffer paramDoubleBuffer1, int paramInt4, DoubleBuffer paramDoubleBuffer2, int paramInt5, double paramDouble2, DoubleBuffer paramDoubleBuffer3, int paramInt6);
/*     */   
/*     */   void gemm(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified1, TransposeModified paramTransposeModified2, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, DoublePointer paramDoublePointer1, int paramInt4, DoublePointer paramDoublePointer2, int paramInt5, double paramDouble2, DoublePointer paramDoublePointer3, int paramInt6);
/*     */   
/*     */   void gemm(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified1, TransposeModified paramTransposeModified2, int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float[] paramArrayOffloat1, int paramInt4, float[] paramArrayOffloat2, int paramInt5, float paramFloat2, float[] paramArrayOffloat3, int paramInt6);
/*     */   
/*     */   void gemm(LayoutModified paramLayoutModified, TransposeModified paramTransposeModified1, TransposeModified paramTransposeModified2, int paramInt1, int paramInt2, int paramInt3, float paramFloat1, FloatBuffer paramFloatBuffer1, int paramInt4, FloatBuffer paramFloatBuffer2, int paramInt5, float paramFloat2, FloatBuffer paramFloatBuffer3, int paramInt6);
/*     */   
/*     */   void symm(LayoutModified paramLayoutModified, SideModified paramSideModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double paramDouble1, double[] paramArrayOfdouble1, int paramInt3, double[] paramArrayOfdouble2, int paramInt4, double paramDouble2, double[] paramArrayOfdouble3, int paramInt5);
/*     */   
/*     */   void symm(LayoutModified paramLayoutModified, SideModified paramSideModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double paramDouble1, DoubleBuffer paramDoubleBuffer1, int paramInt3, DoubleBuffer paramDoubleBuffer2, int paramInt4, double paramDouble2, DoubleBuffer paramDoubleBuffer3, int paramInt5);
/*     */   
/*     */   void symm(LayoutModified paramLayoutModified, SideModified paramSideModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, double paramDouble1, DoublePointer paramDoublePointer1, int paramInt3, DoublePointer paramDoublePointer2, int paramInt4, double paramDouble2, DoublePointer paramDoublePointer3, int paramInt5);
/*     */   
/*     */   void symm(LayoutModified paramLayoutModified, SideModified paramSideModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, float paramFloat1, float[] paramArrayOffloat1, int paramInt3, float[] paramArrayOffloat2, int paramInt4, float paramFloat2, float[] paramArrayOffloat3, int paramInt5);
/*     */   
/*     */   void symm(LayoutModified paramLayoutModified, SideModified paramSideModified, UPLOModified paramUPLOModified, int paramInt1, int paramInt2, float paramFloat1, FloatBuffer paramFloatBuffer1, int paramInt3, FloatBuffer paramFloatBuffer2, int paramInt4, float paramFloat2, FloatBuffer paramFloatBuffer3, int paramInt5);
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/BLASModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */