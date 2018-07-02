package com.microsoft.recognizers.text.number.japanese;

/**
 * These modes can be applied to JapaneseNumberExtractor.
 * The default more urilizes an allow list to avoid extracting numbers in ambiguous/undesired combinations of Japanese ideograms.
 * --> such as "西九条" is a place name in Japanese, should not be extracted.
 * ExtractAll mode is to be used in cases where extraction should be more aggressive (e.g. in Units extraction).
 */
public enum JapaneseNumberExtractorMode {
    /**
     * Number extraction with an allow list that filters what numbers to extract.
     */
    Default,

    /**
     * Extract all number-related terms aggressively.
     */
    ExtractAll
}
