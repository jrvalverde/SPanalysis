import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import org.apache.commons.math3.stat.StatUtils;
import java.util.Collection;
import java.util.HashSet;
import trajectory_classifier.AbstractClassifierModified;
import trajectory_classifier.WeightedWindowedClassificationProcessModified;
import trajectory_classifier.RRFClassifierRenjinModified;
import DiffusionCoefficientEstimator.AbstractDiffusionCoefficientEstimatorModified;
import java.text.NumberFormat;
import features.AbstractTrajectoryFeatureModified;
import java.awt.Font;
import java.util.Iterator;
import java.util.Set;
import ij.measure.ResultsTable;
import DiffusionCoefficientEstimator.CovarianceDiffusionCoefficientEstimatorModified;
import features.MSDRatioFeatureModified;
import features.Asymmetry3FeatureModified;
import features.GaussianityFeautureModified;
import features.KurtosisFeatureModified;
import features.MeanSpeedFeatureModified;
import features.StraightnessFeatureModified;
import features.EfficiencyFeatureModified;
import features.TrappedProbabilityFeatureModified;
import features.FractalDimensionFeatureModified;
import features.CenterOfGravityFeatureModified;
import features.ConfinedDiffusionParametersFeatureModified;
import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
import features.PowerLawFeatureModified;
import features.ActiveTransportParametersFeatureModified;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.io.File;
import trajectory_classifier.ExportImportToolsModified;
import de.biomedical_imaging.ij.trajectory_classifier.TraJResultsTable;
import ij.gui.TextRoi;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import ij.gui.Roi;
import ij.IJ;
import trajectory_classifier.VisualizationUtilsModified;
import ij.gui.Overlay;
import drift.StaticDriftCalculatorModified;
import java.awt.Color;
import java.util.HashMap;
import vecmath.Point3dModified;
import java.util.Collections;
import fiji.plugin.trackmate.Spot;
import java.util.List;
import traJ.TrajectoryModified;
import trajectory_classifier.SubtrajectoryModified;
import java.util.ArrayList;
import ij.plugin.PlugIn;

// 
// Decompiled by Procyon v0.5.36
// 

