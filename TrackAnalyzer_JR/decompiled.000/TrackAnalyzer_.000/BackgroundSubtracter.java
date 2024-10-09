import ij.IJ;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.process.ShortProcessor;
import ij.util.Tools;
import java.awt.image.ColorModel;

public class BackgroundSubtracter {
   private static double staticRadius = 50.0D;
   private static boolean staticLightBackground = false;
   private static boolean staticSeparateColors;
   private static boolean staticCreateBackground;
   private static boolean staticUseParaboloid;
   private static boolean staticDoPresmooth = false;
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

   public BackgroundSubtracter() {
      this.radius = staticRadius;
      this.lightBackground = staticLightBackground;
      this.separateColors = staticSeparateColors;
      this.createBackground = staticCreateBackground;
      this.useParaboloid = staticUseParaboloid;
      this.doPresmooth = staticDoPresmooth;
      this.nPasses = 9;
   }

   public void rollingBallBrightnessBackground(ColorProcessor ip, double radius, boolean createBackground, boolean lightBackground, boolean useParaboloid, boolean doPresmooth, boolean correctCorners) {
      int width = ip.getWidth();
      int height = ip.getHeight();
      byte[] H = new byte[width * height];
      byte[] S = new byte[width * height];
      byte[] B = new byte[width * height];
      ip.getHSB(H, S, B);
      ByteProcessor bp = new ByteProcessor(width, height, B, (ColorModel)null);
      this.rollingBallBackground(bp, radius, createBackground, lightBackground, useParaboloid, doPresmooth, correctCorners);
      ip.setHSB(H, S, (byte[])bp.getPixels());
   }

