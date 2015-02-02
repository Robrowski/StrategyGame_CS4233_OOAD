/**
 * 
 */
package visualizer.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

import notifications.NotificationSystem;
import notifications.PuzzleObserver;
import puzzle.Piece;
import solver.PuzzleGenerator;
import solver.Solver;

/**
 * @author rpdabrowski
 *
 */
public class ControlPanel extends JPanel implements PuzzleObserver {

	private static final long serialVersionUID = 2922161435705130922L;
	private JLabel status_box = new JLabel("Long-Named JTextField 4");
	
	public ControlPanel(){
		super();
		this.setPreferredSize(new Dimension( 150, 0)); 
		this.setLayout( new GridLayout(3,1));

	    this.add(new BasicArrowButton(SwingConstants.EAST,  Color.WHITE, Color.WHITE,Color.GREEN, Color.WHITE));
	    this.add(new BasicArrowButton(SwingConstants.EAST,  Color.WHITE, Color.WHITE,Color.MAGENTA, Color.WHITE));
	    this.add( status_box);
	    
		NotificationSystem.register(this);
	}

	/** Reads the statuses and puts them in the label    	 */
	protected void updateStatus(){
		status_box.setText("<html>Generator: " + NotificationSystem.getStatus(PuzzleGenerator.id) + 
						   "<br> Solver:    " + NotificationSystem.getStatus(Solver.id) + "</html>");
	}
	
	
	@Override
	public void notifyPlacement(Piece p, int x, int y) {
		updateStatus();
	}

	@Override
	public void notifyAttemptedPlacement(Piece p, int x, int y) {
		updateStatus();		
	}

	@Override
	public void notifyRemove(int x, int y) {
		updateStatus();
		
	}

	@Override
	public void notifyStatusUpdate(String id) {
		// TODO Auto-generated method stub

	}
}
