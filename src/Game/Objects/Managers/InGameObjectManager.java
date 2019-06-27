package Game.Objects.Managers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Game.Objects.Camera;
import Objects.CoordinateDouble;

public abstract class InGameObjectManager {
	private final BufferedImage objectImage = null;
	private CoordinateDouble pos;
	public InGameObjectManager(CoordinateDouble pos) {
		this.pos = pos;
	}
	public abstract void draw(Camera camera, Graphics2D g);
}
