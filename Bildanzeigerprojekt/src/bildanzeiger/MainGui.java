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

		JButton sendButton = new JButton("Bild öffnen...");
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

			// Füge eigenen Filter hinzu und deaktiviere den Standardfilter

			fc.addChoosableFileFilter(new ImageFilter());
			fc.setAcceptAllFileFilterUsed(false);

			// Verwende eigene icons für Dateitypen
			fc.setFileView(new ImageFileIcon());

			// Erstelle das Vorschau Bild
			fc.setAccessory(new ImagePreview(fc));
		}

		// Zeige den FileChooser
		int returnVal = fc.showDialog(MainGui.this, "Öffnen");

		// Mit dem Ergebnis weiterarbeiten
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			ImageIcon tmpIcon = new ImageIcon(file.getAbsolutePath());
			// Image enthält das ausgewählte Bild
			image = tmpIcon.getImage();
		}
		repaint();

		fc.setSelectedFile(null);
	}

	private static void createAndShowGUI() {
		// Erstelle das Fenster:
		JFrame frame = new JFrame("Bildanzeiger");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// füge MainGui dem aüseren Fenster hinzu
		frame.getContentPane().add(new MainGui());

		// Fenster anzeigen.
		frame.pack();
		frame.setVisible(true);
	}

	// paintComponent wird ausgefüht, wenn die Fenstergröße geändert wird
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) { // Bild muss im Speicher liegen, sonst wird nichts gezeichnet
			ImageIcon tempIcon = new ImageIcon(image);
			// Wenn die Länge des Bildes größer ist als die Länge des Fensters,
			if (tempIcon.getIconWidth() > getWidth()) {
				// dann wird die Bildlänge auf die Länge des Fenstes runterskaliert
				tempIcon = new ImageIcon(image.getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));
				// Wenn die Höhe des Bildes größer ist als die Höhe des Fensters,
				if (tempIcon.getIconHeight() > getHeight()) {
					// dann wird die Bildhöhe auf die Höhe des Fenstes runterskaliert
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
				// Verändere die Schriftart
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}
}
