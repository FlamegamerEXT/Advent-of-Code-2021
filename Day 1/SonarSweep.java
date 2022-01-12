import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

/**
 * An indepth description of what SonarSweep is attempting to do can be found here:
 * https://adventofcode.com/2021/day/1
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (2/12/2021)
 */
public class SonarSweep {
    /**
     * SonarSweep takes in a file of numbers, and counts the number of increases from one number to the next.
     */
    public SonarSweep(){
        // initialise instance variables
        int[] nums = {-1, -1, -1, -1};  // Record of the last four numbers
        int increasesP1 = 0, increasesP2 = 0;  // Count of increases
        
        try {  // Load file
            File file = new File("Sonar Sweep.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {  // For each line:
                nums[3] = sc.nextInt();  // Record the most recent number
                
                // Checks for if the second number is greater than the first (in two cases)
                if (nums[2] != -1){ increasesP1 += reportIncrease(nums[2], nums[3]); }
                if (nums[0] != -1){ increasesP2 += reportIncrease(nums[0]+nums[1]+nums[2], nums[1]+nums[2]+nums[3]); }
                
                for (int i = 0; i < 3; i++){  // Move each number one along for next iteration
                    nums[i] = nums[i+1];
                }
            }
            sc.close();
            
            System.out.println("########## Day 1 ##########");  // Print outputs
            System.out.println("Increases (Part 1): "+increasesP1);
            System.out.println("Increases (Part 2): "+increasesP2);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    /** Returns 1 if the second number is greater than the first, else it returns 0 */
    public int reportIncrease(int n1, int n2){
        if (n1 == -1){ return 0; }
        else if (n1 < n2){ return 1; }
        return 0;
    }

    // Main
    public static void main(String[] arguments) {
        SonarSweep ss = new SonarSweep();
    }
}