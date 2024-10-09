package smileModified;

public enum TransposeModified {
   NO_TRANSPOSE(111, (byte)78),
   TRANSPOSE(112, (byte)84),
   CONJUGATE_TRANSPOSE(113, (byte)67);

   private final int blas;
   private final byte lapack;

   private TransposeModified(int blas, byte lapack) {
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
