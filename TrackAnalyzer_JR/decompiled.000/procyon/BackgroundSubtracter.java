import ij.IJ;
import ij.util.Tools;
import ij.process.ShortProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import java.awt.image.ColorModel;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;

// 
// Decompiled by Procyon v0.5.36
// 

public class BackgroundSubtracter
{
    private static double staticRadius;
    private static boolean staticLightBackground;
    private static boolean staticSeparateColors;
    private static boolean staticCreateBackground;
    private static boolean staticUseParaboloid;
    private static boolean staticDoPresmooth;
    private double radius;
    private boolean lightBackground;
    private boolean separateColors;
    private boolean createBackground;
    private boolean useParaboloid;
    private boolean doPresmooth;
    private boolean isRGB;
    private boolean previewing;
    private static final int MAXIMUM = 0;
    private static final int MEAN = 1;
    private static final int X_DIRECTION = 0;
    private static final int Y_DIRECTION = 1;
    private static final int DIAGONAL_1A = 2;
    private static final int DIAGONAL_1B = 3;
    private static final int DIAGONAL_2A = 4;
    private static final int DIAGONAL_2B = 5;
    private static final int DIRECTION_PASSES = 9;
    private int nPasses;
    private int pass;
    private boolean calledAsPlugin;
    double meanValue;
    
    static {
        BackgroundSubtracter.staticRadius = 50.0;
        BackgroundSubtracter.staticLightBackground = false;
        BackgroundSubtracter.staticDoPresmooth = false;
    }
    
    public BackgroundSubtracter() {
        this.radius = BackgroundSubtracter.staticRadius;
        this.lightBackground = BackgroundSubtracter.staticLightBackground;
        this.separateColors = BackgroundSubtracter.staticSeparateColors;
        this.createBackground = BackgroundSubtracter.staticCreateBackground;
        this.useParaboloid = BackgroundSubtracter.staticUseParaboloid;
        this.doPresmooth = BackgroundSubtracter.staticDoPresmooth;
        this.nPasses = 9;
    }
    
    public void rollingBallBrightnessBackground(final ColorProcessor ip, final double radius, final boolean createBackground, final boolean lightBackground, final boolean useParaboloid, final boolean doPresmooth, final boolean correctCorners) {
        final int width = ip.getWidth();
        final int height = ip.getHeight();
        final byte[] H = new byte[width * height];
        final byte[] S = new byte[width * height];
        final byte[] B = new byte[width * height];
        ip.getHSB(H, S, B);
        final ByteProcessor bp = new ByteProcessor(width, height, B, (ColorModel)null);
        this.rollingBallBackground((ImageProcessor)bp, radius, createBackground, lightBackground, useParaboloid, doPresmooth, correctCorners);
        ip.setHSB(H, S, (byte[])bp.getPixels());
    }
    
