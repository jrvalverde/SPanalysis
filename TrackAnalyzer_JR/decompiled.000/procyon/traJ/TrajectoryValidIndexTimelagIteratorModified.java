// 
// Decompiled by Procyon v0.5.36
// 

package traJ;

import java.util.Iterator;

public class TrajectoryValidIndexTimelagIteratorModified implements Iterator<Integer>
{
    private TrajectoryModified t;
    private int timelag;
    boolean overlap;
    int currentIndex;
    
    public TrajectoryValidIndexTimelagIteratorModified(final TrajectoryModified t, final int timelag) {
        this.overlap = true;
        this.t = t;
        this.timelag = timelag;
        this.overlap = true;
        this.currentIndex = 0;
    }
    
    public TrajectoryValidIndexTimelagIteratorModified(final TrajectoryModified t, final int timelag, final boolean overlap) {
        this.overlap = true;
        this.t = t;
        this.timelag = timelag;
        this.overlap = overlap;
        this.currentIndex = 0;
    }
    
    @Override
    public boolean hasNext() {
        for (int i = this.currentIndex; i < this.t.size(); ++i) {
            if (i + this.timelag >= this.t.size()) {
                return false;
            }
            if (this.t.get(i) != null && this.t.get(i + this.timelag) != null) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Integer next() {
        for (int i = this.currentIndex; i < this.t.size(); ++i) {
            if (i + this.timelag >= this.t.size()) {
                return null;
            }
            if (this.t.get(i) != null && this.t.get(i + this.timelag) != null) {
                if (this.overlap) {
                    this.currentIndex = i + 1;
                }
                else {
                    this.currentIndex = i + this.timelag;
                }
                return i;
            }
        }
        return null;
    }
    
    @Override
    public void remove() {
    }
}
