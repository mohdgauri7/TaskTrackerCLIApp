import java.nio.file.Path;
import java.time.LocalDateTime;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.File;
import java.io.IOException;

public class TaskManager {
    private static final String FILE_NAME = "task.json";
    
    public void addTask(String description) {
        List<TaskModel> tasks = loadTasks();
        int id = tasks.isEmpty() ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
        TaskModel task = new TaskModel(id, description);
        tasks.add(task);
        saveTasks(tasks);
        System.out.println("Task added successfully (ID: " + task.getId() + ")");
    }

    public void listTasks() {
        List<TaskModel> tasks = loadTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        tasks.forEach(task -> System.out.println(task.getId() + ": " + task.getDescription() + " [" + task.getStatus().getValue() + "]"));
    }

    public void listDoneTasks() {
        List<TaskModel> tasks = loadTasks();
        tasks.stream()
                .filter(task -> task.getStatus() == Status.DONE)
                .forEach(task -> System.out.println(task.getId() + ": " + task.getDescription() + " [" + task.getStatus().getValue() + "]"));
    }

    public void listNotDoneTasks() {
        List<TaskModel> tasks = loadTasks();
        tasks.stream()
                .filter(task -> task.getStatus() != Status.DONE)
                .forEach(task -> System.out.println(task.getId() + ": " + task.getDescription() + " [" + task.getStatus().getValue() + "]"));
    }

    public void listInProgressTasks() {
        List<TaskModel> tasks = loadTasks();
        tasks.stream()
                .filter(task -> task.getStatus() == Status.IN_PROGRESS)
                .forEach(task -> System.out.println(task.getId() + ": " + task.getDescription() + " [" + task.getStatus().getValue() + "]"));
    }

    public void updateTaskStatus(int id, Status newStatus) {
        List<TaskModel> tasks = loadTasks();
        Optional<TaskModel> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (taskOpt.isPresent()) {
            TaskModel task = taskOpt.get();
            task.setStatus(newStatus);
            task.setUpdatedAt(LocalDateTime.now());
            saveTasks(tasks);
            System.out.println("Task updated: " + task.getDescription() + " is now " + newStatus.getValue());
        } else {
            System.out.println("Task not found with ID: " + id);
        }
    }

    public void updateTaskDescription(int id, String newDescription) {
        List<TaskModel> tasks = loadTasks();
        Optional<TaskModel> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (taskOpt.isPresent()) {
            TaskModel task = taskOpt.get();
            task.setDescription(newDescription);
            task.setUpdatedAt(LocalDateTime.now());
            saveTasks(tasks);
            System.out.println("Task updated: ID " + id + " description changed to \"" + newDescription + "\"");
        } else {
            System.out.println("Task not found with ID: " + id);
        }
    }

    public void markTaskTodo(int id) {
        List<TaskModel> tasks = loadTasks();
        Optional<TaskModel> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (taskOpt.isPresent()) {
            TaskModel task = taskOpt.get();
            task.setStatus(Status.TODO);
            task.setUpdatedAt(LocalDateTime.now());
            saveTasks(tasks);
            System.out.println("Task updated: " + task.getDescription() + " is now " + Status.TODO.getValue());
        } else {
            System.out.println("Task not found with ID: " + id);
        }
    }
    public void markTaskInProgress(int id) {
        List<TaskModel> tasks = loadTasks();
        Optional<TaskModel> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (taskOpt.isPresent()) {
            TaskModel task = taskOpt.get();
            task.setStatus(Status.IN_PROGRESS);
            task.setUpdatedAt(LocalDateTime.now());
            saveTasks(tasks);
            System.out.println("Task updated: " + task.getDescription() + " is now " + Status.IN_PROGRESS.getValue());
        } else {
            System.out.println("Task not found with ID: " + id);
        }
    }
    public void markTaskDone(int id) {
        List<TaskModel> tasks = loadTasks();
        Optional<TaskModel> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (taskOpt.isPresent()) {
            TaskModel task = taskOpt.get();
            task.setStatus(Status.DONE);
            task.setUpdatedAt(LocalDateTime.now());
            saveTasks(tasks);
            System.out.println("Task updated: " + task.getDescription() + " is now " + Status.DONE.getValue());
        } else {
            System.out.println("Task not found with ID: " + id);
        }
    }

    public void deleteTask(int id) {
        List<TaskModel> tasks = loadTasks();
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) {
            saveTasks(tasks);
            System.out.println("Task deleted with ID: " + id);
        } else {
            System.out.println("Task not found with ID: " + id);
        }
    }



    private void saveTasks(List<TaskModel> tasks) {
        String json = TaskModel.toJsonList(tasks);
        try {
            Files.writeString(Path.of(FILE_NAME), json);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }



    private List<TaskModel> loadTasks() {
        List<TaskModel> tasks = new ArrayList<>();

        try {
            File file = new File(FILE_NAME);

            // If file doesn't exist → return empty list
            if (!file.exists()) {
                return tasks;
            }

            String content = Files.readString(file.toPath()).trim();
            if (content.isEmpty() || content.equals("[]")) {
                return tasks;
            }

            // Remove [ and ]

            content = content.substring(1, content.length() - 1).trim();

            // Split objects (very basic, works for your flat structure)

            String[] objects = content.split("\\},\\{");

            for (String obj : objects) {

                // Fix braces after split

                if (!obj.startsWith("{")) obj = "{" + obj;

                if (!obj.endsWith("}")) obj = obj + "}";

                TaskModel task = parseTask(obj);

                tasks.add(task);

            }

        } catch (Exception e) {

            e.printStackTrace(); // optional: handle properly

        }

        return tasks;

    }
    private TaskModel parseTask(String json) {

        TaskModel task = new TaskModel();

        json = json.substring(1, json.length() - 1);

        String[] fields = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        for (String field : fields) {

            String[] keyValue = field.split(":", 2);

            String key = keyValue[0].replace("\"", "").trim();

            String value = keyValue[1].trim().replace("\"", "");

            switch (key) {

                case "id":

                    task.setId(Integer.parseInt(value));

                    break;

                case "description":

                    task.setDescription(value);

                    break;

                case "status":

                    task.setStatus(Status.valueOf(value));

                    break;

                case "createdAt":

                    task.setCreatedAt(LocalDateTime.parse(value));

                    break;

                case "updatedAt":

                    task.setUpdatedAt(LocalDateTime.parse(value));

                    break;

            }

        }

        return task;

    }
    // javac TaskTracker.java TaskManager.java TaskModel.java Status.java

}
