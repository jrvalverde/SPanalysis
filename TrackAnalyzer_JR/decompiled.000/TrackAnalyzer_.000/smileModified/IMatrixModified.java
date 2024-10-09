package smileModified;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class IMatrixModified implements Cloneable, Serializable {
   private static final Logger logger = LoggerFactory.getLogger(IMatrixModified.class);
   private String[] rowNames;
   private String[] colNames;

   public abstract int nrow();

   public abstract int ncol();

   public abstract long size();

   public String[] rowNames() {
      return this.rowNames;
   }

   public void rowNames(String[] names) {
      if (names != null && names.length != this.nrow()) {
         throw new IllegalArgumentException(String.format("Invalid row names length: %d != %d", names.length, this.nrow()));
      } else {
         this.rowNames = names;
      }
   }

   public String rowName(int i) {
      return this.rowNames[i];
   }

   public String[] colNames() {
      return this.colNames;
   }

   public void colNames(String[] names) {
      if (names != null && names.length != this.ncol()) {
         throw new IllegalArgumentException(String.format("Invalid column names length: %d != %d", names.length, this.ncol()));
      } else {
         this.colNames = names;
      }
   }

   public String colName(int i) {
      return this.colNames[i];
   }

   public String toString() {
      return this.toString(false);
   }

   public String toString(boolean full) {
      return full ? this.toString(this.nrow(), this.ncol()) : this.toString(7, 7);
   }

   public String toString(int m, int n) {
      StringBuilder sb = new StringBuilder(this.nrow() + " x " + this.ncol() + "\n");
      m = Math.min(m, this.nrow());
      n = Math.min(n, this.ncol());
      String newline = n < this.ncol() ? "  ...\n" : "\n";
      int i;
      if (this.colNames != null) {
         if (this.rowNames != null) {
            sb.append("            ");
         }

         for(i = 0; i < n; ++i) {
            sb.append(String.format(" %12.12s", this.colNames[i]));
         }

         sb.append(newline);
      }

      for(i = 0; i < m; ++i) {
         if (this.rowNames != null) {
            sb.append(String.format("%-12.12s", this.rowNames[i]));
         }

         for(int j = 0; j < n; ++j) {
            sb.append(String.format(" %12.12s", this.str(i, j)));
         }

         sb.append(newline);
      }

      if (m < this.nrow()) {
         sb.append("  ...\n");
      }

      return sb.toString();
   }

   private String str(int i, int j) {
      return StringsModified.format(this.get(i, j), true);
   }

   public abstract void mv(TransposeModified var1, double var2, double[] var4, double var5, double[] var7);

   public double[] mv(double[] x) {
      double[] y = new double[this.nrow()];
      this.mv(TransposeModified.NO_TRANSPOSE, 1.0D, x, 0.0D, y);
      return y;
   }

   public void mv(double[] x, double[] y) {
      this.mv(TransposeModified.NO_TRANSPOSE, 1.0D, x, 0.0D, y);
   }

   public void mv(double alpha, double[] x, double beta, double[] y) {
      this.mv(TransposeModified.NO_TRANSPOSE, alpha, x, beta, y);
   }

   public abstract void mv(double[] var1, int var2, int var3);

   public double[] tv(double[] x) {
      double[] y = new double[this.ncol()];
      this.mv(TransposeModified.TRANSPOSE, 1.0D, x, 0.0D, y);
      return y;
   }

   public void tv(double[] x, double[] y) {
      this.mv(TransposeModified.TRANSPOSE, 1.0D, x, 0.0D, y);
   }

   public void tv(double alpha, double[] x, double beta, double[] y) {
      this.mv(TransposeModified.TRANSPOSE, alpha, x, beta, y);
   }

   public abstract void tv(double[] var1, int var2, int var3);

   static int ld(int n) {
      int elementSize = 4;
      return n <= 256 / elementSize ? n : ((n * elementSize + 511) / 512 * 512 + 64) / elementSize;
   }

   static TransposeModified flip(TransposeModified trans) {
      return trans == TransposeModified.NO_TRANSPOSE ? TransposeModified.TRANSPOSE : TransposeModified.NO_TRANSPOSE;
   }

   public void set(int i, int j, double x) {
      throw new UnsupportedOperationException();
   }

   public void update(int i, int j, double x) {
      this.set(i, j, x);
   }

   public double get(int i, int j) {
      throw new UnsupportedOperationException();
   }

   public double apply(int i, int j) {
      return this.get(i, j);
   }

   public double[] diag() {
      int n = Math.min(this.nrow(), this.ncol());
      double[] d = new double[n];

      for(int i = 0; i < n; ++i) {
         d[i] = this.get(i, i);
      }

      return d;
   }

   public double trace() {
      int n = Math.min(this.nrow(), this.ncol());
      double t = 0.0D;

      for(int i = 0; i < n; ++i) {
         t += this.get(i, i);
      }

      return t;
   }

   public double eigen(double[] v) {
      return this.eigen(v, 0.0D, Math.max(1.0E-6D, (double)this.nrow() * MathExModified.EPSILON), Math.max(20, 2 * this.nrow()));
   }

   public double eigen(double[] v, double p, double tol, int maxIter) {
      if (this.nrow() != this.ncol()) {
         throw new IllegalArgumentException("Matrix is not square.");
      } else if (tol <= 0.0D) {
         throw new IllegalArgumentException("Invalid tolerance: " + tol);
      } else if (maxIter <= 0) {
         throw new IllegalArgumentException("Invalid maximum number of iterations: " + maxIter);
      } else {
         int n = this.nrow();
         tol = Math.max(tol, MathExModified.EPSILON * (double)n);
         double[] z = new double[n];
         double lambda = this.power(v, z, p);

         for(int iter = 1; iter <= maxIter; ++iter) {
            double l = lambda;
            lambda = this.power(v, z, p);
            double eps = Math.abs(lambda - l);
            if (iter % 10 == 0) {
               logger.trace(String.format("Largest eigenvalue after %3d power iterations: %.4f", iter, lambda + p));
            }

            if (eps < tol) {
               logger.info(String.format("Largest eigenvalue after %3d power iterations: %.4f", iter, lambda + p));
               return lambda + p;
            }
         }

         logger.info(String.format("Largest eigenvalue after %3d power iterations: %.4f", maxIter, lambda + p));
         logger.error("Power iteration exceeded the maximum number of iterations.");
         return lambda + p;
      }
   }

   private double power(double[] x, double[] y, double p) {
      this.mv(x, y);
      if (p != 0.0D) {
         for(int i = 0; i < y.length; ++i) {
            y[i] -= p * x[i];
         }
      }

      double lambda = y[0];

      int i;
      for(i = 1; i < y.length; ++i) {
         if (Math.abs(y[i]) > Math.abs(lambda)) {
            lambda = y[i];
         }
      }

      for(i = 0; i < y.length; ++i) {
         x[i] = y[i] / lambda;
      }

      return lambda;
   }

   public static IMatrixModified market(Path path) throws IOException, ParseException {
      Throwable var1 = null;
      Object var2 = null;

      try {
         LineNumberReader reader = new LineNumberReader(Files.newBufferedReader(path));

         MatrixModified var40;
         label1105: {
            SymmMatrixModified var41;
            label1106: {
               label1107: {
                  SparseMatrixModified var10000;
                  try {
                     Scanner scanner = new Scanner(reader);

                     try {
                        String header = scanner.next();
                        if (!header.equals("%%MatrixMarket")) {
                           throw new ParseException("Invalid Matrix Market file header", reader.getLineNumber());
                        }

                        String object = scanner.next();
                        if (!object.equals("matrix")) {
                           throw new UnsupportedOperationException("The object is not a matrix file: " + object);
                        }

                        String format = scanner.next();
                        String field = scanner.next();
                        if (field.equals("complex") || field.equals("pattern")) {
                           throw new UnsupportedOperationException("No support of complex or pattern matrix");
                        }

                        String symmetry = scanner.nextLine().trim();
                        if (symmetry.equals("Hermitian")) {
                           throw new UnsupportedOperationException("No support of Hermitian matrix");
                        }

                        boolean symmetric = symmetry.equals("symmetric");
                        boolean skew = symmetry.equals("skew-symmetric");

                        String line;
                        for(line = scanner.nextLine(); line.startsWith("%"); line = scanner.nextLine()) {
                        }

                        Scanner s;
                        int nrow;
                        int ncol;
                        int k;
                        if (format.equals("array")) {
                           s = new Scanner(line);
                           nrow = s.nextInt();
                           ncol = s.nextInt();
                           MatrixModified matrix = new MatrixModified(nrow, ncol);

                           for(int j = 0; j < ncol; ++j) {
                              for(k = 0; k < nrow; ++k) {
                                 double x = scanner.nextDouble();
                                 matrix.set(k, j, x);
                              }
                           }

                           if (symmetric) {
                              matrix.uplo(UPLOModified.LOWER);
                           }

                           var40 = matrix;
                           break label1105;
                        }

                        if (!format.equals("coordinate")) {
                           throw new ParseException("Invalid Matrix Market format: " + format, 0);
                        }

                        s = new Scanner(line);
                        nrow = s.nextInt();
                        ncol = s.nextInt();
                        int nz = s.nextInt();
                        int i;
                        String[] tokens;
                        int i;
                        double x;
                        if (symmetric && nz == nrow * (nrow + 1) / 2) {
                           if (nrow != ncol) {
                              throw new IllegalStateException(String.format("Symmetric matrix is not square: %d != %d", nrow, ncol));
                           }

                           SymmMatrixModified matrix = new SymmMatrixModified(UPLOModified.LOWER, nrow);

                           for(k = 0; k < nz; ++k) {
                              tokens = scanner.nextLine().trim().split("\\s+");
                              if (tokens.length != 3) {
                                 throw new ParseException("Invalid data line: " + line, reader.getLineNumber());
                              }

                              i = Integer.parseInt(tokens[0]) - 1;
                              i = Integer.parseInt(tokens[1]) - 1;
                              x = Double.parseDouble(tokens[2]);
                              matrix.set(i, i, x);
                           }

                           var41 = matrix;
                           break label1106;
                        }

                        if (skew && nz == nrow * (nrow + 1) / 2) {
                           if (nrow != ncol) {
                              throw new IllegalStateException(String.format("Skew-symmetric matrix is not square: %d != %d", nrow, ncol));
                           }

                           MatrixModified matrix = new MatrixModified(nrow, ncol);

                           for(k = 0; k < nz; ++k) {
                              tokens = scanner.nextLine().trim().split("\\s+");
                              if (tokens.length != 3) {
                                 throw new ParseException("Invalid data line: " + line, reader.getLineNumber());
                              }

                              i = Integer.parseInt(tokens[0]) - 1;
                              i = Integer.parseInt(tokens[1]) - 1;
                              x = Double.parseDouble(tokens[2]);
                              matrix.set(i, i, x);
                              matrix.set(i, i, -x);
                           }

                           var40 = matrix;
                           break label1107;
                        }

                        int[] colSize = new int[ncol];
                        List<SparseArrayModified> rows = new ArrayList();

                        int k;
                        for(k = 0; k < nrow; ++k) {
                           rows.add(new SparseArrayModified());
                        }

                        int var10002;
                        for(k = 0; k < nz; ++k) {
                           String[] tokens = scanner.nextLine().trim().split("\\s+");
                           if (tokens.length != 3) {
                              throw new ParseException("Invalid data line: " + line, reader.getLineNumber());
                           }

                           i = Integer.parseInt(tokens[0]) - 1;
                           int j = Integer.parseInt(tokens[1]) - 1;
                           double x = Double.parseDouble(tokens[2]);
                           SparseArrayModified row = (SparseArrayModified)rows.get(i);
                           row.set(j, x);
                           var10002 = colSize[j]++;
                           if (symmetric) {
                              row = (SparseArrayModified)rows.get(j);
                              row.set(i, x);
                              var10002 = colSize[i]++;
                           } else if (skew) {
                              row = (SparseArrayModified)rows.get(j);
                              row.set(i, -x);
                              var10002 = colSize[i]++;
                           }
                        }

                        int[] pos = new int[ncol];
                        int[] colIndex = new int[ncol + 1];

                        for(i = 0; i < ncol; ++i) {
                           colIndex[i + 1] = colIndex[i] + colSize[i];
                        }

                        if (symmetric || skew) {
                           nz *= 2;
                        }

                        int[] rowIndex = new int[nz];
                        double[] x = new double[nz];
                        int i = 0;

                        while(true) {
                           if (i >= nrow) {
                              var10000 = new SparseMatrixModified(nrow, ncol, x, rowIndex, colIndex);
                              break;
                           }

                           int j;
                           for(Iterator var56 = ((SparseArrayModified)rows.get(i)).iterator(); var56.hasNext(); var10002 = pos[j]++) {
                              SparseArrayModified.Entry e = (SparseArrayModified.Entry)var56.next();
                              j = e.i;
                              int k = colIndex[j] + pos[j];
                              rowIndex[k] = i;
                              x[k] = e.x;
                           }

                           ++i;
                        }
                     } finally {
                        if (scanner != null) {
                           scanner.close();
                        }

                     }
                  } catch (Throwable var38) {
                     if (var1 == null) {
                        var1 = var38;
                     } else if (var1 != var38) {
                        var1.addSuppressed(var38);
                     }

                     if (reader != null) {
                        reader.close();
                     }

                     throw var1;
                  }

                  if (reader != null) {
                     reader.close();
                  }

                  return var10000;
               }

               if (reader != null) {
                  reader.close();
               }

               return var40;
            }

            if (reader != null) {
               reader.close();
            }

            return var41;
         }

         if (reader != null) {
            reader.close();
         }

         return var40;
      } catch (Throwable var39) {
         if (var1 == null) {
            var1 = var39;
         } else if (var1 != var39) {
            var1.addSuppressed(var39);
         }

         throw var1;
      }
   }

   public IMatrixModified square() {
      return new IMatrixModified.Square(this);
   }

   public IMatrixModified.Preconditioner Jacobi() {
      double[] diag = this.diag();
      return (b, x) -> {
         int n = diag.length;

         for(int i = 0; i < n; ++i) {
            x[i] = diag[i] != 0.0D ? b[i] / diag[i] : b[i];
         }

      };
   }

   public double solve(double[] b, double[] x) {
      return this.solve(b, x, this.Jacobi(), 1.0E-6D, 1, 2 * Math.max(this.nrow(), this.ncol()));
   }

   public double solve(double[] b, double[] x, IMatrixModified.Preconditioner P, double tol, int itol, int maxIter) {
      if (tol <= 0.0D) {
         throw new IllegalArgumentException("Invalid tolerance: " + tol);
      } else if (itol >= 1 && itol <= 4) {
         if (maxIter <= 0) {
            throw new IllegalArgumentException("Invalid maximum iterations: " + maxIter);
         } else {
            double err = 0.0D;
            double bkden = 1.0D;
            double znrm = 0.0D;
            int n = b.length;
            double[] p = new double[n];
            double[] pp = new double[n];
            double[] r = new double[n];
            double[] rr = new double[n];
            double[] z = new double[n];
            double[] zz = new double[n];
            this.mv(x, r);

            int j;
            for(j = 0; j < n; ++j) {
               r[j] = b[j] - r[j];
               rr[j] = r[j];
            }

            double bnrm;
            if (itol == 1) {
               bnrm = norm(b, itol);
               P.asolve(r, z);
            } else if (itol == 2) {
               P.asolve(b, z);
               bnrm = norm(z, itol);
               P.asolve(r, z);
            } else {
               if (itol != 3 && itol != 4) {
                  throw new IllegalArgumentException(String.format("Illegal itol: %d", itol));
               }

               P.asolve(b, z);
               bnrm = norm(z, itol);
               P.asolve(r, z);
               znrm = norm(z, itol);
            }

            for(int iter = 1; iter <= maxIter; ++iter) {
               P.asolve(rr, zz);
               double bknum = 0.0D;

               for(j = 0; j < n; ++j) {
                  bknum += z[j] * rr[j];
               }

               if (iter == 1) {
                  for(j = 0; j < n; ++j) {
                     p[j] = z[j];
                     pp[j] = zz[j];
                  }
               } else {
                  double bk = bknum / bkden;

                  for(j = 0; j < n; ++j) {
                     p[j] = bk * p[j] + z[j];
                     pp[j] = bk * pp[j] + zz[j];
                  }
               }

               bkden = bknum;
               this.mv(p, z);
               double akden = 0.0D;

               for(j = 0; j < n; ++j) {
                  akden += z[j] * pp[j];
               }

               double ak = bknum / akden;
               this.tv(pp, zz);

               for(j = 0; j < n; ++j) {
                  x[j] += ak * p[j];
                  r[j] -= ak * z[j];
                  rr[j] -= ak * zz[j];
               }

               P.asolve(r, z);
               if (itol == 1) {
                  err = norm(r, itol) / bnrm;
               } else if (itol == 2) {
                  err = norm(z, itol) / bnrm;
               } else if (itol == 3 || itol == 4) {
                  double zm1nrm = znrm;
                  znrm = norm(z, itol);
                  if (!(Math.abs(zm1nrm - znrm) > MathExModified.EPSILON * znrm)) {
                     err = znrm / bnrm;
                     continue;
                  }

                  double dxnrm = Math.abs(ak) * norm(p, itol);
                  err = znrm / Math.abs(zm1nrm - znrm) * dxnrm;
                  double xnrm = norm(x, itol);
                  if (!(err <= 0.5D * xnrm)) {
                     err = znrm / bnrm;
                     continue;
                  }

                  err /= xnrm;
               }

               if (iter % 10 == 0) {
                  logger.info(String.format("BCG: the error after %3d iterations: %.5g", iter, err));
               }

               if (err <= tol) {
                  logger.info(String.format("BCG: the error after %3d iterations: %.5g", iter, err));
                  break;
               }
            }

            return err;
         }
      } else {
         throw new IllegalArgumentException("Invalid itol: " + itol);
      }
   }

   private static double norm(double[] x, int itol) {
      int n = x.length;
      if (itol > 3) {
         int isamax = 0;

         for(int i = 0; i < n; ++i) {
            if (Math.abs(x[i]) > Math.abs(x[isamax])) {
               isamax = i;
            }
         }

         return Math.abs(x[isamax]);
      } else {
         double ans = 0.0D;
         double[] var9 = x;
         int var8 = x.length;

         for(int var7 = 0; var7 < var8; ++var7) {
            double v = var9[var7];
            ans += v * v;
         }

         return Math.sqrt(ans);
      }
   }

   public interface Preconditioner {
      void asolve(double[] var1, double[] var2);
   }

   static class Square extends IMatrixModified {
      private final IMatrixModified A;
      private final int m;
      private final int n;
      private final double[] Ax;

      public Square(IMatrixModified A) {
         this.A = A;
         this.m = Math.max(A.nrow(), A.ncol());
         this.n = Math.min(A.nrow(), A.ncol());
         this.Ax = new double[this.m + this.n];
      }

      public int nrow() {
         return this.n;
      }

      public int ncol() {
         return this.n;
      }

      public long size() {
         return this.A.size();
      }

      public void mv(TransposeModified trans, double alpha, double[] x, double beta, double[] y) {
         if (this.A.nrow() >= this.A.ncol()) {
            this.A.mv(x, this.Ax);
            this.A.tv(alpha, this.Ax, beta, y);
         } else {
            this.A.tv(x, this.Ax);
            this.A.mv(alpha, this.Ax, beta, y);
         }

      }

      public void mv(double[] work, int inputOffset, int outputOffset) {
         System.arraycopy(work, inputOffset, this.Ax, 0, this.n);
         if (this.A.nrow() >= this.A.ncol()) {
            this.A.mv(this.Ax, 0, this.n);
            this.A.tv(this.Ax, this.n, 0);
         } else {
            this.A.tv(this.Ax, 0, this.n);
            this.A.mv(this.Ax, this.n, 0);
         }

         System.arraycopy(this.Ax, 0, work, outputOffset, this.n);
      }

      public void tv(double[] work, int inputOffset, int outputOffset) {
         this.mv(work, inputOffset, outputOffset);
      }
   }
}
