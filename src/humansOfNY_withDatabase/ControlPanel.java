package humansOfNY_withDatabase;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This is the control panel that handles all the button and JComboBox events
 * 
 * 
 */
public class ControlPanel extends JPanel {

	private static final long serialVersionUID = -2831417750785089523L;

	// Create all the necessary fields
	private JButton play, pause, stop, next, previous;
	private JComboBox<?> speciesBox, genderBox, ageBox;

	private final int TIMER_DELAY = 2000;

	private boolean timerStarted = false;

	private Clip clip;
	// Create the options that will be offered in the JComboBoxes
	private String[] species = { "Select Species", "Animal", "Human" };
	private String[] gender = { "Selecet Gender", "Male", "Female" };
	private String[] age = { "Select Age", "Young", "Middle", "Old" };
	private Database database;

	// Create objects to refer to final objects in the constructor paramaters
	private Object accessObject;
	private Object drawPanelObject;
	private Object labelPanelObject;

	/**
	 * Construct the ControlPanel
	 * 
	 * @param drawPanel access
	 * 
	 * @param drawPanel photoPanel
	 * 
	 * @param drawPanel labelPanel
	 */

	public ControlPanel(final AccessDatabase access,
			final PhotoPanel drawPanel, final LabelPanel labelPanel) {
		// initialize all the fields as necessary

		accessObject = access;
		drawPanelObject = drawPanel;
		labelPanelObject = labelPanel;

		database = new Database();
		play = new JButton("Play");
		pause = new JButton("Pause");
		stop = new JButton("Stop");

		play.setFont(new Font("Verdana", Font.BOLD, 15));
		stop.setFont(new Font("Verdana", Font.BOLD, 15));
		pause.setFont(new Font("Verdana", Font.BOLD, 15));

		play.setForeground(Color.BLUE);
		pause.setForeground(Color.BLUE);
		stop.setForeground(Color.BLUE);

		// next = new JButton(new
		// ImageIcon(getClass().getResource("next.jpg")));
		next = new JButton(new ImageIcon("src/images/next.jpg"));

		// previous = new JButton(
		// new ImageIcon(getClass().getResource("back.jpg")));
		previous = new JButton(new ImageIcon("src/images/back.jpg"));

		speciesBox = new JComboBox<Object>(species);
		genderBox = new JComboBox<Object>(gender);
		ageBox = new JComboBox<Object>(age);

		speciesBox.setBackground(Color.ORANGE);
		genderBox.setBackground(Color.ORANGE);
		ageBox.setBackground(Color.ORANGE);

		this.setLayout(new GridLayout(0, 3));
		this.add(speciesBox);
		this.add(genderBox);
		this.add(ageBox);
		this.add(play);
		this.add(pause);
		this.add(stop);

		this.add(previous);
		this.add(next);
		stop.setEnabled(false);
		pause.setEnabled(false);

		genderBox.setVisible(false);
		ageBox.setVisible(false);
		/*
		 * ADD all the action listeners accordingly
		 */
		previous.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				access.previousImage();
				drawPanel.repaint();
				labelPanel.updateLabel(access.getNumImages(),
						access.getImageIndex());
			} // end method actionPerformed
		} // end anonymous inner class
		); // end call to addActionListener
		next.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				access.nextImage();
				drawPanel.repaint();
				labelPanel.updateLabel(access.getNumImages(),
						access.getImageIndex());
			} // end method actionPerformed
		} // end anonymous inner class
		); // end call to addActionListener

		speciesBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					// Get all the Human images
					if (speciesBox.getSelectedIndex() == 2) {

						setImages("speciesId=2");
						genderBox.setVisible(true);
						ageBox.setVisible(true);

					}
					// Get all the animal images
					else {
						setImages("speciesId=1 and genderID=0 and ageId=0");
						genderBox.setVisible(false);
						ageBox.setVisible(false);
					}
				}

			}

		}

		);

		ageBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					// Get Male Young
					if (genderBox.getSelectedIndex() == 1
							&& ageBox.getSelectedIndex() == 1) {

						setImages("speciesId=2 and genderID=1 and ageId=1");

					}

					// Get male middle
					else if (genderBox.getSelectedIndex() == 1
							&& ageBox.getSelectedIndex() == 2) {

						setImages("speciesId=2 and genderID=1 and ageId=2");

					}
					// get male old
					else if (genderBox.getSelectedIndex() == 1
							&& ageBox.getSelectedIndex() == 3) {

						setImages("speciesId=2 and genderID=1 and ageId=3");

					} // get female young
					else if (genderBox.getSelectedIndex() == 2
							&& ageBox.getSelectedIndex() == 1) {

						setImages("speciesId=2 and genderID=2 and ageId=1");

					}
					// get female middle
					else if (genderBox.getSelectedIndex() == 2
							&& ageBox.getSelectedIndex() == 2) {

						setImages("speciesId=2 and genderID=2 and ageId=2");

					}
					// get female old
					else if (genderBox.getSelectedIndex() == 2
							&& ageBox.getSelectedIndex() == 3) {

						setImages("speciesId=2 and genderID=2 and ageId=3");

					}
					// If ageBox is unselected refresh the images accordingly
					else if (genderBox.getSelectedIndex() == 2
							&& ageBox.getSelectedIndex() == 0) {

						setImages("speciesId=2 and genderID=2");

					}
					// If ageBox is unselected refresh the images accordingly
					else if (genderBox.getSelectedIndex() == 1
							&& ageBox.getSelectedIndex() == 0) {

						setImages("speciesId=2 and genderID=1");

					}
					// If ageBox is unselected refresh the images accordingly
					else if (speciesBox.getSelectedIndex() == 2
							&& ageBox.getSelectedIndex() == 0) {

						setImages("speciesId=2");

					}
					// Get all the young
					else if (ageBox.getSelectedIndex() == 1) {

						setImages("speciesId=2 and ageId=1");
					}
					// Get all the middle
					else if (ageBox.getSelectedIndex() == 2) {

						setImages("speciesId=2 and ageID=2");
					}
					// Get all the old
					else if (ageBox.getSelectedIndex() == 3) {

						setImages("speciesId=2 and ageID=3");
					}

				}
			}
		});

		genderBox.addItemListener(

		new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					// Get male youngs
					if (genderBox.getSelectedIndex() == 1
							&& ageBox.getSelectedIndex() == 1) {

						setImages("speciesId=2 and genderID=1 and ageId=1");

					}
					// Get male middle
					else if (genderBox.getSelectedIndex() == 1
							&& ageBox.getSelectedIndex() == 2) {

						setImages("speciesId=2 and genderID=1 and ageId=2");
					}
					// get male old
					else if (genderBox.getSelectedIndex() == 1
							&& ageBox.getSelectedIndex() == 3) {

						setImages("speciesId=2 and genderID=1 and ageId=3");

					}
					// get female young
					else if (genderBox.getSelectedIndex() == 2
							&& ageBox.getSelectedIndex() == 1) {

						setImages("speciesId=2 and genderID=2 and ageId=1");

					}
					// get female middle
					else if (genderBox.getSelectedIndex() == 2
							&& ageBox.getSelectedIndex() == 2) {

						setImages("speciesId=2 and genderID=2 and ageId=2");
					}
					// get female old
					else if (genderBox.getSelectedIndex() == 2
							&& ageBox.getSelectedIndex() == 3) {

						setImages("speciesId=2 and genderID=2 and ageId=3");

					}
					// Get all males
					else if (genderBox.getSelectedIndex() == 1) {

						setImages("speciesId=2 and genderID=1");
					}
					// Get all females
					else if (genderBox.getSelectedIndex() == 2) {

						setImages("speciesId=2 and genderID=2");

					}
					// If genderBox is unselected refresh the images
					// accordingly. Also for the rest of the "else if"s
					else if (genderBox.getSelectedIndex() == 0
							&& ageBox.getSelectedIndex() == 1) {

						setImages("speciesId=2 and ageID=1");

					} else if (genderBox.getSelectedIndex() == 0
							&& ageBox.getSelectedIndex() == 2) {

						setImages("speciesId=2 and ageID=2");

					} else if (genderBox.getSelectedIndex() == 0
							&& ageBox.getSelectedIndex() == 3) {

						setImages("speciesId=2 and ageID=3");

					} else if (speciesBox.getSelectedIndex() == 2
							&& ageBox.getSelectedIndex() == 0) {

						setImages("speciesId=2");

					}

				}

			}

		}

		);

		playBackroundMusic();
		clip.stop();

		final Timer timer = new Timer(TIMER_DELAY, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				access.nextImage();
				drawPanel.repaint();
				timerStarted = true;
				labelPanel.updateLabel(access.getNumImages(),
						access.getImageIndex());
			}
		});

		play.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				timer.start();
				drawPanel.repaint();
				play.setEnabled(false);
				stop.setEnabled(true);
				pause.setEnabled(true);
				previous.setEnabled(false);
				next.setEnabled(false);
				playBackroundMusic();
				clip.start();

			} // end method actionPerformed
		} // end anonymous inner class
		); // end call to addActionListener

		pause.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (timer.isRunning()) {
					timer.stop();
					drawPanel.repaint();
					pause.setText("Continue");
					clip.stop();
				} else if (timerStarted) {
					pause.setText("Pause");
					timer.start();
					clip.start();

				}

			} // end method actionPerformed
		} // end anonymous inner class
		); // end call to addActionListener

		stop.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				timer.stop();

				play.setEnabled(true);
				pause.setEnabled(false);
				stop.setEnabled(false);
				previous.setEnabled(true);
				next.setEnabled(true);
				pause.setText("Pause");
				clip.stop();
				clip.close();

			} // end method actionPerformed
		} // end anonymous inner class
		); // end call to addActionListener

	}

	/**
	 * setImages changes the images if the criteria is changed
	 * 
	 * @param criteria
	 *            This is the criteria that is sent from the action listeners
	 */
	private void setImages(String criteria) {
		try {
			database.setQuery("select url from picture where " + criteria);

			((AccessDatabase) accessObject).setImages(database.getImages());
			((Component) drawPanelObject).repaint();
			((LabelPanel) labelPanelObject).updateLabel(
					((AccessDatabase) accessObject).getNumImages(),
					((AccessDatabase) accessObject).getImageIndex());
		} catch (IllegalStateException | SQLException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Plays the background music when in slideshow mode.
	 */
	public void playBackroundMusic() {
		int rand = (int) (Math.random() * 3 + 1);
		URL url;
		try {
			// AudioInputStream audio =
			// AudioSystem.getAudioInputStream(getClass()
			// .getResource(rand + ".wav"));
			
			url = this.getClass().getClassLoader()
					.getResource("./music/" + rand + ".wav");

			AudioInputStream audio = AudioSystem.getAudioInputStream(url);

			clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}

		catch (UnsupportedAudioFileException uae) {
			System.out.println(uae);
		} catch (IOException ioe) {
			System.out.println(ioe);
		} catch (LineUnavailableException lua) {
			System.out.println(lua);
		}
	}
}
