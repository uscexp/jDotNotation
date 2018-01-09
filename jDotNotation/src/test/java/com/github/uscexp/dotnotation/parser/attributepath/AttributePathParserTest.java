/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributepath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.fge.grappa.exceptions.InvalidGrammarException;
import com.github.uscexp.dotnotation.DotNotationAccessor;
import com.github.uscexp.dotnotation.parser.attributedetail.AttributeDetailInterpreterResult;

/**
 * @author haui
 *
 */
public class AttributePathParserTest {

	@Test
	public void testParseAttributePath()
		throws Exception {
		String attributePath = "bla.bla[].bla[1].bla['a'].bla.bla[java.lang.String(bla)].bla";

		AttributePathInterpreterResult attributePathInterpreterResult = DotNotationAccessor.runInterpreter(attributePath);

		assertTrue(attributePathInterpreterResult.hasMoreElements());

		AttributeDetailInterpreterResult element = attributePathInterpreterResult.nextElement();
		assertEquals("bla", element.getValue());
		assertTrue(attributePathInterpreterResult.hasMoreElements());
		element = attributePathInterpreterResult.nextElement();
		assertEquals("bla[]", element.getValue());
		assertTrue(attributePathInterpreterResult.hasMoreElements());
		element = attributePathInterpreterResult.nextElement();
		assertEquals("bla[1]", element.getValue());
		assertTrue(attributePathInterpreterResult.hasMoreElements());
		element = attributePathInterpreterResult.nextElement();
		assertEquals("bla['a']", element.getValue());
		assertTrue(attributePathInterpreterResult.hasMoreElements());
		element = attributePathInterpreterResult.nextElement();
		assertEquals("bla", element.getValue());
		assertTrue(attributePathInterpreterResult.hasMoreElements());
		element = attributePathInterpreterResult.nextElement();
		assertEquals("bla[java.lang.String(bla)]", element.getValue());
		assertTrue(attributePathInterpreterResult.hasMoreElements());
		element = attributePathInterpreterResult.nextElement();
		assertEquals("bla", element.getValue());
	}

	@Test(expected = InvalidGrammarException.class)
	public void testParseAttributePathError()
		throws Exception {
		String attributePath = "bla.bla].bla[1].bla['a'].bla.bla[java.lang.String(bla)].bla";

		DotNotationAccessor.runInterpreter(attributePath);
	}

	@Test(expected = InvalidGrammarException.class)
	public void testParseAttributePathError1()
		throws Exception {
		String attributePath = "bla.bla[.bla[1].bla['a'].bla.bla[java.lang.String(bla)].bla";

		DotNotationAccessor.runInterpreter(attributePath);
	}

	@Test(expected = InvalidGrammarException.class)
	public void testParseAttributePathError2()
		throws Exception {
		String attributePath = "bla.bla[].bla1].bla['a'].bla.bla[java.lang.String(bla)].bla";

		DotNotationAccessor.runInterpreter(attributePath);
	}

	@Test(expected = InvalidGrammarException.class)
	public void testParseAttributePathError3()
		throws Exception {
		String attributePath = "bla.bla[].bla[1.bla['a'].bla.bla[java.lang.String(bla)].bla";

		DotNotationAccessor.runInterpreter(attributePath);
	}

	@Test(expected = InvalidGrammarException.class)
	public void testParseAttributePathError4()
		throws Exception {
		String attributePath = "bla.bla[].bla[1].['a'].bla.bla[java.lang.String(bla)].bla";

		DotNotationAccessor.runInterpreter(attributePath);
	}

	@Test(expected = InvalidGrammarException.class)
	public void testParseAttributePathError5()
		throws Exception {
		String attributePath = "bla.bla[].bla[1].bla[.bla.bla[java.lang.String(bla)]].bla";

		DotNotationAccessor.runInterpreter(attributePath);
	}
}
