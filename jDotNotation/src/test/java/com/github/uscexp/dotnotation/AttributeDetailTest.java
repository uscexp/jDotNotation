/*
 * Copyright (C) 2014 - 2026 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
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
	private String attribute = "attribute";

	public static String createString(String value) {
		return value == null ? GENERATED_NULL : value;
	}

	public static String createString(String value1, String value2, String value3) {
		return createString(value1) + createString(value2) + createString(value3);
	}

	@Test
	public void testAttributeDetail() throws Exception {
		AttributeDetail attributeDetail = constructAttributeDetail(attribute);

		assertEquals(attribute, attributeDetail.getName());
        assertFalse(attributeDetail.isArrayType());

		attributeDetail = constructAttributeDetail(attribute + "[3]");

		assertEquals(attribute, attributeDetail.getName());
        assertTrue(attributeDetail.isArrayType());
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
		AttributeDetail attributeDetail = constructAttributeDetail(attribute + "['test']");

		assertEquals(attribute, attributeDetail.getName());
        assertTrue(attributeDetail.isMapType());
		assertEquals("test", attributeDetail.getMapKey());
	}

	@Test
	public void testAttributeDetailAccessMapWithConstructor() throws Exception {
		AttributeDetail attributeDetail = constructAttributeDetail(attribute + "[java.lang.String(test)]");

		assertEquals(attribute, attributeDetail.getName());
        assertTrue(attributeDetail.isMapType());
		assertEquals("test", attributeDetail.getMapKey());
	}

	@Test
	public void testAttributeDetailAccessMapWithFactoryMethod() throws Exception {
		AttributeDetail attributeDetail = constructAttributeDetail(attribute + "[java.lang.Integer#valueOf(1)]");

		assertEquals(attribute, attributeDetail.getName());
        assertTrue(attributeDetail.isMapType());
		assertEquals(Integer.valueOf(1), attributeDetail.getMapKey());
	}

	@Test
	public void testAttributeDetailAccessMapWithErrorOnFactoryMethod() {
		assertThrows(GrammarException.class,
				() -> constructAttributeDetail(attribute + "[java.lang.Integer#valueOf#bla(1)]"));
	}

	@Test
	public void testAttributeDetailAccessMapWithErrorOnFactoryClass() {
		assertThrows(ClassNotFoundException.class,
				() -> constructAttributeDetail(attribute + "[bla#valueOf(1)]"));
	}

	@Test
	public void testAttributeDetailAccessMapWithErrorOnFactoryMethodNotFound() {
		assertThrows(NoSuchMethodException.class,
				() -> constructAttributeDetail(attribute + "[java.lang.Integer#bla(1)]"));
	}

	@Test
	public void testAttributeDetailAccessMapWithErrorOnConstructor() {
		assertThrows(NoSuchMethodException.class,
				() -> constructAttributeDetail(attribute + "[java.lang.String(1,2)]"));
	}

	@Test
	public void testAttributeDetailAccessMapWithFactoryMethodParameterNull() throws Exception {
		AttributeDetail attributeDetail = constructAttributeDetail(attribute + "[com.github.uscexp.dotnotation.AttributeDetailTest#createString(null)]");

		assertEquals(attribute, attributeDetail.getName());
        assertTrue(attributeDetail.isMapType());
		assertEquals(GENERATED_NULL, attributeDetail.getMapKey());
	}

	@Test
	public void testAttributeDetailAccessMapWithFactoryMethodTreeParameters() throws Exception {
		AttributeDetail attributeDetail = constructAttributeDetail(attribute + "[com.github.uscexp.dotnotation.AttributeDetailTest#createString(value1,null,value3)]");

		assertEquals(attribute, attributeDetail.getName());
        assertTrue(attributeDetail.isMapType());
		assertEquals("value1" + GENERATED_NULL + "value3", attributeDetail.getMapKey());
	}

	@Test
	public void testAttributeDetail1() throws Exception {
		AttributeDetail attributeDetail = constructAttributeDetail(attribute + "[]");

		assertEquals(attribute, attributeDetail.getName());
        assertTrue(attributeDetail.isArrayType());
	}

	@Test
	public void testAttributeDetailError() {
		assertThrows(GrammarException.class, () -> constructAttributeDetail(attribute + "[a]"));
	}

	@Test
	public void testAttributeDetailError1() {
		assertThrows(GrammarException.class, () -> constructAttributeDetail(attribute + "[2"));
	}

	@Test
	public void testAttributeDetailError2() {
		assertThrows(GrammarException.class, () -> constructAttributeDetail(attribute + "]["));
	}
}
