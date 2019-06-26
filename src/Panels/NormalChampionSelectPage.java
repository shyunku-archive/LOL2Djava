package Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.font.TextAttribute;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;

import Core.Starter;
import Engines.CustomTextPane;
import Engines.EpicEngine;
import Engines.PageControl;
import Global.ChampionManager;
import Global.Constants;
import Global.Functions;
import Global.ImageManager;
import Global.SoundManager;
import Global.Variables;
import Global.Constants.GameMode;
import Network.InnerData.NormalChampionSelectingRoomInfo;
import Network.NetworkCore.GameClient;
import Network.NetworkCore.GameServer;
import Network.NetworkCore.NetworkTag;
import Network.Objects.Chat;
import Network.Objects.User;
import Objects.Coordinate;
import Utility.ChampionSelectionImageScroller;
import Utility.EnginesControl;
import Utility.TriggeredButton;
import Utility.TriggeredTextArea;
import Utility.onButtonListener;
import Utility.TriggeredTextArea.EnterListener;

public class NormalChampionSelectPage extends JPanel implements PageControl{
	//Necessary
	public static Dimension PanelSize;
	public static boolean isActivated;
	
	//Utility
	private static Functions ff = new Functions();
	private static EnginesControl ect = new EnginesControl();
	
	private static TriggeredButton CloseBtn, TerminateBtn, PickBtn;
	
	//Server
	private GameServer gameServer;
	private GameClient gameClient;
	private boolean isGameMaster;
	
	ChampionManager championPack;
	
	GameMode mode;
	NormalChampionSelectingRoomInfo RoomInfo =  new NormalChampionSelectingRoomInfo();
	ChampionSelectionImageScroller imageScroller;
	
	private static TriggeredTextArea Chatr = new TriggeredTextArea(new Rectangle(21, 669, 300, 30));
	private static JScrollPane scrollPane;
	private static CustomTextPane chatArea = new CustomTextPane(true);
	private int chatSize = 0;
	private int myTeamIndex = 0;
	
	private int customPhase = 0;
	
	private Clip c = null;
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.drawImage(ImageManager.ChampionSelectTemplate, null, 0, 0);
		//293·Î Á¤·Ä
		if(RoomInfo != null) {			
			final int SAH = 50;
			final int SLW = 95;
			final int SRW = 1193;
			
			long Remained = RoomInfo.getCurReaminWaitTime();
			if(Remained <0)
				Remained = 0;
			g.setColor(Color.WHITE);
			int code;
			
			ArrayList<User> OurTeam, EnemyTeam;
			if(myTeamIndex == 1) {
				OurTeam = RoomInfo.getUserList1();
				EnemyTeam = RoomInfo.getUserList2();
			}else {
				OurTeam = RoomInfo.getUserList2();
				EnemyTeam = RoomInfo.getUserList1();
			}
			int adjust = 81;
			for(int i=0;i<OurTeam.size();i++) {
				Coordinate coord = new Coordinate(18, 293 - (adjust*OurTeam.size()/2) + adjust*i);
				g.setFont(ff.getClassicFont(15, true));
				if(OurTeam.get(i).isSelecting()) {
					if(OurTeam.get(i).getUserName().equals(Variables.Username)) {
						g.drawImage(ImageManager.MyPickingUserFrame, null, coord.getX()-13, coord.getY());
						g.drawString(OurTeam.get(i).getUserName(), SLW+36, 293 - (adjust*OurTeam.size()/2) + adjust*i + SAH + 10);
						
						g.setFont(ff.getClassicFont(30, true));
						g.drawString(""+Remained/1000, SLW+165, 293 - (adjust*OurTeam.size()/2) + adjust*i + SAH + 7);
					}else {
						g.drawImage(ImageManager.OurTeamPickingUserFrame, null, coord.getX(), coord.getY());
						g.drawString(OurTeam.get(i).getUserName(), SLW+36, 293 - (adjust*OurTeam.size()/2) + adjust*i + SAH + 10);
					}
				}else if(OurTeam.get(i).isPicked()) {
					if(OurTeam.get(i).getUserName().equals(Variables.Username)) {
						g.drawImage(ImageManager.MyPickedUserFrame, null, coord.getX()-13, coord.getY());
						g.drawString(OurTeam.get(i).getUserName(), SLW - 10, 293 - (adjust*OurTeam.size()/2) + adjust*i + SAH + 10);
						g.drawString(championPack.getChampionByCode(OurTeam.get(i).getSelectedChampionCode()).getChampionName(), 
								SLW - 10, 293 - (adjust*OurTeam.size()/2) + adjust*i + SAH - 5);
					}else {
						g.drawImage(ImageManager.OurTeamPickingUserFrame, null, coord.getX(), coord.getY());
						g.drawString(OurTeam.get(i).getUserName(), SLW - 10, 293 - (adjust*OurTeam.size()/2) + adjust*i + SAH + 10);
						g.drawString(championPack.getChampionByCode(OurTeam.get(i).getSelectedChampionCode()).getChampionName(), 
								SLW - 10, 293 - (adjust*OurTeam.size()/2) + adjust*i + SAH - 5);
					}
				} else {
					if(OurTeam.get(i).getUserName().equals(Variables.Username)) {
						g.drawImage(ImageManager.MyPickedUserFrame, null, coord.getX()-13, coord.getY());
						g.drawString(OurTeam.get(i).getUserName(), SLW+36, 293 - (adjust*OurTeam.size()/2) + adjust*i + SAH);
					}else {
						g.drawImage(ImageManager.OurTeamPickingUserFrame, null, coord.getX(), coord.getY());
						g.drawString(OurTeam.get(i).getUserName(), SLW+36, 293 - (adjust*OurTeam.size()/2) + adjust*i + SAH + 10);
					}
				}
				
				if((code = OurTeam.get(i).getSelectedChampionCode())!=0) {
					GeneralPath path = new GeneralPath();
					if(OurTeam.get(i).isSelecting()) path.append(new Ellipse2D.Double(63, coord.getY()+17, 56, 56), false);
					else path.append(new Ellipse2D.Double(15, coord.getY()+18, 54, 54), false);
					g.clip(path);
					BufferedImage bi = championPack.getChampionByCode(code).getChampionIcon();
					g.drawImage(bi, null, 43 - bi.getWidth()/2+ (OurTeam.get(i).isSelecting()?48:0), coord.getY()+43 - bi.getHeight()/2);
					g.setClip(0, 0, (int)Constants.ClientPanelDimension.getWidth(), (int)Constants.ClientPanelDimension.getHeight());
				}
				
			}
			for(int i=0;i<EnemyTeam.size();i++) {
				g.setFont(ff.getClassicFont(15, true));
				Coordinate coord = new Coordinate(1280 - ImageManager.EnemyTeamPickingUserFrame.getWidth() - 10, 293 - (adjust*EnemyTeam.size()/2) + adjust*i);
				if(EnemyTeam.get(i).isSelecting()) {
					g.drawImage(ImageManager.EnemyTeamPickingUserFrame, null, coord.getX(), coord.getY());
					ect.fde.drawRightAlignedString(g, EnemyTeam.get(i).getUserName(), new Coordinate(SRW, 293 - (93*EnemyTeam.size()/2) + 93*i + SAH+15));
				}
				else {
					g.drawImage(ImageManager.EnemyTeamPickedUserFrame, null, coord.getX(), coord.getY());
					ect.fde.drawRightAlignedString(g, EnemyTeam.get(i).getUserName(), new Coordinate(SRW, 293 - (93*EnemyTeam.size()/2) + 93*i + SAH+15));
				}
				if((code = EnemyTeam.get(i).getSelectedChampionCode())!=0) {
					GeneralPath path = new GeneralPath();
					path.append(new Ellipse2D.Double(coord.getX() + 150, coord.getY()+16, 58, 58), false);
					g.clip(path);
					BufferedImage bi = championPack.getChampionByCode(code).getChampionIcon();
					g.drawImage(bi, null, 41 - bi.getWidth()/2, 292 - bi.getHeight()/2);
					g.setClip(0, 0, (int)Constants.ClientPanelDimension.getWidth(), (int)Constants.ClientPanelDimension.getHeight());
				}
				
			}
			
			g.setColor(Color.WHITE);
			g.setFont(ff.getClassicFont(40, true));
			ect.fde.drawCenteredString(g, ""+Remained/1000, new Rectangle(610, 31, 60, 79));
		}
		PickBtn.draw(g);
		if(customPhase>0) {
			g.setColor(new Color(0, 7, 14));
			g.fillRect(342, 90, 596, 570);
			g.drawImage(ImageManager.FinalPhaseMainTextImage, null, 475, 13);
		}
	}
	
	public void update() {
		EpicEngine ee = new EpicEngine();
		if(gameClient.getSelectRoomInfo().getOurTeamChat(Variables.Username)== null)return;
		ArrayList<Chat> myTeamChat = gameClient.getSelectRoomInfo().getOurTeamChat(Variables.Username);
		for(int i=chatSize;i< myTeamChat.size();i++){
			Chat c = myTeamChat.get(i);
			String str = "";
			if(chatSize != 0)
				ee.appendToPane(chatArea, "\n", new Color(79,79,79));
			if(c.isSystemic()) {
				ee.appendToPane(chatArea, c.getContent(), new Color(79,79,79));
			}else {
				if(c.getSender().equals(Variables.Username))
					ee.appendToPane(chatArea, c.getSender()+": ", new Color(186, 144, 56));
				else
					ee.appendToPane(chatArea, c.getSender()+": ", new Color(145, 136, 129));
				ee.appendToPane(chatArea, c.getContent(), new Color(214, 208, 192));
			}
			if(shouldScroll()) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
					}
				});
			}
			chatSize++;
		}
		RoomInfo = gameClient.getSelectRoomInfo();
		Variables.ping = gameClient.ping;
		imageScroller.revalidate();
		imageScroller.repaint();
		
		if(RoomInfo.getCurReaminWaitTime() < 0 && this.isGameMaster) {
			if(RoomInfo.getWaitingPhaseIndex()>=1)
				gameClient.sendMessageToServer(NetworkTag.REAL_GAME_START_SIGNAL);
			else
				gameClient.sendMessageToServer(NetworkTag.NEXT_PHASE);
		}
		myTeamIndex = RoomInfo.getOurTeamIndex(Variables.Username);
		
		if(gameClient.isNextPhaseSignalActiavted()) {
			c.close();
			Starter.pme.exitChampionSelectPage();
			Starter.pme.GoGamePage(mode, isGameMaster, gameServer, gameClient);
		}
	}
	
	public void setThis() {
		Chatr.setFont();
		Map<TextAttribute, Object> attributes = new HashMap<>();
		chatArea.setFont(Font.getFont(attributes));
		championPack = new ChampionManager();
		imageScroller = new ChampionSelectionImageScroller(new Rectangle(350, 120, 577, 461), new Dimension(65,65), 6, championPack.pack, 1, 29, 10);
		for(int i=0;i<championPack.pack.size();i++) {
			final int index = i;
			imageScroller.championClick[i].addOnButtonListener(new onButtonListener() {
	
				@Override
				public void onClick() {
					// TODO Auto-generated method stub
					if(imageScroller.prevSelected != -1) {
						PickBtn.setVisible(true);
						imageScroller.championClick[imageScroller.prevSelected].unselectThis();
					}
					imageScroller.prevSelected = index;
					if(RoomInfo.getMe(Variables.Username).isSelecting())
						gameClient.sendMessageToServer(NetworkTag.CHAMP_SELECT_SIGNAL+"|"+championPack.pack.get(index).getChampionCode());
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
		}
		this.add(imageScroller);
	}
	
	public NormalChampionSelectPage(GameMode mode, boolean isGM, GameServer server, GameClient client) {
		setPanelSize(Constants.ClientPanelDimension);
		setVisible(true);
		this.setLayout(null);
		
		this.isGameMaster = isGM;
		this.gameServer = server;
		this.gameClient = client;
		this.mode = mode;
		
		
		try {
			c = AudioSystem.getClip();
			c.open(AudioSystem.getAudioInputStream(new File(SoundManager.KnifeWindChampionSelectBGMPath).getAbsoluteFile()));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ff.setDeciBel(c, SoundManager.VERY_TINY_VOLUME);
		c.start();
		
		Chatr.addEnterListener(new EnterListener() {
			@Override
			public void onEnterKey() {
				// TODO Auto-generated method stub
				if(Chatr.getText().length()==0)return;
				gameClient.sendMessageToServer(NetworkTag.CHAT+"|"+NetworkTag.NORMAL_CHAMP_SELECT_ROOM+"|"+Variables.Username+"|"+Chatr.getText()+"|"+NetworkTag.NON_SYSTEMIC);
				Chatr.setText("");
			}
			
		});
		this.add(Chatr);
		
		Border border = BorderFactory.createMatteBorder(0, 1, 1, 1, new Color(27,37,41,255));
		
		UIManager.getLookAndFeel().uninitialize();
		chatArea.setBounds(new Rectangle(46, 486, 299, 180));
		chatArea.setBackground(new Color(2,11,17,255));
		
		chatArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5,10,7,10)));
		chatArea.setMargin(new Insets(15,15,15,15));
		chatArea.setEditable(false);
		chatArea.setVisible(true);
		
		scrollPane = new JScrollPane(chatArea);
		scrollPane.setViewportView(chatArea);
		scrollPane.setBackground(new Color(2,11,17,255));
		scrollPane.setBounds(21, 486, 299, 180);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(6,0));
		scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors()
            {
                this.thumbColor = new Color(102, 84, 36);
                this.trackColor = new Color(2,11,17);
                this.thumbDarkShadowColor = new Color(102, 84, 36);
            }
			@Override
	        protected JButton createDecreaseButton(int orientation) {
	            return createZeroButton();
	        }

	        @Override    
	        protected JButton createIncreaseButton(int orientation) {
	            return createZeroButton();
	        }

	        private JButton createZeroButton() {
	            JButton jbutton = new JButton();
	            jbutton.setPreferredSize(new Dimension(0, 0));
	            jbutton.setMinimumSize(new Dimension(0, 0));
	            jbutton.setMaximumSize(new Dimension(0, 0));
	            return jbutton;
	        }
		});
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVisible(true);
		
		this.add(scrollPane);
		
		
		PickBtn = new TriggeredButton(
				ImageManager.ActivatedPickButtonImage,
				ImageManager.FocusedPickButtonImage,
				null,
				new Coordinate(553, 587),
				new Coordinate(553,587),
				null,
				new Rectangle(553,587,181,41),
				0,
				0
				);
		PickBtn.addOnButtonListener(new onButtonListener() {
			@Override
			public void onClick() {
				gameClient.sendMessageToServer(NetworkTag.CHAMP_PICK_SIGNAL);
				imageScroller.setVisible(false);
				PickBtn.setVisible(false);
				customPhase++;
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
		PickBtn.setVisible(false);
		this.add(PickBtn);
		
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
		
		this.setDoubleBuffered(true);
		
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
		
		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				if(e.getWheelRotation()>0) {
					imageScroller.scrollUp();
				}
				else if(e.getWheelRotation()<0) {
					imageScroller.scrollDown();
				}
			}
			
		});
	}
	
	public boolean shouldScroll() {
        int minimumValue = scrollPane.getVerticalScrollBar().getValue() + scrollPane.getVerticalScrollBar().getVisibleAmount();
        int maximumValue = scrollPane.getVerticalScrollBar().getMaximum();
        return maximumValue == minimumValue;
	}
	
	public void setPanelSize(Dimension ps) {
		this.setSize(ps);
		this.PanelSize = ps;
	}
}
