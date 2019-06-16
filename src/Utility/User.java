package Utility;

import Global.Constants;

public class User {
	private String userName;
	private String ip;
	private boolean isGameHost;
	
	public User(String userName, String ip, String GameHostTag) {
		this.userName = userName;
		this.ip = ip;
		this.setGameHost(GameHostTag.compareTo(Constants.IS_GAME_HOST)==0);
	}
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isGameHost() {
		return isGameHost;
	}
	public void setGameHost(boolean isGameHost) {
		this.isGameHost = isGameHost;
	}
	
	public String toString() {
		return this.userName+"|"+this.ip+"|"+(isGameHost?Constants.IS_GAME_HOST:Constants.IS_GAME_CLIENT);
	}
}
