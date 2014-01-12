package networkChessPackage2;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Client
{
	public Client()
	{
		
	}
	
	public static void main(String[] args)
	{
		String ipAddress = new String(JOptionPane.showInputDialog("IP Adress?"));
		String portString = new String(JOptionPane.showInputDialog("Port Number?"));
		int portNum = java.lang.Integer.parseInt(portString);
		//String imageFolder = new String(JOptionPane.showInputDialog("Location of images?"));
		
		
		Socket rSocket = new Socket();
		InetAddress ia = null;
		try
		{
			ia = InetAddress.getByName(ipAddress);
		}
		catch(UnknownHostException e)
		{
			System.err.println("Unknown host.");
			e.printStackTrace();
		}
		
		//Makes socket.
		try
		{
			rSocket = new Socket(ia, portNum);
		}
		  
		catch (UnknownHostException e)
		{
			System.err.println("Unknown host.");
			e.printStackTrace();
		}
		  
		catch (IOException e)
		{
		   System.err.println("IOException occurred.");
		   e.printStackTrace();
		}
		
		//Gets ins and outs.
		InputStream inStream = null;
		BufferedInputStream buffStream = null;
		OutputStream outStream = null;
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		
		try
		{
			inStream = rSocket.getInputStream();
		}
		catch (IOException e)
		{
			System.err.println("Could not get InputStream from clientSocket.");
			e.printStackTrace();
		}
		
		try
		{
			outStream = rSocket.getOutputStream();
		}
		catch (IOException e)
		{
			System.err.println("Could not get OutputStream from clientSocket.");
			e.printStackTrace();
		}
		
		try
		{
			out = new ObjectOutputStream(outStream);
			out.flush();
		}
		catch (IOException e)
		{
			System.err.println("Could not get ObjectOutputStream for clientSocket.");
			e.printStackTrace();
		}
		
		try
		{
			buffStream = new BufferedInputStream(inStream);
			in = new ObjectInputStream(buffStream);
		}
		catch (IOException e)
		{
			System.err.println("Could not get ObjectInputStream for clientSocket.");
			e.printStackTrace();
		}
		
		//Game setup.
		ChessFrame chess = new ChessFrame();
		chess.setTitle("Chess");
		chess.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		compo = new ChessComponent(true, cellSize, chess, out);
		chess.add(compo);
		chess.setVisible(true);
		
		looper(in);

	}
	
	public static void looper(ObjectInputStream ois)
	{	
		ObjectInputStream in = ois;
		
			while (true)
			{
				String s = null;
				
						try
						{	
							s = (String) in.readObject();
							doMove(s);
						}
						catch (NullPointerException e)
						{
							System.err.println("NullPointerException");
							e.printStackTrace();
						}
						catch (ClassNotFoundException e)
						{
							System.err.println("class not found");
							e.printStackTrace();
						}
						catch (IOException e)
						{
							System.err.println("IO problem");
							e.printStackTrace();
						}
				
			}
		
	}
	
	public static void doMove(String theString)
	{
		String s = theString;
			String[] points = s.split(" ");
			
			int pX = (int) Double.parseDouble(points[0]);
			int pY = (int) Double.parseDouble(points[1]);
			int qX = (int) Double.parseDouble(points[2]);
			int qY = (int) Double.parseDouble(points[3]);
			
			
			try 
			{
				Point p = new Point(pX, pY);
				Point q = new Point(qX, qY);
				compo.move(p, q);
			}
			catch (NullPointerException n)
			{
				System.err.println("Null pointer exception.");
				n.printStackTrace();
			}
	}
	public static int cellSize = 48;
	public static ChessComponent compo;
	public static int FRAME_WIDTH = 720;
	public static int FRAME_HEIGHT = 496;
	


}
