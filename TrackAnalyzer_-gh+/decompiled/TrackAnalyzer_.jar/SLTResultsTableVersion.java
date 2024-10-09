import fiji.plugin.trackmate.Dimension;
import fiji.plugin.trackmate.FeatureModel;
import fiji.plugin.trackmate.Model;
import fiji.plugin.trackmate.SelectionModel;
import fiji.plugin.trackmate.Spot;
import fiji.plugin.trackmate.TrackMate;
import fiji.plugin.trackmate.util.ModelTools;
import ij.measure.ResultsTable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jgrapht.graph.DefaultWeightedEdge;

public class SLTResultsTableVersion {
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
   public static final List<String> FEATURES = new ArrayList(9);
   public static final Map<String, String> FEATURE_NAMES = new HashMap(9);
   public static final Map<String, String> FEATURE_SHORT_NAMES = new HashMap(9);
   public static final Map<String, Dimension> FEATURE_DIMENSIONS = new HashMap(9);
   public static final Map<String, Boolean> IS_INT = new HashMap(9);

   public SLTResultsTableVersion(SelectionModel selectionModel) {
      this.selectionModel = selectionModel;
   }

   public ResultsTable executeSpot(TrackMate trackmate) {
      Model model = trackmate.getModel();
      FeatureModel fm = model.getFeatureModel();
      Set<Integer> trackIDs = model.getTrackModel().trackIDs(true);
      Collection<String> spotFeatures = trackmate.getModel().getFeatureModel().getSpotFeatures();
      this.spotTable = new ResultsTable();
      Iterator var7 = trackIDs.iterator();

      while(var7.hasNext()) {
         Integer trackID = (Integer)var7.next();
         Set<Spot> track = model.getTrackModel().trackSpots(trackID);
         List<Spot> sortedTrack = new ArrayList(track);
         Collections.sort(sortedTrack, Spot.frameComparator);
         Iterator var11 = sortedTrack.iterator();

         while(var11.hasNext()) {
            Spot spot = (Spot)var11.next();
            this.spotTable.incrementCounter();
            this.spotTable.addLabel(spot.getName());
            this.spotTable.addValue("ID", "" + spot.ID());
            this.spotTable.addValue("TRACK_ID", "" + trackID);
            Iterator var13 = spotFeatures.iterator();

            while(var13.hasNext()) {
               String feature = (String)var13.next();
               Double val = spot.getFeature(feature);
               if (val == null) {
                  this.spotTable.addValue(feature, "None");
               } else if ((Boolean)fm.getSpotFeatureIsInt().get(feature)) {
                  this.spotTable.addValue(feature, "" + val.intValue());
               } else {
                  this.spotTable.addValue(feature, val);
               }
            }
         }
      }

      return this.spotTable;
   }

   public ResultsTable executeLink(TrackMate trackmate) {
      Model model = trackmate.getModel();
      FeatureModel fm = model.getFeatureModel();
      Set<Integer> trackIDs = model.getTrackModel().trackIDs(true);
      Collection<String> edgeFeatures = fm.getEdgeFeatures();
      this.edgeTable = new ResultsTable();
      Iterator var7 = trackIDs.iterator();

      while(var7.hasNext()) {
         Integer trackID = (Integer)var7.next();
         Comparator<DefaultWeightedEdge> edgeTimeComparator = ModelTools.featureEdgeComparator("EDGE_TIME", fm);
         Comparator<DefaultWeightedEdge> edgeSourceSpotTimeComparator = new SLTResultsTableVersion.EdgeSourceSpotFrameComparator(model);
         Set<DefaultWeightedEdge> track = model.getTrackModel().trackEdges(trackID);
         List<DefaultWeightedEdge> sortedTrack = new ArrayList(track);
         if (model.getFeatureModel().getEdgeFeatures().contains("Edge location")) {
            Collections.sort(sortedTrack, edgeTimeComparator);
         } else {
            Collections.sort(sortedTrack, edgeSourceSpotTimeComparator);
         }

         Iterator var13 = sortedTrack.iterator();

         while(var13.hasNext()) {
            DefaultWeightedEdge edge = (DefaultWeightedEdge)var13.next();
            this.edgeTable.incrementCounter();
            this.edgeTable.addLabel(edge.toString());
            this.edgeTable.addValue("TRACK_ID", "" + trackID);
            Iterator var15 = edgeFeatures.iterator();

            while(var15.hasNext()) {
               String feature = (String)var15.next();
               Object o = fm.getEdgeFeature(edge, feature);
               if (!(o instanceof String)) {
                  Number d = (Number)o;
                  if (d == null) {
                     this.edgeTable.addValue(feature, "None");
                  } else if ((Boolean)fm.getEdgeFeatureIsInt().get(feature)) {
                     this.edgeTable.addValue(feature, "" + d.intValue());
                  } else {
                     this.edgeTable.addValue(feature, d.doubleValue());
                  }
               }
            }
         }
      }

      return this.edgeTable;
   }

