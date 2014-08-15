/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.testclasses;

/**
 * @author haui
 *
 */
public class KeyObject {

	private Integer key1;
	private String key2;
	
	public KeyObject(Integer key1, String key2) {
		super();
		this.key1 = key1;
		this.key2 = key2;
	}

	public KeyObject(String key1, String key2) {
		this(Integer.parseInt(key1), key2);
	}

	public Integer getKey1() {
		return key1;
	}

	public String getKey2() {
		return key2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key1 == null) ? 0 : key1.hashCode());
		result = prime * result + ((key2 == null) ? 0 : key2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyObject other = (KeyObject) obj;
		if (key1 == null) {
			if (other.key1 != null)
				return false;
		} else if (!key1.equals(other.key1))
			return false;
		if (key2 == null) {
			if (other.key2 != null)
				return false;
		} else if (!key2.equals(other.key2))
			return false;
		return true;
	}
}
