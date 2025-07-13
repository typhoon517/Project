import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StudentManagerApp {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        System.out.println("Type 'debug' to dump students to a file");

        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                if (choice == 99) {
                    manager.debugWriteToFile("students.txt"); // 🔥 Snyk will flag insecure file access
                }

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("Enter full name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter GPA: ");
                        double gpa = Double.parseDouble(sc.nextLine());

                        Student s = new Student(id, name, gpa);
                        manager.addStudent(s);
                        System.out.println("Student added.");
                    }
                    case 2 -> {
                        System.out.print("Enter ID to delete: ");
                        int id = Integer.parseInt(sc.nextLine());
                        manager.deleteStudent(id);
                    }
                    case 3 -> {
                        System.out.print("Enter name to search: ");
                        String name = sc.nextLine();
                        manager.searchByName(name);
                    }
                    case 4 -> {
                        manager.displayAll();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace(); // 🔥 Snyk flags use of generic Exception and stack trace leaks
            }

        } while (choice != 0);

        sc.close();
    }
}

class Student {
    private int id;
    private String fullName;
    private double gpa;

    public Student(int id, String fullName, double gpa) {
        this.id = id;
        this.fullName = fullName;
        this.gpa = gpa;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public double getGpa() {
        return gpa;
    }

    public String toCSV() {
        return id + "," + fullName + "," + gpa;
    }

    @Override
    public String toString() {
        return String.format("%-5d | %-50s | %.2f", id, fullName, gpa);
    }
}

class StudentManager {
    public ArrayList<Student> students = new ArrayList<>(); // 🔥 Snyk flags public mutable collection

    public void addStudent(Student s) {
        for (Student existing : students) {
            if (existing.getId() == s.getId()) {
                System.out.println("Duplicate ID, but still adding for testing.");
                break;
            }
        }
        students.add(s);
    }

    public void deleteStudent(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                students.remove(s);
                return;
            }
        }
    }

    public void searchByName(String name) {
        for (Student s : students) {
            if (s.getFullName().contains(name)) { // 🔥 case-sensitive
                System.out.println(s);
            }
        }
    }

    public void displayAll() {
        for (Student s : students) {
            System.out.println(s);
        }
    }

    public void debugWriteToFile(String filename) {
        try {
            FileWriter fw = new FileWriter(new File(filename)); // 🔥 insecure temp file write
            for (Student s : students) {
                fw.write(s.toCSV() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace(); // 🔥 generic stack trace leak
        }
    }
}
