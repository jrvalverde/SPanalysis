
// Class 1
// Helper thread Class extending main Thread Class
class MyThread1 extends Thread {

    // Method inside MyThread2
    // run() method which is called as
    // soon as thread is started
    public void run()
    {

        // Print statement when the thread is called
        System.out.println("Thread1 is running");
    }
}
