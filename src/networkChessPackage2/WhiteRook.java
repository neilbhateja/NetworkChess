package networkChessPackage2;

import java.awt.Point;

public class WhiteRook extends WhitePiece
{
	public WhiteRook()
	{
		typeSet();
		setUp();
	}
	
	public WhiteRook(Point p, int size)
	{
		typeSet();
		setUp();
		setLocation(p);
		cellSize = size;
	}
	
	public WhiteRook(Point p)
	{
		typeSet();
		setUp();
		setLocation(p);
	}
	
	public WhiteRook(int x, int y, int size)
	{
		typeSet();
		setUp();
		setLocation(x, y);
		cellSize = size;
	}
	
	public WhiteRook(int x, int y)
	{
		typeSet();
		setUp();
		setLocation(x, y);
	}
	
	public WhiteRook(int size)
	{
		typeSet();
		cellSize = size;
	}
	
	private void typeSet()
	{
		type = PieceType.Rook;
	}
}
