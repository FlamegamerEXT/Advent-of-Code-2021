import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.awt.Point;  // Import the Point class
import java.lang.*;
import java.util.*;  // Import for the ArrayList, HashSet, and Queue class

/**
 * An indepth description of what SmokeBasin is attempting to do can be found here:
 * https://adventofcode.com/2021/day/9
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (10/12/2021)
 */
public class SmokeBasin{
    private static final int NUM_OF_BASINS = 3;  // Number of basins for Part 2
    /**
     * SmokeBasin takes a heightmap and finds 'basins' by getting minimum points and then finds the largest basins
     */
    public SmokeBasin(){
        try {
            File file = new File("SmokeBasin.txt");
            Scanner sc = new Scanner(file);
            ArrayList<String> lines = new ArrayList<String>();
            while (sc.hasNext()){
                lines.add(sc.nextLine());
            }
            sc.close();
            
            // Create heightmap
            int[][] map = new int[lines.size()][lines.get(0).length()];
            for (int row = 0; row < lines.size(); row++){
                char[] lineChars = lines.get(row).toCharArray();
                for (int col = 0; col < lineChars.length; col++){
                    map[row][col] = Character.getNumericValue(lineChars[col]);
                }
            }
            
            int riskLevelSum = 0;
            Set<Point> lowPoints = new HashSet<Point>();
            for (int row = 0; row < map.length; row++){
                for (int col = 0; col < map[row].length; col++){
                    boolean minimum = true;  // If all surrounding values are larger, then it is a minimum point
                    if (row > 0){ minimum = minimum&&(map[row][col] < map[row-1][col]); }
                    if (row < map.length - 1){ minimum = minimum&&(map[row][col] < map[row+1][col]); }
                    if (col > 0){ minimum = minimum&&(map[row][col] < map[row][col-1]); }
                    if (col < map[row].length - 1){ minimum = minimum&&(map[row][col] < map[row][col+1]); }
                    
                    if (minimum){
                        riskLevelSum += (1 + map[row][col]);
                        lowPoints.add(new Point(col, row));
                    }
                }
            }
            
            // Part 2: Iterative Algorithm
            ArrayList<Integer> largestBasins = new ArrayList<Integer>();
            for (int i = 0; i < NUM_OF_BASINS; i++){ largestBasins.add(0); }  // ArrayList of the largest basins
            for (Point p : lowPoints){  // For each lowPoint, get the corresponding basin
                Queue<Point> toBeExplored = new ArrayDeque<Point>();
                HashSet<Point> visited = new HashSet<Point>();
                visited.add(p);
                toBeExplored.offer(p);
                while (!toBeExplored.isEmpty()){  // While there are still points to be explored:
                    Point target = toBeExplored.poll();
                    int row = (int)target.getY(), col = (int)target.getX();  // Get adjacents to the target point
                    Point[] adjacents = {new Point(col, row-1), new Point(col, row+1), new Point(col-1, row), new Point(col+1, row)};
                    for (Point adj : adjacents){  // If adjacent point needs to be discovered, add to toBeExplored
                        row = (int)adj.getY(); col = (int)adj.getX();
                        if ((map.length > row)&&(row >= 0)&&(map[row].length > col)&&(col >= 0)&&(map[row][col] < 9)){
                            if (visited.add(adj)) { toBeExplored.offer(adj); }  // If unexplored, add to the queue
                        }
                    }
                }
                
                for (int i = 0; i < NUM_OF_BASINS; i++){  // Check if this basin is one of the new largestBasins
                    if (largestBasins.get(i) < visited.size()){
                        largestBasins.add(i, visited.size());
                        largestBasins.remove(NUM_OF_BASINS);
                        break;
                    }
                }
            }
            
            int basinProduct = 1;
            for (int n : largestBasins){ basinProduct *= n; }  // Get the product of all the largestBasins
            
            System.out.println("########## Day 9 ##########");  // Print outputs
            System.out.println("Part 1: "+riskLevelSum);
            System.out.println("Part 2: "+basinProduct);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] arguments) {
        SmokeBasin sb = new SmokeBasin();
    }
}