   public double rollingBallBackground(ImageProcessor ip, double radius, boolean createBackground, boolean lightBackground, boolean useParaboloid, boolean doPresmooth, boolean correctCorners) {
      boolean invertedLut = ip.isInvertedLut();
      boolean invert = invertedLut && !lightBackground || !invertedLut && lightBackground;
      RollingBall ball = null;
      if (!useParaboloid) {
         ball = new RollingBall(radius);
      }

      FloatProcessor fp = null;

      for(int channelNumber = 0; channelNumber < ip.getNChannels(); ++channelNumber) {
         fp = ip.toFloat(channelNumber, fp);
         if (ip instanceof FloatProcessor && !this.calledAsPlugin && !createBackground) {
            fp.snapshot();
         }

         this.rollingBallFloatBackground(fp, (float)radius, invert, doPresmooth, ball);
         if (createBackground) {
            ip.setPixels(channelNumber, fp);
         } else {
            float[] bgPixels = (float[])fp.getPixels();
            if (ip instanceof FloatProcessor) {
               float[] snapshotPixels = (float[])fp.getSnapshotPixels();

               for(int p = 0; p < bgPixels.length; ++p) {
                  bgPixels[p] = snapshotPixels[p] - bgPixels[p];
               }
            } else {
               float offset;
               int shift;
               int resetMask;
               double total;
               float value;
               float valueToSubtract;
               if (ip instanceof ShortProcessor) {
                  offset = invert ? 65535.5F : 0.5F;
                  short[] pixels = (short[])ip.getPixels();
                  short[] pixelsToSubtract = (short[])ip.getPixels();

                  for(shift = 0; shift < bgPixels.length; ++shift) {
                     value = (float)(pixels[shift] & '\uffff') - bgPixels[shift] + offset;
                     valueToSubtract = bgPixels[shift] + offset;
                     if (value < 0.0F) {
                        value = 0.0F;
                     }

                     if (valueToSubtract < 0.0F) {
                        valueToSubtract = 0.0F;
                     }

                     if (value > 65535.0F) {
                        value = 65535.0F;
                     }

                     if (valueToSubtract > 65535.0F) {
                        valueToSubtract = 65535.0F;
                     }

                     pixels[shift] = (short)((int)value);
                     pixelsToSubtract[shift] = (short)((int)valueToSubtract);
                  }

                  total = 0.0D;

                  for(resetMask = 0; resetMask < pixelsToSubtract.length; ++resetMask) {
                     total += (double)pixelsToSubtract[resetMask];
                  }

                  this.meanValue = total / (double)pixelsToSubtract.length;
               } else if (ip instanceof ByteProcessor) {
                  offset = invert ? 255.5F : 0.5F;
                  byte[] pixels = (byte[])ip.getPixels();
                  byte[] pixelsToSubtract = (byte[])ip.getPixels();

                  for(shift = 0; shift < bgPixels.length; ++shift) {
                     value = (float)(pixels[shift] & 255) - bgPixels[shift] + offset;
                     valueToSubtract = bgPixels[shift] + offset;
                     if (value < 0.0F) {
                        value = 0.0F;
                     }

                     if (valueToSubtract < 0.0F) {
                        valueToSubtract = 0.0F;
                     }

                     if (value > 255.0F) {
                        value = 255.0F;
                     }

                     if (valueToSubtract > 255.0F) {
                        valueToSubtract = 255.0F;
                     }

                     pixels[shift] = (byte)((int)value);
                     pixelsToSubtract[shift] = (byte)((int)valueToSubtract);
                  }

                  total = 0.0D;

                  for(resetMask = 0; resetMask < pixelsToSubtract.length; ++resetMask) {
                     total += (double)pixelsToSubtract[resetMask];
                  }

                  this.meanValue = total / (double)pixelsToSubtract.length;
               } else if (ip instanceof ColorProcessor) {
                  offset = invert ? 255.5F : 0.5F;
                  int[] pixels = (int[])ip.getPixels();
                  int[] pixelsToSubtract = (int[])ip.getPixels();
                  shift = 16 - 8 * channelNumber;
                  int byteMask = 255 << shift;
                  resetMask = ~(255 << shift);

                  for(int p = 0; p < bgPixels.length; ++p) {
                     int pxl = pixels[p];
                     float value = (float)((pxl & byteMask) >> shift) - bgPixels[p] + offset;
                     float valueToSubtract = bgPixels[p] + offset;
                     if (value < 0.0F) {
                        value = 0.0F;
                     }

                     if (valueToSubtract < 0.0F) {
                        valueToSubtract = 0.0F;
                     }

                     if (value > 255.0F) {
                        value = 255.0F;
                     }

                     if (valueToSubtract > 255.0F) {
                        valueToSubtract = 255.0F;
                     }

                     pixels[p] = pxl & resetMask | (int)value << shift;
                     pixelsToSubtract[p] = pxl & resetMask | (int)valueToSubtract << shift;
                  }

                  double total = 0.0D;

                  for(int i = 0; i < pixelsToSubtract.length; ++i) {
                     total += (double)pixelsToSubtract[i];
                  }

                  this.meanValue = total / (double)pixelsToSubtract.length;
               }
            }
         }
      }

      return this.meanValue;
   }

