package distribute_strategy;

import java.util.ArrayList;
import java.util.List;

public class DoTeacherWork {

    public static void main(String[] args) {
        // 构建老师的任务列表
        List<Task> taskList = new ArrayList<Task>();
        for (int i = 0; i < 3; i++) {
            taskList.add(new Task(i));
        }

        System.out.println("老师的任务列表数：" + taskList.size());

        // 设定学生工作的线程数为5
        int threadNum = 5;

        List[] perThreadWorkList = Teacher.distributeTasks(taskList, threadNum);

        System.out.println("实际要启动的工作线程数：" + perThreadWorkList.length);

        for (int i = 0; i < perThreadWorkList.length; i++) {
            StudentWorkThread studentWorkThread = new StudentWorkThread(perThreadWorkList[i], i);
            studentWorkThread.start();
        }
    }

}
