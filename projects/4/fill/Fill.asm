// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, 
// the screen should be cleared.

//addr = SCREEN address (16384)
@SCREEN
D=A
@addr
M=D

//i is the number of iterations.
@i
M=0

//R0 will hold the last address offset of the screen memory map (the maximum number of i). (8191)
@R0
D=M
@max
M=D

(LOOP)
@i
D=M
@addr
A=M+D
M=-1
@i
M=M+1
@max
D= M-D // if max-i == 0 go to the end of the program.
@END
D; JEQ

@LOOP
0;JMP


(END)
@END
0;JMP

