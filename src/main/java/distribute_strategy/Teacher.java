package distribute_strategy;

import java.util.ArrayList;
import java.util.List;

public class Teacher {

    public static List[] distributeTasks(List taskList, int threadCount) {
        // 每个线程至少执行的任务数
        int minTaskCount = taskList.size() / threadCount;
        // 平均分配后还剩余的任务数
        int remainTaskCount = taskList.size() % threadCount;
        // 实际启动的线程数，如果实际工作的线程大于工作任务数，则启动任务数个线程即可
        int actualThreadCount = minTaskCount > 0 ? threadCount : remainTaskCount;
        // 要启动的线程数组，以及每个线程要执行的任务列表
        List[] perThreadWorkList = new List[actualThreadCount];
        int taskIndex = 0;
        // 任务剩余数重新声明标志，防止在执行过程中改变剩余线程数remainTaskCount的值
        int remainIndces = remainTaskCount;

        for (int i = 0; i < perThreadWorkList.length; i++) {
            perThreadWorkList[i] = new ArrayList();
            // 如果每个线程至少要执行的任务数>0，线程需要分配到基本任务
            if (minTaskCount > 0) {
                for (int j = taskIndex; j < minTaskCount + taskIndex; j++) {
                    perThreadWorkList[i].add(taskList.get(j));
                }
            }
            taskIndex += minTaskCount;

            // 还有剩余的任务，则补充一个到这个线程中
            if (remainIndces > 0) {
                perThreadWorkList[i].add(taskList.get(taskIndex++));
                remainIndces--;
            }
        }

        // 打印任务的分配情况
        for (int i = 0; i < perThreadWorkList.length; i++) {
            System.out.println("线程" + i + "任务数: " + perThreadWorkList[i].size() +
                    ",区间[" + ((Task) perThreadWorkList[i].get(0)).getTaskId() + "," +
                    ((Task) perThreadWorkList[i].get(perThreadWorkList[i].size() - 1)).getTaskId() + "]");
        }

        return perThreadWorkList;
    }
    
}
