package puzzle;

/**
 * 
 */

/** Puzzle piece
 * 
 * currently assume 4 sided pieces
 * 
 * 
 * @author rpdabrowski
 *
 */
public class Piece {
	
	Connector[] sides;
	/*           2
	 *      ___________
	 *     |           |
	 *     |           |
	 *  1  |           | 3
	 *     |           |
	 *     |___________|
	 *           0 
	 *   <-- origin
	 */
	
	static int next_id = 0;
	public int id;
	
	private void assignID(){
		this.id = Piece.next_id;
		Piece.next_id++;
	}
	
	
	public Piece(Connector[] sides){
		this.sides = sides;
		this.assignID();
	}
	
	public Piece(Connection[] s){
		this.sides = new Connector[s.length];
		
		for (int i = 0; i < s.length; i++){
			this.sides[i] = new Connector(s[i]);
		}	
		this.assignID();
	}
	
	public Piece(Connection a, Connection b, Connection c, Connection d) {
		this.sides = new Connector[4];
		this.sides[0] = new Connector(a);
		this.sides[1] = new Connector(b);
		this.sides[2] = new Connector(c);
		this.sides[3] = new Connector(d);
		this.assignID();
	}

	/** Tests if this piece is a corner - 2 consecutive flat sides
	 * 
	 * @return
	 */
	public boolean isCorner(){
		int flats = 0;
		for (Connector c: this.sides)
			if (c.c == Connection.FLAT) flats++;
		
		if (flats == 2 ){
			// Check for the two cases where there are 2 flats aren't consecutive
			return !((this.sides[0].c.equals(Connection.FLAT) && this.sides[2].c.equals(Connection.FLAT)) ||
				     (this.sides[1].c.equals(Connection.FLAT) && this.sides[3].c.equals(Connection.FLAT)));
			}
		return false;
	}

	/** Tests if this piece is an edge - has only one flat side
	 * 
	 * @return
	 */
	public boolean isEdge() {
		// Return true if exactly one flat side
		int flats = 0;
		for (Connector c: this.sides)
			if (c.c == Connection.FLAT) flats++;

		return flats == 1;
	}
	

	/** Tests if this piece is an inner piece - zero flat sides
	 * 
	 * @return
	 */
	public boolean isInner() {
		for (Connector c : this.sides)
			if (c.c == Connection.FLAT)	return false;

		return true;
	}
	
	
	/** Returns true if the two pieces can be connected
	 * 
	 * @param corner
	 * @return
	 */
	public boolean canConnectRotated(Piece corner) {
		for (Connector ca: this.sides)
			for (Connector cb: corner.sides)
				if ( ca.c.equals(cb.c) && ca.c != Connection.FLAT)
					return true;
					
		return false;
	}
	
	/** Can connect pieces, assumes both are rotated first
	 * 
	 * @param c
	 * @param side side on this to connect to
	 * @return
	 */
	public boolean canConnect(Piece c, int side){
		return this.sides[side].c == c.sides[(side + 2) % 2].c;
	}
	

	
	/** Returns true if the two pieces have sides in the same order
	 * 
	 * @param corner2
	 * @return
	 */
	public boolean isEquivalent(Piece corner2) {
		// Since two pieces can be rotated... copy this.sides and add the first 3 to the end
		Connector[] c = new Connector[2*this.sides.length - 1];
		for (int i = 0; i < c.length; i++){
			c[i] = this.sides[i % this.sides.length];			
		}
		
		// Now compare - if we get 4 consecutive connectors 
		for (int start = 0; start < this.sides.length; start++ ){// Start index in c
			int i = 0;
			for (; i < this.sides.length; i++)
				if (!corner2.sides[i].equals( c[i + start]))	break;
			if (i == 4) return true; // 4 consecutive == equivalent
		}	
		return false;
	}
	

	/** Rotates the sides in place
	 * 
	 */
	public void rotate(){
		Connector tmp = this.sides[0];
		for (int i = 0; i < this.sides.length - 1; i++){
			this.sides[i] = this.sides[i + 1];			
		}	
		this.sides[this.sides.length -1 ]= tmp;
	}
	
	/** Prints the id and connections of a piece
	 * 
	 */
	public void print(){
		System.out.print("ID: " + this.id );
		System.out.print(":: "  + this.toString());
		System.out.println(" ");
	}
	

	public String toString(){
		String s = "";
		for (Connector c: this.sides) s = s + c.toString() + " ";
		return s;
	}

	public Connection bottom(){
		return this.sides[0].c;
	}
	
	public Connection left(){
		return this.sides[1].c;
	}
	
	public Connection top(){
		return this.sides[2].c;
	}
	
	public Connection right(){
		return this.sides[3].c;
	}


	public Connector[] getSides() {
		return this.sides;
	}
	
}
