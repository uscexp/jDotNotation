/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.exception;

/**
 * @author haui
 *
 */
public class AttributeAccessExeption extends Exception {

	private static final long serialVersionUID = 6553428672927717399L;

	public AttributeAccessExeption(String message, Throwable cause) {
		super(message, cause);
	}

	public AttributeAccessExeption(String message) {
		super(message);
	}

}
