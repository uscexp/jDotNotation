/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributedetail;

import com.github.uscexp.dotnotation.parser.attributepath.AttributePathInterpreterResult;
import com.github.uscexp.dotnotation.parser.attributepath.AttributePathParser;
import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstCommandTreeNode;

/**
 * @author haui
 *
 */
public class AstAttributeTreeNode extends AstCommandTreeNode<String> {

	public AstAttributeTreeNode(String node, String value) {
		super(node, value);
	}

	@Override
	protected void interpretAfterChilds(Long id) throws ReflectiveOperationException {
		ProcessStore<Object> processStore = ProcessStore.getInstance(id);
		AttributeDetailInterpreterResult attributeDetailInterpreterResult = (AttributeDetailInterpreterResult) processStore.getVariable(
				AttributeDetailParser.ATTRIBUTE_DETAIL_INTERPRETER_RESULT);
		AttributePathInterpreterResult attributePathInterpreterResult = (AttributePathInterpreterResult) processStore.getVariable(
				AttributePathParser.ATTRIBUTE_PATH_INTERPRETER_RESULT);
		
		if(attributePathInterpreterResult != null) {
			attributePathInterpreterResult.getAttributeDetailInterpreterResults().add(0, attributeDetailInterpreterResult);
			processStore.setVariable(AttributeDetailParser.ATTRIBUTE_DETAIL_INTERPRETER_RESULT, new AttributeDetailInterpreterResult());
		}
	}

	@Override
	protected void interpretBeforeChilds(Long id) throws Exception {
	}

}
