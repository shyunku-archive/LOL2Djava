package Network.InnerData.InfoManager;

import java.util.Arrays;

import Global.Constants;
import Network.NetworkCore.NetworkTag;

public abstract class InfoReceiver {
	public abstract void AddfromMsg(String seg[]);
	public abstract void DeletefromMsg(String seg[]);
	public abstract void ModifyfromMsg(String seg[]);
	public void fromMsg(String seg[]) {
		String[] seq = Arrays.copyOfRange(seg, 1, seg.length);
		if(seg[0].equals(NetworkTag.ADD_DATA_TAG))
			AddfromMsg(seq);
		else if(seg[0].equals(NetworkTag.DELETE_DATA_TAG))
			DeletefromMsg(seq);
		else if(seg[0].equals(NetworkTag.MODIFY_DATA_TAG))
			ModifyfromMsg(seq);
		else
			Constants.ff.printDebugWithMessage("UNKNOWN RESPONSE!");
	}
}
