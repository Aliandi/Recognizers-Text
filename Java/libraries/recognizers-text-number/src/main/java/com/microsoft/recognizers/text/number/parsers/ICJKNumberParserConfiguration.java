package com.microsoft.recognizers.text.number.parsers;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public interface ICJKNumberParserConfiguration extends INumberParserConfiguration {
    // language dictionaries

    Map<Character, Double> getZeroToNineMap();

    Map<Character, Long> getRoundNumberMapChar();

    Map<Character, Character> getFullToHalfMap();

    Map<String, String> getUnitMap();

    Map<Character, Character> getTratoSimMap();

    // language lists
    List<Character> getRoundDirectList();

    // language settings
    Pattern getFracSplitRegex();

    Pattern getDigitNumRegex();

    Pattern getSpeGetNumberRegex();

    Pattern getPercentageRegex();

    Pattern getPointRegex();

    Pattern getDoubleAndRoundRegex();

    Pattern getPairRegex();

    Pattern getDozenRegex();

    Pattern getRoundNumberIntegerRegex();
}
