/*     */ import org.jfree.data.function.Function2D;
/*     */ import org.jfree.data.function.LineFunction2D;
/*     */ import org.jfree.data.function.PowerFunction2D;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ 
/*     */ 
/*     */ public class RegressionLE_
/*     */ {
/*     */   private static double[][] getXYData(XYDataset data, int series) {
/*  10 */     int n = data.getItemCount(series);
/*  11 */     if (n < 1) {
/*  12 */       throw new IllegalArgumentException("Not enough data.");
/*     */     }
/*  14 */     double[][] result = new double[n][2];
/*  15 */     for (int i = 0; i < n; i++) {
/*  16 */       result[i][0] = data.getXValue(series, i);
/*  17 */       result[i][1] = data.getYValue(series, i);
/*     */     } 
/*  19 */     return result;
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
/*     */   public static double[] getOLSRegression(double[][] data) {
/*  33 */     int n = data.length;
/*  34 */     if (n < 2) {
/*  35 */       throw new IllegalArgumentException("LinearRegression: Not enough data.");
/*     */     }
/*     */     
/*  38 */     double sumX = 0.0D;
/*  39 */     double sumY = 0.0D;
/*  40 */     double sumXX = 0.0D;
/*  41 */     double sumYY = 0.0D;
/*  42 */     double sumXY = 0.0D;
/*  43 */     for (int i = 0; i < n; i++) {
/*  44 */       double x = data[i][0];
/*  45 */       double y = data[i][1];
/*  46 */       sumX += x;
/*  47 */       sumY += y;
/*  48 */       double xx = x * x;
/*  49 */       sumXX += xx;
/*  50 */       double yy = y * y;
/*  51 */       sumYY += yy;
/*  52 */       double xy = x * y;
/*  53 */       sumXY += xy;
/*     */     } 
/*  55 */     double sxx = sumXX - sumX * sumX / n;
/*  56 */     double sxy = sumXY - sumX * sumY / n;
/*  57 */     double xbar = sumX / n;
/*  58 */     double ybar = sumY / n;
/*     */ 
/*     */     
/*  61 */     double tmp1 = n * sumXX - sumX * sumX;
/*  62 */     double tmp2 = n * sumYY - sumY * sumY;
/*  63 */     if (tmp1 < 0.0D || tmp2 < 0.0D)
/*  64 */       throw new IllegalArgumentException("LinearRegression: Data would cause sqrt of a negative number."); 
/*  65 */     double numerator = n * sumXY - sumX * sumY;
/*  66 */     double denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
/*  67 */     if (denominator == 0.0D)
/*  68 */       throw new IllegalArgumentException("LinearRegression: Data would cause divide by zero error."); 
/*  69 */     double r = numerator / denominator;
/*     */     
/*  71 */     double[] result = new double[3];
/*  72 */     result[2] = r * r;
/*  73 */     if (sxx < 0.0D)
/*  74 */       throw new IllegalArgumentException("LinearRegression: Data would cause divide by zero error."); 
/*  75 */     result[1] = sxy / sxx;
/*  76 */     result[0] = ybar - result[1] * xbar;
/*     */     
/*  78 */     return result;
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
/*     */   public static double[] getOLSRegression(XYDataset data, int series) {
/*  95 */     return getOLSRegression(getXYData(data, series));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] getPowerRegression(double[][] data) {
/* 101 */     int n = data.length;
/* 102 */     if (n < 2) {
/* 103 */       throw new IllegalArgumentException("Not enough data.");
/*     */     }
/*     */     
/* 106 */     double sumX = 0.0D;
/* 107 */     double sumY = 0.0D;
/* 108 */     double sumXX = 0.0D;
/* 109 */     double sumYY = 0.0D;
/* 110 */     double sumXY = 0.0D;
/* 111 */     for (int i = 0; i < n; i++) {
/*     */       double x, y;
/* 113 */       if (data[i][0] > 0.0D && data[i][1] > 0.0D) {
/* 114 */         x = Math.log(data[i][0]);
/* 115 */         y = Math.log(data[i][1]);
/*     */       } else {
/* 117 */         throw new IllegalArgumentException("PowerRegression: X & Y Data must be greater than zero.");
/* 118 */       }  sumX += x;
/* 119 */       sumY += y;
/* 120 */       double xx = x * x;
/* 121 */       sumXX += xx;
/* 122 */       double yy = y * y;
/* 123 */       sumYY += yy;
/* 124 */       double xy = x * y;
/* 125 */       sumXY += xy;
/*     */     } 
/* 127 */     double sxx = sumXX - sumX * sumX / n;
/* 128 */     double sxy = sumXY - sumX * sumY / n;
/* 129 */     double xbar = sumX / n;
/* 130 */     double ybar = sumY / n;
/*     */ 
/*     */     
/* 133 */     double tmp1 = n * sumXX - sumX * sumX;
/* 134 */     double tmp2 = n * sumYY - sumY * sumY;
/* 135 */     if (tmp1 < 0.0D || tmp2 < 0.0D)
/* 136 */       throw new IllegalArgumentException("PowerRegression: Data would cause sqrt of a negative number."); 
/* 137 */     double numerator = n * sumXY - sumX * sumY;
/* 138 */     double denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
/* 139 */     if (denominator == 0.0D)
/* 140 */       throw new IllegalArgumentException("PowerRegression: Data would cause divide by zero error."); 
/* 141 */     double r = numerator / denominator;
/*     */     
/* 143 */     double[] result = new double[3];
/* 144 */     result[2] = r * r;
/* 145 */     if (sxx < 0.0D)
/* 146 */       throw new IllegalArgumentException("PowerRegression: Data would cause divide by zero error."); 
/* 147 */     result[1] = sxy / sxx;
/* 148 */     result[0] = Math.pow(Math.exp(1.0D), ybar - result[1] * xbar);
/* 149 */     return result;
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
/*     */   public static double[] getPowerRegression(XYDataset data, int series) {
/* 165 */     return getPowerRegression(getXYData(data, series));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] getLogarithmicRegression(double[][] data) {
/* 171 */     int n = data.length;
/* 172 */     if (n < 2) {
/* 173 */       throw new IllegalArgumentException("LogarithmicRegression: Not enough data.");
/*     */     }
/*     */     
/* 176 */     double sumX = 0.0D;
/* 177 */     double sumY = 0.0D;
/* 178 */     double sumXX = 0.0D;
/* 179 */     double sumYY = 0.0D;
/* 180 */     double sumXY = 0.0D;
/* 181 */     for (int i = 0; i < n; i++) {
/*     */       double x;
/* 183 */       if (data[i][0] > 0.0D) {
/* 184 */         x = Math.log(data[i][0]);
/*     */       } else {
/* 186 */         throw new IllegalArgumentException("LogarithmicRegression: X Data must be greater than zero.");
/* 187 */       }  double y = data[i][1];
/* 188 */       sumX += x;
/* 189 */       sumY += y;
/* 190 */       double xx = x * x;
/* 191 */       sumXX += xx;
/* 192 */       double yy = y * y;
/* 193 */       sumYY += yy;
/* 194 */       double xy = x * y;
/* 195 */       sumXY += xy;
/*     */     } 
/* 197 */     double sxx = sumXX - sumX * sumX / n;
/* 198 */     double sxy = sumXY - sumX * sumY / n;
/* 199 */     double xbar = sumX / n;
/* 200 */     double ybar = sumY / n;
/*     */ 
/*     */     
/* 203 */     double tmp1 = n * sumXX - sumX * sumX;
/* 204 */     double tmp2 = n * sumYY - sumY * sumY;
/* 205 */     if (tmp1 < 0.0D || tmp2 < 0.0D)
/* 206 */       throw new IllegalArgumentException("LogarithmicRegression: Data would cause sqrt of a negative number."); 
/* 207 */     double numerator = n * sumXY - sumX * sumY;
/* 208 */     double denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
/* 209 */     if (denominator == 0.0D)
/* 210 */       throw new IllegalArgumentException("LogarithmicRegression: Data would cause divide by zero error."); 
/* 211 */     double r = numerator / denominator;
/*     */     
/* 213 */     double[] result = new double[3];
/* 214 */     result[2] = r * r;
/* 215 */     if (sxx < 0.0D)
/* 216 */       throw new IllegalArgumentException("LogarithmicRegression: Data would cause divide by zero error."); 
/* 217 */     result[1] = sxy / sxx;
/* 218 */     result[0] = ybar - result[1] * xbar;
/* 219 */     return result;
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
/*     */   public static double[] getLogarithmicRegression(XYDataset data, int series) {
/* 235 */     return getLogarithmicRegression(getXYData(data, series));
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
/*     */   public static double[] getExponentialRegression(double[][] data) {
/* 250 */     int n = data.length;
/* 251 */     if (n < 2) {
/* 252 */       throw new IllegalArgumentException("ExponentialRegression: Not enough data.");
/*     */     }
/*     */     
/* 255 */     double sumX = 0.0D;
/* 256 */     double sumY = 0.0D;
/* 257 */     double sumXX = 0.0D;
/* 258 */     double sumYY = 0.0D;
/* 259 */     double sumXY = 0.0D;
/* 260 */     for (int i = 0; i < n; i++) {
/*     */       
/* 262 */       double y, x = data[i][0];
/* 263 */       if (data[i][1] > 0.0D) {
/* 264 */         y = Math.log(data[i][1]);
/*     */       } else {
/* 266 */         throw new IllegalArgumentException("ExponentialRegression: Y Data must be greater than zero.");
/* 267 */       }  sumX += x;
/* 268 */       sumY += y;
/* 269 */       double xx = x * x;
/* 270 */       sumXX += xx;
/* 271 */       double yy = y * y;
/* 272 */       sumYY += yy;
/* 273 */       double xy = x * y;
/* 274 */       sumXY += xy;
/*     */     } 
/* 276 */     double sxx = sumXX - sumX * sumX / n;
/* 277 */     double sxy = sumXY - sumX * sumY / n;
/* 278 */     double xbar = sumX / n;
/* 279 */     double ybar = sumY / n;
/*     */ 
/*     */     
/* 282 */     double tmp1 = n * sumXX - sumX * sumX;
/* 283 */     double tmp2 = n * sumYY - sumY * sumY;
/* 284 */     if (tmp1 < 0.0D || tmp2 < 0.0D)
/* 285 */       throw new IllegalArgumentException("ExponentialRegression: Data would cause sqrt of a negative number."); 
/* 286 */     double numerator = n * sumXY - sumX * sumY;
/* 287 */     double denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
/* 288 */     if (denominator == 0.0D)
/* 289 */       throw new IllegalArgumentException("ExponentialRegression: Data would cause divide by zero error."); 
/* 290 */     double r = numerator / denominator;
/*     */     
/* 292 */     double[] result = new double[3];
/* 293 */     result[2] = r * r;
/* 294 */     if (sxx < 0.0D)
/* 295 */       throw new IllegalArgumentException("ExponentialRegression: Data would cause divide by zero error."); 
/* 296 */     result[1] = sxy / sxx;
/* 297 */     result[0] = Math.exp(ybar - result[1] * xbar);
/* 298 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] getExponentialRegression(XYDataset data, int series) {
/* 304 */     return getExponentialRegression(getXYData(data, series));
/*     */   }
/*     */   
/*     */   public static Function2D getBestRegressionFunction(double[][] data) {
/*     */     ExponentialFunction2D exponentialFunction2D;
/* 309 */     Function2D retVal = null;
/* 310 */     double r2 = 0.0D;
/* 311 */     double[] coefficients = null;
/*     */ 
/*     */     
/*     */     try {
/* 315 */       coefficients = getOLSRegression(data);
/* 316 */       LineFunction2D lineFunction2D = new LineFunction2D(coefficients[0], coefficients[1]);
/* 317 */       r2 = coefficients[2];
/* 318 */     } catch (Exception e) {
/* 319 */       System.err.println(e.getMessage());
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 324 */       coefficients = getPowerRegression(data);
/* 325 */       if (coefficients[2] > r2) {
/* 326 */         PowerFunction2D powerFunction2D = new PowerFunction2D(coefficients[0], coefficients[1]);
/* 327 */         r2 = coefficients[2];
/*     */       } 
/* 329 */     } catch (Exception e) {
/* 330 */       System.err.println(e.getMessage());
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 335 */       coefficients = getLogarithmicRegression(data);
/* 336 */       if (coefficients[2] > r2) {
/* 337 */         LogarithmicFunction2D logarithmicFunction2D = new LogarithmicFunction2D(coefficients[0], coefficients[1]);
/* 338 */         r2 = coefficients[2];
/*     */       } 
/* 340 */     } catch (Exception e) {
/* 341 */       System.err.println(e.getMessage());
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 346 */       coefficients = getExponentialRegression(data);
/* 347 */       if (coefficients[2] > r2) {
/* 348 */         exponentialFunction2D = new ExponentialFunction2D(coefficients[0], coefficients[1]);
/* 349 */         r2 = coefficients[2];
/*     */       } 
/* 351 */     } catch (Exception e) {
/* 352 */       System.err.println(e.getMessage());
/*     */     } 
/*     */     
/* 355 */     if (exponentialFunction2D == null) {
/* 356 */       throw new IllegalArgumentException("No regression functions were found with current dataset.");
/*     */     }
/*     */     
/* 359 */     return (Function2D)exponentialFunction2D;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Function2D getBestRegressionFunction(XYDataset data, int series) {
/* 364 */     return getBestRegressionFunction(getXYData(data, series));
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/RegressionLE_.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */