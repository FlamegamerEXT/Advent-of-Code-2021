import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.*;
import java.util.*;

/**
 * An indepth description of what TrickShot is attempting to do can be found here:
 * https://adventofcode.com/2021/day/17
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (20/12/2021)
 */
public class TrickShot{
    public static final String DIGITS = "0123456789";
    /**
     * TrickShot simulates the trajectory of a probe, with the intent of getting it to enter a particular area
     */
    public TrickShot() {
        try {  // Load file
            File file = new File("TrickShot.txt");
            Scanner sc = new Scanner(file);
            String[] ss = sc.nextLine().split(" ", 0);
            sc.close();
            
            int[][] bounds = {{0, 0}, {0, 0}};  // The x and y values that constrain the target area
            for (int i = 0; i <= 1; i++){  // x, and then y
                int index = 0, multiplyer = 1;
                for (char c : ss[i+2].toCharArray()){  // Turns string into decimal number
                    String s = c+"";
                    if (s.equals("-")){  // Makes it a negative number
                        multiplyer = -1;
                    } else if (DIGITS.contains(s)){  // Makes an integer
                        bounds[i][index] *= 10;
                        bounds[i][index] += multiplyer*Character.getNumericValue(c);
                    } else if (s.equals(".")){  // Begins second number
                        index = 1;
                        multiplyer = 1;
                    }
                }
            }
            // Sort the bounds into upper and lower
            int xUpper = Math.max(bounds[0][0], bounds[0][1]), xLower = Math.min(bounds[0][0], bounds[0][1]);
            int yUpper = Math.max(bounds[1][0], bounds[1][1]), yLower = Math.min(bounds[1][0], bounds[1][1]);
            
            int maxPeak = 0, possibleShots = 0;  // Output variables
            // For each shot that could pass through the target area:
            for (int xVi = Math.min(0, xLower); xVi <=  Math.max(0, xUpper); xVi++){
                for (int yVi = Math.min(0, yLower); yVi <= Math.max(yUpper, -yLower); yVi++){ 
                    int x = 0, y = 0, xV = xVi, yV = yVi, peak = 0;
                    while (y >= Math.min(yLower, 0)){ // Simulate while above the minimum possible valid height
                        x += xV; y += yV;  // Move according to the current velocity
                        if (xV > 0){ xV--; }  // Reduce horizontal velocity
                        else if (xV < 0){ xV++; }
                        yV--;  // Accelerate downwards
                        peak = Math.max(peak, y);
                        if ((x <= xUpper)&&(x >= xLower)&&(y <= yUpper)&&(y >= yLower)){  // If within the target area:
                            maxPeak = Math.max(peak, maxPeak);
                            possibleShots++;
                            break;
                        }
                    }
                }
            }
            
            System.out.println("########## Day 17 ##########");
            System.out.println("Part 1: "+maxPeak);
            System.out.println("Part 2: "+possibleShots);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    // Main
    public static void main(String[] arguments) {
        TrickShot ts = new TrickShot();
    }
}