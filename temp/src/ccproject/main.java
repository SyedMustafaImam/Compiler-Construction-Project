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

        

        System.out.println("---------------Reading Input Form File-------------------\n\n");
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

    // fileInformation();
    writeToFile();
    readFromFile();

    }

    public static void fileInformation(){
        if(file.exists()){
            System.out.println("File name : "+ file.getName());
            System.out.println("Absolute path : " + file.getAbsolutePath());
            System.out.println("IsWriteable : "+ file.canWrite());
            System.out.println("IsReadable : "+ file.canRead());
            System.out.println("File Size In Bytes : "+file.length());

        }
        else{
            System.out.println("The file does not exits!");
        }
    }

    public static void writeToFile () {
      Scanner input = new Scanner(System.in);
        try{
            FileWriter writer = new FileWriter("test.txt");
            String text;
            
            do{
                text = input.nextLine();
                writer.write(text);

            }while(text.equalsIgnoreCase("quit()"));

            writer.close();
            System.out.println("Successfully Worte ot the file! ");
        }catch(IOException e){
            System.out.println("An Error Occured!");
        }
    }

    public static void readFromFile(){
       
       
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