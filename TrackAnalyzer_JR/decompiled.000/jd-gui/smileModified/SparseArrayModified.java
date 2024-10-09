/*     */ package smileModified;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.Stream;
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
/*     */ public class SparseArrayModified
/*     */   implements Iterable<SparseArrayModified.Entry>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*     */   private final IntArrayListModified index;
/*     */   private final DoubleArrayListModified value;
/*     */   
/*     */   public class Entry
/*     */   {
/*     */     public final int i;
/*     */     public final double x;
/*     */     private final int index;
/*     */     
/*     */     private Entry(int index) {
/*  63 */       this.i = SparseArrayModified.this.index.get(index);
/*  64 */       this.x = SparseArrayModified.this.value.get(index);
/*  65 */       this.index = index;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void update(double x) {
/*  74 */       SparseArrayModified.this.value.set(this.index, x);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  79 */       return String.format("%d:%s", new Object[] { Integer.valueOf(this.i), StringsModified.format(this.x) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseArrayModified() {
/*  87 */     this(10);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseArrayModified(int initialCapacity) {
/*  95 */     this.index = new IntArrayListModified(initialCapacity);
/*  96 */     this.value = new DoubleArrayListModified(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseArrayModified(List<Entry> entries) {
/* 104 */     this.index = new IntArrayListModified(entries.size());
/* 105 */     this.value = new DoubleArrayListModified(entries.size());
/*     */     
/* 107 */     for (Entry e : entries) {
/* 108 */       this.index.add(e.i);
/* 109 */       this.value.add(e.x);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SparseArrayModified(Stream<Entry> stream) {
/* 118 */     this(stream.collect((Collector)Collectors.toList()));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 123 */     return stream().<CharSequence>map(Entry::toString).collect(Collectors.joining(", ", "[", "]"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 131 */     return this.index.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 139 */     return this.index.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Entry> iterator() {
/* 144 */     return new Iterator<Entry>() {
/* 145 */         int i = 0;
/*     */         
/*     */         public boolean hasNext() {
/* 148 */           return (this.i < SparseArrayModified.this.size());
/*     */         }
/*     */ 
/*     */         
/*     */         public SparseArrayModified.Entry next() {
/* 153 */           return new SparseArrayModified.Entry(this.i++, null);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stream<Entry> stream() {
/* 163 */     return IntStream.range(0, size()).mapToObj(paramInt -> new Entry(paramInt, null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sort() {
/* 170 */     QuickSortModified.sort(this.index.data, this.value.data, size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double get(int i) {
/* 179 */     int length = size();
/* 180 */     for (int k = 0; k < length; k++) {
/* 181 */       if (this.index.get(k) == i) return this.value.get(k); 
/*     */     } 
/* 183 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean set(int i, double x) {
/* 193 */     if (x == 0.0D) {
/* 194 */       remove(i);
/* 195 */       return false;
/*     */     } 
/*     */     
/* 198 */     int length = size();
/* 199 */     for (int k = 0; k < length; k++) {
/* 200 */       if (this.index.get(k) == i) {
/* 201 */         this.value.set(k, x);
/* 202 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 206 */     this.index.add(i);
/* 207 */     this.value.add(x);
/* 208 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(int i, double x) {
/* 218 */     if (x != 0.0D) {
/* 219 */       this.index.add(i);
/* 220 */       this.value.add(x);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(int i) {
/* 229 */     int length = size();
/* 230 */     for (int k = 0; k < length; k++) {
/* 231 */       if (this.index.get(k) == i) {
/* 232 */         this.index.remove(k);
/* 233 */         this.value.remove(k);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/SparseArrayModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */