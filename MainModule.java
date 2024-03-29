package main;
import java.util.*;
import java.sql.*;
import controller.SISController;
import dao.StudentServiceProvider;
import entity.*;
import util.DBConnUtil;
import dao.StudentServiceProviderImpl;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class MainModule {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = DBConnUtil.getConnection();
        StudentServiceProvider studentServiceProvider = new StudentServiceProviderImpl(connection);
        SISController sisController = new SISController(studentServiceProvider);

        int choice;
        do {
            System.out.println("SIS Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Add course");
            System.out.println("4. Enroll Student in Course");
            System.out.println("5. Display Enrolled Courses for Student");
            System.out.println("6. Make Payment");
            System.out.println("7. Display Payments for Student");
            System.out.println("8. Update Student Information");
            System.out.println("9. Display All Courses");
            System.out.println("10. Display All Teachers");
            System.out.println("11. Assign Teacher to Course");
            System.out.println("12. Course Statistics");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Student newStudent = sisController.getStudentInformation();
                    sisController.addStudentToDatabase(newStudent);
                    break;
                case 2:
                    List<Student> allStudents = sisController.getAllStudentsFromDatabase();
                    sisController.displayStudentInfo(allStudents);
                    break;
                case 3:
                	Course newCourse = sisController.getCourseInformation(scanner);
                    sisController.addCourseToDatabase(newCourse);
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    int enrollStudentId = scanner.nextInt();
                    System.out.print("Enter Course ID: ");
                    int enrollCourseId = scanner.nextInt();
                    sisController.enrollInCourse(enrollStudentId, enrollCourseId);
                    break;
                case 5:
                    System.out.print("Enter Student ID: ");
                    int displayEnrolledStudentId = scanner.nextInt();
                    List<Course> enrolledCourses = sisController.getEnrolledCourses(displayEnrolledStudentId);
                    sisController.displayEnrolledCoursesForStudent(enrolledCourses);
                    break;
                case 6:
                    System.out.print("Enter Student ID: ");
                    int paymentStudentId = scanner.nextInt();
                    System.out.print("Enter Payment Amount: ");
                    BigDecimal paymentAmount = scanner.nextBigDecimal();
                    System.out.print("Enter Payment Date (YYYY-MM-DD): ");
                    String paymentDateString = scanner.next();
                    LocalDate paymentDate = LocalDate.parse(paymentDateString);
                    sisController.makePayment(paymentStudentId, paymentAmount, paymentDate);
                    break;
                case 7:
                    System.out.print("Enter Student ID: ");
                    int displayPaymentStudentId = scanner.nextInt();
                    List<Payment> payments = sisController.getPayments(displayPaymentStudentId);
                    sisController.displayPayments(payments);
                    break;
                case 8:
                    System.out.print("Enter Student ID: ");
                    int updateStudentId = scanner.nextInt();
                    sisController.updateStudentInformation(updateStudentId);
                    break;
                case 9:
                    sisController.displayAllCourses();
                    break;
                case 10:
                    sisController.displayAllTeachers();
                    break;
                case 11:
                    System.out.print("Enter Teacher ID: ");
                    int teacherId = scanner.nextInt();
                    System.out.print("Enter Course ID: ");
                    int assignCourseId = scanner.nextInt();
                    sisController.assignTeacherToCourse(teacherId, assignCourseId);
                    break;
               

                case 12:
                    System.out.print("Enter course ID: ");
                    int courseStatisticsId = scanner.nextInt();
                    Course courseStatisticsCourse = sisController.getCourseById(courseStatisticsId);

                    if (courseStatisticsCourse != null) {
                        System.out.println("Course Statistics for Course ID " + courseStatisticsId + ":");
                        sisController.generateEnrollmentReportForCourse(courseStatisticsCourse);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting SIS Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }
}
