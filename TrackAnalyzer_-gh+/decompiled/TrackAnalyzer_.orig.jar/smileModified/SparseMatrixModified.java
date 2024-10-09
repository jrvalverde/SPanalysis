package smileModified;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SparseMatrixModified extends IMatrixModified implements Iterable<SparseMatrixModified.Entry> {
   private static final Logger logger = LoggerFactory.getLogger(SparseMatrixModified.class);
   private static final long serialVersionUID = 2L;
   private final int m;
   private final int n;
   private final int[] colIndex;
   private final int[] rowIndex;
   private final double[] nonzeros;

   private SparseMatrixModified(int m, int n, int nvals) {
      this.m = m;
      this.n = n;
      this.rowIndex = new int[nvals];
      this.colIndex = new int[n + 1];
      this.nonzeros = new double[nvals];
   }

   public SparseMatrixModified(int m, int n, double[] nonzeros, int[] rowIndex, int[] colIndex) {
      this.m = m;
      this.n = n;
      this.rowIndex = rowIndex;
      this.colIndex = colIndex;
      this.nonzeros = nonzeros;
   }

   public SparseMatrixModified(double[][] A) {
      this(A, 100.0D * MathExModified.EPSILON);
   }

   public SparseMatrixModified(double[][] A, double tol) {
      this.m = A.length;
      this.n = A[0].length;
      int nvals = 0;

      int k;
      int j;
      for(k = 0; k < this.m; ++k) {
         for(j = 0; j < this.n; ++j) {
            if (Math.abs(A[k][j]) >= tol) {
               ++nvals;
            }
         }
      }

      this.nonzeros = new double[nvals];
      this.rowIndex = new int[nvals];
      this.colIndex = new int[this.n + 1];
      this.colIndex[this.n] = nvals;
      k = 0;

      for(j = 0; j < this.n; ++j) {
         this.colIndex[j] = k;

         for(int i = 0; i < this.m; ++i) {
            if (Math.abs(A[i][j]) >= tol) {
               this.rowIndex[k] = i;
               this.nonzeros[k] = A[i][j];
               ++k;
            }
         }
      }

   }

   public SparseMatrixModified clone() {
      return new SparseMatrixModified(this.m, this.n, (double[])this.nonzeros.clone(), (int[])this.rowIndex.clone(), (int[])this.colIndex.clone());
   }

   public int nrow() {
      return this.m;
   }

   public int ncol() {
      return this.n;
   }

   public long size() {
      return (long)this.colIndex[this.n];
   }

   public Stream<SparseMatrixModified.Entry> nonzeros() {
      Spliterator<SparseMatrixModified.Entry> spliterator = Spliterators.spliterator(this.iterator(), this.size(), 1104);
      return StreamSupport.stream(spliterator, false);
   }

   public Stream<SparseMatrixModified.Entry> nonzeros(int beginColumn, int endColumn) {
      Spliterator<SparseMatrixModified.Entry> spliterator = Spliterators.spliterator(this.iterator(beginColumn, endColumn), (long)(this.colIndex[endColumn] - this.colIndex[beginColumn]), 1104);
      return StreamSupport.stream(spliterator, false);
   }

   public Iterator<SparseMatrixModified.Entry> iterator() {
      return this.iterator(0, this.n);
   }

   public Iterator<SparseMatrixModified.Entry> iterator(int beginColumn, final int endColumn) {
      if (beginColumn >= 0 && beginColumn < this.n) {
         if (endColumn > beginColumn && endColumn <= this.n) {
            return new Iterator<SparseMatrixModified.Entry>(beginColumn) {
               int k;
               int j;

               {
                  this.k = SparseMatrixModified.this.colIndex[var2];
                  this.j = var2;
               }

               public boolean hasNext() {
                  return this.k < SparseMatrixModified.this.colIndex[endColumn];
               }

               public SparseMatrixModified.Entry next() {
                  int i;
                  for(i = SparseMatrixModified.this.rowIndex[this.k]; this.k >= SparseMatrixModified.this.colIndex[this.j + 1]; ++this.j) {
                  }

                  return SparseMatrixModified.this.new Entry(i, this.j, this.k++, (SparseMatrixModified.Entry)null);
               }
            };
         } else {
            throw new IllegalArgumentException("Invalid end column: " + endColumn);
         }
      } else {
         throw new IllegalArgumentException("Invalid begin column: " + beginColumn);
      }
   }

   public void forEachNonZero(DoubleConsumerModified consumer) {
      for(int j = 0; j < this.n; ++j) {
         for(int k = this.colIndex[j]; k < this.colIndex[j + 1]; ++k) {
            int i = this.rowIndex[k];
            consumer.accept(i, j, this.nonzeros[k]);
         }
      }

   }

   public void forEachNonZero(int beginColumn, int endColumn, DoubleConsumerModified consumer) {
      if (beginColumn >= 0 && beginColumn < this.n) {
         if (endColumn > beginColumn && endColumn <= this.n) {
            for(int j = beginColumn; j < endColumn; ++j) {
               for(int k = this.colIndex[j]; k < this.colIndex[j + 1]; ++k) {
                  int i = this.rowIndex[k];
                  consumer.accept(i, j, this.nonzeros[k]);
               }
            }

         } else {
            throw new IllegalArgumentException("Invalid end column: " + endColumn);
         }
      } else {
         throw new IllegalArgumentException("Invalid begin column: " + beginColumn);
      }
   }

   public double get(int index) {
      return this.nonzeros[index];
   }

   public void set(int index, double value) {
      this.nonzeros[index] = value;
   }

   public double get(int i, int j) {
      if (i >= 0 && i < this.m && j >= 0 && j < this.n) {
         for(int k = this.colIndex[j]; k < this.colIndex[j + 1]; ++k) {
            if (this.rowIndex[k] == i) {
               return this.nonzeros[k];
            }
         }

         return 0.0D;
      } else {
         throw new IllegalArgumentException("Invalid index: row = " + i + " col = " + j);
      }
   }

   public void mv(TransposeModified trans, double alpha, double[] x, double beta, double[] y) {
      int k = trans == TransposeModified.NO_TRANSPOSE ? this.m : this.n;
      double[] ax = y;
      if (beta == 0.0D) {
         Arrays.fill(y, 0.0D);
      } else {
         ax = new double[k];
      }

      int i;
      int i;
      if (trans == TransposeModified.NO_TRANSPOSE) {
         for(i = 0; i < this.n; ++i) {
            for(i = this.colIndex[i]; i < this.colIndex[i + 1]; ++i) {
               int var10001 = this.rowIndex[i];
               ax[var10001] += this.nonzeros[i] * x[i];
            }
         }
      } else {
         for(i = 0; i < this.n; ++i) {
            for(i = this.colIndex[i]; i < this.colIndex[i + 1]; ++i) {
               ax[i] += this.nonzeros[i] * x[this.rowIndex[i]];
            }
         }
      }

      if (beta != 0.0D || alpha != 1.0D) {
         for(i = 0; i < k; ++i) {
            y[i] = alpha * ax[i] + beta * y[i];
         }
      }

   }

   public void mv(double[] work, int inputOffset, int outputOffset) {
      Arrays.fill(work, outputOffset, outputOffset + this.m, 0.0D);

      for(int j = 0; j < this.n; ++j) {
         for(int i = this.colIndex[j]; i < this.colIndex[j + 1]; ++i) {
            int var10001 = outputOffset + this.rowIndex[i];
            work[var10001] += this.nonzeros[i] * work[inputOffset + j];
         }
      }

   }

   public void tv(double[] work, int inputOffset, int outputOffset) {
      Arrays.fill(work, outputOffset, outputOffset + this.n, 0.0D);

      for(int i = 0; i < this.n; ++i) {
         for(int j = this.colIndex[i]; j < this.colIndex[i + 1]; ++j) {
            work[outputOffset + i] += this.nonzeros[j] * work[inputOffset + this.rowIndex[j]];
         }
      }

   }

   public SparseMatrixModified transpose() {
      SparseMatrixModified trans = new SparseMatrixModified(this.n, this.m, this.nonzeros.length);
      int[] count = new int[this.m];

      int var10002;
      int i;
      int j;
      int k;
      for(i = 0; i < this.n; ++i) {
         for(j = this.colIndex[i]; j < this.colIndex[i + 1]; ++j) {
            k = this.rowIndex[j];
            var10002 = count[k]++;
         }
      }

      for(i = 0; i < this.m; ++i) {
         trans.colIndex[i + 1] = trans.colIndex[i] + count[i];
      }

      Arrays.fill(count, 0);

      for(i = 0; i < this.n; ++i) {
         for(j = this.colIndex[i]; j < this.colIndex[i + 1]; ++j) {
            k = this.rowIndex[j];
            int index = trans.colIndex[k] + count[k];
            trans.rowIndex[index] = i;
            trans.nonzeros[index] = this.nonzeros[j];
            var10002 = count[k]++;
         }
      }

      return trans;
   }

   public SparseMatrixModified mm(SparseMatrixModified B) {
      if (this.n != B.m) {
         throw new IllegalArgumentException(String.format("Matrix dimensions do not match for matrix multiplication: %d x %d vs %d x %d", this.nrow(), this.ncol(), B.nrow(), B.ncol()));
      } else {
         int n = B.n;
         int anz = this.colIndex[n];
         int[] Bp = B.colIndex;
         int[] Bi = B.rowIndex;
         double[] Bx = B.nonzeros;
         int bnz = Bp[n];
         int[] w = new int[this.m];
         double[] abj = new double[this.m];
         int nzmax = Math.max(anz + bnz, this.m);
         SparseMatrixModified C = new SparseMatrixModified(this.m, n, nzmax);
         int[] Cp = C.colIndex;
         int[] Ci = C.rowIndex;
         double[] Cx = C.nonzeros;
         int nz = 0;

         for(int j = 0; j < n; ++j) {
            if (nz + this.m > nzmax) {
               nzmax = 2 * nzmax + this.m;
               double[] Cx2 = new double[nzmax];
               int[] Ci2 = new int[nzmax];
               System.arraycopy(Ci, 0, Ci2, 0, nz);
               System.arraycopy(Cx, 0, Cx2, 0, nz);
               Ci = Ci2;
               Cx = Cx2;
               C = new SparseMatrixModified(this.m, n, Cx2, Ci2, Cp);
            }

            Cp[j] = nz;

            int p;
            for(p = Bp[j]; p < Bp[j + 1]; ++p) {
               nz = scatter(this, Bi[p], Bx[p], w, abj, j + 1, C, nz);
            }

            for(p = Cp[j]; p < nz; ++p) {
               Cx[p] = abj[Ci[p]];
            }
         }

         Cp[n] = nz;
         return C;
      }
   }

   private static int scatter(SparseMatrixModified A, int j, double beta, int[] w, double[] x, int mark, SparseMatrixModified C, int nz) {
      int[] Ap = A.colIndex;
      int[] Ai = A.rowIndex;
      double[] Ax = A.nonzeros;
      int[] Ci = C.rowIndex;

      for(int p = Ap[j]; p < Ap[j + 1]; ++p) {
         int i = Ai[p];
         if (w[i] < mark) {
            w[i] = mark;
            Ci[nz++] = i;
            x[i] = beta * Ax[p];
         } else {
            x[i] += beta * Ax[p];
         }
      }

      return nz;
   }

   public SparseMatrixModified ata() {
      SparseMatrixModified AT = this.transpose();
      return AT.aat(this);
   }

   public SparseMatrixModified aat() {
      SparseMatrixModified AT = this.transpose();
      return this.aat(AT);
   }

   private SparseMatrixModified aat(SparseMatrixModified AT) {
      int[] done = new int[this.m];

      int nvals;
      for(nvals = 0; nvals < this.m; ++nvals) {
         done[nvals] = -1;
      }

      nvals = 0;

      int j;
      int i;
      int j;
      int k;
      for(int j = 0; j < this.m; ++j) {
         for(j = AT.colIndex[j]; j < AT.colIndex[j + 1]; ++j) {
            i = AT.rowIndex[j];

            for(j = this.colIndex[i]; j < this.colIndex[i + 1]; ++j) {
               k = this.rowIndex[j];
               if (done[k] != j) {
                  done[k] = j;
                  ++nvals;
               }
            }
         }
      }

      SparseMatrixModified aat = new SparseMatrixModified(this.m, this.m, nvals);
      nvals = 0;

      for(j = 0; j < this.m; ++j) {
         done[j] = -1;
      }

      int l;
      for(j = 0; j < this.m; ++j) {
         aat.colIndex[j] = nvals;

         for(i = AT.colIndex[j]; i < AT.colIndex[j + 1]; ++i) {
            j = AT.rowIndex[i];

            for(k = this.colIndex[j]; k < this.colIndex[j + 1]; ++k) {
               l = this.rowIndex[k];
               if (done[l] != j) {
                  done[l] = j;
                  aat.rowIndex[nvals] = l;
                  ++nvals;
               }
            }
         }
      }

      aat.colIndex[this.m] = nvals;

      for(j = 0; j < this.m; ++j) {
         if (aat.colIndex[j + 1] - aat.colIndex[j] > 1) {
            Arrays.sort(aat.rowIndex, aat.colIndex[j], aat.colIndex[j + 1]);
         }
      }

      double[] temp = new double[this.m];

      for(i = 0; i < this.m; ++i) {
         for(j = AT.colIndex[i]; j < AT.colIndex[i + 1]; ++j) {
            k = AT.rowIndex[j];

            for(l = this.colIndex[k]; l < this.colIndex[k + 1]; ++l) {
               int h = this.rowIndex[l];
               temp[h] += AT.nonzeros[j] * this.nonzeros[l];
            }
         }

         for(j = aat.colIndex[i]; j < aat.colIndex[i + 1]; ++j) {
            k = aat.rowIndex[j];
            aat.nonzeros[j] = temp[k];
            temp[k] = 0.0D;
         }
      }

      return aat;
   }

   public double[] diag() {
      int n = Math.min(this.nrow(), this.ncol());
      double[] d = new double[n];

      for(int i = 0; i < n; ++i) {
         for(int j = this.colIndex[i]; j < this.colIndex[i + 1]; ++j) {
            if (this.rowIndex[j] == i) {
               d[i] = this.nonzeros[j];
               break;
            }
         }
      }

      return d;
   }

   public static SparseMatrixModified harwell(Path path) throws IOException {
      logger.info("Reads sparse matrix file '{}'", path.toAbsolutePath());
      Throwable var1 = null;
      Object var2 = null;

      try {
         InputStream stream = Files.newInputStream(path);

         SparseMatrixModified var10000;
         try {
            Scanner scanner = new Scanner(stream);

            try {
               String line = scanner.nextLine();
               logger.info(line);
               line = scanner.nextLine().trim();
               logger.info(line);
               String[] tokens = line.split("\\s+");
               int RHSCRD = Integer.parseInt(tokens[4]);
               line = scanner.nextLine().trim();
               logger.info(line);
               if (!line.startsWith("R")) {
                  throw new UnsupportedOperationException("SparseMatrix supports only real-valued matrix.");
               }

               tokens = line.split("\\s+");
               int nrow = Integer.parseInt(tokens[1]);
               int ncol = Integer.parseInt(tokens[2]);
               int nz = Integer.parseInt(tokens[3]);
               line = scanner.nextLine();
               logger.info(line);
               if (RHSCRD > 0) {
                  line = scanner.nextLine();
                  logger.info(line);
               }

               int[] colIndex = new int[ncol + 1];
               int[] rowIndex = new int[nz];
               double[] data = new double[nz];

               int i;
               for(i = 0; i <= ncol; ++i) {
                  colIndex[i] = scanner.nextInt() - 1;
               }

               for(i = 0; i < nz; ++i) {
                  rowIndex[i] = scanner.nextInt() - 1;
               }

               for(i = 0; i < nz; ++i) {
                  data[i] = scanner.nextDouble();
               }

               var10000 = new SparseMatrixModified(nrow, ncol, data, rowIndex, colIndex);
            } finally {
               if (scanner != null) {
                  scanner.close();
               }

            }
         } catch (Throwable var25) {
            if (var1 == null) {
               var1 = var25;
            } else if (var1 != var25) {
               var1.addSuppressed(var25);
            }

            if (stream != null) {
               stream.close();
            }

            throw var1;
         }

         if (stream != null) {
            stream.close();
         }

         return var10000;
      } catch (Throwable var26) {
         if (var1 == null) {
            var1 = var26;
         } else if (var1 != var26) {
            var1.addSuppressed(var26);
         }

         throw var1;
      }
   }

   public static SparseMatrixModified rutherford(Path path) throws IOException {
      return harwell(path);
   }

   public static SparseMatrixModified text(Path path) throws IOException {
      Throwable var1 = null;
      Object var2 = null;

      try {
         InputStream stream = Files.newInputStream(path);

         SparseMatrixModified var10000;
         try {
            Scanner scanner = new Scanner(stream);

            try {
               int nrow = scanner.nextInt();
               int ncol = scanner.nextInt();
               int nz = scanner.nextInt();
               int[] colIndex = new int[ncol + 1];
               int[] rowIndex = new int[nz];
               double[] data = new double[nz];

               int i;
               for(i = 0; i <= ncol; ++i) {
                  colIndex[i] = scanner.nextInt() - 1;
               }

               for(i = 0; i < nz; ++i) {
                  rowIndex[i] = scanner.nextInt() - 1;
               }

               i = 0;

               while(true) {
                  if (i >= nz) {
                     var10000 = new SparseMatrixModified(nrow, ncol, data, rowIndex, colIndex);
                     break;
                  }

                  data[i] = scanner.nextDouble();
                  ++i;
               }
            } finally {
               if (scanner != null) {
                  scanner.close();
               }

            }
         } catch (Throwable var22) {
            if (var1 == null) {
               var1 = var22;
            } else if (var1 != var22) {
               var1.addSuppressed(var22);
            }

            if (stream != null) {
               stream.close();
            }

            throw var1;
         }

         if (stream != null) {
            stream.close();
         }

         return var10000;
      } catch (Throwable var23) {
         if (var1 == null) {
            var1 = var23;
         } else if (var1 != var23) {
            var1.addSuppressed(var23);
         }

         throw var1;
      }
   }

   public class Entry {
      public final int i;
      public final int j;
      public final double x;
      public final int index;

      private Entry(int i, int j, int index) {
         this.i = i;
         this.j = j;
         this.x = SparseMatrixModified.this.nonzeros[index];
         this.index = index;
      }

      public void update(double value) {
         SparseMatrixModified.this.nonzeros[this.index] = value;
      }

      public String toString() {
         return String.format("(%d, %d):%s", this.i, this.j, StringsModified.format(this.x));
      }

      // $FF: synthetic method
      Entry(int var2, int var3, int var4, SparseMatrixModified.Entry var5) {
         this(var2, var3, var4);
      }
   }
}
