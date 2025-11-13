package service;

import model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * TodoService类 - 处理待办事项的业务逻辑
 */
public class TodoService {
    private List<Task> tasks;
    
    public TodoService() {
        this.tasks = new ArrayList<>();
    }

    /**
     * 添加新任务
     */
    public Task addTask(String title, String description) {
        Task task = new Task(title, description);
        tasks.add(task);
        return task;
    }

    /**
     * 根据ID删除任务
     */
    public boolean deleteTask(String id) {
        return tasks.removeIf(task -> task.getId().startsWith(id));
    }

    /**
     * 根据ID查找任务
     */
    public Optional<Task> findTaskById(String id) {
        return tasks.stream()
                .filter(task -> task.getId().startsWith(id))
                .findFirst();
    }

    /**
     * 获取所有任务
     */
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

     /**
     * 获取未完成的任务
     */
    public List<Task> getIncompleteTasks() {
        return tasks.stream()
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }

    /**
     * 获取已完成的任务
     */
    public List<Task> getCompletedTasks() {
        return tasks.stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }

    /**
     * 标记任务为完成
     */
    public boolean completeTask(String id) {
        Optional<Task> task = findTaskById(id);
        if (task.isPresent()) {
            task.get().setCompleted(true);
            return true;
        }
        return false;
    }

     /**
     * 切换任务完成状态
     */
    public boolean toggleTaskStatus(String id) {
        Optional<Task> task = findTaskById(id);
        if (task.isPresent()) {
            task.get().toggleCompleted();
            return true;
        }
        return false;
    }


     /**
     * 更新任务信息
     */
    public boolean updateTask(String id, String newTitle, String newDescription) {
        Optional<Task> task = findTaskById(id);
        if (task.isPresent()) {
            Task t = task.get();
            if (newTitle != null && !newTitle.isEmpty()) {
                t.setTitle(newTitle);
            }
            if (newDescription != null) {
                t.setDescription(newDescription);
            }
            return true;
        }
        return false;
    }

     /**
     * 设置任务优先级
     */
    public boolean setPriority(String id, Task.Priority priority) {
        Optional<Task> task = findTaskById(id);
        if (task.isPresent()) {
            task.get().setPriority(priority);
            return true;
        }
        return false;
    }


    /**
     * 获取任务统计信息
     */
    public String getStatistics() {
        int total = tasks.size();
        int completed = (int) tasks.stream().filter(Task::isCompleted).count();
        int incomplete = total - completed;
        int overdue = (int) tasks.stream().filter(Task::isOverdue).count();

        return String.format("总任务: %d | 已完成: %d | 未完成: %d | 已过期: %d",
                total, completed, incomplete, overdue);
    }

    /**
     * 清空所有任务
     */
    public void clearAllTasks() {
        tasks.clear();
    }

    /**
     * 获取任务数量
     */
    public int getTaskCount() {
        return tasks.size();
    }


}