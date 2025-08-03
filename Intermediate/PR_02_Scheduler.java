package Java_Programs.Projects_CodeChef.Intermediate;

import java.util.*;

public class PR_02_Scheduler {

    static class Task {
        String name;
        int burst_time;
        int priority;
        int remaining_time;
        Integer start_time;
        Integer finish_time;

        public Task(String name, int burst_time, int priority) {
            this.name = name;
            this.burst_time = burst_time;
            this.priority = priority;
            this.remaining_time = burst_time;
            this.start_time = null;
            this.finish_time = null;
        }

        @Override
        public String toString() {
            return name + "(burst=" + burst_time + ", priority=" + priority + ")";
        }
    }

    public static class Scheduler {
        public List<Task> tasks;

        public Scheduler() {
            this.tasks = new ArrayList<>();
        }

        public void get_user_tasks(Scanner scanner) {
            System.out.print("Enter the number of tasks: ");
            int num_tasks = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < num_tasks; i++) {
                System.out.print("Enter name for Task " + (i + 1) + ": ");
                String name = scanner.nextLine();
                System.out.print("Enter burst time for " + name + ": ");
                int burst_time = scanner.nextInt();
                System.out.print("Enter priority for " + name + " (lower number = higher priority): ");
                int priority = scanner.nextInt();
                scanner.nextLine();
                tasks.add(new Task(name, burst_time, priority));
            }
        }

        public void reset_tasks() {
            for (Task task : tasks) {
                task.remaining_time = task.burst_time;
                task.start_time = null;
                task.finish_time = null;
            }
        }

        public void fcfs() {
            System.out.println("\nRunning First-Come, First-Serve (FCFS) Scheduling:");
            int current_time = 0;
            for (Task task : tasks) {
                task.start_time = current_time;
                current_time += task.burst_time;
                task.finish_time = current_time;
                System.out.println(task.name + ": start at " + task.start_time + ", finish at " + task.finish_time);
            }
        }

        public void round_robin(int quantum) {
            System.out.println("\nRunning Round Robin Scheduling:");
            Queue<Task> task_queue = new LinkedList<>(tasks);
            int current_time = 0;

            while (!task_queue.isEmpty()) {
                Task task = task_queue.poll();

                if (task.start_time == null) {
                    task.start_time = current_time;
                }

                if (task.remaining_time > quantum) {
                    current_time += quantum;
                    task.remaining_time -= quantum;
                    task_queue.add(task);
                } else {
                    current_time += task.remaining_time;
                    task.remaining_time = 0;
                    task.finish_time = current_time;
                    System.out.println(task.name + ": start at " + task.start_time + ", finish at " + task.finish_time);
                }
            }
        }

        public void priority_scheduling() {
            System.out.println("\nRunning Priority Scheduling:");
            tasks.sort(Comparator.comparingInt(task -> task.priority));
            int current_time = 0;
            for (Task task : tasks) {
                task.start_time = current_time;
                current_time += task.burst_time;
                task.finish_time = current_time;
                System.out.println(task.name + " (Priority " + task.priority + "): start at " + task.start_time +
                        ", finish at " + task.finish_time);
            }
        }

        public void main_menu() {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("\nChoose a Scheduling Algorithm:");
                System.out.println("1. First-Come, First-Serve (FCFS)");
                System.out.println("2. Round Robin (RR)");
                System.out.println("3. Priority Scheduling (Non-Preemptive)");
                System.out.println("4. Exit");

                System.out.print("Enter your choice (1-4): ");
                String choice = scanner.nextLine();
                reset_tasks();

                switch (choice) {
                    case "1":
                        fcfs();
                        break;
                    case "2":
                        System.out.print("Enter time quantum for Round Robin: ");
                        int quantum = scanner.nextInt();
                        scanner.nextLine();
                        round_robin(quantum);
                        break;
                    case "3":
                        priority_scheduling();
                        break;
                    case "4":
                        System.out.println("Exiting CPU Task Scheduler. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice! Please enter a valid option.");
                }
            }
        }

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Scheduler scheduler = new Scheduler();
            scheduler.get_user_tasks(scanner);
            scheduler.main_menu();
            scanner.close();
        }
    }
}
