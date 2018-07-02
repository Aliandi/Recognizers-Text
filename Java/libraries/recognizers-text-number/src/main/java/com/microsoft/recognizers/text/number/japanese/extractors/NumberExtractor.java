package com.microsoft.recognizers.text.number.japanese.extractors;

import com.microsoft.recognizers.text.number.Constants;
import com.microsoft.recognizers.text.number.extractors.BaseNumberExtractor;
import com.microsoft.recognizers.text.number.japanese.JapaneseNumberExtractorMode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class NumberExtractor extends BaseNumberExtractor {
    private final Map<Pattern, String> regexes;

    @Override
    protected Map<Pattern, String> getRegexes() {
        return this.regexes;
    }

    @Override
    protected String getExtractType() {
        return Constants.SYS_NUM;
    }

    public NumberExtractor() {
        this(JapaneseNumberExtractorMode.Default);
    }


    public NumberExtractor(JapaneseNumberExtractorMode mode) {
        HashMap<Pattern, String> builder = new HashMap<>();

        // Add Cardinal
        CardinalExtractor cardExtract = new CardinalExtractor(mode);
        builder.putAll(cardExtract.getRegexes());

        // Add Fraction
        FractionExtractor fracExtractor = new FractionExtractor();
        builder.putAll(fracExtractor.getRegexes());

        this.regexes = Collections.unmodifiableMap(builder);
    }
}
