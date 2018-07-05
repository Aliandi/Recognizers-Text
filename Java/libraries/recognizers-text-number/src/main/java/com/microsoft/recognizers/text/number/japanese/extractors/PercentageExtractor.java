package com.microsoft.recognizers.text.number.japanese.extractors;

import com.microsoft.recognizers.text.number.Constants;
import com.microsoft.recognizers.text.number.extractors.BaseNumberExtractor;
import com.microsoft.recognizers.text.number.resources.JapaneseNumeric;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PercentageExtractor extends BaseNumberExtractor {
    private final Map<Pattern, String> regexes;

    @Override
    protected Map<Pattern, String> getRegexes() {
        return this.regexes;
    }

    @Override
    protected String getExtractType() {
        return Constants.SYS_NUM_PERCENTAGE;
    }

    public PercentageExtractor() {
        HashMap<Pattern, String> builder = new HashMap<>();

        //二十个百分点,  四点五个百分点
        builder.put(Pattern.compile(Pattern.quote(JapaneseNumeric.PercentagePointRegex), Pattern.UNICODE_CHARACTER_CLASS), "PerChs");

        //百分之五十  百分之一点五
        builder.put(Pattern.compile(Pattern.quote(JapaneseNumeric.SimplePercentageRegex), Pattern.UNICODE_CHARACTER_CLASS), "PerChs");

        //百分之５６.２　百分之１２
        builder.put(Pattern.compile(Pattern.quote(JapaneseNumeric.NumbersPercentagePointRegex), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        //百分之3,000  百分之１，１２３
        builder.put(Pattern.compile(JapaneseNumeric.NumbersPercentageWithSeparatorRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        //百分之3.2 k
        builder.put(Pattern.compile(JapaneseNumeric.NumbersPercentageWithMultiplierRegex, Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        //12.56个百分点  ０.４个百分点
        builder.put(Pattern.compile(JapaneseNumeric.FractionPercentagePointRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        //15,123个百分点  １１１，１１１个百分点
        builder.put(Pattern.compile(JapaneseNumeric.FractionPercentageWithSeparatorRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        //12.1k个百分点  １５.1k个百分点
        builder.put(Pattern.compile(JapaneseNumeric.FractionPercentageWithMultiplierRegex, Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        //百分之22  百分之１２０
        builder.put(Pattern.compile(JapaneseNumeric.SimpleNumbersPercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        //百分之15k
        builder.put(Pattern.compile(JapaneseNumeric.SimpleNumbersPercentageWithMultiplierRegex, Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        //百分之1,111  百分之９，９９９
        builder.put(Pattern.compile(JapaneseNumeric.SimpleNumbersPercentagePointRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        //12个百分点
        builder.put(Pattern.compile(JapaneseNumeric.IntegerPercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        //12k个百分点
        builder.put(Pattern.compile(JapaneseNumeric.IntegerPercentageWithMultiplierRegex, Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        //2,123个百分点
        builder.put(Pattern.compile(JapaneseNumeric.NumbersFractionPercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        // @TODO Example missing
        builder.put(Pattern.compile(JapaneseNumeric.SimpleIntegerPercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerNum");

        //2折 ２.５折
        builder.put(Pattern.compile(JapaneseNumeric.NumbersFoldsPercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerSpe");

        //三折 六点五折 七五折
        builder.put(Pattern.compile(JapaneseNumeric.FoldsPercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerSpe");

        //5成 6成半 6成4
        builder.put(Pattern.compile(JapaneseNumeric.SimpleFoldsPercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerSpe");

        //七成半 七成五
        builder.put(Pattern.compile(JapaneseNumeric.SpecialsPercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerSpe");

        //2成 ２.５成
        builder.put(Pattern.compile(JapaneseNumeric.NumbersSpecialsPercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerSpe");

        //三成 六点五成
        builder.put(Pattern.compile(JapaneseNumeric.SimpleSpecialsPercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerSpe");

        // @TODO Example missing
        builder.put(Pattern.compile(JapaneseNumeric.SpecialsFoldsPercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), "PerSpe");

        this.regexes = Collections.unmodifiableMap(builder);
    }
}
