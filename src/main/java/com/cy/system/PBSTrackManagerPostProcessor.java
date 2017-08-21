package com.cy.system;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class PBSTrackManagerPostProcessor implements BeanPostProcessor
{

	public Object postProcessAfterInitialization(Object obj, String s) throws BeansException
	{
		if (obj instanceof GetDictionaryInfo)
		{
			((GetDictionaryInfo) obj).getAllInfo();
		}
		return obj;
	}

	public Object postProcessBeforeInitialization(Object object, String s) throws BeansException
	{
		return object;
	}

}
