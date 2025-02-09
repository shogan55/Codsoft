package Task_5;
import java.sql.*;
import java.util.Scanner;

public class CourseRegistrationDB {
    private static final String URL = "jdbc:mysql://localhost:3306/StudentCourseDB";
    private static final String USER = "root"; // Change if needed
    private static final String PASSWORD = ""; // Set your MySQL password

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.print("Enter Student Name: ");
            String studentName = scanner.nextLine();
            int studentId = registerStudent(conn, studentName);

            while (true) {
                System.out.println("\n--- Student Course Registration System ---");
                System.out.println("1. View Available Courses");
                System.out.println("2. Register for a Course");
                System.out.println("3. Drop a Course");
                System.out.println("4. View Registered Courses");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        displayCourses(conn);
                        break;
                    case 2:
                        System.out.print("Enter Course Code to Register: ");
                        String regCode = scanner.nextLine();
                        registerCourse(conn, studentId, regCode);
                        break;
                    case 3:
                        System.out.print("Enter Course Code to Drop: ");
                        String dropCode = scanner.nextLine();
                        dropCourse(conn, studentId, dropCode);
                        break;
                    case 4:
                        displayRegisteredCourses(conn, studentId);
                        break;
                    case 5:
                        System.out.println("Thank you for using the system!");
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int registerStudent(Connection conn, String name) throws SQLException {
        String query = "INSERT INTO Students (name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    private static void displayCourses(Connection conn) throws SQLException {
        String query = "SELECT * FROM Courses";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("\nAvailable Courses:");
            while (rs.next()) {
                System.out.println("Course Code: " + rs.getString("course_code"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("Schedule: " + rs.getString("schedule"));
                System.out.println("Capacity: " + rs.getInt("enrolled_students") + "/" + rs.getInt("capacity"));
                System.out.println("-------------------------------");
            }
        }
    }

    private static void registerCourse(Connection conn, int studentId, String courseCode) throws SQLException {
        String courseQuery = "SELECT course_id, capacity, enrolled_students FROM Courses WHERE course_code = ?";
        try (PreparedStatement courseStmt = conn.prepareStatement(courseQuery)) {
            courseStmt.setString(1, courseCode);
            ResultSet rs = courseStmt.executeQuery();
            if (rs.next()) {
                int courseId = rs.getInt("course_id");
                int capacity = rs.getInt("capacity");
                int enrolled = rs.getInt("enrolled_students");

                if (enrolled < capacity) {
                    String insertQuery = "INSERT INTO Registrations (student_id, course_id) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setInt(1, studentId);
                        insertStmt.setInt(2, courseId);
                        insertStmt.executeUpdate();
                    }

                    String updateQuery = "UPDATE Courses SET enrolled_students = enrolled_students + 1 WHERE course_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                        updateStmt.setInt(1, courseId);
                        updateStmt.executeUpdate();
                    }
                    System.out.println("Successfully registered for " + courseCode);
                } else {
                    System.out.println("Course is full.");
                }
            } else {
                System.out.println("Course not found.");
            }
        }
    }

    private static void dropCourse(Connection conn, int studentId, String courseCode) throws SQLException {
        String courseQuery = "SELECT course_id FROM Courses WHERE course_code = ?";
        try (PreparedStatement courseStmt = conn.prepareStatement(courseQuery)) {
            courseStmt.setString(1, courseCode);
            ResultSet rs = courseStmt.executeQuery();
            if (rs.next()) {
                int courseId = rs.getInt("course_id");

                String deleteQuery = "DELETE FROM Registrations WHERE student_id = ? AND course_id = ?";
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                    deleteStmt.setInt(1, studentId);
                    deleteStmt.setInt(2, courseId);
                    int affectedRows = deleteStmt.executeUpdate();
                    if (affectedRows > 0) {
                        String updateQuery = "UPDATE Courses SET enrolled_students = enrolled_students - 1 WHERE course_id = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                            updateStmt.setInt(1, courseId);
                            updateStmt.executeUpdate();
                        }
                        System.out.println("Successfully dropped " + courseCode);
                    } else {
                        System.out.println("You are not registered for this course.");
                    }
                }
            } else {
                System.out.println("Course not found.");
            }
        }
    }

    private static void displayRegisteredCourses(Connection conn, int studentId) throws SQLException {
        String query = "SELECT c.course_code, c.title FROM Courses c JOIN Registrations r ON c.course_id = r.course_id WHERE r.student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            System.out.println("\nRegistered Courses:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("course_code") + ": " + rs.getString("title"));
            }
        }
    }
}
