import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//for mom
//for me

public class Lexer {

    public Lexer(){
    }
    final static String[] keywords = {"else", "if", "int", "float", "return", "void", "while"};
    public static int commDepth = 0;
    public static ArrayList<String> fileLines = new ArrayList<>();
    public static String targetString;
    public static ArrayList<Token> tokens = new ArrayList<>();
    public static boolean done = false;
    
    public static void startLexer(){
        fileLines = readFile("E:\\EclipseStuff\\main2.c");                                                            //This line is for files given without using commandline arguments
        
        /*                                                                                                             //ACTUAL GET FILE FROM COMMANDLINE SETUP
        try{
            fileLines = readFile(ar);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("No commandline argument given");
        }catch(Exception ex){
            System.out.println("File may not have been found to read from");
        }
        //*/
        
        setTokens(fileLines);
        
    }

    public static ArrayList<String> readFile(String f){
        ArrayList<String> list = new ArrayList<>();
        try {
            java.io.File file = new java.io.File(f);
            Scanner sheet = new Scanner(file);
            while(sheet.hasNextLine()){
                list.add(sheet.nextLine()+"~#@!%^$");									//adds end-of-line identifying sting of characters to each line from file
            }
        }catch (FileNotFoundException e){
            System.out.println("Couldn't open file");
            System.exit(0);
        }
        return list;
    }

    public static void setTokens(ArrayList<String> fileLines){
        recCharCheck(fileLines.get(0), 0, 0);                                                              //for future projects maybe
    }


