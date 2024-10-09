import ij.measure.ResultsTable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import math.PowerLawCurveFitModified;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ComputeMSD_Old {
   static List<Double> d14Values;
   static List<Double> alphaValues;
   static List<Double> diffValues;
   static List<Double> msd1Values;
   static List<Double> msd2Values;
   static List<Double> msd3Values;
   static List<Double> msdValues;
   static List<Double> mssValues;

   public void Compute(List<Integer> nOfTracks, ResultsTable rtSpots) {
      List<List<Double>> imported2SpotX = new ArrayList();
      List<List<Double>> imported2SpotY = new ArrayList();
      List<List<Double>> imported2SpotT = new ArrayList();
      List<List<Double>> imported2SpotF = new ArrayList();
      List<Integer> trackName = new ArrayList();

      int i;
      for(i = 0; i < nOfTracks.size(); ++i) {
         trackName.add((Integer)nOfTracks.get(i));
         List<Double> imported1SpotX = new ArrayList();
         List<Double> imported1SpotY = new ArrayList();
         List<Double> imported1SpotT = new ArrayList();
         List<Double> imported1SpotF = new ArrayList();

         for(int i = 0; i < rtSpots.size(); ++i) {
            if (rtSpots.getStringValue(2, i).equals(String.valueOf((Integer)nOfTracks.get(i))) == Boolean.TRUE) {
               imported1SpotX.add(Double.valueOf(rtSpots.getStringValue(4, i)));
               imported1SpotY.add(Double.valueOf(rtSpots.getStringValue(5, i)));
               imported1SpotT.add(Double.valueOf(rtSpots.getStringValue(7, i)));
               imported1SpotF.add(Double.valueOf(rtSpots.getStringValue(8, i)));
            }
         }

         imported2SpotX.add(imported1SpotX);
         imported2SpotY.add(imported1SpotY);
         imported2SpotT.add(imported1SpotT);
         imported2SpotF.add(imported1SpotF);
      }

      diffValues = new ArrayList();
      d14Values = new ArrayList();
      alphaValues = new ArrayList();
      msd1Values = new ArrayList();
      msd2Values = new ArrayList();
      msd3Values = new ArrayList();
      msdValues = new ArrayList();
      mssValues = new ArrayList();

      for(i = 0; i < imported2SpotX.size(); ++i) {
         XYSeriesCollection datasetMSS = new XYSeriesCollection();
         double frameInterval = (Double)((List)imported2SpotT.get(i)).get(2) - (Double)((List)imported2SpotT.get(i)).get(1);
         int nMSD = ((List)imported2SpotX.get(i)).size();
         int[] tau = new int[4];

         for(int z = 0; z < 4; ++z) {
            tau[z] = 1 + z;
         }

         double[] msdArray = new double[tau.length];
         double[] timeArray = new double[tau.length];
         double[] DArray = new double[tau.length];
         double msd = -1.0D;
         SimpleRegression regD14 = new SimpleRegression(true);
         double msdhelp123 = 0.0D;
         double msdhelp1 = 0.0D;
         double msdhelp2 = 0.0D;
         double msdhelp3 = 0.0D;

         for(int dt = 0; dt < tau.length; ++dt) {
            double N = 0.0D;
            msd = 0.0D;

            for(int j = tau[dt]; j < ((List)imported2SpotX.get(i)).size(); ++j) {
               msd += Math.pow((Double)((List)imported2SpotX.get(i)).get(j - tau[dt]) - (Double)((List)imported2SpotX.get(i)).get(j), 2.0D) + Math.pow((Double)((List)imported2SpotY.get(i)).get(j - tau[dt]) - (Double)((List)imported2SpotY.get(i)).get(j), 2.0D);
               ++N;
            }

            msd /= N;
            if (tau[dt] == 1) {
               msdhelp1 = msd;
            }

            if (tau[dt] == 2) {
               msdhelp2 = msd;
            }

            if (tau[dt] == 3) {
               msdhelp3 = msd;
            }

            regD14.addData((double)tau[dt] * frameInterval, msd);
            msdArray[dt] = msd;
            DArray[dt] = msd / ((double)(4 * tau[dt]) * frameInterval);
            timeArray[dt] = (double)tau[dt] * frameInterval;
         }

         double sum = 0.0D;

         for(int x = 0; x < msdArray.length; ++x) {
            sum += msdArray[x];
         }

         double sumD = 0.0D;

         for(int x = 0; x < DArray.length - 1; ++x) {
            sumD += DArray[x];
         }

         double DAvg = sumD / (double)(DArray.length - 1);
         double slopeDiff14 = regD14.getSlope() / 4.0D;
         double msdT = -1.0D;
         SimpleRegression regMSD = new SimpleRegression(true);
         int[] tauMSD = new int[nMSD - 1];

         int dt;
         for(dt = 0; dt < nMSD - 1 - 1 + 1; ++dt) {
            tauMSD[dt] = 1 + dt;
         }

         for(dt = 0; dt < tauMSD.length; ++dt) {
            double NMSD = 0.0D;
            msdT = 0.0D;

            for(int j = tauMSD[dt]; j < ((List)imported2SpotX.get(i)).size(); ++j) {
               msdT += Math.pow((Double)((List)imported2SpotX.get(i)).get(j - tauMSD[dt]) - (Double)((List)imported2SpotX.get(i)).get(j), 2.0D) + Math.pow((Double)((List)imported2SpotY.get(i)).get(j - tauMSD[dt]) - (Double)((List)imported2SpotY.get(i)).get(j), 2.0D);
               ++NMSD;
            }

            msdT /= NMSD;
            regMSD.addData((double)tauMSD[dt] * frameInterval, msdT);
         }

         double msdReg = regMSD.getSlope();
         PowerLawCurveFitModified pwf = new PowerLawCurveFitModified();
         pwf.doFit(timeArray, msdArray);
         alphaValues.add(Math.abs(pwf.getAlpha()));
         diffValues.add(Math.abs(DAvg));
         d14Values.add(Math.abs(slopeDiff14));
         msdValues.add(Math.abs(msdReg));
         msd1Values.add(Math.abs(msdhelp1));
         msd2Values.add(Math.abs(msdhelp2));
         msd3Values.add(Math.abs(msdhelp3));
         int[] tauMSS = new int[10];

         for(int z = 0; z < 10; ++z) {
            tauMSS[z] = 1 + z;
         }

         double[] order = new double[]{1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D};
         double[] scalingCoef = new double[order.length];
         XYSeriesCollection dataset = new XYSeriesCollection();

         for(int o = 0; o < order.length; ++o) {
            XYSeries series1 = new XYSeries(order[o]);
            SimpleRegression regMSS = new SimpleRegression(true);
            double momenthelp = 0.0D;
            double moments = -1.0D;

            for(int dt = 0; dt < tauMSS.length; ++dt) {
               double Nmoments = 0.0D;
               moments = 0.0D;

               for(int j = tauMSS[dt]; j < ((List)imported2SpotX.get(i)).size(); ++j) {
                  moments += Math.pow(Math.abs((Double)((List)imported2SpotX.get(i)).get(j - tauMSS[dt]) - (Double)((List)imported2SpotX.get(i)).get(j)), order[o]) + Math.pow(Math.abs((Double)((List)imported2SpotY.get(i)).get(j - tauMSS[dt]) - (Double)((List)imported2SpotY.get(i)).get(j)), order[o]);
                  ++Nmoments;
               }

               if (moments != 0.0D) {
                  moments /= Nmoments;
                  regMSS.addData(Math.log(Math.abs((double)tauMSS[dt] * frameInterval)), Math.log(moments));
                  series1.add(Math.log(Math.abs((double)tauMSS[dt] * frameInterval)), Math.log(moments));
               }
            }

            dataset.addSeries(series1);
            scalingCoef[o] = Math.abs(regMSS.getSlope());
         }

         JFreeChart chart1 = ChartFactory.createXYLineChart("log µѵ,i(nΔt) vs. log nΔt for " + String.valueOf(nOfTracks.get(i)), "log nΔt", "log µѵ,i(nΔt)", dataset);

         try {
            ChartUtils.saveChartAsPNG(new File(SPTBatch_.directMSS.getAbsolutePath() + File.separator + "log µѵ,i(nΔt) vs. log nΔt for " + nOfTracks.get(i)), chart1, 640, 480);
         } catch (IOException var59) {
            System.err.println(var59);
         }

         SimpleRegression regScal = new SimpleRegression(true);
         XYSeries series2 = new XYSeries((Comparable)nOfTracks.get(i));

         for(int z = 0; z < scalingCoef.length; ++z) {
            regScal.addData((double)(z + 1), scalingCoef[z]);
            series2.add((double)(z + 1), scalingCoef[z]);
         }

         datasetMSS.addSeries(series2);
         JFreeChart chart2 = ChartFactory.createScatterPlot("MSS γν vs.ν for " + String.valueOf(nOfTracks.get(i)), "γν", "ν", datasetMSS);

         try {
            ChartUtils.saveChartAsPNG(new File(SPTBatch_.directMSS.getAbsolutePath() + File.separator + "MSS γν vs.ν for " + nOfTracks.get(i)), chart2, 640, 480);
         } catch (IOException var58) {
            System.err.println(var58);
         }

         double sMss = Math.abs(regScal.getSlope());
         mssValues.add(sMss);
      }

   }
}
