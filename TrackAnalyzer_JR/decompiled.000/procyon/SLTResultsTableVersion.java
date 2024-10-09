import net.imglib2.RealLocalizable;
import org.jgrapht.graph.DefaultWeightedEdge;
import fiji.plugin.trackmate.util.ModelTools;
import java.util.Iterator;
import java.util.Set;
import fiji.plugin.trackmate.FeatureModel;
import fiji.plugin.trackmate.Model;
import java.util.Comparator;
import java.util.Collections;
import java.util.Collection;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.TrackMate;
import java.util.HashMap;
import java.util.ArrayList;
import fiji.plugin.trackmate.Dimension;
import java.util.Map;
import java.util.List;
import fiji.plugin.trackmate.SelectionModel;
import ij.measure.ResultsTable;

// 
// Decompiled by Procyon v0.5.36
// 

public class SLTResultsTableVersion
{
    public static final String NAME = "Export statistics to tables";
    public static final String KEY = "EXPORT_STATS_TO_IJ";
    public static final String INFO_TEXT = "<html>Compute and export all statistics to 3 ImageJ results table. Statistisc are separated in features computed for: <ol> \t<li> spots in filtered tracks; \t<li> links between those spots; \t<li> filtered tracks. </ol> For tracks and links, they are recalculated prior to exporting. Note that spots and links that are not in a filtered tracks are not part of this export.</html>";
    private static final String SPOT_TABLE_NAME = "Spots in tracks statistics";
    private static final String EDGE_TABLE_NAME = "Links in tracks statistics";
    private static final String TRACK_TABLE_NAME = "Track statistics";
    private static final String ID_COLUMN = "ID";
    private static final String TRACK_ID_COLUMN = "TRACK_ID";
    private ResultsTable spotTable;
    private ResultsTable edgeTable;
    private ResultsTable trackTable;
    private final SelectionModel selectionModel;
    public static final String KEYLINEAR = "Linear track analysis";
    public static final String TRACK_TOTAL_DISTANCE_TRAVELED = "TOTAL_DISTANCE_TRAVELED";
    public static final String TRACK_MAX_DISTANCE_TRAVELED = "MAX_DISTANCE_TRAVELED";
    public static final String TRACK_CONFINMENT_RATIO = "CONFINMENT_RATIO";
    public static final String TRACK_MEAN_STRAIGHT_LINE_SPEED = "MEAN_STRAIGHT_LINE_SPEED";
    public static final String TRACK_LINEARITY_OF_FORWARD_PROGRESSION = "LINEARITY_OF_FORWARD_PROGRESSION";
    public static final String TOTAL_ABSOLUTE_ANGLE_XY = "TOTAL_ABSOLUTE_ANGLE_XY";
    public static final String TOTAL_ABSOLUTE_ANGLE_YZ = "TOTAL_ABSOLUTE_ANGLE_YZ";
    public static final String TOTAL_ABSOLUTE_ANGLE_ZX = "TOTAL_ABSOLUTE_ANGLE_ZX";
    public static final List<String> FEATURES;
    public static final Map<String, String> FEATURE_NAMES;
    public static final Map<String, String> FEATURE_SHORT_NAMES;
    public static final Map<String, Dimension> FEATURE_DIMENSIONS;
    public static final Map<String, Boolean> IS_INT;
    
    static {
        FEATURES = new ArrayList<String>(9);
        FEATURE_NAMES = new HashMap<String, String>(9);
        FEATURE_SHORT_NAMES = new HashMap<String, String>(9);
        FEATURE_DIMENSIONS = new HashMap<String, Dimension>(9);
        IS_INT = new HashMap<String, Boolean>(9);
    }
    
    public SLTResultsTableVersion(final SelectionModel selectionModel) {
        this.selectionModel = selectionModel;
    }
    
    public ResultsTable executeSpot(final TrackMate trackmate) {
        final Model model = trackmate.getModel();
        final FeatureModel fm = model.getFeatureModel();
        final Set<Integer> trackIDs = (Set<Integer>)model.getTrackModel().trackIDs(true);
        final Collection<String> spotFeatures = (Collection<String>)trackmate.getModel().getFeatureModel().getSpotFeatures();
        this.spotTable = new ResultsTable();
        for (final Integer trackID : trackIDs) {
            final Set<Spot> track = (Set<Spot>)model.getTrackModel().trackSpots(trackID);
            final List<Spot> sortedTrack = new ArrayList<Spot>(track);
            Collections.<Spot>sort(sortedTrack, Spot.frameComparator);
            for (final Spot spot : sortedTrack) {
                this.spotTable.incrementCounter();
                this.spotTable.addLabel(spot.getName());
                this.spotTable.addValue("ID", new StringBuilder().append(spot.ID()).toString());
                this.spotTable.addValue("TRACK_ID", new StringBuilder().append((int)trackID).toString());
                for (final String feature : spotFeatures) {
                    final Double val = spot.getFeature(feature);
                    if (val == null) {
                        this.spotTable.addValue(feature, "None");
                    }
                    else if (fm.getSpotFeatureIsInt().get(feature)) {
                        this.spotTable.addValue(feature, new StringBuilder().append(val.intValue()).toString());
                    }
                    else {
                        this.spotTable.addValue(feature, (double)val);
                    }
                }
            }
        }
        return this.spotTable;
    }
    
