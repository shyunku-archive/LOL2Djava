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
		
		addString("�ȳ��Ͻʴϱ�? ��ȯ�� �����е�!");
		addString("�� ������ ���� ��Ī�� League Of Legends 2D JAVA Version [���� �ڹٷ�]����, �ѱ۷δ� ���׿��극���� 2D �ڹ� �����Դϴ�.");
		addString("�� ������ �ټ�Ʈ ���� ���׿��극���� ������ ��Ƽ��� ���� �� ��������, �� ������ ���� �ǵ��� �������� ������ ������ �����ϴ�.");
		addString("�ڹٷ��� ���� ����� ���׿��극����� ������ �����ϸ�, è�Ǿ�, ��ų, ����, ���� ���� ���̰� ���� �� �ֽ��ϴ�.");
		addString("�ڹٷ��� �ڹ� ��ġ�� �ݵ�� �ʿ�� �ϸ�, ���� ���� �������� public IP�� �Է��ϰ� [����]��ư�� ������ ������ �� �ֽ��ϴ�.");
		addString("�� �������� ��ȭ���� �����ؾ��ϰ�, ��Ʈ�������� �ؾ��մϴ�. �ڹٷ��� �̿���Ʈ�� [10200]�Դϴ�.");
		addString("��̰� �̿����ֽñ� �ٶ��ϴ�! ���� ���� ������ 8�� 30�� ���� 7���Դϴ�.");
		addString("������ ��α� (��ũ: http://15.164.138.137:8080/)���� �ٿ������ �� �ֽ��ϴ�. - ���� �Ұ���");
		
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
