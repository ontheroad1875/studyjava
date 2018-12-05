package thread;

/**
 * 
 * @Title SynchronizedDemo
 * <p>描述：synchronized关键字修饰方法，由于java的每个对象都有一个内置锁，当用此关键字修饰方法时，内置锁会保护整个方法，在调用该方法钱
 * ，需要获得内置锁，否则就处于阻塞状态。
 * 同步方法默认用this或者当前类class对象作为锁。
 * synchronized关键字修饰的语句块。被该关键字修饰的语句块会自动被加上内置锁，从而实现同步。
 * 同步是一种高开销的操作，因此应该尽量减少同步的内容。 
 * 通常没有必要同步整个方法，使用synchronized代码块同步关键代码即可。
 * 
 * 局限性：加锁的时候不能设置超时。ReentrantLock有提供tryLock方法，可以设置超时时间，如果超过了这个时间并且没有获取到锁，
 * 就会放弃，而synchronized却没有这种功能
 * ReentrantLock可以使用多个Condition，而synchronized却只能有1个
 * 不能中断一个试图获得锁的线程
 * ReentrantLock可以选择公平锁和非公平锁
 * ReentrantLock可以获得正在等待线程的个数，计数器等
 * <p>
 * @author huluy
 * @date 2018年11月4日 下午4:43:27
 * @Version
 */
public class SynchronizedDemo {
	
	public synchronized void execute() {
		System.out.println(Thread.currentThread().getName() + " do something synchronize");
		try {
            anotherLock();
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            System.err.println(Thread.currentThread().getName() + " interrupted");
            Thread.currentThread().interrupt();
        }
	}
	
	public synchronized void anotherLock() {
        System.out.println(Thread.currentThread().getName() + " invoke anotherLock");
    }
	
	public static void main(String[] args) {
		SynchronizedDemo reentrantLockTest = new SynchronizedDemo();
		
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTest.execute();
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
