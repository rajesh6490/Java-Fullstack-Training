import java.util.ArrayList;
import java.util.Scanner;
// Task class (OOP concept)
class Task {
    String name;
    boolean isCompleted;
    Task(String name) {
        this.name = name;
        this.isCompleted = false;
    }
    void markCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return (isCompleted ? "[✔] " : "[✘] ") + name;
    }
}
public class Todo {
    static ArrayList<Task> tasks = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== TO-DO LIST MENU =====");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            // Input validation
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    completeTask();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);
    }

    // Add Task
    static void addTask() {
        System.out.print("Enter task name: ");
        String name = sc.nextLine();

        if (name.trim().isEmpty()) {
            System.out.println("Task cannot be empty.");
            return;
        }

        tasks.add(new Task(name));
        System.out.println("Task added successfully!");
    }

    // View Tasks
    static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        System.out.println("\n--- Your Tasks ---");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    // Mark Task Completed
    static void completeTask() {
        viewTasks();

        if (tasks.isEmpty()) return;

        System.out.print("Enter task number to mark as completed: ");

        if (!sc.hasNextInt()) {
            System.out.println("Invalid input.");
            sc.next();
            return;
        }

        int index = sc.nextInt();
        sc.nextLine();

        if (index < 1 || index > tasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }

        tasks.get(index - 1).markCompleted();
        System.out.println("Task marked as completed!");
    }

    // Delete Task
    static void deleteTask() {
        viewTasks();

        if (tasks.isEmpty()) return;

        System.out.print("Enter task number to delete: ");

        if (!sc.hasNextInt()) {
            System.out.println("Invalid input.");
            sc.next();
            return;
        }

        int index = sc.nextInt();
        sc.nextLine();

        if (index < 1 || index > tasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }
        tasks.remove(index - 1);
        System.out.println("Task deleted successfully!");
    }
}