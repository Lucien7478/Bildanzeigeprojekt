package bildanzeiger;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

public class ImagePreview extends JComponent implements PropertyChangeListener {

	private static final long serialVersionUID = -5219231894613303315L;
	ImageIcon thumbnail = null;
	File file = null;

	public ImagePreview(JFileChooser fc) {
		// Setze die Größe des Vorschaubilds
		setPreferredSize(new Dimension(100, 50));
		fc.addPropertyChangeListener(this);
	}

	public void loadImage() {
		if (file == null) {
			thumbnail = null;
			return;
		}

		ImageIcon tmpIcon = new ImageIcon(file.getPath());
		if (tmpIcon != null) {
			// Wenn die Läge des Bildes größer als 90 ist
			if (tmpIcon.getIconWidth() > 90) {
				// Dann wird die Bildlänge auf 90 herrunter skaliert
				thumbnail = new ImageIcon(tmpIcon.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT));
			} else {
				// ansonsten nehmen wir das Original Bild
				thumbnail = tmpIcon;
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		boolean update = false;
		String prop = e.getPropertyName();

		// Wenn ein Verzeichnis ausgewählt wird, dann wird keine Vorschau angezeigt
		if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
			file = null;
			update = true;

			// Wenn eine Bilddatei ausgewält wird, dann wird eine Vorschau davon angezeigt
		} else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
			file = (File) e.getNewValue();
			update = true;
		}

		// Wenn die Auswahlt verändert wurde dann wird auch das Bild verändert
		if (update) {
			thumbnail = null;
			if (isShowing()) {
				loadImage();
				repaint();
			}
		}
	}

	/*
	 * Die Vorschau wird an der entsprechenden Stelle gezeichnet
	 */
	@Override
	protected void paintComponent(Graphics g) {
		if (thumbnail == null) {
			loadImage();
		}
		if (thumbnail != null) {
			int x = getWidth() / 2 - thumbnail.getIconWidth() / 2;
			int y = getHeight() / 2 - thumbnail.getIconHeight() / 2;

			if (y < 0) {
				y = 0;
			}

			if (x < 5) {
				x = 5;
			}
			thumbnail.paintIcon(this, g, x, y);
		}
	}
}
