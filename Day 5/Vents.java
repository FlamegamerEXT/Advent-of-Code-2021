import java.io.File;  // Import the File class
import java.util.ArrayList;  // Import the ArrayList class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.*;

/**
 * An indepth description of what Vents is attempting to do can be found here:
 * https://adventofcode.com/2021/day/5
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (5/12/2021)
 */
public class Vents {
    /**
     * Vents creates a map of hydrothermal vents, and calculates the number of points where they intersect
     */
    public Vents(){
        // initialise instance variables
        ArrayList<Integer[]> lines = new ArrayList<Integer[]>();
        try {  // Load file
            File file = new File("Vents.txt");
            Scanner sc = new Scanner(file);
            
            int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;  // Boundaries for the table
            while (sc.hasNextLine()) {
                String s1 = sc.next(); sc.next(); String s2 = sc.next();
                Integer line[] = new Integer[4];
                String[] coords = s1.split(",", 0);  // Start of line
                line[0] = Integer.valueOf(coords[0]);
                line[1] = Integer.valueOf(coords[1]);
                coords = s2.split(",", 0);  // End of line
                line[2] = Integer.valueOf(coords[0]);
                line[3] = Integer.valueOf(coords[1]);
                lines.add(line);
                for (int n : line){  // Update limits for the boundaries
                    min = Math.min(min, n);
                    max = Math.max(max, n);
                }
            }
            sc.close();
            
            int part1 = 0, part2 = 0;  // count of all overlaps
            for (int x = min; x <= max; x++){
                for (int y = min; y <= max; y++){  // For each point 
                    int num1 = 0, num2 = 0;
                    for (Integer[] line : lines){  // For each line, if the point is on the line, record it
                        int x1 = Math.abs(line[0]-x), x2 = Math.abs(line[2]-x), xT = Math.abs(line[0]-line[2]);  // x-distances
                        int y1 = Math.abs(line[1]-y), y2 = Math.abs(line[3]-y), yT = Math.abs(line[1]-line[3]);  // y-distances
                        boolean withinBox = (xT == x1 + x2)&&(yT == y1 + y2);
                        if (withinBox&&(((y1 == 0)&&(y2 == 0))||((x1 == 0)&&(x2 == 0)))){ num1++; }  // If on a line (vert or horiz)
                        else if (withinBox&&(x1 == y1)&&(x2 == y2)){ num2++; }  //  If on a diagonal line
                    }
                    if (num1 > 1){ part1++; }  // If there is more than one line (vert or horiz) through the point, add to part 1
                    if (num1 + num2 > 1){ part2++; }  // If there is more than one line through the point, add to part 2
                }
            }
            
            System.out.println("########## Day 5 ##########");  // Print outputs
            System.out.println("Part 1: "+part1);
            System.out.println("Part 2: "+part2);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    // Main
    public static void main(String[] arguments) {
        Vents v = new Vents();
    }
}