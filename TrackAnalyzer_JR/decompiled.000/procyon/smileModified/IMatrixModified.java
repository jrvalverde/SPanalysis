// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.text.ParseException;
import java.util.Scanner;
import java.io.Reader;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.io.Serializable;

public abstract class IMatrixModified implements Cloneable, Serializable
{
    private static final Logger logger;
    private String[] rowNames;
    private String[] colNames;
    
    static {
        logger = LoggerFactory.getLogger((Class)IMatrixModified.class);
    }
    
    public abstract int nrow();
    
    public abstract int ncol();
    
    public abstract long size();
    
    public String[] rowNames() {
        return this.rowNames;
    }
    
    public void rowNames(final String[] names) {
        if (names != null && names.length != this.nrow()) {
            throw new IllegalArgumentException(String.format("Invalid row names length: %d != %d", names.length, this.nrow()));
        }
        this.rowNames = names;
    }
    
    public String rowName(final int i) {
        return this.rowNames[i];
    }
    
    public String[] colNames() {
        return this.colNames;
    }
    
    public void colNames(final String[] names) {
        if (names != null && names.length != this.ncol()) {
            throw new IllegalArgumentException(String.format("Invalid column names length: %d != %d", names.length, this.ncol()));
        }
        this.colNames = names;
    }
    
    public String colName(final int i) {
        return this.colNames[i];
    }
    
    @Override
    public String toString() {
        return this.toString(false);
    }
    
    public String toString(final boolean full) {
        return full ? this.toString(this.nrow(), this.ncol()) : this.toString(7, 7);
    }
    
    public String toString(int m, int n) {
        final StringBuilder sb = new StringBuilder(String.valueOf(this.nrow()) + " x " + this.ncol() + "\n");
        m = Math.min(m, this.nrow());
        n = Math.min(n, this.ncol());
        final String newline = (n < this.ncol()) ? "  ...\n" : "\n";
        if (this.colNames != null) {
            if (this.rowNames != null) {
                sb.append("            ");
            }
            for (int j = 0; j < n; ++j) {
                sb.append(String.format(" %12.12s", this.colNames[j]));
            }
            sb.append(newline);
        }
        for (int i = 0; i < m; ++i) {
            if (this.rowNames != null) {
                sb.append(String.format("%-12.12s", this.rowNames[i]));
            }
            for (int k = 0; k < n; ++k) {
                sb.append(String.format(" %12.12s", this.str(i, k)));
            }
            sb.append(newline);
        }
        if (m < this.nrow()) {
            sb.append("  ...\n");
        }
        return sb.toString();
    }
    
    private String str(final int i, final int j) {
        return StringsModified.format(this.get(i, j), true);
    }
    
    public abstract void mv(final TransposeModified p0, final double p1, final double[] p2, final double p3, final double[] p4);
    
    public double[] mv(final double[] x) {
        final double[] y = new double[this.nrow()];
        this.mv(TransposeModified.NO_TRANSPOSE, 1.0, x, 0.0, y);
        return y;
    }
    
    public void mv(final double[] x, final double[] y) {
        this.mv(TransposeModified.NO_TRANSPOSE, 1.0, x, 0.0, y);
    }
    
    public void mv(final double alpha, final double[] x, final double beta, final double[] y) {
        this.mv(TransposeModified.NO_TRANSPOSE, alpha, x, beta, y);
    }
    
    public abstract void mv(final double[] p0, final int p1, final int p2);
    
    public double[] tv(final double[] x) {
        final double[] y = new double[this.ncol()];
        this.mv(TransposeModified.TRANSPOSE, 1.0, x, 0.0, y);
        return y;
    }
    
    public void tv(final double[] x, final double[] y) {
        this.mv(TransposeModified.TRANSPOSE, 1.0, x, 0.0, y);
    }
    
    public void tv(final double alpha, final double[] x, final double beta, final double[] y) {
        this.mv(TransposeModified.TRANSPOSE, alpha, x, beta, y);
    }
    
    public abstract void tv(final double[] p0, final int p1, final int p2);
    
    static int ld(final int n) {
        final int elementSize = 4;
        if (n <= 256 / elementSize) {
            return n;
        }
        return ((n * elementSize + 511) / 512 * 512 + 64) / elementSize;
    }
    
