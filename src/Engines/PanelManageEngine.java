package Engines;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;

import Core.Starter;
import Global.Constants;
import Global.Functions;
import Global.Constants.GameMode;
import Network.NetworkCore.GameClient;
import Network.NetworkCore.GameServer;
import Panels.NormalChampionSelectPage;
import Panels.ClientGameModeSelectPage;
import Panels.ClientPage;
import Panels.WaitingRoom;
import Panels.LogPanel;
import Panels.LoginPage;

public class PanelManageEngine {
	public static LogPanel logPage;
	
	public static LoginPage loginPage;
	public static ClientPage clientPage;
	public static ClientGameModeSelectPage clientGameModeSelectPage;
	public static WaitingRoom waitingPage;
	public static NormalChampionSelectPage normalChampionSelectPage;
	
	/*===============================CREATE PANELS===============================*/
	
	public void LogPage() {
		Starter.frame.add(logPage);
	}
	
	public void goLoginPage() {
		ff.setCursor(Cursor.DEFAULT_CURSOR);
		
		loginPage.isActivated = true;
		Starter.frame.add(loginPage);
		Starter.frame.setSize(loginPage.PanelSize);
		
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
		clientGameModeSelectPage.setThis();
		Starter.frame.add(clientGameModeSelectPage);
		Starter.frame.setSize(clientGameModeSelectPage.PanelSize);
		
		logPage.setPanelSize(new Dimension(Starter.frame.getWidth(), Starter.frame.getHeight()));
	}
	
	public void GoWaitingPage(GameMode mode, String Roomname, String password, boolean isGameHost) {
		waitingPage = new WaitingRoom(mode, Roomname, password, isGameHost);
		ff.setCursor(Cursor.DEFAULT_CURSOR);
		
		waitingPage.setThis();
		
		waitingPage.isActivated = true;
		Starter.frame.add(waitingPage);
		Starter.frame.setSize(waitingPage.PanelSize);
		
		logPage.setPanelSize(new Dimension(Starter.frame.getWidth(), Starter.frame.getHeight()));
	}
	
	public void GoChampionSelectPage(GameMode mode, boolean isGameMaster, GameServer gameServer, GameClient gameClient) {
		normalChampionSelectPage = new NormalChampionSelectPage(mode, isGameMaster, gameServer, gameClient);
		ff.setCursor(Cursor.DEFAULT_CURSOR);
		
		normalChampionSelectPage.setThis();
		
		normalChampionSelectPage.isActivated = true;
		Starter.frame.add(normalChampionSelectPage);
		Starter.frame.setSize(normalChampionSelectPage.PanelSize);
		
		logPage.setPanelSize(new Dimension(Starter.frame.getWidth(), Starter.frame.getHeight()));
	}
	
	/*===============================DELETE PANELS===============================*/
	
	public void exitLoginPage() {
		deactivateAll();
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
	
	public void exitWaitingPage() {
		deactivateAll();
		Starter.frame.remove(waitingPage);
	}
	
	public void exitChampionSelectPage() {
		deactivateAll();
		Starter.frame.remove(normalChampionSelectPage);
	}
	
	/*===============================INTERNAL METHODS===============================*/
	
	public void setAll() {
		logPage = new LogPanel();
		loginPage = new LoginPage();
		clientPage = new ClientPage();
		clientGameModeSelectPage = new ClientGameModeSelectPage();
		//유동적 페이지는 적지 않음
	}
	
	public static void updateActivated() {
		if(loginPage.isActivated)loginPage.update();
		if(clientPage.isActivated)clientPage.update();
		if(clientGameModeSelectPage.isActivated)clientGameModeSelectPage.update();
		if(waitingPage.isActivated)waitingPage.update();
		if(normalChampionSelectPage.isActivated)normalChampionSelectPage.update();
	}
	
	public static void deactivateAll() {
		loginPage.isActivated = false;
		clientPage.isActivated = false;
		clientGameModeSelectPage.isActivated = false;
		waitingPage.isActivated = false;
		normalChampionSelectPage.isActivated = false;
	}
	
	private static Functions ff = new Functions();
}
