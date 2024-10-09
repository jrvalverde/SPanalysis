package smileModified;

public class IntPair {
   public final int i;
   public final int j;

   public IntPair(int i, int j) {
      this.i = i;
      this.j = j;
   }

   public int hashCode() {
      return this.i * 31 + this.j;
   }

   public String toString() {
      return String.format("(%d, %d)", this.i, this.j);
   }

   public boolean equals(Object o) {
      if (o instanceof IntPair) {
         IntPair p = (IntPair)o;
         return this.i == p.i && this.j == p.j;
      } else {
         return false;
      }
   }
}
