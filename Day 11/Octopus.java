import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.awt.Point;  // Import the Point class
import java.lang.*;
import java.util.*;

/**
 * An indepth description of what Octopus is attempting to do can be found here:
 * https://adventofcode.com/2021/day/11
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (15/12/2021)
 */
public class Octopus{
    /**
     * Octopus simulates the flashing of a group of Dumbo Octopus, until they all flash simultaneously
     */
    public Octopus(){
        List<String> lines = new ArrayList<String>();
        try {  // Load file
            File file = new File("Octopus.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNext()){
                lines.add(sc.nextLine());
            }
            sc.close();
            
            int[][] octopi = new int[lines.size()][lines.get(0).length()];  // Make table of octopi with flash values
            for (int y = 0; y < lines.size(); y++){
                String line = lines.get(y);
                char[] chars = line.toCharArray();
                for (int x = 0; x < line.length(); x++){
                    octopi[y][x] = Character.getNumericValue(chars[x]);
                }
            }
            
            int flashes = 0, newestFlashes = 0, iterations = 0, totalOctopi = octopi.length*octopi[0].length;
            while (newestFlashes < totalOctopi){  // Continue until all of the octopi flash at once
                Set<Point> flashOctopi = new HashSet<Point>();
                Stack<Point> waitingOctopi = new Stack<Point>();
                for (int y = 0; y < octopi.length; y++){
                    for (int x = 0; x < octopi[y].length; x++){
                        waitingOctopi.add(new Point(x, y));  // Add all octopi to the stack
                    }
                }
                while (!waitingOctopi.isEmpty()){  // While there are points to increment:
                    Point p = waitingOctopi.pop();
                    int x = (int)p.getX(), y = (int)p.getY();
                    octopi[y][x]++;
                    if ((octopi[y][x] > 9)&&(!flashOctopi.contains(p))){  // If it is a new flash:
                        flashOctopi.add(p);
                        for (Point a : adjacents(p, octopi)){  // Increment all adjacent points
                            waitingOctopi.push(a);
                        }
                    }
                }
                newestFlashes = flashOctopi.size();
                flashes += newestFlashes;
                for (Point p : flashOctopi){ octopi[(int)p.getY()][(int)p.getX()] = 0; }  // Reset all the flashing pctopi
                iterations++;
                if (iterations == 100){  // Print number of flashes after the first 100 iterations
                    System.out.println("########## Day 11 ##########");  // Print outputs
                    System.out.println("Part 1: "+flashes);
                }
            }
            System.out.println("Part 2: "+iterations);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    /** Returns an Array of booleans to say whether a group of points exists within the dimensions of the given table */
    public boolean[] adjacentsBool(Point p, int[][] table){
        boolean up = (p.getY() > 0), down = (p.getY() < table.length-1), left = (p.getX() > 0), right = (p.getX() < table[0].length-1);
        boolean[] ans = {up, (up&&right), right, (down&&right), down, (down&&left), left, (up&&left)};
        return ans;
    }
    
    /** Returns an Array of points that are neighbouring a given point */
    public Point[] adjacentsPoint(Point p){
        int x = (int)p.getX(), y = (int)p.getY();
        Point[] ans = {new Point(x, y-1), new Point(x+1, y-1),  new Point(x+1, y),  new Point(x+1, y+1), 
                        new Point(x, y+1), new Point(x-1, y+1),  new Point(x-1, y),  new Point(x-1, y-1)};
        return ans; 
    }
    
    /** Returns a set of adjacent Points that do exist within the dimensions of the given table */
    public HashSet<Point> adjacents(Point p, int[][] table){
        HashSet<Point> ans = new HashSet<Point>();
        boolean[] exists = adjacentsBool(p, table);
        Point[] points = adjacentsPoint(p);
        for (int i = 0; i < 8; i++){
            if (exists[i]){
                ans.add(points[i]);
            }
        }
        return ans; 
    }
    
    //Main
    public static void main(String[] arguments) {
        Octopus oct = new Octopus();
    }
}