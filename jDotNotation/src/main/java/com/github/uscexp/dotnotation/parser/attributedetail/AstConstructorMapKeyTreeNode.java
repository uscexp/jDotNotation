/*
 * Copyright (C) 2014 - 2018 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributedetail;

import com.github.uscexp.parboiled.extension.interpreter.ProcessStore;
import com.github.uscexp.parboiled.extension.nodes.AstCommandTreeNode;
import com.github.uscexp.parboiled.extension.util.IStack;

/**
 * @author haui
 *
 */
public class AstConstructorMapKeyTreeNode extends AstCommandTreeNode<String> {

	public AstConstructorMapKeyTreeNode(String node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		ProcessStore<Object> processStore = ProcessStore.getInstance(id);
		IStack<Object> stack = processStore.getStack();
		AttributeDetailInterpreterResult attributeDetailInterpreterResult = (AttributeDetailInterpreterResult) processStore.getVariable(
				AttributeDetailParser.ATTRIBUTE_DETAIL_INTERPRETER_RESULT);
		attributeDetailInterpreterResult.setArrayType(false);
		attributeDetailInterpreterResult.setMapType(true);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; !stack.isEmpty(); ++i) {
			if(i > 0)
				stringBuilder.append('.');
			stringBuilder.append(stack.pop());
		}
		attributeDetailInterpreterResult.setConstructionClass(stringBuilder.toString());
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
	}

}
