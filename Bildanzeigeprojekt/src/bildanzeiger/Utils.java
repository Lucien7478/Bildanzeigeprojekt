package bildanzeiger;

import java.io.File;

import javax.swing.ImageIcon;

public class Utils {
	public final static String jpeg = "jpeg";
	public final static String jpg = "jpg";
	public final static String gif = "gif";
	public final static String tiff = "tiff";
	public final static String tif = "tif";
	public final static String png = "png";

	/*
	 * Gib die Endung der Bilddatei zur�ck
	 */
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	/*
	 * Gibt ein ImageIcon zur�ck, oder null wenn der Pfad ung�ltig war.
	 */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Utils.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Konnte den Pfad nicht finden: " + path);
			return null;
		}
	}
}
