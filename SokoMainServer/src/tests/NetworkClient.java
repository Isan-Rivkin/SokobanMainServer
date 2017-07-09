	package tests;

	import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import common_data.level.Level;

public class NetworkClient 
{

		public static void sendLevel(String ip, int port, Level level) throws IOException
		{

				Socket theServer = new Socket(ip, port);
				if (theServer.isConnected() && level != null) 
				{
					System.out.println("connected to server");
					String p = "7";;
					PrintWriter writer = new PrintWriter(new OutputStreamWriter(theServer.getOutputStream()));
					writer.println(p);
					writer.flush();
					encodeXML(level, theServer.getOutputStream());
					Level l =(Level)decodeXML(theServer.getInputStream());
					System.out.println("Got from client " + l.isPlayerWon());
					theServer.close();
				}
				else 
				{
					System.out.println("Cannot connect to server, Me so sad :`( (or level is null means u so sad =D )");
				}

		}

		public static void encodeXML(Object obj, OutputStream out) throws FileNotFoundException {
			XMLEncoder e = new XMLEncoder(new BufferedOutputStream(out));
			e.writeObject(obj);
			e.close();

		}
		public static Object decodeXML(InputStream in) throws FileNotFoundException{
			XMLDecoder d=new XMLDecoder(new BufferedInputStream(in));
			Object obj=d.readObject();
			d.close();
			return obj;
		}

	}

