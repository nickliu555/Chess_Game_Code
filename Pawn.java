
public class Pawn extends ChessPiece{

	public Pawn(String im, boolean tm, Square lc) 
	{
		super(im, tm, lc);
		lc.setPiece(this);
		
	}

	//pawn can advance 2 squares up if first move. can only advance 1 square up if else
	//pawn kills enemy in front sideways
	public boolean isMoveLegal(Square dest) 
	{
		int dr = 0;
		int dc = 0;
		Boolean isBlack = this.getColor();
		int colorRow = 0;
		if(isBlack) // if it is black
		{
			colorRow = 1; //original spot
			dr = ( dest.getRow() ) - ( this.getSquare().getRow() );
			dc = ( dest.getCol() ) - ( this.getSquare().getCol() );
		}
		else // if its white
		{
			colorRow = 6; // original spot
			dr = ( this.getSquare().getRow() ) - ( dest.getRow() );
			dc = ( this.getSquare().getCol() ) - ( dest.getCol() );
		}
			
		if(isKilling(this,dest)) // if killing
		{
			if( (dc == 1 || dc == -1) && dr == 1 ) // move sideways
				return true;
			else 
				return false;
		}
		else // if not killing
		{
			if( dest.getPiece()!=null )
				return false;
			if( (this.getSquare().getRow() == colorRow) && (dr == 1 || dr == 2) && dc == 0 ) // original spot move
				return ( this.getSquare().getBoard().isBlocked(this.getSquare(), dest) == false );
			else if( dr == 1 && dc == 0) // not at original spot move
				return ( this.getSquare().getBoard().isBlocked(this.getSquare(), dest) == false );
				else 
					return false;
		}
		
		
	
	}
	
	public boolean isKing()
	{
		return false;
	}
	
	public boolean isPawn()
	{
		return true;
	}

}// end of class
