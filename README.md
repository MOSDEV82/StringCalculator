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

# How it works...

 Preparing: Remove spaces
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
