package Network.Objects;

import Global.Constants;
import Network.NetworkCore.NetworkTag;

public class User extends ObjectManage{
	private String userName;
	private String ip;
	private boolean isGameHost;
	
	public User(String userName, String ip, boolean isGameHost) {
		this.userName = userName;
		this.ip = ip;
		this.isGameHost = isGameHost;
	}
	
	public User(String userName, String ip, String isGameHost) {
		this.userName = userName;
		this.ip = ip;
		this.isGameHost = isGameHost.equals(NetworkTag.IS_GAME_HOST);
	}
	
	public User(String[] seg) {
		this.userName = seg[0];
		this.ip = seg[1];
		this.isGameHost = seg[2].equals(NetworkTag.IS_GAME_HOST);
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
	
	@Override
	public String toString() {
		return this.userName+"|"+this.ip+"|"+(isGameHost?NetworkTag.IS_GAME_HOST:NetworkTag.IS_GAME_CLIENT);
	}
}
