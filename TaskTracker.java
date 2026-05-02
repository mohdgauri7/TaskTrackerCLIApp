import java.util.*;

public class TaskTracker {
    public static void main(String [] args) {

        if (args.length < 1) {
            System.out.println("no command found, Ex: <add> <Description>");
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
                    System.out.println("no status found, Ex: <update> <id> <status>");
                    return;
                }
                String statusStr = args[2];
                Status newStatus;
                try {
                    newStatus = Status.valueOf(statusStr.toUpperCase().replace(" ", "_"));
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid status. Use: TODO, IN_PROGRESS, DONE");
                    return;
                }
                taskManager.updateStatus(id, newStatus);
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