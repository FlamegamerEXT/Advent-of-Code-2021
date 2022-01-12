import java.util.ArrayList;  // Import the ArrayList class

/**
 * An indepth description of what BingoObject is attempting to do can be found here:
 * https://adventofcode.com/2021/day/4
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (5/12/2021)
 */
public class BingoObject {
    // instance variables - replace the example below with your own
    private boolean won;
    private ArrayList<ArrayList<Integer>> bingoNums = new ArrayList<ArrayList<Integer>>();
    private ArrayList<ArrayList<Boolean>> bingoMarked = new ArrayList<ArrayList<Boolean>>();
    // It may be more efficient to use 2d Arrays rather than nested ArrayLists, but this is what I chose to do at the time.

    /**
     * Constructor for objects of class BingoObject
     */
    public BingoObject(ArrayList<ArrayList<Integer>> numsArray){
        bingoNums = numsArray;  // Save the nested ArrayList of Integers
        for (ArrayList<Integer> nums : bingoNums){  // Make a corresponding ArrayList of Integers
            bingoMarked.add(new ArrayList<Boolean>());
            int index = bingoMarked.size()-1;  // Latest addition to bingoMarked
            for (Integer num : nums){  // Fill with false booleans
                bingoMarked.get(index).add(false);
            }
        }
    }

    /**
     * Checks a given number off the bingo board if it is on the board
     */
    public boolean checkNumber(int n){
        boolean itemFound = false;
        for (int row = 0; row < bingoNums.size(); row++){  // For each entry on the board:
            for (int col = 0; col < bingoNums.get(row).size(); col++){
                if (bingoNums.get(row).get(col) == n){
                    itemFound = !bingoMarked.get(row).get(col);  // itemFound if it wasn't marked and is the right number
                    bingoMarked.get(row).set(col, true);  // item has been found, so it is marked
                }
            }
        }
        return itemFound;
    }

    /**
     * Checks if the bingo board has a completed row or column, and saves if it has won.
     */
    public boolean checkBoard(){
        if (won){ return false; }  // Board has already won, so no new win was recorded
        // Check for completed row:
        for (int row = 0; row < bingoMarked.size(); row++){  // If every entry in this row has been completed, return true
            boolean completedRow = true;
            for (int col = 0; col < bingoMarked.get(row).size(); col++){
                completedRow = completedRow&&bingoMarked.get(row).get(col);
            }
            won = won||completedRow;
        }
        // Check for completed column:
        for (int col = 0; col < bingoMarked.get(0).size(); col++){  // If every entry in this column has been completed, return true
            boolean completedRow = true;
            for (int row = 0; row < bingoMarked.size(); row++){
                completedRow = completedRow&&bingoMarked.get(row).get(col);
            }
            won = won||completedRow;
        }
        return won;
    }

    /**
     * Returns the sum of all the unmarked entries on the board
     */
    public int unmarkedSum(){
        int ans = 0;
        for (int row = 0; row < bingoMarked.size(); row++){  // For each entry on the board:
            for (int col = 0; col < bingoMarked.get(row).size(); col++){
                if (!bingoMarked.get(row).get(col)){  // if unmarked, add value to the sum
                    ans += bingoNums.get(row).get(col);
                }
            }
        }
        return ans;
    }
    
    /**
     * Print the info of all of the entries on the board
     */
    public void print(){
        for (int row = 0; row < bingoNums.size(); row++){  // For each entry on the board:
            for (int col = 0; col < bingoNums.get(row).size(); col++){
                System.out.print(bingoNums.get(row).get(col)+"("+bingoMarked.get(row).get(col)+") ");  // Print the entry's info
            }
            System.out.println("");
        }
    }
}
