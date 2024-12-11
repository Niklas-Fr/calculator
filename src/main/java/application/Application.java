package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.Calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Application extends javafx.application.Application {
    private static final int WIDTH = 195;
    private static final int HEIGHT = 320;

    private static final int DEFAULT_SIZE = 40;
    private static final int CALC_BUTTONS_X = 145;
    private static final int[] CALC_BUTTONS_Y = {225, 180, 135, 90, 270};
    private static final String PROGRAM_NAME = "Calculator";
    private static final String ROUNDING_FORMAT = "%.2f";

    public static void main(String[] arg) {
        launch(arg);
    }

    @Override
    public void start(Stage primaryStage) {
        Labeled resultLabel = new Label();
        resultLabel.setPrefSize(DEFAULT_SIZE, DEFAULT_SIZE);
        resultLabel.setVisible(false);
        resultLabel.setLayoutX(CALC_BUTTONS_X + 10);
        resultLabel.setLayoutY(50);

        List<Button> buttons = new ArrayList<>();

        Button addButton = new Button("+");
        addButton.setLayoutX(CALC_BUTTONS_X);
        addButton.setLayoutY(CALC_BUTTONS_Y[0]);

        Button subButton = new Button("-");
        subButton.setLayoutX(CALC_BUTTONS_X);
        subButton.setLayoutY(CALC_BUTTONS_Y[1]);

        Button mulButton = new Button("×");
        mulButton.setLayoutX(CALC_BUTTONS_X);
        mulButton.setLayoutY(CALC_BUTTONS_Y[2]);

        Button divButton = new Button("÷");
        divButton.setLayoutX(CALC_BUTTONS_X);
        divButton.setLayoutY(CALC_BUTTONS_Y[3]);

        Button resButton = new Button("=");
        resButton.setLayoutX(CALC_BUTTONS_X);
        resButton.setLayoutY(CALC_BUTTONS_Y[4]);

        buttons.add(addButton);
        buttons.add(subButton);
        buttons.add(mulButton);
        buttons.add(divButton);
        buttons.add(resButton);

        Pane pane = new Pane();
        pane.getStyleClass().add("panel");
        pane.getChildren().addAll(buttons);
        pane.getChildren().add(resultLabel);

        int a = 5;
        int b = 3;

        addButton.setOnAction(event -> resultLabel.setText(String.valueOf(Calculator.add(a,b))));
        subButton.setOnAction(event -> resultLabel.setText(String.valueOf(Calculator.subtract(a,b))));
        mulButton.setOnAction(event -> resultLabel.setText(String.valueOf(Calculator.multiply(a,b))));
        divButton.setOnAction(event -> resultLabel.setText(ROUNDING_FORMAT.formatted(Calculator.divide(a,b))));
        resButton.setOnAction(event -> resultLabel.setVisible(true));

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Style.css")).toExternalForm());
        buttons.forEach(button -> button.getStyleClass().add("calc_button"));

        primaryStage.setTitle(PROGRAM_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
