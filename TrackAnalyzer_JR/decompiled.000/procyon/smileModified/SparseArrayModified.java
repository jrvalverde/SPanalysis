// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.util.stream.IntStream;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Iterator;
import java.util.List;
import java.io.Serializable;

public class SparseArrayModified implements Iterable<Entry>, Serializable
{
    private static final long serialVersionUID = 2L;
    private final IntArrayListModified index;
    private final DoubleArrayListModified value;
    
    public SparseArrayModified() {
        this(10);
    }
    
    public SparseArrayModified(final int initialCapacity) {
        this.index = new IntArrayListModified(initialCapacity);
        this.value = new DoubleArrayListModified(initialCapacity);
    }
    
    public SparseArrayModified(final List<Entry> entries) {
        this.index = new IntArrayListModified(entries.size());
        this.value = new DoubleArrayListModified(entries.size());
        for (final Entry e : entries) {
            this.index.add(e.i);
            this.value.add(e.x);
        }
    }
    
    public SparseArrayModified(final Stream<Entry> stream) {
        this((List<Entry>)stream.<List<? super Entry>, ?>collect((Collector<? super Entry, ?, List<? super Entry>>)Collectors.<Entry>toList()));
    }
    
    @Override
    public String toString() {
        return this.stream().<Object>map((Function<? super Entry, ?>)Entry::toString).<String, ?>collect((Collector<? super Object, ?, String>)Collectors.joining(", ", "[", "]"));
    }
    
    public int size() {
        return this.index.size();
    }
    
    public boolean isEmpty() {
        return this.index.isEmpty();
    }
    
    @Override
    public Iterator<Entry> iterator() {
        return new Iterator<Entry>() {
            int i = 0;
            
            @Override
            public boolean hasNext() {
                return this.i < SparseArrayModified.this.size();
            }
            
            @Override
            public Entry next() {
                return new Entry(this.i++, (Entry)null);
            }
        };
    }
    
    public Stream<Entry> stream() {
        return IntStream.range(0, this.size()).<Entry>mapToObj(n -> new Entry(n, (Entry)null));
    }
    
    public void sort() {
        QuickSortModified.sort(this.index.data, this.value.data, this.size());
    }
    
    public double get(final int i) {
        for (int length = this.size(), k = 0; k < length; ++k) {
            if (this.index.get(k) == i) {
                return this.value.get(k);
            }
        }
        return 0.0;
    }
    
    public boolean set(final int i, final double x) {
        if (x == 0.0) {
            this.remove(i);
            return false;
        }
        for (int length = this.size(), k = 0; k < length; ++k) {
            if (this.index.get(k) == i) {
                this.value.set(k, x);
                return false;
            }
        }
        this.index.add(i);
        this.value.add(x);
        return true;
    }
    
    public void append(final int i, final double x) {
        if (x != 0.0) {
            this.index.add(i);
            this.value.add(x);
        }
    }
    
    public void remove(final int i) {
        for (int length = this.size(), k = 0; k < length; ++k) {
            if (this.index.get(k) == i) {
                this.index.remove(k);
                this.value.remove(k);
                return;
            }
        }
    }
    
    public class Entry
    {
        public final int i;
        public final double x;
        private final int index;
        
        private Entry(final int index) {
            this.i = SparseArrayModified.this.index.get(index);
            this.x = SparseArrayModified.this.value.get(index);
            this.index = index;
        }
        
        public void update(final double x) {
            SparseArrayModified.this.value.set(this.index, x);
        }
        
        @Override
        public String toString() {
            return String.format("%d:%s", this.i, StringsModified.format(this.x));
        }
    }
}
