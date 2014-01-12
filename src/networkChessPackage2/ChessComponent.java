package networkChessPackage2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JComponent;

import networkChessPackage2.ChessBackground.EndType;
import networkChessPackage2.ChessPiece.PieceColor;
import networkChessPackage2.ChessPiece.PieceType;

public class ChessComponent extends JComponent
{
	public ChessComponent()
	{}
	
	public ChessComponent(boolean isBlack, int size, ChessFrame tehFrame, ObjectOutputStream out)
	{
		playerIsBlack = isBlack;
		cellSize = size;
		frame = tehFrame;
		stream = out;

		listener = new MoveListener(this, stream);
		
		theFont = new Font("theFont", 1, (cellSize / 2));
		
		whiteList = new ArrayList<WhitePiece>();
		blackList = new ArrayList<BlackPiece>();
		pieceList = new ArrayList<ChessPiece>();
		
		this.addMouseListener(listener);
		
		resetBoard();
	}
	
	public void resetBoard()
	{
		if (resetOnce)
		{
			bckgrnd.newGame.setVisible(false);
			bckgrnd.quit.setVisible(false);
			this.remove(bckgrnd.newGame);
			this.remove(bckgrnd.quit);
		}
		
		//Creates background.
		bckgrnd = new ChessBackground(cellSize, this);
		
		whiteList.clear();
		blackList.clear();
		pieceList.clear();
		
		blackInCheck = false;
		whiteInCheck = false;
		blackInMate = false;
		whiteInMate = false;
		draw = false;
		
		
		blackGivingCheck.clear();
		whiteGivingCheck.clear();
		
		originalSetUp();
		
		//MUST CHANGE WHEN NETWORKED.
		if (playerIsBlack)
		{
			userCanMove = false;
		}
		if (!playerIsBlack)
		{
			userCanMove = true;
		}
		
		
		repaint();
		resetOnce = true;
	}
	
	private void originalSetUp()
	{		
		if (playerIsBlack)
		{
			blackNum = 7;
			whiteNum = 0;
		}
		else
		{
			blackNum = 0;
			whiteNum = 7;
		}
		
		for(int i = 0; i < 8; i++)
		{
			BlackPawn bp = new BlackPawn(i, Math.abs(1 - blackNum), cellSize);
			blackList.add(bp);
		}
		
		for(int i = 0; i < 8; i++)
		{
			WhitePawn wp = new WhitePawn(i, Math.abs(1 - whiteNum), cellSize);
			whiteList.add(wp);
			updatePieceList();
		}
		
		//Black rooks.
		BlackRook brook = new BlackRook(0,blackNum,cellSize);
		blackList.add(brook);
		brook = new BlackRook(7,blackNum,cellSize);
		blackList.add(brook);
		updatePieceList();
		
		//White rooks.
		WhiteRook wrook = new WhiteRook(0,whiteNum,cellSize);
		whiteList.add(wrook);
		wrook = new WhiteRook(7,whiteNum,cellSize);
		whiteList.add(wrook);
		updatePieceList();
		
		//THE BLACK KNIGHTS ALWAYS TRIUMPH!
		BlackKnight bknight = new BlackKnight(1,blackNum,cellSize);
		blackList.add(bknight);
		bknight = new BlackKnight(6,blackNum,cellSize);
		blackList.add(bknight);
		updatePieceList();
		
		//White knights.
		WhiteKnight wknight = new WhiteKnight(1,whiteNum,cellSize);
		whiteList.add(wknight);
		wknight = new WhiteKnight(6,whiteNum,cellSize);
		whiteList.add(wknight);
		updatePieceList();
		
		//Black bishops.
		BlackBishop bbishop = new BlackBishop(2,blackNum,cellSize);
		blackList.add(bbishop);
		bbishop = new BlackBishop(5,blackNum,cellSize);
		blackList.add(bbishop);
		updatePieceList();
		
		//White bishops.
		WhiteBishop wbishop = new WhiteBishop(2,whiteNum,cellSize);
		whiteList.add(wbishop);
		wbishop = new WhiteBishop(5,whiteNum,cellSize);
		whiteList.add(wbishop);
		updatePieceList();
		
		int queenNum;
		int kingNum;
		
		if (playerIsBlack)
		{
			queenNum = 4;
			kingNum = 3;
		}
		else
		{
			queenNum = 3;
			kingNum = 4;
		}
		//The March of the Black Queen.
		BlackQueen bqueen = new BlackQueen(queenNum,blackNum,cellSize);
		blackList.add(bqueen);
		
		//The White Queen walks and the night grows pale.
		WhiteQueen wqueen = new WhiteQueen(queenNum,whiteNum,cellSize);
		whiteList.add(wqueen);
		
		//Black king.
		BlackKing bking = new BlackKing(kingNum,blackNum,cellSize);
		blackList.add(bking);
		blackKing = bking;
		
		//White king.
		WhiteKing wking = new WhiteKing(kingNum,whiteNum,cellSize);
		whiteList.add(wking);
		whiteKing = wking;
		
		updatePieceList();
		
	}
	
