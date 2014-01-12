package networkChessPackage2;

import java.awt.Point;

public class BlackPawn extends BlackPiece
{
	public BlackPawn()
	{
		typeSet();
		setUp();
	}
	
	public BlackPawn(Point p, int size)
	{
		typeSet();
		setUp();
		setLocation(p);
		cellSize = size;
	}
	
	public BlackPawn(Point p)
	{
		typeSet();
		setUp();
		setLocation(p);
	}
	
	public BlackPawn(int x, int y, int size)
	{
		typeSet();
		setUp();
		setLocation(x, y);
		cellSize = size;
	}
	
	public BlackPawn(int x, int y)
	{
		typeSet();
		setUp();
		setLocation(x, y);
	}
	
	public BlackPawn(int size)
	{
		typeSet();
		cellSize = size;
	}
	
	private void typeSet()
	{
		type = PieceType.Pawn;
	}
}
