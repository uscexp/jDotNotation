/*
 * ----------------------------------------------------------------------------
 * Copyright 2009 - 2014 by PostFinance AG - all rights reserved
 * ----------------------------------------------------------------------------
 */
/*
 * (C) 2014 haui
 */
package haui.dotnotation;

import haui.dotnotation.testclasses.RootClass;
import haui.dotnotation.testclasses.SimpleChildLevel3Class;

import java.lang.reflect.Field;

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

		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(true, false);
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.simpleString";

		Object result = dotNotationAccessorSUT.getAttribute(rootClass, attributePath);

		Assert.assertNotNull(result);
		Assert.assertEquals("Level 3", result);
	}

	@Test
	public void testSetAttributeAccessorsOnly()
		throws Exception {
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(true, false);
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
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(true, false);
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
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(true, false);
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
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(false, true);
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
		DotNotationAccessor dotNotationAccessorSUT = new DotNotationAccessor(false, true);
		RootClass rootClass = new RootClass();
		String attributePath = "simpleChildLevel1Class.simpleChildLevel2Class.simpleChildLevel3Class.privatePrimitiveIntArray[]";
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
}
