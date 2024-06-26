// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/6/max/MaxL.asm

// Symbol-less version of the Max.asm program.
// Designed for testing the basic version of the assembler.

@0
D=M
@1
D=D-M
@12
D;JGT
@1
D=M //1111110000010000
@2  //0000000000000010
M=D //1110110000001000
@16
0;JMP
@0             
D=M
@2
M=D //1110110000010000
@16
0;JMP //1110101010000111
