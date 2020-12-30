
package tafcal_compiler;

 
public class Tafcal_Compiler {

   
    public static void main(String[] args)  {

        SyntaxAnalysis filefun = new SyntaxAnalysis();
        
        filefun.printSymbolTable();
        filefun.getSLRTable();
        filefun.getCFGRules();
        filefun.slrParsing();
        
    
    }
    
    



    }



    
   

