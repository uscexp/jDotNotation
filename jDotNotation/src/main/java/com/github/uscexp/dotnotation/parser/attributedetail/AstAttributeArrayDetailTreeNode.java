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
public class AstAttributeArrayDetailTreeNode extends AstCommandTreeNode<String> {

	public AstAttributeArrayDetailTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpret(Long id) throws ReflectiveOperationException {
		ProcessStore<Object> processStore = ProcessStore.getInstance(id);
		Stack<Object> stack = processStore.getStack();
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

}
