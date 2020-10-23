package temp;

import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.nio.Buffer;

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

        final String rep = source.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)|(\\n)|' '| |\\s|", "");

        // For Printing the String
        /*
         * for(int i =0 ; i<rep.length();i++){
         * 
         * System.out.println(rep.charAt(i)); }
         */
        identifyReloc(stringToArray(rep));

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
        for(int i=0; i<stname.length();i++){
            strarr[i]= Character.toString(stname.charAt(i));

        }
// For Printing An Array

/*        for(int j=0; j<strarr.length;j++){
        System.out.println(strarr[j]);
        }
*/
        return strarr;

    }

    static void identifyReloc(final String[] strarr) {

        final int arrayLength = strarr.length;
        for (int i = 0; i < arrayLength; i++) {

            if (strarr[i].equals("=") || strarr[i].equals(">") || strarr[i].equals("<")) {
                i++;
                if (strarr[i].equals("=") || strarr[i].equals(">") || strarr[i].equals("<")) {
                    final String d1 = strarr[i - 1];
                    final String d2 = strarr[i];

                    if((d1+d2).equals("<=")){
                    strarr[i]=d1 + d2 + "\tLE --Relop Identified!";
                 
                }


                if((d1+d2).equals(">=")){
                    strarr[i]=d1 + d2 + "\tGE --Relop Identified!";
                 
                }

                if((d1+d2).equals("==")){
                    strarr[i]=d1 + d2 + "\tEQ --Relop Identified!";
                 
                }

                }else{
                    i--;
                }
            }

              

                if(strarr[i].equals(">")||strarr[i].equals("<")){ 
                    if(strarr[i].equals(">")){
                        String f1 = strarr[i];
                    strarr[i]=f1 +  "\tGT --Relop Identified!";
                    i--;

                }

                if(strarr[i].equals("<")){
                    String f2 = strarr[i];
                    strarr[i]=f2 +  "\tLT --Relop Identified!";
                    i--;
                }
                
                else{
                    
                    continue;
                }
            }
    
            System.out.println(strarr[i]);



            }
    
    }
}
