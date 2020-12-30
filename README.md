# Compiler-Construction-Project
# Compiler Construction Project

## Tafcal Language Compiler
I have created a compiler for <b>tafcal language</b>, This Compiler is Made by Syed Mustafa Imam. As the final Project of Compiler Construction course given by Sir Khawaja.
I have named my compiler 'TAFCAL' as 'TAF' is my nick Name and 'CAL' is for Compiler.
My compiler for TAFCAL language, which is defined by Sir Khawaja, it can recognize lexeme and  it will
decide that the lexeme should go into the symbol table or not.It also assigns the token type and 
attributes value to each token. This TAFCAL Compiler can also find some basic Errors.It can also extract
the values from the SLR(1) parse table and also from the given CFG. 
The main brach is the `dev` branch, which my development branch. 

## <b>The keywords For Tafcal</b> :

<ol start = "1">
<li>begin</li>
<li>end</li>
<li>if</li>
<li>else</li>
<li>then</li>
<li>int</li>
<li>float</li>
<li>char</li>
<li>string</li>
  


### How to run the project:
You just need to download the development `master` branch and navigate to this location `\temp\src\ccproject`  through cmd or terminal and
then run the following commands: </br>
``` javac main.java ```</br>
``` java main.java ```

or 

Use netbeans to run this project on your machine. 

#
  # Steps of the Project
  
 ## Project Step - 1
• Write a program in java that reads its input from the following file:
“C:/temp/sourcecode.txt”
• The program then prints each character of the input file on a separate line on the screen.


## Project Step - 2
Add following code to your project:
• Your program should eliminate the comments enclosed in /* and */ or following // on the
same line.
• Your program should eliminate the white spaces (blank, tab or newline).
(Eliminate means you should ignore and do not do anything about them)


## Project Step - 3
• Add code to your project to recognize the Relational Operators <, <=, ==, <>, >= and >
(use the logic similar to the code given in figure 3.18).
• Whenever any of these relational operators are matched, your project should also print the
token name “relop” and the attribute value LT, LE, EQ, NE, GE or GT respectively (after printing
the relational operator.

## Project Step - 4
• Add code to your project to recognize:
• Arithmetic Operators (arop): +, -, *, /
• Other Operators (otop): assignment: =, parenthesis: (, ), braces: {, }, line terminator: ; .
• Whenever any of the above operators are matched, your project should also print the
respective token name “arop” or “otop” and the attribute value ADD, SUB, MUL, DIV,
ASN, LPRN, RPRN, LBRC, RBRC or LNTR respectively (after printing the relational
operator).

## Project Step - 5
• Add code to your project to recognize:
• Keywords (keyword): begin, end, if, then, else, int, float, char, string
• Identifiers (id): letter followed by zero or more letters or digits.
• Whenever any of the above tokens are matched, your project should also print the
respective token name “keyword” or “id” and for keywords, some attribute value (any
numeric value for now) after printing the lexeme.

## Project Step - 6
• Add code to your project to recognize:
  • Unsigned Integer (uint): unsigned integer value
  • String Literal: (sliteral): anything that is surrounded by double quotes (“ ’s)
• Whenever any of the above tokens are matched, your project should also print the respective token name (“uint” or “sliteral”) and the attribute value (the unsigned integer value or the string literal value) after printing the lexeme.
• If the input (source code to be compiled) to your project (compiler) contains something other than the comments, white spaces or the lexemes mentioned above or in previous project steps, then your compiler should generate and display the error with:
  • The line number
  • The unrecognized lexeme in the source code and 
  • A meaningful error message



## Project Step - 7
• Add code to your project to
• Create a symbol table to store tokens in it
• Every time you recognize a lexeme, you need to decide whether you want to store the token
in the symbol table or not and then if needed, you store it.
• One way is to keep symbol table in the form of an array of objects that store token name,
type and attribute value. (if you like you can use any other type of data structure)
• Before exiting, your program should print out the tokens stored in the symbol table. 

## Project Step - 8
• Add the code to your project to store the data
of:
1. The productions of the given grammar.
2. The parsing table.
• Print the data of the productions and the
parsing table.
