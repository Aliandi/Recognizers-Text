import { RegExpUtility } from "@microsoft/recognizers-text";
import { ISetExtractorConfiguration, ISetParserConfiguration } from "../baseSet";
import { BaseDurationExtractor, BaseDurationParser } from "../baseDuration";
import { BaseTimeExtractor, BaseTimeParser } from "../baseTime";
import { BaseDateExtractor, BaseDateParser } from "../baseDate";
import { BaseDateTimeExtractor, BaseDateTimeParser, IDateTimeExtractor } from "../baseDateTime";
import { BaseDatePeriodExtractor, BaseDatePeriodParser } from "../baseDatePeriod";
import { BaseTimePeriodExtractor, BaseTimePeriodParser } from "../baseTimePeriod";
import { BaseDateTimePeriodExtractor, BaseDateTimePeriodParser } from "../baseDateTimePeriod";
import { SpanishDateTime } from "../../resources/spanishDateTime";
import { SpanishDurationExtractorConfiguration } from "./durationConfiguration";
import { SpanishDateExtractorConfiguration } from "./dateConfiguration";
import { SpanishDateTimeExtractorConfiguration } from "./dateTimeConfiguration";
import { SpanishDatePeriodExtractorConfiguration } from "./datePeriodConfiguration";
import { SpanishDateTimePeriodExtractorConfiguration } from "./dateTimePeriodConfiguration";
import { ICommonDateTimeParserConfiguration } from "../parsers";
import { SpanishTimeExtractorConfiguration } from "./timeConfiguration";
import { SpanishTimePeriodExtractorConfiguration } from "./timePeriodConfiguration";

export class SpanishSetExtractorConfiguration implements ISetExtractorConfiguration {
    readonly lastRegex: RegExp;
    readonly eachPrefixRegex: RegExp;
    readonly periodicRegex: RegExp;
    readonly eachUnitRegex: RegExp;
    readonly eachDayRegex: RegExp;
    readonly beforeEachDayRegex: RegExp;
    readonly setWeekDayRegex: RegExp;
    readonly setEachRegex: RegExp;
    readonly durationExtractor: IDateTimeExtractor;
    readonly timeExtractor: IDateTimeExtractor;
    readonly dateExtractor: IDateTimeExtractor;
    readonly dateTimeExtractor: IDateTimeExtractor;
    readonly datePeriodExtractor: IDateTimeExtractor;
    readonly timePeriodExtractor: IDateTimeExtractor;
    readonly dateTimePeriodExtractor: IDateTimeExtractor;

    constructor() {
        this.lastRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.LastDateRegex, "gis");
        this.periodicRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.PeriodicRegex, "gis");
        this.eachUnitRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.EachUnitRegex, "gis");
        this.eachPrefixRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.EachPrefixRegex, "gis");
        this.eachDayRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.EachDayRegex, "gis");
        this.beforeEachDayRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.BeforeEachDayRegex, "gis");
        this.setEachRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.SetEachRegex, "gis");
        this.setWeekDayRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.SetWeekDayRegex, "gis");

        this.durationExtractor = new BaseDurationExtractor(new SpanishDurationExtractorConfiguration());
        this.timeExtractor = new BaseTimeExtractor(new SpanishTimeExtractorConfiguration());
        this.dateExtractor = new BaseDateExtractor(new SpanishDateExtractorConfiguration());
        this.dateTimeExtractor = new BaseDateTimeExtractor(new SpanishDateTimeExtractorConfiguration());
        this.datePeriodExtractor = new BaseDatePeriodExtractor(new SpanishDatePeriodExtractorConfiguration());
        this.timePeriodExtractor = new BaseTimePeriodExtractor(new SpanishTimePeriodExtractorConfiguration());
        this.dateTimePeriodExtractor = new BaseDateTimePeriodExtractor(new SpanishDateTimePeriodExtractorConfiguration());
    }
}