   static float[] lineSlideParabola(float[] pixels, int start, int inc, int length, float coeff2, float[] cache, int[] nextPoint, float[] correctedEdges) {
      float minValue = Float.MAX_VALUE;
      int lastpoint = 0;
      int firstCorner = length - 1;
      int lastCorner = 0;
      float vPrevious1 = 0.0F;
      float vPrevious2 = 0.0F;
      float curvatureTest = 1.999F * coeff2;
      int i1 = 0;

      float minSlope;
      for(int p = start; i1 < length; p += inc) {
         minSlope = pixels[p];
         cache[i1] = minSlope;
         if (minSlope < minValue) {
            minValue = minSlope;
         }

         if (i1 >= 2 && vPrevious1 + vPrevious1 - vPrevious2 - minSlope < curvatureTest) {
            nextPoint[lastpoint] = i1 - 1;
            lastpoint = i1 - 1;
         }

         vPrevious2 = vPrevious1;
         vPrevious1 = minSlope;
         ++i1;
      }

      nextPoint[lastpoint] = length - 1;
      nextPoint[length - 1] = Integer.MAX_VALUE;

      int i2;
      float dx;
      float dx;
      float v1;
      int i;
      for(i1 = 0; i1 < length - 1; i1 = i2) {
         v1 = cache[i1];
         minSlope = Float.MAX_VALUE;
         i2 = 0;
         int searchTo = length;
         int recalculateLimitNow = 0;

         int j;
         for(j = nextPoint[i1]; j < searchTo; ++recalculateLimitNow) {
            dx = cache[j];
            dx = (dx - v1) / (float)(j - i1) + coeff2 * (float)(j - i1);
            if (dx < minSlope) {
               minSlope = dx;
               i2 = j;
               recalculateLimitNow = -3;
            }

            if (recalculateLimitNow == 0) {
               double b = (double)(0.5F * minSlope / coeff2);
               int maxSearch = i1 + (int)(b + Math.sqrt(b * b + (double)((v1 - minValue) / coeff2)) + 1.0D);
               if (maxSearch < searchTo && maxSearch > 0) {
                  searchTo = maxSearch;
               }
            }

            j = nextPoint[j];
         }

         if (i1 == 0) {
            firstCorner = i2;
         }

         if (i2 == length - 1) {
            lastCorner = i1;
         }

         j = i1 + 1;

         for(i = start + j * inc; j < i2; i += inc) {
            pixels[i] = v1 + (float)(j - i1) * (minSlope - (float)(j - i1) * coeff2);
            ++j;
         }
      }

      if (correctedEdges != null) {
         if (4 * firstCorner >= length) {
            firstCorner = 0;
         }

         if (4 * (length - 1 - lastCorner) >= length) {
            lastCorner = length - 1;
         }

         v1 = cache[firstCorner];
         minSlope = cache[lastCorner];
         float slope = (minSlope - v1) / (float)(lastCorner - firstCorner);
         float value0 = v1 - slope * (float)firstCorner;
         float coeff6 = 0.0F;
         float mid = 0.5F * (float)(lastCorner + firstCorner);

         for(i = (length + 2) / 3; i <= 2 * length / 3; ++i) {
            dx = ((float)i - mid) * 2.0F / (float)(lastCorner - firstCorner);
            float poly6 = dx * dx * dx * dx * dx * dx - 1.0F;
            if (cache[i] < value0 + slope * (float)i + coeff6 * poly6) {
               coeff6 = -(value0 + slope * (float)i - cache[i]) / poly6;
            }
         }

         dx = ((float)firstCorner - mid) * 2.0F / (float)(lastCorner - firstCorner);
         correctedEdges[0] = value0 + coeff6 * (dx * dx * dx * dx * dx * dx - 1.0F) + coeff2 * (float)firstCorner * (float)firstCorner;
         dx = ((float)lastCorner - mid) * 2.0F / (float)(lastCorner - firstCorner);
         correctedEdges[1] = value0 + (float)(length - 1) * slope + coeff6 * (dx * dx * dx * dx * dx * dx - 1.0F) + coeff2 * (float)(length - 1 - lastCorner) * (float)(length - 1 - lastCorner);
      }

      return correctedEdges;
   }

