/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.testclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author haui
 *
 */
public class RootClass {

	private SimpleChildLevel1Class simpleChildLevel1Class;
	private List<ArrayChildLevel1Class> arrayChildLevel1Classes;
	private Map<String, ArrayChildLevel1Class> mapChildLevel1Classes;
	private Map<KeyObject, ArrayChildLevel1Class> mapKeyChildLevel1Classes;
	private String simpleString;
	private int primitiveInt;

	public RootClass() {
		super();
		simpleChildLevel1Class = new SimpleChildLevel1Class();
		arrayChildLevel1Classes = new ArrayList<>();
		arrayChildLevel1Classes.add(new ArrayChildLevel1Class());
		arrayChildLevel1Classes.add(new ArrayChildLevel1Class());
		mapChildLevel1Classes = new HashMap<>();
		mapChildLevel1Classes.put("A", new ArrayChildLevel1Class());
		mapChildLevel1Classes.put("B", new ArrayChildLevel1Class());
		mapKeyChildLevel1Classes = new HashMap<>();
		mapKeyChildLevel1Classes.put(new KeyObject(1, "A"), new ArrayChildLevel1Class());
		mapKeyChildLevel1Classes.put(new KeyObject(2, "B"), new ArrayChildLevel1Class());
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

	public Map<String, ArrayChildLevel1Class> getMapChildLevel1Classes() {
		return mapChildLevel1Classes;
	}

	public Map<KeyObject, ArrayChildLevel1Class> getMapKeyChildLevel1Classes() {
		return mapKeyChildLevel1Classes;
	}

	public void setMapChildLevel1Classes(
			Map<String, ArrayChildLevel1Class> mapChildLevel1Classes) {
		this.mapChildLevel1Classes = mapChildLevel1Classes;
	}

	public void setMapKeyChildLevel1Classes(
			Map<KeyObject, ArrayChildLevel1Class> mapKeyChildLevel1Classes) {
		this.mapKeyChildLevel1Classes = mapKeyChildLevel1Classes;
	}
}
