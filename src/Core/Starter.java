package Core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import Engines.PanelManageEngine;
import Global.Constants;
import Global.Functions;
import Global.Variables;
import Utility.Coordinate;
import Utility.EnginesControl;
import Utility.TriggeredButton;
import Utility.onButtonListener;

public class Starter {
	public static JFrame frame = new JFrame("League of Legends");
	public static PanelManageEngine pme = new PanelManageEngine();
	private static Functions ff = new Functions();
	public static Font fancyFont = null, classicFont = null;
	public static Font fancyFontBold = null, classicFontBold = null;
	
	private static Thread mainThread = new Thread(new Runnable() {
		@Override
		public void run() {
			while(true) {
				if(System.currentTimeMillis() - Variables.StopWatchFlag > Constants.FrameCountPeriod) {
					Variables.FPS = (1000D/(double)Constants.FrameCountPeriod)*(double)Variables.FrameCount;
					Variables.FrameCount = 0;
					Variables.StopWatchFlag = System.currentTimeMillis();
				}
				try {
					Thread.sleep((long)(1000D/(double)Constants.FPSLimit)+1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				frame.repaint();
				update();
				pme.updateActivated();
			}
		}
	});
	
	private static EnginesControl ect = new EnginesControl();
	
	public static void selectResources() {
		//FONTS
		try {
			fancyFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Fonts\\Global\\SH.ttf"));
			classicFont = Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Fonts\\Global\\SM.ttf"));
			fancyFontBold = Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Fonts\\Global\\SHB.TTF"));
			classicFontBold = Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Fonts\\Global\\SMB.TTF"));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Sounds
		Constants.GameStartButtonSoundFilePath = "Resources\\Audios\\ClientPage\\GameStartButtonSound.wav";
		Constants.lightClickSoundFilePath = "Resources\\Audios\\Global\\LightClickSound.wav";
		Constants.LoginButtonPressedSoundPath = "Resources\\Audios\\LoginPage\\LoginButtonPressedSound.wav";
		Constants.ActivatedGameStartButtonSoundFilePath= "Resources\\Audios\\ClientPage\\ActivatedGameStartButtonSound.wav";
		Constants.GameSelectionCancelSoundPath= "Resources\\Audios\\ClientPage\\GameSelectionCancelSound.wav";
		Constants.GameModeSelectSoundPath= "Resources\\Audios\\ClientPage\\GameModeSelectSound.wav";
		Constants.GameModeFocusSoundPath= "Resources\\Audios\\ClientPage\\GameModeFocusSound.wav";
		
		Constants.ActivatedCPSoundPath = "Resources\\Audios\\ClientPage\\ActivatedCPSound.wav";
		Constants.SelectedCPSoundPath = "Resources\\Audios\\ClientPage\\CPselectSound.wav";
		Constants.ParticipateSoundPath = "Resources\\Audios\\ClientPage\\ParticipateRoomSound.wav";
		
		Constants.ActivatedRealGameStartButtonSoundPath = "Resources\\Audios\\ClientPage\\ActivatedRealGameStartButtonSound.wav";
		Constants.PressedRealGameStartButtonSoundPath = "Resources\\Audios\\ClientPage\\PressedRealGameStartButtonSound.wav";
		
		//Images
		try {
			Constants.LoginPageFrameImage = ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\LoginPage\\LoginPageFrameImage.png")),
					0, 0);
			Constants.FocusedTerminateButtonImage = ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\LoginPage\\FocusedTerminateButton.png")),
					0, 0);
			Constants.FocusedLocaleImage= ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\LoginPage\\FocusedLocale.png")),
					0, 0);
			Constants.ActivatedLoginButtonImage= ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\LoginPage\\ActivatedLoginButton.png")),
					0, 0);
			Constants.FocusedLoginButtonImage= ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\LoginPage\\FocusedLoginButton.png")),
					0, 0);
			Constants.AuthentificationImage= ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\LoginPage\\Authentification.png")),
					0, 0);
			Constants.ClientTemplateImage = ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\ClientPage\\ClientTemplate.png")),
					0, 0);
			Constants.FocusedGameStartButtonImage = ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\ClientPage\\FocusedGameStartButton.png")),
					0, 0);
			Constants.GameModeSelectImage = ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\ClientPage\\GameModeSelection.png")),
					0, 0);
			Constants.FocusedGameSelectionCancelButtonImage = ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\ClientPage\\FocusedCancelButton.png")),
					0, 0);
			Constants.FocusedHomeButtonImage = ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\ClientPage\\FocusedHomeButton.png")),
					0, 0);
			Constants.FocusedGameCreateButtonImage = ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\ClientPage\\FocusedGameCreateButton.png")),
					0, 0);
			Constants.FocusedGameParticipateButtonImage = ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\ClientPage\\FocusedGameParticipateButton.png")),
					0, 0);
			Constants.GameModeSelectAdditionImage = ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\ClientPage\\GameSelectAdditional.png")),
					0, 0);
			Constants.WaitingRoomImage = ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\ClientPage\\WaitingRoomTemplate.png")),
					0, 0);
			Constants.RealGameStartButtonImage = ect.ice.CallImage(
					ImageIO.read(new File("Resources\\Images\\ClientPage\\RealGameStartButton.png")),
					0, 0);
			
			BufferedImage sru, kwu, urfu, srs, kws, urfs;
			Color backG = new Color(4,16,26);
			
			srs =  ImageIO.read(new File("Resources\\Images\\ClientPage\\ModeSelected\\SummonersRiftSelected.png"));
			Constants.SummonersRiftSelected = srs;
			
			kws = ImageIO.read(new File("Resources\\Images\\ClientPage\\ModeSelected\\KnifeWindSelected.png"));
			Constants.KnifeWindSelected = kws;
			
			urfs = ImageIO.read(new File("Resources\\Images\\ClientPage\\ModeSelected\\URFSelected.png"));
			Constants.URFSelected = urfs;
			
			sru = ImageIO.read(new File("Resources\\Images\\ClientPage\\ModeUnSelected\\SummonersRiftunSelected.png"));
			Constants.SummonersRiftunSelected= ect.ice.RenderImageAsOpacity(sru,backG, 15);
			
			kwu = ImageIO.read(new File("Resources\\Images\\ClientPage\\ModeUnSelected\\KnifeWindunSelected.png"));
			Constants.KnifeWindunSelected = ect.ice.RenderImageAsOpacity(kwu,backG, 15);
			
			urfu = ImageIO.read(new File("Resources\\Images\\ClientPage\\ModeUnSelected\\URFunSelected.png"));
			Constants.URFunSelected = ect.ice.RenderImageAsOpacity(urfu, backG, 15);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void initial() {
		
	}
	
	public static void update() {
		Variables.FrameCount++;
		Variables.TotalFrameCount++;
		Variables.ElapsedTime = System.currentTimeMillis() - Variables.ElapsedTimeFlag;
	}
	
	public static void main(String[] args) {
		selectResources();
		initial();
		
		frame.setFocusable(true);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		frame.requestFocusInWindow();
		
		pme.setAll();
		
		pme.LogPage();
		pme.goLoginPage();
		
		mainThread.start();
		
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				ff.cprint("KeyPressedEvent : "+e.getKeyCode());
				switch(e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					System.exit(0);
					break;
				case KeyEvent.VK_F2:
					Variables.ShowDetails = !Variables.ShowDetails;
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
