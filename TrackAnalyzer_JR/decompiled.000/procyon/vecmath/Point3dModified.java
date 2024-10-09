// 
// Decompiled by Procyon v0.5.36
// 

package vecmath;

import java.io.Serializable;

public class Point3dModified extends Tuple3dModified implements Serializable
{
    static final long serialVersionUID = 5718062286069042927L;
    
    public Point3dModified(final double x, final double y, final double z) {
        super(x, y, z);
    }
    
    public Point3dModified(final double[] p) {
        super(p);
    }
    
    public Point3dModified(final Point3dModified p1) {
        super(p1);
    }
    
    public Point3dModified(final Point3f p1) {
        super(p1);
    }
    
    public Point3dModified(final Tuple3f t1) {
        super(t1);
    }
    
    public Point3dModified(final Tuple3dModified t1) {
        super(t1);
    }
    
    public Point3dModified() {
    }
    
    public final double distanceSquared(final Point3dModified p1) {
        final double dx = this.x - p1.x;
        final double dy = this.y - p1.y;
        final double dz = this.z - p1.z;
        return dx * dx + dy * dy + dz * dz;
    }
    
    public double distance(final Point3dModified p1) {
        final double dx = this.x - p1.x;
        final double dy = this.y - p1.y;
        final double dz = this.z - p1.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    public final double distanceL1(final Point3dModified p1) {
        return Math.abs(this.x - p1.x) + Math.abs(this.y - p1.y) + Math.abs(this.z - p1.z);
    }
    
    public final double distanceLinf(final Point3dModified p1) {
        final double tmp = Math.max(Math.abs(this.x - p1.x), Math.abs(this.y - p1.y));
        return Math.max(tmp, Math.abs(this.z - p1.z));
    }
    
    public final void project(final Point4d p1) {
        final double oneOw = 1.0 / p1.w;
        this.x = p1.x * oneOw;
        this.y = p1.y * oneOw;
        this.z = p1.z * oneOw;
    }
}
