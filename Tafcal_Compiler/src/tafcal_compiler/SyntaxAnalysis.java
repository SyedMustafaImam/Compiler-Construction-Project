package tafcal_compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jdk.nashorn.internal.parser.TokenType;

/**
 *
 * @author Syed Mustafa Imam - 1812134
 */
public class SyntaxAnalysis {
//    String fileName = "C:/temp/sourcecode.txt";

    String fileName = "test.txt";
    File file = new File(fileName);
    ArrayList<String> identifiedTokens = new ArrayList<>();
    ArrayList<String> eachLineInArray = new ArrayList<>();
    ArrayList<String> errorInArray = new ArrayList<>();
    ArrayList<String> tokenName = new ArrayList<>();
    ArrayList<String> tokenForError = new ArrayList<>();
    ArrayList<String> tokentype = new ArrayList<>();
    ArrayList<String> attributeValue = new ArrayList<>();
    ArrayList<String> lineNumber = new ArrayList<>();
    String inputCode = removeComments(fileName);
    int keyid = 21;
    int lineNo = 1;
    boolean errorFlag = false;
    public String[] production_rules = {"E->E+T", "E->T", "T->T*F", "T->F", "F->(E)", "F->id"};
    public String[][] slrTable = {
        {"0", "s5", "", "", "s4", "", "", "1", "2", "3"},
        {"1", "", "s6", "", "", "", "acpt", "", "", ""},
        {"2", "", "r2", "s7", "", "r2", "r2", "", "", ""},
        {"3", "", "r4", "r4", "", "r4", "r4", "", "", ""},
        {"4", "s5", "", "", "s4", "", "", "8", "2", "3"},
        {"5", "", "r6", "r6", "", "r6", "r6", "", "", ""},
        {"6", "s5", "", "", "s4", "", "", "", "9", "3"},
        {"7", "s5", "", "", "s4", "", "", "", "", "10"},
        {"8", "", "s6", "", "", "s11", "", "", "", ""},
        {"9", "", "r1", "s7", "", "r1", "r1", "", "", ""},
        {"10", "", "r3", "r3", "", "r3", "r3", "", "", ""},
        {"11", "", "r5", "r5", "", "r5", "r5", "", "", ""}};
    String enter;

    public void printSymbolTable() {
        lineByLine();
        systemWait("CLICK on Console and Press ENTER to continue to step 3 and 4 to identify RELOP, OTOP and Keywords...");
        System.out.println("\n**************************************<--Step 3&4-->*******************************************\n");
        System.out.println("-------------------------------------Identifying Lexemes---------------------------------------");
        System.out.println("+==================================+=====================+====================================+\n\n");
        System.out.format("(%3s  , %-2s, %-2s)\n\n", "Token_Name", "Token_Type", "Attribute_Value");
        System.out.println("+=============================================================================================+");
        identifyRelocAndKeywords(stringToArray(inputCode));
        analysingIdentifiers(stringToArray(inputCode));
        identifyStringLiterals(stringToArray(inputCode));
        identifyUnsignedInteger(stringToArray(inputCode));
        System.out.println("----------------------------------------------END---------------------------------------------\n\n");
        systemWait("Press ENTER to check for error if any...");
        errorAnalysis();
        System.out.println("\n***************************************<--Step 6-->********************************************");
        System.out.println("------------------------------------------ERRORS-----------------------------------------------\n");
        if (errorFlag == true) {
            printError();
            System.out.println("-----------------------------------------------------------------------------------------------"
                    + "\n\nProgram terminated due to ERRORS...\nTo Run Program remove or Comment ERRORS\nResolve Error First...\n\n");
        } else {
            System.out.println("No Errors...");
        }
        systemWait("CLICK on Console and Press ENTER to continue to step 7 to Print Symbol table from Array List...");

        System.out.println("\n\n\n*****************************************<--Step 7-->******************************************");
        System.out.println("-----------------------------------------SYMBOL TABLE-----------------------------------------");
        System.out.println("+==================================+=====================+====================================+");
        System.out.format("|\t %-25s |\t %-15s |\t %-25s    |", "Token Name", "TOKEN TYPE", "ATTRIBUTE VALUE");
        System.out.println("\n+==================================+=====================+====================================+");
        for (int i = 0; i < tokenName.size(); i++) {
            System.out.format("|\t %-25s |\t %-15s |\t %-25s    |\n", tokenName.get(i), tokentype.get(i), attributeValue.get(i));
            System.out.println("|__________________________________|_____________________|____________________________________|");
        }
        System.out.println("\n\n\n");
    }

