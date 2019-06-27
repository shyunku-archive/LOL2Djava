package Global;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
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
	public static final String ProgramVersion = "V0.9.0 - Beta";
	public static final long FrameCountPeriod = 850;
	public static final int FPSLimit = 144;
	
	public static enum GameMode {SummonersRift, KnifeWind, URF};
	
	public static boolean activateVideo = false;

	//Function
	public static final Functions ff = new Functions();
	
	//Server
	public static String publicIP = ""; 
	
	public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	
	//Layout
	public static Dimension ClientPanelDimension = new Dimension(1280, 720);
	public static Dimension FullScreenDimension;

	
}
