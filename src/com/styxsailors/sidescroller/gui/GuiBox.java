package com.styxsailors.sidescroller.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.styxsailors.sidescroller.utility.Global;

public class GuiBox extends GuiItem{

	public ArrayList<GuiItem> items = new ArrayList<GuiItem>();
	private String title="";
	
	public GuiBox(int x, int y, int width, int height, Global global) {
		super(x, y, width, height,global);
		
	}
	
	public void tick(){
		int step = 0;
		for(int i = 0; i < items.size(); i++){
			items.get(i).tick(x+ step,y);
			
		}
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.gray);
		g.drawRect(x, y, width, height);
		g.fillRect(x, y, width, 20);
		g.setColor(Color.white);
		g.drawString(title.toUpperCase(),x + 10, y);
		for(int i = 0; i < items.size(); i++)
			items.get(i).render(g);
	}
	
	public void addItem(GuiItem item){
		items.add(item);
	}
	
	public void setTitle(String title){
		this.title=title;
	}

}
