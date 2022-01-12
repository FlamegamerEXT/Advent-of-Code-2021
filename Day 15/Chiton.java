import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.awt.Point;  // Import the Point class
import java.lang.*;
import java.util.*;

/**
 * An indepth description of what Chiton is attempting to do can be found here:
 * https://adventofcode.com/2021/day/15
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (16/12/2021) - Initial attempt, requires an additional check to remove points already visited 
 * Version: 1.0.1 (01/01/2022) - Additional check removed. Fixed by removing from borderPoint after adding to borderPoint
 */
public class Chiton{
    /**
     * Chiton finds the minimum cost to get to the other end of a map for two different maps
     */
    public Chiton(){
        List<String> lines = new ArrayList<String>();
        try {  // Load file
            File file = new File("Chiton.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNext()){
                lines.add(sc.nextLine());
            }
            sc.close();

            System.out.println("########## Day 15 ##########");
            int[] size = {1, 5};  // Size of the map
            for (int part = 0; part < size.length; part++){
                int[][] cavern = new int[lines.size()*size[part]][lines.get(0).length()*size[part]];
                int[][] totalCost = new int[cavern.length][cavern[0].length];
                for (int y = 0; y < lines.size(); y++){
                    String line = lines.get(y);
                    char[] chars = line.toCharArray();
                    for (int x = 0; x < line.length(); x++){
                        int cost = Character.getNumericValue(chars[x]);
                        //  For Part 1, the following loop just fills the table with the cost without modifying it.
                        //  For Part 2, the following loop fills the table with the value at 25 points, with the value
                        // changing more the further it gets from the origin. See description on the Advent of Code website
                        for (int x_ = 0; x_ < size[part]; x_++){
                            for (int y_ = 0; y_ < size[part]; y_++){
                                int yNew = y + y_ * lines.size(), xNew = x + x_ * line.length();  // New positions
                                cavern[yNew][xNew] = (cost+x_+y_);  // Value at position
                                while (cavern[yNew][xNew] > 9){ cavern[yNew][xNew] -= 9; }  // Wrap value back to below 10
                                totalCost[yNew][xNew] = Integer.MIN_VALUE;  // No total cost has been discovered yet
                            }
                        }
                    }
                }
                
                totalCost[0][0] = 0;  // totalCost at start is 0
                Point end = new Point(cavern[0].length-1, cavern.length-1);  // Point at the end
                Set<Point> borderPoint = new HashSet<Point>();  // A set of all the points that can get their value calculated
                borderPoint.add(new Point(0, 1));
                borderPoint.add(new Point(1, 0));
                int maxIterations = (cavern.length+cavern[0].length)*9;  // The maximum possible cost to get to the end
                for (int i = 1; i < maxIterations; i++){  // Finds all the spaces and that have a total cost of i
                    Set<Point> remove = new HashSet<Point>();  // Sets to alter borderPoint after each iteration
                    Set<Point> add = new HashSet<Point>();
                    for (Point p : borderPoint){
                        int x = (int)p.getX(), y = (int)p.getY();
                        
                        // Make a set of neighbours for the border point to check their costs
                        Set<Point> neighbour = new HashSet<Point>();
                        boolean[] exists = {(x > 0), (x < cavern[0].length-1), (y > 0), (y < cavern.length-1)};
                        Point[] adjacents = {new Point(x-1, y), new Point(x+1, y), new Point(x, y-1), new Point(x, y+1)};
                        for (int n = 0; n < 4; n++){
                            if (exists[n]){
                                neighbour.add(adjacents[n]);
                            }
                        }
                        
                        // If the target can be traversed with total cost of i, it gains that total cost
                        // As this is calculated from 1 upwards, this is the lowest possible cost to this point
                        for (Point n : neighbour){
                            int cost = totalCost[(int)n.getY()][(int)n.getX()] + cavern[y][x];
                            if (cost == i){
                                remove.add(p);  // Point has been discovered, will be removed from the borderPoint set
                                totalCost[y][x] = cost;
                                for (Point newN : neighbour){  // Adds all neighbouring points without a totalCost to borderPoint
                                    if (totalCost[(int)newN.getY()][(int)newN.getX()] == Integer.MIN_VALUE){
                                        add.add(newN);
                                    }
                                }
                                if (p.equals(end)){  // Close the iterative loop as the end's cost has been cost
                                    i = maxIterations;
                                }
                                break;
                            }
                        }
                    }
                    for (Point p : add){ borderPoint.add(p); }  // Add new points to borderPoint that need to be explored
                    for (Point p : remove){ borderPoint.remove(p); }  // Remove points from borderPoint that have been explored
                }
                System.out.println("Part "+(part+1)+": "+totalCost[(int)end.getY()][(int)end.getX()]);  // Total cost to end of map
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    //Main
    public static void main(String[] arguments) {
        Chiton c = new Chiton();
    }
}