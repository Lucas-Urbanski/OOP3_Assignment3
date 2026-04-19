package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrackedWord implements Comparable<TrackedWord>, Serializable {

    private static final long serialVersionUID = 1L;

    private String word;

    private Map<String, ArrayList<Integer>> occurrences;

    public TrackedWord(String word) {
        this.word = word.toLowerCase();
        this.occurrences = new HashMap<>();
    }

    public String getWord() {
        return word;
    }

    public Map<String, ArrayList<Integer>> getOccurrences() {
        return occurrences;
    }

    public void addOccurrence(String fileName, int lineNumber) {
        if (!occurrences.containsKey(fileName)) {
            occurrences.put(fileName, new ArrayList<Integer>());
        }
        occurrences.get(fileName).add(lineNumber);
    }

    public int getTotalFrequency() {
        int total = 0;
        for (ArrayList<Integer> lines : occurrences.values()) {
            total += lines.size();
        }
        return total;
    }

    @Override
    public int compareTo(TrackedWord other) {
        return this.word.compareTo(other.word);
    }

    @Override
    public String toString() {
        return word;
    }
}