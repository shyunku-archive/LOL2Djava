package Network.NetworkCore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JOptionPane;

import Global.Constants;
import Global.SoundManager;
import Network.InnerData.NormalChampionSelectingRoomInfo;
import Network.InnerData.WaitingRoomInfo;
import Network.Objects.User;

public class GameClient {
	private Socket socket;
	private PrintWriter writer;
	private User user;
	
	private WaitingRoomInfo RoomInfo = new WaitingRoomInfo("","");
	private NormalChampionSelectingRoomInfo  selectChampRoomInfo = new NormalChampionSelectingRoomInfo();
	
	
	private long updates = 0;
	public long ping = 0;
	private boolean nextPhaseSignal = false;
	private HashMap<Long, Long> pingDiff = new HashMap<Long, Long>();
	private long pingIndex = 0;
	
	private ArrayList<Long> sendPingFlags = new ArrayList<>();
	
	public boolean isNextPhaseSignalActiavted() {
		if(nextPhaseSignal) {
			nextPhaseSignal = false;
			return true;
		}
		return false;
	}
	
	public boolean isUpdated() {
		long saved = updates;
		updates = 0;
		return saved > 0;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public WaitingRoomInfo getRoomInfo() {
		return this.RoomInfo;
	}
	
	public NormalChampionSelectingRoomInfo getSelectRoomInfo() {
		return this.selectChampRoomInfo;
	}
	
	public void connect(User user, String IP, String password) {
		socket = new Socket();
		
		this.user = user;
		try {
			socket.connect(new InetSocketAddress(IP, NetworkTag.DEFAULT_SERVER_PORT));
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			System.out.println("CLIENT: CONNECT SUCCESS");
			
			if(password.equals(""))password = NetworkTag.EMPTY_STRING;
			sendMessageToServer(NetworkTag.PARTICIPATE+"|"+user.toString()+"|"+password);
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//CLIENT <- SERVER
			new Thread(new Runnable() {

				@Override
				public void run() {
					String response = null;
					try {
						while(true){
							 response = reader.readLine();
							 updates++;
							
							 String[] tokens = response.split("\\|");
							 
							 String tag = tokens[0];
							 String[] msg = Arrays.copyOfRange(tokens, 1, tokens.length);
							 
							 if(tag.equals(NetworkTag.PING_TEST)) {
								 synchronized(pingDiff) {
									 HashMap<Long, Long> temp = pingDiff;
									 long index = Long.parseLong(msg[0]);
									 Set<Long> keys = temp.keySet();
									 for(Long key : keys)
										 if(key == index) {
											 ping = System.nanoTime() - temp.get(key);
											 break;
										 }
								 }
							 }else Constants.ff.cprint("CLIENT <- SERVER : "+response);
							 
							 if(tag.equals(NetworkTag.WAITING_ROOM)) {
								 if(msg[0].equals(NetworkTag.UPDATE_SIGNAL)) {
									 if(msg[1].equals(NetworkTag.ITEM_ADDITION)) {
										 if(msg[2].equals(NetworkTag.USER_LIST_TAG))
											 Constants.ff.playSoundClip(SoundManager.ParticipateSoundPath, SoundManager.HIGHER_VOLUME);
										 RoomInfo.addItem(Constants.ff.cutFrontStringArray(msg, 2));
									 }
									 else if(msg[1].equals(NetworkTag.ITEM_DELETION))
										 RoomInfo.deleteItem(Constants.ff.cutFrontStringArray(msg, 2));
									 else if(msg[1].equals(NetworkTag.ITEM_MODIFICATION))
										 RoomInfo.modifyItem(Constants.ff.cutFrontStringArray(msg, 2));
								 }else if(msg[0].equals(NetworkTag.UPDATE_ALL)) {
									 RoomInfo.fromMsg(Constants.ff.cutFrontStringArray(msg, 1));
								 }else if(msg[0].equals(NetworkTag.PASSWORD_NOT_CORRECT)) {
									 JOptionPane.showMessageDialog(null, "패스워드가 틀렸습니다.");
								     System.exit(0);
								 }else if(msg[0].equals(NetworkTag.MOVE_TEAM_SIGNAL)) {
									 RoomInfo.moveTeam(msg[1]);
								 }
							 }else if(tag.equals(NetworkTag.NORMAL_CHAMP_SELECT_ROOM)) {
								 if(msg[0].equals(NetworkTag.SELECT_START)) {
									 nextPhaseSignal = true;
								 }else if(msg[0].equals(NetworkTag.UPDATE_ALL)) {
									 selectChampRoomInfo.fromMsg(Constants.ff.cutFrontStringArray(msg, 1));
									 selectChampRoomInfo.executeNormalPhase();
								 }else if(msg[0].equals(NetworkTag.UPDATE_SIGNAL)) {
									 if(msg[1].equals(NetworkTag.ITEM_ADDITION)) {
										 selectChampRoomInfo.addItem(Constants.ff.cutFrontStringArray(msg, 2));
									 }
									 else if(msg[1].equals(NetworkTag.ITEM_DELETION))
										 selectChampRoomInfo.deleteItem(Constants.ff.cutFrontStringArray(msg, 2));
									 else if(msg[1].equals(NetworkTag.ITEM_MODIFICATION))
										 selectChampRoomInfo.modifyItem(Constants.ff.cutFrontStringArray(msg, 2));
								 }else if(msg[0].equals(NetworkTag.CHAMP_SELECT_SIGNAL)) {
									 selectChampRoomInfo.userSelectedChampion(msg[1], Integer.parseInt(msg[2]));
								 }else if(msg[0].equals(NetworkTag.CHAMP_PICK_SIGNAL)) {
									 selectChampRoomInfo.userPicked(msg[1]);
								 }else if(msg[0].equals(NetworkTag.NEXT_PHASE)) {
									 selectChampRoomInfo.nextPhase();
								 }
							 }else if(tag.equals(NetworkTag.GAMING)) {
								 if(msg[0].equals(NetworkTag.REAL_GAME_START_SIGNAL)) {
									 nextPhaseSignal = true;
								 }
							 }
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						reader.close();
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}).start();
			
			//주기적으로 CLIENT에 데이터 전송 : 핑 테스트
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true) {
						try {
							Thread.sleep(1000);
							synchronized(pingDiff) {
								sendMessageToServer(NetworkTag.PING_TEST+"|"+pingIndex);
								pingDiff.put(pingIndex, System.nanoTime());
								pingIndex++;
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "해당 ip에 접속할 수 없습니다.");
			e.printStackTrace();
			return;
			
		}
	}
	
	public void sendMessageToServer(String msg){
		//클라이언트 -> 서버
		String[] tokens = msg.split("\\|");
		msg = user.getUserName()+"|" +msg;
		if(!tokens[0].equals(NetworkTag.PING_TEST))
			Constants.ff.cprint("CLIENT -> SERVER : "+msg);
		writer.println(msg);
		writer.flush();
	}
}