    public void errorAnalysis() {
        String[] starr = stringToArray(inputCode);
        Pattern string_pattern = Pattern.compile("^\"[0-9a-z A-Z_]*\"$");
        String lexeme = "";
        int i = 0;
        int count = 0;
        if (starr[i].equals("\n") || starr[i].equals("\r")) {
            lineNo++;
        }
        while (starr.length != i) {
            String temp = starr[i];
            if (temp.equals("=") || Character.isWhitespace(temp.charAt(0)) == true || temp.contentEquals(";") || temp.contentEquals(")") || temp.contentEquals("}") || temp.contentEquals("{") || temp.contentEquals("(") || temp.contentEquals("<") || temp.contentEquals(">")) {
                Matcher m = string_pattern.matcher(lexeme);
                if (tokenForError.contains(lexeme)) {
//                    System.out.println(lexeme.replace("\"", "") + "\t String  --sliteral");
                    lexeme = "";
                    i++;
                } else {
                    if (lexeme == null || lexeme.equals("")) {
                    } else {
                        String tem = lexeme.concat("();");
                        int j = 0;
                        if (eachLineInArray.contains(lexeme) || eachLineInArray.contains(lexeme.concat(temp))) {
                            while (!lexeme.equals(eachLineInArray.get(j))) {
                                j++;
                            }
                            i++;
                        }
                        errorFlag = true;
                        String str = String.format("Line No. %-5s \t Error: %-20s  \t Error Message: %-25s", j, lexeme, "Please declare the identifiers or keywords Proprly! ");
                        errorInArray.add(str);
                    }
                    lexeme = "";
                    i++;
                }

            } else {
                lexeme = lexeme.concat(temp);
                i++;
            }
        }
    }

    public void printError() {
        for (int i = 0; i < errorInArray.size(); i++) {
            System.out.println(errorInArray.get(i));
        }
    }

    public void systemWait(String strings) {
        Scanner in = new Scanner(System.in);

        System.out.println(strings);
        enter = in.nextLine();

    }

