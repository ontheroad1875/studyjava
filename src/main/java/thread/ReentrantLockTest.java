package thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @Title ReentrantLockTest
 *        <p>
 * 		描述：java
 *        提供了很多的同步操作，比如synchronized，wait/nofityAll,ReetrantLock,Condition,还有一些并发下的工具包
 *        <p>
 * @author huluy
 * @date 2018年11月4日 下午4:33:07
 * @Version
 */
public class ReentrantLockTest {
	// ReentrantLock可以重入锁是jdk内置的一个锁对象，可以实现同步。
	private ReentrantLock lock = new ReentrantLock();

	public void execute() {
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + " do something synchronized");
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				System.err.println(Thread.currentThread().getName() + " interrupted");
				Thread.currentThread().interrupt();
			}
		} finally {
			lock.unlock();
		}

	}

	//synchronized关键跟ReentrantLock一样，也支持可重入锁。但是它是一个关键字，是一种语法级别的同步方式，称为内置锁。
	//synchronized是基于监视器（monitor）实现来实现方法同步和代码块同步。synchronized是基于JVM层面的。
	//使用的是对象内置的锁。静态方法锁住的是该class的监视器，实例方法锁住的是对应实例的监视器。
	//另外 java 还提供了显式监视器( Lock )和隐式监视器( synchronized )两种锁方案
	public static void main(String[] args) {
		ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
		
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					reentrantLockTest.execute();
				}
			}
		});
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				reentrantLockTest.execute();
			}
		});
		thread1.start();
		thread2.start();
	}
}
