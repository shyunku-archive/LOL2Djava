package Utility;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import Objects.Coordinate;

public class ImageScroller extends JPanel{
	private final int ScrollMovement = 15;
	
	private Rectangle ScrollFrame;
	private Dimension ImageFrameSize;
	private int horizontalFillNum;
	private int verticalPos;
	
	private int verticalStart;
	private int horizontalMargin;
	private int verticalMargin;
	
	private ArrayList<BufferedImage> images;
	
	public ImageScroller(Rectangle entire, Dimension imageframe, int horNum, ArrayList<BufferedImage> images, int hm, int vm, int vs) {
		this.ScrollFrame = entire;
		this.ImageFrameSize = imageframe;
		this.horizontalFillNum = horNum;
		this.images = images;
		this.verticalPos = 0;
		this.horizontalMargin = hm;
		this.verticalMargin = vm;
		this.verticalStart = vs;
	}
	
	public void paintComponent(Graphics2D g) {
		BufferedImage current;
		int row = images.size()/horizontalFillNum + 1;
		int ImageWidth = images.get(0).getWidth();
		int ImageHeight = images.get(0).getHeight();
		int horGap = ScrollFrame.width - 2*horizontalMargin - ImageFrameSize.width;
		int verGap = verticalMargin + ImageFrameSize.height;
		for(int i=0;i<row;i++)
			for(int j=0;j<horizontalFillNum;j++) {
				int index = i*horizontalFillNum + j;
				if(index>=images.size())break;
				current = images.get(index);
				Coordinate coord = new Coordinate(horizontalMargin + j*horGap, verticalStart + i*verGap);
				GeneralPath restrict = new GeneralPath();
				restrict.append(new Rectangle(coord.getX(), coord.getY(), ImageFrameSize.width, ImageFrameSize.height), false);
				g.clip(restrict);
				Coordinate realImageStartPos = new Coordinate(
						coord.getX() + ImageFrameSize.width/2 - ImageWidth/2,
						coord.getY() + ImageFrameSize.height/2 - ImageHeight/2);
				g.drawImage(current, null, realImageStartPos.getX(), realImageStartPos.getY());
				g.setClip(0, 0, (int)this.getSize().getWidth(), (int)this.getSize().getHeight());
			}
	}
	
	public void scrollUp() {
		this.verticalPos -= this.ScrollMovement;
		if(this.verticalPos<0)
			this.verticalPos = 0;
	}
	
	public void scrollDown() {
		this.verticalPos += this.ScrollMovement;
		//아래 끝에 도달시 제한 추가
	}
}
