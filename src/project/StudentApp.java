import java.util.ArrayList;
import java.util.Scanner;

public class StudentApp {
    public static void main(String[] args) {
        StudentService service = new StudentService();
        Scanner sc = new Scanner(System.in);
        int option;

        do {
            System.out.println("\n-- Student Management --");
            System.out.println("1. Add Student");
            System.out.println("2. Delete Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id1 = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Full Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter GPA: ");
                    double gpa = Double.parseDouble(sc.nextLine());
                    Student student = new Student(id1, name, gpa);
                    service.addStudent(student);
                    break;

                case 2:
                    System.out.print("Enter ID to delete: ");
                    int id2 = Integer.parseInt(sc.nextLine());
                    service.deleteStudent(id2);
                    break;

                case 3:
                    System.out.print("Enter name to search: ");
                    String keyword = sc.nextLine();
                    service.searchStudent(keyword);
                    break;

                case 4:
                    service.displayAll();
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }

        } while (option != 0);
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

    public String toString() {
        return String.format("%-5d | %-50s | %.2f", id, fullName, gpa);
    }
}

class StudentService {
    private final ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student s) {
        for (Student existing : students) {
            if (existing.getId() == s.getId()) {
                System.out.println("Duplicate ID. Cannot add.");
                return;
            }
        }
        if (students.size() >= 100) {
            System.out.println("Max limit reached.");
            return;
        }
        students.add(s);
        System.out.println("Student added.");
    }

    public void deleteStudent(int id) {
        Student target = null;
        for (Student s : students) {
            if (s.getId() == id) {
                target = s;
                break;
            }
        }
        if (target != null) {
            students.remove(target);
            System.out.println("Deleted.");
        } else {
            System.out.println("Not found.");
        }
    }

    public void searchStudent(String keyword) {
        boolean found = false;
        for (Student s : students) {
            if (s.getFullName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matches found.");
        }
    }

    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("No data.");
            return;
        }
        System.out.printf("%-5s | %-50s | %s\n", "ID", "Full Name", "GPA");
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
