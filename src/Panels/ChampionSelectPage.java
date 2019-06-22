package Panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Core.Starter;
import Engines.PageControl;
import Global.ChampionManager;
import Global.Constants;
import Global.Functions;
import Global.ImageManager;
import Global.SoundManager;
import Global.Variables;
import Global.Constants.GameMode;
import Network.InnerData.ChampionSelectingRoomInfo;
import Network.NetworkCore.GameClient;
import Network.NetworkCore.GameServer;
import Objects.Coordinate;
import Utility.EnginesControl;
import Utility.ImageScroller;
import Utility.TriggeredButton;
import Utility.onButtonListener;

public class ChampionSelectPage extends JPanel implements PageControl{
	//Necessary
	public static Dimension PanelSize;
	public static boolean isActivated;
	
	//Utility
	private static Functions ff = new Functions();
	
	private static TriggeredButton CloseBtn, TerminateBtn;
	
	//Server
	private static GameServer gameServer;
	private static GameClient gameClient;
	private static boolean isGameMaster;
	
	ChampionManager championPack;
	
	GameMode mode;
	ChampionSelectingRoomInfo RoomInfo;
	ImageScroller imageScroller;
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.drawImage(ImageManager.ChampionSelectTemplate, null, 0, 0);
		//293·Î Á¤·Ä
		if(RoomInfo != null) {
			int team1num = RoomInfo.getUserList1().size();
			int team2num = RoomInfo.getUserList2().size();
			for(int i=0;i<team1num;i++)
				g.drawImage(ImageManager.DeactivatedUserSlot_Team1, null, 5, 293 - (93*team1num/2) + 93*i);
			for(int i=0;i<team2num;i++)
				g.drawImage(ImageManager.DeactivatedUserSlot_Team2, null, 998, 293 - (93*team1num/2) + 93*i);
		}
	}
	
	public void update() {
		RoomInfo = gameClient.getSelectRoomInfo();
	}
	
	public void setThis() {
		championPack = new ChampionManager();
		ArrayList<BufferedImage> championIcons = new ArrayList<>();
		for(int i=0;i<championPack.pack.size();i++)
			championIcons.add(championPack.pack.get(i).getChampionIcon());
		
		imageScroller = new ImageScroller(new Rectangle(350, 120, 586, 461), new Dimension(65,65), 6, championIcons, 1, 29, 10);
		this.add(imageScroller);
	}
	
	public ChampionSelectPage(GameMode mode, boolean isGM, GameServer server, GameClient client) {
		setPanelSize(Constants.ClientPanelDimension);
		setVisible(true);
		this.setLayout(null);
		
		this.isGameMaster = isGM;
		this.gameServer = server;
		this.gameClient = client;
		this.mode = mode;
		
		CloseBtn = new TriggeredButton(
				null,
				ImageManager.FocusedTerminateButtonImage,
				null,
				null,
				new Coordinate(1252,8),
				null,
				new Rectangle(1252,7,14,14),
				0,
				0
				);
		CloseBtn.addOnButtonListener(new onButtonListener() {
			@Override
			public void onClick() {
				if(isGM)
					gameServer.endServer();
				ff.playSoundClip(SoundManager.lightClickSoundFilePath, SoundManager.LIGHT_CLICK_SOUND_VOLUME);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}

			@Override
			public void onEnter() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onExit() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPress() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onRelease() {
				// TODO Auto-generated method stub
				
			}
		});
		this.add(CloseBtn);
		
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				//Variables.mousePos.setPos(e.getX(), e.getY());
				if(SwingUtilities.isLeftMouseButton(e)) {
					if(Variables.mousePos.getY()>80)return;
					Dimension frameSize = Starter.frame.getSize();
				    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				    Point loc = Starter.frame.getLocation();
				    Point diff = new Point(e.getX() - Variables.mousePos.getX(), e.getY() - Variables.mousePos.getY());
					Starter.frame.setLocation((int)(loc.getX()+diff.getX()), (int)(loc.getY()+diff.getY()));
				}
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				Variables.mousePos.setPos(e.getX(), e.getY());
			}
			
		});
	}
	
	public void setPanelSize(Dimension ps) {
		this.setSize(ps);
		this.PanelSize = ps;
	}
}
