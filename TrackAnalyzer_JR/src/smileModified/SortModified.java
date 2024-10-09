package smileModified;

public interface SortModified {
   static void swap(int[] x, int i, int j) {
      int a = x[i];
      x[i] = x[j];
      x[j] = a;
   }

   static void swap(float[] x, int i, int j) {
      float a = x[i];
      x[i] = x[j];
      x[j] = a;
   }

   static void swap(double[] x, int i, int j) {
      double a = x[i];
      x[i] = x[j];
      x[j] = a;
   }

   static void swap(Object[] x, int i, int j) {
      Object a = x[i];
      x[i] = x[j];
      x[j] = a;
   }

   static void siftUp(int[] x, int k) {
      while(k > 1 && x[k / 2] < x[k]) {
         swap(x, k, k / 2);
         k /= 2;
      }

   }

   static void siftUp(float[] x, int k) {
      while(k > 1 && x[k / 2] < x[k]) {
         swap(x, k, k / 2);
         k /= 2;
      }

   }

   static void siftUp(double[] x, int k) {
      while(k > 1 && x[k / 2] < x[k]) {
         swap(x, k, k / 2);
         k /= 2;
      }

   }

   static <T extends Comparable<? super T>> void siftUp(T[] x, int k) {
      while(k > 1 && x[k / 2].compareTo(x[k]) < 0) {
         swap((Object[])x, k, k / 2);
         k /= 2;
      }

   }

   static void siftDown(int[] x, int k, int n) {
      while(true) {
         if (2 * k <= n) {
            int j = 2 * k;
            if (j < n && x[j] < x[j + 1]) {
               ++j;
            }

            if (x[k] < x[j]) {
               swap(x, k, j);
               k = j;
               continue;
            }
         }

         return;
      }
   }

   static void siftDown(float[] x, int k, int n) {
      while(true) {
         if (2 * k <= n) {
            int j = 2 * k;
            if (j < n && x[j] < x[j + 1]) {
               ++j;
            }

            if (!(x[k] >= x[j])) {
               swap(x, k, j);
               k = j;
               continue;
            }
         }

         return;
      }
   }

   static void siftDown(double[] x, int k, int n) {
      while(true) {
         if (2 * k <= n) {
            int j = 2 * k;
            if (j < n && x[j] < x[j + 1]) {
               ++j;
            }

            if (!(x[k] >= x[j])) {
               swap(x, k, j);
               k = j;
               continue;
            }
         }

         return;
      }
   }

   static <T extends Comparable<? super T>> void siftDown(T[] x, int k, int n) {
      while(true) {
         if (2 * k <= n) {
            int j = 2 * k;
            if (j < n && x[j].compareTo(x[j + 1]) < 0) {
               ++j;
            }

            if (x[k].compareTo(x[j]) < 0) {
               swap((Object[])x, k, j);
               k = j;
               continue;
            }
         }

         return;
      }
   }
}
