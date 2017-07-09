package tests;

import java.io.IOException;

import common_data.level.Level;
import searching.search_util.SearchUtil;

public class RunClient
{
	public static void main(String[] args) throws IOException
	{
		
		Level level = SearchUtil.loadLevel("");
		NetworkClient.sendLevel("", 234, level);
	}

}
