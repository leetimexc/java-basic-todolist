package ui;

import model.Task;
import service.TodoService;
import java.util.List;
import java.util.Scanner;

/**
 * ConsoleUI类 - 控制台用户界面
 */
public class ConsoleUI {
    private TodoService todoService;
    private Scanner scanner;

    public ConsoleUI() {
        this.todoService = new TodoService();
        this.scanner = new Scanner(System.in);
    }

    /**
     * 启动应用
     */
    public void start() {
        System.out.println("=================================");
        System.out.println("    欢迎使用 TodoList 应用");
        System.out.println("=================================\n");

        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addTask();
                    break;
                case "2":
                    listAllTasks();
                    break;
                case "3":
                    listIncompleteTasks();
                    break;
                case "4":
                    listCompletedTasks();
                    break;
                case "5":
                    toggleTaskStatus();
                    break;
                case "6":
                    updateTask();
                    break;
                case "7":
                    deleteTask();
                    break;
                case "8":
                    setPriority();
                    break;
                case "9":
                    showStatistics();
                    break;
                case "0":
                    running = false;
                    System.out.println("\n感谢使用，再见！");
                    break;
                default:
                    System.out.println("\n无效的选项，请重新选择！\n");
            }
        }
        scanner.close();
    }

    /**
     * 显示菜单
     */
    private void showMenu() {
        System.out.println("========== 主菜单 ==========");
        System.out.println("1. 添加任务");
        System.out.println("2. 查看所有任务");
        System.out.println("3. 查看未完成任务");
        System.out.println("4. 查看已完成任务");
        System.out.println("5. 切换任务状态");
        System.out.println("6. 更新任务");
        System.out.println("7. 删除任务");
        System.out.println("8. 设置优先级");
        System.out.println("9. 查看统计信息");
        System.out.println("0. 退出");
        System.out.println("===========================");
        System.out.print("请选择操作: ");
    }

    /**
     * 添加任务
     */
    private void addTask() {
        System.out.println("\n--- 添加新任务 ---");
        System.out.print("任务标题: ");
        String title = scanner.nextLine().trim();

        if (title.isEmpty()) {
            System.out.println("任务标题不能为空！\n");
            return;
        }

        System.out.print("任务描述 (可选，直接回车跳过): ");
        String description = scanner.nextLine().trim();

        Task task = todoService.addTask(title, description);
        System.out.println("✓ 任务添加成功！");
        System.out.println(task.toShortString() + "\n");
    }

    /**
     * 列出所有任务
     */
    private void listAllTasks() {
        System.out.println("\n--- 所有任务 ---");
        List<Task> tasks = todoService.getAllTasks();
        displayTasks(tasks);
    }

    /**
     * 列出未完成任务
     */
    private void listIncompleteTasks() {
        System.out.println("\n--- 未完成任务 ---");
        List<Task> tasks = todoService.getIncompleteTasks();
        displayTasks(tasks);
    }

    /**
     * 列出已完成任务
     */
    private void listCompletedTasks() {
        System.out.println("\n--- 已完成任务 ---");
        List<Task> tasks = todoService.getCompletedTasks();
        displayTasks(tasks);
    }

    /**
     * 显示任务列表
     */
    private void displayTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("暂无任务\n");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).toShortString());
        }
        System.out.println();
    }

    /**
     * 切换任务状态
     */
    private void toggleTaskStatus() {
        System.out.println("\n--- 切换任务状态 ---");
        System.out.print("请输入任务ID (前8位): ");
        String id = scanner.nextLine().trim();

        if (todoService.toggleTaskStatus(id)) {
            System.out.println("✓ 任务状态已更新！\n");
        } else {
            System.out.println("✗ 未找到该任务！\n");
        }
    }

    /**
     * 更新任务
     */
    private void updateTask() {
        System.out.println("\n--- 更新任务 ---");
        System.out.print("请输入任务ID (前8位): ");
        String id = scanner.nextLine().trim();

        System.out.print("新标题 (直接回车保持不变): ");
        String title = scanner.nextLine().trim();

        System.out.print("新描述 (直接回车保持不变): ");
        String description = scanner.nextLine().trim();

        if (todoService.updateTask(id, title, description)) {
            System.out.println("✓ 任务更新成功！\n");
        } else {
            System.out.println("✗ 未找到该任务！\n");
        }
    }

    /**
     * 删除任务
     */
    private void deleteTask() {
        System.out.println("\n--- 删除任务 ---");
        System.out.print("请输入任务ID (前8位): ");
        String id = scanner.nextLine().trim();

        System.out.print("确认删除？(y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y") || confirm.equals("yes")) {
            if (todoService.deleteTask(id)) {
                System.out.println("✓ 任务删除成功！\n");
            } else {
                System.out.println("✗ 未找到该任务！\n");
            }
        } else {
            System.out.println("已取消删除\n");
        }
    }

    /**
     * 设置优先级
     */
    private void setPriority() {
        System.out.println("\n--- 设置任务优先级 ---");
        System.out.print("请输入任务ID (前8位): ");
        String id = scanner.nextLine().trim();

        System.out.println("优先级选项:");
        System.out.println("1. 低");
        System.out.println("2. 中");
        System.out.println("3. 高");
        System.out.print("请选择: ");
        String choice = scanner.nextLine().trim();

        Task.Priority priority;
        switch (choice) {
            case "1":
                priority = Task.Priority.LOW;
                break;
            case "2":
                priority = Task.Priority.MEDIUM;
                break;
            case "3":
                priority = Task.Priority.HIGH;
                break;
            default:
                System.out.println("无效的选项！\n");
                return;
        }

        if (todoService.setPriority(id, priority)) {
            System.out.println("✓ 优先级设置成功！");
            // 显示更新后的任务信息
            todoService.findTaskById(id).ifPresent(task -> {
                System.out.println("\n更新后的任务:");
                System.out.println(task.toShortString());
            });
            System.out.println();
        } else {
            System.out.println("✗ 未找到该任务！\n");
        }
    }

    /**
     * 显示统计信息
     */
    private void showStatistics() {
        System.out.println("\n--- 统计信息 ---");
        System.out.println(todoService.getStatistics());
        System.out.println();
    }
}