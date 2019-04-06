//*vivin* Commented the unused imports and added regex for pattern matching

import java.io.*;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.Iterator;
//import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello UKP candidate.
 * The following program should read an arbitrary number of files from a directory and store all their tokens in a member variable.
 * The program may contain some bugs interfering with the desired functionality.
 * Your tasks are:
 * 1. Understand the program and correct all errors that lead to erroneous behaviour. Comment your changes and shortly explain the reasons.
 * 2. The program also contains some questionable programming constructs (e.g. disregarding Java naming conventions, etc.).
 *    Try to find as many as you can, and correct them. Comment your changes and shortly explain the reasons.
 * 3. Add the missing JavaDocs at a level of detail that you consider as appropriate.
 * 4. Write a <b>single</b> method that <b>returns</b> the number of items in tokenMap, the average length (as double value) of the elements in tokenMap after calling applyFilters(), and the number of tokens starting with "a" (case sensitive).
 *    Output this information.
 *    
 * @author zesch
 */
public class ProgrammingSkillsTest {
    
    public final static String CHARSET = "ISO-8859-1";

    public File inputDir;
 
    //*vivin* initialized the nrofFiles variable which counts the number of files read
    public double nrofFiles = 0;
    
    public int minimumCharacters;
    public int maximumCharacters;
 
    //*vivin*	added regex variable to store the pattern to be checked against the read tokens, 'a' in this case
    private static final String REGEX = "\\ba";
    
    //*vivin*	Regex variable to store tokens, which is then checked against the pattern to find a match 
    String INPUT = " ";
   
    //*vivin*	created new object for the TokenMap to store the tokens from the TokenBuffer
    HashMap<String, Integer> tokenMap = new HashMap<String, Integer>();
    
    //*vivin*	new HashMap variable to store the tokens from the file as they are being read    
    HashMap<String, Integer> fileTokensBuffer = new HashMap<String, Integer>();
    
    public ProgrammingSkillsTest(File pInputDir, int pMinChars, int pMaxChars) {
        
        inputDir = pInputDir;
        minimumCharacters = pMinChars;
        maximumCharacters = pMaxChars;
              
        if ((pMinChars == 0) || (pMaxChars == 0)) {
            throw new RuntimeException("Configuration parameters have not been correctly initialized.");
        }
    }

    public void run() {
        readFiles();    
        applyFilters();     

        //*vivin	new method for completing task 4
        TransferObject transobj = tokenMapStats();

        //*vivin*	returns the number of items in the TokenMap
        System.out.println("Number of items in the TokenMap: "+ transobj.getNoOfTokens());
      
        //*vivin*	returns the average length of the elements in the TokenMap
        System.out.println("Average Length of the elements in the TokenMap: "+ transobj.getAverageLengthofTokens());
        
        //*vivin*	returns the number of items in the TokenMap starting with 'a'	
        System.out.println("Number of items in the TokenMap starting with a: "+ transobj.getNoOfTokensStartingWithA());
        
        outputTokens();
    }
    
    /**
     * This Method reads all the files in specified directory.
     * It iterates through all the files and generates the tokens which are then stored in the hashmap
     * the program is exited in case the directory is empty or if it does not exist
     * the empty files are skipped.
     */
    private void readFiles() {
        File[] files = inputDir.listFiles();
        //*vivin*	added extra if condition "files.length == 0" to check for empty directory, since the "files == null" condition only checks if the directory exists
        if (files == null || files.length == 0 ) {
            System.err.println("Directory does not exist or the Filelist is empty. Directory: " + inputDir.getAbsolutePath());
            System.exit(1);
        }
        
        for (int i = 0; i < files.length; i++) {
            File file = new File(files[i].getAbsoluteFile().toString());
            
            if (file.length() == 0) {
                System.out.println("Skipping emtpy file " + file.getAbsolutePath());
                continue;
            }

            System.out.println(file.getAbsolutePath());
            
            //*vivin*	calculates the number of files which are read 
            nrofFiles += 1;
            
            //*vivin	removed storing reference of the returned map, since only the logic was incorrect as only the tokens of the last read file were available.    
            getFileTokens(file);
        }
        //*vivin* 	copies all the tokens from the TokenBuffer (which stores all the tokens read from the files) to the tokenMap    
        tokenMap.putAll(fileTokensBuffer); 
    }

