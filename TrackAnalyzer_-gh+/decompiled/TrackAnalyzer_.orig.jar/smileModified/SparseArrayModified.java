package smileModified;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SparseArrayModified implements Iterable<SparseArrayModified.Entry>, Serializable {
   private static final long serialVersionUID = 2L;
   private final IntArrayListModified index;
   private final DoubleArrayListModified value;

   public SparseArrayModified() {
      this(10);
   }

   public SparseArrayModified(int initialCapacity) {
      this.index = new IntArrayListModified(initialCapacity);
      this.value = new DoubleArrayListModified(initialCapacity);
   }

   public SparseArrayModified(List<SparseArrayModified.Entry> entries) {
      this.index = new IntArrayListModified(entries.size());
      this.value = new DoubleArrayListModified(entries.size());
      Iterator var3 = entries.iterator();

      while(var3.hasNext()) {
         SparseArrayModified.Entry e = (SparseArrayModified.Entry)var3.next();
         this.index.add(e.i);
         this.value.add(e.x);
      }

   }

   public SparseArrayModified(Stream<SparseArrayModified.Entry> stream) {
      this((List)stream.collect(Collectors.toList()));
   }

   public String toString() {
      return (String)this.stream().map(SparseArrayModified.Entry::toString).collect(Collectors.joining(", ", "[", "]"));
   }

   public int size() {
      return this.index.size();
   }

   public boolean isEmpty() {
      return this.index.isEmpty();
   }

   public Iterator<SparseArrayModified.Entry> iterator() {
      return new Iterator<SparseArrayModified.Entry>() {
         int i = 0;

         public boolean hasNext() {
            return this.i < SparseArrayModified.this.size();
         }

         public SparseArrayModified.Entry next() {
            return SparseArrayModified.this.new Entry(this.i++, (SparseArrayModified.Entry)null);
         }
      };
   }

   public Stream<SparseArrayModified.Entry> stream() {
      return IntStream.range(0, this.size()).mapToObj((var1) -> {
         return this.new Entry(var1, (SparseArrayModified.Entry)null);
      });
   }

   public void sort() {
      QuickSortModified.sort(this.index.data, this.value.data, this.size());
   }

   public double get(int i) {
      int length = this.size();

      for(int k = 0; k < length; ++k) {
         if (this.index.get(k) == i) {
            return this.value.get(k);
         }
      }

      return 0.0D;
   }

   public boolean set(int i, double x) {
      if (x == 0.0D) {
         this.remove(i);
         return false;
      } else {
         int length = this.size();

         for(int k = 0; k < length; ++k) {
            if (this.index.get(k) == i) {
               this.value.set(k, x);
               return false;
            }
         }

         this.index.add(i);
         this.value.add(x);
         return true;
      }
   }

   public void append(int i, double x) {
      if (x != 0.0D) {
         this.index.add(i);
         this.value.add(x);
      }

   }

   public void remove(int i) {
      int length = this.size();

      for(int k = 0; k < length; ++k) {
         if (this.index.get(k) == i) {
            this.index.remove(k);
            this.value.remove(k);
            return;
         }
      }

   }

   public class Entry {
      public final int i;
      public final double x;
      private final int index;

      private Entry(int index) {
         this.i = SparseArrayModified.this.index.get(index);
         this.x = SparseArrayModified.this.value.get(index);
         this.index = index;
      }

      public void update(double x) {
         SparseArrayModified.this.value.set(this.index, x);
      }

      public String toString() {
         return String.format("%d:%s", this.i, StringsModified.format(this.x));
      }

      // $FF: synthetic method
      Entry(int var2, SparseArrayModified.Entry var3) {
         this(var2);
      }
   }
}
