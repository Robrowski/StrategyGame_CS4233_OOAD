package boards;
import java.util.Comparator;

import puzzle.Piece;


/** A chunk is a set of pieces making up an uncompleted puzzle board
 * 
 * @author rpdabrowski
 *
 */
public class Chunk extends Board implements Comparator<Chunk>{
	int unplaced_pieces; 
	
	public Chunk(int width, int height) {
		super(width, height);
	}


	public Chunk(Board b) {
		super(b.width, b.height);
		this.unplaced_pieces = this.width*this.height;

	}


	@Override
	public int compare(Chunk o1, Chunk o2) {
		return o1.unplaced_pieces - o2.unplaced_pieces;
	}
	
	@Override
	public boolean placeAt(Piece p, int x, int y){
		boolean res = super.placeAt(p,x,y);
		if (res) this.unplaced_pieces--;
		return res;
	}
	
}
