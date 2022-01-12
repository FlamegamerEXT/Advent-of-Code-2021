import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.*;
import java.util.*;

/**
 * An indepth description of what Decoder is attempting to do can be found here:
 * https://adventofcode.com/2021/day/16
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (19/12/2021)
 */
public class Decoder{
    public static final String HEX_STRING = "0123456789ABCDEF";
    /**
     * Decoder takes a hexadecimal string, and derives meaning from the binary values. 
     */
    public Decoder() {
        try {  // Load file
            File file = new File("Decoder.txt");
            Scanner sc = new Scanner(file);
            char[] hexadecimal = sc.nextLine().toCharArray();
            sc.close();
            
            Queue<Integer> binaryQueue = new ArrayDeque<Integer>();
            for (int i = 0; i < hexadecimal.length; i++){
                int num = HEX_STRING.indexOf(hexadecimal[i]);  // Converts Hexadecimal to Decimal value
                if (num != -1){
                    for (int j = 0; j < 4; j++){ // Converts Decimal to Binary values
                        binaryQueue.offer((num%Math.pow(2, 4-j) >= Math.pow(2, 4-j-1)) ? 1 : 0);
                    }
                }
            }
            
            System.out.println("########## Day 16 ##########");
            System.out.println("Part 1: "+openPacket(new ArrayDeque<Integer>(binaryQueue), true));  
            System.out.println("Part 2: "+openPacket(new ArrayDeque<Integer>(binaryQueue), false));
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    /**
     * This recursive method opens binary packets, some of which contain their own packet, and returns either the sum of the versions
     * of all of the packets if returnVersionSum is true, or the encoded expression if returnVersionSum is false.
     */
    public long openPacket(Queue<Integer> queue, boolean returnVersionSum){
        int version = 0, typeID = 0, versionSum;
        for (int j = 0; j < 3; j++){  // gets the version number, an int from 0-7
            version *= 2;
            version += queue.poll();
        }
        for (int j = 0; j < 3; j++){  // gets the typeID number, an int from 0-7
            typeID *= 2;
            typeID += queue.poll();
        }
        versionSum = version;  // Start versionSum
        long expression = Long.valueOf(0);  // Start expression
        if (typeID == 4){  // If this packet contains a numerical value
            int start = 1;
            while (start == 1){  // The final packet for a numerical value begins with a 0, all other numerical values begin with 1;
                start = queue.poll();
                for (int j = 0; j < 4; j++){  // Continue expanding the number with binary values
                    expression *= 2;
                    expression += queue.poll();
                }
            }
        } else {  // If this packet doesn't contain a numerical value
            int lengthOfSubPackets = 0, numberOfSubPackets = 0;
            int lengthTypeID = queue.poll();
            if (lengthTypeID == 0){  // Packets with lengthTypeID of 0 have a fixed total length, number of subpackets unknown
                for (int j = 0; j < 15; j++){
                        lengthOfSubPackets *= 2;
                        lengthOfSubPackets += queue.poll();
                    }
            } else if (lengthTypeID == 1){  // Packets with lengthTypeID of 1 contain a fixed number of subpackets, total length unknown
                for (int j = 0; j < 11; j++){
                    numberOfSubPackets *= 2;
                    numberOfSubPackets += queue.poll();
                }
            }
            int queueEnd = Math.max(queue.size() - lengthOfSubPackets, 0);  // Calculates when the last subpacket should be
            ArrayList<Long> subPackets = new ArrayList<Long>();  // ArrayList of returned values
            while ((numberOfSubPackets > 0)||(queue.size() > queueEnd)){  // While there are still subpackets contained in this packet:
                long returnedValue = openPacket(queue, returnVersionSum);  // Open subpacket
                if (returnVersionSum){ versionSum += returnedValue; }
                subPackets.add(returnedValue);
                numberOfSubPackets--;
            }
            switch (typeID){
                case 0:  // expression is the sum of all subPackets
                    for (long n : subPackets){ expression += n; }
                    break;
                case 1:  // expression is the product of all subPackets
                    expression += 1;
                    for (long n : subPackets){ expression *= n; }
                    break;
                case 2:  // expression is the minimum of the subPackets
                    expression = subPackets.get(0);
                    for (long n : subPackets){ expression = Math.min(expression, n); }
                    break;
                case 3:  // expression is the maximum of the subPackets
                    expression = subPackets.get(0);
                    for (long n : subPackets){ expression = Math.max(expression, n); }
                    break;
                case 5:  // expression is a 1 if the first subPacket is greater than the second
                    expression = Long.valueOf((subPackets.get(0) > subPackets.get(1)) ? 1 : 0);
                    break;
                case 6:  // expression is a 1 if the first subPacket is less than the second
                    expression = Long.valueOf((subPackets.get(0) < subPackets.get(1)) ? 1 : 0);
                    break;
                case 7:  // expression is a 1 if the first subPacket is equal to the second
                    expression = Long.valueOf((subPackets.get(0) == subPackets.get(1)) ? 1 : 0);
                    break;
                default:  // There should not be any values other than 0 to 7
                    break;
            }
        }
        
        if (returnVersionSum) { return versionSum; }
        return expression;
    }
    
    // Main
    public static void main(String[] arguments) {
        Decoder d = new Decoder();
    }
}