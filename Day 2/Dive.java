import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

/**
 * An indepth description of what Dive is attempting to do can be found here:
 * https://adventofcode.com/2021/day/2
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (2/12/2021)
 */
public class Dive {
    /**
     * Dive takes in a set of directions, and changes its position accordingly
     */
    public Dive(){
        // initialise instance variables
        int vertical = 0, horizontal = 0, aim = 0;
        
        try {  // Load file
            File file = new File("Dive.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {  // For each line, alter direction
                String direction = sc.next();
                int amount = sc.nextInt();
                switch (direction) {  // Move in the given direction by given amount
                    case "up":
                        aim-=amount;
                        break;
                    case "down":
                        aim+=amount;
                        break;
                    default: // "forward"
                        horizontal+=amount;
                        vertical+=(aim*amount);
                }
            }
            sc.close();
            
            System.out.println("########## Day 2 ##########");  // Print outputs, the products of two numbers
            System.out.println("Part 1 - Total: "+aim*horizontal);
            System.out.println("Part 2 - Total: "+vertical*horizontal);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    // Main
    public static void main(String[] arguments) {
        Dive dive = new Dive();
    }
}
