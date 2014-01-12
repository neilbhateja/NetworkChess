package networkChessPackage2;

import java.awt.Point;

public class BlackKnight extends BlackPiece
{
	public BlackKnight()
	{
		typeSet();
		setUp();
	}
	
	public BlackKnight(Point p, int size)
	{
		typeSet();
		setUp();
		setLocation(p);
		cellSize = size;
	}
	
	public BlackKnight(Point p)
	{
		typeSet();
		setUp();
		setLocation(p);
	}
	
	public BlackKnight(int x, int y, int size)
	{
		typeSet();
		setUp();
		setLocation(x, y);
		cellSize = size;
	}
	
	public BlackKnight(int x, int y)
	{
		typeSet();
		setUp();
		setLocation(x, y);
	}
	
	public BlackKnight(int size)
	{
		typeSet();
		cellSize = size;
	}
	
	private void typeSet()
	{
		type = PieceType.Knight;
	}
}
