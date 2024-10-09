package traJ;

import java.util.Iterator;

public class TrajectoryValidIndexTimelagIteratorModified implements Iterator<Integer> {
   private TrajectoryModified t;
   private int timelag;
   boolean overlap = true;
   int currentIndex;

   public TrajectoryValidIndexTimelagIteratorModified(TrajectoryModified t, int timelag) {
      this.t = t;
      this.timelag = timelag;
      this.overlap = true;
      this.currentIndex = 0;
   }

   public TrajectoryValidIndexTimelagIteratorModified(TrajectoryModified t, int timelag, boolean overlap) {
      this.t = t;
      this.timelag = timelag;
      this.overlap = overlap;
      this.currentIndex = 0;
   }

   public boolean hasNext() {
      for(int i = this.currentIndex; i < this.t.size(); ++i) {
         if (i + this.timelag >= this.t.size()) {
            return false;
         }

         if (this.t.get(i) != null && this.t.get(i + this.timelag) != null) {
            return true;
         }
      }

      return false;
   }

   public Integer next() {
      for(int i = this.currentIndex; i < this.t.size(); ++i) {
         if (i + this.timelag >= this.t.size()) {
            return null;
         }

         if (this.t.get(i) != null && this.t.get(i + this.timelag) != null) {
            if (this.overlap) {
               this.currentIndex = i + 1;
            } else {
               this.currentIndex = i + this.timelag;
            }

            return i;
         }
      }

      return null;
   }

   public void remove() {
   }
}
