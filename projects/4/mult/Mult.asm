// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/4/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
// The algorithm is based on repetitive addition.


@R0
D=M
@mult
M=D // mult = RAM[0]
@base
M=D

//stop the program if the value of R0 is 0.
@ZERO
D;JEQ

@R1 //get the value of R1 to know the number of add iterations
D=M
@i //i is the iteration based on the value of R1
M=D // i = RAM[1]

//stop the program if the value of R1 is 0.
@ZERO
D;JEQ

(LOOP)
@i
M=M-1
D=M
@STOP
D;JLE //if i is 0 then stop the program.

@base
D=M
@mult
M = M+D // R0= R0+ base
@LOOP
0;JMP

(ZERO)
@R2
M=0
@END
0;JMP

(STOP)
@mult
D=M
@R2
M=D

//The end of the program (infinite loop)
(END)
@END
0;JMP