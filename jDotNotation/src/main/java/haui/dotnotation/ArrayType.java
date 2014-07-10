/*
 * (C) 2014 haui
 */
package haui.dotnotation;

import java.util.Collection;

/**
 * @author haui
 *
 */
public enum ArrayType {
	ARRAY(true),COLLECTION(true),NONE(false);
	
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
			
		default:
			return null;
		}
	}
}
