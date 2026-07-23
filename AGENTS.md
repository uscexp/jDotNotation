# AGENTS.md

## Project at a glance
- `jDotNotation` is a Maven Java 8 library for dot-notation attribute access across object graphs.
- The actual Maven project lives in `jDotNotation/pom.xml`; run build commands from that subdirectory.
- Main entry point: `com.github.uscexp.dotnotation.DotNotationAccessor`.

## Architecture to keep in mind
- Access flow is **parse → interpret → reflect/access**.
- `parser/attributepath/AttributePathParser` splits a full path like `simpleChildLevel1Class.simpleChildLevel2Class.simpleString`.
- `parser/attributedetail/AttributeDetailParser` handles suffixes such as `[1]`, `['B']`, constructor keys, and factory keys.
- `DotNotationAccessor.runInterpreter()` wires Parboiled + `ProcessStore` + `AstInterpreter`; keep these pieces aligned when changing syntax.

## Syntax patterns used by the codebase
- Plain nesting: `simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.simpleString`
- Indexed collection/array access: `arrayChildLevel1Classes[0].arrayChildLevel2Classes[1]`
- Map by string key: `mapChildLevel1Classes['B']`
- Map by object key: `mapKeyChildLevel1Classes[com.github.uscexp.dotnotation.testclasses.KeyObject(2,B)]`

## Accessor conventions
- Constructor flags are used in tests to define behavior: `new DotNotationAccessor(true, false, false)` for accessor-only mode; `new DotNotationAccessor(false, true, false)` for reflective private-field access.
- Array/collection/map writes often replace or copy the underlying container rather than mutating in place; see `setValueInCollection()` and `setValueInMap()`.
- Null traversal behavior is controlled by `throwExceptionOnNullValueInAttributePath`; the tests expect a `NullPointerException` when this is enabled.

## Developer workflow
- Main verification command: `mvn test` (from `jDotNotation/`).
- Focused test run: `mvn -Dtest=DotNotationAccessorTest test`.
- Release packaging uses the `release` profile (`mvn -Prelease verify`) and attaches sources/javadocs plus GPG signing.

## Testing patterns
- Tests are JUnit 4 (`src/test/java/.../*Test.java`); fixtures live under `src/test/java/com/github/uscexp/dotnotation/testclasses/`.
- `DotNotationAccessorTest` is the best reference for supported path forms, reflection rules, and expected edge cases.
- `RootClass` and `KeyObject` model the intended object graph shapes for nested lists, maps, and keyed lookups.

## Change guidance
- Keep parser grammar, interpreter setup, and accessor behavior consistent; syntax changes usually require updates in both parser packages and tests.
- Preserve the existing package layout under `com.github.uscexp.dotnotation`.
- Be careful with the legacy API names and spellings (for example `AttributeAccessExeption`); they are part of the public surface.

