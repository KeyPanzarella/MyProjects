import java.util.Stack;
import java.util.ArrayList;

/**
 * Ki-Jana Panzarella
 * N00918893
 * C- Compiler
 */
public class Parser {

    //------------CHANGE TO TRUE TO SHOW PARSE------------//
    final public static boolean SHOWPARSE = true;
    //----------------------------------------------------//
    
    public static Stack<String> parseLog = new Stack<>();
    public static Stack<String> tokenStack = new Stack<>();
    public static ArrayList<Token> tokenList = Lexer.tokens;
    public static Object currentToken;
    public static boolean done = false;
    public static boolean hangElse = false;
    public static boolean rejectHangElse = false;
    public static Stack<String> hangParseLog = new Stack<>();
    public static Stack<String> hangTokenStack = new Stack<>();
    
    public static void main(String[] args) {                                    //empty this argument and the parameters in Lexer for non-commandline fed files
        Lexer.startLexer();
        //CodeGen.startCodeGen(); //THIS IS WHERE CODEGEN STARTS
        createParseLog();
        createTokenStack();
        startParse();
        checkParse();
    }
    public static void createParseLog(){
        parseLog.push("$");
    }
    public static void createTokenStack(){
        tokenStack.push("$");
        String tempVal;
        for(Token token : reverseTokenList(tokenList)){
            tempVal = token.type;
            tokenStack.push(tempVal);
        }
    }
    private static ArrayList<Token> reverseTokenList(ArrayList<Token> list){
        ArrayList<Token> reversedList = new ArrayList<>();
        for (int  i = list.size()-1; i >= 0; i--){
            reversedList.add(list.get(i));
        }
        return reversedList;
    }
    private static void startParse(){
        parseLog.push("Start");
        displayParse();
        start();
    }
    
    
    
