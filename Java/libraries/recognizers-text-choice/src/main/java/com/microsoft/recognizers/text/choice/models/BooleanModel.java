package com.microsoft.recognizers.text.choice.models;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.microsoft.recognizers.text.IExtractor;
import com.microsoft.recognizers.text.IParser;
import com.microsoft.recognizers.text.ParseResult;
import com.microsoft.recognizers.text.choice.Constants;
import com.microsoft.recognizers.text.choice.parsers.OptionsOtherMatchParseResult;
import com.microsoft.recognizers.text.choice.parsers.OptionsParseDataResult;

public class BooleanModel extends ChoiceModel {

	public BooleanModel(IParser parser, IExtractor extractor) {
		super(parser, extractor);
		throw new UnsupportedOperationException();
	}

	public String getModelTypeName() {
		return Constants.MODEL_BOOLEAN;
	}

	protected SortedMap<String, Object> GetResolution(ParseResult parseResult) {
		OptionsParseDataResult data = (OptionsParseDataResult) parseResult.data;
		SortedMap<String, Object> results = new TreeMap<String, Object>();
		results.put("value", parseResult.value);
		results.put("score", data.score);
		List<Object> otherMatchesResult = data.otherMatches.stream().map(otherMatch -> makeOtherMatchesObject(otherMatch)).collect(Collectors.toList());
		results.put("otherResults", otherMatchesResult);

		return results;
	}

	private SortedMap<String, Object> makeOtherMatchesObject(OptionsOtherMatchParseResult matchParseResult) {
		SortedMap<String, Object> results = new TreeMap<String, Object>();
		results.put("text", matchParseResult.text);
		results.put("value", matchParseResult.value);
		results.put("score", matchParseResult.score);
		return results;
	}
}