package smileModified;

public enum SideModified {
   LEFT(141, (byte)76),
   RIGHT(142, (byte)82);

   private final int blas;
   private final byte lapack;

   private SideModified(int blas, byte lapack) {
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
