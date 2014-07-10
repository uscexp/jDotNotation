/*
 * ----------------------------------------------------------------------------
 * Copyright 2009 - 2014 by PostFinance AG - all rights reserved
 * ----------------------------------------------------------------------------
 */
/*
 * (C) 2014 haui
 */
package haui.dotnotation;

/**
 * @author haui
 *
 */
public class AttributeDetail {

	private static final char ARRAY_OPEN = '[';
	private static final char ARRAY_CLOSE = ']';

	private String name;
	private boolean arrayType;
	private int index = -1;

	public AttributeDetail(String attribute) {
		int idxStart = attribute.indexOf(ARRAY_OPEN);

		if (idxStart > 0) {
			int idxEnd = attribute.indexOf(ARRAY_CLOSE);
			if ((idxEnd == -1) || (idxEnd <= idxStart)) {
				throw new IllegalArgumentException(String.format("Wrong array definition: %s", attribute));
			}

			String indexText = attribute.substring(idxStart + 1, idxEnd);
			if ((indexText != null) && (indexText.length() > 0))
				index = Integer.parseInt(indexText);
			name = attribute.substring(0, idxStart);
			arrayType = true;
		} else {
			name = attribute;
			arrayType = false;
		}
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
}
