package Global;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import Core.Starter;

public class Functions<Temp> {
	public void cprint(String Location, String Message) {
		System.out.println(Location+" : "+Message);
	}
	public void cprint(String Message) {
		System.out.println(" Message : "+ Message);
	}
	public void printPair(String Location, Temp...par){
		String buffer = Location+" : (";
		int paramCount = par.length;
		int curCount = 0;
		for(Temp t : par) {
			buffer += t.toString();
			if(curCount!= paramCount-1)
				buffer += ", ";
			curCount++;
		}
		buffer += ")";
		System.out.println(new Throwable().getStackTrace()[1].getMethodName()+"() says | "+ buffer);
	}
	
	public void printPair(Temp...par){
		String buffer = "Pair : (";
		int paramCount = par.length;
		int curCount = 0;
		for(Temp t : par) {
			buffer += t.toString();
			if(curCount!= paramCount-1)
				buffer += ", ";
			curCount++;
		}
		buffer += ")";
		System.out.println(buffer);
	}
	
	public void setFullScreen(JFrame jframe) {
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
		device.setFullScreenWindow(jframe);
	}
	
	public void printDebugFlag() {
		System.out.println("Flag Activated in "
				+new Throwable().getStackTrace()[3].getMethodName()+"."
				+new Throwable().getStackTrace()[2].getMethodName()+"."
				+new Throwable().getStackTrace()[1].getMethodName()+"()");
	}
	
	public Font getFancyFont(float size, boolean bold) {
		if(bold) return Starter.fancyFontBold.deriveFont(size);
		return Starter.fancyFont.deriveFont(size);
	}
	
	public Font getClassicFont(float size, boolean bold) {
		if(bold) Starter.classicFontBold.deriveFont(size);
		return Starter.classicFont.deriveFont(size);
	}
	
	public static void playSoundClip(String FilePath, double volume) {
		Clip c = null;
		try {
			c = AudioSystem.getClip();
			c.open(AudioSystem.getAudioInputStream(new File(FilePath).getAbsoluteFile()));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.start();
	}
	
	public static void setDeciBel(Clip c, double gain) {
		FloatControl fc = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        fc.setValue(dB);
	}
	
	public static void setCursor(int CURSOR) {
		Starter.frame.setCursor(Cursor.getPredefinedCursor(CURSOR));
	}
}
