// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

(START)

//store the SCREEN address in a pointer addr
@SCREEN
D=A
@addr
M=D

//get the maximum iteration number (8191)
@8191
D=A
@max
M=D

//set a color variable to 0 at first, and update it to 1 if a key is pressed
//@color
//M=0

//i for iterations count
@i
M=0


//check the KBD value (if 0 then no key is pressed, otherwise a key is pressed)
@KBD
D=M
@BLACK
D;JGT


(WHITE)
@i
D=M
@addr
A=M+D
M=0
@i
M=M+1
@max
D= M-D
@WHITE
D;JGT

@START
0;JMP


(BLACK)
@i
D=M
@addr
A=M+D
M=-1
@i
M=M+1
@max
D= M-D
@BLACK
D;JGT


@START
0;JMP




