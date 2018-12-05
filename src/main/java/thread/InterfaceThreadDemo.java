package thread;

public class InterfaceThreadDemo {
	public static void main(String[] args) {
		InterfaceThread t = new InterfaceThread();
		new Thread(t).start();
	}
	
	static class InterfaceThread implements Runnable {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName());
		}
	}
}
