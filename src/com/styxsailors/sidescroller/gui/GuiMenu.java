package com.styxsailors.sidescroller.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.styxsailors.sidescroller.handler.MouseHandler;

public class GuiMenu {
	
	protected int x,y,width,height;
	ArrayList<GuiItem> items = new ArrayList<GuiItem>();
	MouseHandler mouse;
	
	public GuiMenu(int x, int y, int width, int height, MouseHandler mouse){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.mouse = mouse;
	}
	
	public void tick(){
		
	}
	
	public void render(Graphics2D g){
		
	}

}
