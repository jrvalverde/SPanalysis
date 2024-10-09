import org.jfree.chart.JFreeChart;
import java.io.IOException;
import org.jfree.chart.ChartUtils;
import java.io.File;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.data.xy.XYSeries;
import math.PowerLawCurveFitModified;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.ArrayList;
import ij.measure.ResultsTable;
import java.util.List;

// 
// Decompiled by Procyon v0.5.36
// 

public class ComputeMSD
{
    static List<Double> d14Values;
    static List<Double> alphaValues;
    static List<Double> diffValues;
    static List<Double> msd1Values;
    static List<Double> msd2Values;
    static List<Double> msd3Values;
    static List<Double> msdValues;
    static List<Double> mssValues;
    
    public void Compute(final List<Integer> nOfTracks, final ResultsTable rtSpots) {
        final List<List<Double>> imported2SpotX = new ArrayList<List<Double>>();
        final List<List<Double>> imported2SpotY = new ArrayList<List<Double>>();
        final List<List<Double>> imported2SpotT = new ArrayList<List<Double>>();
        final List<List<Double>> imported2SpotF = new ArrayList<List<Double>>();
        final List<Integer> trackName = new ArrayList<Integer>();
        for (int id = 0; id < nOfTracks.size(); ++id) {
            trackName.add(nOfTracks.get(id));
            final List<Double> imported1SpotX = new ArrayList<Double>();
            final List<Double> imported1SpotY = new ArrayList<Double>();
            final List<Double> imported1SpotT = new ArrayList<Double>();
            final List<Double> imported1SpotF = new ArrayList<Double>();
            for (int i = 0; i < rtSpots.size(); ++i) {
                if (rtSpots.getStringValue(2, i).equals(String.valueOf((int)nOfTracks.get(id))) == Boolean.TRUE) {
                    imported1SpotX.add(Double.valueOf(rtSpots.getStringValue(4, i)));
                    imported1SpotY.add(Double.valueOf(rtSpots.getStringValue(5, i)));
                    imported1SpotT.add(Double.valueOf(rtSpots.getStringValue(7, i)));
                    imported1SpotT.add(Double.valueOf(rtSpots.getStringValue(8, i)));
                }
            }
            imported2SpotX.add(imported1SpotX);
            imported2SpotY.add(imported1SpotY);
            imported2SpotT.add(imported1SpotT);
            imported2SpotF.add(imported1SpotF);
        }
        ComputeMSD.diffValues = new ArrayList<Double>();
        ComputeMSD.d14Values = new ArrayList<Double>();
        ComputeMSD.alphaValues = new ArrayList<Double>();
        ComputeMSD.msd1Values = new ArrayList<Double>();
        ComputeMSD.msd2Values = new ArrayList<Double>();
        ComputeMSD.msd3Values = new ArrayList<Double>();
        ComputeMSD.msdValues = new ArrayList<Double>();
        ComputeMSD.mssValues = new ArrayList<Double>();
        for (int j = 0; j < imported2SpotX.size(); ++j) {
            final XYSeriesCollection datasetMSS = new XYSeriesCollection();
            final double frameInterval = imported2SpotT.get(j).get(2) - imported2SpotT.get(j).get(1);
            final int nMSD = imported2SpotX.get(j).size();
            final int[] tau = new int[4];
            for (int z = 0; z < 4; ++z) {
                tau[z] = 1 + z;
            }
            final double[] msdArray = new double[tau.length];
            final double[] timeArray = new double[tau.length];
            final double[] DArray = new double[tau.length];
            double msd = -1.0;
            final SimpleRegression regD14 = new SimpleRegression(true);
            double msdhelp123 = 0.0;
            double msdhelp124 = 0.0;
            double msdhelp125 = 0.0;
            double msdhelp126 = 0.0;
            for (int dt = 0; dt < tau.length; ++dt) {
                double N = 0.0;
                msd = 0.0;
                for (int k = tau[dt]; k < imported2SpotX.get(j).size(); ++k) {
                    msd += (Math.pow(imported2SpotX.get(j).get(k - tau[dt]) - imported2SpotX.get(j).get(k), 2.0) + Math.pow(imported2SpotY.get(j).get(k - tau[dt]) - imported2SpotY.get(j).get(k), 2.0)) / Math.abs(imported2SpotF.get(j).get(k - tau[dt]) - imported2SpotF.get(j).get(k));
                    ++N;
                }
                msd = (msdhelp123 = msd / N);
                if (tau[dt] == 1) {
                    msdhelp124 = msd;
                }
                if (tau[dt] == 2) {
                    msdhelp125 = msd;
                }
                if (tau[dt] == 3) {
                    msdhelp126 = msd;
                }
                regD14.addData(tau[dt] * frameInterval, msdhelp123);
                msdArray[dt] = msd;
                DArray[dt] = msd / (4 * tau[dt] * frameInterval);
                timeArray[dt] = tau[dt] * frameInterval;
            }
            double sum = 0.0;
            for (int x = 0; x < msdArray.length; ++x) {
                sum += msdArray[x];
            }
            double sumD = 0.0;
            for (int x2 = 0; x2 < DArray.length - 1; ++x2) {
                sumD += DArray[x2];
            }
            final double DAvg = sumD / (DArray.length - 1);
            final double slopeDiff14 = regD14.getSlope() / 4.0;
            double msdT = -1.0;
            final SimpleRegression regMSD = new SimpleRegression(true);
            final int[] tauMSD = new int[nMSD - 1];
            for (int z2 = 0; z2 < nMSD - 1 - 1 + 1; ++z2) {
                tauMSD[z2] = 1 + z2;
            }
            for (int dt2 = 0; dt2 < tauMSD.length; ++dt2) {
                double NMSD = 0.0;
                msdT = 0.0;
                for (int l = tauMSD[dt2]; l < imported2SpotX.get(j).size(); ++l) {
                    msdT += (Math.pow(imported2SpotX.get(j).get(l - tauMSD[dt2]) - imported2SpotX.get(j).get(l), 2.0) + Math.pow(imported2SpotY.get(j).get(l - tauMSD[dt2]) - imported2SpotY.get(j).get(l), 2.0)) / Math.abs(imported2SpotF.get(j).get(l - tau[dt2]) - imported2SpotF.get(j).get(l));
                    ++NMSD;
                }
                msdT /= NMSD;
                regMSD.addData(tauMSD[dt2] * frameInterval, msdT);
            }
            final double msdReg = regMSD.getSlope();
            final PowerLawCurveFitModified pwf = new PowerLawCurveFitModified();
            pwf.doFit(timeArray, msdArray);
            ComputeMSD.alphaValues.add(Math.abs(pwf.getAlpha()));
            ComputeMSD.diffValues.add(Math.abs(DAvg));
            ComputeMSD.d14Values.add(Math.abs(slopeDiff14));
            ComputeMSD.msdValues.add(Math.abs(msdReg));
            ComputeMSD.msd1Values.add(Math.abs(msdhelp124));
            ComputeMSD.msd2Values.add(Math.abs(msdhelp125));
            ComputeMSD.msd3Values.add(Math.abs(msdhelp126));
            final int[] tauMSS = new int[10];
            for (int z3 = 0; z3 < 10; ++z3) {
                tauMSS[z3] = 1 + z3;
            }
            final double[] order = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0 };
            final double[] scalingCoef = new double[order.length];
            final XYSeriesCollection dataset = new XYSeriesCollection();
            for (int o = 0; o < order.length; ++o) {
                final XYSeries series1 = new XYSeries((Comparable)order[o]);
                final SimpleRegression regMSS = new SimpleRegression(true);
                double momenthelp = 0.0;
                double moments = -1.0;
                for (int dt3 = 0; dt3 < tauMSS.length; ++dt3) {
                    double Nmoments = 0.0;
                    moments = 0.0;
                    for (int m = tauMSS[dt3]; m < imported2SpotX.get(j).size(); ++m) {
                        moments += (Math.pow(Math.abs(imported2SpotX.get(j).get(m - tauMSS[dt3]) - imported2SpotX.get(j).get(m)), order[o]) + Math.pow(Math.abs(imported2SpotY.get(j).get(m - tauMSS[dt3]) - imported2SpotY.get(j).get(m)), order[o])) / Math.abs(imported2SpotF.get(j).get(m - tau[dt3]) - imported2SpotF.get(j).get(m));
                        ++Nmoments;
                    }
                    if (moments != 0.0) {
                        moments = (momenthelp = moments / Nmoments);
                        regMSS.addData(Math.log(Math.abs(tauMSS[dt3] * frameInterval)), Math.log(momenthelp));
                        series1.add(Math.log(Math.abs(tauMSS[dt3] * frameInterval)), Math.log(momenthelp));
                    }
                }
                dataset.addSeries(series1);
                scalingCoef[o] = Math.abs(regMSS.getSlope());
            }
            final JFreeChart chart1 = ChartFactory.createXYLineChart("log µѵ,i(nΔt) vs. log nΔt for " + String.valueOf(nOfTracks.get(j)), "log nΔt", "log µѵ,i(nΔt)", (XYDataset)dataset);
            try {
                ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directMSS.getAbsolutePath()) + File.separator + "log µѵ,i(nΔt) vs. log nΔt for " + String.valueOf(nOfTracks.get(j))), chart1, 640, 480);
            }
            catch (IOException ex) {
                System.err.println(ex);
            }
            final SimpleRegression regScal = new SimpleRegression(true);
            final XYSeries series2 = new XYSeries((Comparable)nOfTracks.get(j));
            for (int z4 = 0; z4 < scalingCoef.length; ++z4) {
                regScal.addData((double)(z4 + 1), scalingCoef[z4]);
                series2.add((double)(z4 + 1), scalingCoef[z4]);
            }
            datasetMSS.addSeries(series2);
            final JFreeChart chart2 = ChartFactory.createScatterPlot("MSS γν vs.ν for " + String.valueOf(nOfTracks.get(j)), "γν", "ν", (XYDataset)datasetMSS);
            try {
                ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directMSS.getAbsolutePath()) + File.separator + "MSS γν vs.ν for " + String.valueOf(nOfTracks.get(j))), chart2, 640, 480);
            }
            catch (IOException ex2) {
                System.err.println(ex2);
            }
            final double sMss = Math.abs(regScal.getSlope());
            ComputeMSD.mssValues.add(sMss);
        }
    }
}
