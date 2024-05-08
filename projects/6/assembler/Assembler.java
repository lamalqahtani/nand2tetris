import java.io.*;
import java.util.*;

public class Assembler {

    public static void main(String[] args) {

        File file = new File("Add.asm");

        try {
            FileReader reader = new FileReader(file);
            FileReader reader1 = new FileReader(new File("ADD2.asm"));
            String fileName = file.getName().substring(0, file.getName().length() - 4) + ".hack";
            FileWriter writer = new FileWriter(new File(fileName));

            BufferedReader read = new BufferedReader(reader);
            BufferedReader read1 = new BufferedReader(reader1);
            BufferedWriter write = new BufferedWriter(writer);
            // write.append("test0");
            // write.newLine();
            // write.append("test1");
            // write.newLine();
            // write.append("test2");
            // write.newLine();
            // write.flush();

            //PASS 1
            String line = read.readLine();
            while (line != null) {
                if (!line.isEmpty()) {
                    if (line.charAt(0) != '/') {
                        // System.out.println(line);
                        Parser parser = new Parser(line);
                        Parser.isFirstPass = true;
                        parser.scanTokens();
                    }
                }
                line = read.readLine();
            }
            read.close();

            //PASS 2
            String line1 = read1.readLine();
            while (line1 != null) {
                if (!line1.isEmpty()) {
                    if (line1.charAt(0) != '/') {
                        Parser parser = new Parser(line1);
                        Parser.isFirstPass = false;
                        parser.scanTokens();
                        parser.parseTokens();
                    }
                }
                line1 = read1.readLine();
            }
            read1.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

// this class should read the file content and decode it by separating it into
// tokens and then map the symboles values then pass it to another class
// function that should translate the given string into binary code and
// save/append it into a file.
class Parser {
    static int line = 0;
    String source; // the file content (line)
    int start = 0; //the current character index of the line.
    // char c;
    static List<Instruction> tokens = new ArrayList<>(); //will hold all the tokens that have been scanned
    static Hashtable<String, Integer> symbolTable = new Hashtable<String, Integer>();
    static boolean isFirstPass;

    public Parser(String src) {
        source = src;
        // c = source.charAt(start);
    }

    public void scanTokens() {
        symbolTable.put("R0", 0);
        symbolTable.put("R1", 1);
        symbolTable.put("R2", 2);
        symbolTable.put("R3", 3);
        symbolTable.put("R4", 4);
        symbolTable.put("R5", 5);
        symbolTable.put("R6", 6);
        symbolTable.put("R7", 7);
        symbolTable.put("R8", 8);
        symbolTable.put("R9", 9);
        symbolTable.put("R10", 10);
        symbolTable.put("R11", 11);
        symbolTable.put("R12", 12);
        symbolTable.put("R13", 13);
        symbolTable.put("R14", 14);
        symbolTable.put("R15", 15);
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KDB", 24576);
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);

        char c = source.charAt(start);
        if (isFirstPass) {
            System.out.println("FIRST PASS");

            // First-Pass
            switch (c) {
                case '(':
                    start++;
                    c = source.charAt(start);
                    String lable = "";
                    while (c != ')') {
                        lable = lable + c;
                        start++;
                        c = source.charAt(start);
                        // System.out.println("lable: "+ lable);
                    }
                    symbolTable.put(lable, line);
                    line--; // line-1 because it will be incremented eventually. So if we don't want to
                            // increment after a lable then we will say label -1 +1 which will not change
                            // the line value

                    printSymbolTable();

                    break;
                case '\n':
                    line++;
                    break;

                default:
                    break;
            }
            line++;
        } else {

            System.out.println("SECOND PASS");
            // Second-Pass
            switch (c) {
            case '@':
            start++;
            //if A instruction, then we have two paths. First is handling a number directly. Second is handling a symbol which we already have in our symbol table.
            if(isNumber(source.charAt(start))){
                // first path: handling actual numbers
                System.out.println("value: " + getAInstValue(start));
                System.out.println("pass to A instruction translator");
                tokens.add(new AInstruction("A",  Integer.parseInt(getAInstValue(start))));

            } else{
                //second path: handling symbols.
                System.out.println(getAInstValue(start)+": "+symbolTable.get(getAInstValue(start)));
                System.out.println("pass to A instruction translator");
                tokens.add(new AInstruction("A", symbolTable.get(getAInstValue(start))));
            }
            break;

            default:
            // if not starting with @, then it is a C instruction.
            if(c != '@' ){
                //ignore C instructions for now.
                
            }
            break;
            }
        }

    }

    public boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    //Not yet used, but we can use it to return the current character instead of using start++ and source.charAt(start).
    public char getCurrentChar(){
        return source.charAt(start);
    }

    public String getAInstValue(int start){
        return source.substring(start, source.length());
    }

    public void printSymbolTable() {
        Enumeration<String> e = symbolTable.keys();

        // Iterating through the Hashtable
        // object

        // Checking for next element in Hashtable object
        // with the help of hasMoreElements() method
        while (e.hasMoreElements()) {

            // Getting the key of a particular entry
            String key = e.nextElement();

            // Print and display the Rank and Name
            System.out.println("Rank : " + key
                    + "\t\t Name : "
                    + symbolTable.get(key));
        }
    }

    public void printTokens(){
        for(Instruction inst : tokens){
            if(inst instanceof AInstruction){
                // System.out.println("***** " + ((AInstruction)inst).value + " *****");
                System.out.println(inst.toString());
            }else{
                System.out.println(inst.toString());
            }
        }
    }

    public void parseTokens(){
        String placeholder = "000000000000000";
        String value = null;
        for(Instruction inst : tokens){
            if(inst instanceof AInstruction){
                value = Integer.toBinaryString(((AInstruction)inst).value);
                System.out.println("*****");
                System.out.println(inst.toString());
                System.out.println("*** CONV *** " + value );
                System.out.println("0" + placeholder.substring(0, placeholder.length() - value.length()) + value);
                System.out.println("*****");

                //return "0"+ placeholder.substring(placeholder.length() - value.length(), placeholder.length());
                
            }else{
                System.out.println(inst.toString());
            }
        }
    }

}


// Defining the type of instructions.
class Instruction{
    String type;
    public Instruction(String type){
        this.type = type;
    }
    @Override
    public String toString() {
        return "Type: " + this.type;
    }
}

class AInstruction extends Instruction{
    int value;
    public AInstruction(String type, int value){
        super(type);
        this.value = value;
    }
    @Override
    public String toString() {
        return super.toString() + " Value: " + this.value;
    }
}

class CInstruction extends Instruction{
    String comp;
    String dest;
    String jmp;

    public CInstruction(String type, String comp, String dest, String jmp) {
        super(type);
        this.comp = comp;
        this.dest = dest;
        this.jmp = jmp;
    }
    @Override
    public String toString() {
        return super.toString() + " Comp: " + this.comp + " Dest: " + this.dest + " Jmp: " + this.jmp;
    }
}