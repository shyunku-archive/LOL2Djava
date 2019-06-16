package Panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Core.Starter;
import Engines.TriggeredTextArea;
import Global.Constants;
import Global.Constants.GameMode;
import Global.Functions;
import Global.Variables;
import Network.GameServer;
import Network.GameServerConnector;
import Utility.Coordinate;
import Utility.EnginesControl;
import Utility.TriggeredButton;
import Utility.onButtonListener;

public class ClientGameModeSelectPage extends JPanel {

	//Necessary
	public static Dimension PanelSize;
	public static boolean isActivated;
	
	//Utility
	private static Functions ff = new Functions();
	private static EnginesControl ect = new EnginesControl();
	
	
	
	private static GameMode gamemode = GameMode.SummonersRift;
	
	private static TriggeredButton HomeBtn, CloseBtn, CancelBtn, CreateBtn, ParticipateBtn;
	private static TriggeredButton SRsel, KWsel, URFsel;
	private static TriggeredTextArea RoomName = new TriggeredTextArea(new Rectangle(205,469,321,30));
	private static TriggeredTextArea Password = new TriggeredTextArea(new Rectangle(573,469,321,30));
	
	private GameServerConnector connector;
	
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.drawImage(Constants.ClientTemplateImage, null, 0, 0);
		
		g.setColor(Color.WHITE);
		g.setFont(ff.getClassicFont(16F, true));
		g.drawString(Variables.Username, 1127, 40);
		
		g.drawImage(Constants.GameModeSelectImage,null,0,0);
		g.drawImage(Constants.GameModeSelectAdditionImage, null, 205, 470);
		
		
		g.setColor(new Color(65,60,70,255));
		g.setFont(ff.getFancyFont(13F, true));
		ect.fde.drawCenteredString(g, Constants.ProgramVersion, new Rectangle(1060, 688, 220, 32));
		
		g.setColor(new Color(240, 230, 210));
		g.setFont(ff.getClassicFont(15F, true));
		g.drawString("생성 시 방 제목, 참가 시 IP 주소 입력", 205, 460);
		g.drawString("비밀번호 [생성 시 선택 사항 - 아직 구현되지 않음]", 573, 460);
		
		SRsel.draw(g);
		KWsel.draw(g);
		URFsel.draw(g);
		
		switch(gamemode) {
		case SummonersRift:
			break;
		}
		
		CancelBtn.draw(g);
		CreateBtn.draw(g);
		ParticipateBtn.draw(g);
		
		HomeBtn.draw(g);
		CloseBtn.draw(g);
	}
	
	public void update() {
	}
	
	public void setThis() {
		RoomName.setText(Variables.Username+"님의 게임");
		RoomName.setFont();
		Password.setFont();
	}
	
	public ClientGameModeSelectPage() {
		setPanelSize(Constants.ClientPanelDimension);
		setVisible(true);
		this.setLayout(null);
		
		this.add(RoomName);
		this.add(Password);
		SRsel = new TriggeredButton(
				Constants.SummonersRiftunSelected,
				null,
				Constants.SummonersRiftSelected,
				new Coordinate(131,144),
				null,
				new Coordinate(113,135),
				new Rectangle(120,144,255,244),
				20,
				20
				);
		SRsel.selectThis();
		SRsel.addOnButtonListener(new onButtonListener() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onEnter() {
				// TODO Auto-generated method stub
				ff.playSoundClip(Constants.GameModeFocusSoundPath, Constants.DEFAULT_VOLUME);
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
				if(!SRsel.isSelected())
					ff.playSoundClip(Constants.GameModeSelectSoundPath, Constants.DEFAULT_VOLUME);
				SRsel.selectThis();
				KWsel.unselectThis();
				URFsel.unselectThis();
				gamemode = GameMode.SummonersRift;
			}
			
		});
		this.add(SRsel);
		
		KWsel = new TriggeredButton(
				Constants.KnifeWindunSelected,
				null,
				Constants.KnifeWindSelected,
				new Coordinate(438,144),
				null,
				new Coordinate(400,156),
				new Rectangle(400,144,274,236),
				20,
				20
				);
		KWsel.addOnButtonListener(new onButtonListener() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onEnter() {
				// TODO Auto-generated method stub
				ff.playSoundClip(Constants.GameModeFocusSoundPath, Constants.DEFAULT_VOLUME);
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
				if(!KWsel.isSelected())
					ff.playSoundClip(Constants.GameModeSelectSoundPath, Constants.DEFAULT_VOLUME);
				SRsel.unselectThis();
				KWsel.selectThis();
				URFsel.unselectThis();
				gamemode = GameMode.KnifeWind;
			}
			
		});
		this.add(KWsel);
		
		URFsel = new TriggeredButton(
				Constants.URFunSelected,
				null,
				Constants.URFSelected,
				new Coordinate(758,161),
				null,
				new Coordinate(747,153),
				new Rectangle(750,144,227,236),
				20,
				20
				);
		this.add(URFsel);
		URFsel.addOnButtonListener(new onButtonListener() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onEnter() {
				// TODO Auto-generated method stub
				ff.playSoundClip(Constants.GameModeFocusSoundPath, Constants.DEFAULT_VOLUME);
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
				if(!URFsel.isSelected())
					ff.playSoundClip(Constants.GameModeSelectSoundPath, Constants.DEFAULT_VOLUME);
				SRsel.unselectThis();
				KWsel.unselectThis();
				URFsel.selectThis();
				gamemode = GameMode.URF;
			}
			
		});
		
		ParticipateBtn = new TriggeredButton(
				null,
				Constants.FocusedGameParticipateButtonImage,
				null,
				null,
				new Coordinate(540,665),
				null,
				new Rectangle(541,669,85,32),
				20,
				20
				);
		ParticipateBtn.addOnButtonListener(new onButtonListener() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				ff.playSoundClip(Constants.SelectedCPSoundPath, Constants.DEFAULT_VOLUME);
				if(RoomName.getText().length()==0)return;
				connector = new GameServerConnector(Variables.Username, RoomName.getText().trim(), false);
				if(!connector.isSuccess()) {
					JOptionPane.showMessageDialog(null, "해당 ip에 접속할 수 없습니다.");
					return;
				}
				Starter.pme.exitClientGameModeSelectPage();
				Starter.pme.GoWaitingPage(true, gamemode, RoomName.getText(), Password.getText(), false, connector);
			}

			@Override
			public void onEnter() {
				// TODO Auto-generated method stub
				ff.playSoundClip(Constants.ActivatedCPSoundPath, Constants.DEFAULT_VOLUME);
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
		this.add(ParticipateBtn);
		CreateBtn = new TriggeredButton(
				null,
				Constants.FocusedGameCreateButtonImage,
				null,
				null,
				new Coordinate(449,665),
				null,
				new Rectangle(461,669,78,32),
				20,
				20
				);
		CreateBtn.addOnButtonListener(new onButtonListener() {

			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				ff.playSoundClip(Constants.SelectedCPSoundPath, Constants.DEFAULT_VOLUME);
				if(RoomName.getText().length()==0)return;
				Starter.pme.exitClientGameModeSelectPage();
				Starter.pme.GoWaitingPage(true, gamemode, RoomName.getText(), Password.getText(), true, null);
			}

			@Override
			public void onEnter() {
				// TODO Auto-generated method stub
				ff.playSoundClip(Constants.ActivatedCPSoundPath, Constants.DEFAULT_VOLUME);
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
		this.add(CreateBtn);
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
				Starter.pme.exitClientGameModeSelectPage();
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
						ff.playSoundClip(Constants.lightClickSoundFilePath, Constants.LIGHT_CLICK_SOUND_VOLUME);
						Starter.pme.exitClientGameModeSelectPage();
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
