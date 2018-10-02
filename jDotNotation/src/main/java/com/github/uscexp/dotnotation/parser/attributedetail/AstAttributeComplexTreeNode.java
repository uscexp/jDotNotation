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
public class AstAttributeComplexTreeNode extends AstCommandTreeNode<String> {

	public AstAttributeComplexTreeNode(String node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		ProcessStore<Object> processStore = ProcessStore.getInstance(id);
		IStack<Object> stack = processStore.getStack();
		AttributeDetailInterpreterResult attributeDetailInterpreterResult = (AttributeDetailInterpreterResult) processStore.getVariable(
				AttributeDetailParser.ATTRIBUTE_DETAIL_INTERPRETER_RESULT);
		String name = (String) stack.pop();
		attributeDetailInterpreterResult.setName(name);
		attributeDetailInterpreterResult.setValue(value);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
	}

}
