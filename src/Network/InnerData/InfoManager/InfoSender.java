package Network.InnerData.InfoManager;

public abstract class InfoSender {	
	public abstract String AddtoMsg(String[] seg);
	public abstract String DeletetoMsg(String[] seg);
	public abstract String ModifytoMsg(String[] seg);
}
