/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributedetail;

import java.util.Stack;
import java.util.StringTokenizer;

import com.github.uscexp.dotnotation.AttributeDetail;
import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;

/**
 * @author haui
 *
 */
public class AstFactoryMapKeyTreeNode extends AstCommandTreeNode<String> {

	public AstFactoryMapKeyTreeNode(String node, String value) {
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
		int hashMarkIndex = value.indexOf(AttributeDetail.HASHMARK);
		String factoryClass = value.substring(0, hashMarkIndex);
		attributeDetailInterpreterResult.setConstructionClass(factoryClass);
		
		StringTokenizer stringTokenizer = new StringTokenizer(factoryClass, ".", false);
		for (int i = 0; i < stringTokenizer.countTokens(); i++) {
			stack.pop();
		}
		String factoryMethod = (String) stack.pop();
		attributeDetailInterpreterResult.setFactoryMethod(factoryMethod);
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
	}

}
