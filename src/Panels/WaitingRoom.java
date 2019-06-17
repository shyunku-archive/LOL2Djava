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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Core.Starter;
import Engines.TriggeredTextArea;
import Engines.TriggeredTextArea.EnterListener;
import Global.Constants;
import Global.Constants.GameMode;
import Global.Functions;
import Global.Variables;
import Network.NetworkCore.GameClient;
import Network.NetworkCore.GameServer;
import Network.NetworkCore.NetworkTag;
import Network.Objects.Chat;
import Network.Objects.User;
import Utility.Coordinate;
import Utility.EnginesControl;
import Utility.TriggeredButton;
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
		private static TriggeredTextArea Chatr = new TriggeredTextArea(new Rectangle(45, 670, 300, 30));
		
		private String Roomname;
		private String password;
		private boolean isGameHost;
		
		private GameServer gameServer;							//게임 서버, 호스트일 경우에만 활성화
		private GameClient gameClient = new GameClient();;		//게임 클라이언트 무조건 활성화
		
		private ArrayList<User> userList1 = new ArrayList<>(), userList2 = new ArrayList<>();
		private ArrayList<Chat> chatLog = new ArrayList<>();
		
		private static JTextArea chatArea = new JTextArea();
		private static JScrollPane scrollPane = new JScrollPane(chatArea);
		
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
				g.drawString("소환사의 협곡", 90, 143);
				break;
			case KnifeWind:
				g.drawImage(Constants.KWicon, null, 40,100);
				g.drawString("칼바람 나락", 90, 143);
				break;
			case URF:
				g.drawImage(Constants.URFicon, null, 40,100);
				g.drawString("U.R.F", 90, 143);
				break;
			}
			
			g.setFont(ff.getClassicFont(16F, true));
			
			g.drawString("1팀", 70, 200);
			g.drawString("2팀", 970, 200);
			
			g.setColor(new Color(240, 230, 210));
			
			try {
				g.drawString("Host IP address : "+InetAddress.getLocalHost().getHostAddress(), 630, 510);
				g.drawString("Public IP address : "+Constants.publicIP, 630, 530);
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
			//게임 호스트일 경우 게임서버에서 데이터 가져와서 업데이트
			userList1 = gameClient.getRoomInfo().getUserList(1);
			userList2 = gameClient.getRoomInfo().getUserList(2);
			int originalSize = chatLog.size();
			chatLog = gameClient.getRoomInfo().getChats();
			
			for(int i= originalSize;i<chatLog.size();i++) {
				Chat c = chatLog.get(i);
				String str = "";
				if(!c.isSystemic())
					str = c.getSender() + " : ";
				str += c.getContent() + "\n";
				chatArea.append(str);
				chatArea.setCaretPosition(chatArea.getDocument().getLength());
			}
		}
		
		public void setThis() {
			Chatr.setFont();
			if(this.isGameHost) {
				this.gameServer = new GameServer();
				this.gameServer.startServer();
				this.gameClient.connect(new User(Variables.Username, NetworkTag.LOCAL_HOST_ADDRESS, true), NetworkTag.LOCAL_HOST_ADDRESS);
			}else {
		        
				this.gameClient.connect(new User(Variables.Username, Constants.publicIP, false), Roomname);
			}
		}
		
		public WaitingRoom(boolean isC, GameMode mode, String Roomname, String password, boolean isGameHost) {
			this.isCreateMode = isC;
			this.gameMode = mode;
			this.Roomname = Roomname;
			this.password = password;
			
			this.isGameHost = isGameHost;
			
			
			Chatr.addEnterListener(new EnterListener() {
				@Override
				public void onEnterKey() {
					// TODO Auto-generated method stub
					
					Chatr.setText("");
				}
				
			});

			this.add(Chatr);
			
			scrollPane.setPreferredSize(new Dimension(300, 180));
			chatArea.setBounds(new Rectangle(46,480, 299, 180));
			chatArea.setBackground(new Color(0,0,0,0));
			chatArea.setForeground(Color.WHITE);
			chatArea.setLineWrap(true);
			chatArea.setWrapStyleWord(true);
			chatArea.setEditable(false);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			chatArea.setVisible(true);
			scrollPane.setVisible(true);
			
			this.add(chatArea);
			this.add(scrollPane);
			
			
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
						gameClient.endConnect();
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
						gameClient.endConnect();
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
								gameClient.endConnect();
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
