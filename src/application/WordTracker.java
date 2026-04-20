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

public class WordTracker {

    public static void main(String[] args) {
        if (args.length < 2 || args.length > 3) {
            printUsage();
            return;
        }

        String inputFile = args[0];
        String option = args[1];
        String outputFile = null;

        if (!option.equals("-pf") && !option.equals("-pl") && !option.equals("-po")) {
            System.out.println("Invalid report option.");
            printUsage();
            return;
        }

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

        BSTree<TrackedWord> tree = new BSTree<>();
        
        try {
            buildTreeFromFile(tree, inputFile);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        PrintWriter writer = null;

        try {
            
            if (outputFile != null) {
                writer = new PrintWriter(new FileWriter(outputFile));
            } else {
                writer = new PrintWriter(System.out, true);
            }
          
            if (option.equals("-pf")) {
                printPFReport(tree, writer);
            } else if (option.equals("-pl")) {
                printPLReport(tree, writer);
            } else if (option.equals("-po")) {
                printPOReport(tree, writer);
            }

            writer.flush();

            if (outputFile != null) {
                writer.close();
                System.out.println("Report written to: " + outputFile);
            }

            saveRepository(tree);

        } catch (IOException e) {
            System.out.println("Error writing output: " + e.getMessage());
        }
    }

    private static void buildTreeFromFile(BSTree<TrackedWord> tree, String inputFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        int lineNumber = 0;

        while ((line = reader.readLine()) != null) {
            lineNumber++;

            String[] words = line.split("[^a-zA-Z0-9']+");

            for (String word : words) {
                if (word == null || word.trim().isEmpty()) {
                    continue;
                }

                word = word.toLowerCase();

                TrackedWord tempWord = new TrackedWord(word);
                BSTreeNode<TrackedWord> foundNode = tree.search(tempWord);

                if (foundNode == null) {
                    tempWord.addOccurrence(inputFile, lineNumber);
                    tree.add(tempWord);
                } else {
                    foundNode.getElement().addOccurrence(inputFile, lineNumber);
                }
            }
        }

        reader.close();
    }

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

    private static void saveRepository(BSTree<TrackedWord> tree) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("repository.ser"));
        out.writeObject(tree);
        out.close();
        System.out.println("Repository saved to repository.ser");
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]");
    }
}