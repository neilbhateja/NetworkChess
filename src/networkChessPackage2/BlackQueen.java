package networkChessPackage2;

import java.awt.Point;

public class BlackQueen extends BlackPiece
{
	public BlackQueen()
	{
		typeSet();
		setUp();
	}
	
	public BlackQueen(Point p, int size)
	{
		typeSet();
		setUp();
		setLocation(p);
		cellSize = size;
	}
	
	public BlackQueen(Point p)
	{
		typeSet();
		setUp();
		setLocation(p);
	}
	
	public BlackQueen(int x, int y, int size)
	{
		typeSet();
		setUp();
		setLocation(x, y);
		cellSize = size;
	}
	
	public BlackQueen(int x, int y)
	{
		typeSet();
		setUp();
		setLocation(x, y);
	}
	
	public BlackQueen(int size)
	{
		typeSet();
		cellSize = size;
	}
	
	private void typeSet()
	{
		type = PieceType.Queen;
	}
}
