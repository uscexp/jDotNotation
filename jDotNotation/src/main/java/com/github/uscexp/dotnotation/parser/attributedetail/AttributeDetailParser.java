/*
 * Copyright (C) 2014 - 2018 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation.parser.attributedetail;

import org.parboiled.BaseParser;
import org.parboiled.Rule;

import com.github.uscexp.parboiled.extension.annotations.AstCommand;
import com.github.uscexp.parboiled.extension.annotations.AstValue;

//@formatter:off
/**
 * A parser for the attribute details to access arrays and maps.
 * <p>
 * Attribute {@literal <}- AttributeComplex / AttributeSimple
 * AttributeComplex {@literal <}- StringLiteral AttributeArrayDetail
 * AttributeSimple {@literal <}- StringLiteral
 * AttributeArrayDetail {@literal <}- SquareBracketOpen (IntegerLiteral / SimpleMapKey / ConstructorMapKey / FactoryMapKey)? SquareBracketClose
 * FactoryMapKey {@literal <}- StringLiteral (Dot StringLiteral)* HashMark StringLiteral BracketOpen ParameterList BracketClose
 * ConstructorMapKey {@literal <}- StringLiteral (Dot StringLiteral)* BracketOpen ParameterList BracketClose
 * ParameterList {@literal <}- IntegerLiteral / StringLiteral / null (ParameterDelimiter (IntegerLiteral / StringLiteral / NullLiteral))*
 * SimpleMapKey {@literal <}- Quote StringLiteral Quote
 * StringLiteral {@literal <}- (Alpha / "_" / "-") / (Alpha / IntegerLiteral / "_" / "-")
 * Alpha {@literal <}- [a-zA-Z]
 * IntegerLiteral {@literal <}- Digit+
 * Digit {@literal <}- [0-9]
 * SquareBracketOpen {@literal <}- "["
 * SquareBracketClose {@literal <}- "]"
 * BracketOpen {@literal <}- "("
 * BracketClose {@literal <}- ")"
 * HashMark {@literal <}- "#"
 * ParameterDelimiter {@literal <}- ","
 * Dot {@literal <}- "."
 * Quote {@literal <}- "'"
 * NullLiteral {@literal <}- null
 * </p>
 * @author haui
 *
 */
public class AttributeDetailParser extends BaseParser<String> {
//@formatter:on

	public static final String ATTRIBUTE_DETAIL_INTERPRETER_RESULT = "attributeDetailInterpreterResult";

	@AstCommand
	public Rule attribute() {
		return FirstOf(attributeComplex(), attributeSimple());
	}

	public Rule attributeEOI() {
		return FirstOf(Sequence(attributeComplex(), EOI), Sequence(attributeSimple(), EOI));
	}

	@AstCommand
	public Rule attributeComplex() {
		return Sequence(stringLiteral(), attributeArrayDetail());
	}

	@AstCommand
	public Rule attributeSimple() {
		return Sequence(stringLiteral(), true);
	}

	@AstCommand
	public Rule attributeArrayDetail() {
		return Sequence(squareBracketOpen(), Optional(FirstOf(integerLiteral(), simpleMapKey(), constructorMapKey(), factoryMapKey())),
				squareBracketClose());
	}

	@AstCommand
	public Rule factoryMapKey() {
		return Sequence(stringLiteral(), ZeroOrMore(Sequence(dot(), FirstOf(integerLiteral(), stringLiteral()))), hashMark(), stringLiteral(),
				bracketOpen(), parameterList(), bracketClose());
	}

	@AstCommand
	public Rule constructorMapKey() {
		return Sequence(stringLiteral(), ZeroOrMore(Sequence(dot(), FirstOf(integerLiteral(), stringLiteral()))), bracketOpen(),
				parameterList(), bracketClose());
	}

	@AstCommand
	public Rule parameterList() {
		return Sequence(FirstOf(integerLiteral(), stringLiteral(), nullLiteral()),
				ZeroOrMore(Sequence(parameterDelimiter(), FirstOf(integerLiteral(), stringLiteral(), nullLiteral()))));
	}

	@AstCommand
	public Rule simpleMapKey() {
		return Sequence(quote(), stringLiteral(), quote());
	}

	@AstValue
	public Rule stringLiteral() {
		return Sequence(FirstOf(alpha(), Ch('_'), Ch('-')), ZeroOrMore(FirstOf(alpha(), integerLiteral(), Ch('_'), Ch('-'))));
	}

	public Rule squareBracketOpen() {
		return Ch('[');
	}

	public Rule squareBracketClose() {
		return Ch(']');
	}

	public Rule bracketOpen() {
		return Ch('(');
	}

	public Rule bracketClose() {
		return Ch(')');
	}

	public Rule hashMark() {
		return Ch('#');
	}

	public Rule parameterDelimiter() {
		return Ch(',');
	}

	public Rule dot() {
		return Ch('.');
	}

	public Rule quote() {
		return Ch('\'');
	}

	public Rule nullLiteral() {
		return String("null");
	}

	@AstValue
	public Rule integerLiteral() {
		return OneOrMore(digit());
	}

	/**
	 * ALPHA as defined by RFC 5234, appendix B, section 1: ASCII letters
	 *
	 * <p>
	 * Therefore a-z, A-Z.
	 * </p>
	 *
	 * @return a rule
	 */
	public Rule alpha() {
		return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'));
	}

	/**
	 * DIGIT as defined by RFC 5234, appendix B, section 1 (0 to 9)
	 *
	 * @return a rule
	 */
	public Rule digit() {
		return CharRange('0', '9');
	}
}
