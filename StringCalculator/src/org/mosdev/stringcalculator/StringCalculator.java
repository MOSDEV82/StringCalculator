package org.mosdev.stringcalculator;

import java.util.ArrayList;

/**
 * Author: Toni Weber
 * Date: 2018/06/11
 */
public class StringCalculator {

    private String result;
    private boolean debugging = false;

    /**
     * This method paresded a String to an String ArrayList
     *
     * @param input Operation
     * @return ArrayList<String>All values</String>
     */
    private ArrayList<String> parseValues(String input) {
        String numValue = "";
        ArrayList<String> values = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            char chr = input.charAt(i);

            switch (chr) {
                case '²':
                case '³':
                case '^':
                case '√':
                case '%':
                case '-':
                case '+':
                case '*':
                case '/':
                case '(':
                case ')':
                    if (i == 0 && chr == '-') {
                        numValue += chr;
                    } else {
                        char chrBefore = chr;
                        if (i > 0) {
                            chrBefore = input.charAt(i - 1);
                        }
                        if (chr == '-' && (chrBefore == '*' || chrBefore == '/')) {
                            numValue += chr;
                        } else {
                            if (numValue.length() > 0) values.add(parseValue(numValue));
                            numValue = "";
                            values.add(Character.toString(chr));
                        }
                    }
                    break;
                default:
                    // Numbers
                    numValue += chr;
                    break;
            }
        }

        if (!numValue.equals("")) values.add(numValue);

