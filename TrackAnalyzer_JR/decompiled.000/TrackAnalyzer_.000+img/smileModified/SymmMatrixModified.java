package smileModified;

import java.io.Serializable;
import java.nio.DoubleBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SymmMatrixModified extends IMatrixModified {
   private static final long serialVersionUID = 2L;
   private static final Logger logger = LoggerFactory.getLogger(SymmMatrixModified.class);
   final double[] AP;
   final int n;
   final UPLOModified uplo;

   public SymmMatrixModified(UPLOModified uplo, int n) {
      if (uplo == null) {
         throw new NullPointerException("UPLO is null");
      } else {
         this.uplo = uplo;
         this.n = n;
         this.AP = new double[n * (n + 1) / 2];
      }
   }

   public SymmMatrixModified(UPLOModified uplo, double[][] AP) {
      this(uplo, AP.length);
      int i;
      int j;
      if (uplo == UPLOModified.LOWER) {
         for(i = 0; i < this.n; ++i) {
            for(j = 0; j <= i; ++j) {
               this.AP[i + (2 * this.n - j - 1) * j / 2] = AP[i][j];
            }
         }
      } else {
         for(i = 0; i < this.n; ++i) {
            for(j = i; j < this.n; ++j) {
               this.AP[i + j * (j + 1) / 2] = AP[i][j];
            }
         }
      }

   }

   public SymmMatrixModified clone() {
      SymmMatrixModified matrix = new SymmMatrixModified(this.uplo, this.n);
      System.arraycopy(this.AP, 0, matrix.AP, 0, this.AP.length);
      return matrix;
   }

   public int nrow() {
      return this.n;
   }

   public int ncol() {
      return this.n;
   }

   public long size() {
      return (long)this.AP.length;
   }

   public LayoutModified layout() {
      return LayoutModified.COL_MAJOR;
   }

   public UPLOModified uplo() {
      return this.uplo;
   }

   public boolean equals(Object o) {
      return !(o instanceof SymmMatrixModified) ? false : this.equals((SymmMatrixModified)o, 1.0E-10D);
   }

   public boolean equals(SymmMatrixModified o, double epsilon) {
      if (this.n != o.n) {
         return false;
      } else {
         for(int j = 0; j < this.n; ++j) {
            for(int i = 0; i < this.n; ++i) {
               if (!MathExModified.isZero(this.get(i, j) - o.get(i, j), epsilon)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public double get(int i, int j) {
      int tmp;
      if (this.uplo == UPLOModified.LOWER) {
         if (j > i) {
            tmp = i;
            i = j;
            j = tmp;
         }

         return this.AP[i + (2 * this.n - j - 1) * j / 2];
      } else {
         if (i > j) {
            tmp = i;
            i = j;
            j = tmp;
         }

         return this.AP[i + j * (j + 1) / 2];
      }
   }

   public void set(int i, int j, double x) {
      int tmp;
      if (this.uplo == UPLOModified.LOWER) {
         if (j > i) {
            tmp = i;
            i = j;
            j = tmp;
         }

         this.AP[i + (2 * this.n - j - 1) * j / 2] = x;
      } else {
         if (i > j) {
            tmp = i;
            i = j;
            j = tmp;
         }

         this.AP[i + j * (j + 1) / 2] = x;
      }

   }

   public void mv(TransposeModified trans, double alpha, double[] x, double beta, double[] y) {
      BLASModified.engine.spmv(this.layout(), this.uplo, this.n, alpha, (double[])this.AP, (double[])x, 1, beta, (double[])y, 1);
   }

   public void mv(double[] work, int inputOffset, int outputOffset) {
      DoubleBuffer xb = DoubleBuffer.wrap(work, inputOffset, this.n);
      DoubleBuffer yb = DoubleBuffer.wrap(work, outputOffset, this.n);
      BLASModified.engine.spmv(this.layout(), this.uplo, this.n, 1.0D, (DoubleBuffer)DoubleBuffer.wrap(this.AP), (DoubleBuffer)xb, 1, 0.0D, (DoubleBuffer)yb, 1);
   }

   public void tv(double[] work, int inputOffset, int outputOffset) {
      this.mv(work, inputOffset, outputOffset);
   }

   public SymmMatrixModified.BunchKaufman bk() {
      SymmMatrixModified lu = this.clone();
      int[] ipiv = new int[this.n];
      int info = LAPACKModified.engine.sptrf(lu.layout(), lu.uplo, lu.n, lu.AP, ipiv);
      if (info < 0) {
         logger.error("LAPACK SPTRF error code: {}", info);
         throw new ArithmeticException("LAPACK SPTRF error code: " + info);
      } else {
         return new SymmMatrixModified.BunchKaufman(lu, ipiv, info);
      }
   }

   public SymmMatrixModified.Cholesky cholesky() {
      if (this.uplo == null) {
         throw new IllegalArgumentException("The matrix is not symmetric");
      } else {
         SymmMatrixModified lu = this.clone();
         int info = LAPACKModified.engine.pptrf(lu.layout(), lu.uplo, lu.n, lu.AP);
         if (info != 0) {
            logger.error("LAPACK PPTRF error code: {}", info);
            throw new ArithmeticException("LAPACK PPTRF error code: " + info);
         } else {
            return new SymmMatrixModified.Cholesky(lu);
         }
      }
   }

   public static class BunchKaufman implements Serializable {
      private static final long serialVersionUID = 2L;
      public final SymmMatrixModified lu;
      public final int[] ipiv;
      public final int info;

      public BunchKaufman(SymmMatrixModified lu, int[] ipiv, int info) {
         this.lu = lu;
         this.ipiv = ipiv;
         this.info = info;
      }

      public boolean isSingular() {
         return this.info > 0;
      }

      public double det() {
         int n = this.lu.n;
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
         if (B.m != this.lu.n) {
            throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", this.lu.n, this.lu.n, B.m, B.n));
         } else if (this.lu.layout() != B.layout()) {
            throw new IllegalArgumentException("The matrix layout is inconsistent.");
         } else if (this.info > 0) {
            throw new RuntimeException("The matrix is singular.");
         } else {
            int ret = LAPACKModified.engine.sptrs(this.lu.layout(), this.lu.uplo, this.lu.n, B.n, this.lu.AP, this.ipiv, B.A, B.ld);
            if (ret != 0) {
               SymmMatrixModified.logger.error("LAPACK GETRS error code: {}", ret);
               throw new ArithmeticException("LAPACK GETRS error code: " + ret);
            }
         }
      }
   }

   public static class Cholesky implements Serializable {
      private static final long serialVersionUID = 2L;
      public final SymmMatrixModified lu;

      public Cholesky(SymmMatrixModified lu) {
         if (lu.nrow() != lu.ncol()) {
            throw new UnsupportedOperationException("Cholesky constructor on a non-square matrix");
         } else {
            this.lu = lu;
         }
      }

      public double det() {
         double d = 1.0D;

         for(int i = 0; i < this.lu.n; ++i) {
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
         if (B.m != this.lu.n) {
            throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", this.lu.n, this.lu.n, B.m, B.n));
         } else {
            int info = LAPACKModified.engine.pptrs(this.lu.layout(), this.lu.uplo, this.lu.n, B.n, this.lu.AP, B.A, B.ld);
            if (info != 0) {
               SymmMatrixModified.logger.error("LAPACK POTRS error code: {}", info);
               throw new ArithmeticException("LAPACK POTRS error code: " + info);
            }
         }
      }
   }
}
