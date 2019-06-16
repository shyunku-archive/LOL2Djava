package Utility;

public class Chat {
	private String sender;
	private String content;
	private boolean systemic;
	
	public Chat(String sender, String content, boolean isSystemic) {
		if(sender==null) {
			System.out.println("¿©±â´Ù");
			new Throwable().printStackTrace();
		}
		this.setSender(sender);
		this.setContent(content);
		this.setSystemic(isSystemic);
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
}
