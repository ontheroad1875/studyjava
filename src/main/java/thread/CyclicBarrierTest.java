package thread;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * @Title CyclicBarrierTest
 * <p>描述：CyclicBarrier阻塞调用的线程，直到条件满足时，阻塞的线程同时被打开。
 * 调用await()方法的时候，这个线程就会被阻塞，当调用await()的线程数量到达屏障数的时候，主线程就会取消所有被阻塞线程的状态。
 * 在CyclicBarrier的构造方法中，还可以设置一个barrierAction。
 * 相比CountDownLatch，CyclicBarrier是可以被循环使用的，而且遇到线程中断等情况时，还可以利用reset()方法，重置计数器，从这些方面来说，CyclicBarrier会比CountDownLatch更加灵活一些。
 * <p>
 * @author huluy
 * @date 2018年11月4日 下午8:05:52
 * @Version
 */
public class CyclicBarrierTest {
	 
    public static void main(String[] args) {
        Random random = new Random();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for(int i = 0; i < 5; i ++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int secs = random.nextInt(5);
                    System.out.println(Thread.currentThread().getName() + " " + new Date() + " run, sleep " + secs + " secs");
                    try {
                        Thread.sleep(secs * 1000);
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " " + new Date() + " runs over");
                }
            }).start();
        }
    }
 
}