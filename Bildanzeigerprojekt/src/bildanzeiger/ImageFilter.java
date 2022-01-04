package bildanzeiger;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImageFilter extends FileFilter {

	// Akzeptiere alle Verzeichnisse und gif, jpg, tiff, oder png Datein.
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = Utils.getExtension(f);
		if (extension != null) {
			if (extension.equals(Utils.tiff) || extension.equals(Utils.tif) || extension.equals(Utils.gif)
					|| extension.equals(Utils.jpeg) || extension.equals(Utils.jpg) || extension.equals(Utils.png)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	// Die Beschreibung des Filters
	@Override
	public String getDescription() {
		return "Bilddateien";

	}
}
