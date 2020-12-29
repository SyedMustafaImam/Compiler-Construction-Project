/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tafcal_compiler;

import java.util.Scanner;

/**
 *
 * @author cs181
 */
public class SLR_Table {

    Scanner input = new Scanner(System.in);

    public String[] production_rules = {"E->E+T", "E->T", "T->T*F", "T->F", "F->(E)", "F->i"};

    public void generatingCFGRules() {
        System.out.println("Rule: ");
        for (int arr = 0; arr < production_rules.length; arr++) {
            System.out.println("\tNo."+ (arr+1)+ " :"+ production_rules[arr]);
        }
        System.out.println("-----------------------------------------------------END RULES----------------------------------------------------");
    }

    public String[][] slrTable = {
        {"0", "s5", "", "", "s4", "", "", "1", "2", "3"},
        {"1", "", "s6", "", "", "", "acpt", "", "", ""},
        {"2", "", "r2", "s7", "", "r2", "r2", "", "", ""},
        {"3", "", "r4", "r4", "", "r4", "r4", "", "", ""},
        {"4", "s5", "", "", "s4", "", "", "8", "2", "3"},
        {"5", "", "r6", "r6", "", "r6", "r6", "", "", ""},
        {"6", "s5", "", "", "s4", "", "", "", "9", "3"},
        {"7", "s5", "", "", "s4", "", "", "", "", "10"},
        {"8", "", "s6", "", "", "s11", "", "", "", ""},
        {"9", "", "r1", "s7", "", "r1", "r1", "", "", ""},
        {"10", "", "r3", "r3", "", "r3", "r3", "", "", ""},
        {"11", "", "r5", "r5", "", "r5", "r5", "", "", ""}};

    public void getSLRTable() {
        
        System.out.println("\n\n\n----------------------------------------------SLR(1) Parsing Table---------------------------------------------");
        System.out.println("|==========|==========|==========|==========|==========|==========|==========|==========|==========|==========|");
        System.out.format("| %5s    | %5s    | %5s    | %5s    | %5s    | %5s    | %5s    | %5s    | %5s    | %5s    |\n", "State", "id", "+", "*", "(", ")", "$", "E", "T", "F");
        System.out.format("|==========|==========|==========|==========|==========|==========|==========|==========|==========|==========|\n");
        for (int arr1 = 0; arr1 < slrTable.length; arr1++) {
            for (int arr2 = 0; arr2 < slrTable[arr1].length; arr2++) {

                System.out.format("| %5s    ", slrTable[arr1][arr2]);
            }
            System.out.print("|\n");
            System.out.println("|__________|__________|__________|__________|__________|__________|__________|__________|__________|__________|");
        }
    }

    public void slrParsing() {
        boolean flag = true;

        int arrayIndexOfLexeme;
        System.out.println("\n\n\n---------------------------------------------------SLR(1) Parsing-------------------------------------------------");

        while (flag != false) {
            System.out.print("Enter state No.  :\t");
            int state = Integer.parseInt(input.nextLine());
            System.out.print("Enter lexeme     :\t");
            String lexeme = input.nextLine();

            switch (lexeme) {
                case "id":
                    arrayIndexOfLexeme = 1;
                    break;
                case "+":
                    arrayIndexOfLexeme = 2;
                    break;
                case "*":
                    arrayIndexOfLexeme = 3;
                    break;
                case "(":
                    arrayIndexOfLexeme = 4;
                    break;
                case ")":
                    arrayIndexOfLexeme = 5;
                    break;
                case "$":
                    arrayIndexOfLexeme = 6;
                    break;
                case "E":
                    arrayIndexOfLexeme = 7;
                    break;
                case "T":
                    arrayIndexOfLexeme = 8;
                    break;
                case "F":
                    arrayIndexOfLexeme = 9;
                    break;
                default:
                    arrayIndexOfLexeme = 10;
                    break;
            }
            if (state >= 0 && state <= 11) {
                flag = false;
                if (slrTable[state][arrayIndexOfLexeme].equals("")) {
                    System.out.println("\nOutput: error");
                } else {
                    System.out.println("\nOutput: " + slrTable[state][arrayIndexOfLexeme]);
                    
                    
                }
            } else {
                flag = true;
                System.out.println("\n\t\t\t\t\t\t   XXX ERROR XXX\n"
                        + "------------------------------------------------------------------------------------------------------------------\n"
                        + "Please Enter Correct Sate No. or Lexeme from the SLR Table...\n\n");
            }
        }
    }

    public void getCFGRules() {
        generatingCFGRules();
        boolean flag = true;

        while (flag != false) {

            System.out.print("Enter any Non-Terminal or Rule No. from these Rules     :\t");
            String nonTerminal = input.nextLine();

            switch (nonTerminal) {

                case "E":
                    System.out.println("No.1 :"+production_rules[0]);
                    System.out.println("No.2 :"+production_rules[1]);
                    flag = false;
                    break;

                case "T":
                    System.out.println("No.3 :"+production_rules[2]);
                    System.out.println("No.4 :"+production_rules[3]);
                    flag = false;
                    break;

                case "F":
                    System.out.println("No.5 :"+production_rules[4]);
                    System.out.println("No.6 :"+production_rules[5]);
                    flag = false;
                    break;
                    
                    case "1":
                    System.out.println("No.1 :"+production_rules[0]);
                    flag = false;
                    break;

                case "2":
                    System.out.println("No.2 :"+production_rules[1]);
                    flag = false;
                    break;

                case "3":
                    System.out.println("No.3 :"+production_rules[2]);
                    flag = false;
                    break;
                    
                    case "4":
                    System.out.println("No.4 :"+production_rules[3]);
                    flag = false;
                    break;

                case "5":
                    System.out.println("No.5 :"+production_rules[4]);
                    flag = false;
                    break;

                case "6":
                    System.out.println("No.6 :"+production_rules[5]);
                    flag = false;
                    break;

                default:
                    flag = true;
                    System.out.println("\n\t\t\t\t\t\t   XXX ERROR XXX\n"
                            + "------------------------------------------------------------------------------------------------------------------\n"
                            + "Please Enter Correct Production Rule from the Production Rules...\n\n");

            }
        }
        }
        
    
        
}
