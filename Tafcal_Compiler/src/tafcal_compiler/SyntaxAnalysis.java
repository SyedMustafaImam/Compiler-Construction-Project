/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tafcal_compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author cs181
 */
public class SyntaxAnalysis {

    File file = new File("test.txt");
    ArrayList<String> eachLineInArray = new ArrayList<>();
    ArrayList<String> identifiedTokens = new ArrayList<>();

    ArrayList<String> lexeme = new ArrayList<>();
    ArrayList<String> token = new ArrayList<>();
    ArrayList<String> attributeValue = new ArrayList<>();

    String fileName = "test.txt";
    String inputCode = removeComments(fileName);

    public void printngFile() {

//
//    identifiedTokens = stringToArrayList(inputCode);  
//        for (int i = 1; i < identifiedTokens.size(); i++) {
//            System.out.println(identifiedTokens.get(i));
//        }
        identifyRelocAndKeywords(stringToArray(inputCode));
        analysingIdentifiers(stringToArray(inputCode));

    }

    public String readFile(String fileName) {
        System.out.println("\n*************Welcome to Text Reader**********\n\n");

        System.out.println("\n---------------Reading Input Form File-------------------");

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
            e.printStackTrace();
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            buffer = new char[(int) file.length()];

            int i = 0;
            int c = bufferedReader.read();

            while (c != -1) {
                buffer[i++] = (char) c;
                c = bufferedReader.read();

            }

        } catch (final IOException e) {
            e.printStackTrace();
        }

