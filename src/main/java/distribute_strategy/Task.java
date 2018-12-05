package distribute_strategy;

public class Task {

    private static final int READY = 0;				//就绪
    private static final int RUNNING = 1;			//执行中
    private static final int FINISHED = 2;			//结束

    // 执行任务的状态
    private int status;
    // 用于标识任务
    private int taskId;

    public Task(int taskId) {
        this.status = READY;
        this.taskId = taskId;
    }

    public void execute() {
        // 设置当前线程为执行状态
        setStatus(Task.RUNNING);
        System.out.println("线程ID:" + Thread.currentThread().getName() + ", 任务ID:" + this.taskId);

        // 附加延时
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 执行结束
        setStatus(Task.FINISHED);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTaskId() {
        return taskId;
    }

}
