/*     */ import DiffusionCoefficientEstimator.AbstractDiffusionCoefficientEstimatorModified;
/*     */ import DiffusionCoefficientEstimator.CovarianceDiffusionCoefficientEstimatorModified;
/*     */ import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
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
/*     */   String type;
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
/* 334 */       HashMap<String, ResultsTable> rtables = new HashMap<>();
/* 335 */       rtables.put("DIRECTED/ACTIVE", new ResultsTable());
/* 336 */       rtables.put("NORM. DIFFUSION", new ResultsTable());
/* 337 */       rtables.put("SUBDIFFUSION", new ResultsTable());
/* 338 */       rtables.put("CONFINED", new ResultsTable());
/*     */       
/* 340 */       double sumConf = 0.0D;
/* 341 */       for (int k = 0; k < this.classifiedSegments.size(); k++) {
/*     */         
/* 343 */         IJ.showProgress(k, this.classifiedSegments.size());
/* 344 */         SubtrajectoryModified t = this.classifiedSegments.get(k);
/* 345 */         this.type = t.getType();
/* 346 */         ResultsTable rtT = rtables.get(this.type);
/*     */         
/* 348 */         if (rtT == null) {
/* 349 */           SPTBatch_.taskOutput.append("Type: " + this.type);
/* 350 */           ExportImportToolsModified eit = new ExportImportToolsModified();
/* 351 */           ArrayList<TrajectoryModified> hlp = new ArrayList<>();
/* 352 */           eit.exportTrajectoryDataAsCSV(hlp, String.valueOf(SPTBatch_.csvPath) + File.separator + "bad" + ".csv");
/* 353 */           SPTBatch_.taskOutput.append(t.toString());
/*     */         } 
/*     */         
/* 356 */         ResultsTable rt = ResultsTable.getResultsTable();
/* 357 */         if (this.type != null) {
/* 358 */           rt.incrementCounter();
/* 359 */           rt.addValue("PARENT-ID", t.getParent().getID());
/* 360 */           rt.addValue("ID", t.getID());
/* 361 */           rt.addValue("LENGTH", t.size());
/* 362 */           rt.addValue("START", t.getRelativeStartTimepoint());
/* 363 */           rt.addValue("END", (t.getRelativeStartTimepoint() + t.size() - 1));
/* 364 */           rt.addValue("CLASS", this.type);
/*     */         } 
/* 366 */         AbstractTrajectoryFeatureModified dcEstim = null;
/* 367 */         double dc = 0.0D;
/* 368 */         DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
/* 369 */         NumberFormat formatter = new DecimalFormat("0.###E0", otherSymbols);
/*     */         
/* 371 */         double goodness = 0.0D;
/* 372 */         double alphaGoodness = 0.0D;
/*     */         
/* 374 */         if (this.type != null) {
/* 375 */           RegressionDiffusionCoefficientEstimatorModified regressionDiffusionCoefficientEstimatorModified1; double[] arrayOfDouble1; ActiveTransportParametersFeatureModified apf; RegressionDiffusionCoefficientEstimatorModified regressionDiffusionCoefficientEstimatorModified2; ConfinedDiffusionParametersFeatureModified confp; double[] p; PowerLawFeatureModified pwf; String str; switch ((str = this.type).hashCode()) { case -1768066899: if (!str.equals("DIRECTED/ACTIVE")) {
/*     */                 break;
/*     */               }
/* 378 */               apf = new ActiveTransportParametersFeatureModified((TrajectoryModified)t, 
/* 379 */                   timelag);
/* 380 */               arrayOfDouble1 = apf.evaluate();
/* 381 */               rt.addValue("(FIT) D", formatter.format(arrayOfDouble1[0]));
/* 382 */               rt.addValue("(FIT) Velocity", arrayOfDouble1[1]);
/* 383 */               goodness = arrayOfDouble1[2];
/*     */               break;
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
/*     */             case -568951419:
/*     */               if (!str.equals("SUBDIFFUSION")) {
/*     */                 break;
/*     */               }
/* 416 */               pwf = new PowerLawFeatureModified((TrajectoryModified)t, 1.0D / timelag, 1, t.size() / 3);
/* 417 */               arrayOfDouble1 = pwf.evaluate();
/* 418 */               dc = arrayOfDouble1[1];
/*     */               
/* 420 */               rt.addValue("(FIT) D", formatter.format(dc));
/* 421 */               goodness = arrayOfDouble1[2]; break;
/*     */             case 160958711:
/*     */               if (!str.equals("NORM. DIFFUSION"))
/*     */                 break;  regressionDiffusionCoefficientEstimatorModified1 = new RegressionDiffusionCoefficientEstimatorModified((TrajectoryModified)t, 1.0D / timelag, 1, t.size() / 3); arrayOfDouble1 = regressionDiffusionCoefficientEstimatorModified1.evaluate(); dc = arrayOfDouble1[0]; rt.addValue("(FIT) D", formatter.format(dc)); goodness = arrayOfDouble1[3]; break;
/*     */             case 202494376:
/*     */               if (!str.equals("CONFINED"))
/*     */                 break;  regressionDiffusionCoefficientEstimatorModified2 = new RegressionDiffusionCoefficientEstimatorModified((TrajectoryModified)t, 1.0D / timelag, 1, 3); confp = new ConfinedDiffusionParametersFeatureModified((TrajectoryModified)t, timelag, this.useReducedModelConfinedMotion, (AbstractDiffusionCoefficientEstimatorModified)regressionDiffusionCoefficientEstimatorModified2); p = confp.evaluate(); dc = p[1]; if (this.useReducedModelConfinedMotion) { rt.addValue("(FIT) CONF. RADIUS", Math.sqrt(p[0])); rt.addValue("(FIT) D", formatter.format(p[1])); goodness = p[2]; break; }  rt.addValue("(FIT) CONF. RADIUS", Math.sqrt(p[0])); rt.addValue("(FIT) A [CONF. SHAPE]", p[2]); rt.addValue("(FIT) B (CONF SHAPE)", p[3]); rt.addValue("(FIT) D", formatter.format(p[1])); goodness = p[4]; break; } 
/* 428 */         }  PowerLawFeatureModified powerLawFeatureModified = new PowerLawFeatureModified((TrajectoryModified)t, 1.0D / timelag, 1, t.size() / 3);
/* 429 */         double[] res = powerLawFeatureModified.evaluate();
/* 430 */         double alpha = res[0];
/* 431 */         alphaGoodness = res[2];
/*     */         
/* 433 */         CenterOfGravityFeatureModified centerOfGravityFeatureModified = new CenterOfGravityFeatureModified((TrajectoryModified)t);
/* 434 */         double cog_x = centerOfGravityFeatureModified.evaluate()[0];
/* 435 */         double cog_y = centerOfGravityFeatureModified.evaluate()[1];
/*     */         
/* 437 */         rt.addValue("X (COG)", cog_x);
/* 438 */         rt.addValue("Y (COG)", cog_y);
/* 439 */         if (t.getType() != null && 
/* 440 */           !t.getType().equals("NONE")) {
/*     */           
/* 442 */           FractalDimensionFeatureModified fdf = new FractalDimensionFeatureModified((TrajectoryModified)t);
/* 443 */           double v = fdf.evaluate()[0];
/*     */           
/* 445 */           rt.addValue("FRACT. DIM.", v);
/*     */           
/* 447 */           TrappedProbabilityFeatureModified trapped = new TrappedProbabilityFeatureModified((TrajectoryModified)t);
/* 448 */           v = trapped.evaluate()[0];
/* 449 */           rt.addValue("TRAPPEDNESS", v);
/*     */           
/* 451 */           EfficiencyFeatureModified eff = new EfficiencyFeatureModified((TrajectoryModified)t);
/* 452 */           v = eff.evaluate()[0];
/* 453 */           rt.addValue("EFFICENCY", v);
/*     */           
/* 455 */           StraightnessFeatureModified straight = new StraightnessFeatureModified((TrajectoryModified)t);
/* 456 */           v = straight.evaluate()[0];
/* 457 */           rt.addValue("STRAIGHTNESS", v);
/*     */           
/* 459 */           MeanSpeedFeatureModified msfeature = new MeanSpeedFeatureModified((TrajectoryModified)t, timelag);
/* 460 */           v = msfeature.evaluate()[1];
/* 461 */           rt.addValue("SPEED", v);
/*     */           
/* 463 */           KurtosisFeatureModified kurt = new KurtosisFeatureModified((TrajectoryModified)t);
/* 464 */           v = kurt.evaluate()[0];
/* 465 */           rt.addValue("KURTOSIS", v);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 470 */           rt.addValue("(FIT) ALPHA", alpha);
/*     */           
/* 472 */           GaussianityFeautureModified gauss = new GaussianityFeautureModified((TrajectoryModified)t, 1);
/* 473 */           v = gauss.evaluate()[0];
/* 474 */           rt.addValue("GAUSSIANITY", v);
/*     */           
/* 476 */           Asymmetry3FeatureModified asym3 = new Asymmetry3FeatureModified((TrajectoryModified)t);
/* 477 */           v = asym3.evaluate()[0];
/* 478 */           rt.addValue("Asymmetry", v);
/*     */           
/* 480 */           MSDRatioFeatureModified msdratio = new MSDRatioFeatureModified((TrajectoryModified)t, 1, 5);
/* 481 */           v = msdratio.evaluate()[0];
/* 482 */           rt.addValue("MSDRatio", v);
/*     */           
/* 484 */           CovarianceDiffusionCoefficientEstimatorModified cest = new CovarianceDiffusionCoefficientEstimatorModified(
/* 485 */               (TrajectoryModified)t, 1.0D / timelag);
/* 486 */           res = cest.evaluate();
/* 487 */           rt.addValue("Loc. noise_sigma", (res[1] + res[2]) / 2.0D);
/* 488 */           rt.addValue("Fit Goodness", goodness);
/* 489 */           rt.addValue("Alpha Fit Goodness", alphaGoodness);
/* 490 */           double conf = t.getConfidence();
/* 491 */           sumConf += conf;
/* 492 */           rt.addValue("Confidence", conf);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 503 */       Iterator<String> rtIt = rtables.keySet().iterator();
/*     */       
/* 505 */       ResultsTable parents = new ResultsTable();
/*     */       
/* 507 */       for (int m = 0; m < this.parentTrajectories.size(); m++) {
/* 508 */         parents.incrementCounter();
/* 509 */         TrajectoryModified t = this.parentTrajectories.get(m);
/* 510 */         parents.addValue("ID", t.getID());
/* 511 */         parents.addValue("LENGTH", t.size());
/* 512 */         parents.addValue("START", t.getRelativeStartTimepoint());
/* 513 */         parents.addValue("END", (t.getRelativeStartTimepoint() + t.size() - 1));
/* 514 */         int subPosCount = 0;
/* 515 */         int subSegCount = 0;
/* 516 */         int normPosCount = 0;
/* 517 */         int normSegCount = 0;
/* 518 */         int directedPosCount = 0;
/* 519 */         int directSegCount = 0;
/* 520 */         int confPosCount = 0;
/* 521 */         int confSegCount = 0;
/*     */         
/* 523 */         ArrayList<SubtrajectoryModified> sameParent = 
/* 524 */           SubtrajectoryModified.getTracksWithSameParant(this.classifiedSegments, t.getID());
/* 525 */         for (SubtrajectoryModified sub : sameParent) {
/* 526 */           if (sub.getType() != null)
/* 527 */           { String str; switch ((str = sub.getType()).hashCode()) { case -1768066899: if (!str.equals("DIRECTED/ACTIVE"))
/*     */                   continue; 
/* 529 */                 directedPosCount += sub.size();
/* 530 */                 directSegCount++;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               case -568951419:
/*     */                 if (!str.equals("SUBDIFFUSION")) {
/*     */                   continue;
/*     */                 }
/* 541 */                 subPosCount += sub.size();
/* 542 */                 subSegCount++;
/*     */               case 160958711:
/*     */                 if (!str.equals("NORM. DIFFUSION"))
/*     */                   continue;  normPosCount += sub.size(); normSegCount++;
/*     */               case 202494376:
/*     */                 if (!str.equals("CONFINED"))
/*     */                   continue;  confPosCount += sub.size(); confSegCount++; }  } 
/* 549 */         }  parents.addValue("#SEG_NORM", normSegCount);
/* 550 */         parents.addValue("#POS_NORM", normPosCount);
/* 551 */         parents.addValue("#SEG_SUB", subSegCount);
/* 552 */         parents.addValue("#POS_SUB", subPosCount);
/* 553 */         parents.addValue("#SEG_CONF", confSegCount);
/* 554 */         parents.addValue("#POS_CONF", confPosCount);
/* 555 */         parents.addValue("#SEG_DIRECTED", directSegCount);
/* 556 */         parents.addValue("#POS_DIRECTED", directedPosCount);
/*     */       } 
/*     */       
/* 559 */       String trajVersion = TrajectoryModified.class.getPackage().getImplementationVersion();
/* 560 */       double[] drift = dcalc.calculateDrift(this.parentTrajectories);
/* 561 */       ResultsTable overall = new ResultsTable();
/* 562 */       overall.incrementCounter();
/* 563 */       overall.addValue("Mean confindence", sumConf / this.classifiedSegments.size());
/* 564 */       overall.addValue("Drift x", drift[0]);
/* 565 */       overall.addValue("Drift y", drift[1]);
/* 566 */       overall.addValue("Omitted segments", this.ommittedTrajectories);
/* 567 */       overall.addValue("Min. track length", this.minTrackLength);
/* 568 */       overall.addValue("Window size", (this.windowSizeClassification * 2));
/* 569 */       overall.addValue("Min. segment length", this.minSegmentLength);
/* 570 */       overall.addValue("Resamplerate", this.resampleRate);
/* 571 */       overall.addValue("Pixelsize", this.pixelsize);
/* 572 */       overall.addValue("Framerate", 1.0D / timelag);
/* 573 */       overall.addValue("Reduced conf. model", Boolean.toString(this.useReducedModelConfinedMotion));
/* 574 */       overall.addValue("Remove global drift", Boolean.toString(this.removeGlobalDrift));
/* 575 */       overall.addValue("TraJ library version", trajVersion);
/* 576 */       overall.save(SPTBatch_.directDiff + File.separator + "Settings & Miscellaneous" + 
/* 577 */           SPTBatch_.imps.getShortTitle() + ".xls");
/*     */ 
/*     */       
/* 580 */       parents.save(SPTBatch_.directDiff + File.separator + "Parents_" + SPTBatch_.imps.getShortTitle() + ".xls");
/*     */       
/* 582 */       rtIt = rtables.keySet().iterator();
/* 583 */       while (rtIt.hasNext()) {
/* 584 */         String rt = rtIt.next();
/* 585 */         if (rt.equals("DIRECTED/ACTIVE") == Boolean.TRUE.booleanValue()) {
/* 586 */           ((ResultsTable)rtables.get(rt)).save(SPTBatch_.directDiff + File.separator + "DIRECTED_ACTIVE" + "_trajectories_" + 
/* 587 */               SPTBatch_.imps.getShortTitle() + ".xls"); continue;
/*     */         } 
/* 589 */         ((ResultsTable)rtables.get(rt)).save(SPTBatch_.directDiff + File.separator + rt + "_trajectories_" + 
/* 590 */             SPTBatch_.imps.getShortTitle() + ".xls");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<SubtrajectoryModified> classifyAndSegment(TrajectoryModified trackToClassify, String modelpath, int windowSizeClassification, int minSegmentLength, int modeFilterLength, int resampleRate) {
/* 600 */     ArrayList<TrajectoryModified> help = new ArrayList<>();
/* 601 */     help.add(trackToClassify);
/* 602 */     return classifyAndSegment(help, modelpath, windowSizeClassification, minSegmentLength, modeFilterLength, 
/* 603 */         resampleRate);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<SubtrajectoryModified> classifyAndSegment(ArrayList<TrajectoryModified> tracksToClassify, String modelpath, int windowSizeClassification, int minSegmentLength, int modeFilterLength, int resampleRate) {
/* 609 */     ArrayList<SubtrajectoryModified> classified = new ArrayList<>();
/* 610 */     int j = 0;
/* 611 */     RRFClassifierRenjinModified rrf = new RRFClassifierRenjinModified(modelpath, resampleRate * timelag);
/* 612 */     rrf.start();
/*     */     
/* 614 */     WeightedWindowedClassificationProcessModified wcp = new WeightedWindowedClassificationProcessModified();
/* 615 */     int subidcounter = 1;
/* 616 */     for (TrajectoryModified track : tracksToClassify) {
/* 617 */       j++;
/* 618 */       IJ.showProgress(j, tracksToClassify.size());
/* 619 */       TrajectoryModified mTrack = track;
/*     */       
/* 621 */       String[] classes = wcp.windowedClassification(mTrack, (AbstractClassifierModified)rrf, windowSizeClassification, resampleRate);
/*     */       
/* 623 */       double[] classConfidence = wcp.getPositionConfidence();
/*     */       
/* 625 */       classes = movingMode(classes, modeFilterLength);
/* 626 */       double sumConf = 0.0D;
/* 627 */       int Nconf = 0;
/* 628 */       SubtrajectoryModified tr = new SubtrajectoryModified(track, 2);
/*     */       
/* 630 */       tr.setID(subidcounter);
/* 631 */       subidcounter++;
/* 632 */       tr.add(((Point3dModified)track.get(0)).x, ((Point3dModified)track.get(0)).y, 0.0D);
/* 633 */       sumConf += classConfidence[0];
/* 634 */       Nconf++;
/* 635 */       String prevCls = classes[0];
/* 636 */       int start = track.getRelativeStartTimepoint();
/* 637 */       tr.setRelativStartTimepoint(start);
/* 638 */       tr.setType(prevCls);
/*     */       
/* 640 */       for (int k = 1; k < classes.length; k++) {
/* 641 */         if (prevCls == classes[k]) {
/* 642 */           tr.add(((Point3dModified)track.get(k)).x, ((Point3dModified)track.get(k)).y, 0.0D);
/* 643 */           sumConf += classConfidence[k];
/* 644 */           Nconf++;
/*     */         } else {
/*     */           
/* 647 */           tr.setConfidence(sumConf / Nconf);
/* 648 */           classified.add(tr);
/* 649 */           tr = new SubtrajectoryModified(track, 2);
/* 650 */           tr.setID(subidcounter);
/* 651 */           subidcounter++;
/* 652 */           tr.setRelativStartTimepoint(start + k);
/* 653 */           tr.add(((Point3dModified)track.get(k)).x, ((Point3dModified)track.get(k)).y, 0.0D);
/* 654 */           sumConf = classConfidence[k];
/* 655 */           Nconf = 1;
/* 656 */           prevCls = classes[k];
/* 657 */           tr.setType(prevCls);
/*     */         } 
/*     */       } 
/* 660 */       tr.setConfidence(sumConf / Nconf);
/* 661 */       classified.add(tr);
/* 662 */       sumConf = 0.0D;
/* 663 */       Nconf = 0;
/*     */     } 
/*     */     
/* 666 */     rrf.stop();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 673 */     for (int i = 0; i < classified.size(); i++) {
/* 674 */       if (((SubtrajectoryModified)classified.get(i)).size() < minSegmentLength) {
/* 675 */         classified.remove(i);
/* 676 */         i--;
/*     */       } 
/*     */     } 
/* 679 */     return classified;
/*     */   }
/*     */   
/*     */   public double getTimelag() {
/* 683 */     return timelag;
/*     */   }
/*     */   
/*     */   public static String[] movingMode(String[] types, int n) {
/* 687 */     ArrayList<String> ltypes = new ArrayList<>();
/* 688 */     for (int i = 0; i < types.length; i++) {
/* 689 */       ltypes.add(types[i]);
/*     */     }
/* 691 */     return movingMode(ltypes, n);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] movingMode(ArrayList<String> types, int n) {
/* 696 */     int windowsize = 2 * n + 1;
/* 697 */     HashSet<String> uniqueTypes = new HashSet<>();
/* 698 */     uniqueTypes.addAll(types);
/* 699 */     HashMap<String, Integer> mapTypeToInt = new HashMap<>();
/* 700 */     HashMap<Integer, String> mapIntToType = new HashMap<>();
/* 701 */     Iterator<String> it = uniqueTypes.iterator();
/* 702 */     int key = 0;
/* 703 */     while (it.hasNext()) {
/* 704 */       String type = it.next();
/* 705 */       mapTypeToInt.put(type, Integer.valueOf(key));
/* 706 */       mapIntToType.put(Integer.valueOf(key), type);
/* 707 */       key++;
/*     */     } 
/*     */     
/* 710 */     String[] medTypes = new String[types.size()];
/*     */     int i;
/* 712 */     for (i = 0; i < n; i++) {
/* 713 */       medTypes[i] = types.get(i);
/*     */     }
/* 715 */     for (i = types.size() - n; i < types.size(); i++) {
/* 716 */       medTypes[i] = types.get(i);
/*     */     }
/*     */     
/* 719 */     for (i = 0; i < types.size() - windowsize + 1; i++) {
/* 720 */       List<String> sub = types.subList(i, i + windowsize - 1);
/* 721 */       double[] values = new double[sub.size()];
/* 722 */       for (int j = 0; j < sub.size(); j++) {
/* 723 */         values[j] = ((Integer)mapTypeToInt.get(sub.get(j))).intValue();
/*     */       }
/*     */       
/* 726 */       medTypes[i + n] = mapIntToType.get(Integer.valueOf((int)StatUtils.mode(values)[0]));
/*     */     } 
/* 728 */     return medTypes;
/*     */   }
/*     */   
/*     */   public ArrayList<SubtrajectoryModified> getClassifiedTrajectories() {
/* 732 */     return this.classifiedSegments;
/*     */   }
/*     */   
/*     */   public ArrayList<TrajectoryModified> getParentTrajectories() {
/* 736 */     return this.parentTrajectories;
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
/* 747 */     InputStream stream = null;
/* 748 */     OutputStream resStreamOut = null;
/*     */     
/*     */     try {
/* 751 */       stream = getClass().getResourceAsStream(resourceName);
/*     */       
/* 753 */       if (stream == null) {
/* 754 */         IJ.error("Cannot get resource \"" + resourceName + "\" from Jar file.");
/* 755 */         throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
/*     */       } 
/*     */ 
/*     */       
/* 759 */       byte[] buffer = new byte[4096];
/* 760 */       File folderDir = new File(String.valueOf(IJ.getDirectory("temp")) + "/.trajclassifier");
/*     */ 
/*     */       
/* 763 */       if (!folderDir.exists()) {
/* 764 */         folderDir.mkdir();
/*     */       }
/* 766 */       tmpFolder = folderDir.getPath().replace('\\', '/');
/* 767 */       resStreamOut = new FileOutputStream(String.valueOf(tmpFolder) + resourceName); int readBytes;
/* 768 */       while ((readBytes = stream.read(buffer)) > 0) {
/* 769 */         resStreamOut.write(buffer, 0, readBytes);
/*     */       }
/* 771 */     } catch (Exception ex) {
/* 772 */       Exception exception1; IJ.error(exception1.getMessage());
/* 773 */       throw exception1;
/*     */     } finally {
/* 775 */       stream.close();
/* 776 */       resStreamOut.close();
/* 777 */     }  return 
/*     */       
/* 779 */       String.valueOf(tmpFolder) + resourceName;
/*     */   }
/*     */   
/*     */   public void setTracksToClassify(ArrayList<TrajectoryModified> t) {
/* 783 */     this.tracksToClassify = t;
/*     */   }
/*     */   
/*     */   public double getMinTrackLength() {
/* 787 */     return this.minTrackLength;
/*     */   }
/*     */   
/*     */   public void setMinTrackLength(double minTrackLength) {
/* 791 */     this.minTrackLength = minTrackLength;
/*     */   }
/*     */   
/*     */   public double getPixelsize() {
/* 795 */     return this.pixelsize;
/*     */   }
/*     */   
/*     */   public void setPixelsize(double pixelsize) {
/* 799 */     this.pixelsize = pixelsize;
/*     */   }
/*     */   
/*     */   public boolean isShowID() {
/* 803 */     return this.showID;
/*     */   }
/*     */   
/*     */   public void setShowID(boolean showID) {
/* 807 */     this.showID = showID;
/*     */   }
/*     */   
/*     */   public int getWindowSizeClassification() {
/* 811 */     return this.windowSizeClassification;
/*     */   }
/*     */   
/*     */   public boolean isUseReducedModelConfinedMotion() {
/* 815 */     return this.useReducedModelConfinedMotion;
/*     */   }
/*     */   
/*     */   public void setTimelag(double timelag) {
/* 819 */     TraJClassifierTest_.timelag = timelag;
/*     */   }
/*     */   
/*     */   public void setWindowSizeClassification(int windowSizeClassification) {
/* 823 */     this.windowSizeClassification = windowSizeClassification;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/TraJClassifierTest_.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */