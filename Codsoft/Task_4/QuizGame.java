package Task_4;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

// Class to represent each quiz question
class Question {
    String question;
    String[] options;
    int correctAnswer;

    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

// Main Quiz Class
public class QuizGame {
    private static Question[] questions = {
        new Question("What is the capital of India?", new String[]{"1. Mumbai", "2. Delhi", "3. Kolkata", "4. Chennai"}, 2),
        new Question("Which programming language is used for Android development?", new String[]{"1. Python", "2. C++", "3. Java", "4. Swift"}, 3),
        new Question("Who wrote the national anthem of India?", new String[]{"1. Rabindranath Tagore", "2. Mahatma Gandhi", "3. Subhash Chandra Bose", "4. Sardar Patel"}, 1),
        new Question("What is 15 + 6?", new String[]{"1. 19", "2. 21", "3. 20", "4. 22"}, 2),
        new Question("Which planet is known as the Red Planet?", new String[]{"1. Venus", "2. Jupiter", "3. Mars", "4. Saturn"}, 3)
    };

    private static int score = 0;
    private static Scanner scanner = new Scanner(System.in);
    private static boolean timeUp = false;

    public static void main(String[] args) {
        System.out.println("Welcome to the Quiz Game!");
        System.out.println("You have 10 seconds to answer each question.");
        System.out.println("Select the correct option number (1-4).");

        for (int i = 0; i < questions.length; i++) {
            askQuestion(i);
        }

        // Display final result
        System.out.println("\nQuiz Over!");
        System.out.println("Your final score: " + score + " / " + questions.length);
        scanner.close();
    }

    private static void askQuestion(int index) {
        Question q = questions[index];
        System.out.println("\nQuestion " + (index + 1) + ": " + q.question);
        for (String option : q.options) {
            System.out.println(option);
        }

        // Timer for 10 seconds
        Timer timer = new Timer();
        timeUp = false;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeUp = true;
                System.out.println("\nTime's up! Moving to the next question...");
            }
        }, 10000); // 10 seconds

        // Get user input
        System.out.print("Your answer: ");
        int userAnswer = -1;
        if (scanner.hasNextInt()) {
            userAnswer = scanner.nextInt();
        }
        timer.cancel();

        if (timeUp) return;

        if (userAnswer == q.correctAnswer) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Wrong! The correct answer was: " + q.correctAnswer);
        }
    }
}
