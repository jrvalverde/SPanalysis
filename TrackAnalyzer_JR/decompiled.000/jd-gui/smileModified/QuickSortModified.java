/*      */ package smileModified;
/*      */ 
/*      */ import java.util.Comparator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class QuickSortModified
/*      */ {
/*      */   private static final int M = 7;
/*      */   private static final int NSTACK = 64;
/*      */   
/*      */   public static int[] sort(int[] x) {
/*   22 */     int[] order = new int[x.length];
/*   23 */     for (int i = 0; i < order.length; i++) {
/*   24 */       order[i] = i;
/*      */     }
/*   26 */     sort(x, order);
/*   27 */     return order;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(int[] x, int[] y) {
/*   37 */     sort(x, y, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(int[] x, int[] y, int n) {
/*   49 */     int jstack = -1;
/*   50 */     int l = 0;
/*   51 */     int[] istack = new int[64];
/*   52 */     int ir = n - 1;
/*      */ 
/*      */     
/*      */     while (true) {
/*   56 */       while (ir - l < 7) {
/*   57 */         for (int m = l + 1; m <= ir; m++) {
/*   58 */           int i2 = x[m];
/*   59 */           int i3 = y[m]; int i1;
/*   60 */           for (i1 = m - 1; i1 >= l && 
/*   61 */             x[i1] > i2; i1--) {
/*      */ 
/*      */             
/*   64 */             x[i1 + 1] = x[i1];
/*   65 */             y[i1 + 1] = y[i1];
/*      */           } 
/*   67 */           x[i1 + 1] = i2;
/*   68 */           y[i1 + 1] = i3;
/*      */         } 
/*   70 */         if (jstack >= 0) {
/*      */ 
/*      */           
/*   73 */           ir = istack[jstack--];
/*   74 */           l = istack[jstack--]; continue;
/*      */         }  return;
/*   76 */       }  int k = l + ir >> 1;
/*   77 */       SortModified.swap(x, k, l + 1);
/*   78 */       SortModified.swap(y, k, l + 1);
/*   79 */       if (x[l] > x[ir]) {
/*   80 */         SortModified.swap(x, l, ir);
/*   81 */         SortModified.swap(y, l, ir);
/*      */       } 
/*   83 */       if (x[l + 1] > x[ir]) {
/*   84 */         SortModified.swap(x, l + 1, ir);
/*   85 */         SortModified.swap(y, l + 1, ir);
/*      */       } 
/*   87 */       if (x[l] > x[l + 1]) {
/*   88 */         SortModified.swap(x, l, l + 1);
/*   89 */         SortModified.swap(y, l, l + 1);
/*      */       } 
/*   91 */       int i = l + 1;
/*   92 */       int j = ir;
/*   93 */       int a = x[l + 1];
/*   94 */       int b = y[l + 1];
/*      */       
/*      */       while (true) {
/*   97 */         i++;
/*   98 */         if (x[i] >= a) {
/*      */           do {
/*  100 */             j--;
/*  101 */           } while (x[j] > a);
/*  102 */           if (j < i) {
/*      */             break;
/*      */           }
/*  105 */           SortModified.swap(x, i, j);
/*  106 */           SortModified.swap(y, i, j);
/*      */         } 
/*  108 */       }  x[l + 1] = x[j];
/*  109 */       x[j] = a;
/*  110 */       y[l + 1] = y[j];
/*  111 */       y[j] = b;
/*  112 */       jstack += 2;
/*      */       
/*  114 */       if (jstack >= 64) {
/*  115 */         throw new IllegalStateException("NSTACK too small in SortModified.");
/*      */       }
/*      */       
/*  118 */       if (ir - i + 1 >= j - l) {
/*  119 */         istack[jstack] = ir;
/*  120 */         istack[jstack - 1] = i;
/*  121 */         ir = j - 1; continue;
/*      */       } 
/*  123 */       istack[jstack] = j - 1;
/*  124 */       istack[jstack - 1] = l;
/*  125 */       l = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(int[] x, double[] y) {
/*  138 */     sort(x, y, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(int[] x, double[] y, int n) {
/*  150 */     int jstack = -1;
/*  151 */     int l = 0;
/*  152 */     int[] istack = new int[64];
/*  153 */     int ir = n - 1;
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  158 */       while (ir - l < 7) {
/*  159 */         for (int m = l + 1; m <= ir; m++) {
/*  160 */           int i2 = x[m];
/*  161 */           double d = y[m]; int i1;
/*  162 */           for (i1 = m - 1; i1 >= l && 
/*  163 */             x[i1] > i2; i1--) {
/*      */ 
/*      */             
/*  166 */             x[i1 + 1] = x[i1];
/*  167 */             y[i1 + 1] = y[i1];
/*      */           } 
/*  169 */           x[i1 + 1] = i2;
/*  170 */           y[i1 + 1] = d;
/*      */         } 
/*  172 */         if (jstack >= 0) {
/*      */ 
/*      */           
/*  175 */           ir = istack[jstack--];
/*  176 */           l = istack[jstack--]; continue;
/*      */         }  return;
/*  178 */       }  int k = l + ir >> 1;
/*  179 */       SortModified.swap(x, k, l + 1);
/*  180 */       SortModified.swap(y, k, l + 1);
/*  181 */       if (x[l] > x[ir]) {
/*  182 */         SortModified.swap(x, l, ir);
/*  183 */         SortModified.swap(y, l, ir);
/*      */       } 
/*  185 */       if (x[l + 1] > x[ir]) {
/*  186 */         SortModified.swap(x, l + 1, ir);
/*  187 */         SortModified.swap(y, l + 1, ir);
/*      */       } 
/*  189 */       if (x[l] > x[l + 1]) {
/*  190 */         SortModified.swap(x, l, l + 1);
/*  191 */         SortModified.swap(y, l, l + 1);
/*      */       } 
/*  193 */       int i = l + 1;
/*  194 */       int j = ir;
/*  195 */       int a = x[l + 1];
/*  196 */       double b = y[l + 1];
/*      */       
/*      */       while (true) {
/*  199 */         i++;
/*  200 */         if (x[i] >= a) {
/*      */           do {
/*  202 */             j--;
/*  203 */           } while (x[j] > a);
/*  204 */           if (j < i) {
/*      */             break;
/*      */           }
/*  207 */           SortModified.swap(x, i, j);
/*  208 */           SortModified.swap(y, i, j);
/*      */         } 
/*  210 */       }  x[l + 1] = x[j];
/*  211 */       x[j] = a;
/*  212 */       y[l + 1] = y[j];
/*  213 */       y[j] = b;
/*  214 */       jstack += 2;
/*      */       
/*  216 */       if (jstack >= 64) {
/*  217 */         throw new IllegalStateException("NSTACK too small in SortModified.");
/*      */       }
/*      */       
/*  220 */       if (ir - i + 1 >= j - l) {
/*  221 */         istack[jstack] = ir;
/*  222 */         istack[jstack - 1] = i;
/*  223 */         ir = j - 1; continue;
/*      */       } 
/*  225 */       istack[jstack] = j - 1;
/*  226 */       istack[jstack - 1] = l;
/*  227 */       l = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(int[] x, Object[] y) {
/*  240 */     sort(x, y, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(int[] x, Object[] y, int n) {
/*  252 */     int jstack = -1;
/*  253 */     int l = 0;
/*  254 */     int[] istack = new int[64];
/*  255 */     int ir = n - 1;
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  260 */       while (ir - l < 7) {
/*  261 */         for (int m = l + 1; m <= ir; m++) {
/*  262 */           int i2 = x[m];
/*  263 */           Object object = y[m]; int i1;
/*  264 */           for (i1 = m - 1; i1 >= l && 
/*  265 */             x[i1] > i2; i1--) {
/*      */ 
/*      */             
/*  268 */             x[i1 + 1] = x[i1];
/*  269 */             y[i1 + 1] = y[i1];
/*      */           } 
/*  271 */           x[i1 + 1] = i2;
/*  272 */           y[i1 + 1] = object;
/*      */         } 
/*  274 */         if (jstack >= 0) {
/*      */ 
/*      */           
/*  277 */           ir = istack[jstack--];
/*  278 */           l = istack[jstack--]; continue;
/*      */         }  return;
/*  280 */       }  int k = l + ir >> 1;
/*  281 */       SortModified.swap(x, k, l + 1);
/*  282 */       SortModified.swap(y, k, l + 1);
/*  283 */       if (x[l] > x[ir]) {
/*  284 */         SortModified.swap(x, l, ir);
/*  285 */         SortModified.swap(y, l, ir);
/*      */       } 
/*  287 */       if (x[l + 1] > x[ir]) {
/*  288 */         SortModified.swap(x, l + 1, ir);
/*  289 */         SortModified.swap(y, l + 1, ir);
/*      */       } 
/*  291 */       if (x[l] > x[l + 1]) {
/*  292 */         SortModified.swap(x, l, l + 1);
/*  293 */         SortModified.swap(y, l, l + 1);
/*      */       } 
/*  295 */       int i = l + 1;
/*  296 */       int j = ir;
/*  297 */       int a = x[l + 1];
/*  298 */       Object b = y[l + 1];
/*      */       
/*      */       while (true) {
/*  301 */         i++;
/*  302 */         if (x[i] >= a) {
/*      */           do {
/*  304 */             j--;
/*  305 */           } while (x[j] > a);
/*  306 */           if (j < i) {
/*      */             break;
/*      */           }
/*  309 */           SortModified.swap(x, i, j);
/*  310 */           SortModified.swap(y, i, j);
/*      */         } 
/*  312 */       }  x[l + 1] = x[j];
/*  313 */       x[j] = a;
/*  314 */       y[l + 1] = y[j];
/*  315 */       y[j] = b;
/*  316 */       jstack += 2;
/*      */       
/*  318 */       if (jstack >= 64) {
/*  319 */         throw new IllegalStateException("NSTACK too small in SortModified.");
/*      */       }
/*      */       
/*  322 */       if (ir - i + 1 >= j - l) {
/*  323 */         istack[jstack] = ir;
/*  324 */         istack[jstack - 1] = i;
/*  325 */         ir = j - 1; continue;
/*      */       } 
/*  327 */       istack[jstack] = j - 1;
/*  328 */       istack[jstack - 1] = l;
/*  329 */       l = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] sort(float[] x) {
/*  341 */     int[] order = new int[x.length];
/*  342 */     for (int i = 0; i < order.length; i++) {
/*  343 */       order[i] = i;
/*      */     }
/*  345 */     sort(x, order);
/*  346 */     return order;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(float[] x, int[] y) {
/*  356 */     sort(x, y, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(float[] x, int[] y, int n) {
/*  368 */     int jstack = -1;
/*  369 */     int l = 0;
/*  370 */     int[] istack = new int[64];
/*  371 */     int ir = n - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  377 */       while (ir - l < 7) {
/*  378 */         for (int m = l + 1; m <= ir; m++) {
/*  379 */           float f = x[m];
/*  380 */           int i2 = y[m]; int i1;
/*  381 */           for (i1 = m - 1; i1 >= l && 
/*  382 */             x[i1] > f; i1--) {
/*      */ 
/*      */             
/*  385 */             x[i1 + 1] = x[i1];
/*  386 */             y[i1 + 1] = y[i1];
/*      */           } 
/*  388 */           x[i1 + 1] = f;
/*  389 */           y[i1 + 1] = i2;
/*      */         } 
/*  391 */         if (jstack >= 0) {
/*      */ 
/*      */           
/*  394 */           ir = istack[jstack--];
/*  395 */           l = istack[jstack--]; continue;
/*      */         }  return;
/*  397 */       }  int k = l + ir >> 1;
/*  398 */       SortModified.swap(x, k, l + 1);
/*  399 */       SortModified.swap(y, k, l + 1);
/*  400 */       if (x[l] > x[ir]) {
/*  401 */         SortModified.swap(x, l, ir);
/*  402 */         SortModified.swap(y, l, ir);
/*      */       } 
/*  404 */       if (x[l + 1] > x[ir]) {
/*  405 */         SortModified.swap(x, l + 1, ir);
/*  406 */         SortModified.swap(y, l + 1, ir);
/*      */       } 
/*  408 */       if (x[l] > x[l + 1]) {
/*  409 */         SortModified.swap(x, l, l + 1);
/*  410 */         SortModified.swap(y, l, l + 1);
/*      */       } 
/*  412 */       int i = l + 1;
/*  413 */       int j = ir;
/*  414 */       float a = x[l + 1];
/*  415 */       int b = y[l + 1];
/*      */       
/*      */       while (true) {
/*  418 */         i++;
/*  419 */         if (x[i] >= a) {
/*      */           do {
/*  421 */             j--;
/*  422 */           } while (x[j] > a);
/*  423 */           if (j < i) {
/*      */             break;
/*      */           }
/*  426 */           SortModified.swap(x, i, j);
/*  427 */           SortModified.swap(y, i, j);
/*      */         } 
/*  429 */       }  x[l + 1] = x[j];
/*  430 */       x[j] = a;
/*  431 */       y[l + 1] = y[j];
/*  432 */       y[j] = b;
/*  433 */       jstack += 2;
/*      */       
/*  435 */       if (jstack >= 64) {
/*  436 */         throw new IllegalStateException("NSTACK too small in SortModified.");
/*      */       }
/*      */       
/*  439 */       if (ir - i + 1 >= j - l) {
/*  440 */         istack[jstack] = ir;
/*  441 */         istack[jstack - 1] = i;
/*  442 */         ir = j - 1; continue;
/*      */       } 
/*  444 */       istack[jstack] = j - 1;
/*  445 */       istack[jstack - 1] = l;
/*  446 */       l = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(float[] x, float[] y) {
/*  459 */     sort(x, y, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(float[] x, float[] y, int n) {
/*  471 */     int jstack = -1;
/*  472 */     int l = 0;
/*  473 */     int[] istack = new int[64];
/*  474 */     int ir = n - 1;
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  479 */       while (ir - l < 7) {
/*  480 */         for (int m = l + 1; m <= ir; m++) {
/*  481 */           float f1 = x[m];
/*  482 */           float f2 = y[m]; int i1;
/*  483 */           for (i1 = m - 1; i1 >= l && 
/*  484 */             x[i1] > f1; i1--) {
/*      */ 
/*      */             
/*  487 */             x[i1 + 1] = x[i1];
/*  488 */             y[i1 + 1] = y[i1];
/*      */           } 
/*  490 */           x[i1 + 1] = f1;
/*  491 */           y[i1 + 1] = f2;
/*      */         } 
/*  493 */         if (jstack >= 0) {
/*      */ 
/*      */           
/*  496 */           ir = istack[jstack--];
/*  497 */           l = istack[jstack--]; continue;
/*      */         }  return;
/*  499 */       }  int k = l + ir >> 1;
/*  500 */       SortModified.swap(x, k, l + 1);
/*  501 */       SortModified.swap(y, k, l + 1);
/*  502 */       if (x[l] > x[ir]) {
/*  503 */         SortModified.swap(x, l, ir);
/*  504 */         SortModified.swap(y, l, ir);
/*      */       } 
/*  506 */       if (x[l + 1] > x[ir]) {
/*  507 */         SortModified.swap(x, l + 1, ir);
/*  508 */         SortModified.swap(y, l + 1, ir);
/*      */       } 
/*  510 */       if (x[l] > x[l + 1]) {
/*  511 */         SortModified.swap(x, l, l + 1);
/*  512 */         SortModified.swap(y, l, l + 1);
/*      */       } 
/*  514 */       int i = l + 1;
/*  515 */       int j = ir;
/*  516 */       float a = x[l + 1];
/*  517 */       float b = y[l + 1];
/*      */       
/*      */       while (true) {
/*  520 */         i++;
/*  521 */         if (x[i] >= a) {
/*      */           do {
/*  523 */             j--;
/*  524 */           } while (x[j] > a);
/*  525 */           if (j < i) {
/*      */             break;
/*      */           }
/*  528 */           SortModified.swap(x, i, j);
/*  529 */           SortModified.swap(y, i, j);
/*      */         } 
/*  531 */       }  x[l + 1] = x[j];
/*  532 */       x[j] = a;
/*  533 */       y[l + 1] = y[j];
/*  534 */       y[j] = b;
/*  535 */       jstack += 2;
/*      */       
/*  537 */       if (jstack >= 64) {
/*  538 */         throw new IllegalStateException("NSTACK too small in SortModified.");
/*      */       }
/*      */       
/*  541 */       if (ir - i + 1 >= j - l) {
/*  542 */         istack[jstack] = ir;
/*  543 */         istack[jstack - 1] = i;
/*  544 */         ir = j - 1; continue;
/*      */       } 
/*  546 */       istack[jstack] = j - 1;
/*  547 */       istack[jstack - 1] = l;
/*  548 */       l = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(float[] x, Object[] y) {
/*  561 */     sort(x, y, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(float[] x, Object[] y, int n) {
/*  573 */     int jstack = -1;
/*  574 */     int l = 0;
/*  575 */     int[] istack = new int[64];
/*  576 */     int ir = n - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  582 */       while (ir - l < 7) {
/*  583 */         for (int m = l + 1; m <= ir; m++) {
/*  584 */           float f = x[m];
/*  585 */           Object object = y[m]; int i1;
/*  586 */           for (i1 = m - 1; i1 >= l && 
/*  587 */             x[i1] > f; i1--) {
/*      */ 
/*      */             
/*  590 */             x[i1 + 1] = x[i1];
/*  591 */             y[i1 + 1] = y[i1];
/*      */           } 
/*  593 */           x[i1 + 1] = f;
/*  594 */           y[i1 + 1] = object;
/*      */         } 
/*  596 */         if (jstack >= 0) {
/*      */ 
/*      */           
/*  599 */           ir = istack[jstack--];
/*  600 */           l = istack[jstack--]; continue;
/*      */         }  return;
/*  602 */       }  int k = l + ir >> 1;
/*  603 */       SortModified.swap(x, k, l + 1);
/*  604 */       SortModified.swap(y, k, l + 1);
/*  605 */       if (x[l] > x[ir]) {
/*  606 */         SortModified.swap(x, l, ir);
/*  607 */         SortModified.swap(y, l, ir);
/*      */       } 
/*  609 */       if (x[l + 1] > x[ir]) {
/*  610 */         SortModified.swap(x, l + 1, ir);
/*  611 */         SortModified.swap(y, l + 1, ir);
/*      */       } 
/*  613 */       if (x[l] > x[l + 1]) {
/*  614 */         SortModified.swap(x, l, l + 1);
/*  615 */         SortModified.swap(y, l, l + 1);
/*      */       } 
/*  617 */       int i = l + 1;
/*  618 */       int j = ir;
/*  619 */       float a = x[l + 1];
/*  620 */       Object b = y[l + 1];
/*      */       
/*      */       while (true) {
/*  623 */         i++;
/*  624 */         if (x[i] >= a) {
/*      */           do {
/*  626 */             j--;
/*  627 */           } while (x[j] > a);
/*  628 */           if (j < i) {
/*      */             break;
/*      */           }
/*  631 */           SortModified.swap(x, i, j);
/*  632 */           SortModified.swap(y, i, j);
/*      */         } 
/*  634 */       }  x[l + 1] = x[j];
/*  635 */       x[j] = a;
/*  636 */       y[l + 1] = y[j];
/*  637 */       y[j] = b;
/*  638 */       jstack += 2;
/*      */       
/*  640 */       if (jstack >= 64) {
/*  641 */         throw new IllegalStateException("NSTACK too small in SortModified.");
/*      */       }
/*      */       
/*  644 */       if (ir - i + 1 >= j - l) {
/*  645 */         istack[jstack] = ir;
/*  646 */         istack[jstack - 1] = i;
/*  647 */         ir = j - 1; continue;
/*      */       } 
/*  649 */       istack[jstack] = j - 1;
/*  650 */       istack[jstack - 1] = l;
/*  651 */       l = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] sort(double[] x) {
/*  663 */     int[] order = new int[x.length];
/*  664 */     for (int i = 0; i < order.length; i++) {
/*  665 */       order[i] = i;
/*      */     }
/*  667 */     sort(x, order);
/*  668 */     return order;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(double[] x, int[] y) {
/*  678 */     sort(x, y, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(double[] x, int[] y, int n) {
/*  690 */     int jstack = -1;
/*  691 */     int l = 0;
/*  692 */     int[] istack = new int[64];
/*  693 */     int ir = n - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  699 */       while (ir - l < 7) {
/*  700 */         for (int m = l + 1; m <= ir; m++) {
/*  701 */           double d = x[m];
/*  702 */           int i2 = y[m]; int i1;
/*  703 */           for (i1 = m - 1; i1 >= l && 
/*  704 */             x[i1] > d; i1--) {
/*      */ 
/*      */             
/*  707 */             x[i1 + 1] = x[i1];
/*  708 */             y[i1 + 1] = y[i1];
/*      */           } 
/*  710 */           x[i1 + 1] = d;
/*  711 */           y[i1 + 1] = i2;
/*      */         } 
/*  713 */         if (jstack >= 0) {
/*      */ 
/*      */           
/*  716 */           ir = istack[jstack--];
/*  717 */           l = istack[jstack--]; continue;
/*      */         }  return;
/*  719 */       }  int k = l + ir >> 1;
/*  720 */       SortModified.swap(x, k, l + 1);
/*  721 */       SortModified.swap(y, k, l + 1);
/*  722 */       if (x[l] > x[ir]) {
/*  723 */         SortModified.swap(x, l, ir);
/*  724 */         SortModified.swap(y, l, ir);
/*      */       } 
/*  726 */       if (x[l + 1] > x[ir]) {
/*  727 */         SortModified.swap(x, l + 1, ir);
/*  728 */         SortModified.swap(y, l + 1, ir);
/*      */       } 
/*  730 */       if (x[l] > x[l + 1]) {
/*  731 */         SortModified.swap(x, l, l + 1);
/*  732 */         SortModified.swap(y, l, l + 1);
/*      */       } 
/*  734 */       int i = l + 1;
/*  735 */       int j = ir;
/*  736 */       double a = x[l + 1];
/*  737 */       int b = y[l + 1];
/*      */       
/*      */       while (true) {
/*  740 */         i++;
/*  741 */         if (x[i] >= a) {
/*      */           do {
/*  743 */             j--;
/*  744 */           } while (x[j] > a);
/*  745 */           if (j < i) {
/*      */             break;
/*      */           }
/*  748 */           SortModified.swap(x, i, j);
/*  749 */           SortModified.swap(y, i, j);
/*      */         } 
/*  751 */       }  x[l + 1] = x[j];
/*  752 */       x[j] = a;
/*  753 */       y[l + 1] = y[j];
/*  754 */       y[j] = b;
/*  755 */       jstack += 2;
/*      */       
/*  757 */       if (jstack >= 64) {
/*  758 */         throw new IllegalStateException("NSTACK too small in SortModified.");
/*      */       }
/*      */       
/*  761 */       if (ir - i + 1 >= j - l) {
/*  762 */         istack[jstack] = ir;
/*  763 */         istack[jstack - 1] = i;
/*  764 */         ir = j - 1; continue;
/*      */       } 
/*  766 */       istack[jstack] = j - 1;
/*  767 */       istack[jstack - 1] = l;
/*  768 */       l = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(double[] x, double[] y) {
/*  782 */     sort(x, y, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(double[] x, double[] y, int n) {
/*  794 */     int jstack = -1;
/*  795 */     int l = 0;
/*  796 */     int[] istack = new int[64];
/*  797 */     int ir = n - 1;
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  802 */       while (ir - l < 7) {
/*  803 */         for (int m = l + 1; m <= ir; m++) {
/*  804 */           double d1 = x[m];
/*  805 */           double d2 = y[m]; int i1;
/*  806 */           for (i1 = m - 1; i1 >= l && 
/*  807 */             x[i1] > d1; i1--) {
/*      */ 
/*      */             
/*  810 */             x[i1 + 1] = x[i1];
/*  811 */             y[i1 + 1] = y[i1];
/*      */           } 
/*  813 */           x[i1 + 1] = d1;
/*  814 */           y[i1 + 1] = d2;
/*      */         } 
/*  816 */         if (jstack >= 0) {
/*      */ 
/*      */           
/*  819 */           ir = istack[jstack--];
/*  820 */           l = istack[jstack--]; continue;
/*      */         }  return;
/*  822 */       }  int k = l + ir >> 1;
/*  823 */       SortModified.swap(x, k, l + 1);
/*  824 */       SortModified.swap(y, k, l + 1);
/*  825 */       if (x[l] > x[ir]) {
/*  826 */         SortModified.swap(x, l, ir);
/*  827 */         SortModified.swap(y, l, ir);
/*      */       } 
/*  829 */       if (x[l + 1] > x[ir]) {
/*  830 */         SortModified.swap(x, l + 1, ir);
/*  831 */         SortModified.swap(y, l + 1, ir);
/*      */       } 
/*  833 */       if (x[l] > x[l + 1]) {
/*  834 */         SortModified.swap(x, l, l + 1);
/*  835 */         SortModified.swap(y, l, l + 1);
/*      */       } 
/*  837 */       int i = l + 1;
/*  838 */       int j = ir;
/*  839 */       double a = x[l + 1];
/*  840 */       double b = y[l + 1];
/*      */       
/*      */       while (true) {
/*  843 */         i++;
/*  844 */         if (x[i] >= a) {
/*      */           do {
/*  846 */             j--;
/*  847 */           } while (x[j] > a);
/*  848 */           if (j < i) {
/*      */             break;
/*      */           }
/*  851 */           SortModified.swap(x, i, j);
/*  852 */           SortModified.swap(y, i, j);
/*      */         } 
/*  854 */       }  x[l + 1] = x[j];
/*  855 */       x[j] = a;
/*  856 */       y[l + 1] = y[j];
/*  857 */       y[j] = b;
/*  858 */       jstack += 2;
/*      */       
/*  860 */       if (jstack >= 64) {
/*  861 */         throw new IllegalStateException("NSTACK too small in SortModified.");
/*      */       }
/*      */       
/*  864 */       if (ir - i + 1 >= j - l) {
/*  865 */         istack[jstack] = ir;
/*  866 */         istack[jstack - 1] = i;
/*  867 */         ir = j - 1; continue;
/*      */       } 
/*  869 */       istack[jstack] = j - 1;
/*  870 */       istack[jstack - 1] = l;
/*  871 */       l = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(double[] x, Object[] y) {
/*  884 */     sort(x, y, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sort(double[] x, Object[] y, int n) {
/*  896 */     int jstack = -1;
/*  897 */     int l = 0;
/*  898 */     int[] istack = new int[64];
/*  899 */     int ir = n - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/*  905 */       while (ir - l < 7) {
/*  906 */         for (int m = l + 1; m <= ir; m++) {
/*  907 */           double d = x[m];
/*  908 */           Object object = y[m]; int i1;
/*  909 */           for (i1 = m - 1; i1 >= l && 
/*  910 */             x[i1] > d; i1--) {
/*      */ 
/*      */             
/*  913 */             x[i1 + 1] = x[i1];
/*  914 */             y[i1 + 1] = y[i1];
/*      */           } 
/*  916 */           x[i1 + 1] = d;
/*  917 */           y[i1 + 1] = object;
/*      */         } 
/*  919 */         if (jstack >= 0) {
/*      */ 
/*      */           
/*  922 */           ir = istack[jstack--];
/*  923 */           l = istack[jstack--]; continue;
/*      */         }  return;
/*  925 */       }  int k = l + ir >> 1;
/*  926 */       SortModified.swap(x, k, l + 1);
/*  927 */       SortModified.swap(y, k, l + 1);
/*  928 */       if (x[l] > x[ir]) {
/*  929 */         SortModified.swap(x, l, ir);
/*  930 */         SortModified.swap(y, l, ir);
/*      */       } 
/*  932 */       if (x[l + 1] > x[ir]) {
/*  933 */         SortModified.swap(x, l + 1, ir);
/*  934 */         SortModified.swap(y, l + 1, ir);
/*      */       } 
/*  936 */       if (x[l] > x[l + 1]) {
/*  937 */         SortModified.swap(x, l, l + 1);
/*  938 */         SortModified.swap(y, l, l + 1);
/*      */       } 
/*  940 */       int i = l + 1;
/*  941 */       int j = ir;
/*  942 */       double a = x[l + 1];
/*  943 */       Object b = y[l + 1];
/*      */       
/*      */       while (true) {
/*  946 */         i++;
/*  947 */         if (x[i] >= a) {
/*      */           do {
/*  949 */             j--;
/*  950 */           } while (x[j] > a);
/*  951 */           if (j < i) {
/*      */             break;
/*      */           }
/*  954 */           SortModified.swap(x, i, j);
/*  955 */           SortModified.swap(y, i, j);
/*      */         } 
/*  957 */       }  x[l + 1] = x[j];
/*  958 */       x[j] = a;
/*  959 */       y[l + 1] = y[j];
/*  960 */       y[j] = b;
/*  961 */       jstack += 2;
/*      */       
/*  963 */       if (jstack >= 64) {
/*  964 */         throw new IllegalStateException("NSTACK too small in SortModified.");
/*      */       }
/*      */       
/*  967 */       if (ir - i + 1 >= j - l) {
/*  968 */         istack[jstack] = ir;
/*  969 */         istack[jstack - 1] = i;
/*  970 */         ir = j - 1; continue;
/*      */       } 
/*  972 */       istack[jstack] = j - 1;
/*  973 */       istack[jstack - 1] = l;
/*  974 */       l = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Comparable<? super T>> int[] sort(Comparable[] x) {
/*  987 */     int[] order = new int[x.length];
/*  988 */     for (int i = 0; i < order.length; i++) {
/*  989 */       order[i] = i;
/*      */     }
/*  991 */     sort(x, order);
/*  992 */     return order;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Comparable<? super T>> void sort(Comparable[] x, int[] y) {
/* 1003 */     sort(x, y, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Comparable<? super T>> void sort(Comparable[] x, int[] y, int n) {
/* 1016 */     int jstack = -1;
/* 1017 */     int l = 0;
/* 1018 */     int[] istack = new int[64];
/* 1019 */     int ir = n - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1025 */       while (ir - l < 7) {
/* 1026 */         for (int m = l + 1; m <= ir; m++) {
/* 1027 */           Comparable comparable1 = x[m];
/* 1028 */           int i2 = y[m]; int i1;
/* 1029 */           for (i1 = m - 1; i1 >= l && 
/* 1030 */             x[i1].compareTo(comparable1) > 0; i1--) {
/*      */ 
/*      */             
/* 1033 */             x[i1 + 1] = x[i1];
/* 1034 */             y[i1 + 1] = y[i1];
/*      */           } 
/* 1036 */           x[i1 + 1] = comparable1;
/* 1037 */           y[i1 + 1] = i2;
/*      */         } 
/* 1039 */         if (jstack >= 0) {
/*      */ 
/*      */           
/* 1042 */           ir = istack[jstack--];
/* 1043 */           l = istack[jstack--]; continue;
/*      */         }  return;
/* 1045 */       }  int k = l + ir >> 1;
/* 1046 */       SortModified.swap((Object[])x, k, l + 1);
/* 1047 */       SortModified.swap(y, k, l + 1);
/* 1048 */       if (x[l].compareTo(x[ir]) > 0) {
/* 1049 */         SortModified.swap((Object[])x, l, ir);
/* 1050 */         SortModified.swap(y, l, ir);
/*      */       } 
/* 1052 */       if (x[l + 1].compareTo(x[ir]) > 0) {
/* 1053 */         SortModified.swap((Object[])x, l + 1, ir);
/* 1054 */         SortModified.swap(y, l + 1, ir);
/*      */       } 
/* 1056 */       if (x[l].compareTo(x[l + 1]) > 0) {
/* 1057 */         SortModified.swap((Object[])x, l, l + 1);
/* 1058 */         SortModified.swap(y, l, l + 1);
/*      */       } 
/* 1060 */       int i = l + 1;
/* 1061 */       int j = ir;
/* 1062 */       Comparable comparable = x[l + 1];
/* 1063 */       int b = y[l + 1];
/*      */       
/*      */       while (true) {
/* 1066 */         i++;
/* 1067 */         if (x[i].compareTo(comparable) >= 0) {
/*      */           do {
/* 1069 */             j--;
/* 1070 */           } while (x[j].compareTo(comparable) > 0);
/* 1071 */           if (j < i) {
/*      */             break;
/*      */           }
/* 1074 */           SortModified.swap((Object[])x, i, j);
/* 1075 */           SortModified.swap(y, i, j);
/*      */         } 
/* 1077 */       }  x[l + 1] = x[j];
/* 1078 */       x[j] = comparable;
/* 1079 */       y[l + 1] = y[j];
/* 1080 */       y[j] = b;
/* 1081 */       jstack += 2;
/*      */       
/* 1083 */       if (jstack >= 64) {
/* 1084 */         throw new IllegalStateException("NSTACK too small in SortModified.");
/*      */       }
/*      */       
/* 1087 */       if (ir - i + 1 >= j - l) {
/* 1088 */         istack[jstack] = ir;
/* 1089 */         istack[jstack - 1] = i;
/* 1090 */         ir = j - 1; continue;
/*      */       } 
/* 1092 */       istack[jstack] = j - 1;
/* 1093 */       istack[jstack - 1] = l;
/* 1094 */       l = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> void sort(Object[] x, int[] y, int n, Comparator<T> comparator) {
/* 1111 */     int jstack = -1;
/* 1112 */     int l = 0;
/* 1113 */     int[] istack = new int[64];
/* 1114 */     int ir = n - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1120 */       while (ir - l < 7) {
/* 1121 */         for (int m = l + 1; m <= ir; m++) {
/* 1122 */           T t = (T)x[m];
/* 1123 */           int i2 = y[m]; int i1;
/* 1124 */           for (i1 = m - 1; i1 >= l && 
/* 1125 */             comparator.compare((T)x[i1], t) > 0; i1--) {
/*      */ 
/*      */             
/* 1128 */             x[i1 + 1] = x[i1];
/* 1129 */             y[i1 + 1] = y[i1];
/*      */           } 
/* 1131 */           x[i1 + 1] = t;
/* 1132 */           y[i1 + 1] = i2;
/*      */         } 
/* 1134 */         if (jstack >= 0) {
/*      */ 
/*      */           
/* 1137 */           ir = istack[jstack--];
/* 1138 */           l = istack[jstack--]; continue;
/*      */         }  return;
/* 1140 */       }  int k = l + ir >> 1;
/* 1141 */       SortModified.swap(x, k, l + 1);
/* 1142 */       SortModified.swap(y, k, l + 1);
/* 1143 */       if (comparator.compare((T)x[l], (T)x[ir]) > 0) {
/* 1144 */         SortModified.swap(x, l, ir);
/* 1145 */         SortModified.swap(y, l, ir);
/*      */       } 
/* 1147 */       if (comparator.compare((T)x[l + 1], (T)x[ir]) > 0) {
/* 1148 */         SortModified.swap(x, l + 1, ir);
/* 1149 */         SortModified.swap(y, l + 1, ir);
/*      */       } 
/* 1151 */       if (comparator.compare((T)x[l], (T)x[l + 1]) > 0) {
/* 1152 */         SortModified.swap(x, l, l + 1);
/* 1153 */         SortModified.swap(y, l, l + 1);
/*      */       } 
/* 1155 */       int i = l + 1;
/* 1156 */       int j = ir;
/* 1157 */       T a = (T)x[l + 1];
/* 1158 */       int b = y[l + 1];
/*      */       
/*      */       while (true) {
/* 1161 */         i++;
/* 1162 */         if (comparator.compare((T)x[i], a) >= 0) {
/*      */           do {
/* 1164 */             j--;
/* 1165 */           } while (comparator.compare((T)x[j], a) > 0);
/* 1166 */           if (j < i) {
/*      */             break;
/*      */           }
/* 1169 */           SortModified.swap(x, i, j);
/* 1170 */           SortModified.swap(y, i, j);
/*      */         } 
/* 1172 */       }  x[l + 1] = x[j];
/* 1173 */       x[j] = a;
/* 1174 */       y[l + 1] = y[j];
/* 1175 */       y[j] = b;
/* 1176 */       jstack += 2;
/*      */       
/* 1178 */       if (jstack >= 64) {
/* 1179 */         throw new IllegalStateException("NSTACK too small in SortModified.");
/*      */       }
/*      */       
/* 1182 */       if (ir - i + 1 >= j - l) {
/* 1183 */         istack[jstack] = ir;
/* 1184 */         istack[jstack - 1] = i;
/* 1185 */         ir = j - 1; continue;
/*      */       } 
/* 1187 */       istack[jstack] = j - 1;
/* 1188 */       istack[jstack - 1] = l;
/* 1189 */       l = i;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Comparable<? super T>> void sort(Comparable[] x, Object[] y) {
/* 1203 */     sort(x, y, x.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Comparable<? super T>> void sort(Comparable[] x, Object[] y, int n) {
/* 1216 */     int jstack = -1;
/* 1217 */     int l = 0;
/* 1218 */     int[] istack = new int[64];
/* 1219 */     int ir = n - 1;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     while (true) {
/* 1225 */       while (ir - l < 7) {
/* 1226 */         for (int m = l + 1; m <= ir; m++) {
/* 1227 */           Comparable comparable1 = x[m];
/* 1228 */           Object object = y[m]; int i1;
/* 1229 */           for (i1 = m - 1; i1 >= l && 
/* 1230 */             x[i1].compareTo(comparable1) > 0; i1--) {
/*      */ 
/*      */             
/* 1233 */             x[i1 + 1] = x[i1];
/* 1234 */             y[i1 + 1] = y[i1];
/*      */           } 
/* 1236 */           x[i1 + 1] = comparable1;
/* 1237 */           y[i1 + 1] = object;
/*      */         } 
/* 1239 */         if (jstack >= 0) {
/*      */ 
/*      */           
/* 1242 */           ir = istack[jstack--];
/* 1243 */           l = istack[jstack--]; continue;
/*      */         }  return;
/* 1245 */       }  int k = l + ir >> 1;
/* 1246 */       SortModified.swap((Object[])x, k, l + 1);
/* 1247 */       SortModified.swap(y, k, l + 1);
/* 1248 */       if (x[l].compareTo(x[ir]) > 0) {
/* 1249 */         SortModified.swap((Object[])x, l, ir);
/* 1250 */         SortModified.swap(y, l, ir);
/*      */       } 
/* 1252 */       if (x[l + 1].compareTo(x[ir]) > 0) {
/* 1253 */         SortModified.swap((Object[])x, l + 1, ir);
/* 1254 */         SortModified.swap(y, l + 1, ir);
/*      */       } 
/* 1256 */       if (x[l].compareTo(x[l + 1]) > 0) {
/* 1257 */         SortModified.swap((Object[])x, l, l + 1);
/* 1258 */         SortModified.swap(y, l, l + 1);
/*      */       } 
/* 1260 */       int i = l + 1;
/* 1261 */       int j = ir;
/* 1262 */       Comparable comparable = x[l + 1];
/* 1263 */       Object b = y[l + 1];
/*      */       
/*      */       while (true) {
/* 1266 */         i++;
/* 1267 */         if (x[i].compareTo(comparable) >= 0) {
/*      */           do {
/* 1269 */             j--;
/* 1270 */           } while (x[j].compareTo(comparable) > 0);
/* 1271 */           if (j < i) {
/*      */             break;
/*      */           }
/* 1274 */           SortModified.swap((Object[])x, i, j);
/* 1275 */           SortModified.swap(y, i, j);
/*      */         } 
/* 1277 */       }  x[l + 1] = x[j];
/* 1278 */       x[j] = comparable;
/* 1279 */       y[l + 1] = y[j];
/* 1280 */       y[j] = b;
/* 1281 */       jstack += 2;
/*      */       
/* 1283 */       if (jstack >= 64) {
/* 1284 */         throw new IllegalStateException("NSTACK too small in SortModified.");
/*      */       }
/*      */       
/* 1287 */       if (ir - i + 1 >= j - l) {
/* 1288 */         istack[jstack] = ir;
/* 1289 */         istack[jstack - 1] = i;
/* 1290 */         ir = j - 1; continue;
/*      */       } 
/* 1292 */       istack[jstack] = j - 1;
/* 1293 */       istack[jstack - 1] = l;
/* 1294 */       l = i;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/QuickSortModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */