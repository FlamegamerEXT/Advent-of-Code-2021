import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.lang.*;
import java.util.*;

/**
 * An indepth description of what SyntaxScoring is attempting to do can be found here:
 * https://adventofcode.com/2021/day/10
 *
 * Author: Matthew McCabe
 * Version: 1.0.0 (14/12/2021)
 */
public class SyntaxScoring{
    /**
     * SyntaxScoring finds illegal syntax and incomplete syntax, and gives a syntax score for each of them
     */
    private static final Map<String, String> closingChars = new HashMap<String, String>();
    public SyntaxScoring(){
        closingChars.put("(", ")");
        closingChars.put("[", "]");
        closingChars.put("{", "}");
        closingChars.put("<", ">");
        try {
            File file = new File("SyntaxScoring.txt");
            Scanner sc = new Scanner(file);
            int syntaxScore = 0;
            ArrayList<Long> completionScores = new ArrayList<Long>(); 
            while (sc.hasNext()){  // Work on each line individually
                char[] chars = sc.nextLine().toCharArray();
                Stack<String> chunks = new Stack<String>();
                boolean completable = true;
                for (char c : chars){  // Part 1: Illegal Syntax
                    String s = String.valueOf(c);
                    if (closingChars.containsKey(s)){
                        chunks.push(s);
                    } else {
                        String expected = closingChars.get(chunks.peek());
                        if (s.equals(expected)){
                            chunks.pop();
                        } else {
                            switch (s){
                                case ")":
                                    syntaxScore += 3;
                                    break;
                                case "]":
                                    syntaxScore += 57;
                                    break;
                                case "}":
                                    syntaxScore += 1197;
                                    break;
                                case ">":
                                    syntaxScore += 25137;
                                    break;
                            }
                            completable = false;
                            break;
                        }
                    }
                }
                
                long completionScore = 0;
                while (completable&&(!chunks.isEmpty())){  // Part 2: Incomplete Syntax
                    completionScore *= 5;
                    String s = closingChars.get(chunks.pop());
                    switch (s){
                        case ")":
                            completionScore++;
                            break;
                        case "]":
                            completionScore += 2;
                            break;
                        case "}":
                            completionScore += 3;
                            break;
                        case ">":
                            completionScore += 4;
                            break;
                    }
                }
                if (completionScore > 0) {completionScores.add(completionScore); }
            }
            sc.close();
            
            Collections.sort(completionScores);  // Sort to get the median value
            int middle = (completionScores.size()-1)/2;
            
            System.out.println("########## Day 10 ##########");  // Print outputs
            System.out.println("Part 1: "+syntaxScore);
            System.out.println("Part 2: "+completionScores.get(middle));
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public static void main(String[] arguments) {
        SyntaxScoring ss = new SyntaxScoring();
    }
}