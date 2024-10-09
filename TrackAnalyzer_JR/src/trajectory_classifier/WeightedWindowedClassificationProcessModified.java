package trajectory_classifier;

import ij.IJ;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import traJ.TrajectoryModified;
import traJ.TrajectoryUtilModified;

public class WeightedWindowedClassificationProcessModified {
   private double[] posConfidence = null;
   private double numberOmittedSegments = 0.0D;

   public String[] windowedClassification(TrajectoryModified t, AbstractClassifierModified c, int n, int rate) {
      int windowsize = 2 * n + 1;
      int increment = 1;
      ArrayList<TrajectoryModified> tracks = new ArrayList();

      for(int i = 0; i < t.size() - windowsize + increment; i += increment) {
         TrajectoryModified sub = t.subList(i, i + windowsize - 1);
         if (rate > 1) {
            sub = TrajectoryUtilModified.resample(sub, rate);
         }

         tracks.add(sub);
      }

      String[] res = c.classify(tracks);
      double[] confidence = c.getConfindence();

      for(int i = 0; i < res.length; ++i) {
         if (res[i] == null) {
            return null;
         }
      }

      String[] types = this.applyWeightening(res, confidence, n, t.size());
      return types;
   }

   public double[] getPositionConfidence() {
      return this.posConfidence;
   }

   protected String[] applyWeightening(String[] res, double[] confidence, int n, int tracklength) {
      String[] types = new String[tracklength];
      this.posConfidence = new double[tracklength];
      ArrayList<String> restypes = new ArrayList();

      for(int i = 0; i < res.length; ++i) {
         restypes.add(res[i]);
      }

      HashSet<String> uniqueTypes = new HashSet();
      uniqueTypes.addAll(restypes);
      HashMap<String, Integer> mapTypeToInt = new HashMap();
      HashMap<Integer, String> mapIntToType = new HashMap();
      Iterator<String> it = uniqueTypes.iterator();

      int key;
      for(key = 0; it.hasNext(); ++key) {
         String type = (String)it.next();
         mapTypeToInt.put(type, key);
         mapIntToType.put(key, type);
      }

      ArrayList<Double[]> weightes = new ArrayList();
      ArrayList<Integer[]> Nvotes = new ArrayList();

      int i;
      for(i = 0; i < tracklength; ++i) {
         Double[] h = new Double[key];
         Arrays.fill(h, new Double(0.0D));
         weightes.add(h);
         Integer[] h1 = new Integer[key];
         Arrays.fill(h1, new Integer(0));
         Nvotes.add(h1);
      }

      int mode1;
      for(i = 0; i < res.length; ++i) {
         for(int j = i; j < i + 2 * n + 1; ++j) {
            mode1 = (Integer)mapTypeToInt.get(res[i]);

            try {
               ((Double[])weightes.get(j))[mode1] = ((Double[])weightes.get(j))[mode1] + confidence[i];
               ((Integer[])Nvotes.get(j))[mode1] = ((Integer[])Nvotes.get(j))[mode1] + 1;
            } catch (Exception var22) {
               IJ.log("Res: " + res[i] + " j: " + j + " i: " + i + " weigthes size " + weightes.size());
            }
         }
      }

      for(i = 0; i < types.length; ++i) {
         if (((Double[])weightes.get(i)).length > 0) {
            double[] result = this.getHighest((Double[])weightes.get(i));
            mode1 = (int)result[0];
            double maxv = result[1];
            double wConf = maxv / (double)((Integer[])Nvotes.get(i))[mode1];
            String mode = (String)mapIntToType.get(mode1);
            types[i] = mode;
            this.posConfidence[i] = wConf;
         }
      }

      return types;
   }

   private double[] getHighest(Double[] weightes) {
      double max = 0.0D;
      int maxindex = 0;

      for(int i = 0; i < weightes.length; ++i) {
         if (weightes[i] > max) {
            max = weightes[i];
            maxindex = i;
         }
      }

      return new double[]{(double)maxindex, max};
   }

   public double[] arrListToArray(ArrayList<Integer> l) {
      double[] a = new double[l.size()];

      for(int i = 0; i < l.size(); ++i) {
         a[i] = (double)(Integer)l.get(i);
      }

      return a;
   }

   public int[] arrListToArrayInt(ArrayList<Integer> l) {
      int[] a = new int[l.size()];

      for(int i = 0; i < l.size(); ++i) {
         a[i] = (Integer)l.get(i);
      }

      return a;
   }
}
