package Panels;

import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Engines.PageControl;
import Global.Constants;
import Network.NetworkCore.GameClient;
import Network.NetworkCore.GameServer;

public class ChampionSelectPage extends JPanel implements PageControl{
	//Necessary
	public static Dimension PanelSize;
	public static boolean isActivated;
	
	//Server
	private static GameServer gameServer;
	private static GameClient gameClient;
	private static boolean isGameMaster;
	
	public void paintComponent(Graphics2D g) {
		
	}
	
	public void update() {
		
	}
	
	public void setThis() {
		
	}
	
	public ChampionSelectPage(boolean isGM, GameServer server, GameClient client) {
		setPanelSize(Constants.ClientPanelDimension);
		setVisible(true);
		this.setLayout(null);
		
		this.isGameMaster = isGM;
		this.gameServer = server;
		this.gameClient = client;
	}
	
	public void setPanelSize(Dimension ps) {
		this.setSize(ps);
		this.PanelSize = ps;
	}
}
