import logic.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Class for Testing the {@link Calculator} and its method {@link Calculator#performCalculation(String)}.
 * @author niklasfrietsch
 * @version 10
 */
class TestCalculator {

    /**
     * Test addition and subtraction.
     */
    @Test
    void addAndSubtract() {
        double result = Calculator.performCalculation("2+52-6");
        double expectedResult = 2 + 52 - 6;
        Assertions.assertEquals(expectedResult, result);
    }

    /**
     * Tests multiplication.
     */
    @Test
    void multiply() {
        double result = Calculator.performCalculation("3×6-5");
        double expectedResult = 3 * 6 - 5;
        Assertions.assertEquals(expectedResult, result);
    }

    /**
     * Tests division.
     */
    @Test
    void division() {
        double result = Calculator.performCalculation("51÷3+9");
        double expectedResult = (double) 51 / 3 + 9;
        Assertions.assertEquals(expectedResult, result);
    }
}
