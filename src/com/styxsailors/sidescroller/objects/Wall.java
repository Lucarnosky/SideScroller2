package com.styxsailors.sidescroller.objects;

import java.awt.Color;
import java.awt.Graphics2D;

import com.styxsailors.sidescroller.mobs.GameObj;

public class Wall extends GameObj{
	
	protected String name = "Generic";
	
	public Wall(int x, int y, int width, int height) {
		super(x,y,width,height);
		init();
	}

	public void init(){
		setColor(Color.black);
	}
	
	public void update(){
		
	}
	public void render(Graphics2D g){
		super.render(g);
		g.setColor(Color.red);
		g.drawRect(x, y, width, height);
	}
	public void trigBlock(){
		
	}
	
}
