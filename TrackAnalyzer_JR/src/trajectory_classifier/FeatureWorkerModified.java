package trajectory_classifier;

import features.AbstractTrajectoryFeatureModified;

public class FeatureWorkerModified extends Thread {
   double[] result;
   AbstractTrajectoryFeatureModified c;
   FeatureWorkerModified.EVALTYPE ev;
   int resIndex;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$trajectory_classifier$FeatureWorkerModified$EVALTYPE;

   public FeatureWorkerModified(double[] result, int resIndex, AbstractTrajectoryFeatureModified c, FeatureWorkerModified.EVALTYPE ev) {
      this.result = result;
      this.c = c;
      this.ev = ev;
      this.resIndex = resIndex;
   }

   public void run() {
      double[] res;
      switch($SWITCH_TABLE$trajectory_classifier$FeatureWorkerModified$EVALTYPE()[this.ev.ordinal()]) {
      case 1:
         res = this.c.getValue();
         this.result[this.resIndex] = res[0];
         break;
      case 2:
         res = this.c.getValue();
         this.result[this.resIndex] = res[1];
         break;
      case 3:
         res = this.c.getValue();
         this.result[this.resIndex] = res[0] / res[1];
         break;
      case 4:
         res = this.c.getValue();
         this.result[this.resIndex] = res[1] / res[0];
         break;
      case 5:
         res = this.c.getValue();
         this.result[this.resIndex] = res[1] / res[2];
      }

   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$trajectory_classifier$FeatureWorkerModified$EVALTYPE() {
      int[] var10000 = $SWITCH_TABLE$trajectory_classifier$FeatureWorkerModified$EVALTYPE;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[FeatureWorkerModified.EVALTYPE.values().length];

         try {
            var0[FeatureWorkerModified.EVALTYPE.FIRST.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            var0[FeatureWorkerModified.EVALTYPE.RATIO_01.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            var0[FeatureWorkerModified.EVALTYPE.RATIO_10.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[FeatureWorkerModified.EVALTYPE.RATIO_12.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[FeatureWorkerModified.EVALTYPE.SECOND.ordinal()] = 2;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$trajectory_classifier$FeatureWorkerModified$EVALTYPE = var0;
         return var0;
      }
   }

   static enum EVALTYPE {
      FIRST,
      SECOND,
      RATIO_01,
      RATIO_10,
      RATIO_12;
   }
}
