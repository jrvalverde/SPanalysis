package smileModified;

public enum EVDJobModified {
   NO_VECTORS((byte)78),
   VECTORS((byte)86);

   private final byte lapack;

   private EVDJobModified(byte lapack) {
      this.lapack = lapack;
   }

   public byte lapack() {
      return this.lapack;
   }
}
