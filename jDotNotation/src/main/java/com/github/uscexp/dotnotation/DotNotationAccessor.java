/*
 * Copyright (C) 2014 by haui - all rights reserved
 */
package com.github.uscexp.dotnotation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.fge.grappa.Grappa;
import com.github.uscexp.dotnotation.exception.AttributeAccessExeption;
import com.github.uscexp.dotnotation.parser.attributedetail.AttributeDetailInterpreterResult;
import com.github.uscexp.dotnotation.parser.attributedetail.AttributeDetailParser;
import com.github.uscexp.dotnotation.parser.attributepath.AttributePathInterpreterResult;
import com.github.uscexp.dotnotation.parser.attributepath.AttributePathParser;
import com.github.uscexp.grappa.extension.exception.AstInterpreterException;
import com.github.uscexp.grappa.extension.interpreter.AstInterpreter;
import com.github.uscexp.grappa.extension.interpreter.ProcessStore;
import com.github.uscexp.grappa.extension.nodes.AstTreeNode;
import com.github.uscexp.grappa.extension.parser.Parser;

/**
 * With this class one can access values of attributes via a defined path from a root element.
 * <p>
 * To access an attrubute value use the dot notation:<br>
 * <dl>
 * <dt>level1.level2.text</dt>
 * <dd>accesses attrbute level1 in root class, than accesses attribute level2 in level1 class and than accesses attrbute text in level2 class.</dd>
 * <dt>levels1.text</dt>
 * <dd>accesses all text attributes in collection or array levels1 (returns an array).</dd>
 * <dt>levels1[0].text</dt>
 * <dd>accesses attribute text in first (index 0) collection or array element of levels1</dd>
 * <dt>level1.texts[0]</dt>
 * <dd>accessses first (index 0) element of collection or array attribute texts<dd>
 * </dl>
 * </p>
 * <b>Attention!</b>
 * <p>
 * Accessing collections e.g. HashSets via index doesn't make alway sense, because the order of the set isn't fix.
 * So if you set a value at index 0 and later you want to access it, it is very probable that you won't find it at index 0 anymore.
 * </p>
 * @author haui
 *
 */
public class DotNotationAccessor {

	private boolean resolveAttributesViaAccessorsOnly = true;
	private boolean accessPrivateAttributes = false;
	private boolean throwExceptionOnNullValueInAttributePath = true;

	/**
	 * Creates an {@link DotNotationAccessor} instance with default configuration:<br>
	 * only resolves attributes via getters and setters, does not access private attributes
	 * and throws exeptions on null values in attribute path.
	 */
	public DotNotationAccessor() {
		this(false, true, true);
	}

	/**
	 * Creates an {@link DotNotationAccessor} instance with given configuration.
	 *
	 * @param resolveAttributesViaAccessorsOnly if true, attributes will only be resolved via accessors (gertters/setters), else attributes will also be accessed via reflection.
	 * @param accessPrivateAttributes if true, private attributes can be accessed, too.
	 * @param throwExceptionOnNullValueInAttributePath if true, exceptions will be thrown if there are null values within the attribute path.
	 */
	public DotNotationAccessor(boolean resolveAttributesViaAccessorsOnly, boolean accessPrivateAttributes,
			boolean throwExceptionOnNullValueInAttributePath) {
		super();
		this.resolveAttributesViaAccessorsOnly = resolveAttributesViaAccessorsOnly;
		this.accessPrivateAttributes = accessPrivateAttributes;
		this.throwExceptionOnNullValueInAttributePath = throwExceptionOnNullValueInAttributePath;
	}

	/**
	 * get the value/values of an attibute, defined via dot notation.
	 *
	 * @param rootElement root class instance to start the attribute path resolvation.
	 * @param attributePath the attribute path (dot notation).
	 * @return the value/values of the final attribute in attribute path.
	 * @throws AttributeAccessExeption on error
	 */
	public Object getAttribute(Object rootElement, String attributePath)
		throws AttributeAccessExeption {
		Object result = accessAttribute(rootElement, attributePath, null, AccessorType.GETTER);
		return result;
	}

	/**
	 * set the value of an attibute, defined via dot notation.
	 *
	 * @param rootElement root class instance to start the attribute path resolvation.
	 * @param attributePath the attribute path (dot notation).
	 * @param value to set in the final attribute/attributes.
	 * @throws AttributeAccessExeption on error
	 */
	public void setAttribute(Object rootElement, String attributePath, Object value)
		throws AttributeAccessExeption {
		accessAttribute(rootElement, attributePath, value, AccessorType.SETTER);
	}

