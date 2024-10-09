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
/*     */ public class ComputeMSD
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
/*  82 */           imported1SpotT.add(Double.valueOf(rtSpots.getStringValue(8, j)));
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
/* 127 */           msd += (
/* 128 */             Math.pow(((Double)((List<Double>)imported2SpotX.get(i)).get(i2 - tau[dt])).doubleValue() - (
/* 129 */               (Double)((List<Double>)imported2SpotX.get(i)).get(i2)).doubleValue(), 2.0D) + 
/* 130 */             Math.pow(((Double)((List<Double>)imported2SpotY.get(i)).get(i2 - tau[dt])).doubleValue() - (
/* 131 */               (Double)((List<Double>)imported2SpotY.get(i)).get(i2)).doubleValue(), 2.0D)) / 
/* 132 */             Math.abs(((Double)((List<Double>)imported2SpotF.get(i)).get(i2 - tau[dt])).doubleValue() - (
/* 133 */               (Double)((List<Double>)imported2SpotF.get(i)).get(i2)).doubleValue());
/*     */           
/* 135 */           N++;
/*     */         } 
/*     */ 
/*     */         
/* 139 */         msd /= N;
/* 140 */         msdhelp123 = msd;
/* 141 */         if (tau[dt] == 1)
/* 142 */           msdhelp1 = msd; 
/* 143 */         if (tau[dt] == 2)
/* 144 */           msdhelp2 = msd; 
/* 145 */         if (tau[dt] == 3)
/* 146 */           msdhelp3 = msd; 
/* 147 */         regD14.addData(tau[dt] * frameInterval, msdhelp123);
/* 148 */         msdArray[dt] = msd;
/* 149 */         DArray[dt] = msd / (4 * tau[dt]) * frameInterval;
/* 150 */         timeArray[dt] = tau[dt] * frameInterval;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       double sum = 0.0D;
/* 160 */       for (int x = 0; x < msdArray.length; x++) {
/* 161 */         sum += msdArray[x];
/*     */       }
/* 163 */       double sumD = 0.0D;
/* 164 */       for (int j = 0; j < DArray.length - 1; j++)
/* 165 */         sumD += DArray[j]; 
/* 166 */       double DAvg = sumD / (DArray.length - 1);
/* 167 */       double slopeDiff14 = regD14.getSlope() / 4.0D;
/*     */ 
/*     */       
/* 170 */       double msdT = -1.0D;
/* 171 */       SimpleRegression regMSD = new SimpleRegression(true);
/* 172 */       int[] tauMSD = new int[nMSD - 1];
/* 173 */       for (int m = 0; m < nMSD - 1 - 1 + 1; m++)
/* 174 */         tauMSD[m] = 1 + m; 
/* 175 */       for (int k = 0; k < tauMSD.length; k++) {
/* 176 */         double NMSD = 0.0D;
/* 177 */         msdT = 0.0D;
/*     */         
/* 179 */         for (int i2 = tauMSD[k]; i2 < ((List)imported2SpotX.get(i)).size(); i2++) {
/* 180 */           msdT += (
/* 181 */             Math.pow(((Double)((List<Double>)imported2SpotX.get(i)).get(i2 - tauMSD[k])).doubleValue() - (
/* 182 */               (Double)((List<Double>)imported2SpotX.get(i)).get(i2)).doubleValue(), 2.0D) + 
/* 183 */             Math.pow(((Double)((List<Double>)imported2SpotY.get(i)).get(i2 - tauMSD[k])).doubleValue() - (
/* 184 */               (Double)((List<Double>)imported2SpotY.get(i)).get(i2)).doubleValue(), 2.0D)) / 
/* 185 */             Math.abs(((Double)((List<Double>)imported2SpotF.get(i)).get(i2 - tau[k])).doubleValue() - (
/* 186 */               (Double)((List<Double>)imported2SpotF.get(i)).get(i2)).doubleValue());
/*     */           
/* 188 */           NMSD++;
/*     */         } 
/*     */ 
/*     */         
/* 192 */         msdT /= NMSD;
/* 193 */         regMSD.addData(tauMSD[k] * frameInterval, msdT);
/*     */       } 
/* 195 */       double msdReg = regMSD.getSlope();
/*     */ 
/*     */       
/* 198 */       PowerLawCurveFitModified pwf = new PowerLawCurveFitModified();
/* 199 */       pwf.doFit(timeArray, msdArray);
/*     */ 
/*     */       
/* 202 */       alphaValues.add(Double.valueOf(Math.abs(pwf.getAlpha())));
/* 203 */       diffValues.add(Double.valueOf(Math.abs(DAvg)));
/* 204 */       d14Values.add(Double.valueOf(Math.abs(slopeDiff14)));
/* 205 */       msdValues.add(Double.valueOf(Math.abs(msdReg)));
/* 206 */       msd1Values.add(Double.valueOf(Math.abs(msdhelp1)));
/* 207 */       msd2Values.add(Double.valueOf(Math.abs(msdhelp2)));
/* 208 */       msd3Values.add(Double.valueOf(Math.abs(msdhelp3)));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 213 */       int[] tauMSS = new int[10];
/* 214 */       for (int n = 0; n < 10; n++) {
/* 215 */         tauMSS[n] = 1 + n;
/*     */       }
/* 217 */       double[] order = { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D };
/* 218 */       double[] scalingCoef = new double[order.length];
/* 219 */       XYSeriesCollection dataset = new XYSeriesCollection();
/* 220 */       for (int o = 0; o < order.length; o++) {
/* 221 */         XYSeries series1 = new XYSeries(Double.valueOf(order[o]));
/* 222 */         SimpleRegression regMSS = new SimpleRegression(true);
/* 223 */         double momenthelp = 0.0D;
/* 224 */         double moments = -1.0D;
/* 225 */         for (int i2 = 0; i2 < tauMSS.length; i2++) {
/*     */           
/* 227 */           double Nmoments = 0.0D;
/* 228 */           moments = 0.0D;
/*     */           
/* 230 */           for (int i3 = tauMSS[i2]; i3 < ((List)imported2SpotX.get(i)).size(); i3++) {
/*     */             
/* 232 */             moments += (
/* 233 */               Math.pow(Math.abs(((Double)((List<Double>)imported2SpotX.get(i)).get(i3 - tauMSS[i2])).doubleValue() - (
/* 234 */                   (Double)((List<Double>)imported2SpotX.get(i)).get(i3)).doubleValue()), order[o]) + 
/* 235 */               Math.pow(Math.abs(((Double)((List<Double>)imported2SpotY.get(i)).get(i3 - tauMSS[i2])).doubleValue() - (
/* 236 */                   (Double)((List<Double>)imported2SpotY.get(i)).get(i3)).doubleValue()), order[o])) / 
/* 237 */               Math.abs(((Double)((List<Double>)imported2SpotF.get(i)).get(i3 - tau[i2])).doubleValue() - (
/* 238 */                 (Double)((List<Double>)imported2SpotF.get(i)).get(i3)).doubleValue());
/*     */             
/* 240 */             Nmoments++;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 245 */           if (moments != 0.0D) {
/* 246 */             moments /= Nmoments;
/* 247 */             momenthelp = moments;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 252 */             regMSS.addData(Math.log(Math.abs(tauMSS[i2] * frameInterval)), Math.log(momenthelp));
/* 253 */             series1.add(Math.log(Math.abs(tauMSS[i2] * frameInterval)), Math.log(momenthelp));
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 259 */         dataset.addSeries(series1);
/*     */ 
/*     */         
/* 262 */         scalingCoef[o] = Math.abs(regMSS.getSlope());
/*     */       } 
/*     */ 
/*     */       
/* 266 */       JFreeChart chart1 = ChartFactory.createXYLineChart(
/* 267 */           "log µѵ,i(nΔt) vs. log nΔt for " + String.valueOf(nOfTracks.get(i)), "log nΔt", "log µѵ,i(nΔt)", 
/* 268 */           (XYDataset)dataset);
/*     */       
/*     */       try {
/* 271 */         ChartUtils.saveChartAsPNG(
/* 272 */             new File(String.valueOf(SPTBatch_.directMSS.getAbsolutePath()) + File.separator + 
/* 273 */               "log µѵ,i(nΔt) vs. log nΔt for " + String.valueOf(nOfTracks.get(i))), 
/* 274 */             chart1, 640, 480);
/* 275 */       } catch (IOException ex) {
/* 276 */         System.err.println(ex);
/*     */       } 
/* 278 */       SimpleRegression regScal = new SimpleRegression(true);
/* 279 */       XYSeries series2 = new XYSeries(nOfTracks.get(i));
/* 280 */       for (int i1 = 0; i1 < scalingCoef.length; i1++) {
/*     */         
/* 282 */         regScal.addData((i1 + 1), scalingCoef[i1]);
/* 283 */         series2.add((i1 + 1), scalingCoef[i1]);
/*     */       } 
/*     */       
/* 286 */       datasetMSS.addSeries(series2);
/* 287 */       JFreeChart chart2 = ChartFactory.createScatterPlot("MSS γν vs.ν for " + String.valueOf(nOfTracks.get(i)), 
/* 288 */           "γν", "ν", (XYDataset)datasetMSS);
/*     */       try {
/* 290 */         ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directMSS.getAbsolutePath()) + File.separator + 
/* 291 */               "MSS γν vs.ν for " + String.valueOf(nOfTracks.get(i))), chart2, 640, 480);
/* 292 */       } catch (IOException ex) {
/* 293 */         System.err.println(ex);
/*     */       } 
/*     */       
/* 296 */       double sMss = Math.abs(regScal.getSlope());
/* 297 */       mssValues.add(Double.valueOf(sMss));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/ComputeMSD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */