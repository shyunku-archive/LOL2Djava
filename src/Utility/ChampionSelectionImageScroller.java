package Utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import Global.Constants;
import Global.ImageManager;
import Objects.Champion;
import Objects.Coordinate;

public class ChampionSelectionImageScroller extends JPanel{
	private final int ScrollMovement = 15;
	
	private Rectangle ScrollFrame;
	private Dimension ImageFrameSize;
	private int horizontalFillNum;
	private int verticalPos;
	
	private int verticalStart;
	private int horizontalMargin;
	private int verticalMargin;
	
	public int prevSelected = -1;
	
	private ArrayList<Champion> pack;
	
	public static TriggeredButton championClick[];
	
	private static EnginesControl ect = new EnginesControl();
	
	public ChampionSelectionImageScroller(Rectangle entire, Dimension imageframe, int horNum, ArrayList<Champion> pack, int hm, int vm, int vs) {
		this.ScrollFrame = entire;
		this.ImageFrameSize = imageframe;
		this.horizontalFillNum = horNum;
		this.pack = pack;
		this.verticalPos = 0;
		this.horizontalMargin = hm;
		this.verticalMargin = vm;
		this.verticalStart = vs;
		
		this.setLocation(this.ScrollFrame.x, this.ScrollFrame.y);
		this.setSize(this.ScrollFrame.width, this.ScrollFrame.height);
		this.setVisible(true);
		this.setDoubleBuffered(true);
		
		this.setLayout(null);
		
		championClick = new TriggeredButton[pack.size()];
		
		int row = ((pack.size()-1)/horizontalFillNum) + 1;
		int ImageWidth = pack.get(0).getChampionIcon().getWidth();
		int ImageHeight = pack.get(0).getChampionIcon().getHeight();
		int horGap = (ScrollFrame.width - 2*horizontalMargin - ImageFrameSize.width)/(horizontalFillNum-1);
		int verGap = verticalMargin + ImageFrameSize.height;
		for(int i=0;i<row;i++)
			for(int j=0;j<horizontalFillNum;j++) {
				int index = i*horizontalFillNum + j;
				if(index>=pack.size())break;
				Coordinate coord = new Coordinate(horizontalMargin + j*horGap, verticalStart + i*verGap + verticalPos);
				championClick[index] = new TriggeredButton(
						null,
						ImageManager.FocusedChampionIconFrame,
						ImageManager.SelectedChampionIconFrame,
						null,
						new Coordinate(coord.getX() - 5, coord.getY() - 4),
						new Coordinate(coord.getX() - 4, coord.getY() - 4),
						new Rectangle(coord.getX(), coord.getY(), ImageFrameSize.width, ImageFrameSize.height),
						0,
						0
						);
				
				prevSelected = index;
				this.add(championClick[index]);
			}
	}
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(Constants.ff.getFancyFont(13, true));
		g.setColor(new Color(0, 8, 15));
		g.fillRect(0, 0, this.ScrollFrame.width, this.ScrollFrame.height);
		BufferedImage current;
		
		int row = ((pack.size()-1)/horizontalFillNum) + 1;
		int ImageWidth = pack.get(0).getChampionIcon().getWidth();
		int ImageHeight = pack.get(0).getChampionIcon().getHeight();
		int horGap = (ScrollFrame.width - 2*horizontalMargin - ImageFrameSize.width)/(horizontalFillNum-1);
		int verGap = verticalMargin + ImageFrameSize.height;
		for(int i=0;i<row;i++)
			for(int j=0;j<horizontalFillNum;j++) {
				int index = i*horizontalFillNum + j;
				if(index>=pack.size())break;
				current = pack.get(index).getChampionIcon();
				Coordinate coord = new Coordinate(horizontalMargin + j*horGap, verticalStart + i*verGap + verticalPos);
				GeneralPath restrict = new GeneralPath();
				restrict.append(new Rectangle(coord.getX(), coord.getY(), ImageFrameSize.width, ImageFrameSize.height), false);
				g.drawImage(ImageManager.ChampionIconFrame, null, coord.getX()-1, coord.getY()-1);
				g.clip(restrict);
				Coordinate realImageStartPos = new Coordinate(
						coord.getX() + ImageFrameSize.width/2 - ImageWidth/2,
						coord.getY() + ImageFrameSize.height/2 - ImageHeight/2);
				g.drawImage(current, null, realImageStartPos.getX(), realImageStartPos.getY());
				g.setClip(0, 0, (int)this.getSize().getWidth(), (int)this.getSize().getHeight());
				
				championClick[index].draw(g);
				
				g.setColor(new Color(240, 230, 210));
				ect.fde.drawFlexibleCenteredString(g, pack.get(index).getChampionName(), new Coordinate(
						coord.getX() + ImageFrameSize.width/2,
						coord.getY() + ImageFrameSize.height + 5
						));
			}
	}
	
	public void scrollUp() {
		this.verticalPos -= this.ScrollMovement;
		int limit = 0, row = ((pack.size()-1)/horizontalFillNum) + 1;
		if(row >= 5)
			limit = 15 + (row-5)*93;
		if(this.verticalPos<-limit)
			this.verticalPos = -limit;
		scrollPerformed();
	}
	
	public void scrollDown() {
		this.verticalPos += this.ScrollMovement;
		if(this.verticalPos>0)
			this.verticalPos = 0;
		scrollPerformed();
	}
	
	public void scrollPerformed() {
		int row = ((pack.size()-1)/horizontalFillNum) + 1;
		int ImageWidth = pack.get(0).getChampionIcon().getWidth();
		int ImageHeight = pack.get(0).getChampionIcon().getHeight();
		int horGap = (ScrollFrame.width - 2*horizontalMargin - ImageFrameSize.width)/(horizontalFillNum-1);
		int verGap = verticalMargin + ImageFrameSize.height;
		for(int i=0;i<row;i++)
			for(int j=0;j<horizontalFillNum;j++) {
				int index = i*horizontalFillNum + j;
				if(index>=pack.size())break;
				Coordinate coord = new Coordinate(horizontalMargin + j*horGap, verticalStart + i*verGap + verticalPos);
				championClick[index].setThis(
						null,
						ImageManager.FocusedChampionIconFrame,
						ImageManager.SelectedChampionIconFrame,
						null,
						new Coordinate(coord.getX() - 5, coord.getY() - 4),
						new Coordinate(coord.getX() - 4, coord.getY() - 4),
						new Rectangle(coord.getX(), coord.getY(), ImageFrameSize.width, ImageFrameSize.height),
						0,
						0
						);
				championClick[index].validate();
				championClick[index].repaint();
			}
	}
}
