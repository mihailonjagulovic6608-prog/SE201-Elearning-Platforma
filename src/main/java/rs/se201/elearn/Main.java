package rs.se201.elearn;

import rs.se201.elearn.model.Course;
import rs.se201.elearn.service.AuthService;
import rs.se201.elearn.service.CourseService;
import rs.se201.elearn.service.EnrollmentService;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        AuthService auth = new AuthService();
        CourseService courses = new CourseService();
        EnrollmentService enrollments = new EnrollmentService();

        Scanner sc = new Scanner(System.in);

        System.out.println("E-Learning Platforma");
        System.out.println("1) Registruj korisnika");
        System.out.println("2) Login");
        System.out.println("3) Napravi kurs");
        System.out.println("4) Objavi kurs");
        System.out.println("5) Navedi objavljene kurseve");
        System.out.println("6) Ubaci korisnika u kurs");
        System.out.println("0) Exit");

        while (true) {
            System.out.print("\nOpcija: ");
            String opt = sc.nextLine().trim();

            try {
                switch (opt) {
                    case "1" -> {
                        System.out.print("Email: "); String email = sc.nextLine();
                        System.out.print("Password (min 8, 1 veliko, 1 broj): "); String pass = sc.nextLine();
                        System.out.print("Uloga (STUDENT/INSTRUCTOR/ADMIN): "); String role = sc.nextLine();
                        int id = auth.register(email, pass, role);
                        System.out.println("Kreiran korisnik ID=" + id);
                    }
                    case "2" -> {
                        System.out.print("Email: "); String email = sc.nextLine();
                        System.out.print("Password: "); String pass = sc.nextLine();
                        auth.login(email, pass);
                    }
                    case "3" -> {
                        System.out.print("InstructorId: "); int instructorId = Integer.parseInt(sc.nextLine());
                        System.out.print("Title: "); String title = sc.nextLine();
                        System.out.print("Category: "); String category = sc.nextLine();
                        System.out.print("Level: "); String level = sc.nextLine();
                        int id = courses.createCourse(title, category, level, instructorId);
                        System.out.println("Kreiran kurs ID=" + id + " (DRAFT)");
                    }
                    case "4" -> {
                        System.out.print("CourseId: "); int courseId = Integer.parseInt(sc.nextLine());
                        courses.publishCourse(courseId);
                        System.out.println("Kurs objavljen.");
                    }
                    case "5" -> {
                        List<Course> list = courses.listPublished();
                        System.out.println("Published courses:");
                        for (Course c : list) {
                            System.out.println("#" + c.id + " | " + c.title + " | " + c.category + " | " + c.level);
                        }
                    }
                    case "6" -> {
                        System.out.print("UserId: "); int userId = Integer.parseInt(sc.nextLine());
                        System.out.print("CourseId: "); int courseId = Integer.parseInt(sc.nextLine());
                        int eid = enrollments.enroll(userId, courseId);
                        System.out.println("Enrollment ID=" + eid);
                    }
                    case "0" -> System.exit(0);
                    default -> System.out.println("Nepoznata opcija.");
                }
            } catch (Exception e) {
                System.out.println("Greska: " + e.getMessage());
            }
        }
    }
}
