import java.util.logging.Logger;

public class SeriesRunExample extends Thread {
    private static Logger log = Logger.getLogger(Main.class.getName());
    private static int currentMax = 1;
    private int mainId;
    private static final Object waitObject = new Object();

    public SeriesRunExample(int id){
        this.mainId = id;
        //this.waitObject = waitObject;
    }

    public static void example(){
        //Object waitObject = new Object();

    }

    public void run(){
        try {
            synchronized (waitObject) {
                while(mainId > currentMax){
                    waitObject.wait();
                }
                currentMax++;
                //System.out.println("Hello from thread: " + mainId);
                //log.info("Hello from thread: " + mainId);
                log.info(Thread.currentThread().getName());
                waitObject.notifyAll();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for(int i = currentMax; i <= 10; i++){
            Thread thread = new SeriesRunExample(i);
            thread.start();
        }
    }

}