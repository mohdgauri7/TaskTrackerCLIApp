import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TaskModel {
    private int id;
    private String description;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public TaskModel() {
        // Default constructor
    }

    public TaskModel(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = Status.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static String toJsonList(List<TaskModel> tasks) {
        return tasks.stream()
                .map(TaskModel::toJson)
                .collect(Collectors.joining(",", "[", "]"));

    }


    private String toJson() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return "{"
            + "\"id\":" + id + ","
            + "\"description\":\"" + escape(description) + "\","
            + "\"status\":\"" + status + "\","
            + "\"createdAt\":\"" + createdAt.format(formatter) + "\","
            + "\"updatedAt\":\"" + updatedAt.format(formatter) + "\""
            + "}";
    }

    // Handle quotes and special chars
    private String escape(String str) {
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"");

    }



    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
