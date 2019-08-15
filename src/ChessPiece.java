import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public abstract class ChessPiece {	
	private Image img;
	//variable for what team/color i am
	private boolean black;
	//variable for what Square I'm sitting on 
	private Square currSq;
	private boolean check = false;
	
	//Constructor :  you will need some more parameters!
	public ChessPiece(String im, boolean tm, Square sq){
		loadImage(im);
		currSq = sq;
		black = tm;
	    
		//set up your other variables


	}//end constructor
	
	//returns current square
	public Square getSquare()
	{
		return currSq;
	}
	
	//change the current square of the piece
	public void setSquare(Square s)
	{
		currSq = s;
	}
	
	//return the color of the piece
	//true = black; false = white
	public boolean getColor()
	{
		return black;
	}
	
	//helper function for loading up your image
	private void loadImage( String im ){
		img = Toolkit.getDefaultToolkit().getImage( getClass().getResource(im) );		
	    
		MediaTracker tracker = new MediaTracker (new Component () {});
		tracker.addImage(img, 0);
		//block while reading image
		try { tracker.waitForID (0); }
	        catch (InterruptedException e) {
	        	JOptionPane.showMessageDialog(null, "Error reading file");
	        }
	}//end loadImage

	public void draw(Graphics g){
		g.drawImage(img,0,0,90,90,null,null);
	}
	
	public abstract boolean isMoveLegal(Square dest);
	public abstract boolean isKing();
	public abstract boolean isPawn();
	
	
	public boolean isKilling(ChessPiece cp, Square end)
	{
		if( ( end.getPiece() != null ) && ( end.getPiece().getColor() != cp.getColor() ) )
		{
			return true;
		}
		else 
			return false;
	}
	
	//if the 'color' team is in check
	//if 'color' is true then black; is 'color' is false then white
	public boolean isCheck(boolean color)
	{
		
		ChessPiece theKing = null;
		for(int x=0; x<8; x++) 
		{
			for(int y=0; y<8; y++)
			{
				Square theSquare = this.getSquare().getBoard().getSquareArray()[x][y];
				if( theSquare.getPiece() != null && theSquare.getPiece().isKing() && theSquare.getPiece().getColor() == color )
				{
					for(int a=0; a<8; a++) 
					{
						for(int b=0; b<8; b++)
						{
							Square aSquare = this.getSquare().getBoard().getSquareArray()[a][b];
							if( aSquare.getPiece() != null && aSquare.getPiece().getColor() != color && aSquare.getPiece().isMoveLegal(theSquare))
								return true;
						}
					}
				}
					
			}
		}
		return false;
					
	}
	
	
	public void move(Square dest) 
	{
		Square originalSquare = this.getSquare();
		
		//if the king is going to be killed
		if( this.isMoveLegal( dest ) )
		{
			if(dest.getPiece()!=null)
				if(dest.getPiece().isKing())
				{
					if(dest.getPiece().getColor())
						JOptionPane.showMessageDialog(null, "Starks (White) Team Wins!");
					else
						JOptionPane.showMessageDialog(null, "Lannisters (Black) Team Wins!");
					
					dest.getBoard().dispose();
				}
			
			//move the piece to its location
			( this.getSquare() ).setPiece(null);
			dest.setPiece(this);
			this.setSquare(dest);
			
			
			//Check
			//int danger = 0;
			//check = false;
			boolean checkmateConfirm = true;
			boolean otherColor;
			//ChessPiece theKing = null;
			//ChessPiece theKiller = null;
			//ChessPiece confirmKiller = null;
			if(this.getColor())
				otherColor = false;
			else
				otherColor = true;
			
			//determines if actually checkmate
			if(isCheck(otherColor))
			{
				for(int x=0; x<8; x++) 
				{
					for(int y=0; y<8; y++)
					{
						//get all the enemy pieces that may be threats to the king
						if(dest.getBoard().getSquareArray()[x][y].getPiece() != null && dest.getBoard().getSquareArray()[x][y].getPiece().getColor() != this.getColor())
							for(int a=0; a<8; a++)
							{
								for(int b=0; b<8; b++)
								{
									ChessPiece apiece = dest.getBoard().getSquareArray()[x][y].getPiece();
									//make the piece make all of its possible moves and check to see if the king is still in check
									if(apiece.isMoveLegal(dest.getBoard().getSquareArray()[a][b]))
									{
										Square originalSq = apiece.getSquare();
										ChessPiece squarePiece = null;
										if(dest.getBoard().getSquareArray()[a][b].getPiece()!=null)
											squarePiece = dest.getBoard().getSquareArray()[a][b].getPiece();
										
										//return the piece back to its original spot
										apiece.setSquare(dest.getBoard().getSquareArray()[a][b]);
										dest.getBoard().getSquareArray()[a][b].setPiece(apiece);
										originalSq.setPiece(null);
										
										//if their is a way to 'escape' then it is not a legit checkmate
										if(isCheck(otherColor)==false)
											checkmateConfirm = false;
										
										//prevents accidentally killing a piece while testing another piece's possible moves
										apiece.setSquare(originalSq);
										originalSq.setPiece(apiece);
										dest.getBoard().getSquareArray()[a][b].setPiece(null);
										if(squarePiece != null)
											squarePiece.setSquare(dest.getBoard().getSquareArray()[a][b]);
											dest.getBoard().getSquareArray()[a][b].setPiece(squarePiece);
									}
								}
							}
					}
				}
				//if the checkmate is legit then award the winner and end game
				if(checkmateConfirm)
				{
					JOptionPane.showMessageDialog(null, "CheckMate");
					if(this.getColor())
						JOptionPane.showMessageDialog(null, "Lannisters (Black) Team Wins!");
					else
						JOptionPane.showMessageDialog(null, "Starks (White) Team Wins!");
				
					dest.getBoard().dispose();
				}
			}
			
			//if it not a legit checkmate and just a check
			if(isCheck(otherColor) && checkmateConfirm == false)
			{
				JOptionPane.showMessageDialog(null, "Check");
				
			}
			
			
		}
		else
		{
			JOptionPane.showMessageDialog(null, "This Move is Invalid");
		}
		
		//Promotion
		if(this.isPawn())
		{
			if(this.getColor())
			{
				//if its at the 'endzone' for the appropriate color then turn pawn into queen
				if(this.getSquare().getRow()==7 && this.isCheck(this.getColor())==false )
				{
					Square sss = this.getSquare();
					this.setSquare(null);
					sss.setPiece(null);
					sss.getBoard().addQueen(true, sss);
					JOptionPane.showMessageDialog(null, "The Pawn has been promoted to a Queen!");
				}
				//prevents promotion from occuring if the pawn's king is in check
				else if(this.getSquare().getRow()==7 && this.isCheck(this.getColor())==true)
				{
					JOptionPane.showMessageDialog(null, "This move is invalid since your King is in Checkabcde");
					
					ChessPiece squarePiece = null;
					if(dest.getPiece()!=null)
						squarePiece = dest.getPiece();
					
					this.getSquare().setPiece(null);
					this.setSquare(originalSquare);
					originalSquare.setPiece(this);
					if(squarePiece!=null)
					{
						dest.setPiece(squarePiece);
						squarePiece.setSquare(dest);
					}
					
					boolean turn = this.getSquare().getBoard().getTurn();
	
					if(turn)
						this.getSquare().getBoard().setTurn(false);
					else
						this.getSquare().getBoard().setTurn(true);
				}
				
			}
			else
			{
				//if its at the 'endzone' for the appropriate color then turn pawn into queen
				if(this.getSquare().getRow()==0 && this.isCheck(this.getColor())==false )
				{
					Square sss = this.getSquare();
					this.setSquare(null);
					sss.setPiece(null);
					sss.getBoard().addQueen(false, sss);
					JOptionPane.showMessageDialog(null, "The Pawn has been promoted to a Queen!");
					
					/*
					boolean otherTeam;
					if(this.getColor())
						otherTeam = false;
					else
						otherTeam = true;
					
					if(isCheck(otherTeam));
						JOptionPane.showMessageDialog(null, "Check");
					*/
						
				}
				//prevents promotion from occuring if the pawn's king is in check
				else if(this.getSquare().getRow()==0 && this.isCheck(this.getColor())==true)
				{
					JOptionPane.showMessageDialog(null, "This move is invalid since your King is in Check");
					
					ChessPiece squarePiece = null;
					if(dest.getPiece()!=null)
						squarePiece = dest.getPiece();
					
					this.getSquare().setPiece(null);
					this.setSquare(originalSquare);
					originalSquare.setPiece(this);
					if(squarePiece!=null)
					{
						dest.setPiece(squarePiece);
						squarePiece.setSquare(dest);
					}
					
					boolean turn = this.getSquare().getBoard().getTurn();
	
					if(turn)
						this.getSquare().getBoard().setTurn(false);
					else
						this.getSquare().getBoard().setTurn(true);
				}
				
			}

		}// end of isPawn()
		
		
	}
}
