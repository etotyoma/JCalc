package net.etotyoma.jcalc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import net.etotyoma.jcalc.core.CalculatorLogic;
import net.etotyoma.jcalc.core.CalculatorLogger;

public class CalculatorController {

    private double num1;
    private double num2;
    private boolean start = true;
    private boolean negativeToggle = false;
    private boolean hasActiveCalculation = false;
    private Button activeOperatorButton = null;
    private String operator = "";

    @FXML
    private Label output;
    @FXML
    private Button clearButton;

    @FXML
    protected void onNumpadButtonClick(MouseEvent event) {
        if (start) {
            output.setText("");
            start = false;
        }

        if (activeOperatorButton != null)
            activeOperatorButton.setStyle("-fx-background-color: #DCD7C9");

        hasActiveCalculation = true;
        String value = ((Button)event.getSource()).getText();
        output.setText(output.getText() + value);
        updateClearButton();
    }

    @FXML
    protected void onOperatorButtonClick(MouseEvent event) {
        Button clickedButton = (Button) event.getSource();
        String value = clickedButton.getText();

        if (activeOperatorButton != null)
            activeOperatorButton.setStyle("-fx-background-color: #DCD7C9");

        if (!value.equals("=")) {
            if (!operator.isEmpty()) {
                num2 = Double.parseDouble(output.getText());
                String result = CalculatorLogic.calculate(num1, num2, operator);

                if (!result.equals("Error"))
                    CalculatorLogger.log(String.format("%s %s %s = %s", num1, operator, num2, result), CalculatorLogger.LogLevel.INFO);

                output.setText(result);
                start = true;
                num1 = Double.parseDouble(result);
            }
            clickedButton.setStyle("-fx-background-color: #ffffff");
            activeOperatorButton = clickedButton;

            operator = value;
            num1 = Double.parseDouble(output.getText());
            start = true;
        } else {
            if (operator.isEmpty())
                return;
            if (output.getText().isEmpty()) {
                operator = "";
                start = true;
            }
            num2 = Double.parseDouble(output.getText());
            String result = CalculatorLogic.calculate(num1, num2, operator);

            if (!result.equals("Error"))
                CalculatorLogger.log(String.format("%s %s %s = %s", num1, operator, num2, result), CalculatorLogger.LogLevel.INFO);

            output.setText(result);
            start = true;
            operator = "";
        }
        updateClearButton();
    }

    @FXML
    protected void onActionButtonClick(MouseEvent event) {
        String value = ((Button)event.getSource()).getText();
        String currentValue = output.getText();

        double percentValue;
        if (value.equals("%")) {
            if (operator.isEmpty()) {
                percentValue = Double.parseDouble(currentValue) / 100;
                output.setText(String.valueOf(percentValue));
            } else {
                num2 = Double.parseDouble(currentValue);
                percentValue = num1 * (num2 / 100);
                output.setText(String.valueOf(percentValue));
            }
            return;
        }

        if (value.equals(".")) {
            if (output.getText().contains("."))
                return;
            else
                output.setText(output.getText() + ".");
            return;
        }

        if (!(output.getText().contains("-"))) {
            if (value.equals("±")) {
                if (!negativeToggle) {
                    output.setText("-" + output.getText());
                    negativeToggle = true;
                }
            }
        } else {
            if (value.equals("±")) {
                if (negativeToggle) {
                    output.setText(output.getText().replace("-", ""));
                    negativeToggle = false;
                }
            }
        }
    }

    @FXML
    protected void onClearButtonClick() {
        if (clearButton.getText().equals("AC")) {
            CalculatorLogger.log("Calculator cleared (AC)", CalculatorLogger.LogLevel.INFO);
            output.setText("0");
            start = true;
            operator = "";
            hasActiveCalculation = false;
            if (activeOperatorButton != null) {
                activeOperatorButton.setStyle("-fx-background-color: #DCD7C9");
                activeOperatorButton = null;
            }
        } else {
            output.setText("0");
            start = true;
        }
        updateClearButton();
    }

    private void updateClearButton() {
        if (!output.getText().isEmpty() && hasActiveCalculation) {
            clearButton.setText("C");
        }
        if (output.getText().equals("0"))
            clearButton.setText("AC");
    }
}
