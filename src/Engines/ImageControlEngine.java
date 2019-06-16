package Engines;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import Global.Constants;

public class ImageControlEngine {
	public static BufferedImage CallImage(BufferedImage bi, int x, int y) {	//transformed size
		BufferedImage resized = null;
		int ox = bi.getWidth();
		int oy = bi.getHeight();
		if(x==0) {
			if(y==0) return bi;
			int rx = (int)((double)(ox*y)/(double)oy);
			resized = new BufferedImage(rx,y,BufferedImage.TYPE_INT_ARGB);
			resized.getGraphics().drawImage(bi.getScaledInstance(rx, y, Image.SCALE_AREA_AVERAGING),0,0,rx,y,null);
		}else if(y==0) {
			int ry = (int)((double)(oy*x)/(double)ox);
			resized = new BufferedImage(x,ry,BufferedImage.TYPE_INT_ARGB);
			resized.getGraphics().drawImage(bi.getScaledInstance(x, ry, Image.SCALE_AREA_AVERAGING),0,0,x,ry,null);
		}else {
			resized = new BufferedImage(x,y,BufferedImage.TYPE_INT_ARGB);
			resized.getGraphics().drawImage(bi.getScaledInstance(x, y, Image.SCALE_AREA_AVERAGING),0,0,x,y,null);
		}
		return resized;
	}
	
	public BufferedImage RenderImageAsOpacity(BufferedImage bi, Color c, int floats) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				int minDist = getMin(getMin(height-1-i, i), getMin(width-1-j, j));
				if(minDist>floats)continue;
				byte alpha = (byte)(255*(double)minDist/(double)floats);
				int alphaBit = ( alpha << 24 ) | 0x00ffffff;
				int colorRGBA = bi.getRGB(j, i) & alphaBit;
				bi.setRGB(j, i, colorRGBA);
			}
		}
		return bi;
	}
	
	private static int getMax(int a, int b) {
		return (a>b)?a:b;
	}
	
	private static int getMin(int a, int b) {
		return (a<b)?a:b;
	}
}
