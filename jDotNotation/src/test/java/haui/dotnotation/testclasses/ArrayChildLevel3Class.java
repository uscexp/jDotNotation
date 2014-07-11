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
public class ArrayChildLevel3Class {

	private String simpleString;
	private int primitiveInt;
	private int[] privatePrimitiveIntArray;
	private List<String> privateStringList;

	public ArrayChildLevel3Class() {
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
