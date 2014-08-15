package com.github.uscexp.dotnotation.parser.attributepath;

import java.util.ArrayList;
import java.util.List;

import com.github.uscexp.dotnotation.parser.attributedetail.AttributeDetailInterpreterResult;

public class AttributePathInterpreterResult {
	
	private int count = 0;

	private List<AttributeDetailInterpreterResult> attributeDetailInterpreterResults = new ArrayList<>();

	public List<AttributeDetailInterpreterResult> getAttributeDetailInterpreterResults() {
		return attributeDetailInterpreterResults;
	}
	
	public boolean hasMoreElements() {
		return count < attributeDetailInterpreterResults.size();
	}
	
	public AttributeDetailInterpreterResult nextElement() {
		AttributeDetailInterpreterResult result = attributeDetailInterpreterResults.get(count++);
		return result;
	}
}
