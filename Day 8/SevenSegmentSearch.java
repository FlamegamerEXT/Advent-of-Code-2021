import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.HashSet;  // Import the HashSet class
import java.lang.*;

/**
 * An indepth description of what SevenSegmentSearch is attempting to do can be found here:
 * https://adventofcode.com/2021/day/8
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (9/12/2021)
 */
public class SevenSegmentSearch{
    private static final int[] NUM_OF_SEGMENTS = {6, 2, 5, 5, 4, 5, 6, 3, 7, 6};  // The number of segments used for these number 0-9
    private static final int[][] SEGMENTS_FOR_NUMS = {{}, {}, {1}, {7}, {4}, {2, 3, 5}, {0, 6, 9}, {8}};
    /**
     * SevenSegmentSearch decodes a complete set of segments orientations, and then uses that info to get a 4-digit number
     */
    public SevenSegmentSearch(){
        try {
            File file = new File("SevenSegmentSearch.txt");
            Scanner sc = new Scanner(file);
            int easyOutputs = 0, outputSum = 0;
            while (sc.hasNext()){  // Decode the digits for each line
                String[] strings = sc.nextLine().split(" ", 0);
                boolean foundDelimiter = false;
                
                HashSet<String> lengthOfFive = new HashSet<String>(), lengthOfSix = new HashSet<String>();
                String[] foundNumbers = new String[NUM_OF_SEGMENTS.length];
                char[] foundSegments = new char[SEGMENTS_FOR_NUMS.length-1];
                String[] outputs = new String[4];  // Output is always 4 digits
                
                int outputIndex = 0;
                for (String s : strings){
                    if (foundDelimiter){  // 4-digit number
                        if (s.length()<SEGMENTS_FOR_NUMS.length){ // Part 1:
                            if (SEGMENTS_FOR_NUMS[s.length()].length == 1) {
                                easyOutputs++;  // Count the 1s, 4s, 7s, and 8s
                            }
                        }
                        outputs[outputIndex] = s;
                        outputIndex++;
                    } else if (s.equals("|")){
                        foundDelimiter = true;
                    } else if (SEGMENTS_FOR_NUMS.length > s.length()){  // Digits to decode
                        if (SEGMENTS_FOR_NUMS[s.length()].length == 1) {
                            foundNumbers[SEGMENTS_FOR_NUMS[s.length()][0]] = s;
                        } else if (s.length() == 5) { lengthOfFive.add(s);
                        } else if (s.length() == 6) { lengthOfSix.add(s); }
                    }
                }
                
                // Gives the string for the number 6
                for (String s: lengthOfSix){
                    boolean isSix = false;
                    // 6 is the only lengthOfSix digit to not have 1 be a subset of itself
                    for (char c1: foundNumbers[1].toCharArray()){
                        boolean charFound = true;
                        for (char c: s.toCharArray()){ charFound = charFound&&(c1 != c); }
                        isSix = isSix||charFound;
                    }
                    if (isSix){
                        foundNumbers[6] = s;
                        break;
                    }
                }
                lengthOfSix.remove(foundNumbers[6]);
                
                // Gives the string for the number 9
                for (String s: lengthOfSix){
                    boolean isNine = true;
                    // 9 is the only lengthOfSix digit to have 4 be a subset of itself
                    for (char c4: foundNumbers[4].toCharArray()){
                        boolean charFound = false;
                        for (char c: s.toCharArray()){ charFound = charFound||(c4 == c); }
                        isNine = isNine&&charFound;
                    }
                    if (isNine){
                        foundNumbers[9] = s;
                        break;
                    }
                }
                lengthOfSix.remove(foundNumbers[9]);
                
                // Gives the string for the number 0, as 6 and 9 have been found
                if (lengthOfSix.size() == 1){
                    for (String s : lengthOfSix){ foundNumbers[0] = s; }
                    lengthOfSix.clear();
                }
                
                // Gives the string for the number 3
                for (String s: lengthOfFive){
                    boolean isThree = true;
                    // 3 is the only lengthOfFive digit to have 1 be a subset of itself
                    for (char c1: foundNumbers[1].toCharArray()){
                        boolean charFound = false; // If the number contains the same chars as 1;
                        for (char c: s.toCharArray()){
                            charFound = charFound||(c1 == c);
                        }
                        isThree = isThree&&charFound;
                    }
                    if (isThree){
                        foundNumbers[3] = s;
                        break;
                    }
                }
                lengthOfFive.remove(foundNumbers[3]);
                
                // Gives the string for the number 2
                for (String s: lengthOfFive){
                    boolean isTwo = false;
                    // 2 is the only lengthOfFive digit to not be a subset of 9
                    for (char c: s.toCharArray()){
                        boolean charFound = true;
                        for (char c9: foundNumbers[9].toCharArray()){ charFound = charFound&&(c9 != c); }
                        isTwo = isTwo||charFound;
                    }
                    if (isTwo){
                        foundNumbers[2] = s;
                        break;
                    }
                }
                lengthOfFive.remove(foundNumbers[2]);
                
                // Gives the string for the number 5, as 2 and 3 have been found
                if (lengthOfFive.size() == 1){
                    for (String s : lengthOfFive){ foundNumbers[5] = s; }
                    lengthOfFive.clear();
                }
                
                // Convert the 4 encoded digits to a 4 digit number
                int output = 0;
                for (String s : outputs){
                    output *= 10;
                    int[] numsSameLength = SEGMENTS_FOR_NUMS[s.length()];
                    if (numsSameLength.length == 1){  // If there is only one digit of this length, convert immediately
                        output += numsSameLength[0];
                    } else {  // Else, check for the number that has all the same segments
                        for (int n : numsSameLength){
                            String f = foundNumbers[n];
                            boolean sameNumber = true;
                            for (char cf: f.toCharArray()){
                                boolean charFound = false;
                                for (char cs: s.toCharArray()){
                                    charFound = charFound||(cf == cs);
                                }
                                sameNumber = sameNumber&&charFound;
                            }
                            if (sameNumber){ output += n; }
                        }
                    }
                }
                outputSum += output;
            }
            sc.close();
            
            System.out.println("########## Day 8 ##########");  // Print outputs
            System.out.println("Part 1: "+easyOutputs);  // Sum of all the 1s, 4s, 7s and 8s
            System.out.println("Part 2: "+outputSum);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] arguments) {
        SevenSegmentSearch sss = new SevenSegmentSearch();
    }
}