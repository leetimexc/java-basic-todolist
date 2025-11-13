package dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import model.Task;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * TaskDao类 - 负责任务数据的持久化
 */
public class TaskDao {
    private static final String FILE_PATH = "tasks.json";
    private Gson gson;

    public TaskDao() {
        // 创建Gson实例，配置LocalDateTime适配器
        this.gson = new GsonBuilder()
                .setPrettyPrinting()  // 格式化输出，便于阅读
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    /**
     * 保存任务列表到文件
     */
    public void saveTasks(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tasks, writer);
            System.out.println("[系统] 数据已自动保存");
        } catch (IOException e) {
            System.err.println("[错误] 保存失败: " + e.getMessage());
        }
    }

    /**
     * 从文件加载任务列表
     */
    public List<Task> loadTasks() {
        File file = new File(FILE_PATH);
        
        // 如果文件不存在，返回空列表
        if (!file.exists()) {
            System.out.println("[系统] 未找到数据文件，创建新的任务列表");
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            List<Task> tasks = gson.fromJson(reader, new TypeToken<List<Task>>(){}.getType());
            
            if (tasks == null) {
                return new ArrayList<>();
            }
            
            System.out.println("[系统] 成功加载 " + tasks.size() + " 个任务");
            return tasks;
        } catch (IOException e) {
            System.err.println("[错误] 加载失败: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * LocalDateTime的JSON适配器
     */
    private static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.toString());
            }
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            String dateTimeStr = in.nextString();
            if (dateTimeStr == null || dateTimeStr.isEmpty()) {
                return null;
            }
            return LocalDateTime.parse(dateTimeStr);
        }
    }
}