package networkChessPackage2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ChessBackground 
{
	public ChessBackground()
	{}
	
	public ChessBackground(int diam, ChessComponent tehCompo)
	{
		cellSize = diam;
		
		borderRect = new Rectangle(0,0,(8 * diam),(8 * diam));
		
		loseMessageX = (int) (9 * cellSize);
		loseMessageY = (int)(cellSize * 4);
		winMessageX = (int) (9 * cellSize);
		winMessageY = loseMessageY;
		drawMessageX = (int) (11 * cellSize);
		drawMessageY = loseMessageY;
		newGameButtonX = (int)(cellSize * 11);
		newGameButtonY = (int) (cellSize * 4.5);
		quitButtonX = (int)(cellSize * 15);
		quitButtonY = newGameButtonY;
		
		compo = tehCompo;
		
		newGame = new JButton("New Game");
		newGame.setLocation(newGameButtonX, newGameButtonY);
		newGame.setSize(cellSize * 2, cellSize);
		newGame.addActionListener(newGameListener);
		
		quit = new JButton("Quit");
		quit.setLocation(quitButtonX, quitButtonY);
		quit.setSize(cellSize * 2, cellSize);
		quit.addActionListener(quitListener);
		
		
		theFont = new Font("theFont", 1, cellSize * 2);
		ending = EndType.NotOver;
	}
	
	public void setGameOver(boolean setTo)
	{
		gameOver = setTo;
	}
	
	public void setEnding(EndType setTo)
	{
		ending = setTo;
	}
	
	public boolean getGameOver()
	{
		return gameOver;
	}
	
	public void draw(Graphics2D g2)
	{	
		if (gameOver)
		{
			g2.setColor(Color.RED);
			
			g2.setFont(theFont);
			
			if (ending == EndType.Win)
			{
				g2.drawString(winMessage, winMessageX, winMessageY);
			}
			if (ending == EndType.Draw)
			{
				g2.drawString(drawMessage, drawMessageX, drawMessageY);
			}
			else
			{
				g2.drawString(loseMessage, loseMessageX, loseMessageY);
			}
		}
		
		g2.setColor(Color.WHITE);
		for(int i = 0; i < 8; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				Square s = new Square();
					s.setLocation((i * cellSize), (j * cellSize));
					s.setSize(cellSize);
					g2.fill(s);
					colorChange(g2);
			}
			colorChange(g2);
		}
		
		g2.setColor(Color.BLACK);
		g2.draw(borderRect);
	}
	
	private void colorChange(Graphics2D g2)
	{
		if (g2.getColor().equals(Color.WHITE))
		{
			g2.setColor(squareColor);
		}
		
		else
		{
			g2.setColor(Color.WHITE);
		}
	}
	
	class newGameListenerClass implements ActionListener
	{	
		public void actionPerformed(ActionEvent event)
		{
			gameOver = false;
			ending = EndType.NotOver;
			compo.resetBoard();
		}
	}
	
	class quitListenerClass implements ActionListener
	{	
		public void actionPerformed(ActionEvent event)
		{
			compo.quit();
		}
	}
	
	public int cellSize;
	public boolean gameOver;
	public enum EndType {Win, Lose, Draw, NotOver};
	public EndType ending;
	public String loseMessage = "YOU LOST.";
	public String winMessage = "YOU WON!";
	public String drawMessage = "DRAW.";
	public int winMessageX;
	public int winMessageY;
	public int loseMessageX;
	public int loseMessageY;
	public int drawMessageX;
	public int drawMessageY;
	public int newGameButtonX;
	public int newGameButtonY;
	public int quitButtonX;
	public int quitButtonY;
	
	//public HiVoltsFrame frame;
	public ChessComponent compo;
	public JButton newGame;
	public JButton quit;
	public newGameListenerClass newGameListener = new newGameListenerClass();
	public quitListenerClass quitListener = new quitListenerClass();
	public Font theFont;
	public Color squareColor = (Color.GREEN).darker();
	public Rectangle borderRect;

}