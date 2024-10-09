package smileModified;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import org.bytedeco.javacpp.DoublePointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface BLASModified {
   BLASModified engine = getInstance();

   static BLASModified getInstance() {
      BLASModified mkl = MKL();
      return (BLASModified)(mkl != null ? mkl : new OpenBLASModified());
   }

   static BLASModified MKL() {
      Logger logger = LoggerFactory.getLogger(BLASModified.class);

      try {
         Class<?> clazz = Class.forName("smile.math.blas.mkl.MKL");
         logger.info("smile-mkl module is available.");
         return (BLASModified)clazz.getDeclaredConstructor().newInstance();
      } catch (Exception var2) {
         logger.debug("Failed to create MKL instance: ", var2);
         return null;
      }
   }

   double asum(int var1, double[] var2, int var3);

   float asum(int var1, float[] var2, int var3);

   default double asum(double[] x) {
      return this.asum(x.length, (double[])x, 1);
   }

   default float asum(float[] x) {
      return this.asum(x.length, (float[])x, 1);
   }

   void axpy(int var1, double var2, double[] var4, int var5, double[] var6, int var7);

   void axpy(int var1, float var2, float[] var3, int var4, float[] var5, int var6);

   default void axpy(double alpha, double[] x, double[] y) {
      this.axpy(x.length, alpha, x, 1, y, 1);
   }

   default void axpy(float alpha, float[] x, float[] y) {
      this.axpy(x.length, alpha, x, 1, y, 1);
   }

   double dot(int var1, double[] var2, int var3, double[] var4, int var5);

   float dot(int var1, float[] var2, int var3, float[] var4, int var5);

   default double dot(double[] x, double[] y) {
      return this.dot(x.length, (double[])x, 1, (double[])y, 1);
   }

   default float dot(float[] x, float[] y) {
      return this.dot(x.length, (float[])x, 1, (float[])y, 1);
   }

   double nrm2(int var1, double[] var2, int var3);

   float nrm2(int var1, float[] var2, int var3);

   default double nrm2(double[] x) {
      return this.nrm2(x.length, (double[])x, 1);
   }

   default float nrm2(float[] x) {
      return this.nrm2(x.length, (float[])x, 1);
   }

   void scal(int var1, double var2, double[] var4, int var5);

   void scal(int var1, float var2, float[] var3, int var4);

   default void scal(double alpha, double[] x) {
      this.scal(x.length, alpha, x, 1);
   }

   default void scal(float alpha, float[] x) {
      this.scal(x.length, alpha, x, 1);
   }

   void swap(int var1, double[] var2, int var3, double[] var4, int var5);

   void swap(int var1, float[] var2, int var3, float[] var4, int var5);

   default void swap(double[] x, double[] y) {
      this.swap(x.length, (double[])x, 1, (double[])y, 1);
   }

   default void swap(float[] x, float[] y) {
      this.swap(x.length, (float[])x, 1, (float[])y, 1);
   }

   long iamax(int var1, double[] var2, int var3);

   long iamax(int var1, float[] var2, int var3);

   default long iamax(double[] x) {
      return this.iamax(x.length, (double[])x, 1);
   }

   default long iamax(float[] x) {
      return this.iamax(x.length, (float[])x, 1);
   }

   void gemv(LayoutModified var1, TransposeModified var2, int var3, int var4, double var5, double[] var7, int var8, double[] var9, int var10, double var11, double[] var13, int var14);

   void gemv(LayoutModified var1, TransposeModified var2, int var3, int var4, double var5, DoubleBuffer var7, int var8, DoubleBuffer var9, int var10, double var11, DoubleBuffer var13, int var14);

   void gemv(LayoutModified var1, TransposeModified var2, int var3, int var4, double var5, DoublePointer var7, int var8, DoublePointer var9, int var10, double var11, DoublePointer var13, int var14);

   void gemv(LayoutModified var1, TransposeModified var2, int var3, int var4, float var5, float[] var6, int var7, float[] var8, int var9, float var10, float[] var11, int var12);

   void gemv(LayoutModified var1, TransposeModified var2, int var3, int var4, float var5, FloatBuffer var6, int var7, FloatBuffer var8, int var9, float var10, FloatBuffer var11, int var12);

   void symv(LayoutModified var1, UPLOModified var2, int var3, double var4, double[] var6, int var7, double[] var8, int var9, double var10, double[] var12, int var13);

   void symv(LayoutModified var1, UPLOModified var2, int var3, double var4, DoubleBuffer var6, int var7, DoubleBuffer var8, int var9, double var10, DoubleBuffer var12, int var13);

   void symv(LayoutModified var1, UPLOModified var2, int var3, double var4, DoublePointer var6, int var7, DoublePointer var8, int var9, double var10, DoublePointer var12, int var13);

   void symv(LayoutModified var1, UPLOModified var2, int var3, float var4, float[] var5, int var6, float[] var7, int var8, float var9, float[] var10, int var11);

   void symv(LayoutModified var1, UPLOModified var2, int var3, float var4, FloatBuffer var5, int var6, FloatBuffer var7, int var8, float var9, FloatBuffer var10, int var11);

   void spmv(LayoutModified var1, UPLOModified var2, int var3, double var4, double[] var6, double[] var7, int var8, double var9, double[] var11, int var12);

   void spmv(LayoutModified var1, UPLOModified var2, int var3, double var4, DoubleBuffer var6, DoubleBuffer var7, int var8, double var9, DoubleBuffer var11, int var12);

   void spmv(LayoutModified var1, UPLOModified var2, int var3, float var4, float[] var5, float[] var6, int var7, float var8, float[] var9, int var10);

   void spmv(LayoutModified var1, UPLOModified var2, int var3, float var4, FloatBuffer var5, FloatBuffer var6, int var7, float var8, FloatBuffer var9, int var10);

   void trmv(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, double[] var6, int var7, double[] var8, int var9);

   void trmv(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, DoubleBuffer var6, int var7, DoubleBuffer var8, int var9);

   void trmv(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, DoublePointer var6, int var7, DoublePointer var8, int var9);

   void trmv(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, float[] var6, int var7, float[] var8, int var9);

   void trmv(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, FloatBuffer var6, int var7, FloatBuffer var8, int var9);

   void tpmv(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, double[] var6, double[] var7, int var8);

   void tpmv(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, DoubleBuffer var6, DoubleBuffer var7, int var8);

   void tpmv(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, float[] var6, float[] var7, int var8);

   void tpmv(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, FloatBuffer var6, FloatBuffer var7, int var8);

   void gbmv(LayoutModified var1, TransposeModified var2, int var3, int var4, int var5, int var6, double var7, double[] var9, int var10, double[] var11, int var12, double var13, double[] var15, int var16);

   void gbmv(LayoutModified var1, TransposeModified var2, int var3, int var4, int var5, int var6, double var7, DoubleBuffer var9, int var10, DoubleBuffer var11, int var12, double var13, DoubleBuffer var15, int var16);

   void gbmv(LayoutModified var1, TransposeModified var2, int var3, int var4, int var5, int var6, float var7, float[] var8, int var9, float[] var10, int var11, float var12, float[] var13, int var14);

   void gbmv(LayoutModified var1, TransposeModified var2, int var3, int var4, int var5, int var6, float var7, FloatBuffer var8, int var9, FloatBuffer var10, int var11, float var12, FloatBuffer var13, int var14);

   void sbmv(LayoutModified var1, UPLOModified var2, int var3, int var4, double var5, double[] var7, int var8, double[] var9, int var10, double var11, double[] var13, int var14);

   void sbmv(LayoutModified var1, UPLOModified var2, int var3, int var4, double var5, DoubleBuffer var7, int var8, DoubleBuffer var9, int var10, double var11, DoubleBuffer var13, int var14);

   void sbmv(LayoutModified var1, UPLOModified var2, int var3, int var4, float var5, float[] var6, int var7, float[] var8, int var9, float var10, float[] var11, int var12);

   void sbmv(LayoutModified var1, UPLOModified var2, int var3, int var4, float var5, FloatBuffer var6, int var7, FloatBuffer var8, int var9, float var10, FloatBuffer var11, int var12);

   void ger(LayoutModified var1, int var2, int var3, double var4, double[] var6, int var7, double[] var8, int var9, double[] var10, int var11);

   void ger(LayoutModified var1, int var2, int var3, double var4, DoubleBuffer var6, int var7, DoubleBuffer var8, int var9, DoubleBuffer var10, int var11);

   void ger(LayoutModified var1, int var2, int var3, double var4, DoublePointer var6, int var7, DoublePointer var8, int var9, DoublePointer var10, int var11);

   void ger(LayoutModified var1, int var2, int var3, float var4, float[] var5, int var6, float[] var7, int var8, float[] var9, int var10);

   void ger(LayoutModified var1, int var2, int var3, float var4, FloatBuffer var5, int var6, FloatBuffer var7, int var8, FloatBuffer var9, int var10);

   void syr(LayoutModified var1, UPLOModified var2, int var3, double var4, double[] var6, int var7, double[] var8, int var9);

   void syr(LayoutModified var1, UPLOModified var2, int var3, double var4, DoubleBuffer var6, int var7, DoubleBuffer var8, int var9);

   void syr(LayoutModified var1, UPLOModified var2, int var3, double var4, DoublePointer var6, int var7, DoublePointer var8, int var9);

   void syr(LayoutModified var1, UPLOModified var2, int var3, float var4, float[] var5, int var6, float[] var7, int var8);

   void syr(LayoutModified var1, UPLOModified var2, int var3, float var4, FloatBuffer var5, int var6, FloatBuffer var7, int var8);

   void spr(LayoutModified var1, UPLOModified var2, int var3, double var4, double[] var6, int var7, double[] var8);

   void spr(LayoutModified var1, UPLOModified var2, int var3, double var4, DoubleBuffer var6, int var7, DoubleBuffer var8);

   void spr(LayoutModified var1, UPLOModified var2, int var3, float var4, float[] var5, int var6, float[] var7);

   void spr(LayoutModified var1, UPLOModified var2, int var3, float var4, FloatBuffer var5, int var6, FloatBuffer var7);

   void gemm(LayoutModified var1, TransposeModified var2, TransposeModified var3, int var4, int var5, int var6, double var7, double[] var9, int var10, double[] var11, int var12, double var13, double[] var15, int var16);

   void gemm(LayoutModified var1, TransposeModified var2, TransposeModified var3, int var4, int var5, int var6, double var7, DoubleBuffer var9, int var10, DoubleBuffer var11, int var12, double var13, DoubleBuffer var15, int var16);

   void gemm(LayoutModified var1, TransposeModified var2, TransposeModified var3, int var4, int var5, int var6, double var7, DoublePointer var9, int var10, DoublePointer var11, int var12, double var13, DoublePointer var15, int var16);

   void gemm(LayoutModified var1, TransposeModified var2, TransposeModified var3, int var4, int var5, int var6, float var7, float[] var8, int var9, float[] var10, int var11, float var12, float[] var13, int var14);

   void gemm(LayoutModified var1, TransposeModified var2, TransposeModified var3, int var4, int var5, int var6, float var7, FloatBuffer var8, int var9, FloatBuffer var10, int var11, float var12, FloatBuffer var13, int var14);

   void symm(LayoutModified var1, SideModified var2, UPLOModified var3, int var4, int var5, double var6, double[] var8, int var9, double[] var10, int var11, double var12, double[] var14, int var15);

   void symm(LayoutModified var1, SideModified var2, UPLOModified var3, int var4, int var5, double var6, DoubleBuffer var8, int var9, DoubleBuffer var10, int var11, double var12, DoubleBuffer var14, int var15);

   void symm(LayoutModified var1, SideModified var2, UPLOModified var3, int var4, int var5, double var6, DoublePointer var8, int var9, DoublePointer var10, int var11, double var12, DoublePointer var14, int var15);

   void symm(LayoutModified var1, SideModified var2, UPLOModified var3, int var4, int var5, float var6, float[] var7, int var8, float[] var9, int var10, float var11, float[] var12, int var13);

   void symm(LayoutModified var1, SideModified var2, UPLOModified var3, int var4, int var5, float var6, FloatBuffer var7, int var8, FloatBuffer var9, int var10, float var11, FloatBuffer var12, int var13);
}
