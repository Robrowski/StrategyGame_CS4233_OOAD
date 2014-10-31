/**
 * 
 */
package visualizer.puzzle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import puzzle.Piece;

/**
 * @author rpdabrowski
 *
 */
public class PiecePanel extends JPanel {

	private static final long serialVersionUID = 8206863799890863678L;
	/* pixel origin
	 * (0,0)
	 *           2
	 *      ___________
	 *     |           |
	 *     |           |
	 *  1  |           | 3
	 *     |           |
	 *     |___________|
	 *           0 
	 *   <-- origin
	 */
	private static Polygon bottom, left, top, right;
	private Piece p = null; // The puzzle piece being visualized
	private static int s = -1; // side length
	
	/** Sets the size for all visualized puzzle pieces
	 * 
	 * @param s
	 */
	public static void setSize(int s){
		PiecePanel.s = s;
		int c = s/2;
		
		bottom = new Polygon(
				new int[]{s, c, 0},
				new int[]{s, c, s},
				3);

		left = new Polygon(
				new int[]{0, c, 0},
				new int[]{0, c, s},
				3);

		top = new Polygon(
				new int[]{0, c, s},
				new int[]{0, c, 0},
				3);

		right = new Polygon(
				new int[]{s, c, s},
				new int[]{0, c, s},
				3);
	}
	
	/** Takes a side length, scales the triangles appropriately
	 * 
	 * @param s
	 */
	public PiecePanel(){
		if (s == -1){
			PiecePanel.setSize(20);
		}
	}

	public PiecePanel( Piece p){
		this();
		this.p = p;
	}

	
	/** Highlights the puzzle piece on the board. True gets green, 
	 * false gets white to indicate visitation
	 * 
	 * @param tf
	 */
	public void highlight(boolean tf){
		if (tf){
			this.setBorder( BorderFactory.createLineBorder(Color.GREEN));
		} else{
			// TODO: How to clear the border?
			this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		}
	}
	
	

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(s, s);
	}

	/** Repaints this component with the given puzzle piece
	 * 
	 * @param p
	 */
	public void updatePiece(Piece p){
		this.p = p;
		this.repaint();
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Only paint if there is a puzzle piece to paint
		if (this.p != null){
			// Make a new graphics engine for 2D drawing
			Graphics2D g2d = (Graphics2D) g.create();
			
			// Draw the components of each part of the piece
			g2d.setColor(p.bottom().color());
			g2d.fill(bottom);

			g2d.setColor(p.left().color());
			g2d.fill(left);

			g2d.setColor(p.top().color());
			g2d.fill(top);
			
			g2d.setColor(p.right().color());
			g2d.fill(right);
			
			// Cleanup
			g2d.dispose();
		}
	}
}
