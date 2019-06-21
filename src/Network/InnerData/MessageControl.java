package Network.InnerData;

public abstract class MessageControl {
	protected final String TOKEN = "|";
	
	//불러오기
	public abstract String toMsg();
	public abstract void fromMsg(String[] seg);
	public abstract void init();
	
	//업데이트	
	public abstract void addItem(String[] seg);
	public abstract void deleteItem(String[] seg);
	public abstract void modifyItem(String[] seg);
}
