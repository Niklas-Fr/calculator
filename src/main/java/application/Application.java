package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Calculator;
import logic.Coordinate;
import logic.Operators;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class models the entry of the application and is responsible for the creation of the graphic interface.
 * Inherits from {@link javafx.application.Application}.
 *
 * @author niklasfrietsch
 * @version 1.0
 */
public class Application extends javafx.application.Application {
    private static final int WIDTH = 195;
    private static final int HEIGHT = 320;

    private static final Coordinate LOWER_LABEL_COORDINATE = new Coordinate(20, 45);
    private static final Coordinate UPPER_LABEL_COORDINATE = new Coordinate(20, 10);

    private static final String PROGRAM_NAME = "Calculator";
    private static final String DEFAULT_SYMBOL = "0";
    private static final String[] CSS_STYLES = {"/Style.css", "panel", "label1", "label2", "calc_button", "number_button", "operate_button"};
    private static final String CALCULATION_OPERATORS = "+-×÷";

    private static final int BUFFER_SIZE = 20;
    private static final int BUTTON_DISTANCE = 45;
    private Label lowerLabel;
    private Label upperLabel;

    /**
     * Main method.
     * @param arg command line arguments
     */
    public static void main(String[] arg) {
        launch(arg);
    }

    // TODO: result should always be visible -> change font size depending on result
    // TODO: how to handel large numbers?
    // TODO: handel overflow method
    // TODO: multiple commata

