package smileModified;

import java.io.Serializable;
import java.nio.DoubleBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatrixModified extends IMatrixModified {
   private static final long serialVersionUID = 3L;
   private static final Logger logger = LoggerFactory.getLogger(MatrixModified.class);
   double[] A;
   int ld;
   int m;
   int n;
   UPLOModified uplo;
   DiagModified diag;

   public MatrixModified(int m, int n) {
      this(m, n, 0.0D);
   }

   public MatrixModified(int m, int n, double a) {
      if (m > 0 && n > 0) {
         this.m = m;
         this.n = n;
         this.ld = ld(m);
         this.A = new double[this.ld * n];
         if (a != 0.0D) {
            Arrays.fill(this.A, a);
         }

      } else {
         throw new IllegalArgumentException(String.format("Invalid matrix size: %d x %d", m, n));
      }
   }

   public MatrixModified(int m, int n, int ld, double[] A) {
      if (this.layout() == LayoutModified.COL_MAJOR && ld < m) {
         throw new IllegalArgumentException(String.format("Invalid leading dimension for COL_MAJOR: %d < %d", ld, m));
      } else if (this.layout() == LayoutModified.ROW_MAJOR && ld < n) {
         throw new IllegalArgumentException(String.format("Invalid leading dimension for ROW_MAJOR: %d < %d", ld, n));
      } else {
         this.m = m;
         this.n = n;
         this.ld = ld;
         this.A = A;
      }
   }

   public static MatrixModified of(double[][] A) {
      int m = A.length;
      int n = A[0].length;
      MatrixModified matrix = new MatrixModified(m, n);

      for(int i = 0; i < m; ++i) {
         for(int j = 0; j < n; ++j) {
            matrix.set(i, j, A[i][j]);
         }
      }

      return matrix;
   }

   public static MatrixModified column(double[] A) {
      return column(A, 0, A.length);
   }

   public static MatrixModified column(double[] A, int offset, int length) {
      MatrixModified matrix = new MatrixModified(length, 1, length, new double[length]);
      System.arraycopy(A, offset, matrix.A, 0, length);
      return matrix;
   }

   public static MatrixModified row(double[] A) {
      return row(A, 0, A.length);
   }

   public static MatrixModified row(double[] A, int offset, int length) {
      MatrixModified matrix = new MatrixModified(1, length, 1, new double[length]);
      System.arraycopy(A, offset, matrix.A, 0, length);
      return matrix;
   }

   public static MatrixModified rand(int m, int n, DistributionModified distribution) {
      MatrixModified matrix = new MatrixModified(m, n);

      for(int j = 0; j < n; ++j) {
         for(int i = 0; i < m; ++i) {
            matrix.set(i, j, distribution.rand());
         }
      }

      return matrix;
   }

   public static MatrixModified randn(int m, int n) {
      return rand(m, n, GaussianDistributionModified.getInstance());
   }

   public static MatrixModified rand(int m, int n) {
      MatrixModified matrix = new MatrixModified(m, n);

      for(int j = 0; j < n; ++j) {
         for(int i = 0; i < m; ++i) {
            matrix.set(i, j, MathExModified.random());
         }
      }

      return matrix;
   }

   public static MatrixModified rand(int m, int n, double lo, double hi) {
      MatrixModified matrix = new MatrixModified(m, n);

      for(int j = 0; j < n; ++j) {
         for(int i = 0; i < m; ++i) {
            matrix.set(i, j, MathExModified.random(lo, hi));
         }
      }

      return matrix;
   }

   public static MatrixModified eye(int n) {
      return diag(n, 1.0D);
   }

   public static MatrixModified eye(int m, int n) {
      return diag(m, n, 1.0D);
   }

   public static MatrixModified diag(int n, double diag) {
      return diag(n, n, diag);
   }

   public static MatrixModified diag(int m, int n, double diag) {
      MatrixModified D = new MatrixModified(m, n);
      int k = Math.min(m, n);

      for(int i = 0; i < k; ++i) {
         D.set(i, i, diag);
      }

      return D;
   }

   public static MatrixModified diag(double[] diag) {
      int n = diag.length;
      MatrixModified D = new MatrixModified(n, n);

      for(int i = 0; i < n; ++i) {
         D.set(i, i, diag[i]);
      }

      return D;
   }

   public static MatrixModified toeplitz(double[] a) {
      int n = a.length;
      MatrixModified toeplitz = new MatrixModified(n, n);
      toeplitz.uplo(UPLOModified.LOWER);

      for(int i = 0; i < n; ++i) {
         int j;
         for(j = 0; j < i; ++j) {
            toeplitz.set(i, j, a[i - j]);
         }

         for(j = i; j < n; ++j) {
            toeplitz.set(i, j, a[j - i]);
         }
      }

      return toeplitz;
   }

   public static MatrixModified toeplitz(double[] kl, double[] ku) {
      if (kl.length != ku.length - 1) {
         throw new IllegalArgumentException(String.format("Invalid sub-diagonals and super-diagonals size: %d != %d - 1", kl.length, ku.length));
      } else {
         int n = kl.length;
         MatrixModified toeplitz = new MatrixModified(n, n);

         for(int i = 0; i < n; ++i) {
            int j;
            for(j = 0; j < i; ++j) {
               toeplitz.set(i, j, kl[i - j]);
            }

            for(j = i; j < n; ++j) {
               toeplitz.set(i, j, ku[j - i]);
            }
         }

         return toeplitz;
      }
   }

   public int nrow() {
      return this.m;
   }

   public int ncol() {
      return this.n;
   }

   public long size() {
      return (long)(this.m * this.n);
   }

   public LayoutModified layout() {
      return LayoutModified.COL_MAJOR;
   }

   public int ld() {
      return this.ld;
   }

   public boolean isSymmetric() {
      return this.uplo != null && this.diag == null;
   }

   public MatrixModified uplo(UPLOModified uplo) {
      if (this.m != this.n) {
         throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", this.m, this.n));
      } else {
         this.uplo = uplo;
         return this;
      }
   }

   public UPLOModified uplo() {
      return this.uplo;
   }

   public MatrixModified triangular(DiagModified diag) {
      if (this.m != this.n) {
         throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", this.m, this.n));
      } else {
         this.diag = diag;
         return this;
      }
   }

   public DiagModified triangular() {
      return this.diag;
   }

   public MatrixModified clone() {
      MatrixModified matrix;
      if (this.layout() == LayoutModified.COL_MAJOR) {
         matrix = new MatrixModified(this.m, this.n, this.ld, (double[])this.A.clone());
      } else {
         matrix = new MatrixModified(this.m, this.n);

         for(int j = 0; j < this.n; ++j) {
            for(int i = 0; i < this.m; ++i) {
               matrix.set(i, j, this.get(i, j));
            }
         }
      }

      if (this.m == this.n) {
         matrix.uplo(this.uplo);
         matrix.triangular(this.diag);
      }

      return matrix;
   }

   public double[][] toArray() {
      double[][] array = new double[this.m][this.n];

      for(int i = 0; i < this.m; ++i) {
         for(int j = 0; j < this.n; ++j) {
            array[i][j] = this.get(i, j);
         }
      }

      return array;
   }

   public MatrixModified set(MatrixModified b) {
      this.m = b.m;
      this.n = b.n;
      this.diag = b.diag;
      this.uplo = b.uplo;
      if (this.layout() == b.layout()) {
         this.A = b.A;
         this.ld = b.ld;
      } else {
         int j;
         int i;
         if (this.layout() == LayoutModified.COL_MAJOR) {
            this.ld = ld(this.m);
            this.A = new double[this.ld * this.n];

            for(j = 0; j < this.n; ++j) {
               for(i = 0; i < this.m; ++i) {
                  this.set(i, j, this.get(i, j));
               }
            }
         } else {
            this.ld = ld(this.n);
            this.A = new double[this.ld * this.m];

            for(j = 0; j < this.m; ++j) {
               for(i = 0; i < this.n; ++i) {
                  this.set(j, i, this.get(j, i));
               }
            }
         }
      }

      return this;
   }

   protected int index(int i, int j) {
      return j * this.ld + i;
   }

   public double get(int i, int j) {
      return this.A[this.index(i, j)];
   }

   public void set(int i, int j, double x) {
      this.A[this.index(i, j)] = x;
   }

   public MatrixModified get(int[] rows, int[] cols) {
      MatrixModified sub = new MatrixModified(rows.length, cols.length);

      for(int j = 0; j < cols.length; ++j) {
         int col = cols[j];
         if (col < 0) {
            col += this.n;
         }

         for(int i = 0; i < rows.length; ++i) {
            int row = rows[i];
            if (row < 0) {
               row += this.m;
            }

            sub.set(i, j, this.get(row, col));
         }
      }

      return sub;
   }

   public double[] row(int i) {
      double[] x = new double[this.n];
      if (i < 0) {
         i += this.m;
      }

      if (this.layout() == LayoutModified.COL_MAJOR) {
         for(int j = 0; j < this.n; ++j) {
            x[j] = this.get(i, j);
         }
      } else {
         System.arraycopy(this.A, this.index(i, 0), x, 0, this.n);
      }

      return x;
   }

   public double[] col(int j) {
      double[] x = new double[this.m];
      if (j < 0) {
         j += this.n;
      }

      if (this.layout() == LayoutModified.COL_MAJOR) {
         System.arraycopy(this.A, this.index(0, j), x, 0, this.m);
      } else {
         for(int i = 0; i < this.m; ++i) {
            x[i] = this.get(i, j);
         }
      }

      return x;
   }

   public MatrixModified rows(int... rows) {
      MatrixModified x = new MatrixModified(rows.length, this.n);

      for(int i = 0; i < rows.length; ++i) {
         int row = rows[i];
         if (row < 0) {
            row += this.m;
         }

         if (this.layout() == LayoutModified.COL_MAJOR) {
            for(int j = 0; j < this.n; ++j) {
               x.set(i, j, this.get(row, j));
            }
         } else {
            System.arraycopy(this.A, this.index(row, 0), x.A, x.index(i, 0), this.n);
         }
      }

      return x;
   }

   public MatrixModified cols(int... cols) {
      MatrixModified x = new MatrixModified(this.m, cols.length);

      for(int j = 0; j < cols.length; ++j) {
         int col = cols[j];
         if (col < 0) {
            col += this.n;
         }

         if (this.layout() == LayoutModified.COL_MAJOR) {
            System.arraycopy(this.A, this.index(0, col), x.A, x.index(0, j), this.m);
         } else {
            for(int i = 0; i < this.m; ++i) {
               x.set(i, j, this.get(i, col));
            }
         }
      }

      return x;
   }

   public MatrixModified submatrix(int i, int j, int k, int l) {
      if (i >= 0 && i < this.m && k >= i && k < this.m && j >= 0 && j < this.n && l >= j && l < this.n) {
         MatrixModified sub = new MatrixModified(k - i + 1, l - j + 1);

         for(int jj = j; jj <= l; ++jj) {
            for(int ii = i; ii <= k; ++ii) {
               sub.set(ii - i, jj - j, this.get(ii, jj));
            }
         }

         return sub;
      } else {
         throw new IllegalArgumentException(String.format("Invalid submatrix range (%d:%d, %d:%d) of %d x %d", i, k, j, l, this.m, this.n));
      }
   }

   public void fill(double x) {
      Arrays.fill(this.A, x);
   }

   public MatrixModified transpose() {
      return this.transpose(true);
   }

   public MatrixModified transpose(boolean share) {
      Object matrix;
      if (share) {
         if (this.layout() == LayoutModified.ROW_MAJOR) {
            matrix = new MatrixModified(this.n, this.m, this.ld, this.A);
         } else {
            matrix = new MatrixModified.RowMajor(this.n, this.m, this.ld, this.A);
         }
      } else {
         matrix = new MatrixModified(this.n, this.m);

         for(int j = 0; j < this.m; ++j) {
            for(int i = 0; i < this.n; ++i) {
               ((MatrixModified)matrix).set(i, j, this.get(j, i));
            }
         }
      }

      if (this.m == this.n) {
         ((MatrixModified)matrix).uplo(this.uplo);
         ((MatrixModified)matrix).triangular(this.diag);
      }

      return (MatrixModified)matrix;
   }

   public boolean equals(Object o) {
      return !(o instanceof MatrixModified) ? false : this.equals((MatrixModified)o, 1.0E-10D);
   }

   public boolean equals(MatrixModified o, double epsilon) {
      if (this.m == o.m && this.n == o.n) {
         for(int j = 0; j < this.n; ++j) {
            for(int i = 0; i < this.m; ++i) {
               if (!MathExModified.isZero(this.get(i, j) - o.get(i, j), epsilon)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public double add(int i, int j, double b) {
      double[] var10000 = this.A;
      int var10001 = this.index(i, j);
      return var10000[var10001] += b;
   }

   public double sub(int i, int j, double b) {
      double[] var10000 = this.A;
      int var10001 = this.index(i, j);
      return var10000[var10001] -= b;
   }

   public double mul(int i, int j, double b) {
      double[] var10000 = this.A;
      int var10001 = this.index(i, j);
      return var10000[var10001] *= b;
   }

   public double div(int i, int j, double b) {
      double[] var10000 = this.A;
      int var10001 = this.index(i, j);
      return var10000[var10001] /= b;
   }

   public MatrixModified addDiag(double b) {
      int l = Math.min(this.m, this.n);

      for(int i = 0; i < l; ++i) {
         double[] var10000 = this.A;
         int var10001 = this.index(i, i);
         var10000[var10001] += b;
      }

      return this;
   }

   public MatrixModified addDiag(double[] b) {
      int l = Math.min(this.m, this.n);
      if (b.length != l) {
         throw new IllegalArgumentException("Invalid diagonal array size: " + b.length);
      } else {
         for(int i = 0; i < l; ++i) {
            double[] var10000 = this.A;
            int var10001 = this.index(i, i);
            var10000[var10001] += b[i];
         }

         return this;
      }
   }

   public MatrixModified add(double b) {
      for(int i = 0; i < this.A.length; ++i) {
         double[] var10000 = this.A;
         var10000[i] += b;
      }

      return this;
   }

   public MatrixModified sub(double b) {
      for(int i = 0; i < this.A.length; ++i) {
         double[] var10000 = this.A;
         var10000[i] -= b;
      }

      return this;
   }

   public MatrixModified mul(double b) {
      for(int i = 0; i < this.A.length; ++i) {
         double[] var10000 = this.A;
         var10000[i] *= b;
      }

      return this;
   }

   public MatrixModified div(double b) {
      for(int i = 0; i < this.A.length; ++i) {
         double[] var10000 = this.A;
         var10000[i] /= b;
      }

      return this;
   }

   public MatrixModified add(MatrixModified B) {
      if (this.m == B.m && this.n == B.n) {
         int j;
         if (this.layout() == B.layout() && this.ld == B.ld) {
            for(j = 0; j < this.A.length; ++j) {
               double[] var10000 = this.A;
               var10000[j] += B.A[j];
            }
         } else {
            for(j = 0; j < this.n; ++j) {
               for(int i = 0; i < this.m; ++i) {
                  this.add(i, j, B.get(i, j));
               }
            }
         }

         return this;
      } else {
         throw new IllegalArgumentException("Matrix is not of same size.");
      }
   }

   public MatrixModified sub(MatrixModified B) {
      if (this.m == B.m && this.n == B.n) {
         int j;
         if (this.layout() == B.layout() && this.ld == B.ld) {
            for(j = 0; j < this.A.length; ++j) {
               double[] var10000 = this.A;
               var10000[j] -= B.A[j];
            }
         } else {
            for(j = 0; j < this.n; ++j) {
               for(int i = 0; i < this.m; ++i) {
                  this.sub(i, j, B.get(i, j));
               }
            }
         }

         return this;
      } else {
         throw new IllegalArgumentException("Matrix is not of same size.");
      }
   }

   public MatrixModified mul(MatrixModified B) {
      if (this.m == B.m && this.n == B.n) {
         int j;
         if (this.layout() == B.layout() && this.ld == B.ld) {
            for(j = 0; j < this.A.length; ++j) {
               double[] var10000 = this.A;
               var10000[j] *= B.A[j];
            }
         } else {
            for(j = 0; j < this.n; ++j) {
               for(int i = 0; i < this.m; ++i) {
                  this.mul(i, j, B.get(i, j));
               }
            }
         }

         return this;
      } else {
         throw new IllegalArgumentException("Matrix is not of same size.");
      }
   }

   public MatrixModified div(MatrixModified B) {
      if (this.m == B.m && this.n == B.n) {
         int j;
         if (this.layout() == B.layout() && this.ld == B.ld) {
            for(j = 0; j < this.A.length; ++j) {
               double[] var10000 = this.A;
               var10000[j] /= B.A[j];
            }
         } else {
            for(j = 0; j < this.n; ++j) {
               for(int i = 0; i < this.m; ++i) {
                  this.div(i, j, B.get(i, j));
               }
            }
         }

         return this;
      } else {
         throw new IllegalArgumentException("Matrix is not of same size.");
      }
   }

   public MatrixModified add(double beta, MatrixModified B) {
      if (this.m == B.m && this.n == B.n) {
         int j;
         if (this.layout() == B.layout() && this.ld == B.ld) {
            for(j = 0; j < this.A.length; ++j) {
               double[] var10000 = this.A;
               var10000[j] += beta * B.A[j];
            }
         } else {
            for(j = 0; j < this.n; ++j) {
               for(int i = 0; i < this.m; ++i) {
                  this.add(i, j, beta * B.get(i, j));
               }
            }
         }

         return this;
      } else {
         throw new IllegalArgumentException("Matrix is not of same size.");
      }
   }

   public MatrixModified add(double alpha, double beta, MatrixModified B) {
      if (this.m == B.m && this.n == B.n) {
         int j;
         if (this.layout() == B.layout() && this.ld == B.ld) {
            for(j = 0; j < this.A.length; ++j) {
               this.A[j] = alpha * this.A[j] + beta * B.A[j];
            }
         } else {
            for(j = 0; j < this.n; ++j) {
               for(int i = 0; i < this.m; ++i) {
                  this.set(i, j, alpha * this.get(i, j) + beta * B.get(i, j));
               }
            }
         }

         return this;
      } else {
         throw new IllegalArgumentException("Matrix B is not of same size.");
      }
   }

   public MatrixModified add2(double alpha, double beta, MatrixModified B) {
      if (this.m == B.m && this.n == B.n) {
         int j;
         if (this.layout() == B.layout() && this.ld == B.ld) {
            for(j = 0; j < this.A.length; ++j) {
               this.A[j] = alpha * this.A[j] + beta * B.A[j] * B.A[j];
            }
         } else {
            for(j = 0; j < this.n; ++j) {
               for(int i = 0; i < this.m; ++i) {
                  this.set(i, j, alpha * this.get(i, j) + beta * B.get(i, j) * B.get(i, j));
               }
            }
         }

         return this;
      } else {
         throw new IllegalArgumentException("Matrix B is not of same size.");
      }
   }

   public MatrixModified add(double alpha, MatrixModified A, double beta, MatrixModified B) {
      if (this.m == A.m && this.n == A.n) {
         if (this.m == B.m && this.n == B.n) {
            int j;
            if (this.layout() == A.layout() && this.layout() == B.layout() && this.ld == A.ld && this.ld == B.ld) {
               for(j = 0; j < this.A.length; ++j) {
                  this.A[j] = alpha * A.A[j] + beta * B.A[j];
               }
            } else {
               for(j = 0; j < this.n; ++j) {
                  for(int i = 0; i < this.m; ++i) {
                     this.set(i, j, alpha * A.get(i, j) + beta * B.get(i, j));
                  }
               }
            }

            return this;
         } else {
            throw new IllegalArgumentException("Matrix B is not of same size.");
         }
      } else {
         throw new IllegalArgumentException("Matrix A is not of same size.");
      }
   }

   public MatrixModified add(double alpha, double[] x, double[] y) {
      if (this.m == x.length && this.n == y.length) {
         if (this.isSymmetric() && x == y) {
            BLASModified.engine.syr(this.layout(), this.uplo, this.m, alpha, (double[])x, 1, (double[])this.A, this.ld);
         } else {
            BLASModified.engine.ger(this.layout(), this.m, this.n, alpha, (double[])x, 1, (double[])y, 1, (double[])this.A, this.ld);
         }

         return this;
      } else {
         throw new IllegalArgumentException("Matrix is not of same size.");
      }
   }

   public MatrixModified replaceNaN(double x) {
      for(int i = 0; i < this.A.length; ++i) {
         if (Double.isNaN(this.A[i])) {
            this.A[i] = x;
         }
      }

      return this;
   }

   public double sum() {
      double s = 0.0D;

      for(int j = 0; j < this.n; ++j) {
         for(int i = 0; i < this.m; ++i) {
            s += this.get(i, j);
         }
      }

      return s;
   }

   public double norm1() {
      double f = 0.0D;

      for(int j = 0; j < this.n; ++j) {
         double s = 0.0D;

         for(int i = 0; i < this.m; ++i) {
            s += Math.abs(this.get(i, j));
         }

         f = Math.max(f, s);
      }

      return f;
   }

   public double norm2() {
      return this.svd(false, false).s[0];
   }

   public double norm() {
      return this.norm2();
   }

   public double normInf() {
      double[] f = new double[this.m];

      for(int j = 0; j < this.n; ++j) {
         for(int i = 0; i < this.m; ++i) {
            f[i] += Math.abs(this.get(i, j));
         }
      }

      return MathExModified.max(f);
   }

   public double normFro() {
      double f = 0.0D;

      for(int j = 0; j < this.n; ++j) {
         for(int i = 0; i < this.m; ++i) {
            f = Math.hypot(f, this.get(i, j));
         }
      }

      return f;
   }

   public double xAx(double[] x) {
      if (this.m != this.n) {
         throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", this.m, this.n));
      } else if (this.n != x.length) {
         throw new IllegalArgumentException(String.format("Matrix: %d x %d, Vector: %d", this.m, this.n, x.length));
      } else {
         double[] Ax = this.mv(x);
         return MathExModified.dot(x, Ax);
      }
   }

   public double[] rowSums() {
      double[] x = new double[this.m];

      for(int j = 0; j < this.n; ++j) {
         for(int i = 0; i < this.m; ++i) {
            x[i] += this.get(i, j);
         }
      }

      return x;
   }

   public double[] rowMeans() {
      double[] x = this.rowSums();

      for(int i = 0; i < this.m; ++i) {
         x[i] /= (double)this.n;
      }

      return x;
   }

   public double[] rowSds() {
      double[] x = new double[this.m];
      double[] x2 = new double[this.m];

      int i;
      for(i = 0; i < this.n; ++i) {
         for(int i = 0; i < this.m; ++i) {
            double a = this.get(i, i);
            x[i] += a;
            x2[i] += a * a;
         }
      }

      for(i = 0; i < this.m; ++i) {
         double mu = x[i] / (double)this.n;
         x[i] = Math.sqrt(x2[i] / (double)this.n - mu * mu);
      }

      return x;
   }

   public double[] colSums() {
      double[] x = new double[this.n];

      for(int j = 0; j < this.n; ++j) {
         for(int i = 0; i < this.m; ++i) {
            x[j] += this.get(i, j);
         }
      }

      return x;
   }

   public double[] colMeans() {
      double[] x = this.colSums();

      for(int j = 0; j < this.n; ++j) {
         x[j] /= (double)this.m;
      }

      return x;
   }

   public double[] colSds() {
      double[] x = new double[this.n];

      for(int j = 0; j < this.n; ++j) {
         double mu = 0.0D;
         double sumsq = 0.0D;

         for(int i = 0; i < this.m; ++i) {
            double a = this.get(i, j);
            mu += a;
            sumsq += a * a;
         }

         mu /= (double)this.m;
         x[j] = Math.sqrt(sumsq / (double)this.m - mu * mu);
      }

      return x;
   }

   public MatrixModified standardize() {
      double[] center = this.colMeans();
      double[] scale = this.colSds();
      return this.scale(center, scale);
   }

   public MatrixModified scale(double[] center, double[] scale) {
      if (center == null && scale == null) {
         throw new IllegalArgumentException("Both center and scale are null");
      } else {
         MatrixModified matrix = new MatrixModified(this.m, this.n);
         int j;
         int i;
         if (center == null) {
            for(j = 0; j < this.n; ++j) {
               for(i = 0; i < this.m; ++i) {
                  matrix.set(i, j, this.get(i, j) / scale[j]);
               }
            }
         } else if (scale == null) {
            for(j = 0; j < this.n; ++j) {
               for(i = 0; i < this.m; ++i) {
                  matrix.set(i, j, this.get(i, j) - center[j]);
               }
            }
         } else {
            for(j = 0; j < this.n; ++j) {
               for(i = 0; i < this.m; ++i) {
                  matrix.set(i, j, (this.get(i, j) - center[j]) / scale[j]);
               }
            }
         }

         return matrix;
      }
   }

   public MatrixModified inverse() {
      if (this.m != this.n) {
         throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", this.m, this.n));
      } else {
         MatrixModified lu = this.clone();
         MatrixModified inv = eye(this.n);
         int[] ipiv = new int[this.n];
         int info;
         if (this.isSymmetric()) {
            info = LAPACKModified.engine.sysv(lu.layout(), this.uplo, this.n, this.n, lu.A, lu.ld, ipiv, inv.A, inv.ld);
            if (info != 0) {
               throw new ArithmeticException("SYSV fails: " + info);
            }
         } else {
            info = LAPACKModified.engine.gesv(lu.layout(), this.n, this.n, lu.A, lu.ld, ipiv, inv.A, inv.ld);
            if (info != 0) {
               throw new ArithmeticException("GESV fails: " + info);
            }
         }

         return inv;
      }
   }

   private void mv(TransposeModified trans, double alpha, DoubleBuffer x, double beta, DoubleBuffer y) {
      DoubleBuffer A = DoubleBuffer.wrap(this.A);
      if (this.uplo != null) {
         if (this.diag != null) {
            if (alpha == 1.0D && beta == 0.0D && x == y) {
               BLASModified.engine.trmv(this.layout(), this.uplo, trans, this.diag, this.m, (DoubleBuffer)A, this.ld, (DoubleBuffer)y, 1);
            } else {
               BLASModified.engine.gemv(this.layout(), trans, this.m, this.n, alpha, (DoubleBuffer)A, this.ld, (DoubleBuffer)x, 1, beta, (DoubleBuffer)y, 1);
            }
         } else {
            BLASModified.engine.symv(this.layout(), this.uplo, this.m, alpha, (DoubleBuffer)A, this.ld, (DoubleBuffer)x, 1, beta, (DoubleBuffer)y, 1);
         }
      } else {
         BLASModified.engine.gemv(this.layout(), trans, this.m, this.n, alpha, (DoubleBuffer)A, this.ld, (DoubleBuffer)x, 1, beta, (DoubleBuffer)y, 1);
      }

   }

   public void mv(TransposeModified trans, double alpha, double[] x, double beta, double[] y) {
      if (this.uplo != null) {
         if (this.diag != null) {
            if (alpha == 1.0D && beta == 0.0D && x == y) {
               BLASModified.engine.trmv(this.layout(), this.uplo, trans, this.diag, this.m, (double[])this.A, this.ld, (double[])y, 1);
            } else {
               BLASModified.engine.gemv(this.layout(), trans, this.m, this.n, alpha, (double[])this.A, this.ld, (double[])x, 1, beta, (double[])y, 1);
            }
         } else {
            BLASModified.engine.symv(this.layout(), this.uplo, this.m, alpha, (double[])this.A, this.ld, (double[])x, 1, beta, (double[])y, 1);
         }
      } else {
         BLASModified.engine.gemv(this.layout(), trans, this.m, this.n, alpha, (double[])this.A, this.ld, (double[])x, 1, beta, (double[])y, 1);
      }

   }

   public void mv(double[] work, int inputOffset, int outputOffset) {
      DoubleBuffer xb = DoubleBuffer.wrap(work, inputOffset, this.n);
      DoubleBuffer yb = DoubleBuffer.wrap(work, outputOffset, this.m);
      this.mv(TransposeModified.NO_TRANSPOSE, 1.0D, xb, 0.0D, yb);
   }

   public void tv(double[] work, int inputOffset, int outputOffset) {
      DoubleBuffer xb = DoubleBuffer.wrap(work, inputOffset, this.m);
      DoubleBuffer yb = DoubleBuffer.wrap(work, outputOffset, this.n);
      this.mv(TransposeModified.TRANSPOSE, 1.0D, xb, 0.0D, yb);
   }

   public MatrixModified mm(TransposeModified transA, MatrixModified A, TransposeModified transB, MatrixModified B) {
      return this.mm(transA, A, transB, B, 1.0D, 0.0D);
   }

   public MatrixModified mm(TransposeModified transA, MatrixModified A, TransposeModified transB, MatrixModified B, double alpha, double beta) {
      if (A.isSymmetric() && transB == TransposeModified.NO_TRANSPOSE && B.layout() == this.layout()) {
         BLASModified.engine.symm(this.layout(), SideModified.LEFT, A.uplo, this.m, this.n, alpha, A.A, A.ld, B.A, B.ld, beta, this.A, this.ld);
      } else if (B.isSymmetric() && transA == TransposeModified.NO_TRANSPOSE && A.layout() == this.layout()) {
         BLASModified.engine.symm(this.layout(), SideModified.RIGHT, B.uplo, this.m, this.n, alpha, B.A, B.ld, A.A, A.ld, beta, this.A, this.ld);
      } else {
         if (this.layout() != A.layout()) {
            transA = flip(transA);
            A = A.transpose();
         }

         if (this.layout() != B.layout()) {
            transB = flip(transB);
            B = B.transpose();
         }

         int k = transA == TransposeModified.NO_TRANSPOSE ? A.n : A.m;
         BLASModified.engine.gemm(this.layout(), transA, transB, this.m, this.n, k, alpha, A.A, A.ld, B.A, B.ld, beta, this.A, this.ld);
      }

      return this;
   }

   public MatrixModified ata() {
      MatrixModified C = new MatrixModified(this.n, this.n);
      C.mm(TransposeModified.TRANSPOSE, this, TransposeModified.NO_TRANSPOSE, this);
      C.uplo(UPLOModified.LOWER);
      return C;
   }

   public MatrixModified aat() {
      MatrixModified C = new MatrixModified(this.m, this.m);
      C.mm(TransposeModified.NO_TRANSPOSE, this, TransposeModified.TRANSPOSE, this);
      C.uplo(UPLOModified.LOWER);
      return C;
   }

   public static MatrixModified adb(TransposeModified transA, MatrixModified A, double[] D, TransposeModified transB, MatrixModified B) {
      int m = A.m;
      int n = A.n;
      MatrixModified AD;
      int j;
      double dj;
      int i;
      if (transA == TransposeModified.NO_TRANSPOSE) {
         AD = new MatrixModified(m, n);

         for(j = 0; j < n; ++j) {
            dj = D[j];

            for(i = 0; i < m; ++i) {
               AD.set(i, j, dj * A.get(i, j));
            }
         }
      } else {
         AD = new MatrixModified(n, m);

         for(j = 0; j < m; ++j) {
            dj = D[j];

            for(i = 0; i < n; ++i) {
               AD.set(i, j, dj * A.get(j, i));
            }
         }
      }

      return transB == TransposeModified.NO_TRANSPOSE ? AD.mm(B) : AD.mt(B);
   }

   public MatrixModified mm(MatrixModified B) {
      if (this.n != B.m) {
         throw new IllegalArgumentException(String.format("Matrix multiplication A * B: %d x %d vs %d x %d", this.m, this.n, B.m, B.n));
      } else {
         MatrixModified C = new MatrixModified(this.m, B.n);
         C.mm(TransposeModified.NO_TRANSPOSE, this, TransposeModified.NO_TRANSPOSE, B);
         return C;
      }
   }

   public MatrixModified mt(MatrixModified B) {
      if (this.n != B.n) {
         throw new IllegalArgumentException(String.format("Matrix multiplication A * B': %d x %d vs %d x %d", this.m, this.n, B.m, B.n));
      } else {
         MatrixModified C = new MatrixModified(this.m, B.m);
         C.mm(TransposeModified.NO_TRANSPOSE, this, TransposeModified.TRANSPOSE, B);
         return C;
      }
   }

   public MatrixModified tm(MatrixModified B) {
      if (this.m != B.m) {
         throw new IllegalArgumentException(String.format("Matrix multiplication A' * B: %d x %d vs %d x %d", this.m, this.n, B.m, B.n));
      } else {
         MatrixModified C = new MatrixModified(this.n, B.n);
         C.mm(TransposeModified.TRANSPOSE, this, TransposeModified.NO_TRANSPOSE, B);
         return C;
      }
   }

   public MatrixModified tt(MatrixModified B) {
      if (this.m != B.n) {
         throw new IllegalArgumentException(String.format("Matrix multiplication A' * B': %d x %d vs %d x %d", this.m, this.n, B.m, B.n));
      } else {
         MatrixModified C = new MatrixModified(this.n, B.m);
         C.mm(TransposeModified.TRANSPOSE, this, TransposeModified.TRANSPOSE, B);
         return C;
      }
   }

   public MatrixModified.LU lu() {
      return this.lu(false);
   }

   public MatrixModified.LU lu(boolean overwrite) {
      MatrixModified lu = overwrite ? this : this.clone();
      int[] ipiv = new int[Math.min(this.m, this.n)];
      int info = LAPACKModified.engine.getrf(lu.layout(), lu.m, lu.n, lu.A, lu.ld, ipiv);
      if (info < 0) {
         logger.error("LAPACK GETRF error code: {}", info);
         throw new ArithmeticException("LAPACK GETRF error code: " + info);
      } else {
         lu.uplo = null;
         return new MatrixModified.LU(lu, ipiv, info);
      }
   }

   public MatrixModified.Cholesky cholesky() {
      return this.cholesky(false);
   }

   public MatrixModified.Cholesky cholesky(boolean overwrite) {
      if (this.uplo == null) {
         throw new IllegalArgumentException("The matrix is not symmetric");
      } else {
         MatrixModified lu = overwrite ? this : this.clone();
         int info = LAPACKModified.engine.potrf(lu.layout(), lu.uplo, lu.n, lu.A, lu.ld);
         if (info != 0) {
            logger.error("LAPACK GETRF error code: {}", info);
            throw new ArithmeticException("LAPACK GETRF error code: " + info);
         } else {
            return new MatrixModified.Cholesky(lu);
         }
      }
   }

   public MatrixModified.QR qr() {
      return this.qr(false);
   }

   public MatrixModified.QR qr(boolean overwrite) {
      MatrixModified qr = overwrite ? this : this.clone();
      double[] tau = new double[Math.min(this.m, this.n)];
      int info = LAPACKModified.engine.geqrf(qr.layout(), qr.m, qr.n, qr.A, qr.ld, tau);
      if (info != 0) {
         logger.error("LAPACK GEQRF error code: {}", info);
         throw new ArithmeticException("LAPACK GEQRF error code: " + info);
      } else {
         qr.uplo = null;
         return new MatrixModified.QR(qr, tau);
      }
   }

   public MatrixModified.SVD svd() {
      return this.svd(true, false);
   }

   public MatrixModified.SVD svd(boolean vectors, boolean overwrite) {
      int k = Math.min(this.m, this.n);
      double[] s = new double[k];
      MatrixModified W = overwrite ? this : this.clone();
      MatrixModified U;
      MatrixModified VT;
      int info;
      if (vectors) {
         U = new MatrixModified(this.m, k);
         VT = new MatrixModified(k, this.n);
         info = LAPACKModified.engine.gesdd(W.layout(), SVDJobModified.COMPACT, W.m, W.n, W.A, W.ld, s, U.A, U.ld, VT.A, VT.ld);
         if (info != 0) {
            logger.error("LAPACK GESDD error code: {}", info);
            throw new ArithmeticException("LAPACK GESDD error code: " + info);
         } else {
            return new MatrixModified.SVD(s, U, VT.transpose());
         }
      } else {
         U = new MatrixModified(1, 1);
         VT = new MatrixModified(1, 1);
         info = LAPACKModified.engine.gesdd(W.layout(), SVDJobModified.NO_VECTORS, W.m, W.n, W.A, W.ld, s, U.A, U.ld, VT.A, VT.ld);
         if (info != 0) {
            logger.error("LAPACK GESDD error code: {}", info);
            throw new ArithmeticException("LAPACK GESDD error code: " + info);
         } else {
            return new MatrixModified.SVD(this.m, this.n, s);
         }
      }
   }

   public MatrixModified.EVD eigen() {
      return this.eigen(false, true, false);
   }

   public MatrixModified.EVD eigen(boolean vl, boolean vr, boolean overwrite) {
      if (this.m != this.n) {
         throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", this.m, this.n));
      } else {
         MatrixModified eig = overwrite ? this : this.clone();
         double[] wr;
         if (this.isSymmetric()) {
            wr = new double[this.n];
            int info = LAPACKModified.engine.syevd(eig.layout(), vr ? EVDJobModified.VECTORS : EVDJobModified.NO_VECTORS, eig.uplo, this.n, eig.A, eig.ld, wr);
            if (info != 0) {
               logger.error("LAPACK SYEV error code: {}", info);
               throw new ArithmeticException("LAPACK SYEV error code: " + info);
            } else {
               eig.uplo = null;
               return new MatrixModified.EVD(wr, vr ? eig : null);
            }
         } else {
            wr = new double[this.n];
            double[] wi = new double[this.n];
            MatrixModified Vl = vl ? new MatrixModified(this.n, this.n) : new MatrixModified(1, 1);
            MatrixModified Vr = vr ? new MatrixModified(this.n, this.n) : new MatrixModified(1, 1);
            int info = LAPACKModified.engine.geev(eig.layout(), vl ? EVDJobModified.VECTORS : EVDJobModified.NO_VECTORS, vr ? EVDJobModified.VECTORS : EVDJobModified.NO_VECTORS, this.n, eig.A, eig.ld, wr, wi, Vl.A, Vl.ld, Vr.A, Vr.ld);
            if (info != 0) {
               logger.error("LAPACK GEEV error code: {}", info);
               throw new ArithmeticException("LAPACK GEEV error code: " + info);
            } else {
               return new MatrixModified.EVD(wr, wi, vl ? Vl : null, vr ? Vr : null);
            }
         }
      }
   }

   public static class Cholesky implements Serializable {
      private static final long serialVersionUID = 2L;
      public final MatrixModified lu;

      public Cholesky(MatrixModified lu) {
         if (lu.nrow() != lu.ncol()) {
            throw new UnsupportedOperationException("Cholesky constructor on a non-square matrix");
         } else {
            this.lu = lu;
         }
      }

      public double det() {
         int n = this.lu.n;
         double d = 1.0D;

         for(int i = 0; i < n; ++i) {
            d *= this.lu.get(i, i);
         }

         return d * d;
      }

      public double logdet() {
         int n = this.lu.n;
         double d = 0.0D;

         for(int i = 0; i < n; ++i) {
            d += Math.log(this.lu.get(i, i));
         }

         return 2.0D * d;
      }

      public MatrixModified inverse() {
         MatrixModified inv = MatrixModified.eye(this.lu.n);
         this.solve(inv);
         return inv;
      }

      public double[] solve(double[] b) {
         MatrixModified x = MatrixModified.column(b);
         this.solve(x);
         return x.A;
      }

      public void solve(MatrixModified B) {
         if (B.m != this.lu.m) {
            throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", this.lu.m, this.lu.n, B.m, B.n));
         } else {
            int info = LAPACKModified.engine.potrs(this.lu.layout(), this.lu.uplo, this.lu.n, B.n, this.lu.A, this.lu.ld, B.A, B.ld);
            if (info != 0) {
               MatrixModified.logger.error("LAPACK POTRS error code: {}", info);
               throw new ArithmeticException("LAPACK POTRS error code: " + info);
            }
         }
      }
   }

   public static class EVD implements Serializable {
      private static final long serialVersionUID = 2L;
      public final double[] wr;
      public final double[] wi;
      public final MatrixModified Vl;
      public final MatrixModified Vr;

      public EVD(double[] w, MatrixModified V) {
         this.wr = w;
         this.wi = null;
         this.Vl = V;
         this.Vr = V;
      }

      public EVD(double[] wr, double[] wi, MatrixModified Vl, MatrixModified Vr) {
         this.wr = wr;
         this.wi = wi;
         this.Vl = Vl;
         this.Vr = Vr;
      }

      public MatrixModified diag() {
         MatrixModified D = MatrixModified.diag(this.wr);
         if (this.wi != null) {
            int n = this.wr.length;

            for(int i = 0; i < n; ++i) {
               if (this.wi[i] > 0.0D) {
                  D.set(i, i + 1, this.wi[i]);
               } else if (this.wi[i] < 0.0D) {
                  D.set(i, i - 1, this.wi[i]);
               }
            }
         }

         return D;
      }

      public MatrixModified.EVD sort() {
         int n = this.wr.length;
         double[] w = new double[n];
         int i;
         if (this.wi != null) {
            for(i = 0; i < n; ++i) {
               w[i] = -(this.wr[i] * this.wr[i] + this.wi[i] * this.wi[i]);
            }
         } else {
            for(i = 0; i < n; ++i) {
               w[i] = -(this.wr[i] * this.wr[i]);
            }
         }

         int[] index = QuickSortModified.sort(w);
         double[] wr2 = new double[n];

         for(int j = 0; j < n; ++j) {
            wr2[j] = this.wr[index[j]];
         }

         double[] wi2 = null;
         if (this.wi != null) {
            wi2 = new double[n];

            for(int j = 0; j < n; ++j) {
               wi2[j] = this.wi[index[j]];
            }
         }

         MatrixModified Vl2 = null;
         int m;
         int j;
         if (this.Vl != null) {
            int m = this.Vl.m;
            Vl2 = new MatrixModified(m, n);

            for(m = 0; m < n; ++m) {
               for(j = 0; j < m; ++j) {
                  Vl2.set(j, m, this.Vl.get(j, index[m]));
               }
            }
         }

         MatrixModified Vr2 = null;
         if (this.Vr != null) {
            m = this.Vr.m;
            Vr2 = new MatrixModified(m, n);

            for(j = 0; j < n; ++j) {
               for(int i = 0; i < m; ++i) {
                  Vr2.set(i, j, this.Vr.get(i, index[j]));
               }
            }
         }

         return new MatrixModified.EVD(wr2, wi2, Vl2, Vr2);
      }
   }

   public static class LU implements Serializable {
      private static final long serialVersionUID = 2L;
      public final MatrixModified lu;
      public final int[] ipiv;
      public final int info;

      public LU(MatrixModified lu, int[] ipiv, int info) {
         this.lu = lu;
         this.ipiv = ipiv;
         this.info = info;
      }

      public boolean isSingular() {
         return this.info > 0;
      }

      public double det() {
         int m = this.lu.m;
         int n = this.lu.n;
         if (m != n) {
            throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", m, n));
         } else {
            double d = 1.0D;

            int j;
            for(j = 0; j < n; ++j) {
               d *= this.lu.get(j, j);
            }

            for(j = 0; j < n; ++j) {
               if (j + 1 != this.ipiv[j]) {
                  d = -d;
               }
            }

            return d;
         }
      }

      public MatrixModified inverse() {
         MatrixModified inv = MatrixModified.eye(this.lu.n);
         this.solve(inv);
         return inv;
      }

      public double[] solve(double[] b) {
         MatrixModified x = MatrixModified.column(b);
         this.solve(x);
         return x.A;
      }

      public void solve(MatrixModified B) {
         if (this.lu.m != this.lu.n) {
            throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", this.lu.m, this.lu.n));
         } else if (B.m != this.lu.m) {
            throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", this.lu.m, this.lu.n, B.m, B.n));
         } else if (this.lu.layout() != B.layout()) {
            throw new IllegalArgumentException("The matrix layout is inconsistent.");
         } else if (this.info > 0) {
            throw new RuntimeException("The matrix is singular.");
         } else {
            int ret = LAPACKModified.engine.getrs(this.lu.layout(), TransposeModified.NO_TRANSPOSE, this.lu.n, B.n, this.lu.A, this.lu.ld, this.ipiv, B.A, B.ld);
            if (ret != 0) {
               MatrixModified.logger.error("LAPACK GETRS error code: {}", ret);
               throw new ArithmeticException("LAPACK GETRS error code: " + ret);
            }
         }
      }
   }

   public static class QR implements Serializable {
      private static final long serialVersionUID = 2L;
      public final MatrixModified qr;
      public final double[] tau;

      public QR(MatrixModified qr, double[] tau) {
         this.qr = qr;
         this.tau = tau;
      }

      public MatrixModified.Cholesky CholeskyOfAtA() {
         int n = this.qr.n;
         MatrixModified L = new MatrixModified(n, n);

         for(int i = 0; i < n; ++i) {
            for(int j = 0; j <= i; ++j) {
               L.set(i, j, this.qr.get(j, i));
            }
         }

         L.uplo(UPLOModified.LOWER);
         return new MatrixModified.Cholesky(L);
      }

      public MatrixModified R() {
         int n = this.qr.n;
         MatrixModified R = MatrixModified.diag(this.tau);

         for(int i = 0; i < n; ++i) {
            for(int j = i; j < n; ++j) {
               R.set(i, j, this.qr.get(i, j));
            }
         }

         return R;
      }

      public MatrixModified Q() {
         int m = this.qr.m;
         int n = this.qr.n;
         int k = Math.min(m, n);
         MatrixModified Q = this.qr.clone();
         int info = LAPACKModified.engine.orgqr(this.qr.layout(), m, n, k, Q.A, this.qr.ld, this.tau);
         if (info != 0) {
            MatrixModified.logger.error("LAPACK ORGRQ error code: {}", info);
            throw new ArithmeticException("LAPACK ORGRQ error code: " + info);
         } else {
            return Q;
         }
      }

      public double[] solve(double[] b) {
         if (b.length != this.qr.m) {
            throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x 1", this.qr.m, this.qr.n, b.length));
         } else {
            MatrixModified x = MatrixModified.column(b);
            this.solve(x);
            return Arrays.copyOf(x.A, this.qr.n);
         }
      }

      public void solve(MatrixModified B) {
         if (B.m != this.qr.m) {
            throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", this.qr.nrow(), this.qr.nrow(), B.nrow(), B.ncol()));
         } else {
            int m = this.qr.m;
            int n = this.qr.n;
            int k = Math.min(m, n);
            int info = LAPACKModified.engine.ormqr(this.qr.layout(), SideModified.LEFT, TransposeModified.TRANSPOSE, B.nrow(), B.ncol(), k, this.qr.A, this.qr.ld, this.tau, B.A, B.ld);
            if (info != 0) {
               MatrixModified.logger.error("LAPACK ORMQR error code: {}", info);
               throw new IllegalArgumentException("LAPACK ORMQR error code: " + info);
            } else {
               info = LAPACKModified.engine.trtrs(this.qr.layout(), UPLOModified.UPPER, TransposeModified.NO_TRANSPOSE, DiagModified.NON_UNIT, this.qr.n, B.n, this.qr.A, this.qr.ld, B.A, B.ld);
               if (info != 0) {
                  MatrixModified.logger.error("LAPACK TRTRS error code: {}", info);
                  throw new IllegalArgumentException("LAPACK TRTRS error code: " + info);
               }
            }
         }
      }
   }

   private static class RowMajor extends MatrixModified {
      RowMajor(int m, int n, int ld, double[] A) {
         super(m, n, ld, A);
      }

      public LayoutModified layout() {
         return LayoutModified.ROW_MAJOR;
      }

      protected int index(int i, int j) {
         return i * this.ld + j;
      }
   }

   public static class SVD implements Serializable {
      private static final long serialVersionUID = 2L;
      public final int m;
      public final int n;
      public final double[] s;
      public final MatrixModified U;
      public final MatrixModified V;
      private transient MatrixModified Ur;

      public SVD(int m, int n, double[] s) {
         this.m = m;
         this.n = n;
         this.s = s;
         this.U = null;
         this.V = null;
      }

      public SVD(double[] s, MatrixModified U, MatrixModified V) {
         this.m = U.m;
         this.n = V.m;
         this.s = s;
         this.U = U;
         this.V = V;
      }

      public MatrixModified diag() {
         MatrixModified S = new MatrixModified(this.U.m, this.V.m);

         for(int i = 0; i < this.s.length; ++i) {
            S.set(i, i, this.s[i]);
         }

         return S;
      }

      public double norm() {
         return this.s[0];
      }

      private double rcond() {
         return 0.5D * Math.sqrt((double)(this.m + this.n + 1)) * this.s[0] * MathExModified.EPSILON;
      }

      public int rank() {
         if (this.s.length != Math.min(this.m, this.n)) {
            throw new UnsupportedOperationException("The operation cannot be called on a partial SVD.");
         } else {
            int r = 0;
            double tol = this.rcond();
            double[] var8;
            int var7 = (var8 = this.s).length;

            for(int var6 = 0; var6 < var7; ++var6) {
               double si = var8[var6];
               if (si > tol) {
                  ++r;
               }
            }

            return r;
         }
      }

      public int nullity() {
         return Math.min(this.m, this.n) - this.rank();
      }

      public double condition() {
         if (this.s.length != Math.min(this.m, this.n)) {
            throw new UnsupportedOperationException("The operation cannot be called on a partial SVD.");
         } else {
            return !(this.s[0] <= 0.0D) && !(this.s[this.s.length - 1] <= 0.0D) ? this.s[0] / this.s[this.s.length - 1] : Double.POSITIVE_INFINITY;
         }
      }

      public MatrixModified range() {
         if (this.s.length != Math.min(this.m, this.n)) {
            throw new UnsupportedOperationException("The operation cannot be called on a partial SVD.");
         } else if (this.U == null) {
            throw new IllegalStateException("The left singular vectors are not available.");
         } else {
            int r = this.rank();
            if (r == 0) {
               return null;
            } else {
               MatrixModified R = new MatrixModified(this.m, r);

               for(int j = 0; j < r; ++j) {
                  for(int i = 0; i < this.m; ++i) {
                     R.set(i, j, this.U.get(i, j));
                  }
               }

               return R;
            }
         }
      }

      public MatrixModified nullspace() {
         if (this.s.length != Math.min(this.m, this.n)) {
            throw new UnsupportedOperationException("The operation cannot be called on a partial SVD.");
         } else if (this.V == null) {
            throw new IllegalStateException("The right singular vectors are not available.");
         } else {
            int nr = this.nullity();
            if (nr == 0) {
               return null;
            } else {
               MatrixModified N = new MatrixModified(this.n, nr);

               for(int j = 0; j < nr; ++j) {
                  for(int i = 0; i < this.n; ++i) {
                     N.set(i, j, this.V.get(i, this.n - j - 1));
                  }
               }

               return N;
            }
         }
      }

      public MatrixModified pinv() {
         int k = this.s.length;
         double[] sigma = new double[k];
         int r = this.rank();

         for(int i = 0; i < r; ++i) {
            sigma[i] = 1.0D / this.s[i];
         }

         return MatrixModified.adb(TransposeModified.NO_TRANSPOSE, this.V, sigma, TransposeModified.TRANSPOSE, this.U);
      }

      public double[] solve(double[] b) {
         if (this.U != null && this.V != null) {
            if (b.length != this.m) {
               throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x 1", this.m, this.n, b.length));
            } else {
               int r = this.rank();
               if (this.Ur == null) {
                  this.Ur = r == this.U.ncol() ? this.U : this.U.submatrix(0, 0, this.m - 1, r - 1);
               }

               double[] Utb = new double[this.s.length];
               this.Ur.tv(b, Utb);

               for(int i = 0; i < r; ++i) {
                  Utb[i] /= this.s[i];
               }

               return this.V.mv(Utb);
            }
         } else {
            throw new IllegalStateException("The singular vectors are not available.");
         }
      }
   }
}
