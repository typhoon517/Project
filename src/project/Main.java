import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Task Manager =====");
            System.out.println("1. Add Task");
            System.out.println("2. List Tasks");
            System.out.println("3. Mark Task Completed");
            System.out.println("4. Delete Task");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter task name: ");
                    String name = sc.nextLine();
                    manager.addTask(name);
                    break;
                case 2:
                    manager.listTasks();
                    break;
                case 3:
                    System.out.print("Enter task number to complete: ");
                    int completeIndex = sc.nextInt() - 1;
                    manager.completeTask(completeIndex);
                    break;
                case 4:
                    System.out.print("Enter task number to delete: ");
                    int deleteIndex = sc.nextInt() - 1;
                    manager.deleteTask(deleteIndex);
                    break;
                case 0:
                    System.out.println("Exiting Task Manager.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
        sc.close();
    }
}
