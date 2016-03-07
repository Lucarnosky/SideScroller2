package com.styxsailors.sidescroller.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.styxsailors.sidescroller.handler.MouseHandler;
import com.styxsailors.sidescroller.level.LevelEditor;

public class EmptyTile {
	
	int x, y;
	MouseHandler mouse;
	public BufferedImage filled = null;
	public String selectedLayer = null;
	LevelEditor editor;
	public int index;
	
	public EmptyTile(int x, int y, MouseHandler mouse, LevelEditor editor){
		this.x = x;
		this.y = y;
		this.mouse = mouse;
		this.editor = editor;
	}
	
	public void init(){
		
	}
	
	public void tick(String layerSelected,int index){
		
		if(mouse != null){
			if( mouse.x > x && mouse.x < x + 32 && mouse.y > y && mouse.y < y + 32){
				if(mouse.left.down){
					if(layerSelected == "player"){
						if(editor.alreadyPlayerPlaced()){
							editor.removePlayerTile();
							editor.setPlayerTile(this);
							mouse.releaseAll();
						}else{
							editor.setPlayerTile(this);
							mouse.releaseAll();
						}
					}
					setFilled(editor.selectImageByLayer(layerSelected, index));
					selectedLayer = layerSelected;
					this.index = index;
				}
				if(mouse.right.down){
					resetTile();
				}
			}
		}
	}
	
	public void render(Graphics2D g){
		if(filled != null){
			g.drawImage(filled, x, y, 32, 32, null);
		}else{
			g.setColor(Color.white);
			g.drawRect(x,y,32,32);
		}
	}
	
	public void setFilled(BufferedImage image){
		this.filled = image;
	}
	
	public int getY(){
		return y;
	}
	public int getX(){
		return x;
	}
	
	public void resetTile(){
		selectedLayer = null;
		filled = null;
		index = 0;
	}

}
