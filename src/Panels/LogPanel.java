package Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import Core.Starter;
import Global.Constants;
import Global.Functions;
import Global.Variables;
import Utility.Coordinate;
import Utility.EnginesControl;

public class LogPanel extends JPanel{
	//Necessary
	public static Dimension PanelSize;
	public static boolean isActivated;
	
	//Utility
	private static Functions ff = new Functions();
	private static EnginesControl ect = new EnginesControl();
	
	private ArrayList<String> logs = new ArrayList<>();
	String str = "";
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		update();
		if(!Variables.ShowDetails)return;
		g.setFont(ff.getFancyFont(15F, true));
		if(Starter.pme.loginPage.isActivated) {
			for(int i=0;i<logs.size();i++)
				ect.fde.advancedDrawRightAlignedString(g, logs.get(i)+"", new Coordinate(15, 325+20*i), this.PanelSize, new Color(190,190,190,255), Color.BLACK, 1);
		}else if(Starter.pme.clientPage.isActivated) {
			for(int i=0;i<logs.size();i++)
				ect.fde.advancedDrawRightAlignedString(g, logs.get(i)+"", new Coordinate(235, 100+20*i), this.PanelSize, new Color(190,190,190,255), Color.BLACK, 1);
		}
		else if(Starter.pme.clientGameModeSelectPage.isActivated) {
			for(int i=0;i<logs.size();i++)
				ect.fde.advancedDrawRightAlignedString(g, logs.get(i)+"", new Coordinate(15, 100+20*i), this.PanelSize, new Color(190,190,190,255), Color.BLACK, 1);
		}
	}
	
	public void update() {
		logs.clear();
		addLog("FPS", (int)Variables.FPS+"");
		addLog("Mouse Position", Variables.mousePos.getPosByString());
		addLog("Elapsed Time", String.format("%.3f sec", ((double)Variables.ElapsedTime)/1000));
		Random r = new Random();
		
		if(Variables.TotalFrameCount % 100 == 0) {
			str = "";
			for(int i=0;i<10;i++)
				str += ""+(char)(r.nextInt()%33+90);
		}
		addLog("Fancy", str);
		
	}
	
	public void addLog(String title, String message) {
		logs.add(title+" : "+message);
	}
	
	public void addLine() {
		logs.add("");
	}
	
	public void addMessage(String message) {
		logs.add(message);
	}
	
	public LogPanel() {
		setFocusable(false);
		setVisible(true);
		
		this.setLayout(null);
		this.setBackground(new Color(0,0,0));
	}
	
	public void setPanelSize(Dimension ps) {
		this.setSize(ps);
		this.PanelSize = ps;
	}
}