    public ResultsTable executeLink(final TrackMate trackmate) {
        final Model model = trackmate.getModel();
        final FeatureModel fm = model.getFeatureModel();
        final Set<Integer> trackIDs = (Set<Integer>)model.getTrackModel().trackIDs(true);
        final Collection<String> edgeFeatures = (Collection<String>)fm.getEdgeFeatures();
        this.edgeTable = new ResultsTable();
        for (final Integer trackID : trackIDs) {
            final Comparator<DefaultWeightedEdge> edgeTimeComparator = (Comparator<DefaultWeightedEdge>)ModelTools.featureEdgeComparator("EDGE_TIME", fm);
            final Comparator<DefaultWeightedEdge> edgeSourceSpotTimeComparator = new EdgeSourceSpotFrameComparator(model);
            final Set<DefaultWeightedEdge> track = (Set<DefaultWeightedEdge>)model.getTrackModel().trackEdges(trackID);
            final List<DefaultWeightedEdge> sortedTrack = new ArrayList<DefaultWeightedEdge>(track);
            if (model.getFeatureModel().getEdgeFeatures().contains("Edge location")) {
                Collections.<DefaultWeightedEdge>sort(sortedTrack, edgeTimeComparator);
            }
            else {
                Collections.<DefaultWeightedEdge>sort(sortedTrack, edgeSourceSpotTimeComparator);
            }
            for (final DefaultWeightedEdge edge : sortedTrack) {
                this.edgeTable.incrementCounter();
                this.edgeTable.addLabel(edge.toString());
                this.edgeTable.addValue("TRACK_ID", new StringBuilder().append((int)trackID).toString());
                for (final String feature : edgeFeatures) {
                    final Object o = fm.getEdgeFeature(edge, feature);
                    if (o instanceof String) {
                        continue;
                    }
                    final Number d = (Number)o;
                    if (d == null) {
                        this.edgeTable.addValue(feature, "None");
                    }
                    else if (fm.getEdgeFeatureIsInt().get(feature)) {
                        this.edgeTable.addValue(feature, new StringBuilder().append(d.intValue()).toString());
                    }
                    else {
                        this.edgeTable.addValue(feature, d.doubleValue());
                    }
                }
            }
        }
        return this.edgeTable;
    }
    
