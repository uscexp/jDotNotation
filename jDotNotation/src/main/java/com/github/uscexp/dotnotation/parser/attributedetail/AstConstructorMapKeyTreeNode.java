/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributedetail;

import java.util.Stack;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;

/**
 * @author haui
 *
 */
public class AstConstructorMapKeyTreeNode extends AstCommandTreeNode<String> {

	public AstConstructorMapKeyTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		ProcessStore<Object> processStore = ProcessStore.getInstance(id);
		Stack<Object> stack = processStore.getStack();
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
