/*
 * Copyright (C) 2014 - 2026 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributedetail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.parboiled.parser.Parboiled;
import org.parboiled.Rule;

import com.github.uscexp.dotnotation.exception.AttributeAccessExeption;
import com.github.uscexp.parboiled.extension.exception.AstInterpreterException;
import com.github.uscexp.parboiled.extension.interpreter.AstInterpreter;
import com.github.uscexp.parboiled.extension.interpreter.ProcessStore;
import com.github.uscexp.parboiled.extension.nodes.AstTreeNode;
import com.github.uscexp.parboiled.extension.parser.Parser;

/**
 * @author haui
 *
 */
public class AttributeDetailParserTest {

	private static AttributeDetailParser attributeDetailParser;

	@BeforeEach
	public void setup() {
		attributeDetailParser = Parboiled.createParser(AttributeDetailParser.class);
	}

	public static AttributeDetailInterpreterResult runInterpreter(String attribute, Rule rule)
			throws AttributeAccessExeption, AstInterpreterException {
		AstTreeNode<String> rootNode = Parser.parseInput(AttributeDetailParser.class, rule, attribute, true);

		AstInterpreter<String> attributePathInterpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		ProcessStore<String> processStore = ProcessStore.getInstance(id);
		// set the result object
		processStore.setNewVariable(AttributeDetailParser.ATTRIBUTE_DETAIL_INTERPRETER_RESULT, new AttributeDetailInterpreterResult());
		attributePathInterpreter.interpretBackwardOrder(attributeDetailParser.getClass(), rootNode, id);

		AttributeDetailInterpreterResult attributeDetailInterpreterResult = (AttributeDetailInterpreterResult) processStore.getVariable(AttributeDetailParser.ATTRIBUTE_DETAIL_INTERPRETER_RESULT);
		attributePathInterpreter.cleanUp(id);
		return attributeDetailInterpreterResult;
	}

	@Test
	public void testAttributeEOI() throws Exception {
		String attribute = "attribute";
		Rule rule = attributeDetailParser.attributeEOI();

		AttributeDetailInterpreterResult result = runInterpreter(attribute, rule);

		assertNotNull(result);
		assertEquals(attribute, result.getValue());
	}

}
