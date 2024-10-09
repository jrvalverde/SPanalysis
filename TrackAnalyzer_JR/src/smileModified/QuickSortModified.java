package smileModified;

import java.util.Comparator;

public class QuickSortModified {
   private static final int M = 7;
   private static final int NSTACK = 64;

   private QuickSortModified() {
   }

   public static int[] sort(int[] x) {
      int[] order = new int[x.length];

      for(int i = 0; i < order.length; order[i] = i++) {
      }

      sort(x, order);
      return order;
   }

   public static void sort(int[] x, int[] y) {
      sort(x, y, x.length);
   }

   public static void sort(int[] x, int[] y, int n) {
      int jstack = -1;
      int l = 0;
      int[] istack = new int[64];
      int ir = n - 1;

      while(true) {
         int i;
         int j;
         int a;
         int b;
         while(ir - l >= 7) {
            int k = l + ir >> 1;
            SortModified.swap(x, k, l + 1);
            SortModified.swap(y, k, l + 1);
            if (x[l] > x[ir]) {
               SortModified.swap(x, l, ir);
               SortModified.swap(y, l, ir);
            }

            if (x[l + 1] > x[ir]) {
               SortModified.swap(x, l + 1, ir);
               SortModified.swap(y, l + 1, ir);
            }

            if (x[l] > x[l + 1]) {
               SortModified.swap(x, l, l + 1);
               SortModified.swap(y, l, l + 1);
            }

            i = l + 1;
            j = ir;
            a = x[l + 1];
            b = y[l + 1];

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
                  y[l + 1] = y[j];
                  y[j] = b;
                  jstack += 2;
                  if (jstack >= 64) {
                     throw new IllegalStateException("NSTACK too small in SortModified.");
                  }

                  if (ir - i + 1 >= j - l) {
                     istack[jstack] = ir;
                     istack[jstack - 1] = i;
                     ir = j - 1;
                  } else {
                     istack[jstack] = j - 1;
                     istack[jstack - 1] = l;
                     l = i;
                  }
                  break;
               }

               SortModified.swap(x, i, j);
               SortModified.swap(y, i, j);
            }
         }

         for(j = l + 1; j <= ir; ++j) {
            a = x[j];
            b = y[j];

            for(i = j - 1; i >= l && x[i] > a; --i) {
               x[i + 1] = x[i];
               y[i + 1] = y[i];
            }

            x[i + 1] = a;
            y[i + 1] = b;
         }

         if (jstack < 0) {
            return;
         }

