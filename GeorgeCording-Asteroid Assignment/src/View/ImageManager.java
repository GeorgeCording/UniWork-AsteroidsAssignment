package View;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/*
 * The ImageManager class is the class that handles manipulating .png images.
 * Images are stored in a Map<String, Image> where string is the name to refer to the image.
 */
public class ImageManager {

	// this may need modifying
	final static String path = "images/";
	final static String ext = ".png";

	// This Map stores the Images.
	static Map<String, Image> images = new HashMap<String, Image>();

	// Simple function to get a selected image.
	public static Image getImage(String s) {
		return images.get(s);
	}

	// Function used to import an image from "path"+"fname"+"ext". this is stored in the Map "images".
	public static Image loadImage(String fname) throws IOException {
		BufferedImage img = null;
		img = ImageIO.read(new File(path + fname + ext));
		images.put(fname, img);
		return img; 
	}

	// Function similar to loadImage(string) but this one allows for saving to the map of a different name than the one
	// used to import the image.
	public static Image loadImage(String imName, String fname) throws IOException {
		BufferedImage img = null;
		img = ImageIO.read(new File(path + fname + ext));
		images.put(imName, img);
		return img; 
	}

	// Function used to load in a series of images.
	public static void loadImages(String[] fNames) throws IOException {
		for (String s : fNames)
			loadImage(s);
	}

	// Function used to load in a series of images.
	public static void loadImages(Iterable<String> fNames) throws IOException {
		for (String s : fNames)
			loadImage(s);
	}
}
