// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.util.Comparator;

public class QuickSortModified
{
    private static final int M = 7;
    private static final int NSTACK = 64;
    
    private QuickSortModified() {
    }
    
    public static int[] sort(final int[] x) {
        final int[] order = new int[x.length];
        for (int i = 0; i < order.length; ++i) {
            order[i] = i;
        }
        sort(x, order);
        return order;
    }
    
    public static void sort(final int[] x, final int[] y) {
        sort(x, y, x.length);
    }
    
    public static void sort(final int[] x, final int[] y, final int n) {
        int jstack = -1;
        int l = 0;
        final int[] istack = new int[64];
        int ir = n - 1;
        while (true) {
            if (ir - l < 7) {
                for (int j = l + 1; j <= ir; ++j) {
                    final int a = x[j];
                    final int b = y[j];
                    int i;
                    for (i = j - 1; i >= l && x[i] > a; --i) {
                        x[i + 1] = x[i];
                        y[i + 1] = y[i];
                    }
                    x[i + 1] = a;
                    y[i + 1] = b;
                }
                if (jstack < 0) {
                    return;
                }
                ir = istack[jstack--];
                l = istack[jstack--];
            }
            else {
                final int k = l + ir >> 1;
                SortModified.swap(x, k, l + 1);
                SortModified.swap(y, k, l + 1);
                if (x[l] > x[ir]) {
                    SortModified.swap(x, l, ir);
                    SortModified.swap(y, l, ir);
                }
                if (x[l + 1] > x[ir]) {
                    SortModified.swap(x, l + 1, ir);
                    SortModified.swap(y, l + 1, ir);
                }
                if (x[l] > x[l + 1]) {
                    SortModified.swap(x, l, l + 1);
                    SortModified.swap(y, l, l + 1);
                }
                int i = l + 1;
                int j = ir;
                final int a = x[l + 1];
                final int b = y[l + 1];
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
                        SortModified.swap(y, i, j);
                    }
                }
                x[l + 1] = x[j];
                x[j] = a;
                y[l + 1] = y[j];
                y[j] = b;
                jstack += 2;
                if (jstack >= 64) {
                    throw new IllegalStateException("NSTACK too small in SortModified.");
                }
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                }
                else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }
    }
    
    public static void sort(final int[] x, final double[] y) {
        sort(x, y, x.length);
    }
    
    public static void sort(final int[] x, final double[] y, final int n) {
        int jstack = -1;
        int l = 0;
        final int[] istack = new int[64];
        int ir = n - 1;
        while (true) {
            if (ir - l < 7) {
                for (int j = l + 1; j <= ir; ++j) {
                    final int a = x[j];
                    final double b = y[j];
                    int i;
                    for (i = j - 1; i >= l && x[i] > a; --i) {
                        x[i + 1] = x[i];
                        y[i + 1] = y[i];
                    }
                    x[i + 1] = a;
                    y[i + 1] = b;
                }
                if (jstack < 0) {
                    return;
                }
                ir = istack[jstack--];
                l = istack[jstack--];
            }
            else {
                final int k = l + ir >> 1;
                SortModified.swap(x, k, l + 1);
                SortModified.swap(y, k, l + 1);
                if (x[l] > x[ir]) {
                    SortModified.swap(x, l, ir);
                    SortModified.swap(y, l, ir);
                }
                if (x[l + 1] > x[ir]) {
                    SortModified.swap(x, l + 1, ir);
                    SortModified.swap(y, l + 1, ir);
                }
                if (x[l] > x[l + 1]) {
                    SortModified.swap(x, l, l + 1);
                    SortModified.swap(y, l, l + 1);
                }
                int i = l + 1;
                int j = ir;
                final int a = x[l + 1];
                final double b = y[l + 1];
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
                        SortModified.swap(y, i, j);
                    }
                }
                x[l + 1] = x[j];
                x[j] = a;
                y[l + 1] = y[j];
                y[j] = b;
                jstack += 2;
                if (jstack >= 64) {
                    throw new IllegalStateException("NSTACK too small in SortModified.");
                }
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                }
                else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }
    }
    
    public static void sort(final int[] x, final Object[] y) {
        sort(x, y, x.length);
    }
    
    public static void sort(final int[] x, final Object[] y, final int n) {
        int jstack = -1;
        int l = 0;
        final int[] istack = new int[64];
        int ir = n - 1;
        while (true) {
            if (ir - l < 7) {
                for (int j = l + 1; j <= ir; ++j) {
                    final int a = x[j];
                    final Object b = y[j];
                    int i;
                    for (i = j - 1; i >= l && x[i] > a; --i) {
                        x[i + 1] = x[i];
                        y[i + 1] = y[i];
                    }
                    x[i + 1] = a;
                    y[i + 1] = b;
                }
                if (jstack < 0) {
                    return;
                }
                ir = istack[jstack--];
                l = istack[jstack--];
            }
            else {
                final int k = l + ir >> 1;
                SortModified.swap(x, k, l + 1);
                SortModified.swap(y, k, l + 1);
                if (x[l] > x[ir]) {
                    SortModified.swap(x, l, ir);
                    SortModified.swap(y, l, ir);
                }
                if (x[l + 1] > x[ir]) {
                    SortModified.swap(x, l + 1, ir);
                    SortModified.swap(y, l + 1, ir);
                }
                if (x[l] > x[l + 1]) {
                    SortModified.swap(x, l, l + 1);
                    SortModified.swap(y, l, l + 1);
                }
                int i = l + 1;
                int j = ir;
                final int a = x[l + 1];
                final Object b = y[l + 1];
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
                        SortModified.swap(y, i, j);
                    }
                }
                x[l + 1] = x[j];
                x[j] = a;
                y[l + 1] = y[j];
                y[j] = b;
                jstack += 2;
                if (jstack >= 64) {
                    throw new IllegalStateException("NSTACK too small in SortModified.");
                }
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                }
                else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }
    }
    
    public static int[] sort(final float[] x) {
        final int[] order = new int[x.length];
        for (int i = 0; i < order.length; ++i) {
            order[i] = i;
        }
        sort(x, order);
        return order;
    }
    
    public static void sort(final float[] x, final int[] y) {
        sort(x, y, x.length);
    }
    
    public static void sort(final float[] x, final int[] y, final int n) {
        int jstack = -1;
        int l = 0;
        final int[] istack = new int[64];
        int ir = n - 1;
        while (true) {
            if (ir - l < 7) {
                for (int j = l + 1; j <= ir; ++j) {
                    final float a = x[j];
                    final int b = y[j];
                    int i;
                    for (i = j - 1; i >= l && x[i] > a; --i) {
                        x[i + 1] = x[i];
                        y[i + 1] = y[i];
                    }
                    x[i + 1] = a;
                    y[i + 1] = b;
                }
                if (jstack < 0) {
                    return;
                }
                ir = istack[jstack--];
                l = istack[jstack--];
            }
            else {
                final int k = l + ir >> 1;
                SortModified.swap(x, k, l + 1);
                SortModified.swap(y, k, l + 1);
                if (x[l] > x[ir]) {
                    SortModified.swap(x, l, ir);
                    SortModified.swap(y, l, ir);
                }
                if (x[l + 1] > x[ir]) {
                    SortModified.swap(x, l + 1, ir);
                    SortModified.swap(y, l + 1, ir);
                }
                if (x[l] > x[l + 1]) {
                    SortModified.swap(x, l, l + 1);
                    SortModified.swap(y, l, l + 1);
                }
                int i = l + 1;
                int j = ir;
                final float a = x[l + 1];
                final int b = y[l + 1];
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
                        SortModified.swap(y, i, j);
                    }
                }
                x[l + 1] = x[j];
                x[j] = a;
                y[l + 1] = y[j];
                y[j] = b;
                jstack += 2;
                if (jstack >= 64) {
                    throw new IllegalStateException("NSTACK too small in SortModified.");
                }
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                }
                else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }
    }
    
    public static void sort(final float[] x, final float[] y) {
        sort(x, y, x.length);
    }
    
    public static void sort(final float[] x, final float[] y, final int n) {
        int jstack = -1;
        int l = 0;
        final int[] istack = new int[64];
        int ir = n - 1;
        while (true) {
            if (ir - l < 7) {
                for (int j = l + 1; j <= ir; ++j) {
                    final float a = x[j];
                    final float b = y[j];
                    int i;
                    for (i = j - 1; i >= l && x[i] > a; --i) {
                        x[i + 1] = x[i];
                        y[i + 1] = y[i];
                    }
                    x[i + 1] = a;
                    y[i + 1] = b;
                }
                if (jstack < 0) {
                    return;
                }
                ir = istack[jstack--];
                l = istack[jstack--];
            }
            else {
                final int k = l + ir >> 1;
                SortModified.swap(x, k, l + 1);
                SortModified.swap(y, k, l + 1);
                if (x[l] > x[ir]) {
                    SortModified.swap(x, l, ir);
                    SortModified.swap(y, l, ir);
                }
                if (x[l + 1] > x[ir]) {
                    SortModified.swap(x, l + 1, ir);
                    SortModified.swap(y, l + 1, ir);
                }
                if (x[l] > x[l + 1]) {
                    SortModified.swap(x, l, l + 1);
                    SortModified.swap(y, l, l + 1);
                }
                int i = l + 1;
                int j = ir;
                final float a = x[l + 1];
                final float b = y[l + 1];
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
                        SortModified.swap(y, i, j);
                    }
                }
                x[l + 1] = x[j];
                x[j] = a;
                y[l + 1] = y[j];
                y[j] = b;
                jstack += 2;
                if (jstack >= 64) {
                    throw new IllegalStateException("NSTACK too small in SortModified.");
                }
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                }
                else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }
    }
    
    public static void sort(final float[] x, final Object[] y) {
        sort(x, y, x.length);
    }
    
    public static void sort(final float[] x, final Object[] y, final int n) {
        int jstack = -1;
        int l = 0;
        final int[] istack = new int[64];
        int ir = n - 1;
        while (true) {
            if (ir - l < 7) {
                for (int j = l + 1; j <= ir; ++j) {
                    final float a = x[j];
                    final Object b = y[j];
                    int i;
                    for (i = j - 1; i >= l && x[i] > a; --i) {
                        x[i + 1] = x[i];
                        y[i + 1] = y[i];
                    }
                    x[i + 1] = a;
                    y[i + 1] = b;
                }
                if (jstack < 0) {
                    return;
                }
                ir = istack[jstack--];
                l = istack[jstack--];
            }
            else {
                final int k = l + ir >> 1;
                SortModified.swap(x, k, l + 1);
                SortModified.swap(y, k, l + 1);
                if (x[l] > x[ir]) {
                    SortModified.swap(x, l, ir);
                    SortModified.swap(y, l, ir);
                }
                if (x[l + 1] > x[ir]) {
                    SortModified.swap(x, l + 1, ir);
                    SortModified.swap(y, l + 1, ir);
                }
                if (x[l] > x[l + 1]) {
                    SortModified.swap(x, l, l + 1);
                    SortModified.swap(y, l, l + 1);
                }
                int i = l + 1;
                int j = ir;
                final float a = x[l + 1];
                final Object b = y[l + 1];
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
                        SortModified.swap(y, i, j);
                    }
                }
                x[l + 1] = x[j];
                x[j] = a;
                y[l + 1] = y[j];
                y[j] = b;
                jstack += 2;
                if (jstack >= 64) {
                    throw new IllegalStateException("NSTACK too small in SortModified.");
                }
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                }
                else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }
    }
    
    public static int[] sort(final double[] x) {
        final int[] order = new int[x.length];
        for (int i = 0; i < order.length; ++i) {
            order[i] = i;
        }
        sort(x, order);
        return order;
    }
    
    public static void sort(final double[] x, final int[] y) {
        sort(x, y, x.length);
    }
    
    public static void sort(final double[] x, final int[] y, final int n) {
        int jstack = -1;
        int l = 0;
        final int[] istack = new int[64];
        int ir = n - 1;
        while (true) {
            if (ir - l < 7) {
                for (int j = l + 1; j <= ir; ++j) {
                    final double a = x[j];
                    final int b = y[j];
                    int i;
                    for (i = j - 1; i >= l && x[i] > a; --i) {
                        x[i + 1] = x[i];
                        y[i + 1] = y[i];
                    }
                    x[i + 1] = a;
                    y[i + 1] = b;
                }
                if (jstack < 0) {
                    return;
                }
                ir = istack[jstack--];
                l = istack[jstack--];
            }
            else {
                final int k = l + ir >> 1;
                SortModified.swap(x, k, l + 1);
                SortModified.swap(y, k, l + 1);
                if (x[l] > x[ir]) {
                    SortModified.swap(x, l, ir);
                    SortModified.swap(y, l, ir);
                }
                if (x[l + 1] > x[ir]) {
                    SortModified.swap(x, l + 1, ir);
                    SortModified.swap(y, l + 1, ir);
                }
                if (x[l] > x[l + 1]) {
                    SortModified.swap(x, l, l + 1);
                    SortModified.swap(y, l, l + 1);
                }
                int i = l + 1;
                int j = ir;
                final double a = x[l + 1];
                final int b = y[l + 1];
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
                        SortModified.swap(y, i, j);
                    }
                }
                x[l + 1] = x[j];
                x[j] = a;
                y[l + 1] = y[j];
                y[j] = b;
                jstack += 2;
                if (jstack >= 64) {
                    throw new IllegalStateException("NSTACK too small in SortModified.");
                }
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                }
                else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }
    }
    
    public static void sort(final double[] x, final double[] y) {
        sort(x, y, x.length);
    }
    
    public static void sort(final double[] x, final double[] y, final int n) {
        int jstack = -1;
        int l = 0;
        final int[] istack = new int[64];
        int ir = n - 1;
        while (true) {
            if (ir - l < 7) {
                for (int j = l + 1; j <= ir; ++j) {
                    final double a = x[j];
                    final double b = y[j];
                    int i;
                    for (i = j - 1; i >= l && x[i] > a; --i) {
                        x[i + 1] = x[i];
                        y[i + 1] = y[i];
                    }
                    x[i + 1] = a;
                    y[i + 1] = b;
                }
                if (jstack < 0) {
                    return;
                }
                ir = istack[jstack--];
                l = istack[jstack--];
            }
            else {
                final int k = l + ir >> 1;
                SortModified.swap(x, k, l + 1);
                SortModified.swap(y, k, l + 1);
                if (x[l] > x[ir]) {
                    SortModified.swap(x, l, ir);
                    SortModified.swap(y, l, ir);
                }
                if (x[l + 1] > x[ir]) {
                    SortModified.swap(x, l + 1, ir);
                    SortModified.swap(y, l + 1, ir);
                }
                if (x[l] > x[l + 1]) {
                    SortModified.swap(x, l, l + 1);
                    SortModified.swap(y, l, l + 1);
                }
                int i = l + 1;
                int j = ir;
                final double a = x[l + 1];
                final double b = y[l + 1];
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
                        SortModified.swap(y, i, j);
                    }
                }
                x[l + 1] = x[j];
                x[j] = a;
                y[l + 1] = y[j];
                y[j] = b;
                jstack += 2;
                if (jstack >= 64) {
                    throw new IllegalStateException("NSTACK too small in SortModified.");
                }
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                }
                else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }
    }
    
    public static void sort(final double[] x, final Object[] y) {
        sort(x, y, x.length);
    }
    
    public static void sort(final double[] x, final Object[] y, final int n) {
        int jstack = -1;
        int l = 0;
        final int[] istack = new int[64];
        int ir = n - 1;
        while (true) {
            if (ir - l < 7) {
                for (int j = l + 1; j <= ir; ++j) {
                    final double a = x[j];
                    final Object b = y[j];
                    int i;
                    for (i = j - 1; i >= l && x[i] > a; --i) {
                        x[i + 1] = x[i];
                        y[i + 1] = y[i];
                    }
                    x[i + 1] = a;
                    y[i + 1] = b;
                }
                if (jstack < 0) {
                    return;
                }
                ir = istack[jstack--];
                l = istack[jstack--];
            }
            else {
                final int k = l + ir >> 1;
                SortModified.swap(x, k, l + 1);
                SortModified.swap(y, k, l + 1);
                if (x[l] > x[ir]) {
                    SortModified.swap(x, l, ir);
                    SortModified.swap(y, l, ir);
                }
                if (x[l + 1] > x[ir]) {
                    SortModified.swap(x, l + 1, ir);
                    SortModified.swap(y, l + 1, ir);
                }
                if (x[l] > x[l + 1]) {
                    SortModified.swap(x, l, l + 1);
                    SortModified.swap(y, l, l + 1);
                }
                int i = l + 1;
                int j = ir;
                final double a = x[l + 1];
                final Object b = y[l + 1];
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
                        SortModified.swap(y, i, j);
                    }
                }
                x[l + 1] = x[j];
                x[j] = a;
                y[l + 1] = y[j];
                y[j] = b;
                jstack += 2;
                if (jstack >= 64) {
                    throw new IllegalStateException("NSTACK too small in SortModified.");
                }
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                }
                else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }
    }
    
    public static <T extends Comparable<? super T>> int[] sort(final T[] x) {
        final int[] order = new int[x.length];
        for (int i = 0; i < order.length; ++i) {
            order[i] = i;
        }
        QuickSortModified.<T>sort(x, order);
        return order;
    }
    
    public static <T extends Comparable<? super T>> void sort(final T[] x, final int[] y) {
        QuickSortModified.<T>sort(x, y, x.length);
    }
    
    public static <T extends Comparable<? super T>> void sort(final T[] x, final int[] y, final int n) {
        int jstack = -1;
        int l = 0;
        final int[] istack = new int[64];
        int ir = n - 1;
        while (true) {
            if (ir - l < 7) {
                for (int j = l + 1; j <= ir; ++j) {
                    final T a = x[j];
                    final int b = y[j];
                    int i;
                    for (i = j - 1; i >= l && x[i].compareTo((Object)a) > 0; --i) {
                        x[i + 1] = x[i];
                        y[i + 1] = y[i];
                    }
                    x[i + 1] = a;
                    y[i + 1] = b;
                }
                if (jstack < 0) {
                    return;
                }
                ir = istack[jstack--];
                l = istack[jstack--];
            }
            else {
                final int k = l + ir >> 1;
                SortModified.swap(x, k, l + 1);
                SortModified.swap(y, k, l + 1);
                if (x[l].compareTo((Object)x[ir]) > 0) {
                    SortModified.swap(x, l, ir);
                    SortModified.swap(y, l, ir);
                }
                if (x[l + 1].compareTo((Object)x[ir]) > 0) {
                    SortModified.swap(x, l + 1, ir);
                    SortModified.swap(y, l + 1, ir);
                }
                if (x[l].compareTo((Object)x[l + 1]) > 0) {
                    SortModified.swap(x, l, l + 1);
                    SortModified.swap(y, l, l + 1);
                }
                int i = l + 1;
                int j = ir;
                final T a = x[l + 1];
                final int b = y[l + 1];
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
                        SortModified.swap(y, i, j);
                    }
                }
                x[l + 1] = x[j];
                x[j] = a;
                y[l + 1] = y[j];
                y[j] = b;
                jstack += 2;
                if (jstack >= 64) {
                    throw new IllegalStateException("NSTACK too small in SortModified.");
                }
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                }
                else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }
    }
    
    public static <T> void sort(final T[] x, final int[] y, final int n, final Comparator<T> comparator) {
        int jstack = -1;
        int l = 0;
        final int[] istack = new int[64];
        int ir = n - 1;
        while (true) {
            if (ir - l < 7) {
                for (int j = l + 1; j <= ir; ++j) {
                    final T a = x[j];
                    final int b = y[j];
                    int i;
                    for (i = j - 1; i >= l && comparator.compare(x[i], a) > 0; --i) {
                        x[i + 1] = x[i];
                        y[i + 1] = y[i];
                    }
                    x[i + 1] = a;
                    y[i + 1] = b;
                }
                if (jstack < 0) {
                    return;
                }
                ir = istack[jstack--];
                l = istack[jstack--];
            }
            else {
                final int k = l + ir >> 1;
                SortModified.swap(x, k, l + 1);
                SortModified.swap(y, k, l + 1);
                if (comparator.compare(x[l], x[ir]) > 0) {
                    SortModified.swap(x, l, ir);
                    SortModified.swap(y, l, ir);
                }
                if (comparator.compare(x[l + 1], x[ir]) > 0) {
                    SortModified.swap(x, l + 1, ir);
                    SortModified.swap(y, l + 1, ir);
                }
                if (comparator.compare(x[l], x[l + 1]) > 0) {
                    SortModified.swap(x, l, l + 1);
                    SortModified.swap(y, l, l + 1);
                }
                int i = l + 1;
                int j = ir;
                final T a = x[l + 1];
                final int b = y[l + 1];
                while (true) {
                    ++i;
                    if (comparator.compare(x[i], a) >= 0) {
                        do {
                            --j;
                        } while (comparator.compare(x[j], a) > 0);
                        if (j < i) {
                            break;
                        }
                        SortModified.swap(x, i, j);
                        SortModified.swap(y, i, j);
                    }
                }
                x[l + 1] = x[j];
                x[j] = a;
                y[l + 1] = y[j];
                y[j] = b;
                jstack += 2;
                if (jstack >= 64) {
                    throw new IllegalStateException("NSTACK too small in SortModified.");
                }
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                }
                else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }
    }
    
    public static <T extends Comparable<? super T>> void sort(final T[] x, final Object[] y) {
        QuickSortModified.<T>sort(x, y, x.length);
    }
    
    public static <T extends Comparable<? super T>> void sort(final T[] x, final Object[] y, final int n) {
        int jstack = -1;
        int l = 0;
        final int[] istack = new int[64];
        int ir = n - 1;
        while (true) {
            if (ir - l < 7) {
                for (int j = l + 1; j <= ir; ++j) {
                    final T a = x[j];
                    final Object b = y[j];
                    int i;
                    for (i = j - 1; i >= l && x[i].compareTo((Object)a) > 0; --i) {
                        x[i + 1] = x[i];
                        y[i + 1] = y[i];
                    }
                    x[i + 1] = a;
                    y[i + 1] = b;
                }
                if (jstack < 0) {
                    return;
                }
                ir = istack[jstack--];
                l = istack[jstack--];
            }
            else {
                final int k = l + ir >> 1;
                SortModified.swap(x, k, l + 1);
                SortModified.swap(y, k, l + 1);
                if (x[l].compareTo((Object)x[ir]) > 0) {
                    SortModified.swap(x, l, ir);
                    SortModified.swap(y, l, ir);
                }
                if (x[l + 1].compareTo((Object)x[ir]) > 0) {
                    SortModified.swap(x, l + 1, ir);
                    SortModified.swap(y, l + 1, ir);
                }
                if (x[l].compareTo((Object)x[l + 1]) > 0) {
                    SortModified.swap(x, l, l + 1);
                    SortModified.swap(y, l, l + 1);
                }
                int i = l + 1;
                int j = ir;
                final T a = x[l + 1];
                final Object b = y[l + 1];
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
                        SortModified.swap(y, i, j);
                    }
                }
                x[l + 1] = x[j];
                x[j] = a;
                y[l + 1] = y[j];
                y[j] = b;
                jstack += 2;
                if (jstack >= 64) {
                    throw new IllegalStateException("NSTACK too small in SortModified.");
                }
                if (ir - i + 1 >= j - l) {
                    istack[jstack] = ir;
                    istack[jstack - 1] = i;
                    ir = j - 1;
                }
                else {
                    istack[jstack] = j - 1;
                    istack[jstack - 1] = l;
                    l = i;
                }
            }
        }
    }
}
