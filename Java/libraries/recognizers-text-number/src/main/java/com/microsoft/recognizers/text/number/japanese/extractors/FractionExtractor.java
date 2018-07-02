package com.microsoft.recognizers.text.number.japanese.extractors;

import com.microsoft.recognizers.text.number.Constants;
import com.microsoft.recognizers.text.number.extractors.BaseNumberExtractor;
import com.microsoft.recognizers.text.number.resources.JapaneseNumeric;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FractionExtractor extends BaseNumberExtractor {

    private final Map<Pattern, String> regexes;

    @Override
    protected Map<Pattern, String> getRegexes() {
        return this.regexes;
    }

    @Override
    protected String getExtractType() {
        return Constants.SYS_NUM_FRACTION;
    }

    public FractionExtractor() {
        HashMap<Pattern, String> builder = new HashMap<>();

        // -4 5/2,       ４ ６／３
        builder.put(Pattern.compile(JapaneseNumeric.FractionNotationSpecialsCharsRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "FracNum");

        // 8/3
        builder.put(Pattern.compile(JapaneseNumeric.FractionNotationRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "FracNum");

        //五分の二   七分の三
        builder.put(Pattern.compile(JapaneseNumeric.AllFractionNumber, Pattern.UNICODE_CHARACTER_CLASS), "FracJpn");

        this.regexes = Collections.unmodifiableMap(builder);
    }
}
