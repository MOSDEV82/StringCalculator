package org.mosdev.stringcalculator.demo;

import org.mosdev.stringcalculator.StringCalculator;

/**
 * Author: Toni Weber
 * Date: 2018/06/11
 */
public class Main {

    public static void main(String[] args) {
        new Main().run();
    }

    /**
     * Sample project
     */
    private void run() {
        String operation = "√(9*2)*2²*(5*(4+10)*2-5*8+(2*4*(1+1)))>";
        StringCalculator stringCalculator = new StringCalculator();

        String result = stringCalculator.calculate(operation);
        System.out.println(operation + " = " + result);
        System.out.println(operation + " = " + stringCalculator.getResultAsDouble());
        System.out.println(operation + " = " + stringCalculator.getResultAsLong());
        System.out.println(operation + " = " + stringCalculator.getResultAsBinary());
        System.out.println(operation + " = " + stringCalculator.getResultAsHex());

        // Hex and Binary
        operation = "0x7F*0b10";
        result = stringCalculator.calculate(operation);
        System.out.println(operation + " = " + stringCalculator.getResultAsLong());
        System.out.println(operation + " = " + stringCalculator.getResultAsBinary());
        System.out.println(operation + " = " + stringCalculator.getResultAsHex());

        operation = "(4+4)² >= 16*4";
        result = stringCalculator.calculate(operation);
        System.out.println(operation + " = " + result);
    }
}
