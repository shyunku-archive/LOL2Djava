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
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
	
	private static TriggeredButton CloseBtn, TerminateBtn;
	
	//Server
	private GameServer gameServer;
	private GameClient gameClient;
	private boolean isGameMaster;
	
	ChampionManager championPack;
	
	GameMode mode;
	NormalChampionSelectingRoomInfo RoomInfo;
	ChampionSelectionImageScroller imageScroller;
	
	private static TriggeredTextArea Chatr = new TriggeredTextArea(new Rectangle(21, 669, 300, 30));
	private static JScrollPane scrollPane;
	private static CustomTextPane chatArea = new CustomTextPane(true);
	private int chatSize = 0;
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.drawImage(ImageManager.ChampionSelectTemplate, null, 0, 0);
		//293·Î Á¤·Ä
		if(RoomInfo != null) {
			int team1num = RoomInfo.getUserList1().size();
			int team2num = RoomInfo.getUserList2().size();
			for(int i=0;i<team1num;i++) {
				g.drawImage(ImageManager.DeactivatedUserSlot_Team1, null, 5, 293 - (93*team1num/2) + 93*i);
			}
			for(int i=0;i<team2num;i++)
				g.drawImage(ImageManager.DeactivatedUserSlot_Team2, null, 998, 293 - (93*team1num/2) + 93*i);
		}
		
		long Remained = RoomInfo.getCurReaminWaitTime();
		
		g.setColor(Color.WHITE);
		g.setFont(ff.getClassicFont(35, true));
		ect.fde.drawCenteredString(g, ""+Remained/1000, new Rectangle(610, 33, 60, 79));
	}
	
	public void update() {
		EpicEngine ee = new EpicEngine();
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
	}
	
	public void setThis() {
		Chatr.setFont();
		Map<TextAttribute, Object> attributes = new HashMap<>();
		chatArea.setFont(Font.getFont(attributes));
		championPack = new ChampionManager();
		imageScroller = new ChampionSelectionImageScroller(new Rectangle(350, 120, 577, 461), new Dimension(65,65), 6, championPack.pack, 1, 29, 10);
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
		
		Chatr.addEnterListener(new EnterListener() {
			@Override
			public void onEnterKey() {
				// TODO Auto-generated method stub
				if(Chatr.getText().length()==0)return;
				gameClient.sendMessageToServer(NetworkTag.CHAT+"|"+NetworkTag.CHAMP_SELECT_ROOM+"|"+Variables.Username+"|"+Chatr.getText()+"|"+NetworkTag.NON_SYSTEMIC);
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
