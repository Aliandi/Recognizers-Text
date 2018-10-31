package com.microsoft.recognizers.text.matcher;

import java.util.HashSet;
import java.util.Set;

public class MatchResult<T> {
    private int start;
    private int length;
    private T text;
    private Set<String> canonicalValues;

    public MatchResult(int start, int lenght, HashSet<String> canonicalValues) {
        this.start = start;
        this.length = lenght;
        this.canonicalValues = canonicalValues;
    }

    public MatchResult(int start, int length) {
        this(start, length, new HashSet<>());
    }

    public MatchResult() {
        this(0, 0);
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public T getText() { return text; }

    public void setText(T text) {
        this.text = text;
    }

    public Set<String> getCanonicalValues() {
        return canonicalValues;
    }

    public void setCanonicalValues(Set<String> canonicalValues) {
        this.canonicalValues = canonicalValues;
    }

    public  int getEnd() { return getStart()+getLength(); }
}
