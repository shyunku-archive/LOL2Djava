package Game.Objects;

import Global.Constants;
import Objects.CoordinateDouble;

public class Camera {
	public final int ALLOW_OUTSIDE_OF_FRAME = 500;
	private CoordinateDouble centerPos;
	
	public boolean isInCamera(CoordinateDouble pos) {
		return pos.getY()<=centerPos.getY()+Constants.FullScreenDimension.getHeight()+this.ALLOW_OUTSIDE_OF_FRAME&&
				pos.getY()>=centerPos.getY()-Constants.FullScreenDimension.getHeight()-this.ALLOW_OUTSIDE_OF_FRAME&&
				pos.getX()<=centerPos.getX()+Constants.FullScreenDimension.getWidth()+this.ALLOW_OUTSIDE_OF_FRAME&&
				pos.getX()>=centerPos.getX()+Constants.FullScreenDimension.getWidth()-this.ALLOW_OUTSIDE_OF_FRAME;
	}
	
	public CoordinateDouble getFixedPosition(CoordinateDouble original) {
		return new CoordinateDouble(
				original.getX() - (centerPos.getX() - Constants.FullScreenDimension.getWidth()/2),
				original.getY() - (centerPos.getY() - Constants.FullScreenDimension.getHeight()/2)
				);
	}
}
