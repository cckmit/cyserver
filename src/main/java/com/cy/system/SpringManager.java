package com.cy.system;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SpringManager
{
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName, Class<T> cls)
	{
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		return (T) context.getBean(beanName);
	}
}
