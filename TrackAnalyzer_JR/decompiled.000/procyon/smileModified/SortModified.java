// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public interface SortModified
{
    default void swap(final int[] x, final int i, final int j) {
        final int a = x[i];
        x[i] = x[j];
        x[j] = a;
    }
    
    default void swap(final float[] x, final int i, final int j) {
        final float a = x[i];
        x[i] = x[j];
        x[j] = a;
    }
    
    default void swap(final double[] x, final int i, final int j) {
        final double a = x[i];
        x[i] = x[j];
        x[j] = a;
    }
    
    default void swap(final Object[] x, final int i, final int j) {
        final Object a = x[i];
        x[i] = x[j];
        x[j] = a;
    }
    
    default void siftUp(final int[] x, int k) {
        while (k > 1 && x[k / 2] < x[k]) {
            swap(x, k, k / 2);
            k /= 2;
        }
    }
    
    default void siftUp(final float[] x, int k) {
        while (k > 1 && x[k / 2] < x[k]) {
            swap(x, k, k / 2);
            k /= 2;
        }
    }
    
    default void siftUp(final double[] x, int k) {
        while (k > 1 && x[k / 2] < x[k]) {
            swap(x, k, k / 2);
            k /= 2;
        }
    }
    
    default <T extends Comparable<? super T>> void siftUp(final T[] x, int k) {
        while (k > 1 && x[k / 2].compareTo((Object)x[k]) < 0) {
            swap(x, k, k / 2);
            k /= 2;
        }
    }
    
    default void siftDown(final int[] x, int k, final int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && x[j] < x[j + 1]) {
                ++j;
            }
            if (x[k] >= x[j]) {
                break;
            }
            swap(x, k, j);
            k = j;
        }
    }
    
    default void siftDown(final float[] x, int k, final int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && x[j] < x[j + 1]) {
                ++j;
            }
            if (x[k] >= x[j]) {
                break;
            }
            swap(x, k, j);
            k = j;
        }
    }
    
    default void siftDown(final double[] x, int k, final int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && x[j] < x[j + 1]) {
                ++j;
            }
            if (x[k] >= x[j]) {
                break;
            }
            swap(x, k, j);
            k = j;
        }
    }
    
    default <T extends Comparable<? super T>> void siftDown(final T[] x, int k, final int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && x[j].compareTo((Object)x[j + 1]) < 0) {
                ++j;
            }
            if (x[k].compareTo((Object)x[j]) >= 0) {
                break;
            }
            swap(x, k, j);
            k = j;
        }
    }
}
