import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.*;

/**
 * An indepth description of what CrabSubs is attempting to do can be found here:
 * https://adventofcode.com/2021/day/7
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (8/12/2021)
 */
public class CrabSubs{
    /**
     * CrabSubs finds the minimum cost to move many objects at different positions to the same position.
     */
    public CrabSubs(){
        try {
            File file = new File("CrabSubs.txt");
            Scanner sc = new Scanner(file);
            String[] strings = sc.nextLine().split(",", 0);
            sc.close();
            
            int[] ints = new int[strings.length];  // Array of all the positions
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;  // Max and min gives the range of the positionings
            for (int i = 0; i < ints.length; i++){
                ints[i] = Integer.valueOf(strings[i]);
                min = Math.min(min, ints[i]);
                max = Math.max(max, ints[i]);
                sc.close();
            }
            
            int bestFuel1 = Integer.MAX_VALUE, bestIndex1 = Integer.MIN_VALUE;
            int bestFuel2 = Integer.MAX_VALUE, bestIndex2 = Integer.MIN_VALUE;
            for (int pos = min; pos <= max; pos++){  // Determine the total cost(s) for all the given positions to be aligned
                int sum1 = 0, sum2 = 0;
                for (int n : ints){
                    int dist = Math.abs(pos-n);  // Distance from individual to the given position
                    sum1 += dist;  // Linear cost
                    sum2 += (int)(0.5*(dist*dist + dist));  // Polynomial cost
                }
                if (sum1 < bestFuel1){  // If sum is the minimum amount of fuel used
                    bestFuel1 = sum1;
                    bestIndex1 = pos;
                }
                if (sum2 < bestFuel2){  // If sum is the minimum amount of fuel used
                    bestFuel2 = sum2;
                    bestIndex2 = pos;
                }
            }
            
            System.out.println("########## Day 7 ##########");  // Print outputs
            System.out.println("Part 1: "+bestFuel1);
            System.out.println("Part 2: "+bestFuel2);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] arguments) {
        CrabSubs cs = new CrabSubs();
    }
}