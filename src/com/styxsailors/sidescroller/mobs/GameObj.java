package com.styxsailors.sidescroller.mobs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.styxsailors.sidescroller.main.Game;
import com.styxsailors.sidescroller.utility.Global;


public class GameObj {
	
	/**
	 * x,y coordinate pubbliche
	 * width,height dimensioni dello sprite
	 * xSpeed,ySpeed velocità orizzontale e verticale del gameObj
	 * hsp,vsp sono le accelerazioni verticali e orizzontali
	 * hspMax,vspMax sono le accelerazioni massime consentite
	 * active se attivo oppure no
	 * isNullObject casistica decisa dal costruttore, se ha coordinate o dimensioni nulle
	 * collidingEntity arraylist dei GameObj che intersecano con i bound del GameObj corrente
	 */
	public int x,y,width,height,xSpeed,ySpeed,dirX,dirY,hsp,vsp,hspMax = 10,vspMax = 10,initialX,initialY;
	public boolean active,isNullObject,remove=false,stopRight,stopLeft,grounded;
	public BufferedImage texture;
	public Graphics2D g;
	public Color fillColor = Color.green,borderColor = Color.red;
	public ArrayList<GameObj> collidingEntity;
	public Global global;
	
	public GameObj(){
		active = true;
		texture = null;
		isNullObject = true;
		hsp = 0;
		vsp = 0;
	}
	
	public GameObj(int x, int y){
		this.x = x;
		this.y = y;
		initialX = x;
		initialY = y;
		hsp = 0;
		vsp = 0;
		active = true;
		texture = null;
		isNullObject = true;
	}
	
	public GameObj(int x, int y,int width,int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		active = true;
		texture = null;
		isNullObject = false;
		collidingEntity = new ArrayList<GameObj>();
		hsp = 0;
		vsp = 0;
		initialX = x;
		initialY = y;
	}
	
	public GameObj(int x, int y,int width,int height,BufferedImage texture){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		active = true;
		this.texture = texture;
		isNullObject = false;
		collidingEntity = new ArrayList<GameObj>();
		hsp = 0;
		vsp = 0;
		initialX = x;
		initialY = y;
	}
	
	/**
	 * Loop di aggiornamento
	 */
	public void update(){
		checkCollisions();
		if(!stopRight){
			if(hsp >= hspMax)
				hsp = hspMax;
			else
				hsp += xSpeed;
			dirX = 1;
		}else if(!stopLeft){
			if(hsp <= -hspMax)
				hsp = -hspMax;
			else
				hsp -= xSpeed;
			dirX = -1;
		}else{
			hsp = 0;
			dirX = 0;
		}
		
		if(dirY == -1){
			if(vsp <= -vspMax)
				vsp = -vspMax;
			else
				vsp -= ySpeed;
		}else if(dirY == 1){
			if(vsp >= vspMax)
				vsp = vspMax;
			else 
				vsp += global.GRAVITY;
			
		}else if(dirY == 0){
			if(!grounded){
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
		}
	}
	
	private void computeVerticalCollision(GameObj object) {
		switch (object.getName()){
			case "Wall":
				grounded = (dirY >= 0 && object.getBounds().intersects(new Rectangle(x, y+ height,width,10)));
				vsp = 0;
				dirY = 0;
				break;
		}
		
	}
	
	/**
	 * disegna il gameObject sullo schermo
	 * il render prende in considerazione i parametri
	 * active = se falso esce direttamente dal loop
	 * isNullObject = se è un NullObject non controlla le texture e non va nel place holder
	 * texture = se null va nel place holder e disegna un quadrato [colori di default fillColor per riempimento,borderColor per debug]
	 * @param graphics
	 */
	public void render(Graphics2D graphics){
		g =  graphics;
		if(active){
			if(!isNullObject){
				if(texture != null){
					g.drawImage(texture, x, y, width, height, null);
				}else{
					g.setColor(fillColor);
					g.fillRect(x, y, width, height);
				}
			}
		}
		
	}
	
	/**
	 * Setta il colore del place holder cambiando il default 
	 * @default fillColor Color.green;
	 * @param fill Color il colore di riempimento del GameObj
	 */
	public void setColor(Color fill){
		fillColor = fill;
	}
	
	/**
	 * Setta il color del place holder e delle righe di debug
	 * @default fillColor Color.green;
	 * @default borderColor Color.red;
	 * @param fill Color il colore di riempimento del GameObj
	 * @param border Color il colore delle righe di debug del GameObj
	 */
	public void setColor(Color fill, Color border){
		fillColor = fill;
		borderColor = border;
	}
	
	/**
	 * Ritorna il rettangolo di collisione
	 * calcolato se non è un null object
	 * @default new Rectangle(0,0,0,0)
	 * @return new Rectangle il bound del GameObj
	 */
	public Rectangle getBounds(){
		if(!isNullObject)
			return new Rectangle(x, y, width, height);
		return new Rectangle(0,0,0,0);
	}

	/**
	 * Setta le velocità orizzontale e verticale del GameObj
	 * @param xSpeed int velocità orizzontale
	 * @param ySpeed int velocità verticale
	 */
	public void setSpeed(int xSpeed,int ySpeed){
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	/**
	 * Aggiunge un GameObj con cui l'oggetto corrente sta entrando in contatto
	 * @param colliding GameObj oggetto con cui si collide se diverso da sè stesso
	 */
	public void addCollidingGameObj(GameObj colliding){
		if(colliding != this)
			collidingEntity.add(colliding);
	}
	
	public void setMaxSpeed(int maxHorizontalSpeed,int maxVerticalSpeed){
		hspMax = maxHorizontalSpeed;
		vspMax = maxVerticalSpeed;
	}
	
	public String getName(){
		return getClass().getSimpleName();
	}
	
}
