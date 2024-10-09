class RollingBall {
   float[] data;
   int width;
   int shrinkFactor;

   RollingBall(double radius) {
      byte arcTrimPer;
      if (radius <= 10.0D) {
         this.shrinkFactor = 1;
         arcTrimPer = 24;
      } else if (radius <= 30.0D) {
         this.shrinkFactor = 2;
         arcTrimPer = 24;
      } else if (radius <= 100.0D) {
         this.shrinkFactor = 4;
         arcTrimPer = 32;
      } else {
         this.shrinkFactor = 8;
         arcTrimPer = 40;
      }

      this.buildRollingBall(radius, arcTrimPer);
   }

   void buildRollingBall(double ballradius, int arcTrimPer) {
      this.shrinkFactor = this.shrinkFactor;
      double smallballradius = ballradius / (double)this.shrinkFactor;
      if (smallballradius < 1.0D) {
         smallballradius = 1.0D;
      }

      double rsquare = smallballradius * smallballradius;
      int xtrim = (int)((double)arcTrimPer * smallballradius) / 100;
      int halfWidth = (int)Math.round(smallballradius - (double)xtrim);
      this.width = 2 * halfWidth + 1;
      this.data = new float[this.width * this.width];
      int y = 0;

      for(int p = 0; y < this.width; ++y) {
         for(int x = 0; x < this.width; ++p) {
            int xval = x - halfWidth;
            int yval = y - halfWidth;
            double temp = rsquare - (double)(xval * xval) - (double)(yval * yval);
            this.data[p] = temp > 0.0D ? (float)Math.sqrt(temp) : 0.0F;
            ++x;
         }
      }

   }
}
