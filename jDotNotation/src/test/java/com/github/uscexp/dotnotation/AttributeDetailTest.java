/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author eisenhauera
 *
 */
public class AttributeDetailTest {

	@Test
	public void testAttributeDetail() throws Exception {
		String attribute = "attribute";
		AttributeDetail attributeDetail = new AttributeDetail(attribute);
		
		Assert.assertEquals(attribute, attributeDetail.getName());
		Assert.assertEquals(false, attributeDetail.isArrayType());
		
		attributeDetail = new AttributeDetail(attribute + "[3]");
		
		Assert.assertEquals(attribute, attributeDetail.getName());
		Assert.assertEquals(true, attributeDetail.isArrayType());
		Assert.assertEquals(3, attributeDetail.getIndex());
	}

	@Test
	public void testAttributeDetail1() throws Exception {
		String attribute = "attribute";
		AttributeDetail attributeDetail = new AttributeDetail(attribute + "[]");

		Assert.assertEquals(attribute, attributeDetail.getName());
		Assert.assertEquals(true, attributeDetail.isArrayType());
	}


	@Test(expected = NumberFormatException.class)
	public void testAttributeDetailError() throws Exception {
		String attribute = "attribute";
		new AttributeDetail(attribute + "[a]");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAttributeDetailError1() throws Exception {
		String attribute = "attribute";
		new AttributeDetail(attribute + "[2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAttributeDetailError2() throws Exception {
		String attribute = "attribute";
		new AttributeDetail(attribute + "][");
	}
}
