
// Removed unused imports.#Correction

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Hello UKP candidate.
 * The following program should read an arbitrary number of files from a directory and store all their tokens in a member variable.
 * The program may contain some bugs interfering with the desired functionality.
 * Your tasks are:
 * 1. Understand the program and correct all errors that lead to erroneous behaviour. Comment your changes and shortly explain the reasons.
 * 2. The program also contains some questionable programming constructs (e.g. disregarding Java naming conventions, etc.).
 * Try to find as many as you can, and correct them. Comment your changes and shortly explain the reasons.
 * 3. Add the missing JavaDocs at a level of detail that you consider as appropriate.
 * 4. Write a <b>single</b> method that <b>returns</b> the number of items in tokenMap, the average length (as double value) of the elements in tokenMap after calling applyFilters(), and the number of tokens starting with "a" (case sensitive).
 * Output this information.
 *
 * @author zesch
 */
// class name was wrong, renamed class to file name. #correction
public class ProgrammingSkillsTest_v2_solution {
    // set visibility of member varibles to private since they are used within this class only. #correction
    private final static String CHARSET = "ISO-8859-1";
    private File inputDir;
    private int minimumCharacters;
    private int maximumCharacters;
    // initialized the tokenMap here.#correction
    private HashMap<String, Integer> tokenMap = new HashMap<String, Integer>();


    public ProgrammingSkillsTest_v2_solution(File pInputDir, int pMinChars, int pMaxChars) {

        inputDir = pInputDir;
        minimumCharacters = pMinChars;
        maximumCharacters = pMaxChars;

        if ((pMinChars == 0) || (pMaxChars == 0)) {
            throw new RuntimeException("Configuration parameters have not been correctly initialized.");
        }
    }

    public void run() {
        // read files and generate tokens
        readFiles();
        // apply filter to these tokens
        applyFilters();
        // output final tokens
        outputTokens();
        // call the newly added method
        TransferObject obj = findAgvLengthAndTokenStartingWitha();

        System.out.println("No of items : " + obj.getNoOfTokens());
        System.out.println("Average Length : " + obj.getAverageLength());
        System.out.println("No. of tokens starting with alphabet 'a' : " + obj.getStartingWithA());
    }


    /**
     * This Method reads all the files in given location.
     * It iterates through all the files, skipping the ones which are empty.
     * For the ones which are not empty, method getFileTokens is called.
     */
    private void readFiles() {

        File[] files = inputDir.listFiles();
        if (files == null) {
            System.err.println("Filelist is empty. Directory: " + inputDir.getAbsolutePath());
            System.exit(1);
        }

        for (File file : files) {
            if (file.length() == 0) {
                System.out.println("Skipping emtpy file " + file.getAbsolutePath());
                continue;
            }
            //removed storing reference of the returned map, as it will only reflect the last read file.
            // instead changed the signature of the getFileTokens method so that it directly updates the tokenMap.#correction
            getFileTokens(file);
        }
    }

    /**
     * This Method receives a file, which is iterated line by line.
     * Every line is further tokenized by empty spaces delimiter.Then for each token it is checked  it is already
     * present in the tokenMap or not, if present the count is incremented and if not the new token is added to the map.
     *
     * @param infile file which needs to be tokenized
     */
    // changed the method signature, updated to void.#correction
    private void getFileTokens(File infile) {
        // there is no need to created another map here. we can also use the tokenMap directly. 
        // so local map not required anymore.#correction
        //HashMap<String, Integer> fileTokens = new HashMap<String, Integer>();

        BufferedReader in;
        String line;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(infile), CHARSET));

            while ((line = in.readLine()) != null) {
                System.out.println(line);
                String lineParts[] = line.split(" ");
                for (String part : lineParts) {
                    if (tokenMap.containsKey(part)) {
                        tokenMap.put(part, tokenMap.get(part) + 1);
                    } else {
                        tokenMap.put(part, 1);
                    }
                }
            }
            in.close();
            // bad practice to catch Exception, changed to IOException instead.#Correction
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        // method returns nothing now.#correction
        // return fileTokens;
    }


    /**
     * This method applies the filter on the extracted tokens.
     * Tokens which are above the maximumCharacters length and token which are below the minimumCharacters
     * are removed from the map.
     */
    private void applyFilters() {

        // used Iterator to avoid concurrent modification exception.
        // tokenMap was used for iteration and modified at the same time which leads to exception.
        Iterator itr = tokenMap.entrySet().iterator();
        String token;
        while (itr.hasNext()) {
            token = itr.next().toString();
            if (token.length() < minimumCharacters || token.length() > maximumCharacters) {
                itr.remove();
            }
        }
    }

    /**
     * This method prints all the tokens to console.
     */
    private void outputTokens() {
        String output = "";
        for (String token : tokenMap.keySet()) {
            output += token + "\n";
        }
        System.out.println(output);
    }


    // TASK No 4

    /**
     * This Method finds and returns the following:
     * 1. No. of token.
     * 2. Average length of tokens.
     * 3. No. of tokens starting with 'a'.
     */
    private TransferObject findAgvLengthAndTokenStartingWitha() {
        int no_of_token_starting_with_a = 0;
        double total_length_of_all_tokens = 0;
        //iterate over all the tokens.
        for (String temp : tokenMap.keySet()) {
            total_length_of_all_tokens = total_length_of_all_tokens + temp.length();
            if (!temp.equals("") && temp.startsWith("a")) {
                no_of_token_starting_with_a++;
            }

        }
        return new TransferObject(tokenMap.size(), (total_length_of_all_tokens / tokenMap.size()), no_of_token_starting_with_a);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Wrong number of parameters: java <application> <indir> <minChars> <maxChars>");
            System.exit(1);
        }

        File inputDir = new File(args[0]);
        int minChars = new Integer(args[1]);
        int maxChars = new Integer(args[2]);

        ProgrammingSkillsTest_v2_solution pst = new ProgrammingSkillsTest_v2_solution(inputDir, minChars, maxChars);
        pst.run();
    }


    // This class is used as a transfer object. Containing the required information as member variables and package
    // visible getter methods
    private class TransferObject {
        private int noOfTokens;
        private double averageLength;
        private int startingWithA;

        TransferObject(int noOfTokens, double averageLength, int startingWithA) {
            this.noOfTokens = noOfTokens;
            this.averageLength = averageLength;
            this.startingWithA = startingWithA;
        }


        int getNoOfTokens() {
            return noOfTokens;
        }


        double getAverageLength() {
            return averageLength;
        }


        int getStartingWithA() {
            return startingWithA;
        }


    }
}