package Engines;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import Global.Functions;
import Utility.Coordinate;

public class FigureDrawEngine {
	Functions ff = new Functions();
	public void drawRect(Graphics2D g, double x, double y, double w, double h) {
		Rectangle2D rect = new Rectangle2D.Double(x,y,w,h);
		g.draw(rect);
	}
	
	public void fillRect(Graphics2D g, double x, double y, double w, double h) {
		Rectangle2D rect = new Rectangle2D.Double(x,y,w,h);
		g.fill(rect);
	}
	
	public void fillScreen(Graphics2D g, Color c, Dimension d) {
		Color original = g.getColor();
		g.setColor(c);
		fillRect(g, 0, 0, d.getWidth(), d.getHeight());
		g.setColor(original);
	}
	
	// Same Color Covering is not Recommended.
	public void advancedDrawString(Graphics2D g, String str, Coordinate p, Color top, Color bot, int floats) {
		Color original = g.getColor();
		g.setColor(bot);
		g.drawString(str, p.getX(), p.getY());
		g.setColor(top);
		g.drawString(str, p.getX()-floats, p.getY()-floats);
		g.setColor(original);
	}
	
	public void advancedDrawRightAlignedString(Graphics2D g, String str, Coordinate p, Dimension wind, Color top, Color bot, int floats) {
		Color original = g.getColor();
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		g.setColor(bot);
		g.drawString(str, (int)(wind.getWidth() -p.getX()) - metrics.stringWidth(str), (int)(p.getY()));
		g.setColor(top);
		g.drawString(str, (int)(wind.getWidth() -p.getX()+floats) - metrics.stringWidth(str), (int)(p.getY()-floats));
		g.setColor(original);
	}
	
	public void drawCenteredString(Graphics2D g, String text, Rectangle rect) {
		Font font = g.getFont();
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
	public void drawFlexibleCenteredString(Graphics2D g, String text, Coordinate coord) {
		Font font = g.getFont();
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    int w = metrics.stringWidth(text);
	    int h = metrics.getHeight();
	    // Determine the X coordinate for the text
	    int x = coord.getX()-w/2 + (w - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = coord.getY() + ((h - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
	public void drawAdvancedFlexibleCenteredString(Graphics2D g, String text, Coordinate coord, Color top, Color bot, int floats) {
		Font font = g.getFont();
		Color original = g.getColor();
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    int w = metrics.stringWidth(text);
	    int h = metrics.getHeight();
	    // Determine the X coordinate for the text
	    int x = coord.getX()-w/2 + (w - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = coord.getY() + ((h - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Draw the String
	    g.setColor(bot);
	    g.drawString(text, x, y);
	    g.setColor(top);
	    g.drawString(text, x-floats, y-floats);
	    g.setColor(original);
	}
	
	public void drawAdvancedFlexibleRightAlignedString(Graphics2D g, String text, Coordinate coord, Dimension wind, Color top, Color bot, int floats) {
		Font font = g.getFont();
		Color original = g.getColor();
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    int w = metrics.stringWidth(text);
	    int h = metrics.getHeight();
	    // Determine the X coordinate for the text
	    int x = coord.getX()-w/2 + (w - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = coord.getY() + ((h - metrics.getHeight()) / 2) + metrics.getAscent();
	    x = (int)wind.getWidth() - x;
	    y = (int)wind.getHeight() - y;
	    // Draw the String
	    g.setColor(bot);
	    g.drawString(text, x, y);
	    g.setColor(top);
	    g.drawString(text, x-floats, y-floats);
	    g.setColor(original);
	}
	
	public void drawSemiTranslucentImage(Graphics2D g, BufferedImage bi, int x, int y, float opacity) {
		if(opacity<0)opacity = 0f;
		if(opacity>1)opacity = 1f;
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g.drawImage(bi, null, x, y);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
	
	public final static int RECOMMENDED_FLOATING_SHADOW = 2;
}
