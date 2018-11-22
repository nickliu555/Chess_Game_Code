import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Square extends JPanel implements MouseListener{
	//~~~~ Private Member Variables ~~~~
	private static GameBoard board;
	private ChessPiece piece;
	private int row, col;
	private boolean highlight = false;
	
	//~~~~ Constructors ~~~~
	public Square(int r, int c, GameBoard gb, boolean b){
		board = gb;
		row = r;
		col = c;
		if(b)
			this.setBackground(Color.BLACK);
		else
			this.setBackground(Color.WHITE);
		this.addMouseListener(this);
	}
	public Square(int r, int c, GameBoard gb){
		board = gb;
		row = r;
		col = c;
		this.setBackground(Color.BLACK);		
		this.addMouseListener(this);
	}
	
	public Boolean getHighlight()
	{
		return highlight;
	}
	
	public void setHighlight(Boolean b)
	{
		highlight = b;
		this.repaint();
	}
	
	public ChessPiece getPiece()
	{
		return piece;
	}
	
	public void setPiece(ChessPiece cp)
	{
		piece = cp;
		this.repaint();
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public GameBoard getBoard()
	{
		return board;
	}
	
	//~~~~ some functions to specify the color of this square ~~~~
	public void setColor( int c ){//1 means black, 0 means white
		if(c==1)
			this.setBackground(Color.BLACK);
		else
			this.setBackground(Color.WHITE);
	}

	public void setColor( boolean black ){//true means black, false means white
		if(black)
			this.setBackground(Color.BLACK);
		else
			this.setBackground(Color.WHITE);
	}
	public void setColor( Color c ){
		this.setBackground(c);
	}


	public void paintComponent(Graphics g){
		Color blackColor = new Color(255, 0, 0, 100);
		Color whiteColor = new Color(0, 0, 255, 100);
		
		super.paintComponent(g);
		if(piece!=null)
			piece.draw(g);
		if( this.getHighlight() )
		{
				g.setColor( new Color(255, 255, 0, 100) );
				g.fillRect(0, 0, this.getWidth(), this.getHeight() );
			
		}
	}
	
	//~~~~ MouseListener stuff ~~~~
	public void mousePressed(MouseEvent arg0) {
		//when I am clicked, I will tell the board that I was clicked!
		board.clicked(this);		
	}

	public void mouseClicked(MouseEvent arg0) {}

	public void mouseReleased(MouseEvent arg0) {}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}


}
