/*     */ import ij.IJ;
/*     */ import ij.process.ByteProcessor;
/*     */ import ij.process.ColorProcessor;
/*     */ import ij.process.FloatProcessor;
/*     */ import ij.process.ImageProcessor;
/*     */ import ij.util.Tools;
/*     */ 
/*     */ 
/*     */ public class BackgroundSubtracter
/*     */ {
/*  11 */   private static double staticRadius = 50.0D;
/*     */   
/*     */   private static boolean staticLightBackground = false;
/*     */   private static boolean staticSeparateColors;
/*     */   private static boolean staticCreateBackground;
/*     */   private static boolean staticUseParaboloid;
/*     */   private static boolean staticDoPresmooth = false;
/*  18 */   private double radius = staticRadius;
/*  19 */   private boolean lightBackground = staticLightBackground;
/*  20 */   private boolean separateColors = staticSeparateColors;
/*  21 */   private boolean createBackground = staticCreateBackground;
/*  22 */   private boolean useParaboloid = staticUseParaboloid;
/*  23 */   private boolean doPresmooth = staticDoPresmooth;
/*     */   
/*     */   private boolean isRGB;
/*     */   
/*     */   private boolean previewing;
/*     */   private static final int MAXIMUM = 0;
/*     */   private static final int MEAN = 1;
/*     */   private static final int X_DIRECTION = 0;
/*  31 */   private int nPasses = 9; private static final int Y_DIRECTION = 1; private static final int DIAGONAL_1A = 2; private static final int DIAGONAL_1B = 3; private static final int DIAGONAL_2A = 4; private static final int DIAGONAL_2B = 5;
/*     */   private static final int DIRECTION_PASSES = 9;
/*     */   private int pass;
/*     */   private boolean calledAsPlugin;
/*     */   double meanValue;
/*     */   
/*     */   public void rollingBallBrightnessBackground(ColorProcessor ip, double radius, boolean createBackground, boolean lightBackground, boolean useParaboloid, boolean doPresmooth, boolean correctCorners) {
/*  38 */     int width = ip.getWidth();
/*  39 */     int height = ip.getHeight();
/*  40 */     byte[] H = new byte[width * height];
/*  41 */     byte[] S = new byte[width * height];
/*  42 */     byte[] B = new byte[width * height];
/*  43 */     ip.getHSB(H, S, B);
/*  44 */     ByteProcessor bp = new ByteProcessor(width, height, B, null);
/*  45 */     rollingBallBackground((ImageProcessor)bp, radius, createBackground, lightBackground, useParaboloid, doPresmooth, 
/*  46 */         correctCorners);
/*  47 */     ip.setHSB(H, S, (byte[])bp.getPixels());
/*     */   }
/*     */ 
/*     */   
/*     */   public double rollingBallBackground(ImageProcessor ip, double radius, boolean createBackground, boolean lightBackground, boolean useParaboloid, boolean doPresmooth, boolean correctCorners) {
/*  52 */     boolean invertedLut = ip.isInvertedLut();
/*  53 */     boolean invert = !((!invertedLut || lightBackground) && (invertedLut || !lightBackground));
/*  54 */     RollingBall ball = null;
/*  55 */     if (!useParaboloid)
/*  56 */       ball = new RollingBall(radius); 
/*  57 */     FloatProcessor fp = null;
/*  58 */     for (int channelNumber = 0; channelNumber < ip.getNChannels(); channelNumber++) {
/*  59 */       fp = ip.toFloat(channelNumber, fp);
/*  60 */       if (ip instanceof FloatProcessor && !this.calledAsPlugin && !createBackground) {
/*  61 */         fp.snapshot();
/*     */       }
/*  63 */       rollingBallFloatBackground(fp, (float)radius, invert, doPresmooth, ball);
/*     */       
/*  65 */       if (createBackground) {
/*  66 */         ip.setPixels(channelNumber, fp);
/*     */       } else {
/*  68 */         float[] bgPixels = (float[])fp.getPixels();
/*  69 */         if (ip instanceof FloatProcessor) {
/*  70 */           float[] snapshotPixels = (float[])fp.getSnapshotPixels();
/*  71 */           for (int p = 0; p < bgPixels.length; p++) {
/*  72 */             bgPixels[p] = snapshotPixels[p] - bgPixels[p];
/*     */           }
/*     */         }
/*  75 */         else if (ip instanceof ij.process.ShortProcessor) {
/*  76 */           float offset = invert ? 65535.5F : 0.5F;
/*  77 */           short[] pixels = (short[])ip.getPixels();
/*  78 */           short[] pixelsToSubtract = (short[])ip.getPixels();
/*  79 */           for (int p = 0; p < bgPixels.length; p++) {
/*  80 */             float value = (pixels[p] & 0xFFFF) - bgPixels[p] + offset;
/*  81 */             float valueToSubtract = bgPixels[p] + offset;
/*  82 */             if (value < 0.0F)
/*  83 */               value = 0.0F; 
/*  84 */             if (valueToSubtract < 0.0F)
/*  85 */               valueToSubtract = 0.0F; 
/*  86 */             if (value > 65535.0F)
/*  87 */               value = 65535.0F; 
/*  88 */             if (valueToSubtract > 65535.0F) {
/*  89 */               valueToSubtract = 65535.0F;
/*     */             }
/*  91 */             pixels[p] = (short)(int)value;
/*  92 */             pixelsToSubtract[p] = (short)(int)valueToSubtract;
/*     */           } 
/*     */           
/*  95 */           double total = 0.0D;
/*  96 */           for (int i = 0; i < pixelsToSubtract.length; i++) {
/*  97 */             total += pixelsToSubtract[i];
/*     */           }
/*  99 */           this.meanValue = total / pixelsToSubtract.length;
/*     */         }
/* 101 */         else if (ip instanceof ByteProcessor) {
/* 102 */           float offset = invert ? 255.5F : 0.5F;
/* 103 */           byte[] pixels = (byte[])ip.getPixels();
/* 104 */           byte[] pixelsToSubtract = (byte[])ip.getPixels();
/* 105 */           for (int p = 0; p < bgPixels.length; p++) {
/* 106 */             float value = (pixels[p] & 0xFF) - bgPixels[p] + offset;
/* 107 */             float valueToSubtract = bgPixels[p] + offset;
/* 108 */             if (value < 0.0F)
/* 109 */               value = 0.0F; 
/* 110 */             if (valueToSubtract < 0.0F) {
/* 111 */               valueToSubtract = 0.0F;
/*     */             }
/* 113 */             if (value > 255.0F)
/* 114 */               value = 255.0F; 
/* 115 */             if (valueToSubtract > 255.0F)
/* 116 */               valueToSubtract = 255.0F; 
/* 117 */             pixels[p] = (byte)(int)value;
/* 118 */             pixelsToSubtract[p] = (byte)(int)valueToSubtract;
/*     */           } 
/*     */           
/* 121 */           double total = 0.0D;
/* 122 */           for (int i = 0; i < pixelsToSubtract.length; i++) {
/* 123 */             total += pixelsToSubtract[i];
/*     */           }
/* 125 */           this.meanValue = total / pixelsToSubtract.length;
/* 126 */         } else if (ip instanceof ColorProcessor) {
/* 127 */           float offset = invert ? 255.5F : 0.5F;
/* 128 */           int[] pixels = (int[])ip.getPixels();
/* 129 */           int[] pixelsToSubtract = (int[])ip.getPixels();
/* 130 */           int shift = 16 - 8 * channelNumber;
/*     */           
/* 132 */           int byteMask = 255 << shift;
/* 133 */           int resetMask = 0xFFFFFFFF ^ 255 << shift;
/*     */           
/* 135 */           for (int p = 0; p < bgPixels.length; p++) {
/* 136 */             int pxl = pixels[p];
/* 137 */             float value = ((pxl & byteMask) >> shift) - bgPixels[p] + offset;
/* 138 */             float valueToSubtract = bgPixels[p] + offset;
/* 139 */             if (value < 0.0F)
/* 140 */               value = 0.0F; 
/* 141 */             if (valueToSubtract < 0.0F) {
/* 142 */               valueToSubtract = 0.0F;
/*     */             }
/* 144 */             if (value > 255.0F)
/* 145 */               value = 255.0F; 
/* 146 */             if (valueToSubtract > 255.0F)
/* 147 */               valueToSubtract = 255.0F; 
/* 148 */             pixels[p] = pxl & resetMask | (int)value << shift;
/* 149 */             pixelsToSubtract[p] = pxl & resetMask | (int)valueToSubtract << shift;
/*     */           } 
/*     */           
/* 152 */           double total = 0.0D;
/* 153 */           for (int i = 0; i < pixelsToSubtract.length; i++) {
/* 154 */             total += pixelsToSubtract[i];
/*     */           }
/* 156 */           this.meanValue = total / pixelsToSubtract.length;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 162 */     return this.meanValue;
/*     */   }
/*     */ 
/*     */   
/*     */   static float[] lineSlideParabola(float[] pixels, int start, int inc, int length, float coeff2, float[] cache, int[] nextPoint, float[] correctedEdges) {
/* 167 */     float minValue = Float.MAX_VALUE;
/* 168 */     int lastpoint = 0;
/* 169 */     int firstCorner = length - 1;
/* 170 */     int lastCorner = 0;
/* 171 */     float vPrevious1 = 0.0F;
/* 172 */     float vPrevious2 = 0.0F;
/* 173 */     float curvatureTest = 1.999F * coeff2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     for (int i = 0, p = start; i < length; i++, p += inc) {
/* 180 */       float v = pixels[p];
/* 181 */       cache[i] = v;
/* 182 */       if (v < minValue)
/* 183 */         minValue = v; 
/* 184 */       if (i >= 2 && vPrevious1 + vPrevious1 - vPrevious2 - v < curvatureTest) {
/* 185 */         nextPoint[lastpoint] = i - 1;
/* 186 */         lastpoint = i - 1;
/*     */       } 
/* 188 */       vPrevious2 = vPrevious1;
/* 189 */       vPrevious1 = v;
/*     */     } 
/* 191 */     nextPoint[lastpoint] = length - 1;
/* 192 */     nextPoint[length - 1] = Integer.MAX_VALUE;
/*     */     
/* 194 */     int i1 = 0;
/* 195 */     while (i1 < length - 1) {
/* 196 */       float v1 = cache[i1];
/* 197 */       float minSlope = Float.MAX_VALUE;
/* 198 */       int i2 = 0;
/* 199 */       int searchTo = length;
/* 200 */       int recalculateLimitNow = 0;
/*     */       int j;
/* 202 */       for (j = nextPoint[i1]; j < searchTo; j = nextPoint[j], recalculateLimitNow++) {
/* 203 */         float v2 = cache[j];
/* 204 */         float slope = (v2 - v1) / (j - i1) + coeff2 * (j - i1);
/* 205 */         if (slope < minSlope) {
/* 206 */           minSlope = slope;
/* 207 */           i2 = j;
/* 208 */           recalculateLimitNow = -3;
/*     */         } 
/* 210 */         if (recalculateLimitNow == 0) {
/*     */           
/* 212 */           double b = (0.5F * minSlope / coeff2);
/* 213 */           int maxSearch = i1 + (int)(b + Math.sqrt(b * b + ((v1 - minValue) / coeff2)) + 1.0D);
/*     */ 
/*     */ 
/*     */           
/* 217 */           if (maxSearch < searchTo && maxSearch > 0)
/* 218 */             searchTo = maxSearch; 
/*     */         } 
/*     */       } 
/* 221 */       if (i1 == 0)
/* 222 */         firstCorner = i2; 
/* 223 */       if (i2 == length - 1)
/* 224 */         lastCorner = i1; 
/*     */       int k;
/* 226 */       for (j = i1 + 1, k = start + j * inc; j < i2; j++, k += inc)
/* 227 */         pixels[k] = v1 + (j - i1) * (minSlope - (j - i1) * coeff2); 
/* 228 */       i1 = i2;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     if (correctedEdges != null) {
/* 235 */       if (4 * firstCorner >= length)
/* 236 */         firstCorner = 0; 
/* 237 */       if (4 * (length - 1 - lastCorner) >= length)
/* 238 */         lastCorner = length - 1; 
/* 239 */       float v1 = cache[firstCorner];
/* 240 */       float v2 = cache[lastCorner];
/* 241 */       float slope = (v2 - v1) / (lastCorner - firstCorner);
/*     */       
/* 243 */       float value0 = v1 - slope * firstCorner;
/* 244 */       float coeff6 = 0.0F;
/* 245 */       float mid = 0.5F * (lastCorner + firstCorner);
/* 246 */       for (int j = (length + 2) / 3; j <= 2 * length / 3; j++) {
/*     */         
/* 248 */         float f1 = (j - mid) * 2.0F / (lastCorner - firstCorner);
/* 249 */         float poly6 = f1 * f1 * f1 * f1 * f1 * f1 - 1.0F;
/*     */         
/* 251 */         if (cache[j] < value0 + slope * j + coeff6 * poly6) {
/* 252 */           coeff6 = -(value0 + slope * j - cache[j]) / poly6;
/*     */         }
/*     */       } 
/* 255 */       float dx = (firstCorner - mid) * 2.0F / (lastCorner - firstCorner);
/* 256 */       correctedEdges[0] = value0 + coeff6 * (dx * dx * dx * dx * dx * dx - 1.0F) + 
/* 257 */         coeff2 * firstCorner * firstCorner;
/* 258 */       dx = (lastCorner - mid) * 2.0F / (lastCorner - firstCorner);
/* 259 */       correctedEdges[1] = value0 + (length - 1) * slope + coeff6 * (dx * dx * dx * dx * dx * dx - 1.0F) + 
/* 260 */         coeff2 * (length - 1 - lastCorner) * (length - 1 - lastCorner);
/*     */     } 
/* 262 */     return correctedEdges;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void correctCorners(FloatProcessor fp, float coeff2, float[] cache, int[] nextPoint) {
/* 271 */     int width = fp.getWidth();
/* 272 */     int height = fp.getHeight();
/* 273 */     float[] pixels = (float[])fp.getPixels();
/* 274 */     float[] corners = new float[4];
/* 275 */     float[] correctedEdges = new float[2];
/* 276 */     correctedEdges = lineSlideParabola(pixels, 0, 1, width, coeff2, cache, nextPoint, correctedEdges);
/* 277 */     corners[0] = correctedEdges[0];
/* 278 */     corners[1] = correctedEdges[1];
/* 279 */     correctedEdges = lineSlideParabola(pixels, (height - 1) * width, 1, width, coeff2, cache, nextPoint, 
/* 280 */         correctedEdges);
/* 281 */     corners[2] = correctedEdges[0];
/* 282 */     corners[3] = correctedEdges[1];
/* 283 */     correctedEdges = lineSlideParabola(pixels, 0, width, height, coeff2, cache, nextPoint, correctedEdges);
/* 284 */     corners[0] = corners[0] + correctedEdges[0];
/* 285 */     corners[2] = corners[2] + correctedEdges[1];
/* 286 */     correctedEdges = lineSlideParabola(pixels, width - 1, width, height, coeff2, cache, nextPoint, correctedEdges);
/* 287 */     corners[1] = corners[1] + correctedEdges[0];
/* 288 */     corners[3] = corners[3] + correctedEdges[1];
/* 289 */     int diagLength = Math.min(width, height);
/* 290 */     float coeff2diag = 2.0F * coeff2;
/* 291 */     correctedEdges = lineSlideParabola(pixels, 0, 1 + width, diagLength, coeff2diag, cache, nextPoint, 
/* 292 */         correctedEdges);
/* 293 */     corners[0] = corners[0] + correctedEdges[0];
/* 294 */     correctedEdges = lineSlideParabola(pixels, width - 1, -1 + width, diagLength, coeff2diag, cache, nextPoint, 
/* 295 */         correctedEdges);
/* 296 */     corners[1] = corners[1] + correctedEdges[0];
/* 297 */     correctedEdges = lineSlideParabola(pixels, (height - 1) * width, 1 - width, diagLength, coeff2diag, cache, 
/* 298 */         nextPoint, correctedEdges);
/* 299 */     corners[2] = corners[2] + correctedEdges[0];
/* 300 */     correctedEdges = lineSlideParabola(pixels, width * height - 1, -1 - width, diagLength, coeff2diag, cache, 
/* 301 */         nextPoint, correctedEdges);
/* 302 */     corners[3] = corners[3] + correctedEdges[0];
/* 303 */     if (pixels[0] > corners[0] / 3.0F)
/* 304 */       pixels[0] = corners[0] / 3.0F; 
/* 305 */     if (pixels[width - 1] > corners[1] / 3.0F)
/* 306 */       pixels[width - 1] = corners[1] / 3.0F; 
/* 307 */     if (pixels[(height - 1) * width] > corners[2] / 3.0F)
/* 308 */       pixels[(height - 1) * width] = corners[2] / 3.0F; 
/* 309 */     if (pixels[width * height - 1] > corners[3] / 3.0F) {
/* 310 */       pixels[width * height - 1] = corners[3] / 3.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void rollingBallFloatBackground(FloatProcessor fp, float radius, boolean invert, boolean doPresmooth, RollingBall ball) {
/* 321 */     float[] pixels = (float[])fp.getPixels();
/* 322 */     boolean shrink = (ball.shrinkFactor > 1);
/*     */     
/* 324 */     showProgress(0.0D);
/* 325 */     if (invert)
/* 326 */       for (int i = 0; i < pixels.length; i++)
/* 327 */         pixels[i] = -pixels[i];  
/* 328 */     if (doPresmooth)
/* 329 */       filter3x3(fp, 1); 
/* 330 */     double[] minmax = Tools.getMinMax(pixels);
/* 331 */     if (Thread.currentThread().isInterrupted())
/*     */       return; 
/* 333 */     FloatProcessor smallImage = shrink ? shrinkImage(fp, ball.shrinkFactor) : fp;
/* 334 */     if (Thread.currentThread().isInterrupted())
/*     */       return; 
/* 336 */     rollBall(ball, smallImage);
/* 337 */     if (Thread.currentThread().isInterrupted())
/*     */       return; 
/* 339 */     showProgress(0.9D);
/* 340 */     if (shrink)
/* 341 */       enlargeImage(smallImage, fp, ball.shrinkFactor); 
/* 342 */     if (Thread.currentThread().isInterrupted()) {
/*     */       return;
/*     */     }
/* 345 */     if (invert)
/* 346 */       for (int i = 0; i < pixels.length; i++)
/* 347 */         pixels[i] = -pixels[i];  
/* 348 */     this.pass++;
/*     */   }
/*     */ 
/*     */   
/*     */   FloatProcessor shrinkImage(FloatProcessor ip, int shrinkFactor) {
/* 353 */     int width = ip.getWidth();
/* 354 */     int height = ip.getHeight();
/* 355 */     float[] pixels = (float[])ip.getPixels();
/* 356 */     int sWidth = (width + shrinkFactor - 1) / shrinkFactor;
/* 357 */     int sHeight = (height + shrinkFactor - 1) / shrinkFactor;
/* 358 */     showProgress(0.1D);
/* 359 */     FloatProcessor smallImage = new FloatProcessor(sWidth, sHeight);
/* 360 */     float[] sPixels = (float[])smallImage.getPixels();
/*     */     
/* 362 */     for (int ySmall = 0; ySmall < sHeight; ySmall++) {
/* 363 */       for (int xSmall = 0; xSmall < sWidth; xSmall++) {
/* 364 */         float min = Float.MAX_VALUE;
/* 365 */         for (int j = 0, y = shrinkFactor * ySmall; j < shrinkFactor && y < height; j++, y++) {
/* 366 */           for (int k = 0, x = shrinkFactor * xSmall; k < shrinkFactor && x < width; k++, x++) {
/* 367 */             float thispixel = pixels[x + y * width];
/* 368 */             if (thispixel < min)
/* 369 */               min = thispixel; 
/*     */           } 
/*     */         } 
/* 372 */         sPixels[xSmall + ySmall * sWidth] = min;
/*     */       } 
/*     */     } 
/*     */     
/* 376 */     return smallImage;
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
/*     */   void rollBall(RollingBall ball, FloatProcessor fp) {
/* 393 */     float[] pixels = (float[])fp.getPixels();
/* 394 */     int width = fp.getWidth();
/* 395 */     int height = fp.getHeight();
/* 396 */     float[] zBall = ball.data;
/* 397 */     int ballWidth = ball.width;
/* 398 */     int radius = ballWidth / 2;
/* 399 */     float[] cache = new float[width * ballWidth];
/*     */     
/* 401 */     Thread thread = Thread.currentThread();
/* 402 */     long lastTime = System.currentTimeMillis();
/* 403 */     for (int y = -radius; y < height + radius; y++) {
/* 404 */       long time = System.currentTimeMillis();
/* 405 */       if (time - lastTime > 100L) {
/* 406 */         lastTime = time;
/* 407 */         if (thread.isInterrupted())
/*     */           return; 
/* 409 */         showProgress(0.1D + 0.8D * y / (height + ballWidth));
/*     */       } 
/* 411 */       int nextLineToWriteInCache = (y + radius) % ballWidth;
/* 412 */       int nextLineToRead = y + radius;
/* 413 */       if (nextLineToRead < height) {
/* 414 */         System.arraycopy(pixels, nextLineToRead * width, cache, nextLineToWriteInCache * width, width);
/* 415 */         for (int i = 0, p = nextLineToRead * width; i < width; i++, p++)
/* 416 */           pixels[p] = -3.4028235E38F; 
/*     */       } 
/* 418 */       int y0 = y - radius;
/* 419 */       if (y0 < 0)
/* 420 */         y0 = 0; 
/* 421 */       int yBall0 = y0 - y + radius;
/* 422 */       int yend = y + radius;
/* 423 */       if (yend >= height)
/* 424 */         yend = height - 1; 
/* 425 */       for (int x = -radius; x < width + radius; x++) {
/* 426 */         float z = Float.MAX_VALUE;
/* 427 */         int x0 = x - radius;
/* 428 */         if (x0 < 0)
/* 429 */           x0 = 0; 
/* 430 */         int xBall0 = x0 - x + radius;
/* 431 */         int xend = x + radius;
/* 432 */         if (xend >= width)
/* 433 */           xend = width - 1;  int yp, yBall;
/* 434 */         for (yp = y0, yBall = yBall0; yp <= yend; yp++, yBall++) {
/* 435 */           int cachePointer = yp % ballWidth * width + x0;
/* 436 */           for (int xp = x0, bp = xBall0 + yBall * ballWidth; xp <= xend; xp++, cachePointer++, bp++) {
/* 437 */             float zReduced = cache[cachePointer] - zBall[bp];
/* 438 */             if (z > zReduced)
/* 439 */               z = zReduced; 
/*     */           } 
/*     */         } 
/* 442 */         for (yp = y0, yBall = yBall0; yp <= yend; yp++, yBall++) {
/* 443 */           int xp = x0, p = xp + yp * width;
/* 444 */           int bp = xBall0 + yBall * ballWidth; for (; xp <= xend; xp++, p++, bp++) {
/* 445 */             float zMin = z + zBall[bp];
/* 446 */             if (pixels[p] < zMin) {
/* 447 */               pixels[p] = zMin;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
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
/*     */   void enlargeImage(FloatProcessor smallImage, FloatProcessor fp, int shrinkFactor) {
/* 463 */     int width = fp.getWidth();
/* 464 */     int height = fp.getHeight();
/* 465 */     int smallWidth = smallImage.getWidth();
/* 466 */     int smallHeight = smallImage.getHeight();
/* 467 */     float[] pixels = (float[])fp.getPixels();
/* 468 */     float[] sPixels = (float[])smallImage.getPixels();
/* 469 */     int[] xSmallIndices = new int[width];
/* 470 */     float[] xWeights = new float[width];
/* 471 */     makeInterpolationArrays(xSmallIndices, xWeights, width, smallWidth, shrinkFactor);
/* 472 */     int[] ySmallIndices = new int[height];
/* 473 */     float[] yWeights = new float[height];
/* 474 */     makeInterpolationArrays(ySmallIndices, yWeights, height, smallHeight, shrinkFactor);
/* 475 */     float[] line0 = new float[width];
/* 476 */     float[] line1 = new float[width];
/* 477 */     for (int x = 0; x < width; x++)
/* 478 */       line1[x] = sPixels[xSmallIndices[x]] * xWeights[x] + sPixels[xSmallIndices[x] + 1] * (1.0F - xWeights[x]); 
/* 479 */     int ySmallLine0 = -1;
/* 480 */     for (int y = 0; y < height; y++) {
/* 481 */       if (ySmallLine0 < ySmallIndices[y]) {
/* 482 */         float[] swap = line0;
/* 483 */         line0 = line1;
/* 484 */         line1 = swap;
/* 485 */         ySmallLine0++;
/* 486 */         int sYPointer = (ySmallIndices[y] + 1) * smallWidth;
/* 487 */         for (int j = 0; j < width; j++)
/* 488 */           line1[j] = sPixels[sYPointer + xSmallIndices[j]] * xWeights[j] + 
/* 489 */             sPixels[sYPointer + xSmallIndices[j] + 1] * (1.0F - xWeights[j]); 
/*     */       } 
/* 491 */       float weight = yWeights[y];
/* 492 */       for (int i = 0, p = y * width; i < width; i++, p++) {
/* 493 */         pixels[p] = line0[i] * weight + line1[i] * (1.0F - weight);
/*     */       }
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
/*     */   void makeInterpolationArrays(int[] smallIndices, float[] weights, int length, int smallLength, int shrinkFactor) {
/* 510 */     for (int i = 0; i < length; i++) {
/* 511 */       int smallIndex = (i - shrinkFactor / 2) / shrinkFactor;
/* 512 */       if (smallIndex >= smallLength - 1)
/* 513 */         smallIndex = smallLength - 2; 
/* 514 */       smallIndices[i] = smallIndex;
/* 515 */       float distance = (i + 0.5F) / shrinkFactor - smallIndex + 0.5F;
/*     */       
/* 517 */       weights[i] = 1.0F - distance;
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
/*     */   double filter3x3(FloatProcessor fp, int type) {
/* 529 */     int width = fp.getWidth();
/* 530 */     int height = fp.getHeight();
/* 531 */     double shiftBy = 0.0D;
/* 532 */     float[] pixels = (float[])fp.getPixels();
/* 533 */     for (int y = 0; y < height; y++)
/* 534 */       shiftBy += filter3(pixels, width, y * width, 1, type); 
/* 535 */     for (int x = 0; x < width; x++)
/* 536 */       shiftBy += filter3(pixels, height, x, width, type); 
/* 537 */     return shiftBy / width / height;
/*     */   }
/*     */ 
/*     */   
/*     */   double filter3(float[] pixels, int length, int pixel0, int inc, int type) {
/* 542 */     double shiftBy = 0.0D;
/* 543 */     float v3 = pixels[pixel0];
/* 544 */     float v2 = v3;
/*     */     
/* 546 */     for (int i = 0, p = pixel0; i < length; i++, p += inc) {
/* 547 */       float v1 = v2;
/* 548 */       v2 = v3;
/* 549 */       if (i < length - 1)
/* 550 */         v3 = pixels[p + inc]; 
/* 551 */       if (type == 0) {
/* 552 */         float max = (v1 > v3) ? v1 : v3;
/* 553 */         if (v2 > max)
/* 554 */           max = v2; 
/* 555 */         shiftBy += (max - v2);
/* 556 */         pixels[p] = max;
/*     */       } else {
/* 558 */         pixels[p] = (v1 + v2 + v3) * 0.33333334F;
/*     */       } 
/* 560 */     }  return shiftBy;
/*     */   }
/*     */   
/*     */   public void setNPasses(int nPasses) {
/* 564 */     if (this.isRGB && this.separateColors)
/* 565 */       nPasses *= 3; 
/* 566 */     if (this.useParaboloid)
/* 567 */       nPasses *= this.doPresmooth ? 11 : 9; 
/* 568 */     this.nPasses = nPasses;
/* 569 */     this.pass = 0;
/*     */   }
/*     */   
/*     */   private void showProgress(double percent) {
/* 573 */     if (this.nPasses <= 0)
/*     */       return; 
/* 575 */     percent = this.pass / this.nPasses + percent / this.nPasses;
/* 576 */     IJ.showProgress(percent);
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/BackgroundSubtracter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */