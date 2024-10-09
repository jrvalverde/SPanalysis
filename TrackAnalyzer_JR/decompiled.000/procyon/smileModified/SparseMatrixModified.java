// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Spliterator;
import java.util.stream.StreamSupport;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class SparseMatrixModified extends IMatrixModified implements Iterable<Entry>
{
    private static final Logger logger;
    private static final long serialVersionUID = 2L;
    private final int m;
    private final int n;
    private final int[] colIndex;
    private final int[] rowIndex;
    private final double[] nonzeros;
    
    static {
        logger = LoggerFactory.getLogger((Class)SparseMatrixModified.class);
    }
    
    private SparseMatrixModified(final int m, final int n, final int nvals) {
        this.m = m;
        this.n = n;
        this.rowIndex = new int[nvals];
        this.colIndex = new int[n + 1];
        this.nonzeros = new double[nvals];
    }
    
    public SparseMatrixModified(final int m, final int n, final double[] nonzeros, final int[] rowIndex, final int[] colIndex) {
        this.m = m;
        this.n = n;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.nonzeros = nonzeros;
    }
    
    public SparseMatrixModified(final double[][] A) {
        this(A, 100.0 * MathExModified.EPSILON);
    }
    
    public SparseMatrixModified(final double[][] A, final double tol) {
        this.m = A.length;
        this.n = A[0].length;
        int nvals = 0;
        for (int i = 0; i < this.m; ++i) {
            for (int j = 0; j < this.n; ++j) {
                if (Math.abs(A[i][j]) >= tol) {
                    ++nvals;
                }
            }
        }
        this.nonzeros = new double[nvals];
        this.rowIndex = new int[nvals];
        (this.colIndex = new int[this.n + 1])[this.n] = nvals;
        int k = 0;
        for (int j = 0; j < this.n; ++j) {
            this.colIndex[j] = k;
            for (int l = 0; l < this.m; ++l) {
                if (Math.abs(A[l][j]) >= tol) {
                    this.rowIndex[k] = l;
                    this.nonzeros[k] = A[l][j];
                    ++k;
                }
            }
        }
    }
    
    public SparseMatrixModified clone() {
        return new SparseMatrixModified(this.m, this.n, this.nonzeros.clone(), this.rowIndex.clone(), this.colIndex.clone());
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
        return this.colIndex[this.n];
    }
    
    public Stream<Entry> nonzeros() {
        final Spliterator<Entry> spliterator = Spliterators.<Entry>spliterator((Iterator<? extends Entry>)this.iterator(), this.size(), 1104);
        return StreamSupport.<Entry>stream(spliterator, false);
    }
    
    public Stream<Entry> nonzeros(final int beginColumn, final int endColumn) {
        final Spliterator<Entry> spliterator = Spliterators.<Entry>spliterator((Iterator<? extends Entry>)this.iterator(beginColumn, endColumn), (long)(this.colIndex[endColumn] - this.colIndex[beginColumn]), 1104);
        return StreamSupport.<Entry>stream(spliterator, false);
    }
    
    @Override
    public Iterator<Entry> iterator() {
        return this.iterator(0, this.n);
    }
    
    public Iterator<Entry> iterator(final int beginColumn, final int endColumn) {
        if (beginColumn < 0 || beginColumn >= this.n) {
            throw new IllegalArgumentException("Invalid begin column: " + beginColumn);
        }
        if (endColumn <= beginColumn || endColumn > this.n) {
            throw new IllegalArgumentException("Invalid end column: " + endColumn);
        }
        return new Iterator<Entry>(beginColumn) {
            int k = SparseMatrixModified.this.colIndex[j];
            int j = j;
            
            @Override
            public boolean hasNext() {
                return this.k < SparseMatrixModified.this.colIndex[endColumn];
            }
            
            @Override
            public Entry next() {
                final int i = SparseMatrixModified.this.rowIndex[this.k];
                while (this.k >= SparseMatrixModified.this.colIndex[this.j + 1]) {
                    ++this.j;
                }
                return new Entry(i, this.j, this.k++, (Entry)null);
            }
        };
    }
    
    public void forEachNonZero(final DoubleConsumerModified consumer) {
        for (int j = 0; j < this.n; ++j) {
            for (int k = this.colIndex[j]; k < this.colIndex[j + 1]; ++k) {
                final int i = this.rowIndex[k];
                consumer.accept(i, j, this.nonzeros[k]);
            }
        }
    }
    
    public void forEachNonZero(final int beginColumn, final int endColumn, final DoubleConsumerModified consumer) {
        if (beginColumn < 0 || beginColumn >= this.n) {
            throw new IllegalArgumentException("Invalid begin column: " + beginColumn);
        }
        if (endColumn <= beginColumn || endColumn > this.n) {
            throw new IllegalArgumentException("Invalid end column: " + endColumn);
        }
        for (int j = beginColumn; j < endColumn; ++j) {
            for (int k = this.colIndex[j]; k < this.colIndex[j + 1]; ++k) {
                final int i = this.rowIndex[k];
                consumer.accept(i, j, this.nonzeros[k]);
            }
        }
    }
    
    public double get(final int index) {
        return this.nonzeros[index];
    }
    
    public void set(final int index, final double value) {
        this.nonzeros[index] = value;
    }
    
    @Override
    public double get(final int i, final int j) {
        if (i < 0 || i >= this.m || j < 0 || j >= this.n) {
            throw new IllegalArgumentException("Invalid index: row = " + i + " col = " + j);
        }
        for (int k = this.colIndex[j]; k < this.colIndex[j + 1]; ++k) {
            if (this.rowIndex[k] == i) {
                return this.nonzeros[k];
            }
        }
        return 0.0;
    }
    
    @Override
    public void mv(final TransposeModified trans, final double alpha, final double[] x, final double beta, final double[] y) {
        final int k = (trans == TransposeModified.NO_TRANSPOSE) ? this.m : this.n;
        double[] ax = y;
        if (beta == 0.0) {
            Arrays.fill(y, 0.0);
        }
        else {
            ax = new double[k];
        }
        if (trans == TransposeModified.NO_TRANSPOSE) {
            for (int j = 0; j < this.n; ++j) {
                for (int i = this.colIndex[j]; i < this.colIndex[j + 1]; ++i) {
                    final double[] array = ax;
                    final int n = this.rowIndex[i];
                    array[n] += this.nonzeros[i] * x[j];
                }
            }
        }
        else {
            for (int l = 0; l < this.n; ++l) {
                for (int m = this.colIndex[l]; m < this.colIndex[l + 1]; ++m) {
                    final double[] array2 = ax;
                    final int n2 = l;
                    array2[n2] += this.nonzeros[m] * x[this.rowIndex[m]];
                }
            }
        }
        if (beta != 0.0 || alpha != 1.0) {
            for (int l = 0; l < k; ++l) {
                y[l] = alpha * ax[l] + beta * y[l];
            }
        }
    }
    
    @Override
    public void mv(final double[] work, final int inputOffset, final int outputOffset) {
        Arrays.fill(work, outputOffset, outputOffset + this.m, 0.0);
        for (int j = 0; j < this.n; ++j) {
            for (int i = this.colIndex[j]; i < this.colIndex[j + 1]; ++i) {
                final int n = outputOffset + this.rowIndex[i];
                work[n] += this.nonzeros[i] * work[inputOffset + j];
            }
        }
    }
    
    @Override
    public void tv(final double[] work, final int inputOffset, final int outputOffset) {
        Arrays.fill(work, outputOffset, outputOffset + this.n, 0.0);
        for (int i = 0; i < this.n; ++i) {
            for (int j = this.colIndex[i]; j < this.colIndex[i + 1]; ++j) {
                final int n = outputOffset + i;
                work[n] += this.nonzeros[j] * work[inputOffset + this.rowIndex[j]];
            }
        }
    }
    
    public SparseMatrixModified transpose() {
        final SparseMatrixModified trans = new SparseMatrixModified(this.n, this.m, this.nonzeros.length);
        final int[] count = new int[this.m];
        for (int i = 0; i < this.n; ++i) {
            for (int j = this.colIndex[i]; j < this.colIndex[i + 1]; ++j) {
                final int k = this.rowIndex[j];
                final int[] array = count;
                final int n = k;
                ++array[n];
            }
        }
        for (int l = 0; l < this.m; ++l) {
            trans.colIndex[l + 1] = trans.colIndex[l] + count[l];
        }
        Arrays.fill(count, 0);
        for (int i = 0; i < this.n; ++i) {
            for (int j = this.colIndex[i]; j < this.colIndex[i + 1]; ++j) {
                final int k = this.rowIndex[j];
                final int index = trans.colIndex[k] + count[k];
                trans.rowIndex[index] = i;
                trans.nonzeros[index] = this.nonzeros[j];
                final int[] array2 = count;
                final int n2 = k;
                ++array2[n2];
            }
        }
        return trans;
    }
    
    public SparseMatrixModified mm(final SparseMatrixModified B) {
        if (this.n != B.m) {
            throw new IllegalArgumentException(String.format("Matrix dimensions do not match for matrix multiplication: %d x %d vs %d x %d", this.nrow(), this.ncol(), B.nrow(), B.ncol()));
        }
        final int n = B.n;
        final int anz = this.colIndex[n];
        final int[] Bp = B.colIndex;
        final int[] Bi = B.rowIndex;
        final double[] Bx = B.nonzeros;
        final int bnz = Bp[n];
        final int[] w = new int[this.m];
        final double[] abj = new double[this.m];
        int nzmax = Math.max(anz + bnz, this.m);
        SparseMatrixModified C = new SparseMatrixModified(this.m, n, nzmax);
        final int[] Cp = C.colIndex;
        int[] Ci = C.rowIndex;
        double[] Cx = C.nonzeros;
        int nz = 0;
        for (int j = 0; j < n; ++j) {
            if (nz + this.m > nzmax) {
                nzmax = 2 * nzmax + this.m;
                final double[] Cx2 = new double[nzmax];
                final int[] Ci2 = new int[nzmax];
                System.arraycopy(Ci, 0, Ci2, 0, nz);
                System.arraycopy(Cx, 0, Cx2, 0, nz);
                Ci = Ci2;
                Cx = Cx2;
                C = new SparseMatrixModified(this.m, n, Cx2, Ci2, Cp);
            }
            Cp[j] = nz;
            for (int p = Bp[j]; p < Bp[j + 1]; ++p) {
                nz = scatter(this, Bi[p], Bx[p], w, abj, j + 1, C, nz);
            }
            for (int p = Cp[j]; p < nz; ++p) {
                Cx[p] = abj[Ci[p]];
            }
        }
        Cp[n] = nz;
        return C;
    }
    
    private static int scatter(final SparseMatrixModified A, final int j, final double beta, final int[] w, final double[] x, final int mark, final SparseMatrixModified C, int nz) {
        final int[] Ap = A.colIndex;
        final int[] Ai = A.rowIndex;
        final double[] Ax = A.nonzeros;
        final int[] Ci = C.rowIndex;
        for (int p = Ap[j]; p < Ap[j + 1]; ++p) {
            final int i = Ai[p];
            if (w[i] < mark) {
                w[i] = mark;
                x[Ci[nz++] = i] = beta * Ax[p];
            }
            else {
                final int n = i;
                x[n] += beta * Ax[p];
            }
        }
        return nz;
    }
    
    public SparseMatrixModified ata() {
        final SparseMatrixModified AT = this.transpose();
        return AT.aat(this);
    }
    
    public SparseMatrixModified aat() {
        final SparseMatrixModified AT = this.transpose();
        return this.aat(AT);
    }
    
    private SparseMatrixModified aat(final SparseMatrixModified AT) {
        final int[] done = new int[this.m];
        for (int i = 0; i < this.m; ++i) {
            done[i] = -1;
        }
        int nvals = 0;
        for (int j = 0; j < this.m; ++j) {
            for (int k = AT.colIndex[j]; k < AT.colIndex[j + 1]; ++k) {
                for (int l = AT.rowIndex[k], m = this.colIndex[l]; m < this.colIndex[l + 1]; ++m) {
                    final int h = this.rowIndex[m];
                    if (done[h] != j) {
                        done[h] = j;
                        ++nvals;
                    }
                }
            }
        }
        final SparseMatrixModified aat = new SparseMatrixModified(this.m, this.m, nvals);
        nvals = 0;
        for (int k = 0; k < this.m; ++k) {
            done[k] = -1;
        }
        for (int j2 = 0; j2 < this.m; ++j2) {
            aat.colIndex[j2] = nvals;
            for (int i2 = AT.colIndex[j2]; i2 < AT.colIndex[j2 + 1]; ++i2) {
                for (int k2 = AT.rowIndex[i2], l2 = this.colIndex[k2]; l2 < this.colIndex[k2 + 1]; ++l2) {
                    final int h2 = this.rowIndex[l2];
                    if (done[h2] != j2) {
                        done[h2] = j2;
                        aat.rowIndex[nvals] = h2;
                        ++nvals;
                    }
                }
            }
        }
        aat.colIndex[this.m] = nvals;
        for (int j2 = 0; j2 < this.m; ++j2) {
            if (aat.colIndex[j2 + 1] - aat.colIndex[j2] > 1) {
                Arrays.sort(aat.rowIndex, aat.colIndex[j2], aat.colIndex[j2 + 1]);
            }
        }
        final double[] temp = new double[this.m];
        for (int i2 = 0; i2 < this.m; ++i2) {
            for (int j3 = AT.colIndex[i2]; j3 < AT.colIndex[i2 + 1]; ++j3) {
                for (int k3 = AT.rowIndex[j3], l3 = this.colIndex[k3]; l3 < this.colIndex[k3 + 1]; ++l3) {
                    final int h3 = this.rowIndex[l3];
                    final double[] array = temp;
                    final int n = h3;
                    array[n] += AT.nonzeros[j3] * this.nonzeros[l3];
                }
            }
            for (int j3 = aat.colIndex[i2]; j3 < aat.colIndex[i2 + 1]; ++j3) {
                final int k3 = aat.rowIndex[j3];
                aat.nonzeros[j3] = temp[k3];
                temp[k3] = 0.0;
            }
        }
        return aat;
    }
    
    @Override
    public double[] diag() {
        final int n = Math.min(this.nrow(), this.ncol());
        final double[] d = new double[n];
        for (int i = 0; i < n; ++i) {
            for (int j = this.colIndex[i]; j < this.colIndex[i + 1]; ++j) {
                if (this.rowIndex[j] == i) {
                    d[i] = this.nonzeros[j];
                    break;
                }
            }
        }
        return d;
    }
    
    public static SparseMatrixModified harwell(final Path path) throws IOException {
        SparseMatrixModified.logger.info("Reads sparse matrix file '{}'", (Object)path.toAbsolutePath());
        Throwable t = null;
        try {
            final InputStream stream = Files.newInputStream(path, new OpenOption[0]);
            try {
                final Scanner scanner = new Scanner(stream);
                try {
                    String line = scanner.nextLine();
                    SparseMatrixModified.logger.info(line);
                    line = scanner.nextLine().trim();
                    SparseMatrixModified.logger.info(line);
                    String[] tokens = line.split("\\s+");
                    final int RHSCRD = Integer.parseInt(tokens[4]);
                    line = scanner.nextLine().trim();
                    SparseMatrixModified.logger.info(line);
                    if (!line.startsWith("R")) {
                        throw new UnsupportedOperationException("SparseMatrix supports only real-valued matrix.");
                    }
                    tokens = line.split("\\s+");
                    final int nrow = Integer.parseInt(tokens[1]);
                    final int ncol = Integer.parseInt(tokens[2]);
                    final int nz = Integer.parseInt(tokens[3]);
                    line = scanner.nextLine();
                    SparseMatrixModified.logger.info(line);
                    if (RHSCRD > 0) {
                        line = scanner.nextLine();
                        SparseMatrixModified.logger.info(line);
                    }
                    final int[] colIndex = new int[ncol + 1];
                    final int[] rowIndex = new int[nz];
                    final double[] data = new double[nz];
                    for (int i = 0; i <= ncol; ++i) {
                        colIndex[i] = scanner.nextInt() - 1;
                    }
                    for (int i = 0; i < nz; ++i) {
                        rowIndex[i] = scanner.nextInt() - 1;
                    }
                    for (int i = 0; i < nz; ++i) {
                        data[i] = scanner.nextDouble();
                    }
                    final SparseMatrixModified sparseMatrixModified = new SparseMatrixModified(nrow, ncol, data, rowIndex, colIndex);
                    if (scanner != null) {
                        scanner.close();
                    }
                    if (stream != null) {
                        stream.close();
                    }
                    return sparseMatrixModified;
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
                if (stream != null) {
                    stream.close();
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
    
    public static SparseMatrixModified rutherford(final Path path) throws IOException {
        return harwell(path);
    }
    
    public static SparseMatrixModified text(final Path path) throws IOException {
        Throwable t = null;
        try {
            final InputStream stream = Files.newInputStream(path, new OpenOption[0]);
            try {
                final Scanner scanner = new Scanner(stream);
                try {
                    final int nrow = scanner.nextInt();
                    final int ncol = scanner.nextInt();
                    final int nz = scanner.nextInt();
                    final int[] colIndex = new int[ncol + 1];
                    final int[] rowIndex = new int[nz];
                    final double[] data = new double[nz];
                    for (int i = 0; i <= ncol; ++i) {
                        colIndex[i] = scanner.nextInt() - 1;
                    }
                    for (int i = 0; i < nz; ++i) {
                        rowIndex[i] = scanner.nextInt() - 1;
                    }
                    for (int i = 0; i < nz; ++i) {
                        data[i] = scanner.nextDouble();
                    }
                    final SparseMatrixModified sparseMatrixModified = new SparseMatrixModified(nrow, ncol, data, rowIndex, colIndex);
                    if (scanner != null) {
                        scanner.close();
                    }
                    if (stream != null) {
                        stream.close();
                    }
                    return sparseMatrixModified;
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
                if (stream != null) {
                    stream.close();
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
    
    public class Entry
    {
        public final int i;
        public final int j;
        public final double x;
        public final int index;
        
        private Entry(final int i, final int j, final int index) {
            this.i = i;
            this.j = j;
            this.x = SparseMatrixModified.this.nonzeros[index];
            this.index = index;
        }
        
        public void update(final double value) {
            SparseMatrixModified.this.nonzeros[this.index] = value;
        }
        
        @Override
        public String toString() {
            return String.format("(%d, %d):%s", this.i, this.j, StringsModified.format(this.x));
        }
    }
}
