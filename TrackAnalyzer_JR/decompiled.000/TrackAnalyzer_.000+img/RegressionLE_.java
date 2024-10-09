import org.jfree.data.function.Function2D;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.function.PowerFunction2D;
import org.jfree.data.xy.XYDataset;

public class RegressionLE_ {
   private static double[][] getXYData(XYDataset data, int series) {
      int n = data.getItemCount(series);
      if (n < 1) {
         throw new IllegalArgumentException("Not enough data.");
      } else {
         double[][] result = new double[n][2];

         for(int i = 0; i < n; ++i) {
            result[i][0] = data.getXValue(series, i);
            result[i][1] = data.getYValue(series, i);
         }

         return result;
      }
   }

   public static double[] getOLSRegression(double[][] data) {
      int n = data.length;
      if (n < 2) {
         throw new IllegalArgumentException("LinearRegression: Not enough data.");
      } else {
         double sumX = 0.0D;
         double sumY = 0.0D;
         double sumXX = 0.0D;
         double sumYY = 0.0D;
         double sumXY = 0.0D;

         for(int i = 0; i < n; ++i) {
            double x = data[i][0];
            double y = data[i][1];
            sumX += x;
            sumY += y;
            double xx = x * x;
            sumXX += xx;
            double yy = y * y;
            sumYY += yy;
            double xy = x * y;
            sumXY += xy;
         }

         double sxx = sumXX - sumX * sumX / (double)n;
         double sxy = sumXY - sumX * sumY / (double)n;
         double xbar = sumX / (double)n;
         double ybar = sumY / (double)n;
         double tmp1 = (double)n * sumXX - sumX * sumX;
         double tmp2 = (double)n * sumYY - sumY * sumY;
         if (!(tmp1 < 0.0D) && !(tmp2 < 0.0D)) {
            double numerator = (double)n * sumXY - sumX * sumY;
            double denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
            if (denominator == 0.0D) {
               throw new IllegalArgumentException("LinearRegression: Data would cause divide by zero error.");
            } else {
               double r = numerator / denominator;
               double[] result = new double[]{0.0D, 0.0D, r * r};
               if (sxx < 0.0D) {
                  throw new IllegalArgumentException("LinearRegression: Data would cause divide by zero error.");
               } else {
                  result[1] = sxy / sxx;
                  result[0] = ybar - result[1] * xbar;
                  return result;
               }
            }
         } else {
            throw new IllegalArgumentException("LinearRegression: Data would cause sqrt of a negative number.");
         }
      }
   }

   public static double[] getOLSRegression(XYDataset data, int series) {
      return getOLSRegression(getXYData(data, series));
   }

   public static double[] getPowerRegression(double[][] data) {
      int n = data.length;
      if (n < 2) {
         throw new IllegalArgumentException("Not enough data.");
      } else {
         double sumX = 0.0D;
         double sumY = 0.0D;
         double sumXX = 0.0D;
         double sumYY = 0.0D;
         double sumXY = 0.0D;

         for(int i = 0; i < n; ++i) {
            if (!(data[i][0] > 0.0D) || !(data[i][1] > 0.0D)) {
               throw new IllegalArgumentException("PowerRegression: X & Y Data must be greater than zero.");
            }

            double x = Math.log(data[i][0]);
            double y = Math.log(data[i][1]);
            sumX += x;
            sumY += y;
            double xx = x * x;
            sumXX += xx;
            double yy = y * y;
            sumYY += yy;
            double xy = x * y;
            sumXY += xy;
         }

         double sxx = sumXX - sumX * sumX / (double)n;
         double sxy = sumXY - sumX * sumY / (double)n;
         double xbar = sumX / (double)n;
         double ybar = sumY / (double)n;
         double tmp1 = (double)n * sumXX - sumX * sumX;
         double tmp2 = (double)n * sumYY - sumY * sumY;
         if (!(tmp1 < 0.0D) && !(tmp2 < 0.0D)) {
            double numerator = (double)n * sumXY - sumX * sumY;
            double denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
            if (denominator == 0.0D) {
               throw new IllegalArgumentException("PowerRegression: Data would cause divide by zero error.");
            } else {
               double r = numerator / denominator;
               double[] result = new double[]{0.0D, 0.0D, r * r};
               if (sxx < 0.0D) {
                  throw new IllegalArgumentException("PowerRegression: Data would cause divide by zero error.");
               } else {
                  result[1] = sxy / sxx;
                  result[0] = Math.pow(Math.exp(1.0D), ybar - result[1] * xbar);
                  return result;
               }
            }
         } else {
            throw new IllegalArgumentException("PowerRegression: Data would cause sqrt of a negative number.");
         }
      }
   }

   public static double[] getPowerRegression(XYDataset data, int series) {
      return getPowerRegression(getXYData(data, series));
   }

   public static double[] getLogarithmicRegression(double[][] data) {
      int n = data.length;
      if (n < 2) {
         throw new IllegalArgumentException("LogarithmicRegression: Not enough data.");
      } else {
         double sumX = 0.0D;
         double sumY = 0.0D;
         double sumXX = 0.0D;
         double sumYY = 0.0D;
         double sumXY = 0.0D;

         for(int i = 0; i < n; ++i) {
            if (!(data[i][0] > 0.0D)) {
               throw new IllegalArgumentException("LogarithmicRegression: X Data must be greater than zero.");
            }

            double x = Math.log(data[i][0]);
            double y = data[i][1];
            sumX += x;
            sumY += y;
            double xx = x * x;
            sumXX += xx;
            double yy = y * y;
            sumYY += yy;
            double xy = x * y;
            sumXY += xy;
         }

         double sxx = sumXX - sumX * sumX / (double)n;
         double sxy = sumXY - sumX * sumY / (double)n;
         double xbar = sumX / (double)n;
         double ybar = sumY / (double)n;
         double tmp1 = (double)n * sumXX - sumX * sumX;
         double tmp2 = (double)n * sumYY - sumY * sumY;
         if (!(tmp1 < 0.0D) && !(tmp2 < 0.0D)) {
            double numerator = (double)n * sumXY - sumX * sumY;
            double denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
            if (denominator == 0.0D) {
               throw new IllegalArgumentException("LogarithmicRegression: Data would cause divide by zero error.");
            } else {
               double r = numerator / denominator;
               double[] result = new double[]{0.0D, 0.0D, r * r};
               if (sxx < 0.0D) {
                  throw new IllegalArgumentException("LogarithmicRegression: Data would cause divide by zero error.");
               } else {
                  result[1] = sxy / sxx;
                  result[0] = ybar - result[1] * xbar;
                  return result;
               }
            }
         } else {
            throw new IllegalArgumentException("LogarithmicRegression: Data would cause sqrt of a negative number.");
         }
      }
   }

   public static double[] getLogarithmicRegression(XYDataset data, int series) {
      return getLogarithmicRegression(getXYData(data, series));
   }

   public static double[] getExponentialRegression(double[][] data) {
      int n = data.length;
      if (n < 2) {
         throw new IllegalArgumentException("ExponentialRegression: Not enough data.");
      } else {
         double sumX = 0.0D;
         double sumY = 0.0D;
         double sumXX = 0.0D;
         double sumYY = 0.0D;
         double sumXY = 0.0D;

         for(int i = 0; i < n; ++i) {
            double x = data[i][0];
            if (!(data[i][1] > 0.0D)) {
               throw new IllegalArgumentException("ExponentialRegression: Y Data must be greater than zero.");
            }

            double y = Math.log(data[i][1]);
            sumX += x;
            sumY += y;
            double xx = x * x;
            sumXX += xx;
            double yy = y * y;
            sumYY += yy;
            double xy = x * y;
            sumXY += xy;
         }

         double sxx = sumXX - sumX * sumX / (double)n;
         double sxy = sumXY - sumX * sumY / (double)n;
         double xbar = sumX / (double)n;
         double ybar = sumY / (double)n;
         double tmp1 = (double)n * sumXX - sumX * sumX;
         double tmp2 = (double)n * sumYY - sumY * sumY;
         if (!(tmp1 < 0.0D) && !(tmp2 < 0.0D)) {
            double numerator = (double)n * sumXY - sumX * sumY;
            double denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
            if (denominator == 0.0D) {
               throw new IllegalArgumentException("ExponentialRegression: Data would cause divide by zero error.");
            } else {
               double r = numerator / denominator;
               double[] result = new double[]{0.0D, 0.0D, r * r};
               if (sxx < 0.0D) {
                  throw new IllegalArgumentException("ExponentialRegression: Data would cause divide by zero error.");
               } else {
                  result[1] = sxy / sxx;
                  result[0] = Math.exp(ybar - result[1] * xbar);
                  return result;
               }
            }
         } else {
            throw new IllegalArgumentException("ExponentialRegression: Data would cause sqrt of a negative number.");
         }
      }
   }

   public static double[] getExponentialRegression(XYDataset data, int series) {
      return getExponentialRegression(getXYData(data, series));
   }

   public static Function2D getBestRegressionFunction(double[][] data) {
      Function2D retVal = null;
      double r2 = 0.0D;
      Object var4 = null;

      double[] coefficients;
      try {
         coefficients = getOLSRegression(data);
         retVal = new LineFunction2D(coefficients[0], coefficients[1]);
         r2 = coefficients[2];
      } catch (Exception var9) {
         System.err.println(var9.getMessage());
      }

      try {
         coefficients = getPowerRegression(data);
         if (coefficients[2] > r2) {
            retVal = new PowerFunction2D(coefficients[0], coefficients[1]);
            r2 = coefficients[2];
         }
      } catch (Exception var8) {
         System.err.println(var8.getMessage());
      }

      try {
         coefficients = getLogarithmicRegression(data);
         if (coefficients[2] > r2) {
            retVal = new LogarithmicFunction2D(coefficients[0], coefficients[1]);
            r2 = coefficients[2];
         }
      } catch (Exception var7) {
         System.err.println(var7.getMessage());
      }

      try {
         coefficients = getExponentialRegression(data);
         if (coefficients[2] > r2) {
            retVal = new ExponentialFunction2D(coefficients[0], coefficients[1]);
            r2 = coefficients[2];
         }
      } catch (Exception var6) {
         System.err.println(var6.getMessage());
      }

      if (retVal == null) {
         throw new IllegalArgumentException("No regression functions were found with current dataset.");
      } else {
         return (Function2D)retVal;
      }
   }

   public static Function2D getBestRegressionFunction(XYDataset data, int series) {
      return getBestRegressionFunction(getXYData(data, series));
   }
}
