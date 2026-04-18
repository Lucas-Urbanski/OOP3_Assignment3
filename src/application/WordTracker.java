package application;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import implementations.BSTree;
import implementations.BSTreeNode;
import utilities.Iterator;

/**
 * WordTracker.java
 * 
 * @author Jordi Usen and Alessandro 
 * @version 1.0
 * 
 * Class Description: Reads a text file, stores all unique words in a binary
 * search tree, tracks the file names and line numbers in which each word
 * appears, and generates reports based on the option supplied at the command
 * line.
 */
public class WordTracker {

    /**
     * Main method for the WordTracker program.
     * 
     * Expected usage:
     * java -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]
     * 
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            printUsage();
            return;
        }

        String inputFile = args[0];
        String option = args[1];
        String outputFile = null;

        // Validate the report option
        if (!option.equals("-pf") && !option.equals("-pl") && !option.equals("-po")) {
            System.out.println("Invalid report option.");
            printUsage();
            return;
        }

        // Validate the optional output file argument
        if (args.length == 3) {
            if (!args[2].startsWith("-f")) {
                System.out.println("Invalid output file argument.");
                printUsage();
                return;
            }
            outputFile = args[2].substring(2);
            if (outputFile.isEmpty()) {
                System.out.println("Output file name cannot be empty.");
                return;
            }
        }

        // Create the BST that will store all tracked words
        BSTree<TrackedWord> tree = new BSTree<>();

        // Build the BST from the input file
        try {
            buildTreeFromFile(tree, inputFile);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        PrintWriter writer = null;

        try {
            // If an output file is provided, write to that file.
            // Otherwise, write to the console.
            if (outputFile != null) {
                writer = new PrintWriter(new FileWriter(outputFile));
            } else {
                writer = new PrintWriter(System.out, true);
            }

            // Print the selected report
            if (option.equals("-pf")) {
                printPFReport(tree, writer);
            } else if (option.equals("-pl")) {
                printPLReport(tree, writer);
            } else if (option.equals("-po")) {
                printPOReport(tree, writer);
            }

            writer.flush();

            // Close the file writer if output was redirected to a file
            if (outputFile != null) {
                writer.close();
                System.out.println("Report written to: " + outputFile);
            }

            // Save the tree to repository.ser
            // Note: This completes assignment requirement 5.
            // Requirement 6 (restoring the repository on startup) is still being skipped.
            saveRepository(tree);

        } catch (IOException e) {
            System.out.println("Error writing output: " + e.getMessage());
        }
    }

    /**
     * Reads the input file line by line, extracts valid words, and inserts them
     * into the BST. If a word already exists in the tree, its occurrence
     * information is updated.
     * 
     * @param tree the BST used to store tracked words
     * @param inputFile the path of the text file to be processed
     * @throws IOException if the file cannot be read
     */
    private static void buildTreeFromFile(BSTree<TrackedWord> tree, String inputFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        int lineNumber = 0;

        while ((line = reader.readLine()) != null) {
            lineNumber++;

            // Split each line into words using non-alphanumeric characters as delimiters
            String[] words = line.split("[^a-zA-Z0-9']+");

            for (String word : words) {
                if (word == null || word.trim().isEmpty()) {
                    continue;
                }

                // Convert words to lowercase so they are stored consistently
                word = word.toLowerCase();

                TrackedWord tempWord = new TrackedWord(word);
                BSTreeNode<TrackedWord> foundNode = tree.search(tempWord);

                // If the word is not in the tree, add it
                if (foundNode == null) {
                    tempWord.addOccurrence(inputFile, lineNumber);
                    tree.add(tempWord);
                } else {
                    // If the word already exists, update its file/line occurrence list
                    foundNode.getElement().addOccurrence(inputFile, lineNumber);
                }
            }
        }

        reader.close();
    }

    /**
     * Prints the -pf report:
     * all words in alphabetical order with the corresponding list of file names
     * in which they appear.
     * 
     * @param tree the BST storing tracked words
     * @param writer the output destination
     */
    private static void printPFReport(BSTree<TrackedWord> tree, PrintWriter writer) {
        Iterator<TrackedWord> iterator = tree.inorderIterator();

        while (iterator.hasNext()) {
            TrackedWord currentWord = iterator.next();

            writer.print(currentWord.getWord() + ": ");

            boolean firstFile = true;
            for (String fileName : currentWord.getOccurrences().keySet()) {
                if (!firstFile) {
                    writer.print(", ");
                }
                writer.print(fileName);
                firstFile = false;
            }

            writer.println();
        }
    }

    /**
     * Prints the -pl report:
     * all words in alphabetical order with the corresponding file names and line
     * numbers in which they appear.
     * 
     * @param tree the BST storing tracked words
     * @param writer the output destination
     */
    private static void printPLReport(BSTree<TrackedWord> tree, PrintWriter writer) {
        Iterator<TrackedWord> iterator = tree.inorderIterator();

        while (iterator.hasNext()) {
            TrackedWord currentWord = iterator.next();

            writer.print(currentWord.getWord() + ": ");

            boolean firstFile = true;
            for (Map.Entry<String, ArrayList<Integer>> entry : currentWord.getOccurrences().entrySet()) {
                if (!firstFile) {
                    writer.print(" | ");
                }

                writer.print(entry.getKey() + " -> ");

                ArrayList<Integer> lines = entry.getValue();
                for (int i = 0; i < lines.size(); i++) {
                    writer.print(lines.get(i));
                    if (i < lines.size() - 1) {
                        writer.print(", ");
                    }
                }

                firstFile = false;
            }

            writer.println();
        }
    }

    /**
     * Prints the -po report:
     * all words in alphabetical order with the corresponding file names, line
     * numbers, and the total frequency of occurrence of each word.
     * 
     * @param tree the BST storing tracked words
     * @param writer the output destination
     */
    private static void printPOReport(BSTree<TrackedWord> tree, PrintWriter writer) {
        Iterator<TrackedWord> iterator = tree.inorderIterator();

        while (iterator.hasNext()) {
            TrackedWord currentWord = iterator.next();

            writer.print(currentWord.getWord() + ": ");

            boolean firstFile = true;
            for (Map.Entry<String, ArrayList<Integer>> entry : currentWord.getOccurrences().entrySet()) {
                if (!firstFile) {
                    writer.print(" | ");
                }

                writer.print(entry.getKey() + " -> ");

                ArrayList<Integer> lines = entry.getValue();
                for (int i = 0; i < lines.size(); i++) {
                    writer.print(lines.get(i));
                    if (i < lines.size() - 1) {
                        writer.print(", ");
                    }
                }

                firstFile = false;
            }

            writer.print(" | frequency: " + currentWord.getTotalFrequency());
            writer.println();
        }
    }

    /**
     * Saves the current BST of tracked words to a binary file called
     * repository.ser using Java serialization.
     * 
     * Note: This method only saves the repository.
     * Requirement 6, which restores and merges old repository data on each run,
     * is still intentionally not being implemented here.
     * 
     * @param tree the BST storing tracked words
     * @throws IOException if the repository file cannot be written
     */
    private static void saveRepository(BSTree<TrackedWord> tree) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("repository.ser"));
        out.writeObject(tree);
        out.close();
        System.out.println("Repository saved to repository.ser");
    }

    /**
     * Prints the correct command-line usage for the program.
     */
    private static void printUsage() {
        System.out.println("Usage: java -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]");
    }
}