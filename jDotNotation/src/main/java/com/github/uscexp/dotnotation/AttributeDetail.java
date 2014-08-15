/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.github.uscexp.dotnotation.exception.AttributeAccessExeption;
import com.github.uscexp.dotnotation.parser.attributedetail.AttributeDetailInterpreterResult;
import com.github.uscexp.grappa.extension.exception.AstInterpreterException;

/**
 * @author haui
 *
 */
public class AttributeDetail {

	public static final String HASHMARK = "#";

	private String name;
	private boolean arrayType;
	private boolean mapType;
	private int index = -1;
	private Object mapKey;

	public AttributeDetail(AttributeDetailInterpreterResult attributeDetailInterpreterResult)
		throws AstInterpreterException, AttributeAccessExeption, ReflectiveOperationException {
		
		name = attributeDetailInterpreterResult.getName();
		if(attributeDetailInterpreterResult.isArrayType()) {
			index = attributeDetailInterpreterResult.getIndex();
			arrayType = true;
		} else if(attributeDetailInterpreterResult.isMapType()) {
			String constructionClass = attributeDetailInterpreterResult.getConstructionClass();
			if(constructionClass != null) {
				mapKey = createKey(attributeDetailInterpreterResult, constructionClass);
			} else {
				mapKey = attributeDetailInterpreterResult.getSimpleMapKey();
			}
			mapType = true;
		}
	}

	private Object createKey(
			AttributeDetailInterpreterResult attributeDetailInterpreterResult,
			String constructionClass) throws ClassNotFoundException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		String factoryMethodString = attributeDetailInterpreterResult.getFactoryMethod();
		Object[] parameters = attributeDetailInterpreterResult.getParameters();
		Class<?>[] parameterTypes = new Class[parameters.length];
		
		for (int i = 0; i < parameterTypes.length; i++) {
			parameterTypes[i] = String.class;
		}
		Object result = null;
		Class<?> factoryClass = Class.forName(constructionClass);
		if (factoryMethodString != null) {
			Method method = factoryClass.getDeclaredMethod(factoryMethodString, parameterTypes);
			result = method.invoke(null, parameters);
		} else {
			Constructor<?> constructor = factoryClass.getConstructor(parameterTypes);
			result = constructor.newInstance(parameters);
		}
		return result;
	}

	public String getName() {
		return name;
	}

	public boolean isArrayType() {
		return arrayType;
	}

	public int getIndex() {
		return index;
	}

	public boolean isMapType() {
		return mapType;
	}

	public Object getMapKey() {
		return mapKey;
	}
}
