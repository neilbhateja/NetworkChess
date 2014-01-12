package networkChessPackage2;

import java.awt.Point;

public class WhitePawn extends WhitePiece
{
	public WhitePawn()
	{
		typeSet();
		setUp();
	}
	
	public WhitePawn(Point p, int size)
	{
		typeSet();
		setUp();
		setLocation(p);
		cellSize = size;
	}
	
	public WhitePawn(Point p)
	{
		typeSet();
		setUp();
		setLocation(p);
	}
	
	public WhitePawn(int x, int y, int size)
	{
		typeSet();
		setUp();
		setLocation(x, y);
		cellSize = size;
	}
	
	public WhitePawn(int x, int y)
	{
		typeSet();
		setUp();
		setLocation(x, y);
	}
	
	public WhitePawn(int size)
	{
		typeSet();
		cellSize = size;
	}
	
	private void typeSet()
	{
		type = PieceType.Pawn;
	}
}
