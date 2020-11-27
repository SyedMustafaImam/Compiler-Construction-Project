package temp;

import java.util.*;
import java.util.function.BiConsumer;


import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.nio.Buffer;
import java.security.Key;

public class main {
    static File file = new File("test.txt");

    public static void main(final String[] args) {

        System.out.println("\n*************Welcome to Text Reader**********\n\n");
        System.out.println("Creating or Finding File...\n");
        for (float i = 0; i <= 10; i++) {
            for (float j = 0; j <= 10; j++) {

                j = j + 1;
            }
        }
        try {

            if (file.createNewFile()) {
                System.out.println("File Created" + file.getName());

            } else {
                System.out.println("File already exist.");
            }

        } catch (final IOException e) {
            System.out.print("Got into an error!");
            e.printStackTrace();
        }

        // readFromFile();

        final String source = readFile("test.txt");

        final String rem_comments = source.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)|", "");

        // For Printing the String

        /*
         * for(int i =0 ; i<rep.length();i++){
         * 
         * System.out.println(rep.charAt(i)); }
         * 
         */
        identifyReloc(stringToArray(rem_comments));

    }

    public static void readFromFile() {

        System.out.println("\n---------------Reading Input Form File-------------------\n\nOutput:\n");
        System.out.println("\n---------------Ignoring Comments-------------------\n\nOutput:\n");

        try {
            final FileReader fr = new FileReader(file);
            final BufferedReader br = new BufferedReader(fr);
            int c = 0;

            while ((c = br.read()) != -1) {
                final char character = (char) c;
                System.out.println(br.read());

                System.out.println(character);
            }

        } catch (final IOException e) {
            System.out.println("Error encounterd!");
        }

    }

    static String readFile(final String fileName) {
        System.out.println("\n---------------Reading Input Form File-------------------");
        System.out.println("\n---------------Ignoring Comments-------------------\n\nOutput:\n");

        final File file = new File(fileName);

        char[] buffer = null;

        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

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

    static String[] stringToArray(final String stname) {

        final String[] strarr = new String[stname.length()];
        for (int i = 0; i < stname.length(); i++) {
            strarr[i] = Character.toString(stname.charAt(i));

        }
        // For Printing An Array

        /*
         * for(int j=0; j<strarr.length;j++){ System.out.println(strarr[j]); }
         */
        return strarr;

    }

    static void identifyReloc(final String[] strarr) {
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

                    }

                    if ((d1 + d2).equals(">=")) {
                        strarr[i] = d1 + d2 + "\tGE --Relop Identified!";

                    }

                    if ((d1 + d2).equals("==")) {
                        strarr[i] = d1 + d2 + "\tEQ --Relop Identified!";

                    }

                } else {
                    i--;
                }
            }

            if (strarr[i].equals(">") || strarr[i].equals("<")) {
                if (strarr[i].equals(">")) {
                    String f1 = strarr[i];
                    strarr[i] = f1 + "\tGT --Relop Identified!";
                    i--;

                }

                if (strarr[i].equals("<")) {
                    String f2 = strarr[i];
                    strarr[i] = f2 + "\tLT --Relop Identified!";
                    i--;
                }

                else {

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

            // Identifing Keywords

            if (strarr[i].equals("e")) {

                if (strarr[i + 1].equals("l")) {

                    if (strarr[i + 2].equals("s")) {

                        if (strarr[i + 3].equals("e")) {
                            if (Character.isWhitespace(strarr[i + 4].charAt(0))||strarr[i + 4].equals("{")) {
                            i = i + 3;

                            String key5 = "else";
                            strarr[i] = key5 + "\t--Keyword " + keyid + " Identified";
                            keyid++;

                        }}
                    }
                }
            }

            if (strarr[i].equals("b")) {

                if (strarr[i + 1].equals("e")) {

                    if (strarr[i + 2].equals("g")) {

                        if (strarr[i + 3].equals("i")) {

                            if (strarr[i + 4].equals("n")) {
                                
                                if (Character.isWhitespace(strarr[i + 5].charAt(0))||strarr[i + 5].equals(";")) {
                                i = i + 4;

                                String key1 = "begin";
                                strarr[i] = key1 + "\t--Keyword " + keyid + " Identified";
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
                        if (Character.isWhitespace(strarr[i + 3].charAt(0))||strarr[i + 3].equals(";")) {
                        i = i + 2;
                        String key2 = "end";
                        strarr[i] = key2 + "\t--Keyword " + keyid + " Identified";
                        keyid++;
                        }
                    }
                }
            }
            if (strarr[i].equals("i")) {

                if (strarr[i + 1].equals("f")) {
                    if (Character.isWhitespace(strarr[i + 2].charAt(0))||strarr[i + 2].equals("(")) {
                    i=i+1;
                    String key3 = "i" + "f";
                    strarr[i] = key3 + "\t--Keyword " + keyid + " Identified";
                    keyid++;
                    }
                }
            }

            if (strarr[i].equals("t")) {
                if (strarr[i + 1].equals("h")) {
                    if (strarr[i + 2].equals("e")) {
                        if (strarr[i + 3].equals("n")) {
                            if (Character.isWhitespace(strarr[i + 4].charAt(0))||strarr[i + 4].equals(";")) {
                            i = i + 3;

                            String key4 = "then";
                            strarr[i] = key4 + "\t--Keyword " + keyid + " Identified";
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
                            i = i + 4;

                            String key8 = "char";
                            strarr[i] = key8 + "\t--Keyword " + keyid + " Identified";
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
                                    keyid++;

                                }

                            }

                        }

                    }

                }
            }
            }

            System.out.println(strarr[i].replaceAll("\n|\t|\\s", ""));
            

        }

    }
}
