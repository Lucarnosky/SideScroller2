package com.styxsailors.sidescroller.mobs;

public class Enemy extends GameObj{
	
	public Enemy(int x, int y, int width, int height) {
		super(x,y,width,height);
	}

	public void update(){
		super.update();
		if(!stopRight && !stopLeft && hsp == 0){
			stopLeft = true;
		}
		if(stopLeft && hsp < 0){
			hsp = xSpeed;
		}
		if(stopRight && hsp > 0){
			hsp = -xSpeed;
		}
		
	}
}