	private void updatePieceList()
	{
		pieceList.clear();
		pieceList.addAll(whiteList);
		pieceList.addAll(blackList);
		
		for (ChessPiece cp: pieceList)
		{
			cp.setCompo(this);
		}
	}
	
	private boolean isOccupied(Point p)
	{
		return ((blackPiece(p)) || (whitePiece(p)));
	}
	
	private boolean isOccupied(int x, int y)
	{
		Point p = new Point(x, y);
		return isOccupied(p);
	}

	public WhitePiece getWhitePieceAt(Point p)
	{
		WhitePiece piece = new WhitePiece();
		for (WhitePiece wp : whiteList)
		{
			if (wp.getLocation().equals(p))
			{
				piece = wp;
				break;
			}
		}
		return piece;
	}
	
	public BlackPiece getBlackPieceAt(Point p)
	{
		BlackPiece piece = new BlackPiece();
		for (BlackPiece wp : blackList)
		{
			if (wp.getLocation().equals(p))
			{
				piece = wp;
				break;
			}
		}
		return piece;
	}
	
	public ChessPiece getPieceAt(Point p, PieceColor c)
	{
		if (c == PieceColor.Black)
		{
			return getBlackPieceAt(p);
		}
		else
		{
			return getWhitePieceAt(p);
		}
	}
	
	public ChessPiece getPieceAt(Point p)
	{
		ChessPiece piece = new ChessPiece();
		for (ChessPiece wp : pieceList)
		{
			if (wp.getLocation().equals(p))
			{
				piece = wp;
				break;
			}
		}
		return piece;
	}
	
	public ChessPiece getUnmovedRookOfSpecifiedColorClosestTo(Point p, PieceColor c)
	{
		Point q = new Point();
		
		if (p.getX() <= 3)
		{
			q = new Point(0, (int) p.getY());
		}
		else
		{
			q = new Point(7, (int) p.getY());
		}
		return getPieceAt(q, c);
	}
	
	public boolean blackPiece(Point p)
	{
		return (!notBlackPiece(p));
	}

	public boolean blackPiece(int x, int y)
	{
		return (!notBlackPiece(x, y));
	}
	
	public boolean notBlackPiece(Point p)
	{
		return notBlackPiece((int) p.getX(), (int) p.getY());
	}
	
	public boolean notBlackPiece(int x, int y)
	{
		boolean toReturn = true;
		
		for(BlackPiece f : blackList)
		{
			if ((f.getX() == x) && (f.getY() == y))
			{
				toReturn = false;
				break;
			}
		}
		return toReturn;
	}
	
	public boolean whitePiece(Point p)
	{
		return (!notWhitePiece(p));
	}

	public boolean whitePiece(int x, int y)
	{
		return (!notWhitePiece(x, y));
	}
	
	public boolean notWhitePiece(Point p)
	{
		return notWhitePiece((int) p.getX(), (int) p.getY());
	}
	
	public boolean notWhitePiece(int x, int y)
	{
		boolean toReturn = true;
		
		for(WhitePiece f : whiteList)
		{
			if ((f.getX() == x) && (f.getY() == y))
			{
				toReturn = false;
				break;
			}
		}
		return toReturn;
	}
	
	public boolean enemyPiece(Point q, PieceColor c)
	{
		boolean toReturn = false;
		
		if (c == PieceColor.Black)
		{
			toReturn = whitePiece(q);
		}
		if (c == PieceColor.White)
		{
			toReturn = blackPiece(q);
		}
		
		return toReturn;
	}

	public boolean notEnemyPiece(Point q, PieceColor c)
	{
		return (!enemyPiece(q, c));
	}

	private WhiteKing getWhiteKing()
	{
		WhiteKing toReturn = new WhiteKing();
		
		for (WhitePiece bp : whiteList)
		 {
			if (bp.type == ChessPiece.PieceType.King)
			{
				toReturn = (WhiteKing) bp;
				break;
			}
		 }
		
		return toReturn;
	}
	
	private BlackKing getBlackKing()
	{
		BlackKing toReturn = new BlackKing();
		
		for (BlackPiece bp : blackList)
		 {
			if (bp.type == ChessPiece.PieceType.King)
			{
				toReturn = (BlackKing) bp;
				break;
			}
		 }
		
		return toReturn;
	}
	
