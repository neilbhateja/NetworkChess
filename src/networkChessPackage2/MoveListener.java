package networkChessPackage2;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MoveListener implements MouseListener 
{
	public MoveListener() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public MoveListener(ChessComponent cc, ObjectOutputStream oos)
	{
		compo = cc;
		out = oos;
	}

	public void mouseClicked(MouseEvent event)
	{
		
	}
	
	public void mouseEntered(MouseEvent event)
	{

	}
	
	public void mouseExited(MouseEvent event)
	{
		
	}
	
	public void mousePressed(MouseEvent event)
	{
		p = event.getPoint();
	}
	
	public void mouseReleased(MouseEvent event)
	{
		if (!(event.getPoint().equals(p)))
		{
			q = event.getPoint();
			mouseDragged(p,q);
		}
	}

	public void mouseDragged(Point a, Point b)
	{
		if (compo.userCanMove)
		{
			String send = (a.getX() + " " + a.getY() + " " + b.getX() + " " + b.getY());
			compo.move(a,b);
			try
			{
				out.writeObject(send);
				out.flush();
				out.reset();
			}
			catch (IOException e)
			{
				System.err.println("MoveListener could not send.");
				e.printStackTrace();
			}
		}
	}

	public Point p;
	public Point q;
	public ChessComponent compo;
	public ObjectOutputStream out;
}

