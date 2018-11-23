package com.microsoft.recognizers.text.datetime.parsers;

import com.microsoft.recognizers.text.ExtractResult;
import com.microsoft.recognizers.text.ParseResult;
import com.microsoft.recognizers.text.datetime.Constants;
import com.microsoft.recognizers.text.datetime.DateTimeOptions;
import com.microsoft.recognizers.text.datetime.parsers.config.IMergedParserConfiguration;
import com.microsoft.recognizers.text.datetime.utilities.DateTimeResolutionResult;
import com.microsoft.recognizers.text.datetime.utilities.FormatUtil;
import com.microsoft.recognizers.text.datetime.utilities.MatchingUtil;
import com.microsoft.recognizers.text.utilities.Match;
import com.microsoft.recognizers.text.utilities.RegExpUtility;
import com.microsoft.recognizers.text.utilities.StringUtility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BaseMergedParser implements IDateTimeParser {

    private final String parserName = "datetimeV2";
    private final IMergedParserConfiguration config;
    private static final String dateMinString = FormatUtil.formatDate(LocalDateTime.MIN);
    private static final String dateTimeMinString = FormatUtil.formatDateTime(LocalDateTime.MIN);
    //private static final Calendar Cal = DateTimeFormatInfo.InvariantInfo.Calendar;

    public BaseMergedParser(IMergedParserConfiguration config) {
        this.config = config;
    }

    public String getDateMinString() {
        return dateMinString;
    }

    public String getDateTimeMinString() {
        return dateTimeMinString;
    }

    @Override
    public String getParserName() {
        return parserName;
    }

    @Override
    public ParseResult parse(ExtractResult extractResult) {
        return this.parse(extractResult, LocalDateTime.now());
    }

    @Override
    public DateTimeParseResult parse(ExtractResult er, LocalDateTime reference) {

        DateTimeParseResult pr = null;

        String originText = er.text;
        if (this.config.getOptions().match(DateTimeOptions.EnablePreview)) {

            er = er.withText(MatchingUtil.preProcessTextRemoveSuperfluousWords(er.text, config.getSuperfluousWordMatcher()).text)
                .withLength(er.length + er.text.length() - originText.length());
        }

        // Push, save the MOD string
        boolean hasBefore = false, hasAfter = false, hasSince = false, hasYearAfter = false;

        // "InclusiveModifier" means MOD should include the start/end time
        // For example, cases like "on or later than", "earlier than or in" have inclusive modifier
        boolean hasInclusiveModifier = false;
        String modStr = "";
        Optional<Match> beforeMatch = Arrays.stream(RegExpUtility.getMatches(config.getBeforeRegex(), er.text)).findFirst();
        Optional<Match> afterMatch = Arrays.stream(RegExpUtility.getMatches(config.getAfterRegex(), er.text)).findFirst();
        Optional<Match> sinceMatch = Arrays.stream(RegExpUtility.getMatches(config.getSinceRegex(), er.text)).findFirst();

        if (beforeMatch.isPresent() && beforeMatch.get().index == 0) {
            hasBefore = true;
            er = er.withStart(er.start + beforeMatch.get().length)
                .withLength(er.length - beforeMatch.get().length)
                .withText(er.text.substring(beforeMatch.get().length));
            modStr = beforeMatch.get().value;

            if (!StringUtility.isNullOrEmpty(beforeMatch.get().getGroup("include").value)) {
                hasInclusiveModifier = true;
            }
        } else if (afterMatch.isPresent() && afterMatch.get().index == 0) {
            hasAfter = true;
            er = er.withStart(er.start + afterMatch.get().length)
                .withLength(er.length - afterMatch.get().length)
                .withText(er.text.substring(afterMatch.get().length));
            modStr = afterMatch.get().value;

            if (!StringUtility.isNullOrEmpty(afterMatch.get().getGroup("include").value)) {
                hasInclusiveModifier = true;
            }
        } else if (sinceMatch.isPresent() && sinceMatch.get().index == 0) {
            hasSince = true;
            er = er.withStart(er.start + sinceMatch.get().length)
                .withLength(er.length - sinceMatch.get().length)
                .withText(er.text.substring(sinceMatch.get().length));
            modStr = sinceMatch.get().value;
        } else if (er.type.equals(Constants.SYS_DATETIME_DATEPERIOD) && Arrays.stream(RegExpUtility.getMatches(config.getYearRegex(), er.text)).findFirst().isPresent()) {
            // This has to be put at the end of the if, or cases like "before 2012" and "after 2012" would fall into this
            // 2012 or after/above
            Optional<Match> match = Arrays.stream(RegExpUtility.getMatches(config.getYearAfterRegex(),er.text)).findFirst();
            if (match.isPresent() && er.text.endsWith(match.get().value)) {
                hasYearAfter = true;
                er = er.withLength(er.length - match.get().length)
                    .withText(er.length >0 ? er.text.substring(0, er.length) : "");
                modStr = match.get().value;
            }
        }

        if (er.type.equals(Constants.SYS_DATETIME_DATE)) {
            pr = this.config.getDateParser().parse(er, reference);
            if (pr.value == null)
            {
                pr = config.getHolidayParser().parse(er, reference);
            }
        } else if (er.type.equals(Constants.SYS_DATETIME_TIME)) {
            pr = this.config.getTimeParser().parse(er, reference);
        } else if (er.type.equals(Constants.SYS_DATETIME_DATETIME)) {
            pr = this.config.getDateTimeParser().parse(er, reference);
        } else if (er.type.equals(Constants.SYS_DATETIME_DATEPERIOD)) {
            pr = this.config.getDatePeriodParser().parse(er, reference);
        } else if (er.type.equals(Constants.SYS_DATETIME_TIMEPERIOD)) {
            pr = this.config.getTimePeriodParser().parse(er, reference);
        } else if (er.type.equals(Constants.SYS_DATETIME_DATETIMEPERIOD)) {
            pr = this.config.getDateTimePeriodParser().parse(er, reference);
        } else if (er.type.equals(Constants.SYS_DATETIME_DURATION)) {
            pr = this.config.getDurationParser().parse(er, reference);
        } else if (er.type.equals(Constants.SYS_DATETIME_SET)) {
            pr = this.config.getGetParser().parse(er, reference);
        } else if (er.type.equals(Constants.SYS_DATETIME_DATETIMEALT)) {
            pr = this.config.getDateTimeAltParser().parse(er, reference);
        } else if (er.type.equals(Constants.SYS_DATETIME_TIMEZONE)) {
            if (config.getOptions().match(DateTimeOptions.EnablePreview)){
                pr = this.config.getTimeZoneParser().parse(er, reference);
            }
        } else {
            return null;
        }

        // Pop, restore the MOD string
        if (hasBefore && pr != null && pr.value != null) {
            pr = (DateTimeParseResult) pr.withLength(pr.length + modStr.length())
                .withStart(pr.start - modStr.length())
                .withText(modStr + pr.text);
            DateTimeResolutionResult val = (DateTimeResolutionResult) pr.value;

            if (!hasInclusiveModifier) {
                val.setMod(Constants.BEFORE_MOD);
            } else {
                val.setMod(Constants.UNTIL_MOD);
            }

            pr = (DateTimeParseResult) pr.withValue(val);
        }

        if (hasAfter && pr != null && pr.value != null) {
            pr = (DateTimeParseResult) pr.withLength(pr.length + modStr.length())
                .withStart(pr.start - modStr.length())
                .withText(modStr + pr.text);
            DateTimeResolutionResult val = (DateTimeResolutionResult) pr.value;

            if (!hasInclusiveModifier) {
                val.setMod(Constants.AFTER_MOD);
            } else {
                val.setMod(Constants.SINCE_MOD);
            }

            pr = (DateTimeParseResult) pr.withValue(val);
        }

        if (hasSince && pr != null && pr.value != null) {
            pr = (DateTimeParseResult) pr.withLength(pr.length + modStr.length())
                .withStart(pr.start - modStr.length())
                .withText(modStr + pr.text);
            DateTimeResolutionResult val = (DateTimeResolutionResult)pr.value;
            val.setMod( Constants.SINCE_MOD);
            pr = (DateTimeParseResult) pr.withValue(val);
        }

        if (hasYearAfter && pr != null && pr.value != null) {
            pr = (DateTimeParseResult) pr.withLength(pr.length + modStr.length())
                .withText(pr.text + modStr);
            DateTimeResolutionResult val = (DateTimeResolutionResult)pr.value;
            val.setMod(Constants.SINCE_MOD);
            pr = (DateTimeParseResult) pr.withValue(val);
            hasSince = true;
        }

        if (config.getOptions().match(DateTimeOptions.SplitDateAndTime) && pr != null && pr.value != null &&
                ((DateTimeResolutionResult)pr.value).getSubDateTimeEntities() != null) {
            pr = (DateTimeParseResult) pr.withValue(dateTimeResolutionForSplit(pr));
        } else {
            boolean hasModifier = hasBefore || hasAfter || hasSince;
            pr = setParseResult(pr, hasModifier);
        }

        if (this.config.getOptions().match(DateTimeOptions.EnablePreview) {
            pr = (DateTimeParseResult) pr.withLength(pr.length + originText.length() - pr.text.length())
                .withText(originText);
        }

        return pr;
    }


    @Override
    public List<DateTimeParseResult> filterResults(String query, List<DateTimeParseResult> candidateResults) {
        if (config.getAmbiguousMonthP0Regex() != null) {
            if (candidateResults != null && !candidateResults.isEmpty()) {

                List<Match> matches = Arrays.asList(RegExpUtility.getMatches(config.getAmbiguousMonthP0Regex(),query));

                for (Match match : matches) {
                    // Check for intersections/overlaps
                    candidateResults = candidateResults.stream().filter(
                        c -> filterResultsPredicate(c, match))
                        .collect(Collectors.toList());
                }

            }
        }

        return candidateResults;
    }

    private boolean filterResultsPredicate(DateTimeParseResult pr, Match match) {
        return !(match.index < pr.start + pr.length && pr.start < match.index + match.length);
    }

    public DateTimeParseResult setParseResult(DateTimeParseResult slot, boolean hasMod) {
        slot = slot.withValue(dateTimeResolution(slot));

        // Change the type at last for the after or before modes
        slot.withType(String.format("%s.%s",parserName, determineDateTimeType(slot.type, hasMod)));

        return slot;
    }

    public String determineDateTimeType(String type, boolean hasMod) {
        if (config.getOptions().match(DateTimeOptions.SplitDateAndTime)) {
            if (type.equals(Constants.SYS_DATETIME_DATETIME)) {
                return Constants.SYS_DATETIME_TIME;
            }
        } else {
            if (hasMod) {
                if (type.equals(Constants.SYS_DATETIME_DATE)) {
                    return Constants.SYS_DATETIME_DATEPERIOD;
                }

                if (type.equals(Constants.SYS_DATETIME_TIME)) {
                    return Constants.SYS_DATETIME_TIMEPERIOD;
                }

                if (type.equals(Constants.SYS_DATETIME_DATETIME)) {
                    return Constants.SYS_DATETIME_DATETIMEPERIOD;
                }
            }
        }

        return type;
    }

    public List<DateTimeParseResult> dateTimeResolutionForSplit(DateTimeParseResult slot) {
        List<DateTimeParseResult> results = new ArrayList<>();
        if (((DateTimeResolutionResult) slot.value).getSubDateTimeEntities() != null) {
            List<Object> subEntities = ((DateTimeResolutionResult) slot.value).getSubDateTimeEntities();
            for (Object subEntity : subEntities)
            {
                DateTimeParseResult result = (DateTimeParseResult) subEntity;
                results.addAll(dateTimeResolutionForSplit(result));
            }
        } else {
            slot = slot.withValue(dateTimeResolution(slot))
                .withType(String.format("%s.%s",parserName, determineDateTimeType(slot.type, false)));
            results.add(slot);
        }

        return results;
    }

    public SortedMap<String, Object> dateTimeResolution(DateTimeParseResult slot) {
        
    }

}