	public boolean inCheck(PieceColor c)
	{
		if (c == PieceColor.White)
		{
			return whiteInCheck;
		}
		else
		{
			return blackInCheck;
		}
	}

	public boolean castlesThroughCheck(ChessPiece king, Point p)
	{
		boolean toReturn = false;
		
		Point q = new Point( (int)  ( ((king.getX()) + (p.getX()))  / 2 ), (int)  p.getY() );
		
		if (king.getColor() == PieceColor.White)
		{
			toReturn = moveResultsInWhiteCheck((WhitePiece) king, q);
		}
		
		if (king.getColor() == PieceColor.Black)
		{
			toReturn = moveResultsInBlackCheck((BlackPiece) king, q);
		}
		
		return toReturn;
	}
	
	public void castleMove(ChessPiece cp)
	{
		Point p = new Point();
		int newX = cp.getX();
		
		if (playerIsBlack)
		{	
			if (cp.getX() == 7)
			{
				newX = 4;
			}
		
			if (cp.getX() == 0)
			{
				newX = 2;
			}
		}
		
		else
		{	
			if (cp.getX() == 7)
			{
				newX = 5;
			}
		
			if (cp.getX() == 0)
			{
				newX = 3;
			}
		}
		
		p = new Point(newX, (int) cp.getY());
		
		if (cp.getColor() == PieceColor.White)
		{
			moveWhite( (WhitePiece) cp, p);
		}
		if (cp.getColor() == PieceColor.Black)
		{
			moveBlack( (BlackPiece) cp, p);
		}
		
	}
	
	public boolean whiteInCheck()
	{
		 boolean toReturn = false;
		 blackGivingCheck.clear();
		 
		 updateWhiteKing();
		 Point p = whiteKing.getLocation();
		 
		 for (BlackPiece bp : blackList)
		 {
			 if (bp.canMove(p))
			 {
				 toReturn = true;
				 blackGivingCheck.add(bp);
			 }
		 }
		 
		 return toReturn;
	}
	
	public boolean blackInCheck()
	{
		 boolean toReturn = false;
		 whiteGivingCheck.clear();
		 
		 updateBlackKing();
		 Point p = blackKing.getLocation();
		 
		 for (WhitePiece wp : whiteList)
		 {
			 if (wp.canMove(p))
			 {
				 whiteGivingCheck.add(wp);
				 toReturn = true;
			 }
		 }
		 
		 return toReturn;
	}
	
	public boolean draw()
	{
		boolean toReturn = false;
		
		if (playerIsBlack)
		{
			if ((!blackCanEscapeCheck()) && (!blackInMate) && blackCannotMove())
			{
				toReturn = true;
			}
		}
		
		if (!(playerIsBlack))
		{
			if ((!whiteCanEscapeCheck()) && (!whiteInMate) && whiteCannotMove())
			{
				toReturn = true;
			}
		}
		
		return toReturn;
	}
	
	private boolean blackCannotMove()
	{
		boolean toReturn = true;
		
		for (int i = 0; i < blackList.size(); i++)
		{
			BlackPiece bp = blackList.get(i);
			
			if (!(bp.type == PieceType.King  )  )
			{
				
				for (int j = 0; j < 8; j++)
				{
					for (int k = 0; k < 8; k++)
					{
						Point p = new Point(j,k);
						if (!  (p.equals(bp.getLocation()))  )
						{
							if ( (bp.canMove(p)) &&  (!(moveResultsInBlackCheck(bp, p)))   )
							{
								toReturn = false;
								break;
							}
						}
					}
					
					if (!toReturn)
					{
						break;
					}
				}
			}
			
			if (!toReturn)
			{
				break;
			}
		}
		
		return toReturn;
	}
	
	private boolean whiteCannotMove()
	{
		boolean toReturn = true;
		
		for (int i = 0; i < whiteList.size(); i++)
		{
			WhitePiece bp = whiteList.get(i);
			
			if (!(bp.type == PieceType.King  )  )
			{
				
				for (int j = 0; j < 8; j++)
				{
					for (int k = 0; k < 8; k++)
					{
						Point p = new Point(j,k);
						if (!  (p.equals(bp.getLocation()))  )
						{
							if ( (bp.canMove(p)) &&  (!(moveResultsInWhiteCheck(bp, p)))   )
							{
								toReturn = false;
								break;
							}
						}
					}
					
					if (!toReturn)
					{
						break;
					}
				}
			}
			
			if (!toReturn)
			{
				break;
			}
		}
		
		return toReturn;
	}
	
	public boolean whiteInMate()
	{
		boolean toReturn = true;
		
		if (!whiteInCheck)
		{
			toReturn = false;
			return toReturn;
		}
		
		if (whiteCanEscapeCheck())
		{
			toReturn = false;
			return toReturn;
		}
		
		if (blackCheckerCanBeKilled())
		{
			toReturn = false;
			return toReturn;
		}
		
		if (blackCheckerCanBeBlocked())
		{
			toReturn = false;
			return toReturn;
		}
		
		whiteInMate = toReturn;
		
		return toReturn;
	}
	
	public boolean blackInMate()
	{
		boolean toReturn = true;
		
		if (!blackInCheck)
		{
			toReturn = false;
			return toReturn;
		}
		
		if (blackCanEscapeCheck())
		{
			toReturn = false;
			return toReturn;
		}
		
		if (whiteCheckerCanBeKilled())
		{
			toReturn = false;
			return toReturn;
		}
		
		if (whiteCheckerCanBeBlocked())
		{
			toReturn = false;
			return toReturn;
		}
		
		blackInMate = toReturn;
		return toReturn;
	}
	
	private boolean whiteCanEscapeCheck()
	{
		boolean toReturn = false;
		
		WhitePiece king = whiteKing;
		Point kingLocation = whiteKing.getLocation();
		
		kingLocation = king.getLocation();
		
		for (int i = (int) ((kingLocation.getX() - 1)); i <= (int) ((kingLocation.getX() + 1)); i++)
		{
			for (int j = (int) ((kingLocation.getY() - 1)); j <= (int) ((kingLocation.getY() + 1)); j++)
			{
				Point test = new Point(i,j);
				if (!(test.equals(kingLocation)))
				{
					if ((!(moveResultsInWhiteCheck(king, test))) && king.canMove(test))
					{
						toReturn = true;
						break;
					}
				}
			}
			if (toReturn)
			{
				break;
			}
		}
		
		
		
		return toReturn;
	}
	
	private boolean blackCanEscapeCheck()
	{
		boolean toReturn = false;
		
		BlackPiece king = blackKing;
		Point kingLocation = blackKing.getLocation();
		
		for (BlackPiece k : blackList)
		{
			if (k.type == PieceType.King)
			{
				king = k;
				break;
			}
		}
		
		kingLocation = king.getLocation();
		
		for (int i = (int) ((kingLocation.getX() - 1)); i <= (int) ((kingLocation.getX() + 1)); i++)
		{
			for (int j = (int) ((kingLocation.getY() - 1)); j <= (int) ((kingLocation.getY() + 1)); j++)
			{
				Point test = new Point(i,j);
				if (!(test.equals(kingLocation)))
				{
					if ((!(moveResultsInBlackCheck(king, test))) && king.canMove(test))
					{
						toReturn = true;
						break;
					}
				}
			}
			if (toReturn)
			{
				break;
			}
		}
		
		
		
		return toReturn;
	}
	
	private boolean whiteCheckerCanBeKilled()
	{
		boolean toReturn = false;
		WhitePiece checker = new WhitePiece();
		Point q = new Point();
		
		if (whiteGivingCheck.size() > 1)
		{
			return toReturn;
		}
		else if (whiteGivingCheck.size() > 0)
		{
			checker = whiteGivingCheck.get(0);
			q = checker.getLocation();
			
		}
		else if (!(whiteGivingCheck.size() > 0))
		{
			return true;
		}
		
		
		for (BlackPiece wp : blackList)
		{
			if (wp.canMove(q) && (!(moveResultsInBlackCheck(wp, q))) )
			{
				toReturn = true;
				break;
			}
		}
		return toReturn;
	}
	
	private boolean blackCheckerCanBeKilled()
	{
		boolean toReturn = false;
		BlackPiece checker = new BlackPiece();
		Point q = new Point();
		
		if (blackGivingCheck.size() > 1)
		{
			return toReturn;
		}
		else if (blackGivingCheck.size() > 0)
		{
			checker = blackGivingCheck.get(0);
			q = checker.getLocation();
			
		}
		else if (!(blackGivingCheck.size() > 0))
		{
			return true;
		}
		
		
		for (WhitePiece wp : whiteList)
		{
			if (wp.canMove(q) && (!(moveResultsInWhiteCheck(wp, q))) )
			{
				toReturn = true;
				break;
			}
		}
		return toReturn;
	}
	
	private boolean whiteCheckerCanBeBlocked()
	{
		boolean toReturn = false;
		
		if (whiteGivingCheck.size() > 1)
		{
			return toReturn;
		}
		
		WhitePiece checker = whiteGivingCheck.get(0);
		
		if (checker.type == PieceType.Knight)
		{
			return toReturn;
		}
		
		Point q = checker.getLocation();
		
		int dist = (int) distance(q, blackKing.getLocation());
		
		if (adjacent(q, blackKing.getLocation()) || (dist == 1))
		{
			return false;
		}
		
		ArrayList<Point> squaresBetween = getBetweenSquares(q, blackKing.getLocation());
		
		ArrayList<BlackPiece> potentialBlockersList = new ArrayList<BlackPiece>();
		ArrayList<Point> potentialBlockingSpotsList = new ArrayList<Point>();
		
		for (BlackPiece bp : blackList)
		{
			for (Point between : squaresBetween)
			{
				if (bp.canMove(between))
				{
					potentialBlockersList.add(bp);
					potentialBlockingSpotsList.add(between);
				}
			}
		}
		
		BlackPiece[] potentialBlockers = new BlackPiece[1];
		Point[] potentialBlockingSpots = new Point[1];
		potentialBlockers = potentialBlockersList.toArray(potentialBlockers);
		potentialBlockingSpots = potentialBlockingSpotsList.toArray(potentialBlockingSpots);
		
		for (int i = 0; i < potentialBlockers.length; i++)
		{
			if (!(moveResultsInBlackCheck(potentialBlockers[i],potentialBlockingSpots[i])))
			{
				toReturn = true;
				break;
			}
		}
		
		return toReturn;
	}
	
	private boolean blackCheckerCanBeBlocked()
	{
		boolean toReturn = false;
		
		if (blackGivingCheck.size() > 1)
		{
			return toReturn;
		}
		
		BlackPiece checker = blackGivingCheck.get(0);
		
		if (checker.type == PieceType.Knight)
		{
			return toReturn;
		}
		
		Point q = checker.getLocation();
		
		int dist = (int) distance(q, whiteKing.getLocation());
		
		if (adjacent(q, whiteKing.getLocation()) || (dist == 1))
		{
			return false;
		}
		
		ArrayList<Point> squaresBetween = getBetweenSquares(q, whiteKing.getLocation());
		
		ArrayList<WhitePiece> potentialBlockersList = new ArrayList<WhitePiece>();
		ArrayList<Point> potentialBlockingSpotsList = new ArrayList<Point>();
		
		for (WhitePiece bp : whiteList)
		{
			for (Point between : squaresBetween)
			{
				if (bp.canMove(between))
				{
					potentialBlockersList.add(bp);
					potentialBlockingSpotsList.add(between);
				}
			}
		}

		WhitePiece[] potentialBlockers = new WhitePiece[1];
		Point[] potentialBlockingSpots = new Point[1];
		potentialBlockers = potentialBlockersList.toArray(potentialBlockers);
		potentialBlockingSpots = potentialBlockingSpotsList.toArray(potentialBlockingSpots);
		
		for (int i = 0; i < potentialBlockers.length; i++)
		{
			if (!(moveResultsInWhiteCheck(potentialBlockers[i],potentialBlockingSpots[i])))
			{
				toReturn = true;
				break;
			}
		}
		
		return toReturn;
	}

	private ArrayList<Point> getBetweenSquares(Point a, Point b)
	{
		ArrayList<Point> toReturn = new ArrayList<Point>();
		
		int dist = (int) distance(a, b);
		
		int xDiff =(int) ((b.getX() - a.getX()) / dist);
		int yDiff =(int) ((b.getY() - a.getY()) / dist);
		
		Point p; 		
		
		Point d = new Point();
		
		for(p = new Point((int) (a.getX() + xDiff), (int) (a.getY() + yDiff)); (distance(a,p) < distance(a,b)); p = d)
		{	
			toReturn.add(p);
			
			d = new Point((int) (p.getX() + xDiff), (int) (p.getY() + yDiff));
		}
		return toReturn;
	}
	
	public double distance(Point p, Point q)
	{
		if (diagonal(p,q))
		{
			return Math.abs(p.getX() - q.getX());
		}
		
		double x = (p.getX() - q.getX());
		double y = (p.getY() - q.getY());
		
		double squared = (x * x) + (y * y);
		
		return Math.sqrt(squared);
	}
	
	public void paintComponent(Graphics g)
	{	
		Graphics2D g2 = (Graphics2D) g;
		
		if (userCanMove)
		{
			g2.setFont(theFont);
			g2.setColor(Color.RED);
			g2.drawString(yourTurn, (int) (9 * cellSize), (1 * cellSize));	
		}
		
		if (whiteInCheck)
		{
			g2.drawString(whiteCheck, (int) (9 * cellSize), (2 * cellSize));
		}
		
		if (blackInCheck)
		{
			g2.drawString(blackCheck, (int) (9 * cellSize), (2 * cellSize));
		}
		
		if ((blackInMate && playerIsBlack) || (whiteInMate && (!playerIsBlack)))
		{
			bckgrnd.setGameOver(true);
			bckgrnd.setEnding(EndType.Lose);
		}
		
		if ((blackInMate && (!playerIsBlack)) || (whiteInMate && playerIsBlack))
		{
			bckgrnd.setGameOver(true);
			bckgrnd.setEnding(EndType.Win);
		}
		
		if (draw)
		{
			bckgrnd.setGameOver(true);
			bckgrnd.setEnding(EndType.Draw);
		}
		
		if (bckgrnd.getGameOver())
		{
			add(bckgrnd.newGame);
			add(bckgrnd.quit);
			bckgrnd.newGame.setVisible(true);
			bckgrnd.quit.setVisible(true);
		}
		
		bckgrnd.draw(g2);
		
		for (WhitePiece wite: whiteList)
		{
			wite.draw(g2);
		}
		
		for (BlackPiece peace: blackList)
		{
			peace.draw(g2);
		}
		
	}
	
	private void moveWhite(WhitePiece piece, Point location)
	{
		whiteList.remove(piece);
		piece.setLocation(location);
		whiteList.add((WhitePiece)piece);
		if (piece.type == PieceType.King)
		{
			whiteKing = (WhiteKing)  piece;
		}
	}
	
	private void moveBlack(BlackPiece piece, Point location)
	{
		blackList.remove(piece);
		piece.setLocation(location);
		blackList.add((BlackPiece) piece);
		if (piece.type == PieceType.King)
		{
			blackKing = (BlackKing)  piece;
		}
	}
	
	private void moveBlackAndKill(BlackPiece piece, Point newLocation)
	{
		moveBlack(piece, newLocation);
		
		WhitePiece toRemove = new WhitePiece();
		boolean removed = false;
		
		for (WhitePiece wp : whiteList)
		{
			if (wp.getLocation().equals(newLocation))
			{
				toRemove = wp;
				removed = true;
				break;
			}
		}
		
		if (removed)
		{
			whiteList.remove(toRemove);
		}
		
	}
	
	private void moveWhiteAndKill(WhitePiece piece, Point newLocation)
	{
		moveWhite(piece, newLocation);
		
		BlackPiece toRemove = new BlackPiece();
		boolean removed = false;
		
		for (BlackPiece wp : blackList)
		{
			if (wp.getLocation().equals(newLocation))
			{
				toRemove = wp;
				removed = true;
				break;
			}
		}
		
		if (removed)
		{
			blackList.remove(toRemove);
		}
		
	}
	
	private void updateBlackKing()
	{
		blackKing = getBlackKing();
	}
	
	private void updateWhiteKing()
	{
		whiteKing = getWhiteKing();
	}
	
	private void updateKings()
	{
		updateBlackKing();
		updateWhiteKing();
	}
	
	private boolean isKing(Point p)
	{
		boolean toReturn = ( ( whiteKing.getLocation().equals(p) ) || ( blackKing.getLocation().equals(p) ) );
		return toReturn;
	}
	
	private boolean moveResultsInWhiteCheck(WhitePiece piece, Point newLocation)
	{
		boolean toReturn = false;
		
		//Makes sure black king is not killed either.
		BlackPiece theKing = blackKing;
		
		if (theKing.getLocation().equals(newLocation))
		{
			return true;
		}
		
		Point oldLocation = piece.getLocation();
		boolean removed = false;
		
		moveWhite(piece, newLocation);
		BlackPiece blackRemove = new BlackPiece();
		
		//Kills any black piece at the new white location.
		if (blackPiece(newLocation))
		{
			blackRemove = new BlackPiece();
			for(BlackPiece bp : blackList)
			{
				if (bp.getLocation().equals(newLocation))
				{
					blackRemove = bp;
				}
			}
			blackList.remove(blackRemove);
			removed = true;
		}
		
		toReturn = whiteInCheck();
		
		//Undoes move.

			moveWhite(piece, oldLocation);
			if (removed)
			{
				blackList.add(blackRemove);
			}
		
		return toReturn;
	}
	
	private boolean moveResultsInBlackCheck(BlackPiece piece, Point newLocation)
	{
		boolean toReturn = false;
		
		//Makes sure white king is not killed either.
		WhitePiece theKing = whiteKing;
		
		if (theKing.getLocation().equals(newLocation))
		{
			return true;
		}
		
		Point oldLocation = piece.getLocation();
		boolean removed = false;
		
		moveBlack(piece, newLocation);
		
		WhitePiece whiteRemove = new WhitePiece();
		
		//Kills any white piece at the new black location.
		if (whitePiece(newLocation))
		{
			whiteRemove = new WhitePiece();
			for(WhitePiece bp : whiteList)
			{
				if (bp.getLocation().equals(newLocation))
				{
					whiteRemove = bp;
				}
			}
			whiteList.remove(whiteRemove);
			removed = true;
		}
		
		toReturn = blackInCheck();
		
		//Undoes move.

			moveBlack(piece, oldLocation);
			if (removed)
			{
				whiteList.add(whiteRemove);
			}
		
		return toReturn;
	}
	
	private void drag(Point p, Point q)
	{
		boolean moved = false;
		
		ChessPiece cp = new ChessPiece();
		
		for (ChessPiece piece : pieceList)
		{
			if (p.equals(piece.getLocation()))
			{
				cp = piece;
				break;
			}
		}
		
		if((cp.getColor().equals(PieceColor.White)) && (cp.canMove(q)))
		{
			if (!(moveResultsInWhiteCheck((WhitePiece) cp, q)))
			{
				moveWhiteAndKill((WhitePiece) cp, q);
				moved = true;
			}
		}


		if((cp.getColor().equals(PieceColor.Black)) && (cp.canMove(q)))
		{
			if (!(moveResultsInBlackCheck((BlackPiece) cp, q)))
			{
				moveBlackAndKill((BlackPiece) cp, q);
				moved = true;
			}
		}
		
		whiteInCheck = whiteInCheck();
		blackInCheck = blackInCheck();
		
		whiteInMate = whiteInMate();
		blackInMate = blackInMate();
		
		draw = draw();
		
		if (moved)
		{
			cp.beenMoved = true;
			userCanMove = !userCanMove;
		}
		
		updatePieceList();
	} 
		
	public void move(Point a, Point b)
	{	
		
		if (userCanMove)
		{
			Point p = new Point((int) (a.getX() / cellSize), (int) (a.getY() / cellSize));
			Point q = new Point((int) (b.getX() / cellSize), (int) (b.getY() / cellSize));
			
			if( !(isKing(q)) && (   ( (getPieceAt(p).getColor() == PieceColor.Black) && playerIsBlack) || ((getPieceAt(p).getColor() == PieceColor.White) && (!playerIsBlack))    )   )
			{
				drag(p, q);
			}
			
		}
		else if (!userCanMove)
		{
			Point p = new Point((int) (a.getX() / cellSize), (int) (a.getY() / cellSize));
			Point q = new Point((int) (b.getX() / cellSize), (int) (b.getY() / cellSize));
			
			p = flipPoint(p);
			q = flipPoint(q);
			
			if( !(isKing(q)) && (   ( (getPieceAt(p).getColor() == PieceColor.Black) && !playerIsBlack) || ((getPieceAt(p).getColor() == PieceColor.White) && (playerIsBlack))    )   )
			{
				drag(p, q);
			}
			
		}
		
		repaint();
	}

	public boolean sameRow(ChessPiece a, ChessPiece b)
	{
		return sameRow(a.getLocation(), b.getLocation());
	}
	
	public boolean sameRow(Point a, Point b)
	{
		return (a.getY() == b.getY());
	}
		
	public boolean sameColumn(ChessPiece a, ChessPiece b)
	{
		return sameColumn(a.getLocation(), b.getLocation());
	}

	public boolean onBoard(Point p)
	{
		return ((p.getX() >= 0) && (p.getX() <= 7) && (p.getY() >= 0) && (p.getY() <= 7));
	}
	
	public boolean piecesBetween(Point a, Point b)
	{
		boolean toReturn = false;
		
		if ( (!(sameRow(a,b))) && (!(sameColumn(a,b))) && (!(diagonal(a,b))) )
		{
			return false;
		}
		
		int dist = (int) distance(a, b);
		
		if (adjacent(a,b) || (dist == 1))
		{
			return false;
		}
		
		int xDiff =(int) (b.getX() - a.getX());
		int yDiff =(int) (b.getY() - a.getY());
		
		Point p; 		
		
		Point d = new Point();
		
		for(p = new Point((int) (a.getX() + (xDiff / dist)), (int) (a.getY() + (yDiff / dist))); (distance(a,p) < distance(a,b)); p = d)
		{	
			if(isOccupied(p))
			{
				toReturn = true;
				break;
			}
			
			d = new Point((int) (p.getX() + (xDiff / dist)), (int) (p.getY() + (yDiff / dist)));
		}
		return toReturn;
	}
	
	public boolean sameColumn(Point a, Point b)
	{
		return (a.getX() == b.getX());
	}
	
	public boolean diagonal(ChessPiece a, ChessPiece b)
	{
		return diagonal(a.getLocation(), b.getLocation());
	}
	
	public boolean diagonal(Point a, Point b)
	{
		int dist1 = Math.abs((int) (a.getX() - b.getX()));
		int dist2 = Math.abs((int) (a.getY() - b.getY()));
		return (dist1 == dist2);
	}
	
	public boolean diagonalAdjacent(ChessPiece a, ChessPiece b)
	{
		return (diagonal(a, b) && adjacent(a, b));
	}
	
	public boolean diagonalAdjacent(Point a, Point b)
	{
		return (diagonal(a, b) && adjacent(a, b));
	}
	
	public boolean correctPawnDirection(ChessPiece p, Point q)
	{
		if (((p.getColor() == PieceColor.Black) && playerIsBlack)  ||   ((p.getColor() == PieceColor.White) && (!playerIsBlack)))
		{
			return (q.getY() < p.getY());
		}
		
		else
		{
			return (q.getY() > p.getY());
		}
	}
	
	public boolean adjacent(ChessPiece a, ChessPiece b)
	{
		return adjacent(a.getLocation(), b.getLocation());
	}
	
	public boolean adjacent(Point a, Point b)
	{
		return ( ( (Math.abs(a.getX() - b.getX())) <= 1) && ( (Math.abs(a.getY() - b.getY())) <= 1));
		
	}
	
	public void quit()
	{
		frame.dispose();
	}
	
	public Point flipPoint(Point p)
	{
		p.setLocation(7 - p.getX() , 7 - p.getY() );
		return p;
	}

	public ArrayList<WhitePiece> flipWhites(ArrayList<WhitePiece> toFlip)
	{
		ArrayList<WhitePiece> flipped = new ArrayList<WhitePiece>();
		

		WhitePiece piece;			
		for(WhitePiece wp : toFlip)
		{
			piece = wp;
			piece.setLocation( (7 - wp.getX()) ,  (7 - wp.getY()) );
			flipped.add(piece);
		}
		
		return flipped;
	}
	
	public ArrayList<BlackPiece> flipBlacks(ArrayList<BlackPiece> toFlip)
	{
		ArrayList<BlackPiece> flipped = new ArrayList<BlackPiece>();
		

		BlackPiece piece;			
		for(BlackPiece bp : toFlip)
		{
			piece = bp;
			piece.setLocation( (7 - bp.getX()) ,  (7 - bp.getY()) );
			flipped.add(piece);
		}
		
		return flipped;
	}
		
	public void setBlackList(ArrayList<BlackPiece> list)
	{
		blackList = flipBlacks(list);
		updateBlackKing();
		updatePieceList();
	}

	public void setWhiteList(ArrayList<WhitePiece> list)
	{
		whiteList = flipWhites(list);
		updateWhiteKing();
		updatePieceList();
	}
	
	public void setPlayerBlack() 
	{
		playerIsBlack = true;
	}

	public void setPlayerWhite() 
	{
		playerIsBlack = false;
		
	}
	
	public void changePlayerColor()
	{
		playerIsBlack = !playerIsBlack;
	}
	
	public void setBlackInCheck(boolean setTo) 
	{
		blackInCheck = setTo;
		
	}

	public void setBlackInMate() 
	{
		blackInMate = true;
		
	}

	public void setDraw() 
	{
		draw = true;
		
	}

	public void setWhiteInCheck(boolean setTo) 
	{
		whiteInCheck = setTo;
		
	}

	public void setWhiteInMate() 
	{
		whiteInMate = true;
		
	}
	
	public int cellSize;
	public boolean userCanMove = false;
	
	//Number of whites and blacks.
	public final int originalWhites = 16;
	public final int originalBlacks = 16;
	
	public Font theFont;
	public final String yourTurn = "Your turn.";
	public final String whiteCheck = "White in check.";
	public final String blackCheck = "Black in check.";
	
	public ChessBackground bckgrnd;
	public boolean resetOnce = false;
	public ArrayList<WhitePiece> whiteList;
	public ArrayList<BlackPiece> blackList;
	public ArrayList<ChessPiece> pieceList;
	
	public ChessFrame frame;
	public MouseListener listener = new MoveListener();
	public ObjectOutputStream stream;
	public static final long serialVersionUID = 823;
	
	public boolean playerIsBlack;
	public boolean blackInCheck;
	public boolean whiteInCheck;
	public boolean whiteInMate;
	public boolean blackInMate;
	public boolean draw;
	public ArrayList<BlackPiece> blackGivingCheck = new ArrayList<BlackPiece>();
	public ArrayList<WhitePiece> whiteGivingCheck = new ArrayList<WhitePiece>();
	public int blackNum;
	public int whiteNum;
	public WhiteKing whiteKing;
	public BlackKing blackKing;
	
}