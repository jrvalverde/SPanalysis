package smileModified;

import java.io.Serializable;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.IntStream;

public interface DistanceModified<T> extends ToDoubleBiFunction<T, T>, Serializable {
   double d(T var1, T var2);

   default double apply(T x, T y) {
      return this.d(x, y);
   }

   default double applyAsDouble(T x, T y) {
      return this.d(x, y);
   }

   default MatrixModified D(T[] x) {
      int n = x.length;
      int N = n * (n - 1) / 2;
      MatrixModified D = new MatrixModified(n, n);
      IntStream.range(0, N).parallel().forEach((k) -> {
         int j = n - 2 - (int)Math.floor(Math.sqrt((double)(-8 * k + 4 * n * (n - 1) - 7)) / 2.0D - 0.5D);
         int i = k + j + 1 - n * (n - 1) / 2 + (n - j) * (n - j - 1) / 2;
         D.set(i, j, this.d(x[i], x[j]));
      });

      for(int i = 0; i < n; ++i) {
         for(int j = i + 1; j < n; ++j) {
            D.set(i, j, D.get(j, i));
         }
      }

      D.uplo(UPLOModified.LOWER);
      return D;
   }

   default MatrixModified D(T[] x, T[] y) {
      int m = x.length;
      int n = y.length;
      MatrixModified D = new MatrixModified(m, n);
      IntStream.range(0, m).parallel().forEach((i) -> {
         T xi = x[i];

         for(int j = 0; j < n; ++j) {
            D.set(i, j, this.d(xi, y[j]));
         }

      });
      return D;
   }
}
