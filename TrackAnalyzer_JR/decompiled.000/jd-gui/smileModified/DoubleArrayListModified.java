/*     */ package smileModified;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.DoubleStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DoubleArrayListModified
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  35 */   private static final DecimalFormat format = new DecimalFormat("#.######");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double[] data;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int size;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayListModified() {
/*  51 */     this(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayListModified(int capacity) {
/*  60 */     this.data = new double[capacity];
/*  61 */     this.size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleArrayListModified(double[] values) {
/*  70 */     this(Math.max(values.length, 10));
/*  71 */     add(values);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  76 */     return Arrays.stream(this.data).limit(this.size).<CharSequence>mapToObj(format::format).collect(Collectors.joining(", ", "[", "]"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoubleStream stream() {
/*  84 */     return DoubleStream.of(this.data).limit(this.size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ensureCapacity(int capacity) {
/*  95 */     if (capacity > this.data.length) {
/*  96 */       int newCap = Math.max(this.data.length << 1, capacity);
/*  97 */       double[] tmp = new double[newCap];
/*  98 */       System.arraycopy(this.data, 0, tmp, 0, this.data.length);
/*  99 */       this.data = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 109 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 118 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 125 */     if (this.data.length > size()) {
/* 126 */       this.data = toArray();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(double val) {
/* 136 */     ensureCapacity(this.size + 1);
/* 137 */     this.data[this.size++] = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(double[] vals) {
/* 146 */     ensureCapacity(this.size + vals.length);
/* 147 */     System.arraycopy(vals, 0, this.data, this.size, vals.length);
/* 148 */     this.size += vals.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int index) {
/* 158 */     return this.data[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int index, double val) {
/* 170 */     if (index < 0 || index >= this.size) {
/* 171 */       throw new IndexOutOfBoundsException(String.valueOf(index));
/*     */     }
/* 173 */     this.data[index] = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 181 */     this.size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double remove(int index) {
/* 192 */     if (index < 0 || index >= this.size) {
/* 193 */       throw new IndexOutOfBoundsException(String.valueOf(index));
/*     */     }
/*     */     
/* 196 */     double old = get(index);
/*     */     
/* 198 */     if (index == 0) {
/*     */       
/* 200 */       System.arraycopy(this.data, 1, this.data, 0, this.size - 1);
/* 201 */     } else if (this.size - 1 != index) {
/*     */       
/* 203 */       System.arraycopy(this.data, index + 1, this.data, index, this.size - index + 1);
/*     */     } 
/*     */     
/* 206 */     this.size--;
/* 207 */     return old;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] toArray() {
/* 217 */     return toArray(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] toArray(double[] dest) {
/* 232 */     if (dest == null || dest.length < size()) {
/* 233 */       dest = new double[this.size];
/*     */     }
/*     */     
/* 236 */     System.arraycopy(this.data, 0, dest, 0, this.size);
/* 237 */     return dest;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/DoubleArrayListModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */