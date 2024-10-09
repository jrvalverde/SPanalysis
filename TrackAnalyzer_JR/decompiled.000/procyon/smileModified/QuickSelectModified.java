// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public interface QuickSelectModified
{
    default int select(final int[] x, final int k) {
        final int n = x.length;
        int l;
        int ir;
        int i;
        for (l = 0, ir = n - 1; ir > l + 1; l = i) {
            final int mid = l + ir >> 1;
            SortModified.swap(x, mid, l + 1);
            if (x[l] > x[ir]) {
                SortModified.swap(x, l, ir);
            }
            if (x[l + 1] > x[ir]) {
                SortModified.swap(x, l + 1, ir);
            }
            if (x[l] > x[l + 1]) {
                SortModified.swap(x, l, l + 1);
            }
            i = l + 1;
            int j = ir;
            final int a = x[l + 1];
            while (true) {
                ++i;
                if (x[i] >= a) {
                    do {
                        --j;
                    } while (x[j] > a);
                    if (j < i) {
                        break;
                    }
                    SortModified.swap(x, i, j);
                }
            }
            x[l + 1] = x[j];
            x[j] = a;
            if (j >= k) {
                ir = j - 1;
            }
            if (j <= k) {}
        }
        if (ir == l + 1 && x[ir] < x[l]) {
            SortModified.swap(x, l, ir);
        }
        return x[k];
    }
    
    default float select(final float[] x, final int k) {
        final int n = x.length;
        int l;
        int ir;
        int i;
        for (l = 0, ir = n - 1; ir > l + 1; l = i) {
            final int mid = l + ir >> 1;
            SortModified.swap(x, mid, l + 1);
            if (x[l] > x[ir]) {
                SortModified.swap(x, l, ir);
            }
            if (x[l + 1] > x[ir]) {
                SortModified.swap(x, l + 1, ir);
            }
            if (x[l] > x[l + 1]) {
                SortModified.swap(x, l, l + 1);
            }
            i = l + 1;
            int j = ir;
            final float a = x[l + 1];
            while (true) {
                ++i;
                if (x[i] >= a) {
                    do {
                        --j;
                    } while (x[j] > a);
                    if (j < i) {
                        break;
                    }
                    SortModified.swap(x, i, j);
                }
            }
            x[l + 1] = x[j];
            x[j] = a;
            if (j >= k) {
                ir = j - 1;
            }
            if (j <= k) {}
        }
        if (ir == l + 1 && x[ir] < x[l]) {
            SortModified.swap(x, l, ir);
        }
        return x[k];
    }
    
    default double select(final double[] x, final int k) {
        final int n = x.length;
        int l;
        int ir;
        int i;
        for (l = 0, ir = n - 1; ir > l + 1; l = i) {
            final int mid = l + ir >> 1;
            SortModified.swap(x, mid, l + 1);
            if (x[l] > x[ir]) {
                SortModified.swap(x, l, ir);
            }
            if (x[l + 1] > x[ir]) {
                SortModified.swap(x, l + 1, ir);
            }
            if (x[l] > x[l + 1]) {
                SortModified.swap(x, l, l + 1);
            }
            i = l + 1;
            int j = ir;
            final double a = x[l + 1];
            while (true) {
                ++i;
                if (x[i] >= a) {
                    do {
                        --j;
                    } while (x[j] > a);
                    if (j < i) {
                        break;
                    }
                    SortModified.swap(x, i, j);
                }
            }
            x[l + 1] = x[j];
            x[j] = a;
            if (j >= k) {
                ir = j - 1;
            }
            if (j <= k) {}
        }
        if (ir == l + 1 && x[ir] < x[l]) {
            SortModified.swap(x, l, ir);
        }
        return x[k];
    }
    
    default <T extends Comparable<? super T>> T select(final T[] x, final int k) {
        final int n = x.length;
        int l;
        int ir;
        int i;
        for (l = 0, ir = n - 1; ir > l + 1; l = i) {
            final int mid = l + ir >> 1;
            SortModified.swap(x, mid, l + 1);
            if (x[l].compareTo((Object)x[ir]) > 0) {
                SortModified.swap(x, l, ir);
            }
            if (x[l + 1].compareTo((Object)x[ir]) > 0) {
                SortModified.swap(x, l + 1, ir);
            }
            if (x[l].compareTo((Object)x[l + 1]) > 0) {
                SortModified.swap(x, l, l + 1);
            }
            i = l + 1;
            int j = ir;
            final T a = x[l + 1];
            while (true) {
                ++i;
                if (x[i].compareTo((Object)a) >= 0) {
                    do {
                        --j;
                    } while (x[j].compareTo((Object)a) > 0);
                    if (j < i) {
                        break;
                    }
                    SortModified.swap(x, i, j);
                }
            }
            x[l + 1] = x[j];
            x[j] = a;
            if (j >= k) {
                ir = j - 1;
            }
            if (j <= k) {}
        }
        if (ir == l + 1 && x[ir].compareTo((Object)x[l]) < 0) {
            SortModified.swap(x, l, ir);
        }
        return x[k];
    }
    
    default int median(final int[] x) {
        final int k = x.length / 2;
        return select(x, k);
    }
    
    default float median(final float[] x) {
        final int k = x.length / 2;
        return select(x, k);
    }
    
    default double median(final double[] x) {
        final int k = x.length / 2;
        return select(x, k);
    }
    
    default <T extends Comparable<? super T>> T median(final T[] x) {
        final int k = x.length / 2;
        return QuickSelectModified.<T>select(x, k);
    }
    
    default int q1(final int[] x) {
        final int k = x.length / 4;
        return select(x, k);
    }
    
    default float q1(final float[] x) {
        final int k = x.length / 4;
        return select(x, k);
    }
    
    default double q1(final double[] x) {
        final int k = x.length / 4;
        return select(x, k);
    }
    
    default <T extends Comparable<? super T>> T q1(final T[] x) {
        final int k = x.length / 4;
        return QuickSelectModified.<T>select(x, k);
    }
    
    default int q3(final int[] x) {
        final int k = 3 * x.length / 4;
        return select(x, k);
    }
    
    default float q3(final float[] x) {
        final int k = 3 * x.length / 4;
        return select(x, k);
    }
    
    default double q3(final double[] x) {
        final int k = 3 * x.length / 4;
        return select(x, k);
    }
    
    default <T extends Comparable<? super T>> T q3(final T[] x) {
        final int k = 3 * x.length / 4;
        return QuickSelectModified.<T>select(x, k);
    }
}
