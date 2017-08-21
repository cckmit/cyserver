package com.cy.shortmessage;

public class ShortMessageFactory {

	public static Object createInstance(Class cls) {
		Object obj = null;
		try {
			obj = cls.newInstance();
		} catch (Exception e) {
			obj = null;
		}
		return obj;
	}
}
