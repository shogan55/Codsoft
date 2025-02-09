import java.util.Scanner;

class StudentResult {
    private int[] marks;
    private int totalMarks;
    private double averagePercentage;
    private String grade;

    public StudentResult(int numSubjects) {
        marks = new int[numSubjects];
    }

    public void inputMarks() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < marks.length; i++) {
            System.out.print("Enter marks obtained in subject " + (i + 1) + " (out of 100): ");
            marks[i] = scanner.nextInt();

            // Validate input (0-100)
            while (marks[i] < 0 || marks[i] > 100) {
                System.out.print("Invalid marks! Enter again (0-100): ");
                marks[i] = scanner.nextInt();
            }
        }
    }

    public void calculateResults() {
        totalMarks = 0;
        for (int mark : marks) {
            totalMarks += mark;
        }
        averagePercentage = (double) totalMarks / marks.length;
        grade = assignGrade(averagePercentage);
    }

    private String assignGrade(double percentage) {
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B";
        else if (percentage >= 60) return "C";
        else if (percentage >= 50) return "D";
        else return "F";
    }

    public void displayResults() {
        System.out.println("\n=== Results ===");
        System.out.println("Total Marks: " + totalMarks);
        System.out.println("Average Percentage: " + String.format("%.2f", averagePercentage) + "%");
        System.out.println("Grade: " + grade);
    }
}

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();

        StudentResult student = new StudentResult(numSubjects);
        student.inputMarks();
        student.calculateResults();
        student.displayResults();
    }
}
