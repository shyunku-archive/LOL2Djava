package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Global.Constants;
import Network.Message.FromSever.WaitingRoomStatusMessage;
import Utility.Chat;
import Utility.User;

public class GameServer {
	public static final int WAITING_ROOM = 0;
	public static final int CHAMPION_SELECTING = 1;
	public static final int GAME_LOADING = 2;
	public static final int GAMING = 3;
	public static final int GAME_RESULT = 4;
	
	
	
	private ServerSocket serverSocket;
	private OnServerMessageListener listener;
	private ArrayList<User> userList1, userList2;
	private ArrayList<Chat> chatLog;
	
	private int currentStatus = WAITING_ROOM;
	
	public HashMap<String, PrintWriter> writermap;
	
	
	public GameServer() {
		userList1 = new ArrayList<>();
		userList2 = new ArrayList<>();
		chatLog = new ArrayList<>();
		startServer();
	}
	
	public GameServer(OnServerMessageListener listener) {
		userList1 = new ArrayList<>();
		userList2 = new ArrayList<>();
		chatLog = new ArrayList<>();
		this.listener = listener;
	}
	
	public void addChat(String sender, String str, boolean system) {
		chatLog.add(new Chat(sender, str, system));
	}
	
	public ArrayList<Chat> getChats(){
		return this.chatLog;
	}

	public ArrayList<User> getUserList(int index){
		if(index == 1) return this.userList1;
		return this.userList2;
	}
	
	public ArrayList<User> getUserList2(){
		return this.userList2;
	}
	
	public void endServer() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public OnServerMessageListener getListener() {
		return listener;
	}

	public void setListener(OnServerMessageListener listener) {
		this.listener = listener;
	}

	public static interface OnServerMessageListener {
		public abstract void onServerMessage(String userName, String msg);
	}
	
	public void startServer() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					serverSocket = new ServerSocket(Global.Constants.DEFAULT_SERVER_PORT);
					writermap = new HashMap<String, PrintWriter>();
					while (true) {
						// wait for accept
						Socket socket = serverSocket.accept();
						GameThread thread = new GameThread(socket, writermap);
						thread.start();
					}
				} catch(BindException e) {
					JOptionPane.showMessageDialog(null, "서버가 이미 실행되고 있습니다.");
					System.exit(0);
				} catch (Exception e) {
					System.out.println("서버 닫음");
				}

			}
		}).start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (;;) {
					try {
						Thread.sleep(500);
						if (currentStatus == WAITING_ROOM) {
							WaitingRoomStatusMessage msg = new WaitingRoomStatusMessage();
							msg.setUserList(userList1, 1);
							msg.setUserList(userList2, 2);
							msg.setChats(chatLog);
							broadcast(msg.toMsg());
						} else {
							break;
						}
					} catch (Exception e) {

					}
				}
			}

		}).start();
	}
	
	public void sendMessage(String userName, String message) {
		try {
			synchronized (writermap) {
				writermap.get(userName).println(message);
				writermap.get(userName).flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void broadcast(String message) {
		try {
			synchronized (writermap) {
				Collection<PrintWriter> allUsers = writermap.values();
				Iterator<PrintWriter> i = allUsers.iterator();
				while (i.hasNext()) {
					PrintWriter p = i.next();
					p.println(message);
					p.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class GameThread extends Thread {
		private Socket serv;
		private String userName;
		private String isGameHost;
		private HashMap<String, PrintWriter> writermap;
		private BufferedReader reader;

		public GameThread(Socket serv, HashMap<String, PrintWriter> writermap) {
			boolean valid = true;
			this.serv = serv;
			this.writermap = writermap;
			try {
				reader = new BufferedReader(new InputStreamReader(serv.getInputStream()));
				String statusTeller = reader.readLine();
				System.out.println("Server Recieved : "+statusTeller);
				if(statusTeller.compareTo(Constants.PARTICIPATE)==0) {
					userName = reader.readLine();
					isGameHost = reader.readLine();
					System.out.println("content : "+userName+" 방에 참여함. host = "+isGameHost);
					if ((userList1.size()==5)&&(userList2.size()==5)) {
						PrintWriter temp = new PrintWriter(new OutputStreamWriter(serv.getOutputStream()));
						temp.println(WaitingRoomStatusMessage.ROOM_IS_FULL);
						temp.flush();
						temp.close();
						serv.close();
						valid = false;
						this.interrupt();
					}
					for (int i = 0; i < userList1.size(); i++) {
						User user = userList1.get(i);
						if (userName.equals(user.getUserName())) {
							PrintWriter temp = new PrintWriter(new OutputStreamWriter(serv.getOutputStream()));
							temp.println(WaitingRoomStatusMessage.SAME_USERNAME_EXIST);
							temp.flush();
							temp.close();
							serv.close();
							valid = false;
							this.interrupt();
						}
					}
					for (int i = 0; i < userList2.size(); i++) {
						User user = userList2.get(i);
						if (userName.equals(user.getUserName())) {
							PrintWriter temp = new PrintWriter(new OutputStreamWriter(serv.getOutputStream()));
							temp.println(WaitingRoomStatusMessage.SAME_USERNAME_EXIST);
							temp.flush();
							temp.close();
							serv.close();
							valid = false;
							this.interrupt();
						}
					}
					if (valid) {
						if(userList1.size()==5)
							userList2.add(new User(userName, serv.getInetAddress().getHostAddress(), isGameHost));
						else
							userList1.add(new User(userName, serv.getInetAddress().getHostAddress(), isGameHost));
						addChat(Constants.EMPTY_STRING,userName+"님이 로비에 참가했습니다.", true);
						synchronized (writermap) {
							writermap.put(userName, new PrintWriter(new OutputStreamWriter(serv.getOutputStream())));
						}
					}
				}else if(statusTeller.compareTo(Constants.CHAT)==0) {
					String sender = reader.readLine();
					String content = reader.readLine();
					boolean isSystemic = reader.readLine().compareTo(Constants.SYSTEMIC) == 0;
					if(sender != null)
						System.out.println("content : "+sender+"가 ["+content+"]라고 메시지를 전송함");
					else
						System.out.println("content : 시스템에서 ["+content+"]라고 메시지를 전송함");
					addChat(sender, content, false);
				}else {
					userName = reader.readLine();
					isGameHost = reader.readLine();
					System.out.println("content : "+userName+" 방에서 나감. host = "+isGameHost);
					for (int i = 0; i < userList1.size(); i++) {
							User user = userList1.get(i);
							if (userName.equals(user.getUserName())) {
								userList1.remove(i);
								return;
							}
					}
					for (int i = 0; i < userList2.size(); i++) {
						User user = userList2.get(i);
						if (userName.equals(user.getUserName())) {
							userList2.remove(i);
							return;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				String line = null;
				while ((line = reader.readLine()) != null) {
					if (listener != null)
						listener.onServerMessage(userName, line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
