/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.testckasses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * @author haui
 *
 */
public class SimpleChildLevel3Class {

	private String simpleString;
	private int primitiveInt;
	private int[] privatePrimitiveIntArray;
	private List<String> privateStringList;
	private Collection<String> stringCollection;

	public SimpleChildLevel3Class() {
		super();
		simpleString = "Level 3";
		primitiveInt = 3;
		privatePrimitiveIntArray = new int[3];
		privatePrimitiveIntArray[0] = 1;
		privatePrimitiveIntArray[1] = 2;
		privatePrimitiveIntArray[2] = 3;
		privateStringList = new ArrayList<>();
		privateStringList.add("a");
		privateStringList.add("b");
		privateStringList.add("c");
		stringCollection = new TreeSet<>();
		stringCollection.add("a");
		stringCollection.add("b");
		stringCollection.add("c");
	}
	public String getSimpleString() {
		return simpleString;
	}
	public void setSimpleString(String simpleString) {
		this.simpleString = simpleString;
	}
	public int getPrimitiveInt() {
		return primitiveInt;
	}
	public void setPrimitiveInt(int primitiveInt) {
		this.primitiveInt = primitiveInt;
	}
	public Collection<String> getStringCollection() {
		return stringCollection;
	}
	public void setStringCollection(Collection<String> stringCollection) {
		this.stringCollection = stringCollection;
	}
}
