import java.util.*;

public class TaskTracker {
    public static void main(String [] args) {

        if (args.length < 1) {
            System.out.println("enter a valid command, Ex: <add> <Description>");
            return;
        }

        TaskManager taskManager = new TaskManager();

        switch (args[0]) {
            case "add":
                if (args.length < 2) {
                    System.out.println("no description found, Ex: <add> <Description>");
                    return;
                }
                String description = args[1];
                taskManager.addTask(description);
                break;
        

            case "list":
                if (args.length < 2) {
                    System.out.println("no filter found, Ex: <list> <all|done|notdone|inprogress>");
                    return;
                }
                String filter = args[1];
                switch (filter) {
                    case "all":                        taskManager.listTasks();
                        break;
                    case "done":                       taskManager.listDoneTasks();
                        break;
                    case "notdone":                    taskManager.listNotDoneTasks();
                        break;
                    case "inprogress":                 taskManager.listInProgressTasks();
                        break;
                    default:                        System.out.println("Invalid filter. Use: all, done, notdone, inprogress");
                        break;
                }
                break;

            case "update":
                if (args.length < 3) {
                    System.out.println("no id found, Ex: <update> <id> <status>");
                    return;
                }
                int id = Integer.parseInt(args[1]);
                if (args.length < 3) {
                    System.out.println("no description found, Ex: <update> <id> <description>");
                    return;
                }
                String newDescription = args[2];
                taskManager.updateTaskDescription(id, newDescription);
                break;
            case "mark-todo":
                if (args.length < 2) {
                    System.out.println("no id found, Ex: <marktodo> <id>");
                    return;
                }
                int markTodoId = Integer.parseInt(args[1]);
                taskManager.markTaskTodo(markTodoId);
                break;

            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("no id found, Ex: <markinprogress> <id>");
                    return;
                }
                int markInProgressId = Integer.parseInt(args[1]);
                taskManager.markTaskInProgress(markInProgressId);
                break;

            case "mark-done":
                if (args.length < 2) {
                    System.out.println("no id found, Ex: <markdone> <id>");
                    return;
                }
                int markDoneId = Integer.parseInt(args[1]);
                taskManager.markTaskDone(markDoneId);
                break;

            case "delete":
                if (args.length < 2) {
                    System.out.println("no id found, Ex: <delete> <id>");
                    return;
                }
                int deleteId = Integer.parseInt(args[1]);
                taskManager.deleteTask(deleteId);
                break;
            default:
                System.out.println("Invalid command. Use: add, list, update, delete");
                break;
        }

     
    }
}