package com.microsoft.recognizers.text.tests.datetime;

import com.microsoft.recognizers.text.ModelResult;
import com.microsoft.recognizers.text.datetime.DateTimeOptions;
import com.microsoft.recognizers.text.datetime.DateTimeRecognizer;
import com.microsoft.recognizers.text.tests.AbstractTest;
import com.microsoft.recognizers.text.tests.TestCase;
import org.junit.AssumptionViolatedException;
import org.junit.runners.Parameterized;

import java.util.*;

public class DateTimeTest extends AbstractTest {

    private static final String recognizerType = "DateTime";

    @Parameterized.Parameters(name = "{0}")
    public static Collection<TestCase> testCases() {
        return AbstractTest.enumerateTestCases(recognizerType, "Model");
    }

    public DateTimeTest(TestCase currentCase) {
        super(currentCase);
    }

    @Override
    protected void recognizeAndAssert(TestCase currentCase) {
        // parse
        List<ModelResult> results = recognize(currentCase);

        // assert
        assertResultsWithKeys(currentCase, results, getKeysToTest(currentCase));
    }

    private List<String> getKeysToTest(TestCase currentCase) {
        switch (currentCase.modelName) {
            default:
                return Collections.emptyList();
        }
    }

    @Override
    protected List<ModelResult> recognize(TestCase currentCase) {
        try {
            String culture = getCultureCode(currentCase.language);
            switch (currentCase.modelName) {
                case "DateTimeModel":
                    return DateTimeRecognizer.recognizeDateTime(currentCase.input, culture, DateTimeOptions.None, false);
                case "DateTimeModelCalendarMode":
                    return DateTimeRecognizer.recognizeDateTime(currentCase.input, culture, DateTimeOptions.CalendarMode, false);
                case "DateTimeModelExtendedTypes":
                    return DateTimeRecognizer.recognizeDateTime(currentCase.input, culture, DateTimeOptions.ExtendedTypes, false);
                case "DateTimeModelSplitDateAndTime":
                    return DateTimeRecognizer.recognizeDateTime(currentCase.input, culture, DateTimeOptions.SplitDateAndTime, false);
                case "DateTimeModelComplexCalendar":
                    return DateTimeRecognizer.recognizeDateTime(currentCase.input, culture, DateTimeOptions.ComplexCalendar, false);
                default:
                    throw new AssumptionViolatedException("Model Type/Name not supported.");
            }
        } catch (IllegalArgumentException ex) {
            throw new AssumptionViolatedException(ex.getMessage(), ex);
        }
    }
}

