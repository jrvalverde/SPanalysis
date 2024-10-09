import DiffusionCoefficientEstimator.AbstractDiffusionCoefficientEstimatorModified;
import DiffusionCoefficientEstimator.CovarianceDiffusionCoefficientEstimatorModified;
import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
import de.biomedical_imaging.ij.trajectory_classifier.TraJResultsTable;
import drift.StaticDriftCalculatorModified;
import features.AbstractTrajectoryFeatureModified;
import features.ActiveTransportParametersFeatureModified;
import features.Asymmetry3FeatureModified;
import features.CenterOfGravityFeatureModified;
import features.ConfinedDiffusionParametersFeatureModified;
import features.EfficiencyFeatureModified;
import features.FractalDimensionFeatureModified;
import features.GaussianityFeautureModified;
import features.KurtosisFeatureModified;
import features.MSDRatioFeatureModified;
import features.MeanSpeedFeatureModified;
import features.PowerLawFeatureModified;
import features.StraightnessFeatureModified;
import features.TrappedProbabilityFeatureModified;
import fiji.plugin.trackmate.Spot;
import ij.IJ;
import ij.gui.Overlay;
import ij.gui.Roi;
import ij.gui.TextRoi;
import ij.measure.ResultsTable;
import ij.plugin.PlugIn;
import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.math3.stat.StatUtils;
import traJ.TrajectoryModified;
import trajectory_classifier.ExportImportToolsModified;
import trajectory_classifier.RRFClassifierRenjinModified;
import trajectory_classifier.SubtrajectoryModified;
import trajectory_classifier.VisualizationUtilsModified;
import trajectory_classifier.WeightedWindowedClassificationProcessModified;
import vecmath.Point3dModified;

public class TraJClassifierTest_ implements PlugIn {
   static double timelag;
   double minTrackLength;
   int windowSizeClassification;
   int minSegmentLength;
   double pixelsize;
   int resampleRate;
   boolean showID;
   boolean showOverviewClasses;
   boolean removeGlobalDrift;
   boolean useReducedModelConfinedMotion;
   int ommittedTrajectories;
   public static TraJClassifierTest_ instance;
   ArrayList<SubtrajectoryModified> classifiedSegments;
   ArrayList<TrajectoryModified> tracksToClassify;
   ArrayList<TrajectoryModified> parentTrajectories;

   public TraJClassifierTest_() {
      instance = this;
   }

   public static TraJClassifierTest_ getInstance() {
      if (instance == null) {
         instance = new TraJClassifierTest_();
      }

      return instance;
   }

