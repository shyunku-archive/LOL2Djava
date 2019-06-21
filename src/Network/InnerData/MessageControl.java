package Network.InnerData;

public abstract class MessageControl {
	protected final String TOKEN = "|";
	
	//�ҷ�����
	public abstract String toMsg();
	public abstract void fromMsg(String[] seg);
	public abstract void init();
	
	//������Ʈ	
	public abstract void addItem(String[] seg);
	public abstract void deleteItem(String[] seg);
	public abstract void modifyItem(String[] seg);
}
