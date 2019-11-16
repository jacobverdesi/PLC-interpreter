# PLC-interpreter
An interpreter
Built in java

To build this program:
1. Place the src and tests folders in a root directory
2. Make sure that the LALR and GRAMMAR files are in the root directory
3. From the root directory, run "javac src/*java -d classes" in a terminal

To run this program:
1. From the root directory, run "java -cp classes Jott tests/progN.j" in a terminal where N is test Number



We created this project with a series of classes to interpret the file
First off the program will read in the file along with a grammar and LALR parse table
From this the program sends the jott file to the Tokenizer class which uses a DFA to tokenize the file
In this tokenizer the DFA reads character by character then if its found in an accepting state lables the token with a TERMINAL type
The tokenizer returns a 2d List of maps containing the token and its TERMINAL enum  type
This list is then sent to the Parser that takes in the LALR parse Table and the grammer
This LALR parser reads token by token then looks up the next state to go to and either shifts or reduces a stack that is the parse tree
When a shift occurs we look at the next state and add that state to the stack
When a reduce occurs we create a new node and adds the popped states to the tree
The parser then will return a node that is a parseTree and has all the children 
We send the node to the interpreter that reads the node and handles each child node and goes down untill all the tree has been handled
Throughout this we detect if there is an error with the file and if there is send and error message and exit the program.