export class SpanishSetParserConfiguration implements ISetParserConfiguration {
    readonly durationExtractor: IDateTimeExtractor;
    readonly durationParser: BaseDurationParser;
    readonly timeExtractor: IDateTimeExtractor;
    readonly timeParser: BaseTimeParser;
    readonly dateExtractor: IDateTimeExtractor;
    readonly dateParser: BaseDateParser;
    readonly dateTimeExtractor: IDateTimeExtractor;
    readonly dateTimeParser: BaseDateTimeParser;
    readonly datePeriodExtractor: IDateTimeExtractor;
    readonly datePeriodParser: BaseDatePeriodParser;
    readonly timePeriodExtractor: IDateTimeExtractor;
    readonly timePeriodParser: BaseTimePeriodParser;
    readonly dateTimePeriodExtractor: IDateTimeExtractor;
    readonly dateTimePeriodParser: BaseDateTimePeriodParser;
    readonly unitMap: ReadonlyMap<string, string>;
    readonly eachPrefixRegex: RegExp;
    readonly periodicRegex: RegExp;
    readonly eachUnitRegex: RegExp;
    readonly eachDayRegex: RegExp;
    readonly setWeekDayRegex: RegExp;
    readonly setEachRegex: RegExp;

    constructor(config: ICommonDateTimeParserConfiguration) {
        this.durationExtractor = config.durationExtractor;
        this.timeExtractor = config.timeExtractor;
        this.dateExtractor = config.dateExtractor;
        this.dateTimeExtractor = config.dateTimeExtractor;
        this.datePeriodExtractor = config.datePeriodExtractor;
        this.timePeriodExtractor = config.timePeriodExtractor;
        this.dateTimePeriodExtractor = config.dateTimePeriodExtractor;
        this.durationParser = config.durationParser;
        this.timeParser = config.timeParser;
        this.dateParser = config.dateParser;
        this.dateTimeParser = config.dateTimeParser;
        this.datePeriodParser = config.datePeriodParser;
        this.timePeriodParser = config.timePeriodParser;
        this.dateTimePeriodParser = config.dateTimePeriodParser;
        this.unitMap = config.unitMap;

        this.eachPrefixRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.EachPrefixRegex, "gis");
        this.periodicRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.PeriodicRegex, "gis");
        this.eachUnitRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.EachUnitRegex, "gis");
        this.eachDayRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.EachDayRegex, "gis");
        this.setWeekDayRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.SetWeekDayRegex, "gis");
        this.setEachRegex = RegExpUtility.getSafeRegExp(SpanishDateTime.SetEachRegex, "gis");
    }

    getMatchedDailyTimex(text: string): { matched: boolean; timex: string; } {
        let trimmedText = text.trim().toLowerCase();
        let timex = "";

        if (trimmedText.endsWith("diario") || trimmedText.endsWith("diariamente")) {
            timex = "P1D";
        }
        else if (trimmedText === "semanalmente") {
            timex = "P1W";
        }
        else if (trimmedText === "quincenalmente") {
            timex = "P2W";
        }
        else if (trimmedText === "mensualmente") {
            timex = "P1M";
        }
        else if (trimmedText === "anualmente") {
            timex = "P1Y";
        }
        else {
            timex = null;
            return {
                timex,
                matched: false
            };
        }

        return {
            timex,
            matched: true
        }
    }

    getMatchedUnitTimex(text: string): { matched: boolean; timex: string; } {
        let trimmedText = text.trim().toLowerCase();
        let timex = "";

        if (trimmedText === "día" || trimmedText === "dia" ||
            trimmedText === "días" || trimmedText === "dias") {
            timex = "P1D";
        }
        else if (trimmedText === "semana" || trimmedText === "semanas") {
            timex = "P1W";
        }
        else if (trimmedText === "mes" || trimmedText === "meses") {
            timex = "P1M";
        }
        else if (trimmedText === "año" || trimmedText === "años") {
            timex = "P1Y";
        }
        else {
            timex = null;
            return {
                matched: false,
                timex
            };
        }

        return {
            matched: true,
            timex
        };
    }
}