package smileModified;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public final class DoubleArrayListModified implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final DecimalFormat format = new DecimalFormat("#.######");
   double[] data;
   private int size;

   public DoubleArrayListModified() {
      this(10);
   }

   public DoubleArrayListModified(int capacity) {
      this.data = new double[capacity];
      this.size = 0;
   }

   public DoubleArrayListModified(double[] values) {
      this(Math.max(values.length, 10));
      this.add(values);
   }

   public String toString() {
      DoubleStream var10000 = Arrays.stream(this.data).limit((long)this.size);
      DecimalFormat var10001 = format;
      var10001.getClass();
      return (String)var10000.mapToObj(var10001::format).collect(Collectors.joining(", ", "[", "]"));
   }

   public DoubleStream stream() {
      return DoubleStream.of(this.data).limit((long)this.size);
   }

   public void ensureCapacity(int capacity) {
      if (capacity > this.data.length) {
         int newCap = Math.max(this.data.length << 1, capacity);
         double[] tmp = new double[newCap];
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

   public void add(double val) {
      this.ensureCapacity(this.size + 1);
      this.data[this.size++] = val;
   }

   public void add(double[] vals) {
      this.ensureCapacity(this.size + vals.length);
      System.arraycopy(vals, 0, this.data, this.size, vals.length);
      this.size += vals.length;
   }

   public double get(int index) {
      return this.data[index];
   }

   public void set(int index, double val) {
      if (index >= 0 && index < this.size) {
         this.data[index] = val;
      } else {
         throw new IndexOutOfBoundsException(String.valueOf(index));
      }
   }

   public void clear() {
      this.size = 0;
   }

   public double remove(int index) {
      if (index >= 0 && index < this.size) {
         double old = this.get(index);
         if (index == 0) {
            System.arraycopy(this.data, 1, this.data, 0, this.size - 1);
         } else if (this.size - 1 != index) {
            System.arraycopy(this.data, index + 1, this.data, index, this.size - (index + 1));
         }

         --this.size;
         return old;
      } else {
         throw new IndexOutOfBoundsException(String.valueOf(index));
      }
   }

   public double[] toArray() {
      return this.toArray((double[])null);
   }

   public double[] toArray(double[] dest) {
      if (dest == null || dest.length < this.size()) {
         dest = new double[this.size];
      }

      System.arraycopy(this.data, 0, dest, 0, this.size);
      return dest;
   }
}