   public void run(String arg) {
      String modelpath = "";

      try {
         modelpath = this.ExportResource("/randomForestModel.RData");
      } catch (Exception var47) {
         var47.printStackTrace();
      }

      List<Integer> trackSize = null;
      Set classes;
      ArrayList prois;
      ArrayList dcEstim;
      int i;
      TrajectoryModified track;
      int i;
      int subPosCount;
      if (!arg.contains("DEBUG")) {
         this.tracksToClassify = new ArrayList();
         TrajectoryModified.restIDCounter();
         List<List<Spot>> imported2Spot = new ArrayList();
         Set<Integer> trackIDs = SPTBatch_.model.getTrackModel().trackIDs(true);
         trackSize = new ArrayList();
         Iterator var7 = trackIDs.iterator();

         while(var7.hasNext()) {
            Integer id = (Integer)var7.next();
            List<Spot> imported1Spot = new ArrayList();
            classes = SPTBatch_.model.getTrackModel().trackSpots(id);
            trackSize.add(classes.size());
            ArrayList<Float> framesByTrack = new ArrayList();
            prois = new ArrayList();
            ArrayList<Float> trackID = new ArrayList();
            ArrayList<Integer> indexes = new ArrayList();
            dcEstim = new ArrayList();
            Iterator var16 = classes.iterator();

            while(var16.hasNext()) {
               Spot spot = (Spot)var16.next();
               trackID.add((float)id);
               framesByTrack.add(Float.valueOf(spot.getFeature("FRAME").toString()));
               prois.add(Float.valueOf(spot.getFeature("FRAME").toString()));
               dcEstim.add(spot);
            }

            Collections.sort(prois);

            for(subPosCount = 0; subPosCount < prois.size(); ++subPosCount) {
               indexes.add(framesByTrack.indexOf(prois.get(subPosCount)));
            }

            for(subPosCount = 0; subPosCount < indexes.size(); ++subPosCount) {
               imported1Spot.add((Spot)dcEstim.get((Integer)indexes.get(subPosCount)));
            }

            imported2Spot.add(imported1Spot);
         }

         for(i = 0; i < imported2Spot.size(); ++i) {
            track = new TrajectoryModified(2);
            boolean firstPosition = true;

            for(i = 0; i < ((List)imported2Spot.get(i)).size(); ++i) {
               double x = ((Spot)((List)imported2Spot.get(i)).get(i)).getFeature("POSITION_X");
               double y = ((Spot)((List)imported2Spot.get(i)).get(i)).getFeature("POSITION_Y");
               int time = ((Spot)((List)imported2Spot.get(i)).get(i)).getFeature("FRAME").intValue();
               if (firstPosition) {
                  track.setRelativStartTimepoint(time);
                  firstPosition = false;
               }

               track.add(x, y, 0.0D);
            }

            this.tracksToClassify.add(track);
         }
      }

      int maxNumberOfPositions = 0;

      for(int i = 0; i < this.tracksToClassify.size(); ++i) {
         if (((TrajectoryModified)this.tracksToClassify.get(i)).size() > maxNumberOfPositions) {
            maxNumberOfPositions = ((TrajectoryModified)this.tracksToClassify.get(i)).size();
         }
      }

      boolean visualize = true;
      if (traJParametersWindow.keyWord == "keyword") {
         this.minTrackLength = (double)Integer.valueOf(traJParametersWindow.minLengthTextS);
         this.windowSizeClassification = Integer.valueOf(traJParametersWindow.windowTextS);
         this.minSegmentLength = Integer.valueOf(traJParametersWindow.minSegTextS);
      } else {
         this.minTrackLength = 10.0D;
         this.windowSizeClassification = 5;
         this.minSegmentLength = 5;
      }

      this.resampleRate = 1;
      if (SPTBatch_.imps.getCalibration().getXUnit() == "pixel") {
         this.pixelsize = SPTBatch_.imps.getCalibration().pixelWidth;
      }

      if (SPTBatch_.imps.getCalibration().getXUnit() != "pixel") {
         this.pixelsize = 0.0D;
      }

      timelag = SPTBatch_.fps;
      this.useReducedModelConfinedMotion = false;
      this.showID = true;
      this.showOverviewClasses = true;
      this.removeGlobalDrift = false;
      if (this.pixelsize > 1.0E-6D) {
         for(i = 0; i < this.tracksToClassify.size(); ++i) {
            ((TrajectoryModified)this.tracksToClassify.get(i)).scale(this.pixelsize);
         }
      }

      for(i = 0; i < this.tracksToClassify.size(); ++i) {
         track = (TrajectoryModified)this.tracksToClassify.get(i);
         int changesCounter = 0;

         for(i = 1; i < track.size(); ++i) {
            if (((Point3dModified)track.get(i)).distance((Point3dModified)track.get(i - 1)) > Math.pow(10.0D, -12.0D)) {
               ++changesCounter;
            }
         }

         if (1.0D * (double)changesCounter / (double)track.size() < 0.5D) {
            this.tracksToClassify.remove(i);
            ++this.ommittedTrajectories;
            --i;
         }
      }

      HashMap<String, Color> mapTypeToColor = new HashMap();
      mapTypeToColor.put("DIRECTED/ACTIVE", Color.MAGENTA);
      mapTypeToColor.put("NORM. DIFFUSION", Color.RED);
      mapTypeToColor.put("CONFINED", Color.YELLOW);
      mapTypeToColor.put("SUBDIFFUSION", Color.GREEN);
      this.parentTrajectories = new ArrayList();
      Iterator var58 = this.tracksToClassify.iterator();

      while(var58.hasNext()) {
         track = (TrajectoryModified)var58.next();
         if ((double)track.size() > this.minTrackLength) {
            this.parentTrajectories.add(track);
         }
      }

      StaticDriftCalculatorModified<TrajectoryModified> dcalc = new StaticDriftCalculatorModified();
      if (this.parentTrajectories.isEmpty() == Boolean.FALSE) {
         this.classifiedSegments = this.classifyAndSegment((ArrayList)this.parentTrajectories, modelpath, this.windowSizeClassification, this.minSegmentLength, 10, this.resampleRate);
         int y;
         if (visualize) {
            Overlay ov = new Overlay();
            i = 0;

            while(true) {
               if (i >= this.classifiedSegments.size()) {
                  if (this.showOverviewClasses) {
                     classes = mapTypeToColor.keySet();
                     Iterator<String> it = classes.iterator();
                     y = 5;
                     float fsize = 20.0F;
                     AffineTransform affinetransform = new AffineTransform();
                     FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
                     subPosCount = (int)IJ.getImage().getProcessor().getFont().getStringBounds("Norm. Diffusion", frc).getWidth();

                     for(Font f = IJ.getImage().getProcessor().getFont(); 1.0D * (double)subPosCount / (double)IJ.getImage().getWidth() > 0.08D; subPosCount = (int)f.getStringBounds("Norm. Diffusion", frc).getWidth()) {
                        --fsize;
                        f = f.deriveFont(fsize);
                     }

                     TextRoi.setFont("TimesRoman", (int)fsize, 0);

                     while(it.hasNext()) {
                        String type = (String)it.next();
                        TextRoi troi = new TextRoi(5, y, type);
                        troi.setFillColor(Color.DARK_GRAY);
                        troi.setStrokeColor((Color)mapTypeToColor.get(type));
                        ov.add(troi);
                        y += 20;
                     }
                  }

                  IJ.getImage().setOverlay(ov);
                  IJ.getImage().updateAndRepaintWindow();
                  break;
               }

               SubtrajectoryModified tr = (SubtrajectoryModified)this.classifiedSegments.get(i);
               prois = null;
               if (this.pixelsize > 1.0E-6D) {
                  prois = VisualizationUtilsModified.generateVisualizationRoisFromTrack(tr, (Color)mapTypeToColor.get(tr.getType()), this.showID, this.pixelsize);
               } else {
                  prois = VisualizationUtilsModified.generateVisualizationRoisFromTrack(tr, (Color)mapTypeToColor.get(tr.getType()), this.showID, IJ.getImage().getCalibration().pixelWidth);
               }

               Iterator var69 = prois.iterator();

               while(var69.hasNext()) {
                  Roi r = (Roi)var69.next();
                  ov.add(r);
               }

               ++i;
            }
         }

         HashMap<String, TraJResultsTable> rtables = new HashMap();
         rtables.put("DIRECTED/ACTIVE", new TraJResultsTable());
         rtables.put("NORM. DIFFUSION", new TraJResultsTable());
         rtables.put("SUBDIFFUSION", new TraJResultsTable());
         rtables.put("CONFINED", new TraJResultsTable());
         double sumConf = 0.0D;

         for(y = 0; y < this.classifiedSegments.size(); ++y) {
            IJ.showProgress(y, this.classifiedSegments.size());
            SubtrajectoryModified t = (SubtrajectoryModified)this.classifiedSegments.get(y);
            TraJResultsTable rt = (TraJResultsTable)rtables.get(t.getType());
            if (rt == null) {
               SPTBatch_.taskOutput.append("Type: " + t.getType());
               ExportImportToolsModified eit = new ExportImportToolsModified();
               ArrayList<TrajectoryModified> hlp = new ArrayList();
               eit.exportTrajectoryDataAsCSV(hlp, SPTBatch_.csvPath + File.separator + "bad" + ".csv");
               SPTBatch_.taskOutput.append(t.toString());
            }

            rt.incrementCounter();
            rt.addValue("PARENT-ID", (double)t.getParent().getID());
            rt.addValue("ID", (double)t.getID());
            rt.addValue("LENGTH", (double)t.size());
            rt.addValue("START", (double)t.getRelativeStartTimepoint());
            rt.addValue("END", (double)(t.getRelativeStartTimepoint() + t.size() - 1));
            rt.addValue("CLASS", t.getType());
            dcEstim = null;
            double dc = 0.0D;
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
            NumberFormat formatter = new DecimalFormat("0.###E0", otherSymbols);
            double goodness = 0.0D;
            double alphaGoodness = 0.0D;
            double[] res;
            String var24;
            switch((var24 = t.getType()).hashCode()) {
            case -1768066899:
               if (var24.equals("DIRECTED/ACTIVE")) {
                  ActiveTransportParametersFeatureModified apf = new ActiveTransportParametersFeatureModified(t, timelag);
                  res = apf.evaluate();
                  rt.addValue("(FIT) D", formatter.format(res[0]));
                  rt.addValue("(FIT) Velocity", res[1]);
                  goodness = res[2];
               }
               break;
            case -568951419:
               if (var24.equals("SUBDIFFUSION")) {
                  PowerLawFeatureModified pwf = new PowerLawFeatureModified(t, 1.0D / timelag, 1, t.size() / 3);
                  res = pwf.evaluate();
                  dc = res[1];
                  rt.addValue("(FIT) D", formatter.format(dc));
                  goodness = res[2];
               }
               break;
            case 160958711:
               if (var24.equals("NORM. DIFFUSION")) {
                  AbstractTrajectoryFeatureModified dcEstim = new RegressionDiffusionCoefficientEstimatorModified(t, 1.0D / timelag, 1, t.size() / 3);
                  res = dcEstim.evaluate();
                  dc = res[0];
                  rt.addValue("(FIT) D", formatter.format(dc));
                  goodness = res[3];
               }
               break;
            case 202494376:
               if (var24.equals("CONFINED")) {
                  AbstractDiffusionCoefficientEstimatorModified dcEst = new RegressionDiffusionCoefficientEstimatorModified(t, 1.0D / timelag, 1, 3);
                  ConfinedDiffusionParametersFeatureModified confp = new ConfinedDiffusionParametersFeatureModified(t, timelag, this.useReducedModelConfinedMotion, dcEst);
                  double[] p = confp.evaluate();
                  dc = p[1];
                  if (this.useReducedModelConfinedMotion) {
                     rt.addValue("(FIT) CONF. RADIUS", Math.sqrt(p[0]));
                     rt.addValue("(FIT) D", formatter.format(p[1]));
                     goodness = p[2];
                  } else {
                     rt.addValue("(FIT) CONF. RADIUS", Math.sqrt(p[0]));
                     rt.addValue("(FIT) A [CONF. SHAPE]", p[2]);
                     rt.addValue("(FIT) B (CONF SHAPE)", p[3]);
                     rt.addValue("(FIT) D", formatter.format(p[1]));
                     goodness = p[4];
                  }
               }
            }

            AbstractTrajectoryFeatureModified pwf = new PowerLawFeatureModified(t, 1.0D / timelag, 1, t.size() / 3);
            res = pwf.evaluate();
            double alpha = res[0];
            alphaGoodness = res[2];
            AbstractTrajectoryFeatureModified f = new CenterOfGravityFeatureModified(t);
            double cog_x = f.evaluate()[0];
            double cog_y = f.evaluate()[1];
            rt.addValue("X (COG)", cog_x);
            rt.addValue("Y (COG)", cog_y);
            if (!t.getType().equals("NONE")) {
               FractalDimensionFeatureModified fdf = new FractalDimensionFeatureModified(t);
               double v = fdf.evaluate()[0];
               rt.addValue("FRACT. DIM.", v);
               TrappedProbabilityFeatureModified trapped = new TrappedProbabilityFeatureModified(t);
               v = trapped.evaluate()[0];
               rt.addValue("TRAPPEDNESS", v);
               EfficiencyFeatureModified eff = new EfficiencyFeatureModified(t);
               v = eff.evaluate()[0];
               rt.addValue("EFFICENCY", v);
               StraightnessFeatureModified straight = new StraightnessFeatureModified(t);
               v = straight.evaluate()[0];
               rt.addValue("STRAIGHTNESS", v);
               MeanSpeedFeatureModified msfeature = new MeanSpeedFeatureModified(t, timelag);
               v = msfeature.evaluate()[1];
               rt.addValue("SPEED", v);
               KurtosisFeatureModified kurt = new KurtosisFeatureModified(t);
               v = kurt.evaluate()[0];
               rt.addValue("KURTOSIS", v);
               rt.addValue("(FIT) ALPHA", alpha);
               GaussianityFeautureModified gauss = new GaussianityFeautureModified(t, 1);
               v = gauss.evaluate()[0];
               rt.addValue("GAUSSIANITY", v);
               Asymmetry3FeatureModified asym3 = new Asymmetry3FeatureModified(t);
               v = asym3.evaluate()[0];
               rt.addValue("Asymmetry", v);
               MSDRatioFeatureModified msdratio = new MSDRatioFeatureModified(t, 1, 5);
               v = msdratio.evaluate()[0];
               rt.addValue("MSDRatio", v);
               CovarianceDiffusionCoefficientEstimatorModified cest = new CovarianceDiffusionCoefficientEstimatorModified(t, 1.0D / timelag);
               res = cest.evaluate();
               rt.addValue("Loc. noise_sigma", (res[1] + res[2]) / 2.0D);
               rt.addValue("Fit Goodness", goodness);
               rt.addValue("Alpha Fit Goodness", alphaGoodness);
               double conf = t.getConfidence();
               sumConf += conf;
               rt.addValue("Confidence", conf);
            }
         }

         Iterator<String> rtIt = rtables.keySet().iterator();
         ResultsTable parents = new TraJResultsTable(true);

         for(int i = 0; i < this.parentTrajectories.size(); ++i) {
            parents.incrementCounter();
            TrajectoryModified t = (TrajectoryModified)this.parentTrajectories.get(i);
            parents.addValue("ID", (double)t.getID());
            parents.addValue("LENGTH", (double)t.size());
            parents.addValue("START", (double)t.getRelativeStartTimepoint());
            parents.addValue("END", (double)(t.getRelativeStartTimepoint() + t.size() - 1));
            subPosCount = 0;
            int subSegCount = 0;
            int normPosCount = 0;
            int normSegCount = 0;
            int directedPosCount = 0;
            int directSegCount = 0;
            int confPosCount = 0;
            int confSegCount = 0;
            ArrayList<SubtrajectoryModified> sameParent = SubtrajectoryModified.getTracksWithSameParant(this.classifiedSegments, t.getID());
            Iterator var99 = sameParent.iterator();

            while(var99.hasNext()) {
               SubtrajectoryModified sub = (SubtrajectoryModified)var99.next();
               String var101;
               switch((var101 = sub.getType()).hashCode()) {
               case -1768066899:
                  if (var101.equals("DIRECTED/ACTIVE")) {
                     directedPosCount += sub.size();
                     ++directSegCount;
                  }
                  break;
               case -568951419:
                  if (var101.equals("SUBDIFFUSION")) {
                     subPosCount += sub.size();
                     ++subSegCount;
                  }
                  break;
               case 160958711:
                  if (var101.equals("NORM. DIFFUSION")) {
                     normPosCount += sub.size();
                     ++normSegCount;
                  }
                  break;
               case 202494376:
                  if (var101.equals("CONFINED")) {
                     confPosCount += sub.size();
                     ++confSegCount;
                  }
               }
            }

            parents.addValue("#SEG_NORM", (double)normSegCount);
            parents.addValue("#POS_NORM", (double)normPosCount);
            parents.addValue("#SEG_SUB", (double)subSegCount);
            parents.addValue("#POS_SUB", (double)subPosCount);
            parents.addValue("#SEG_CONF", (double)confSegCount);
            parents.addValue("#POS_CONF", (double)confPosCount);
            parents.addValue("#SEG_DIRECTED", (double)directSegCount);
            parents.addValue("#POS_DIRECTED", (double)directedPosCount);
         }

         String trajVersion = TrajectoryModified.class.getPackage().getImplementationVersion();
         double[] drift = dcalc.calculateDrift(this.parentTrajectories);
         ResultsTable overall = new ResultsTable();
         overall.incrementCounter();
         overall.addValue("Mean confindence", sumConf / (double)this.classifiedSegments.size());
         overall.addValue("Drift x", drift[0]);
         overall.addValue("Drift y", drift[1]);
         overall.addValue("Omitted segments", (double)this.ommittedTrajectories);
         overall.addValue("Min. track length", this.minTrackLength);
         overall.addValue("Window size", (double)(this.windowSizeClassification * 2));
         overall.addValue("Min. segment length", (double)this.minSegmentLength);
         overall.addValue("Resamplerate", (double)this.resampleRate);
         overall.addValue("Pixelsize", this.pixelsize);
         overall.addValue("Framerate", 1.0D / timelag);
         overall.addValue("Reduced conf. model", Boolean.toString(this.useReducedModelConfinedMotion));
         overall.addValue("Remove global drift", Boolean.toString(this.removeGlobalDrift));
         overall.addValue("TraJ library version", trajVersion);
         overall.save(SPTBatch_.directDiff + File.separator + "Settings & Miscellaneous" + SPTBatch_.imps.getShortTitle() + ".xls");
         parents.save(SPTBatch_.directDiff + File.separator + "Parents_" + SPTBatch_.imps.getShortTitle() + ".xls");
         rtIt = rtables.keySet().iterator();

         while(rtIt.hasNext()) {
            String rt = (String)rtIt.next();
            if (rt.equals("DIRECTED/ACTIVE") == Boolean.TRUE) {
               ((TraJResultsTable)rtables.get(rt)).save(SPTBatch_.directDiff + File.separator + "DIRECTED_ACTIVE" + "_trajectories_" + SPTBatch_.imps.getShortTitle() + ".xls");
            } else {
               ((TraJResultsTable)rtables.get(rt)).save(SPTBatch_.directDiff + File.separator + rt + "_trajectories_" + SPTBatch_.imps.getShortTitle() + ".xls");
            }
         }
      }

   }

