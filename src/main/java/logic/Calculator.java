package logic;

import java.util.Arrays;

/**
 * The class models the logic behind the Calculator and is responsible for peforming the calculation given by the
 * {@link application.Application}.
 *
 * @author niklasfrietsch
 * @version 1.0
 */
public final class Calculator {
    private static final String SEPARATOR = "[+\\-×÷]";
    private static final String INVERSED_SEPARATOR = "[^+\\-×÷]";
    private static final String PERCENT = "%";
    private static final String DECIMAL_POINT = ".";

    private Calculator() {
    }

    //TODO: order of operations

    /**
     * The methods performs the arithmetic operation given by a String, containing the input to the Calculator.
     * @param parsedCalculation the parsed input of the calculation
     * @return the result of the operation
     */
    public static double performCalculation(String parsedCalculation) {
        String calculation = parsedCalculation;
        boolean firstNegative = false;
        if (String.valueOf(parsedCalculation.charAt(0)).equals((Operators.SUBTRACT.getSymbol()))) {
            firstNegative = true;
            calculation = parsedCalculation.substring(1);
        }

        String[] temp = calculation.split(SEPARATOR);
        double[] numbers = new double[temp.length];

        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].replace(Operators.COMMA.getSymbol(), DECIMAL_POINT);
            if (temp[i].contains(PERCENT)) {
                numbers[i] = Double.parseDouble(temp[i].replaceAll(PERCENT, "")) / 100;
            } else {
                numbers[i] = Double.parseDouble(temp[i]);
            }
        }
        Arrays.stream(temp).forEach(System.out::println);
        if (firstNegative) numbers[0] = -numbers[0];

        char[] operators = calculation.replaceAll(INVERSED_SEPARATOR, "").toCharArray();

        for (int i = 0; i < temp.length - 1; i++) {
            double firstNum = numbers[i];
            double secondNum = numbers[i + 1];

            numbers[i + 1] = calculate(firstNum, secondNum, operators[i]);
        }
        return numbers[numbers.length - 1];
    }

    /**
     * The method applies a calculation to two numbers, depending on the given operator char and returns the result of
     * the operation.
     * @param firstNum first number
     * @param secondNum second number
     * @param operator given operator
     * @return the result of the operation
     */
    private static double calculate(double firstNum, double secondNum, char operator) {
        if (operator == Operators.ADD.getSymbol().charAt(0)) {
            return firstNum + secondNum;
        } else if (operator == Operators.SUBTRACT.getSymbol().charAt(0)) {
            return firstNum - secondNum;
        } else if (operator == Operators.MULTIPLY.getSymbol().charAt(0)) {
            return firstNum * secondNum;
        } else if (operator == Operators.DIVIDE.getSymbol().charAt(0)) {
            return firstNum / secondNum;
        }
        return 0;
    }
}
