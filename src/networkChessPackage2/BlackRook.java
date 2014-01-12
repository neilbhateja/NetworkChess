package networkChessPackage2;

import java.awt.Point;

public class BlackRook extends BlackPiece
{
	public BlackRook()
	{
		typeSet();
		setUp();
	}
	
	public BlackRook(Point p, int size)
	{
		typeSet();
		setUp();
		setLocation(p);
		cellSize = size;
	}
	
	public BlackRook(Point p)
	{
		typeSet();
		setUp();
		setLocation(p);
	}
	
	public BlackRook(int x, int y, int size)
	{
		typeSet();
		setUp();
		setLocation(x, y);
		cellSize = size;
	}
	
	public BlackRook(int x, int y)
	{
		typeSet();
		setUp();
		setLocation(x, y);
	}
	
	public BlackRook(int size)
	{
		typeSet();
		cellSize = size;
	}
	
	private void typeSet()
	{
		type = PieceType.Rook;
	}
}
