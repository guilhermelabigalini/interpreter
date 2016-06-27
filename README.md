Kepler Language - a interpreter implemented in Java
========================================
[![Build Status](https://travis-ci.org/guilhermelabigalini/interpreter.svg?branch=master)](https://travis-ci.org/guilhermelabigalini/interpreter)
## Features
--------
Kepler Language provides most common commands used in modern programming languages, this includes:
- Dynamic type system with Strings, Floats and Integers
- Math Operators (+ - / * %) and the =<operator> version (+= , -=, *=, /* and %=)
- Pre/Pos increment/decrement operators (++ --)
- User defined functions
- Loop stataments (while, do while and for with break/continue)
- Conditional stataments (if and case)
- Error handling (with try-catch-finally)

## Samples
-------
Sample codes used to test the interpreter are avaiable on the code repository at 
[interpreter/src/test/resources/samples/](https://github.com/guilhermelabigalini/interpreter/tree/master/src/test/resources/samples), below are some small examples.

### Sample: Create User Function 

```javascript
function factorial(n) {
    if (n == 1) 
        return 1;
    
    return n * factorial(n - 1);
}

var x = factorial(6);       
```

### Sample: Performing a loop with if-else
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

## Running from command line
--------
The maven build copies a .bat file named "kepler" that runs the interpreter, to list the supported parameters, just run it with "-help" option:
```
kepler -help
```
It outputs:
```
usage: kepler
 -file <file>   execute the script from the file
 -help          print this message
 -verbose       display parsing and execution time in ms
```
Currently, source file and verbose file are supported. The source file is the file containing the script to be executed. "Verbose" flag should be used if you want to view the value of the variables and the time that was required to parse and run the script, running kepler with these options would look like: 
```
kepler -file t001_declare_var.kpl -verbose
```
Once the script is executed, because the verbose flag was used, Kepler also displayed variables and time statis about the script:
```
Time for t001_declare_var.kpl - parse: 0ms, execute: 0ms
Varibles:
a=null
b=0
c=123
d=255
e=29
```