   public ResultsTable executeTrack(TrackMate trackmate) {
      FEATURES.add("TOTAL_DISTANCE_TRAVELED");
      FEATURES.add("MAX_DISTANCE_TRAVELED");
      FEATURES.add("CONFINMENT_RATIO");
      FEATURES.add("MEAN_STRAIGHT_LINE_SPEED");
      FEATURES.add("LINEARITY_OF_FORWARD_PROGRESSION");
      FEATURES.add("TOTAL_ABSOLUTE_ANGLE_XY");
      FEATURES.add("TOTAL_ABSOLUTE_ANGLE_YZ");
      FEATURES.add("TOTAL_ABSOLUTE_ANGLE_ZX");
      FEATURE_NAMES.put("TOTAL_DISTANCE_TRAVELED", "Total distance traveled");
      FEATURE_NAMES.put("MAX_DISTANCE_TRAVELED", "Max distance traveled");
      FEATURE_NAMES.put("CONFINMENT_RATIO", "Confinment ratio");
      FEATURE_NAMES.put("MEAN_STRAIGHT_LINE_SPEED", "Mean straight line speed");
      FEATURE_NAMES.put("LINEARITY_OF_FORWARD_PROGRESSION", "Linearity of forward progression");
      FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_XY", "Absolute angle in xy plane");
      FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_YZ", "Absolute angle in yz plane");
      FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_ZX", "Absolute angle in zx plane");
      FEATURE_SHORT_NAMES.put("TOTAL_DISTANCE_TRAVELED", "Total dist.");
      FEATURE_SHORT_NAMES.put("MAX_DISTANCE_TRAVELED", "Max dist.");
      FEATURE_SHORT_NAMES.put("CONFINMENT_RATIO", "Cnfnmnt ratio");
      FEATURE_SHORT_NAMES.put("MEAN_STRAIGHT_LINE_SPEED", "Mean v. line");
      FEATURE_SHORT_NAMES.put("LINEARITY_OF_FORWARD_PROGRESSION", "Lin. fwd. progr.");
      FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_XY", "Abs. angle xy");
      FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_YZ", "Abs. angle yz");
      FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_ZX", "Abs. angle zx");
      FEATURE_DIMENSIONS.put("TOTAL_DISTANCE_TRAVELED", Dimension.LENGTH);
      FEATURE_DIMENSIONS.put("MAX_DISTANCE_TRAVELED", Dimension.LENGTH);
      FEATURE_DIMENSIONS.put("CONFINMENT_RATIO", Dimension.NONE);
      FEATURE_DIMENSIONS.put("MEAN_STRAIGHT_LINE_SPEED", Dimension.VELOCITY);
      FEATURE_DIMENSIONS.put("LINEARITY_OF_FORWARD_PROGRESSION", Dimension.NONE);
      FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_XY", Dimension.ANGLE);
      FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_YZ", Dimension.ANGLE);
      FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_ZX", Dimension.ANGLE);
      IS_INT.put("TOTAL_DISTANCE_TRAVELED", Boolean.FALSE);
      IS_INT.put("MAX_DISTANCE_TRAVELED", Boolean.FALSE);
      IS_INT.put("CONFINMENT_RATIO", Boolean.FALSE);
      IS_INT.put("MEAN_STRAIGHT_LINE_SPEED", Boolean.FALSE);
      IS_INT.put("LINEARITY_OF_FORWARD_PROGRESSION", Boolean.FALSE);
      IS_INT.put("TOTAL_ABSOLUTE_ANGLE_XY", Boolean.FALSE);
      IS_INT.put("TOTAL_ABSOLUTE_ANGLE_YZ", Boolean.FALSE);
      IS_INT.put("TOTAL_ABSOLUTE_ANGLE_ZX", Boolean.FALSE);
      Model model = trackmate.getModel();
      FeatureModel fm = model.getFeatureModel();
      Set<Integer> trackIDs = model.getTrackModel().trackIDs(true);
      this.trackTable = new ResultsTable();
      Iterator var6 = trackIDs.iterator();

      while(var6.hasNext()) {
         Integer trackID = (Integer)var6.next();
         List<Spot> spots = new ArrayList(model.getTrackModel().trackSpots(trackID));
         Collections.sort(spots, Spot.frameComparator);
         Spot first = (Spot)spots.get(0);
         Set<DefaultWeightedEdge> edges = model.getTrackModel().trackEdges(trackID);
         double totalDistance = 0.0D;
         double maxDistanceSq = Double.NEGATIVE_INFINITY;
         double maxDistance = 0.0D;
         double dx = 0.0D;
         double dy = 0.0D;
         double dz = 0.0D;

         Spot source;
         Spot target;
         double vMean;
         double confinmentRatio;
         for(Iterator var23 = edges.iterator(); var23.hasNext(); dz += target.getDoublePosition(2) - source.getDoublePosition(2)) {
            DefaultWeightedEdge edge = (DefaultWeightedEdge)var23.next();
            source = model.getTrackModel().getEdgeSource(edge);
            target = model.getTrackModel().getEdgeTarget(edge);
            vMean = Math.sqrt(source.squareDistanceTo(target));
            totalDistance += vMean;
            confinmentRatio = first.squareDistanceTo(target);
            if (confinmentRatio > maxDistanceSq) {
               maxDistanceSq = confinmentRatio;
               maxDistance = Math.sqrt(confinmentRatio);
            }

            dx += target.getDoublePosition(0) - source.getDoublePosition(0);
            dy += target.getDoublePosition(1) - source.getDoublePosition(1);
         }

         double netDistance = fm.getTrackFeature(trackID, "TRACK_DISPLACEMENT");
         double tTotal = fm.getTrackFeature(trackID, "TRACK_DURATION");
         vMean = fm.getTrackFeature(trackID, "TRACK_MEAN_SPEED");
         confinmentRatio = netDistance / totalDistance;
         double meanStraightLineSpeed = netDistance / tTotal;
         double linearityForwardProgression = meanStraightLineSpeed / vMean;
         double angleXY = Math.atan2(dy, dx);
         double angleYZ = Math.atan2(dz, dy);
         double angleZX = Math.atan2(dx, dz);
         Collection<String> trackFeatures = fm.getTrackFeatures();
         this.trackTable.incrementCounter();
         this.trackTable.addLabel(model.getTrackModel().name(trackID));
         this.trackTable.addValue("TRACK_ID", "" + trackID);
         Iterator var42 = trackFeatures.iterator();

         while(var42.hasNext()) {
            String feature = (String)var42.next();
            Double val = fm.getTrackFeature(trackID, feature);
            if (val == null) {
               this.trackTable.addValue(feature, "None");
            } else if ((Boolean)fm.getTrackFeatureIsInt().get(feature)) {
               this.trackTable.addValue(feature, "" + val.intValue());
            } else {
               this.trackTable.addValue(feature, val);
            }
         }

         this.trackTable.addValue("TOTAL_DISTANCE_TRAVELED", "" + (double)Math.round(totalDistance * 1000.0D) / 1000.0D);
         this.trackTable.addValue("MAX_DISTANCE_TRAVELED", "" + (double)Math.round(maxDistance * 1000.0D) / 1000.0D);
         this.trackTable.addValue("MEAN_STRAIGHT_LINE_SPEED", "" + (double)Math.round(meanStraightLineSpeed * 1000.0D) / 1000.0D);
         this.trackTable.addValue("LINEARITY_OF_FORWARD_PROGRESSION", "" + (double)Math.round(linearityForwardProgression * 1000.0D) / 1000.0D);
         this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_XY", "" + (double)Math.round(angleXY * 1000.0D) / 1000.0D);
         this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_YZ", "" + (double)Math.round(angleYZ * 1000.0D) / 1000.0D);
         this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_ZX", "" + (double)Math.round(angleZX * 1000.0D) / 1000.0D);
         this.trackTable.addValue("CONFINMENT_RATIO", "" + (double)Math.round(confinmentRatio * 1000.0D) / 1000.0D);
         this.trackTable.addValue("TRACK_CLASSIFICATION", "");
         if (confinmentRatio == 0.0D) {
            this.trackTable.addValue("TRACK_CLASSIFICATION", "Total-Confined Track");
         }

         if (confinmentRatio == 1.0D) {
            this.trackTable.addValue("TRACK_CLASSIFICATION", "Perfectly Straight Track");
         }

         if (confinmentRatio > 0.0D && confinmentRatio <= 0.5D) {
            this.trackTable.addValue("TRACK_CLASSIFICATION", "Strongly Confined Track");
         }

         if (confinmentRatio > 0.05D && confinmentRatio <= 0.25D) {
            this.trackTable.addValue("TRACK_CLASSIFICATION", "Purely Random Track");
         }

         if (confinmentRatio > 0.25D && confinmentRatio < 1.0D) {
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

   private static final class EdgeSourceSpotFrameComparator implements Comparator<DefaultWeightedEdge> {
      private final Model model;

      public EdgeSourceSpotFrameComparator(Model model) {
         this.model = model;
      }

      public int compare(DefaultWeightedEdge e1, DefaultWeightedEdge e2) {
         double t1 = this.model.getTrackModel().getEdgeSource(e1).getFeature("FRAME");
         double t2 = this.model.getTrackModel().getEdgeSource(e2).getFeature("FRAME");
         if (t1 < t2) {
            return -1;
         } else {
            return t1 > t2 ? 1 : 0;
         }
      }
   }
}