   public ArrayList<SubtrajectoryModified> classifyAndSegment(TrajectoryModified trackToClassify, String modelpath, int windowSizeClassification, int minSegmentLength, int modeFilterLength, int resampleRate) {
      ArrayList<TrajectoryModified> help = new ArrayList();
      help.add(trackToClassify);
      return this.classifyAndSegment(help, modelpath, windowSizeClassification, minSegmentLength, modeFilterLength, resampleRate);
   }

   public ArrayList<SubtrajectoryModified> classifyAndSegment(ArrayList<TrajectoryModified> tracksToClassify, String modelpath, int windowSizeClassification, int minSegmentLength, int modeFilterLength, int resampleRate) {
      ArrayList<SubtrajectoryModified> classified = new ArrayList();
      int j = 0;
      RRFClassifierRenjinModified rrf = new RRFClassifierRenjinModified(modelpath, (double)resampleRate * timelag);
      rrf.start();
      WeightedWindowedClassificationProcessModified wcp = new WeightedWindowedClassificationProcessModified();
      int subidcounter = 1;

      boolean var26;
      for(Iterator var13 = tracksToClassify.iterator(); var13.hasNext(); var26 = false) {
         TrajectoryModified track = (TrajectoryModified)var13.next();
         ++j;
         IJ.showProgress(j, tracksToClassify.size());
         String[] classes = wcp.windowedClassification(track, rrf, windowSizeClassification, resampleRate);
         double[] classConfidence = wcp.getPositionConfidence();
         classes = movingMode(classes, modeFilterLength);
         double sumConf = 0.0D;
         int Nconf = 0;
         SubtrajectoryModified tr = new SubtrajectoryModified(track, 2);
         tr.setID(subidcounter);
         ++subidcounter;
         tr.add(((Point3dModified)track.get(0)).x, ((Point3dModified)track.get(0)).y, 0.0D);
         sumConf += classConfidence[0];
         int Nconf = Nconf + 1;
         String prevCls = classes[0];
         int start = track.getRelativeStartTimepoint();
         tr.setRelativStartTimepoint(start);
         tr.setType(prevCls);

         for(int i = 1; i < classes.length; ++i) {
            if (prevCls == classes[i]) {
               tr.add(((Point3dModified)track.get(i)).x, ((Point3dModified)track.get(i)).y, 0.0D);
               sumConf += classConfidence[i];
               ++Nconf;
            } else {
               tr.setConfidence(sumConf / (double)Nconf);
               classified.add(tr);
               tr = new SubtrajectoryModified(track, 2);
               tr.setID(subidcounter);
               ++subidcounter;
               tr.setRelativStartTimepoint(start + i);
               tr.add(((Point3dModified)track.get(i)).x, ((Point3dModified)track.get(i)).y, 0.0D);
               sumConf = classConfidence[i];
               Nconf = 1;
               prevCls = classes[i];
               tr.setType(prevCls);
            }
         }

         tr.setConfidence(sumConf / (double)Nconf);
         classified.add(tr);
         sumConf = 0.0D;
      }

      rrf.stop();

      for(int i = 0; i < classified.size(); ++i) {
         if (((SubtrajectoryModified)classified.get(i)).size() < minSegmentLength) {
            classified.remove(i);
            --i;
         }
      }

      return classified;
   }

