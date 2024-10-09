// 
// Decompiled by Procyon v0.5.36
// 

package trajectory_classifier;

import java.util.Iterator;
import ij.IJ;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Collection;
import java.util.HashSet;
import traJ.TrajectoryUtilModified;
import java.util.ArrayList;
import traJ.TrajectoryModified;

public class WeightedWindowedClassificationProcessModified
{
    private double[] posConfidence;
    private double numberOmittedSegments;
    
    public WeightedWindowedClassificationProcessModified() {
        this.posConfidence = null;
        this.numberOmittedSegments = 0.0;
    }
    
    public String[] windowedClassification(final TrajectoryModified t, final AbstractClassifierModified c, final int n, final int rate) {
        final int windowsize = 2 * n + 1;
        final int increment = 1;
        final ArrayList<TrajectoryModified> tracks = new ArrayList<TrajectoryModified>();
        for (int i = 0; i < t.size() - windowsize + increment; i += increment) {
            TrajectoryModified sub = t.subList(i, i + windowsize - 1);
            if (rate > 1) {
                sub = TrajectoryUtilModified.resample(sub, rate);
            }
            tracks.add(sub);
        }
        final String[] res = c.classify(tracks);
        final double[] confidence = c.getConfindence();
        for (int j = 0; j < res.length; ++j) {
            if (res[j] == null) {
                return null;
            }
        }
        final String[] types = this.applyWeightening(res, confidence, n, t.size());
        return types;
    }
    
    public double[] getPositionConfidence() {
        return this.posConfidence;
    }
    
    protected String[] applyWeightening(final String[] res, final double[] confidence, final int n, final int tracklength) {
        final String[] types = new String[tracklength];
        this.posConfidence = new double[tracklength];
        final ArrayList<String> restypes = new ArrayList<String>();
        for (int i = 0; i < res.length; ++i) {
            restypes.add(res[i]);
        }
        final HashSet<String> uniqueTypes = new HashSet<String>();
        uniqueTypes.addAll((Collection<?>)restypes);
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
        final ArrayList<Double[]> weightes = new ArrayList<Double[]>();
        final ArrayList<Integer[]> Nvotes = new ArrayList<Integer[]>();
        for (int j = 0; j < tracklength; ++j) {
            final Double[] h = new Double[key];
            Arrays.fill(h, new Double(0.0));
            weightes.add(h);
            final Integer[] h2 = new Integer[key];
            Arrays.fill(h2, new Integer(0));
            Nvotes.add(h2);
        }
        for (int j = 0; j < res.length; ++j) {
            for (int k = j; k < j + 2 * n + 1; ++k) {
                final int typ = mapTypeToInt.get(res[j]);
                try {
                    weightes.get(k)[typ] += confidence[j];
                    ++Nvotes.get(k)[typ];
                }
                catch (Exception e) {
                    IJ.log("Res: " + res[j] + " j: " + k + " i: " + j + " weigthes size " + weightes.size());
                }
            }
        }
        for (int j = 0; j < types.length; ++j) {
            if (weightes.get(j).length > 0) {
                final double[] result = this.getHighest(weightes.get(j));
                final int mode1 = (int)result[0];
                final double maxv = result[1];
                final double wConf = maxv / Nvotes.get(j)[mode1];
                final String mode2 = mapIntToType.get(mode1);
                types[j] = mode2;
                this.posConfidence[j] = wConf;
            }
        }
        return types;
    }
    
    private double[] getHighest(final Double[] weightes) {
        double max = 0.0;
        int maxindex = 0;
        for (int i = 0; i < weightes.length; ++i) {
            if (weightes[i] > max) {
                max = weightes[i];
                maxindex = i;
            }
        }
        return new double[] { maxindex, max };
    }
    
    public double[] arrListToArray(final ArrayList<Integer> l) {
        final double[] a = new double[l.size()];
        for (int i = 0; i < l.size(); ++i) {
            a[i] = l.get(i);
        }
        return a;
    }
    
    public int[] arrListToArrayInt(final ArrayList<Integer> l) {
        final int[] a = new int[l.size()];
        for (int i = 0; i < l.size(); ++i) {
            a[i] = l.get(i);
        }
        return a;
    }
}
