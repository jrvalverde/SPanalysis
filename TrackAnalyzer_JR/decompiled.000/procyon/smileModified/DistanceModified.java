// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.util.stream.IntStream;
import java.io.Serializable;
import java.util.function.ToDoubleBiFunction;

public interface DistanceModified<T> extends ToDoubleBiFunction<T, T>, Serializable
{
    double d(final T p0, final T p1);
    
    default double apply(final T x, final T y) {
        return this.d(x, y);
    }
    
    default double applyAsDouble(final T x, final T y) {
        return this.d(x, y);
    }
    
    default MatrixModified D(final T[] x) {
        final int n = x.length;
        final int N = n * (n - 1) / 2;
        final MatrixModified D = new MatrixModified(n, n);
        final int n2;
        final int j;
        final int i;
        final MatrixModified matrixModified;
        IntStream.range(0, N).parallel().forEach(k -> {
            j = n2 - 2 - (int)Math.floor(Math.sqrt(-8 * k + 4 * n2 * (n2 - 1) - 7) / 2.0 - 0.5);
            i = k + j + 1 - n2 * (n2 - 1) / 2 + (n2 - j) * (n2 - j - 1) / 2;
            matrixModified.set(i, j, this.d(x[i], x[j]));
            return;
        });
        for (int l = 0; l < n; ++l) {
            for (int m = l + 1; m < n; ++m) {
                D.set(l, m, D.get(m, l));
            }
        }
        D.uplo(UPLOModified.LOWER);
        return D;
    }
    
    default MatrixModified D(final T[] x, final T[] y) {
        final int m = x.length;
        final int n = y.length;
        final MatrixModified D = new MatrixModified(m, n);
        final T xi;
        int j;
        final int n2;
        final MatrixModified matrixModified;
        IntStream.range(0, m).parallel().forEach(i -> {
            xi = x[i];
            for (j = 0; j < n2; ++j) {
                matrixModified.set(i, j, this.d(xi, y[j]));
            }
            return;
        });
        return D;
    }
}
