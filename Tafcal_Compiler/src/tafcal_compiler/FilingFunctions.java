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
   
   String fileName = "test.txt";
   
    public void printngFile(){
    
       
       
        String source = removeComments(fileName);
        
      
          for(int i =0 ; i<source.length();i++){
          
          System.out.print(source.charAt(i)); }
          
         
    
    }
    
    public String removeComments(String fileName){

         String inputStr = readFile(fileName);
         
         String rem_comments = inputStr.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)|", "");

         
         return rem_comments;
     }
    
    
    public String readFile(String fileName) {
                        System.out.println("\n*************Welcome to Text Reader**********\n\n");   

        System.out.println("\n---------------Reading Input Form File-------------------");

        final File file = new File(fileName);

        char[] buffer = null;

    System.out.println("Creating or Finding File...\n");
   
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
    
}
