package com.microsoft.recognizers.text.number.japanese.parsers;

import com.microsoft.recognizers.text.Culture;
import com.microsoft.recognizers.text.CultureInfo;
import com.microsoft.recognizers.text.ParseResult;
import com.microsoft.recognizers.text.number.NumberOptions;
import com.microsoft.recognizers.text.number.parsers.BaseCJKNumberParserConfiguration;
import com.microsoft.recognizers.text.number.resources.JapaneseNumeric;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class JapaneseNumberParserConfiguration extends BaseCJKNumberParserConfiguration {

    public JapaneseNumberParserConfiguration() {
        super(
                JapaneseNumeric.LangMarker,
                new CultureInfo(Culture.Japanese),
                NumberOptions.None,
                JapaneseNumeric.NonDecimalSeparatorChar,
                JapaneseNumeric.DecimalSeparatorChar,
                JapaneseNumeric.FractionMarkerToken,
                JapaneseNumeric.HalfADozenText,
                JapaneseNumeric.WordSeparatorToken,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyMap(),
                Collections.emptyMap(),
                JapaneseNumeric.RoundNumberMap,
                null,
                Pattern.compile(JapaneseNumeric.DigitalNumberRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS),
                Pattern.compile(JapaneseNumeric.NegativeNumberSignRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS),
                null,
                JapaneseNumeric.ZeroToNineMap,
                JapaneseNumeric.RoundNumberMapChar,
                JapaneseNumeric.FullToHalfMap,
                JapaneseNumeric.UnitMap,
                null,
                JapaneseNumeric.RoundDirectList,
                Pattern.compile(JapaneseNumeric.FracSplitRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS),
                Pattern.compile(JapaneseNumeric.DigitNumRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS),
                Pattern.compile(JapaneseNumeric.SpeGetNumberRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS),
                Pattern.compile(JapaneseNumeric.PercentageRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS),
                Pattern.compile(JapaneseNumeric.PointRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS),
                Pattern.compile(JapaneseNumeric.DoubleAndRoundRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS),
                Pattern.compile(JapaneseNumeric.PairRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS),
                Pattern.compile(JapaneseNumeric.DozenRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS),
                Pattern.compile(JapaneseNumeric.RoundNumberIntegerRegex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CHARACTER_CLASS)
        );
    }


    @Override
    public List<String> normalizeTokenSet(List<String> tokens, ParseResult context) {
        return tokens;
    }

    @Override
    public long resolveCompositeNumber(String numberStr) {
        return 0;
    }
}
