/*
 * Copyright (C) 2014 - 2026 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributepath;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.parboiled.errors.GrammarException;

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

	@Test
	public void testParseAttributePathError() {
		String attributePath = "bla.bla].bla[1].bla['a'].bla.bla[java.lang.String(bla)].bla";
		assertThrows(GrammarException.class, () -> DotNotationAccessor.runInterpreter(attributePath));
	}

	@Test
	public void testParseAttributePathError1() {
		String attributePath = "bla.bla[.bla[1].bla['a'].bla.bla[java.lang.String(bla)].bla";
		assertThrows(GrammarException.class, () -> DotNotationAccessor.runInterpreter(attributePath));
	}

	@Test
	public void testParseAttributePathError2() {
		String attributePath = "bla.bla[].bla1].bla['a'].bla.bla[java.lang.String(bla)].bla";
		assertThrows(GrammarException.class, () -> DotNotationAccessor.runInterpreter(attributePath));
	}

	@Test
	public void testParseAttributePathError3() {
		String attributePath = "bla.bla[].bla[1.bla['a'].bla.bla[java.lang.String(bla)].bla";
		assertThrows(GrammarException.class, () -> DotNotationAccessor.runInterpreter(attributePath));
	}

	@Test
	public void testParseAttributePathError4() {
		String attributePath = "bla.bla[].bla[1].['a'].bla.bla[java.lang.String(bla)].bla";
		assertThrows(GrammarException.class, () -> DotNotationAccessor.runInterpreter(attributePath));
	}

	@Test
	public void testParseAttributePathError5() {
		String attributePath = "bla.bla[].bla[1].bla[.bla.bla[java.lang.String(bla)]].bla";
		assertThrows(GrammarException.class, () -> DotNotationAccessor.runInterpreter(attributePath));
	}
}
