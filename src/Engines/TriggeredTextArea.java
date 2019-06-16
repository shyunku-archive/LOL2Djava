package Engines;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

import Core.Starter;
import Global.Constants;
import Global.Variables;
import Utility.EnginesControl;

public class TriggeredTextArea extends JTextArea{
	private Rectangle focusRect;
	
	public void setFont() {
		setFont(Constants.ff.getClassicFont(16F, true));
	}
	
	public TriggeredTextArea(Rectangle focusRect) {
		this.focusRect = focusRect;
		
		setBounds(focusRect);
		setBackground(new Color(0,0,0,0));
		setBorder(BorderFactory.createCompoundBorder(
				getBorder(), 
		        BorderFactory.createEmptyBorder(5, 8, 5, 5)));
		setForeground(new Color(220,220,220,255));
		try {
			setFont(Font.createFont(Font.TRUETYPE_FONT, new File("Resources\\Fonts\\Global\\SMB.ttf")));
		} catch (FontFormatException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		setVisible(true);
		setFocusable(true);
		setCaretColor(new Color(220,220,220,255));
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				Variables.mousePos.setPos(e.getX()+getBounds().x, e.getY()+getBounds().y);
			}
			
		});
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				setFocusable(true);
				Constants.ff.playSoundClip(Constants.lightClickSoundFilePath, Constants.LIGHT_CLICK_SOUND_VOLUME);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				Starter.frame.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				Starter.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	private static EnginesControl ect = new EnginesControl();
}