    static TransposeModified flip(final TransposeModified trans) {
        return (trans == TransposeModified.NO_TRANSPOSE) ? TransposeModified.TRANSPOSE : TransposeModified.NO_TRANSPOSE;
    }
    
    public void set(final int i, final int j, final double x) {
        throw new UnsupportedOperationException();
    }
    
    public void update(final int i, final int j, final double x) {
        this.set(i, j, x);
    }
    
    public double get(final int i, final int j) {
        throw new UnsupportedOperationException();
    }
    
    public double apply(final int i, final int j) {
        return this.get(i, j);
    }
    
    public double[] diag() {
        final int n = Math.min(this.nrow(), this.ncol());
        final double[] d = new double[n];
        for (int i = 0; i < n; ++i) {
            d[i] = this.get(i, i);
        }
        return d;
    }
    
    public double trace() {
        final int n = Math.min(this.nrow(), this.ncol());
        double t = 0.0;
        for (int i = 0; i < n; ++i) {
            t += this.get(i, i);
        }
        return t;
    }
    
    public double eigen(final double[] v) {
        return this.eigen(v, 0.0, Math.max(1.0E-6, this.nrow() * MathExModified.EPSILON), Math.max(20, 2 * this.nrow()));
    }
    
    public double eigen(final double[] v, final double p, double tol, final int maxIter) {
        if (this.nrow() != this.ncol()) {
            throw new IllegalArgumentException("Matrix is not square.");
        }
        if (tol <= 0.0) {
            throw new IllegalArgumentException("Invalid tolerance: " + tol);
        }
        if (maxIter <= 0) {
            throw new IllegalArgumentException("Invalid maximum number of iterations: " + maxIter);
        }
        final int n = this.nrow();
        tol = Math.max(tol, MathExModified.EPSILON * n);
        final double[] z = new double[n];
        double lambda = this.power(v, z, p);
        for (int iter = 1; iter <= maxIter; ++iter) {
            final double l = lambda;
            lambda = this.power(v, z, p);
            final double eps = Math.abs(lambda - l);
            if (iter % 10 == 0) {
                IMatrixModified.logger.trace(String.format("Largest eigenvalue after %3d power iterations: %.4f", iter, lambda + p));
            }
            if (eps < tol) {
                IMatrixModified.logger.info(String.format("Largest eigenvalue after %3d power iterations: %.4f", iter, lambda + p));
                return lambda + p;
            }
        }
        IMatrixModified.logger.info(String.format("Largest eigenvalue after %3d power iterations: %.4f", maxIter, lambda + p));
        IMatrixModified.logger.error("Power iteration exceeded the maximum number of iterations.");
        return lambda + p;
    }
    
    private double power(final double[] x, final double[] y, final double p) {
        this.mv(x, y);
        if (p != 0.0) {
            for (int i = 0; i < y.length; ++i) {
                final int n = i;
                y[n] -= p * x[i];
            }
        }
        double lambda = y[0];
        for (int j = 1; j < y.length; ++j) {
            if (Math.abs(y[j]) > Math.abs(lambda)) {
                lambda = y[j];
            }
        }
        for (int j = 0; j < y.length; ++j) {
            x[j] = y[j] / lambda;
        }
        return lambda;
    }
    
