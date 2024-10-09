package smileModified;

public enum EigenRangeModified {
   ALL((byte)65),
   VALUE((byte)86),
   INDEX((byte)73);

   private final byte lapack;

   private EigenRangeModified(byte lapack) {
      this.lapack = lapack;
   }

   public byte lapack() {
      return this.lapack;
   }
}
