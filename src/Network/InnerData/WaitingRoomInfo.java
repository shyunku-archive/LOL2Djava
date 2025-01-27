package Network.InnerData;

import java.util.ArrayList;
import java.util.Arrays;

import Global.Constants;
import Network.NetworkCore.NetworkTag;
import Network.Objects.Chat;
import Network.Objects.User;

public class WaitingRoomInfo extends MessageControl{
	private String RoomName, Password;
	private ArrayList<User> userList1, userList2;
	private ArrayList<Chat> chats;
	
	public String getRoomName() {
		return RoomName;
	}

	public void setRoomName(String roomName) {
		RoomName = roomName;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
	public WaitingRoomInfo(String Roomname, String Password) {
		this.RoomName = Roomname;
		this.Password = Password;
		userList1 = new ArrayList<>();
		userList2 = new ArrayList<>();
		chats = new ArrayList<>();
	}
	
	public ArrayList<User> getUserList(int index) {
		if(index == 1)return userList1;
		return userList2;
	}

	public void setUserList(ArrayList<User> userList, int index) {
		if(index == 1) this.userList1 = userList;
		else this.userList2 = userList;
	}
	
	public int getTeamOfUser(String username) {
		for(int i=0;i<userList1.size();i++)
			if(userList1.get(i).getUserName().equals(username))
				return 1;
		for(int i=0;i<userList2.size();i++)
			if(userList2.get(i).getUserName().equals(username))
				return 2;
		return 0;
	}
	
	public void addUser(User user) {
		if(userList1.size()==5)
			if(userList2.size()==5)return;
			else userList2.add(user);
		else userList1.add(user);
	}
	
	public void addChat(Chat chat) {
		this.chats.add(chat);
	}
	
	public void removeUser(String name) {
		for(int i=0;i<userList1.size();i++)
			if(userList1.get(i).getUserName().equals(name)) {
				userList1.remove(i);
				return;
			}
		for(int i=0;i<userList2.size();i++)
			if(userList2.get(i).getUserName().equals(name)) {
				userList2.remove(i);
				return;
			}
		//Constants.ff.printFatalError();
	}
	
	public ArrayList<Chat> getChats(){
		return this.chats;
	}

	@Override
	public String toMsg() {
		// TODO Auto-generated method stub
		String msg = "";
		msg += this.RoomName + TOKEN;
		msg += this.Password + TOKEN;
		msg += NetworkTag.USER_LIST_TAG1 +TOKEN;
		for(int i=0;i<userList1.size();i++)
			msg += userList1.get(i).toString() + TOKEN;
		msg += NetworkTag.USER_LIST_TAG2+TOKEN;
		for(int i=0;i<userList2.size();i++) {
			msg += userList2.get(i).toString() + TOKEN;
			if(i<userList2.size()-1)
				msg += TOKEN;
		}
		return msg;
	}

	@Override
	public void fromMsg(String[] seg) {
		// TODO Auto-generated method stub
		init();
		this.RoomName = seg[0];
		this.Password = seg[1];
		
		seg = Constants.ff.cutFrontStringArray(seg, 2);
		
		if(!seg[0].equals(NetworkTag.USER_LIST_TAG1)) Constants.ff.printFatalError();
		seg = Constants.ff.cutFrontStringArray(seg, 1);
		for(int i=0;i<5;i++) {
			if(seg[0].equals(NetworkTag.USER_LIST_TAG2))break;
			userList1.add(new User(seg[0], seg[1], seg[2]));
			seg = Constants.ff.cutFrontStringArray(seg, 3);
		}
		seg = Constants.ff.cutFrontStringArray(seg, 1);
		for(int i=0;i<seg.length;i++) {
			userList2.add(new User(seg[0], seg[1], seg[2]));
			seg = Constants.ff.cutFrontStringArray(seg, 3);
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		userList1.clear();
		userList2.clear();
		chats.clear();
	}

	@Override
	public void addItem(String[] seg) {
		// TODO Auto-generated method stub
		if(seg[0].equals(NetworkTag.USER_LIST_TAG)) {
			seg = Constants.ff.cutFrontStringArray(seg, 1);
			this.addUser(new User(seg));
		}else if(seg[0].equals(NetworkTag.CHAT_LOG_TAG)) {
			seg = Constants.ff.cutFrontStringArray(seg, 1);
			this.addChat(new Chat(seg));
		}
	}
	
	public void moveTeam(String username) {
		int index = 0;
		for(int i=0;i<userList1.size();i++) {
			if(userList1.get(i).getUserName().equals(username)) {
				userList2.add(userList1.get(i));
				userList1.remove(i);
				return;
			}
		}
		for(int i=0;i<userList2.size();i++)
			if(userList2.get(i).getUserName().equals(username)) {
				userList1.add(userList2.get(i));
				userList2.remove(i);
				return;
			}
	}

	@Override
	public void deleteItem(String[] seg) {
		// TODO Auto-generated method stub
		if(seg[0].equals(NetworkTag.USER_LIST_TAG)) {
			seg = Constants.ff.cutFrontStringArray(seg, 1);
			this.removeUser(seg[0]);
		}
	}

	@Override
	public void modifyItem(String[] seg) {
		// TODO Auto-generated method stub
		
	}
}
