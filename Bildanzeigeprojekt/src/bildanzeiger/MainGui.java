package bildanzeiger;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainGui extends JPanel implements ActionListener {

	private static final long serialVersionUID = -922637141821524644L;
	private JFileChooser fc;
	private Image image;

	public MainGui() {
		super(new BorderLayout());

		JButton sendButton = new JButton("Bild �ffnen...");
		sendButton.setBorder(null);
		sendButton.addActionListener(this);

		add(sendButton, BorderLayout.PAGE_START);

		setPreferredSize(new Dimension(900, 900));
		setLocation(500, 500);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Erstelle den file chooser.
		if (fc == null) {
			fc = new JFileChooser();

			// F�ge eigenen Filter hinzu und deaktiviere den Standardfilter

			fc.addChoosableFileFilter(new ImageFilter());
			fc.setAcceptAllFileFilterUsed(false);

			// Verwende eigene icons f�r Dateitypen
			fc.setFileView(new ImageFileIcon());

			// Erstelle das Vorschau Bild
			fc.setAccessory(new ImagePreview(fc));
		}

		// Zeige den FileChooser
		int returnVal = fc.showDialog(MainGui.this, "�ffnen");

		// Mit dem Ergebnis weiterarbeiten
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			ImageIcon tmpIcon = new ImageIcon(file.getAbsolutePath());
			// Image enth�lt das ausgew�hlte Bild
			image = tmpIcon.getImage();
		}
		repaint();

		fc.setSelectedFile(null);
	}

	private static void createAndShowGUI() {
		// Erstelle das Fenster:
		JFrame frame = new JFrame("Bildanzeiger");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// f�ge MainGui dem a�seren Fenster hinzu
		frame.getContentPane().add(new MainGui());

		// Fenster anzeigen.
		frame.pack();
		frame.setVisible(true);
	}

	// paintComponent wird ausgef�ht, wenn die Fenstergr��e ge�ndert wird
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) { // Bild muss im Speicher liegen, sonst wird nichts gezeichnet
			ImageIcon tempIcon = new ImageIcon(image);
			// Wenn die L�nge des Bildes gr��er ist als die L�nge des Fensters,
			if (tempIcon.getIconWidth() > getWidth()) {
				// dann wird die Bildl�nge auf die L�nge des Fenstes runterskaliert
				tempIcon = new ImageIcon(image.getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));
				// Wenn die H�he des Bildes gr��er ist als die H�he des Fensters,
				if (tempIcon.getIconHeight() > getHeight()) {
					// dann wird die Bildh�he auf die H�he des Fenstes runterskaliert
					tempIcon = new ImageIcon(
							tempIcon.getImage().getScaledInstance(-1, getHeight(), Image.SCALE_DEFAULT));
				}
			}
			Image resultImage = tempIcon.getImage();
			// Das Bild wird gezeichnet
			g.drawImage(resultImage, 0, 0, this);
		}

	}

	public static void main(String[] args) {
		// Erstelle und starte einen Thread, der die GUI erstellt und anzeigt
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Ver�ndere die Schriftart
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}
}
