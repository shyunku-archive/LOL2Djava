package Network.InnerData;

import java.util.ArrayList;

import Global.Constants;
import Network.NetworkCore.NetworkTag;
import Network.Objects.Chat;
import Network.Objects.User;

public class NormalChampionSelectingRoomInfo extends MessageControl {
	private final int FinalPhaseIndex = 1;
	
	private int WaitingPhaseIndex;
	private long curRemainWaitTimeFlag;
	
	private ArrayList<User> userList1, userList2;
	private ArrayList<Chat> chats1, chats2;
	
	
	public NormalChampionSelectingRoomInfo() {
		init();
	}
	
	public NormalChampionSelectingRoomInfo(ArrayList<User> u1, ArrayList<User> u2, ArrayList<Chat> chats) {
		this.userList1 = u1;
		this.userList2 = u2;
		this.chats1 = chats;
		this.chats2 = chats;
		this.WaitingPhaseIndex = 0;
		this.curRemainWaitTimeFlag = System.currentTimeMillis();
	}

	public ArrayList<Chat> getOurTeamChat(String myName){
		for(int i=0;i<userList1.size();i++)
			if(myName.equals(userList1.get(i).getUserName()))
				return chats1;
		for(int i=0;i<userList2.size();i++)
			if(myName.equals(userList2.get(i).getUserName()))
				return chats2;
		return null;
	}
	
	public User getMe(String myName) {
		for(int i=0;i<userList1.size();i++)
			if(myName.equals(userList1.get(i).getUserName()))
				return userList1.get(i);
		for(int i=0;i<userList2.size();i++)
			if(myName.equals(userList2.get(i).getUserName()))
				return userList2.get(i);
		return null;
	}
	
	public int getOurTeamIndex(String myName){
		for(int i=0;i<userList1.size();i++)
			if(myName.equals(userList1.get(i).getUserName()))
				return 1;
		for(int i=0;i<userList2.size();i++)
			if(myName.equals(userList2.get(i).getUserName()))
				return 2;
		return 0;
	}
	
	public void userSelectedChampion(String userName, int code) {
		for(int i=0;i<userList1.size();i++)
			if(userName.equals(userList1.get(i).getUserName())) {
				userList1.get(i).setSelectedChampionCode(code);
				return;
			}
		for(int i=0;i<userList2.size();i++)
			if(userName.equals(userList2.get(i).getUserName())) {
				userList2.get(i).setSelectedChampionCode(code);
				return;
			}
	}
	
	public void userPicked(String userName) {
		for(int i=0;i<userList1.size();i++)
			if(userName.equals(userList1.get(i).getUserName())) {
				userList1.get(i).setPicked(true);
				userList1.get(i).setSelecting(false);
				return;
			}
		for(int i=0;i<userList2.size();i++)
			if(userName.equals(userList2.get(i).getUserName())) {
				userList2.get(i).setPicked(true);
				userList2.get(i).setSelecting(false);
				return;
			}
	}
	
	public void nextPhase() {
		this.WaitingPhaseIndex++;
		if(WaitingPhaseIndex >= FinalPhaseIndex)
			this.curRemainWaitTimeFlag = System.currentTimeMillis();
	}
	
	public void executeNormalPhase() {
		for(int i=0;i<userList1.size();i++)
			userList1.get(i).setSelecting(true);
		for(int i=0;i<userList2.size();i++)
			userList2.get(i).setSelecting(true);
	}
	
	public boolean isAllPicked() {
		for(int i=0;i<userList1.size();i++)
			if(!userList1.get(i).isPicked())
				return false;
		for(int i=0;i<userList2.size();i++)
			if(!userList2.get(i).isPicked())
				return false;
		return true;
	}
	
	public void addChat(Chat chat, String Username) {
		int ind = getOurTeamIndex(Username);
		if(ind == 1)
			chats1.add(chat);
		if(ind == 2)
			chats2.add(chat);
	}
	
