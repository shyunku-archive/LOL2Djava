package Game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Core.Starter;
import Game.Objects.Camera;
import Global.Constants;
import Global.ImageManager;
import Global.Constants.GameMode;
import Network.NetworkCore.GameClient;
import Network.NetworkCore.GameServer;

public class GamePanel extends JPanel{
	//Necessary
	public static Dimension PanelSize;
	public static boolean isActivated;
	
	//Server
	private GameServer gameServer;
	private GameClient gameClient;
	private boolean isGameMaster;
	
	private Camera camera = new Camera();
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.drawImage(ImageManager.AmumuIconImage, null, 0, 0);
		
		//¸Ê
	}
		
	public GamePanel(GameMode mode, boolean isGameMaster, GameServer gameServer, GameClient gameClient) {
		// TODO Auto-generated constructor stub
		Starter.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setPanelSize(Constants.ClientPanelDimension);
		setVisible(true);
		this.setLayout(null);
	}

	public void setThis() {
		// TODO Auto-generated method stub
		
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void setPanelSize(Dimension ps) {
		this.setSize(ps);
		this.PanelSize = ps;
	}
}