   public double getTimelag() {
      return timelag;
   }

   public static String[] movingMode(String[] types, int n) {
      ArrayList<String> ltypes = new ArrayList();

      for(int i = 0; i < types.length; ++i) {
         ltypes.add(types[i]);
      }

      return movingMode(ltypes, n);
   }

   public static String[] movingMode(ArrayList<String> types, int n) {
      int windowsize = 2 * n + 1;
      HashSet<String> uniqueTypes = new HashSet();
      uniqueTypes.addAll(types);
      HashMap<String, Integer> mapTypeToInt = new HashMap();
      HashMap<Integer, String> mapIntToType = new HashMap();
      Iterator<String> it = uniqueTypes.iterator();

      for(int key = 0; it.hasNext(); ++key) {
         String type = (String)it.next();
         mapTypeToInt.put(type, key);
         mapIntToType.put(key, type);
      }

      String[] medTypes = new String[types.size()];

      int i;
      for(i = 0; i < n; ++i) {
         medTypes[i] = (String)types.get(i);
      }

      for(i = types.size() - n; i < types.size(); ++i) {
         medTypes[i] = (String)types.get(i);
      }

      for(i = 0; i < types.size() - windowsize + 1; ++i) {
         List<String> sub = types.subList(i, i + windowsize - 1);
         double[] values = new double[sub.size()];

         for(int j = 0; j < sub.size(); ++j) {
            values[j] = (double)(Integer)mapTypeToInt.get(sub.get(j));
         }

         medTypes[i + n] = (String)mapIntToType.get((int)StatUtils.mode(values)[0]);
      }

      return medTypes;
   }

