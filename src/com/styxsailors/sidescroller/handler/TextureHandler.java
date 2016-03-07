package com.styxsailors.sidescroller.handler;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class TextureHandler {

	BufferedImage requestImage;
	private int maxIndex = 0;
	
	public TextureHandler(){
		init();
	}
	
	public void init(){
		
	}
	
	public BufferedImage grabImage(String fileName, int subImgNumber){
		BufferedImage tmp;
		if(!fileName.equals("null"))
		try {
			tmp = ImageIO.read(ClassLoader.getSystemClassLoader().getResource("textures/"+fileName+".png"));
			maxIndex = tmp.getWidth()/32 - 1;
			if(subImgNumber > maxIndex){
				subImgNumber = maxIndex;
			}else if(subImgNumber < 0){
				subImgNumber = 0;
			}
			requestImage = tmp.getSubimage(subImgNumber * 32, 0, 32, 32);
		} catch (IOException e) {
			e.printStackTrace();
		}
		else
			requestImage = null;
		return requestImage;
	}
	
	public int getMaxIndex(){
		return maxIndex;
	}
	
	public BufferedImage rootImage(String fileName){
		if(!fileName.equals("null"))
			try {
				return ImageIO.read(ClassLoader.getSystemClassLoader().getResource("textures/"+fileName+".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
}
