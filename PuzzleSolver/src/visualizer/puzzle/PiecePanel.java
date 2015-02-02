/**
 * 
 */
package visualizer.puzzle;

import java.awt.Color;
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
	/** The piece represented by this panel */
	private Piece p = null;
	/** Dimensioned polygons to speed up rendering. */
	private static Polygon bottom, left, top, right;
	/** width and height of the panels in pixels */
	private static int piece_w, piece_h;
	
	/** Sets the size for all visualized puzzle pieces
	 * 
	 * @param s
	 */
	private static void updatePolygons() {
		// Calculate the center of the box
		int cx = piece_w / 2;
		int cy = piece_h / 2;

		bottom = new Polygon(new int[] { piece_w, cx, 0 }, 
							 new int[] { piece_h, cy, piece_h }, 3);

		left = new Polygon(new int[] { 0, cx, 0 },
						   new int[] { 0, cy, piece_h }, 3);

		top = new Polygon(new int[] { 0, cx, piece_w }, 
						  new int[] { 0, cy, 0 }, 3);

		right = new Polygon(new int[] { piece_w, cx, piece_w }, 
							new int[] { 0, cy, piece_h }, 3);
	}

	/**
	 * On PuzzlePanel resize, this is called to set up the PiecePanels for the
	 * new size
	 * 
	 * @param piece_w
	 * @param piece_h
	 */
	public static void setPieceSize(int piece_w, int piece_h) {
		PiecePanel.piece_w = piece_w;
		PiecePanel.piece_h = piece_h;
		updatePolygons();
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
