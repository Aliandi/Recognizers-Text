package com.microsoft.recognizers.text.matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

public class StringMatcher {
    private final ITokenizer tokenizer;
    private final IMatcher<String> matcher;

    public StringMatcher(MatchStrategy strategy, ITokenizer tokenizer) {
        this.tokenizer = tokenizer;
        switch (strategy) {
            case AcAutomaton:
                matcher = new AcAutomation<>();
                break;
            case TrieTree:
                matcher = new TrieTree<>();
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public StringMatcher(MatchStrategy strategy) {
        this(strategy, null);
    }

    public StringMatcher(ITokenizer tokenizer) {
        this(MatchStrategy.TrieTree, tokenizer);
    }

    public StringMatcher() {
        this(MatchStrategy.TrieTree, null);
    }

    void init(Iterable<String> values) {
        init(values, (String[]) StreamSupport.stream(values.spliterator(), false).toArray());
    }

    void init(Iterable<String> values, String[] ids) {
        Iterable<String>[] tokenizedValues = getTokenizedText(values);
        init(tokenizedValues, ids);
    }

    void init(Map<String, List<String>> valuesMap) {
        ArrayList<String> values = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();

        for (Map.Entry<String, List<String>> item : valuesMap.entrySet()) {
            String id = item.getKey();
            for (String value : item.getValue()) {
                values.add(value);
                ids.add(id);
            }
        }

        Iterable<String>[] tokenizedValues = getTokenizedText(values);
        init(tokenizedValues, (String[]) ids.toArray());
    }

    void init(Iterable<String>[] tokenizedValues, String[] ids) {
        matcher.init(tokenizedValues, ids);
    }

    private Iterable<String>[] getTokenizedText(Iterable<String> values) {
        return (Iterable<String>[]) StreamSupport.stream(values.spliterator(), false).map(t -> tokenizer.tokenize(t).stream().map(i -> i.text)).toArray();
    }

    public Iterable<MatchResult<String>> find(Iterable<String> tokenizedQuery) {
        return matcher.find(tokenizedQuery);
    }

    public Iterable<MatchResult<String>> find(String queryText) {
        List<Token> queryTokens = tokenizer.tokenize(queryText);
        Iterable<String> tokenizedQueryText = (Iterable<String>) StreamSupport.stream(queryTokens.spliterator(), false).map(t -> t.text);

        List<MatchResult<String>> ret = new ArrayList<MatchResult<String>>();

        for (MatchResult<String> r : find(tokenizedQueryText)) {
            Token startToken = queryTokens.get(r.getStart());
            Token endToken = queryTokens.get(r.getStart() + r.getLength() - 1);
            int start = startToken.getStart();
            int length = endToken.getEnd() - startToken.getStart();
            String rtext = queryText.substring(start, length);

            ret.add(new MatchResult<String>(start, length, r.getCanonicalValues(), rtext));
        }

        return ret;
    }
}