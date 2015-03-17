package humansOfNY_withDatabase;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

// PhotoPanel used to display loaded images
public class PhotoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	// Create all the necessary fields
	private Image image; // references image to display
	private AccessDatabase access;
	public int count = 0;

	/*
	 * Construct the PhotoPanel
	 */
	public PhotoPanel(AccessDatabase holder) {
		this.access = holder;

//		image = new ImageIcon(this.getClass().getResource(holder.getImage()))
//				.getImage();
		 image = new ImageIcon("src/images/" + holder.getImage()).getImage();
		// System.out.println(this.getClass().getResource("images/"+holder.getImage()));

	}


	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		 image = new ImageIcon("src/images/" + access.getImage()).getImage();

//		image = new ImageIcon(this.getClass().getResource(access.getImage()))
//				.getImage();

		super.paintComponent(g);

		// the following values are used to center the image
		double spareWidth = getWidth() - access.getScaleValue()
				* image.getWidth(null);
		double spareHeight = getHeight() - access.getScaleValue()
				* image.getHeight(null);

		// draw image with scaled width and height
		g.drawImage(image, (int) (spareWidth) / 2, (int) (spareHeight) / 2,
				(int) (image.getWidth(null) * access.getScaleValue()),
				(int) (image.getHeight(null) * access.getScaleValue()), this);
	} // end method paint
} // end class DrawJPanel