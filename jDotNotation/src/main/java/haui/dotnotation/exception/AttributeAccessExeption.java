/*
 * (C) 2014 haui
 */
package haui.dotnotation.exception;

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
