package Network.Objects;

import Global.Constants;
import Network.NetworkCore.NetworkTag;
import Objects.Champion;

public class User extends ObjectManage{
	private String userName;
	private String ip;
	private boolean isGameHost;
	
	private int selectedChampionCode;
	private boolean isSelecting;
	private boolean isPicked;
	
	public int getSelectedChampionCode() {
		return selectedChampionCode;
	}

	public void setSelectedChampionCode(int selectedChampionCode) {
		this.selectedChampionCode = selectedChampionCode;
	}

	public boolean isSelecting() {
		return isSelecting;
	}

	public void setSelecting(boolean isSelecting) {
		this.isSelecting = isSelecting;
	}

	public boolean isPicked() {
		return isPicked;
	}

	public void setPicked(boolean isPicked) {
		this.isPicked = isPicked;
	}

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
		if(seg.length==3)return;
		this.selectedChampionCode = Integer.parseInt(seg[3]);
		this.isSelecting = seg[4].equals(NetworkTag.SELECTING_CHAMP);
		this.isPicked = seg[5].equals(NetworkTag.CHAMPION_PICKED);
	}
	
	public void init() {
		selectedChampionCode = 0;
		isSelecting = false;
		isPicked = false;
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
	
	public String toSelectedString() {
		return this.toString() + "|" + this.selectedChampionCode + "|" + (this.isSelecting?NetworkTag.SELECTING_CHAMP:NetworkTag.NOT_SELECTING_CHAMP)
				+"|"+(this.isPicked?NetworkTag.CHAMPION_PICKED:NetworkTag.NOT_CHAMPION_PICKED);
	}
}
