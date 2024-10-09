/*     */ import DiffusionCoefficientEstimator.AbstractDiffusionCoefficientEstimatorModified;
/*     */ import DiffusionCoefficientEstimator.CovarianceDiffusionCoefficientEstimatorModified;
/*     */ import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
/*     */ import de.biomedical_imaging.ij.trajectory_classifier.TraJResultsTable;
/*     */ import drift.StaticDriftCalculatorModified;
/*     */ import features.AbstractTrajectoryFeatureModified;
/*     */ import features.ActiveTransportParametersFeatureModified;
/*     */ import features.Asymmetry3FeatureModified;
/*     */ import features.CenterOfGravityFeatureModified;
/*     */ import features.ConfinedDiffusionParametersFeatureModified;
/*     */ import features.EfficiencyFeatureModified;
/*     */ import features.FractalDimensionFeatureModified;
/*     */ import features.GaussianityFeautureModified;
/*     */ import features.KurtosisFeatureModified;
/*     */ import features.MSDRatioFeatureModified;
/*     */ import features.MeanSpeedFeatureModified;
/*     */ import features.PowerLawFeatureModified;
/*     */ import features.StraightnessFeatureModified;
/*     */ import features.TrappedProbabilityFeatureModified;
/*     */ import fiji.plugin.trackmate.Spot;
/*     */ import ij.IJ;
/*     */ import ij.gui.Overlay;
/*     */ import ij.gui.Roi;
/*     */ import ij.gui.TextRoi;
/*     */ import ij.measure.ResultsTable;
/*     */ import ij.plugin.PlugIn;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.math3.stat.StatUtils;
/*     */ import traJ.TrajectoryModified;
/*     */ import trajectory_classifier.AbstractClassifierModified;
/*     */ import trajectory_classifier.ExportImportToolsModified;
/*     */ import trajectory_classifier.RRFClassifierRenjinModified;
/*     */ import trajectory_classifier.SubtrajectoryModified;
/*     */ import trajectory_classifier.VisualizationUtilsModified;
/*     */ import trajectory_classifier.WeightedWindowedClassificationProcessModified;
/*     */ import vecmath.Point3dModified;
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
/*     */ public class TraJClassifierTest_
/*     */   implements PlugIn
/*     */ {
/*     */   static double timelag;
/*     */   double minTrackLength;
/*     */   int windowSizeClassification;
/*     */   int minSegmentLength;
/*     */   double pixelsize;
/*     */   int resampleRate;
/*     */   boolean showID;
/*     */   boolean showOverviewClasses;
/*     */   boolean removeGlobalDrift;
/*     */   boolean useReducedModelConfinedMotion;
/*     */   int ommittedTrajectories;
/*     */   public static TraJClassifierTest_ instance;
/*     */   ArrayList<SubtrajectoryModified> classifiedSegments;
/*     */   ArrayList<TrajectoryModified> tracksToClassify;
/*     */   ArrayList<TrajectoryModified> parentTrajectories;
/*     */   
/*     */   public TraJClassifierTest_() {
/*  92 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public static TraJClassifierTest_ getInstance() {
/*  97 */     if (instance == null) {
/*  98 */       instance = new TraJClassifierTest_();
/*     */     }
/* 100 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void run(String arg) {
/* 108 */     String modelpath = "";
/*     */     try {
/* 110 */       modelpath = ExportResource("/randomForestModel.RData");
/* 111 */     } catch (Exception e) {
/*     */       
/* 113 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     List<Integer> trackSize = null;
/* 120 */     if (!arg.contains("DEBUG")) {
/* 121 */       this.tracksToClassify = new ArrayList<>();
/* 122 */       TrajectoryModified.restIDCounter();
/*     */       
/* 124 */       List<List<Spot>> imported2Spot = new ArrayList<>();
/* 125 */       Set<Integer> trackIDs = SPTBatch_.model.getTrackModel().trackIDs(true);
/* 126 */       trackSize = new ArrayList<>();
/*     */       
/* 128 */       for (Integer id : trackIDs) {
/* 129 */         List<Spot> imported1Spot = new ArrayList<>();
/* 130 */         Set<Spot> track = SPTBatch_.model.getTrackModel().trackSpots(id);
/* 131 */         trackSize.add(Integer.valueOf(track.size()));
/* 132 */         ArrayList<Float> framesByTrack = new ArrayList<>();
/* 133 */         ArrayList<Float> framesByTrackSort = new ArrayList<>();
/* 134 */         ArrayList<Float> trackID = new ArrayList<>();
/* 135 */         ArrayList<Integer> indexes = new ArrayList<>();
/* 136 */         List<Spot> spots = new ArrayList<>();
/* 137 */         for (Spot spot : track) {
/* 138 */           trackID.add(Float.valueOf(id.intValue()));
/* 139 */           framesByTrack.add(Float.valueOf(spot.getFeature("FRAME").toString()));
/* 140 */           framesByTrackSort.add(Float.valueOf(spot.getFeature("FRAME").toString()));
/* 141 */           spots.add(spot);
/*     */         } 
/*     */         
/* 144 */         Collections.sort(framesByTrackSort);
/* 145 */         for (int z = 0; z < framesByTrackSort.size(); z++)
/* 146 */           indexes.add(Integer.valueOf(framesByTrack.indexOf(framesByTrackSort.get(z)))); 
/* 147 */         for (int y = 0; y < indexes.size(); y++) {
/* 148 */           imported1Spot.add(spots.get(((Integer)indexes.get(y)).intValue()));
/*     */         }
/* 150 */         imported2Spot.add(imported1Spot);
/*     */       } 
/*     */ 
/*     */       
/* 154 */       for (int k = 0; k < imported2Spot.size(); k++) {
/* 155 */         TrajectoryModified t = new TrajectoryModified(2);
/* 156 */         boolean firstPosition = true;
/* 157 */         for (int m = 0; m < ((List)imported2Spot.get(k)).size(); m++) {
/* 158 */           double x = ((Spot)((List<Spot>)imported2Spot.get(k)).get(m)).getFeature("POSITION_X").doubleValue();
/* 159 */           double y = ((Spot)((List<Spot>)imported2Spot.get(k)).get(m)).getFeature("POSITION_Y").doubleValue();
/* 160 */           int time = ((Spot)((List<Spot>)imported2Spot.get(k)).get(m)).getFeature("FRAME").intValue();
/* 161 */           if (firstPosition) {
/* 162 */             t.setRelativStartTimepoint(time);
/* 163 */             firstPosition = false;
/*     */           } 
/* 165 */           t.add(x, y, 0.0D);
/*     */         } 
/*     */         
/* 168 */         this.tracksToClassify.add(t);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 173 */     int maxNumberOfPositions = 0;
/* 174 */     for (int i = 0; i < this.tracksToClassify.size(); i++) {
/* 175 */       if (((TrajectoryModified)this.tracksToClassify.get(i)).size() > maxNumberOfPositions) {
/* 176 */         maxNumberOfPositions = ((TrajectoryModified)this.tracksToClassify.get(i)).size();
/*     */       }
/*     */     } 
/* 179 */     boolean visualize = true;
/*     */     
/* 181 */     if (traJParametersWindow.keyWord == "keyword") {
/* 182 */       this.minTrackLength = Integer.valueOf(traJParametersWindow.minLengthTextS).intValue();
/* 183 */       this.windowSizeClassification = Integer.valueOf(traJParametersWindow.windowTextS).intValue();
/* 184 */       this.minSegmentLength = Integer.valueOf(traJParametersWindow.minSegTextS).intValue();
/*     */     } else {
/*     */       
/* 187 */       this.minTrackLength = 10.0D;
/* 188 */       this.windowSizeClassification = 5;
/* 189 */       this.minSegmentLength = 5;
/*     */     } 
/* 191 */     this.resampleRate = 1;
/* 192 */     if (SPTBatch_.imps.getCalibration().getXUnit() == "pixel")
/* 193 */       this.pixelsize = (SPTBatch_.imps.getCalibration()).pixelWidth; 
/* 194 */     if (SPTBatch_.imps.getCalibration().getXUnit() != "pixel") {
/* 195 */       this.pixelsize = 0.0D;
/*     */     }
/* 197 */     timelag = SPTBatch_.fps;
/* 198 */     this.useReducedModelConfinedMotion = false;
/* 199 */     this.showID = true;
/* 200 */     this.showOverviewClasses = true;
/* 201 */     this.removeGlobalDrift = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     if (this.pixelsize > 1.0E-6D) {
/* 207 */       for (int k = 0; k < this.tracksToClassify.size(); k++) {
/* 208 */         ((TrajectoryModified)this.tracksToClassify.get(k)).scale(this.pixelsize);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     for (int j = 0; j < this.tracksToClassify.size(); j++) {
/* 219 */       TrajectoryModified t = this.tracksToClassify.get(j);
/* 220 */       int changesCounter = 0;
/* 221 */       for (int k = 1; k < t.size(); k++) {
/*     */         
/* 223 */         if (((Point3dModified)t.get(k)).distance((Point3dModified)t.get(k - 1)) > Math.pow(10.0D, -12.0D)) {
/* 224 */           changesCounter++;
/*     */         }
/*     */       } 
/* 227 */       if (1.0D * changesCounter / t.size() < 0.5D) {
/* 228 */         this.tracksToClassify.remove(j);
/* 229 */         this.ommittedTrajectories++;
/* 230 */         j--;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     HashMap<String, Color> mapTypeToColor = new HashMap<>();
/* 239 */     mapTypeToColor.put("DIRECTED/ACTIVE", Color.MAGENTA);
/* 240 */     mapTypeToColor.put("NORM. DIFFUSION", Color.RED);
/* 241 */     mapTypeToColor.put("CONFINED", Color.YELLOW);
/* 242 */     mapTypeToColor.put("SUBDIFFUSION", Color.GREEN);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 247 */     this.parentTrajectories = new ArrayList<>();
/* 248 */     for (TrajectoryModified track : this.tracksToClassify) {
/* 249 */       if (track.size() > this.minTrackLength) {
/* 250 */         this.parentTrajectories.add(track);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 257 */     StaticDriftCalculatorModified<TrajectoryModified> dcalc = new StaticDriftCalculatorModified();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     if (this.parentTrajectories.isEmpty() == Boolean.FALSE.booleanValue()) {
/*     */       
/* 269 */       this.classifiedSegments = classifyAndSegment(this.parentTrajectories, modelpath, this.windowSizeClassification, 
/* 270 */           this.minSegmentLength, 10, this.resampleRate);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 275 */       if (visualize) {
/*     */         
/* 277 */         Overlay ov = new Overlay();
/* 278 */         for (int n = 0; n < this.classifiedSegments.size(); n++) {
/* 279 */           SubtrajectoryModified tr = this.classifiedSegments.get(n);
/*     */           
/* 281 */           ArrayList<Roi> prois = null;
/* 282 */           if (this.pixelsize > 1.0E-6D) {
/* 283 */             prois = VisualizationUtilsModified.generateVisualizationRoisFromTrack(tr, 
/* 284 */                 mapTypeToColor.get(tr.getType()), this.showID, this.pixelsize);
/*     */           } else {
/*     */             
/* 287 */             prois = VisualizationUtilsModified.generateVisualizationRoisFromTrack(tr, 
/* 288 */                 mapTypeToColor.get(tr.getType()), this.showID, (IJ.getImage().getCalibration()).pixelWidth);
/*     */           } 
/* 290 */           for (Roi r : prois) {
/* 291 */             ov.add(r);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 296 */         if (this.showOverviewClasses) {
/* 297 */           Set<String> classes = mapTypeToColor.keySet();
/*     */           
/* 299 */           Iterator<String> it = classes.iterator();
/* 300 */           int y = 5;
/*     */           
/* 302 */           float fsize = 20.0F;
/* 303 */           AffineTransform affinetransform = new AffineTransform();
/* 304 */           FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
/* 305 */           int width = 
/* 306 */             (int)IJ.getImage().getProcessor().getFont().getStringBounds("Norm. Diffusion", frc).getWidth();
/* 307 */           Font f = IJ.getImage().getProcessor().getFont();
/* 308 */           while (1.0D * width / IJ.getImage().getWidth() > 0.08D) {
/* 309 */             fsize--;
/* 310 */             f = f.deriveFont(fsize);
/* 311 */             width = (int)f.getStringBounds("Norm. Diffusion", frc).getWidth();
/*     */           } 
/*     */           
/* 314 */           TextRoi.setFont("TimesRoman", (int)fsize, 0);
/* 315 */           while (it.hasNext()) {
/* 316 */             String type = it.next();
/* 317 */             TextRoi troi = new TextRoi(5, y, type);
/* 318 */             troi.setFillColor(Color.DARK_GRAY);
/* 319 */             troi.setStrokeColor(mapTypeToColor.get(type));
/* 320 */             ov.add((Roi)troi);
/* 321 */             y += 20;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 326 */         IJ.getImage().setOverlay(ov);
/* 327 */         IJ.getImage().updateAndRepaintWindow();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 334 */       HashMap<String, TraJResultsTable> rtables = new HashMap<>();
/* 335 */       rtables.put("DIRECTED/ACTIVE", new TraJResultsTable());
/* 336 */       rtables.put("NORM. DIFFUSION", new TraJResultsTable());
/* 337 */       rtables.put("SUBDIFFUSION", new TraJResultsTable());
/* 338 */       rtables.put("CONFINED", new TraJResultsTable());
/*     */       
/* 340 */       double sumConf = 0.0D;
/* 341 */       for (int k = 0; k < this.classifiedSegments.size(); k++) {
/* 342 */         RegressionDiffusionCoefficientEstimatorModified regressionDiffusionCoefficientEstimatorModified1; ActiveTransportParametersFeatureModified apf; RegressionDiffusionCoefficientEstimatorModified regressionDiffusionCoefficientEstimatorModified2; ConfinedDiffusionParametersFeatureModified confp; double[] p; PowerLawFeatureModified pwf; IJ.showProgress(k, this.classifiedSegments.size());
/* 343 */         SubtrajectoryModified t = this.classifiedSegments.get(k);
/* 344 */         TraJResultsTable rt = rtables.get(t.getType());
/*     */         
/* 346 */         if (rt == null) {
/* 347 */           SPTBatch_.taskOutput.append("Type: " + t.getType());
/* 348 */           ExportImportToolsModified eit = new ExportImportToolsModified();
/* 349 */           ArrayList<TrajectoryModified> hlp = new ArrayList<>();
/* 350 */           eit.exportTrajectoryDataAsCSV(hlp, String.valueOf(SPTBatch_.csvPath) + File.separator + "bad" + ".csv");
/* 351 */           SPTBatch_.taskOutput.append(t.toString());
/*     */         } 
/*     */         
/* 354 */         rt.incrementCounter();
/* 355 */         rt.addValue("PARENT-ID", t.getParent().getID());
/* 356 */         rt.addValue("ID", t.getID());
/* 357 */         rt.addValue("LENGTH", t.size());
/* 358 */         rt.addValue("START", t.getRelativeStartTimepoint());
/* 359 */         rt.addValue("END", (t.getRelativeStartTimepoint() + t.size() - 1));
/* 360 */         rt.addValue("CLASS", t.getType());
/*     */         
/* 362 */         AbstractTrajectoryFeatureModified dcEstim = null;
/* 363 */         double dc = 0.0D;
/* 364 */         DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
/* 365 */         NumberFormat formatter = new DecimalFormat("0.###E0", otherSymbols);
/*     */         
/* 367 */         double goodness = 0.0D;
/* 368 */         double alphaGoodness = 0.0D; String str;
/* 369 */         switch ((str = t.getType()).hashCode()) { case -1768066899: if (!str.equals("DIRECTED/ACTIVE")) {
/*     */               break;
/*     */             }
/* 372 */             apf = new ActiveTransportParametersFeatureModified((TrajectoryModified)t, 
/* 373 */                 timelag);
/* 374 */             res = apf.evaluate();
/* 375 */             rt.addValue("(FIT) D", formatter.format(res[0]));
/* 376 */             rt.addValue("(FIT) Velocity", res[1]);
/* 377 */             goodness = res[2];
/*     */             break;
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
/*     */           case -568951419:
/*     */             if (!str.equals("SUBDIFFUSION")) {
/*     */               break;
/*     */             }
/* 408 */             pwf = new PowerLawFeatureModified((TrajectoryModified)t, 1.0D / timelag, 1, t.size() / 3);
/* 409 */             res = pwf.evaluate();
/* 410 */             dc = res[1];
/*     */             
/* 412 */             rt.addValue("(FIT) D", formatter.format(dc));
/* 413 */             goodness = res[2]; break;
/*     */           case 160958711:
/*     */             if (!str.equals("NORM. DIFFUSION"))
/*     */               break;  regressionDiffusionCoefficientEstimatorModified1 = new RegressionDiffusionCoefficientEstimatorModified((TrajectoryModified)t, 1.0D / timelag, 1, t.size() / 3); res = regressionDiffusionCoefficientEstimatorModified1.evaluate(); dc = res[0]; rt.addValue("(FIT) D", formatter.format(dc)); goodness = res[3]; break;
/*     */           case 202494376:
/*     */             if (!str.equals("CONFINED"))
/* 419 */               break;  regressionDiffusionCoefficientEstimatorModified2 = new RegressionDiffusionCoefficientEstimatorModified((TrajectoryModified)t, 1.0D / timelag, 1, 3); confp = new ConfinedDiffusionParametersFeatureModified((TrajectoryModified)t, timelag, this.useReducedModelConfinedMotion, (AbstractDiffusionCoefficientEstimatorModified)regressionDiffusionCoefficientEstimatorModified2); p = confp.evaluate(); dc = p[1]; if (this.useReducedModelConfinedMotion) { rt.addValue("(FIT) CONF. RADIUS", Math.sqrt(p[0])); rt.addValue("(FIT) D", formatter.format(p[1])); goodness = p[2]; break; }  rt.addValue("(FIT) CONF. RADIUS", Math.sqrt(p[0])); rt.addValue("(FIT) A [CONF. SHAPE]", p[2]); rt.addValue("(FIT) B (CONF SHAPE)", p[3]); rt.addValue("(FIT) D", formatter.format(p[1])); goodness = p[4]; break; }  PowerLawFeatureModified powerLawFeatureModified1 = new PowerLawFeatureModified((TrajectoryModified)t, 1.0D / timelag, 1, t.size() / 3);
/* 420 */         double[] res = powerLawFeatureModified1.evaluate();
/* 421 */         double alpha = res[0];
/* 422 */         alphaGoodness = res[2];
/*     */         
/* 424 */         CenterOfGravityFeatureModified centerOfGravityFeatureModified = new CenterOfGravityFeatureModified((TrajectoryModified)t);
/* 425 */         double cog_x = centerOfGravityFeatureModified.evaluate()[0];
/* 426 */         double cog_y = centerOfGravityFeatureModified.evaluate()[1];
/* 427 */         rt.addValue("X (COG)", cog_x);
/* 428 */         rt.addValue("Y (COG)", cog_y);
/*     */         
/* 430 */         if (!t.getType().equals("NONE")) {
/* 431 */           FractalDimensionFeatureModified fdf = new FractalDimensionFeatureModified((TrajectoryModified)t);
/* 432 */           double v = fdf.evaluate()[0];
/*     */           
/* 434 */           rt.addValue("FRACT. DIM.", v);
/*     */           
/* 436 */           TrappedProbabilityFeatureModified trapped = new TrappedProbabilityFeatureModified((TrajectoryModified)t);
/* 437 */           v = trapped.evaluate()[0];
/* 438 */           rt.addValue("TRAPPEDNESS", v);
/*     */           
/* 440 */           EfficiencyFeatureModified eff = new EfficiencyFeatureModified((TrajectoryModified)t);
/* 441 */           v = eff.evaluate()[0];
/* 442 */           rt.addValue("EFFICENCY", v);
/*     */           
/* 444 */           StraightnessFeatureModified straight = new StraightnessFeatureModified((TrajectoryModified)t);
/* 445 */           v = straight.evaluate()[0];
/* 446 */           rt.addValue("STRAIGHTNESS", v);
/*     */           
/* 448 */           MeanSpeedFeatureModified msfeature = new MeanSpeedFeatureModified((TrajectoryModified)t, timelag);
/* 449 */           v = msfeature.evaluate()[1];
/* 450 */           rt.addValue("SPEED", v);
/*     */           
/* 452 */           KurtosisFeatureModified kurt = new KurtosisFeatureModified((TrajectoryModified)t);
/* 453 */           v = kurt.evaluate()[0];
/* 454 */           rt.addValue("KURTOSIS", v);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 459 */           rt.addValue("(FIT) ALPHA", alpha);
/*     */           
/* 461 */           GaussianityFeautureModified gauss = new GaussianityFeautureModified((TrajectoryModified)t, 1);
/* 462 */           v = gauss.evaluate()[0];
/* 463 */           rt.addValue("GAUSSIANITY", v);
/*     */           
/* 465 */           Asymmetry3FeatureModified asym3 = new Asymmetry3FeatureModified((TrajectoryModified)t);
/* 466 */           v = asym3.evaluate()[0];
/* 467 */           rt.addValue("Asymmetry", v);
/*     */           
/* 469 */           MSDRatioFeatureModified msdratio = new MSDRatioFeatureModified((TrajectoryModified)t, 1, 5);
/* 470 */           v = msdratio.evaluate()[0];
/* 471 */           rt.addValue("MSDRatio", v);
/*     */           
/* 473 */           CovarianceDiffusionCoefficientEstimatorModified cest = new CovarianceDiffusionCoefficientEstimatorModified(
/* 474 */               (TrajectoryModified)t, 1.0D / timelag);
/* 475 */           res = cest.evaluate();
/* 476 */           rt.addValue("Loc. noise_sigma", (res[1] + res[2]) / 2.0D);
/* 477 */           rt.addValue("Fit Goodness", goodness);
/* 478 */           rt.addValue("Alpha Fit Goodness", alphaGoodness);
/* 479 */           double conf = t.getConfidence();
/* 480 */           sumConf += conf;
/* 481 */           rt.addValue("Confidence", conf);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 491 */       Iterator<String> rtIt = rtables.keySet().iterator();
/*     */       
/* 493 */       TraJResultsTable traJResultsTable = new TraJResultsTable(true);
/*     */       
/* 495 */       for (int m = 0; m < this.parentTrajectories.size(); m++) {
/* 496 */         traJResultsTable.incrementCounter();
/* 497 */         TrajectoryModified t = this.parentTrajectories.get(m);
/* 498 */         traJResultsTable.addValue("ID", t.getID());
/* 499 */         traJResultsTable.addValue("LENGTH", t.size());
/* 500 */         traJResultsTable.addValue("START", t.getRelativeStartTimepoint());
/* 501 */         traJResultsTable.addValue("END", (t.getRelativeStartTimepoint() + t.size() - 1));
/* 502 */         int subPosCount = 0;
/* 503 */         int subSegCount = 0;
/* 504 */         int normPosCount = 0;
/* 505 */         int normSegCount = 0;
/* 506 */         int directedPosCount = 0;
/* 507 */         int directSegCount = 0;
/* 508 */         int confPosCount = 0;
/* 509 */         int confSegCount = 0;
/*     */         
/* 511 */         ArrayList<SubtrajectoryModified> sameParent = 
/* 512 */           SubtrajectoryModified.getTracksWithSameParant(this.classifiedSegments, t.getID());
/* 513 */         for (SubtrajectoryModified sub : sameParent)
/* 514 */         { String str; switch ((str = sub.getType()).hashCode()) { case -1768066899: if (!str.equals("DIRECTED/ACTIVE"))
/*     */                 continue; 
/* 516 */               directedPosCount += sub.size();
/* 517 */               directSegCount++;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case -568951419:
/*     */               if (!str.equals("SUBDIFFUSION")) {
/*     */                 continue;
/*     */               }
/* 528 */               subPosCount += sub.size();
/* 529 */               subSegCount++;
/*     */             case 160958711:
/*     */               if (!str.equals("NORM. DIFFUSION"))
/*     */                 continue;  normPosCount += sub.size(); normSegCount++;
/*     */             case 202494376:
/*     */               if (!str.equals("CONFINED"))
/* 535 */                 continue;  confPosCount += sub.size(); confSegCount++; }  }  traJResultsTable.addValue("#SEG_NORM", normSegCount);
/* 536 */         traJResultsTable.addValue("#POS_NORM", normPosCount);
/* 537 */         traJResultsTable.addValue("#SEG_SUB", subSegCount);
/* 538 */         traJResultsTable.addValue("#POS_SUB", subPosCount);
/* 539 */         traJResultsTable.addValue("#SEG_CONF", confSegCount);
/* 540 */         traJResultsTable.addValue("#POS_CONF", confPosCount);
/* 541 */         traJResultsTable.addValue("#SEG_DIRECTED", directSegCount);
/* 542 */         traJResultsTable.addValue("#POS_DIRECTED", directedPosCount);
/*     */       } 
/*     */       
/* 545 */       String trajVersion = TrajectoryModified.class.getPackage().getImplementationVersion();
/* 546 */       double[] drift = dcalc.calculateDrift(this.parentTrajectories);
/* 547 */       ResultsTable overall = new ResultsTable();
/* 548 */       overall.incrementCounter();
/* 549 */       overall.addValue("Mean confindence", sumConf / this.classifiedSegments.size());
/* 550 */       overall.addValue("Drift x", drift[0]);
/* 551 */       overall.addValue("Drift y", drift[1]);
/* 552 */       overall.addValue("Omitted segments", this.ommittedTrajectories);
/* 553 */       overall.addValue("Min. track length", this.minTrackLength);
/* 554 */       overall.addValue("Window size", (this.windowSizeClassification * 2));
/* 555 */       overall.addValue("Min. segment length", this.minSegmentLength);
/* 556 */       overall.addValue("Resamplerate", this.resampleRate);
/* 557 */       overall.addValue("Pixelsize", this.pixelsize);
/* 558 */       overall.addValue("Framerate", 1.0D / timelag);
/* 559 */       overall.addValue("Reduced conf. model", Boolean.toString(this.useReducedModelConfinedMotion));
/* 560 */       overall.addValue("Remove global drift", Boolean.toString(this.removeGlobalDrift));
/* 561 */       overall.addValue("TraJ library version", trajVersion);
/* 562 */       overall.save(SPTBatch_.directDiff + File.separator + "Settings & Miscellaneous" + 
/* 563 */           SPTBatch_.imps.getShortTitle() + ".xls");
/*     */ 
/*     */       
/* 566 */       traJResultsTable.save(SPTBatch_.directDiff + File.separator + "Parents_" + SPTBatch_.imps.getShortTitle() + ".xls");
/*     */       
/* 568 */       rtIt = rtables.keySet().iterator();
/* 569 */       while (rtIt.hasNext()) {
/* 570 */         String rt = rtIt.next();
/* 571 */         if (rt.equals("DIRECTED/ACTIVE") == Boolean.TRUE.booleanValue()) {
/* 572 */           ((TraJResultsTable)rtables.get(rt)).save(SPTBatch_.directDiff + File.separator + "DIRECTED_ACTIVE" + "_trajectories_" + 
/* 573 */               SPTBatch_.imps.getShortTitle() + ".xls"); continue;
/*     */         } 
/* 575 */         ((TraJResultsTable)rtables.get(rt)).save(SPTBatch_.directDiff + File.separator + rt + "_trajectories_" + 
/* 576 */             SPTBatch_.imps.getShortTitle() + ".xls");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<SubtrajectoryModified> classifyAndSegment(TrajectoryModified trackToClassify, String modelpath, int windowSizeClassification, int minSegmentLength, int modeFilterLength, int resampleRate) {
/* 586 */     ArrayList<TrajectoryModified> help = new ArrayList<>();
/* 587 */     help.add(trackToClassify);
/* 588 */     return classifyAndSegment(help, modelpath, windowSizeClassification, minSegmentLength, modeFilterLength, 
/* 589 */         resampleRate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<SubtrajectoryModified> classifyAndSegment(ArrayList<TrajectoryModified> tracksToClassify, String modelpath, int windowSizeClassification, int minSegmentLength, int modeFilterLength, int resampleRate) {
/* 595 */     ArrayList<SubtrajectoryModified> classified = new ArrayList<>();
/* 596 */     int j = 0;
/* 597 */     RRFClassifierRenjinModified rrf = new RRFClassifierRenjinModified(modelpath, resampleRate * timelag);
/* 598 */     rrf.start();
/*     */     
/* 600 */     WeightedWindowedClassificationProcessModified wcp = new WeightedWindowedClassificationProcessModified();
/* 601 */     int subidcounter = 1;
/* 602 */     for (TrajectoryModified track : tracksToClassify) {
/* 603 */       j++;
/* 604 */       IJ.showProgress(j, tracksToClassify.size());
/* 605 */       TrajectoryModified mTrack = track;
/*     */       
/* 607 */       String[] classes = wcp.windowedClassification(mTrack, (AbstractClassifierModified)rrf, windowSizeClassification, resampleRate);
/*     */       
/* 609 */       double[] classConfidence = wcp.getPositionConfidence();
/*     */       
/* 611 */       classes = movingMode(classes, modeFilterLength);
/* 612 */       double sumConf = 0.0D;
/* 613 */       int Nconf = 0;
/* 614 */       SubtrajectoryModified tr = new SubtrajectoryModified(track, 2);
/*     */       
/* 616 */       tr.setID(subidcounter);
/* 617 */       subidcounter++;
/* 618 */       tr.add(((Point3dModified)track.get(0)).x, ((Point3dModified)track.get(0)).y, 0.0D);
/* 619 */       sumConf += classConfidence[0];
/* 620 */       Nconf++;
/* 621 */       String prevCls = classes[0];
/* 622 */       int start = track.getRelativeStartTimepoint();
/* 623 */       tr.setRelativStartTimepoint(start);
/* 624 */       tr.setType(prevCls);
/*     */       
/* 626 */       for (int k = 1; k < classes.length; k++) {
/* 627 */         if (prevCls == classes[k]) {
/* 628 */           tr.add(((Point3dModified)track.get(k)).x, ((Point3dModified)track.get(k)).y, 0.0D);
/* 629 */           sumConf += classConfidence[k];
/* 630 */           Nconf++;
/*     */         } else {
/*     */           
/* 633 */           tr.setConfidence(sumConf / Nconf);
/* 634 */           classified.add(tr);
/* 635 */           tr = new SubtrajectoryModified(track, 2);
/* 636 */           tr.setID(subidcounter);
/* 637 */           subidcounter++;
/* 638 */           tr.setRelativStartTimepoint(start + k);
/* 639 */           tr.add(((Point3dModified)track.get(k)).x, ((Point3dModified)track.get(k)).y, 0.0D);
/* 640 */           sumConf = classConfidence[k];
/* 641 */           Nconf = 1;
/* 642 */           prevCls = classes[k];
/* 643 */           tr.setType(prevCls);
/*     */         } 
/*     */       } 
/* 646 */       tr.setConfidence(sumConf / Nconf);
/* 647 */       classified.add(tr);
/* 648 */       sumConf = 0.0D;
/* 649 */       Nconf = 0;
/*     */     } 
/*     */     
/* 652 */     rrf.stop();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 659 */     for (int i = 0; i < classified.size(); i++) {
/* 660 */       if (((SubtrajectoryModified)classified.get(i)).size() < minSegmentLength) {
/* 661 */         classified.remove(i);
/* 662 */         i--;
/*     */       } 
/*     */     } 
/* 665 */     return classified;
/*     */   }
/*     */   
/*     */   public double getTimelag() {
/* 669 */     return timelag;
/*     */   }
/*     */   
/*     */   public static String[] movingMode(String[] types, int n) {
/* 673 */     ArrayList<String> ltypes = new ArrayList<>();
/* 674 */     for (int i = 0; i < types.length; i++) {
/* 675 */       ltypes.add(types[i]);
/*     */     }
/* 677 */     return movingMode(ltypes, n);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] movingMode(ArrayList<String> types, int n) {
/* 682 */     int windowsize = 2 * n + 1;
/* 683 */     HashSet<String> uniqueTypes = new HashSet<>();
/* 684 */     uniqueTypes.addAll(types);
/* 685 */     HashMap<String, Integer> mapTypeToInt = new HashMap<>();
/* 686 */     HashMap<Integer, String> mapIntToType = new HashMap<>();
/* 687 */     Iterator<String> it = uniqueTypes.iterator();
/* 688 */     int key = 0;
/* 689 */     while (it.hasNext()) {
/* 690 */       String type = it.next();
/* 691 */       mapTypeToInt.put(type, Integer.valueOf(key));
/* 692 */       mapIntToType.put(Integer.valueOf(key), type);
/* 693 */       key++;
/*     */     } 
/*     */     
/* 696 */     String[] medTypes = new String[types.size()];
/*     */     int i;
/* 698 */     for (i = 0; i < n; i++) {
/* 699 */       medTypes[i] = types.get(i);
/*     */     }
/* 701 */     for (i = types.size() - n; i < types.size(); i++) {
/* 702 */       medTypes[i] = types.get(i);
/*     */     }
/*     */     
/* 705 */     for (i = 0; i < types.size() - windowsize + 1; i++) {
/* 706 */       List<String> sub = types.subList(i, i + windowsize - 1);
/* 707 */       double[] values = new double[sub.size()];
/* 708 */       for (int j = 0; j < sub.size(); j++) {
/* 709 */         values[j] = ((Integer)mapTypeToInt.get(sub.get(j))).intValue();
/*     */       }
/*     */       
/* 712 */       medTypes[i + n] = mapIntToType.get(Integer.valueOf((int)StatUtils.mode(values)[0]));
/*     */     } 
/* 714 */     return medTypes;
/*     */   }
/*     */   
/*     */   public ArrayList<SubtrajectoryModified> getClassifiedTrajectories() {
/* 718 */     return this.classifiedSegments;
/*     */   }
/*     */   
/*     */   public ArrayList<TrajectoryModified> getParentTrajectories() {
/* 722 */     return this.parentTrajectories;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String ExportResource(String resourceName) throws Exception {
/*     */     String tmpFolder;
/* 733 */     InputStream stream = null;
/* 734 */     OutputStream resStreamOut = null;
/*     */     
/*     */     try {
/* 737 */       stream = getClass().getResourceAsStream(resourceName);
/*     */       
/* 739 */       if (stream == null) {
/* 740 */         IJ.error("Cannot get resource \"" + resourceName + "\" from Jar file.");
/* 741 */         throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
/*     */       } 
/*     */ 
/*     */       
/* 745 */       byte[] buffer = new byte[4096];
/* 746 */       File folderDir = new File(String.valueOf(IJ.getDirectory("temp")) + "/.trajclassifier");
/*     */ 
/*     */       
/* 749 */       if (!folderDir.exists()) {
/* 750 */         folderDir.mkdir();
/*     */       }
/* 752 */       tmpFolder = folderDir.getPath().replace('\\', '/');
/* 753 */       resStreamOut = new FileOutputStream(String.valueOf(tmpFolder) + resourceName); int readBytes;
/* 754 */       while ((readBytes = stream.read(buffer)) > 0) {
/* 755 */         resStreamOut.write(buffer, 0, readBytes);
/*     */       }
/* 757 */     } catch (Exception ex) {
/* 758 */       Exception exception1; IJ.error(exception1.getMessage());
/* 759 */       throw exception1;
/*     */     } finally {
/* 761 */       stream.close();
/* 762 */       resStreamOut.close();
/* 763 */     }  return 
/*     */       
/* 765 */       String.valueOf(tmpFolder) + resourceName;
/*     */   }
/*     */   
/*     */   public void setTracksToClassify(ArrayList<TrajectoryModified> t) {
/* 769 */     this.tracksToClassify = t;
/*     */   }
/*     */   
/*     */   public double getMinTrackLength() {
/* 773 */     return this.minTrackLength;
/*     */   }
/*     */   
/*     */   public void setMinTrackLength(double minTrackLength) {
/* 777 */     this.minTrackLength = minTrackLength;
/*     */   }
/*     */   
/*     */   public double getPixelsize() {
/* 781 */     return this.pixelsize;
/*     */   }
/*     */   
/*     */   public void setPixelsize(double pixelsize) {
/* 785 */     this.pixelsize = pixelsize;
/*     */   }
/*     */   
/*     */   public boolean isShowID() {
/* 789 */     return this.showID;
/*     */   }
/*     */   
/*     */   public void setShowID(boolean showID) {
/* 793 */     this.showID = showID;
/*     */   }
/*     */   
/*     */   public int getWindowSizeClassification() {
/* 797 */     return this.windowSizeClassification;
/*     */   }
/*     */   
/*     */   public boolean isUseReducedModelConfinedMotion() {
/* 801 */     return this.useReducedModelConfinedMotion;
/*     */   }
/*     */   
/*     */   public void setTimelag(double timelag) {
/* 805 */     TraJClassifierTest_.timelag = timelag;
/*     */   }
/*     */   
/*     */   public void setWindowSizeClassification(int windowSizeClassification) {
/* 809 */     this.windowSizeClassification = windowSizeClassification;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/TraJClassifierTest_.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */