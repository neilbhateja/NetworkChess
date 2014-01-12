package networkChessPackage2;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ChessPiece 
{
	public void setLocation(Point p)
	{
		gridLocation = p;
	}

	public void setLocation(int x, int y)
	{
		Point p = new Point(x, y);
		setLocation(p);
	}
	
	public Point getLocation()
	{
		return gridLocation;
	}
	
	public int getX()
	{
		return (int) gridLocation.getX();
	}
	
	public int getY()
	{
		return (int) gridLocation.getY();
	}
	
	public void draw(Graphics2D g2)
	{	
		g2.drawImage(img, (getX() * cellSize), (getY() * cellSize), null);
	}

	public boolean canMove(Point p)
	{
		boolean toReturn = true;
		
		if (  (!(type == PieceType.Knight))  &&  (compo.piecesBetween(gridLocation, p))  )
		{	
			toReturn = false;
		}
		
		
		
		if ((toReturn) && (!(compo.onBoard(p))))
		{
			toReturn = false;
		}
		
		if (toReturn)
		{
			if (color == PieceColor.White)
			{
				toReturn = (compo.notWhitePiece(p));
			}
			else if (color == PieceColor.Black)
			{
				toReturn = (compo.notBlackPiece(p));
			}	
		}
		
		if (toReturn)
		{
			toReturn = allowedSquare(p);
		}
		
		return toReturn;
	}
	
	public boolean allowedSquare(Point p)
	{
		boolean toReturn = false;
		
		if (type == PieceType.Bishop)
		{
			toReturn = compo.diagonal(p, gridLocation);
			
		}
		
		else if (type == PieceType.Rook)
		{
			toReturn = (compo.sameRow(p, gridLocation) || compo.sameColumn(p, gridLocation));
		}
		
		else if (type == PieceType.King)
		{
			if (compo.adjacent(p, gridLocation))
			{
				toReturn = true;
			}
			//Castling
			else
			{
				if ((compo.sameRow(p, gridLocation)) && (!beenMoved) && (compo.distance(p, gridLocation) == 2)  && (compo.piecesBetween(gridLocation, p) == false) && (!compo.inCheck(color)) && (!compo.castlesThroughCheck(this, p)) )
				{
					ChessPiece rook = compo.getUnmovedRookOfSpecifiedColorClosestTo(p, color);
					if (rook.beenMoved == false)
					{
						compo.castleMove(rook);
						toReturn = true;
					}
					else
					{
						toReturn = false;
					}
				}
				else
				{
					toReturn = false;
				}
			}
		}
		
		else if (type == PieceType.Pawn)
		{	
			if (compo.diagonalAdjacent(p, gridLocation) && compo.enemyPiece(p, color) && (compo.correctPawnDirection(this, p)))
			{
				toReturn = true;
			}
			
			else if ((compo.sameColumn(p, gridLocation)) && ((compo.distance(p, gridLocation)) == 2) && (compo.notEnemyPiece(p, color)) && (!beenMoved) && (compo.correctPawnDirection(this, p)))
			{
				toReturn = true;
			}
			
			else if ((compo.sameColumn(p, gridLocation)) && ((compo.distance(p, gridLocation)) == 1) && (compo.notEnemyPiece(p, color)) && (compo.correctPawnDirection(this, p)))
			{
				toReturn = true;
			}
			
			if (    (toReturn == true) && ( (p.getY() == 7) || (p.getY() == 0))    )
			{
				type = PieceType.Queen;
				setImage();
			}
			
		}
		
		else if (type == PieceType.Queen)
		{
			toReturn = (compo.diagonal(p, gridLocation) || compo.sameRow(p, gridLocation) || compo.sameColumn(p, gridLocation));
		}
		
		else if (type == PieceType.Knight)
		{
			toReturn = (compo.distance(gridLocation, p) == Math.sqrt(5));
		}
		
		return toReturn;
		
	}
	
	public void setImage()
	{
		beenMoved = false;
		
		genericLocation = genericLocation.concat("/");
		imageLocation = ((genericLocation.concat(color.toString())).concat(type.toString())).concat(".png");
		try
		{
			tehFile = new File(imageLocation);
			BufferedImage tehImage = ImageIO.read(tehFile);
			img = tehImage;
		}
		catch (Exception w)
		{
			System.out.println("Location: " + imageLocation);
			w.printStackTrace();
		}
	}
	
	public PieceColor getColor()
	{
		return color;
	}
	
	public void setCompo(ChessComponent cc)
	{
		compo = cc;
	}
	
	public Point gridLocation;
	public int cellSize;
	public Image img;
	public enum PieceType {Pawn, Rook, Knight, Bishop, Queen, King};
	public enum PieceColor {White, Black};
	public PieceType type;
	public PieceColor color;
	//public String genericLocation = "C:/Documents and Settings/Neil Bhateja/My Documents/Homework/Comp Sci/Workspace/NetworkChess2/pictures";
	public String genericLocation = "pictures";
	public String imageLocation;
	public File tehFile;
	public ChessComponent compo;
	public boolean beenMoved;
}
