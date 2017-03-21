/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributedetail;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;
import com.github.uscexp.grappa.extension.util.IStack;

/**
 * @author haui
 *
 */
public class AstAttributeArrayDetailTreeNode extends AstCommandTreeNode<String> {

	public AstAttributeArrayDetailTreeNode(String node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		ProcessStore<Object> processStore = ProcessStore.getInstance(id);
		IStack<Object> stack = processStore.getStack();
		AttributeDetailInterpreterResult attributeDetailInterpreterResult = (AttributeDetailInterpreterResult) processStore.getVariable(
				AttributeDetailParser.ATTRIBUTE_DETAIL_INTERPRETER_RESULT);
		if(!attributeDetailInterpreterResult.isMapType()) {
			attributeDetailInterpreterResult.setArrayType(true);
			if(!stack.isEmpty()) {
				String index = (String) stack.pop();
				attributeDetailInterpreterResult.setIndex(Integer.valueOf(index));
			}
		}
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
	}

}
