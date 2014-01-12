package networkChessPackage2;

import java.rmi.Remote;
import java.util.ArrayList;

public interface ChessInterface extends Remote
{
	public void repaint();
	
	//Should also updatePieceList.
	public void setBlackList(ArrayList<BlackPiece> list);
	
	//Should also updatePieceList.
	public void setWhiteList(ArrayList<WhitePiece> list);
	
	public void setPlayerBlack();
	
	public void setPlayerWhite();
	
	public void setBlackInCheck(boolean setTo);
	
	public void setWhiteInCheck(boolean setTo);
	
	public void setWhiteInMate();
	
	public void setBlackInMate();
	
	public void setDraw();
	
}
