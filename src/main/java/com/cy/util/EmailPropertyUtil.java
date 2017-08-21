package com.cy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class EmailPropertyUtil
{
	static Logger logger = Logger.getLogger(EmailPropertyUtil.class);

	private static EmailPropertyUtil propUtil = null;

	private Properties property;

	private FileInputStream input;

	private EmailPropertyUtil(String path)
	{
		try
		{
			input = new FileInputStream(path);
			property = new Properties();
			property.load(input);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				input.close();
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public static void doInit()
	{
		Resource resource = new ClassPathResource("Mail.properties");
		try
		{
			EmailPropertyUtil.doInit(resource.getFile().getCanonicalPath());
		}
		catch (IOException e)
		{
			logger.error("EmailPropertyUtil have not initial successful!", e);
		}
	}

	public static void doInit(String path)
	{
		if (propUtil == null)
		{
			propUtil = new EmailPropertyUtil(path);
		}
	}

	public static EmailPropertyUtil getInstance()
	{
		if (propUtil == null)
		{
			doInit();
		}
		return propUtil;
	}

	public String getProperty(String key)
	{
		if (propUtil == null)
		{
			doInit();
		}
		return property.getProperty(key).trim();
	}

}