        return values;
    }

    private String parseValue(String value) {
        String result = null;

        // Is it Pi?
        if (value.toLowerCase().equals("pi") || value.toLowerCase().equals("π")) {
            result = String.valueOf(Math.PI);
        } else if (value.contains("0x")) { // Check for Hex
            value = value.replace("0x", "");
            result = String.valueOf(Long.parseLong(value, 16));
        } else if (value.contains("0b")) {  // Check for Binary
            value = value.replace("0b", "");
            result = String.valueOf(Long.parseLong(value, 2));
        } else {
            result = String.valueOf(Double.parseDouble(value));
        }
        return result;
    }

    /**
     * In this method operations will done.
     * There is no check for validy, for example: Same count '(' and ')'!
     *
     * How it works...
     *
     * Preparing: Remove spaces
     * 1.: Remove brackets... And calculate inside of bracket
     * 2.: Calculate Operations with one value (eg. pi) or one value and one operator (eg. 4² or √9)
     * 3.: Calculate Multiplication, Division, Modulu first!
     * 4.: Calculate Addition and Subtraction
     *
     * Example calcualtion:
     * Operation to solve: √(9*2)*2²*(5*(4+10)*2-5*8+(2*4*(1+1)))
     * 9.0*2.0
     * √18.0*2.0²*(5.0*(4.0+10.0)*2.0-5.0*8.0+(2.0*4.0*(1.0+1.0)))
     * 5.0*(4.0+10.0)*2.0-5.0*8.0+(2.0*4.0*(1.0+1.0))
     * 4.0+10.0
     * 5.0*14.0*2.0-5.0*8.0+(2.0*4.0*(1.0+1.0))
     * 2.0*4.0*(1.0+1.0)
     * 1.0+1.0
     * 2.0*4.0*2.0
     * 8.0*2.0
     * 5.0*14.0*2.0-5.0*8.0+16.0
     * 70.0*2.0-5.0*8.0+16.0
     * 140.0-5.0*8.0+16.0
     * 140.0-40.0+16.0
     * 100.0+16.0
     * √18.0*2.0²*116.0
     * 4.242640687119285*2.0²*116.0
     * 4.242640687119285*4.0*116.0
     * 16.97056274847714*116.0
     *
     * Output: √(9*2)*2²*(5*(4+10)*2-5*8+(2*4*(1+1))) = 1968.585278823348
     *
     * @param input
     * @return
     * @throws NumberFormatException
     */
    private String calculateRunner(String input) throws NumberFormatException {
        // Remove spaces from input
        input = input.trim().replace(" ", "");

        ArrayList<String> values = parseValues(input); // Returns a valid ArrayList of all values and operators
        if (values.size() == 1) return parseValue(values.get(0)); // If size == 1 then is there no operation!
        if (debugging) System.out.println(input); // Just a output for debugging, when enabled!

        int nextBracket = -1;
        int closedBracket = 0;
        int bracketLevel = 0;

        for (int i = 0; i < values.size(); i++) {
            switch (values.get(i)) {
                case "(":
                    if (nextBracket == -1) {
                        nextBracket = i;
                        closedBracket = -1;
                    }
                    bracketLevel++;
                    break;
                case ")":
                    bracketLevel--;
                    if (bracketLevel == 0) closedBracket = i;
                    break;
            }

            if (nextBracket > -1 && bracketLevel == 0) {
                break;
            }
        }

        if (nextBracket >= 0 && closedBracket > 0) {
            String operation = "";
            for (int i = nextBracket + 1; i < closedBracket; i++) {
                operation += values.get(i);
            }

            String result = calculateRunner(operation);

            // Remove calculated values from array
            for (int i = nextBracket + 1; i < closedBracket + 1; i++) {
                values.remove(nextBracket + 1);
            }

            // First bracket change to result
            values.set(nextBracket, result);
            operation = "";
            for (String val : values) {
                operation += val;
            }

            // Neu berechnen:
            return calculateRunner(operation);
        }

        // Check for operations that have one operator
        int nextOperationPosition = -1;
        for (int i = 0; i < values.size(); i++) {
            switch (values.get(i)) {
                case "²":
                case "³":
                case "√":
                    nextOperationPosition = i;
                    break;
            }
            if (nextOperationPosition >= 0) break;
        }

        // Check for operations with multiplication, division, modulu etc.
        if (nextOperationPosition < 0) {
            for (int i = 0; i < values.size(); i++) {
                switch (values.get(i)) {
                    case "^":
                    case "%":
                    case "*":
                    case "/":
                        nextOperationPosition = i;
                        break;
                }
                if (nextOperationPosition >= 0) break;
            }

            if (nextOperationPosition < 0) nextOperationPosition = 0;
        }

        Double result = 0d;
        int index = -1;
        String operation = "";

        // Start and nextOperationPosition, that is the position of next Operation, because */% etc. have calculated before + and -
        for (int i = nextOperationPosition; i < values.size(); i++) {
            // Parse value from String / Check for Bin or Hex
            switch (values.get(i)) {
                case "*":
                    result = Double.parseDouble(parseValue(values.get(i - 1))) * Double.parseDouble(parseValue(values.get(i + 1)));
                    index = i;
                    break;
                case "/":
                    result = Double.parseDouble(parseValue(values.get(i - 1))) / Double.parseDouble(parseValue(values.get(i + 1)));
                    index = i;
                    break;
                case "+":
                    result = Double.parseDouble(parseValue(values.get(i - 1))) + Double.parseDouble(parseValue(values.get(i + 1)));
                    index = i;
                    break;
                case "-":
                    result = Double.parseDouble(parseValue(values.get(i - 1))) - Double.parseDouble(parseValue(values.get(i + 1)));
                    index = i;
                    break;
                case "²":
                    result = Math.pow(Double.parseDouble(parseValue(values.get(i - 1))), 2);
                    index = i;
                    break;
                case "³":
                    result = Math.pow(Double.parseDouble(parseValue(values.get(i - 1))), 3);
                    index = i;
                    break;
                case "^":
                    result = Math.pow(Double.parseDouble(parseValue(values.get(i - 1))), Double.parseDouble(parseValue(values.get(i + 1))));
                    index = i;
                    break;
                case "√":
                    result = Math.sqrt(Double.parseDouble(parseValue(values.get(i + 1))));
                    index = i;
                    break;
                case "%":
                    result = Double.parseDouble(parseValue(values.get(i - 1))) % Double.parseDouble(parseValue(values.get(i + 1)));
                    index = i;
                    break;
            }

            // Remove oeration from ArrayList, first entry of operation have to change to result value!
            if (index >= 0) {
                String operator = values.remove(index); // Operator
                switch (operator) {
                    case "²":
                    case "³":
                        values.set(index - 1, String.valueOf(result)); // Here is only one value and one operator
                        break;
                    case "√":
                        values.set(index, String.valueOf(result)); // Here is the operator the first String and the value the second
                        break;
                    default:
                        values.set(index - 1, String.valueOf(result)); // Here are two values and one operator
                        values.remove(index); // Other value
                        break;
                }

                for (String val : values) operation += val;
                break;
            }
        }

        return calculateRunner(operation);
    }

    public String calculate(String operation) {
        int lessThan = operation.indexOf("<");
        int biggerThan = operation.indexOf(">");
        int equals = operation.indexOf("=");
        if (lessThan > -1 || biggerThan > -1 || equals > -1) {
            String[] operations = equals > -1 ? operation.split("=") : lessThan > -1 ? operation.split("<") : operation.split(">");

            // Check, when = is in String again for <> because <= or >= is valid too!
            if (equals > -1) {
                lessThan = operations[0].indexOf("<");
                biggerThan = operations[0].indexOf(">");

                if (lessThan > -1 || biggerThan > -1){
                    operations[0] = lessThan > -1 ? operations[0].substring(0, operations[0].indexOf("<")):operations[0].substring(0, operations[0].indexOf(">"));
                }
            }

            if (operations.length == 1) {
                result = "false";
                return result;
            }

            this.result = calculateRunner(operations[0]);
            double resultLeftDouble = getResultAsDouble();
            this.result = calculateRunner(operations[1]);
            double resultRightDouble = getResultAsDouble();

            if (equals > -1) {
                if (lessThan > -1){
                    this.result = String.valueOf(resultLeftDouble <= resultRightDouble);
                } else if (biggerThan > -1) {
                    this.result = String.valueOf(resultLeftDouble >= resultRightDouble);
                } else {
                    this.result = String.valueOf(resultLeftDouble == resultRightDouble);
                }
            } else {
                if (lessThan > -1){
                    this.result = String.valueOf(resultLeftDouble < resultRightDouble);
                } else if (biggerThan > -1) {
                    this.result = String.valueOf(resultLeftDouble > resultRightDouble);
                }
            }
        } else {
            this.result = calculateRunner(operation);
        }

        return this.result;
    }

    public int getResultAsInt() {
        int output = 0;
        if (result.contains(".")) {
            output = Integer.parseInt(result.substring(0, result.indexOf(".")));
        } else {
            output = Integer.parseInt(result);
        }
        return output;
    }

    public long getResultAsLong() {
        long output = 0;
        if (result.contains(".")) {
            output = Long.parseLong(result.substring(0, result.indexOf(".")));
        } else {
            output = Long.parseLong(result);
        }
        return output;
    }

    public Float getResultAsFloat() {
        return Float.parseFloat(this.result);
    }

    public Double getResultAsDouble() {
        return Double.parseDouble(this.result);
    }

    public String getResultAsBinary() {
        long output = 0;
        if (result.contains(".")) {
            output = Long.parseLong(result.substring(0, result.indexOf(".")));
        } else {
            output = Long.parseLong(result);
        }
        return Long.toBinaryString(output);
    }

    public String getResultAsHex() {
        long output = 0;
        if (result.contains(".")) {
            output = Long.parseLong(result.substring(0, result.indexOf(".")));
        } else {
            output = Long.parseLong(result);
        }
        return Long.toHexString(output);
    }

    public void setDebugging(boolean debugging) {
        this.debugging = debugging;
    }
}
