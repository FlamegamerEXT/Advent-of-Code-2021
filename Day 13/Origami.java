import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.awt.Point;  // Import the Point class
import java.lang.*;
import java.util.*;

/**
 * An indepth description of what Origami is attempting to do can be found here:
 * https://adventofcode.com/2021/day/13
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (15/12/2021)
 */
public class Origami{
    /**
     * Origami takes in a set of points on a sheet, and then folds the sheet over via a set of instructions
     */
    public Origami(){
        Set<Point> dots = new HashSet<Point>();
        List<String> directions = new ArrayList<String>();
        List<Integer> positions = new ArrayList<Integer>();
        try {  // Load file
            File file = new File("Origami.txt");
            Scanner sc = new Scanner(file);
            boolean fold = false;
            while (sc.hasNext()){
                String line = sc.nextLine();
                if (fold){  // Read the folding instructions
                    String[] words = line.split(" ", 0), instruction = words[words.length-1].split("=", 0);
                    directions.add(instruction[0]);
                    positions.add(Integer.parseInt(instruction[1]));
                } else if (line.equals("")){
                    fold = true;
                } else {  // Make a set of co-ordinates of points
                    String[] coords = line.split(",", 0);
                    int x = Integer.parseInt(coords[0]), y = Integer.parseInt(coords[1]);
                    dots.add(new Point(x, y));
                }
            }
            sc.close();
            
            int[] dimensions = {Integer.MAX_VALUE, Integer.MAX_VALUE};
            for (int i = 0; i < directions.size(); i++){  // Fold for each direction
                int position = positions.get(i);
                Set<Point> dotsOverLine = new HashSet<Point>();
                
                int target = ((directions.get(i).equals("x")) ? 0 : 1);  // Direction of the fold
                dimensions[target] = position;
                for (Point dot : dots){  // If dot is over the line, add it to set to be folded
                    int[] coords = {(int)dot.getX(), (int)dot.getY()};
                    if (coords[target] > position){
                        dotsOverLine.add(dot);
                    }
                }
                for (Point dot : dotsOverLine){  // Fold all points that are over the line
                    dots.remove(dot);
                    int[] coords = {(int)dot.getX(), (int)dot.getY()};
                    coords[target] = 2*position-coords[target];  // Fold over the line
                    dots.add(new Point(coords[0], coords[1]));
                }
                if (i == 0){  // Number of dots after one fold
                    System.out.println("########## Day 13 ##########");  // Print outputs
                    System.out.println("Part 1: "+dots.size());
                    System.out.println("Part 2 has the following output:");
                }
            }
            
            for (int y = 0; y < dimensions[1]; y++){  // Print the output as a collection of characters
                for (int x = 0; x < dimensions[0]; x++){
                    if (dots.contains(new Point(x, y))){  // If this point has a dot:
                        System.out.print("()");
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    //Main
    public static void main(String[] arguments) {
        Origami ori = new Origami();
    }
}
