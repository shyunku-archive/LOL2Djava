package Engines;

import java.awt.Color;

import javax.print.attribute.AttributeSet;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class EpicEngine {
	public void appendToPane(JTextPane tp, String msg, Color c)
    {        
        StyledDocument doc = tp.getStyledDocument();
        SimpleAttributeSet keyword = new SimpleAttributeSet();
        StyleConstants.setForeground(keyword, c);
        try {
			doc.insertString(doc.getLength(), msg, keyword);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
}
