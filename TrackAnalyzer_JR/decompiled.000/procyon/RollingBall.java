// 
// Decompiled by Procyon v0.5.36
// 

class RollingBall
{
    float[] data;
    int width;
    int shrinkFactor;
    
    RollingBall(final double radius) {
        int arcTrimPer;
        if (radius <= 10.0) {
            this.shrinkFactor = 1;
            arcTrimPer = 24;
        }
        else if (radius <= 30.0) {
            this.shrinkFactor = 2;
            arcTrimPer = 24;
        }
        else if (radius <= 100.0) {
            this.shrinkFactor = 4;
            arcTrimPer = 32;
        }
        else {
            this.shrinkFactor = 8;
            arcTrimPer = 40;
        }
        this.buildRollingBall(radius, arcTrimPer);
    }
    
    void buildRollingBall(final double ballradius, final int arcTrimPer) {
        this.shrinkFactor = this.shrinkFactor;
        double smallballradius = ballradius / this.shrinkFactor;
        if (smallballradius < 1.0) {
            smallballradius = 1.0;
        }
        final double rsquare = smallballradius * smallballradius;
        final int xtrim = (int)(arcTrimPer * smallballradius) / 100;
        final int halfWidth = (int)Math.round(smallballradius - xtrim);
        this.width = 2 * halfWidth + 1;
        this.data = new float[this.width * this.width];
        int y = 0;
        int p = 0;
        while (y < this.width) {
            for (int x = 0; x < this.width; ++x, ++p) {
                final int xval = x - halfWidth;
                final int yval = y - halfWidth;
                final double temp = rsquare - xval * xval - yval * yval;
                this.data[p] = ((temp > 0.0) ? ((float)Math.sqrt(temp)) : 0.0f);
            }
            ++y;
        }
    }
}
