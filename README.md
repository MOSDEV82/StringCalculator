# StringCalculator
Calculates an operation inside of a String variable.

# Features
* Calculate inside of brackets
* */ before +-
* value² and value³
* √
* pi or π
* result contains true|false when operation contains one of this operators < <= = >= >

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
 # String operation = "√(9*2)*2²*(5*(4+10)*2-5*8+(2*4*(1+1)))";<br>
 String result = calculator.calculate(operation);<br>
 System.out.println(operation + " = " + result);<br>

# Output:<br>
 # √(9*2)*2²*(5*(4+10)*2-5*8+(2*4*(1+1))) = 1968.585278823348<br>
