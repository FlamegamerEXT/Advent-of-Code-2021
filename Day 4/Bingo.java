import java.io.File;  // Import the File class
import java.util.ArrayList;  // Import the ArrayList class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.*;

/**
 * An indepth description of what Bingo is attempting to do can be found here:
 * https://adventofcode.com/2021/day/4
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (5/12/2021)
 */
public class Bingo {
    /**
     * Bingo reads a file with number calls and bingo boards, and plays Bingo for all the boards using the ordered number calls.
     */
    public Bingo(){
        // initialise instance variables
        ArrayList<BingoObject> bingoBoard = new ArrayList<BingoObject>();
        
        try {  // Load file
            File file = new File("Bingo.txt");
            Scanner sc = new Scanner(file);
            
            String[] bingoCall = sc.nextLine().split(",", 0);  // Array of number calls
            
            ArrayList<ArrayList<Integer>> bingoNums = new ArrayList<ArrayList<Integer>>();  // Adjustible bingo board
            while (sc.hasNextLine()){
                Scanner scan = new Scanner(sc.nextLine());  // Read line
                if (scan.hasNextInt()){  // If the line has ints in it, save them in an ArrayList
                    ArrayList<Integer> bingoRow = new ArrayList<Integer>();
                    while (scan.hasNextInt()){  // Fill up the row
                        bingoRow.add(scan.nextInt());
                    }
                    bingoNums.add(bingoRow);  // Add the row to the nested ArrayList
                    
                    if (bingoNums.size() == bingoNums.get(0).size()){  // If the board is a square, create object
                        bingoBoard.add(new BingoObject(new ArrayList<ArrayList<Integer>>(bingoNums)));
                        bingoNums.clear();
                    }
                }
            }
            sc.close();
            
            int wins = 0;  // Count of boards that have 'won', i.e. completed a row or column
            for (int i = 0; i < bingoCall.length; i++){
                int call = Integer.valueOf(bingoCall[i]);  // Next number to be called
                
                for (BingoObject b : bingoBoard){
                    if (b.checkNumber(call) && b.checkBoard()){  // If number called has been found, and causes a win:
                        wins++;
                        int sum = call*b.unmarkedSum();  // The product of two numbers
                        
                        // Print outputs
                        if (wins == 1){  // First win score
                            System.out.println("########## Day 4 ##########");
                            System.out.println("Part 1: "+sum);
                        } else if (wins == bingoBoard.size()){ System.out.println("Part 2: "+sum); } // Final win score
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Main
    public static void main(String[] arguments) {
        Bingo b = new Bingo();
    }
}