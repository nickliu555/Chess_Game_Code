import java.awt.*;
import javax.swing.*;

public class GameBoard extends JFrame{
	private static final int ROWS = 8, COLS = 8;
	Square s[][];
	Boolean first = true;
	int r;
	int rr;
	int c;
	int cc;
	Boolean turn = false; //false = white's turn; true = black's turn
	
	public GameBoard()
	{
		super("Game of Thrones CHESS");
		
		this.setLayout(new GridLayout(ROWS,COLS));
		boolean black = false;
		//be sure to instantiate the array
		s = new Square[ROWS][COLS];

		// birth each element of the array AND add it to the Frame 
		for(int r=0; r<ROWS; r++)
		{
			for(int c=0; c<COLS; c++)
			{
				s[r][c] = new Square(r,c,this,black);
				this.add(s[r][c]);
				if(black)
					black = false;
				else
					black = true;
			}
			if(black)
				black = false;
			else
				black = true;
		}
		
		for(int i=0; i<8; i++)
		{
			Pawn p = new Pawn("housestark.png", false, s[6][i]);
			//s[6][i].setPiece(p);
		}
		
		
		King sk = new King("EddardStark.png",false, s[7][4]);
		Queen sq = new Queen("SansaStark.png",false, s[7][3]);
		Bishop sb1 = new Bishop("RobbStark.png",false, s[7][2]);
		Bishop sb2 = new Bishop("RobbStark.png",false, s[7][5]);
		Knight sk1 = new Knight("JonSnow.png",false, s[7][1]);
		Knight sk2 = new Knight("JonSnow.png",false, s[7][6]);
		Rook sr1 = new Rook("AryaStark.png",false, s[7][0]);
		Rook sr2 = new Rook("AryaStark.png",false, s[7][7]);
	
		
		
		for(int i=0; i<8; i++)
		{
			Pawn p = new Pawn("houselannister.png", true, s[1][i]);
		}
		
		King lk = new King("TywinLannister.png",true, s[0][4]);
		Queen lq = new Queen("CerseiLannister.png",true, s[0][3]);
		Bishop lb1 = new Bishop("LancelLannister.png",true, s[0][2]);
		Bishop lb2 = new Bishop("LancelLannister.png",true, s[0][5]);
		Knight lk1 = new Knight("JaimeLannister.png",true, s[0][1]);
		Knight lk2 = new Knight("JaimeLannister.png",true, s[0][6]);
		Rook lr1 = new Rook("TyrionLannister.png",true, s[0][0]);
		Rook lr2 = new Rook("TyrionLannister.png",true, s[0][7]);

		
		//some finishing touches
		this.setSize(750,750);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	//returns whose turn it is
	public boolean getTurn()
	{
		return turn;
	}
	
	//set whose turn it is
	public void setTurn(boolean b)
	{
		turn = b;
	}
	
	//adds a queen to the board at the given location
	public void addQueen(boolean b, Square ss)
	{
		Queen qq;
		if(b)
			qq = new Queen("CerseiLannister.png",b, ss);
		else
			qq = new Queen("SansaStark.png",b, ss);
		
		if(turn)
			turn = false;
		else
			turn = true;
	}
	
	//determine if 'dest' square is blocked from 'origin' square
	public boolean isBlocked(Square origin, Square dest)
	{
		int dr = ( origin.getRow() ) - ( dest.getRow() ); //difference between selected piece row and destination row
		int dc = ( origin.getCol() ) - ( dest.getCol() ); //difference between selected piece column and destination column
		
		int addR; //how much it is moving in the rows per "cycle" to search for blocking
		int addC; //how much it is moving in the column per "cycle" to search for blocking
		
		//Determining the addR and addC
		if(dr > 0)
			addR = -1;
		else if(dr < 0)
			addR = 1;
		else 
			addR = 0;
		
		if(dc > 0)
			addC = -1;
		else if(dc < 0)
			addC = 1;
		else 
			addC = 0;
		
		int tempR = origin.getRow() + addR;
		int tempC = origin.getCol() + addC;
			
		
		// keep searching until it reaches the destination
		while( (tempR!=dest.getRow() ) || ( tempC!=dest.getCol() ) )
		{
			if(isOutOfBounds(tempR, tempC)==false && s[tempR][tempC].getPiece()!=null) // if there is a piece in between
				return true;
			
			tempR += addR;
			tempC += addC;

		}
		
		return false;
		
	}
	
	//determines if a square position is out of bounds
	public boolean isOutOfBounds( int r, int c){
		if(r<0 || c<0 || r>7 || c>7)
			return true;
		else
			return false;
				
	}
	
	//returns the square array
	public Square[][] getSquareArray()
	{
		return s;
	}
	
	
	//one of the squares will call this function to tell the board it was clicked
	public void clicked(Square whoGotClicked)
	{
		ChessPiece p;
		
		if(first)
		{
			r = whoGotClicked.getRow();
			c = whoGotClicked.getCol();
			p = whoGotClicked.getPiece();
			
			first = false;//set it false for next click
			
			if(s[r][c].getPiece()==null) //if the selected square doesnt have a  piece
			{
				JOptionPane.showMessageDialog(this, "You did not select a piece");
				first = true;
			}
			
			//if right team was selected on the right turn
			else if( (turn == true && s[r][c].getPiece().getColor() == false) || (turn == false && s[r][c].getPiece().getColor() == true) )
			{
				JOptionPane.showMessageDialog(this, "It is not your turn!");
				first = true;
			}
			
			else
			{
				// search through all the squares and highlight the squares where the piece has a legal move
				for(int row=0; row<8; row++)
				{
					for(int col=0; col<8; col++)
					{
						if( whoGotClicked.getPiece().isMoveLegal(s[row][col]) )
						{
							s[row][col].setHighlight(true);
						}
					}
				}
			}
			//else
				//JOptionPane.showMessageDialog(this, "It seems that you have clicked a piece at: ("
				//									 +whoGotClicked.getRow()+", "+whoGotClicked.getCol()+")");
		}
		else // second click
		{
			//JOptionPane.showMessageDialog(this, "It seems that you have clicked: ("
			//									+whoGotClicked.getRow()+", "+whoGotClicked.getCol()+")");
			//JOptionPane.showMessageDialog(this, "Moved from ("+r+", "+c+")"
			//									+" to ("+whoGotClicked.getRow()+", "+whoGotClicked.getCol()+")" );
			rr = whoGotClicked.getRow(); // second click row location
			cc = whoGotClicked.getCol(); // second click column location
			p = s[r][c].getPiece();
			
			ChessPiece squarePiece = null;
			if(s[rr][cc].getPiece()!=null)
				squarePiece = s[rr][cc].getPiece();
	
			p.move(s[rr][cc]);
			first = true;
			
			// go through every square and unhighlight
			for(int x=0; x<8; x++) 
			{
				for(int y=0; y<8; y++)
				{
					s[x][y].setHighlight(false);
				}
			}
			
			//if the second click was a valid click 
			if( (s[r][c] != s[rr][cc]) && (p == whoGotClicked.getPiece()) )
			{
				if(turn)
					turn = false;
				else
					turn = true;
			}
			
			//checks if the moving piece's king is in check. If so then undo the move and warn them
			if(p.isCheck(p.getColor()))
			{
				JOptionPane.showMessageDialog(this, "This move is invalid since your King is in Check");
				
				p.getSquare().setPiece(null);
				p.setSquare(s[r][c]);
				s[r][c].setPiece(p);
				if(squarePiece!=null)
				{
					s[rr][cc].setPiece(squarePiece);
					squarePiece.setSquare(s[rr][cc]);
				}
				first = true;
				if(turn)
					turn = false;
				else
					turn = true;
			}
		}// end of else
		
	}	

	//lame main
	public static void main(String[] args) 
	{
		new GameBoard();
	}

}
