package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @Title ConditionDemo
 * <p>描述：
 * 条件对象的意义在于对于一个已经获取锁的线程，如果还需要等待其他条件才能够继续执行下去，才会使用Condition对象
 * 这个例子中thread1执行到condition.await()时，当前线程会被挂起，直到thread2调用了condition.signalAll()方法之后，thread1才会重新被激活执行。
 * <p>
 * @author huluy
 * @date 2018年11月4日 下午5:36:13
 * @Version
 */
public class ConditionDemo {
	
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " run");
                    System.out.println(Thread.currentThread().getName() + " wait for condition");
                    try {
                        condition.await();
                        System.out.println(Thread.currentThread().getName() + " continue");
                    } catch (InterruptedException e) {
                        System.err.println(Thread.currentThread().getName() + " interrupted");
                        Thread.currentThread().interrupt();
                    }
                } finally {
                    lock.unlock();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " run");
                    System.out.println(Thread.currentThread().getName() + " sleep 5 secs");
                    try {
                        Thread.sleep(5000l);
                    } catch (InterruptedException e) {
                        System.err.println(Thread.currentThread().getName() + " interrupted");
                        Thread.currentThread().interrupt();
                    }
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        });
        thread1.start();
        thread2.start();

	}
}
