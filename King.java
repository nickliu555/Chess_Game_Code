
public class King extends ChessPiece
{

	public King(String im, boolean tm, Square lc) 
	{
		super(im, tm, lc);
		lc.setPiece(this);
	}
	
	//king can only move 1 square around him
	public boolean isMoveLegal(Square dest) 
	{
		int dr = Math.abs( ( this.getSquare().getRow() ) - ( dest.getRow() ) );
		int dc = Math.abs( ( this.getSquare().getCol() ) - ( dest.getCol() ) );
		
		if( (dr == 1 && dc == 1) || (dr == 1 && dc == 0) || (dr == 0 && dc == 1) && ( this.getSquare().getBoard().isBlocked(this.getSquare(), dest) == false ) )
		{
			//if(isCheck())
				//return false;
			if(isKilling(this,dest))
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
		return true;
	}
	
	public boolean isPawn()
	{
		return false;
	}
	
}



