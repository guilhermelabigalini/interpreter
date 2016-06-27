Kepler Language - a interpreter implemented in Java
========================================

# Features
--------
Kepler Language provides most commands used in modern programming languages, this includes:
- Dynamic type system with Strings, Floats and Integers
- Math Operators (+ - / * %) and the =<operator> version (+= , -=, *=, /* and %=)
- Pre/Pos increment/decrement operators (++ --)
- User defined functions
- Loop stataments (while, do while and for with break/continue)
- Conditional stataments (if and case)
- Error handling (with try-catch-finally)

# Samples
-------
Sample codes used to test the interpreter are avaiable on the code repository at 
[interpreter/src/test/resources/samples/](https://github.com/guilhermelabigalini/interpreter/tree/master/src/test/resources/samples) , below are some small examples.

# Sample: Create User Function 

```javascript
function factorial(n) {
    if (n == 1) 
        return 1;
    
    return n * factorial(n - 1);
}

var x = factorial(6);       
```

# Sample: Performing a loop with if-else
```javascript
var i, r;
r = 0;
for (i = 1; i <= 10; i++) {
    if (i == 5)
        break;
    else 
        r++;
}
```

# Running from command line
--------
The maven build copies the .bat file "kepler" that run the interpreter with parameters, to list the expected parameters, just run:
```
kepler -help
```
It will output:
```
usage: java -jar interpreter.jar
 -file <file>   execute the script from the file
 -help          print this message
 -verbose       display parsing and execution time in ms
```
If you already have a source, and want to run it and view the total execution time, just show:
```
kepler -file t001_declare_var.kpl -verbose
```
Because the interpreter was started with -verbose option, it will output the variables and the total time to parse and run the script:
```
Time for t001_declare_var.kpl - parse: 0ms, execute: 0ms
Varibles:
a=null
b=0
c=123
d=255
e=29
```