        return new String(buffer);
    }

    public String removeComments(String fileName) {

        String inputStr = readFile(fileName);

        String rem_comments = inputStr.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)|", "");

        return rem_comments;
    }

    public ArrayList<String> stringToArrayList(String stname) {

//        final String[] strarr = new String[stname.length()];
        ArrayList<String> strarr = new ArrayList<>();
        for (int i = 0; i < stname.length(); i++) {
            strarr.add(Character.toString(stname.charAt(i)));

        }

        return strarr;

    }

    static String[] stringToArray(String stname) {

        final String[] strarr = new String[stname.length()];
        for (int i = 0; i < stname.length(); i++) {
            strarr[i] = Character.toString(stname.charAt(i));

        }

        return strarr;

    }

    static void identifyRelocAndKeywords(String[] strarr) {
        int keyid = 1;
        final int arrayLength = strarr.length;
        for (int i = 0; i < arrayLength; i++) {

            if (strarr[i].equals("=") || strarr[i].equals(">") || strarr[i].equals("<")) {
                i++;
                if (strarr[i].equals("=") || strarr[i].equals(">") || strarr[i].equals("<")) {
                    final String d1 = strarr[i - 1];
                    final String d2 = strarr[i];

                    if ((d1 + d2).equals("<=")) {
                        strarr[i] = d1 + d2 + "\tLE --Relop Identified!";
                        System.out.println(strarr[i]);

                    }

                    if ((d1 + d2).equals(">=")) {
                        strarr[i] = d1 + d2 + "\tGE --Relop Identified!";
                        System.out.println(strarr[i]);


                    }

                    if ((d1 + d2).equals("==")) {
                        strarr[i] = d1 + d2 + "\tEQ --Relop Identified!";
                                                System.out.println(strarr[i]);


                    }
                    
                    if ((d1 + d2).equals("<>")) {
                        strarr[i] = d1 + d2 + "\tNE --Relop Identified!";
                                                System.out.println(strarr[i]);


                    }

                } else {
                    i--;
                }
            }

            if (strarr[i].equals(">") || strarr[i].equals("<")) {
                if (strarr[i].equals(">")) {
                    String f1 = strarr[i];
                    strarr[i] = f1 + "\tGT --Relop Identified!";
                                            System.out.println(strarr[i]);

                    i--;

                }

                if (strarr[i].equals("<")) {
                    String f2 = strarr[i];
                    strarr[i] = f2 + "\tLT --Relop Identified!";
                                            System.out.println(strarr[i]);

                    i--;
                } else {

                    continue;
                }
            }

            // Identifying Operators
            if (strarr[i].equals("+")) {
                String arop = strarr[i];

                System.out.println(arop + "\t --ADD Arop Identified!");
                
                continue;

            }
            if (strarr[i].equals("-")) {
                String arop = strarr[i];

                System.out.println(arop + "\t --SUB Arop Identified!");
                continue;

            }

            if (strarr[i].equals("*")) {
                String arop = strarr[i];

                System.out.println(arop + "\t --MUL Arop Identified!");
                continue;

            }

            if (strarr[i].equals("/")) {
                String arop = strarr[i];

                System.out.println(arop + "\t --DIV Arop Identified!");
                continue;

            }

            if (strarr[i].equals("-")) {
                String arop = strarr[i];

                System.out.println(arop + "\t --SUB Arop Identified!");
                continue;

            }

            // Identifing Other Operators OTOP
            if (strarr[i].equals("=")) {
                String arop = strarr[i];

                System.out.println(arop + "\t --ASN Otop Identified!");
                continue;

            }

            if (strarr[i].equals("(")) {
                String arop = strarr[i];

                System.out.println(arop + "\t --LPRN Otop Identified!");
                continue;

            }

            if (strarr[i].equals(")")) {
                String arop = strarr[i];

                System.out.println(arop + "\t --RPRN Otop Identified!");
                continue;

            }

            if (strarr[i].equals("{")) {
                String arop = strarr[i];

                System.out.println(arop + "\t --LBRC Otop Identified!");
                continue;

            }

            if (strarr[i].equals("}")) {
                String arop = strarr[i];

                System.out.println(arop + "\t --RBRC Otop Identified!");
                continue;

            }

            if (strarr[i].equals(";")) {
                String arop = strarr[i];

                System.out.println(arop + "\t --LNTR Otop Identified!");
                continue;

            }
            // Identifying the Keywords

            if (strarr[i].equals("e")) {

                if (strarr[i + 1].equals("l")) {

                    if (strarr[i + 2].equals("s")) {

                        if (strarr[i + 3].equals("e")) {
                            if (Character.isWhitespace(strarr[i + 4].charAt(0)) || strarr[i + 4].equals("{")) {
                                i = i + 3;

                                String key5 = "else";
                                strarr[i] = key5 + "\t--Keyword " + keyid + " Identified";
                                System.out.println(strarr[i]);
                                keyid++;

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

                                    String key1 = "begin";
                                    strarr[i] = key1 + "\t--Keyword " + keyid + " Identified";
                                    System.out.println(strarr[i]);
                                    keyid++;

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
                            String key2 = "end";
                            strarr[i] = key2 + "\t--Keyword " + keyid + " Identified";
                            System.out.println(strarr[i]);
                            keyid++;
                            
                        }
                    }
                }
            }
            if (strarr[i].equals("i")) {

                if (strarr[i + 1].equals("f")) {
                    if (Character.isWhitespace(strarr[i + 2].charAt(0)) || strarr[i + 2].equals("(")) {
                        i = i + 1;
                        String key3 = "i" + "f";
                        strarr[i] = key3 + "\t--Keyword " + keyid + " Identified";
                        System.out.println(strarr[i]);
                        keyid++;
                    }
                }
            }

            if (strarr[i].equals("t")) {
                if (strarr[i + 1].equals("h")) {
                    if (strarr[i + 2].equals("e")) {
                        if (strarr[i + 3].equals("n")) {
                            if (Character.isWhitespace(strarr[i + 4].charAt(0)) || strarr[i + 4].equals(";") || strarr[i + 4].equals("{")) {
                                i = i + 3;

                                String key4 = "then";
                                strarr[i] = key4 + "\t--Keyword " + keyid + " Identified";
                                System.out.println(strarr[i]);
                                keyid++;

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

                            String key6 = "int";
                            strarr[i] = key6 + "\t--Keyword " + keyid + " Identified";
                            System.out.println(strarr[i]);
                            keyid++;
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

                                    String key7 = "float";
                                    strarr[i] = key7 + "\t--Keyword " + keyid + " Identified";
                                    keyid++;
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

                                String key8 = "char";
                                strarr[i] = key8 + "\t--Keyword " + keyid + " Identified";
                            System.out.println(strarr[i]);
                                keyid++;
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

                                        String key9 = "string";
                                        strarr[i] = key9 + "\t--Keyword " + keyid + " Identified";
                                System.out.println(strarr[i]);
                                        keyid++;

                                    }

                                }

                            }

                        }
                    }
                }
            }
            
            
            
            
            
            
            
            
  
            // Identifying the identifiers
            
            
//            System.out.println(strarr[i]);

        }
    }

    public void analysingIdentifiers(String[] strarr) {

        int i = 0;
        String str = "";
        String identifer_pattern = "^([a-zA-Z_$][a-zA-Z\\d_$]*)$";
        Pattern p = Pattern.compile(identifer_pattern);
        
        while (strarr.length != i) {
            String c = strarr[i];

            boolean flag = Character.isWhitespace(c.charAt(0));
            if (flag == true || c.equals("=") || c.contentEquals(";")) {
                Matcher m = p.matcher(str);
                boolean flage= m.matches();

                if (flage == true) {

                    switch (str) {
                        case "if":
//                            System.out.println("if" + "\t Keyword");
                            i++;
                            str = "";
                            break;
                        case "begin":
//                            System.out.println("begin"+ "\t Keyword");
                            i++;
                            str = "";
                            break;
                        case "then":
//                            System.out.println("then" + "\t Keyword");
                            i++;
                            str = "";
                            break;
                        case "int":
//                            System.out.println("int" + "\t Keyword");
                            i++;
                            str = "";
                            break;
                        case "float":
//                            System.out.println("float" + "\t Keyword");
                            i++;
                            str = "";
                            break;
                        case "char":
//                            System.out.println("char" + "\t Keyword");
                            i++;
                            str = "";
                            break;
                        case "else":
//                            System.out.println("else" + "\t Keyword");
                            i++;
                            str = "";
                            break;
                        case "string":
//                            System.out.println("else" + "\t Keyword");
                            i++;
                            str = "";
                            break;
                        case "end":
//                            System.out.println("else" + "\t Keyword");
                            i++;
                            str = "";
                            break;
                        default:
                            System.out.println(str + "\t identifier");
                            i++;
                            str = "";
                            break;
                    }

                } else {
                    str = "";
                    i++;
                }
                
                

            } else {
                str = str.concat(c);
//                System.out.println(str);
                i++;

            }

        }

    }
    
    
    public void identifyStringLiterals(String[] starr){
    
        
    }
    
    public void identifyUnsignedInteger(String[] starr){
    
        
    }

    public void lineByLine() {

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("test.txt"));
            String line = reader.readLine();
            while (line != null) {

                // read next line
                line = reader.readLine();
                eachLineInArray.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
