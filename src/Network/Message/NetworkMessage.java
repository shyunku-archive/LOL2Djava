package Network.Message;

public abstract class NetworkMessage {
	//SERVER -> CLIENT
	public static final int WAITING_ROOM 		= 10000;			//대기실
	public static final int GAME_START_SIGNAL 	= 10001;		//게임 시작 버튼 누르는 신호
	
	//CLIENT -> SERVER
	
	
	public abstract void fromMsg(String[] segment);
	public abstract String toMsg();
}
