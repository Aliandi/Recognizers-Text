package com.microsoft.recognizers.text.datetime.english.extractors;

import com.microsoft.recognizers.text.IExtractor;
import com.microsoft.recognizers.text.datetime.DateTimeOptions;
import com.microsoft.recognizers.text.datetime.extractors.BaseDateExtractor;
import com.microsoft.recognizers.text.datetime.extractors.BaseDatePeriodExtractor;
import com.microsoft.recognizers.text.datetime.extractors.BaseDateTimeAltExtractor;
import com.microsoft.recognizers.text.datetime.extractors.BaseDateTimeExtractor;
import com.microsoft.recognizers.text.datetime.extractors.BaseDateTimePeriodExtractor;
import com.microsoft.recognizers.text.datetime.extractors.BaseDurationExtractor;
import com.microsoft.recognizers.text.datetime.extractors.BaseHolidayExtractor;
import com.microsoft.recognizers.text.datetime.extractors.BaseSetExtractor;
import com.microsoft.recognizers.text.datetime.extractors.BaseTimeExtractor;
import com.microsoft.recognizers.text.datetime.extractors.BaseTimePeriodExtractor;
import com.microsoft.recognizers.text.datetime.extractors.BaseTimeZoneExtractor;
import com.microsoft.recognizers.text.datetime.extractors.IDateTimeExtractor;
import com.microsoft.recognizers.text.datetime.extractors.IDateTimeListExtractor;
import com.microsoft.recognizers.text.datetime.extractors.IDateTimeZoneExtractor;
import com.microsoft.recognizers.text.datetime.extractors.config.IMergedExtractorConfiguration;
import com.microsoft.recognizers.text.datetime.resources.EnglishDateTime;
import com.microsoft.recognizers.text.matcher.StringMatcher;
import com.microsoft.recognizers.text.number.english.extractors.IntegerExtractor;
import com.microsoft.recognizers.text.utilities.RegExpUtility;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class EnglishMergedExtractorConfiguration implements IMergedExtractorConfiguration {

    public static final Pattern AfterRegex = RegExpUtility.getSafeRegExp(EnglishDateTime.AfterRegex, Pattern.CASE_INSENSITIVE);
    public static final Pattern SinceRegex = RegExpUtility.getSafeRegExp(EnglishDateTime.SinceRegex, Pattern.CASE_INSENSITIVE);
    public static final Pattern BeforeRegex = RegExpUtility.getSafeRegExp(EnglishDateTime.BeforeRegex, Pattern.CASE_INSENSITIVE);
    public static final Pattern FromToRegex = RegExpUtility.getSafeRegExp(EnglishDateTime.FromToRegex, Pattern.CASE_INSENSITIVE);
    public static final Pattern YearAfterRegex = RegExpUtility.getSafeRegExp(EnglishDateTime.YearAfterRegex, Pattern.CASE_INSENSITIVE);
    public static final Pattern NumberEndingPattern = RegExpUtility.getSafeRegExp(EnglishDateTime.NumberEndingPattern, Pattern.CASE_INSENSITIVE);
    public static final Pattern PrepositionSuffixRegex = RegExpUtility.getSafeRegExp(EnglishDateTime.PrepositionSuffixRegex, Pattern.CASE_INSENSITIVE);
    public static final Pattern SingleAmbiguousMonthRegex = RegExpUtility.getSafeRegExp(EnglishDateTime.SingleAmbiguousMonthRegex, Pattern.CASE_INSENSITIVE);
    public static final Pattern UnspecificDatePeriodRegex = RegExpUtility.getSafeRegExp(EnglishDateTime.UnspecificDatePeriodRegex, Pattern.CASE_INSENSITIVE);
    public static final StringMatcher SuperfluousWordMatcher = new StringMatcher();

    private DateTimeOptions options;
    private IDateTimeExtractor setExtractor;
    private IExtractor integerExtractor;
    private IDateTimeExtractor dateExtractor;
    private IDateTimeExtractor timeExtractor;
    private IDateTimeExtractor holidayExtractor;
    private IDateTimeExtractor dateTimeExtractor;
    private IDateTimeExtractor durationExtractor;
    private IDateTimeExtractor datePeriodExtractor;
    private IDateTimeExtractor timePeriodExtractor;
    private IDateTimeZoneExtractor timeZoneExtractor;
    private IDateTimeListExtractor dateTimeAltExtractor;
    private IDateTimeExtractor dateTimePeriodExtractor;

    private static final Iterable<Pattern> filterWordRegexList = new ArrayList<Pattern>() {
        {
            // one on one
            add(RegExpUtility.getSafeRegExp(EnglishDateTime.OneOnOneRegex, Pattern.CASE_INSENSITIVE));

            // (the)? (day|week|month|year)
            add(RegExpUtility.getSafeRegExp(EnglishDateTime.SingleAmbiguousTermsRegex, Pattern.CASE_INSENSITIVE));
        }
    };

    public final Iterable<Pattern> getFilterWordRegexList() {
        return filterWordRegexList;
    }

    public final StringMatcher getSuperfluousWordMatcher() {
        return SuperfluousWordMatcher;
    }

    public final DateTimeOptions getOptions() {
        return options;
    }

    public final IDateTimeExtractor getSetExtractor() {
        return setExtractor;
    }

    public final IExtractor getIntegerExtractor() {
        return integerExtractor;
    }

    public final IDateTimeExtractor getDateExtractor() {
        return dateExtractor;
    }

    public final IDateTimeExtractor getTimeExtractor() {
        return timeExtractor;
    }

    public final IDateTimeExtractor getHolidayExtractor() {
        return holidayExtractor;
    }

    public final IDateTimeExtractor getDateTimeExtractor() {
        return dateTimeExtractor;
    }

    public final IDateTimeExtractor getDurationExtractor() {
        return durationExtractor;
    }

    public final IDateTimeExtractor getDatePeriodExtractor() {
        return datePeriodExtractor;
    }

    public final IDateTimeExtractor getTimePeriodExtractor() {
        return timePeriodExtractor;
    }

    public final IDateTimeZoneExtractor getTimeZoneExtractor() {
        return timeZoneExtractor;
    }

    public final IDateTimeListExtractor getDateTimeAltExtractor() {
        return dateTimeAltExtractor;
    }

    public final IDateTimeExtractor getDateTimePeriodExtractor() {
        return dateTimePeriodExtractor;
    }

    public EnglishMergedExtractorConfiguration(DateTimeOptions options) {

        this.options = options;

        setExtractor = new BaseSetExtractor(new EnglishSetExtractorConfiguration());
        dateExtractor = new BaseDateExtractor(new EnglishDateExtractorConfiguration());
        timeExtractor = new BaseTimeExtractor(new EnglishTimeExtractorConfiguration(options));
        holidayExtractor = new BaseHolidayExtractor(new EnglishHolidayExtractorConfiguration());
        datePeriodExtractor = new BaseDatePeriodExtractor(new EnglishDatePeriodExtractorConfiguration());
        dateTimeExtractor = new BaseDateTimeExtractor(new EnglishDateTimeExtractorConfiguration(options));
        durationExtractor = new BaseDurationExtractor(new EnglishDurationExtractorConfiguration(options));
        timeZoneExtractor = new BaseTimeZoneExtractor(new EnglishTimeZoneExtractorConfiguration(options));
        dateTimeAltExtractor = new BaseDateTimeAltExtractor(new EnglishDateTimeAltExtractorConfiguration());
        timePeriodExtractor = new BaseTimePeriodExtractor(new EnglishTimePeriodExtractorConfiguration(options));
        integerExtractor = IntegerExtractor.getInstance();
        dateTimePeriodExtractor = new BaseDateTimePeriodExtractor(new EnglishDateTimePeriodExtractorConfiguration(options));

        if (this.options != null & this.options != DateTimeOptions.EnablePreview) {
            getSuperfluousWordMatcher().init(EnglishDateTime.SuperfluousWordList);
        }
    }

    public final Pattern getAfterRegex() {
        return AfterRegex;
    }

    public final Pattern getSinceRegex() {
        return SinceRegex;
    }

    public final Pattern getBeforeRegex() {
        return BeforeRegex;
    }

    public final Pattern getFromToRegex() {
        return FromToRegex;
    }

    public final Pattern getYearAfterRegex() {
        return YearAfterRegex;
    }

    public final Pattern getNumberEndingPattern() {
        return NumberEndingPattern;
    }

    public final Pattern getPrepositionSuffixRegex() {
        return PrepositionSuffixRegex;
    }

    public final Pattern getSingleAmbiguousMonthRegex() {
        return SingleAmbiguousMonthRegex;
    }

    public final Pattern getUnspecificDatePeriodRegex() {
        return UnspecificDatePeriodRegex;
    }
}
