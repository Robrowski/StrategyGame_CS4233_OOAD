package solver;

import java.util.Collection;
import java.util.LinkedList;

import puzzle.Connection;
import puzzle.Piece;
import notifications.NotificationSystem;
import boards.Board;
import boards.IBoard;

/** Generates a valid puzzle and returns it as Collection<Piece>
 * 
 * @author rpdabrowski
 *
 */
public class PuzzleGenerator  {

	public static final String id = "PuzzleGenerator";
	
	/** Generate a puzzle of given height and width with the given number of colors
	 * 
	 * @param width
	 * @param height
	 * @param colors
	 * @return
	 */
	public static Collection<Piece> generateRandomPuzzle(int width, int height, int colors, int sleep_ms){
		Collection<Piece> pieces = new LinkedList<Piece>();
		IBoard b = new Board(width, height);
		NotificationSystem.setStatus(id,"generating pieces");
		// Generates the puzzle solution (left to right)(bottom to top)
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				// Connections for a single piece
				Connection[] conns = new Connection[4];

				// Bottom and left should either be flat or match the previous pieces
				if (y == 0) conns[0] = Connection.FLAT; //Bottom
				else conns[0] =  b.getPiece(x, y-1).top();
				if (x == 0) conns[1] = Connection.FLAT; // Left
				else conns[1] =  b.getPiece(x-1, y).right();

				// Top and right should either be flat or random
				if (y == height - 1) conns[2] = Connection.FLAT;
				else conns[2] = Connection.random(colors);
				if (x == width - 1) conns[3] = Connection.FLAT;
				else conns[3] = Connection.random(colors);

				// Make and place the piece
				Piece p = new Piece(conns);
				b.placeAt(p, x, y); // Place on the board for reference
				pieces.add(p); // Add the pieces to the list for returns
				NotificationSystem.notifyAll(p, x, y); // Notification for visualization
				NotificationSystem.setStatus(id,"Piece: " + x + ", " + y);

				// Sleep statement for making visualization/animations
				try {
					Thread.sleep(sleep_ms);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		NotificationSystem.setStatus(id,"done");
		return pieces;
	}

	// TODO  more shapes? uniqueness? pictures?

}
