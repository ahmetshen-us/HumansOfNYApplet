package humansOfNY_withDatabase;

/**
 * Author: Ahmet Sen, John Devivo
 * 05/01/2014
 */

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JFrame;

/**
 * This is the main class that runs the application
 * 
 */
public class HumansOfNewYork extends JFrame {

	private static final long serialVersionUID = 1L;
	private ControlPanel contPanel;
	private PhotoPanel photoPanel;
	private LabelPanel labelPanel;
	private AccessDatabase access;
	private Database database;

	public HumansOfNewYork() {
		this.setSize(500, 600);
		database = new Database();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			database.setQuery("select url from picture ");
		} catch (IllegalStateException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		access = new AccessDatabase(database.getImages());

		photoPanel = new PhotoPanel(access);

		labelPanel = new LabelPanel(access.getNumImages(),
				access.getImageIndex());

		contPanel = new ControlPanel(access, photoPanel, labelPanel);

		this.add(contPanel, BorderLayout.NORTH);
		this.add(photoPanel, BorderLayout.CENTER);
		this.add(labelPanel, BorderLayout.SOUTH);
		this.setVisible(true);
		this.setResizable(false);
	}

	/**
	 * This is the main method that starts the whole application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new HumansOfNewYork();

	}

}
