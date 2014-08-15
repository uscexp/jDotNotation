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
public class AstAttributeSimpleTreeNode extends AstCommandTreeNode<String> {

	public AstAttributeSimpleTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id)
		throws ReflectiveOperationException {
		ProcessStore<Object> processStore = ProcessStore.getInstance(id);
		Stack<Object> stack = processStore.getStack();
		AttributeDetailInterpreterResult attributeDetailInterpreterResult = (AttributeDetailInterpreterResult) processStore.getVariable(
				AttributeDetailParser.ATTRIBUTE_DETAIL_INTERPRETER_RESULT);
		String name = (String) stack.pop();
		attributeDetailInterpreterResult.setName(name);
		attributeDetailInterpreterResult.setValue(value);
	}

}
