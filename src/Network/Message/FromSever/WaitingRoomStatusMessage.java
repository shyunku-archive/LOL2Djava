package Network.Message.FromSever;

import java.util.ArrayList;

import Global.Constants;
import Network.Message.NetworkMessage;
import Utility.User;


public class WaitingRoomStatusMessage extends NetworkMessage {
	public static final String SAME_USERNAME_EXIST = "%%SAMENAME%%";
	public static final String ROOM_IS_FULL = "%%FULLROOM";
	
	private ArrayList<User> userList1, userList2;
	public ArrayList<User> getUserList(int index) {
		if(index == 1)return userList1;
		return userList2;
	}

	public void setUserList(ArrayList<User> userList, int index) {
		if(index == 1) this.userList1 = userList;
		else this.userList2 = userList;
	}

	public WaitingRoomStatusMessage() {
		userList1 = new ArrayList<>();
		userList2 = new ArrayList<>();
	}

	@Override
	public void fromMsg(String[] seg) {
		int userCount = (seg.length - 1) / 3;
		for(int i=0;i<5;i++)
			userList1.add(new User(seg[1+3*i], seg[2+3*i], seg[3+3*i]));
		for(int i=0;i<5;i++)
			userList1.add(new User(seg[16+3*i], seg[17+3*i], seg[18+3*i]));
	}

	@Override
	public String toMsg() {
		String msg = NetworkMessage.WAITING_ROOM + "|";
		for (int i = 0; i < userList1.size(); i++) {
			msg += userList1.get(i).getUserName() + "|";
			msg += userList1.get(i).getIp() + "|";
			msg += userList1.get(i).isGameHost()?Constants.IS_GAME_HOST:Constants.IS_GAME_CLIENT + "|";
		}
		for (int i = userList1.size(); i < 5; i++) {
			msg += Constants.EMPTY_STRING + "|";
			msg += Constants.EMPTY_STRING + "|";
			msg += Constants.EMPTY_STRING + "|";
		}
		for (int i = 0; i < userList2.size(); i++) {
			msg += userList2.get(i).getUserName() + "|";
			msg += userList2.get(i).getIp() + "|";
			msg += userList2.get(i).isGameHost()?Constants.IS_GAME_HOST:Constants.IS_GAME_CLIENT + "|";
		}
		for (int i = userList2.size(); i < 5; i++) {
			msg += Constants.EMPTY_STRING + "|";
			msg += Constants.EMPTY_STRING + "|";
			msg += Constants.EMPTY_STRING;
			if (i != 4) {
				msg += "|";
			}
		}
		return msg;
	}
}
