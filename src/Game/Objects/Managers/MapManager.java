package Game.Objects.Managers;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Game.Objects.Camera;
import Game.Objects.Inhibitor;
import Game.Objects.Turret;

public abstract class MapManager {
	private ArrayList<Turret> turrets;
	private ArrayList<Inhibitor> inhibitors;
	
	public void drawComponents(Camera camera, Graphics2D g) {
		for(Turret turret : turrets)
			turret.draw(camera, g);
		for(Inhibitor in : inhibitors)
			in.draw(camera, g);
	}
}
