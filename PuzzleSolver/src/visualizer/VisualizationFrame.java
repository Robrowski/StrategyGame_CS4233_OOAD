/**
 * 
 */
package visualizer;


import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import visualizer.control.ControlPanel;
import visualizer.control.KeyControls;
import visualizer.puzzle.PuzzlePanel;

/** The base of the entire GUI
 * @author rpdabrowski
 *
 */
public class VisualizationFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2879555493764751765L;



	public VisualizationFrame(int width, int height, String name) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		this.setLayout(new BorderLayout());
		this.setTitle(name);
		// Controls
		this.addKeyListener(new KeyControls());

		// Right side
		this.add(new ControlPanel(), BorderLayout.EAST);

		// Left side
		puzzle_panel = new PuzzlePanel( width,  height); // This will take a Board someday
		this.add(puzzle_panel, BorderLayout.CENTER);
		// Retarded, but it resizes properly when set to the center

		this.pack(); // Sets the size to the combination of the components
		this.setVisible(true);
	}
	
	PuzzlePanel puzzle_panel;
	
	
	public PuzzlePanel getPuzzlePanel(){
		return this.puzzle_panel;
	}
}