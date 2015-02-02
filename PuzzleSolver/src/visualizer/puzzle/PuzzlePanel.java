/**
 * 
 */
package visualizer.puzzle;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import notifications.PuzzleObserver;
import puzzle.Piece;

/**
 * @author rpdabrowski
 *
 */
public class PuzzlePanel extends JPanel implements PuzzleObserver,
		ComponentListener {

	// TODO could have this extend Board... since this holds a board?

	private static final long serialVersionUID = 6010899997957217708L;
	private PiecePanel[][] piecePanels;
	private int width, height;

	public PuzzlePanel(int width2, int height2) {
		super();

		// Initialize data structures
		this.width  = width2;
		this.height = height2;
		this.piecePanels = new PiecePanel[width][height];

		// Constraints for the double array of PiecePanels
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1; // Allows for stretching
		c.weighty = 1;

		// Using GridBag because its easy and can resize
		this.setLayout(new GridBagLayout());

		// Makes the piece panels and puts them in the layout
		for (int x = 0; x < width; x++) {
			c.gridx = x;
			for (int y = 0; y < height; y++) {
				c.gridy = height - y - 1;
				PiecePanel n = new PiecePanel();
				this.piecePanels[x][y] = n; // Store in array for updating later
				this.add(n, c); // Pass constraints with new panel
			}
		}

		// Listen for resizing
		this.addComponentListener(this);
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		// Panels are resized automatically, but their static members need to be
		// updated only once
		PiecePanel p = this.piecePanels[0][0];
		Dimension d = p.getSize();
		PiecePanel.setPieceSize(d.width, d.height);
		// Now the pieces magically repaint on their own...
	}

	@Override
	public Dimension getPreferredSize() {
		// Arbitrary size that takes up most of a screen
		return new Dimension(1000, 700);
	}

	/** Gets the piece panel so that its piece may be updated 
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private PiecePanel getPiecePanel(int x, int y){
		// The list origin is actually at the top left.. so the 
		// row indices are mixed up
		return this.piecePanels[x][y];
	}

	@Override
	public void notifyPlacement(Piece p, int x, int y) {
		PiecePanel toUpdate = this.getPiecePanel(x, y);
		toUpdate.updatePiece(p);
		toUpdate.highlight(false);
	}

	@Override
	public void notifyAttemptedPlacement(Piece p, int x, int y) {
		PiecePanel toUpdate = this.getPiecePanel(x, y);
		toUpdate.updatePiece(p);
		toUpdate.highlight(true);
	}

	@Override
	public void notifyRemove(int x, int y) {
		PiecePanel toUpdate = this.getPiecePanel(x, y);
		toUpdate.updatePiece(null);
		toUpdate.highlight(true);
	}

	@Override
	public void notifyStatusUpdate(String id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
	}
}
