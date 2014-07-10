/*
 * ----------------------------------------------------------------------------
 * Copyright 2009 - 2014 by PostFinance AG - all rights reserved
 * ----------------------------------------------------------------------------
 */
/*
 * (C) 2014 haui
 */
package haui.dotnotation.testclasses;

/**
 * @author haui
 *
 */
public class SimpleChildLevel3Class {

	private String simpleString;
	private int primitiveInt;
	private int[] privatePrimitiveIntArray;

	public SimpleChildLevel3Class() {
		super();
		simpleString = "Level 3";
		primitiveInt = 3;
		privatePrimitiveIntArray = new int[3];
		privatePrimitiveIntArray[0] = 1;
		privatePrimitiveIntArray[1] = 2;
		privatePrimitiveIntArray[2] = 3;
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
}
