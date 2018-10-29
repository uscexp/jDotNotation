/*
 * Copyright (C) 2014 - 2018 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.parboiled.errors.GrammarException;

import com.github.uscexp.dotnotation.exception.AttributeAccessExeption;
import com.github.uscexp.dotnotation.parser.attributepath.AttributePathInterpreterResult;
import com.github.uscexp.parboiled.extension.exception.AstInterpreterException;

/**
 * @author haui
 *
 */
public class AttributeDetailTest {

	private static final String GENERATED_NULL = "generatedNull";

	public static String createString(String value) {
		return value == null ? GENERATED_NULL : value;
	}

	public static String createString(String value1, String value2, String value3) {
		return createString(value1) + createString(value2) + createString(value3);
	}

	@Test
	public void testAttributeDetail() throws Exception {
		String attribute = "attribute";

		AttributeDetail attributeDetail = constructAttributeDetail(attribute);

		assertEquals(attribute, attributeDetail.getName());
		assertEquals(false, attributeDetail.isArrayType());

		attributeDetail = constructAttributeDetail(attribute + "[3]");

		assertEquals(attribute, attributeDetail.getName());
		assertEquals(true, attributeDetail.isArrayType());
		assertEquals(3, attributeDetail.getIndex());
	}

	private AttributeDetail constructAttributeDetail(String attribute)
			throws AttributeAccessExeption, AstInterpreterException,
			ReflectiveOperationException {
		AttributePathInterpreterResult attributePathInterpreterResult = DotNotationAccessor.runInterpreter(attribute);

		assertTrue(attributePathInterpreterResult.hasMoreElements());

		AttributeDetail attributeDetail = new AttributeDetail(attributePathInterpreterResult.nextElement());

		assertFalse(attributePathInterpreterResult.hasMoreElements());
		return attributeDetail;
	}

	@Test
	public void testAttributeDetailAccessMapWithString() throws Exception {
		String attribute = "attribute";
		AttributeDetail attributeDetail = constructAttributeDetail(attribute + "['test']");

		assertEquals(attribute, attributeDetail.getName());
		assertEquals(true, attributeDetail.isMapType());
		assertEquals("test", attributeDetail.getMapKey());
	}

	@Test
	public void testAttributeDetailAccessMapWithConstructor() throws Exception {
		String attribute = "attribute";
		AttributeDetail attributeDetail = constructAttributeDetail(attribute + "[java.lang.String(test)]");

		assertEquals(attribute, attributeDetail.getName());
		assertEquals(true, attributeDetail.isMapType());
		assertEquals("test", attributeDetail.getMapKey());
	}

	@Test
	public void testAttributeDetailAccessMapWithFactoryMethod() throws Exception {
		String attribute = "attribute";
		AttributeDetail attributeDetail = constructAttributeDetail(attribute + "[java.lang.Integer#valueOf(1)]");

		assertEquals(attribute, attributeDetail.getName());
		assertEquals(true, attributeDetail.isMapType());
		assertEquals(Integer.valueOf(1), attributeDetail.getMapKey());
	}

	@Test(expected = GrammarException.class)
	public void testAttributeDetailAccessMapWithErrorOnFactoryMethod() throws Exception {
		String attribute = "attribute";
		constructAttributeDetail(attribute + "[java.lang.Integer#valueOf#bla(1)]");
	}

	@Test(expected = ClassNotFoundException.class)
	public void testAttributeDetailAccessMapWithErrorOnFactoryClass() throws Exception {
		String attribute = "attribute";
		constructAttributeDetail(attribute + "[bla#valueOf(1)]");
	}

	@Test(expected = NoSuchMethodException.class)
	public void testAttributeDetailAccessMapWithErrorOnFactoryMethodNotFound() throws Exception {
		String attribute = "attribute";
		constructAttributeDetail(attribute + "[java.lang.Integer#bla(1)]");
	}

	@Test(expected = NoSuchMethodException.class)
	public void testAttributeDetailAccessMapWithErrorOnConstructor() throws Exception {
		String attribute = "attribute";
		constructAttributeDetail(attribute + "[java.lang.String(1,2)]");
	}

	@Test
	public void testAttributeDetailAccessMapWithFactoryMethodParameterNull() throws Exception {
		String attribute = "attribute";
		AttributeDetail attributeDetail = constructAttributeDetail(attribute + "[com.github.uscexp.dotnotation.AttributeDetailTest#createString(null)]");

		assertEquals(attribute, attributeDetail.getName());
		assertEquals(true, attributeDetail.isMapType());
		assertEquals(GENERATED_NULL, attributeDetail.getMapKey());
	}

	@Test
	public void testAttributeDetailAccessMapWithFactoryMethodTreeParameters() throws Exception {
		String attribute = "attribute";
		AttributeDetail attributeDetail = constructAttributeDetail(attribute + "[com.github.uscexp.dotnotation.AttributeDetailTest#createString(value1,null,value3)]");

		assertEquals(attribute, attributeDetail.getName());
		assertEquals(true, attributeDetail.isMapType());
		assertEquals("value1" + GENERATED_NULL + "value3", attributeDetail.getMapKey());
	}

	@Test
	public void testAttributeDetail1() throws Exception {
		String attribute = "attribute";
		AttributeDetail attributeDetail = constructAttributeDetail(attribute + "[]");

		assertEquals(attribute, attributeDetail.getName());
		assertEquals(true, attributeDetail.isArrayType());
	}

	@Test(expected = GrammarException.class)
	public void testAttributeDetailError() throws Exception {
		String attribute = "attribute";
		constructAttributeDetail(attribute + "[a]");
	}

	@Test(expected = GrammarException.class)
	public void testAttributeDetailError1() throws Exception {
		String attribute = "attribute";
		constructAttributeDetail(attribute + "[2");
	}

	@Test(expected = GrammarException.class)
	public void testAttributeDetailError2() throws Exception {
		String attribute = "attribute";
		constructAttributeDetail(attribute + "][");
	}
}
