package logic;

public enum Operators {

    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("×"),
    DIVIDE("÷"),
    EQUALS("="),
    PERCENTAGE("%"),
    COMMA(","),
    DELETE("AC");

    private final String symbol;

    Operators(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
