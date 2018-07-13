package com.microsoft.recognizers.text.datetime.english.extractors;

import com.microsoft.recognizers.text.datetime.resources.EnglishDateTime;
import com.microsoft.recognizers.text.datetime.config.BaseOptionsConfiguration;
import com.microsoft.recognizers.text.datetime.extractors.IDateTimeExtractor;
import com.microsoft.recognizers.text.datetime.extractors.config.ITimeExtractorConfiguration;
import com.microsoft.recognizers.text.datetime.DateTimeOptions;
import com.microsoft.recognizers.text.utilities.RegExpUtility;
import com.microsoft.recognizers.text.datetime.extractors.BaseTimeZoneExtractor;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnglishTimeExtractorConfiguration extends BaseOptionsConfiguration implements ITimeExtractorConfiguration {

    private final IDateTimeExtractor timeZoneExtractor;
    private final List<Pattern> timeRegexList;
    private final Pattern atRegex = RegExpUtility.getSafeRegExp(EnglishDateTime.AtRegex);
    private final Pattern ishRegex = RegExpUtility.getSafeRegExp(EnglishDateTime.IshRegex);
    private final Pattern timeBeforeAfterRegex = RegExpUtility.getSafeRegExp(EnglishDateTime.TimeBeforeAfterRegex);

    public EnglishTimeExtractorConfiguration() {
        this(DateTimeOptions.None);
    }

    public EnglishTimeExtractorConfiguration(DateTimeOptions options) {
        super(options);
        timeZoneExtractor = new BaseTimeZoneExtractor(new EnglishTimeZoneExtractorConfiguration(getOptions()));
        timeRegexList = new ArrayList<>(
                Arrays.asList(
                        // (three min past)? seven|7|(senven thirty) pm
                        RegExpUtility.getSafeRegExp(EnglishDateTime.TimeRegex1),

                        // (three min past)? 3:00(:00)? (pm)?
                        RegExpUtility.getSafeRegExp(EnglishDateTime.TimeRegex2),

                        // (three min past)? 3.00 (pm)
                        RegExpUtility.getSafeRegExp(EnglishDateTime.TimeRegex3),

                        // (three min past) (five thirty|seven|7|7:00(:00)?) (pm)? (in the night)
                        RegExpUtility.getSafeRegExp(EnglishDateTime.TimeRegex4),

                        // (three min past) (five thirty|seven|7|7:00(:00)?) (pm)?
                        RegExpUtility.getSafeRegExp(EnglishDateTime.TimeRegex5),

                        // (five thirty|seven|7|7:00(:00)?) (pm)? (in the night)
                        RegExpUtility.getSafeRegExp(EnglishDateTime.TimeRegex6),

                        // (in the night) at (five thirty|seven|7|7:00(:00)?) (pm)?
                        RegExpUtility.getSafeRegExp(EnglishDateTime.TimeRegex7),

                        // (in the night) (five thirty|seven|7|7:00(:00)?) (pm)?
                        RegExpUtility.getSafeRegExp(EnglishDateTime.TimeRegex8),

                        RegExpUtility.getSafeRegExp(EnglishDateTime.TimeRegex9),

                        // (three min past)? 3h00 (pm)?
                        RegExpUtility.getSafeRegExp(EnglishDateTime.TimeRegex10),

                        // 340pm
                        RegExpUtility.getSafeRegExp(EnglishDateTime.ConnectNumRegex)
                ));
    }

    @Override
    public IDateTimeExtractor getTimeZoneExtractor() { return timeZoneExtractor; }

    @Override
    public Iterable<Pattern> getTimeRegexList() { return timeRegexList; }

    @Override
    public Pattern getAtRegex() { return atRegex; }

    @Override
    public Pattern getIshRegex() { return ishRegex; }

    @Override
    public Pattern getTimeBeforeAfterRegex() { return timeBeforeAfterRegex; }
}