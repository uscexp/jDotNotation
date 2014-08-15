/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributedetail;

/**
 * @author haui
 *
 */
public class AttributeDetailInterpreterResult {

	private String name;
	private boolean arrayType;
	private boolean mapType;
	private int index = -1;
	private Object simpleMapKey;
	private String constructionClass;
	private String factoryMethod;
	private Object[] parameters;
	private String value;
	

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isArrayType() {
		return arrayType;
	}
	
	public void setArrayType(boolean arrayType) {
		this.arrayType = arrayType;
	}
	
	public boolean isMapType() {
		return mapType;
	}
	
	public void setMapType(boolean mapType) {
		this.mapType = mapType;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public Object getSimpleMapKey() {
		return simpleMapKey;
	}
	
	public void setSimpleMapKey(Object mapKey) {
		this.simpleMapKey = mapKey;
	}

	public String getConstructionClass() {
		return constructionClass;
	}

	public void setConstructionClass(String constructionClass) {
		this.constructionClass = constructionClass;
	}

	public String getFactoryMethod() {
		return factoryMethod;
	}

	public void setFactoryMethod(String factoryMethod) {
		this.factoryMethod = factoryMethod;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
