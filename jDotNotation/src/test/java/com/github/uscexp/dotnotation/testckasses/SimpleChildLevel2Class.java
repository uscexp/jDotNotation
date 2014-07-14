/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.testckasses;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haui
 *
 */
public class SimpleChildLevel2Class {

	private SimpleChildLevel3Class simpleChildLevel3Class;
	private SimpleChildLevel3Class simpleChildLevel3Class2;
	private List<ArrayChildLevel3Class> arrayChildLevel3Classes;
	private String simpleString;
	private int primitiveInt;

	public SimpleChildLevel2Class() {
		super();
		simpleChildLevel3Class = new SimpleChildLevel3Class();
		arrayChildLevel3Classes = new ArrayList<>();
		arrayChildLevel3Classes.add(new ArrayChildLevel3Class());
		arrayChildLevel3Classes.add(new ArrayChildLevel3Class());
		simpleString = "Level 2";
		primitiveInt = 2;
	}
	public SimpleChildLevel3Class getSimpleChildLevel3Class() {
		return simpleChildLevel3Class;
	}
	public void setSimpleChildLevel3Class(SimpleChildLevel3Class simpleChildLevel3Class) {
		this.simpleChildLevel3Class = simpleChildLevel3Class;
	}
	public List<ArrayChildLevel3Class> getArrayChildLevel3Classes() {
		return arrayChildLevel3Classes;
	}
	public void setArrayChildLevel3Classes(List<ArrayChildLevel3Class> arrayChildLevel3Classes) {
		this.arrayChildLevel3Classes = arrayChildLevel3Classes;
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
	public SimpleChildLevel3Class getSimpleChildLevel3Class2() {
		return simpleChildLevel3Class2;
	}
	public void setSimpleChildLevel3Class2(
			SimpleChildLevel3Class simpleChildLevel3Class2) {
		this.simpleChildLevel3Class2 = simpleChildLevel3Class2;
	}
}
