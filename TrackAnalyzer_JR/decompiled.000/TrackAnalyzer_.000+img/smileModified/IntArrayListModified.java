package smileModified;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class IntArrayListModified implements Serializable {
   private static final long serialVersionUID = 1L;
   int[] data;
   private int size;

   public IntArrayListModified() {
      this(10);
   }

   public IntArrayListModified(int capacity) {
      this.data = new int[capacity];
      this.size = 0;
   }

   public IntArrayListModified(int[] values) {
      this(Math.max(values.length, 10));
      this.add(values);
   }

   public String toString() {
      return (String)Arrays.stream(this.data).limit((long)this.size).mapToObj(String::valueOf).collect(Collectors.joining(", ", "[", "]"));
   }

   public IntStream stream() {
      return IntStream.of(this.data).limit((long)this.size);
   }

   public void ensureCapacity(int capacity) {
      if (capacity > this.data.length) {
         int newCap = Math.max(this.data.length << 1, capacity);
         int[] tmp = new int[newCap];
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
      if (this.data.length > this.size) {
         this.data = this.toArray();
      }

   }

   public void add(int val) {
      this.ensureCapacity(this.size + 1);
      this.data[this.size++] = val;
   }

   public void add(IntArrayListModified vals) {
      this.ensureCapacity(this.size + vals.size);
      System.arraycopy(vals.data, 0, this.data, this.size, vals.size);
      this.size += vals.size;
   }

   public void add(int[] vals) {
      this.ensureCapacity(this.size + vals.length);
      System.arraycopy(vals, 0, this.data, this.size, vals.length);
      this.size += vals.length;
   }

   public int get(int index) {
      return this.data[index];
   }

   public void set(int index, int val) {
      if (index >= 0 && index < this.size) {
         this.data[index] = val;
      } else {
         throw new IndexOutOfBoundsException(String.valueOf(index));
      }
   }

   public void clear() {
      this.size = 0;
   }

   public int remove(int index) {
      if (index >= 0 && index < this.size) {
         int old = this.get(index);
         if (index == 0) {
            System.arraycopy(this.data, 1, this.data, 0, this.size - 1);
         } else if (index != this.size - 1) {
            System.arraycopy(this.data, index + 1, this.data, index, this.size - (index + 1));
         }

         --this.size;
         return old;
      } else {
         throw new IndexOutOfBoundsException(String.valueOf(index));
      }
   }

   public int[] toArray() {
      return this.toArray((int[])null);
   }

   public int[] toArray(int[] dest) {
      if (dest == null || dest.length < this.size()) {
         dest = new int[this.size];
      }

      System.arraycopy(this.data, 0, dest, 0, this.size);
      return dest;
   }
}
