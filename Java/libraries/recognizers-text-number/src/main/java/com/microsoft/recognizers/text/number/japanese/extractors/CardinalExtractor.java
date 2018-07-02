package com.microsoft.recognizers.text.number.japanese.extractors;

import com.microsoft.recognizers.text.number.Constants;
import com.microsoft.recognizers.text.number.extractors.BaseNumberExtractor;
import com.microsoft.recognizers.text.number.japanese.JapaneseNumberExtractorMode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CardinalExtractor extends BaseNumberExtractor {
    private final Map<Pattern, String> regexes;

    @Override
    protected Map<Pattern, String> getRegexes() {
        return this.regexes;
    }

    @Override
    protected String getExtractType() {
        return Constants.SYS_NUM_CARDINAL;
    }

    public CardinalExtractor() {
        this(JapaneseNumberExtractorMode.Default);
    }

    public CardinalExtractor(JapaneseNumberExtractorMode mode) {
        HashMap<Pattern, String> builder = new HashMap<>();

        IntegerExtractor intExtractor = new IntegerExtractor(mode);
        builder.putAll(intExtractor.getRegexes());

        DoubleExtractor doubleExtractor = new DoubleExtractor();
        builder.putAll(doubleExtractor.getRegexes());

        this.regexes = Collections.unmodifiableMap(builder);
    }
}