         ir = istack[jstack--];
         l = istack[jstack--];
      }
   }

   public static void sort(int[] x, double[] y) {
      sort(x, y, x.length);
   }

   public static void sort(int[] x, double[] y, int n) {
      int jstack = -1;
      int l = 0;
      int[] istack = new int[64];
      int ir = n - 1;

      while(true) {
         int i;
         int j;
         int a;
         double b;
         while(ir - l >= 7) {
            int k = l + ir >> 1;
            SortModified.swap(x, k, l + 1);
            SortModified.swap(y, k, l + 1);
            if (x[l] > x[ir]) {
               SortModified.swap(x, l, ir);
               SortModified.swap(y, l, ir);
            }

            if (x[l + 1] > x[ir]) {
               SortModified.swap(x, l + 1, ir);
               SortModified.swap(y, l + 1, ir);
            }

            if (x[l] > x[l + 1]) {
               SortModified.swap(x, l, l + 1);
               SortModified.swap(y, l, l + 1);
            }

            i = l + 1;
            j = ir;
            a = x[l + 1];
            b = y[l + 1];

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
                  y[l + 1] = y[j];
                  y[j] = b;
                  jstack += 2;
                  if (jstack >= 64) {
                     throw new IllegalStateException("NSTACK too small in SortModified.");
                  }

                  if (ir - i + 1 >= j - l) {
                     istack[jstack] = ir;
                     istack[jstack - 1] = i;
                     ir = j - 1;
                  } else {
                     istack[jstack] = j - 1;
                     istack[jstack - 1] = l;
                     l = i;
                  }
                  break;
               }

               SortModified.swap(x, i, j);
               SortModified.swap(y, i, j);
            }
         }

         for(j = l + 1; j <= ir; ++j) {
            a = x[j];
            b = y[j];

            for(i = j - 1; i >= l && x[i] > a; --i) {
               x[i + 1] = x[i];
               y[i + 1] = y[i];
            }

            x[i + 1] = a;
            y[i + 1] = b;
         }

         if (jstack < 0) {
            return;
         }

         ir = istack[jstack--];
         l = istack[jstack--];
      }
   }

   public static void sort(int[] x, Object[] y) {
      sort(x, y, x.length);
   }

   public static void sort(int[] x, Object[] y, int n) {
      int jstack = -1;
      int l = 0;
      int[] istack = new int[64];
      int ir = n - 1;

      while(true) {
         int i;
         int j;
         int a;
         Object b;
         while(ir - l >= 7) {
            int k = l + ir >> 1;
            SortModified.swap(x, k, l + 1);
            SortModified.swap(y, k, l + 1);
            if (x[l] > x[ir]) {
               SortModified.swap(x, l, ir);
               SortModified.swap(y, l, ir);
            }

            if (x[l + 1] > x[ir]) {
               SortModified.swap(x, l + 1, ir);
               SortModified.swap(y, l + 1, ir);
            }

            if (x[l] > x[l + 1]) {
               SortModified.swap(x, l, l + 1);
               SortModified.swap(y, l, l + 1);
            }

            i = l + 1;
            j = ir;
            a = x[l + 1];
            b = y[l + 1];

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
                  y[l + 1] = y[j];
                  y[j] = b;
                  jstack += 2;
                  if (jstack >= 64) {
                     throw new IllegalStateException("NSTACK too small in SortModified.");
                  }

                  if (ir - i + 1 >= j - l) {
                     istack[jstack] = ir;
                     istack[jstack - 1] = i;
                     ir = j - 1;
                  } else {
                     istack[jstack] = j - 1;
                     istack[jstack - 1] = l;
                     l = i;
                  }
                  break;
               }

               SortModified.swap(x, i, j);
               SortModified.swap(y, i, j);
            }
         }

         for(j = l + 1; j <= ir; ++j) {
            a = x[j];
            b = y[j];

            for(i = j - 1; i >= l && x[i] > a; --i) {
               x[i + 1] = x[i];
               y[i + 1] = y[i];
            }

            x[i + 1] = a;
            y[i + 1] = b;
         }

         if (jstack < 0) {
            return;
         }

         ir = istack[jstack--];
         l = istack[jstack--];
      }
   }

   public static int[] sort(float[] x) {
      int[] order = new int[x.length];

      for(int i = 0; i < order.length; order[i] = i++) {
      }

      sort(x, order);
      return order;
   }

   public static void sort(float[] x, int[] y) {
      sort(x, y, x.length);
   }

   public static void sort(float[] x, int[] y, int n) {
      int jstack = -1;
      int l = 0;
      int[] istack = new int[64];
      int ir = n - 1;

      while(true) {
         int i;
         int j;
         float a;
         int b;
         while(ir - l >= 7) {
            int k = l + ir >> 1;
            SortModified.swap(x, k, l + 1);
            SortModified.swap(y, k, l + 1);
            if (x[l] > x[ir]) {
               SortModified.swap(x, l, ir);
               SortModified.swap(y, l, ir);
            }

            if (x[l + 1] > x[ir]) {
               SortModified.swap(x, l + 1, ir);
               SortModified.swap(y, l + 1, ir);
            }

            if (x[l] > x[l + 1]) {
               SortModified.swap(x, l, l + 1);
               SortModified.swap(y, l, l + 1);
            }

            i = l + 1;
            j = ir;
            a = x[l + 1];
            b = y[l + 1];

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
                  y[l + 1] = y[j];
                  y[j] = b;
                  jstack += 2;
                  if (jstack >= 64) {
                     throw new IllegalStateException("NSTACK too small in SortModified.");
                  }

                  if (ir - i + 1 >= j - l) {
                     istack[jstack] = ir;
                     istack[jstack - 1] = i;
                     ir = j - 1;
                  } else {
                     istack[jstack] = j - 1;
                     istack[jstack - 1] = l;
                     l = i;
                  }
                  break;
               }

               SortModified.swap(x, i, j);
               SortModified.swap(y, i, j);
            }
         }

         for(j = l + 1; j <= ir; ++j) {
            a = x[j];
            b = y[j];

            for(i = j - 1; i >= l && !(x[i] <= a); --i) {
               x[i + 1] = x[i];
               y[i + 1] = y[i];
            }

            x[i + 1] = a;
            y[i + 1] = b;
         }

         if (jstack < 0) {
            return;
         }

         ir = istack[jstack--];
         l = istack[jstack--];
      }
   }

   public static void sort(float[] x, float[] y) {
      sort(x, y, x.length);
   }

   public static void sort(float[] x, float[] y, int n) {
      int jstack = -1;
      int l = 0;
      int[] istack = new int[64];
      int ir = n - 1;

      while(true) {
         int i;
         int j;
         float a;
         float b;
         while(ir - l >= 7) {
            int k = l + ir >> 1;
            SortModified.swap(x, k, l + 1);
            SortModified.swap(y, k, l + 1);
            if (x[l] > x[ir]) {
               SortModified.swap(x, l, ir);
               SortModified.swap(y, l, ir);
            }

            if (x[l + 1] > x[ir]) {
               SortModified.swap(x, l + 1, ir);
               SortModified.swap(y, l + 1, ir);
            }

            if (x[l] > x[l + 1]) {
               SortModified.swap(x, l, l + 1);
               SortModified.swap(y, l, l + 1);
            }

            i = l + 1;
            j = ir;
            a = x[l + 1];
            b = y[l + 1];

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
                  y[l + 1] = y[j];
                  y[j] = b;
                  jstack += 2;
                  if (jstack >= 64) {
                     throw new IllegalStateException("NSTACK too small in SortModified.");
                  }

                  if (ir - i + 1 >= j - l) {
                     istack[jstack] = ir;
                     istack[jstack - 1] = i;
                     ir = j - 1;
                  } else {
                     istack[jstack] = j - 1;
                     istack[jstack - 1] = l;
                     l = i;
                  }
                  break;
               }

               SortModified.swap(x, i, j);
               SortModified.swap(y, i, j);
            }
         }

         for(j = l + 1; j <= ir; ++j) {
            a = x[j];
            b = y[j];

            for(i = j - 1; i >= l && !(x[i] <= a); --i) {
               x[i + 1] = x[i];
               y[i + 1] = y[i];
            }

            x[i + 1] = a;
            y[i + 1] = b;
         }

         if (jstack < 0) {
            return;
         }

         ir = istack[jstack--];
         l = istack[jstack--];
      }
   }

   public static void sort(float[] x, Object[] y) {
      sort(x, y, x.length);
   }

   public static void sort(float[] x, Object[] y, int n) {
      int jstack = -1;
      int l = 0;
      int[] istack = new int[64];
      int ir = n - 1;

      while(true) {
         int i;
         int j;
         float a;
         Object b;
         while(ir - l >= 7) {
            int k = l + ir >> 1;
            SortModified.swap(x, k, l + 1);
            SortModified.swap(y, k, l + 1);
            if (x[l] > x[ir]) {
               SortModified.swap(x, l, ir);
               SortModified.swap(y, l, ir);
            }

            if (x[l + 1] > x[ir]) {
               SortModified.swap(x, l + 1, ir);
               SortModified.swap(y, l + 1, ir);
            }

            if (x[l] > x[l + 1]) {
               SortModified.swap(x, l, l + 1);
               SortModified.swap(y, l, l + 1);
            }

            i = l + 1;
            j = ir;
            a = x[l + 1];
            b = y[l + 1];

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
                  y[l + 1] = y[j];
                  y[j] = b;
                  jstack += 2;
                  if (jstack >= 64) {
                     throw new IllegalStateException("NSTACK too small in SortModified.");
                  }

                  if (ir - i + 1 >= j - l) {
                     istack[jstack] = ir;
                     istack[jstack - 1] = i;
                     ir = j - 1;
                  } else {
                     istack[jstack] = j - 1;
                     istack[jstack - 1] = l;
                     l = i;
                  }
                  break;
               }

               SortModified.swap(x, i, j);
               SortModified.swap(y, i, j);
            }
         }

         for(j = l + 1; j <= ir; ++j) {
            a = x[j];
            b = y[j];

            for(i = j - 1; i >= l && !(x[i] <= a); --i) {
               x[i + 1] = x[i];
               y[i + 1] = y[i];
            }

            x[i + 1] = a;
            y[i + 1] = b;
         }

         if (jstack < 0) {
            return;
         }

         ir = istack[jstack--];
         l = istack[jstack--];
      }
   }

   public static int[] sort(double[] x) {
      int[] order = new int[x.length];

      for(int i = 0; i < order.length; order[i] = i++) {
      }

      sort(x, order);
      return order;
   }

   public static void sort(double[] x, int[] y) {
      sort(x, y, x.length);
   }

   public static void sort(double[] x, int[] y, int n) {
      int jstack = -1;
      int l = 0;
      int[] istack = new int[64];
      int ir = n - 1;

      while(true) {
         int i;
         int j;
         double a;
         int b;
         while(ir - l >= 7) {
            int k = l + ir >> 1;
            SortModified.swap(x, k, l + 1);
            SortModified.swap(y, k, l + 1);
            if (x[l] > x[ir]) {
               SortModified.swap(x, l, ir);
               SortModified.swap(y, l, ir);
            }

            if (x[l + 1] > x[ir]) {
               SortModified.swap(x, l + 1, ir);
               SortModified.swap(y, l + 1, ir);
            }

            if (x[l] > x[l + 1]) {
               SortModified.swap(x, l, l + 1);
               SortModified.swap(y, l, l + 1);
            }

            i = l + 1;
            j = ir;
            a = x[l + 1];
            b = y[l + 1];

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
                  y[l + 1] = y[j];
                  y[j] = b;
                  jstack += 2;
                  if (jstack >= 64) {
                     throw new IllegalStateException("NSTACK too small in SortModified.");
                  }

                  if (ir - i + 1 >= j - l) {
                     istack[jstack] = ir;
                     istack[jstack - 1] = i;
                     ir = j - 1;
                  } else {
                     istack[jstack] = j - 1;
                     istack[jstack - 1] = l;
                     l = i;
                  }
                  break;
               }

               SortModified.swap(x, i, j);
               SortModified.swap(y, i, j);
            }
         }

         for(j = l + 1; j <= ir; ++j) {
            a = x[j];
            b = y[j];

            for(i = j - 1; i >= l && !(x[i] <= a); --i) {
               x[i + 1] = x[i];
               y[i + 1] = y[i];
            }

            x[i + 1] = a;
            y[i + 1] = b;
         }

         if (jstack < 0) {
            return;
         }

         ir = istack[jstack--];
         l = istack[jstack--];
      }
   }

   public static void sort(double[] x, double[] y) {
      sort(x, y, x.length);
   }

   public static void sort(double[] x, double[] y, int n) {
      int jstack = -1;
      int l = 0;
      int[] istack = new int[64];
      int ir = n - 1;

      while(true) {
         int i;
         int j;
         double a;
         double b;
         while(ir - l >= 7) {
            int k = l + ir >> 1;
            SortModified.swap(x, k, l + 1);
            SortModified.swap(y, k, l + 1);
            if (x[l] > x[ir]) {
               SortModified.swap(x, l, ir);
               SortModified.swap(y, l, ir);
            }

            if (x[l + 1] > x[ir]) {
               SortModified.swap(x, l + 1, ir);
               SortModified.swap(y, l + 1, ir);
            }

            if (x[l] > x[l + 1]) {
               SortModified.swap(x, l, l + 1);
               SortModified.swap(y, l, l + 1);
            }

            i = l + 1;
            j = ir;
            a = x[l + 1];
            b = y[l + 1];

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
                  y[l + 1] = y[j];
                  y[j] = b;
                  jstack += 2;
                  if (jstack >= 64) {
                     throw new IllegalStateException("NSTACK too small in SortModified.");
                  }

                  if (ir - i + 1 >= j - l) {
                     istack[jstack] = ir;
                     istack[jstack - 1] = i;
                     ir = j - 1;
                  } else {
                     istack[jstack] = j - 1;
                     istack[jstack - 1] = l;
                     l = i;
                  }
                  break;
               }

               SortModified.swap(x, i, j);
               SortModified.swap(y, i, j);
            }
         }

         for(j = l + 1; j <= ir; ++j) {
            a = x[j];
            b = y[j];

            for(i = j - 1; i >= l && !(x[i] <= a); --i) {
               x[i + 1] = x[i];
               y[i + 1] = y[i];
            }

            x[i + 1] = a;
            y[i + 1] = b;
         }

         if (jstack < 0) {
            return;
         }

         ir = istack[jstack--];
         l = istack[jstack--];
      }
   }

   public static void sort(double[] x, Object[] y) {
      sort(x, y, x.length);
   }

   public static void sort(double[] x, Object[] y, int n) {
      int jstack = -1;
      int l = 0;
      int[] istack = new int[64];
      int ir = n - 1;

      while(true) {
         int i;
         int j;
         double a;
         Object b;
         while(ir - l >= 7) {
            int k = l + ir >> 1;
            SortModified.swap(x, k, l + 1);
            SortModified.swap(y, k, l + 1);
            if (x[l] > x[ir]) {
               SortModified.swap(x, l, ir);
               SortModified.swap(y, l, ir);
            }

            if (x[l + 1] > x[ir]) {
               SortModified.swap(x, l + 1, ir);
               SortModified.swap(y, l + 1, ir);
            }

            if (x[l] > x[l + 1]) {
               SortModified.swap(x, l, l + 1);
               SortModified.swap(y, l, l + 1);
            }

            i = l + 1;
            j = ir;
            a = x[l + 1];
            b = y[l + 1];

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
                  y[l + 1] = y[j];
                  y[j] = b;
                  jstack += 2;
                  if (jstack >= 64) {
                     throw new IllegalStateException("NSTACK too small in SortModified.");
                  }

                  if (ir - i + 1 >= j - l) {
                     istack[jstack] = ir;
                     istack[jstack - 1] = i;
                     ir = j - 1;
                  } else {
                     istack[jstack] = j - 1;
                     istack[jstack - 1] = l;
                     l = i;
                  }
                  break;
               }

               SortModified.swap(x, i, j);
               SortModified.swap(y, i, j);
            }
         }

         for(j = l + 1; j <= ir; ++j) {
            a = x[j];
            b = y[j];

            for(i = j - 1; i >= l && !(x[i] <= a); --i) {
               x[i + 1] = x[i];
               y[i + 1] = y[i];
            }

            x[i + 1] = a;
            y[i + 1] = b;
         }

         if (jstack < 0) {
            return;
         }

         ir = istack[jstack--];
         l = istack[jstack--];
      }
   }

   public static <T extends Comparable<? super T>> int[] sort(T[] x) {
      int[] order = new int[x.length];

      for(int i = 0; i < order.length; order[i] = i++) {
      }

      sort(x, order);
      return order;
   }

   public static <T extends Comparable<? super T>> void sort(T[] x, int[] y) {
      sort(x, y, x.length);
   }

   public static <T extends Comparable<? super T>> void sort(T[] x, int[] y, int n) {
      int jstack = -1;
      int l = 0;
      int[] istack = new int[64];
      int ir = n - 1;

      while(true) {
         int i;
         int j;
         Comparable a;
         int b;
         while(ir - l >= 7) {
            int k = l + ir >> 1;
            SortModified.swap((Object[])x, k, l + 1);
            SortModified.swap(y, k, l + 1);
            if (x[l].compareTo(x[ir]) > 0) {
               SortModified.swap((Object[])x, l, ir);
               SortModified.swap(y, l, ir);
            }

            if (x[l + 1].compareTo(x[ir]) > 0) {
               SortModified.swap((Object[])x, l + 1, ir);
               SortModified.swap(y, l + 1, ir);
            }

            if (x[l].compareTo(x[l + 1]) > 0) {
               SortModified.swap((Object[])x, l, l + 1);
               SortModified.swap(y, l, l + 1);
            }

            i = l + 1;
            j = ir;
            a = x[l + 1];
            b = y[l + 1];

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
                  y[l + 1] = y[j];
                  y[j] = b;
                  jstack += 2;
                  if (jstack >= 64) {
                     throw new IllegalStateException("NSTACK too small in SortModified.");
                  }

                  if (ir - i + 1 >= j - l) {
                     istack[jstack] = ir;
                     istack[jstack - 1] = i;
                     ir = j - 1;
                  } else {
                     istack[jstack] = j - 1;
                     istack[jstack - 1] = l;
                     l = i;
                  }
                  break;
               }

               SortModified.swap((Object[])x, i, j);
               SortModified.swap(y, i, j);
            }
         }

         for(j = l + 1; j <= ir; ++j) {
            a = x[j];
            b = y[j];

            for(i = j - 1; i >= l && x[i].compareTo(a) > 0; --i) {
               x[i + 1] = x[i];
               y[i + 1] = y[i];
            }

            x[i + 1] = a;
            y[i + 1] = b;
         }

         if (jstack < 0) {
            return;
         }

         ir = istack[jstack--];
         l = istack[jstack--];
      }
   }

   public static <T> void sort(T[] x, int[] y, int n, Comparator<T> comparator) {
      int jstack = -1;
      int l = 0;
      int[] istack = new int[64];
      int ir = n - 1;

      while(true) {
         int i;
         int j;
         Object a;
         int b;
         while(ir - l >= 7) {
            int k = l + ir >> 1;
            SortModified.swap(x, k, l + 1);
            SortModified.swap(y, k, l + 1);
            if (comparator.compare(x[l], x[ir]) > 0) {
               SortModified.swap(x, l, ir);
               SortModified.swap(y, l, ir);
            }

            if (comparator.compare(x[l + 1], x[ir]) > 0) {
               SortModified.swap(x, l + 1, ir);
               SortModified.swap(y, l + 1, ir);
            }

            if (comparator.compare(x[l], x[l + 1]) > 0) {
               SortModified.swap(x, l, l + 1);
               SortModified.swap(y, l, l + 1);
            }

            i = l + 1;
            j = ir;
            a = x[l + 1];
            b = y[l + 1];

            while(true) {
               do {
                  ++i;
               } while(comparator.compare(x[i], a) < 0);

               do {
                  --j;
               } while(comparator.compare(x[j], a) > 0);

               if (j < i) {
                  x[l + 1] = x[j];
                  x[j] = a;
                  y[l + 1] = y[j];
                  y[j] = b;
                  jstack += 2;
                  if (jstack >= 64) {
                     throw new IllegalStateException("NSTACK too small in SortModified.");
                  }

                  if (ir - i + 1 >= j - l) {
                     istack[jstack] = ir;
                     istack[jstack - 1] = i;
                     ir = j - 1;
                  } else {
                     istack[jstack] = j - 1;
                     istack[jstack - 1] = l;
                     l = i;
                  }
                  break;
               }

               SortModified.swap(x, i, j);
               SortModified.swap(y, i, j);
            }
         }

         for(j = l + 1; j <= ir; ++j) {
            a = x[j];
            b = y[j];

            for(i = j - 1; i >= l && comparator.compare(x[i], a) > 0; --i) {
               x[i + 1] = x[i];
               y[i + 1] = y[i];
            }

            x[i + 1] = a;
            y[i + 1] = b;
         }

         if (jstack < 0) {
            return;
         }

         ir = istack[jstack--];
         l = istack[jstack--];
      }
   }

   public static <T extends Comparable<? super T>> void sort(T[] x, Object[] y) {
      sort(x, y, x.length);
   }

   public static <T extends Comparable<? super T>> void sort(T[] x, Object[] y, int n) {
      int jstack = -1;
      int l = 0;
      int[] istack = new int[64];
      int ir = n - 1;

      while(true) {
         int i;
         int j;
         Comparable a;
         Object b;
         while(ir - l >= 7) {
            int k = l + ir >> 1;
            SortModified.swap((Object[])x, k, l + 1);
            SortModified.swap(y, k, l + 1);
            if (x[l].compareTo(x[ir]) > 0) {
               SortModified.swap((Object[])x, l, ir);
               SortModified.swap(y, l, ir);
            }

            if (x[l + 1].compareTo(x[ir]) > 0) {
               SortModified.swap((Object[])x, l + 1, ir);
               SortModified.swap(y, l + 1, ir);
            }

            if (x[l].compareTo(x[l + 1]) > 0) {
               SortModified.swap((Object[])x, l, l + 1);
               SortModified.swap(y, l, l + 1);
            }

            i = l + 1;
            j = ir;
            a = x[l + 1];
            b = y[l + 1];

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
                  y[l + 1] = y[j];
                  y[j] = b;
                  jstack += 2;
                  if (jstack >= 64) {
                     throw new IllegalStateException("NSTACK too small in SortModified.");
                  }

                  if (ir - i + 1 >= j - l) {
                     istack[jstack] = ir;
                     istack[jstack - 1] = i;
                     ir = j - 1;
                  } else {
                     istack[jstack] = j - 1;
                     istack[jstack - 1] = l;
                     l = i;
                  }
                  break;
               }

               SortModified.swap((Object[])x, i, j);
               SortModified.swap(y, i, j);
            }
         }

         for(j = l + 1; j <= ir; ++j) {
            a = x[j];
            b = y[j];

            for(i = j - 1; i >= l && x[i].compareTo(a) > 0; --i) {
               x[i + 1] = x[i];
               y[i + 1] = y[i];
            }

            x[i + 1] = a;
            y[i + 1] = b;
         }

         if (jstack < 0) {
            return;
         }

         ir = istack[jstack--];
         l = istack[jstack--];
      }
   }
}
