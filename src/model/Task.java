package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Task类 - 表示一个待办事项
 */
public class Task {
    private String id;              // 任务唯一标识
    private String title;           // 任务标题
    private String description;     // 任务描述
    private boolean completed;      // 完成状态
    private LocalDateTime createdAt; // 创建时间
    private LocalDateTime dueDate;   // 截止日期（可选）
    private Priority priority;       // 优先级

     /**
     * 优先级枚举
     */
    public enum Priority {
        LOW("低"),
        MEDIUM("中"),
        HIGH("高");

        private final String displayName;

        Priority(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

     /**
     * 构造函数 - 创建新任务
     */
    public Task(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
        this.priority = Priority.MEDIUM;
    }

    /**
     * 完整构造函数 - 用于从存储中恢复任务
     */
    public Task(String id, String title, String description, boolean completed,
                LocalDateTime createdAt, LocalDateTime dueDate, Priority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    // Getter和Setter方法
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * 切换任务完成状态
     */
    public void toggleCompleted() {
        this.completed = !this.completed;
    }

    /**
     * 检查任务是否已过期
     */
    public boolean isOverdue() {
        if (dueDate == null || completed) {
            return false;
        }
        return LocalDateTime.now().isAfter(dueDate);
    }


    /**
     * 格式化显示任务信息
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        
        sb.append("[ID: ").append(id.substring(0, 8)).append("...] ");
        sb.append(completed ? "[✓] " : "[ ] ");
        sb.append(title);
        sb.append(" (优先级: ").append(priority.getDisplayName()).append(")");
        
        if (description != null && !description.isEmpty()) {
            sb.append("\n  描述: ").append(description);
        }
        
        sb.append("\n  创建时间: ").append(createdAt.format(formatter));
        
        if (dueDate != null) {
            sb.append("\n  截止时间: ").append(dueDate.format(formatter));
            if (isOverdue()) {
                sb.append(" [已过期]");
            }
        }
        
        return sb.toString();
    }

    /**
     * 简短显示格式
     */
    public String toShortString() {
        return String.format("%s %s - %s [%s]",
                completed ? "[✓]" : "[ ]",
                id.substring(0, 8),
                title,
                priority.getDisplayName());
    }
}