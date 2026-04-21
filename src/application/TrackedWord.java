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
 * Class Description:
 * Represents a word stored in the BST. Tracks all occurrences of the word,
 * including the file names and line numbers where the word appears.
 */
public class TrackedWord implements Comparable<TrackedWord>, Serializable {

    // Required for serialization
    private static final long serialVersionUID = 1L;

    // The word being tracked
    private String word;

    // Map of occurrences:
    // Key = file name
    // Value = list of line numbers where the word appears
    private Map<String, ArrayList<Integer>> occurrences;

    /**
     * Constructs a TrackedWord object.
     * Converts the word to lowercase for consistency.
     * 
     * @param word the word to be tracked
     */
    public TrackedWord(String word) {
        this.word = word.toLowerCase();
        this.occurrences = new HashMap<>();
    }

    /**
     * Returns the word.
     * 
     * @return the word as a string
     */
    public String getWord() {
        return word;
    }

    /**
     * Returns the occurrences map.
     * 
     * @return map of file names to line number lists
     */
    public Map<String, ArrayList<Integer>> getOccurrences() {
        return occurrences;
    }

    /**
     * Adds an occurrence of the word for a given file and line number.
     * Prevents duplicate line numbers from being added for the same file.
     * 
     * @param fileName the file where the word appears
     * @param lineNumber the line number where the word appears
     */
    public void addOccurrence(String fileName, int lineNumber) {

        // If the file is not already in the map, add it
        if (!occurrences.containsKey(fileName)) {
            occurrences.put(fileName, new ArrayList<Integer>());
        }

        ArrayList<Integer> lines = occurrences.get(fileName);

        // Prevent duplicate entries (important when the same file is processed multiple times)
        if (!lines.contains(lineNumber)) {
            lines.add(lineNumber);
        }
    }

    /**
     * Calculates the total number of occurrences of the word.
     * 
     * @return total frequency of the word
     */
    public int getTotalFrequency() {
        int total = 0;

        for (ArrayList<Integer> lines : occurrences.values()) {
            total += lines.size();
        }

        return total;
    }

    /**
     * Compares this word with another TrackedWord alphabetically.
     * 
     * @param other the other TrackedWord
     * @return negative, zero, or positive depending on order
     */
    @Override
    public int compareTo(TrackedWord other) {
        return this.word.compareTo(other.word);
    }

    /**
     * Returns the word as a string.
     * 
     * @return string representation of the word
     */
    @Override
    public String toString() {
        return word;
    }
}