package problem.gui;

import java.awt.*;

import javax.swing.*;

class ImageProxy implements Icon {

	ImageIcon imageIcon;
	String pathToImage;
	Thread retrievalThread;
	boolean retrieving = false;

	public ImageProxy(String path) {
		pathToImage = path;
	}

	public int getIconWidth() {
		if (imageIcon != null) {
			return imageIcon.getIconWidth();
		} else {
			return 800;
		}
	}

	public int getIconHeight() {
		if (imageIcon != null) {
			return imageIcon.getIconHeight();
		} else {
			return 600;
		}
	}

	public void paintIcon(final Component c, Graphics g, int x, int y) {
		if (imageIcon != null) {
			imageIcon.paintIcon(c, g, x, y);
		} else {
			g.drawString("Loading image, please wait...", x + 300, y + 190);
			if (!retrieving) {
				retrieving = true;

				retrievalThread = new Thread(new Runnable() {
					public void run() {
						try {
							imageIcon = new ImageIcon(pathToImage, "UML Icon");
							// NOTE: Do both revalidate() and repaint() on the
							// parent component
							c.revalidate();
							c.repaint();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				retrievalThread.start();
			}
		}
	}
}
