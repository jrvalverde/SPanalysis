// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public enum SVDJobModified
{
    ALL("ALL", 0, (byte)65), 
    COMPACT("COMPACT", 1, (byte)83), 
    OVERWRITE("OVERWRITE", 2, (byte)79), 
    NO_VECTORS("NO_VECTORS", 3, (byte)78);
    
    private final byte lapack;
    
    private SVDJobModified(final String name, final int ordinal, final byte lapack) {
        this.lapack = lapack;
    }
    
    public byte lapack() {
        return this.lapack;
    }
}
