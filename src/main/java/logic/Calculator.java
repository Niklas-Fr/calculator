package logic;

import java.util.ArrayDeque;
import java.util.Deque;

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

    /**
     * The methods performs the arithmetic operation given by a String, containing the input to the Calculator.
     * Operator precedence (multiplication/division before addition/subtraction) is respected via the
     * shunting-yard algorithm.
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

        return evaluate(numbers, operators);
    }

    /**
     * Evaluates a sequence of numbers and the operators between them using the shunting-yard algorithm, so that
     * multiplication and division are applied before addition and subtraction, regardless of their order in the
     * input.
     *
     * @param numbers   the operands, in the order they appear in the expression
     * @param operators the operators between consecutive operands, in the order they appear in the expression
     * @return the result of the operation
     */
    private static double evaluate(double[] numbers, char[] operators) {
        Deque<Double> numberStack = new ArrayDeque<>();
        Deque<Character> operatorStack = new ArrayDeque<>();

        numberStack.push(numbers[0]);
        for (int i = 0; i < operators.length; i++) {
            char operator = operators[i];
            while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(operator)) {
                numberStack.push(applyOperator(numberStack.pop(), numberStack.pop(), operatorStack.pop()));
            }
            operatorStack.push(operator);
            numberStack.push(numbers[i + 1]);
        }

        while (!operatorStack.isEmpty()) {
            numberStack.push(applyOperator(numberStack.pop(), numberStack.pop(), operatorStack.pop()));
        }

        return numberStack.pop();
    }

    /**
     * Returns the precedence of an operator, higher meaning it binds more tightly.
     *
     * @param operator the operator
     * @return the precedence of the operator
     */
    private static int precedence(char operator) {
        if (operator == Operators.MULTIPLY.getSymbol().charAt(0) || operator == Operators.DIVIDE.getSymbol().charAt(0)) {
            return 2;
        }
        return 1;
    }

    /**
     * The method applies a calculation to two numbers, depending on the given operator char and returns the result of
     * the operation.
     *
     * @param secondNum second number, popped first off the number stack
     * @param firstNum  first number, popped second off the number stack
     * @param operator  given operator
     * @return the result of the operation
     */
    private static double applyOperator(double secondNum, double firstNum, char operator) {
        if (operator == Operators.ADD.getSymbol().charAt(0)) return firstNum + secondNum;
        else if (operator == Operators.SUBTRACT.getSymbol().charAt(0)) return firstNum - secondNum;
        else if (operator == Operators.MULTIPLY.getSymbol().charAt(0)) return firstNum * secondNum;
        else if (operator == Operators.DIVIDE.getSymbol().charAt(0)) return firstNum / secondNum;
        return 0;
    }
}
