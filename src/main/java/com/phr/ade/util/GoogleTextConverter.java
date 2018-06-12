package com.phr.ade.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.beanutils.Converter;

import com.google.appengine.api.datastore.Text;

public class GoogleTextConverter implements Converter
{
	
	@Override
	public Object convert(Class googleText, Object value)
	{
		String _fileName = (String) value;
		
		String _messageValue = "";
		Text _message = null;
		try
		{
			_messageValue = readFeedFile(_fileName);
			_message = new Text(_messageValue);
		} catch (IOException e)
		{
			e.printStackTrace();
			_messageValue = "ERR IN READING FILE FROM " + _fileName;
		}
		
		return _message;
	}
	
	/**
	 * Read the template file and return the complete file
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	private String readFeedFile(String fileName) throws IOException
	{
		
		FileReader fr = null;
		File f = new File(fileName);
		StringBuffer _sb = new StringBuffer();
		
		if (f.exists())
		{
			try
			{
				fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				StringBuffer sb = new StringBuffer();
				String eachLine = "";
				while (eachLine != null)
				{
					eachLine = br.readLine();
					
					if (eachLine != null)
					{
						_sb.append(eachLine);
					}
				}
			} finally
			{
				fr.close();
			}
		}
		return _sb.toString();
	}
	
}
