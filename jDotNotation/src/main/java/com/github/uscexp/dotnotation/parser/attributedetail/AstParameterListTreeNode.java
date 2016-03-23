/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributedetail;

import java.util.Stack;
import java.util.StringTokenizer;

import org.parboiled.Node;

import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;

/**
 * @author haui
 *
 */
public class AstParameterListTreeNode extends AstCommandTreeNode<String> {

	public AstParameterListTreeNode(Node<?> node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		ProcessStore<Object> processStore = ProcessStore.getInstance(id);
		Stack<Object> stack = processStore.getStack();
		AttributeDetailInterpreterResult attributeDetailInterpreterResult = (AttributeDetailInterpreterResult) processStore.getVariable(
				AttributeDetailParser.ATTRIBUTE_DETAIL_INTERPRETER_RESULT);
		StringTokenizer tokenizer = new StringTokenizer(value, ",", false);
		String[] parameters = new String[tokenizer.countTokens()];
		for (int i = 0; tokenizer.hasMoreTokens(); i++) {
			String token = tokenizer.nextToken();
			if(token.equals("null"))
				continue;
			parameters[i] = token;
		}
		attributeDetailInterpreterResult.setParameters(parameters);
		stack.clear();
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
	}

}
