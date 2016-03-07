package com.styxsailors.sidescroller.gui;

import java.awt.Graphics2D;

import com.styxsailors.sidescroller.utility.Global;

public class GuiItem {

	protected int x,y,width,height;
	protected Global global;
	
	public GuiItem(int x, int y, int width, int height,Global global){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height=height;
		this.global = global;
	}
	
	public void tick(int x, int y){
		updatedPosition(x, y);
	}
	
	public void render(Graphics2D g){
		
	}
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	private void updatedPosition(int x ,int y){
		this.x = x;
		this.y = y;
	}
}
