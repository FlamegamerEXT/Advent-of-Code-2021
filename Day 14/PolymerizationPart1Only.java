import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.*;
import java.util.*;

/**
 * Write a description of class PolymerizationPart1Only here.
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (16/12/2021)
 */
public class PolymerizationPart1Only{
    /**
     * Constructor for objects of class PolymerizationPart1Only
     */
    public PolymerizationPart1Only(){
        Map<String, Character> template = new HashMap<String, Character>();
        Set<Character> elements = new HashSet<Character>();
        try {
            File file = new File("Polymerization (Test).txt");// (Test)
            Scanner sc = new Scanner(file);
            char[] polymer = sc.nextLine().toCharArray();
            sc.nextLine();
            while (sc.hasNext()){
                char[] line = sc.nextLine().toCharArray();
                template.put(line[0]+""+line[1], line[line.length-1]);
                elements.add(line[0]);
                elements.add(line[1]);
                elements.add(line[line.length-1]);
            }
            sc.close();
             
            int[] printStep = {10, 40};
            for (int i = 1; i <= 10; i++){ // Memory error if attempt to reach 40
                char[] newPolymer = new char[polymer.length*2-1];
                newPolymer[0] = polymer[0];
                for (int n = 1; n < polymer.length; n++){
                    newPolymer[2*n-1] = template.get(polymer[n-1]+""+polymer[n]);
                    newPolymer[2*n] = polymer[n];
                }
                polymer = newPolymer;
                
                for (int n = 1; n <= printStep.length; n++){
                    if (i == printStep[n-1]){
                        long elementMin = Long.MAX_VALUE, elementMax = Long.MIN_VALUE;
                        for (char e : elements){
                            int count = 0;
                            for (char p : polymer){
                                if (e == p){ count++; }
                            }
                            elementMin = Math.min(count, elementMin);
                            elementMax = Math.max(count, elementMax);
                        }
                        System.out.println("Part "+n+": "+(elementMax-elementMin));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] arguments) {
        PolymerizationPart1Only p = new PolymerizationPart1Only();
    }
}
