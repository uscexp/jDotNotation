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
public class RootClass {

	private SimpleChildLevel1Class simpleChildLevel1Class;
	private List<ArrayChildLevel1Class> arrayChildLevel1Classes;
	private String simpleString;
	private int primitiveInt;

	public RootClass() {
		super();
		simpleChildLevel1Class = new SimpleChildLevel1Class();
		arrayChildLevel1Classes = new ArrayList<>();
		arrayChildLevel1Classes.add(new ArrayChildLevel1Class());
		arrayChildLevel1Classes.add(new ArrayChildLevel1Class());
		simpleString = "Root";
		primitiveInt = 0;
	}

	public SimpleChildLevel1Class getSimpleChildLevel1Class() {
		return simpleChildLevel1Class;
	}
	
	public void setSimpleChildLevel1Class(SimpleChildLevel1Class simpleChildLevel1Class) {
		this.simpleChildLevel1Class = simpleChildLevel1Class;
	}
	
	public List<ArrayChildLevel1Class> getArrayChildLevel1Classes() {
		return arrayChildLevel1Classes;
	}
	
	public void setArrayChildLevel1Classes(List<ArrayChildLevel1Class> arrayChildLevel1Classes) {
		this.arrayChildLevel1Classes = arrayChildLevel1Classes;
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
