package com.ramyarecipes;

import java.io.IOException;

import com.ramyarecipes.xml.XmlFileReader;

public class App 
{
	public static void main( String[] args ) throws IOException
	{
		XmlFileReader.fileToString("C:\\Users\\anand\\Desktop\\"
				+ "ramyarecipesblog-wordpress-com-2017-12-25-01_42_47-xsll40uv1bhcfkrpvsqiv6kecrbqjdi7\\"
				+ "ramyarecipesblog.wordpress.com-2017-12-25-01_42_42\\"
				+ "ramyarecipes.wordpress.2017-12-25.001.xml");
	}
}