    public static IMatrixModified market(final Path path) throws IOException, ParseException {
        Throwable t = null;
        try {
            final LineNumberReader reader = new LineNumberReader(Files.newBufferedReader(path));
            try {
                final Scanner scanner = new Scanner(reader);
                try {
                    final String header = scanner.next();
                    if (!header.equals("%%MatrixMarket")) {
                        throw new ParseException("Invalid Matrix Market file header", reader.getLineNumber());
                    }
                    final String object = scanner.next();
                    if (!object.equals("matrix")) {
                        throw new UnsupportedOperationException("The object is not a matrix file: " + object);
                    }
                    final String format = scanner.next();
                    final String field = scanner.next();
                    if (field.equals("complex") || field.equals("pattern")) {
                        throw new UnsupportedOperationException("No support of complex or pattern matrix");
                    }
                    final String symmetry = scanner.nextLine().trim();
                    if (symmetry.equals("Hermitian")) {
                        throw new UnsupportedOperationException("No support of Hermitian matrix");
                    }
                    final boolean symmetric = symmetry.equals("symmetric");
                    final boolean skew = symmetry.equals("skew-symmetric");
                    String line;
                    for (line = scanner.nextLine(); line.startsWith("%"); line = scanner.nextLine()) {}
                    if (format.equals("array")) {
                        final Scanner s = new Scanner(line);
                        final int nrow = s.nextInt();
                        final int ncol = s.nextInt();
                        final MatrixModified matrix = new MatrixModified(nrow, ncol);
                        for (int j = 0; j < ncol; ++j) {
                            for (int i = 0; i < nrow; ++i) {
                                final double x = scanner.nextDouble();
                                matrix.set(i, j, x);
                            }
                        }
                        if (symmetric) {
                            matrix.uplo(UPLOModified.LOWER);
                        }
                        final MatrixModified matrixModified = matrix;
                        if (scanner != null) {
                            scanner.close();
                        }
                        if (reader != null) {
                            reader.close();
                        }
                        return matrixModified;
                    }
                    if (!format.equals("coordinate")) {
                        throw new ParseException("Invalid Matrix Market format: " + format, 0);
                    }
                    final Scanner s = new Scanner(line);
                    final int nrow = s.nextInt();
                    final int ncol = s.nextInt();
                    int nz = s.nextInt();
                    if (symmetric && nz == nrow * (nrow + 1) / 2) {
                        if (nrow != ncol) {
                            throw new IllegalStateException(String.format("Symmetric matrix is not square: %d != %d", nrow, ncol));
                        }
                        final SymmMatrixModified matrix2 = new SymmMatrixModified(UPLOModified.LOWER, nrow);
                        for (int k = 0; k < nz; ++k) {
                            final String[] tokens = scanner.nextLine().trim().split("\\s+");
                            if (tokens.length != 3) {
                                throw new ParseException("Invalid data line: " + line, reader.getLineNumber());
                            }
                            final int l = Integer.parseInt(tokens[0]) - 1;
                            final int m = Integer.parseInt(tokens[1]) - 1;
                            final double x2 = Double.parseDouble(tokens[2]);
                            matrix2.set(l, m, x2);
                        }
                        final SymmMatrixModified symmMatrixModified = matrix2;
                        if (scanner != null) {
                            scanner.close();
                        }
                        if (reader != null) {
                            reader.close();
                        }
                        return symmMatrixModified;
                    }
                    else {
                        if (!skew || nz != nrow * (nrow + 1) / 2) {
                            final int[] colSize = new int[ncol];
                            final List<SparseArrayModified> rows = new ArrayList<SparseArrayModified>();
                            for (int i2 = 0; i2 < nrow; ++i2) {
                                rows.add(new SparseArrayModified());
                            }
                            for (int k2 = 0; k2 < nz; ++k2) {
                                final String[] tokens2 = scanner.nextLine().trim().split("\\s+");
                                if (tokens2.length != 3) {
                                    throw new ParseException("Invalid data line: " + line, reader.getLineNumber());
                                }
                                final int i3 = Integer.parseInt(tokens2[0]) - 1;
                                final int j2 = Integer.parseInt(tokens2[1]) - 1;
                                final double x3 = Double.parseDouble(tokens2[2]);
                                SparseArrayModified row = rows.get(i3);
                                row.set(j2, x3);
                                final int[] array = colSize;
                                final int n = j2;
                                ++array[n];
                                if (symmetric) {
                                    row = rows.get(j2);
                                    row.set(i3, x3);
                                    final int[] array2 = colSize;
                                    final int n2 = i3;
                                    ++array2[n2];
                                }
                                else if (skew) {
                                    row = rows.get(j2);
                                    row.set(i3, -x3);
                                    final int[] array3 = colSize;
                                    final int n3 = i3;
                                    ++array3[n3];
                                }
                            }
                            final int[] pos = new int[ncol];
                            final int[] colIndex = new int[ncol + 1];
                            for (int i3 = 0; i3 < ncol; ++i3) {
                                colIndex[i3 + 1] = colIndex[i3] + colSize[i3];
                            }
                            if (symmetric || skew) {
                                nz *= 2;
                            }
                            final int[] rowIndex = new int[nz];
                            final double[] x4 = new double[nz];
                            for (int i4 = 0; i4 < nrow; ++i4) {
                                for (final SparseArrayModified.Entry e : rows.get(i4)) {
                                    final int j3 = e.i;
                                    final int k3 = colIndex[j3] + pos[j3];
                                    rowIndex[k3] = i4;
                                    x4[k3] = e.x;
                                    final int[] array4 = pos;
                                    final int n4 = j3;
                                    ++array4[n4];
                                }
                            }
                            final SparseMatrixModified sparseMatrixModified = new SparseMatrixModified(nrow, ncol, x4, rowIndex, colIndex);
                            if (scanner != null) {
                                scanner.close();
                            }
                            if (reader != null) {
                                reader.close();
                            }
                            return sparseMatrixModified;
                        }
                        if (nrow != ncol) {
                            throw new IllegalStateException(String.format("Skew-symmetric matrix is not square: %d != %d", nrow, ncol));
                        }
                        final MatrixModified matrix3 = new MatrixModified(nrow, ncol);
                        for (int k = 0; k < nz; ++k) {
                            final String[] tokens = scanner.nextLine().trim().split("\\s+");
                            if (tokens.length != 3) {
                                throw new ParseException("Invalid data line: " + line, reader.getLineNumber());
                            }
                            final int l = Integer.parseInt(tokens[0]) - 1;
                            final int m = Integer.parseInt(tokens[1]) - 1;
                            final double x2 = Double.parseDouble(tokens[2]);
                            matrix3.set(l, m, x2);
                            matrix3.set(m, l, -x2);
                        }
                        final MatrixModified matrixModified2 = matrix3;
                        if (scanner != null) {
                            scanner.close();
                        }
                        if (reader != null) {
                            reader.close();
                        }
                        return matrixModified2;
                    }
                }
                finally {
                    if (scanner != null) {
                        scanner.close();
                    }
                }
            }
            finally {
                if (t == null) {
                    final Throwable exception;
                    t = exception;
                }
                else {
                    final Throwable exception;
                    if (t != exception) {
                        t.addSuppressed(exception);
                    }
                }
                if (reader != null) {
                    reader.close();
                }
            }
        }
        finally {
            if (t == null) {
                final Throwable exception2;
                t = exception2;
            }
            else {
                final Throwable exception2;
                if (t != exception2) {
                    t.addSuppressed(exception2);
                }
            }
        }
    }
    
    public IMatrixModified square() {
        return new Square(this);
    }
    
    public Preconditioner Jacobi() {
        final double[] diag = this.diag();
        final Object o;
        int n;
        int i;
        return (b, x) -> {
            for (n = o.length, i = 0; i < n; ++i) {
                x[i] = ((o[i] != 0.0) ? (b[i] / o[i]) : b[i]);
            }
        };
    }
    
    public double solve(final double[] b, final double[] x) {
        return this.solve(b, x, this.Jacobi(), 1.0E-6, 1, 2 * Math.max(this.nrow(), this.ncol()));
    }
    
    public double solve(final double[] b, final double[] x, final Preconditioner P, final double tol, final int itol, final int maxIter) {
        if (tol <= 0.0) {
            throw new IllegalArgumentException("Invalid tolerance: " + tol);
        }
        if (itol < 1 || itol > 4) {
            throw new IllegalArgumentException("Invalid itol: " + itol);
        }
        if (maxIter <= 0) {
            throw new IllegalArgumentException("Invalid maximum iterations: " + maxIter);
        }
        double err = 0.0;
        double bkden = 1.0;
        double znrm = 0.0;
        final int n = b.length;
        final double[] p = new double[n];
        final double[] pp = new double[n];
        final double[] r = new double[n];
        final double[] rr = new double[n];
        final double[] z = new double[n];
        final double[] zz = new double[n];
        this.mv(x, r);
        for (int j = 0; j < n; ++j) {
            rr[j] = (r[j] = b[j] - r[j]);
        }
        double bnrm;
        if (itol == 1) {
            bnrm = norm(b, itol);
            P.asolve(r, z);
        }
        else if (itol == 2) {
            P.asolve(b, z);
            bnrm = norm(z, itol);
            P.asolve(r, z);
        }
        else {
            if (itol != 3 && itol != 4) {
                throw new IllegalArgumentException(String.format("Illegal itol: %d", itol));
            }
            P.asolve(b, z);
            bnrm = norm(z, itol);
            P.asolve(r, z);
            znrm = norm(z, itol);
        }
        for (int iter = 1; iter <= maxIter; ++iter) {
            P.asolve(rr, zz);
            double bknum = 0.0;
            for (int j = 0; j < n; ++j) {
                bknum += z[j] * rr[j];
            }
            if (iter == 1) {
                for (int j = 0; j < n; ++j) {
                    p[j] = z[j];
                    pp[j] = zz[j];
                }
            }
            else {
                final double bk = bknum / bkden;
                for (int j = 0; j < n; ++j) {
                    p[j] = bk * p[j] + z[j];
                    pp[j] = bk * pp[j] + zz[j];
                }
            }
            bkden = bknum;
            this.mv(p, z);
            double akden = 0.0;
            for (int j = 0; j < n; ++j) {
                akden += z[j] * pp[j];
            }
            final double ak = bknum / akden;
            this.tv(pp, zz);
            for (int j = 0; j < n; ++j) {
                final int n2 = j;
                x[n2] += ak * p[j];
                final double[] array = r;
                final int n3 = j;
                array[n3] -= ak * z[j];
                final double[] array2 = rr;
                final int n4 = j;
                array2[n4] -= ak * zz[j];
            }
            P.asolve(r, z);
            if (itol == 1) {
                err = norm(r, itol) / bnrm;
            }
            else if (itol == 2) {
                err = norm(z, itol) / bnrm;
            }
            else if (itol == 3 || itol == 4) {
                final double zm1nrm = znrm;
                znrm = norm(z, itol);
                if (Math.abs(zm1nrm - znrm) <= MathExModified.EPSILON * znrm) {
                    err = znrm / bnrm;
                    continue;
                }
                final double dxnrm = Math.abs(ak) * norm(p, itol);
                err = znrm / Math.abs(zm1nrm - znrm) * dxnrm;
                final double xnrm = norm(x, itol);
                if (err > 0.5 * xnrm) {
                    err = znrm / bnrm;
                    continue;
                }
                err /= xnrm;
            }
            if (iter % 10 == 0) {
                IMatrixModified.logger.info(String.format("BCG: the error after %3d iterations: %.5g", iter, err));
            }
            if (err <= tol) {
                IMatrixModified.logger.info(String.format("BCG: the error after %3d iterations: %.5g", iter, err));
                break;
            }
        }
        return err;
    }
    
