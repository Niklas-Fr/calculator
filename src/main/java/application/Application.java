package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Calculator;
import logic.Coordinate;
import logic.Operators;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Application extends javafx.application.Application {
    private static final int WIDTH = 195;
    private static final int HEIGHT = 320;

    private static final Coordinate RESULT_LABEL_COORDINATE = new Coordinate(20, 45);
    private static final Coordinate OPERATION_LABEL_COORDINATE = new Coordinate(20, 10);

    private static final Coordinate[] CALC_COORDINATES = {new Coordinate(145, 90),
            new Coordinate(145, 135), new Coordinate(145, 180), new Coordinate(145, 225),
            new Coordinate(145, 270), new Coordinate(145, 310)};

    private static final Coordinate[] INPUT_COORDINATES = {new Coordinate(10, 90), new Coordinate(100, 90),
            new Coordinate(10, 270), new Coordinate(100, 270)
    };

    private static final Coordinate[] NUMBER_COORDINATES = {new Coordinate(55, 270), new Coordinate(10, 135),
            new Coordinate(55, 135), new Coordinate(100, 135), new Coordinate(10, 180),
            new Coordinate(55, 180), new Coordinate(100, 180), new Coordinate(10, 225),
            new Coordinate(55, 225), new Coordinate(100, 225)
    };

    private static final String PROGRAM_NAME = "Calculator";
    private static final String STANDARD_SYMBOL = "0";
    private static final String[] CSS_STYLES = {"/Style.css", "panel", "label1", "label2", "calc_button", "number_button", "operate_button"};
    private static final String DELETE_BUTTON_UNICODE = "\u232B";
    private static final String CALCULATION_OPERATORS = "+-×÷";
    private static final String ROUNDING_FORMAT = "%.2f";
    private Label resultLabel;
    private Label operationLabel;

    public static void main(String[] arg) {
        launch(arg);
    }

    // TODO: parse calculation
    // TODO: parentheses ()
    // TODO: adapt label size to input
    //TODO: font size?
    // TODO: first symobl - bzw. - extra case
    //TODO: commata
    //TODO: percentae %
    @Override
    public void start(Stage stage) {
        resultLabel = new Label();
        resultLabel.setLayoutX(RESULT_LABEL_COORDINATE.xPos());
        resultLabel.setLayoutY(RESULT_LABEL_COORDINATE.yPos());

        operationLabel = new Label();
        operationLabel.setLayoutX(OPERATION_LABEL_COORDINATE.xPos());
        operationLabel.setLayoutY(OPERATION_LABEL_COORDINATE.yPos());

        List<Button> calculationButtons = getCalculationButtons();

        Pane pane = new Pane();
        pane.getStyleClass().add(CSS_STYLES[1]);
        pane.getChildren().addAll(calculationButtons);
        pane.getChildren().add(resultLabel);
        pane.getChildren().add(operationLabel);

        List<Button> numberButtons = getNumberButtons();
        List<Button> operationButtons = getOperationButtons();
        pane.getChildren().addAll(numberButtons);
        pane.getChildren().addAll(operationButtons);

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_STYLES[0])).toExternalForm());

        resultLabel.getStyleClass().add(CSS_STYLES[2]);
        operationLabel.getStyleClass().add(CSS_STYLES[3]);
        calculationButtons.forEach(button -> button.getStyleClass().add(CSS_STYLES[4]));
        numberButtons.forEach(button -> button.getStyleClass().add(CSS_STYLES[5]));
        operationButtons.forEach(button -> button.getStyleClass().add(CSS_STYLES[6]));

        stage.setTitle(PROGRAM_NAME);
        stage.setScene(scene);
        stage.show();
    }

    private boolean validateOperation() {
        return !resultLabel.getText().isEmpty() && !isOperation(resultLabel.getText().charAt(resultLabel.getText().length() - 1));
    }

    private boolean isOperation(char lastCharacter) {
        return CALCULATION_OPERATORS.lastIndexOf(lastCharacter) != -1;
    }

    private List<Button> getCalculationButtons() {
        List<Button> buttons = new ArrayList<>();

        buttons.add(new Button(Operators.DIVIDE.getSymbol()));
        buttons.add(new Button(Operators.MULTIPLY.getSymbol()));
        buttons.add(new Button(Operators.SUBTRACT.getSymbol()));
        buttons.add(new Button(Operators.ADD.getSymbol()));

        buttons.forEach(button -> button.setOnAction(event -> {
            if (validateOperation()) {
                resultLabel.setText(resultLabel.getText() + button.getText());
                operationLabel.setText("");
            }
        }));

        buttons.add(new Button(Operators.EQUALS.getSymbol()));

        buttons.getLast().setOnAction(event -> {
            String calulationText = resultLabel.getText();
            if (validateOperation()) {
                resultLabel.setText(String.valueOf(Calculator.performCalculation(calulationText)));
                operationLabel.setText(calulationText);
            }
        });

        for (int i = 0; i < buttons.size(); i++) {
            Button currentButton = buttons.get(i);
            currentButton.setLayoutX(CALC_COORDINATES[i].xPos());
            currentButton.setLayoutY(CALC_COORDINATES[i].yPos());
        }
        return buttons;
    }

    private List<Button> getOperationButtons() {
        List<Button> buttons = new ArrayList<>();

        Button removeButton = new Button(DELETE_BUTTON_UNICODE);
        removeButton.setOnAction(event -> {
            String text = resultLabel.getText();
            resultLabel.setText(text.length() <= 1 ? "" : text.substring(0, text.length() - 1));
        });
        buttons.add(removeButton);

        Button percButton = new Button(Operators.PERCENTAGE.getSymbol());
        percButton.setOnAction(event -> resultLabel.setText(resultLabel.getText() + percButton.getText()));
        buttons.add(percButton);

        Button delButton = new Button(Operators.DELETE.getSymbol());
        delButton.setOnAction(event -> {
            resultLabel.setText(STANDARD_SYMBOL);
            operationLabel.setText("");
        });
        buttons.add(delButton);

        Button floatButton = new Button(Operators.COMMA.getSymbol());
        floatButton.setOnAction(event -> resultLabel.setText(resultLabel.getText() + floatButton.getText()));
        buttons.add(floatButton);

        for (int i = 0; i < buttons.size(); i++) {
            Button currentButton = buttons.get(i);
            currentButton.setLayoutX(INPUT_COORDINATES[i].xPos());
            currentButton.setLayoutY(INPUT_COORDINATES[i].yPos());
        }

        return buttons;
    }

    private List<Button> getNumberButtons() {
        List<Button> buttons = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Button currentButton = new Button(String.valueOf(i));
            currentButton.setLayoutX(NUMBER_COORDINATES[i].xPos());
            currentButton.setLayoutY(NUMBER_COORDINATES[i].yPos());

            currentButton.setOnAction(event -> {
                resultLabel.setText((resultLabel.getText().equals(STANDARD_SYMBOL) ? "" : resultLabel.getText()) + currentButton.getText());
                operationLabel.setText("");
            });
            buttons.add(currentButton);
        }
        return buttons;
    }
}
