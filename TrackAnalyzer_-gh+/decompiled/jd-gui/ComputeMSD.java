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
/*     */ public class ComputeMSD
/*     */ {
/*     */   static List<Double> d1NValues;
/*     */   static List<Double> alphaValues;
/*     */   static List<Double> diffValues;
/*     */   static List<Double> msd1Values;
/*     */   static List<Double> msd2Values;
/*     */   static List<Double> msd3Values;
/*     */   static List<Double> msdValues;
/*     */   static List<Double> mssValues;
/*     */   int nOfPoints;
/*     */   
/*     */   public ComputeMSD(int nOfPoints) {
/*  60 */     this.nOfPoints = nOfPoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public void Compute(List<Integer> nOfTracks, ResultsTable rtSpots) {
/*  65 */     List<List<Double>> imported2SpotX = new ArrayList<>();
/*  66 */     List<List<Double>> imported2SpotY = new ArrayList<>();
/*  67 */     List<List<Double>> imported2SpotT = new ArrayList<>();
/*  68 */     List<List<Double>> imported2SpotF = new ArrayList<>();
/*  69 */     List<Integer> trackName = new ArrayList<>();
/*     */     
/*  71 */     for (int id = 0; id < nOfTracks.size(); id++) {
/*  72 */       trackName.add(nOfTracks.get(id));
/*  73 */       List<Double> imported1SpotX = new ArrayList<>();
/*  74 */       List<Double> imported1SpotY = new ArrayList<>();
/*  75 */       List<Double> imported1SpotT = new ArrayList<>();
/*  76 */       List<Double> imported1SpotF = new ArrayList<>();
/*     */       
/*  78 */       for (int j = 0; j < rtSpots.size(); j++) {
/*  79 */         if (rtSpots.getStringValue(2, j).equals(String.valueOf(((Integer)nOfTracks.get(id)).intValue())) == Boolean.TRUE.booleanValue()) {
/*     */           
/*  81 */           imported1SpotX.add(Double.valueOf(rtSpots.getStringValue(4, j)));
/*  82 */           imported1SpotY.add(Double.valueOf(rtSpots.getStringValue(5, j)));
/*  83 */           imported1SpotT.add(Double.valueOf(rtSpots.getStringValue(7, j)));
/*  84 */           imported1SpotF.add(Double.valueOf(rtSpots.getStringValue(8, j)));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  90 */       imported2SpotX.add(imported1SpotX);
/*  91 */       imported2SpotY.add(imported1SpotY);
/*  92 */       imported2SpotT.add(imported1SpotT);
/*  93 */       imported2SpotF.add(imported1SpotF);
/*     */     } 
/*     */ 
/*     */     
/*  97 */     diffValues = new ArrayList<>();
/*  98 */     d1NValues = new ArrayList<>();
/*  99 */     alphaValues = new ArrayList<>();
/* 100 */     msd1Values = new ArrayList<>();
/* 101 */     msd2Values = new ArrayList<>();
/* 102 */     msd3Values = new ArrayList<>();
/* 103 */     msdValues = new ArrayList<>();
/* 104 */     mssValues = new ArrayList<>();
/*     */     
/* 106 */     for (int i = 0; i < imported2SpotX.size(); i++) {
/*     */       
/* 108 */       XYSeriesCollection datasetMSS = new XYSeriesCollection();
/* 109 */       double frameInterval = ((Double)((List<Double>)imported2SpotT.get(i)).get(2)).doubleValue() - ((Double)((List<Double>)imported2SpotT.get(i)).get(1)).doubleValue();
/* 110 */       int nMSD = ((List)imported2SpotX.get(i)).size();
/* 111 */       int[] tau = new int[4];
/* 112 */       for (int z = 0; z < 4; z++)
/* 113 */         tau[z] = 1 + z; 
/* 114 */       double[] msdArray = new double[tau.length];
/* 115 */       double[] timeArray = new double[tau.length];
/* 116 */       double[] DArray = new double[tau.length];
/* 117 */       double msd = -1.0D;
/*     */       
/* 119 */       double msdhelp123 = 0.0D;
/* 120 */       double msdhelp1 = 0.0D;
/* 121 */       double msdhelp2 = 0.0D;
/* 122 */       double msdhelp3 = 0.0D;
/* 123 */       for (int dt = 0; dt < tau.length; dt++) {
/* 124 */         double N = 0.0D;
/* 125 */         msd = 0.0D;
/*     */         
/* 127 */         for (int i3 = tau[dt]; i3 < ((List)imported2SpotX.get(i)).size(); i3++) {
/* 128 */           msd += 
/* 129 */             Math.pow(((Double)((List<Double>)imported2SpotX.get(i)).get(i3 - tau[dt])).doubleValue() - (
/* 130 */               (Double)((List<Double>)imported2SpotX.get(i)).get(i3)).doubleValue(), 2.0D) + 
/* 131 */             Math.pow(((Double)((List<Double>)imported2SpotY.get(i)).get(i3 - tau[dt])).doubleValue() - (
/* 132 */               (Double)((List<Double>)imported2SpotY.get(i)).get(i3)).doubleValue(), 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 137 */           N++;
/*     */         } 
/*     */ 
/*     */         
/* 141 */         msd /= N;
/* 142 */         msdhelp123 = msd;
/* 143 */         if (tau[dt] == 1)
/* 144 */           msdhelp1 = msd; 
/* 145 */         if (tau[dt] == 2)
/* 146 */           msdhelp2 = msd; 
/* 147 */         if (tau[dt] == 3)
/* 148 */           msdhelp3 = msd; 
/* 149 */         msdArray[dt] = msd;
/* 150 */         DArray[dt] = msd / (4 * tau[dt]) * frameInterval;
/* 151 */         timeArray[dt] = tau[dt] * frameInterval;
/*     */       } 
/*     */ 
/*     */       
/* 155 */       double sum = 0.0D;
/* 156 */       for (int x = 0; x < msdArray.length; x++)
/* 157 */         sum += msdArray[x]; 
/* 158 */       double sumD = 0.0D;
/* 159 */       for (int j = 0; j < DArray.length - 1; j++)
/* 160 */         sumD += DArray[j]; 
/* 161 */       double DAvg = sumD / (DArray.length - 1);
/* 162 */       double msdT = -1.0D;
/* 163 */       SimpleRegression regMSD = new SimpleRegression(true);
/* 164 */       int[] tauMSD = new int[nMSD - 1];
/* 165 */       for (int m = 0; m < nMSD - 1 - 1 + 1; m++)
/* 166 */         tauMSD[m] = 1 + m; 
/* 167 */       for (int k = 0; k < tauMSD.length; k++) {
/* 168 */         double NMSD = 0.0D;
/* 169 */         msdT = 0.0D;
/*     */         
/* 171 */         for (int i3 = tauMSD[k]; i3 < ((List)imported2SpotX.get(i)).size(); i3++) {
/* 172 */           msdT += 
/* 173 */             Math.pow(((Double)((List<Double>)imported2SpotX.get(i)).get(i3 - tauMSD[k])).doubleValue() - (
/* 174 */               (Double)((List<Double>)imported2SpotX.get(i)).get(i3)).doubleValue(), 2.0D) + 
/* 175 */             Math.pow(((Double)((List<Double>)imported2SpotY.get(i)).get(i3 - tauMSD[k])).doubleValue() - (
/* 176 */               (Double)((List<Double>)imported2SpotY.get(i)).get(i3)).doubleValue(), 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 181 */           NMSD++;
/*     */         } 
/*     */ 
/*     */         
/* 185 */         msdT /= NMSD;
/* 186 */         regMSD.addData(tauMSD[k] * frameInterval, msdT);
/*     */       } 
/* 188 */       double msdReg = regMSD.getSlope();
/*     */       
/* 190 */       int[] tauD1N = new int[this.nOfPoints];
/* 191 */       for (int n = 0; n < this.nOfPoints - 1 + 1; n++)
/* 192 */         tauD1N[n] = 1 + n; 
/* 193 */       SimpleRegression regD1N = new SimpleRegression(true);
/* 194 */       double msd1N = -1.0D;
/* 195 */       double msdhelp1N = 0.0D;
/* 196 */       for (int dt1N = 0; dt1N < tauD1N.length; dt1N++) {
/* 197 */         double N1N = 0.0D;
/* 198 */         msd1N = 0.0D;
/*     */         
/* 200 */         for (int i3 = tauD1N[dt1N]; i3 < ((List)imported2SpotX.get(i)).size(); i3++) {
/* 201 */           msd1N += 
/* 202 */             Math.pow(((Double)((List<Double>)imported2SpotX.get(i)).get(i3 - tau[dt1N])).doubleValue() - (
/* 203 */               (Double)((List<Double>)imported2SpotX.get(i)).get(i3)).doubleValue(), 2.0D) + 
/* 204 */             Math.pow(((Double)((List<Double>)imported2SpotY.get(i)).get(i3 - tau[dt1N])).doubleValue() - (
/* 205 */               (Double)((List<Double>)imported2SpotY.get(i)).get(i3)).doubleValue(), 2.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 210 */           N1N++;
/*     */         } 
/*     */ 
/*     */         
/* 214 */         msd1N /= N1N;
/* 215 */         msdhelp1N = msd1N;
/*     */         
/* 217 */         regD1N.addData(tauD1N[dt1N] * frameInterval, msdhelp1N);
/*     */       } 
/*     */ 
/*     */       
/* 221 */       double slopeDiff1N = regD1N.getSlope() / this.nOfPoints;
/*     */ 
/*     */ 
/*     */       
/* 225 */       PowerLawCurveFitModified pwf = new PowerLawCurveFitModified();
/* 226 */       pwf.doFit(timeArray, msdArray);
/* 227 */       alphaValues.add(Double.valueOf(Math.abs(pwf.getAlpha())));
/* 228 */       diffValues.add(Double.valueOf(Math.abs(DAvg)));
/* 229 */       d1NValues.add(Double.valueOf(Math.abs(slopeDiff1N)));
/* 230 */       msdValues.add(Double.valueOf(Math.abs(msdReg)));
/* 231 */       msd1Values.add(Double.valueOf(Math.abs(msdhelp1)));
/* 232 */       msd2Values.add(Double.valueOf(Math.abs(msdhelp2)));
/* 233 */       msd3Values.add(Double.valueOf(Math.abs(msdhelp3)));
/*     */ 
/*     */       
/* 236 */       int[] tauMSS = new int[10];
/* 237 */       for (int i1 = 0; i1 < 10; i1++) {
/* 238 */         tauMSS[i1] = 1 + i1;
/*     */       }
/* 240 */       double[] order = { 1.0D, 2.0D, 3.0D, 4.0D, 5.0D, 6.0D };
/* 241 */       double[] scalingCoef = new double[order.length];
/* 242 */       XYSeriesCollection dataset = new XYSeriesCollection();
/* 243 */       for (int o = 0; o < order.length; o++) {
/* 244 */         XYSeries series1 = new XYSeries(Double.valueOf(order[o]));
/* 245 */         SimpleRegression regMSS = new SimpleRegression(true);
/* 246 */         double momenthelp = 0.0D;
/* 247 */         double moments = -1.0D;
/* 248 */         for (int i3 = 0; i3 < tauMSS.length; i3++) {
/*     */           
/* 250 */           double Nmoments = 0.0D;
/* 251 */           moments = 0.0D;
/*     */           
/* 253 */           for (int i4 = tauMSS[i3]; i4 < ((List)imported2SpotX.get(i)).size(); i4++) {
/*     */             
/* 255 */             moments += 
/* 256 */               Math.pow(Math.abs(((Double)((List<Double>)imported2SpotX.get(i)).get(i4 - tauMSS[i3])).doubleValue() - (
/* 257 */                   (Double)((List<Double>)imported2SpotX.get(i)).get(i4)).doubleValue()), order[o]) + 
/* 258 */               Math.pow(Math.abs(((Double)((List<Double>)imported2SpotY.get(i)).get(i4 - tauMSS[i3])).doubleValue() - (
/* 259 */                   (Double)((List<Double>)imported2SpotY.get(i)).get(i4)).doubleValue()), order[o]);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 264 */             Nmoments++;
/*     */           } 
/*     */ 
/*     */           
/* 268 */           if (moments != 0.0D) {
/* 269 */             moments /= Nmoments;
/* 270 */             momenthelp = moments;
/*     */             
/* 272 */             regMSS.addData(Math.log(Math.abs(tauMSS[i3] * frameInterval)), Math.log(momenthelp));
/* 273 */             series1.add(Math.log(Math.abs(tauMSS[i3] * frameInterval)), Math.log(momenthelp));
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 279 */         dataset.addSeries(series1);
/*     */ 
/*     */         
/* 282 */         scalingCoef[o] = Math.abs(regMSS.getSlope());
/*     */       } 
/*     */ 
/*     */       
/* 286 */       JFreeChart chart1 = ChartFactory.createXYLineChart(
/* 287 */           "log µѵ,i(nΔt) vs. log nΔt for " + String.valueOf(nOfTracks.get(i)), "log nΔt", "log µѵ,i(nΔt)", 
/* 288 */           (XYDataset)dataset);
/*     */       
/*     */       try {
/* 291 */         ChartUtils.saveChartAsPNG(
/* 292 */             new File(String.valueOf(SPTBatch_.directMSS.getAbsolutePath()) + File.separator + 
/* 293 */               "log µѵ,i(nΔt) vs. log nΔt for " + String.valueOf(nOfTracks.get(i))), 
/* 294 */             chart1, 640, 480);
/* 295 */       } catch (IOException ex) {
/* 296 */         System.err.println(ex);
/*     */       } 
/* 298 */       SimpleRegression regScal = new SimpleRegression(true);
/* 299 */       XYSeries series2 = new XYSeries(nOfTracks.get(i));
/* 300 */       for (int i2 = 0; i2 < scalingCoef.length; i2++) {
/*     */         
/* 302 */         regScal.addData((i2 + 1), scalingCoef[i2]);
/* 303 */         series2.add((i2 + 1), scalingCoef[i2]);
/*     */       } 
/*     */       
/* 306 */       datasetMSS.addSeries(series2);
/* 307 */       JFreeChart chart2 = ChartFactory.createScatterPlot("MSS γν vs.ν for " + String.valueOf(nOfTracks.get(i)), 
/* 308 */           "γν", "ν", (XYDataset)datasetMSS);
/*     */       try {
/* 310 */         ChartUtils.saveChartAsPNG(new File(String.valueOf(SPTBatch_.directMSS.getAbsolutePath()) + File.separator + 
/* 311 */               "MSS γν vs.ν for " + String.valueOf(nOfTracks.get(i))), chart2, 640, 480);
/* 312 */       } catch (IOException ex) {
/* 313 */         System.err.println(ex);
/*     */       } 
/*     */       
/* 316 */       double sMss = Math.abs(regScal.getSlope());
/* 317 */       mssValues.add(Double.valueOf(sMss));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/ComputeMSD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */