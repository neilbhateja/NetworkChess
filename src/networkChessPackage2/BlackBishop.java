package networkChessPackage2;

import java.awt.Point;

public class BlackBishop extends BlackPiece
{
	public BlackBishop()
	{
		typeSet();
		setUp();
	}
	
	public BlackBishop(Point p, int size)
	{
		typeSet();
		setUp();
		setLocation(p);
		cellSize = size;
	}
	
	public BlackBishop(Point p)
	{
		typeSet();
		setUp();
		setLocation(p);
	}
	
	public BlackBishop(int x, int y, int size)
	{
		typeSet();
		setUp();
		setLocation(x, y);
		cellSize = size;
	}
	
	public BlackBishop(int x, int y)
	{
		typeSet();
		setUp();
		setLocation(x, y);
	}
	
	public BlackBishop(int size)
	{
		typeSet();
		cellSize = size;
	}
	
	private void typeSet()
	{
		type = PieceType.Bishop;
	}
}
