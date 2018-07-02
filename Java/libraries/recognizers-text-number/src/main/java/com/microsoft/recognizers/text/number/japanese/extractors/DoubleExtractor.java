package com.microsoft.recognizers.text.number.japanese.extractors;

import com.microsoft.recognizers.text.number.Constants;
import com.microsoft.recognizers.text.number.extractors.BaseNumberExtractor;
import com.microsoft.recognizers.text.number.resources.JapaneseNumeric;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class DoubleExtractor extends BaseNumberExtractor {

    private final Map<Pattern, String> regexes;

    @Override
    protected Map<Pattern, String> getRegexes() {
        return this.regexes;
    }

    @Override
    protected String getExtractType() {
        return Constants.SYS_NUM_DOUBLE;
    }

    public DoubleExtractor() {
        HashMap<Pattern, String> builder = new HashMap<>();

        builder.put(Pattern.compile(JapaneseNumeric.DoubleSpecialsChars, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "DoubleNum");

        // (-)2.5, can avoid cases like ip address xx.xx.xx.xx
        builder.put(Pattern.compile(JapaneseNumeric.DoubleSpecialsCharsWithNegatives, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "DoubleNum");

        //(-).2
        builder.put(Pattern.compile(JapaneseNumeric.SimpleDoubleSpecialsChars, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "DoubleNum");

        // 1.0 K
        builder.put(Pattern.compile(JapaneseNumeric.DoubleWithMultiplierRegex, Pattern.UNICODE_CHARACTER_CLASS), "DoubleNum");

        //１５.２万
        builder.put(Pattern.compile(JapaneseNumeric.DoubleWithThousandsRegex, Pattern.UNICODE_CHARACTER_CLASS), "DoubleJpn");

        // 2e6, 21.2e0
        builder.put(Pattern.compile(JapaneseNumeric.DoubleExponentialNotationRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "DoublePow");

        //2^5
        builder.put(Pattern.compile(JapaneseNumeric.DoubleScientificNotationRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "DoublePow");

        this.regexes = Collections.unmodifiableMap(builder);
    }
}
