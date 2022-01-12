import java.io.File;  // Import the File class
import java.util.ArrayList;  // Import the ArrayList class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

/**
 * An indepth description of what BinaryDiagnostic is attempting to do can be found here:
 * https://adventofcode.com/2021/day/3
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (3/12/2021)
 */
public class BinaryDiagnostic {
    /**
     * BinaryDiagnostic takes in a series of ones and zeroes to get a reading from them
     */
    public BinaryDiagnostic(){
        try {  // Load file
            File file = new File("Binary Diagnostic.txt");
            Scanner sc = new Scanner(file);
            
            ArrayList<Integer> ones = new ArrayList<Integer>(), zeroes = new ArrayList<Integer>();  // Counts of ones and zeroes
            ArrayList<String> lines = new ArrayList<String>();  // Record of all lines 
            
            while (sc.hasNextLine()) {  // Read each line
                String line = sc.next();
                lines.add(line);
                
                for (int i = 0; i < line.length(); i++){  // For each line, count the number of ones and zeroes
                    if (ones.size() + zeroes.size() < line.length()*2){  // If there are fewer columns than in lines, add new ones.
                        ones.add(i, 0);  zeroes.add(i, 0);
                    }
                    if (line.substring(i, i+1).equals("0")){ zeroes.set(i, zeroes.get(i)+1); }  // Increment zeroes
                    else if (line.substring(i, i+1).equals("1")){ ones.set(i, ones.get(i)+1); }  // Increment ones
                }
            }
            sc.close();
            
            // Part 1 calculations
            int gamma = 0, epsilon = 0;
            for (int i = 0; i < ones.size(); i++){  // Convert totals to a number in binary
                gamma*=2; epsilon*=2;  // Move along one line (in binary)
                if (ones.get(i) > zeroes.get(i)){ gamma++; }  // If ones outnumber zeroes, increment gamma
                else { epsilon++; }  // Else, increment epsilon
            }
            
            // Part 2 calculations
            ArrayList<String> o2nums = new ArrayList<String>(), co2nums = new ArrayList<String>();  // Lines that fit the readings
            o2nums.addAll(lines);  co2nums.addAll(lines);  // Start with all lines
            
            for (int i = 0; i < lines.get(0).length(); i++){  // For each column:
                int o2one = 0, o2zero = 0, co2one = 0, co2zero = 0;  // Counts of each digit for each gas
                
                for (String num : o2nums){  // For each row in o2nums, count the number of each digits
                    if (num.substring(i, i+1).equals("0")){ o2zero++; }
                    else { o2one++; }
                }
                if (o2nums.size() > 1){  // If there is more than one o2 number left:
                    for (int n = 0; n < o2nums.size(); n++){  // If the number is in the minority, remove it from o2nums
                        String num = o2nums.get(n);
                        if ((o2one < o2zero)&&(num.substring(i, i+1).equals("1"))||
                            ((o2one >= o2zero)&&(num.substring(i, i+1).equals("0")))){ o2nums.remove(n); n--; }                   
                    }
                }
                
                for (String num : co2nums){  // For each row in co2nums, count the number of each digits
                    if (num.substring(i, i+1).equals("0")){ co2zero++; }
                    else { co2one++; }             
                }
                if (co2nums.size() > 1){  // If there is more than one co2 number left:
                    for (int n = 0; n < co2nums.size(); n++){  // If the number is in the majority, remove it from co2nums
                        String num = co2nums.get(n);
                        if ((co2one < co2zero)&&(num.substring(i, i+1).equals("0"))||
                            ((co2one >= co2zero)&&(num.substring(i, i+1).equals("1")))){ co2nums.remove(n); n--; }                   
                    }
                }
            }
            
            int o2 = 0, co2 = 0;  // Gas readings
            String o2s = o2nums.get(0), co2s = co2nums.get(0);  // Get the one remaining reading
            for (int i = 0; i < lines.get(0).length(); i++){  // Convert binary strings into numbers
                o2 *= 2; co2 *= 2;
                if (o2s.substring(i, i+1).equals("1")){ o2++; }
                if (co2s.substring(i, i+1).equals("1")){ co2++; }
            }
            
            System.out.println("########## Day 3 ##########");  // Print outputs, the products of two numbers
            System.out.println("Part 1 - Total: "+gamma*epsilon);
            System.out.println("Part 2 - Total: "+o2*co2);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Main
    public static void main(String[] arguments) {
        BinaryDiagnostic bd = new BinaryDiagnostic();
    }
}
