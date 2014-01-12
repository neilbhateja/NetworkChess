package networkChessPackage2;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import networkChessPackage2.ChessComponent;
import networkChessPackage2.ChessFrame;

public class Server
{
	public Server()
	{
		
	}
	
	public static void main(String[] args)
	{	
		// Make server socket.
		ServerSocket sock = null;
		int portTest;
		
		for (portTest = 1000; portTest < 3000; portTest++)
		{
			try
			{
				sock = new ServerSocket(portTest);
				break;
			}
			catch(IOException e)
			{
				if (portTest == 2999)
				{
					System.err.println("Could not make ServerSocket at this port.");
					e.printStackTrace();
					System.exit(-1);
				}
				
			}
		}
		
		int portNum = portTest;
		
		JOptionPane.showMessageDialog(null, ("Port number is " + portNum + "."));
		
		
		// Get clientSocket.
		
		Socket clientSocket = new Socket();
		
		try
		{
			clientSocket = sock.accept();
		}
		catch (Exception e)
		{
			System.err.println("Could not get client socket.");
			e.printStackTrace();
		}
		
		// Now get in and out for clientSocket.
		
		InputStream inStream = null;
		BufferedInputStream buffStream = null;
		OutputStream outStream = null;
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		
		
		try
		{
			inStream = clientSocket.getInputStream();
		}
		catch (IOException e)
		{
			System.err.println("Could not get InputStream from clientSocket.");
			e.printStackTrace();
		}
		
		try
		{
			outStream = clientSocket.getOutputStream();
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
		
		//Frame and component stuff.
		ChessFrame chess = new ChessFrame();
		chess.setTitle("Chess");
		chess.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		compo = new ChessComponent(false, cellSize, chess, out);
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
	
	public static void doMove(String tehString)
	{
		String s = tehString;
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
	public static ChessComponent compo = new ChessComponent();
	public static ChessFrame chess = new ChessFrame();
	public static int FRAME_WIDTH = 720;
	public static int FRAME_HEIGHT = 496;


}
