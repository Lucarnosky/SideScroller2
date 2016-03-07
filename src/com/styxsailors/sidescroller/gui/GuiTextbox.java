package com.styxsailors.sidescroller.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import com.styxsailors.sidescroller.handler.MouseHandler;
import com.styxsailors.sidescroller.utility.Global;

public class GuiTextbox extends GuiItem {

	MouseHandler mouse;
	boolean selected = false;
	String message = "";
	
	public GuiTextbox(int x, int y, int width, int height,Global global, MouseHandler mouse, String message) {
		
		super(x, y, width, height,global);
		width = message.length();
		height = 10;
		this.mouse = mouse;
		this.message = message;	
	}
	
	public void tick(int x, int y){
		super.tick(x, y);
		if( mouse.x > x && mouse.x < x + width && mouse.y > y - 12 && mouse.y < y - 12 + height){
			if(mouse.left.down)
				selected = !selected;
			mouse.releaseButton(mouse.left);
		}
	}
	
	public void render(Graphics2D g){
		
		g.setColor(Color.green);
		g.drawRect(x, y-12, width, height);
		if(selected){
			g.setColor(Color.cyan);
			g.fillRect(x, y - 12, width, height);
			g.setColor(Color.orange);
			g.drawString(message, x, y);
		}else{
			g.setColor(Color.white);
			g.fillRect(x, y - 12, width, height);
			g.setColor(Color.black);
			g.drawString(message, x, y);
		}
	}

	
}
