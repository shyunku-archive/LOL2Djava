package Network.InnerData;

public abstract class MessageControl {
	public abstract String toMsg();
	public abstract void fromMsg(String[] seg);
	
	protected final String TOKEN = "|";
	
	public abstract void init();
}
