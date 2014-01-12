package networkChessPackage2;

import java.awt.Point;

public class WhiteKnight extends WhitePiece
{
	public WhiteKnight()
	{
		typeSet();
		setUp();
	}
	
	public WhiteKnight(Point p, int size)
	{
		typeSet();
		setUp();
		setLocation(p);
		cellSize = size;
	}
	
	public WhiteKnight(Point p)
	{
		typeSet();
		setUp();
		setLocation(p);
	}
	
	public WhiteKnight(int x, int y, int size)
	{
		typeSet();
		setUp();
		setLocation(x, y);
		cellSize = size;
	}
	
	public WhiteKnight(int x, int y)
	{
		typeSet();
		setUp();
		setLocation(x, y);
	}
	
	public WhiteKnight(int size)
	{
		typeSet();
		cellSize = size;
	}
	
	private void typeSet()
	{
		type = PieceType.Knight;
	}
}
