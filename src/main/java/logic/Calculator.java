package logic;

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
    private static final String DECIMAL_POINT = ".";

    private Calculator() {
    }

    // TODO: order of operations -> shunting yard algorithm
    /**
     * The methods performs the arithmetic operation given by a String, containing the input to the Calculator.
     *
     * @param parsedCalculation the parsed input of the calculation
     * @return the result of the operation
     */
    public static double performCalculation(String parsedCalculation) {
        String[] tokens = parsedCalculation.split(SEPARATOR);
        double[] numbers = new double[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].replace(Operators.COMMA.getSymbol(), DECIMAL_POINT);
            boolean negative = tokens[i].contains(Operators.NEGATIVE.getSymbol());
            tokens[i] = tokens[i].replaceAll(Operators.NEGATIVE.getSymbol(), "");

            if (tokens[i].contains(Operators.PERCENTAGE.getSymbol())) {
                numbers[i] = Double.parseDouble(tokens[i].replaceAll(Operators.PERCENTAGE.getSymbol(), "")) / 100;
            } else {
                numbers[i] = Double.parseDouble(tokens[i]);
            }

            if (negative) numbers[i] = -numbers[i];
        }


        char[] operators = parsedCalculation.replaceAll(INVERSED_SEPARATOR, "").toCharArray();

        for (int i = 0; i < numbers.length - 1; i++) {
            double firstNum = numbers[i];
            double secondNum = numbers[i + 1];

            numbers[i + 1] = calculate(firstNum, secondNum, operators[i]);
        }

        return numbers[numbers.length - 1];
    }

    /**
     * The method applies a calculation to two numbers, depending on the given operator char and returns the result of
     * the operation.
     *
     * @param firstNum  first number
     * @param secondNum second number
     * @param operator  given operator
     * @return the result of the operation
     */
    private static double calculate(double firstNum, double secondNum, char operator) {
        if (operator == Operators.ADD.getSymbol().charAt(0)) return firstNum + secondNum;
        else if (operator == Operators.SUBTRACT.getSymbol().charAt(0)) return firstNum - secondNum;
        else if (operator == Operators.MULTIPLY.getSymbol().charAt(0)) return firstNum * secondNum;
        else if (operator == Operators.DIVIDE.getSymbol().charAt(0)) return firstNum / secondNum;
        return 0;
    }
}
