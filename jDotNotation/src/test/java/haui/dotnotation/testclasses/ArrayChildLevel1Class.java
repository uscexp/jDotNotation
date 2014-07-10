/*
 * ----------------------------------------------------------------------------
 * Copyright 2009 - 2014 by PostFinance AG - all rights reserved
 * ----------------------------------------------------------------------------
 */
/*
 * (C) 2014 haui
 */
package haui.dotnotation.testclasses;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haui
 *
 */
public class ArrayChildLevel1Class {

	private SimpleChildLevel2Class simpleChildLevel2Class;
	private List<ArrayChildLevel2Class> arrayChildLevel2Classes;
	private String simpleString;
	private int primitiveInt;

	public ArrayChildLevel1Class() {
		super();
		simpleChildLevel2Class = new SimpleChildLevel2Class();
		arrayChildLevel2Classes = new ArrayList<>();
		arrayChildLevel2Classes.add(new ArrayChildLevel2Class());
		arrayChildLevel2Classes.add(new ArrayChildLevel2Class());
		simpleString = "Level 1";
		primitiveInt = 1;
	}
	public SimpleChildLevel2Class getSimpleChildLevel2Class() {
		return simpleChildLevel2Class;
	}
	public void setSimpleChildLevel2Class(SimpleChildLevel2Class simpleChildLevel2Class) {
		this.simpleChildLevel2Class = simpleChildLevel2Class;
	}
	public List<ArrayChildLevel2Class> getArrayChildLevel2Classes() {
		return arrayChildLevel2Classes;
	}
	public void setArrayChildLevel2Classes(List<ArrayChildLevel2Class> arrayChildLevel2Classes) {
		this.arrayChildLevel2Classes = arrayChildLevel2Classes;
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
