package Utility;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import Core.Starter;
import Global.Constants;
import Global.Variables;
import Objects.Coordinate;

public class TriggeredButton extends JButton{
	private BufferedImage FocusedImage = null;
	private BufferedImage unFocusedImage = null;
	private BufferedImage SelectedImage = null;
	private Rectangle focusRect;
	private Coordinate FocusImagePoint = null;
	private Coordinate UnFocusImagePoint = null;
	private Coordinate SelectedImagePoint = null;
	
	private onButtonListener listener = null;
	
	private boolean isOnButton = false;
	private boolean isSelected = false;
	private long FadeInTime;
	private long FadeOutTime;
	
	private long LastExitedFlag = 0;
	private long LastEnteredFlag = 0;
	private double SavedProcess[] = {0,0};
	
	private float opacity = 0f;
	
	public void draw(Graphics2D g) {
		if(!this.isVisible())return;
		if(unFocusedImage!=null)
			g.drawImage(unFocusedImage, null, this.UnFocusImagePoint.getX(), this.UnFocusImagePoint.getY());
		opacity = (float) this.getProcessRate();
		if(FocusedImage!=null)
			ect.fde.drawSemiTranslucentImage(g, FocusedImage, this.FocusImagePoint.getX(), this.FocusImagePoint.getY(), opacity);
		if(isSelected && (SelectedImage != null)) {
			g.drawImage(SelectedImage, null, this.SelectedImagePoint.getX(), this.SelectedImagePoint.getY());
		}
	}
	
	public double getProcessRate() {
		double Elapsed = (double)(System.currentTimeMillis() - (isOnButton ? this.LastExitedFlag : this.LastEnteredFlag));
		if(isOnButton) {
			SavedProcess[0] = RateCal(SavedProcess[1] + RateCal(Elapsed/(double)this.FadeInTime));
			return SavedProcess[0];
		}
		SavedProcess[1] = RateCal(SavedProcess[0] - RateCal(Elapsed/(double)this.FadeOutTime));
		return SavedProcess[1];
	}
	
	private double RateCal(double db) {
		if(db>1)return 1;
		if(db<0)return 0;
		return db;
	}
	
	public void selectThis() {
		this.isSelected = true;
	}
	
	public void unselectThis() {
		this.isSelected = false;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public TriggeredButton(BufferedImage unFocused, BufferedImage Focused, BufferedImage Selected,
			Coordinate unFocusedPoint, Coordinate FocusedPoint, Coordinate SelectedPoint,
			 Rectangle FocusFrame, long fadeIn, long fadeOut) {
		
		this.unFocusedImage = unFocused;
		this.UnFocusImagePoint = unFocusedPoint;
		
		this.FocusedImage = Focused;
		this.FocusImagePoint = FocusedPoint;
		
		this.SelectedImage = Selected;
		this.SelectedImagePoint = SelectedPoint;
		
		this.FadeInTime = fadeIn;
		this.FadeOutTime = fadeOut;
		
		this.setBounds(FocusFrame);
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setVisible(true);
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				isOnButton = false;
				LastEnteredFlag = System.currentTimeMillis();
				if(listener!=null)listener.onClick();
				selectThis();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				isOnButton = true;
				LastExitedFlag = System.currentTimeMillis();
				Starter.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				if(listener!=null)listener.onEnter();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				isOnButton = false;
				LastEnteredFlag = System.currentTimeMillis();
				Starter.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				if(listener!=null)listener.onExit();
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(listener!=null)listener.onPress();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(listener!=null)listener.onRelease();
			}
			
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				Variables.mousePos.setPos(e.getX()+getBounds().x, e.getY()+getBounds().y);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				Variables.mousePos.setPos(e.getX()+getBounds().x, e.getY()+getBounds().y);
			}
			
		});
	}
	
	public void addOnButtonListener(onButtonListener obl) {
		this.listener = obl;
	}
	private static EnginesControl ect = new EnginesControl();
}
