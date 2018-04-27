import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MyThread extends Thread {

    private static Logger log = Logger.getLogger(Main.class.toString());
    private static volatile int count = 1;
    private static final Object lock = new Object();

    private int id;

    private MyThread(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Thread-" + id;
    }

    @Override
    public void run() {
        synchronized (lock) {

            try {
                while (id > count) {
                    lock.wait();
                }
                count++;
                log.info(Thread.currentThread().toString());
                lock.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        List<Thread> list = new ArrayList<>();

        for (int i = count; i <= 10; i++) {
            Thread thread = new MyThread(i);
            list.add(thread);
            thread.start();
        }
    }
}
