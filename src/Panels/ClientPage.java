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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Core.Starter;
import Engines.TriggeredAnimationEngine;
import Engines.TriggeredButton;
import Global.Constants;
import Global.Functions;
import Global.Variables;
import Utility.Coordinate;
import Utility.EnginesControl;
import Utility.onButtonListener;

@SuppressWarnings("serial")
public class ClientPage extends JPanel{
	//Necessary
	public static Dimension PanelSize;
	public static boolean isActivated;
	
	//Utility
	private static Functions ff = new Functions();
	private static EnginesControl ect = new EnginesControl();
	
	private static TriggeredButton GameStartBtn, CloseBtn;
	
	private static ArrayList<String> Content = new ArrayList<>();
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		g.drawImage(Constants.ClientTemplateImage, null, 0, 0);
		
		g.setColor(Color.WHITE);
		g.setFont(ff.getClassicFont(16F, true));
		g.drawString(Variables.Username, 1127, 40);
		
		g.setColor(new Color(65,60,70,255));
		g.setFont(ff.getFancyFont(13F, true));
		ect.fde.drawCenteredString(g, Constants.ProgramVersion, new Rectangle(1060, 688, 220, 32));
		
		g.setColor(Color.WHITE);
		g.setFont(ff.getFancyFont(15F, false));
		for(int i=0;i<Content.size();i++)
			g.drawString(Content.get(i), 80, 180+25*i);
		
		GameStartBtn.draw(g);
		CloseBtn.draw(g);
	}
	
	public void update() {
	}
	
	public void addString(String str) {
		Content.add(str);
	}
	
	public void addLine() {
		Content.add("");
	}
	
	public ClientPage() {
		this.setPanelSize(Constants.ClientPanelDimension);
		this.setLayout(null);
		
		addString("안녕하십니까? 소환사 여러분들!");
		addString("본 게임의 정식 명칭은 League Of Legends 2D JAVA Version [이하 자바롤]으로, 한글로는 리그오브레전드 2D 자바 버전입니다.");
		addString("이 게임은 텐센트 사의 리그오브레전드 게임을 모티브로 만든 팬 게임으로, 본 게임의 제작 의도에 영리적인 목적이 없음을 밝힙니다.");
		addString("자바롤의 진행 방식은 리그오브레전드와 완전히 동일하며, 챔피언, 스킬, 스펠, 판정 등의 차이가 있을 수 있습니다.");
		addString("자바롤은 자바 설치를 반드시 필요로 하며, 방을 만든 서버장의 public IP를 입력하고 [참가]버튼을 누르면 참여할 수 있습니다.");
		addString("단 서버장은 방화벽을 해제해야하고, 포트포워딩을 해야합니다. 자바롤의 이용포트는 [10200]입니다.");
		addString("즐겁게 이용해주시기 바랍니다! 게임 정식 오픈은 8월 30일 오전 7시입니다.");
		addString("션쿠의 블로그 (링크: http://15.164.138.137:8080/)에서 다운받으실 수 있습니다. - 현재 불가능");
		
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
		GameStartBtn = new TriggeredButton(
				null,
				Constants.FocusedGameStartButtonImage,
				null,
				null,
				new Coordinate(28,21),
				null,
				new Rectangle(64,23,122,33),
				1000,
				1000
				);
		GameStartBtn.addOnButtonListener(new onButtonListener() {
			@Override
			public void onClick() {
			}

			@Override
			public void onEnter() {
				// TODO Auto-generated method stub
				ff.playSoundClip(Constants.ActivatedGameStartButtonSoundFilePath, Constants.GAME_START_BUTTON_SOUND_VOLUME);
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
				ff.playSoundClip(Constants.GameStartButtonSoundFilePath, Constants.GAME_START_BUTTON_SOUND_VOLUME);
				Starter.pme.exitClientPage();
				Starter.pme.GoClientGameModeSelectPage();
			}
		});
		
		this.add(GameStartBtn);
		
		
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
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public void setPanelSize(Dimension ps) {
		this.setSize(ps);
		this.PanelSize = ps;
	}
}
