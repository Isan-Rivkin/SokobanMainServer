package model.restclient;

import java.io.IOException;
import java.io.PrintWriter;

public class LogDescriptorTxt 
{
	public static void writeLogTxt(String path , String text)
	{
		try{
		    PrintWriter writer = new PrintWriter(path, "UTF-8");
		    writer.println(text);
		    writer.close();
		} catch (IOException e)
		{
		   // do something
		}
	}
}
