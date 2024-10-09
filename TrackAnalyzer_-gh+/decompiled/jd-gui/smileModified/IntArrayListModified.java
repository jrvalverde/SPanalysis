/*     */ package smileModified;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.IntStream;
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
/*     */ public final class IntArrayListModified
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   int[] data;
/*     */   private int size;
/*     */   
/*     */   public IntArrayListModified() {
/*  32 */     this(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayListModified(int capacity) {
/*  41 */     this.data = new int[capacity];
/*  42 */     this.size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntArrayListModified(int[] values) {
/*  51 */     this(Math.max(values.length, 10));
/*  52 */     add(values);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  57 */     return Arrays.stream(this.data).limit(this.size).<CharSequence>mapToObj(String::valueOf).collect(Collectors.joining(", ", "[", "]"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntStream stream() {
/*  65 */     return IntStream.of(this.data).limit(this.size);
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
/*  76 */     if (capacity > this.data.length) {
/*  77 */       int newCap = Math.max(this.data.length << 1, capacity);
/*  78 */       int[] tmp = new int[newCap];
/*  79 */       System.arraycopy(this.data, 0, tmp, 0, this.data.length);
/*  80 */       this.data = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  90 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  99 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trim() {
/* 106 */     if (this.data.length > this.size) {
/* 107 */       this.data = toArray();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int val) {
/* 117 */     ensureCapacity(this.size + 1);
/* 118 */     this.data[this.size++] = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IntArrayListModified vals) {
/* 127 */     ensureCapacity(this.size + vals.size);
/* 128 */     System.arraycopy(vals.data, 0, this.data, this.size, vals.size);
/* 129 */     this.size += vals.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(int[] vals) {
/* 138 */     ensureCapacity(this.size + vals.length);
/* 139 */     System.arraycopy(vals, 0, this.data, this.size, vals.length);
/* 140 */     this.size += vals.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int get(int index) {
/* 150 */     return this.data[index];
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
/*     */   public void set(int index, int val) {
/* 162 */     if (index < 0 || index >= this.size) {
/* 163 */       throw new IndexOutOfBoundsException(String.valueOf(index));
/*     */     }
/* 165 */     this.data[index] = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 173 */     this.size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int remove(int index) {
/* 184 */     if (index < 0 || index >= this.size) {
/* 185 */       throw new IndexOutOfBoundsException(String.valueOf(index));
/*     */     }
/*     */     
/* 188 */     int old = get(index);
/*     */     
/* 190 */     if (index == 0) {
/*     */       
/* 192 */       System.arraycopy(this.data, 1, this.data, 0, this.size - 1);
/* 193 */     } else if (index != this.size - 1) {
/*     */       
/* 195 */       System.arraycopy(this.data, index + 1, this.data, index, this.size - index + 1);
/*     */     } 
/*     */     
/* 198 */     this.size--;
/* 199 */     return old;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] toArray() {
/* 209 */     return toArray(null);
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
/*     */   public int[] toArray(int[] dest) {
/* 224 */     if (dest == null || dest.length < size()) {
/* 225 */       dest = new int[this.size];
/*     */     }
/*     */     
/* 228 */     System.arraycopy(this.data, 0, dest, 0, this.size);
/* 229 */     return dest;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/IntArrayListModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */