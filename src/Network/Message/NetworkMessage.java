package Network.Message;

public abstract class NetworkMessage {
	//SERVER -> CLIENT
	public static final int WAITING_ROOM 		= 10000;			//����
	public static final int GAME_START_SIGNAL 	= 10001;		//���� ���� ��ư ������ ��ȣ
	
	//CLIENT -> SERVER
	
	
	public abstract void fromMsg(String[] segment);
	public abstract String toMsg();
}
