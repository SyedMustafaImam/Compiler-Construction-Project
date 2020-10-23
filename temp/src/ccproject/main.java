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

    public static void main(String[] args) {

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

        } catch (IOException e) {
            System.out.print("Got into an error!");
            e.printStackTrace();
        }

        // readFromFile();

        String source = readFile("test.txt");    

        String rep = source.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)|(\n)|' '", "");
        for(int i =0 ; i<rep.length();i++){

            System.out.println(rep.charAt(i));
        }

        // identifyReloc(rep);

    }

    public static void readFromFile() {

        System.out.println("\n---------------Reading Input Form File-------------------\n\nOutput:\n");
        System.out.println("\n---------------Ignoring Comments-------------------\n\nOutput:\n");

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            int c = 0;
            
            while((c = br.read()) != -1)         
            {      
                  char character = (char) c;        
                  System.out.println(br.read());       

                  
                  System.out.println(character);       
            }

        } catch (IOException e) {
            System.out.println("Error encounterd!");
        }

    }

    static String readFile(String fileName) {    
        System.out.println("\n---------------Reading Input Form File-------------------");
        System.out.println("\n---------------Ignoring Comments-------------------\n\nOutput:\n");

        File file = new File(fileName);    

        char[] buffer = null;    

        try {    
                BufferedReader bufferedReader = new BufferedReader( new FileReader(file));    

                buffer = new char[(int)file.length()];    

                int i = 0;    
                int c = bufferedReader.read();    

                while (c != -1) {    
                    buffer[i++] = (char)c;   
                    c = bufferedReader.read();


                }    

        } catch (IOException e) {    
            e.printStackTrace();    
        }    

        return new String(buffer);    
    }    

    static void identifyReloc(String stname){

String[] strarr = new String[stname.length()] ;
        for(int i=0; i<stname.length();i++){
            strarr[i]= Character.toString(stname.charAt(i));

        }

        for(int j=0; j<strarr.length;j++){
        System.out.println(strarr[j]);
        }

         
    }

}
