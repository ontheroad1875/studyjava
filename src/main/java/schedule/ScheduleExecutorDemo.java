package schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @Title ScheduleExecutorDemo
 * <p>
 * 描述：基于线程池设计的 ScheduledExecutor:每一个被调度的任务都会由线程池中一个线程去执行，因此任务是并发执行的，相互之间不会受到干扰
 * 需要注意的是，只有当任务的执行时间到来时，ScheduedExecutor 才会真正启动一个线程，其余时间 ScheduledExecutor 都是在轮询任务的状态。
 * <p>
 * @author huluy
 * @date 2018年10月10日 下午5:25:07
 * @Version
 */
public class ScheduleExecutorDemo {
	public static void main(String[] args) {
//		ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
//		long initialDelay1 = 1;
//        long period1 = 1;
//		service.scheduleAtFixedRate(new ScheduleExecutor("job1"), initialDelay1, period1, TimeUnit.SECONDS);
//		long initialDelay2 = 1;
//        long period2 = 1;
//		service.scheduleWithFixedDelay(new ScheduleExecutor("job2"), initialDelay2, period2, TimeUnit.SECONDS);
		
		//scheduleAtFixedRate();
		scheduleWithFixedDelay();
	}
	/**
	 * 
	 * @Title scheduleAtFixedRate
	 * @Description 以固定频率来执行线程任务，固定频率的含义就是可能设定的固定时间不足以完成线程任务，但是它不管，达到设定的延迟时间了就要执行下一次了。
	 * scheduleAtFixedRate 延迟线程执行的时间或者delay的时间，谁长取谁
	 */
	public static void scheduleAtFixedRate() {
		// 声明线程池
		final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		// 响铃线程
		final Runnable beeper = new Runnable() {
			public void run() {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//long time = (long) (Math.random() * 1000);
				long time = (long) (Math.random() * 10000);
				// 输出线程的名字和使用目标对象及休眠的时间
				System.out.println(sf.format(new Date()) + " 线程：" + Thread.currentThread().getName() + ":Sleeping " + time + "ms");
				try {
					Thread.sleep(time);		//一旦延时时间突破了设定的时间频率，下面的执行频率将不在起作用
				} catch (InterruptedException e) {
				}
			}
		};
		// 计划响铃，初始延迟10s然后以10s的频率执行响铃
		//final ScheduledFuture<?> beeperHandle = scheduledExecutorService.scheduleAtFixedRate(beeper, 10, 10, TimeUnit.SECONDS);
		final ScheduledFuture<?> beeperHandle = scheduledExecutorService.scheduleAtFixedRate(beeper, 2, 2, TimeUnit.SECONDS);
		// 取消响铃并关闭线程
		final Runnable cancelBeeper = new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + "CANCEL...");
				beeperHandle.cancel(true);
				scheduledExecutorService.shutdown();
			}
		};
		// 60s后执行scheduleAtFixedRate
		scheduledExecutorService.schedule(cancelBeeper, 40, TimeUnit.SECONDS);
	}
	/**
	 * 
	 * @Title scheduleWithFixedDelay
	 * @Description 可以理解为就是以固定延迟（时间）来执行线程任务，它实际上是不管线程任务的执行时间的，每次都要把任务执行完成后再延迟固定时间后再执行下一次
	 * @param  
	 * @return void
	 */
	public static void scheduleWithFixedDelay() {
		 
		final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
		// 响铃线程
		final Runnable beeper = new Runnable() {
			public void run() {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long time = (long) (Math.random() * 1000);
				// 输出线程的名字和使用目标对象及休眠的时间
				System.out.println(sf.format(new Date())+"线程:"+Thread.currentThread().getName()+":Sleeping"+time+"ms");
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
				}
				}
			};
			// 设定执行线程计划,初始10s延迟,每次任务完成后延迟10s再执行一次任务
			final ScheduledFuture<?> sFuture=scheduledExecutorService.scheduleWithFixedDelay(beeper,10,10,TimeUnit.SECONDS);
	 
			// 40s后取消线程任务
			scheduledExecutorService.schedule(new Runnable() {
				public void run() {
					sFuture.cancel(true);
					scheduledExecutorService.shutdown();
				}
			}, 40, TimeUnit.SECONDS);
	 
		}

}

class ScheduleExecutor implements Runnable {
	private String jobName;
	public ScheduleExecutor(String jobName) {
		this.jobName = jobName;
	}
	@Override
	public void run() {
		System.out.println("execute " + jobName);
	}
	
}
