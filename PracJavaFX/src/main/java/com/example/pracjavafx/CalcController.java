package com.example.pracjavafx;

import ASTTree.CalcParser;
import ASTTree.Node;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcController implements Initializable {
    @FXML
    private TextField display;
    @FXML
    private AnchorPane pane;
    @FXML
    private GridPane grid;
    @FXML
    private Button zero, one, two, three, four, five, six, seven, eight, nine, point, plus, minus, multiply, divide, equals, backspace,
            percent, clearAfterOperator, clearAll, oneOverX, xSquare, squareRoot, positiveNegative;

    private Timeline longPressTimeline;
    private CalculatorBackground calculatorBackground;
    private boolean isSpaceHeld = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calculatorBackground = new CalculatorBackground();
        longPressTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            try {
                calculatorBackground.openBackgroundChoose(grid);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        longPressTimeline.setCycleCount(1);

        display.setEditable(false);
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setText("0");

        AnchorPane.setTopAnchor(display, 0.0);
        AnchorPane.setLeftAnchor(display, 0.0);
        AnchorPane.setRightAnchor(display, 0.0);
        display.prefHeightProperty().bind(pane.heightProperty().multiply(0.2));
        display.prefWidthProperty().bind(pane.widthProperty());

        AnchorPane.setLeftAnchor(grid, 0.0);
        AnchorPane.setRightAnchor(grid, 0.0);
        AnchorPane.setBottomAnchor(grid, 0.0);
        grid.prefHeightProperty().bind(pane.heightProperty().multiply(0.8));
        grid.prefWidthProperty().bind(pane.widthProperty());

        for (Button button : new Button[]{zero, one, two, three, four, five, six, seven, eight, nine, point, plus, minus, multiply, divide, equals, backspace,
                percent, clearAfterOperator, clearAll, oneOverX, xSquare, squareRoot, positiveNegative}) {

            button.prefWidthProperty().bind(grid.widthProperty().divide(4));
            button.prefHeightProperty().bind(grid.heightProperty().divide(6));

            button.styleProperty().bind(
                    button.prefHeightProperty().divide(2.5).asString("-fx-font-size: %.0fpx;")
            );
        }
        display.styleProperty().bind(display.prefHeightProperty().divide(2.5).asString("-fx-font-size: %.0fpx;"));
    }


    public void handleKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case NUMPAD0:
                keyPressedHighlight(zero);
                zero();
                break;
            case DIGIT0:
                keyPressedHighlight(zero);
                zero();
                break;
            case NUMPAD1:
                keyPressedHighlight(one);
                one();
                break;
            case DIGIT1:
                keyPressedHighlight(one);
                one();
                break;
            case NUMPAD2:
                keyPressedHighlight(two);
                two();
                break;
            case DIGIT2:
                keyPressedHighlight(two);
                two();
                break;
            case NUMPAD3:
                keyPressedHighlight(three);
                three();
                break;
            case DIGIT3:
                keyPressedHighlight(three);
                three();
                break;
            case NUMPAD4:
                keyPressedHighlight(four);
                four();
                break;
            case DIGIT4:
                keyPressedHighlight(four);
                four();
                break;
            case NUMPAD5:
                keyPressedHighlight(five);
                five();
                break;
            case DIGIT5:
                keyPressedHighlight(five);
                five();
                break;
            case NUMPAD6:
                keyPressedHighlight(six);
                six();
                break;
            case DIGIT6:
                keyPressedHighlight(six);
                six();
                break;
            case NUMPAD7:
                keyPressedHighlight(seven);
                seven();
                break;
            case DIGIT7:
                keyPressedHighlight(seven);
                seven();
                break;
            case NUMPAD8:
                keyPressedHighlight(eight);
                eight();
                break;
            case DIGIT8:
                keyPressedHighlight(eight);
                eight();
                break;
            case NUMPAD9:
                keyPressedHighlight(nine);
                nine();
                break;
            case DIGIT9:
                keyPressedHighlight(nine);
                nine();
                break;
            case DECIMAL:
                keyPressedHighlight(point);
                point();
                break;
            case ADD:
                keyPressedHighlight(plus);
                plus();
                break;
            case SUBTRACT:
                keyPressedHighlight(minus);
                minus();
                break;
            case MULTIPLY:
                keyPressedHighlight(multiply);
                multiply();
                break;
            case DIVIDE:
                keyPressedHighlight(divide);
                divide();
                break;
            case ENTER:
                keyPressedHighlight(equals);
                equals();
                break;
            case BACK_SPACE:
                keyPressedHighlight(backspace);
                backspace();
                break;
            case SPACE:
                keyPressedHighlight(clearAll);
                clearAll();
                if(!isSpaceHeld){
                    isSpaceHeld = true;
                    longPressTimeline.playFromStart();
                }
                break;
            default:
                break;
        }
    }

    public void handleKeyReleased(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.SPACE) {
            longPressTimeline.stop();
            isSpaceHeld = false;
        }
    }

    private void keyPressedHighlight(Button button) {
        button.getStyleClass().add("highlight");
        PauseTransition pause = new PauseTransition(Duration.millis(100));
        pause.setOnFinished(event -> {
            button.getStyleClass().remove("highlight");
        });
        pause.play();
    }

    @FXML
    protected void removeFocus(){
        grid.requestFocus();
    }

    @FXML
    protected void percent() {
        if (display.getText().isEmpty()) {
            display.setText("");
            return;
        }
        display.setText(display.getText() + "%");
    }

    @FXML
    protected void clearAfterOperator() {
        String fromDisplay = display.getText();
        Pattern pattern = Pattern.compile("[+\\-×÷%]");
        Matcher matcher = pattern.matcher(fromDisplay);

        int lastOperatorIndex = -1;
        while (matcher.find()) {
            lastOperatorIndex = matcher.start();
        }
        if (lastOperatorIndex != -1) {
            display.setText(fromDisplay.substring(0, lastOperatorIndex + 1));
        } else {
            display.setText(fromDisplay);
        }
    }

    @FXML
    protected void clearAll() {
        display.setText("");
    }

    @FXML
    protected void backspace() {
        String displayText = display.getText();
        if (!displayText.isEmpty()) {
            display.setText(displayText.substring(0, displayText.length() - 1));
        }
    }

    @FXML
    protected void oneOverX() {
        if (display.getText().isEmpty()) {
            display.setText("");
            return;
        }
        double res = 1 / Double.parseDouble(display.getText());
        display.setText(String.valueOf(res));
    }

    @FXML
    protected void xSquare() {
        if (display.getText().isEmpty()) {
            display.setText("");
            return;
        }
        double x = Double.parseDouble(display.getText());
        double res = x * x;
        if (res % 2 == 0 || res % 2 == 1) {
            display.setText(String.valueOf((int) res));
        } else {
            display.setText(String.valueOf(res));
        }
    }

    @FXML
    protected void squareRoot() {
        if (display.getText().isEmpty()) {
            display.setText("");
            return;
        }
        double res = Math.sqrt(Double.parseDouble(display.getText()));
        if (res % 2 == 0 || res % 2 == 1) {
            display.setText(String.valueOf((int) res));
        } else {
            display.setText(String.valueOf(res));
        }
    }

    @FXML
    protected void divide() {
        if (display.getText().isEmpty()) {
            display.setText("");
            return;
        }
        display.setText(display.getText() + "÷");
    }

    @FXML
    protected void multiply() {
        if (display.getText().isEmpty()) {
            display.setText("");
            return;
        }
        display.setText(display.getText() + "×");
    }

    @FXML
    protected void minus() {
        if (display.getText().isEmpty()) {
            display.setText("");
            return;
        }
        display.setText(display.getText() + "-");
    }

    @FXML
    protected void plus() {
        if (display.getText().isEmpty()) {
            display.setText("");
            return;
        }
        display.setText(display.getText() + "+");
    }

    @FXML
    protected void equals() {
        if (display.getText().isEmpty() || display.getText().equals("0")) {
            display.setText("0");
        } else if (display.getText().length() == 1) {
            display.setText(display.getText());
        } else {
            CalcParser parser = new CalcParser(display.getText());
            Node root = parser.parse();
            double res = root.getValue();
            if (res % 2 == 0 || res % 2 == 1) {
                display.setText(String.valueOf((int) res));
            } else {
                display.setText(String.valueOf(res));
            }
        }
    }

    @FXML
    protected void point() {
        if (display.getText().isEmpty()) {
            display.setText("");
            return;
        }
        display.setText(display.getText() + ".");
    }

    @FXML
    protected void positiveNegative() {
        double res = Double.parseDouble(display.getText());
        if (res < 0) {
            res += 2 * (-res);
        } else if (res > 0) {
            res -= 2 * res;
        } else {
            display.setText("0");
        }
        if (res % 2 == 0 || res % 2 == 1 || res % 2 == -1) {
            display.setText(String.valueOf((int) res));
        } else {
            display.setText(String.valueOf(res));
        }
    }

    @FXML
    protected void zero() {
        if (display.getText().isEmpty() || display.getText().equals("0")) {
            display.setText("");
        }
        display.setText(display.getText() + "0");
    }

    @FXML
    protected void one() {
        if (display.getText().isEmpty() || display.getText().equals("0")) {
            display.setText("");
        }
        display.setText(display.getText() + "1");
    }

    @FXML
    protected void two() {
        if (display.getText().isEmpty() || display.getText().equals("0")) {
            display.setText("");
        }
        display.setText(display.getText() + "2");
    }

    @FXML
    protected void three() {
        if (display.getText().isEmpty() || display.getText().equals("0")) {
            display.setText("");
        }
        display.setText(display.getText() + "3");
    }

    @FXML
    protected void four() {
        if (display.getText().isEmpty() || display.getText().equals("0")) {
            display.setText("");
        }
        display.setText(display.getText() + "4");
    }

    @FXML
    protected void five() {
        if (display.getText().isEmpty() || display.getText().equals("0")) {
            display.setText("");
        }
        display.setText(display.getText() + "5");
    }

    @FXML
    protected void six() {
        if (display.getText().isEmpty() || display.getText().equals("0")) {
            display.setText("");
        }
        display.setText(display.getText() + "6");
    }

    @FXML
    protected void seven() {
        if (display.getText().isEmpty() || display.getText().equals("0")) {
            display.setText("");
        }
        display.setText(display.getText() + "7");
    }

    @FXML
    protected void eight() {
        if (display.getText().isEmpty() || display.getText().equals("0")) {
            display.setText("");
        }
        display.setText(display.getText() + "8");
    }

    @FXML
    protected void nine() {
        if (display.getText().isEmpty() || display.getText().equals("0")) {
            display.setText("");
        }
        display.setText(display.getText() + "9");
    }
}