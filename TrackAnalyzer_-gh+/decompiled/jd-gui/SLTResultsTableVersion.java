/*     */ import fiji.plugin.trackmate.Dimension;
/*     */ import fiji.plugin.trackmate.FeatureModel;
/*     */ import fiji.plugin.trackmate.Model;
/*     */ import fiji.plugin.trackmate.SelectionModel;
/*     */ import fiji.plugin.trackmate.Spot;
/*     */ import fiji.plugin.trackmate.TrackMate;
/*     */ import fiji.plugin.trackmate.util.ModelTools;
/*     */ import ij.measure.ResultsTable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.imglib2.RealLocalizable;
/*     */ import org.jgrapht.graph.DefaultWeightedEdge;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SLTResultsTableVersion
/*     */ {
/*     */   public static final String NAME = "Export statistics to tables";
/*     */   public static final String KEY = "EXPORT_STATS_TO_IJ";
/*     */   public static final String INFO_TEXT = "<html>Compute and export all statistics to 3 ImageJ results table. Statistisc are separated in features computed for: <ol> \t<li> spots in filtered tracks; \t<li> links between those spots; \t<li> filtered tracks. </ol> For tracks and links, they are recalculated prior to exporting. Note that spots and links that are not in a filtered tracks are not part of this export.</html>";
/*     */   private static final String SPOT_TABLE_NAME = "Spots in tracks statistics";
/*     */   private static final String EDGE_TABLE_NAME = "Links in tracks statistics";
/*     */   private static final String TRACK_TABLE_NAME = "Track statistics";
/*     */   private static final String ID_COLUMN = "ID";
/*     */   private static final String TRACK_ID_COLUMN = "TRACK_ID";
/*     */   private ResultsTable spotTable;
/*     */   private ResultsTable edgeTable;
/*     */   private ResultsTable trackTable;
/*     */   private final SelectionModel selectionModel;
/*     */   public static final String KEYLINEAR = "Linear track analysis";
/*     */   public static final String TRACK_TOTAL_DISTANCE_TRAVELED = "TOTAL_DISTANCE_TRAVELED";
/*     */   public static final String TRACK_MAX_DISTANCE_TRAVELED = "MAX_DISTANCE_TRAVELED";
/*     */   public static final String TRACK_CONFINMENT_RATIO = "CONFINMENT_RATIO";
/*     */   public static final String TRACK_MEAN_STRAIGHT_LINE_SPEED = "MEAN_STRAIGHT_LINE_SPEED";
/*     */   public static final String TRACK_LINEARITY_OF_FORWARD_PROGRESSION = "LINEARITY_OF_FORWARD_PROGRESSION";
/*     */   public static final String TOTAL_ABSOLUTE_ANGLE_XY = "TOTAL_ABSOLUTE_ANGLE_XY";
/*     */   public static final String TOTAL_ABSOLUTE_ANGLE_YZ = "TOTAL_ABSOLUTE_ANGLE_YZ";
/*     */   public static final String TOTAL_ABSOLUTE_ANGLE_ZX = "TOTAL_ABSOLUTE_ANGLE_ZX";
/*  87 */   public static final List<String> FEATURES = new ArrayList<>(9);
/*     */   
/*  89 */   public static final Map<String, String> FEATURE_NAMES = new HashMap<>(9);
/*     */   
/*  91 */   public static final Map<String, String> FEATURE_SHORT_NAMES = new HashMap<>(9);
/*     */   
/*  93 */   public static final Map<String, Dimension> FEATURE_DIMENSIONS = new HashMap<>(9);
/*     */   
/*  95 */   public static final Map<String, Boolean> IS_INT = new HashMap<>(9);
/*     */   
/*     */   public SLTResultsTableVersion(SelectionModel selectionModel) {
/*  98 */     this.selectionModel = selectionModel;
/*     */   }
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
/*     */   public ResultsTable executeSpot(TrackMate trackmate) {
/* 111 */     Model model = trackmate.getModel();
/* 112 */     FeatureModel fm = model.getFeatureModel();
/*     */ 
/*     */ 
/*     */     
/* 116 */     Set<Integer> trackIDs = model.getTrackModel().trackIDs(true);
/* 117 */     Collection<String> spotFeatures = trackmate.getModel().getFeatureModel().getSpotFeatures();
/*     */     
/* 119 */     this.spotTable = new ResultsTable();
/*     */ 
/*     */     
/* 122 */     for (Integer trackID : trackIDs) {
/* 123 */       Set<Spot> track = model.getTrackModel().trackSpots(trackID);
/*     */       
/* 125 */       List<Spot> sortedTrack = new ArrayList<>(track);
/* 126 */       Collections.sort(sortedTrack, Spot.frameComparator);
/*     */       
/* 128 */       for (Spot spot : sortedTrack) {
/* 129 */         this.spotTable.incrementCounter();
/* 130 */         this.spotTable.addLabel(spot.getName());
/* 131 */         this.spotTable.addValue("ID", spot.ID());
/* 132 */         this.spotTable.addValue("TRACK_ID", trackID.intValue());
/* 133 */         for (String feature : spotFeatures) {
/* 134 */           Double val = spot.getFeature(feature);
/* 135 */           if (val == null) {
/* 136 */             this.spotTable.addValue(feature, "None"); continue;
/*     */           } 
/* 138 */           if (((Boolean)fm.getSpotFeatureIsInt().get(feature)).booleanValue()) {
/* 139 */             this.spotTable.addValue(feature, val.intValue()); continue;
/*     */           } 
/* 141 */           this.spotTable.addValue(feature, val.doubleValue());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 148 */     return this.spotTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultsTable executeLink(TrackMate trackmate) {
/* 155 */     Model model = trackmate.getModel();
/* 156 */     FeatureModel fm = model.getFeatureModel();
/*     */ 
/*     */     
/* 159 */     Set<Integer> trackIDs = model.getTrackModel().trackIDs(true);
/*     */     
/* 161 */     Collection<String> edgeFeatures = fm.getEdgeFeatures();
/*     */     
/* 163 */     this.edgeTable = new ResultsTable();
/*     */ 
/*     */     
/* 166 */     for (Integer trackID : trackIDs) {
/*     */       
/* 168 */       Comparator<DefaultWeightedEdge> edgeTimeComparator = 
/* 169 */         ModelTools.featureEdgeComparator("EDGE_TIME", fm);
/* 170 */       Comparator<DefaultWeightedEdge> edgeSourceSpotTimeComparator = new EdgeSourceSpotFrameComparator(
/* 171 */           model);
/*     */       
/* 173 */       Set<DefaultWeightedEdge> track = model.getTrackModel().trackEdges(trackID);
/* 174 */       List<DefaultWeightedEdge> sortedTrack = new ArrayList<>(track);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 180 */       if (model.getFeatureModel().getEdgeFeatures().contains("Edge location")) {
/* 181 */         Collections.sort(sortedTrack, edgeTimeComparator);
/*     */       } else {
/* 183 */         Collections.sort(sortedTrack, edgeSourceSpotTimeComparator);
/*     */       } 
/* 185 */       for (DefaultWeightedEdge edge : sortedTrack) {
/* 186 */         this.edgeTable.incrementCounter();
/* 187 */         this.edgeTable.addLabel(edge.toString());
/* 188 */         this.edgeTable.addValue("TRACK_ID", trackID.intValue());
/* 189 */         for (String feature : edgeFeatures) {
/* 190 */           Object o = fm.getEdgeFeature(edge, feature);
/* 191 */           if (o instanceof String) {
/*     */             continue;
/*     */           }
/* 194 */           Number d = (Number)o;
/* 195 */           if (d == null) {
/* 196 */             this.edgeTable.addValue(feature, "None"); continue;
/*     */           } 
/* 198 */           if (((Boolean)fm.getEdgeFeatureIsInt().get(feature)).booleanValue()) {
/* 199 */             this.edgeTable.addValue(feature, d.intValue()); continue;
/*     */           } 
/* 201 */           this.edgeTable.addValue(feature, d.doubleValue());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     return this.edgeTable;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultsTable executeTrack(TrackMate trackmate) {
/* 218 */     FEATURES.add("TOTAL_DISTANCE_TRAVELED");
/* 219 */     FEATURES.add("MAX_DISTANCE_TRAVELED");
/* 220 */     FEATURES.add("CONFINMENT_RATIO");
/* 221 */     FEATURES.add("MEAN_STRAIGHT_LINE_SPEED");
/* 222 */     FEATURES.add("LINEARITY_OF_FORWARD_PROGRESSION");
/*     */     
/* 224 */     FEATURES.add("TOTAL_ABSOLUTE_ANGLE_XY");
/* 225 */     FEATURES.add("TOTAL_ABSOLUTE_ANGLE_YZ");
/* 226 */     FEATURES.add("TOTAL_ABSOLUTE_ANGLE_ZX");
/*     */     
/* 228 */     FEATURE_NAMES.put("TOTAL_DISTANCE_TRAVELED", "Total distance traveled");
/* 229 */     FEATURE_NAMES.put("MAX_DISTANCE_TRAVELED", "Max distance traveled");
/* 230 */     FEATURE_NAMES.put("CONFINMENT_RATIO", "Confinment ratio");
/* 231 */     FEATURE_NAMES.put("MEAN_STRAIGHT_LINE_SPEED", "Mean straight line speed");
/* 232 */     FEATURE_NAMES.put("LINEARITY_OF_FORWARD_PROGRESSION", "Linearity of forward progression");
/*     */ 
/*     */     
/* 235 */     FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_XY", "Absolute angle in xy plane");
/* 236 */     FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_YZ", "Absolute angle in yz plane");
/* 237 */     FEATURE_NAMES.put("TOTAL_ABSOLUTE_ANGLE_ZX", "Absolute angle in zx plane");
/*     */     
/* 239 */     FEATURE_SHORT_NAMES.put("TOTAL_DISTANCE_TRAVELED", "Total dist.");
/* 240 */     FEATURE_SHORT_NAMES.put("MAX_DISTANCE_TRAVELED", "Max dist.");
/* 241 */     FEATURE_SHORT_NAMES.put("CONFINMENT_RATIO", "Cnfnmnt ratio");
/* 242 */     FEATURE_SHORT_NAMES.put("MEAN_STRAIGHT_LINE_SPEED", "Mean v. line");
/* 243 */     FEATURE_SHORT_NAMES.put("LINEARITY_OF_FORWARD_PROGRESSION", "Lin. fwd. progr.");
/*     */ 
/*     */     
/* 246 */     FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_XY", "Abs. angle xy");
/* 247 */     FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_YZ", "Abs. angle yz");
/* 248 */     FEATURE_SHORT_NAMES.put("TOTAL_ABSOLUTE_ANGLE_ZX", "Abs. angle zx");
/*     */     
/* 250 */     FEATURE_DIMENSIONS.put("TOTAL_DISTANCE_TRAVELED", Dimension.LENGTH);
/* 251 */     FEATURE_DIMENSIONS.put("MAX_DISTANCE_TRAVELED", Dimension.LENGTH);
/* 252 */     FEATURE_DIMENSIONS.put("CONFINMENT_RATIO", Dimension.NONE);
/* 253 */     FEATURE_DIMENSIONS.put("MEAN_STRAIGHT_LINE_SPEED", Dimension.VELOCITY);
/* 254 */     FEATURE_DIMENSIONS.put("LINEARITY_OF_FORWARD_PROGRESSION", Dimension.NONE);
/*     */     
/* 256 */     FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_XY", Dimension.ANGLE);
/* 257 */     FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_YZ", Dimension.ANGLE);
/* 258 */     FEATURE_DIMENSIONS.put("TOTAL_ABSOLUTE_ANGLE_ZX", Dimension.ANGLE);
/*     */     
/* 260 */     IS_INT.put("TOTAL_DISTANCE_TRAVELED", Boolean.FALSE);
/* 261 */     IS_INT.put("MAX_DISTANCE_TRAVELED", Boolean.FALSE);
/* 262 */     IS_INT.put("CONFINMENT_RATIO", Boolean.FALSE);
/* 263 */     IS_INT.put("MEAN_STRAIGHT_LINE_SPEED", Boolean.FALSE);
/* 264 */     IS_INT.put("LINEARITY_OF_FORWARD_PROGRESSION", Boolean.FALSE);
/*     */     
/* 266 */     IS_INT.put("TOTAL_ABSOLUTE_ANGLE_XY", Boolean.FALSE);
/* 267 */     IS_INT.put("TOTAL_ABSOLUTE_ANGLE_YZ", Boolean.FALSE);
/* 268 */     IS_INT.put("TOTAL_ABSOLUTE_ANGLE_ZX", Boolean.FALSE);
/*     */ 
/*     */     
/* 271 */     Model model = trackmate.getModel();
/* 272 */     FeatureModel fm = model.getFeatureModel();
/*     */ 
/*     */     
/* 275 */     Set<Integer> trackIDs = model.getTrackModel().trackIDs(true);
/*     */ 
/*     */     
/* 278 */     this.trackTable = new ResultsTable();
/*     */ 
/*     */     
/* 281 */     for (Integer trackID : trackIDs) {
/*     */       
/* 283 */       List<Spot> spots = new ArrayList<>(model.getTrackModel().trackSpots(trackID));
/* 284 */       Collections.sort(spots, Spot.frameComparator);
/* 285 */       Spot first = spots.get(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 291 */       Set<DefaultWeightedEdge> edges = model.getTrackModel().trackEdges(trackID);
/*     */       
/* 293 */       double totalDistance = 0.0D;
/* 294 */       double maxDistanceSq = Double.NEGATIVE_INFINITY;
/* 295 */       double maxDistance = 0.0D;
/* 296 */       double dx = 0.0D;
/* 297 */       double dy = 0.0D;
/* 298 */       double dz = 0.0D;
/*     */       
/* 300 */       for (DefaultWeightedEdge edge : edges) {
/*     */         
/* 302 */         Spot source = model.getTrackModel().getEdgeSource(edge);
/* 303 */         Spot target = model.getTrackModel().getEdgeTarget(edge);
/* 304 */         double d = Math.sqrt(source.squareDistanceTo((RealLocalizable)target));
/* 305 */         totalDistance += d;
/*     */ 
/*     */         
/* 308 */         double dToFirstSq = first.squareDistanceTo((RealLocalizable)target);
/* 309 */         if (dToFirstSq > maxDistanceSq) {
/* 310 */           maxDistanceSq = dToFirstSq;
/* 311 */           maxDistance = Math.sqrt(maxDistanceSq);
/*     */         } 
/*     */         
/* 314 */         dx += target.getDoublePosition(0) - source.getDoublePosition(0);
/* 315 */         dy += target.getDoublePosition(1) - source.getDoublePosition(1);
/* 316 */         dz += target.getDoublePosition(2) - source.getDoublePosition(2);
/*     */       } 
/*     */ 
/*     */       
/* 320 */       double netDistance = fm.getTrackFeature(trackID, "TRACK_DISPLACEMENT").doubleValue();
/* 321 */       double tTotal = fm.getTrackFeature(trackID, "TRACK_DURATION").doubleValue();
/* 322 */       double vMean = fm.getTrackFeature(trackID, "TRACK_MEAN_SPEED").doubleValue();
/*     */ 
/*     */       
/* 325 */       double confinmentRatio = netDistance / totalDistance;
/* 326 */       double meanStraightLineSpeed = netDistance / tTotal;
/* 327 */       double linearityForwardProgression = meanStraightLineSpeed / vMean;
/*     */ 
/*     */ 
/*     */       
/* 331 */       double angleXY = Math.atan2(dy, dx);
/* 332 */       double angleYZ = Math.atan2(dz, dy);
/* 333 */       double angleZX = Math.atan2(dx, dz);
/* 334 */       Collection<String> trackFeatures = fm.getTrackFeatures();
/* 335 */       this.trackTable.incrementCounter();
/* 336 */       this.trackTable.addLabel(model.getTrackModel().name(trackID));
/* 337 */       this.trackTable.addValue("TRACK_ID", trackID.intValue());
/*     */       
/* 339 */       for (String feature : trackFeatures) {
/* 340 */         Double val = fm.getTrackFeature(trackID, feature);
/* 341 */         if (val == null) {
/* 342 */           this.trackTable.addValue(feature, "None"); continue;
/*     */         } 
/* 344 */         if (((Boolean)fm.getTrackFeatureIsInt().get(feature)).booleanValue()) {
/* 345 */           this.trackTable.addValue(feature, val.intValue()); continue;
/*     */         } 
/* 347 */         this.trackTable.addValue(feature, val.doubleValue());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 352 */       this.trackTable.addValue("TOTAL_DISTANCE_TRAVELED", Math.round(totalDistance * 1000.0D) / 1000.0D);
/* 353 */       this.trackTable.addValue("MAX_DISTANCE_TRAVELED", Math.round(maxDistance * 1000.0D) / 1000.0D);
/* 354 */       this.trackTable.addValue("MEAN_STRAIGHT_LINE_SPEED", 
/* 355 */           Math.round(meanStraightLineSpeed * 1000.0D) / 1000.0D);
/* 356 */       this.trackTable.addValue("LINEARITY_OF_FORWARD_PROGRESSION", 
/* 357 */           Math.round(linearityForwardProgression * 1000.0D) / 1000.0D);
/* 358 */       this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_XY", Math.round(angleXY * 1000.0D) / 1000.0D);
/* 359 */       this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_YZ", Math.round(angleYZ * 1000.0D) / 1000.0D);
/* 360 */       this.trackTable.addValue("TOTAL_ABSOLUTE_ANGLE_ZX", Math.round(angleZX * 1000.0D) / 1000.0D);
/* 361 */       this.trackTable.addValue("CONFINMENT_RATIO", Math.round(confinmentRatio * 1000.0D) / 1000.0D);
/* 362 */       this.trackTable.addValue("TRACK_CLASSIFICATION", "");
/*     */       
/* 364 */       if (confinmentRatio == 0.0D)
/* 365 */         this.trackTable.addValue("TRACK_CLASSIFICATION", "Total-Confined Track"); 
/* 366 */       if (confinmentRatio == 1.0D)
/* 367 */         this.trackTable.addValue("TRACK_CLASSIFICATION", "Perfectly Straight Track"); 
/* 368 */       if (confinmentRatio > 0.0D && confinmentRatio <= 0.5D)
/* 369 */         this.trackTable.addValue("TRACK_CLASSIFICATION", "Strongly Confined Track"); 
/* 370 */       if (confinmentRatio > 0.05D && confinmentRatio <= 0.25D)
/* 371 */         this.trackTable.addValue("TRACK_CLASSIFICATION", "Purely Random Track"); 
/* 372 */       if (confinmentRatio > 0.25D && confinmentRatio < 1.0D) {
/* 373 */         this.trackTable.addValue("TRACK_CLASSIFICATION", "Fairly Straight Track");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 378 */     return this.trackTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultsTable getEdgeTable() {
/* 389 */     return this.edgeTable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultsTable getTrackTable() {
/* 400 */     return this.trackTable;
/*     */   }
/*     */   
/*     */   private static final class EdgeSourceSpotFrameComparator
/*     */     implements Comparator<DefaultWeightedEdge> {
/*     */     private final Model model;
/*     */     
/*     */     public EdgeSourceSpotFrameComparator(Model model) {
/* 408 */       this.model = model;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compare(DefaultWeightedEdge e1, DefaultWeightedEdge e2) {
/* 413 */       double t1 = this.model.getTrackModel().getEdgeSource(e1).getFeature("FRAME").doubleValue();
/* 414 */       double t2 = this.model.getTrackModel().getEdgeSource(e2).getFeature("FRAME").doubleValue();
/* 415 */       if (t1 < t2) {
/* 416 */         return -1;
/*     */       }
/* 418 */       if (t1 > t2) {
/* 419 */         return 1;
/*     */       }
/* 421 */       return 0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/SLTResultsTableVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */