package thread;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @Title ThreadLocalTest
 * <p>描述：ThreadLocal是一种把变量放到线程本地的方式来实现线程同步的<p>
 * @author huluy
 * @date 2018年11月4日 下午7:51:02
 * @Version
 */
public class ThreadLocalTest {
	 
    private static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
 
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                System.out.println(dateFormatThreadLocal.get().format(date));
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Date date = new Date();
                System.out.println(dateFormatThreadLocal.get().format(date));
            }
        });
        thread1.start();
        thread2.start();
    }
 
}