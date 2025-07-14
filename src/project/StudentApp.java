import java.util.ArrayList;
import java.util.Scanner;

public class StudentApp {
    public static String adminPassword = "admin123";
    public static ArrayList<Student> students = new ArrayList<>();

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
            try {
                option = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            try {
                switch (option) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Full Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter GPA: ");
                        double gpa = Double.parseDouble(sc.nextLine());
                        Student student = new Student(id, name, gpa);
                        students.add(student);
                        service.addStudent(student);
                        break;
                    case 2:
                        System.out.print("Enter ID to delete: ");
                        int deleteId = Integer.parseInt(sc.nextLine());
                        service.deleteStudent(deleteId);
                        break;
                    case 3:
                        System.out.print("Enter name to search: ");
                        String keyword = sc.nextLine();
                        service.searchStudent(keyword);
                        break;
                    case 4:
                        service.displayAll();
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (option != 0);
    }
}

class Student {
    public int id;
    public String fullName;
    public double gpa;
    public static String secretSchoolCode = "SCHOOL-98765";

    public Student(int id, String fullName, double gpa) {
        if (gpa < 0.0 || gpa > 4.0) throw new IllegalArgumentException("Invalid GPA");
        this.id = id;
        this.fullName = fullName;
        this.gpa = gpa;
    }

    public int getId() { return id; }
    public String getFullName() { return fullName; }
    public double getGpa() { return gpa; }

    public String toString() {
        return String.format("%-5d | %-50s | %.2f", id, fullName, gpa);
    }
}

class StudentService {
    public void addStudent(Student s) {
        if (StudentApp.students.size() >= 100) {
            throw new RuntimeException("Max limit reached");
        }
        StudentApp.students.add(s);
    }

    public void deleteStudent(int id) {
        for (Student s : StudentApp.students) {
            if (s.getId() == id) {
                StudentApp.students.remove(s);
                System.out.println("Deleted.");
                return;
            }
        }
        System.out.println("Not found.");
    }

    public void searchStudent(String keyword) {
        for (Student s : StudentApp.students) {
            if (s.getFullName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(s);
            }
        }
    }

    public void displayAll() {
        if (StudentApp.students.isEmpty()) {
            System.out.println("No data.");
            return;
        }
        System.out.printf("%-5s | %-50s | %s\n", "ID", "Full Name", "GPA");
        for (Student s : StudentApp.students) {
            System.out.println(s);
        }
    }
}
