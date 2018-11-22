
public class Knight extends ChessPiece
{

	public Knight(String im, boolean tm, Square lc) 
	{
		super(im, tm, lc);
		lc.setPiece(this);
	}
	
	//knight can move in 'L' shape
	public boolean isMoveLegal(Square dest) 
	{
		int dr = Math.abs( ( this.getSquare().getRow() ) - ( dest.getRow() ) );
		int dc = Math.abs( ( this.getSquare().getCol() ) - ( dest.getCol() ) );
		if( (dr == 2 && dc == 1) || (dr == 1 && dc == 2) )
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

