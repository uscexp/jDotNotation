/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation;

import java.util.Collection;
import java.util.Map;

/**
 * @author haui
 *
 */
public enum ArrayType {
	ARRAY(true),COLLECTION(true),MAP(true),NONE(false);
	
	private boolean arrayType;
	
	private ArrayType(boolean arrayType) {
		this.arrayType = arrayType;
	}
	
	public boolean isArrayType() {
		return arrayType;
	}
	
	@SuppressWarnings("unchecked")
	public Object[] getArray(Object object) {
		switch (this) {
		case ARRAY:
			return (Object[])object;

		case COLLECTION:
			return (Object[])((Collection<Object>)object).toArray(new Object[((Collection<Object>)object).size()]);
			
		case MAP:
			return (Object[])((Map<Object, Object>)object).values().toArray(new Object[((Map<Object, Object>)object).size()]);
			
		default:
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Map<?, ?> getMap(Object object) {
		switch (this) {
		case ARRAY:
			return null;

		case COLLECTION:
			return null;
			
		case MAP:
			return (Map<Object, Object>)object;
			
		default:
			return null;
		}
	}
}
