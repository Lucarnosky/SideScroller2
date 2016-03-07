package com.styxsailors.sidescroller.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.styxsailors.sidescroller.handler.MouseHandler;

public class GuiMenuBar extends GuiMenu{

	ArrayList<GuiItem>items = new ArrayList<GuiItem>();
	
	public GuiMenuBar(int x, int y, int width, int height, MouseHandler mouse) {
		super(x, y, width, height,mouse);
		
	}

	public void tick(int x, int y){
		updatePosition(x, y);
		int step = 0;
		for(int i = 0; i < items.size(); i++){
			items.get(i).tick(x + step,y + 10);
			step += 40;
		}
	}

	public void render(Graphics2D g){
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
		for(int i = 0; i < items.size(); i++)
			items.get(i).render(g);
	}
	
	public void addItem(GuiItem item){
		items.add(item);
	}
	
	private void updatePosition(int x, int y){
		this.x = x;
		this.y = y;
	}
}
