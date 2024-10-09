// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public enum SideModified
{
    LEFT("LEFT", 0, 141, (byte)76), 
    RIGHT("RIGHT", 1, 142, (byte)82);
    
    private final int blas;
    private final byte lapack;
    
    private SideModified(final String name, final int ordinal, final int blas, final byte lapack) {
        this.blas = blas;
        this.lapack = lapack;
    }
    
    public int blas() {
        return this.blas;
    }
    
    public byte lapack() {
        return this.lapack;
    }
}
