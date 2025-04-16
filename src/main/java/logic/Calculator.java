package logic;

public final class Calculator {
    private static final String SEPARATOR = "[+\\-×÷]";
    private static final String INVERSED_SEPARATOR = "[^+\\-×÷]";
    private static final String PERCENT = "%";
    private static final String DECIMAL_POINT = ".";

    private Calculator() {
    }

    //TODO: handle negative numbers
    //TODO: punkt vor strich
    public static double performCalculation(String caluculation) {
        String[] temp = caluculation.split(SEPARATOR);
        double[] numbers = new double[temp.length];

        for (int i = 0; i < temp.length; i++) {
            temp[i] = temp[i].replace(Operators.COMMA.getSymbol(), DECIMAL_POINT);
            if (temp[i].contains(PERCENT)) {
                numbers[i] = Double.parseDouble(temp[i].replaceAll(PERCENT, "")) / 100;
            } else {
                System.out.println("temp:" + temp[i]);
                numbers[i] = Double.parseDouble(temp[i]);
            }
        }
        char[] operators = caluculation.replaceAll(INVERSED_SEPARATOR, "").toCharArray();

        for (int i = 0; i < temp.length - 1; i++) {
            double firstNum = numbers[i];
            double secondNum = numbers[i + 1];

            numbers[i + 1] = calculate(firstNum, secondNum, operators[i]);
        }
        return numbers[numbers.length - 1];
    }

    private static double calculate(double firstNum, double secondNum, char operator) {
        return switch (operator) {
            case '+' -> firstNum + secondNum;
            case '-' -> firstNum - secondNum;
            case '×' -> firstNum * secondNum;
            case '÷' -> firstNum / secondNum;
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };
    }
}
