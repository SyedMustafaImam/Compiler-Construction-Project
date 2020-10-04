package temp;

import java.util.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;

public class main {
    static File file = new File("test.txt");
    public static void main(String[] args)  {

    System.out.println("\n*************Welcome to Text Reader**********\n\n");   
    System.out.println("Creating or Finding Fil...\n");
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

    readFromFile();

    }



    public static void readFromFile(){
       
       System.out.println("\n---------------Reading Input Form File-------------------\n\nOutput:\n");
       
        try {
        
        Scanner reader = new Scanner(file);
        while(reader.hasNextLine()){
            String data = reader.nextLine();
            System.out.println(data);
        }  
       } catch (FileNotFoundException e) {
           System.out.println("Error encounterd!");
    }
    
}
    }
