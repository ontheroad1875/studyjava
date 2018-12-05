package distribute_strategy;

import java.util.List;

public class StudentWorkThread extends Thread {

    private List<Task> taskList = null;
    private int threadId;

    public StudentWorkThread(List<Task> taskList, int threadId) {
        this.taskList = taskList;
        this.threadId = threadId;
    }

    // 执行被指派的任务
    public void run() {
        for (Task task : taskList) {
            task.execute();
        }
    }

}
