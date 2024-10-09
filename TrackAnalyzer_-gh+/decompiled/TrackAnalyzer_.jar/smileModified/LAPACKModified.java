package smileModified;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LAPACKModified {
   LAPACKModified engine = getInstance();

   static LAPACKModified getInstance() {
      LAPACKModified mkl = MKL();
      return (LAPACKModified)(mkl != null ? mkl : new OpenBLASModified());
   }

   static LAPACKModified MKL() {
      Logger logger = LoggerFactory.getLogger(LAPACKModified.class);

      try {
         Class<?> clazz = Class.forName("smile.math.blas.mkl.MKL");
         logger.info("smile-mkl module is available.");
         return (LAPACKModified)clazz.getDeclaredConstructor().newInstance();
      } catch (Exception var2) {
         logger.debug("Failed to create MKL instance: ", var2);
         return null;
      }
   }

   int gesv(LayoutModified var1, int var2, int var3, double[] var4, int var5, int[] var6, double[] var7, int var8);

   int gesv(LayoutModified var1, int var2, int var3, DoubleBuffer var4, int var5, IntBuffer var6, DoubleBuffer var7, int var8);

   int gesv(LayoutModified var1, int var2, int var3, DoublePointer var4, int var5, IntPointer var6, DoublePointer var7, int var8);

   int gesv(LayoutModified var1, int var2, int var3, float[] var4, int var5, int[] var6, float[] var7, int var8);

   int gesv(LayoutModified var1, int var2, int var3, FloatBuffer var4, int var5, IntBuffer var6, FloatBuffer var7, int var8);

   int sysv(LayoutModified var1, UPLOModified var2, int var3, int var4, double[] var5, int var6, int[] var7, double[] var8, int var9);

   int sysv(LayoutModified var1, UPLOModified var2, int var3, int var4, DoubleBuffer var5, int var6, IntBuffer var7, DoubleBuffer var8, int var9);

   int sysv(LayoutModified var1, UPLOModified var2, int var3, int var4, DoublePointer var5, int var6, IntPointer var7, DoublePointer var8, int var9);

   int sysv(LayoutModified var1, UPLOModified var2, int var3, int var4, float[] var5, int var6, int[] var7, float[] var8, int var9);

   int sysv(LayoutModified var1, UPLOModified var2, int var3, int var4, FloatBuffer var5, int var6, IntBuffer var7, FloatBuffer var8, int var9);

   int spsv(LayoutModified var1, UPLOModified var2, int var3, int var4, double[] var5, int[] var6, double[] var7, int var8);

   int spsv(LayoutModified var1, UPLOModified var2, int var3, int var4, DoubleBuffer var5, IntBuffer var6, DoubleBuffer var7, int var8);

   int spsv(LayoutModified var1, UPLOModified var2, int var3, int var4, float[] var5, int[] var6, float[] var7, int var8);

   int spsv(LayoutModified var1, UPLOModified var2, int var3, int var4, FloatBuffer var5, IntBuffer var6, FloatBuffer var7, int var8);

   int posv(LayoutModified var1, UPLOModified var2, int var3, int var4, double[] var5, int var6, double[] var7, int var8);

   int posv(LayoutModified var1, UPLOModified var2, int var3, int var4, DoubleBuffer var5, int var6, DoubleBuffer var7, int var8);

   int posv(LayoutModified var1, UPLOModified var2, int var3, int var4, float[] var5, int var6, float[] var7, int var8);

   int posv(LayoutModified var1, UPLOModified var2, int var3, int var4, FloatBuffer var5, int var6, FloatBuffer var7, int var8);

   int ppsv(LayoutModified var1, UPLOModified var2, int var3, int var4, double[] var5, double[] var6, int var7);

   int ppsv(LayoutModified var1, UPLOModified var2, int var3, int var4, DoubleBuffer var5, DoubleBuffer var6, int var7);

   int ppsv(LayoutModified var1, UPLOModified var2, int var3, int var4, float[] var5, float[] var6, int var7);

   int ppsv(LayoutModified var1, UPLOModified var2, int var3, int var4, FloatBuffer var5, FloatBuffer var6, int var7);

   int gbsv(LayoutModified var1, int var2, int var3, int var4, int var5, double[] var6, int var7, int[] var8, double[] var9, int var10);

   int gbsv(LayoutModified var1, int var2, int var3, int var4, int var5, DoubleBuffer var6, int var7, IntBuffer var8, DoubleBuffer var9, int var10);

   int gbsv(LayoutModified var1, int var2, int var3, int var4, int var5, float[] var6, int var7, int[] var8, float[] var9, int var10);

   int gbsv(LayoutModified var1, int var2, int var3, int var4, int var5, FloatBuffer var6, int var7, IntBuffer var8, FloatBuffer var9, int var10);

   int gels(LayoutModified var1, TransposeModified var2, int var3, int var4, int var5, double[] var6, int var7, double[] var8, int var9);

   int gels(LayoutModified var1, TransposeModified var2, int var3, int var4, int var5, DoubleBuffer var6, int var7, DoubleBuffer var8, int var9);

   int gels(LayoutModified var1, TransposeModified var2, int var3, int var4, int var5, float[] var6, int var7, float[] var8, int var9);

   int gels(LayoutModified var1, TransposeModified var2, int var3, int var4, int var5, FloatBuffer var6, int var7, FloatBuffer var8, int var9);

   int gelsy(LayoutModified var1, int var2, int var3, int var4, double[] var5, int var6, double[] var7, int var8, int[] var9, double var10, int[] var12);

   int gelsy(LayoutModified var1, int var2, int var3, int var4, DoubleBuffer var5, int var6, DoubleBuffer var7, int var8, IntBuffer var9, double var10, IntBuffer var12);

   int gelsy(LayoutModified var1, int var2, int var3, int var4, float[] var5, int var6, float[] var7, int var8, int[] var9, float var10, int[] var11);

   int gelsy(LayoutModified var1, int var2, int var3, int var4, FloatBuffer var5, int var6, FloatBuffer var7, int var8, IntBuffer var9, float var10, IntBuffer var11);

   int gelss(LayoutModified var1, int var2, int var3, int var4, double[] var5, int var6, double[] var7, int var8, double[] var9, double var10, int[] var12);

   int gelss(LayoutModified var1, int var2, int var3, int var4, DoubleBuffer var5, int var6, DoubleBuffer var7, int var8, DoubleBuffer var9, double var10, IntBuffer var12);

   int gelss(LayoutModified var1, int var2, int var3, int var4, float[] var5, int var6, float[] var7, int var8, float[] var9, float var10, int[] var11);

   int gelss(LayoutModified var1, int var2, int var3, int var4, FloatBuffer var5, int var6, FloatBuffer var7, int var8, FloatBuffer var9, float var10, IntBuffer var11);

   int gelsd(LayoutModified var1, int var2, int var3, int var4, double[] var5, int var6, double[] var7, int var8, double[] var9, double var10, int[] var12);

   int gelsd(LayoutModified var1, int var2, int var3, int var4, DoubleBuffer var5, int var6, DoubleBuffer var7, int var8, DoubleBuffer var9, double var10, IntBuffer var12);

   int gelsd(LayoutModified var1, int var2, int var3, int var4, float[] var5, int var6, float[] var7, int var8, float[] var9, float var10, int[] var11);

   int gelsd(LayoutModified var1, int var2, int var3, int var4, FloatBuffer var5, int var6, FloatBuffer var7, int var8, FloatBuffer var9, float var10, IntBuffer var11);

   int gglse(LayoutModified var1, int var2, int var3, int var4, double[] var5, int var6, double[] var7, int var8, double[] var9, double[] var10, double[] var11);

   int gglse(LayoutModified var1, int var2, int var3, int var4, DoubleBuffer var5, int var6, DoubleBuffer var7, int var8, DoubleBuffer var9, DoubleBuffer var10, DoubleBuffer var11);

   int gglse(LayoutModified var1, int var2, int var3, int var4, float[] var5, int var6, float[] var7, int var8, float[] var9, float[] var10, float[] var11);

   int gglse(LayoutModified var1, int var2, int var3, int var4, FloatBuffer var5, int var6, FloatBuffer var7, int var8, FloatBuffer var9, FloatBuffer var10, FloatBuffer var11);

   int ggglm(LayoutModified var1, int var2, int var3, int var4, double[] var5, int var6, double[] var7, int var8, double[] var9, double[] var10, double[] var11);

   int ggglm(LayoutModified var1, int var2, int var3, int var4, DoubleBuffer var5, int var6, DoubleBuffer var7, int var8, DoubleBuffer var9, DoubleBuffer var10, DoubleBuffer var11);

   int ggglm(LayoutModified var1, int var2, int var3, int var4, float[] var5, int var6, float[] var7, int var8, float[] var9, float[] var10, float[] var11);

   int ggglm(LayoutModified var1, int var2, int var3, int var4, FloatBuffer var5, int var6, FloatBuffer var7, int var8, FloatBuffer var9, FloatBuffer var10, FloatBuffer var11);

   int geev(LayoutModified var1, EVDJobModified var2, EVDJobModified var3, int var4, double[] var5, int var6, double[] var7, double[] var8, double[] var9, int var10, double[] var11, int var12);

   int geev(LayoutModified var1, EVDJobModified var2, EVDJobModified var3, int var4, DoubleBuffer var5, int var6, DoubleBuffer var7, DoubleBuffer var8, DoubleBuffer var9, int var10, DoubleBuffer var11, int var12);

   int geev(LayoutModified var1, EVDJobModified var2, EVDJobModified var3, int var4, DoublePointer var5, int var6, DoublePointer var7, DoublePointer var8, DoublePointer var9, int var10, DoublePointer var11, int var12);

   int geev(LayoutModified var1, EVDJobModified var2, EVDJobModified var3, int var4, float[] var5, int var6, float[] var7, float[] var8, float[] var9, int var10, float[] var11, int var12);

   int geev(LayoutModified var1, EVDJobModified var2, EVDJobModified var3, int var4, FloatBuffer var5, int var6, FloatBuffer var7, FloatBuffer var8, FloatBuffer var9, int var10, FloatBuffer var11, int var12);

   int syev(LayoutModified var1, EVDJobModified var2, UPLOModified var3, int var4, double[] var5, int var6, double[] var7);

   int syev(LayoutModified var1, EVDJobModified var2, UPLOModified var3, int var4, DoubleBuffer var5, int var6, DoubleBuffer var7);

   int syev(LayoutModified var1, EVDJobModified var2, UPLOModified var3, int var4, float[] var5, int var6, float[] var7);

   int syev(LayoutModified var1, EVDJobModified var2, UPLOModified var3, int var4, FloatBuffer var5, int var6, FloatBuffer var7);

   int syevd(LayoutModified var1, EVDJobModified var2, UPLOModified var3, int var4, double[] var5, int var6, double[] var7);

   int syevd(LayoutModified var1, EVDJobModified var2, UPLOModified var3, int var4, DoubleBuffer var5, int var6, DoubleBuffer var7);

   int syevd(LayoutModified var1, EVDJobModified var2, UPLOModified var3, int var4, DoublePointer var5, int var6, DoublePointer var7);

   int syevd(LayoutModified var1, EVDJobModified var2, UPLOModified var3, int var4, float[] var5, int var6, float[] var7);

   int syevd(LayoutModified var1, EVDJobModified var2, UPLOModified var3, int var4, FloatBuffer var5, int var6, FloatBuffer var7);

   int syevr(LayoutModified var1, EVDJobModified var2, EigenRangeModified var3, UPLOModified var4, int var5, double[] var6, int var7, double var8, double var10, int var12, int var13, double var14, int[] var16, double[] var17, double[] var18, int var19, int[] var20);

   int syevr(LayoutModified var1, EVDJobModified var2, EigenRangeModified var3, UPLOModified var4, int var5, DoubleBuffer var6, int var7, double var8, double var10, int var12, int var13, double var14, IntBuffer var16, DoubleBuffer var17, DoubleBuffer var18, int var19, IntBuffer var20);

   int syevr(LayoutModified var1, EVDJobModified var2, EigenRangeModified var3, UPLOModified var4, int var5, float[] var6, int var7, float var8, float var9, int var10, int var11, float var12, int[] var13, float[] var14, float[] var15, int var16, int[] var17);

   int syevr(LayoutModified var1, EVDJobModified var2, EigenRangeModified var3, UPLOModified var4, int var5, FloatBuffer var6, int var7, float var8, float var9, int var10, int var11, float var12, IntBuffer var13, FloatBuffer var14, FloatBuffer var15, int var16, IntBuffer var17);

   int gesvd(LayoutModified var1, SVDJobModified var2, SVDJobModified var3, int var4, int var5, double[] var6, int var7, double[] var8, double[] var9, int var10, double[] var11, int var12, double[] var13);

   int gesvd(LayoutModified var1, SVDJobModified var2, SVDJobModified var3, int var4, int var5, DoubleBuffer var6, int var7, DoubleBuffer var8, DoubleBuffer var9, int var10, DoubleBuffer var11, int var12, DoubleBuffer var13);

   int gesvd(LayoutModified var1, SVDJobModified var2, SVDJobModified var3, int var4, int var5, float[] var6, int var7, float[] var8, float[] var9, int var10, float[] var11, int var12, float[] var13);

   int gesvd(LayoutModified var1, SVDJobModified var2, SVDJobModified var3, int var4, int var5, FloatBuffer var6, int var7, FloatBuffer var8, FloatBuffer var9, int var10, FloatBuffer var11, int var12, FloatBuffer var13);

   int gesdd(LayoutModified var1, SVDJobModified var2, int var3, int var4, double[] var5, int var6, double[] var7, double[] var8, int var9, double[] var10, int var11);

   int gesdd(LayoutModified var1, SVDJobModified var2, int var3, int var4, DoubleBuffer var5, int var6, DoubleBuffer var7, DoubleBuffer var8, int var9, DoubleBuffer var10, int var11);

   int gesdd(LayoutModified var1, SVDJobModified var2, int var3, int var4, DoublePointer var5, int var6, DoublePointer var7, DoublePointer var8, int var9, DoublePointer var10, int var11);

   int gesdd(LayoutModified var1, SVDJobModified var2, int var3, int var4, float[] var5, int var6, float[] var7, float[] var8, int var9, float[] var10, int var11);

   int gesdd(LayoutModified var1, SVDJobModified var2, int var3, int var4, FloatBuffer var5, int var6, FloatBuffer var7, FloatBuffer var8, int var9, FloatBuffer var10, int var11);

   int getrf(LayoutModified var1, int var2, int var3, double[] var4, int var5, int[] var6);

   int getrf(LayoutModified var1, int var2, int var3, DoubleBuffer var4, int var5, IntBuffer var6);

   int getrf(LayoutModified var1, int var2, int var3, DoublePointer var4, int var5, IntPointer var6);

   int getrf(LayoutModified var1, int var2, int var3, float[] var4, int var5, int[] var6);

   int getrf(LayoutModified var1, int var2, int var3, FloatBuffer var4, int var5, IntBuffer var6);

   int getrf2(LayoutModified var1, int var2, int var3, double[] var4, int var5, int[] var6);

   int getrf2(LayoutModified var1, int var2, int var3, DoubleBuffer var4, int var5, IntBuffer var6);

   int getrf2(LayoutModified var1, int var2, int var3, float[] var4, int var5, int[] var6);

   int getrf2(LayoutModified var1, int var2, int var3, FloatBuffer var4, int var5, IntBuffer var6);

   int gbtrf(LayoutModified var1, int var2, int var3, int var4, int var5, double[] var6, int var7, int[] var8);

   int gbtrf(LayoutModified var1, int var2, int var3, int var4, int var5, DoubleBuffer var6, int var7, IntBuffer var8);

   int gbtrf(LayoutModified var1, int var2, int var3, int var4, int var5, float[] var6, int var7, int[] var8);

   int gbtrf(LayoutModified var1, int var2, int var3, int var4, int var5, FloatBuffer var6, int var7, IntBuffer var8);

   int sptrf(LayoutModified var1, UPLOModified var2, int var3, double[] var4, int[] var5);

   int sptrf(LayoutModified var1, UPLOModified var2, int var3, DoubleBuffer var4, IntBuffer var5);

   int sptrf(LayoutModified var1, UPLOModified var2, int var3, float[] var4, int[] var5);

   int sptrf(LayoutModified var1, UPLOModified var2, int var3, FloatBuffer var4, IntBuffer var5);

   int getrs(LayoutModified var1, TransposeModified var2, int var3, int var4, double[] var5, int var6, int[] var7, double[] var8, int var9);

   int getrs(LayoutModified var1, TransposeModified var2, int var3, int var4, DoubleBuffer var5, int var6, IntBuffer var7, DoubleBuffer var8, int var9);

   int getrs(LayoutModified var1, TransposeModified var2, int var3, int var4, DoublePointer var5, int var6, IntPointer var7, DoublePointer var8, int var9);

   int getrs(LayoutModified var1, TransposeModified var2, int var3, int var4, float[] var5, int var6, int[] var7, float[] var8, int var9);

   int getrs(LayoutModified var1, TransposeModified var2, int var3, int var4, FloatBuffer var5, int var6, IntBuffer var7, FloatBuffer var8, int var9);

   int gbtrs(LayoutModified var1, TransposeModified var2, int var3, int var4, int var5, int var6, double[] var7, int var8, int[] var9, double[] var10, int var11);

   int gbtrs(LayoutModified var1, TransposeModified var2, int var3, int var4, int var5, int var6, DoubleBuffer var7, int var8, IntBuffer var9, DoubleBuffer var10, int var11);

   int gbtrs(LayoutModified var1, TransposeModified var2, int var3, int var4, int var5, int var6, float[] var7, int var8, int[] var9, float[] var10, int var11);

   int gbtrs(LayoutModified var1, TransposeModified var2, int var3, int var4, int var5, int var6, FloatBuffer var7, int var8, IntBuffer var9, FloatBuffer var10, int var11);

   int sptrs(LayoutModified var1, UPLOModified var2, int var3, int var4, double[] var5, int[] var6, double[] var7, int var8);

   int sptrs(LayoutModified var1, UPLOModified var2, int var3, int var4, DoubleBuffer var5, IntBuffer var6, DoubleBuffer var7, int var8);

   int sptrs(LayoutModified var1, UPLOModified var2, int var3, int var4, FloatBuffer var5, IntBuffer var6, FloatBuffer var7, int var8);

   int sptrs(LayoutModified var1, UPLOModified var2, int var3, int var4, float[] var5, int[] var6, float[] var7, int var8);

   int potrf(LayoutModified var1, UPLOModified var2, int var3, double[] var4, int var5);

   int potrf(LayoutModified var1, UPLOModified var2, int var3, DoubleBuffer var4, int var5);

   int potrf(LayoutModified var1, UPLOModified var2, int var3, DoublePointer var4, int var5);

   int potrf(LayoutModified var1, UPLOModified var2, int var3, float[] var4, int var5);

   int potrf(LayoutModified var1, UPLOModified var2, int var3, FloatBuffer var4, int var5);

   int potrf2(LayoutModified var1, UPLOModified var2, int var3, double[] var4, int var5);

   int potrf2(LayoutModified var1, UPLOModified var2, int var3, DoubleBuffer var4, int var5);

   int potrf2(LayoutModified var1, UPLOModified var2, int var3, float[] var4, int var5);

   int potrf2(LayoutModified var1, UPLOModified var2, int var3, FloatBuffer var4, int var5);

   int pbtrf(LayoutModified var1, UPLOModified var2, int var3, int var4, double[] var5, int var6);

   int pbtrf(LayoutModified var1, UPLOModified var2, int var3, int var4, DoubleBuffer var5, int var6);

   int pbtrf(LayoutModified var1, UPLOModified var2, int var3, int var4, float[] var5, int var6);

   int pbtrf(LayoutModified var1, UPLOModified var2, int var3, int var4, FloatBuffer var5, int var6);

   int pptrf(LayoutModified var1, UPLOModified var2, int var3, double[] var4);

   int pptrf(LayoutModified var1, UPLOModified var2, int var3, DoubleBuffer var4);

   int pptrf(LayoutModified var1, UPLOModified var2, int var3, float[] var4);

   int pptrf(LayoutModified var1, UPLOModified var2, int var3, FloatBuffer var4);

   int potrs(LayoutModified var1, UPLOModified var2, int var3, int var4, double[] var5, int var6, double[] var7, int var8);

   int potrs(LayoutModified var1, UPLOModified var2, int var3, int var4, DoubleBuffer var5, int var6, DoubleBuffer var7, int var8);

   int potrs(LayoutModified var1, UPLOModified var2, int var3, int var4, DoublePointer var5, int var6, DoublePointer var7, int var8);

   int potrs(LayoutModified var1, UPLOModified var2, int var3, int var4, float[] var5, int var6, float[] var7, int var8);

   int potrs(LayoutModified var1, UPLOModified var2, int var3, int var4, FloatBuffer var5, int var6, FloatBuffer var7, int var8);

   int pbtrs(LayoutModified var1, UPLOModified var2, int var3, int var4, int var5, double[] var6, int var7, double[] var8, int var9);

   int pbtrs(LayoutModified var1, UPLOModified var2, int var3, int var4, int var5, DoubleBuffer var6, int var7, DoubleBuffer var8, int var9);

   int pbtrs(LayoutModified var1, UPLOModified var2, int var3, int var4, int var5, float[] var6, int var7, float[] var8, int var9);

   int pbtrs(LayoutModified var1, UPLOModified var2, int var3, int var4, int var5, FloatBuffer var6, int var7, FloatBuffer var8, int var9);

   int pptrs(LayoutModified var1, UPLOModified var2, int var3, int var4, double[] var5, double[] var6, int var7);

   int pptrs(LayoutModified var1, UPLOModified var2, int var3, int var4, DoubleBuffer var5, DoubleBuffer var6, int var7);

   int pptrs(LayoutModified var1, UPLOModified var2, int var3, int var4, float[] var5, float[] var6, int var7);

   int pptrs(LayoutModified var1, UPLOModified var2, int var3, int var4, FloatBuffer var5, FloatBuffer var6, int var7);

   int geqrf(LayoutModified var1, int var2, int var3, double[] var4, int var5, double[] var6);

   int geqrf(LayoutModified var1, int var2, int var3, DoubleBuffer var4, int var5, DoubleBuffer var6);

   int geqrf(LayoutModified var1, int var2, int var3, DoublePointer var4, int var5, DoublePointer var6);

   int geqrf(LayoutModified var1, int var2, int var3, float[] var4, int var5, float[] var6);

   int geqrf(LayoutModified var1, int var2, int var3, FloatBuffer var4, int var5, FloatBuffer var6);

   int ormqr(LayoutModified var1, SideModified var2, TransposeModified var3, int var4, int var5, int var6, double[] var7, int var8, double[] var9, double[] var10, int var11);

   int ormqr(LayoutModified var1, SideModified var2, TransposeModified var3, int var4, int var5, int var6, DoubleBuffer var7, int var8, DoubleBuffer var9, DoubleBuffer var10, int var11);

   int ormqr(LayoutModified var1, SideModified var2, TransposeModified var3, int var4, int var5, int var6, DoublePointer var7, int var8, DoublePointer var9, DoublePointer var10, int var11);

   int ormqr(LayoutModified var1, SideModified var2, TransposeModified var3, int var4, int var5, int var6, float[] var7, int var8, float[] var9, float[] var10, int var11);

   int ormqr(LayoutModified var1, SideModified var2, TransposeModified var3, int var4, int var5, int var6, FloatBuffer var7, int var8, FloatBuffer var9, FloatBuffer var10, int var11);

   int orgqr(LayoutModified var1, int var2, int var3, int var4, double[] var5, int var6, double[] var7);

   int orgqr(LayoutModified var1, int var2, int var3, int var4, DoubleBuffer var5, int var6, DoubleBuffer var7);

   int orgqr(LayoutModified var1, int var2, int var3, int var4, DoublePointer var5, int var6, DoublePointer var7);

   int orgqr(LayoutModified var1, int var2, int var3, int var4, float[] var5, int var6, float[] var7);

   int orgqr(LayoutModified var1, int var2, int var3, int var4, FloatBuffer var5, int var6, FloatBuffer var7);

   int trtrs(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, int var6, double[] var7, int var8, double[] var9, int var10);

   int trtrs(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, int var6, DoubleBuffer var7, int var8, DoubleBuffer var9, int var10);

   int trtrs(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, int var6, DoublePointer var7, int var8, DoublePointer var9, int var10);

   int trtrs(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, int var6, float[] var7, int var8, float[] var9, int var10);

   int trtrs(LayoutModified var1, UPLOModified var2, TransposeModified var3, DiagModified var4, int var5, int var6, FloatBuffer var7, int var8, FloatBuffer var9, int var10);
}
