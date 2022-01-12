import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.*;
import java.util.*;

/**
 * An indepth description of what Polymerization is attempting to do can be found here:
 * https://adventofcode.com/2021/day/14
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (16/12/2021)
 */
public class Polymerization{
    /**
     * Polymerization simulates the expansion of a given polymer 
     */
    public Polymerization(){
        Map<String, Long> pairCount, pairCountBlank = new HashMap<String, Long>();
        Map<String, String[]> template = new HashMap<String, String[]>();
        Set<Character> elements = new HashSet<Character>();
        try {  // Load file
            File file = new File("Polymerization.txt");
            Scanner sc = new Scanner(file);
            char[] startingPolymer = sc.nextLine().toCharArray();  // First line is the starting polymer
            sc.nextLine();  // Second line is blank
            while (sc.hasNext()){
                char[] line = sc.nextLine().toCharArray();
                String[] output = {(line[0]+""+line[line.length-1]), (line[line.length-1]+""+line[1])};
                template.put(line[0]+""+line[1], output);  // One pair give two new pairs
                elements.add(line[0]);
                elements.add(line[1]);
                elements.add(line[line.length-1]);
            }
            sc.close();
            
            for (char e1 : elements){  // Initialise the element pair count
                for (char e2 : elements){
                    pairCountBlank.put((e1+""+e2), Long.valueOf(0));
                }
            }
            
            // Put all the pairs in the starting polymer into pairCount
            pairCount = new HashMap<String, Long>(pairCountBlank);
            for (int i = 1; i < startingPolymer.length; i++){
                String compound = startingPolymer[i-1]+""+startingPolymer[i];
                pairCount.put(compound, pairCount.get(compound)+1);
            }
            
            int prints = 0;
            int[] printStep = {10, 40};
            System.out.println("########## Day 14 ##########");  // Print outputs
            for (int i = 1; i <= printStep[printStep.length-1]; i++){  // For each step:
                Map<String, Long> newPairCount = new HashMap<String, Long>(pairCountBlank);
                for (String pair : pairCount.keySet()){  // Get new pairs from the previous set of pairs
                    long count = pairCount.get(pair);
                    for (String newPair : template.get(pair)){
                        newPairCount.put(newPair, newPairCount.get(newPair)+count);
                    }
                }
                pairCount = newPairCount;
                                
                if (i == printStep[prints]){
                    prints++;
                    long elementMin = Long.MAX_VALUE, elementMax = Long.MIN_VALUE;
                    for (char e : elements){  // For each element, for count how often it appears in the newest polymer
                        long sum = 0;
                        if (e == startingPolymer[0]){ sum++; }  // If the polymer starts with this element, increment one
                        for (String pair : pairCount.keySet()){
                            long count = pairCount.get(pair);
                            if (e == pair.toCharArray()[1]){ sum += count; }  // If the polymer ends with this element, add count
                        }
                        elementMin = Math.min(sum, elementMin);
                        elementMax = Math.max(sum, elementMax);
                    }
                    System.out.println("Part "+prints+": "+(elementMax-elementMin));  // Print difference between max and min
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    //Main
    public static void main(String[] arguments) {
        Polymerization p = new Polymerization();
    }
}