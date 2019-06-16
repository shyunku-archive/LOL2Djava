package Global;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import Core.Starter;
import Utility.EnginesControl;
import Utility.Layout;
import Utility.TriggeredButton;

public class Constants {	
	public static final String ProgramVersion = "V0.4.1 - Beta";
	public static final long FrameCountPeriod = 850;
	public static final int FPSLimit = 144;
	
	public static enum GameMode {SummonersRift, KnifeWind, URF};
	
	public static boolean activateVideo = false;

	//Function
	public static final Functions ff = new Functions();
	
	//Layout
	public static Dimension ClientPanelDimension = new Dimension(1280, 720);
	
	//Server
	public static final int DEFAULT_SERVER_PORT = 10426;
	public static final String EMPTY_STRING = "%%EMPTY%%";
	public static final String LOCAL_HOST_ADDRESS = "127.0.0.1";
	public static final String IS_GAME_HOST = "%%GAMEHOST%%";
	public static final String IS_GAME_CLIENT = "%%GAMECLIENT%%";
	public static final String PARTICIPATE = "%%PARTICIPATED%%";
	public static final String GO_OUT = "%%GOOUTFROMHERE%%";
	public static final String CHAT = "%%CHAT%%";
	public static final String SYSTEMIC = "%%SYSTEMIC%%";
	public static final String NON_SYSTEMIC = "%%NONSYSTEMIC%%";
	
	/*=================IMAGES=================*/
	//login
	public static BufferedImage LoginPageFrameImage = null;
	public static BufferedImage FocusedTerminateButtonImage = null;
	public static BufferedImage FocusedLocaleImage = null;
	public static BufferedImage ActivatedLoginButtonImage = null;
	public static BufferedImage FocusedLoginButtonImage = null;
	public static BufferedImage AuthentificationImage = null;
	
	//client
	public static BufferedImage ClientTemplateImage = null;
	public static BufferedImage FocusedGameStartButtonImage = null;
	public static BufferedImage GameModeSelectImage = null;
	public static BufferedImage FocusedGameSelectionCancelButtonImage = null;
	public static BufferedImage FocusedHomeButtonImage = null;
	public static BufferedImage FocusedGameCreateButtonImage = null;
	public static BufferedImage FocusedGameParticipateButtonImage = null;
	public static BufferedImage GameModeSelectAdditionImage = null;
	
	public static BufferedImage WaitingRoomImage = null;
	public static BufferedImage RealGameStartButtonImage = null;
	
	//select-unselect
	public static BufferedImage SummonersRiftSelected = null;
	public static BufferedImage KnifeWindSelected = null;
	public static BufferedImage URFSelected = null;
	
	public static BufferedImage SummonersRiftunSelected = null;
	public static BufferedImage KnifeWindunSelected = null;
	public static BufferedImage URFunSelected = null;
	
	public static BufferedImage SRicon = null;
	public static BufferedImage KWicon = null;
	public static BufferedImage URFicon = null;
	
	public static BufferedImage GameHostSymbol = null;
	
	
	/*=================SOUNDS=================*/
	//path
	public static String lightClickSoundFilePath;
	public static String GameStartButtonSoundFilePath;
	public static String LoginButtonPressedSoundPath;
	public static String ActivatedGameStartButtonSoundFilePath;
	public static String GameSelectionCancelSoundPath;
	
	public static String GameModeSelectSoundPath;
	public static String GameModeFocusSoundPath;
	
	public static String ActivatedCPSoundPath;
	public static String SelectedCPSoundPath;
	public static String ActivatedRealGameStartButtonSoundPath;
	public static String PressedRealGameStartButtonSoundPath;
	
	public static String ParticipateSoundPath;
	
	//Volume
	public static final double LIGHT_CLICK_SOUND_VOLUME = 2D;
	public static final double GAME_START_BUTTON_SOUND_VOLUME = 0.7D;
	public static final double GAME_SELECT_CANCEL_SOUND_VOLUME = 1.5D;
	public static final double DEFAULT_VOLUME = 1D;
	public static final String GameMode = null;
}
