package bildanzeiger;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;

public class ImageFileIcon extends FileView {
	ImageIcon jpgIcon = Utils.createImageIcon("images/jpgIcon.gif");
	ImageIcon gifIcon = Utils.createImageIcon("images/gifIcon.gif");
	ImageIcon tiffIcon = Utils.createImageIcon("images/tiffIcon.gif");
	ImageIcon pngIcon = Utils.createImageIcon("images/pngIcon.png");

	@Override
	public String getName(File f) {
		return null;
	}

	@Override
	public String getDescription(File f) {
		return null;
	}

	@Override
	public Boolean isTraversable(File f) {
		return null;
	}

	/*
	 * Gibt den Dateitypen zurück
	 */
	@Override
	public String getTypeDescription(File f) {
		String extension = Utils.getExtension(f);
		String type = null;

		if (extension != null) {
			if (extension.equals(Utils.jpeg) || extension.equals(Utils.jpg)) {
				type = "JPEG Image";
			} else if (extension.equals(Utils.gif)) {
				type = "GIF Image";
			} else if (extension.equals(Utils.tiff) || extension.equals(Utils.tif)) {
				type = "TIFF Image";
			} else if (extension.equals(Utils.png)) {
				type = "PNG Image";
			}
		}
		return type;
	}

	/*
	 * Gibt das bestimmte Icon je nach Dateitypen von f zurück
	 */
	@Override
	public Icon getIcon(File f) {
		String extension = Utils.getExtension(f);
		Icon icon = null;

		if (extension != null) {
			if (extension.equals(Utils.jpeg) || extension.equals(Utils.jpg)) {
				icon = jpgIcon;
			} else if (extension.equals(Utils.gif)) {
				icon = gifIcon;
			} else if (extension.equals(Utils.tiff) || extension.equals(Utils.tif)) {
				icon = tiffIcon;
			} else if (extension.equals(Utils.png)) {
				icon = pngIcon;
			}
		}
		return icon;
	}
}