	@Override
	public String toMsg() {
		// TODO Auto-generated method stub
		String msg = "";
		msg += this.WaitingPhaseIndex + TOKEN;
		msg += this.curRemainWaitTimeFlag + TOKEN;
		msg += NetworkTag.USER_LIST_TAG1 +TOKEN;
		for(int i=0;i<userList1.size();i++)
			msg += userList1.get(i).toSelectedString() + TOKEN;
		msg += NetworkTag.USER_LIST_TAG2+TOKEN;
		for(int i=0;i<userList2.size();i++)
			msg += userList2.get(i).toSelectedString() + TOKEN;
		msg += NetworkTag.CHAT_LOG_TAG+TOKEN;
		for(int i=0;i<chats1.size();i++)
			msg += chats1.get(i).toString() + TOKEN;
		msg += NetworkTag.CHAT_LOG_TAG+TOKEN;
		for(int i=0;i<chats2.size();i++) {
			msg += chats2.get(i).toString();
			if(i<chats2.size()-1)
				msg += TOKEN;
		}
		return msg;
	}
	
	@Override
	public void fromMsg(String[] seg) {
		// TODO Auto-generated method stub
		init();
		this.WaitingPhaseIndex = Integer.parseInt(seg[0]);
		this.curRemainWaitTimeFlag = Long.parseLong(seg[1]);
		
		seg = Constants.ff.cutFrontStringArray(seg, 2);
		
		if(!seg[0].equals(NetworkTag.USER_LIST_TAG1)) Constants.ff.printFatalError();
		seg = Constants.ff.cutFrontStringArray(seg, 1);
		for(int i=0;i<5;i++) {
			if(seg[0].equals(NetworkTag.USER_LIST_TAG2))break;
			userList1.add(new User(Constants.ff.subArray(seg, 0, 5)));
			seg = Constants.ff.cutFrontStringArray(seg, 6);
		}
		seg = Constants.ff.cutFrontStringArray(seg, 1);
		for(int i=0;i<5;i++) {
			if(seg[0].equals(NetworkTag.CHAT_LOG_TAG))break;
			userList2.add(new User(Constants.ff.subArray(seg, 0, 5)));
			seg = Constants.ff.cutFrontStringArray(seg, 6);
		}
		seg = Constants.ff.cutFrontStringArray(seg, 1);
		while(true) {
			if(seg[0].equals(NetworkTag.CHAT_LOG_TAG))break;
			chats1.add(new Chat(seg[0],seg[1],seg[2]));
			seg = Constants.ff.cutFrontStringArray(seg, 3);
		}
		seg = Constants.ff.cutFrontStringArray(seg, 1);
		int remain = seg.length/3;
		for(int i=0;i<remain;i++) {
			chats2.add(new Chat(seg[0],seg[1],seg[2]));
			seg = Constants.ff.cutFrontStringArray(seg, 3);
		}
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		userList1 = new ArrayList<>();
		userList2 = new ArrayList<>();
		chats1 = new ArrayList<>();
		chats2 = new ArrayList<>();
	}
	@Override
	public void addItem(String[] seg) {
		// TODO Auto-generated method stub
		if(seg[0].equals(NetworkTag.CHAT_LOG_TAG)) {
			seg = Constants.ff.cutFrontStringArray(seg, 1);
			this.addChat(new Chat(seg), seg[0]);
		}
	}
	@Override
	public void deleteItem(String[] seg) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modifyItem(String[] seg) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public int getWaitingPhaseIndex() {
		return WaitingPhaseIndex;
	}
	public void setWaitingPhaseIndex(int waitingPhaseIndex) {
		WaitingPhaseIndex = waitingPhaseIndex;
	}
	public long getCurReaminWaitTime() {
		long elapsed = System.currentTimeMillis() - this.curRemainWaitTimeFlag;
		if(WaitingPhaseIndex == FinalPhaseIndex)
			return NetworkTag.FINAL_WAITING_TIME - elapsed;
		return NetworkTag.NORMAL_CHAMPION_SELECT_TIME - elapsed;
	}
	public void setCurReaminWaitTime(long curReaminWaitTime) {
		this.curRemainWaitTimeFlag = curReaminWaitTime;
	}
	public ArrayList<User> getUserList1() {
		return userList1;
	}
	public ArrayList<User> getUserList2() {
		return userList2;
	}
	public ArrayList<Chat> getChats1() {
		return chats1;
	}
	public void setChats1(ArrayList<Chat> chats1) {
		this.chats1 = chats1;
	}
	public ArrayList<Chat> getChats2() {
		return chats2;
	}
	public void setChats2(ArrayList<Chat> chats2) {
		this.chats2 = chats2;
	}

}
