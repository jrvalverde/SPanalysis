// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.util.stream.DoubleStream;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.DoubleFunction;
import java.util.Arrays;
import java.text.DecimalFormat;
import java.io.Serializable;

public final class DoubleArrayListModified implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final DecimalFormat format;
    double[] data;
    private int size;
    
    static {
        format = new DecimalFormat("#.######");
    }
    
    public DoubleArrayListModified() {
        this(10);
    }
    
    public DoubleArrayListModified(final int capacity) {
        this.data = new double[capacity];
        this.size = 0;
    }
    
    public DoubleArrayListModified(final double[] values) {
        this(Math.max(values.length, 10));
        this.add(values);
    }
    
    @Override
    public String toString() {
        return Arrays.stream(this.data).limit(this.size).<Object>mapToObj((DoubleFunction<?>)DoubleArrayListModified.format::format).<String, ?>collect((Collector<? super Object, ?, String>)Collectors.joining(", ", "[", "]"));
    }
    
    public DoubleStream stream() {
        return DoubleStream.of(this.data).limit(this.size);
    }
    
    public void ensureCapacity(final int capacity) {
        if (capacity > this.data.length) {
            final int newCap = Math.max(this.data.length << 1, capacity);
            final double[] tmp = new double[newCap];
            System.arraycopy(this.data, 0, tmp, 0, this.data.length);
            this.data = tmp;
        }
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public void trim() {
        if (this.data.length > this.size()) {
            this.data = this.toArray();
        }
    }
    
    public void add(final double val) {
        this.ensureCapacity(this.size + 1);
        this.data[this.size++] = val;
    }
    
    public void add(final double[] vals) {
        this.ensureCapacity(this.size + vals.length);
        System.arraycopy(vals, 0, this.data, this.size, vals.length);
        this.size += vals.length;
    }
    
    public double get(final int index) {
        return this.data[index];
    }
    
    public void set(final int index, final double val) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        this.data[index] = val;
    }
    
    public void clear() {
        this.size = 0;
    }
    
    public double remove(final int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        final double old = this.get(index);
        if (index == 0) {
            System.arraycopy(this.data, 1, this.data, 0, this.size - 1);
        }
        else if (this.size - 1 != index) {
            System.arraycopy(this.data, index + 1, this.data, index, this.size - (index + 1));
        }
        --this.size;
        return old;
    }
    
    public double[] toArray() {
        return this.toArray(null);
    }
    
    public double[] toArray(double[] dest) {
        if (dest == null || dest.length < this.size()) {
            dest = new double[this.size];
        }
        System.arraycopy(this.data, 0, dest, 0, this.size);
        return dest;
    }
}
