package com.microsoft.recognizers.text.choice.models;

import java.util.List;
import java.util.Map;

import com.microsoft.recognizers.text.ExtractResult;
import com.microsoft.recognizers.text.IExtractor;
import com.microsoft.recognizers.text.IModel;
import com.microsoft.recognizers.text.IParser;
import com.microsoft.recognizers.text.ModelResult;
import com.microsoft.recognizers.text.ParseResult;
import com.microsoft.recognizers.text.choice.Constants;

public abstract class ChoiceModel implements IModel {
	protected IExtractor extractor;
	protected IParser parser;
	public String modelTypeName;
	public ChoiceModel(IParser Parser, IExtractor Extractor) {
		parser = Parser;
		extractor = Extractor;
		modelTypeName = Constants.MODEL_BOOLEAN;
	}

	@Override
	public String getModelTypeName() {
		return modelTypeName;
	}

	@Override
	public List<ModelResult> parse(String query) {
		List<ExtractResult> extractResults = extractor.extract(query);
		List<ParseResult> parseResults = extractResults.stream().map(extrRes->parser.parse(extrRes)).collect(Collectors.toList());
		
		List<ModelResult> modelResultList = parseResults.stream().map(parseRes->new ModelResult(parseRes.Start.Value, parseRes.Start.Value + parseRes.Length.Value - 1, GetResolution(parseRes), parseRes.Text, modelTypeName).collect(Collectors.toList());;
		return parseResults.Select();
	}

	protected abstract Map<String, Object> GetResolution(ParseResult parseResult);
}