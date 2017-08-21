package com.cy.system;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ImageUtil
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ImageUtil.class);

	private String destFile;
	private File file;
	private String destFileName;

	public ImageUtil(String resourceFile)
	{
		file = new File(resourceFile);
		destFile = file.getPath().substring(0, file.getPath().indexOf(file.getName())) + "thin" + file.getName();
		destFileName = "thin" + file.getName();
	}

	public String resize(int w, int h)
	{
		try
		{
			Thumbnails.of(file).size(640, 320).rotate(0).toFile(new File(destFile));
		} catch (IOException e)
		{
			logger.error(e, e);
			destFileName = "";
		}
		return destFileName;
	}

}
