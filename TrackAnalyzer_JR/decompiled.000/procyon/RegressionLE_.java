import org.jfree.data.function.PowerFunction2D;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.function.Function2D;
import org.jfree.data.xy.XYDataset;

// 
// Decompiled by Procyon v0.5.36
// 

public class RegressionLE_
{
    private static double[][] getXYData(final XYDataset data, final int series) {
        final int n = data.getItemCount(series);
        if (n < 1) {
            throw new IllegalArgumentException("Not enough data.");
        }
        final double[][] result = new double[n][2];
        for (int i = 0; i < n; ++i) {
            result[i][0] = data.getXValue(series, i);
            result[i][1] = data.getYValue(series, i);
        }
        return result;
    }
    
    public static double[] getOLSRegression(final double[][] data) {
        final int n = data.length;
        if (n < 2) {
            throw new IllegalArgumentException("LinearRegression: Not enough data.");
        }
        double sumX = 0.0;
        double sumY = 0.0;
        double sumXX = 0.0;
        double sumYY = 0.0;
        double sumXY = 0.0;
        for (int i = 0; i < n; ++i) {
            final double x = data[i][0];
            final double y = data[i][1];
            sumX += x;
            sumY += y;
            final double xx = x * x;
            sumXX += xx;
            final double yy = y * y;
            sumYY += yy;
            final double xy = x * y;
            sumXY += xy;
        }
        final double sxx = sumXX - sumX * sumX / n;
        final double sxy = sumXY - sumX * sumY / n;
        final double xbar = sumX / n;
        final double ybar = sumY / n;
        final double tmp1 = n * sumXX - sumX * sumX;
        final double tmp2 = n * sumYY - sumY * sumY;
        if (tmp1 < 0.0 || tmp2 < 0.0) {
            throw new IllegalArgumentException("LinearRegression: Data would cause sqrt of a negative number.");
        }
        final double numerator = n * sumXY - sumX * sumY;
        final double denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
        if (denominator == 0.0) {
            throw new IllegalArgumentException("LinearRegression: Data would cause divide by zero error.");
        }
        final double r = numerator / denominator;
        final double[] result = { 0.0, 0.0, r * r };
        if (sxx < 0.0) {
            throw new IllegalArgumentException("LinearRegression: Data would cause divide by zero error.");
        }
        result[1] = sxy / sxx;
        result[0] = ybar - result[1] * xbar;
        return result;
    }
    
    public static double[] getOLSRegression(final XYDataset data, final int series) {
        return getOLSRegression(getXYData(data, series));
    }
    
    public static double[] getPowerRegression(final double[][] data) {
        final int n = data.length;
        if (n < 2) {
            throw new IllegalArgumentException("Not enough data.");
        }
        double sumX = 0.0;
        double sumY = 0.0;
        double sumXX = 0.0;
        double sumYY = 0.0;
        double sumXY = 0.0;
        for (int i = 0; i < n; ++i) {
            if (data[i][0] <= 0.0 || data[i][1] <= 0.0) {
                throw new IllegalArgumentException("PowerRegression: X & Y Data must be greater than zero.");
            }
            final double x = Math.log(data[i][0]);
            final double y = Math.log(data[i][1]);
            sumX += x;
            sumY += y;
            final double xx = x * x;
            sumXX += xx;
            final double yy = y * y;
            sumYY += yy;
            final double xy = x * y;
            sumXY += xy;
        }
        final double sxx = sumXX - sumX * sumX / n;
        final double sxy = sumXY - sumX * sumY / n;
        final double xbar = sumX / n;
        final double ybar = sumY / n;
        final double tmp1 = n * sumXX - sumX * sumX;
        final double tmp2 = n * sumYY - sumY * sumY;
        if (tmp1 < 0.0 || tmp2 < 0.0) {
            throw new IllegalArgumentException("PowerRegression: Data would cause sqrt of a negative number.");
        }
        final double numerator = n * sumXY - sumX * sumY;
        final double denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
        if (denominator == 0.0) {
            throw new IllegalArgumentException("PowerRegression: Data would cause divide by zero error.");
        }
        final double r = numerator / denominator;
        final double[] result = { 0.0, 0.0, r * r };
        if (sxx < 0.0) {
            throw new IllegalArgumentException("PowerRegression: Data would cause divide by zero error.");
        }
        result[1] = sxy / sxx;
        result[0] = Math.pow(Math.exp(1.0), ybar - result[1] * xbar);
        return result;
    }
    
    public static double[] getPowerRegression(final XYDataset data, final int series) {
        return getPowerRegression(getXYData(data, series));
    }
    
