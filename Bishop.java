
public class Bishop extends ChessPiece
{

	public Bishop(String im, boolean tm, Square lc) 
	{
		super(im, tm, lc);
		lc.setPiece(this);
	}
	
	//bishop can only move diagonally
	public boolean isMoveLegal(Square dest) 
	{
		int dr = Math.abs( ( this.getSquare().getRow() ) - ( dest.getRow() ) );
		int dc = Math.abs( ( this.getSquare().getCol() ) - ( dest.getCol() ) );
		
		if( ( dr == dc ) && ( this.getSquare().getBoard().isBlocked(this.getSquare(), dest) == false ) )
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