    public double rollingBallBackground(final ImageProcessor ip, final double radius, final boolean createBackground, final boolean lightBackground, final boolean useParaboloid, final boolean doPresmooth, final boolean correctCorners) {
        final boolean invertedLut = ip.isInvertedLut();
        final boolean invert = (invertedLut && !lightBackground) || (!invertedLut && lightBackground);
        RollingBall ball = null;
        if (!useParaboloid) {
            ball = new RollingBall(radius);
        }
        FloatProcessor fp = null;
        for (int channelNumber = 0; channelNumber < ip.getNChannels(); ++channelNumber) {
            fp = ip.toFloat(channelNumber, fp);
            if (ip instanceof FloatProcessor && !this.calledAsPlugin && !createBackground) {
                fp.snapshot();
            }
            this.rollingBallFloatBackground(fp, (float)radius, invert, doPresmooth, ball);
            if (createBackground) {
                ip.setPixels(channelNumber, fp);
            }
            else {
                final float[] bgPixels = (float[])fp.getPixels();
                if (ip instanceof FloatProcessor) {
                    final float[] snapshotPixels = (float[])fp.getSnapshotPixels();
                    for (int p = 0; p < bgPixels.length; ++p) {
                        bgPixels[p] = snapshotPixels[p] - bgPixels[p];
                    }
                }
                else if (ip instanceof ShortProcessor) {
                    final float offset = invert ? 65535.5f : 0.5f;
                    final short[] pixels = (short[])ip.getPixels();
                    final short[] pixelsToSubtract = (short[])ip.getPixels();
                    for (int p2 = 0; p2 < bgPixels.length; ++p2) {
                        float value = (pixels[p2] & 0xFFFF) - bgPixels[p2] + offset;
                        float valueToSubtract = bgPixels[p2] + offset;
                        if (value < 0.0f) {
                            value = 0.0f;
                        }
                        if (valueToSubtract < 0.0f) {
                            valueToSubtract = 0.0f;
                        }
                        if (value > 65535.0f) {
                            value = 65535.0f;
                        }
                        if (valueToSubtract > 65535.0f) {
                            valueToSubtract = 65535.0f;
                        }
                        pixels[p2] = (short)value;
                        pixelsToSubtract[p2] = (short)valueToSubtract;
                    }
                    double total = 0.0;
                    for (int i = 0; i < pixelsToSubtract.length; ++i) {
                        total += pixelsToSubtract[i];
                    }
                    this.meanValue = total / pixelsToSubtract.length;
                }
                else if (ip instanceof ByteProcessor) {
                    final float offset = invert ? 255.5f : 0.5f;
                    final byte[] pixels2 = (byte[])ip.getPixels();
                    final byte[] pixelsToSubtract2 = (byte[])ip.getPixels();
                    for (int p2 = 0; p2 < bgPixels.length; ++p2) {
                        float value = (pixels2[p2] & 0xFF) - bgPixels[p2] + offset;
                        float valueToSubtract = bgPixels[p2] + offset;
                        if (value < 0.0f) {
                            value = 0.0f;
                        }
                        if (valueToSubtract < 0.0f) {
                            valueToSubtract = 0.0f;
                        }
                        if (value > 255.0f) {
                            value = 255.0f;
                        }
                        if (valueToSubtract > 255.0f) {
                            valueToSubtract = 255.0f;
                        }
                        pixels2[p2] = (byte)value;
                        pixelsToSubtract2[p2] = (byte)valueToSubtract;
                    }
                    double total = 0.0;
                    for (int i = 0; i < pixelsToSubtract2.length; ++i) {
                        total += pixelsToSubtract2[i];
                    }
                    this.meanValue = total / pixelsToSubtract2.length;
                }
                else if (ip instanceof ColorProcessor) {
                    final float offset = invert ? 255.5f : 0.5f;
                    final int[] pixels3 = (int[])ip.getPixels();
                    final int[] pixelsToSubtract3 = (int[])ip.getPixels();
                    final int shift = 16 - 8 * channelNumber;
                    final int byteMask = 255 << shift;
                    final int resetMask = -1 ^ 255 << shift;
                    for (int p3 = 0; p3 < bgPixels.length; ++p3) {
                        final int pxl = pixels3[p3];
                        float value2 = ((pxl & byteMask) >> shift) - bgPixels[p3] + offset;
                        float valueToSubtract2 = bgPixels[p3] + offset;
                        if (value2 < 0.0f) {
                            value2 = 0.0f;
                        }
                        if (valueToSubtract2 < 0.0f) {
                            valueToSubtract2 = 0.0f;
                        }
                        if (value2 > 255.0f) {
                            value2 = 255.0f;
                        }
                        if (valueToSubtract2 > 255.0f) {
                            valueToSubtract2 = 255.0f;
                        }
                        pixels3[p3] = ((pxl & resetMask) | (int)value2 << shift);
                        pixelsToSubtract3[p3] = ((pxl & resetMask) | (int)valueToSubtract2 << shift);
                    }
                    double total2 = 0.0;
                    for (int j = 0; j < pixelsToSubtract3.length; ++j) {
                        total2 += pixelsToSubtract3[j];
                    }
                    this.meanValue = total2 / pixelsToSubtract3.length;
                }
            }
        }
        return this.meanValue;
    }
    