    public static double[] getLogarithmicRegression(final double[][] data) {
        final int n = data.length;
        if (n < 2) {
            throw new IllegalArgumentException("LogarithmicRegression: Not enough data.");
        }
        double sumX = 0.0;
        double sumY = 0.0;
        double sumXX = 0.0;
        double sumYY = 0.0;
        double sumXY = 0.0;
        for (int i = 0; i < n; ++i) {
            if (data[i][0] <= 0.0) {
                throw new IllegalArgumentException("LogarithmicRegression: X Data must be greater than zero.");
            }
            final double x = Math.log(data[i][0]);
            final double y = data[i][1];
            sumX += x;
            sumY += y;
            final double xx = x * x;
            sumXX += xx;
            final double yy = y * y;
            sumYY += yy;
            final double xy = x * y;
            sumXY += xy;
        }
        final double sxx = sumXX - sumX * sumX / n;
        final double sxy = sumXY - sumX * sumY / n;
        final double xbar = sumX / n;
        final double ybar = sumY / n;
        final double tmp1 = n * sumXX - sumX * sumX;
        final double tmp2 = n * sumYY - sumY * sumY;
        if (tmp1 < 0.0 || tmp2 < 0.0) {
            throw new IllegalArgumentException("LogarithmicRegression: Data would cause sqrt of a negative number.");
        }
        final double numerator = n * sumXY - sumX * sumY;
        final double denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
        if (denominator == 0.0) {
            throw new IllegalArgumentException("LogarithmicRegression: Data would cause divide by zero error.");
        }
        final double r = numerator / denominator;
        final double[] result = { 0.0, 0.0, r * r };
        if (sxx < 0.0) {
            throw new IllegalArgumentException("LogarithmicRegression: Data would cause divide by zero error.");
        }
        result[1] = sxy / sxx;
        result[0] = ybar - result[1] * xbar;
        return result;
    }
    
    public static double[] getLogarithmicRegression(final XYDataset data, final int series) {
        return getLogarithmicRegression(getXYData(data, series));
    }
    
    public static double[] getExponentialRegression(final double[][] data) {
        final int n = data.length;
        if (n < 2) {
            throw new IllegalArgumentException("ExponentialRegression: Not enough data.");
        }
        double sumX = 0.0;
        double sumY = 0.0;
        double sumXX = 0.0;
        double sumYY = 0.0;
        double sumXY = 0.0;
        for (int i = 0; i < n; ++i) {
            final double x = data[i][0];
            if (data[i][1] <= 0.0) {
                throw new IllegalArgumentException("ExponentialRegression: Y Data must be greater than zero.");
            }
            final double y = Math.log(data[i][1]);
            sumX += x;
            sumY += y;
            final double xx = x * x;
            sumXX += xx;
            final double yy = y * y;
            sumYY += yy;
            final double xy = x * y;
            sumXY += xy;
        }
        final double sxx = sumXX - sumX * sumX / n;
        final double sxy = sumXY - sumX * sumY / n;
        final double xbar = sumX / n;
        final double ybar = sumY / n;
        final double tmp1 = n * sumXX - sumX * sumX;
        final double tmp2 = n * sumYY - sumY * sumY;
        if (tmp1 < 0.0 || tmp2 < 0.0) {
            throw new IllegalArgumentException("ExponentialRegression: Data would cause sqrt of a negative number.");
        }
        final double numerator = n * sumXY - sumX * sumY;
        final double denominator = Math.sqrt(tmp1) * Math.sqrt(tmp2);
        if (denominator == 0.0) {
            throw new IllegalArgumentException("ExponentialRegression: Data would cause divide by zero error.");
        }
        final double r = numerator / denominator;
        final double[] result = { 0.0, 0.0, r * r };
        if (sxx < 0.0) {
            throw new IllegalArgumentException("ExponentialRegression: Data would cause divide by zero error.");
        }
        result[1] = sxy / sxx;
        result[0] = Math.exp(ybar - result[1] * xbar);
        return result;
    }
    
    public static double[] getExponentialRegression(final XYDataset data, final int series) {
        return getExponentialRegression(getXYData(data, series));
    }
    
    public static Function2D getBestRegressionFunction(final double[][] data) {
        Function2D retVal = null;
        double r2 = 0.0;
        double[] coefficients = null;
        try {
            coefficients = getOLSRegression(data);
            retVal = (Function2D)new LineFunction2D(coefficients[0], coefficients[1]);
            r2 = coefficients[2];
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            coefficients = getPowerRegression(data);
            if (coefficients[2] > r2) {
                retVal = (Function2D)new PowerFunction2D(coefficients[0], coefficients[1]);
                r2 = coefficients[2];
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            coefficients = getLogarithmicRegression(data);
            if (coefficients[2] > r2) {
                retVal = (Function2D)new LogarithmicFunction2D(coefficients[0], coefficients[1]);
                r2 = coefficients[2];
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        try {
            coefficients = getExponentialRegression(data);
            if (coefficients[2] > r2) {
                retVal = (Function2D)new ExponentialFunction2D(coefficients[0], coefficients[1]);
                r2 = coefficients[2];
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if (retVal == null) {
            throw new IllegalArgumentException("No regression functions were found with current dataset.");
        }
        return retVal;
    }
    
    public static Function2D getBestRegressionFunction(final XYDataset data, final int series) {
        return getBestRegressionFunction(getXYData(data, series));
    }
}
