package smileModified;

public enum UPLOModified {
   UPPER(121, (byte)85),
   LOWER(122, (byte)76);

   private final int blas;
   private final byte lapack;

   private UPLOModified(int blas, byte lapack) {
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