    public String readFile(String fileName) {

        System.out.println("\n********************************WELCOME TO TAFCAL COMPILER*************************************");
        System.out.println("------------------------------------------------------------------------------------------------------------------------"
                + "\n***\nThis Compiler is Made by Syed Mustafa Imam. As the final Project of Compiler Construction "
                + "course given by Sir Khawaja.\n"
                + "I have named my compiler 'TAFCAL' as 'TAF' is my nick Name and 'CAL' is for Compiler.\n"
                + "My compiler for TAFCAL language, which is defined by Sir Khawaja, it can recognize lexeme and  it will\n"
                + "decide that the lexeme should go into the symbol table or not.It also assigns the token type and \n"
                + "attributes value to each token. This TAFCAL Compiler can also find some basic Errors.It can also extract\n"
                + "the values from the SLR(1) parse table and also from the given CFG.\n***\nThe reserved keywords for this language are:\n"
                + "begin, end, if, then, else, int, float, char, string.\n\n"
                + "***"
                + "\nNOTE: TO EXECUTE EACH STEP, FIRST CLICK ON CONSOLE AND THEN PRESS ENTER AFTER EVERY STEP\n"
                + "------------------------------------------------------------------------------------------------------------------------");

        systemWait("CLICK on Console and Press ENTER to continue to step 1 to read or create file...");
        System.out.println("\n***************************************<--Step 1-->*********************************************\n");
        System.out.println("----------------------------------Reading Input Form File--------------------------------------");
        final File file = new File(fileName);
        char[] buffer = null;
        System.out.println("Creating or Finding File...\n");
        try {
            if (file.createNewFile()) {
                System.out.println("File Created" + file.getName());
            } else {
                System.out.println("File already exist.");
            }
        } catch (IOException e) {
            System.out.print("Got into an error!");
        }
        int line = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            buffer = new char[(int) file.length()];
            int i = 0;
            int c = bufferedReader.read();
            while (c != -1) {
                buffer[i++] = (char) c;
                c = bufferedReader.read();
                if (c == 10) {
                    line++;
                }
            }

        } catch (final IOException e) {
        }
        return new String(buffer);
    }

    public void toSymbolTable(String Name, String Type, String value) {
        String tokenNames = Name, tokenTypes = Type, atValue = value;
        if (tokenName.contains(tokenNames) == false) {
            tokenName.add(tokenNames);
            tokentype.add(tokenTypes);
            attributeValue.add(atValue);
        }
    }

    public String removeComments(String fileName) {
        String inputStr = readFile(fileName);
        String rem_comments = inputStr.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)|", "");
        return rem_comments;
    }

    public ArrayList<String> stringToArrayList(String stname) {
        ArrayList<String> strarr = new ArrayList<>();
        for (int i = 0; i < stname.length(); i++) {
            strarr.add(Character.toString(stname.charAt(i)));
        }
        return strarr;
    }

    static String[] stringToArray(String stname) {
        String[] strarr = new String[stname.length() + 1];
        int j = 0;
        for (int i = 0; i < stname.length(); i++) {
            strarr[i] = Character.toString(stname.charAt(i));
            j++;
        }
        strarr[j] = "\n";
        return strarr;
    }

    public void identifyRelocAndKeywords(String[] strarr) {
        final int arrayLength = strarr.length;
        for (int i = 0; i < arrayLength; i++) {
            if (strarr[i].equals("=") || strarr[i].equals(">") || strarr[i].equals("<")) {
                i++;
                if (strarr[i].equals("=") || strarr[i].equals(">") || strarr[i].equals("<")) {
                    final String d1 = strarr[i - 1];
                    final String d2 = strarr[i];
                    if ((d1 + d2).equals("<=")) {
                        strarr[i] = d1 + d2 + "\tLE --Relop Identified!";
                        toSymbolTable("<=", "RELOP", "LE");
                        tokenForError.add("<=");
                        System.out.format("(%3s  , %-2s, %-2s)\n\n", "Token_Name", "<=", "RELOP", "LE");
                    }
                    if ((d1 + d2).equals(">=")) {
                        strarr[i] = d1 + d2 + "\tGE --Relop Identified!";
                        toSymbolTable(">=", "RELOP", "GE");
                        System.out.format("(%3s  , %-2s, %-2s)\n\n", ">=", "RELOP", "GE");
                        tokenForError.add(">=");
                    }
                    if ((d1 + d2).equals("==")) {
                        strarr[i] = d1 + d2 + "\tEQ --Relop Identified!";
                        toSymbolTable("==", "RELOP", "EQ");
                        System.out.format("(%3s  , %-2s, %-2s)\n\n", "==", "RELOP", "EQ");
                        tokenForError.add("==");
                    }
                    if ((d1 + d2).equals("<>")) {
                        strarr[i] = d1 + d2 + "\tNE --Relop Identified!";
                        toSymbolTable("<>", "RELOP", "NE");
                        System.out.format("(%3s  , %-2s, %-2s)\n\n", "<>", "RELOP", "NE");
                        tokenForError.add("<>");
                    }
                } else {
                    i--;
                }
            }

            if (strarr[i].equals(">") || strarr[i].equals("<")) {
                if (strarr[i].equals(">")) {
                    String f1 = strarr[i];
                    strarr[i] = f1 + "\tGT --Relop Identified!";
                    toSymbolTable(">", "RELOP", "GT");
                    System.out.format("(%3s  , %-2s, %-2s)\n\n", ">", "RELOP", "GT");
                    tokenForError.add(">");
                    i--;
                }
                if (strarr[i].equals("<")) {
                    String f2 = strarr[i];
                    strarr[i] = f2 + "\tLT --Relop Identified!";
                    toSymbolTable("<", "RELOP", "LT");
                    System.out.format("(%3s  , %-2s, %-2s)\n\n", "<", "RELOP", "LT");
                    tokenForError.add("<");
                    i--;
                } else {
                    continue;
                }
            }
            // Identifying Operators
            if (strarr[i].equals("+")) {
                String arop = strarr[i];
                toSymbolTable("+", "AROP", "ADD");
                System.out.format("(%3s  , %-2s, %-2s)\n\n", "+", "AROP", "ADD");
                tokenForError.add("+");
                continue;
            }
            if (strarr[i].equals("-")) {
                String arop = strarr[i];
                toSymbolTable("-", "AROP", "SUB");
                System.out.format("(%3s  , %-2s, %-2s)\n\n", "-", "AROP", "SUB");
                tokenForError.add("-");
                continue;
            }
            if (strarr[i].equals("*")) {
                toSymbolTable("*", "AROP", "MUL");
                String arop = strarr[i];
                System.out.format("(%3s  , %-2s, %-2s)\n\n", "*", "AROP", "MUL");
                tokenForError.add("*");
                continue;
            }
            if (strarr[i].equals("/")) {
                String arop = strarr[i];
                toSymbolTable("/", "AROP", "DIV");
                System.out.format("(%3s  , %-2s, %-2s)\n\n", "/", "AROP", "DIV");
                tokenForError.add("/");
                continue;
            }
            // Identifing Other Operators OTOP
            if (strarr[i].equals("=")) {
                String arop = strarr[i];
                toSymbolTable("=", "OTOP", "ASN");
                System.out.format("(%3s  , %-2s, %-2s)\n\n", "=", "OTOP", "ASN");
                tokenForError.add("=");
                continue;
            }
            if (strarr[i].equals("(")) {
                String arop = strarr[i];
                toSymbolTable("(", "OTOP", "LPRN");
                System.out.format("(%3s  , %-2s, %-2s)\n\n", "(", "OTOP", "LPRN");
                tokenForError.add("(");
                continue;
            }

            if (strarr[i].equals(")")) {
                String arop = strarr[i];
                toSymbolTable(")", "OTOP", "RPRN");
                System.out.format("(%3s  , %-2s, %-2s)\n\n", ")", "OTOP", "RPRN");
                tokenForError.add(")");
                continue;
            }

            if (strarr[i].equals("{")) {
                String arop = strarr[i];
                toSymbolTable("{", "OTOP", "LBRC");
                tokenForError.add("{");
                System.out.format("(%3s  , %-2s, %-2s)\n\n", "{", "OTOP", "LBRC");
                continue;

            }

            if (strarr[i].equals("}")) {
                String arop = strarr[i];
                toSymbolTable("}", "OTOP", "RBRC");
                tokenForError.add("}");
                System.out.format("(%3s  , %-2s, %-2s)\n\n", "}", "OTOP", "RBRC");
                continue;
            }

            if (strarr[i].equals(";")) {
                String arop = strarr[i];
                toSymbolTable(";", "OTOP", "LNTR");
                tokenForError.add(";");
                System.out.format("(%3s  , %-2s, %-2s)\n\n", ";", "OTOP", "LNTR");
                continue;
            }
            // Identifying the Keywords

            if (strarr[i].equals("e")) {
                if (strarr[i + 1].equals("l")) {
                    if (strarr[i + 2].equals("s")) {
                        if (strarr[i + 3].equals("e")) {
                            if (Character.isWhitespace(strarr[i + 4].charAt(0)) || strarr[i + 4].equals("{")) {
                                i = i + 3;
                                keyid++;
                                String key5 = "else";
                                strarr[i] = key5 + "\t--Keyword " + keyid + " Identified";
//                                toSymbolTable(key5, "Keyword", Integer.toString(keyid));
                                System.out.format("(%3s  , %-2s, %-2s)\n\n", key5, "Keyword", Integer.toString(keyid));
                                tokenForError.add(key5);
                            }
                        }
                    }
                }
            }

            if (strarr[i].equals("b")) {
                if (strarr[i + 1].equals("e")) {
                    if (strarr[i + 2].equals("g")) {
                        if (strarr[i + 3].equals("i")) {
                            if (strarr[i + 4].equals("n")) {
                                if (Character.isWhitespace(strarr[i + 5].charAt(0)) || strarr[i + 5].equals(";")) {
                                    i = i + 4;
                                    keyid++;
                                    String key1 = "begin";
                                    strarr[i] = key1 + "\t--Keyword " + keyid + " Identified";
//                                    toSymbolTable(key1, "Keyword", Integer.toString(keyid));
                                    System.out.format("(%3s  , %-2s, %-2s)\n\n", key1, "Keyword", Integer.toString(keyid));
                                    tokenForError.add(key1);
                                }
                            }
                        }
                    }
                }
            }

            if (strarr[i].equals("e")) {
                if (strarr[i + 1].equals("n")) {
                    if (strarr[i + 2].equals("d")) {
                        if (Character.isWhitespace(strarr[i + 3].charAt(0)) || strarr[i + 3].equals(";")) {
                            i = i + 2;
                            keyid++;
                            String key2 = "end";
//                            toSymbolTable(key2, "Keyword", Integer.toString(keyid));
                            strarr[i] = key2 + "\t--Keyword " + keyid + " Identified";
                            System.out.format("(%3s  , %-2s, %-2s)\n\n", key2, "Keyword", Integer.toString(keyid));
                            tokenForError.add(key2);
                        }
                    }
                }
            }

            if (strarr[i].equals("i")) {
                if (strarr[i + 1].equals("f")) {
                    if (Character.isWhitespace(strarr[i + 2].charAt(0)) || strarr[i + 2].equals("(")) {
                        keyid++;
                        i = i + 1;
                        String key3 = "if";
//                        toSymbolTable(key3, "Keyword", Integer.toString(keyid));
                        strarr[i] = key3 + "\t--Keyword " + keyid + " Identified";
                        System.out.format("(%3s  , %-2s, %-2s)\n\n", key3, "Keyword", Integer.toString(keyid));
                        tokenForError.add(key3);
                    }
                }
            }

            if (strarr[i].equals("t")) {
                if (strarr[i + 1].equals("h")) {
                    if (strarr[i + 2].equals("e")) {
                        if (strarr[i + 3].equals("n")) {
                            if (Character.isWhitespace(strarr[i + 4].charAt(0)) || strarr[i + 4].equals(";") || strarr[i + 4].equals("{")) {
                                i = i + 3;
                                keyid++;
                                String key4 = "then";
//                                toSymbolTable(key4, "Keyword", Integer.toString(keyid));
                                strarr[i] = key4 + "\t--Keyword " + keyid + " Identified";
                                System.out.format("(%3s  , %-2s, %-2s)\n\n", key4, "Keyword", Integer.toString(keyid));
                                tokenForError.add(key4);
                            }
                        }

                    }

                }

            }

            if (strarr[i].equals("i")) {
                if (strarr[i + 1].equals("n")) {
                    if (strarr[i + 2].equals("t")) {
                        if (Character.isWhitespace(strarr[i + 3].charAt(0))) {
                            i = i + 3;
                            keyid++;
                            String key6 = "int";
//                            toSymbolTable(key6, "Keyword", Integer.toString(keyid));
                            strarr[i] = key6 + "\t--Keyword " + keyid + " Identified";
                            System.out.format("(%3s  , %-2s, %-2s)\n\n", key6, "Keyword", Integer.toString(keyid));
                            tokenForError.add(key6);
                        }
                    }
                }

            }

            if (strarr[i].equals("f")) {
                if (strarr[i + 1].equals("l")) {
                    if (strarr[i + 2].equals("o")) {
                        if (strarr[i + 3].equals("a")) {
                            if (strarr[i + 4].equals("t")) {
                                if (Character.isWhitespace(strarr[i + 5].charAt(0))) {
                                    i = i + 5;
                                    keyid++;
                                    String key7 = "float";
                                    tokenForError.add(key7);
//                                    toSymbolTable(key7, "Keyword", Integer.toString(keyid));
                                    strarr[i] = key7 + "\t--Keyword " + keyid + " Identified";
                                    System.out.format("(%3s  , %-2s, %-2s)\n\n", key7, "Keyword", Integer.toString(keyid));

                                }
                            }
                        }
                    }
                }
            }

            if (strarr[i].equals("c")) {
                if (strarr[i + 1].equals("h")) {
                    if (strarr[i + 2].equals("a")) {
                        if (strarr[i + 3].equals("r")) {
                            if (Character.isWhitespace(strarr[i + 4].charAt(0))) {
                                i = i + 3;
                                keyid++;
                                String key8 = "char";
                                strarr[i] = key8 + "\t--Keyword " + keyid + " Identified";
                                System.out.format("(%3s  , %-2s, %-2s)\n\n", key8, "Keyword", Integer.toString(keyid));
//                                toSymbolTable(key8, "Keyword", Integer.toString(keyid));
                                tokenForError.add(key8);
                            }
                        }
                    }
                }
            }

            if (strarr[i].equals("s")) {
                if (strarr[i + 1].equals("t")) {
                    if (strarr[i + 2].equals("r")) {
                        if (strarr[i + 3].equals("i")) {
                            if (strarr[i + 4].equals("n")) {
                                if (strarr[i + 5].equals("g")) {
                                    if (Character.isWhitespace(strarr[i + 6].charAt(0))) {
                                        i = i + 6;
                                        keyid++;
                                        String key9 = "string";
                                        strarr[i] = key9 + "\t--Keyword " + keyid + " Identified";
                                        System.out.format("(%3s  , %-2s, %-2s)\n\n", key9, "Keyword", Integer.toString(keyid));
//                                        toSymbolTable(key9, "Keyword", Integer.toString(keyid));
                                        tokenForError.add(key9);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void analysingIdentifiers(String[] strarr) {
        String lexeme = "";
        Pattern identifer_pattern = Pattern.compile("^([a-zA-Z_$][a-zA-Z\\d_$]*)$");
        int i = 0;
        systemWait("CLICK on Console and Press ENTER to continue to step 5 to identify Identifiers...");
        System.out.println("\n***************************************<--Step 5-->********************************************\n");
        while (strarr.length != i) {
            String temp = strarr[i];
            if ((temp.equals("=") || Character.isWhitespace(temp.charAt(0)) == true || temp.contentEquals(";"))) {
                Matcher m = identifer_pattern.matcher(lexeme);
                if (m.matches() == true) {
                    switch (lexeme) {
                        case "if":
//                            System.out.println("if" + "\t Keyword");
                            i++;
                            lexeme = "";
                            break;
                        case "begin":
//                            System.out.println("begin"+ "\t Keyword");
                            i++;
                            lexeme = "";
                            break;
                        case "then":
//                            System.out.println("then" + "\t Keyword");
                            i++;
                            lexeme = "";
                            break;
                        case "int":
//                            System.out.println("int" + "\t Keyword");
                            i++;
                            lexeme = "";
                            break;
                        case "float":
//                            System.out.println("float" + "\t Keyword");
                            i++;
                            lexeme = "";
                            break;
                        case "char":
//                            System.out.println("char" + "\t Keyword");
                            i++;
                            lexeme = "";
                            break;
                        case "else":
//                            System.out.println("else" + "\t Keyword");
                            i++;
                            lexeme = "";
                            break;
                        case "string":
//                            System.out.println("else" + "\t Keyword");
                            i++;
                            lexeme = "";
                            break;
                        case "end":
//                            System.out.println("else" + "\t Keyword");
                            i++;
                            lexeme = "";
                            break;
                        default:
                            keyid++;
                            tokenForError.add(lexeme);
                            toSymbolTable(lexeme, "ID", Integer.toString(attributeValue.size() + 1));
                            System.out.format("(%3s  , %-2s, %-2s)\n\n", lexeme, "ID", getAttValue(lexeme));
                            i++;
                            lexeme = "";
                            break;
                    }
                } else {
                    lexeme = "";
                    i++;
                }
            } else {
                lexeme = lexeme.concat(temp);
//                System.out.println(lexeme);
                i++;
            }
        }
    }

    public void identifyStringLiterals(String[] starr) {
        systemWait("CLICK on Console and Press ENTER to continue to step 6 to identify String Literals and Unsigned Integers...");

        System.out.println("\n***************************************<--Step 6-->********************************************\n");
        Pattern string_pattern = Pattern.compile("^\"[0-9a-zA-Z\\a-z_]*\"$");
//        Pattern string_pattern = Pattern.compile("^\"[0-9a-z\\n A-Z_]*\"$ | . | [A-Z]* . \\n [a-z] | \\D | \\s | \\S | \\w | \\W | \\b | \\B | [^\\w]");
        String lexeme = "";
        int i = 0;
        while (starr.length != i) {
            String temp = starr[i];
            if (temp.equals("=") || Character.isWhitespace(temp.charAt(0)) == true || temp.contentEquals(";")) {
                Matcher m = string_pattern.matcher(lexeme);
                if (m.matches() == true) {
                    toSymbolTable(lexeme, "sLiteral", Integer.toString(attributeValue.size() + 1));
                    System.out.format("(%3s  , %-2s, %-2s)\n\n", lexeme, "sLiteral", getAttValue(lexeme));
                    tokenForError.add(lexeme);
                    lexeme = "";
                    i++;
                } else {
                    lexeme = "";
                    i++;
                }
            } else {
                lexeme = lexeme.concat(temp);
                i++;
            }
        }
    }

    public void identifyUnsignedInteger(String[] starr) {
        int i = 0;
        String lexeme = "";
        String regex = "^\\d+$";
        Pattern p = Pattern.compile(regex);
        while (starr.length != i) {
            String temp = starr[i];
            if (temp.equals("=") || Character.isWhitespace(temp.charAt(0)) == true || temp.contentEquals(";")) {
                Matcher m = p.matcher(lexeme);
                if (m.matches() == true) {
                    toSymbolTable(lexeme, "uInt", Integer.toString(attributeValue.size() + 1));
                    System.out.format("(%3s  , %-2s, %-2s)\n\n", lexeme, "uInt", getAttValue(lexeme));
                    tokenForError.add(lexeme);
                    i++;
                    lexeme = "";
                } else {
                    lexeme = "";
                    i++;
                }
            } else {
                lexeme = lexeme.concat(temp);
                i++;
            }
        }
    }

    public void lineByLine() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            eachLineInArray.add(null);
            eachLineInArray.add(line);
            while (line != null) {
                // read next line
                line = reader.readLine();
                eachLineInArray.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        systemWait("CLICK on Console and Press ENTER to continue"
                + " to see our input code with line number...");
        System.out.println("----------------------------------INPUT CODE WITH LINE NUMBER----------------------------------");
        for (int i = 1; i < eachLineInArray.size(); i++) {
            System.out.println(i + ". " + eachLineInArray.get(i));
        }
        System.out.println("--------------------------------------------END CODE-------------------------------------------\n\n");
        systemWait("CLICK on Console and Press ENTER to continue to step 2...");
        System.out.println("\n***************************************<--Step 2-->********************************************");
        System.out.println("------------------------------INPUT CODE Without Spaces and Comments----------------------------");
//        String inputCommentsWithout = removeComments(inputCode);
//        String [] input = stringToArray(inputCommentsWithout);
        String rem_comments = inputCode.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)|\\n|\\s|", "");
        String[] myCode = stringToArray(rem_comments);
        for (int i = 0; i < myCode.length; i++) {
            System.out.println(myCode[i]);
        }
        System.out.println("--------------------------------------------END CODE-------------------------------------------\n\n");

    }
    Scanner input = new Scanner(System.in);

    public void generatingCFGRules() {
        systemWait("CLICK on Console and Press ENTER to continue to step 8 to print Production Rules stored in the array...");

        System.out.println("\n\n\n****************************************************<--Step 8-->**************************************************");

        System.out.println("-----------------------------------------------------CFG RULES----------------------------------------------------");
        System.out.println("Rule: ");
        for (int arr = 0; arr < production_rules.length; arr++) {
            System.out.println("\tNo." + (arr + 1) + " :" + production_rules[arr]);
        }
        System.out.println("-----------------------------------------------------END RULES----------------------------------------------------");
    }

    public void getSLRTable() {
        System.out.println("----------------------------------------------SLR(1) Parsing Table---------------------------------------------");
        System.out.println("|==========|==========|==========|==========|==========|==========|==========|==========|==========|==========|");
        System.out.format("| %5s    | %5s    | %5s    | %5s    | %5s    | %5s    | %5s    | %5s    | %5s    | %5s    |\n", "State", "id", "+", "*", "(", ")", "$", "E", "T", "F");
        System.out.format("|==========|==========|==========|==========|==========|==========|==========|==========|==========|==========|\n");
        for (int arr1 = 0; arr1 < slrTable.length; arr1++) {
            for (int arr2 = 0; arr2 < slrTable[arr1].length; arr2++) {

                System.out.format("| %5s    ", slrTable[arr1][arr2]);
            }
            System.out.print("|\n");
            System.out.println("|__________|__________|__________|__________|__________|__________|__________|__________|__________|__________|");
        }
    }

    public void slrParsing() {
        boolean flag = true;
        systemWait("\n\n\nCLICK on Console and Press ENTER to continue to step 8 to print SLR(1) \nParsing table from the 2d Array and Fetching the specific action from the SLR table...");
        getSLRTable();
        int arrayIndexOfLexeme;
        System.out.println("----------------------------------------------------SLR(1) Parsing-------------------------------------------------");
        while (flag != false) {
            System.out.print("Enter state No. from table  :\t");
            String state1 = input.nextLine();
            System.out.print("Enter lexeme from table     :\t");
            String lexeme = input.nextLine();
            if (state1.matches("[0-9]*") && (lexeme.equals("id") || lexeme.equals("+") || lexeme.equals("*") || lexeme.equals("(") || lexeme.equals(")") || lexeme.equals("$") || lexeme.equals("E") || lexeme.equals("T") || lexeme.equals("F"))) {
                switch (lexeme) {
                    case "id":
                        arrayIndexOfLexeme = 1;
                        break;
                    case "+":
                        arrayIndexOfLexeme = 2;
                        break;
                    case "*":
                        arrayIndexOfLexeme = 3;
                        break;
                    case "(":
                        arrayIndexOfLexeme = 4;
                        break;
                    case ")":
                        arrayIndexOfLexeme = 5;
                        break;
                    case "$":
                        arrayIndexOfLexeme = 6;
                        break;
                    case "E":
                        arrayIndexOfLexeme = 7;
                        break;
                    case "T":
                        arrayIndexOfLexeme = 8;
                        break;
                    case "F":
                        arrayIndexOfLexeme = 9;
                        break;
                    default:
                        arrayIndexOfLexeme = 10;
                        break;
                }

                int state = Integer.parseInt(state1);
                if (state >= 0 && state <= 11) {
                    flag = false;
                    if (slrTable[state][arrayIndexOfLexeme].equals("")) {
                        System.out.println("\nOutput: error");
                        System.out.println("\n\n--------------------------------------"
                                + "\nIf you want to parse again press (y) OR to exit (e): ");
                        String toExit = input.nextLine();
                        if (toExit.equalsIgnoreCase("y")) {
                            System.out.println("\n\n\n\n");

                            flag = true;
                        } else {
                            flag = false;
                        }
                    } else {
                        System.out.println("\nOutput: " + slrTable[state][arrayIndexOfLexeme]);
                        System.out.println("\n\n--------------------------------------"
                                + "\nIf you want to parse again press (y) OR to exit (e): ");
                        String toExit = input.nextLine();
                        if (toExit.equalsIgnoreCase("y")) {
                            System.out.println("\n\n\n\n");

                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                } else {
                    flag = true;
                    System.out.println("\n\t\t\t\t\t\t   XXX ERROR XXX\n"
                            + "------------------------------------------------------------------------------------------------------------------\n"
                            + "Please Enter Correct Sate No. or Lexeme from the SLR Table...\n\n");
                }

            } else {
                flag = true;
                System.out.println("\n\t\t\t\t\t\t   XXX ERROR XXX\n"
                        + "------------------------------------------------------------------------------------------------------------------\n"
                        + "Please Enter Correct Sate No. or Lexeme from the SLR Table...\n\n");
            }

        }
    }

    public void getCFGRules() {
        generatingCFGRules();
        boolean flag = true;
        while (flag != false) {
            System.out.print("Enter any Non-Terminal or Rule No. from these Rules     :\t");
            String nonTerminal = input.nextLine();
            switch (nonTerminal) {
                case "E":
                    System.out.println("No.1 :" + production_rules[0]);
                    System.out.println("No.2 :" + production_rules[1]);
                    flag = false;
                    break;
                case "T":
                    System.out.println("No.3 :" + production_rules[2]);
                    System.out.println("No.4 :" + production_rules[3]);
                    flag = false;
                    break;
                case "F":
                    System.out.println("No.5 :" + production_rules[4]);
                    System.out.println("No.6 :" + production_rules[5]);
                    flag = false;
                    break;
                case "1":
                    System.out.println("No.1 :" + production_rules[0]);
                    flag = false;
                    break;
                case "2":
                    System.out.println("No.2 :" + production_rules[1]);
                    flag = false;
                    break;
                case "3":
                    System.out.println("No.3 :" + production_rules[2]);
                    flag = false;
                    break;
                case "4":
                    System.out.println("No.4 :" + production_rules[3]);
                    flag = false;
                    break;
                case "5":
                    System.out.println("No.5 :" + production_rules[4]);
                    flag = false;
                    break;
                case "6":
                    System.out.println("No.6 :" + production_rules[5]);
                    flag = false;
                    break;
                default:
                    flag = true;
                    System.out.println("\n\t\t\t\t\t\t   XXX ERROR XXX\n"
                            + "------------------------------------------------------------------------------------------------------------------\n"
                            + "Please Enter Correct Production Rule from the Production Rules...\n\n");
            }
        }
    }
    int i = 0;

    public String getAttValue(String lexeme) {
        int j = 0;
        if (tokenName.contains(lexeme) == true) {
            while (!lexeme.equals(tokenName.get(j))) {
                j++;
                i++;
            }

            return String.valueOf(j + 1);
        } else {
            return String.valueOf(tokenName.size() + 1);
        }

    }

}
