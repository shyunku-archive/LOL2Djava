package Panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import Core.Starter;
import Engines.TriggeredAnimationEngine;
import Global.Constants;
import Global.Functions;
import Global.ImageManager;
import Global.SoundManager;
import Global.Variables;
import Utility.EnginesControl;


@SuppressWarnings("serial")
public class LoginPage extends JPanel{
	//Necessary
	public static Dimension PanelSize;
	public static boolean isActivated;
	
	//Utility
	private static Functions ff = new Functions();
	private static EnginesControl ect = new EnginesControl();
	
	
    private static String videoFilePath = "Resources\\Videos\\LoginPage\\MainVideo.mp4";
    
    private static Rectangle CloseButtonRect = new Rectangle(1252, 7, 14, 14);
    private static Rectangle LocaleButtonRect = new Rectangle(1070, 256, 84, 18);
    private static Rectangle LoginButtonRect = new Rectangle(1073, 540, 191, 31);
    private static Rectangle textArea = new Rectangle(1072, 177, 191, 28);
    private static boolean isInCloseBtn = false;
    private static boolean isInLocaleBtn = false;
    private static boolean isInLoginBtn = false;
    private static boolean isInTextArea = false;
    
    private static Clip mainAudioClip;
    
    private static JTextArea inputNicknameArea = new JTextArea();
    
    private TriggeredAnimationEngine LoginDelayAnimation = new TriggeredAnimationEngine(100);
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if(LoginDelayAnimation.isRunning(true)) {
			g.drawImage(ImageManager.AuthentificationImage, null, 0, 0);
			if(!LoginDelayAnimation.isRunning(false)) {
				this.mainAudioClip.stop();
				Starter.pme.exitLoginPage();
				Starter.pme.goClientPage();
			}
		}else{
			g.drawImage(ImageManager.LoginPageFrameImage, null, 0, 0);
			if(this.isInCloseBtn)
				g.drawImage(ImageManager.FocusedTerminateButtonImage, null, 1252, 8);
			if(this.isInLocaleBtn)
				g.drawImage(ImageManager.FocusedLocaleImage, null, 1070, 256);
			if(inputNicknameArea.getText().length()!=0)
				if(isInLoginBtn)
					g.drawImage(ImageManager.FocusedLoginButtonImage, null, 1063, 531);
				else
					g.drawImage(ImageManager.ActivatedLoginButtonImage, null, 1072, 538);
			
			g.setColor(new Color(255, 210, 170));
			g.setFont(ff.getClassicFont(13F, true));
			g.drawString("´Ð³×ÀÓ", 1073, 170);
			
			g.setColor(new Color(65,60,70,255));
			g.setFont(ff.getFancyFont(13F, true));
			ect.fde.drawCenteredString(g, Constants.ProgramVersion, new Rectangle(1060, 688, 220, 32));
		}
	}
	
	public void update() {
		LoginDelayAnimation.update();
		isInCloseBtn = CloseButtonRect.contains(Variables.mousePos.getX(), Variables.mousePos.getY());
		isInLocaleBtn = LocaleButtonRect.contains(Variables.mousePos.getX(), Variables.mousePos.getY());
		isInLoginBtn = LoginButtonRect.contains(Variables.mousePos.getX(), Variables.mousePos.getY());
		isInTextArea = textArea.contains(Variables.mousePos.getX(), Variables.mousePos.getY());
		if(isInCloseBtn||isInLocaleBtn||(isInLoginBtn&&(inputNicknameArea.getText().length()!=0))) {
			Starter.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}else if(isInTextArea){
			Starter.frame.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		}else {
			Starter.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	public void setThis() {
		inputNicknameArea.setFont(Starter.classicFont.deriveFont(15F));
	}
	
	public void setPanelSize(Dimension ps) {
		this.setSize(ps);
		this.PanelSize = ps;
	}
	public LoginPage() {
		setPanelSize(new Dimension(1280, 720));
		setVisible(true);
		
		inputNicknameArea.setBounds(1072, 177, 191, 28);
		inputNicknameArea.setBackground(new Color(0,0,0,0));
		inputNicknameArea.setBorder(BorderFactory.createCompoundBorder(
				inputNicknameArea.getBorder(), 
		        BorderFactory.createEmptyBorder(5, 8, 5, 5)));
		this.disableKeys(inputNicknameArea.getInputMap(), new String[]{"ENTER"});
		inputNicknameArea.setForeground(new Color(220,220,220,255));
		try {
			inputNicknameArea.setFont(Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Fonts\\Global\\SMB.ttf")));
		} catch (FontFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		inputNicknameArea.setVisible(true);
		inputNicknameArea.setFocusable(true);
		inputNicknameArea.setCaretColor(new Color(220,220,220,255));
		inputNicknameArea.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				Variables.mousePos.setPos(e.getX()+inputNicknameArea.getBounds().x, e.getY()+inputNicknameArea.getBounds().y);
			}
			
		});
		inputNicknameArea.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				inputNicknameArea.setFocusable(true);
				ff.playSoundClip(SoundManager.lightClickSoundFilePath, SoundManager.LIGHT_CLICK_SOUND_VOLUME);
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
		inputNicknameArea.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
				    inputNicknameArea.setFocusable(false);
					if(inputNicknameArea.getText().length()!=0){
						LoginDelayAnimation.start(false);
						Variables.Username = inputNicknameArea.getText();
						inputNicknameArea.setVisible(false);
						inputNicknameArea.setText("");
						ff.playSoundClip(SoundManager.LoginButtonPressedSoundPath, SoundManager.HIGHER_VOLUME);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.add(inputNicknameArea);
		
		

        
        this.setLayout(null);
        try {
			this.mainAudioClip = AudioSystem.getClip();
			this.mainAudioClip.open(AudioSystem.getAudioInputStream(new File("Resources\\Audios\\LoginPage\\MainAudio.wav").getAbsoluteFile()));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        ff.setDeciBel(mainAudioClip, SoundManager.DEFAULT_VOLUME);
		this.mainAudioClip.start();
        
		
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				//Variables.mousePos.setPos(e.getX(), e.getY());
				if(SwingUtilities.isLeftMouseButton(e)) {
					if(Variables.mousePos.getY()>100)return;
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
				inputNicknameArea.setFocusable(false);
				if(inputNicknameArea.getText().length()!=0)
					if(LoginButtonRect.contains(Variables.mousePos.getX(), Variables.mousePos.getY())) {
						LoginDelayAnimation.start(false);
						Variables.Username = inputNicknameArea.getText();
						inputNicknameArea.setVisible(false);
						inputNicknameArea.setText("");
						ff.playSoundClip(SoundManager.LoginButtonPressedSoundPath, 50D);
					}
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
				if(isInCloseBtn)System.exit(0);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	static void disableKeys(InputMap im,String[] keystrokeNames) {              
        for (int i = 0; i < keystrokeNames.length; ++i)
            im.put(KeyStroke.getKeyStroke(keystrokeNames[i]), "none");
    }

}
