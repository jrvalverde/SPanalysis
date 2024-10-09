package smileModified;

public class ErfModified {
   private static final double[] cof = new double[]{-1.3026537197817094D, 0.6419697923564902D, 0.019476473204185836D, -0.00956151478680863D, -9.46595344482036E-4D, 3.66839497852761E-4D, 4.2523324806907E-5D, -2.0278578112534E-5D, -1.624290004647E-6D, 1.30365583558E-6D, 1.5626441722E-8D, -8.5238095915E-8D, 6.529054439E-9D, 5.059343495E-9D, -9.91364156E-10D, -2.27365122E-10D, 9.6467911E-11D, 2.394038E-12D, -6.886027E-12D, 8.94487E-13D, 3.13092E-13D, -1.12708E-13D, 3.81E-16D, 7.106E-15D, -1.523E-15D, -9.4E-17D, 1.21E-16D, -2.8E-17D};

   private ErfModified() {
   }

   public static double erf(double x) {
      return x >= 0.0D ? 1.0D - erfccheb(x) : erfccheb(-x) - 1.0D;
   }

   public static double erfc(double x) {
      return x >= 0.0D ? erfccheb(x) : 2.0D - erfccheb(-x);
   }

   public static double erfcc(double x) {
      double z = Math.abs(x);
      double t = 2.0D / (2.0D + z);
      double ans = t * Math.exp(-z * z - 1.26551223D + t * (1.00002368D + t * (0.37409196D + t * (0.09678418D + t * (-0.18628806D + t * (0.27886807D + t * (-1.13520398D + t * (1.48851587D + t * (-0.82215223D + t * 0.17087277D)))))))));
      return x >= 0.0D ? ans : 2.0D - ans;
   }

   private static double erfccheb(double z) {
      double d = 0.0D;
      double dd = 0.0D;
      if (z < 0.0D) {
         throw new IllegalArgumentException("erfccheb requires non-negative argument");
      } else {
         double t = 2.0D / (2.0D + z);
         double ty = 4.0D * t - 2.0D;

         for(int j = cof.length - 1; j > 0; --j) {
            double tmp = d;
            d = ty * d - dd + cof[j];
            dd = tmp;
         }

         return t * Math.exp(-z * z + 0.5D * (cof[0] + ty * d) - dd);
      }
   }

   public static double inverfc(double p) {
      if (p >= 2.0D) {
         return -100.0D;
      } else if (p <= 0.0D) {
         return 100.0D;
      } else {
         double pp = p < 1.0D ? p : 2.0D - p;
         double t = Math.sqrt(-2.0D * Math.log(pp / 2.0D));
         double x = -0.70711D * ((2.30753D + t * 0.27061D) / (1.0D + t * (0.99229D + t * 0.04481D)) - t);

         for(int j = 0; j < 2; ++j) {
            double err = erfc(x) - pp;
            x += err / (1.1283791670955126D * Math.exp(-x * x) - x * err);
         }

         return p < 1.0D ? x : -x;
      }
   }

   public static double inverf(double p) {
      return inverfc(1.0D - p);
   }
}
