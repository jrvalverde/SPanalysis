package smileModified;

public interface QuickSelectModified {
   static int select(int[] x, int k) {
      int n = x.length;
      int l = 0;
      int ir = n - 1;

      while(ir > l + 1) {
         int mid = l + ir >> 1;
         SortModified.swap(x, mid, l + 1);
         if (x[l] > x[ir]) {
            SortModified.swap(x, l, ir);
         }

         if (x[l + 1] > x[ir]) {
            SortModified.swap(x, l + 1, ir);
         }

         if (x[l] > x[l + 1]) {
            SortModified.swap(x, l, l + 1);
         }

         int i = l + 1;
         int j = ir;
         int a = x[l + 1];

         while(true) {
            do {
               ++i;
            } while(x[i] < a);

            do {
               --j;
            } while(x[j] > a);

            if (j < i) {
               x[l + 1] = x[j];
               x[j] = a;
               if (j >= k) {
                  ir = j - 1;
               }

               if (j <= k) {
                  l = i;
               }
               break;
            }

            SortModified.swap(x, i, j);
         }
      }

      if (ir == l + 1 && x[ir] < x[l]) {
         SortModified.swap(x, l, ir);
      }

      return x[k];
   }

   static float select(float[] x, int k) {
      int n = x.length;
      int l = 0;
      int ir = n - 1;

      while(ir > l + 1) {
         int mid = l + ir >> 1;
         SortModified.swap(x, mid, l + 1);
         if (x[l] > x[ir]) {
            SortModified.swap(x, l, ir);
         }

         if (x[l + 1] > x[ir]) {
            SortModified.swap(x, l + 1, ir);
         }

         if (x[l] > x[l + 1]) {
            SortModified.swap(x, l, l + 1);
         }

         int i = l + 1;
         int j = ir;
         float a = x[l + 1];

         while(true) {
            do {
               ++i;
            } while(x[i] < a);

            do {
               --j;
            } while(x[j] > a);

            if (j < i) {
               x[l + 1] = x[j];
               x[j] = a;
               if (j >= k) {
                  ir = j - 1;
               }

               if (j <= k) {
                  l = i;
               }
               break;
            }

            SortModified.swap(x, i, j);
         }
      }

      if (ir == l + 1 && x[ir] < x[l]) {
         SortModified.swap(x, l, ir);
      }

      return x[k];
   }

   static double select(double[] x, int k) {
      int n = x.length;
      int l = 0;
      int ir = n - 1;

      while(ir > l + 1) {
         int mid = l + ir >> 1;
         SortModified.swap(x, mid, l + 1);
         if (x[l] > x[ir]) {
            SortModified.swap(x, l, ir);
         }

         if (x[l + 1] > x[ir]) {
            SortModified.swap(x, l + 1, ir);
         }

         if (x[l] > x[l + 1]) {
            SortModified.swap(x, l, l + 1);
         }

         int i = l + 1;
         int j = ir;
         double a = x[l + 1];

         while(true) {
            do {
               ++i;
            } while(x[i] < a);

            do {
               --j;
            } while(x[j] > a);

            if (j < i) {
               x[l + 1] = x[j];
               x[j] = a;
               if (j >= k) {
                  ir = j - 1;
               }

               if (j <= k) {
                  l = i;
               }
               break;
            }

            SortModified.swap(x, i, j);
         }
      }

      if (ir == l + 1 && x[ir] < x[l]) {
         SortModified.swap(x, l, ir);
      }

      return x[k];
   }

   static <T extends Comparable<? super T>> T select(T[] x, int k) {
      int n = x.length;
      int l = 0;
      int ir = n - 1;

      while(ir > l + 1) {
         int mid = l + ir >> 1;
         SortModified.swap((Object[])x, mid, l + 1);
         if (x[l].compareTo(x[ir]) > 0) {
            SortModified.swap((Object[])x, l, ir);
         }

         if (x[l + 1].compareTo(x[ir]) > 0) {
            SortModified.swap((Object[])x, l + 1, ir);
         }

         if (x[l].compareTo(x[l + 1]) > 0) {
            SortModified.swap((Object[])x, l, l + 1);
         }

         int i = l + 1;
         int j = ir;
         Comparable a = x[l + 1];

         while(true) {
            do {
               ++i;
            } while(x[i].compareTo(a) < 0);

            do {
               --j;
            } while(x[j].compareTo(a) > 0);

            if (j < i) {
               x[l + 1] = x[j];
               x[j] = a;
               if (j >= k) {
                  ir = j - 1;
               }

               if (j <= k) {
                  l = i;
               }
               break;
            }

            SortModified.swap((Object[])x, i, j);
         }
      }

      if (ir == l + 1 && x[ir].compareTo(x[l]) < 0) {
         SortModified.swap((Object[])x, l, ir);
      }

      return x[k];
   }

   static int median(int[] x) {
      int k = x.length / 2;
      return select(x, k);
   }

   static float median(float[] x) {
      int k = x.length / 2;
      return select(x, k);
   }

   static double median(double[] x) {
      int k = x.length / 2;
      return select(x, k);
   }

   static <T extends Comparable<? super T>> T median(T[] x) {
      int k = x.length / 2;
      return select(x, k);
   }

   static int q1(int[] x) {
      int k = x.length / 4;
      return select(x, k);
   }

   static float q1(float[] x) {
      int k = x.length / 4;
      return select(x, k);
   }

   static double q1(double[] x) {
      int k = x.length / 4;
      return select(x, k);
   }

   static <T extends Comparable<? super T>> T q1(T[] x) {
      int k = x.length / 4;
      return select(x, k);
   }

   static int q3(int[] x) {
      int k = 3 * x.length / 4;
      return select(x, k);
   }

   static float q3(float[] x) {
      int k = 3 * x.length / 4;
      return select(x, k);
   }

   static double q3(double[] x) {
      int k = 3 * x.length / 4;
      return select(x, k);
   }

   static <T extends Comparable<? super T>> T q3(T[] x) {
      int k = 3 * x.length / 4;
      return select(x, k);
   }
}
