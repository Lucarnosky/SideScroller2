package com.styxsailors.sidescroller.mobs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import com.styxsailors.sidescroller.handler.InputHandler;
import com.styxsailors.sidescroller.main.Game;
import com.styxsailors.sidescroller.utility.Global;

public class Player extends GameObj{
	
	private InputHandler input;
	public boolean grounded = true,isJumping=false;
	private Global global;
	public boolean outOfBounds = false;
	private boolean stopLeft = false, stopRight = false;
	private int targetY=0;
	
	public Player(int x,int y,int width,int height,Global global){
		super(x,y,width,height);
		this.global = global;
		init();
	}
	
	public Player(int x,int y,int width,int height,Global global,BufferedImage texture){
		super(x,y,width,height,texture);
		this.global = global;
		init();
	}
	
	
	public void init(){
		setColor(Color.gray);
		input = global.input;
		setSpeed(2,2);
		setMaxSpeed(8, 8);
		dirY = 1;
	}
	
	public void update(){
		
		global.camY = (y - Game.HEIGHT / 2 * Game.SCALE);
		global.camX = (x - Game.WIDTH / 2 * Game.SCALE);
		
		checkCollisions();
		if(input.right.down && !stopRight){
			if(hsp >= hspMax)
				hsp = hspMax;
			else
				hsp += xSpeed;
			dirX = 1;
		}else if(input.left.down && !stopLeft){
			if(hsp <= -hspMax)
				hsp = -hspMax;
			else
				hsp -= xSpeed;
			dirX = -1;
		}else{
			hsp = 0;
			dirX = 0;
		}
		
		if(input.up.down && dirY!=-1 && !isJumping){
			targetY = y - 64;
			dirY = -1;
			isJumping = true;
		}
		
		if(dirY == -1){
			if(y <= targetY){
				dirY = 1;
				vsp = 0;
			}else{
				if(vsp <= -vspMax)
					vsp = -vspMax;
				else
					vsp -= ySpeed;
			}
		}else if(dirY == 1){
			if(vsp >= vspMax)
				vsp = vspMax;
			else 
				vsp += global.GRAVITY;
			
		}else if(dirY == 0){
			if(grounded){
				isJumping = false;
			}else{
				isJumping = true;
				dirY = 1;
			}
		}
		x += hsp;
		y += vsp;
		grounded = false;
	}
	
	protected void checkCollisions(){
		
		for(int i = 0; i < global.allEntities.size(); i++){//Passo tutti i gameobj e controllo se stanno per colpirsi con il player
			GameObj object = global.allEntities.get(i);
			if(object instanceof Player)
				continue;
			
			///COLLISIONE ORIZZONTALE 
			Rectangle tmpRectX = null; //Setto il rettangolo di check in base alla direzione e alla velocità
			if(dirX > 0)
				tmpRectX = new Rectangle(x + width , y + 4 ,  hsp, height - 10);
			else if (dirX < 0)
				tmpRectX = new Rectangle(x + hsp  , y + 4 ,  -hsp, height - 10);
			else
				tmpRectX = getBounds();
			
			if(object.getBounds().intersects(tmpRectX)){ //Se è contenuto nel GameObj in analisi, mi avvicino il più possibile
				while(!(getBounds().intersects(object.getBounds()))){
					if(dirX < 0)
						x -= 1;
					else if(dirX > 0)
						x += 1;
				}
				computeHorizontalCollision(object); //E lo aggiungo ai GameObj che collidono
			}
			///FINE COLLISIONE ORIZZONTALE
			
			///Collisione verticale
			Rectangle tmpRectY = null;
			if(dirY > 0)
				tmpRectY = new Rectangle(x,y + height , width, vsp);
			else if(dirY < 0)
				tmpRectY = new Rectangle(x,y - vsp,width,vsp);
			else
				tmpRectY = new Rectangle(x,y+height,width,1);
			
			if(object.getBounds().intersects(tmpRectY)){ //Se è contenuto nel GameObj in analisi, mi avvicino il più possibile
				grounded = true;
				while(!(getBounds().intersects(object.getBounds()))){
					if(dirY < 0){
						y -= 1;
					}else if(dirY > 0){
						y += 1;
					}
					vsp = 0;
				}
				computeVerticalCollision(object);
			}
			
		}
		
	}
	
	private void computeHorizontalCollision(GameObj object){
		switch(object.getName()){
			case "Wall":
				stopRight = (object.getBounds().intersects(new Rectangle(x + width,  y + 4, 10, height - 10)));
				stopLeft = (object.getBounds().intersects(new Rectangle(x - 10, y + 4, 10, height - 10)));
				hsp = 0;
				break;
			case "Enemy":
				remove = true;
				break;
			
		}
	}
	
	private void computeVerticalCollision(GameObj object) {
		switch (object.getName()){
			case "Wall":
				grounded = (dirY >= 0 && object.getBounds().intersects(new Rectangle(x, y+ height,width,10)));
				vsp = 0;
				dirY = 0;
				break;
			case "Enemy":
				object.remove = true;
				break;
		}
		
	}

	public void render(Graphics2D g){
		super.render(g);
		Rectangle tmpRectX = new Rectangle(x - 10, y + 4, 10, height - 10);
		g.setColor(Color.yellow);
		g.drawRect(tmpRectX.x,tmpRectX.y,tmpRectX.width,tmpRectX.height);
	}
}
