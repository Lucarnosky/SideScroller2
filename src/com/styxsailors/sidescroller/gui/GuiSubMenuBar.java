package com.styxsailors.sidescroller.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.styxsailors.sidescroller.handler.MouseHandler;
import com.styxsailors.sidescroller.utility.Global;

public class GuiSubMenuBar extends GuiItem{

	ArrayList<GuiItem> items = new ArrayList<GuiItem>();
	protected MouseHandler mouse;
	public String mainTitle = "";
	protected boolean selected = false;
	
	public GuiSubMenuBar(int x, int y, int width, int height,Global global, MouseHandler mouse, String mainTitle) {
		super(x, y, width, height, global);
		this.mouse = mouse;
		this.mainTitle = mainTitle;
	}
	
	public void tick(int x, int y){
		super.tick(x, y);
		if( mouse.x > x && mouse.x < x + width && mouse.y > y - 12 && mouse.y < y - 12 + height){
			if(mouse.left.down)
				selected = !selected;
			mouse.releaseButton(mouse.left);
		}
		int step = 20;
		if(selected){
			for(int i = 0; i < items.size(); i++){
				items.get(i).tick(x,y + step);
				step += 20;
			}
		}
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.red);
		g.drawRect(x, y - 12, width, height);
		g.setColor(Color.white);
		g.drawString(mainTitle, x, y);
		if(selected){
			for(int i = 0; i < items.size(); i++)
				items.get(i).render(g);
		}
	}
	
	public void addItem(GuiItem item){
		items.add(item);
	}
	
	

}
