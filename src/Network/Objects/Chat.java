package Network.Objects;

import Network.NetworkCore.NetworkTag;

public class Chat extends ObjectManage{
	private String sender;
	private String content;
	private boolean systemic;
	
	public Chat(String sender, String content, boolean isSystemic) {
		this.setSender(sender);
		this.setContent(content);
		this.setSystemic(isSystemic);
	}
	
	public Chat(String sender, String content, String isSystemic) {
		this.setSender(sender);
		this.setContent(content);
		this.setSystemic(isSystemic.equals(NetworkTag.SYSTEMIC));
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean isSystemic() {
		return systemic;
	}
	
	public void setSystemic(boolean systemic) {
		this.systemic = systemic;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.sender + "|" + this.content +"|"+ (this.isSystemic()?NetworkTag.SYSTEMIC:NetworkTag.NON_SYSTEMIC);
	}
	
	
}
