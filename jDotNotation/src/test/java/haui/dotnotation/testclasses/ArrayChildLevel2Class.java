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
public class ArrayChildLevel2Class {

	private SimpleChildLevel3Class simpleChildLevel3Class;
	private List<ArrayChildLevel3Class> arrayChildLevel3Classes;
	private String simpleString;
	private int primitiveInt;

	public ArrayChildLevel2Class() {
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
}