public class TraJClassifierTest_ implements PlugIn
{
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
        TraJClassifierTest_.instance = this;
    }
    
    public static TraJClassifierTest_ getInstance() {
        if (TraJClassifierTest_.instance == null) {
            TraJClassifierTest_.instance = new TraJClassifierTest_();
        }
        return TraJClassifierTest_.instance;
    }
    
    public void run(final String arg) {
        String modelpath = "";
        try {
            modelpath = this.ExportResource("/randomForestModel.RData");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        List<Integer> trackSize = null;
        if (!arg.contains("DEBUG")) {
            this.tracksToClassify = new ArrayList<TrajectoryModified>();
            TrajectoryModified.restIDCounter();
            final List<List<Spot>> imported2Spot = new ArrayList<List<Spot>>();
            final Set<Integer> trackIDs = (Set<Integer>)SPTBatch_.model.getTrackModel().trackIDs(true);
            trackSize = new ArrayList<Integer>();
            for (final Integer id : trackIDs) {
                final List<Spot> imported1Spot = new ArrayList<Spot>();
                final Set<Spot> track = (Set<Spot>)SPTBatch_.model.getTrackModel().trackSpots(id);
                trackSize.add(track.size());
                final ArrayList<Float> framesByTrack = new ArrayList<Float>();
                final ArrayList<Float> framesByTrackSort = new ArrayList<Float>();
                final ArrayList<Float> trackID = new ArrayList<Float>();
                final ArrayList<Integer> indexes = new ArrayList<Integer>();
                final List<Spot> spots = new ArrayList<Spot>();
                for (final Spot spot : track) {
                    trackID.add((float)id);
                    framesByTrack.add(Float.valueOf(spot.getFeature("FRAME").toString()));
                    framesByTrackSort.add(Float.valueOf(spot.getFeature("FRAME").toString()));
                    spots.add(spot);
                }
                Collections.<Float>sort(framesByTrackSort);
                for (int z = 0; z < framesByTrackSort.size(); ++z) {
                    indexes.add(framesByTrack.indexOf(framesByTrackSort.get(z)));
                }
                for (int y = 0; y < indexes.size(); ++y) {
                    imported1Spot.add(spots.get(indexes.get(y)));
                }
                imported2Spot.add(imported1Spot);
            }
            for (int i = 0; i < imported2Spot.size(); ++i) {
                final TrajectoryModified t = new TrajectoryModified(2);
                boolean firstPosition = true;
                for (int j = 0; j < imported2Spot.get(i).size(); ++j) {
                    final double x = imported2Spot.get(i).get(j).getFeature("POSITION_X");
                    final double y2 = imported2Spot.get(i).get(j).getFeature("POSITION_Y");
                    final int time = imported2Spot.get(i).get(j).getFeature("FRAME").intValue();
                    if (firstPosition) {
                        t.setRelativStartTimepoint(time);
                        firstPosition = false;
                    }
                    t.add(x, y2, 0.0);
                }
                this.tracksToClassify.add(t);
            }
        }
        int maxNumberOfPositions = 0;
        for (int k = 0; k < this.tracksToClassify.size(); ++k) {
            if (this.tracksToClassify.get(k).size() > maxNumberOfPositions) {
                maxNumberOfPositions = this.tracksToClassify.get(k).size();
            }
        }
        final boolean visualize = true;
        if (traJParametersWindow.keyWord == "keyword") {
            this.minTrackLength = Integer.valueOf(traJParametersWindow.minLengthTextS);
            this.windowSizeClassification = Integer.valueOf(traJParametersWindow.windowTextS);
            this.minSegmentLength = Integer.valueOf(traJParametersWindow.minSegTextS);
        }
        else {
            this.minTrackLength = 10.0;
            this.windowSizeClassification = 5;
            this.minSegmentLength = 5;
        }
        this.resampleRate = 1;
        if (SPTBatch_.imps.getCalibration().getXUnit() == "pixel") {
            this.pixelsize = SPTBatch_.imps.getCalibration().pixelWidth;
        }
        if (SPTBatch_.imps.getCalibration().getXUnit() != "pixel") {
            this.pixelsize = 0.0;
        }
        TraJClassifierTest_.timelag = SPTBatch_.fps;
        this.useReducedModelConfinedMotion = false;
        this.showID = true;
        this.showOverviewClasses = true;
        this.removeGlobalDrift = false;
        if (this.pixelsize > 1.0E-6) {
            for (int i = 0; i < this.tracksToClassify.size(); ++i) {
                this.tracksToClassify.get(i).scale(this.pixelsize);
            }
        }
        for (int i = 0; i < this.tracksToClassify.size(); ++i) {
            final TrajectoryModified t = this.tracksToClassify.get(i);
            int changesCounter = 0;
            for (int j = 1; j < t.size(); ++j) {
                if (t.get(j).distance(t.get(j - 1)) > Math.pow(10.0, -12.0)) {
                    ++changesCounter;
                }
            }
            if (1.0 * changesCounter / t.size() < 0.5) {
                this.tracksToClassify.remove(i);
                ++this.ommittedTrajectories;
                --i;
            }
        }
        final HashMap<String, Color> mapTypeToColor = new HashMap<String, Color>();
        mapTypeToColor.put("DIRECTED/ACTIVE", Color.MAGENTA);
        mapTypeToColor.put("NORM. DIFFUSION", Color.RED);
        mapTypeToColor.put("CONFINED", Color.YELLOW);
        mapTypeToColor.put("SUBDIFFUSION", Color.GREEN);
        this.parentTrajectories = new ArrayList<TrajectoryModified>();
        for (final TrajectoryModified track2 : this.tracksToClassify) {
            if (track2.size() > this.minTrackLength) {
                this.parentTrajectories.add(track2);
            }
        }
        final StaticDriftCalculatorModified<TrajectoryModified> dcalc = new StaticDriftCalculatorModified<TrajectoryModified>();
        if (this.parentTrajectories.isEmpty() == Boolean.FALSE) {
            this.classifiedSegments = this.classifyAndSegment(this.parentTrajectories, modelpath, this.windowSizeClassification, this.minSegmentLength, 10, this.resampleRate);
            if (visualize) {
                final Overlay ov = new Overlay();
                for (int l = 0; l < this.classifiedSegments.size(); ++l) {
                    final SubtrajectoryModified tr = this.classifiedSegments.get(l);
                    ArrayList<Roi> prois = null;
                    if (this.pixelsize > 1.0E-6) {
                        prois = VisualizationUtilsModified.generateVisualizationRoisFromTrack(tr, mapTypeToColor.get(tr.getType()), this.showID, this.pixelsize);
                    }
                    else {
                        prois = VisualizationUtilsModified.generateVisualizationRoisFromTrack(tr, mapTypeToColor.get(tr.getType()), this.showID, IJ.getImage().getCalibration().pixelWidth);
                    }
                    for (final Roi r : prois) {
                        ov.add(r);
                    }
                }
                if (this.showOverviewClasses) {
                    final Set<String> classes = mapTypeToColor.keySet();
                    final Iterator<String> it = classes.iterator();
                    int y3 = 5;
                    float fsize = 20.0f;
                    final AffineTransform affinetransform = new AffineTransform();
                    final FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
                    int width = (int)IJ.getImage().getProcessor().getFont().getStringBounds("Norm. Diffusion", frc).getWidth();
                    for (Font f = IJ.getImage().getProcessor().getFont(); 1.0 * width / IJ.getImage().getWidth() > 0.08; width = (int)f.getStringBounds("Norm. Diffusion", frc).getWidth()) {
                        --fsize;
                        f = f.deriveFont(fsize);
                    }
                    TextRoi.setFont("TimesRoman", (int)fsize, 0);
                    while (it.hasNext()) {
                        final String type = it.next();
                        final TextRoi troi = new TextRoi(5, y3, type);
                        troi.setFillColor(Color.DARK_GRAY);
                        troi.setStrokeColor((Color)mapTypeToColor.get(type));
                        ov.add((Roi)troi);
                        y3 += 20;
                    }
                }
                IJ.getImage().setOverlay(ov);
                IJ.getImage().updateAndRepaintWindow();
            }
            final HashMap<String, TraJResultsTable> rtables = new HashMap<String, TraJResultsTable>();
            rtables.put("DIRECTED/ACTIVE", new TraJResultsTable());
            rtables.put("NORM. DIFFUSION", new TraJResultsTable());
            rtables.put("SUBDIFFUSION", new TraJResultsTable());
            rtables.put("CONFINED", new TraJResultsTable());
            double sumConf = 0.0;
            for (int m = 0; m < this.classifiedSegments.size(); ++m) {
                IJ.showProgress(m, this.classifiedSegments.size());
                final SubtrajectoryModified t2 = this.classifiedSegments.get(m);
                final TraJResultsTable rt = rtables.get(t2.getType());
                if (rt == null) {
                    SPTBatch_.taskOutput.append("Type: " + t2.getType());
                    final ExportImportToolsModified eit = new ExportImportToolsModified();
                    final ArrayList<TrajectoryModified> hlp = new ArrayList<TrajectoryModified>();
                    eit.exportTrajectoryDataAsCSV(hlp, String.valueOf(SPTBatch_.csvPath) + File.separator + "bad" + ".csv");
                    SPTBatch_.taskOutput.append(t2.toString());
                }
                rt.incrementCounter();
                rt.addValue("PARENT-ID", (double)t2.getParent().getID());
                rt.addValue("ID", (double)t2.getID());
                rt.addValue("LENGTH", (double)t2.size());
                rt.addValue("START", (double)t2.getRelativeStartTimepoint());
                rt.addValue("END", (double)(t2.getRelativeStartTimepoint() + t2.size() - 1));
                rt.addValue("CLASS", t2.getType());
                AbstractTrajectoryFeatureModified dcEstim = null;
                double dc = 0.0;
                final DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
                final NumberFormat formatter = new DecimalFormat("0.###E0", otherSymbols);
                double goodness = 0.0;
                double alphaGoodness = 0.0;
                final String type2;
                switch (type2 = t2.getType()) {
                    case "DIRECTED/ACTIVE": {
                        final ActiveTransportParametersFeatureModified apf = new ActiveTransportParametersFeatureModified(t2, TraJClassifierTest_.timelag);
                        final double[] res = apf.evaluate();
                        rt.addValue("(FIT) D", formatter.format(res[0]));
                        rt.addValue("(FIT) Velocity", res[1]);
                        goodness = res[2];
                        break;
                    }
                    case "SUBDIFFUSION": {
                        final PowerLawFeatureModified pwf = new PowerLawFeatureModified(t2, 1.0 / TraJClassifierTest_.timelag, 1, t2.size() / 3);
                        final double[] res = pwf.evaluate();
                        dc = res[1];
                        rt.addValue("(FIT) D", formatter.format(dc));
                        goodness = res[2];
                        break;
                    }
                    case "NORM. DIFFUSION": {
                        dcEstim = new RegressionDiffusionCoefficientEstimatorModified(t2, 1.0 / TraJClassifierTest_.timelag, 1, t2.size() / 3);
                        final double[] res = dcEstim.evaluate();
                        dc = res[0];
                        rt.addValue("(FIT) D", formatter.format(dc));
                        goodness = res[3];
                        break;
                    }
                    case "CONFINED": {
                        final AbstractDiffusionCoefficientEstimatorModified dcEst = new RegressionDiffusionCoefficientEstimatorModified(t2, 1.0 / TraJClassifierTest_.timelag, 1, 3);
                        final ConfinedDiffusionParametersFeatureModified confp = new ConfinedDiffusionParametersFeatureModified(t2, TraJClassifierTest_.timelag, this.useReducedModelConfinedMotion, dcEst);
                        final double[] p = confp.evaluate();
                        dc = p[1];
                        if (this.useReducedModelConfinedMotion) {
                            rt.addValue("(FIT) CONF. RADIUS", Math.sqrt(p[0]));
                            rt.addValue("(FIT) D", formatter.format(p[1]));
                            goodness = p[2];
                            break;
                        }
                        rt.addValue("(FIT) CONF. RADIUS", Math.sqrt(p[0]));
                        rt.addValue("(FIT) A [CONF. SHAPE]", p[2]);
                        rt.addValue("(FIT) B (CONF SHAPE)", p[3]);
                        rt.addValue("(FIT) D", formatter.format(p[1]));
                        goodness = p[4];
                        break;
                    }
                    default:
                        break;
                }
                final AbstractTrajectoryFeatureModified pwf2 = new PowerLawFeatureModified(t2, 1.0 / TraJClassifierTest_.timelag, 1, t2.size() / 3);
                double[] res = pwf2.evaluate();
                final double alpha = res[0];
                alphaGoodness = res[2];
                final AbstractTrajectoryFeatureModified f2 = new CenterOfGravityFeatureModified(t2);
                final double cog_x = f2.evaluate()[0];
                final double cog_y = f2.evaluate()[1];
                rt.addValue("X (COG)", cog_x);
                rt.addValue("Y (COG)", cog_y);
                if (!t2.getType().equals("NONE")) {
                    final FractalDimensionFeatureModified fdf = new FractalDimensionFeatureModified(t2);
                    double v = fdf.evaluate()[0];
                    rt.addValue("FRACT. DIM.", v);
                    final TrappedProbabilityFeatureModified trapped = new TrappedProbabilityFeatureModified(t2);
                    v = trapped.evaluate()[0];
                    rt.addValue("TRAPPEDNESS", v);
                    final EfficiencyFeatureModified eff = new EfficiencyFeatureModified(t2);
                    v = eff.evaluate()[0];
                    rt.addValue("EFFICENCY", v);
                    final StraightnessFeatureModified straight = new StraightnessFeatureModified(t2);
                    v = straight.evaluate()[0];
                    rt.addValue("STRAIGHTNESS", v);
                    final MeanSpeedFeatureModified msfeature = new MeanSpeedFeatureModified(t2, TraJClassifierTest_.timelag);
                    v = msfeature.evaluate()[1];
                    rt.addValue("SPEED", v);
                    final KurtosisFeatureModified kurt = new KurtosisFeatureModified(t2);
                    v = kurt.evaluate()[0];
                    rt.addValue("KURTOSIS", v);
                    rt.addValue("(FIT) ALPHA", alpha);
                    final GaussianityFeautureModified gauss = new GaussianityFeautureModified(t2, 1);
                    v = gauss.evaluate()[0];
                    rt.addValue("GAUSSIANITY", v);
                    final Asymmetry3FeatureModified asym3 = new Asymmetry3FeatureModified(t2);
                    v = asym3.evaluate()[0];
                    rt.addValue("Asymmetry", v);
                    final MSDRatioFeatureModified msdratio = new MSDRatioFeatureModified(t2, 1, 5);
                    v = msdratio.evaluate()[0];
                    rt.addValue("MSDRatio", v);
                    final CovarianceDiffusionCoefficientEstimatorModified cest = new CovarianceDiffusionCoefficientEstimatorModified(t2, 1.0 / TraJClassifierTest_.timelag);
                    res = cest.evaluate();
                    rt.addValue("Loc. noise_sigma", (res[1] + res[2]) / 2.0);
                    rt.addValue("Fit Goodness", goodness);
                    rt.addValue("Alpha Fit Goodness", alphaGoodness);
                    final double conf = t2.getConfidence();
                    sumConf += conf;
                    rt.addValue("Confidence", conf);
                }
            }
            Iterator<String> rtIt = rtables.keySet().iterator();
            final ResultsTable parents = (ResultsTable)new TraJResultsTable(true);
            for (int i2 = 0; i2 < this.parentTrajectories.size(); ++i2) {
                parents.incrementCounter();
                final TrajectoryModified t3 = this.parentTrajectories.get(i2);
                parents.addValue("ID", (double)t3.getID());
                parents.addValue("LENGTH", (double)t3.size());
                parents.addValue("START", (double)t3.getRelativeStartTimepoint());
                parents.addValue("END", (double)(t3.getRelativeStartTimepoint() + t3.size() - 1));
                int subPosCount = 0;
                int subSegCount = 0;
                int normPosCount = 0;
                int normSegCount = 0;
                int directedPosCount = 0;
                int directSegCount = 0;
                int confPosCount = 0;
                int confSegCount = 0;
                final ArrayList<SubtrajectoryModified> sameParent = SubtrajectoryModified.getTracksWithSameParant(this.classifiedSegments, t3.getID());
                for (final SubtrajectoryModified sub : sameParent) {
                    final String type3;
                    switch ((type3 = sub.getType()).hashCode()) {
                        case -1768066899: {
                            if (!type3.equals("DIRECTED/ACTIVE")) {
                                continue;
                            }
                            directedPosCount += sub.size();
                            ++directSegCount;
                            continue;
                        }
                        case -568951419: {
                            if (!type3.equals("SUBDIFFUSION")) {
                                continue;
                            }
                            subPosCount += sub.size();
                            ++subSegCount;
                            continue;
                        }
                        case 160958711: {
                            if (!type3.equals("NORM. DIFFUSION")) {
                                continue;
                            }
                            normPosCount += sub.size();
                            ++normSegCount;
                            continue;
                        }
                        case 202494376: {
                            if (!type3.equals("CONFINED")) {
                                continue;
                            }
                            confPosCount += sub.size();
                            ++confSegCount;
                            continue;
                        }
                        default: {
                            continue;
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
            final String trajVersion = TrajectoryModified.class.getPackage().getImplementationVersion();
            final double[] drift = dcalc.calculateDrift(this.parentTrajectories);
            final ResultsTable overall = new ResultsTable();
            overall.incrementCounter();
            overall.addValue("Mean confindence", sumConf / this.classifiedSegments.size());
            overall.addValue("Drift x", drift[0]);
            overall.addValue("Drift y", drift[1]);
            overall.addValue("Omitted segments", (double)this.ommittedTrajectories);
            overall.addValue("Min. track length", this.minTrackLength);
            overall.addValue("Window size", (double)(this.windowSizeClassification * 2));
            overall.addValue("Min. segment length", (double)this.minSegmentLength);
            overall.addValue("Resamplerate", (double)this.resampleRate);
            overall.addValue("Pixelsize", this.pixelsize);
            overall.addValue("Framerate", 1.0 / TraJClassifierTest_.timelag);
            overall.addValue("Reduced conf. model", Boolean.toString(this.useReducedModelConfinedMotion));
            overall.addValue("Remove global drift", Boolean.toString(this.removeGlobalDrift));
            overall.addValue("TraJ library version", trajVersion);
            overall.save(SPTBatch_.directDiff + File.separator + "Settings & Miscellaneous" + SPTBatch_.imps.getShortTitle() + ".xls");
            parents.save(SPTBatch_.directDiff + File.separator + "Parents_" + SPTBatch_.imps.getShortTitle() + ".xls");
            rtIt = rtables.keySet().iterator();
            while (rtIt.hasNext()) {
                final String rt2 = rtIt.next();
                if (rt2.equals("DIRECTED/ACTIVE") == Boolean.TRUE) {
                    rtables.get(rt2).save(SPTBatch_.directDiff + File.separator + "DIRECTED_ACTIVE" + "_trajectories_" + SPTBatch_.imps.getShortTitle() + ".xls");
                }
                else {
                    rtables.get(rt2).save(SPTBatch_.directDiff + File.separator + rt2 + "_trajectories_" + SPTBatch_.imps.getShortTitle() + ".xls");
                }
            }
        }
    }
    
    public ArrayList<SubtrajectoryModified> classifyAndSegment(final TrajectoryModified trackToClassify, final String modelpath, final int windowSizeClassification, final int minSegmentLength, final int modeFilterLength, final int resampleRate) {
        final ArrayList<TrajectoryModified> help = new ArrayList<TrajectoryModified>();
        help.add(trackToClassify);
        return this.classifyAndSegment(help, modelpath, windowSizeClassification, minSegmentLength, modeFilterLength, resampleRate);
    }
    
    public ArrayList<SubtrajectoryModified> classifyAndSegment(final ArrayList<TrajectoryModified> tracksToClassify, final String modelpath, final int windowSizeClassification, final int minSegmentLength, final int modeFilterLength, final int resampleRate) {
        final ArrayList<SubtrajectoryModified> classified = new ArrayList<SubtrajectoryModified>();
        int j = 0;
        final RRFClassifierRenjinModified rrf = new RRFClassifierRenjinModified(modelpath, resampleRate * TraJClassifierTest_.timelag);
        rrf.start();
        final WeightedWindowedClassificationProcessModified wcp = new WeightedWindowedClassificationProcessModified();
        int subidcounter = 1;
        for (final TrajectoryModified track : tracksToClassify) {
            IJ.showProgress(++j, tracksToClassify.size());
            final TrajectoryModified mTrack = track;
            String[] classes = wcp.windowedClassification(mTrack, rrf, windowSizeClassification, resampleRate);
            final double[] classConfidence = wcp.getPositionConfidence();
            classes = movingMode(classes, modeFilterLength);
            double sumConf = 0.0;
            int Nconf = 0;
            SubtrajectoryModified tr = new SubtrajectoryModified(track, 2);
            tr.setID(subidcounter);
            ++subidcounter;
            tr.add(track.get(0).x, track.get(0).y, 0.0);
            sumConf += classConfidence[0];
            ++Nconf;
            String prevCls = classes[0];
            final int start = track.getRelativeStartTimepoint();
            tr.setRelativStartTimepoint(start);
            tr.setType(prevCls);
            for (int i = 1; i < classes.length; ++i) {
                if (prevCls == classes[i]) {
                    tr.add(track.get(i).x, track.get(i).y, 0.0);
                    sumConf += classConfidence[i];
                    ++Nconf;
                }
                else {
                    tr.setConfidence(sumConf / Nconf);
                    classified.add(tr);
                    tr = new SubtrajectoryModified(track, 2);
                    tr.setID(subidcounter);
                    ++subidcounter;
                    tr.setRelativStartTimepoint(start + i);
                    tr.add(track.get(i).x, track.get(i).y, 0.0);
                    sumConf = classConfidence[i];
                    Nconf = 1;
                    prevCls = classes[i];
                    tr.setType(prevCls);
                }
            }
            tr.setConfidence(sumConf / Nconf);
            classified.add(tr);
            sumConf = 0.0;
            Nconf = 0;
        }
        rrf.stop();
        for (int k = 0; k < classified.size(); ++k) {
            if (classified.get(k).size() < minSegmentLength) {
                classified.remove(k);
                --k;
            }
        }
        return classified;
    }
    
    public double getTimelag() {
        return TraJClassifierTest_.timelag;
    }
    
    public static String[] movingMode(final String[] types, final int n) {
        final ArrayList<String> ltypes = new ArrayList<String>();
        for (int i = 0; i < types.length; ++i) {
            ltypes.add(types[i]);
        }
        return movingMode(ltypes, n);
    }
    
    public static String[] movingMode(final ArrayList<String> types, final int n) {
        final int windowsize = 2 * n + 1;
        final HashSet<String> uniqueTypes = new HashSet<String>();
        uniqueTypes.addAll((Collection<?>)types);
        final HashMap<String, Integer> mapTypeToInt = new HashMap<String, Integer>();
        final HashMap<Integer, String> mapIntToType = new HashMap<Integer, String>();
        final Iterator<String> it = uniqueTypes.iterator();
        int key = 0;
        while (it.hasNext()) {
            final String type = it.next();
            mapTypeToInt.put(type, key);
            mapIntToType.put(key, type);
            ++key;
        }
        final String[] medTypes = new String[types.size()];
        for (int i = 0; i < n; ++i) {
            medTypes[i] = types.get(i);
        }
        for (int i = types.size() - n; i < types.size(); ++i) {
            medTypes[i] = types.get(i);
        }
        for (int i = 0; i < types.size() - windowsize + 1; ++i) {
            final List<String> sub = types.subList(i, i + windowsize - 1);
            final double[] values = new double[sub.size()];
            for (int j = 0; j < sub.size(); ++j) {
                values[j] = mapTypeToInt.get(sub.get(j));
            }
            medTypes[i + n] = mapIntToType.get((int)StatUtils.mode(values)[0]);
        }
        return medTypes;
    }
    
    public ArrayList<SubtrajectoryModified> getClassifiedTrajectories() {
        return this.classifiedSegments;
    }
    
    public ArrayList<TrajectoryModified> getParentTrajectories() {
        return this.parentTrajectories;
    }
    
    public String ExportResource(final String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        String tmpFolder;
        try {
            stream = this.getClass().getResourceAsStream(resourceName);
            if (stream == null) {
                IJ.error("Cannot get resource \"" + resourceName + "\" from Jar file.");
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }
            final byte[] buffer = new byte[4096];
            final File folderDir = new File(String.valueOf(IJ.getDirectory("temp")) + "/.trajclassifier");
            if (!folderDir.exists()) {
                folderDir.mkdir();
            }
            tmpFolder = folderDir.getPath().replace('\\', '/');
            resStreamOut = new FileOutputStream(String.valueOf(tmpFolder) + resourceName);
            int readBytes;
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        }
        catch (Exception ex) {
            IJ.error(ex.getMessage());
            throw ex;
        }
        finally {
            stream.close();
            resStreamOut.close();
        }
        stream.close();
        resStreamOut.close();
        return String.valueOf(tmpFolder) + resourceName;
    }
    
    public void setTracksToClassify(final ArrayList<TrajectoryModified> t) {
        this.tracksToClassify = t;
    }
    
    public double getMinTrackLength() {
        return this.minTrackLength;
    }
    
    public void setMinTrackLength(final double minTrackLength) {
        this.minTrackLength = minTrackLength;
    }
    
    public double getPixelsize() {
        return this.pixelsize;
    }
    
    public void setPixelsize(final double pixelsize) {
        this.pixelsize = pixelsize;
    }
    
    public boolean isShowID() {
        return this.showID;
    }
    
    public void setShowID(final boolean showID) {
        this.showID = showID;
    }
    
    public int getWindowSizeClassification() {
        return this.windowSizeClassification;
    }
    
    public boolean isUseReducedModelConfinedMotion() {
        return this.useReducedModelConfinedMotion;
    }
    
    public void setTimelag(final double timelag) {
        TraJClassifierTest_.timelag = timelag;
    }
    
    public void setWindowSizeClassification(final int windowSizeClassification) {
        this.windowSizeClassification = windowSizeClassification;
    }
}
