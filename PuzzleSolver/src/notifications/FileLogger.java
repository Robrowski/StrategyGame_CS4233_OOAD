
package notifications;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import puzzle.Piece;

public class FileLogger implements PuzzleObserver {

	private Writer logWriter;

	/** The state of the logger */
	private boolean active = true;

	public FileLogger(String log_name) {
		try {
			logWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(log_name + "_log.txt"), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	/** Close up the log writing service */
	public void close() {
		try {
			logWriter.close();
		} catch (IOException e) {
			logException(e);
		}
	}

	/**
	 * Writes the given message to the appropriate log file
	 * 
	 * @param msg
	 */
	public void println(String msg) {
		if (!active)
			return;

		try {
			logWriter.write(msg + "\n");
			logWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes the given message to the appropriate log file with tabs
	 * 
	 * @param msg
	 *            the message
	 * @param tabs
	 *            the number of tabs
	 */
	public void println(String msg, int tabs) {
		for (int i = 0; i < tabs; i++)
			msg = "    " + msg;
		println(msg);
	}

	/**
	 * Activates the logging system
	 */
	public void activate() {
		active = true;
	}

	/**
	 * Deactivates the logging system
	 */
	public void deactivate() {
		active = false;
	}

	/**
	 * Logs important information from an exception
	 * 
	 * @param e
	 */
	public void logException(Exception e) {
		// TODO throw an exception and see what this stuff actually is...
		println("\n\n A " + e.getClass().toString() + " was caught.");
		println("Localized Description: " + e.getLocalizedMessage());
		println("Message: " + e.getMessage());
		println("Stack trace...");
		for (StackTraceElement st : e.getStackTrace()) {
			println("	" + st.toString());
		}
	}

	@Override
	public void notifyPlacement(Piece p, int x, int y) {
		if (p != null)
			println("Placed: (" + x + "," + y + ") " + p.toString());
		else
			println("Placed: (" + x + "," + y + ") ");
	}

	@Override
	public void notifyAttemptedPlacement(Piece p, int x, int y) {
		if (p != null)
			println("Attempted: (" + x + "," + y + ") " + p.toString());
		else
			println("Attempted: (" + x + "," + y + ") ");
	}

	@Override
	public void notifyRemove(int x, int y) {
		println("Removed: (" + x + "," + y + ") ");
	}

	@Override
	public void notifyStatusUpdate(String id) {
		println("Status:" + id + ": " + NotificationSystem.getStatus(id));
	}
}