    public static void recCharCheck(String line, int targetChar, int i){
        if(done){return;}
            if (targetChar == 0) {
                //System.out.println("\nINPUT: " + fileLines.get(i).substring(0,(fileLines.get(i).length() - 7)));        //hides my end-of-line mark
            }

            if (line.charAt(targetChar) == '~') {
                if(done){return;}
                if (line.substring(targetChar).equals("~#@!%^$")) {
                    i++;
                    try {
                        recCharCheck(fileLines.get(i), 0, i);
                        if(done){return;}
                    }catch(IndexOutOfBoundsException e){
                        /*                                                      //PURELY FOR TESTING TOKEN ARRAY
                        System.out.println("\tTokens Collected:");
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        System.out.println("Type:\t\t\tContent:");
                        for(Token2 t : tokens){
                            System.out.println(t.type +"\t\t\t"+ t.tContent);
                        }
                        System.out.println("\n\n\n");
                        //*/
                        done = true;
                        return;
                        //System.exit(0);
                    }
                }
                if(done){return;}
                System.out.println("ERROR: ~");
                recCharCheck(line, targetChar + 1, i);
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            else if (commDepth > 0) {
                if (line.charAt(targetChar) == '*') {
                    if(done){return;}
                    targetChar++;
                    if (line.charAt(targetChar) == '/') {                                                               //block comment close
                        decDepth();
                        recCharCheck(line, targetChar + 1, i);
                        if(done){return;}
                    }
                    recCharCheck(line, targetChar, i);
                    if(done){return;}
                }
                if (line.charAt(targetChar) == '/') {
                    if(done){return;}
                    targetChar++;
                    if (line.charAt(targetChar) == '*') {                                                               //block comment open
                        incDepth();
                        recCharCheck(line, targetChar + 1, i);
                        if(done){return;}
                    }
                    recCharCheck(line, targetChar, i);
                    if(done){return;}
                }
                recCharCheck(line, targetChar + 1, i);
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            else if (line.charAt(targetChar) == '/') {
                if(done){return;}
                targetChar++;
                if (line.charAt(targetChar) == '/') {                                                                   //this is line comment, move on immediately
                    i++;
                    recCharCheck(fileLines.get(i), 0, i);

                } else if (line.charAt(targetChar) == '*') {                                                            //block comment open
                    incDepth();
                    recCharCheck(line, targetChar + 1, i);
                } else {
                    //System.out.println("Special symbol: /");                    //*
                    tokens.add(new Token("/", "/"));
                    recCharCheck(line, targetChar, i);
                }
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            else if(line.charAt(targetChar) == '*'){                                                                        //if random star (no comment depth) (no / before)
                if(done){return;}
                //System.out.println("Special symbol: *");                        //*
                tokens.add(new Token("*", "*"));
                recCharCheck(line, targetChar + 1, i);
            }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            else if (line.charAt(targetChar) == ' ') {                                                                       //if random space
                if(done){return;}
                recCharCheck(line, targetChar + 1, i);
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// handles most special symbols
            else if(line.charAt(targetChar) == '+' || line.charAt(targetChar) == '-' ||
                    line.charAt(targetChar) == ';' || line.charAt(targetChar) == ',' ||
                    line.charAt(targetChar) == '(' || line.charAt(targetChar) == ')' ||
                    line.charAt(targetChar) == '[' || line.charAt(targetChar) == ']' ||
                    line.charAt(targetChar) == '{' || line.charAt(targetChar) == '}'){
                if(done){return;}
                //System.out.println("Special symbol: " + line.charAt(targetChar));           //*
                tokens.add(new Token(line.substring(targetChar, targetChar+1), line.substring(targetChar, targetChar+1)));
                recCharCheck(line, targetChar + 1, i);
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// handles the characters in two-character special symbols
            else if(line.charAt(targetChar) == '<'){
                if(done){return;}
                targetChar++;
                if (line.charAt(targetChar) == '=') {
                    //System.out.println("Special symbol: <=");                   //*
                    tokens.add(new Token("<=", "<="));
                    recCharCheck(line, targetChar + 1, i);
                    if(done){return;}
                }
                //System.out.println("Special symbol: <");                        //*
                tokens.add(new Token("<", "<"));
                recCharCheck(line, targetChar, i);
            }
            else if (line.charAt(targetChar) == '>'){
                if(done){return;}
                targetChar++;
                if (line.charAt(targetChar) == '=') {
                    //System.out.println("Special symbol: >=");                   //*
                    tokens.add(new Token(">=", ">="));
                    recCharCheck(line, targetChar + 1, i);
                    if(done){return;}
                }
                //System.out.println("Special symbol: >");                        //*
                tokens.add(new Token(">", ">"));
                recCharCheck(line, targetChar, i);
                
            }
            else if(line.charAt(targetChar) == '='){
                if(done){return;}
                targetChar++;
                if (line.charAt(targetChar) == '=') {
                    //System.out.println("Special symbol: ==");                   //*
                    tokens.add(new Token("==", "=="));
                    recCharCheck(line, targetChar + 1, i);
                    if(done){return;}
                }
                
                //System.out.println("Special symbol: =");                        //*
                tokens.add(new Token("=", "="));
                recCharCheck(line, targetChar, i);
                
            }
            else if(line.charAt(targetChar) == '!'){
                if(done){return;}
                targetChar++;
                if (line.charAt(targetChar) == '=') {
                    //System.out.println("Special symbol: !=");                   //*
                    tokens.add(new Token("!=", "!="));
                    recCharCheck(line, targetChar + 1, i);
                    if(done){return;}
                }
                System.out.println("ERROR: !");
                recCharCheck(line, targetChar, i);
                
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// handles letters that might be keywords or IDs
            else if(isInAlpha(line.charAt(targetChar))){
                if(done){return;}
                int letterCount = 0;
                while(isInAlpha(line.charAt(targetChar))){
                    targetChar++;
                    letterCount++;
                }
                targetString = line.substring(targetChar - letterCount, targetChar);                                          //target string is the string of letters grouped together (separated by anything not a letter)
                for(int j = 0; j < keywords.length; j++) {
                    if (targetString.equals(keywords[j])) {
                        //System.out.println("keyword: " + keywords[j]);          //*
                        tokens.add(new Token(targetString, targetString));
                        recCharCheck(line, targetChar, i);
                        if(done){return;}
                    }
                }
                //System.out.println("ID: " + targetString);                      //*
                if(done){return;}
                tokens.add(new Token(targetString, "ID"));
                recCharCheck(line, targetChar, i);
                
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// handles groups of numbers and a lot of this is just dealing with floats
            else if(isNum(line.charAt(targetChar))){
                if(done){return;}
                int numCount = 0;
                while(isNum(line.charAt(targetChar))){
                    targetChar++;
                    numCount++;
                }

                if(line.charAt(targetChar) == '.'){                                                                     //In hindsight I could have made this section much more efficiently...
                    if(isNum(line.charAt(targetChar+1))){
                        do{
                            targetChar++;
                            numCount++;
                        }while(isNum(line.charAt(targetChar)));
                        if(line.charAt(targetChar) == 'E'){
                            if(!isNum(line.charAt(targetChar+1))){
                                if(line.charAt(targetChar+1) == '-' || line.charAt(targetChar+1) == '+'){
                                    if(isNum(line.charAt(targetChar+2))){
                                        targetChar+=2;
                                        numCount+=2;
                                    }else{
                                        targetChar+=2;
                                        numCount+=2;
                                        targetString = line.substring(targetChar - numCount, targetChar);
                                        System.out.println("ERROR: " + targetString);
                                        recCharCheck(line, targetChar, i);
                                        if(done){return;}
                                    }
                                }else {
                                    targetChar++;
                                    numCount++;
                                    targetString = line.substring(targetChar - numCount, targetChar);
                                    System.out.println("ERROR: " + targetString);
                                    recCharCheck(line, targetChar, i);
                                    if(done){return;}
                                }
                            }
                            do{
                                targetChar++;
                                numCount++;
                            }while(isNum(line.charAt(targetChar)));
                        }
                        targetString = line.substring(targetChar - numCount, targetChar);
                        //System.out.println("Float: " + targetString);           //*
                        tokens.add(new Token(targetString, "Num"));
                        recCharCheck(line, targetChar, i);
                        if(done){return;}
                    }
                }

                targetString = line.substring(targetChar - numCount, targetChar);
                //System.out.println("Num: " + targetString);                     //*
                tokens.add(new Token(targetString, "Num"));
                recCharCheck(line, targetChar, i);
                
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// gives error for anything not handled
            else{
                if(done){return;}
                System.out.println("ERROR: " + line.charAt(targetChar));
                recCharCheck(line, targetChar + 1, i);
                
            }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// extra utility methods
    public static void incDepth(){
        commDepth++;
    }
    public static void decDepth(){
        if(commDepth > 0){
            commDepth--;
        }
    }
    public static boolean isInAlpha(char letter){
        char[] alpha = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        for(int i = 0; i < alpha.length; i++){
            if(letter == alpha[i]){return true;}
        }
        return false;
    }
    public static boolean isNum(char character){
        char[] num = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for(int i = 0; i < num.length; i++){
            if(character == num[i]){return true;}
        }
        return false;
    }
}