    static float[] lineSlideParabola(final float[] pixels, final int start, final int inc, final int length, final float coeff2, final float[] cache, final int[] nextPoint, final float[] correctedEdges) {
        float minValue = Float.MAX_VALUE;
        int lastpoint = 0;
        int firstCorner = length - 1;
        int lastCorner = 0;
        float vPrevious1 = 0.0f;
        float vPrevious2 = 0.0f;
        final float curvatureTest = 1.999f * coeff2;
        for (int i = 0, p = start; i < length; ++i, p += inc) {
            final float v = pixels[p];
            cache[i] = v;
            if (v < minValue) {
                minValue = v;
            }
            if (i >= 2 && vPrevious1 + vPrevious1 - vPrevious2 - v < curvatureTest) {
                nextPoint[lastpoint] = i - 1;
                lastpoint = i - 1;
            }
            vPrevious2 = vPrevious1;
            vPrevious1 = v;
        }
        nextPoint[nextPoint[lastpoint] = length - 1] = Integer.MAX_VALUE;
        int i3;
        for (int i2 = 0; i2 < length - 1; i2 = i3) {
            final float v2 = cache[i2];
            float minSlope = Float.MAX_VALUE;
            i3 = 0;
            for (int searchTo = length, recalculateLimitNow = 0, j = nextPoint[i2]; j < searchTo; j = nextPoint[j], ++recalculateLimitNow) {
                final float v3 = cache[j];
                final float slope = (v3 - v2) / (j - i2) + coeff2 * (j - i2);
                if (slope < minSlope) {
                    minSlope = slope;
                    i3 = j;
                    recalculateLimitNow = -3;
                }
                if (recalculateLimitNow == 0) {
                    final double b = 0.5f * minSlope / coeff2;
                    final int maxSearch = i2 + (int)(b + Math.sqrt(b * b + (v2 - minValue) / coeff2) + 1.0);
                    if (maxSearch < searchTo && maxSearch > 0) {
                        searchTo = maxSearch;
                    }
                }
            }
            if (i2 == 0) {
                firstCorner = i3;
            }
            if (i3 == length - 1) {
                lastCorner = i2;
            }
            for (int j = i2 + 1, p2 = start + j * inc; j < i3; ++j, p2 += inc) {
                pixels[p2] = v2 + (j - i2) * (minSlope - (j - i2) * coeff2);
            }
        }
        if (correctedEdges != null) {
            if (4 * firstCorner >= length) {
                firstCorner = 0;
            }
            if (4 * (length - 1 - lastCorner) >= length) {
                lastCorner = length - 1;
            }
            final float v2 = cache[firstCorner];
            final float v4 = cache[lastCorner];
            final float slope2 = (v4 - v2) / (lastCorner - firstCorner);
            final float value0 = v2 - slope2 * firstCorner;
            float coeff3 = 0.0f;
            final float mid = 0.5f * (lastCorner + firstCorner);
            for (int k = (length + 2) / 3; k <= 2 * length / 3; ++k) {
                final float dx = (k - mid) * 2.0f / (lastCorner - firstCorner);
                final float poly6 = dx * dx * dx * dx * dx * dx - 1.0f;
                if (cache[k] < value0 + slope2 * k + coeff3 * poly6) {
                    coeff3 = -(value0 + slope2 * k - cache[k]) / poly6;
                }
            }
            float dx2 = (firstCorner - mid) * 2.0f / (lastCorner - firstCorner);
            correctedEdges[0] = value0 + coeff3 * (dx2 * dx2 * dx2 * dx2 * dx2 * dx2 - 1.0f) + coeff2 * firstCorner * firstCorner;
            dx2 = (lastCorner - mid) * 2.0f / (lastCorner - firstCorner);
            correctedEdges[1] = value0 + (length - 1) * slope2 + coeff3 * (dx2 * dx2 * dx2 * dx2 * dx2 * dx2 - 1.0f) + coeff2 * (length - 1 - lastCorner) * (length - 1 - lastCorner);
        }
        return correctedEdges;
    }
    
