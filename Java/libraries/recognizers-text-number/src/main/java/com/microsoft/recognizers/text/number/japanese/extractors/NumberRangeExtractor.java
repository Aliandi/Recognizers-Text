package com.microsoft.recognizers.text.number.japanese.extractors;

import com.microsoft.recognizers.text.number.Constants;
import com.microsoft.recognizers.text.number.NumberRangeConstants;
import com.microsoft.recognizers.text.number.extractors.BaseNumberRangeExtractor;
import com.microsoft.recognizers.text.number.japanese.parsers.JapaneseNumberParserConfiguration;
import com.microsoft.recognizers.text.number.parsers.BaseCJKNumberParser;
import com.microsoft.recognizers.text.number.resources.JapaneseNumeric;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class NumberRangeExtractor extends BaseNumberRangeExtractor {
    private final Map<Pattern, String> regexes;
    private final Pattern ambiguousFractionConnectorsRegex;

    @Override
    protected Map<Pattern, String> getRegexes() {
        return this.regexes;
    }

    @Override
    protected String getExtractType() {
        return Constants.SYS_NUMRANGE;
    }

    protected Pattern getAmbiguousFractionConnectorsRegex() {
        return this.ambiguousFractionConnectorsRegex;
    }

    public NumberRangeExtractor() {
        super(new NumberExtractor(), new OrdinalExtractor(), new BaseCJKNumberParser(new JapaneseNumberParserConfiguration()));

        HashMap<Pattern, String> builder = new HashMap<>();

        // ...と...の間
        builder.put(Pattern.compile(JapaneseNumeric.TwoNumberRangeRegex1, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), NumberRangeConstants.TWONUMBETWEEN);

        // より大きい...より小さい...
        builder.put(Pattern.compile(JapaneseNumeric.TwoNumberRangeRegex2, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), NumberRangeConstants.TWONUM);

        // より小さい...より大きい...
        builder.put(Pattern.compile(JapaneseNumeric.TwoNumberRangeRegex3, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), NumberRangeConstants.TWONUM);

        // ...と/から..., 20~30
        builder.put(Pattern.compile(JapaneseNumeric.TwoNumberRangeRegex4, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), NumberRangeConstants.TWONUMTILL);

        // 大なり|大きい|高い|大きく...
        builder.put(Pattern.compile(JapaneseNumeric.OneNumberRangeMoreRegex1, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), NumberRangeConstants.MORE);

        // ...より大なり|大きい|高い|大きく
        builder.put(Pattern.compile(JapaneseNumeric.OneNumberRangeMoreRegex2, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), NumberRangeConstants.MORE);

        // ...以上
        builder.put(Pattern.compile(JapaneseNumeric.OneNumberRangeMoreRegex3, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), NumberRangeConstants.MORE);

        // 小なり|小さい|低い...
        builder.put(Pattern.compile(JapaneseNumeric.OneNumberRangeLessRegex1, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), NumberRangeConstants.LESS);

        // ...より小なり|小さい|低い
        builder.put(Pattern.compile(JapaneseNumeric.OneNumberRangeLessRegex2, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), NumberRangeConstants.LESS);

        // ...以下
        builder.put(Pattern.compile(JapaneseNumeric.OneNumberRangeLessRegex3, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), NumberRangeConstants.LESS);

        // イコール...　｜　...等しい|
        builder.put(Pattern.compile(JapaneseNumeric.OneNumberRangeEqualRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS), NumberRangeConstants.EQUAL);

        this.regexes = Collections.unmodifiableMap(builder);

        this.ambiguousFractionConnectorsRegex = Pattern.compile(JapaneseNumeric.AmbiguousFractionConnectorsRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS);
    }
}
