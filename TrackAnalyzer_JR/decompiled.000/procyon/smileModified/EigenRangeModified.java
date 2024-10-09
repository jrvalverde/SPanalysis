// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public enum EigenRangeModified
{
    ALL("ALL", 0, (byte)65), 
    VALUE("VALUE", 1, (byte)86), 
    INDEX("INDEX", 2, (byte)73);
    
    private final byte lapack;
    
    private EigenRangeModified(final String name, final int ordinal, final byte lapack) {
        this.lapack = lapack;
    }
    
    public byte lapack() {
        return this.lapack;
    }
}
