
public class Rook extends ChessPiece
{

	public Rook(String im, boolean tm, Square lc) 
	{
		super(im, tm, lc);
		lc.setPiece(this);
	}
	
	//rook can only move horizontally of vertically
	public boolean isMoveLegal(Square dest) 
	{
		int dr = Math.abs( ( this.getSquare().getRow() ) - ( dest.getRow() ) );
		int dc = Math.abs( ( this.getSquare().getCol() ) - ( dest.getCol() ) );
		
		if( (dr == 0 || dc == 0) && ( this.getSquare().getBoard().isBlocked(this.getSquare(), dest) == false ) )
		{
			if(isKilling(this,dest) )
				return true;
			else if (dest.getPiece() != null)
				return false;
			else 
				return true;
		}
		else 
			return false;
	}	
	
	public boolean isKing()
	{
		return false;
	}
	
	public boolean isPawn()
	{
		return false;
	}
	
}// end of class