   void correctCorners(FloatProcessor fp, float coeff2, float[] cache, int[] nextPoint) {
      int width = fp.getWidth();
      int height = fp.getHeight();
      float[] pixels = (float[])fp.getPixels();
      float[] corners = new float[4];
      float[] correctedEdges = new float[2];
      correctedEdges = lineSlideParabola(pixels, 0, 1, width, coeff2, cache, nextPoint, correctedEdges);
      corners[0] = correctedEdges[0];
      corners[1] = correctedEdges[1];
      correctedEdges = lineSlideParabola(pixels, (height - 1) * width, 1, width, coeff2, cache, nextPoint, correctedEdges);
      corners[2] = correctedEdges[0];
      corners[3] = correctedEdges[1];
      correctedEdges = lineSlideParabola(pixels, 0, width, height, coeff2, cache, nextPoint, correctedEdges);
      corners[0] += correctedEdges[0];
      corners[2] += correctedEdges[1];
      correctedEdges = lineSlideParabola(pixels, width - 1, width, height, coeff2, cache, nextPoint, correctedEdges);
      corners[1] += correctedEdges[0];
      corners[3] += correctedEdges[1];
      int diagLength = Math.min(width, height);
      float coeff2diag = 2.0F * coeff2;
      correctedEdges = lineSlideParabola(pixels, 0, 1 + width, diagLength, coeff2diag, cache, nextPoint, correctedEdges);
      corners[0] += correctedEdges[0];
      correctedEdges = lineSlideParabola(pixels, width - 1, -1 + width, diagLength, coeff2diag, cache, nextPoint, correctedEdges);
      corners[1] += correctedEdges[0];
      correctedEdges = lineSlideParabola(pixels, (height - 1) * width, 1 - width, diagLength, coeff2diag, cache, nextPoint, correctedEdges);
      corners[2] += correctedEdges[0];
      correctedEdges = lineSlideParabola(pixels, width * height - 1, -1 - width, diagLength, coeff2diag, cache, nextPoint, correctedEdges);
      corners[3] += correctedEdges[0];
      if (pixels[0] > corners[0] / 3.0F) {
         pixels[0] = corners[0] / 3.0F;
      }

      if (pixels[width - 1] > corners[1] / 3.0F) {
         pixels[width - 1] = corners[1] / 3.0F;
      }

      if (pixels[(height - 1) * width] > corners[2] / 3.0F) {
         pixels[(height - 1) * width] = corners[2] / 3.0F;
      }

      if (pixels[width * height - 1] > corners[3] / 3.0F) {
         pixels[width * height - 1] = corners[3] / 3.0F;
      }

   }

   void rollingBallFloatBackground(FloatProcessor fp, float radius, boolean invert, boolean doPresmooth, RollingBall ball) {
      float[] pixels = (float[])fp.getPixels();
      boolean shrink = ball.shrinkFactor > 1;
      this.showProgress(0.0D);
      if (invert) {
         for(int i = 0; i < pixels.length; ++i) {
            pixels[i] = -pixels[i];
         }
      }

      if (doPresmooth) {
         this.filter3x3(fp, 1);
      }

      double[] minmax = Tools.getMinMax(pixels);
      if (!Thread.currentThread().isInterrupted()) {
         FloatProcessor smallImage = shrink ? this.shrinkImage(fp, ball.shrinkFactor) : fp;
         if (!Thread.currentThread().isInterrupted()) {
            this.rollBall(ball, smallImage);
            if (!Thread.currentThread().isInterrupted()) {
               this.showProgress(0.9D);
               if (shrink) {
                  this.enlargeImage(smallImage, fp, ball.shrinkFactor);
               }

               if (!Thread.currentThread().isInterrupted()) {
                  if (invert) {
                     for(int i = 0; i < pixels.length; ++i) {
                        pixels[i] = -pixels[i];
                     }
                  }

                  ++this.pass;
               }
            }
         }
      }
   }

