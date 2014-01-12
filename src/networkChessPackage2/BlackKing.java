package networkChessPackage2;

import java.awt.Point;

public class BlackKing extends BlackPiece
{
	public BlackKing()
	{
		typeSet();
		setUp();
	}
	
	public BlackKing(Point p, int size)
	{
		typeSet();
		setUp();
		setLocation(p);
		cellSize = size;
	}
	
	public BlackKing(Point p)
	{
		typeSet();
		setUp();
		setLocation(p);
	}
	
	public BlackKing(int x, int y, int size)
	{
		typeSet();
		setUp();
		setLocation(x, y);
		cellSize = size;
	}
	
	public BlackKing(int x, int y)
	{
		typeSet();
		setUp();
		setLocation(x, y);
	}
	
	public BlackKing(int size)
	{
		typeSet();
		cellSize = size;
	}
	
	private void typeSet()
	{
		type = PieceType.King;
	}
}
