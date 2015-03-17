package humansOfNY_withDatabase;

/**
 * @author Ahmet This class accesses database results and sets the images
 */
public class AccessDatabase {

	private String[] images;

	private double scaleValue = 1.0; // scale percentage for image

	public int count = 0;

	/**
	 * Construct the AccessDatabase
	 * 
	 * @param newImages the default image urls as an array
	 */
	public AccessDatabase(String[] newImages) {
		images = new String[newImages.length];
		images = newImages;

	}

	/**
	 * changes the image urls
	 * 
	 * @param newImages image urls as an array
	 */
	public void setImages(String[] newImages) {
		count = 0;
		images = new String[newImages.length];
		images = newImages;

	}

	/**
	 * Return the image to use in PhotoPanel
	 * 
	 * @return: images[count] the correct image in order
	 */
	public String getImage() {
		return images[count];
	}

	/**
	 * Change the scale
	 * 
	 * @param: scaleValue scale amount
	 */
	public void setScaleValue(double scaleValue) {
		this.scaleValue = scaleValue;
	}

	public double getScaleValue() {
		return scaleValue;
	}

	/**
	 * Pass to next image
	 */
	public void nextImage() {

		count++;
		count %= images.length;
		if (images[count] == null)
			count = 0;

	}

	/**
	 * Go back to previous image
	 */

	public void previousImage() {

		count--;
		if (count < 0) {

			int imageLength = 0;
			for (String a : images) {
				if (a != null)
					imageLength++;
			}
			count = imageLength - 1;

		}

	}

	/**
	 * Return the number of images in the selected criteria
	 * 
	 * @return imageLength
	 */
	public int getNumImages() {
		int imageLength = 0;
		for (String a : images) {
			if (a != null)
				imageLength++;
		}
		return imageLength;
	}

	/**
	 * Return the current index used image in the array
	 * 
	 * @return count
	 */
	public int getImageIndex() {
		return count;
	}

}
