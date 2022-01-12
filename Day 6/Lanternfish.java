import java.io.File;  // Import the File class
import java.util.ArrayList;  // Import the ArrayList class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.*;

/**
 * An indepth description of what Lanternfish is attempting to do can be found here:
 * https://adventofcode.com/2021/day/6
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (6/12/2021)
 */
public class Lanternfish{
    /**
     * Lanternfish takes in a population of fish represented as integers, and simulates the (exponential) population growth
     */
    public Lanternfish(){
        // As given in the Advent of Code webpage, number of days from start to end for Part 1 is 80 days, and for Part 2 is 256 days.
        int[] days = {80, 256};
        try {
            File file = new File("Lanternfish.txt");
            Scanner sc = new Scanner(file);
            String[] ss = sc.nextLine().split(",", 0);
            sc.close();
            
            int maxCounter = 8;  // Maximum internal timer value
            ArrayList<Long> fish = new ArrayList<Long>();  // Count of fish at each internal timer value
            for (int n = 0; n <= maxCounter; n++){ fish.add(Long.valueOf(0)); }  // Initialises the map
            for (String s : ss){  // Sort all the fish into the ArrayList, values between 0 and maxCounter
                sc = new Scanner(s);
                int i = sc.nextInt();
                fish.set(i, fish.get(i)+1);  // Increment by 1
            }
            
            System.out.println("########## Day 6 ##########");  // Print outputs
            int print = 0;  // Number of outputs printed so far
            for (int d = 1; d <= days[days.length-1]; d++){
                long zeroes = fish.remove(0);  // Move all fish along one
                fish.set(6, zeroes + fish.get(6));  // Set all the fish at 0 to be at 6
                fish.add(zeroes);  // Add a new fish for each fish that were at 0
                
                if (d == days[print]){
                    print++;
                    long sum = 0;  // Count of all fish at the given day
                    for (long i : fish){
                        sum += i;
                    }
                    System.out.println("Part "+print+": "+sum);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] arguments) {
        Lanternfish lf = new Lanternfish();
    }
}