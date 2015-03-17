package humansOfNY_withDatabase;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This is the panel used in the South of the main panel
 */
public class LabelPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel label;

	/**
	 * Construct the LabelPanel
	 */
	public LabelPanel(int numImages, int index) {

		label = new JLabel();
		label.setFont(new Font("Serif", Font.PLAIN, 32));
		this.add(label);
		updateLabel(numImages, index);
	}

	/**
	 * updates the # of images out of # of selected images
	 * 
	 * @param numImages
	 * 
	 * @param index
	 */
	public void updateLabel(int numImages, int index) {

		label.setText("Image: " + (index + 1) + "/" + numImages);

	}
}
