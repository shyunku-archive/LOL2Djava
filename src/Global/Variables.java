package Global;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import Objects.Coordinate;

public class Variables {
	public static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	public static int FrameCount = 0;
	public static int TotalFrameCount = 0;
	public static Coordinate mousePos = new Coordinate(0,0); 
	public static long ElapsedTime = 0;
	public static long ElapsedTimeFlag = System.currentTimeMillis();
	public static long StopWatchFlag = System.currentTimeMillis();
	public static double FPS = 144;
	public static long ping = 0;
	
	public static boolean ShowDetails = true;
	
	public static int Phase = 0;
	
	public static int LogShowingPhase = 0;
	
	public static Coordinate MouseDragFlag = new Coordinate(0,0);
	
	/*=================IMAGES=================*/
	public static String Username = "Default Username";
}
