package smileModified;

public enum SVDJobModified {
   ALL((byte)65),
   COMPACT((byte)83),
   OVERWRITE((byte)79),
   NO_VECTORS((byte)78);

   private final byte lapack;

   private SVDJobModified(byte lapack) {
      this.lapack = lapack;
   }

   public byte lapack() {
      return this.lapack;
   }
}