	protected Object accessAttribute(Object rootElement, String attributePath, Object value, AccessorType accessorType)
		throws AttributeAccessExeption {
		Object result = null;
		AttributeDetail[] attributes = null;
		int i = 0;
		try {
			AttributePathInterpreterResult attributePathInterpreterResult = runInterpreter(attributePath);
			attributes = new AttributeDetail[attributePathInterpreterResult.getAttributeDetailInterpreterResults().size()];
			for (AttributeDetailInterpreterResult attributeDetailInterpreterResult : attributePathInterpreterResult.getAttributeDetailInterpreterResults()) {
				attributes[i++] = new AttributeDetail(attributeDetailInterpreterResult);
			}
			result = accessAttribute(rootElement, attributes, 0, value, accessorType);
		} catch (AstInterpreterException | ReflectiveOperationException | IllegalArgumentException | IntrospectionException e) {
			throw new AttributeAccessExeption(String.format("Error accessing attribute %s", attributes[i - 1]), e);
		}
		return result;
	}

	public static AttributePathInterpreterResult runInterpreter(String attributePath)
			throws AttributeAccessExeption, AstInterpreterException {
		AttributePathParser attributePathParser = Grappa.createParser(AttributePathParser.class);
		
		AstTreeNode<String> rootNode = Parser.parseInput(AttributePathParser.class, attributePathParser.attributePath(), attributePath, true);
		
		AstInterpreter<String> attributePathInterpreter = new AstInterpreter<>();
		Long id = new Date().getTime();
		ProcessStore<String> processStore = prepareProcessStore(id);
		attributePathInterpreter.interpretBackwardOrder(AttributePathParser.class, rootNode, id);
		
		AttributePathInterpreterResult attributePathInterpreterResult = (AttributePathInterpreterResult) processStore.getVariable(AttributePathParser.ATTRIBUTE_PATH_INTERPRETER_RESULT);
		attributePathInterpreter.cleanUp(id);
		return attributePathInterpreterResult;
	}

	private static ProcessStore<String> prepareProcessStore(Long id) {
		ProcessStore<String> processStore = ProcessStore.getInstance(id);
		// set the result object
		processStore.setNewVariable(AttributePathParser.ATTRIBUTE_PATH_INTERPRETER_RESULT, new AttributePathInterpreterResult());
		processStore.setNewVariable(AttributeDetailParser.ATTRIBUTE_DETAIL_INTERPRETER_RESULT, new AttributeDetailInterpreterResult());
		return processStore;
	}

	protected Object accessAttribute(Object element, AttributeDetail[] attributes, int attributeIndex, Object value,
			AccessorType accessorType)
		throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException, InstantiationException {
		Object result = null;
		ArrayType arrayType = getArrayType(element);

		if (!arrayType.isArrayType()) {
			result = accessNonArrayTypeAttribute(element, attributes, attributeIndex, value, accessorType);
		} else {
			result = accessArrayTypeAttribute(element, attributes, attributeIndex, value, accessorType, arrayType);
		}
		return result;
	}

	private Object accessArrayTypeAttribute(Object element, AttributeDetail[] attributes, int attributeIndex, Object value,
			AccessorType accessorType, ArrayType arrayType)
		throws IllegalAccessException, InvocationTargetException, IntrospectionException, IllegalArgumentException, InstantiationException {
		Object result = null;
		Object[] array = arrayType.getArray(element);
		List<Object> results = new ArrayList<>();

		for (Object object : array) {
			Object resultObject = accessAttribute(object, attributes, attributeIndex, value, accessorType);
			switch (accessorType) {
				case SETTER:
					if (resultObject != null) {
						addResultObjects(results, resultObject);
					}
					break;

				default:
					addResultObjects(results, resultObject);
					break;
			}
		}
		if (results.size() > 0)
			result = results.toArray(new Object[results.size()]);
		return result;
	}

	private void addResultObjects(List<Object> results, Object resultObject) {
		if (resultObject.getClass().isArray()) {
			Object[] objects = (Object[]) resultObject;
			for (Object obj : objects) {
				results.add(obj);
			}
		} else {
			results.add(resultObject);
		}
	}

