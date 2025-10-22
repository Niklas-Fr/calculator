package logic;

/**
 * List of the different operators used with their representative symbol.
 *
 * @author niklasfrietsch
 * @version 1.0
 */
public enum Operators {

    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("×"),
    DIVIDE("÷"),
    EQUALS("="),
    PERCENTAGE("%"),
    COMMA(","),
    NEGATIVE("—"),
    DELETE("AC"),
    REMOVE("⌫");

    private final String symbol;

    Operators(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Getter for the symbol of the operators
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }
}
