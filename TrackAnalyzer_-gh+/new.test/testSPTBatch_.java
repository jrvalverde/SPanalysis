public class testSPTBatch_ {
  public static void main(String[] args) {
  /*
    SPTBatch_ sp = new SPTBatch_("data.xml", "imgDir");
    
    System.out.println("Got a new SPTBatch_" + " " + sp.s);
    
    Five f = new Five();
    System.out.println(f.x);
    
    sp.res("RESULT");
    // as a thread
    sp.start();
  */
   /* as Thread * /
    SPTBatch_ sp = new SPTBatch_("xml", "imgdir") ;

    System.out.println("Got a new SPTBatch_" + " " + sp.s);

    sp.start();

    sp.res("RESULT");
   / * */
   /* as Runnable */
   // create reference of runnable to our class
   Runnable sp = new SPTBatch_("xml1", "imgdir1");
   Runnable ps = new SPTBatch_("xml2", "imgdir2");
   
   // create reference of thread class by passing object of
   // Runnable in constructor of the thread class
   Thread th1 = new Thread(sp);
   Thread th2 = new Thread(ps);
   
   th1.start();
   th2.start();
   
   /* */
   
  }
  
}


