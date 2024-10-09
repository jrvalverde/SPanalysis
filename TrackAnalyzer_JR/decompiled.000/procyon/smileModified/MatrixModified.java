// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.io.Serializable;
import java.nio.DoubleBuffer;
import java.util.Arrays;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class MatrixModified extends IMatrixModified
{
    private static final long serialVersionUID = 3L;
    private static final Logger logger;
    double[] A;
    int ld;
    int m;
    int n;
    UPLOModified uplo;
    DiagModified diag;
    
    static {
        logger = LoggerFactory.getLogger((Class)MatrixModified.class);
    }
    
    public MatrixModified(final int m, final int n) {
        this(m, n, 0.0);
    }
    
    public MatrixModified(final int m, final int n, final double a) {
        if (m <= 0 || n <= 0) {
            throw new IllegalArgumentException(String.format("Invalid matrix size: %d x %d", m, n));
        }
        this.m = m;
        this.n = n;
        this.ld = IMatrixModified.ld(m);
        this.A = new double[this.ld * n];
        if (a != 0.0) {
            Arrays.fill(this.A, a);
        }
    }
    
    public MatrixModified(final int m, final int n, final int ld, final double[] A) {
        if (this.layout() == LayoutModified.COL_MAJOR && ld < m) {
            throw new IllegalArgumentException(String.format("Invalid leading dimension for COL_MAJOR: %d < %d", ld, m));
        }
        if (this.layout() == LayoutModified.ROW_MAJOR && ld < n) {
            throw new IllegalArgumentException(String.format("Invalid leading dimension for ROW_MAJOR: %d < %d", ld, n));
        }
        this.m = m;
        this.n = n;
        this.ld = ld;
        this.A = A;
    }
    
    public static MatrixModified of(final double[][] A) {
        final int m = A.length;
        final int n = A[0].length;
        final MatrixModified matrix = new MatrixModified(m, n);
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                matrix.set(i, j, A[i][j]);
            }
        }
        return matrix;
    }
    
    public static MatrixModified column(final double[] A) {
        return column(A, 0, A.length);
    }
    
    public static MatrixModified column(final double[] A, final int offset, final int length) {
        final MatrixModified matrix = new MatrixModified(length, 1, length, new double[length]);
        System.arraycopy(A, offset, matrix.A, 0, length);
        return matrix;
    }
    
    public static MatrixModified row(final double[] A) {
        return row(A, 0, A.length);
    }
    
    public static MatrixModified row(final double[] A, final int offset, final int length) {
        final MatrixModified matrix = new MatrixModified(1, length, 1, new double[length]);
        System.arraycopy(A, offset, matrix.A, 0, length);
        return matrix;
    }
    
    public static MatrixModified rand(final int m, final int n, final DistributionModified distribution) {
        final MatrixModified matrix = new MatrixModified(m, n);
        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < m; ++i) {
                matrix.set(i, j, distribution.rand());
            }
        }
        return matrix;
    }
    
    public static MatrixModified randn(final int m, final int n) {
        return rand(m, n, GaussianDistributionModified.getInstance());
    }
    
    public static MatrixModified rand(final int m, final int n) {
        final MatrixModified matrix = new MatrixModified(m, n);
        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < m; ++i) {
                matrix.set(i, j, MathExModified.random());
            }
        }
        return matrix;
    }
    
    public static MatrixModified rand(final int m, final int n, final double lo, final double hi) {
        final MatrixModified matrix = new MatrixModified(m, n);
        for (int j = 0; j < n; ++j) {
            for (int i = 0; i < m; ++i) {
                matrix.set(i, j, MathExModified.random(lo, hi));
            }
        }
        return matrix;
    }
    
    public static MatrixModified eye(final int n) {
        return diag(n, 1.0);
    }
    
    public static MatrixModified eye(final int m, final int n) {
        return diag(m, n, 1.0);
    }
    
    public static MatrixModified diag(final int n, final double diag) {
        return diag(n, n, diag);
    }
    
    public static MatrixModified diag(final int m, final int n, final double diag) {
        final MatrixModified D = new MatrixModified(m, n);
        for (int k = Math.min(m, n), i = 0; i < k; ++i) {
            D.set(i, i, diag);
        }
        return D;
    }
    
    public static MatrixModified diag(final double[] diag) {
        final int n = diag.length;
        final MatrixModified D = new MatrixModified(n, n);
        for (int i = 0; i < n; ++i) {
            D.set(i, i, diag[i]);
        }
        return D;
    }
    
    public static MatrixModified toeplitz(final double[] a) {
        final int n = a.length;
        final MatrixModified toeplitz = new MatrixModified(n, n);
        toeplitz.uplo(UPLOModified.LOWER);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                toeplitz.set(i, j, a[i - j]);
            }
            for (int j = i; j < n; ++j) {
                toeplitz.set(i, j, a[j - i]);
            }
        }
        return toeplitz;
    }
    
    public static MatrixModified toeplitz(final double[] kl, final double[] ku) {
        if (kl.length != ku.length - 1) {
            throw new IllegalArgumentException(String.format("Invalid sub-diagonals and super-diagonals size: %d != %d - 1", kl.length, ku.length));
        }
        final int n = kl.length;
        final MatrixModified toeplitz = new MatrixModified(n, n);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                toeplitz.set(i, j, kl[i - j]);
            }
            for (int j = i; j < n; ++j) {
                toeplitz.set(i, j, ku[j - i]);
            }
        }
        return toeplitz;
    }
    
    @Override
    public int nrow() {
        return this.m;
    }
    
    @Override
    public int ncol() {
        return this.n;
    }
    
    @Override
    public long size() {
        return this.m * this.n;
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
    
    public MatrixModified uplo(final UPLOModified uplo) {
        if (this.m != this.n) {
            throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", this.m, this.n));
        }
        this.uplo = uplo;
        return this;
    }
    
    public UPLOModified uplo() {
        return this.uplo;
    }
    
    public MatrixModified triangular(final DiagModified diag) {
        if (this.m != this.n) {
            throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", this.m, this.n));
        }
        this.diag = diag;
        return this;
    }
    
    public DiagModified triangular() {
        return this.diag;
    }
    
    public MatrixModified clone() {
        MatrixModified matrix;
        if (this.layout() == LayoutModified.COL_MAJOR) {
            matrix = new MatrixModified(this.m, this.n, this.ld, this.A.clone());
        }
        else {
            matrix = new MatrixModified(this.m, this.n);
            for (int j = 0; j < this.n; ++j) {
                for (int i = 0; i < this.m; ++i) {
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
        final double[][] array = new double[this.m][this.n];
        for (int i = 0; i < this.m; ++i) {
            for (int j = 0; j < this.n; ++j) {
                array[i][j] = this.get(i, j);
            }
        }
        return array;
    }
    
    public MatrixModified set(final MatrixModified b) {
        this.m = b.m;
        this.n = b.n;
        this.diag = b.diag;
        this.uplo = b.uplo;
        if (this.layout() == b.layout()) {
            this.A = b.A;
            this.ld = b.ld;
        }
        else if (this.layout() == LayoutModified.COL_MAJOR) {
            this.ld = IMatrixModified.ld(this.m);
            this.A = new double[this.ld * this.n];
            for (int j = 0; j < this.n; ++j) {
                for (int i = 0; i < this.m; ++i) {
                    this.set(i, j, this.get(i, j));
                }
            }
        }
        else {
            this.ld = IMatrixModified.ld(this.n);
            this.A = new double[this.ld * this.m];
            for (int k = 0; k < this.m; ++k) {
                for (int l = 0; l < this.n; ++l) {
                    this.set(k, l, this.get(k, l));
                }
            }
        }
        return this;
    }
    
    protected int index(final int i, final int j) {
        return j * this.ld + i;
    }
    
    @Override
    public double get(final int i, final int j) {
        return this.A[this.index(i, j)];
    }
    
    @Override
    public void set(final int i, final int j, final double x) {
        this.A[this.index(i, j)] = x;
    }
    
    public MatrixModified get(final int[] rows, final int[] cols) {
        final MatrixModified sub = new MatrixModified(rows.length, cols.length);
        for (int j = 0; j < cols.length; ++j) {
            int col = cols[j];
            if (col < 0) {
                col += this.n;
            }
            for (int i = 0; i < rows.length; ++i) {
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
        final double[] x = new double[this.n];
        if (i < 0) {
            i += this.m;
        }
        if (this.layout() == LayoutModified.COL_MAJOR) {
            for (int j = 0; j < this.n; ++j) {
                x[j] = this.get(i, j);
            }
        }
        else {
            System.arraycopy(this.A, this.index(i, 0), x, 0, this.n);
        }
        return x;
    }
    
    public double[] col(int j) {
        final double[] x = new double[this.m];
        if (j < 0) {
            j += this.n;
        }
        if (this.layout() == LayoutModified.COL_MAJOR) {
            System.arraycopy(this.A, this.index(0, j), x, 0, this.m);
        }
        else {
            for (int i = 0; i < this.m; ++i) {
                x[i] = this.get(i, j);
            }
        }
        return x;
    }
    
    public MatrixModified rows(final int... rows) {
        final MatrixModified x = new MatrixModified(rows.length, this.n);
        for (int i = 0; i < rows.length; ++i) {
            int row = rows[i];
            if (row < 0) {
                row += this.m;
            }
            if (this.layout() == LayoutModified.COL_MAJOR) {
                for (int j = 0; j < this.n; ++j) {
                    x.set(i, j, this.get(row, j));
                }
            }
            else {
                System.arraycopy(this.A, this.index(row, 0), x.A, x.index(i, 0), this.n);
            }
        }
        return x;
    }
    
    public MatrixModified cols(final int... cols) {
        final MatrixModified x = new MatrixModified(this.m, cols.length);
        for (int j = 0; j < cols.length; ++j) {
            int col = cols[j];
            if (col < 0) {
                col += this.n;
            }
            if (this.layout() == LayoutModified.COL_MAJOR) {
                System.arraycopy(this.A, this.index(0, col), x.A, x.index(0, j), this.m);
            }
            else {
                for (int i = 0; i < this.m; ++i) {
                    x.set(i, j, this.get(i, col));
                }
            }
        }
        return x;
    }
    
    public MatrixModified submatrix(final int i, final int j, final int k, final int l) {
        if (i < 0 || i >= this.m || k < i || k >= this.m || j < 0 || j >= this.n || l < j || l >= this.n) {
            throw new IllegalArgumentException(String.format("Invalid submatrix range (%d:%d, %d:%d) of %d x %d", i, k, j, l, this.m, this.n));
        }
        final MatrixModified sub = new MatrixModified(k - i + 1, l - j + 1);
        for (int jj = j; jj <= l; ++jj) {
            for (int ii = i; ii <= k; ++ii) {
                sub.set(ii - i, jj - j, this.get(ii, jj));
            }
        }
        return sub;
    }
    
    public void fill(final double x) {
        Arrays.fill(this.A, x);
    }
    
    public MatrixModified transpose() {
        return this.transpose(true);
    }
    
    public MatrixModified transpose(final boolean share) {
        MatrixModified matrix;
        if (share) {
            if (this.layout() == LayoutModified.ROW_MAJOR) {
                matrix = new MatrixModified(this.n, this.m, this.ld, this.A);
            }
            else {
                matrix = new RowMajor(this.n, this.m, this.ld, this.A);
            }
        }
        else {
            matrix = new MatrixModified(this.n, this.m);
            for (int j = 0; j < this.m; ++j) {
                for (int i = 0; i < this.n; ++i) {
                    matrix.set(i, j, this.get(j, i));
                }
            }
        }
        if (this.m == this.n) {
            matrix.uplo(this.uplo);
            matrix.triangular(this.diag);
        }
        return matrix;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof MatrixModified && this.equals((MatrixModified)o, 1.0E-10);
    }
    
    public boolean equals(final MatrixModified o, final double epsilon) {
        if (this.m != o.m || this.n != o.n) {
            return false;
        }
        for (int j = 0; j < this.n; ++j) {
            for (int i = 0; i < this.m; ++i) {
                if (!MathExModified.isZero(this.get(i, j) - o.get(i, j), epsilon)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public double add(final int i, final int j, final double b) {
        final double[] a = this.A;
        final int index = this.index(i, j);
        return a[index] += b;
    }
    
    public double sub(final int i, final int j, final double b) {
        final double[] a = this.A;
        final int index = this.index(i, j);
        return a[index] -= b;
    }
    
    public double mul(final int i, final int j, final double b) {
        final double[] a = this.A;
        final int index = this.index(i, j);
        return a[index] *= b;
    }
    
    public double div(final int i, final int j, final double b) {
        final double[] a = this.A;
        final int index = this.index(i, j);
        return a[index] /= b;
    }
    
    public MatrixModified addDiag(final double b) {
        for (int l = Math.min(this.m, this.n), i = 0; i < l; ++i) {
            final double[] a = this.A;
            final int index = this.index(i, i);
            a[index] += b;
        }
        return this;
    }
    
    public MatrixModified addDiag(final double[] b) {
        final int l = Math.min(this.m, this.n);
        if (b.length != l) {
            throw new IllegalArgumentException("Invalid diagonal array size: " + b.length);
        }
        for (int i = 0; i < l; ++i) {
            final double[] a = this.A;
            final int index = this.index(i, i);
            a[index] += b[i];
        }
        return this;
    }
    
    public MatrixModified add(final double b) {
        for (int i = 0; i < this.A.length; ++i) {
            final double[] a = this.A;
            final int n = i;
            a[n] += b;
        }
        return this;
    }
    
    public MatrixModified sub(final double b) {
        for (int i = 0; i < this.A.length; ++i) {
            final double[] a = this.A;
            final int n = i;
            a[n] -= b;
        }
        return this;
    }
    
    public MatrixModified mul(final double b) {
        for (int i = 0; i < this.A.length; ++i) {
            final double[] a = this.A;
            final int n = i;
            a[n] *= b;
        }
        return this;
    }
    
    public MatrixModified div(final double b) {
        for (int i = 0; i < this.A.length; ++i) {
            final double[] a = this.A;
            final int n = i;
            a[n] /= b;
        }
        return this;
    }
    
    public MatrixModified add(final MatrixModified B) {
        if (this.m != B.m || this.n != B.n) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }
        if (this.layout() == B.layout() && this.ld == B.ld) {
            for (int i = 0; i < this.A.length; ++i) {
                final double[] a = this.A;
                final int n = i;
                a[n] += B.A[i];
            }
        }
        else {
            for (int j = 0; j < this.n; ++j) {
                for (int k = 0; k < this.m; ++k) {
                    this.add(k, j, B.get(k, j));
                }
            }
        }
        return this;
    }
    
    public MatrixModified sub(final MatrixModified B) {
        if (this.m != B.m || this.n != B.n) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }
        if (this.layout() == B.layout() && this.ld == B.ld) {
            for (int i = 0; i < this.A.length; ++i) {
                final double[] a = this.A;
                final int n = i;
                a[n] -= B.A[i];
            }
        }
        else {
            for (int j = 0; j < this.n; ++j) {
                for (int k = 0; k < this.m; ++k) {
                    this.sub(k, j, B.get(k, j));
                }
            }
        }
        return this;
    }
    
    public MatrixModified mul(final MatrixModified B) {
        if (this.m != B.m || this.n != B.n) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }
        if (this.layout() == B.layout() && this.ld == B.ld) {
            for (int i = 0; i < this.A.length; ++i) {
                final double[] a = this.A;
                final int n = i;
                a[n] *= B.A[i];
            }
        }
        else {
            for (int j = 0; j < this.n; ++j) {
                for (int k = 0; k < this.m; ++k) {
                    this.mul(k, j, B.get(k, j));
                }
            }
        }
        return this;
    }
    
    public MatrixModified div(final MatrixModified B) {
        if (this.m != B.m || this.n != B.n) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }
        if (this.layout() == B.layout() && this.ld == B.ld) {
            for (int i = 0; i < this.A.length; ++i) {
                final double[] a = this.A;
                final int n = i;
                a[n] /= B.A[i];
            }
        }
        else {
            for (int j = 0; j < this.n; ++j) {
                for (int k = 0; k < this.m; ++k) {
                    this.div(k, j, B.get(k, j));
                }
            }
        }
        return this;
    }
    
    public MatrixModified add(final double beta, final MatrixModified B) {
        if (this.m != B.m || this.n != B.n) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }
        if (this.layout() == B.layout() && this.ld == B.ld) {
            for (int i = 0; i < this.A.length; ++i) {
                final double[] a = this.A;
                final int n = i;
                a[n] += beta * B.A[i];
            }
        }
        else {
            for (int j = 0; j < this.n; ++j) {
                for (int k = 0; k < this.m; ++k) {
                    this.add(k, j, beta * B.get(k, j));
                }
            }
        }
        return this;
    }
    
    public MatrixModified add(final double alpha, final double beta, final MatrixModified B) {
        if (this.m != B.m || this.n != B.n) {
            throw new IllegalArgumentException("Matrix B is not of same size.");
        }
        if (this.layout() == B.layout() && this.ld == B.ld) {
            for (int i = 0; i < this.A.length; ++i) {
                this.A[i] = alpha * this.A[i] + beta * B.A[i];
            }
        }
        else {
            for (int j = 0; j < this.n; ++j) {
                for (int k = 0; k < this.m; ++k) {
                    this.set(k, j, alpha * this.get(k, j) + beta * B.get(k, j));
                }
            }
        }
        return this;
    }
    
    public MatrixModified add2(final double alpha, final double beta, final MatrixModified B) {
        if (this.m != B.m || this.n != B.n) {
            throw new IllegalArgumentException("Matrix B is not of same size.");
        }
        if (this.layout() == B.layout() && this.ld == B.ld) {
            for (int i = 0; i < this.A.length; ++i) {
                this.A[i] = alpha * this.A[i] + beta * B.A[i] * B.A[i];
            }
        }
        else {
            for (int j = 0; j < this.n; ++j) {
                for (int k = 0; k < this.m; ++k) {
                    this.set(k, j, alpha * this.get(k, j) + beta * B.get(k, j) * B.get(k, j));
                }
            }
        }
        return this;
    }
    
    public MatrixModified add(final double alpha, final MatrixModified A, final double beta, final MatrixModified B) {
        if (this.m != A.m || this.n != A.n) {
            throw new IllegalArgumentException("Matrix A is not of same size.");
        }
        if (this.m != B.m || this.n != B.n) {
            throw new IllegalArgumentException("Matrix B is not of same size.");
        }
        if (this.layout() == A.layout() && this.layout() == B.layout() && this.ld == A.ld && this.ld == B.ld) {
            for (int i = 0; i < this.A.length; ++i) {
                this.A[i] = alpha * A.A[i] + beta * B.A[i];
            }
        }
        else {
            for (int j = 0; j < this.n; ++j) {
                for (int k = 0; k < this.m; ++k) {
                    this.set(k, j, alpha * A.get(k, j) + beta * B.get(k, j));
                }
            }
        }
        return this;
    }
    
    public MatrixModified add(final double alpha, final double[] x, final double[] y) {
        if (this.m != x.length || this.n != y.length) {
            throw new IllegalArgumentException("Matrix is not of same size.");
        }
        if (this.isSymmetric() && x == y) {
            BLASModified.engine.syr(this.layout(), this.uplo, this.m, alpha, x, 1, this.A, this.ld);
        }
        else {
            BLASModified.engine.ger(this.layout(), this.m, this.n, alpha, x, 1, y, 1, this.A, this.ld);
        }
        return this;
    }
    
    public MatrixModified replaceNaN(final double x) {
        for (int i = 0; i < this.A.length; ++i) {
            if (Double.isNaN(this.A[i])) {
                this.A[i] = x;
            }
        }
        return this;
    }
    
    public double sum() {
        double s = 0.0;
        for (int j = 0; j < this.n; ++j) {
            for (int i = 0; i < this.m; ++i) {
                s += this.get(i, j);
            }
        }
        return s;
    }
    
    public double norm1() {
        double f = 0.0;
        for (int j = 0; j < this.n; ++j) {
            double s = 0.0;
            for (int i = 0; i < this.m; ++i) {
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
        final double[] f = new double[this.m];
        for (int j = 0; j < this.n; ++j) {
            for (int i = 0; i < this.m; ++i) {
                final double[] array = f;
                final int n = i;
                array[n] += Math.abs(this.get(i, j));
            }
        }
        return MathExModified.max(f);
    }
    
    public double normFro() {
        double f = 0.0;
        for (int j = 0; j < this.n; ++j) {
            for (int i = 0; i < this.m; ++i) {
                f = Math.hypot(f, this.get(i, j));
            }
        }
        return f;
    }
    
    public double xAx(final double[] x) {
        if (this.m != this.n) {
            throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", this.m, this.n));
        }
        if (this.n != x.length) {
            throw new IllegalArgumentException(String.format("Matrix: %d x %d, Vector: %d", this.m, this.n, x.length));
        }
        final double[] Ax = this.mv(x);
        return MathExModified.dot(x, Ax);
    }
    
    public double[] rowSums() {
        final double[] x = new double[this.m];
        for (int j = 0; j < this.n; ++j) {
            for (int i = 0; i < this.m; ++i) {
                final double[] array = x;
                final int n = i;
                array[n] += this.get(i, j);
            }
        }
        return x;
    }
    
    public double[] rowMeans() {
        final double[] x = this.rowSums();
        for (int i = 0; i < this.m; ++i) {
            final double[] array = x;
            final int n = i;
            array[n] /= this.n;
        }
        return x;
    }
    
    public double[] rowSds() {
        final double[] x = new double[this.m];
        final double[] x2 = new double[this.m];
        for (int j = 0; j < this.n; ++j) {
            for (int i = 0; i < this.m; ++i) {
                final double a = this.get(i, j);
                final double[] array = x;
                final int n = i;
                array[n] += a;
                final double[] array2 = x2;
                final int n2 = i;
                array2[n2] += a * a;
            }
        }
        for (int k = 0; k < this.m; ++k) {
            final double mu = x[k] / this.n;
            x[k] = Math.sqrt(x2[k] / this.n - mu * mu);
        }
        return x;
    }
    
    public double[] colSums() {
        final double[] x = new double[this.n];
        for (int j = 0; j < this.n; ++j) {
            for (int i = 0; i < this.m; ++i) {
                final double[] array = x;
                final int n = j;
                array[n] += this.get(i, j);
            }
        }
        return x;
    }
    
    public double[] colMeans() {
        final double[] x = this.colSums();
        for (int j = 0; j < this.n; ++j) {
            final double[] array = x;
            final int n = j;
            array[n] /= this.m;
        }
        return x;
    }
    
    public double[] colSds() {
        final double[] x = new double[this.n];
        for (int j = 0; j < this.n; ++j) {
            double mu = 0.0;
            double sumsq = 0.0;
            for (int i = 0; i < this.m; ++i) {
                final double a = this.get(i, j);
                mu += a;
                sumsq += a * a;
            }
            mu /= this.m;
            x[j] = Math.sqrt(sumsq / this.m - mu * mu);
        }
        return x;
    }
    
    public MatrixModified standardize() {
        final double[] center = this.colMeans();
        final double[] scale = this.colSds();
        return this.scale(center, scale);
    }
    
    public MatrixModified scale(final double[] center, final double[] scale) {
        if (center == null && scale == null) {
            throw new IllegalArgumentException("Both center and scale are null");
        }
        final MatrixModified matrix = new MatrixModified(this.m, this.n);
        if (center == null) {
            for (int j = 0; j < this.n; ++j) {
                for (int i = 0; i < this.m; ++i) {
                    matrix.set(i, j, this.get(i, j) / scale[j]);
                }
            }
        }
        else if (scale == null) {
            for (int j = 0; j < this.n; ++j) {
                for (int i = 0; i < this.m; ++i) {
                    matrix.set(i, j, this.get(i, j) - center[j]);
                }
            }
        }
        else {
            for (int j = 0; j < this.n; ++j) {
                for (int i = 0; i < this.m; ++i) {
                    matrix.set(i, j, (this.get(i, j) - center[j]) / scale[j]);
                }
            }
        }
        return matrix;
    }
    
    public MatrixModified inverse() {
        if (this.m != this.n) {
            throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", this.m, this.n));
        }
        final MatrixModified lu = this.clone();
        final MatrixModified inv = eye(this.n);
        final int[] ipiv = new int[this.n];
        if (this.isSymmetric()) {
            final int info = LAPACKModified.engine.sysv(lu.layout(), this.uplo, this.n, this.n, lu.A, lu.ld, ipiv, inv.A, inv.ld);
            if (info != 0) {
                throw new ArithmeticException("SYSV fails: " + info);
            }
        }
        else {
            final int info = LAPACKModified.engine.gesv(lu.layout(), this.n, this.n, lu.A, lu.ld, ipiv, inv.A, inv.ld);
            if (info != 0) {
                throw new ArithmeticException("GESV fails: " + info);
            }
        }
        return inv;
    }
    
    private void mv(final TransposeModified trans, final double alpha, final DoubleBuffer x, final double beta, final DoubleBuffer y) {
        final DoubleBuffer A = DoubleBuffer.wrap(this.A);
        if (this.uplo != null) {
            if (this.diag != null) {
                if (alpha == 1.0 && beta == 0.0 && x == y) {
                    BLASModified.engine.trmv(this.layout(), this.uplo, trans, this.diag, this.m, A, this.ld, y, 1);
                }
                else {
                    BLASModified.engine.gemv(this.layout(), trans, this.m, this.n, alpha, A, this.ld, x, 1, beta, y, 1);
                }
            }
            else {
                BLASModified.engine.symv(this.layout(), this.uplo, this.m, alpha, A, this.ld, x, 1, beta, y, 1);
            }
        }
        else {
            BLASModified.engine.gemv(this.layout(), trans, this.m, this.n, alpha, A, this.ld, x, 1, beta, y, 1);
        }
    }
    
    @Override
    public void mv(final TransposeModified trans, final double alpha, final double[] x, final double beta, final double[] y) {
        if (this.uplo != null) {
            if (this.diag != null) {
                if (alpha == 1.0 && beta == 0.0 && x == y) {
                    BLASModified.engine.trmv(this.layout(), this.uplo, trans, this.diag, this.m, this.A, this.ld, y, 1);
                }
                else {
                    BLASModified.engine.gemv(this.layout(), trans, this.m, this.n, alpha, this.A, this.ld, x, 1, beta, y, 1);
                }
            }
            else {
                BLASModified.engine.symv(this.layout(), this.uplo, this.m, alpha, this.A, this.ld, x, 1, beta, y, 1);
            }
        }
        else {
            BLASModified.engine.gemv(this.layout(), trans, this.m, this.n, alpha, this.A, this.ld, x, 1, beta, y, 1);
        }
    }
    
    @Override
    public void mv(final double[] work, final int inputOffset, final int outputOffset) {
        final DoubleBuffer xb = DoubleBuffer.wrap(work, inputOffset, this.n);
        final DoubleBuffer yb = DoubleBuffer.wrap(work, outputOffset, this.m);
        this.mv(TransposeModified.NO_TRANSPOSE, 1.0, xb, 0.0, yb);
    }
    
    @Override
    public void tv(final double[] work, final int inputOffset, final int outputOffset) {
        final DoubleBuffer xb = DoubleBuffer.wrap(work, inputOffset, this.m);
        final DoubleBuffer yb = DoubleBuffer.wrap(work, outputOffset, this.n);
        this.mv(TransposeModified.TRANSPOSE, 1.0, xb, 0.0, yb);
    }
    
    public MatrixModified mm(final TransposeModified transA, final MatrixModified A, final TransposeModified transB, final MatrixModified B) {
        return this.mm(transA, A, transB, B, 1.0, 0.0);
    }
    
    public MatrixModified mm(TransposeModified transA, MatrixModified A, TransposeModified transB, MatrixModified B, final double alpha, final double beta) {
        if (A.isSymmetric() && transB == TransposeModified.NO_TRANSPOSE && B.layout() == this.layout()) {
            BLASModified.engine.symm(this.layout(), SideModified.LEFT, A.uplo, this.m, this.n, alpha, A.A, A.ld, B.A, B.ld, beta, this.A, this.ld);
        }
        else if (B.isSymmetric() && transA == TransposeModified.NO_TRANSPOSE && A.layout() == this.layout()) {
            BLASModified.engine.symm(this.layout(), SideModified.RIGHT, B.uplo, this.m, this.n, alpha, B.A, B.ld, A.A, A.ld, beta, this.A, this.ld);
        }
        else {
            if (this.layout() != A.layout()) {
                transA = IMatrixModified.flip(transA);
                A = A.transpose();
            }
            if (this.layout() != B.layout()) {
                transB = IMatrixModified.flip(transB);
                B = B.transpose();
            }
            final int k = (transA == TransposeModified.NO_TRANSPOSE) ? A.n : A.m;
            BLASModified.engine.gemm(this.layout(), transA, transB, this.m, this.n, k, alpha, A.A, A.ld, B.A, B.ld, beta, this.A, this.ld);
        }
        return this;
    }
    
    public MatrixModified ata() {
        final MatrixModified C = new MatrixModified(this.n, this.n);
        C.mm(TransposeModified.TRANSPOSE, this, TransposeModified.NO_TRANSPOSE, this);
        C.uplo(UPLOModified.LOWER);
        return C;
    }
    
    public MatrixModified aat() {
        final MatrixModified C = new MatrixModified(this.m, this.m);
        C.mm(TransposeModified.NO_TRANSPOSE, this, TransposeModified.TRANSPOSE, this);
        C.uplo(UPLOModified.LOWER);
        return C;
    }
    
    public static MatrixModified adb(final TransposeModified transA, final MatrixModified A, final double[] D, final TransposeModified transB, final MatrixModified B) {
        final int m = A.m;
        final int n = A.n;
        MatrixModified AD;
        if (transA == TransposeModified.NO_TRANSPOSE) {
            AD = new MatrixModified(m, n);
            for (int j = 0; j < n; ++j) {
                final double dj = D[j];
                for (int i = 0; i < m; ++i) {
                    AD.set(i, j, dj * A.get(i, j));
                }
            }
        }
        else {
            AD = new MatrixModified(n, m);
            for (int j = 0; j < m; ++j) {
                final double dj = D[j];
                for (int i = 0; i < n; ++i) {
                    AD.set(i, j, dj * A.get(j, i));
                }
            }
        }
        return (transB == TransposeModified.NO_TRANSPOSE) ? AD.mm(B) : AD.mt(B);
    }
    
    public MatrixModified mm(final MatrixModified B) {
        if (this.n != B.m) {
            throw new IllegalArgumentException(String.format("Matrix multiplication A * B: %d x %d vs %d x %d", this.m, this.n, B.m, B.n));
        }
        final MatrixModified C = new MatrixModified(this.m, B.n);
        C.mm(TransposeModified.NO_TRANSPOSE, this, TransposeModified.NO_TRANSPOSE, B);
        return C;
    }
    
    public MatrixModified mt(final MatrixModified B) {
        if (this.n != B.n) {
            throw new IllegalArgumentException(String.format("Matrix multiplication A * B': %d x %d vs %d x %d", this.m, this.n, B.m, B.n));
        }
        final MatrixModified C = new MatrixModified(this.m, B.m);
        C.mm(TransposeModified.NO_TRANSPOSE, this, TransposeModified.TRANSPOSE, B);
        return C;
    }
    
    public MatrixModified tm(final MatrixModified B) {
        if (this.m != B.m) {
            throw new IllegalArgumentException(String.format("Matrix multiplication A' * B: %d x %d vs %d x %d", this.m, this.n, B.m, B.n));
        }
        final MatrixModified C = new MatrixModified(this.n, B.n);
        C.mm(TransposeModified.TRANSPOSE, this, TransposeModified.NO_TRANSPOSE, B);
        return C;
    }
    
    public MatrixModified tt(final MatrixModified B) {
        if (this.m != B.n) {
            throw new IllegalArgumentException(String.format("Matrix multiplication A' * B': %d x %d vs %d x %d", this.m, this.n, B.m, B.n));
        }
        final MatrixModified C = new MatrixModified(this.n, B.m);
        C.mm(TransposeModified.TRANSPOSE, this, TransposeModified.TRANSPOSE, B);
        return C;
    }
    
    public LU lu() {
        return this.lu(false);
    }
    
    public LU lu(final boolean overwrite) {
        final MatrixModified lu = overwrite ? this : this.clone();
        final int[] ipiv = new int[Math.min(this.m, this.n)];
        final int info = LAPACKModified.engine.getrf(lu.layout(), lu.m, lu.n, lu.A, lu.ld, ipiv);
        if (info < 0) {
            MatrixModified.logger.error("LAPACK GETRF error code: {}", (Object)info);
            throw new ArithmeticException("LAPACK GETRF error code: " + info);
        }
        lu.uplo = null;
        return new LU(lu, ipiv, info);
    }
    
    public Cholesky cholesky() {
        return this.cholesky(false);
    }
    
    public Cholesky cholesky(final boolean overwrite) {
        if (this.uplo == null) {
            throw new IllegalArgumentException("The matrix is not symmetric");
        }
        final MatrixModified lu = overwrite ? this : this.clone();
        final int info = LAPACKModified.engine.potrf(lu.layout(), lu.uplo, lu.n, lu.A, lu.ld);
        if (info != 0) {
            MatrixModified.logger.error("LAPACK GETRF error code: {}", (Object)info);
            throw new ArithmeticException("LAPACK GETRF error code: " + info);
        }
        return new Cholesky(lu);
    }
    
    public QR qr() {
        return this.qr(false);
    }
    
    public QR qr(final boolean overwrite) {
        final MatrixModified qr = overwrite ? this : this.clone();
        final double[] tau = new double[Math.min(this.m, this.n)];
        final int info = LAPACKModified.engine.geqrf(qr.layout(), qr.m, qr.n, qr.A, qr.ld, tau);
        if (info != 0) {
            MatrixModified.logger.error("LAPACK GEQRF error code: {}", (Object)info);
            throw new ArithmeticException("LAPACK GEQRF error code: " + info);
        }
        qr.uplo = null;
        return new QR(qr, tau);
    }
    
    public SVD svd() {
        return this.svd(true, false);
    }
    
    public SVD svd(final boolean vectors, final boolean overwrite) {
        final int k = Math.min(this.m, this.n);
        final double[] s = new double[k];
        final MatrixModified W = overwrite ? this : this.clone();
        if (vectors) {
            final MatrixModified U = new MatrixModified(this.m, k);
            final MatrixModified VT = new MatrixModified(k, this.n);
            final int info = LAPACKModified.engine.gesdd(W.layout(), SVDJobModified.COMPACT, W.m, W.n, W.A, W.ld, s, U.A, U.ld, VT.A, VT.ld);
            if (info != 0) {
                MatrixModified.logger.error("LAPACK GESDD error code: {}", (Object)info);
                throw new ArithmeticException("LAPACK GESDD error code: " + info);
            }
            return new SVD(s, U, VT.transpose());
        }
        else {
            final MatrixModified U = new MatrixModified(1, 1);
            final MatrixModified VT = new MatrixModified(1, 1);
            final int info = LAPACKModified.engine.gesdd(W.layout(), SVDJobModified.NO_VECTORS, W.m, W.n, W.A, W.ld, s, U.A, U.ld, VT.A, VT.ld);
            if (info != 0) {
                MatrixModified.logger.error("LAPACK GESDD error code: {}", (Object)info);
                throw new ArithmeticException("LAPACK GESDD error code: " + info);
            }
            return new SVD(this.m, this.n, s);
        }
    }
    
    public EVD eigen() {
        return this.eigen(false, true, false);
    }
    
    public EVD eigen(final boolean vl, final boolean vr, final boolean overwrite) {
        if (this.m != this.n) {
            throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", this.m, this.n));
        }
        final MatrixModified eig = overwrite ? this : this.clone();
        if (this.isSymmetric()) {
            final double[] w = new double[this.n];
            final int info = LAPACKModified.engine.syevd(eig.layout(), vr ? EVDJobModified.VECTORS : EVDJobModified.NO_VECTORS, eig.uplo, this.n, eig.A, eig.ld, w);
            if (info != 0) {
                MatrixModified.logger.error("LAPACK SYEV error code: {}", (Object)info);
                throw new ArithmeticException("LAPACK SYEV error code: " + info);
            }
            eig.uplo = null;
            return new EVD(w, vr ? eig : null);
        }
        else {
            final double[] wr = new double[this.n];
            final double[] wi = new double[this.n];
            final MatrixModified Vl = vl ? new MatrixModified(this.n, this.n) : new MatrixModified(1, 1);
            final MatrixModified Vr = vr ? new MatrixModified(this.n, this.n) : new MatrixModified(1, 1);
            final int info2 = LAPACKModified.engine.geev(eig.layout(), vl ? EVDJobModified.VECTORS : EVDJobModified.NO_VECTORS, vr ? EVDJobModified.VECTORS : EVDJobModified.NO_VECTORS, this.n, eig.A, eig.ld, wr, wi, Vl.A, Vl.ld, Vr.A, Vr.ld);
            if (info2 != 0) {
                MatrixModified.logger.error("LAPACK GEEV error code: {}", (Object)info2);
                throw new ArithmeticException("LAPACK GEEV error code: " + info2);
            }
            return new EVD(wr, wi, vl ? Vl : null, vr ? Vr : null);
        }
    }
    
    private static class RowMajor extends MatrixModified
    {
        RowMajor(final int m, final int n, final int ld, final double[] A) {
            super(m, n, ld, A);
        }
        
        @Override
        public LayoutModified layout() {
            return LayoutModified.ROW_MAJOR;
        }
        
        @Override
        protected int index(final int i, final int j) {
            return i * this.ld + j;
        }
    }
    
    public static class SVD implements Serializable
    {
        private static final long serialVersionUID = 2L;
        public final int m;
        public final int n;
        public final double[] s;
        public final MatrixModified U;
        public final MatrixModified V;
        private transient MatrixModified Ur;
        
        public SVD(final int m, final int n, final double[] s) {
            this.m = m;
            this.n = n;
            this.s = s;
            this.U = null;
            this.V = null;
        }
        
        public SVD(final double[] s, final MatrixModified U, final MatrixModified V) {
            this.m = U.m;
            this.n = V.m;
            this.s = s;
            this.U = U;
            this.V = V;
        }
        
        public MatrixModified diag() {
            final MatrixModified S = new MatrixModified(this.U.m, this.V.m);
            for (int i = 0; i < this.s.length; ++i) {
                S.set(i, i, this.s[i]);
            }
            return S;
        }
        
        public double norm() {
            return this.s[0];
        }
        
        private double rcond() {
            return 0.5 * Math.sqrt(this.m + this.n + 1) * this.s[0] * MathExModified.EPSILON;
        }
        
        public int rank() {
            if (this.s.length != Math.min(this.m, this.n)) {
                throw new UnsupportedOperationException("The operation cannot be called on a partial SVD.");
            }
            int r = 0;
            final double tol = this.rcond();
            double[] s;
            for (int length = (s = this.s).length, i = 0; i < length; ++i) {
                final double si = s[i];
                if (si > tol) {
                    ++r;
                }
            }
            return r;
        }
        
        public int nullity() {
            return Math.min(this.m, this.n) - this.rank();
        }
        
        public double condition() {
            if (this.s.length != Math.min(this.m, this.n)) {
                throw new UnsupportedOperationException("The operation cannot be called on a partial SVD.");
            }
            return (this.s[0] <= 0.0 || this.s[this.s.length - 1] <= 0.0) ? Double.POSITIVE_INFINITY : (this.s[0] / this.s[this.s.length - 1]);
        }
        
        public MatrixModified range() {
            if (this.s.length != Math.min(this.m, this.n)) {
                throw new UnsupportedOperationException("The operation cannot be called on a partial SVD.");
            }
            if (this.U == null) {
                throw new IllegalStateException("The left singular vectors are not available.");
            }
            final int r = this.rank();
            if (r == 0) {
                return null;
            }
            final MatrixModified R = new MatrixModified(this.m, r);
            for (int j = 0; j < r; ++j) {
                for (int i = 0; i < this.m; ++i) {
                    R.set(i, j, this.U.get(i, j));
                }
            }
            return R;
        }
        
        public MatrixModified nullspace() {
            if (this.s.length != Math.min(this.m, this.n)) {
                throw new UnsupportedOperationException("The operation cannot be called on a partial SVD.");
            }
            if (this.V == null) {
                throw new IllegalStateException("The right singular vectors are not available.");
            }
            final int nr = this.nullity();
            if (nr == 0) {
                return null;
            }
            final MatrixModified N = new MatrixModified(this.n, nr);
            for (int j = 0; j < nr; ++j) {
                for (int i = 0; i < this.n; ++i) {
                    N.set(i, j, this.V.get(i, this.n - j - 1));
                }
            }
            return N;
        }
        
        public MatrixModified pinv() {
            final int k = this.s.length;
            final double[] sigma = new double[k];
            for (int r = this.rank(), i = 0; i < r; ++i) {
                sigma[i] = 1.0 / this.s[i];
            }
            return MatrixModified.adb(TransposeModified.NO_TRANSPOSE, this.V, sigma, TransposeModified.TRANSPOSE, this.U);
        }
        
        public double[] solve(final double[] b) {
            if (this.U == null || this.V == null) {
                throw new IllegalStateException("The singular vectors are not available.");
            }
            if (b.length != this.m) {
                throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x 1", this.m, this.n, b.length));
            }
            final int r = this.rank();
            if (this.Ur == null) {
                this.Ur = ((r == this.U.ncol()) ? this.U : this.U.submatrix(0, 0, this.m - 1, r - 1));
            }
            final double[] Utb = new double[this.s.length];
            this.Ur.tv(b, Utb);
            for (int i = 0; i < r; ++i) {
                final double[] array = Utb;
                final int n = i;
                array[n] /= this.s[i];
            }
            return this.V.mv(Utb);
        }
    }
    
    public static class EVD implements Serializable
    {
        private static final long serialVersionUID = 2L;
        public final double[] wr;
        public final double[] wi;
        public final MatrixModified Vl;
        public final MatrixModified Vr;
        
        public EVD(final double[] w, final MatrixModified V) {
            this.wr = w;
            this.wi = null;
            this.Vl = V;
            this.Vr = V;
        }
        
        public EVD(final double[] wr, final double[] wi, final MatrixModified Vl, final MatrixModified Vr) {
            this.wr = wr;
            this.wi = wi;
            this.Vl = Vl;
            this.Vr = Vr;
        }
        
        public MatrixModified diag() {
            final MatrixModified D = MatrixModified.diag(this.wr);
            if (this.wi != null) {
                for (int n = this.wr.length, i = 0; i < n; ++i) {
                    if (this.wi[i] > 0.0) {
                        D.set(i, i + 1, this.wi[i]);
                    }
                    else if (this.wi[i] < 0.0) {
                        D.set(i, i - 1, this.wi[i]);
                    }
                }
            }
            return D;
        }
        
        public EVD sort() {
            final int n = this.wr.length;
            final double[] w = new double[n];
            if (this.wi != null) {
                for (int i = 0; i < n; ++i) {
                    w[i] = -(this.wr[i] * this.wr[i] + this.wi[i] * this.wi[i]);
                }
            }
            else {
                for (int i = 0; i < n; ++i) {
                    w[i] = -(this.wr[i] * this.wr[i]);
                }
            }
            final int[] index = QuickSortModified.sort(w);
            final double[] wr2 = new double[n];
            for (int j = 0; j < n; ++j) {
                wr2[j] = this.wr[index[j]];
            }
            double[] wi2 = null;
            if (this.wi != null) {
                wi2 = new double[n];
                for (int k = 0; k < n; ++k) {
                    wi2[k] = this.wi[index[k]];
                }
            }
            MatrixModified Vl2 = null;
            if (this.Vl != null) {
                final int m = this.Vl.m;
                Vl2 = new MatrixModified(m, n);
                for (int l = 0; l < n; ++l) {
                    for (int i2 = 0; i2 < m; ++i2) {
                        Vl2.set(i2, l, this.Vl.get(i2, index[l]));
                    }
                }
            }
            MatrixModified Vr2 = null;
            if (this.Vr != null) {
                final int m2 = this.Vr.m;
                Vr2 = new MatrixModified(m2, n);
                for (int j2 = 0; j2 < n; ++j2) {
                    for (int i3 = 0; i3 < m2; ++i3) {
                        Vr2.set(i3, j2, this.Vr.get(i3, index[j2]));
                    }
                }
            }
            return new EVD(wr2, wi2, Vl2, Vr2);
        }
    }
    
    public static class LU implements Serializable
    {
        private static final long serialVersionUID = 2L;
        public final MatrixModified lu;
        public final int[] ipiv;
        public final int info;
        
        public LU(final MatrixModified lu, final int[] ipiv, final int info) {
            this.lu = lu;
            this.ipiv = ipiv;
            this.info = info;
        }
        
        public boolean isSingular() {
            return this.info > 0;
        }
        
        public double det() {
            final int m = this.lu.m;
            final int n = this.lu.n;
            if (m != n) {
                throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", m, n));
            }
            double d = 1.0;
            for (int j = 0; j < n; ++j) {
                d *= this.lu.get(j, j);
            }
            for (int j = 0; j < n; ++j) {
                if (j + 1 != this.ipiv[j]) {
                    d = -d;
                }
            }
            return d;
        }
        
        public MatrixModified inverse() {
            final MatrixModified inv = MatrixModified.eye(this.lu.n);
            this.solve(inv);
            return inv;
        }
        
        public double[] solve(final double[] b) {
            final MatrixModified x = MatrixModified.column(b);
            this.solve(x);
            return x.A;
        }
        
        public void solve(final MatrixModified B) {
            if (this.lu.m != this.lu.n) {
                throw new IllegalArgumentException(String.format("The matrix is not square: %d x %d", this.lu.m, this.lu.n));
            }
            if (B.m != this.lu.m) {
                throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", this.lu.m, this.lu.n, B.m, B.n));
            }
            if (this.lu.layout() != B.layout()) {
                throw new IllegalArgumentException("The matrix layout is inconsistent.");
            }
            if (this.info > 0) {
                throw new RuntimeException("The matrix is singular.");
            }
            final int ret = LAPACKModified.engine.getrs(this.lu.layout(), TransposeModified.NO_TRANSPOSE, this.lu.n, B.n, this.lu.A, this.lu.ld, this.ipiv, B.A, B.ld);
            if (ret != 0) {
                MatrixModified.logger.error("LAPACK GETRS error code: {}", (Object)ret);
                throw new ArithmeticException("LAPACK GETRS error code: " + ret);
            }
        }
    }
    
    public static class Cholesky implements Serializable
    {
        private static final long serialVersionUID = 2L;
        public final MatrixModified lu;
        
        public Cholesky(final MatrixModified lu) {
            if (lu.nrow() != lu.ncol()) {
                throw new UnsupportedOperationException("Cholesky constructor on a non-square matrix");
            }
            this.lu = lu;
        }
        
        public double det() {
            final int n = this.lu.n;
            double d = 1.0;
            for (int i = 0; i < n; ++i) {
                d *= this.lu.get(i, i);
            }
            return d * d;
        }
        
        public double logdet() {
            final int n = this.lu.n;
            double d = 0.0;
            for (int i = 0; i < n; ++i) {
                d += Math.log(this.lu.get(i, i));
            }
            return 2.0 * d;
        }
        
        public MatrixModified inverse() {
            final MatrixModified inv = MatrixModified.eye(this.lu.n);
            this.solve(inv);
            return inv;
        }
        
        public double[] solve(final double[] b) {
            final MatrixModified x = MatrixModified.column(b);
            this.solve(x);
            return x.A;
        }
        
        public void solve(final MatrixModified B) {
            if (B.m != this.lu.m) {
                throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", this.lu.m, this.lu.n, B.m, B.n));
            }
            final int info = LAPACKModified.engine.potrs(this.lu.layout(), this.lu.uplo, this.lu.n, B.n, this.lu.A, this.lu.ld, B.A, B.ld);
            if (info != 0) {
                MatrixModified.logger.error("LAPACK POTRS error code: {}", (Object)info);
                throw new ArithmeticException("LAPACK POTRS error code: " + info);
            }
        }
    }
    
    public static class QR implements Serializable
    {
        private static final long serialVersionUID = 2L;
        public final MatrixModified qr;
        public final double[] tau;
        
        public QR(final MatrixModified qr, final double[] tau) {
            this.qr = qr;
            this.tau = tau;
        }
        
        public Cholesky CholeskyOfAtA() {
            final int n = this.qr.n;
            final MatrixModified L = new MatrixModified(n, n);
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j <= i; ++j) {
                    L.set(i, j, this.qr.get(j, i));
                }
            }
            L.uplo(UPLOModified.LOWER);
            return new Cholesky(L);
        }
        
        public MatrixModified R() {
            final int n = this.qr.n;
            final MatrixModified R = MatrixModified.diag(this.tau);
            for (int i = 0; i < n; ++i) {
                for (int j = i; j < n; ++j) {
                    R.set(i, j, this.qr.get(i, j));
                }
            }
            return R;
        }
        
        public MatrixModified Q() {
            final int m = this.qr.m;
            final int n = this.qr.n;
            final int k = Math.min(m, n);
            final MatrixModified Q = this.qr.clone();
            final int info = LAPACKModified.engine.orgqr(this.qr.layout(), m, n, k, Q.A, this.qr.ld, this.tau);
            if (info != 0) {
                MatrixModified.logger.error("LAPACK ORGRQ error code: {}", (Object)info);
                throw new ArithmeticException("LAPACK ORGRQ error code: " + info);
            }
            return Q;
        }
        
        public double[] solve(final double[] b) {
            if (b.length != this.qr.m) {
                throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x 1", this.qr.m, this.qr.n, b.length));
            }
            final MatrixModified x = MatrixModified.column(b);
            this.solve(x);
            return Arrays.copyOf(x.A, this.qr.n);
        }
        
        public void solve(final MatrixModified B) {
            if (B.m != this.qr.m) {
                throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", this.qr.nrow(), this.qr.nrow(), B.nrow(), B.ncol()));
            }
            final int m = this.qr.m;
            final int n = this.qr.n;
            final int k = Math.min(m, n);
            int info = LAPACKModified.engine.ormqr(this.qr.layout(), SideModified.LEFT, TransposeModified.TRANSPOSE, B.nrow(), B.ncol(), k, this.qr.A, this.qr.ld, this.tau, B.A, B.ld);
            if (info != 0) {
                MatrixModified.logger.error("LAPACK ORMQR error code: {}", (Object)info);
                throw new IllegalArgumentException("LAPACK ORMQR error code: " + info);
            }
            info = LAPACKModified.engine.trtrs(this.qr.layout(), UPLOModified.UPPER, TransposeModified.NO_TRANSPOSE, DiagModified.NON_UNIT, this.qr.n, B.n, this.qr.A, this.qr.ld, B.A, B.ld);
            if (info != 0) {
                MatrixModified.logger.error("LAPACK TRTRS error code: {}", (Object)info);
                throw new IllegalArgumentException("LAPACK TRTRS error code: " + info);
            }
        }
    }
}
