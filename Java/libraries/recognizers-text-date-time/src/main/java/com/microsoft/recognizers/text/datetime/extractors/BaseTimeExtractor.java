package com.microsoft.recognizers.text.datetime.extractors;

import com.microsoft.recognizers.text.ExtractResult;
import com.microsoft.recognizers.text.datetime.Constants;
import com.microsoft.recognizers.text.datetime.DateTimeOptions;
import com.microsoft.recognizers.text.datetime.extractors.config.ITimeExtractorConfiguration;
import com.microsoft.recognizers.text.datetime.utilities.Token;
import com.microsoft.recognizers.text.utilities.Match;
import com.microsoft.recognizers.text.utilities.RegExpUtility;
import com.microsoft.recognizers.text.datetime.resources.BaseDateTime;
import com.microsoft.recognizers.text.utilities.StringUtility;

import org.javatuples.Pair;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BaseTimeExtractor implements IDateTimeExtractor {

    private final ITimeExtractorConfiguration config;

    public final Pattern getHouRegex() {
        return RegExpUtility.getSafeRegExp(BaseDateTime.HourRegex);
    }

    public final Pattern getMinuteRegex() {
        return RegExpUtility.getSafeRegExp(BaseDateTime.MinuteRegex);
    }

    public final Pattern getSecondRegex() {
        return RegExpUtility.getSafeRegExp(BaseDateTime.SecondRegex);
    }

    @Override
    public String getExtractorName() {
        return Constants.SYS_DATETIME_TIME;
    }

    public BaseTimeExtractor(ITimeExtractorConfiguration config) {
        this.config = config;
    }

    @Override
    public List<ExtractResult> extract(String input) {
        return this.extract(input, LocalDateTime.now());
    }

    @Override
    public List<ExtractResult> extract(String input, LocalDateTime reference) {
        List<Token> tokens = new ArrayList<>();

        tokens.addAll(basicRegexMatch(input));
        tokens.addAll(atRegexMatch(input));
        tokens.addAll(beforeAfterRegexMatch(input));
        tokens.addAll(specialCasesRegexMatch(input, reference));

        List<ExtractResult> timeErs = Token.mergeAllTokens(tokens, input, getExtractorName());

        if (config.getOptions().match(DateTimeOptions.EnablePreview)) {
            timeErs = mergeTimeZones(timeErs, config.getTimeZoneExtractor().extract(input, reference), input);
        }

        return timeErs;
    }

    private List<ExtractResult> mergeTimeZones(List<ExtractResult> timeErs, List<ExtractResult> timeZoneErs, String input) {
        for (ExtractResult er : timeErs) {
            for (ExtractResult timeZoneEr : timeZoneErs) {
                Integer begin = er.start + er.length;
                Integer end = timeZoneEr.start;

                if (begin < end) {
                    String gapText = input.substring(begin, (end - begin));

                    if (StringUtility.isNullOrWhiteSpace(gapText)) {
                        Integer newLength = timeZoneEr.start + timeZoneEr.length - er.start;

                        er.withText(input.substring(er.start, newLength))
                                .withLength(newLength)
                                .withData(new Pair<String, ExtractResult>(Constants.SYS_DATETIME_TIMEZONE, timeZoneEr));
                    }
                }
            }
        }

        return timeErs;
    }

    private List<Token> basicRegexMatch(String input) {
        List<Token> ret = new ArrayList<>();
        for (Pattern regex : this.config.getTimeRegexList()) {
            Match[] matches = RegExpUtility.getMatches(regex, input);
            for (Match match : matches) {
                ret.add(new Token(match.index, match.index + match.length));
            }
        }

        return ret;
    }

    private List<Token> atRegexMatch(String input) {
        List<Token> ret = new ArrayList<>();

        // handle "at 5", "at seven"
        Match[] matches = RegExpUtility.getMatches(this.config.getAtRegex(), input);
        for (Match match : matches) {
            if (match.index + match.length < input.length() &&
                    input.charAt(match.index + match.length) == '%') {
                continue;
            }
            ret.add(new Token(match.index, match.index + match.length));
        }

        return ret;
    }

    private List<Token> beforeAfterRegexMatch(String input) {
        List<Token> ret = new ArrayList<>();

        // only enabled in CalendarMode
        if (config.getOptions().match(DateTimeOptions.CalendarMode)) {
            // handle "before 3", "after three"
            Match[] matches = RegExpUtility.getMatches(this.config.getTimeBeforeAfterRegex(), input);
            for (Match match : matches) {
                ret.add(new Token(match.index, match.index + match.length));
            }
        }

        return ret;
    }

    private List<Token> specialCasesRegexMatch(String input, LocalDateTime reference) {
        List<Token> ret = new ArrayList<>();

        // handle "ish"
        if (this.config.getIshRegex() != null) {
            Match[] matches = RegExpUtility.getMatches(this.config.getIshRegex(), input);
            for (Match match : matches) {
                ret.add(new Token(match.index, match.index + match.length));
            }
        }

        return ret;
    }
}