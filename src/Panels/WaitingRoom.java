package Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Core.Starter;
import Global.Constants;
import Global.Constants.GameMode;
import Global.Functions;
import Global.Variables;
import Network.GameServer;
import Network.GameServerConnector;
import Network.GameServerConnector.OnMessageListener;
import Network.Message.NetworkMessage;
import Network.Message.FromSever.WaitingRoomStatusMessage;
import Utility.Coordinate;
import Utility.EnginesControl;
import Utility.TriggeredButton;
import Utility.User;
import Utility.onButtonListener;

public class WaitingRoom extends JPanel{
		//Necessary
		public static Dimension PanelSize;
		public static boolean isActivated;
		
		//Utility
		private static Functions ff = new Functions();
		private static EnginesControl ect = new EnginesControl();
		
		private boolean isCreateMode = true;
		private GameMode gameMode = GameMode.SummonersRift;
		
		private static TriggeredButton HomeBtn, CloseBtn, CancelBtn, RealGameStartBtn;
		
		private String Roomname;
		private String password;
		
		private GameServer gameServer;
		private GameServerConnector connector;
		
		private ArrayList<User> userList1, userList2;
		
		private boolean isGameHost;
		
		public void paintComponent(Graphics graphics) {
			Graphics2D g = (Graphics2D) graphics;
			
			g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			
			g.drawImage(Constants.ClientTemplateImage, null, 0, 0);
			
			g.setColor(Color.WHITE);
			g.setFont(ff.getClassicFont(16F, true));
			g.drawString(Variables.Username, 1127, 40);
			
			g.drawImage(Constants.WaitingRoomImage,null,0,0);
			
			g.setColor(new Color(65,60,70,255));
			g.setFont(ff.getFancyFont(13F, true));
			ect.fde.drawCenteredString(g, Constants.ProgramVersion, new Rectangle(1060, 688, 220, 32));
			
			g.setColor(new Color(240, 230, 210));
			g.setFont(ff.getClassicFont(27F, true));
			g.drawString(Roomname, 90, 120);
			
			g.setColor(new Color(170, 165, 119));
			g.setFont(ff.getClassicFont(13F, true));
			switch(gameMode) {
			case SummonersRift:
				g.drawImage(Constants.SRicon, null, 40,100);
				g.drawString("¼ÒÈ¯»çÀÇ Çù°î", 90, 143);
				break;
			case KnifeWind:
				g.drawImage(Constants.KWicon, null, 40,100);
				g.drawString("Ä®¹Ù¶÷ ³ª¶ô", 90, 143);
				break;
			case URF:
				g.drawImage(Constants.URFicon, null, 40,100);
				g.drawString("U.R.F", 90, 143);
				break;
			}
			
			g.setFont(ff.getClassicFont(16F, true));
			
			g.drawString("1ÆÀ", 70, 200);
			g.drawString("2ÆÀ", 970, 200);
			
			g.setColor(new Color(240, 230, 210));
			
			try {
				g.drawString("Host IP address : "+InetAddress.getLocalHost(), 630, 510);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			g.setColor(new Color(70,70,70));
			
			
			for(int i=0;i<5;i++)
				for(int j=0;j<2;j++)
					g.drawLine(50+495*j, 260+50*i, 530+495*j, 260+50*i);
			
			g.setColor(new Color(240, 230, 210));
			
			
			FontMetrics metrics = g.getFontMetrics(g.getFont());
			for(int i=0;i<userList1.size();i++) {
				String name = userList1.get(i).getUserName();
				g.drawString(name, 60, 245+50*i);
				if(userList1.get(i).isGameHost()) {
					g.drawImage(Constants.GameHostSymbol, null, 60+metrics.stringWidth(name)+15, 245+50*i-15);
				}
			}
			for(int i=0;i<userList2.size();i++) {
				String name =userList2.get(i).getUserName();
				g.drawString(name, 60+495, 245+50*i);
				if(userList2.get(i).isGameHost())
					g.drawImage(Constants.GameHostSymbol, null, 60+495+metrics.stringWidth(name)+15, 245+50*i-15);
			}
			
			
			if(this.isGameHost)
				RealGameStartBtn.draw(g);
			CancelBtn.draw(g);
			HomeBtn.draw(g);
			CloseBtn.draw(g);
		}
		
		public void update() {
			if(!this.isGameHost)return;
			userList1 = gameServer.getUserList(1);
			userList2 = gameServer.getUserList(2);
		}
		
		public void setThis() {
			if(!this.isGameHost)return;
			this.gameServer.startServer();
			this.connector = new GameServerConnector(Variables.Username, Constants.LOCAL_HOST_ADDRESS, true);
		}
		
		public void setConnector(GameServerConnector co) {
			this.connector = co;
		}
		
		public WaitingRoom(boolean isC, GameMode mode, String Roomname, String password, boolean isGameHost) {
			this.isCreateMode = isC;
			this.gameMode = mode;
			this.Roomname = Roomname;
			this.password = password;
			
			this.isGameHost = isGameHost;
			if(this.isGameHost)
				this.gameServer = new GameServer(null);
			
			if(!this.isGameHost)
				connector.setOnMessageListener(new OnMessageListener() {
	
					@Override
					public void receivedMessage(String msg) {
						String[] seg = msg.split(Pattern.quote("|"));
						if (seg[0].equals(NetworkMessage.WAITING_ROOM + "")) {
							WaitingRoomStatusMessage rmsg = new WaitingRoomStatusMessage();
							rmsg.fromMsg(seg);
							userList1 = rmsg.getUserList(1);
							userList2 = rmsg.getUserList(2);
						}
						if (msg.equals(NetworkMessage.GAME_START_SIGNAL + "")) {
						}
					}
				});
			
			setPanelSize(Constants.ClientPanelDimension);
			setVisible(true);
			this.setLayout(null);
			
			RealGameStartBtn = new TriggeredButton(
					null,
					Constants.RealGameStartButtonImage,
					null,
					null,
					new Coordinate(453,665),
					null,
					new Rectangle(461,669,165,32),
					20,
					20
					);
			RealGameStartBtn.addOnButtonListener(new onButtonListener() {

				@Override
				public void onClick() {
					// TODO Auto-generated method stub
					ff.playSoundClip(Constants.PressedRealGameStartButtonSoundPath, Constants.DEFAULT_VOLUME);
				}

				@Override
				public void onEnter() {
					// TODO Auto-generated method stub
					ff.playSoundClip(Constants.ActivatedRealGameStartButtonSoundPath, Constants.DEFAULT_VOLUME);
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
			if(isGameHost)
				this.add(RealGameStartBtn);
			CancelBtn = new TriggeredButton(
					null,
					Constants.FocusedGameSelectionCancelButtonImage,
					null,
					null,
					new Coordinate(430,669),
					null,
					new Rectangle(428,669,32,32),
					20,
					20
					);
			CancelBtn.addOnButtonListener(new onButtonListener() {
				@Override
				public void onClick() {
					// TODO Auto-generated method stub
					if(isGameHost)
						gameServer.endServer();
					else
						connector.endConnection();
					Starter.pme.exitWaitingPage();
					Starter.pme.goClientPage();
					ff.playSoundClip(Constants.GameSelectionCancelSoundPath, Constants.GAME_SELECT_CANCEL_SOUND_VOLUME);
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
			this.add(CancelBtn);
			CloseBtn = new TriggeredButton(
					null,
					Constants.FocusedTerminateButtonImage,
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
					if(isGameHost)
						gameServer.endServer();
					else
						connector.endConnection();
					ff.playSoundClip(Constants.lightClickSoundFilePath, Constants.LIGHT_CLICK_SOUND_VOLUME);
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
			HomeBtn = new TriggeredButton(
					null,
					Constants.FocusedHomeButtonImage,
					null,
					null,
					new Coordinate(249,0),
					null,
					new Rectangle(253,2,50,78),
					50,
					400
					);
			HomeBtn.addOnButtonListener(new onButtonListener() {
						@Override
						public void onClick() {
							if(isGameHost)
								gameServer.endServer();
							else
								connector.endConnection();
							ff.playSoundClip(Constants.lightClickSoundFilePath, Constants.LIGHT_CLICK_SOUND_VOLUME);
							Starter.pme.exitWaitingPage();
							Starter.pme.goClientPage();
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
			this.add(HomeBtn);
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
