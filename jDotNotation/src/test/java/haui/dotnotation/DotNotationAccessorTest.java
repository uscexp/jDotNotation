/*
 * (C) 2014 haui
 */
package haui.dotnotation;

import haui.dotnotation.exception.AttributeAccessExeption;
import haui.dotnotation.testclasses.RootClass;
import haui.dotnotation.testclasses.SimpleChildLevel3Class;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author haui
 *
 */
public class DotNotationAccessorTest {

	@Test
	public void testGetAttributeAccessorsOnly()
		throws Exception {

		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(true, false, false);
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.simpleString";

		Object result = dotNotationAccessorSUT.getAttribute(rootClass, attributePath);

		Assert.assertNotNull(result);
		Assert.assertEquals("Level 3", result);
	}

	@Test
	public void testSetAttributeAccessorsOnly()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(true, false, false);
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.simpleString";
		String value = "New level 3";

		dotNotationAccessorSUT.setAttribute(rootClass, attributePath, value);
		Object result = dotNotationAccessorSUT.getAttribute(rootClass, attributePath);

		Assert.assertNotNull(result);
		Assert.assertEquals(value, result);
	}

	@Test
	public void testSetAttributeAccessorsOnlyWithArrays()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(true, false, false);
		RootClass rootClass = new RootClass();
		String attributePath = "arrayChildLevel1Classes.arrayChildLevel2Classes.arrayChildLevel3Classes.simpleString";
		String value = "New level 3";

		dotNotationAccessorSUT.setAttribute(rootClass, attributePath, value);

		Assert.assertEquals(value,
			rootClass.getArrayChildLevel1Classes().get(0).getArrayChildLevel2Classes().get(0).getArrayChildLevel3Classes().get(0)
				.getSimpleString());
		Assert.assertEquals(value,
			rootClass.getArrayChildLevel1Classes().get(1).getArrayChildLevel2Classes().get(1).getArrayChildLevel3Classes().get(1)
				.getSimpleString());

		Object result = dotNotationAccessorSUT.getAttribute(rootClass, attributePath);

		Assert.assertNotNull(result);
		Assert.assertTrue(result.getClass().isArray());
		Object[] objects = ((Object[]) result);
		Assert.assertEquals(8, objects.length);
		for (int i = 0; i < objects.length; i++) {
			Assert.assertEquals(value, objects[i]);
		}
	}

	@Test
	public void testSetAttributeAccessorsOnlyIndexedArray()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(true, false, false);
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.arrayChildLevel2Classes[1].simpleChildLevel3Class.simpleString";
		String value = "New level 3";

		dotNotationAccessorSUT.setAttribute(rootClass, attributePath, value);

		Assert.assertEquals("Level 3",
			rootClass.getSimpleChildLevel1Class().getArrayChildLevel2Classes().get(0).getSimpleChildLevel3Class().getSimpleString());
		Assert.assertEquals(value,
			rootClass.getSimpleChildLevel1Class().getArrayChildLevel2Classes().get(1).getSimpleChildLevel3Class().getSimpleString());

		Object result = dotNotationAccessorSUT.getAttribute(rootClass, attributePath);

		Assert.assertNotNull(result);
		Assert.assertEquals(value, result);
	}

	@Test
	public void testSetAttributeReflectionPrivateAccess()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(false, true, false);
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.privatePrimitiveIntArray";
		int[] value = { 0, 0, 0 };

		dotNotationAccessorSUT.setAttribute(rootClass, attributePath, value);

		SimpleChildLevel3Class simpleChildLevel3Class = rootClass.getSimpleChildLevel1Class().getSimpleChildLevel2Class()
			.getSimpleChildLevel3Class();
		Field field = simpleChildLevel3Class.getClass().getDeclaredField("privatePrimitiveIntArray");
		field.setAccessible(true);
		Object resultValue = field.get(simpleChildLevel3Class);
		Assert.assertEquals(0, ((int[])resultValue)[0]);
		Assert.assertEquals(0, ((int[])resultValue)[1]);
		Assert.assertEquals(0, ((int[])resultValue)[2]);

		Object result = dotNotationAccessorSUT.getAttribute(rootClass, attributePath);

		Assert.assertNotNull(result);
		Assert.assertEquals(value, result);
	}

	@Test
	public void testSetAttributeReflectionPrivateAccessEndArray()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(false, true, false);
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.privatePrimitiveIntArray[]";
		int value = 0;

		dotNotationAccessorSUT.setAttribute(rootClass, attributePath, value);

		SimpleChildLevel3Class simpleChildLevel3Class = rootClass.getSimpleChildLevel1Class().getSimpleChildLevel2Class()
			.getSimpleChildLevel3Class();
		Field field = simpleChildLevel3Class.getClass().getDeclaredField("privatePrimitiveIntArray");
		field.setAccessible(true);
		Object resultValue = field.get(simpleChildLevel3Class);
		Assert.assertEquals(0, ((int[])resultValue)[0]);
		Assert.assertEquals(0, ((int[])resultValue)[1]);
		Assert.assertEquals(0, ((int[])resultValue)[2]);

		Object result = dotNotationAccessorSUT.getAttribute(rootClass, attributePath);

		Assert.assertNotNull(result);
		Assert.assertEquals(0, ((int[])result)[0]);
		Assert.assertEquals(0, ((int[])result)[1]);
		Assert.assertEquals(0, ((int[])result)[2]);
	}

	@Test
	public void testSetAttributeReflectionPrivateAccessEndIndexedArray()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(false, true, false);
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.privatePrimitiveIntArray[1]";
		int value = 0;

		dotNotationAccessorSUT.setAttribute(rootClass, attributePath, value);

		SimpleChildLevel3Class simpleChildLevel3Class = rootClass.getSimpleChildLevel1Class().getSimpleChildLevel2Class()
			.getSimpleChildLevel3Class();
		Field field = simpleChildLevel3Class.getClass().getDeclaredField("privatePrimitiveIntArray");
		field.setAccessible(true);
		Object resultValue = field.get(simpleChildLevel3Class);
		Assert.assertEquals(1, ((int[])resultValue)[0]);
		Assert.assertEquals(0, ((int[])resultValue)[1]);
		Assert.assertEquals(3, ((int[])resultValue)[2]);

		Object result = dotNotationAccessorSUT.getAttribute(rootClass, attributePath);

		Assert.assertNotNull(result);
		Assert.assertEquals(0, result);
	}
	@Test
	public void testSetAttributeReflectionPrivateAccessEndCollection()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(false, true, false);
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.privateStringList[]";
		String value = "z";

		dotNotationAccessorSUT.setAttribute(rootClass, attributePath, value);

		SimpleChildLevel3Class simpleChildLevel3Class = rootClass.getSimpleChildLevel1Class().getSimpleChildLevel2Class()
			.getSimpleChildLevel3Class();
		Field field = simpleChildLevel3Class.getClass().getDeclaredField("privateStringList");
		field.setAccessible(true);
		Object resultValue = field.get(simpleChildLevel3Class);
		List<?> collection = (List<?>) resultValue;
		Assert.assertEquals(value, collection.get(0));
		Assert.assertEquals(value, collection.get(1));
		Assert.assertEquals(value, collection.get(2));

		Object result = dotNotationAccessorSUT.getAttribute(rootClass, attributePath);

		Assert.assertNotNull(result);
		collection = (List<?>) result;
		Assert.assertEquals(value, collection.get(0));
		Assert.assertEquals(value, collection.get(1));
		Assert.assertEquals(value, collection.get(2));
	}

	@Test
	public void testSetAttributeReflectionPrivateAccessEndIndexedCollection()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor();
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.privateStringList[1]";
		String value = "z";

		dotNotationAccessorSUT.setAttribute(rootClass, attributePath, value);

		SimpleChildLevel3Class simpleChildLevel3Class = rootClass.getSimpleChildLevel1Class().getSimpleChildLevel2Class()
			.getSimpleChildLevel3Class();
		Field field = simpleChildLevel3Class.getClass().getDeclaredField("privateStringList");
		field.setAccessible(true);
		Object resultValue = field.get(simpleChildLevel3Class);
		List<?> collection = (List<?>) resultValue;
		Assert.assertEquals("a", collection.get(0));
		Assert.assertEquals(value, collection.get(1));
		Assert.assertEquals("c", collection.get(2));

		Object result = dotNotationAccessorSUT.getAttribute(rootClass, attributePath);

		Assert.assertNotNull(result);
		Assert.assertEquals(value, result);
	}

	@Test(expected = AttributeAccessExeption.class)
	public void testSetAttributeReflectionPrivateAccessProhibitedEndIndexedCollection()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(false, false, false);
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.privateStringList[1]";
		String value = "z";

		dotNotationAccessorSUT.setAttribute(rootClass, attributePath, value);
	}

	@Test(expected = AttributeAccessExeption.class)
	public void testSetAttributeAccessorOnlyWithoutAccessorEndIndexedCollection()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(true, false, false);
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.privateStringList[1]";
		String value = "z";

		dotNotationAccessorSUT.setAttribute(rootClass, attributePath, value);
	}

	@Test(expected = NullPointerException.class)
	public void testSetAttributeAccessorOnlyWithNullValueInPath()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(true, false, true);
		RootClass rootClass = new RootClass();
		
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class2.simpleString";
		String value = "z";

		dotNotationAccessorSUT.setAttribute(rootClass, attributePath, value);
	}

	@Test
	public void testSetAttributeAccessorsOnlyEndIndexedCollection()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(true, false, false);
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.stringCollection[1]";
		String value = "z";

		dotNotationAccessorSUT.setAttribute(rootClass, attributePath, value);

		SimpleChildLevel3Class simpleChildLevel3Class = rootClass.getSimpleChildLevel1Class().getSimpleChildLevel2Class()
			.getSimpleChildLevel3Class();
		Field field = simpleChildLevel3Class.getClass().getDeclaredField("stringCollection");
		field.setAccessible(true);
		Object resultValue = field.get(simpleChildLevel3Class);
		Set<?> collection = (TreeSet<?>) resultValue;
		Iterator<?> it = collection.iterator();
		Assert.assertEquals("a", it.next());
		Assert.assertEquals("c", it.next());
		Assert.assertEquals(value, it.next());

		Object result = dotNotationAccessorSUT.getAttribute(rootClass, attributePath);

		Assert.assertNotNull(result);
		Assert.assertEquals("c", result);
	}
}
