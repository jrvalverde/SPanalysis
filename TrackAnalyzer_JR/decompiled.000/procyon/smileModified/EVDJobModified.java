// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public enum EVDJobModified
{
    NO_VECTORS("NO_VECTORS", 0, (byte)78), 
    VECTORS("VECTORS", 1, (byte)86);
    
    private final byte lapack;
    
    private EVDJobModified(final String name, final int ordinal, final byte lapack) {
        this.lapack = lapack;
    }
    
    public byte lapack() {
        return this.lapack;
    }
}
