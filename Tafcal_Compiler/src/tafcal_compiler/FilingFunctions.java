/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tafcal_compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author cs181
 */
public class FilingFunctions {

   File file = new File("test.txt");

    public void readingFile(){
    
        System.out.println("\n*************Welcome to Text Reader**********\n\n");   
    System.out.println("Creating or Finding File...\n");
    for(float i=0; i<=10;i++){
    for(float j=0; j<=10; j++){		    
		    
		    j=j+1;
    }}
        try{
        
        if(file.createNewFile()){
            System.out.println("File Created"+ file.getName());

        }else{
            System.out.println("File already exist.");
        }


    }catch (IOException e){
        System.out.print("Got into an error!");
        e.printStackTrace();
    }

        String source = readFile("test.txt");
        
        
          for(int i =0 ; i<source.length();i++){
          
          System.out.println(source.charAt(i)); }
          
         
    
    }
    
    public  void readFromFile(){
       
       System.out.println("\n---------------Reading Input Form File-------------------\n\nOutput:\n");
       
        

        try {
        Scanner reader = new Scanner(file);
        while(reader.hasNextLine()){
            int i= 0;
            String  data = reader.nextLine();
    for(i=0; i<data.length();i++){
        System.out.println(data.charAt(i));


    }        
        }  

        
       } catch (FileNotFoundException e) {
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
    
}
