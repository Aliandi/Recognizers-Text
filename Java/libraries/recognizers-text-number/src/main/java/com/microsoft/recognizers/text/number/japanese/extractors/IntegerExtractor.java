package com.microsoft.recognizers.text.number.japanese.extractors;

import com.microsoft.recognizers.text.number.Constants;
import com.microsoft.recognizers.text.number.extractors.BaseNumberExtractor;
import com.microsoft.recognizers.text.number.japanese.JapaneseNumberExtractorMode;
import com.microsoft.recognizers.text.number.resources.JapaneseNumeric;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.microsoft.recognizers.text.number.japanese.JapaneseNumberExtractorMode.*;

public class IntegerExtractor extends BaseNumberExtractor {

    private final Map<Pattern, String> regexes;

    @Override
    protected Map<Pattern, String> getRegexes() {
        return this.regexes;
    }

    @Override
    protected String getExtractType() {
        return Constants.SYS_NUM_INTEGER;
    }

    public IntegerExtractor() {
        this(Default);
    }

    public IntegerExtractor(JapaneseNumberExtractorMode mode) {
        HashMap<Pattern, String> builder = new HashMap<>();

        // 123456,  －１２３４５６
        builder.put(Pattern.compile(JapaneseNumeric.NumbersSpecialsChars, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "IntegerNum");

        //15k,  16 G
        builder.put(Pattern.compile(JapaneseNumeric.NumbersSpecialsCharsWithSuffix, Pattern.UNICODE_CHARACTER_CLASS), "IntegerNum");

        //1,234,  ２，３３２，１１１
        builder.put(Pattern.compile(JapaneseNumeric.DottedNumbersSpecialsChar, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "IntegerNum");

        //半百  半ダース
        builder.put(Pattern.compile(JapaneseNumeric.NumbersWithHalfDozen, Pattern.UNICODE_CHARACTER_CLASS), "IntegerJpn");

        //一ダース  五十ダース
        builder.put(Pattern.compile(JapaneseNumeric.NumbersWithDozen, Pattern.UNICODE_CHARACTER_CLASS), "IntegerJpn");

        switch (mode)
        {
            case Default:
                // 一百五十五, 负一亿三百二十二.
                // Uses an allow list to avoid extracting "西九条" from "九"
                builder.put(Pattern.compile(Pattern.quote(JapaneseNumeric.NumbersWithAllowListRegex), Pattern.UNICODE_CHARACTER_CLASS), "IntegerJpn");
                break;

            case ExtractAll:
                // 一百五十五, 负一亿三百二十二, "西九条" from "九"
                // Uses no allow lists and extracts all potential integers (useful in Units, for example).
                builder.put(Pattern.compile(JapaneseNumeric.NumbersAggressiveRegex, Pattern.UNICODE_CHARACTER_CLASS), "IntegerJpn");
                break;
        }

        this.regexes = Collections.unmodifiableMap(builder);
    }
}
