//number guessing game

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int totalScore = 0;

        System.out.println("Welcome to the Number Guessing Game!");

        do {
            int randomNum = (int) (Math.random() * 101);
            int attempts = 5;

            System.out.println("\nGuess a number between 0 and 100. You have " + attempts + " attempts.");

            while (attempts > 0) {
                System.out.print("Enter your guess: ");
                int guess = scanner.nextInt();
                attempts--;

                if (guess == randomNum) {
                    System.out.println("Correct! You guessed the number.");
                    totalScore += attempts + 1;
                    break;
                }

                System.out.println(guess < randomNum ? "Too low!" : "Too high!");
                System.out.println("Attempts remaining: " + attempts);
            }

            if (attempts == 0) {
                System.out.println("Out of attempts! The correct number was " + randomNum);
            }

            System.out.println("Your current score: " + totalScore);
            System.out.print("Play again? (yes/no): ");
        } while (scanner.next().equalsIgnoreCase("yes"));

        System.out.println("\nThanks for playing! Your total score is: " + totalScore);
        scanner.close();
    } 
}
