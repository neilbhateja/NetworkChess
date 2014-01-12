package networkChessPackage2;

import java.awt.Point;

public class WhiteBishop extends WhitePiece
{
	public WhiteBishop()
	{
		typeSet();
		setUp();
	}
	
	public WhiteBishop(Point p, int size)
	{
		typeSet();
		setUp();
		setLocation(p);
		cellSize = size;
	}
	
	public WhiteBishop(Point p)
	{
		typeSet();
		setUp();
		setLocation(p);
	}
	
	public WhiteBishop(int x, int y, int size)
	{
		typeSet();
		setUp();
		setLocation(x, y);
		cellSize = size;
	}
	
	public WhiteBishop(int x, int y)
	{
		typeSet();
		setUp();
		setLocation(x, y);
	}
	
	public WhiteBishop(int size)
	{
		typeSet();
		cellSize = size;
	}
	
	private void typeSet()
	{
		type = PieceType.Bishop;
	}
}
