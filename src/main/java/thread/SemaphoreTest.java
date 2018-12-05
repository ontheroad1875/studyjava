package thread;

import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * 
 * @Title SemaphoreTest
 * <p>描述：Semaphore信号量被用于控制特定资源在同一个时间被访问的个数。类似连接池的概念，保证资源可以被合理的使用
 * 可以使用构造器初始化资源个数
 * <p>
 * @author huluy
 * @date 2018年11月4日 下午7:52:17
 * @Version
 */
public class SemaphoreTest {
	 
    private static Semaphore semaphore = new Semaphore(2);
 
    public static void main(String[] args) {
        for(int i = 0; i < 5; i ++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName() + " " + new Date());
                        Thread.sleep(5000l);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        System.err.println(Thread.currentThread().getName() + " interrupted");
                    }
                }
            }).start();
        }
    }
 
}