	protected Object accessNonArrayTypeAttribute(Object element, AttributeDetail[] attributes, int attributeIndex, Object value,
			AccessorType accessorType)
		throws IntrospectionException, IllegalAccessException, InvocationTargetException, IllegalArgumentException, InstantiationException {
		Object result = null;
		AttributeDetail attribute = attributes[attributeIndex];
		int nextIndex = attributeIndex + 1;
		if (element == null) {
			if (throwExceptionOnNullValueInAttributePath)
				throw new NullPointerException(String.format("Element %s is null!", attributes[attributeIndex - 1].getName()));
			return null;
		}
		if (attributeIndex == (attributes.length - 1)) {
			switch (accessorType) {
				case GETTER:
					result = getAttributeValueInElement(element, attribute);
					break;

				case SETTER:
					setAttributeValueInElement(element, attribute, value);
					break;
			}
		} else {
			Object nextElement = getNextElement(element, attribute);
			if(nextElement != null)
				result = accessAttribute(nextElement, attributes, nextIndex, value, accessorType);
		}
		return result;
	}

	private Object getNextElement(Object element, AttributeDetail attribute)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException {
		Object nextElement = getAttributeValueInElement(element, attribute);
		if (nextElement == null) {
			if (throwExceptionOnNullValueInAttributePath)
				throw new NullPointerException(String.format("Element %s is null!", attribute.getName()));
			return null;
		}
		ArrayType arrayType = getArrayType(nextElement);
		if (arrayType.isArrayType()) {
			if(attribute.getIndex() > -1) {
				nextElement = arrayType.getArray(nextElement)[attribute.getIndex()];
				arrayType = ArrayType.NONE;
			} else if (attribute.getMapKey() != null) {
				nextElement = arrayType.getMap(nextElement).get(attribute.getMapKey());
				arrayType = ArrayType.NONE;
			}
		}
		return nextElement;
	}

	private ArrayType getArrayType(Object element) {
		ArrayType result = null;
		if (element == null) {
			result = ArrayType.NONE;
		} else if (element.getClass().isArray()) {
			result = ArrayType.ARRAY;
		} else if (element instanceof Collection<?>) {
			result = ArrayType.COLLECTION;
		} else if (element instanceof Map<?, ?>) {
			result = ArrayType.MAP;
		} else {
			result = ArrayType.NONE;
		}
		return result;
	}

