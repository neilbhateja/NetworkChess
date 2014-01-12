package networkChessPackage2;

import java.awt.Point;

public class WhiteKing extends WhitePiece
{
	public WhiteKing()
	{
		typeSet();
		setUp();
	}
	
	public WhiteKing(Point p, int size)
	{
		typeSet();
		setUp();
		setLocation(p);
		cellSize = size;
	}
	
	public WhiteKing(Point p)
	{
		typeSet();
		setUp();
		setLocation(p);
	}
	
	public WhiteKing(int x, int y, int size)
	{
		typeSet();
		setUp();
		setLocation(x, y);
		cellSize = size;
	}
	
	public WhiteKing(int x, int y)
	{
		typeSet();
		setUp();
		setLocation(x, y);
	}
	
	public WhiteKing(int size)
	{
		typeSet();
		cellSize = size;
	}
	
	private void typeSet()
	{
		type = PieceType.King;
	}
}
