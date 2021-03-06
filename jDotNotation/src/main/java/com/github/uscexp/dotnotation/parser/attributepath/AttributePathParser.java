/*
 * Copyright (C) 2014 - 2018 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributepath;

import org.parboiled.Rule;

import com.github.uscexp.dotnotation.parser.attributedetail.AttributeDetailParser;

/**
 * @author haui
 *
 */
public class AttributePathParser extends AttributeDetailParser {

	public static final Object ATTRIBUTE_PATH_INTERPRETER_RESULT = "attributePathInterpreterResult";

	public Rule attributePath() {
		return Sequence(attribute(), ZeroOrMore(Sequence(dot(), attribute())), EOI);
	}
}