    /**
     * This Method receives a file, which is iterated line by line.
     * Every line is further tokenized by empty spaces delimiter.Then each token is checked to see if it is already present in the TokenMap
     * if present only the count is incremented, else the new token is added to the map.
     *
     * @param infile file which needs to be tokenized
     */
    //*vivin	changed the method signature
//  private HashMap<String, Integer> getFileTokens(File infile) { 
    private void getFileTokens(File infile) {     
        HashMap<String, Integer> fileTokens = new HashMap<String, Integer>();
        
        BufferedReader in;
        String line;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(infile), CHARSET));
            while ((line = in.readLine()) != null) {
                String lineParts[] = line.split(" ");
                for (String part : lineParts) {
                    if (fileTokens.containsKey(part)) {
                        fileTokens.put(part, fileTokens.get(part) + 1);
                    }
                    else {
                        fileTokens.put(part, 1);
                    }    
                }
            }
            in.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        //*vivin*	copies all the tokens read from the file to the TokenBuffer
        fileTokensBuffer.putAll(fileTokens); 

        //*vivin* commented since nothing is being return now
//      return fileTokens;       
    }
    
    /**
     * This method applies filter on the tokens stored in the TokenMap.
     * Tokens whose length is more than the maximumCharacters length and less than the minimumCharacters are removed from the TokenMap.
     */
    private void applyFilters() {    	
        try {
        	//*vivin	commented the below code block to avoid exceptions
/*  	      for (String token : tokenMap.keySet()) {
            	System.out.println("token: " + token + ", length: " +token.length());
                if (token.length() < minimumCharacters || token.length() > maximumCharacters) {
                                 	System.out.println("removing token inside applyFilters()");
                        tokenMap.remove(token);
                        System.out.println("after removing token inside applyFilters()"); 
                }
            }
*/   
        	
//*vivin*	new code block for removing the tokens since if the collection is not iterated using the iterator, the ConcurrentModificationException is thrown    	
        	String currentToken = "";
            Iterator iterator = tokenMap.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry)iterator.next();
                currentToken = (String) mentry.getKey();
                if (currentToken.length() < minimumCharacters || currentToken.length() > maximumCharacters) {
                    iterator.remove();
                }
            }
        }
        catch(Exception e) {System.err.println(e);}
    }

    /**new method for 1.) Finding the number of items in the TokenMap
     * 				  2.) Average Length of the elements in the TokenMap
     * 				  3.) Number of items in the TokenMap starting with 'a'
	 *		
     * new variables declared :- no_of_tokens_starting_with_a - stores the number of elements starting with 'a'
     * 							 tokenMapLength 			  - stores the length of the TokenMap
     * 							 tokenMapAvgLength 			  - stores the average length of the TokenMap
     */
    private TransferObject tokenMapStats() {
        int no_of_tokens_starting_with_a= 0;
        double tokenMapLength = 0;
        double tokenMapAvgLength = 0;

        //*vivin*	Checking for the tokens starting with 'a' (case sensitive)        
        for (String token : tokenMap.keySet()) {
        	INPUT = token;
            Pattern p = Pattern.compile(REGEX);
            Matcher m = p.matcher(INPUT);   
            
            while(m.find()) {
            	no_of_tokens_starting_with_a++;
            }
           
            tokenMapLength += token.length();
             		
        }
        
        /*vivin*	calculate the average length of the elements in the TokenMap
         * 			tokenMapLength is cast to double, to do floating point calculations			
         */
        tokenMapAvgLength = (tokenMapLength / tokenMap.size());             

        return new TransferObject(tokenMap.size(), tokenMapAvgLength, no_of_tokens_starting_with_a);
    }
    
    /**
     * Outputs all the tokens in the TokenMap after applying the filter
     */
    private void outputTokens() {
        String output = "";
        for (String token : tokenMap.keySet()) {
            output += token + "\n";
        }
        System.out.println(output);      
    }
    
    // New transfer object class for returning the number of tokens, average length of the tokens and no. of tokens starting with 'a'. 
    private class TransferObject {
        private int noOfTokens;
        private double averageLengthofTokens;
        private int noOfTokensStartingWithA;

        TransferObject(int noOfTokens, double averageLengthofTokens, int noOfTokensStartingWithA) {
            this.noOfTokens = noOfTokens;
            this.averageLengthofTokens = averageLengthofTokens;
            this.noOfTokensStartingWithA = noOfTokensStartingWithA;
        }

        int getNoOfTokens() {
            return noOfTokens;
        }

        double getAverageLengthofTokens() {
            return averageLengthofTokens;
        }

        int getNoOfTokensStartingWithA() {
            return noOfTokensStartingWithA;
        }
    }
    
    public static void main(String[] args) {     	
        if (args.length != 3) {
            System.err.println("Wrong number of parameters: java <application> <indir> <minChars> <maxChars>");
            System.exit(1);
        }

        File inputDir = new File(args[0]);
        int minChars = new Integer(args[1]);
        int maxChars = new Integer(args[2]);
        
        ProgrammingSkillsTest pst = new ProgrammingSkillsTest(inputDir, minChars, maxChars);
        pst.run();
    }
}