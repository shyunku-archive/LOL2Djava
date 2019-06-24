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
import Engines.PageControl;
import Network.NetworkCore.GameServer;
import Objects.Coordinate;
import Global.Constants;
import Global.Functions;
import Global.ImageManager;
import Global.SoundManager;
import Global.Variables;
import Global.Constants.GameMode;
import Utility.EnginesControl;
import Utility.TriggeredButton;
import Utility.TriggeredTextArea;
import Utility.onButtonListener;

public class ClientGameModeSelectPage extends JPanel implements PageControl{

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
	
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.drawImage(ImageManager.ClientTemplateImage, null, 0, 0);
		
		g.setColor(Color.WHITE);
		g.setFont(ff.getClassicFont(16F, true));
		g.drawString(Variables.Username, 1127, 40);
		
		g.drawImage(ImageManager.GameModeSelectImage,null,0,0);
		g.drawImage(ImageManager.GameModeSelectAdditionImage, null, 205, 470);
		
		
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
		
		CancelBtn.draw(g);
		CreateBtn.draw(g);
		ParticipateBtn.draw(g);
		
		HomeBtn.draw(g);
		CloseBtn.draw(g);
	}
	
	public void update() {
	}
	
	public void setThis() {
		//RoomName.setText(Variables.Username+"님의 게임");
		RoomName.setText("192.168.0.4");
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
				ImageManager.SummonersRiftunSelected,
				null,
				ImageManager.SummonersRiftSelected,
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
				ff.playSoundClip(SoundManager.GameModeFocusSoundPath, SoundManager.DEFAULT_VOLUME);
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
					ff.playSoundClip(SoundManager.GameModeSelectSoundPath, SoundManager.DEFAULT_VOLUME);
				SRsel.selectThis();
				KWsel.unselectThis();
				URFsel.unselectThis();
				gamemode = GameMode.SummonersRift;
			}
			
		});
		this.add(SRsel);
		
		KWsel = new TriggeredButton(
				ImageManager.KnifeWindunSelected,
				null,
				ImageManager.KnifeWindSelected,
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
				ff.playSoundClip(SoundManager.GameModeFocusSoundPath,SoundManager.DEFAULT_VOLUME);
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
					ff.playSoundClip(SoundManager.GameModeSelectSoundPath, SoundManager.DEFAULT_VOLUME);
				SRsel.unselectThis();
				KWsel.selectThis();
				URFsel.unselectThis();
				gamemode = GameMode.KnifeWind;
			}
			
		});
		this.add(KWsel);
		
		URFsel = new TriggeredButton(
				ImageManager.URFunSelected,
				null,
				ImageManager.URFSelected,
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
				ff.playSoundClip(SoundManager.GameModeFocusSoundPath, SoundManager.DEFAULT_VOLUME);
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
					ff.playSoundClip(SoundManager.GameModeSelectSoundPath, SoundManager.DEFAULT_VOLUME);
				SRsel.unselectThis();
				KWsel.unselectThis();
				URFsel.selectThis();
				gamemode = GameMode.URF;
			}
			
		});
		
		ParticipateBtn = new TriggeredButton(
				null,
				ImageManager.FocusedGameParticipateButtonImage,
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
				ff.playSoundClip(SoundManager.SelectedCPSoundPath, SoundManager.DEFAULT_VOLUME);
					
				if(RoomName.getText().length()==0)return;
				Starter.pme.exitClientGameModeSelectPage();
				Starter.pme.GoWaitingPage(gamemode, RoomName.getText(), Password.getText(), false);
			}

			@Override
			public void onEnter() {
				// TODO Auto-generated method stub
				ff.playSoundClip(SoundManager.ActivatedCPSoundPath, SoundManager.HIGHER_VOLUME);
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
				ImageManager.FocusedGameCreateButtonImage,
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
				ff.playSoundClip(SoundManager.SelectedCPSoundPath, SoundManager.HIGHER_VOLUME);
				if(gamemode != GameMode.KnifeWind) {
					JOptionPane.showMessageDialog(null, "칼바람 나락 외의 맵은 아직 개발되지 않았습니다.");
					return;
				}
				if(RoomName.getText().length()==0)return;
				Starter.pme.exitClientGameModeSelectPage();
				Starter.pme.GoWaitingPage(gamemode, RoomName.getText(), Password.getText(), true);
			}

			@Override
			public void onEnter() {
				// TODO Auto-generated method stub
				ff.playSoundClip(SoundManager.ActivatedCPSoundPath, SoundManager.HIGHER_VOLUME);
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
				ImageManager.FocusedGameSelectionCancelButtonImage,
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
				ff.playSoundClip(SoundManager.GameSelectionCancelSoundPath, SoundManager.GAME_SELECT_CANCEL_SOUND_VOLUME);
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
		HomeBtn = new TriggeredButton(
				null,
				ImageManager.FocusedHomeButtonImage,
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
						ff.playSoundClip(SoundManager.lightClickSoundFilePath, SoundManager.LIGHT_CLICK_SOUND_VOLUME);
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
