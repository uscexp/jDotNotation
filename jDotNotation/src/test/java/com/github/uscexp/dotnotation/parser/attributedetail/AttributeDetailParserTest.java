/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributedetail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import com.github.uscexp.dotnotation.exception.AttributeAccessExeption;
import com.github.uscexp.grappa.extension.exception.AstInterpreterException;
import com.github.uscexp.grappa.extension.interpreter.AstInterpreter;
import com.github.uscexp.grappa.extension.interpreter.ProcessStore;

/**
 * @author haui
 *
 */
public class AttributeDetailParserTest {

	private static AttributeDetailParser attributeDetailParser;
	
	@Before
	public void setup() {
		attributeDetailParser = Parboiled.createParser(AttributeDetailParser.class);
	}

	public static AttributeDetailInterpreterResult runInterpreter(String attribute, Rule rule)
			throws AttributeAccessExeption, AstInterpreterException {
		
		RecoveringParseRunner<AttributeDetailParser> recoveringParseRunner = new RecoveringParseRunner<>(rule);
		
		ParsingResult<AttributeDetailParser> parsingResult = recoveringParseRunner.run(attribute);
		
		if(parsingResult.hasErrors()) {
			throw new AttributeAccessExeption(String.format("AttributePath parse error(s): %s", ErrorUtils.printParseErrors(parsingResult)));
		}
		
		AstInterpreter<String> attributePathInterpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		ProcessStore<String> processStore = ProcessStore.getInstance(id);
		// set the result object
		processStore.setNewVariable(AttributeDetailParser.ATTRIBUTE_DETAIL_INTERPRETER_RESULT, new AttributeDetailInterpreterResult());
		attributePathInterpreter.execute(attributeDetailParser.getClass(), parsingResult, id);
		
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
