package net.etotyoma.jcalc.core;

public class CalculatorLogic {

    public static String calculate(double num1, double num2, String op) {
        double result = 0;

        switch (op) {
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "X" -> result = num1 * num2;
            case "÷" -> {
                if (num2 == 0) {
                    CalculatorLogger.log("Division by zero! (" + num1 + " ÷ 0)", CalculatorLogger.LogLevel.ERROR);
                    return "Error";
                }
                result = num1 / num2;
            }
        }

        if (result % 1 == 0)
            return String.valueOf((int) result);

        return String.valueOf(result);
    }


}
