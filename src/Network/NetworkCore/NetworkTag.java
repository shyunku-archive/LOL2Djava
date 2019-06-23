package Network.NetworkCore;

public class NetworkTag {
	
	public static final int 	DEFAULT_SERVER_PORT = 10200;
	public static final String 	LOCAL_HOST_ADDRESS 	= "127.0.0.1";
	
	
	//Status
	public static final String	WAITING_ROOM		= "%%STATUS_WAITING_ROOM%%";
	public static final String	CHAMP_SELECT_ROOM	= "%%CHAMPION_SELECT_ROOM%%";
	
	
	//Global
	public static final String	PASSWORD_NOT_CORRECT= "%%WRONG_PASSWORD%%";
	
	public static final String	ITEM_ADDITION		= "%%ADD_ITEM%%";
	public static final String 	ITEM_DELETION		= "%%DELETE_ITEM%%";
	public static final String	ITEM_MODIFICATION	= "%%MODIFY_ITEM%%";
	
	public static final String	UPDATE_ALL			= "%%UPDATE_ALL%%";
	public static final String	UPDATE_SIGNAL		= "%%UPDATE_SIGNAL%%";
	
	public static final String	PING_TEST			= "%%PING_TEST%%";
	
	//WaitingRoom
	public static final String 	USER_LIST_TAG		= "%%USER_LIST%%";
	public static final String 	USER_LIST_TAG1		= "%%USER_LIST1%%";
	public static final String 	USER_LIST_TAG2		= "%%USER_LIST2%%";
	public static final String 	CHAT_LOG_TAG		= "%%CHAT_LOG%%";
	
	public static final String 	MOVE_TEAM_SIGNAL	= "%%MOVE_TEAM_SIGNAL%%";
	
	public static final String 	EMPTY_STRING 		= "%%NULL%%";
	
	public static final String 	IS_GAME_HOST 		= "%%GAME_HOST%%";
	public static final String 	IS_GAME_CLIENT		= "%%GAME_CLIENT%%";
	
	public static final String 	PARTICIPATE 		= "%%PARTICIPATED%%";
	public static final String 	QUIT				= "%%GOOUTFROMHERE%%";
	public static final String 	CHAT 				= "%%CHAT%%";
	public static final String 	SYSTEMIC 			= "%%SYSTEMIC%%";
	public static final String 	NON_SYSTEMIC 		= "%%NONSYSTEMIC%%";
	
	public static final String	SELECT_START		= "%%CHAMPION_SELECT_MOVE%%";
	
	
	//ChampionSelectingPage
	public static final long	CHAMPION_SELECT_TIME	= 28000;
	public static final long	FINAL_WAITING_TIME		= 50000;
}