    private static double norm(final double[] x, final int itol) {
        final int n = x.length;
        if (itol <= 3) {
            double ans = 0.0;
            for (final double v : x) {
                ans += v * v;
            }
            return Math.sqrt(ans);
        }
        int isamax = 0;
        for (int i = 0; i < n; ++i) {
            if (Math.abs(x[i]) > Math.abs(x[isamax])) {
                isamax = i;
            }
        }
        return Math.abs(x[isamax]);
    }
    
    static class Square extends IMatrixModified
    {
        private final IMatrixModified A;
        private final int m;
        private final int n;
        private final double[] Ax;
        
        public Square(final IMatrixModified A) {
            this.A = A;
            this.m = Math.max(A.nrow(), A.ncol());
            this.n = Math.min(A.nrow(), A.ncol());
            this.Ax = new double[this.m + this.n];
        }
        
        @Override
        public int nrow() {
            return this.n;
        }
        
        @Override
        public int ncol() {
            return this.n;
        }
        
        @Override
        public long size() {
            return this.A.size();
        }
        
        @Override
        public void mv(final TransposeModified trans, final double alpha, final double[] x, final double beta, final double[] y) {
            if (this.A.nrow() >= this.A.ncol()) {
                this.A.mv(x, this.Ax);
                this.A.tv(alpha, this.Ax, beta, y);
            }
            else {
                this.A.tv(x, this.Ax);
                this.A.mv(alpha, this.Ax, beta, y);
            }
        }
        
        @Override
        public void mv(final double[] work, final int inputOffset, final int outputOffset) {
            System.arraycopy(work, inputOffset, this.Ax, 0, this.n);
            if (this.A.nrow() >= this.A.ncol()) {
                this.A.mv(this.Ax, 0, this.n);
                this.A.tv(this.Ax, this.n, 0);
            }
            else {
                this.A.tv(this.Ax, 0, this.n);
                this.A.mv(this.Ax, this.n, 0);
            }
            System.arraycopy(this.Ax, 0, work, outputOffset, this.n);
        }
        
        @Override
        public void tv(final double[] work, final int inputOffset, final int outputOffset) {
            this.mv(work, inputOffset, outputOffset);
        }
    }
    
    public interface Preconditioner
    {
        void asolve(final double[] p0, final double[] p1);
    }
}
