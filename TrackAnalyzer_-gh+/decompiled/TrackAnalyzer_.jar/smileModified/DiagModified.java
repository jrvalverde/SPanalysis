package smileModified;

public enum DiagModified {
   NON_UNIT(131, (byte)78),
   UNIT(132, (byte)85);

   private final int blas;
   private final byte lapack;

   private DiagModified(int blas, byte lapack) {
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
