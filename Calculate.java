/**
 *  @author Chris Turgeon
 *  @version 1.0
 *  Driver Class
 */

import java.util.Scanner;
import java.text.NumberFormat;

/**
 *  Driver class to calculate Riemann sums. Takes in user
 *  information relevant to the calculation and computes a
 *  Riemann approximation.
 */
public class Calculate {

  public static void main(String[] args) {
    String separator = "\n===================================";
    System.out.println(separator);
    Boolean done = false;
    while(!done) {

      // Create input scanner and display commands
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter c for Riemann sum\nEnter q to quit\n ==> ");
      String cmd = sc.nextLine();

      // Prompt user for sum type and function then display result
      if (cmd.equals("c")) {
          System.out.print("\nEnter l for left hand Riemann sum\n" +
                           "Enter r for right hand Riemann sum\n"  +
                           "Enter m for midpoint Riemann sum\n"    +
                           "Enter t for trapezoid Riemann sum\n"   +
                           " ==> ");
          String sumType = sc.nextLine();
          if (sumType.equals("l") || sumType.equals("r") || sumType.equals("m") || sumType.equals("t")) {
            int function, numSteps, precision;
            double leftBound, rightBound;

            try {
              // Read in the function type from the user
              System.out.print("\nEnter the number for the corresponding function:\n" +
                               "  1 for f(x) = x * x\n"     +
                               "  2 for f(x) = sin(x)\n"    +
                               "  3 for f(x) = arctan(x)\n" +
                               "  4 for f(x) = x * x * x\n" +
                               "  5 for f(x) = sqrt(x)\n"   +
                               "  6 for f(x) = ln(x)\n"     +
                               "  ==> ");
              function = sc.nextInt();
              if (function < 1 || function > 6) {
                System.out.println("\nERROR: Input entered does not match a function!");
                System.out.println(separator);
                continue;
              }

              // Take in data for Riemann sum calculation
              System.out.print("\nEnter the number of steps (int): ");
              numSteps = sc.nextInt();
              System.out.print("Enter an integer for the precision: ");
              precision = sc.nextInt();
              System.out.print("Enter a left bound: ");
              leftBound = Double.parseDouble(sc.next());
              System.out.print("Enter a right bound: ");
              rightBound = Double.parseDouble(sc.next());
              if (precision < 0) {
                System.out.println("\nERROR: Decimal precision cannot be less than 0");
                System.out.println(separator);
                continue;
              }

            // Catch input exceptions thrown by invalid input
            } catch(Exception e) {
              System.err.format("\nERROR: Invalid input was entered! Try again.\n");
              System.out.println(separator);
              continue;
            }

            // Try to calculate the Riemann sum given user data and catch Exceptions
            try {
              Riemann Riemann = new Riemann(sumType, numSteps, function);
              double result = Riemann.calculate(leftBound, rightBound);
              NumberFormat nf = NumberFormat.getInstance();
              nf.setMaximumFractionDigits(precision);
              nf.setMinimumFractionDigits(precision);
              System.out.print("\nRiemann approximation is: ");
              System.out.println(nf.format(result));
            } catch(IllegalArgumentException e1) {
              System.out.println("");
              e1.printStackTrace();
              System.out.println(separator);
              continue;
            } catch(ArithmeticException e2) {
              System.out.println("");
              e2.printStackTrace();
              System.out.println(separator);
              continue;
            }

          // Inform user if invalid input is entered
          } else {
            System.out.println("\nERROR: Invalid sum type entered! Try again.");
            System.out.println(separator);
            continue;
          }
      }

      // Quit the program if user desires
      else if (cmd.equals("q")) {
        System.out.println("\nExiting program...");
        done = true;
      }

      // If unrecognized command is entered
      else {
        System.out.println("\nERROR: Invalid command entered! Try again.");
      }
      System.out.println(separator);
    }
  }
}
