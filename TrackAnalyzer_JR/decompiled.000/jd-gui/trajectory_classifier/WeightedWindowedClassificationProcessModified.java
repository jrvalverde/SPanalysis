/*     */ package trajectory_classifier;
/*     */ 
/*     */ import ij.IJ;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import traJ.TrajectoryModified;
/*     */ import traJ.TrajectoryUtilModified;
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
/*     */ public class WeightedWindowedClassificationProcessModified
/*     */ {
/*  35 */   private double[] posConfidence = null;
/*  36 */   private double numberOmittedSegments = 0.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] windowedClassification(TrajectoryModified t, AbstractClassifierModified c, int n, int rate) {
/*  46 */     int windowsize = 2 * n + 1;
/*     */     
/*  48 */     int increment = 1;
/*  49 */     ArrayList<TrajectoryModified> tracks = new ArrayList<>();
/*  50 */     for (int i = 0; i < t.size() - windowsize + increment; i += increment) {
/*  51 */       TrajectoryModified sub = t.subList(i, i + windowsize - 1);
/*  52 */       if (rate > 1) {
/*  53 */         sub = TrajectoryUtilModified.resample(sub, rate);
/*     */       }
/*  55 */       tracks.add(sub);
/*     */     } 
/*  57 */     String[] res = c.classify(tracks);
/*  58 */     double[] confidence = c.getConfindence();
/*  59 */     for (int j = 0; j < res.length; j++) {
/*  60 */       if (res[j] == null) {
/*  61 */         return null;
/*     */       }
/*     */     } 
/*     */     
/*  65 */     String[] types = applyWeightening(res, confidence, n, t.size());
/*  66 */     return types;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] getPositionConfidence() {
/*  71 */     return this.posConfidence;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] applyWeightening(String[] res, double[] confidence, int n, int tracklength) {
/*  76 */     String[] types = new String[tracklength];
/*  77 */     this.posConfidence = new double[tracklength];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     ArrayList<String> restypes = new ArrayList<>();
/*  85 */     for (int i = 0; i < res.length; i++) {
/*  86 */       restypes.add(res[i]);
/*     */     }
/*     */     
/*  89 */     HashSet<String> uniqueTypes = new HashSet<>();
/*  90 */     uniqueTypes.addAll(restypes);
/*  91 */     HashMap<String, Integer> mapTypeToInt = new HashMap<>();
/*  92 */     HashMap<Integer, String> mapIntToType = new HashMap<>();
/*  93 */     Iterator<String> it = uniqueTypes.iterator();
/*  94 */     int key = 0;
/*  95 */     while (it.hasNext()) {
/*  96 */       String type = it.next();
/*  97 */       mapTypeToInt.put(type, Integer.valueOf(key));
/*  98 */       mapIntToType.put(Integer.valueOf(key), type);
/*  99 */       key++;
/*     */     } 
/*     */     
/* 102 */     ArrayList<Double[]> weightes = (ArrayList)new ArrayList<>();
/* 103 */     ArrayList<Integer[]> Nvotes = (ArrayList)new ArrayList<>(); int j;
/* 104 */     for (j = 0; j < tracklength; j++) {
/* 105 */       Double[] h = new Double[key];
/* 106 */       Arrays.fill((Object[])h, new Double(0.0D));
/* 107 */       weightes.add(h);
/*     */       
/* 109 */       Integer[] h1 = new Integer[key];
/* 110 */       Arrays.fill((Object[])h1, new Integer(0));
/* 111 */       Nvotes.add(h1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 116 */     for (j = 0; j < res.length; j++) {
/* 117 */       for (int k = j; k < j + 2 * n + 1; k++) {
/* 118 */         int typ = ((Integer)mapTypeToInt.get(res[j])).intValue();
/*     */         
/*     */         try {
/* 121 */           ((Double[])weightes.get(k))[typ] = Double.valueOf(((Double[])weightes.get(k))[typ].doubleValue() + confidence[j]);
/* 122 */           ((Integer[])Nvotes.get(k))[typ] = Integer.valueOf(((Integer[])Nvotes.get(k))[typ].intValue() + 1);
/*     */         }
/* 124 */         catch (Exception e) {
/*     */           
/* 126 */           IJ.log("Res: " + res[j] + " j: " + k + " i: " + j + " weigthes size " + weightes.size());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     for (j = 0; j < types.length; j++) {
/* 132 */       if (((Double[])weightes.get(j)).length > 0) {
/* 133 */         double[] result = getHighest(weightes.get(j));
/* 134 */         int mode1 = (int)result[0];
/* 135 */         double maxv = result[1];
/* 136 */         double wConf = maxv / ((Integer[])Nvotes.get(j))[mode1].intValue();
/* 137 */         String mode = mapIntToType.get(Integer.valueOf(mode1));
/* 138 */         types[j] = mode;
/* 139 */         this.posConfidence[j] = wConf;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     return types;
/*     */   }
/*     */   
/*     */   private double[] getHighest(Double[] weightes) {
/* 151 */     double max = 0.0D;
/* 152 */     int maxindex = 0;
/* 153 */     for (int i = 0; i < weightes.length; i++) {
/* 154 */       if (weightes[i].doubleValue() > max) {
/* 155 */         max = weightes[i].doubleValue();
/* 156 */         maxindex = i;
/*     */       } 
/*     */     } 
/* 159 */     return new double[] { maxindex, max };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] arrListToArray(ArrayList<Integer> l) {
/* 165 */     double[] a = new double[l.size()];
/* 166 */     for (int i = 0; i < l.size(); i++) {
/* 167 */       a[i] = ((Integer)l.get(i)).intValue();
/*     */     }
/* 169 */     return a;
/*     */   }
/*     */   
/*     */   public int[] arrListToArrayInt(ArrayList<Integer> l) {
/* 173 */     int[] a = new int[l.size()];
/* 174 */     for (int i = 0; i < l.size(); i++) {
/* 175 */       a[i] = ((Integer)l.get(i)).intValue();
/*     */     }
/* 177 */     return a;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/trajectory_classifier/WeightedWindowedClassificationProcessModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */