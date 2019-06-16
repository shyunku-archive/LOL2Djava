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

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Core.Starter;
import Global.Constants;
import Global.Functions;
import Global.Variables;
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
	
	
	private static enum GameMode {SummonersRift, KnifeWind, URF};
	private static GameMode gamemode = GameMode.SummonersRift;
	
	private static TriggeredButton HomeBtn, CloseBtn, CancelBtn, CreateBtn, ParticipateBtn;
	private static TriggeredButton SRsel, KWsel, URFsel;
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.drawImage(Constants.ClientTemplateImage, null, 0, 0);
		
		g.setColor(Color.WHITE);
		g.setFont(ff.getClassicFont(16F, true));
		g.drawString(Variables.Username, 1127, 40);
		
		g.drawImage(Constants.GameModeSelectImage,null,0,0);
		
		
		g.setColor(new Color(65,60,70,255));
		g.setFont(ff.getFancyFont(13F, true));
		ect.fde.drawCenteredString(g, Constants.ProgramVersion, new Rectangle(1060, 688, 220, 32));
		
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
	
	public ClientGameModeSelectPage() {
		setPanelSize(Constants.ClientPanelDimension);
		setVisible(true);
		this.setLayout(null);
		
		SRsel = new TriggeredButton(
				Constants.SummonersRiftunSelected,
				null,
				Constants.SummonersRiftSelected,
				new Coordinate(131,144),
				null,
				new Coordinate(113,135),
				new Rectangle(120,144,255,302),
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
				SRsel.selectThis();
				KWsel.unselectThis();
				URFsel.unselectThis();
				if(!SRsel.isSelected())
					ff.playSoundClip(Constants.GameModeSelectSoundPath, Constants.DEFAULT_VOLUME);
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
				new Rectangle(400,144,274,341),
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
				SRsel.unselectThis();
				KWsel.selectThis();
				URFsel.unselectThis();
				if(!KWsel.isSelected())
					ff.playSoundClip(Constants.GameModeSelectSoundPath, Constants.DEFAULT_VOLUME);
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
				new Rectangle(750,144,227,302),
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
				SRsel.unselectThis();
				KWsel.unselectThis();
				URFsel.selectThis();
				if(!URFsel.isSelected())
					ff.playSoundClip(Constants.GameModeSelectSoundPath, Constants.DEFAULT_VOLUME);
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
