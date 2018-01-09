/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributedetail;

import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.github.uscexp.grappa.extension.annotations.AstCommand;
import com.github.uscexp.grappa.extension.annotations.AstValue;

/**
 * A parser for the attribute details to access arrays and maps.
 * <p>
 * Attribute <- AttributeComplex / AttributeSimple
 * AttributeComplex <- StringLiteral AttributeArrayDetail
 * AttributeSimple <- StringLiteral
 * AttributeArrayDetail <- SquareBracketOpen (IntegerLiteral / SimpleMapKey / ConstructorMapKey / FactoryMapKey)? SquareBracketClose
 * FactoryMapKey <- StringLiteral (Dot StringLiteral)* HashMark StringLiteral BracketOpen ParameterList BracketClose
 * ConstructorMapKey <- StringLiteral (Dot StringLiteral)* BracketOpen ParameterList BracketClose
 * ParameterList <- IntegerLiteral / StringLiteral / null (ParameterDelimiter (IntegerLiteral / StringLiteral / NullLiteral))*
 * SimpleMapKey <- Quote StringLiteral Quote
 * StringLiteral <- (Alpha / "_" / "-") / (Alpha / IntegerLiteral / "_" / "-")
 * Alpha <- [a-zA-Z]
 * IntegerLiteral <- Digit+
 * Digit <- [0-9]
 * SquareBracketOpen <- "["
 * SquareBracketClose <- "]"
 * BracketOpen <- "("
 * BracketClose <- ")"
 * HashMark <- "#"
 * ParameterDelimiter <- ","
 * Dot <- "."
 * Quote <- "'"
 * NullLiteral <- null
 * </p>
 * @author haui
 *
 */
public class AttributeDetailParser extends BaseParser<String> {

	public static final String ATTRIBUTE_DETAIL_INTERPRETER_RESULT = "attributeDetailInterpreterResult";

	@AstCommand
	public Rule attribute() {
		return firstOf(attributeComplex(), attributeSimple());
	}

	public Rule attributeEOI() {
		return firstOf(sequence(attributeComplex(), EOI), sequence(attributeSimple(), EOI));
	}

	@AstCommand
	public Rule attributeComplex() {
		return sequence(stringLiteral(), attributeArrayDetail());
	}

	@AstCommand
	public Rule attributeSimple() {
		return sequence(stringLiteral(), true);
	}

	@AstCommand
	public Rule attributeArrayDetail() {
		return
			sequence(squareBracketOpen(), optional(firstOf(integerLiteral(), simpleMapKey(), constructorMapKey(), factoryMapKey())),
				squareBracketClose());
	}

	@AstCommand
	public Rule factoryMapKey() {
		return
			sequence(stringLiteral(), zeroOrMore(sequence(dot(), firstOf(integerLiteral(), stringLiteral()))), hashMark(), stringLiteral(),
				bracketOpen(), parameterList(), bracketClose());
	}

	@AstCommand
	public Rule constructorMapKey() {
		return
			sequence(stringLiteral(), zeroOrMore(sequence(dot(), firstOf(integerLiteral(), stringLiteral()))), bracketOpen(),
				parameterList(), bracketClose());
	}

	@AstCommand
	public Rule parameterList() {
		return
			sequence(firstOf(integerLiteral(), stringLiteral(), nullLiteral()),
				zeroOrMore(sequence(parameterDelimiter(), firstOf(integerLiteral(), stringLiteral(), nullLiteral()))));
	}

	@AstCommand
	public Rule simpleMapKey() {
		return sequence(quote(), stringLiteral(), quote());
	}

	@AstValue
	public Rule stringLiteral() {
		return sequence(firstOf(alpha(), ch('_'), ch('-')), zeroOrMore(firstOf(alpha(), integerLiteral(), ch('_'), ch('-'))));
	}

	public Rule squareBracketOpen() {
		return ch('[');
	}

	public Rule squareBracketClose() {
		return ch(']');
	}

	public Rule bracketOpen() {
		return ch('(');
	}

	public Rule bracketClose() {
		return ch(')');
	}

	public Rule hashMark() {
		return ch('#');
	}

	public Rule parameterDelimiter() {
		return ch(',');
	}

	public Rule dot() {
		return ch('.');
	}

	public Rule quote() {
		return ch('\'');
	}

	public Rule nullLiteral() {
		return string("null");
	}

	@AstValue
	public Rule integerLiteral() {
		return oneOrMore(digit());
	}
}
