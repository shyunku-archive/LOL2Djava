package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import Global.Constants;

public class GameServerConnector {

	private Socket sock;
	private BufferedReader reader;
	private PrintWriter writer;
	private OnMessageListener listener;
	private String userName;
	private boolean success = true;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public static interface OnMessageListener {
		public abstract void receivedMessage(String msg);
	}

	public GameServerConnector(String userName, String ip, boolean isGameHost) {
		this(userName, ip, Constants.DEFAULT_SERVER_PORT, isGameHost);
	}

	public void setOnMessageListener(OnMessageListener listener) {
		this.listener = listener;
	}

	public GameServerConnector(String userName, String ip, int port, boolean isGameHost) {
		this.userName = userName;
		try {
			// 변수 init
			try {
				sock = new Socket(ip, port);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "해당 ip에 접속할 수 없습니다.");
				e.printStackTrace();
				success = false;
				return;
			}
			writer = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

			sendMessage(Constants.PARTICIPATE);
			sendMessage(userName);
			sendMessage(isGameHost?Constants.IS_GAME_HOST:Constants.IS_GAME_CLIENT);
			

			// 서버에서 오는 메시지 처리
			Thread input = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						String msg = null;
						while ((msg = reader.readLine()) != null) {
							System.out.println("SERVER -> CLIENT : "+msg);
							if (msg.equals("@")) {
								JOptionPane.showMessageDialog(null, "서버에 동일한 닉네임이 존재합니다.");
								break;
							}
							if (listener != null) {
								try {
									listener.receivedMessage(msg);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block\
						sendMessage(Constants.GO_OUT);
						sendMessage(userName);
						sendMessage(isGameHost?Constants.IS_GAME_HOST:Constants.IS_GAME_CLIENT);
						Constants.ff.cprint(userName + " Disconnected");
					}

					try {
						reader.close();
						writer.close();
					} catch (Exception e) {

					}
				}

			});
			input.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void endConnection(boolean isGameHost, String username) {
		try {
			sock.close();
			writer.close();
		} catch (Exception e) {

		}
	}

	public void sendMessage(String msg) {
		System.out.println("Client -> SERVER : "+msg);
		writer.println(msg);
		writer.flush();
	}
}