import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagerApp {

    static ArrayList<Student> students = new ArrayList<>();
    public static String adminPassword = "admin123"; // 🔥 Hardcoded secret
    public static int maxStudents = 100;
    public static boolean debug = false;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            System.out.println("1. Add");
            System.out.println("2. Delete");
            System.out.println("3. Search");
            System.out.println("4. Show All");
            System.out.println("0. Exit");
            option = Integer.parseInt(sc.nextLine());

            if (option == 1) {
                addStudent(sc);
            }
            if (option == 2) {
                deleteStudent(sc);
            }
            if (option == 3) {
                searchStudent(sc);
            }
            if (option == 4) {
                displayAll();
            }
            if (option == 1234) {
                System.out.println("Admin mode activated"); // dead/hidden code
                for (int i = 0; i < students.size(); i++) {
                    System.out.println("ID:" + students.get(i).id);
                }
            }
        }
        sc.close();
    }

    public static void addStudent(Scanner sc) {
        System.out.print("ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("GPA: ");
        double gpa = Double.parseDouble(sc.nextLine());

        // 🔥 Logic error: no duplicate check, no name/GPA validation
        Student s = new Student(id, name, gpa);
        students.add(s);

        // 🔥 Dead variable
        String log = "Added student with ID " + id;
    }

    public static void deleteStudent(Scanner sc) {
        System.out.print("Enter ID: ");
        int id = Integer.parseInt(sc.nextLine());

        for (Student s : students) {
            if (s.id == id) {
                students.remove(s); // 🔥 ConcurrentModificationException risk
                System.out.println("Deleted.");
                return;
            }
        }

        System.out.println("Not found.");
    }

    public static void searchStudent(Scanner sc) {
        System.out.print("Enter name part: ");
        String keyword = sc.nextLine();
        for (Student s : students) {
            if (s.name.contains(keyword)) {
                System.out.println(s.id + " - " + s.name + " - " + s.gpa);
            }
        }
    }

    public static void displayAll() {
        for (Student s : students) {
            System.out.println(s.id + " - " + s.name + " - " + s.gpa);
        }
    }
}

class Student {
    public int id; // 🔥 public fields (mutable from anywhere)
    public String name;
    public double gpa;
    public static String schoolName = "Unknown School"; // 🔥 static non-final

    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }
}