    //-----------------------------------RULES-----------------------------------//
    private static void start(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("int", "float", "void", null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("BP");
            parseLog.push("C");
            displayParse();
            C();
            BP();
        }
        else{done = true;}
    }
    private static void BP(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("int", "float", "void", null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("BP");
            parseLog.push("C");
            displayParse();
            C();
            BP();
        }else if(tokenIs("$", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void C(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("int", "float", "void", null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("CS");
            parseLog.push("ID");
            parseLog.push("E");
            displayParse();
            E();
            checkParse();
            CS();
        }
        else{done = true;}
    }
    private static void CS(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs(";", "[", null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push(";");
            parseLog.push("DS");
            displayParse();
            DS();
            checkParse();
        }else if(tokenIs("(", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("J");
            parseLog.push(")");
            parseLog.push("G");
            parseLog.push("(");
            displayParse();
            checkParse();
            G();
            checkParse();
            J();
        }
        else{done = true;}
    }
    private static void D(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("int", "float", "void", null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push(";");
            parseLog.push("DS");
            parseLog.push("ID");
            parseLog.push("E");
            displayParse();
            E();
            checkParse();
            DS();
            checkParse();
        }
        else{done = true;}
    }
    private static void DS(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("[", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("]");
            parseLog.push("Num");
            parseLog.push("[");
            displayParse();
            checkParse();
            checkParse();
            checkParse();
        }else if(tokenIs("int", "float", "void", "ID", "(", ";", "{", "if", "while", "return", null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void E(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("int", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("int");
            displayParse();
            checkParse();
        }
        else if(tokenIs("float", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("float");
            displayParse();
            checkParse();
        }
        else if(tokenIs("void", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("void");
            displayParse();
            checkParse();
        }
        else{done = true;}
    }
    private static void G(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("int", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("HP");
            parseLog.push("IS");
            parseLog.push("ID");
            parseLog.push("int");
            displayParse();
            checkParse();
            checkParse();
            IS();
            HP();
        }
        else if(tokenIs("float", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("HP");
            parseLog.push("IS");
            parseLog.push("ID");
            parseLog.push("float");
            displayParse();
            checkParse();
            checkParse();
            IS();
            HP();
        }
        else if(tokenIs("void", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("GS");
            parseLog.push("void");
            displayParse();
            checkParse();
            GS();
        }
        else{done = true;}
    }
    private static void GS(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("ID", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("HP");
            parseLog.push("IS");
            parseLog.push("ID");
            displayParse();
            checkParse();
            IS();
            HP();
        }else if(tokenIs(")", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void HP(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs(",", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("HP");
            parseLog.push("I");
            parseLog.push(",");
            displayParse();
            checkParse();
            I();
            HP();
        }else if(tokenIs(")", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void I(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("int", "float", "void", null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("IS");
            parseLog.push("ID");
            parseLog.push("E");
            displayParse();
            E();
            checkParse();
            IS();
        }
        else{done = true;}
    }
    private static void IS(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("[", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("]");
            parseLog.push("[");
            displayParse();
            checkParse();
            checkParse();
        }else if(tokenIs(")", ",", null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void J(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("{", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("}");
            parseLog.push("LP");
            parseLog.push("KP");
            parseLog.push("{");
            displayParse();
            checkParse();
            KP();
            LP();
            checkParse();
        }
        else{done = true;}
    }
    private static void KP(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("int", "float", "void", null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("KP");
            parseLog.push("D");
            displayParse();
            D();
            KP();
        }else if(tokenIs("ID", "(", ";", "if", "while", "return", null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void LP(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("ID", "(", ";", "{", "if", "while", "return", null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("LP");
            parseLog.push("M");
            displayParse();
            M();
            LP();
        }else if(tokenIs("}", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void M(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("ID", "(", ";", null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("N");
            displayParse();
            N();
        }
        else if(tokenIs("{", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("J");
            displayParse();
            J();
        }
        else if(tokenIs("if", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("O");
            displayParse();
            O();
        }
        else if(tokenIs("while", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("P");
            displayParse();
            P();
        }
        else if(tokenIs("return", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("Q");
            displayParse();
            Q();
        }
        else{done = true;}
    }
    private static void N(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("ID", "(", null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push(";");
            parseLog.push("R");
            displayParse();
            R();
            checkParse();
        }else if(tokenIs(";", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push(";");
            displayParse();
            checkParse();
        }
        else{done = true;}
    }
    private static void O(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("if", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("OS");
            parseLog.push("M");
            parseLog.push(")");
            parseLog.push("R");
            parseLog.push("(");
            parseLog.push("if");
            displayParse();
            checkParse();
            checkParse();
            R();
            checkParse();
            M();
            OS();
        }
        else{done = true;}
    }
    private static void OS(){
        if(rejectHangElse){parseLog.pop(); displayParse(); hangElse = false; rejectHangElse = false;return;}
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("else", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            hangElse = true;
            hangParseLog = parseLog;
            hangTokenStack = tokenStack;
            parseLog.pop();
            parseLog.push("M");
            parseLog.push("else");
            displayParse();
            checkParse();
            M();
        }else if(tokenIs("ID", "(", ";", "{", "}", "if", "while", "return", null, null, null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void P(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("while", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("M");
            parseLog.push(")");
            parseLog.push("R");
            parseLog.push("(");
            parseLog.push("while");
            displayParse();
            checkParse();
            checkParse();
            R();
            checkParse();
            M();
        }
        else{done = true;}
    }
    private static void Q(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("return", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push(";");
            parseLog.push("QS");
            parseLog.push("return");
            displayParse();
            checkParse();
            QS();
            checkParse();
        }
        else{done = true;}
    }
    private static void QS(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("ID", "(", null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("R");
            displayParse();
            R();
        }else if(tokenIs(";", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void R(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("ID", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("RS");
            parseLog.push("ID");
            displayParse();
            checkParse();
            RS();
        }
        else if(tokenIs("(", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("TS");
            parseLog.push("VP");
            parseLog.push("XP");
            parseLog.push(")");
            parseLog.push("R");
            parseLog.push("(");
            displayParse();
            checkParse();
            R();
            checkParse();
            XP();
            VP();
            TS();
        }
        else if(tokenIs("Num", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("TS");
            parseLog.push("VP");
            parseLog.push("XP");
            parseLog.push("Num");
            displayParse();
            checkParse();
            XP();
            VP();
            TS();
        }
        else{done = true;}
    }
    private static void RS(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("[", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("RSS");
            parseLog.push("]");
            parseLog.push("R");
            parseLog.push("[");
            displayParse();
            checkParse();
            R();
            checkParse();
            RSS();
        }
        else if(tokenIs("=", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("R");
            parseLog.push("=");
            displayParse();
            checkParse();
            R();
        }
        else if(tokenIs("(", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("TS");
            parseLog.push("VP");
            parseLog.push("XP");
            parseLog.push(")");
            parseLog.push("ZZ");
            parseLog.push("(");
            displayParse();
            checkParse();
            ZZ();
            checkParse();
            XP();
            VP();
            TS();
        }
        else if(tokenIs(")", ";", ",", "]", "*", "/", "+", "-", "<=", "<", ">", ">=", "==", "!=", null)){
            parseLog.pop();
            parseLog.push("TS");
            parseLog.push("VP");
            parseLog.push("XP");
            displayParse();
            XP();
            VP();
            TS();
        }
        else{done = true;}
    }
    private static void RSS(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("=", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("R");
            parseLog.push("=");
            displayParse();
            checkParse();
            R();
        }else if(tokenIs(")", ";", ",", "]", "*", "/", "+", "-", "<=", "<", ">", ">=", "==", "!=", null)){
            parseLog.pop();
            parseLog.push("TS");
            parseLog.push("VP");
            parseLog.push("XP");
            displayParse();
            XP();
            VP();
            TS();
        }
        else{done = true;}
    }
    private static void S(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("ID", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("SS");
            parseLog.push("ID");
            displayParse();
            checkParse();
            SS();
        }
        else{done = true;}
    }
    private static void SS(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("[", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("]");
            parseLog.push("R");
            parseLog.push("[");
            displayParse();
            checkParse();
            R();
            checkParse();
        }else if(tokenIs(")", ";", ",", "]", "=", "*", "/", "+", "-", "<=", "<", ">", ">=", "==", "!=")){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void T(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("ID", "(", "Num", null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("TS");
            parseLog.push("V");
            displayParse();
            V();
            TS();
        }
        else{done = true;}
    }
    private static void TS(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("<=", "<", ">", ">=", "==", "!=", null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("V");
            parseLog.push("U");
            displayParse();
            U();
            V();
        }else if(tokenIs(")", ";", ",", "]", null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void U(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("<=", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("<=");
            displayParse();
            checkParse();
        }
        else if(tokenIs("<", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("<");
            displayParse();
            checkParse();
        }
        else if(tokenIs(">", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push(">");
            displayParse();
            checkParse();
        }
        else if(tokenIs(">=", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push(">=");
            displayParse();
            checkParse();
        }
        else if(tokenIs("==", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("==");
            displayParse();
            checkParse();
        }
        else if(tokenIs("!=", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("!=");
            displayParse();
            checkParse();
        }
        else{done = true;}
    }
    private static void V(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("ID", "(", "Num", null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("VP");
            parseLog.push("X");
            displayParse();
            X();
            VP();
        }
        else{done = true;}
    }
    private static void VP(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("+", "-", null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("VP");
            parseLog.push("X");
            parseLog.push("W");
            displayParse();
            W();
            X();
            VP();
        }else if(tokenIs(")",";", ",", "]", "<=", "<", ">", ">=", "==", "!=", null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void W(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("+", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("+");
            displayParse();
            checkParse();
        }
        else if(tokenIs("-", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("-");
            displayParse();
            checkParse();
        }
        else{done = true;}
    }
    private static void X(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("ID", "(", "Num", null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("XP");
            parseLog.push("Z");
            displayParse();
            Z();
            XP();
        }
        else{done = true;}
    }
    private static void XP(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("*", "/", null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("XP");
            parseLog.push("Z");
            parseLog.push("Y");
            displayParse();
            Y();
            Z();
            XP();
        }else if(tokenIs(")",";", ",", "]", "+", "-", "<=", "<", ">", ">=", "==", "!=", null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void Y(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("*", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("*");
            displayParse();
            checkParse();
        }
        else if(tokenIs("/", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("/");
            displayParse();
            checkParse();
        }
        else{done = true;}
    }
    private static void Z(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("(", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push(")");
            parseLog.push("R");
            parseLog.push("(");
            displayParse();
            checkParse();
            R();
            checkParse();
        }
        else if(tokenIs("ID", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("ZS");
            parseLog.push("ID");
            displayParse();
            checkParse();
            ZS();
        }
        else if(tokenIs("Num", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("Num");
            displayParse();
            checkParse();
        }
        else{done = true;}
    }
    private static void ZS(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs(")", ";", ",", "[", "]", "*", "/", "+", "-", "<=", "<", ">", ">=", "==", "!=")){
            parseLog.pop();
            parseLog.push("SS");
            displayParse();
            SS();
        }
        else if(tokenIs("(", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push(")");
            parseLog.push("ZZ");
            parseLog.push("(");
            displayParse();
            checkParse();
            ZZ();
            checkParse();
        }
        else{done = true;}
    }
    private static void ZZ(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("ID", "(", null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("ZZZ");
            displayParse();
            ZZZ();
        }else if(tokenIs(")", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    private static void ZZZ(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs("ID", "(", "Num", null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("ZZZP");
            parseLog.push("R");
            displayParse();
            R();
            ZZZP();
        }
        else{done = true;}
    }
    private static void ZZZP(){
        if(done){return;}
        currentToken = tokenStack.peek();
        if(tokenIs(",", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            parseLog.push("ZZZP");
            parseLog.push("R");
            parseLog.push(",");
            displayParse();
            checkParse();
            R();
            ZZZP();
        }else if(tokenIs(")", null, null, null, null, null, null, null, null, null, null, null, null, null, null)){
            parseLog.pop();
            displayParse();
        }
        else{done = true;}
    }
    
    //-----------------------------------END OF RULES-----------------------------------//
    
    
    private static void displayParse(){
        if(SHOWPARSE){
            displayParseLog();
            displayNextTokens();
        }
    }
    private static void displayParseLog(){
        System.out.println("\n\n\n\nParseStack:");
        for(int i = 0; i < parseLog.size(); i++){
            System.out.print(parseLog.get(i) + " ");
        }
        System.out.print("<---");
        System.out.println();
    }
    private static void displayNextTokens(){
        System.out.println("\nNextTokens:");
        for(int i = 0; i < tokenStack.size(); i++){
            System.out.print(tokenStack.get(i) + " ");
        }
        System.out.print("<---");
        System.out.println();
    }
    private static boolean tokenIs(
            String s1, String s2, String s3, String s4, String s5, 
            String s6, String s7, String s8, String s9, String s10, 
            String s11, String s12, String s13, String s14, String s15
    ){
        //System.out.println(currentToken + " Compared to " + s1);
        return currentToken.equals(s1) || currentToken.equals(s2) || currentToken.equals(s3) || 
                currentToken.equals(s4) || currentToken.equals(s5) || currentToken.equals(s6) ||
                currentToken.equals(s7) || currentToken.equals(s8) || currentToken.equals(s9) ||
                currentToken.equals(s10) || currentToken.equals(s11) || currentToken.equals(s12) ||
                currentToken.equals(s13) || currentToken.equals(s14) || currentToken.equals(s15);
    }
    private static void checkParse(){
        if(parseLog.peek().equals(tokenStack.peek())){
            if(parseLog.peek().equals("$")){
                System.out.println("ACCEPT");
                System.exit(0);
            }
            parseLog.pop();
            tokenStack.pop();
            displayParse();
        }
        else{
            if(hangElse){
                rejectHangElse = true;
                parseLog = hangParseLog;
                tokenStack = hangTokenStack;
                OS();
            }
            System.out.println("REJECT");
            System.exit(0);
        }
    }
}
