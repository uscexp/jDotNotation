/*
 * (C) 2014 haui
 */
package haui.dotnotation;

import haui.dotnotation.exception.AttributeAccessExeption;

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
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author haui
 *
 */
public class DotNotationAccessor {

	private boolean resolveAttributesViaAccessorsOnly = true;
	private boolean accessPrivateAttributes = false;
	private boolean throwExceptionOnNullValueInAttributePath = true;

	public DotNotationAccessor() {
		this(false, true, true);
	}

	public DotNotationAccessor(boolean resolveAttributesViaAccessorsOnly, boolean accessPrivateAttributes, boolean throwExceptionOnNullValueInAttributePath) {
		super();
		this.resolveAttributesViaAccessorsOnly = resolveAttributesViaAccessorsOnly;
		this.accessPrivateAttributes = accessPrivateAttributes;
		this.throwExceptionOnNullValueInAttributePath = throwExceptionOnNullValueInAttributePath;
	}

	public Object getAttribute(Object rootElement, String attributePath)
		throws AttributeAccessExeption {
		Object result = accessAttribute(rootElement, attributePath, null, AccessorType.GETTER);
		return result;
	}

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
			StringTokenizer stringTokenizer = new StringTokenizer(attributePath, ".", false);
			attributes = new AttributeDetail[stringTokenizer.countTokens()];
			while (stringTokenizer.hasMoreTokens()) {
				attributes[i++] = new AttributeDetail(stringTokenizer.nextToken());
			}
			result = accessAttribute(rootElement, attributes, 0, value, accessorType);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException |
				InstantiationException e) {
			throw new AttributeAccessExeption(String.format("Error accessing attribute %s", attributes[i-1]), e);
		}
		return result;
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
			if(throwExceptionOnNullValueInAttributePath)
				throw new NullPointerException(String.format("Element %s is null!", attributes[attributeIndex-1].getName()));
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
			Object nextElement = getAttributeValueInElement(element, attribute);
			if (nextElement == null) {
				if(throwExceptionOnNullValueInAttributePath)
					throw new NullPointerException(String.format("Element %s is null!", attribute.getName()));
				return null;
			}
			ArrayType arrayType = getArrayType(nextElement);
			if (arrayType.isArrayType() && (attribute.getIndex() > -1)) {
				nextElement = arrayType.getArray(nextElement)[attribute.getIndex()];
				arrayType = ArrayType.NONE;
			}
			result = accessAttribute(nextElement, attributes, nextIndex, value, accessorType);
		}
		return result;
	}

	private ArrayType getArrayType(Object element) {
		ArrayType result = null;
		if (element == null) {
			result = ArrayType.NONE;
		} else if (element.getClass().isArray()) {
			result = ArrayType.ARRAY;
		} else if (element instanceof Collection<?>) {
			result = ArrayType.COLLECTION;
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
			result = method.invoke(element, null);
		} else if (!resolveAttributesViaAccessorsOnly) {
			Field field = getDeclaredField(element.getClass(), attribute.getName());

			if (field != null) {
				result = getValue(element, field);
			}
		}
		if (attribute.isArrayType() && attribute.getIndex() != -1) {
			ArrayType arrayType = getArrayType(result);
			
			switch (arrayType) {
			case ARRAY:
				result = Array.get(result, attribute.getIndex());
				break;

			case COLLECTION:
				int index = attribute.getIndex();
				int i = 0;
				for (Object object : (Collection<?>)result) {
					if (i++ == index) {
						result = object;
						break;
					}
				}
				break;
			}
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

		if (!attribute.isArrayType()) {
			if (method != null)
				method.invoke(element, value);
			else {
				if (field != null)
					setValue(element, field, value);
			}
		} else {
			Object result = null;

			if (method != null) {
				Method getMethod = getGetterMethod(element.getClass(), attribute.getName());
				result = getMethod.invoke(element, null);
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

				case NONE:
					break;
			}
			if (method != null)
				method.invoke(element, result);
			else {
				if (field != null)
					setValue(element, field, result);
			}
		}
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
