/**
 *  @author Chris Turgeon
 *  @version 1.0
 */

import java.lang.Math;

public class Riemann {

  final private String sumType;
  final private int numSteps;
  final private int function;


  /**
   *  Construct a Riemann object that can calculate Riemann sums given
   *  a function code, a type of Riemann sum, and a number of rectangles.
   *  @param sumType - a single character string that represents the
   *                   type of the Riemann sum
   *  @param numSteps - the number of rectangles used to calculate the sum
   *  @param function - an integer that marks which function to use
   */
  public Riemann(String sumType, int numSteps, int function)
    throws IllegalArgumentException {

    if (sumType == null || sumType.isEmpty()) {
      throw new IllegalArgumentException("Riemann sum type is null or empty.");
    }
    Boolean validArg = sumType.equals("l") || sumType.equals("r") ||
                       sumType.equals("m") || sumType.equals("t");
    if (!validArg) {
      throw new IllegalArgumentException("Riemann sum type is not recognized.");
    }
    if (numSteps <= 0) {
      throw new IllegalArgumentException("The number of steps must be greater than 0.");
    }
    if (function < 1 || function > 6) {
      throw new IllegalArgumentException("Function not recognized.");
    }
    this.sumType = sumType;
    this.numSteps = numSteps;
    this.function = function;
  }


  /**
   *  This is a driver method to calculate Riemann sum. This method
   *  swaps the bounds if the right one is less than the left one.
   *
   *  @param left - the left bound of the range
   *  @param right - the right bound of the range
   *  @return a double that represents the sum, it returns NaN if a Riemann
   *          sum type that is loaded into the object is invalid
   */
  public double calculate(double left, double right) {
    if (left > right) { // Swap left and right if left is greater
      double temp = left;
      left = right;
      right = temp;
    }
    if (sumType.equals("l")) { return leftHandSum(left,right);  }
    if (sumType.equals("r")) { return rightHandSum(left,right); }
    if (sumType.equals("m")) { return midpointSum(left,right);  }
    if (sumType.equals("t")) { return trapezoidSum(left,right); }
    return Double.NaN;
  }


  /**
   *  This method calculates a left Riemann sum with a
   *  left bound and a right bound. Left is less than
   *  right.
   *
   *  @param left - left, the left boundary
   *  @param right - right, the right boundary
   *  @return the Riemann approximation
   */
  private double leftHandSum(double left, double right) {
    double segmentSize = (right-left) / numSteps;
    double x = left;
    double area = 0;
    for (int i = 0; i < numSteps; i++) {
      area += fetchValue(x) * segmentSize;
      x = x + segmentSize;
    }
    return area;
  }


  /**
   *  This method calculates a right Riemann sum with a
   *  left bound and a right bound. Left is less than
   *  right.
   *
   *  @param left - left, the left boundary
   *  @param right - right, the right boundary
   *  @return the Riemann approximation
   */
  private double rightHandSum(double left, double right) {
    double segmentSize = (right-left) / numSteps;
    double x = left + segmentSize;
    double area = 0;
    for (int i = 0; i < numSteps; i++) {
      area += fetchValue(x) * segmentSize;
      x = x + segmentSize;
    }
    return area;
  }


  /**
   *  This method calculates a midpoint Riemann sum with
   *  a left bound and a right bound. Left is less than
   *  right.
   *
   *  @param left - left, the left boundary
   *  @param right - right, the right boundary
   *  @return the Riemann approximation
   */
  private double midpointSum(double left, double right) {
    double segmentSize = (right-left) / numSteps;
    double x = segmentSize / 2 + left;
    double area = 0;
    for (int i = 0; i < numSteps; i++) {
      area += fetchValue(x) * segmentSize;
      x = x + segmentSize;
    }
    return area;
  }


  /**
   *  This method calculates a trapezoidal Riemann sum
   *  with a left bound and a right bound. Left is less
   *  than right.
   *
   *  @param left - left, the left boundary
   *  @param right - right, the right boundary
   *  @return the Riemann approximation
   */
   private double trapezoidSum(double left, double right) {
     double segmentSize = (right-left) / numSteps;
     double x1 = left;
     double x2 = left + segmentSize;
     double area = 0;
     for (int i = 0; i < numSteps; i++) {
       area += (fetchValue(x1) + fetchValue(x2)) / 2 * segmentSize;
       x1 = x2;
       x2 = x2 + segmentSize;
     }
     return area;
   }


   /**
    *  This method takes in a value x and computes
    *  f(x) for the function indicated by the user.
    *
    *  @param x - the value x supplied to f(x)
    *  @return f(x) computed as a double, returns NaN
    *          if an unrecognized functoin code is held by
    *          the class
    *  @exception ArithmeticException for div by 0 or improper
    *             natural log bounds
    *
    *  1 --> f(x) = x*x
    *  2 --> f(x) = sin(x)
    *  3 --> f(x) = arctan(x)
    *  4 --> f(x) = x*x*x
    *  5 --> f(x) = sqrt(x)
    *  6 --> f(x) = ln(x)
    */
  private double fetchValue(double x) throws ArithmeticException {
    if (this.function == 1) { return x*x; }
    if (this.function == 2) { return Math.sin(x); }
    if (this.function == 3) { return Math.atan(x); }
    if (this.function == 4) { return x*x*x; }

    // Check if x is non-negative
    if (this.function == 5) {
      if (x < 0) {
        throw new ArithmeticException("sqrt(x) is undefined for x < 0");
      }
      return Math.pow(x, 0.5);
    }

    // Check for invalid domain for natural log
    if (this.function == 6) {
      if (x <= 0) {
        throw new ArithmeticException("Cannot take natural log of zero or negative number");
      }
      return Math.log(x);
    }
    return Double.NaN;
  }
}
