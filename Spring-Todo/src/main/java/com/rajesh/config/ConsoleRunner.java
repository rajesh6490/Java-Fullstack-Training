package com.rajesh.config;
import java.util.List;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.rajesh.model.Task;
import com.rajesh.service.TaskService;
@Component
public class ConsoleRunner implements CommandLineRunner {
    private final TaskService service;
    public ConsoleRunner(TaskService service) {
        this.service = service;
    }
    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== TO-DO LIST MENU =====");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Task Name: ");
                    String name = sc.nextLine();
                    service.addTask(new Task(name));
                    System.out.println("✅ Task added!");
                    break;
                case 2:
                    List<Task> tasks = service.getAllTasks();
                    tasks.forEach(t ->
                            System.out.println(t.getId() + ". " + t.getName() +
                                    (t.isCompleted() ? " [✔]" : " [✘]"))
                    );
                    break;
                case 3:
                    System.out.print("Enter Task ID: ");
                    Long id = sc.nextLong();
                    service.markCompleted(id);
                    System.out.println("✅ Completed!");
                    break;
                case 4:
                    System.out.print("Enter Task ID: ");
                    Long delId = sc.nextLong();
                    service.deleteTask(delId);
                    System.out.println("🗑 Deleted!");
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);
        sc.close();
    }
}
