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
public class AstSimpleMapKeyTreeNode extends AstCommandTreeNode<String> {

	public AstSimpleMapKeyTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		ProcessStore<Object> processStore = ProcessStore.getInstance(id);
		Stack<Object> stack = processStore.getStack();
		AttributeDetailInterpreterResult attributeDetailInterpreterResult = (AttributeDetailInterpreterResult) processStore.getVariable(
				AttributeDetailParser.ATTRIBUTE_DETAIL_INTERPRETER_RESULT);
		attributeDetailInterpreterResult.setArrayType(false);
		attributeDetailInterpreterResult.setMapType(true);
		String mapKey = (String) stack.pop();
		attributeDetailInterpreterResult.setSimpleMapKey(mapKey);
	}

}
