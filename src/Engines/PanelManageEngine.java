package Engines;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;

import Core.Starter;
import Global.Constants;
import Global.Functions;
import Panels.ClientGameModeSelectPage;
import Panels.ClientPage;
import Panels.LogPanel;
import Panels.LoginPage;

public class PanelManageEngine {
	public static LogPanel logPage;
	
	public static LoginPage loginPage;
	public static ClientPage clientPage;
	public static ClientGameModeSelectPage clientGameModeSelectPage;
	
	/*===============================CREATE PANELS===============================*/
	
	public void LogPage() {
		Starter.frame.add(logPage);
	}
	
	public void goLoginPage() {
		ff.setCursor(Cursor.DEFAULT_CURSOR);
		
		loginPage.isActivated = true;
		Starter.frame.add(loginPage);
		Starter.frame.setSize(loginPage.PanelSize);
		
		loginPage.playMedia();
		loginPage.setThis();
		
		logPage.setPanelSize(new Dimension(Starter.frame.getWidth(), Starter.frame.getHeight()));
		Dimension frameSize = Starter.frame.getSize();
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    Starter.frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}
	
	public void goClientPage() {
		ff.setCursor(Cursor.DEFAULT_CURSOR);
		
		clientPage.isActivated = true;
		Starter.frame.add(clientPage);
		Starter.frame.setSize(clientPage.PanelSize);
		
		logPage.setPanelSize(new Dimension(Starter.frame.getWidth(), Starter.frame.getHeight()));
	}
	
	public void GoClientGameModeSelectPage() {
		ff.setCursor(Cursor.DEFAULT_CURSOR);
		
		clientGameModeSelectPage.isActivated = true;
		Starter.frame.add(clientGameModeSelectPage);
		Starter.frame.setSize(clientGameModeSelectPage.PanelSize);
		
		logPage.setPanelSize(new Dimension(Starter.frame.getWidth(), Starter.frame.getHeight()));
	}
	
	/*===============================DELETE PANELS===============================*/
	
	public void exitLoginPage() {
		deactivateAll();
		loginPage.endMedia();
		Starter.frame.remove(loginPage);
	}
	
	public void exitClientPage() {
		deactivateAll();
		Starter.frame.remove(clientPage);
	}
	
	public void exitClientGameModeSelectPage() {
		deactivateAll();
		Starter.frame.remove(clientGameModeSelectPage);
	}
	
	/*===============================INTERNAL METHODS===============================*/
	
	public void setAll() {
		logPage = new LogPanel();
		loginPage = new LoginPage();
		clientPage = new ClientPage();
		clientGameModeSelectPage = new ClientGameModeSelectPage();
	}
	
	
	public static void updateActivated() {
		if(loginPage.isActivated)loginPage.update();
		if(clientPage.isActivated)clientPage.update();
		if(clientGameModeSelectPage.isActivated)clientGameModeSelectPage.update();
	}
	
	public static void deactivateAll() {
		loginPage.isActivated = false;
		clientPage.isActivated = false;
		clientGameModeSelectPage.isActivated = false;
	}
	
	private static Functions ff = new Functions();
}
