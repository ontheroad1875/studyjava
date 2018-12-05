package thread;
/**
 * 
 * @Title ExtendThreadDemo
 * <p>
 * 描述：线程，两种方式，一种是继承Thread,另外一种是实现Runnable接口或者实现Callable接口
 * <p>
 * @author huluy
 * @date 2018年10月10日 下午3:58:22
 * @Version
 */
public class ExtendThreadDemo {
	
	static class ExtendThread extends Thread {
		public void run() {
			System.out.println(Thread.currentThread().getName());
		}
	}
	
	public static void main(String[] args) {
		ExtendThread extendThread = new ExtendThread();
		extendThread.start();
	}
}