    public ResultsTable executeTrack(final TrackMate trackmate) {
        SLTResultsTableVersion.FEATURES.add("TOTAL_DISTANCE_TRAVELED");
        SLTResultsTableVersion.FEATURES.add("MAX_DISTANCE_TRAVELED");
        SLTResultsTableVersion.FEATURES.add("CONFINMENT_RATIO");
        SLTResultsTableVersion.FEATURES.add("MEAN_STRAIGHT_LINE_SPEED");
        SLTResultsTableVersion.FEATURES.add("LINEARITY_OF_FORWARD_PROGRESSION");
        SLTResultsTableVersion.FEATURES.add("TOTAL_ABSOLUTE_ANGLE_XY");
        SLTResultsTableVersion.FEATURES.add("TOTAL_ABSOLUTE_ANGLE_YZ");
        SLTResultsTableVersion.FEATURES.add("TOTAL_ABSOLUTE_ANGLE_ZX");
        SLTResultsTableVersion.FEATURE_NAMES.put("TOTAL_DISTANCE_TRAVELED", "Total distance traveled");
        SLTResultsTableVersion.FEATURE_NAMES.put("MAX_DISTANCE_TRAVELED", "Max distance traveled");
        SLTResultsTableVersion.FEATURE_NAMES.put("CONFINMENT_RATIO", "Confinment ratio");
        SLTResultsTableVersion.FEATURE_NAMES.put("MEAN_STRAIGHT_LINE_SPEED", "Mean straight line speed");
        SLTResultsTableVersion.FEATURE_NAMES.put("LINEARITY_OF_FORWARD_PROGRESSION", "Linearity of forward progression");
        SLTResultsTableVersion.FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_XY", "Absolute angle in xy plane");
        SLTResultsTableVersion.FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_YZ", "Absolute angle in yz plane");
        SLTResultsTableVersion.FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_ZX", "Absolute angle in zx plane");
        SLTResultsTableVersion.FEATURE_SHORT_NAMES.put("TOTAL_DISTANCE_TRAVELED", "Total dist.");
        SLTResultsTableVersion.FEATURE_SHORT_NAMES.put("MAX_DISTANCE_TRAVELED", "Max dist.");
        SLTResultsTableVersion.FEATURE_SHORT_NAMES.put("CONFINMENT_RATIO", "Cnfnmnt ratio");
        SLTResultsTableVersion.FEATURE_SHORT_NAMES.put("MEAN_STRAIGHT_LINE_SPEED", "Mean v. line");
        SLTResultsTableVersion.FEATURE_SHORT_NAMES.put("LINEARITY_OF_FORWARD_PROGRESSION", "Lin. fwd. progr.");
        SLTResultsTableVersion.FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_XY", "Abs. angle xy");
        SLTResultsTableVersion.FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_YZ", "Abs. angle yz");
        SLTResultsTableVersion.FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_ZX", "Abs. angle zx");
        SLTResultsTableVersion.FEATURE_DIMENSIONS.put("TOTAL_DISTANCE_TRAVELED", Dimension.LENGTH);
        SLTResultsTableVersion.FEATURE_DIMENSIONS.put("MAX_DISTANCE_TRAVELED", Dimension.LENGTH);
        SLTResultsTableVersion.FEATURE_DIMENSIONS.put("CONFINMENT_RATIO", Dimension.NONE);
        SLTResultsTableVersion.FEATURE_DIMENSIONS.put("MEAN_STRAIGHT_LINE_SPEED", Dimension.VELOCITY);
        SLTResultsTableVersion.FEATURE_DIMENSIONS.put("LINEARITY_OF_FORWARD_PROGRESSION", Dimension.NONE);
        SLTResultsTableVersion.FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_XY", Dimension.ANGLE);
        SLTResultsTableVersion.FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_YZ", Dimension.ANGLE);
        SLTResultsTableVersion.FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_ZX", Dimension.ANGLE);
        SLTResultsTableVersion.IS_INT.put("TOTAL_DISTANCE_TRAVELED", Boolean.FALSE);
        SLTResultsTableVersion.IS_INT.put("MAX_DISTANCE_TRAVELED", Boolean.FALSE);
        SLTResultsTableVersion.IS_INT.put("CONFINMENT_RATIO", Boolean.FALSE);
        SLTResultsTableVersion.IS_INT.put("MEAN_STRAIGHT_LINE_SPEED", Boolean.FALSE);
        SLTResultsTableVersion.IS_INT.put("LINEARITY_OF_FORWARD_PROGRESSION", Boolean.FALSE);
        SLTResultsTableVersion.IS_INT.put("TOTAL_ABSOLUTE_ANGLE_XY", Boolean.FALSE);
        SLTResultsTableVersion.IS_INT.put("TOTAL_ABSOLUTE_ANGLE_YZ", Boolean.FALSE);
        SLTResultsTableVersion.IS_INT.put("TOTAL_ABSOLUTE_ANGLE_ZX", Boolean.FALSE);
        final Model model = trackmate.getModel();
        final FeatureModel fm = model.getFeatureModel();
        final Set<Integer> trackIDs = (Set<Integer>)model.getTrackModel().trackIDs(true);
        this.trackTable = new ResultsTable();
        for (final Integer trackID : trackIDs) {
            final List<Spot> spots = new ArrayList<Spot>(model.getTrackModel().trackSpots(trackID));
            Collections.<Spot>sort(spots, Spot.frameComparator);
            final Spot first = spots.get(0);
            final Set<DefaultWeightedEdge> edges = (Set<DefaultWeightedEdge>)model.getTrackModel().trackEdges(trackID);
            double totalDistance = 0.0;
            double maxDistanceSq = Double.NEGATIVE_INFINITY;
            double maxDistance = 0.0;
            double dx = 0.0;
            double dy = 0.0;
            double dz = 0.0;
            for (final DefaultWeightedEdge edge : edges) {
                final Spot source = model.getTrackModel().getEdgeSource(edge);
                final Spot target = model.getTrackModel().getEdgeTarget(edge);
                final double d = Math.sqrt(source.squareDistanceTo((RealLocalizable)target));
                totalDistance += d;
                final double dToFirstSq = first.squareDistanceTo((RealLocalizable)target);
                if (dToFirstSq > maxDistanceSq) {
                    maxDistanceSq = dToFirstSq;
                    maxDistance = Math.sqrt(maxDistanceSq);
                }
                dx += target.getDoublePosition(0) - source.getDoublePosition(0);
                dy += target.getDoublePosition(1) - source.getDoublePosition(1);
                dz += target.getDoublePosition(2) - source.getDoublePosition(2);
            }
            final double netDistance = fm.getTrackFeature(trackID, "TRACK_DISPLACEMENT");
            final double tTotal = fm.getTrackFeature(trackID, "TRACK_DURATION");
            final double vMean = fm.getTrackFeature(trackID, "TRACK_MEAN_SPEED");
            final double confinmentRatio = netDistance / totalDistance;
            final double meanStraightLineSpeed = netDistance / tTotal;
            final double linearityForwardProgression = meanStraightLineSpeed / vMean;
            final double angleXY = Math.atan2(dy, dx);
            final double angleYZ = Math.atan2(dz, dy);
            final double angleZX = Math.atan2(dx, dz);
            final Collection<String> trackFeatures = (Collection<String>)fm.getTrackFeatures();
            this.trackTable.incrementCounter();
            this.trackTable.addLabel(model.getTrackModel().name(trackID));
            this.trackTable.addValue("TRACK_ID", new StringBuilder().append((int)trackID).toString());
            for (final String feature : trackFeatures) {
                final Double val = fm.getTrackFeature(trackID, feature);
                if (val == null) {
                    this.trackTable.addValue(feature, "None");
                }
                else if (fm.getTrackFeatureIsInt().get(feature)) {
                    this.trackTable.addValue(feature, new StringBuilder().append(val.intValue()).toString());
                }
                else {
                    this.trackTable.addValue(feature, (double)val);
                }
            }
            this.trackTable.addValue("TOTAL_DISTANCE_TRAVELED", new StringBuilder().append(Math.round(totalDistance * 1000.0) / 1000.0).toString());
            this.trackTable.addValue("MAX_DISTANCE_TRAVELED", new StringBuilder().append(Math.round(maxDistance * 1000.0) / 1000.0).toString());
            this.trackTable.addValue("MEAN_STRAIGHT_LINE_SPEED", new StringBuilder().append(Math.round(meanStraightLineSpeed * 1000.0) / 1000.0).toString());
            this.trackTable.addValue("LINEARITY_OF_FORWARD_PROGRESSION", new StringBuilder().append(Math.round(linearityForwardProgression * 1000.0) / 1000.0).toString());
            this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_XY", new StringBuilder().append(Math.round(angleXY * 1000.0) / 1000.0).toString());
            this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_YZ", new StringBuilder().append(Math.round(angleYZ * 1000.0) / 1000.0).toString());
            this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_ZX", new StringBuilder().append(Math.round(angleZX * 1000.0) / 1000.0).toString());
            this.trackTable.addValue("CONFINMENT_RATIO", new StringBuilder().append(Math.round(confinmentRatio * 1000.0) / 1000.0).toString());
            this.trackTable.addValue("TRACK_CLASSIFICATION", "");
            if (confinmentRatio == 0.0) {
                this.trackTable.addValue("TRACK_CLASSIFICATION", "Total-Confined Track");
            }
            if (confinmentRatio == 1.0) {
                this.trackTable.addValue("TRACK_CLASSIFICATION", "Perfectly Straight Track");
            }
            if (confinmentRatio > 0.0 && confinmentRatio <= 0.5) {
                this.trackTable.addValue("TRACK_CLASSIFICATION", "Strongly Confined Track");
            }
            if (confinmentRatio > 0.05 && confinmentRatio <= 0.25) {
                this.trackTable.addValue("TRACK_CLASSIFICATION", "Purely Random Track");
            }
            if (confinmentRatio > 0.25 && confinmentRatio < 1.0) {
                this.trackTable.addValue("TRACK_CLASSIFICATION", "Fairly Straight Track");
            }
        }
        return this.trackTable;
    }
    
    public ResultsTable getEdgeTable() {
        return this.edgeTable;
    }
    
    public ResultsTable getTrackTable() {
        return this.trackTable;
    }
    
    private static final class EdgeSourceSpotFrameComparator implements Comparator<DefaultWeightedEdge>
    {
        private final Model model;
        
        public EdgeSourceSpotFrameComparator(final Model model) {
            this.model = model;
        }
        
        @Override
        public int compare(final DefaultWeightedEdge e1, final DefaultWeightedEdge e2) {
            final double t1 = this.model.getTrackModel().getEdgeSource(e1).getFeature("FRAME");
            final double t2 = this.model.getTrackModel().getEdgeSource(e2).getFeature("FRAME");
            if (t1 < t2) {
                return -1;
            }
            if (t1 > t2) {
                return 1;
            }
            return 0;
        }
    }
}
