package networkChessPackage2;

import java.awt.Point;

public class WhiteQueen extends WhitePiece
{
	public WhiteQueen()
	{
		typeSet();
		setUp();
	}
	
	public WhiteQueen(Point p, int size)
	{
		typeSet();
		setUp();
		setLocation(p);
		cellSize = size;
	}
	
	public WhiteQueen(Point p)
	{
		typeSet();
		setUp();
		setLocation(p);
	}
	
	public WhiteQueen(int x, int y, int size)
	{
		typeSet();
		setUp();
		setLocation(x, y);
		cellSize = size;
	}
	
	public WhiteQueen(int x, int y)
	{
		typeSet();
		setUp();
		setLocation(x, y);
	}
	
	public WhiteQueen(int size)
	{
		typeSet();
		cellSize = size;
	}
	
	private void typeSet()
	{
		type = PieceType.Queen;
	}
}
