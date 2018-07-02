package com.microsoft.recognizers.text.number.japanese.extractors;

import com.microsoft.recognizers.text.number.Constants;
import com.microsoft.recognizers.text.number.extractors.BaseNumberExtractor;
import com.microsoft.recognizers.text.number.resources.JapaneseNumeric;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class OrdinalExtractor extends BaseNumberExtractor {
    private final Map<Pattern, String> regexes;

    @Override
    protected Map<Pattern, String> getRegexes() {
        return this.regexes;
    }

    @Override
    protected String getExtractType() {
        return Constants.SYS_NUM_ORDINAL;
    }

    public OrdinalExtractor() {
        HashMap<Pattern, String> builder = new HashMap<>();

        //だい一百五十四
        builder.put(Pattern.compile(JapaneseNumeric.OrdinalRegex, Pattern.UNICODE_CHARACTER_CLASS), "OrdinalJpn");

        //だい２５６５
        builder.put(Pattern.compile(JapaneseNumeric.OrdinalNumbersRegex, Pattern.UNICODE_CHARACTER_CLASS), "OrdinalJpn");

        //2折 ２.５折
        builder.put(Pattern.compile(JapaneseNumeric.NumbersFoldsPercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerSpe");

        this.regexes = Collections.unmodifiableMap(builder);
    }
}