    void correctCorners(final FloatProcessor fp, final float coeff2, final float[] cache, final int[] nextPoint) {
        final int width = fp.getWidth();
        final int height = fp.getHeight();
        final float[] pixels = (float[])fp.getPixels();
        final float[] corners = new float[4];
        float[] correctedEdges = new float[2];
        correctedEdges = lineSlideParabola(pixels, 0, 1, width, coeff2, cache, nextPoint, correctedEdges);
        corners[0] = correctedEdges[0];
        corners[1] = correctedEdges[1];
        correctedEdges = lineSlideParabola(pixels, (height - 1) * width, 1, width, coeff2, cache, nextPoint, correctedEdges);
        corners[2] = correctedEdges[0];
        corners[3] = correctedEdges[1];
        correctedEdges = lineSlideParabola(pixels, 0, width, height, coeff2, cache, nextPoint, correctedEdges);
        final float[] array = corners;
        final int n = 0;
        array[n] += correctedEdges[0];
        final float[] array2 = corners;
        final int n2 = 2;
        array2[n2] += correctedEdges[1];
        correctedEdges = lineSlideParabola(pixels, width - 1, width, height, coeff2, cache, nextPoint, correctedEdges);
        final float[] array3 = corners;
        final int n3 = 1;
        array3[n3] += correctedEdges[0];
        final float[] array4 = corners;
        final int n4 = 3;
        array4[n4] += correctedEdges[1];
        final int diagLength = Math.min(width, height);
        final float coeff2diag = 2.0f * coeff2;
        correctedEdges = lineSlideParabola(pixels, 0, 1 + width, diagLength, coeff2diag, cache, nextPoint, correctedEdges);
        final float[] array5 = corners;
        final int n5 = 0;
        array5[n5] += correctedEdges[0];
        correctedEdges = lineSlideParabola(pixels, width - 1, -1 + width, diagLength, coeff2diag, cache, nextPoint, correctedEdges);
        final float[] array6 = corners;
        final int n6 = 1;
        array6[n6] += correctedEdges[0];
        correctedEdges = lineSlideParabola(pixels, (height - 1) * width, 1 - width, diagLength, coeff2diag, cache, nextPoint, correctedEdges);
        final float[] array7 = corners;
        final int n7 = 2;
        array7[n7] += correctedEdges[0];
        correctedEdges = lineSlideParabola(pixels, width * height - 1, -1 - width, diagLength, coeff2diag, cache, nextPoint, correctedEdges);
        final float[] array8 = corners;
        final int n8 = 3;
        array8[n8] += correctedEdges[0];
        if (pixels[0] > corners[0] / 3.0f) {
            pixels[0] = corners[0] / 3.0f;
        }
        if (pixels[width - 1] > corners[1] / 3.0f) {
            pixels[width - 1] = corners[1] / 3.0f;
        }
        if (pixels[(height - 1) * width] > corners[2] / 3.0f) {
            pixels[(height - 1) * width] = corners[2] / 3.0f;
        }
        if (pixels[width * height - 1] > corners[3] / 3.0f) {
            pixels[width * height - 1] = corners[3] / 3.0f;
        }
    }
    
    void rollingBallFloatBackground(final FloatProcessor fp, final float radius, final boolean invert, final boolean doPresmooth, final RollingBall ball) {
        final float[] pixels = (float[])fp.getPixels();
        final boolean shrink = ball.shrinkFactor > 1;
        this.showProgress(0.0);
        if (invert) {
            for (int i = 0; i < pixels.length; ++i) {
                pixels[i] = -pixels[i];
            }
        }
        if (doPresmooth) {
            this.filter3x3(fp, 1);
        }
        final double[] minmax = Tools.getMinMax(pixels);
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        final FloatProcessor smallImage = shrink ? this.shrinkImage(fp, ball.shrinkFactor) : fp;
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        this.rollBall(ball, smallImage);
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        this.showProgress(0.9);
        if (shrink) {
            this.enlargeImage(smallImage, fp, ball.shrinkFactor);
        }
        if (Thread.currentThread().isInterrupted()) {
            return;
        }
        if (invert) {
            for (int j = 0; j < pixels.length; ++j) {
                pixels[j] = -pixels[j];
            }
        }
        ++this.pass;
    }
    
