// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.io.Serializable;
import java.nio.DoubleBuffer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class SymmMatrixModified extends IMatrixModified
{
    private static final long serialVersionUID = 2L;
    private static final Logger logger;
    final double[] AP;
    final int n;
    final UPLOModified uplo;
    
    static {
        logger = LoggerFactory.getLogger((Class)SymmMatrixModified.class);
    }
    
    public SymmMatrixModified(final UPLOModified uplo, final int n) {
        if (uplo == null) {
            throw new NullPointerException("UPLO is null");
        }
        this.uplo = uplo;
        this.n = n;
        this.AP = new double[n * (n + 1) / 2];
    }
    
    public SymmMatrixModified(final UPLOModified uplo, final double[][] AP) {
        this(uplo, AP.length);
        if (uplo == UPLOModified.LOWER) {
            for (int i = 0; i < this.n; ++i) {
                for (int j = 0; j <= i; ++j) {
                    this.AP[i + (2 * this.n - j - 1) * j / 2] = AP[i][j];
                }
            }
        }
        else {
            for (int i = 0; i < this.n; ++i) {
                for (int j = i; j < this.n; ++j) {
                    this.AP[i + j * (j + 1) / 2] = AP[i][j];
                }
            }
        }
    }
    
    public SymmMatrixModified clone() {
        final SymmMatrixModified matrix = new SymmMatrixModified(this.uplo, this.n);
        System.arraycopy(this.AP, 0, matrix.AP, 0, this.AP.length);
        return matrix;
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
        return this.AP.length;
    }
    
    public LayoutModified layout() {
        return LayoutModified.COL_MAJOR;
    }
    
    public UPLOModified uplo() {
        return this.uplo;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof SymmMatrixModified && this.equals((SymmMatrixModified)o, 1.0E-10);
    }
    
    public boolean equals(final SymmMatrixModified o, final double epsilon) {
        if (this.n != o.n) {
            return false;
        }
        for (int j = 0; j < this.n; ++j) {
            for (int i = 0; i < this.n; ++i) {
                if (!MathExModified.isZero(this.get(i, j) - o.get(i, j), epsilon)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public double get(int i, int j) {
        if (this.uplo == UPLOModified.LOWER) {
            if (j > i) {
                final int tmp = i;
                i = j;
                j = tmp;
            }
            return this.AP[i + (2 * this.n - j - 1) * j / 2];
        }
        if (i > j) {
            final int tmp = i;
            i = j;
            j = tmp;
        }
        return this.AP[i + j * (j + 1) / 2];
    }
    
    @Override
    public void set(int i, int j, final double x) {
        if (this.uplo == UPLOModified.LOWER) {
            if (j > i) {
                final int tmp = i;
                i = j;
                j = tmp;
            }
            this.AP[i + (2 * this.n - j - 1) * j / 2] = x;
        }
        else {
            if (i > j) {
                final int tmp = i;
                i = j;
                j = tmp;
            }
            this.AP[i + j * (j + 1) / 2] = x;
        }
    }
    
    @Override
    public void mv(final TransposeModified trans, final double alpha, final double[] x, final double beta, final double[] y) {
        BLASModified.engine.spmv(this.layout(), this.uplo, this.n, alpha, this.AP, x, 1, beta, y, 1);
    }
    
    @Override
    public void mv(final double[] work, final int inputOffset, final int outputOffset) {
        final DoubleBuffer xb = DoubleBuffer.wrap(work, inputOffset, this.n);
        final DoubleBuffer yb = DoubleBuffer.wrap(work, outputOffset, this.n);
        BLASModified.engine.spmv(this.layout(), this.uplo, this.n, 1.0, DoubleBuffer.wrap(this.AP), xb, 1, 0.0, yb, 1);
    }
    
    @Override
    public void tv(final double[] work, final int inputOffset, final int outputOffset) {
        this.mv(work, inputOffset, outputOffset);
    }
    
    public BunchKaufman bk() {
        final SymmMatrixModified lu = this.clone();
        final int[] ipiv = new int[this.n];
        final int info = LAPACKModified.engine.sptrf(lu.layout(), lu.uplo, lu.n, lu.AP, ipiv);
        if (info < 0) {
            SymmMatrixModified.logger.error("LAPACK SPTRF error code: {}", (Object)info);
            throw new ArithmeticException("LAPACK SPTRF error code: " + info);
        }
        return new BunchKaufman(lu, ipiv, info);
    }
    
    public Cholesky cholesky() {
        if (this.uplo == null) {
            throw new IllegalArgumentException("The matrix is not symmetric");
        }
        final SymmMatrixModified lu = this.clone();
        final int info = LAPACKModified.engine.pptrf(lu.layout(), lu.uplo, lu.n, lu.AP);
        if (info != 0) {
            SymmMatrixModified.logger.error("LAPACK PPTRF error code: {}", (Object)info);
            throw new ArithmeticException("LAPACK PPTRF error code: " + info);
        }
        return new Cholesky(lu);
    }
    
    public static class BunchKaufman implements Serializable
    {
        private static final long serialVersionUID = 2L;
        public final SymmMatrixModified lu;
        public final int[] ipiv;
        public final int info;
        
        public BunchKaufman(final SymmMatrixModified lu, final int[] ipiv, final int info) {
            this.lu = lu;
            this.ipiv = ipiv;
            this.info = info;
        }
        
        public boolean isSingular() {
            return this.info > 0;
        }
        
        public double det() {
            final int n = this.lu.n;
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
            if (B.m != this.lu.n) {
                throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", this.lu.n, this.lu.n, B.m, B.n));
            }
            if (this.lu.layout() != B.layout()) {
                throw new IllegalArgumentException("The matrix layout is inconsistent.");
            }
            if (this.info > 0) {
                throw new RuntimeException("The matrix is singular.");
            }
            final int ret = LAPACKModified.engine.sptrs(this.lu.layout(), this.lu.uplo, this.lu.n, B.n, this.lu.AP, this.ipiv, B.A, B.ld);
            if (ret != 0) {
                SymmMatrixModified.logger.error("LAPACK GETRS error code: {}", (Object)ret);
                throw new ArithmeticException("LAPACK GETRS error code: " + ret);
            }
        }
    }
    
    public static class Cholesky implements Serializable
    {
        private static final long serialVersionUID = 2L;
        public final SymmMatrixModified lu;
        
        public Cholesky(final SymmMatrixModified lu) {
            if (lu.nrow() != lu.ncol()) {
                throw new UnsupportedOperationException("Cholesky constructor on a non-square matrix");
            }
            this.lu = lu;
        }
        
        public double det() {
            double d = 1.0;
            for (int i = 0; i < this.lu.n; ++i) {
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
            if (B.m != this.lu.n) {
                throw new IllegalArgumentException(String.format("Row dimensions do not agree: A is %d x %d, but B is %d x %d", this.lu.n, this.lu.n, B.m, B.n));
            }
            final int info = LAPACKModified.engine.pptrs(this.lu.layout(), this.lu.uplo, this.lu.n, B.n, this.lu.AP, B.A, B.ld);
            if (info != 0) {
                SymmMatrixModified.logger.error("LAPACK POTRS error code: {}", (Object)info);
                throw new ArithmeticException("LAPACK POTRS error code: " + info);
            }
        }
    }
}
