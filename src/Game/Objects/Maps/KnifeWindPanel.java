package Game.Objects.Maps;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Game.Objects.Camera;
import Game.Objects.Managers.MapManager;

public class KnifeWindPanel extends MapManager{
	private Dimension mapSize = new Dimension(5400, 5400);
	public void draw(Camera camera, Graphics2D g) {
		this.drawComponents(camera, g);
	}
}
