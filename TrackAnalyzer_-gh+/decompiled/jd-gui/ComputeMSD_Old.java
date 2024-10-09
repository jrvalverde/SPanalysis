/*     */ import ij.measure.ResultsTable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import math.PowerLawCurveFitModified;
/*     */ import org.apache.commons.math3.stat.regression.SimpleRegression;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartUtils;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.data.xy.XYSeries;
/*     */ import org.jfree.data.xy.XYSeriesCollection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComputeMSD_Old
/*     */ {
/*     */   static List<Double> d14Values;
/*     */   static List<Double> alphaValues;
/*     */   static List<Double> diffValues;
/*     */   static List<Double> msd1Values;
/*     */   static List<Double> msd2Values;
/*     */   static List<Double> msd3Values;
/*     */   static List<Double> msdValues;
/*     */   static List<Double> mssValues;
/*     */   
/*     */   public void Compute(List<Integer> nOfTracks, ResultsTable rtSpots) {
/*  63 */     List<List<Double>> imported2SpotX = new ArrayList<>();
/*  64 */     List<List<Double>> imported2SpotY = new ArrayList<>();
/*  65 */     List<List<Double>> imported2SpotT = new ArrayList<>();
/*  66 */     List<List<Double>> imported2SpotF = new ArrayList<>();
/*  67 */     List<Integer> trackName = new ArrayList<>();
/*     */     
/*  69 */     for (int id = 0; id < nOfTracks.size(); id++) {
/*  70 */       trackName.add(nOfTracks.get(id));
/*  71 */       List<Double> imported1SpotX = new ArrayList<>();
/*  72 */       List<Double> imported1SpotY = new ArrayList<>();
/*  73 */       List<Double> imported1SpotT = new ArrayList<>();
/*  74 */       List<Double> imported1SpotF = new ArrayList<>();
/*     */       
/*  76 */       for (int j = 0; j < rtSpots.size(); j++) {
/*  77 */         if (rtSpots.getStringValue(2, j).equals(String.valueOf(((Integer)nOfTracks.get(id)).intValue())) == Boolean.TRUE.booleanValue()) {
/*     */           
/*  79 */           imported1SpotX.add(Double.valueOf(rtSpots.getStringValue(4, j)));
/*  80 */           imported1SpotY.add(Double.valueOf(rtSpots.getStringValue(5, j)));
/*  81 */           imported1SpotT.add(Double.valueOf(rtSpots.getStringValue(7, j)));
/*  82 */           imported1SpotF.add(Double.valueOf(rtSpots.getStringValue(8, j)));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  88 */       imported2SpotX.add(imported1SpotX);
/*  89 */       imported2SpotY.add(imported1SpotY);
/*  90 */       imported2SpotT.add(imported1SpotT);
/*  91 */       imported2SpotF.add(imported1SpotF);
/*     */     } 
/*     */ 
/*     */     
/*  95 */     diffValues = new ArrayList<>();
/*  96 */     d14Values = new ArrayList<>();
/*  97 */     alphaValues = new ArrayList<>();
/*  98 */     msd1Values = new ArrayList<>();
/*  99 */     msd2Values = new ArrayList<>();
/* 100 */     msd3Values = new ArrayList<>();
/* 101 */     msdValues = new ArrayList<>();
/* 102 */     mssValues = new ArrayList<>();
/*     */     
/* 104 */     for (int i = 0; i < imported2SpotX.size(); i++) {
/*     */       
/* 106 */       XYSeriesCollection datasetMSS = new XYSeriesCollection();
/* 107 */       double frameInterval = ((Double)((List<Double>)imported2SpotT.get(i)).get(2)).doubleValue() - ((Double)((List<Double>)imported2SpotT.get(i)).get(1)).doubleValue();
/* 108 */       int nMSD = ((List)imported2SpotX.get(i)).size();
/* 109 */       int[] tau = new int[4];
/* 110 */       for (int z = 0; z < 4; z++)
/* 111 */         tau[z] = 1 + z; 
/* 112 */       double[] msdArray = new double[tau.length];
/* 113 */       double[] timeArray = new double[tau.length];
/* 114 */       double[] DArray = new double[tau.length];
/* 115 */       double msd = -1.0D;
/* 116 */       SimpleRegression regD14 = new SimpleRegression(true);
/*     */       
/* 118 */       double msdhelp123 = 0.0D;
/* 119 */       double msdhelp1 = 0.0D;
/* 120 */       double msdhelp2 = 0.0D;
/* 121 */       double msdhelp3 = 0.0D;
/* 122 */       for (int dt = 0; dt < tau.length; dt++) {
/* 123 */         double N = 0.0D;
/* 124 */         msd = 0.0D;
/*     */         
/* 126 */         for (int i2 = tau[dt]; i2 < ((List)imported2SpotX.get(i)).size(); i2++) {
/* 127 */           msd += 
/* 128 */             Math.pow(((Double)((List<Double>)imported2SpotX.get(i)).get(i2 - tau[dt])).doubleValue() - (
/* 129 */               (Double)((List<Double>)imported2SpotX.get(i)).get(i2)).doubleValue(), 2.0D) + 
/* 130 */             Math.pow(((Double)((List<Double>)imported2SpotY.get(i)).get(i2 - tau[dt])).doubleValue() - (
/* 131 */               (Double)((List<Double>)imported2SpotY.get(i)).get(i2)).doubleValue(), 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 136 */           N++;
/*     */         } 
/*     */ 
/*     */         
/* 140 */         msd /= N;
/* 141 */         msdhelp123 = msd;
/* 142 */         if (tau[dt] == 1)
/* 143 */           msdhelp1 = msd; 
/* 144 */         if (tau[dt] == 2)
/* 145 */           msdhelp2 = msd; 
/* 146 */         if (tau[dt] == 3)
/* 147 */           msdhelp3 = msd; 
/* 148 */         regD14.addData(tau[dt] * frameInterval, msdhelp123);
/* 149 */         msdArray[dt] = msd;
/* 150 */         DArray[dt] = msd / (4 * tau[dt]) * frameInterval;
/* 151 */         timeArray[dt] = tau[dt] * frameInterval;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 160 */       double sum = 0.0D;
/* 161 */       for (int x = 0; x < msdArray.length; x++) {
/* 162 */         sum += msdArray[x];
/*     */       }
/* 164 */       double sumD = 0.0D;
/* 165 */       for (int j = 0; j < DArray.length - 1; j++)
/* 166 */         sumD += DArray[j]; 
/* 167 */       double DAvg = sumD / (DArray.length - 1);
/* 168 */       double slopeDiff14 = regD14.getSlope() / 4.0D;
/*     */ 
/*     */       
/* 171 */       double msdT = -1.0D;
/* 172 */       SimpleRegression regMSD = new SimpleRegression(true);
/* 173 */       int[] tauMSD = new int[nMSD - 1];
/* 174 */       for (int m = 0; m < nMSD - 1 - 1 + 1; m++)
/* 175 */         tauMSD[m] = 1 + m; 
/* 176 */       for (int k = 0; k < tauMSD.length; k++) {
/* 177 */         double NMSD = 0.0D;
/* 178 */         msdT = 0.0D;
/*     */         
/* 180 */         for (int i2 = tauMSD[k]; i2 < ((List)imported2SpotX.get(i)).size(); i2++) {
/* 181 */           msdT += 
/* 182 */             Math.pow(((Double)((List<Double>)imported2SpotX.get(i)).get(i2 - tauMSD[k])).doubleValue() - (
/* 183 */               (Double)((List<Double>)imported2SpotX.get(i)).get(i2)).doubleValue(), 2.0D) + 
/* 184 */             Math.pow(((Double)((List<Double>)imported2SpotY.get(i)).get(i2 - tauMSD[k])).doubleValue() - (
/* 185 */               (Double)((List<Double>)imported2SpotY.get(i)).get(i2)).doubleValue(), 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 190 */           NMSD++;
/*     */         } 
/*     */ 
/*     */         
/* 194 */         msdT /= NMSD;
/* 195 */         regMSD.addData(tauMSD[k] * frameInterval, msdT);
/*     */       } 
/* 197 */       double msdReg = regMSD.getSlope();
/*     */ 
/*     */       
/* 200 */       PowerLawCurveFitModified pwf = new PowerLawCurveFitModified();
/* 201 */       pwf.doFit(timeArray, msdArray);
/*     */ 
/*     */       
/* 204 */       alphaValues.add(Double.valueOf(Math.abs(pwf.getAlpha())));
/* 205 */       diffValues.add(Double.valueOf(Math.abs(DAvg)));
/* 206 */       d14Values.add(Double.valueOf(Math.abs(slopeDiff14)));
/* 207 */       msdValues.add(Double.valueOf(Math.abs(msdReg)));
/* 208 */       msd1Values.add(Double.valueOf(Math.abs(msdhelp1)));
/* 209 */       msd2Values.add(Double.valueOf(Math.abs(msdhelp2)));
/* 210 */       msd3Values.add(Double.valueOf(Math.abs(msdhelp3)));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 215 */       int[] tauMSS = new int[10];
/* 216 */       for (int n = 0; n < 10; n++) {
/* 217 */         tauMSS[n] = 1 + n;
/*     */       }
/* 219 */       double[] order = { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D };
/* 220 */       double[] scalingCoef = new double[order.length];
/* 221 */       XYSeriesCollection dataset = new XYSeriesCollection();
/* 222 */       for (int o = 0; o < order.length; o++) {
/* 223 */         XYSeries series1 = new XYSeries(Double.valueOf(order[o]));
/* 224 */         SimpleRegression regMSS = new SimpleRegression(true);
/* 225 */         double momenthelp = 0.0D;
/* 226 */         double moments = -1.0D;
/* 227 */         for (int i2 = 0; i2 < tauMSS.length; i2++) {
/*     */           
/* 229 */           double Nmoments = 0.0D;
/* 230 */           moments = 0.0D;
/*     */           
/* 232 */           for (int i3 = tauMSS[i2]; i3 < ((List)imported2SpotX.get(i)).size(); i3++) {
/*     */             
/* 234 */             moments += 
/* 235 */               Math.pow(Math.abs(((Double)((List<Double>)imported2SpotX.get(i)).get(i3 - tauMSS[i2])).doubleValue() - (
/* 236 */                   (Double)((List<Double>)imported2SpotX.get(i)).get(i3)).doubleValue()), order[o]) + 
/* 237 */               Math.pow(Math.abs(((Double)((List<Double>)imported2SpotY.get(i)).get(i3 - tauMSS[i2])).doubleValue() - (
/* 238 */                   (Double)((List<Double>)imported2SpotY.get(i)).get(i3)).doubleValue()), order[o]);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 243 */             Nmoments++;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 248 */           if (moments != 0.0D) {
/* 249 */             moments /= Nmoments;
/* 250 */             momenthelp = moments;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 255 */             regMSS.addData(Math.log(Math.abs(tauMSS[i2] * frameInterval)), Math.log(momenthelp));
/* 256 */             series1.add(Math.log(Math.abs(tauMSS[i2] * frameInterval)), Math.log(momenthelp));
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 262 */         dataset.addSeries(series1);
/*     */ 
/*     */         
/* 265 */         scalingCoef[o] = Math.abs(regMSS.getSlope());
/*     */       } 
/*     */ 
/*     */       
/* 269 */       JFreeChart chart1 = ChartFactory.createXYLineChart(
/* 270 */           "log µѵ,i(nΔt) vs. log nΔt for " + String.valueOf(nOfTracks.get(i)), "log nΔt", "log µѵ,i(nΔt)", 
/* 271 */           (XYDataset)dataset);
/*     */       
/*     */       try {
/* 274 */         ChartUtils.saveChartAsPNG(
/* 275 */             new File(String.valueOf(SPTBatch_.directMSS.getAbsolutePath()) + File.separator + 
/* 276 */               "log µѵ,i(nΔt) vs. log nΔt for " + String.valueOf(nOfTracks.get(i))), 
/* 277 */             chart1, 640, 480);
/* 278 */       } catch (IOException ex) {
/* 279 */         System.err.println(ex);
/*     */       } 
/* 281 */       SimpleRegression regScal = new SimpleRegression(true);
/* 282 */       XYSeries series2 = new XYSeries(nOfTracks.get(i));
/* 283 */       for (int i1 = 0; i1 < scalingCoef.length; i1++) {
/*     */         
/* 285 */         regScal.addData((i1 + 1), scalingCoef[i1]);
/* 286 */         series2.add((i1 + 1), scalingCoef[i1]);
/*     */       } 
/*     */       
/* 289 */       datasetMSS.addSeries(series2);
/* 290 */       JFreeChart chart2 = ChartFactory.createScatterPlot("MSS γν vs.ν for " + String.valueOf(nOfTracks.get(i)), 
/* 291 */           "γν", "ν", (XYDataset)datasetMSS);
/*     */       try {
/* 293 */         ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directMSS.getAbsolutePath()) + File.separator + 
/* 294 */               "MSS γν vs.ν for " + String.valueOf(nOfTracks.get(i))), chart2, 640, 480);
/* 295 */       } catch (IOException ex) {
/* 296 */         System.err.println(ex);
/*     */       } 
/*     */       
/* 299 */       double sMss = Math.abs(regScal.getSlope());
/* 300 */       mssValues.add(Double.valueOf(sMss));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/ComputeMSD_Old.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */