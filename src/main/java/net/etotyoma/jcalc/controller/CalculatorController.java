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
            clickedButton.setStyle("-fx-background-color: derive(#DCD7C9, 40%)");
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
        double currentValue = Double.parseDouble(output.getText());

        switch (value) {
            case "%" -> {
                double percentValue;
                if (operator.isEmpty()) {
                    percentValue = currentValue / 100;
                    output.setText(String.valueOf(percentValue));
                } else {
                    num2 = currentValue;
                    percentValue = num1 * (num2 / 100);
                    output.setText(String.valueOf(percentValue));
                }
            }
            case "." -> {
                if (!output.getText().contains("."))
                    output.setText(output.getText() + ".");
            }
            case "±" -> {
                if (currentValue % 1 == 0)
                    output.setText(String.valueOf((int) -currentValue));
                else
                    output.setText(String.valueOf(-currentValue));
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
