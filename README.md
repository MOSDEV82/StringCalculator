# StringCalculator
Calculates an operation inside of a String variable.

# Features
* Calculate inside of brackets
* */ before +-
* value² and value³
* √
* pi or π
* auto detect of hex and binar values (Hex start with 0x, bin with 0b)
* result contains true|false when operation contains one of this operators < <= = >= >
* result output as hex and bin too (Long only!)

# Hint: There is no error handling!! When you send wrong operations, java crashes with exception!
# Note: StringCalculator does not support calculations like pow(4,4) at this point. It is really simple to implement that, but this library is just an example how String calculation works!!

# How it works...

 Preparing: Remove spaces<br>
 1.: Remove brackets... And calculate inside of bracket<br>
 2.: Calculate Operations with one value (eg. pi) or one value and one operator (eg. 4² or √9)<br>
 3.: Calculate Multiplication, Division, Modulu first!<br>
 4.: Calculate Addition and Subtraction<br>
 
 # Example Operation:
 StringCalculator calculator = new StringCalculator();<br>
 String operation = "√(9\*2)x2²x(5x(4+10)x2-5x8+(2x4x(1+1)))";<br>
 String result = calculator.calculate(operation);<br>
 System.out.println(operation + " = " + result);<br>

# Output:<br>
 √(9x2)x2²x(5x(4+10)x2-5x8+(2x4x(1+1))) = 1968.585278823348<br>
