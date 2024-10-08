// 
// Decompiled by Procyon v0.5.36
// 

package vecmath;

import java.io.Serializable;

public class Tuple3dModified implements Serializable, Cloneable
{
    static final long serialVersionUID = 5542096614926168415L;
    public double x;
    public double y;
    public double z;
    
    public Tuple3dModified(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Tuple3dModified(final double[] t) {
        this.x = t[0];
        this.y = t[1];
        this.z = t[2];
    }
    
    public Tuple3dModified(final Tuple3dModified t1) {
        this.x = t1.x;
        this.y = t1.y;
        this.z = t1.z;
    }
    
    public Tuple3dModified(final Tuple3f t1) {
        this.x = t1.x;
        this.y = t1.y;
        this.z = t1.z;
    }
    
    public Tuple3dModified() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
    
    public final void set(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public final void set(final double[] t) {
        this.x = t[0];
        this.y = t[1];
        this.z = t[2];
    }
    
    public final void set(final Tuple3dModified t1) {
        this.x = t1.x;
        this.y = t1.y;
        this.z = t1.z;
    }
    
    public final void set(final Tuple3f t1) {
        this.x = t1.x;
        this.y = t1.y;
        this.z = t1.z;
    }
    
    public final void get(final double[] t) {
        t[0] = this.x;
        t[1] = this.y;
        t[2] = this.z;
    }
    
    public final void get(final Tuple3dModified t) {
        t.x = this.x;
        t.y = this.y;
        t.z = this.z;
    }
    
    public final void add(final Tuple3dModified t1, final Tuple3dModified t2) {
        this.x = t1.x + t2.x;
        this.y = t1.y + t2.y;
        this.z = t1.z + t2.z;
    }
    
    public final void add(final Tuple3dModified t1) {
        this.x += t1.x;
        this.y += t1.y;
        this.z += t1.z;
    }
    
    public final void sub(final Tuple3dModified t1, final Tuple3dModified t2) {
        this.x = t1.x - t2.x;
        this.y = t1.y - t2.y;
        this.z = t1.z - t2.z;
    }
    
    public final void sub(final Tuple3dModified t1) {
        this.x -= t1.x;
        this.y -= t1.y;
        this.z -= t1.z;
    }
    
    public final void negate(final Tuple3dModified t1) {
        this.x = -t1.x;
        this.y = -t1.y;
        this.z = -t1.z;
    }
    
    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }
    
    public final void scale(final double s, final Tuple3dModified t1) {
        this.x = s * t1.x;
        this.y = s * t1.y;
        this.z = s * t1.z;
    }
    
    public final void scale(final double s) {
        this.x *= s;
        this.y *= s;
        this.z *= s;
    }
    
    public final void scaleAdd(final double s, final Tuple3dModified t1, final Tuple3dModified t2) {
        this.x = s * t1.x + t2.x;
        this.y = s * t1.y + t2.y;
        this.z = s * t1.z + t2.z;
    }
    
    @Deprecated
    public final void scaleAdd(final double s, final Tuple3f t1) {
        this.scaleAdd(s, new Point3dModified(t1));
    }
    
    public final void scaleAdd(final double s, final Tuple3dModified t1) {
        this.x = s * this.x + t1.x;
        this.y = s * this.y + t1.y;
        this.z = s * this.z + t1.z;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
    
    @Override
    public int hashCode() {
        long bits = 1L;
        bits = VecMathUtil.hashDoubleBits(bits, this.x);
        bits = VecMathUtil.hashDoubleBits(bits, this.y);
        bits = VecMathUtil.hashDoubleBits(bits, this.z);
        return VecMathUtil.hashFinish(bits);
    }
    
    public boolean equals(final Tuple3dModified t1) {
        try {
            return this.x == t1.x && this.y == t1.y && this.z == t1.z;
        }
        catch (NullPointerException e2) {
            return false;
        }
    }
    
    @Override
    public boolean equals(final Object t1) {
        try {
            final Tuple3dModified t2 = (Tuple3dModified)t1;
            return this.x == t2.x && this.y == t2.y && this.z == t2.z;
        }
        catch (ClassCastException e1) {
            return false;
        }
        catch (NullPointerException e2) {
            return false;
        }
    }
    
    public boolean epsilonEquals(final Tuple3dModified t1, final double epsilon) {
        double diff = this.x - t1.x;
        if (Double.isNaN(diff)) {
            return false;
        }
        if (((diff < 0.0) ? (-diff) : diff) > epsilon) {
            return false;
        }
        diff = this.y - t1.y;
        if (Double.isNaN(diff)) {
            return false;
        }
        if (((diff < 0.0) ? (-diff) : diff) > epsilon) {
            return false;
        }
        diff = this.z - t1.z;
        return !Double.isNaN(diff) && ((diff < 0.0) ? (-diff) : diff) <= epsilon;
    }
    
    @Deprecated
    public final void clamp(final float min, final float max, final Tuple3dModified t) {
        this.clamp(min, (double)max, t);
    }
    
    public final void clamp(final double min, final double max, final Tuple3dModified t) {
        if (t.x > max) {
            this.x = max;
        }
        else if (t.x < min) {
            this.x = min;
        }
        else {
            this.x = t.x;
        }
        if (t.y > max) {
            this.y = max;
        }
        else if (t.y < min) {
            this.y = min;
        }
        else {
            this.y = t.y;
        }
        if (t.z > max) {
            this.z = max;
        }
        else if (t.z < min) {
            this.z = min;
        }
        else {
            this.z = t.z;
        }
    }
    
    @Deprecated
    public final void clampMin(final float min, final Tuple3dModified t) {
        this.clampMin((double)min, t);
    }
    
    public final void clampMin(final double min, final Tuple3dModified t) {
        if (t.x < min) {
            this.x = min;
        }
        else {
            this.x = t.x;
        }
        if (t.y < min) {
            this.y = min;
        }
        else {
            this.y = t.y;
        }
        if (t.z < min) {
            this.z = min;
        }
        else {
            this.z = t.z;
        }
    }
    
    @Deprecated
    public final void clampMax(final float max, final Tuple3dModified t) {
        this.clampMax((double)max, t);
    }
    
    public final void clampMax(final double max, final Tuple3dModified t) {
        if (t.x > max) {
            this.x = max;
        }
        else {
            this.x = t.x;
        }
        if (t.y > max) {
            this.y = max;
        }
        else {
            this.y = t.y;
        }
        if (t.z > max) {
            this.z = max;
        }
        else {
            this.z = t.z;
        }
    }
    
    public final void absolute(final Tuple3dModified t) {
        this.x = Math.abs(t.x);
        this.y = Math.abs(t.y);
        this.z = Math.abs(t.z);
    }
    
    @Deprecated
    public final void clamp(final float min, final float max) {
        this.clamp(min, (double)max);
    }
    
    public final void clamp(final double min, final double max) {
        if (this.x > max) {
            this.x = max;
        }
        else if (this.x < min) {
            this.x = min;
        }
        if (this.y > max) {
            this.y = max;
        }
        else if (this.y < min) {
            this.y = min;
        }
        if (this.z > max) {
            this.z = max;
        }
        else if (this.z < min) {
            this.z = min;
        }
    }
    
    @Deprecated
    public final void clampMin(final float min) {
        this.clampMin((double)min);
    }
    
    public final void clampMin(final double min) {
        if (this.x < min) {
            this.x = min;
        }
        if (this.y < min) {
            this.y = min;
        }
        if (this.z < min) {
            this.z = min;
        }
    }
    
    @Deprecated
    public final void clampMax(final float max) {
        this.clampMax((double)max);
    }
    
    public final void clampMax(final double max) {
        if (this.x > max) {
            this.x = max;
        }
        if (this.y > max) {
            this.y = max;
        }
        if (this.z > max) {
            this.z = max;
        }
    }
    
    public final void absolute() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        this.z = Math.abs(this.z);
    }
    
    @Deprecated
    public final void interpolate(final Tuple3dModified t1, final Tuple3dModified t2, final float alpha) {
        this.interpolate(t1, t2, (double)alpha);
    }
    
    public final void interpolate(final Tuple3dModified t1, final Tuple3dModified t2, final double alpha) {
        this.x = (1.0 - alpha) * t1.x + alpha * t2.x;
        this.y = (1.0 - alpha) * t1.y + alpha * t2.y;
        this.z = (1.0 - alpha) * t1.z + alpha * t2.z;
    }
    
    @Deprecated
    public final void interpolate(final Tuple3dModified t1, final float alpha) {
        this.interpolate(t1, (double)alpha);
    }
    
    public final void interpolate(final Tuple3dModified t1, final double alpha) {
        this.x = (1.0 - alpha) * this.x + alpha * t1.x;
        this.y = (1.0 - alpha) * this.y + alpha * t1.y;
        this.z = (1.0 - alpha) * this.z + alpha * t1.z;
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
    
    public final double getX() {
        return this.x;
    }
    
    public final void setX(final double x) {
        this.x = x;
    }
    
    public final double getY() {
        return this.y;
    }
    
    public final void setY(final double y) {
        this.y = y;
    }
    
    public final double getZ() {
        return this.z;
    }
    
    public final void setZ(final double z) {
        this.z = z;
    }
}
