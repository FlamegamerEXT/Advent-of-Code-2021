import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.*;
import java.util.*;

/**
 * An indepth description of what Octopus is attempting to do can be found here:
 * https://adventofcode.com/2021/day/12
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (15/12/2021)
 */
public class Passage{
    /**
     * Passage loads up a cave system and finds all the paths that fit a given criteria
     * Part 1: Visit small caves at most once, and can visit big caves any number of times.
     * Part 2: Big caves can be visited any number of times, a single small cave can be visited at most twice,
     * and the remaining small caves can be visited at most once.
     * (big caves are written in uppercase, small caves are written in lowercase).
     */
    public Passage(){
        List<String[]> lines = new ArrayList<String[]>(); 
        Map<String, Set<String>> caves = new HashMap<String, Set<String>>();
        try {  // Load file
            File file = new File("Passage.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNext()){  // Create links between caves
                String[] link = sc.nextLine().split("-", 0);
                for (int i = 0; i < link.length; i++){
                    Set<String> neighbors = new HashSet<String>();
                    if (caves.containsKey(link[i])){ neighbors = caves.get(link[i]); }
                    neighbors.add(link[link.length-1-i]);
                    caves.put(link[i], neighbors);
                }
            }
            sc.close();
            
            System.out.println("########## Day 12 ##########");  // Print outputs
            System.out.println("Part 1: "+allPaths(caves, 1));
            System.out.println("Part 2: "+allPaths(caves, 2));
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    /** Starts the recursive method for finding paths through the caves */
    public int allPaths(Map<String, Set<String>> caves, int smallCaveVisits){
        return allPathsRecursion(caves, "start", "", smallCaveVisits);
    }
    
    /** Recursively finds paths through the caves */
    public int allPathsRecursion(Map<String, Set<String>> caves, String cave, String path, int smallCaveVisits){
        int paths = 0, smallCavesVisited = 0;
        Set<String> visitedSmallCaves = new HashSet<String>();
        for (String s : path.split(",", 0)){  // Take route so far and add count all of the small caves
            if (s.equals(s.toLowerCase())){
                smallCavesVisited++;
                visitedSmallCaves.add(s);
            }
        }
        // If small caves have been visited too many times, then no valid paths can come from this route
        if ((smallCavesVisited == visitedSmallCaves.size() + smallCaveVisits)){ return 0; }
        if (cave.equals("end")){  // This path is a complete path
            paths = 1;
        } else {  // Sum together all valid paths that start with this route
            for (String neighbour : caves.get(cave)){
                if (!neighbour.equals("start")){ paths += allPathsRecursion(caves, neighbour, path+cave+",", smallCaveVisits); }
            }
        }
        return paths;  // Return count of all valid paths
    }
    
    //Main
    public static void main(String[] arguments) {
        Passage p = new Passage();
    }
}