    FloatProcessor shrinkImage(final FloatProcessor ip, final int shrinkFactor) {
        final int width = ip.getWidth();
        final int height = ip.getHeight();
        final float[] pixels = (float[])ip.getPixels();
        final int sWidth = (width + shrinkFactor - 1) / shrinkFactor;
        final int sHeight = (height + shrinkFactor - 1) / shrinkFactor;
        this.showProgress(0.1);
        final FloatProcessor smallImage = new FloatProcessor(sWidth, sHeight);
        final float[] sPixels = (float[])smallImage.getPixels();
        for (int ySmall = 0; ySmall < sHeight; ++ySmall) {
            for (int xSmall = 0; xSmall < sWidth; ++xSmall) {
                float min = Float.MAX_VALUE;
                for (int j = 0, y = shrinkFactor * ySmall; j < shrinkFactor && y < height; ++j, ++y) {
                    for (int k = 0, x = shrinkFactor * xSmall; k < shrinkFactor && x < width; ++k, ++x) {
                        final float thispixel = pixels[x + y * width];
                        if (thispixel < min) {
                            min = thispixel;
                        }
                    }
                }
                sPixels[xSmall + ySmall * sWidth] = min;
            }
        }
        return smallImage;
    }
    
    void rollBall(final RollingBall ball, final FloatProcessor fp) {
        final float[] pixels = (float[])fp.getPixels();
        final int width = fp.getWidth();
        final int height = fp.getHeight();
        final float[] zBall = ball.data;
        final int ballWidth = ball.width;
        final int radius = ballWidth / 2;
        final float[] cache = new float[width * ballWidth];
        final Thread thread = Thread.currentThread();
        long lastTime = System.currentTimeMillis();
        for (int y = -radius; y < height + radius; ++y) {
            final long time = System.currentTimeMillis();
            if (time - lastTime > 100L) {
                lastTime = time;
                if (thread.isInterrupted()) {
                    return;
                }
                this.showProgress(0.1 + 0.8 * y / (height + ballWidth));
            }
            final int nextLineToWriteInCache = (y + radius) % ballWidth;
            final int nextLineToRead = y + radius;
            if (nextLineToRead < height) {
                System.arraycopy(pixels, nextLineToRead * width, cache, nextLineToWriteInCache * width, width);
                for (int x = 0, p = nextLineToRead * width; x < width; ++x, ++p) {
                    pixels[p] = -3.4028235E38f;
                }
            }
            int y2 = y - radius;
            if (y2 < 0) {
                y2 = 0;
            }
            final int yBall0 = y2 - y + radius;
            int yend = y + radius;
            if (yend >= height) {
                yend = height - 1;
            }
            for (int x2 = -radius; x2 < width + radius; ++x2) {
                float z = Float.MAX_VALUE;
                int x3 = x2 - radius;
                if (x3 < 0) {
                    x3 = 0;
                }
                final int xBall0 = x3 - x2 + radius;
                int xend = x2 + radius;
                if (xend >= width) {
                    xend = width - 1;
                }
                for (int yp = y2, yBall2 = yBall0; yp <= yend; ++yp, ++yBall2) {
                    for (int cachePointer = yp % ballWidth * width + x3, xp = x3, bp = xBall0 + yBall2 * ballWidth; xp <= xend; ++xp, ++cachePointer, ++bp) {
                        final float zReduced = cache[cachePointer] - zBall[bp];
                        if (z > zReduced) {
                            z = zReduced;
                        }
                    }
                }
                for (int yp = y2, yBall2 = yBall0; yp <= yend; ++yp, ++yBall2) {
                    for (int xp2 = x3, p2 = xp2 + yp * width, bp = xBall0 + yBall2 * ballWidth; xp2 <= xend; ++xp2, ++p2, ++bp) {
                        final float zMin = z + zBall[bp];
                        if (pixels[p2] < zMin) {
                            pixels[p2] = zMin;
                        }
                    }
                }
            }
        }
    }
    
    void enlargeImage(final FloatProcessor smallImage, final FloatProcessor fp, final int shrinkFactor) {
        final int width = fp.getWidth();
        final int height = fp.getHeight();
        final int smallWidth = smallImage.getWidth();
        final int smallHeight = smallImage.getHeight();
        final float[] pixels = (float[])fp.getPixels();
        final float[] sPixels = (float[])smallImage.getPixels();
        final int[] xSmallIndices = new int[width];
        final float[] xWeights = new float[width];
        this.makeInterpolationArrays(xSmallIndices, xWeights, width, smallWidth, shrinkFactor);
        final int[] ySmallIndices = new int[height];
        final float[] yWeights = new float[height];
        this.makeInterpolationArrays(ySmallIndices, yWeights, height, smallHeight, shrinkFactor);
        float[] line0 = new float[width];
        float[] line2 = new float[width];
        for (int x = 0; x < width; ++x) {
            line2[x] = sPixels[xSmallIndices[x]] * xWeights[x] + sPixels[xSmallIndices[x] + 1] * (1.0f - xWeights[x]);
        }
        int ySmallLine0 = -1;
        for (int y = 0; y < height; ++y) {
            if (ySmallLine0 < ySmallIndices[y]) {
                final float[] swap = line0;
                line0 = line2;
                line2 = swap;
                ++ySmallLine0;
                final int sYPointer = (ySmallIndices[y] + 1) * smallWidth;
                for (int x2 = 0; x2 < width; ++x2) {
                    line2[x2] = sPixels[sYPointer + xSmallIndices[x2]] * xWeights[x2] + sPixels[sYPointer + xSmallIndices[x2] + 1] * (1.0f - xWeights[x2]);
                }
            }
            final float weight = yWeights[y];
            for (int x3 = 0, p = y * width; x3 < width; ++x3, ++p) {
                pixels[p] = line0[x3] * weight + line2[x3] * (1.0f - weight);
            }
        }
    }
    
    void makeInterpolationArrays(final int[] smallIndices, final float[] weights, final int length, final int smallLength, final int shrinkFactor) {
        for (int i = 0; i < length; ++i) {
            int smallIndex = (i - shrinkFactor / 2) / shrinkFactor;
            if (smallIndex >= smallLength - 1) {
                smallIndex = smallLength - 2;
            }
            smallIndices[i] = smallIndex;
            final float distance = (i + 0.5f) / shrinkFactor - (smallIndex + 0.5f);
            weights[i] = 1.0f - distance;
        }
    }
    
    double filter3x3(final FloatProcessor fp, final int type) {
        final int width = fp.getWidth();
        final int height = fp.getHeight();
        double shiftBy = 0.0;
        final float[] pixels = (float[])fp.getPixels();
        for (int y = 0; y < height; ++y) {
            shiftBy += this.filter3(pixels, width, y * width, 1, type);
        }
        for (int x = 0; x < width; ++x) {
            shiftBy += this.filter3(pixels, height, x, width, type);
        }
        return shiftBy / width / height;
    }
    
    double filter3(final float[] pixels, final int length, final int pixel0, final int inc, final int type) {
        double shiftBy = 0.0;
        float v4;
        float v3 = v4 = pixels[pixel0];
        for (int i = 0, p = pixel0; i < length; ++i, p += inc) {
            final float v5 = v4;
            v4 = v3;
            if (i < length - 1) {
                v3 = pixels[p + inc];
            }
            if (type == 0) {
                float max = (v5 > v3) ? v5 : v3;
                if (v4 > max) {
                    max = v4;
                }
                shiftBy += max - v4;
                pixels[p] = max;
            }
            else {
                pixels[p] = (v5 + v4 + v3) * 0.33333334f;
            }
        }
        return shiftBy;
    }
    
    public void setNPasses(int nPasses) {
        if (this.isRGB && this.separateColors) {
            nPasses *= 3;
        }
        if (this.useParaboloid) {
            nPasses *= (this.doPresmooth ? 11 : 9);
        }
        this.nPasses = nPasses;
        this.pass = 0;
    }
    
    private void showProgress(double percent) {
        if (this.nPasses <= 0) {
            return;
        }
        percent = this.pass / (double)this.nPasses + percent / this.nPasses;
        IJ.showProgress(percent);
    }
}
