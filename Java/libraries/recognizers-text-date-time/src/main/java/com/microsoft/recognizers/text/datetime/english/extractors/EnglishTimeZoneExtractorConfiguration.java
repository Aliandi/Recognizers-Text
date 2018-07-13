package com.microsoft.recognizers.text.datetime.english.extractors;

import com.google.common.collect.ImmutableMap;
import com.microsoft.recognizers.text.datetime.DateTimeOptions;
import com.microsoft.recognizers.text.datetime.config.BaseOptionsConfiguration;
import com.microsoft.recognizers.text.datetime.extractors.config.ITimeZoneExtractorConfiguration;
import com.microsoft.recognizers.text.datetime.resources.EnglishDateTime;
import com.microsoft.recognizers.text.matcher.StringMatcher;
import com.microsoft.recognizers.text.number.english.extractors.CardinalExtractor;
import com.microsoft.recognizers.text.utilities.FormatUtility;

import java.util.List;
import java.util.regex.Pattern;

public class EnglishTimeZoneExtractorConfiguration extends BaseOptionsConfiguration implements ITimeZoneExtractorConfiguration {
    public EnglishTimeZoneExtractorConfiguration() {
        this(DateTimeOptions.None);
    }

    public EnglishTimeZoneExtractorConfiguration(DateTimeOptions options) {
        super(options);
        if (options.match(DateTimeOptions.EnablePreview)) {
            // TODO: Implement TimeZoneDefinitions class
            // getCityMatcher().init(TimeZoneDefinitions.getMajorLocations().map(
            //        o -> FormatUtility.removeDiacritics(o.toLowerCase())));
        }

    }

    @Override
    public Iterable<Pattern> getTimeZoneRegexes() {
        return null;
    }

    @Override
    public Pattern getLocationTimeSuffixRegex() {
        return null;
    }

    @Override
    public StringMatcher getCityMatcher() {
        return null;
    }

    @Override
    public List<String> getAmbiguousTimezoneList() {
        return null;
    }
}
