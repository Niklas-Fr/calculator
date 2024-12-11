package logic;

public final class Calculator {

    private Calculator() {

    }

    public static int add(int intA, int intB) {
        return intA + intB;
    }

    public static int subtract(int intA, int intB) {
        return intA - intB;
    }

    public static int multiply(int intA, int intB) {
        return intA * intB;
    }

    public static double divide(int intA, int intB) {
        return (double) intA / intB;
    }
}
