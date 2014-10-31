/**
 * 
 */
package visualizer.puzzle;

import java.awt.GridLayout;

import javax.swing.JPanel;

import puzzle.Piece;
import notifications.IPuzzleObserver;

/**
 * @author rpdabrowski
 *
 */
public class PuzzlePanel extends JPanel implements IPuzzleObserver  {

	// TODO could have this extend Board... since this holds a board?

	private static final long serialVersionUID = 6010899997957217708L;
	PiecePanel[] piecePanels;
	int width, height;

	public PuzzlePanel(int width2, int height2) {
		this.width  = width2;
		this.height = height2;
		this.piecePanels = new PiecePanel[width*height];
		// Should probably register in some way to the solver...
		this.setLayout( new GridLayout(height,width));
		PiecePanel.setSize(40);
		
		// Makes the piece panels and puts them in the layout
		for (int i = 0; i < width*height; i++){
			this.piecePanels[i] = new PiecePanel();
			this.add(this.piecePanels[i]);
		}	
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
		return this.piecePanels[ (height-y-1)*this.width + x ];
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
}
