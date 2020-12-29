package tafcal_compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Syed Mustafa Imam - 1812134
 */
public class SyntaxAnalysis {

    File file = new File("test.txt");
    ArrayList<String> identifiedTokens = new ArrayList<>();
    ArrayList<String> eachLineInArray = new ArrayList<>();
    ArrayList<String> errorInArray = new ArrayList<>();
    ArrayList<String> tokenName = new ArrayList<>();
    ArrayList<String> tokenForError = new ArrayList<>();
    ArrayList<String> tokentype = new ArrayList<>();
    ArrayList<String> attributeValue = new ArrayList<>();
    ArrayList<String> lineNumber = new ArrayList<>();
    String fileName = "test.txt";
    String inputCode = removeComments(fileName);
    int keyid = 0;
    int lineNo = 1;
    boolean errorFlag = false;

    public void printSymbolTable() {

        lineByLine();
        System.out.println("---------------------------------------Identifying Lexemes-------------------------------------");
        System.out.println("+==================================+=====================+====================================+\n\n");
        System.out.format("(%3s  , %-2s, %-2s)\n\n", "Token_Name", "Token_Type", "Attribute_Value");
                   System.out.println("+===========================================================================================+");
        identifyStringLiterals(stringToArray(inputCode));
        identifyRelocAndKeywords(stringToArray(inputCode));
        analysingIdentifiers(stringToArray(inputCode));
        identifyUnsignedInteger(stringToArray(inputCode));
        System.out.println("----------------------------------------------END----------------------------------------------\n\n");

        errorAnalysis();
        if (errorFlag == false) {
            System.out.println("---------------------------------------SYMBOL TABLE-------------------------------------------");
            System.out.println("+==================================+=====================+====================================+");
            System.out.format("|\t %-25s |\t %-15s |\t %-25s    |", "LEXEME", "TOKEN TYPE", "ATTRIBUTE VALUE");
            System.out.println("\n+==================================+=====================+====================================+");
            for (int i = 0; i < tokenName.size(); i++) {
                System.out.format("|\t %-25s |\t %-15s |\t %-25s    |\n", tokenName.get(i), tokentype.get(i), attributeValue.get(i));
                System.out.println("|__________________________________|_____________________|____________________________________|");
            }
        } else {
            System.out.println("\n\n---------------------------------------ERRORS--------------------------------------------------\n");
            printError();
            System.out.println("-----------------------------------------------------------------------------------------------"
                    + "\n\nProgram terminated due to ERRORS...\nTo Run Program remove or Comment ERRORS\nResolve Error First...\n\n");
        }
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
                        String str = "Line No. " + j + "\tError: " + lexeme;
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

    public String readFile(String fileName) {
        System.out.println("\n********************************WELCOME TO TAFCAL COMPILER*************************************");
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
        String[] strarr = new String[stname.length()];
        for (int i = 0; i < stname.length(); i++) {
            strarr[i] = Character.toString(stname.charAt(i));
        }
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
                System.out.format("(%3s  , %-2s, %-2s)\n\n",key8, "Keyword", Integer.toString(keyid));
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
                System.out.format("(%3s  , %-2s, %-2s)\n\n",key9, "Keyword", Integer.toString(keyid));
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
                System.out.format("(%3s  , %-2s, %-2s)\n\n",lexeme, "ID", Integer.toString(attributeValue.size()));
                            toSymbolTable(lexeme, "ID", Integer.toString(attributeValue.size()));
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
        Pattern string_pattern = Pattern.compile("^\"[0-9a-z A-Z_]*\"$");
        String lexeme = "";
        int i = 0;
        while (starr.length != i) {
            String temp = starr[i];
            if (temp.equals("=") || Character.isWhitespace(temp.charAt(0)) == true || temp.contentEquals(";")) {
                Matcher m = string_pattern.matcher(lexeme);
                if (m.matches() == true) {
                System.out.format("(%3s  , %-2s, %-2s)\n\n",lexeme, "sLiteral", Integer.toString(attributeValue.size()));
                    toSymbolTable(lexeme, "sLiteral", Integer.toString(attributeValue.size()));
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
                System.out.format("(%3s  , %-2s, %-2s)\n\n",lexeme, "uInt", Integer.toString(attributeValue.size()));
                    toSymbolTable(lexeme, "uInt", Integer.toString(attributeValue.size()));
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
            reader = new BufferedReader(new FileReader("test.txt"));
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
        System.out.println("----------------------------------INPUT CODE WITH LINE NUMBER--------------------------------------");

        for (int i = 1; i < eachLineInArray.size(); i++) {
            System.out.println(i + ". " + eachLineInArray.get(i));
        }

        System.out.println("----------------------------------END CODE--------------------------------------\n\n");

    }
}
