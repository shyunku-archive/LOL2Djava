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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import Engines.PanelManageEngine;
import Global.Constants;
import Global.Functions;
import Global.ImageManager;
import Global.SoundManager;
import Global.Variables;
import Objects.Coordinate;
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
	
	public static void initial() {
		try{ 
            URL url_name = new URL("https://api.ipify.org");
            BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream())); 
            // reads system IPAddress 
            Constants.publicIP = sc.readLine().trim(); 
        } 
        catch (Exception e) { 
            Constants.publicIP = "Cannot Execute Properly"; 
            e.printStackTrace();
        } 
		Constants.FullScreenDimension = new Dimension(Constants.gd.getDisplayMode().getWidth(), Constants.gd.getDisplayMode().getHeight());
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
	
	public static void selectResources() {
	    Path resourcesPath = Paths.get("Resources");

        Path fontsPath = resourcesPath.resolve("Fonts");
        Path globalFontsPath = fontsPath.resolve("global");

        Path audioPath = resourcesPath.resolve("Audios");
        Path globalAudioPath = audioPath.resolve("Global");
        Path clientPageAudioPath = audioPath.resolve("ClientPage");
        Path loginPageAudioPath = audioPath.resolve("LoginPage");
        Path championSelectPageAudioPath = audioPath.resolve("ChampionSelectPage");

		//FONTS
		try {
			fancyFont = Font.createFont(Font.TRUETYPE_FONT, new File(globalFontsPath.resolve("SH.ttf").toString()));
			classicFont = Font.createFont(Font.TRUETYPE_FONT, new File(globalFontsPath.resolve("SM.ttf").toString()));
			fancyFontBold = Font.createFont(Font.TRUETYPE_FONT, new File(globalFontsPath.resolve("SHB.TTF").toString()));
			classicFontBold = Font.createFont(Font.TRUETYPE_FONT, new File(globalFontsPath.resolve("SMB.TTF").toString()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
		
		//Sounds
	    SoundManager.GameStartButtonSoundFilePath = clientPageAudioPath.resolve("GameStartButtonSound.wav").toString();
	    SoundManager.lightClickSoundFilePath = globalAudioPath.resolve("LightClickSound.wav").toString();
	    SoundManager.LoginButtonPressedSoundPath = loginPageAudioPath.resolve("LoginButtonPressedSound.wav").toString();
	    SoundManager.ActivatedGameStartButtonSoundFilePath = clientPageAudioPath.resolve("ActivatedGameStartButtonSound.wav").toString();
	    SoundManager.GameSelectionCancelSoundPath = clientPageAudioPath.resolve("GameSelectionCancelSound.wav").toString();
	    SoundManager.GameModeSelectSoundPath= clientPageAudioPath.resolve("GameModeSelectSound.wav").toString();
	    SoundManager.GameModeFocusSoundPath= clientPageAudioPath.resolve("GameModeFocusSound.wav").toString();
	    
	    SoundManager.ActivatedCPSoundPath = clientPageAudioPath.resolve("ActivatedCPSound.wav").toString();
	    SoundManager.SelectedCPSoundPath = clientPageAudioPath.resolve("CPselectSound.wav").toString();
	    SoundManager.ParticipateSoundPath = clientPageAudioPath.resolve("ParticipateRoomSound.wav").toString();
	    
	    SoundManager.ActivatedRealGameStartButtonSoundPath = clientPageAudioPath.resolve("ActivatedRealGameStartButtonSound.wav").toString();
	    SoundManager.PressedRealGameStartButtonSoundPath = clientPageAudioPath.resolve("PressedRealGameStartButtonSound.wav").toString();
	    SoundManager.TeamMoveSoundPath = clientPageAudioPath.resolve("TeamMoveSound.wav").toString();
	    
	    //픽창
	    SoundManager.KnifeWindChampionSelectBGMPath = championSelectPageAudioPath.resolve("KnifeWindChampionPickRoom.wav").toString();
		
		//Images
		try {
			ImageManager.LoginPageFrameImage = ImageIO.read(new File("Resources/Images/LoginPage/LoginPageFrameImage.png"));
	        ImageManager.FocusedTerminateButtonImage = ImageIO.read(new File("Resources/Images/LoginPage/FocusedTerminateButton.png"));
	        ImageManager.FocusedLocaleImage= ImageIO.read(new File("Resources/Images/LoginPage/FocusedLocale.png"));
	        ImageManager.ActivatedLoginButtonImage= ImageIO.read(new File("Resources/Images/LoginPage/ActivatedLoginButton.png"));
	        ImageManager.FocusedLoginButtonImage= ImageIO.read(new File("Resources/Images/LoginPage/FocusedLoginButton.png"));
	        ImageManager.AuthentificationImage= ImageIO.read(new File("Resources/Images/LoginPage/Authentification.png"));
	        ImageManager.ClientTemplateImage = ImageIO.read(new File("Resources/Images/ClientPage/ClientTemplate.png"));
	        ImageManager.FocusedGameStartButtonImage = ImageIO.read(new File("Resources/Images/ClientPage/FocusedGameStartButton.png"));
	        ImageManager.GameModeSelectImage = ImageIO.read(new File("Resources/Images/ClientPage/GameModeSelection.png"));
	        ImageManager.FocusedGameSelectionCancelButtonImage = ImageIO.read(new File("Resources/Images/ClientPage/FocusedCancelButton.png"));
	        ImageManager.FocusedHomeButtonImage = ImageIO.read(new File("Resources/Images/ClientPage/FocusedHomeButton.png"));
	        ImageManager.FocusedGameCreateButtonImage = ImageIO.read(new File("Resources/Images/ClientPage/FocusedGameCreateButton.png"));
	        ImageManager.FocusedGameParticipateButtonImage = ImageIO.read(new File("Resources/Images/ClientPage/FocusedGameParticipateButton.png"));
	        ImageManager.GameModeSelectAdditionImage = ImageIO.read(new File("Resources/Images/ClientPage/GameSelectAdditional.png"));
	        ImageManager.WaitingRoomImage = ImageIO.read(new File("Resources/Images/ClientPage/WaitingRoomTemplate.png"));
	        ImageManager.RealGameStartButtonImage = ImageIO.read(new File("Resources/Images/ClientPage/RealGameStartButton.png"));
	        ImageManager.UnFocusedMoveTeamButtonImage = ImageIO.read(new File("Resources/Images/ClientPage/MoveTeamButton_unfocused.png"));
	        ImageManager.FocusedMoveTeamButtonImage = ImageIO.read(new File("Resources/Images/ClientPage/MoveTeamButton_focused.png"));
	        
	        //픽창
	        ImageManager.ChampionSelectTemplate = ImageIO.read(new File("Resources/Images/ChampionSelectPage/ChampionSelectTemplate.png"));
	        ImageManager.OurTeamPickedUserFrame = ImageIO.read(new File("Resources/Images/ChampionSelectPage/OurTeam/OurTeamPickedUserFrame.png"));
	        ImageManager.OurTeamPickingUserFrame = ImageIO.read(new File("Resources/Images/ChampionSelectPage/OurTeam/OurTeamPickingUserFrame.png"));
	        ImageManager.MyPickedUserFrame = ImageIO.read(new File("Resources/Images/ChampionSelectPage/OurTeam/MyPickedUserFrame.png"));
	        ImageManager.MyPickingUserFrame = ImageIO.read(new File("Resources/Images/ChampionSelectPage/OurTeam/MyPickingUserFrame.png"));
	        ImageManager.EnemyTeamPickedUserFrame = ImageIO.read(new File("Resources/Images/ChampionSelectPage/EnemyTeam/EnemyTeamPickedUserFrame.png"));
	        ImageManager.EnemyTeamPickingUserFrame = ImageIO.read(new File("Resources/Images/ChampionSelectPage/EnemyTeam/EnemyTeamPickingUserFrame.png"));
	        
	        ImageManager.ActivatedPickButtonImage = ImageIO.read(new File("Resources/Images/ChampionSelectPage/ActivatedPickButton.png"));
	        ImageManager.FocusedPickButtonImage = ImageIO.read(new File("Resources/Images/ChampionSelectPage/FocusedPickButton.png"));

	        ImageManager.FinalPhaseMainTextImage = ImageIO.read(new File("Resources/Images/ChampionSelectPage/FinalPhaseMainText.png"));
	        
	        //아이콘	         
	        ImageManager.SRicon = ImageIO.read(new File("Resources/Images/ClientPage/Icons/SRicon.png"));
	        ImageManager.KWicon = ImageIO.read(new File("Resources/Images/ClientPage/Icons/KWicon.png"));
	        ImageManager.URFicon = ImageIO.read(new File("Resources/Images/ClientPage/Icons/URFicon.png"));
	        
	        ImageManager.GameHostSymbol = ImageIO.read(new File("Resources/Images/ClientPage/Icons/GameHostSymbol.png"));
	        
	        
	        //챔피언 아이콘
	        ImageManager.AmumuIconImage = ImageIO.read(new File("Resources/Images/Champions/Icons/AmumuIcon.png"));
	        ImageManager.DariusIconImage = ImageIO.read(new File("Resources/Images/Champions/Icons/DariusIcon.png"));
	        ImageManager.JaxIconImage = ImageIO.read(new File("Resources/Images/Champions/Icons/JaxIcon.png"));
	        ImageManager.JinxIconImage = ImageIO.read(new File("Resources/Images/Champions/Icons/JinxIcon.png"));
	        ImageManager.TrindamereIconImage = ImageIO.read(new File("Resources/Images/Champions/Icons/TrindamereIcon.png"));
	        ImageManager.YasuoIconImage = ImageIO.read(new File("Resources/Images/Champions/Icons/YasuoIcon.png"));
	        ImageManager.SorakaIconImage = ImageIO.read(new File("Resources/Images/Champions/Icons/SorakaIcon.png"));
	        
	        ImageManager.ChampionIconFrame = ImageIO.read(new File("Resources/Images/ChampionSelectPage/ChampionIconFrame.png"));
	        ImageManager.SelectedChampionIconFrame = ImageIO.read(new File("Resources/Images/ChampionSelectPage/SelectedChampionIconFrame.png"));
	        ImageManager.FocusedChampionIconFrame = ImageIO.read(new File("Resources/Images/ChampionSelectPage/FocusedChampionIconFrame.png"));
	        
	        //게임모드 선택
	        BufferedImage sru, kwu, urfu, srs, kws, urfs;
	        Color backG = new Color(4,16,26);
	        
	        srs =  ImageIO.read(new File("Resources/Images/ClientPage/ModeSelected/SummonersRiftSelected.png"));
	        ImageManager.SummonersRiftSelected = srs;
	        
	        kws = ImageIO.read(new File("Resources/Images/ClientPage/ModeSelected/KnifeWindSelected.png"));
	        ImageManager.KnifeWindSelected = kws;
	        
	        urfs = ImageIO.read(new File("Resources/Images/ClientPage/ModeSelected/URFSelected.png"));
	        ImageManager.URFSelected = urfs;
	        
	        sru = ImageIO.read(new File("Resources/Images/ClientPage/ModeUnSelected/SummonersRiftunSelected.png"));
	        ImageManager.SummonersRiftunSelected= ect.ice.RenderImageAsOpacity(sru,backG, 15);
	        
	        kwu = ImageIO.read(new File("Resources/Images/ClientPage/ModeUnSelected/KnifeWindunSelected.png"));
	        ImageManager.KnifeWindunSelected = ect.ice.RenderImageAsOpacity(kwu,backG, 15);
	        
	        urfu = ImageIO.read(new File("Resources/Images/ClientPage/ModeUnSelected/URFunSelected.png"));
	        ImageManager.URFunSelected = ect.ice.RenderImageAsOpacity(urfu, backG, 15);
					} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