   public ArrayList<SubtrajectoryModified> getClassifiedTrajectories() {
      return this.classifiedSegments;
   }

   public ArrayList<TrajectoryModified> getParentTrajectories() {
      return this.parentTrajectories;
   }

   public String ExportResource(String resourceName) throws Exception {
      InputStream stream = null;
      FileOutputStream resStreamOut = null;

      String tmpFolder;
      try {
         stream = this.getClass().getResourceAsStream(resourceName);
         if (stream == null) {
            IJ.error("Cannot get resource \"" + resourceName + "\" from Jar file.");
            throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
         }

         byte[] buffer = new byte[4096];
         File folderDir = new File(IJ.getDirectory("temp") + "/.trajclassifier");
         if (!folderDir.exists()) {
            folderDir.mkdir();
         }

         tmpFolder = folderDir.getPath().replace('\\', '/');
         resStreamOut = new FileOutputStream(tmpFolder + resourceName);

         int readBytes;
         while((readBytes = stream.read(buffer)) > 0) {
            resStreamOut.write(buffer, 0, readBytes);
         }
      } catch (Exception var11) {
         IJ.error(var11.getMessage());
         throw var11;
      } finally {
         stream.close();
         resStreamOut.close();
      }

      return tmpFolder + resourceName;
   }

   public void setTracksToClassify(ArrayList<TrajectoryModified> t) {
      this.tracksToClassify = t;
   }

   public double getMinTrackLength() {
      return this.minTrackLength;
   }

   public void setMinTrackLength(double minTrackLength) {
      this.minTrackLength = minTrackLength;
   }

   public double getPixelsize() {
      return this.pixelsize;
   }

   public void setPixelsize(double pixelsize) {
      this.pixelsize = pixelsize;
   }

   public boolean isShowID() {
      return this.showID;
   }

   public void setShowID(boolean showID) {
      this.showID = showID;
   }

   public int getWindowSizeClassification() {
      return this.windowSizeClassification;
   }

   public boolean isUseReducedModelConfinedMotion() {
      return this.useReducedModelConfinedMotion;
   }

   public void setTimelag(double timelag) {
      TraJClassifierTest_.timelag = timelag;
   }

   public void setWindowSizeClassification(int windowSizeClassification) {
      this.windowSizeClassification = windowSizeClassification;
   }
}