   FloatProcessor shrinkImage(FloatProcessor ip, int shrinkFactor) {
      int width = ip.getWidth();
      int height = ip.getHeight();
      float[] pixels = (float[])ip.getPixels();
      int sWidth = (width + shrinkFactor - 1) / shrinkFactor;
      int sHeight = (height + shrinkFactor - 1) / shrinkFactor;
      this.showProgress(0.1D);
      FloatProcessor smallImage = new FloatProcessor(sWidth, sHeight);
      float[] sPixels = (float[])smallImage.getPixels();

      for(int ySmall = 0; ySmall < sHeight; ++ySmall) {
         for(int xSmall = 0; xSmall < sWidth; ++xSmall) {
            float min = Float.MAX_VALUE;
            int j = 0;

            for(int y = shrinkFactor * ySmall; j < shrinkFactor && y < height; ++y) {
               int k = 0;

               for(int x = shrinkFactor * xSmall; k < shrinkFactor && x < width; ++x) {
                  float thispixel = pixels[x + y * width];
                  if (thispixel < min) {
                     min = thispixel;
                  }

                  ++k;
               }

               ++j;
            }

            sPixels[xSmall + ySmall * sWidth] = min;
         }
      }

      return smallImage;
   }

   void rollBall(RollingBall ball, FloatProcessor fp) {
      float[] pixels = (float[])fp.getPixels();
      int width = fp.getWidth();
      int height = fp.getHeight();
      float[] zBall = ball.data;
      int ballWidth = ball.width;
      int radius = ballWidth / 2;
      float[] cache = new float[width * ballWidth];
      Thread thread = Thread.currentThread();
      long lastTime = System.currentTimeMillis();

      for(int y = -radius; y < height + radius; ++y) {
         long time = System.currentTimeMillis();
         if (time - lastTime > 100L) {
            lastTime = time;
            if (thread.isInterrupted()) {
               return;
            }

            this.showProgress(0.1D + 0.8D * (double)y / (double)(height + ballWidth));
         }

         int nextLineToWriteInCache = (y + radius) % ballWidth;
         int nextLineToRead = y + radius;
         int y0;
         int yBall0;
         if (nextLineToRead < height) {
            System.arraycopy(pixels, nextLineToRead * width, cache, nextLineToWriteInCache * width, width);
            y0 = 0;

            for(yBall0 = nextLineToRead * width; y0 < width; ++yBall0) {
               pixels[yBall0] = -3.4028235E38F;
               ++y0;
            }
         }

         y0 = y - radius;
         if (y0 < 0) {
            y0 = 0;
         }

         yBall0 = y0 - y + radius;
         int yend = y + radius;
         if (yend >= height) {
            yend = height - 1;
         }

         for(int x = -radius; x < width + radius; ++x) {
            float z = Float.MAX_VALUE;
            int x0 = x - radius;
            if (x0 < 0) {
               x0 = 0;
            }

            int xBall0 = x0 - x + radius;
            int xend = x + radius;
            if (xend >= width) {
               xend = width - 1;
            }

            int yp = y0;

            int yBall;
            int xp;
            int p;
            int bp;
            float zMin;
            for(yBall = yBall0; yp <= yend; ++yBall) {
               xp = yp % ballWidth * width + x0;
               p = x0;

               for(bp = xBall0 + yBall * ballWidth; p <= xend; ++bp) {
                  zMin = cache[xp] - zBall[bp];
                  if (z > zMin) {
                     z = zMin;
                  }

                  ++p;
                  ++xp;
               }

               ++yp;
            }

            yp = y0;

            for(yBall = yBall0; yp <= yend; ++yBall) {
               xp = x0;
               p = x0 + yp * width;

               for(bp = xBall0 + yBall * ballWidth; xp <= xend; ++bp) {
                  zMin = z + zBall[bp];
                  if (pixels[p] < zMin) {
                     pixels[p] = zMin;
                  }

                  ++xp;
                  ++p;
               }

               ++yp;
            }
         }
      }

   }

   void enlargeImage(FloatProcessor smallImage, FloatProcessor fp, int shrinkFactor) {
      int width = fp.getWidth();
      int height = fp.getHeight();
      int smallWidth = smallImage.getWidth();
      int smallHeight = smallImage.getHeight();
      float[] pixels = (float[])fp.getPixels();
      float[] sPixels = (float[])smallImage.getPixels();
      int[] xSmallIndices = new int[width];
      float[] xWeights = new float[width];
      this.makeInterpolationArrays(xSmallIndices, xWeights, width, smallWidth, shrinkFactor);
      int[] ySmallIndices = new int[height];
      float[] yWeights = new float[height];
      this.makeInterpolationArrays(ySmallIndices, yWeights, height, smallHeight, shrinkFactor);
      float[] line0 = new float[width];
      float[] line1 = new float[width];

      int ySmallLine0;
      for(ySmallLine0 = 0; ySmallLine0 < width; ++ySmallLine0) {
         line1[ySmallLine0] = sPixels[xSmallIndices[ySmallLine0]] * xWeights[ySmallLine0] + sPixels[xSmallIndices[ySmallLine0] + 1] * (1.0F - xWeights[ySmallLine0]);
      }

      ySmallLine0 = -1;

      for(int y = 0; y < height; ++y) {
         int x;
         int p;
         if (ySmallLine0 < ySmallIndices[y]) {
            float[] swap = line0;
            line0 = line1;
            line1 = swap;
            ++ySmallLine0;
            x = (ySmallIndices[y] + 1) * smallWidth;

            for(p = 0; p < width; ++p) {
               line1[p] = sPixels[x + xSmallIndices[p]] * xWeights[p] + sPixels[x + xSmallIndices[p] + 1] * (1.0F - xWeights[p]);
            }
         }

         float weight = yWeights[y];
         x = 0;

         for(p = y * width; x < width; ++p) {
            pixels[p] = line0[x] * weight + line1[x] * (1.0F - weight);
            ++x;
         }
      }

   }

   void makeInterpolationArrays(int[] smallIndices, float[] weights, int length, int smallLength, int shrinkFactor) {
      for(int i = 0; i < length; ++i) {
         int smallIndex = (i - shrinkFactor / 2) / shrinkFactor;
         if (smallIndex >= smallLength - 1) {
            smallIndex = smallLength - 2;
         }

         smallIndices[i] = smallIndex;
         float distance = ((float)i + 0.5F) / (float)shrinkFactor - ((float)smallIndex + 0.5F);
         weights[i] = 1.0F - distance;
      }

   }

   double filter3x3(FloatProcessor fp, int type) {
      int width = fp.getWidth();
      int height = fp.getHeight();
      double shiftBy = 0.0D;
      float[] pixels = (float[])fp.getPixels();

      int x;
      for(x = 0; x < height; ++x) {
         shiftBy += this.filter3(pixels, width, x * width, 1, type);
      }

      for(x = 0; x < width; ++x) {
         shiftBy += this.filter3(pixels, height, x, width, type);
      }

      return shiftBy / (double)width / (double)height;
   }

   double filter3(float[] pixels, int length, int pixel0, int inc, int type) {
      double shiftBy = 0.0D;
      float v3 = pixels[pixel0];
      float v2 = v3;
      int i = 0;

      for(int p = pixel0; i < length; p += inc) {
         float v1 = v2;
         v2 = v3;
         if (i < length - 1) {
            v3 = pixels[p + inc];
         }

         if (type == 0) {
            float max = v1 > v3 ? v1 : v3;
            if (v2 > max) {
               max = v2;
            }

            shiftBy += (double)(max - v2);
            pixels[p] = max;
         } else {
            pixels[p] = (v1 + v3 + v3) * 0.33333334F;
         }

         ++i;
      }

      return shiftBy;
   }

   public void setNPasses(int nPasses) {
      if (this.isRGB && this.separateColors) {
         nPasses *= 3;
      }

      if (this.useParaboloid) {
         nPasses *= this.doPresmooth ? 11 : 9;
      }

      this.nPasses = nPasses;
      this.pass = 0;
   }

   private void showProgress(double percent) {
      if (this.nPasses > 0) {
         percent = (double)this.pass / (double)this.nPasses + percent / (double)this.nPasses;
         IJ.showProgress(percent);
      }
   }
}
