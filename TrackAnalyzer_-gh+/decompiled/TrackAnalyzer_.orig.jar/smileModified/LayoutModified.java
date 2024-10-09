package smileModified;

public enum LayoutModified {
   ROW_MAJOR(101),
   COL_MAJOR(102);

   private final int blas;
   private final int lapack;

   private LayoutModified(int value) {
      this.blas = value;
      this.lapack = value;
   }

   public int blas() {
      return this.blas;
   }

   public int lapack() {
      return this.lapack;
   }
}