	protected Object getAttributeValueInElement(Object element, AttributeDetail attribute)
		throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object result = null;
		Method method = getGetterMethod(element.getClass(), attribute.getName());
		if (method != null) {
			result = method.invoke(element, (Object[])null);
		} else if (!resolveAttributesViaAccessorsOnly) {
			Field field = getDeclaredField(element.getClass(), attribute.getName());

			if (field != null) {
				result = getValue(element, field);
			}
		}
		result = readFromArrayTypeOrMapType(attribute, result);
		return result;
	}

	private Object readFromArrayTypeOrMapType(AttributeDetail attribute,
			Object value) {
		Object result = value;
		if ((attribute.isArrayType() || attribute.isMapType()) && (attribute.getIndex() != -1)) {
			ArrayType arrayType = getArrayType(value);

			switch (arrayType) {
				case ARRAY:
					result = Array.get(value, attribute.getIndex());
					break;

				case COLLECTION:
					int index = attribute.getIndex();
					int i = 0;
					for (Object object : (Collection<?>) value) {
						if (i++ == index) {
							result = object;
							break;
						}
					}
					break;
					
			case MAP:
				index = attribute.getIndex();
				i = 0;
				for (Object object : ((Map<?, ?>) value).values()) {
					if (i++ == index) {
						result = object;
						break;
					}
				}
				break;
				
			case NONE:
				break;
			default:
				break;
			}
		} else if (attribute.isMapType() && attribute.getMapKey() != null) {
			result = ((Map<?, ?>)value).get(attribute.getMapKey());
		}
		return result;
	}

	protected void setAttributeValueInElement(Object element, AttributeDetail attribute, Object value)
		throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Method method = getSetterMethod(element.getClass(), attribute.getName());
		Field field = null;
		if ((method == null) && !resolveAttributesViaAccessorsOnly) {
			field = getDeclaredField(element.getClass(), attribute.getName());
		} else if (method == null) {
			throw new IllegalAccessException("Access via reflection was not permitted!");
		}

		writeToArrayTypeOrMapType(element, attribute, value, method, field);
	}

	private void writeToArrayTypeOrMapType(Object element,
			AttributeDetail attribute, Object value, Method method, Field field)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException, InstantiationException {
		if (attribute.isArrayType() || attribute.isMapType()) {
			Object result = null;

			if (method != null) {
				Method getMethod = getGetterMethod(element.getClass(), attribute.getName());
				result = getMethod.invoke(element, (Object[])null);
			} else {
				if (field != null)
					result = getValue(element, field);
			}

			ArrayType arrayType = getArrayType(result);

			switch (arrayType) {
				case ARRAY:
					result = setValueInArray(result, attribute, value);
					break;

				case COLLECTION:
					result = setValueInCollection((Collection<?>) result, attribute, value);
					break;
					
				case MAP:
					result = setValueInMap((Map<?, ?>)result, attribute, value);
					break;

				case NONE:
					break;
			default:
				break;
			}
			if (method != null)
				method.invoke(element, result);
			else {
				if (field != null)
					setValue(element, field, result);
			}
		} else if (!attribute.isArrayType() && !attribute.isMapType()) {
			if (method != null)
				method.invoke(element, value);
			else {
				if (field != null)
					setValue(element, field, value);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object setValueInMap(Map<?, ?> map, AttributeDetail attribute, Object value)
		throws InstantiationException, IllegalAccessException {
		Map copiedObjects = (Map) map.getClass().newInstance();
		if (attribute.getIndex() != -1) {
			int index = attribute.getIndex();
			int i = 0;
			for (Entry entry : map.entrySet()) {
				if (i++ == index)
					copiedObjects.put(entry.getKey(), value);
				else
					copiedObjects.put(entry.getKey(), entry.getValue());
			}
		} else if (attribute.getMapKey() != null) {
			for (Entry entry : map.entrySet()) {
				copiedObjects.put(entry.getKey(), entry.getValue());
			}
			copiedObjects.put(attribute.getMapKey(), value);
		}else if (attribute.getIndex() == -1) {
			for (Entry entry : map.entrySet()) {
				copiedObjects.put(entry.getKey(), value);
			}
		}
		return copiedObjects;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object setValueInCollection(Collection<?> collection, AttributeDetail attribute, Object value)
		throws InstantiationException, IllegalAccessException {
		Collection copiedObjects = (Collection) collection.getClass().newInstance();
		if (attribute.getIndex() == -1) {
			for (int i = 0; i < collection.size(); ++i) {
				copiedObjects.add(value);
			}
		} else {
			int index = attribute.getIndex();
			int i = 0;
			for (Object object : collection) {
				if (i++ == index)
					copiedObjects.add(value);
				else
					copiedObjects.add(object);
			}
		}
		return copiedObjects;
	}

	private Object setValueInArray(Object array, AttributeDetail attribute, Object value) {
		if (attribute.getIndex() == -1) {
			int count = Array.getLength(array);
			for (int i = 0; i < count; ++i) {
				Array.set(array, i, value);
			}
		} else {
			int index = attribute.getIndex();
			Array.set(array, index, value);
		}
		return array;
	}

	protected Method getGetterMethod(Class<? extends Object> elementClass, String attribute)
		throws IntrospectionException {
		Method method = getAccessorMethod(elementClass, attribute, AccessorType.GETTER);
		return method;
	}

	protected Method getSetterMethod(Class<? extends Object> elementClass, String attribute)
		throws IntrospectionException {
		Method method = getAccessorMethod(elementClass, attribute, AccessorType.SETTER);
		return method;
	}

	protected Method getAccessorMethod(Class<? extends Object> elementClass, String attribute, AccessorType accessorType)
		throws IntrospectionException {
		Method method = null;
		BeanInfo beanInfo = Introspector.getBeanInfo(elementClass);
		for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
			if (propertyDescriptor.getName().equals(attribute)) {
				switch (accessorType) {
					case SETTER:
						method = propertyDescriptor.getWriteMethod();
						break;

					case GETTER:
						method = propertyDescriptor.getReadMethod();
						break;
				}
			}
			if (method != null)
				break;
		}
		return method;
	}

	/**
	 * Returns a field for a specified class including all inherited ones.
	 */
	protected final Field getDeclaredField(Class<?> clazz, String fieldName) {
		Field field = null;

		while (clazz != null) {
			try {
				field = clazz.getDeclaredField(fieldName);
				if (field == null)
					clazz = clazz.getSuperclass();
				else
					break;
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			}
		}
		return field;
	}

	/**
	 * Returns the value of the given field or null.
	 */
	protected Object getValue(Object instance, Field field)
		throws IllegalArgumentException, IllegalAccessException {
		boolean accessible = field.isAccessible();
		if (accessPrivateAttributes)
			field.setAccessible(true);
		Object value = field.get(instance);
		if (accessPrivateAttributes)
			field.setAccessible(accessible);
		return value;
	}

	/**
	 * Set the value in the given field.
	 */
	protected void setValue(Object instance, Field field, Object value)
		throws IllegalArgumentException, IllegalAccessException {
		boolean accessible = field.isAccessible();
		if (accessPrivateAttributes)
			field.setAccessible(true);
		field.set(instance, value);
		if (accessPrivateAttributes)
			field.setAccessible(accessible);
	}
}
