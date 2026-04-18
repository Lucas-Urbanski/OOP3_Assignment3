package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * TrackedWord.java
 * 
 * @author Alessandro
 * @version 1.0
 * 
 * Class Description: Represents a word stored in the BST along with all of its
 * occurrences. Each word tracks the file names and line numbers in which it
 * appears, as well as its total frequency.
 */
public class TrackedWord implements Comparable<TrackedWord>, Serializable {

    // Added for Java serialization compatibility
    private static final long serialVersionUID = 1L;

    // The word being tracked
    private String word;

    // A map storing occurrences of the word:
    // Key = file name
    // Value = list of line numbers where the word appears in that file
    private Map<String, ArrayList<Integer>> occurrences;

    /**
     * Constructs a TrackedWord object with the specified word.
     * The word is converted to lowercase for consistency.
     * 
     * @param word the word to be tracked
     */
    public TrackedWord(String word) {
        this.word = word.toLowerCase();
        this.occurrences = new HashMap<>();
    }

    /**
     * Returns the word being tracked.
     * 
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * Returns the map of occurrences for this word.
     * 
     * @return a map of file names to lists of line numbers
     */
    public Map<String, ArrayList<Integer>> getOccurrences() {
        return occurrences;
    }

    /**
     * Adds an occurrence of the word for a given file and line number.
     * If the file does not yet exist in the map, it is added.
     * 
     * @param fileName the name of the file where the word appears
     * @param lineNumber the line number where the word appears
     */
    public void addOccurrence(String fileName, int lineNumber) {
        if (!occurrences.containsKey(fileName)) {
            occurrences.put(fileName, new ArrayList<Integer>());
        }
        occurrences.get(fileName).add(lineNumber);
    }

    /**
     * Calculates the total number of occurrences of the word across all files.
     * 
     * @return the total frequency of the word
     */
    public int getTotalFrequency() {
        int total = 0;
        for (ArrayList<Integer> lines : occurrences.values()) {
            total += lines.size();
        }
        return total;
    }

    /**
     * Compares this word with another TrackedWord object based on alphabetical order.
     * 
     * @param other the other TrackedWord to compare with
     * @return a negative value, zero, or a positive value depending on the comparison
     */
    @Override
    public int compareTo(TrackedWord other) {
        return this.word.compareTo(other.word);
    }

    /**
     * Returns the string representation of the word.
     * 
     * @return the word as a string
     */
    @Override
    public String toString() {
        return word;
    }
}