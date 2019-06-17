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
	
	//나중에 게임 데이터 클래스로 한데 모아야함
	private WaitingRoomInfo RoomInfo = new WaitingRoomInfo();
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
										 
										 //서버 <- 클라이언트
										 Constants.ff.cprint("SERVER <- CLIENT : "+request);
										 String[] tokens = request.split("\\|");
										 curUsername = tokens[0];
										 tokens = Constants.ff.cutFrontStringArray(tokens, 1);
										 String tag = tokens[0];
										 String[] msg = Arrays.copyOfRange(tokens, 1, tokens.length);
										 
										 if(tag.equals(NetworkTag.PARTICIPATE)) {
											 User newUser = new User(msg);
											 addWriter(socket, newUser.getUserName());
											 RoomInfo.addUser(newUser);
											 RoomInfo.addChat(new Chat(NetworkTag.EMPTY_STRING, newUser.getUserName()+"님이 로비에 참가하셨습니다.", true));
										 }else if(tag.equals(NetworkTag.CHAT)) {
											 Chat newChat = new Chat(msg[0], msg[1], msg[2]);
											 RoomInfo.addChat(newChat);
										 }
									} catch (SocketException e) {
										// TODO Auto-generated catch block
										RoomInfo.removeUser(curUsername);
										removeWriter(curUsername);
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
					JOptionPane.showMessageDialog(null, "서버가 이미 실행되고 있습니다.");
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
		
		//주기적으로 CLIENT에 데이터 전송 : SERVER -> CLIENT
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					try {
						Thread.sleep(50);
						switch(GameStatus) {
						case NetworkTag.WAITING_ROOM:
							if(RoomInfo != null) {
								broadcast(GameStatus + "|" + RoomInfo.toMsg());
							}
							break;
						}
					} catch (InterruptedException e) {
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
}