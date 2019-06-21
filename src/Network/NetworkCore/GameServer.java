package Network.NetworkCore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

import Global.Constants;
import Network.InnerData.WaitingRoom.WaitingRoomInfo;
import Network.Objects.Chat;
import Network.Objects.User;

public class GameServer {
	
	private ServerSocket serverSocket = null;
	private HashMap<String, PrintWriter> writermap;
	
	//���߿� ���� ������ Ŭ������ �ѵ� ��ƾ���
	private WaitingRoomInfo RoomInfo = new WaitingRoomInfo("", "");
	private String GameStatus = NetworkTag.WAITING_ROOM;
	
	
	public void startServer() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					serverSocket = new ServerSocket(NetworkTag.DEFAULT_SERVER_PORT);
					writermap = new HashMap<String, PrintWriter>();
					Constants.ff.cprint("Waiting for Response...");
					while(true) {
						Socket socket = serverSocket.accept();
						new Thread(new Runnable() {
							@Override
							public void run() {
								String curUsername = null;
								while(true) {
									try {
										String request;
										BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
										PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
										request = bufferedReader.readLine();
										 
										 //���� <- Ŭ���̾�Ʈ
										 String[] tokens = request.split("\\|");
										 curUsername = tokens[0];
										 tokens = Constants.ff.cutFrontStringArray(tokens, 1);
										 String tag = tokens[0];
										 String[] msg = Arrays.copyOfRange(tokens, 1, tokens.length);
										 
										 if(!tag.equals(NetworkTag.PING_TEST))
											 Constants.ff.cprint("SERVER <- CLIENT : "+request);
										 
										 
										 
										 if(tag.equals(NetworkTag.PARTICIPATE)) {
											 if(!RoomInfo.getPassword().equals(""))
												 if(!msg[3].equals(RoomInfo.getPassword())) {
													 PrintWriter temp = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
													 temp.println(NetworkTag.WAITING_ROOM + "|" + NetworkTag.PASSWORD_NOT_CORRECT);
													 temp.flush();
													 temp.close();
													 socket.close();
													 break;
												 }
											 User newUser = new User(msg);
											 Chat newChat = new Chat(NetworkTag.EMPTY_STRING, newUser.getUserName()+"���� �κ� �����ϼ̽��ϴ�.", true);
											 addWriter(socket, newUser.getUserName());
											 
											 broadcastToSpecific(newUser.getUserName(), GameStatus, NetworkTag.UPDATE_ALL, RoomInfo.toMsg());
											 
											 RoomInfo.addUser(newUser);
											 RoomInfo.addChat(newChat);
											 
											 broadcast(GameStatus, NetworkTag.UPDATE_SIGNAL, NetworkTag.ITEM_ADDITION, NetworkTag.USER_LIST_TAG, newUser.toString());
											 broadcast(GameStatus, NetworkTag.UPDATE_SIGNAL, NetworkTag.ITEM_ADDITION, NetworkTag.CHAT_LOG_TAG, newChat.toString());
											 
											 Constants.ff.playSoundClip(Constants.ParticipateSoundPath, Constants.DEFAULT_VOLUME);
										 }else if(tag.equals(NetworkTag.CHAT)) {
											 Chat newChat = new Chat(msg[0], msg[1], msg[2]);
											 RoomInfo.addChat(newChat);
											 broadcast(GameStatus, NetworkTag.UPDATE_SIGNAL, NetworkTag.ITEM_ADDITION, NetworkTag.CHAT_LOG_TAG, newChat.toString());
										 }else if(tag.equals(NetworkTag.MOVE_TEAM_SIGNAL)) {
											 RoomInfo.moveTeam(msg[0]);
											 broadcast(GameStatus, NetworkTag.MOVE_TEAM_SIGNAL, msg[0]);
										 }else if(tag.equals(NetworkTag.PING_TEST)) {
											 broadcastToSpecific(curUsername, NetworkTag.PING_TEST);
										 }
									} catch (SocketException e) {
										// TODO Auto-generated catch block
										Chat newChat = new Chat(NetworkTag.EMPTY_STRING, curUsername+"���� �κ� �������ϴ�.", true);
										RoomInfo.addChat(newChat);
										RoomInfo.removeUser(curUsername);
										removeWriter(curUsername);
										
										broadcast(GameStatus, NetworkTag.UPDATE_SIGNAL, NetworkTag.ITEM_DELETION, NetworkTag.USER_LIST_TAG, curUsername);
										broadcast(GameStatus, NetworkTag.UPDATE_SIGNAL, NetworkTag.ITEM_ADDITION, NetworkTag.CHAT_LOG_TAG, newChat.toString());
										break;
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						}).start();
					}
				}catch(BindException e) {
					JOptionPane.showMessageDialog(null, "������ �̹� ����ǰ� �ֽ��ϴ�.");
					System.exit(0);
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
						if(serverSocket != null && !serverSocket.isClosed())
							serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}).start();
	}
	
	private String rebind(String... strs) {
		String result = "";
		int paramCount = strs.length;
		int cnt = 0;
		for(String str : strs) {
			result += str;
			if(cnt != paramCount)
				result += "|";
		}
		return result;
	}

	private void removeWriter(String username) {
		synchronized(writermap) {
			writermap.remove(username);
		}
	}
	
	private void addWriter(Socket socket, String username) {
		synchronized(writermap) {
			try {
				writermap.put(username, new PrintWriter(new OutputStreamWriter(socket.getOutputStream())));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void broadcast(String...par){
		String buffer = "";
		int paramCount = par.length;
		int curCount = 0;
		for(String t : par) {
			buffer += t.toString();
			if(curCount!= paramCount-1)
				buffer += "|";
			curCount++;
		}
		
		try {
			synchronized (writermap) {
				Collection<PrintWriter> allUsers = writermap.values();
				Constants.ff.cprint("SERVER -> CLIENT["+allUsers.size()+"] : "+buffer);
				Iterator<PrintWriter> i = allUsers.iterator();
				while (i.hasNext()) {
					PrintWriter p = i.next();
					p.println(buffer);
					p.flush();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastToSpecific(String destination, String...par){
		String buffer = "";
		int paramCount = par.length;
		int curCount = 0;
		for(String t : par) {
			buffer += t.toString();
			if(curCount!= paramCount-1)
				buffer += "|";
			curCount++;
		}
		
		try {
			synchronized (writermap) {
				PrintWriter pw = writermap.get(destination);
				pw.println(buffer);
				pw.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void broadcast(String message) {
		try {
			synchronized (writermap) {
				Collection<PrintWriter> allUsers = writermap.values();
				Constants.ff.cprint("SERVER -> CLIENT["+allUsers.size()+"] : "+message);
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
	
	public void endServer() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setWaitingRoom(WaitingRoomInfo wri) {
		this.RoomInfo = wri;
	}
}