    @Override
    public void start(Stage stage) {
        lowerLabel = new Label();
        lowerLabel.setLayoutX(LOWER_LABEL_COORDINATE.xPos());
        lowerLabel.setLayoutY(LOWER_LABEL_COORDINATE.yPos());

        upperLabel = new Label();
        upperLabel.setLayoutX(UPPER_LABEL_COORDINATE.xPos());
        upperLabel.setLayoutY(UPPER_LABEL_COORDINATE.yPos());

        Pane pane = new Pane();
        pane.getChildren().add(lowerLabel);
        pane.getChildren().add(upperLabel);

        List<Button> operatorButtons = getOperatorButtons();
        List<Button> numberButtons = getNumberButtons();
        List<Button> extraButtons = getExtraButtons();
        pane.getChildren().addAll(operatorButtons);
        pane.getChildren().addAll(numberButtons);
        pane.getChildren().addAll(extraButtons);

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_STYLES[0])).toExternalForm());

        pane.getStyleClass().add(CSS_STYLES[1]);
        lowerLabel.getStyleClass().add(CSS_STYLES[2]);
        upperLabel.getStyleClass().add(CSS_STYLES[3]);
        operatorButtons.forEach(button -> button.getStyleClass().add(CSS_STYLES[4]));
        numberButtons.forEach(button -> button.getStyleClass().add(CSS_STYLES[5]));
        extraButtons.forEach(button -> button.getStyleClass().add(CSS_STYLES[6]));

        stage.setTitle(PROGRAM_NAME);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Returns a List of type {@link Button}, containing the buttons responsible for the 4 basic operations as well as the result button
     * and implements their behaviour.
     * @return the list of the buttons
     */
    private List<Button> getOperatorButtons() {
        List<Button> buttons = new ArrayList<>();

        buttons.add(new Button(Operators.DIVIDE.getSymbol()));
        buttons.add(new Button(Operators.MULTIPLY.getSymbol()));
        buttons.add(new Button(Operators.SUBTRACT.getSymbol()));
        buttons.add(new Button(Operators.ADD.getSymbol()));

        buttons.forEach(button -> button.setOnAction(event -> {
            if (validateOperation() || button.getText().equals(Operators.SUBTRACT.getSymbol()) && validateNegativePrefix()) {
                lowerLabel.setText(lowerLabel.getText() + button.getText());
                upperLabel.setText("");
                handleOverflow(lowerLabel);
            }
        }));

        buttons.add(new Button(Operators.EQUALS.getSymbol()));
        buttons.getLast().setOnAction(event -> {
            String calulationText = lowerLabel.getText();
            if (validateOperation()) {
                lowerLabel.setText(Calculator.performCalculation(calulationText));
                upperLabel.setText(calulationText);
                handleOverflow(upperLabel);
                lowerLabel.setLayoutX(LOWER_LABEL_COORDINATE.xPos());
                handleOverflow(lowerLabel);
            }
        });

        Coordinate[] operatorPositions = getOperatorButtonPositions();
        for (int i = 0; i < buttons.size(); i++) {
            Button currentButton = buttons.get(i);
            currentButton.setLayoutX(operatorPositions[i].xPos());
            currentButton.setLayoutY(operatorPositions[i].yPos());
        }
        return buttons;
    }

    /**
     * Returns a List of type {@link Button}, containing the buttons responsible for the extra operations, such as
     * percentage, decimal points etc. and implements their behaviour.
     * @return the list of the buttons
     */
    private List<Button> getExtraButtons() {
        List<Button> buttons = new ArrayList<>();

        Button removeButton = new Button(Operators.REMOVE.getSymbol());
        removeButton.setOnAction(event -> {
            String text = lowerLabel.getText();
            lowerLabel.setText(text.length() <= 1 ? "" : text.substring(0, text.length() - 1));
            lowerLabel.setLayoutX(LOWER_LABEL_COORDINATE.xPos());
            handleOverflow(lowerLabel);
        });
        buttons.add(removeButton);

        Button percButton = new Button(Operators.PERCENTAGE.getSymbol());
        percButton.setOnAction(event -> {
            lowerLabel.setText(lowerLabel.getText() + percButton.getText());
            handleOverflow(lowerLabel);
        });
        buttons.add(percButton);

        Button delButton = new Button(Operators.DELETE.getSymbol());
        delButton.setOnAction(event -> {
            lowerLabel.setText(DEFAULT_SYMBOL);
            upperLabel.setText("");
            lowerLabel.setLayoutX(LOWER_LABEL_COORDINATE.xPos());
        });
        buttons.add(delButton);

        Button floatButton = new Button(Operators.COMMA.getSymbol());
        floatButton.setOnAction(event -> {
            lowerLabel.setText(lowerLabel.getText() + floatButton.getText());
            handleOverflow(lowerLabel);
        });
        buttons.add(floatButton);

        Button negativeButton = new Button(Operators.NEGATIVE.getSymbol());
        negativeButton.setOnAction(event -> {
            lowerLabel.setText(lowerLabel.getText() + negativeButton.getText());
            handleOverflow(lowerLabel);
        });
        buttons.add(negativeButton);

        Coordinate[] extraPositions = getExtraButtonPositions();
        for (int i = 0; i < buttons.size(); i++) {
            Button currentButton = buttons.get(i);
            currentButton.setLayoutX(extraPositions[i].xPos());
            currentButton.setLayoutY(extraPositions[i].yPos());
        }
        return buttons;
    }

    /**
     * Returns a List of type {@link Button}, containing the 10 number buttons and implements their behaviour.
     * @return the list of the buttons
     */
    private List<Button> getNumberButtons() {
        List<Button> buttons = new ArrayList<>();

        Coordinate[] numberPositions = getNumberButtonPoistions();
        for (int i = 0; i < 10; i++) {
            Button currentButton = new Button(String.valueOf(i));
            currentButton.setLayoutX(numberPositions[i].xPos());
            currentButton.setLayoutY(numberPositions[i].yPos());

            currentButton.setOnAction(event -> {
                lowerLabel.setText((lowerLabel.getText().equals(DEFAULT_SYMBOL) || !upperLabel.getText().isEmpty() ? "" :
                        lowerLabel.getText()) + currentButton.getText());
                upperLabel.setText("");
                handleOverflow(lowerLabel);
            });
            buttons.add(currentButton);
        }
        return buttons;
    }

    private boolean validateOperation() {
        return !lowerLabel.getText().isEmpty() && !isOperation(lowerLabel.getText().charAt(lowerLabel.getText().length() - 1));
    }

    private boolean validateNegativePrefix() {
        return lowerLabel.getText().isEmpty() || lowerLabel.getText().charAt(lowerLabel.getText().length() - 1) == Operators.SUBTRACT.getSymbol().charAt(0);
    }

    private boolean isOperation(char lastCharacter) {
        return CALCULATION_OPERATORS.lastIndexOf(lastCharacter) != -1;
    }

    /**
     * Helper method, returns the positions of the Operator Buttons
     * @return Array of the Coordinates
     */
    private Coordinate[] getOperatorButtonPositions() {
        Coordinate[] operatorCoordinates = new Coordinate[6];
        for (int i = 0; i < operatorCoordinates.length; i++) {
            operatorCoordinates[i] = new Coordinate(145, (i + 2) * BUTTON_DISTANCE);
        }
        return operatorCoordinates;
    }

    /**
     * Helper method, returns the positions of the Extra Buttons
     * @return Array of the Coordinates
     */
    private Coordinate[] getExtraButtonPositions() {
        return new Coordinate[]{new Coordinate(10, 90), new Coordinate(100, 90),
                new Coordinate(10, 270), new Coordinate(100, 270), new Coordinate(55, 90)};
    }

    /**
     * Helper method, returns the positions of the Number Buttons
     * @return Array of the Coordinates
     */
    private Coordinate[] getNumberButtonPoistions() {
        Coordinate[] numberPositions = new Coordinate[10];
        numberPositions[0] = new Coordinate(55, 270);
        for (int i = 1; i < numberPositions.length; i++) {
            int columnIndex = (i - 1) % 3;
            int xPos = 10 + columnIndex * BUTTON_DISTANCE;
            int rowIndex = (i - 1) / 3;
            int yPos = (3 + rowIndex) * BUTTON_DISTANCE;
            numberPositions[i] = new Coordinate(xPos, yPos);
        }
        return numberPositions;
    }

    /**
     * Handles overflow for a given {@link Label}, meaning if part of the label reaches out of the panel, the label is
     * moved to the left accordingly.
     * @param label the label to handle
     */
    private void handleOverflow(Labeled label) {
        Text text = new Text(label.getText());
        text.setFont(label.getFont());
        double textWidth = text.getLayoutBounds().getWidth();

        if (label.getLayoutX() + textWidth > WIDTH - BUFFER_SIZE) {
            double overflow = label.getLayoutX() + textWidth - WIDTH + BUFFER_SIZE;
            label.setLayoutX(label.getLayoutX() - overflow);
        }
    }
}
