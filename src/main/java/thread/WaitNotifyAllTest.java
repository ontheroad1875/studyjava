package thread;
/**
 * 
 * @Title WaitNotifyAllTest
 * <p>描述：这里需要注意的是由于Condition是由锁创建的，所以调用wait/notifyAll方法的时候需要获得当前线程的锁，
 * 否则会发生IllegalMonitorStateException异常。<p>
 * wait/notify等方法也依赖于monitor对象，
 * 这就是为什么只有在同步的块或者方法中才能调用wait/notify等方法，否则会抛出java.lang.IllegalMonitorStateException的异常的原因。
 * @author huluy
 * @date 2018年11月4日 下午7:50:17
 * @Version
 */
public class WaitNotifyAllTest {
	 
    public synchronized void doWait() {
        System.out.println(Thread.currentThread().getName() + " run");
        System.out.println(Thread.currentThread().getName() + " wait for condition");
        try {
            this.wait();
            System.out.println(Thread.currentThread().getName() + " continue");
        } catch (InterruptedException e) {
            System.err.println(Thread.currentThread().getName() + " interrupted");
            Thread.currentThread().interrupt();
        }
    }
 
    public synchronized void doNotify() {
        try {
            System.out.println(Thread.currentThread().getName() + " run");
            System.out.println(Thread.currentThread().getName() + " sleep 5 secs");
            Thread.sleep(1000l);
            this.notifyAll();
        } catch (InterruptedException e) {
            System.err.println(Thread.currentThread().getName() + " interrupted");
            Thread.currentThread().interrupt();
        }
    }
 
    public static void main(String[] args) {
        WaitNotifyAllTest waitNotifyAllTest = new WaitNotifyAllTest();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyAllTest.doWait();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyAllTest.doNotify();
            }
        });
        thread1.start();
        thread2.start();
    }
 
}