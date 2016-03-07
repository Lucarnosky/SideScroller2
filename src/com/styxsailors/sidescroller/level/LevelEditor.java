package com.styxsailors.sidescroller.level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.styxsailors.sidescroller.main.Game;
import com.styxsailors.sidescroller.utility.Global;
import com.styxsailors.sidescroller.objects.EmptyTile;
import com.styxsailors.sidescroller.windowMenu.subMenus.EditorSubMenu;
import com.styxsailors.sidescroller.windowMenu.subMenus.FileSubMenu;
import com.styxsailors.sidescroller.gui.GuiMenuBar;
import com.styxsailors.sidescroller.gui.GuiSubMenuBar;
import com.styxsailors.sidescroller.handler.InputHandler;
import com.styxsailors.sidescroller.handler.MouseHandler;
import com.styxsailors.sidescroller.handler.TextureHandler;
import com.styxsailors.sidescroller.objects.EmptyBackGroundTile;

public class LevelEditor {
	
	ArrayList<EmptyTile> tiles = new ArrayList<EmptyTile>();
	ArrayList<EmptyBackGroundTile> bgTiles = new ArrayList<EmptyBackGroundTile>();
	ArrayList<String> levelStructure = new ArrayList<String>();
	ArrayList<String> avaiableLayers = new ArrayList<String>();
	EmptyTile tmpTile;
	MouseHandler mouse;
	String levelName;
	Game game;
	InputHandler input;
	private int width, height,index=0, maxIndex;
	private int selectedLayerIndex = 0;
	private Global global;
	private String layerSelected = "block";
	private String nextLevelName;
	private TextureHandler textureHandler;
	private BufferedImage image;
	private EmptyTile playerTile;
	private GuiMenuBar windowMenu;
	
	public LevelEditor(String levelName,Game game){
		textureHandler = new TextureHandler();
		setAvaiableLayers();
		width = 320;
		height = 160;
		global = game.getGlobals();
		init();
		global.editor = this;
	}
	
	private void setAvaiableLayers() {
		avaiableLayers.add("block");
		avaiableLayers.add("player");
		avaiableLayers.add("enemy");
		avaiableLayers.add("stuff");
	}

	public void init(){
		global.cameraEnabled = true;
		windowMenu = new GuiMenuBar(-global.camX, -global.camY, Game.WIDTH * Game.SCALE, 20, mouse);
		windowMenu.addItem(new FileSubMenu(-global.camX + 10, -global.camY, 30, 20,global, mouse, "File"));
		windowMenu.addItem(new EditorSubMenu(-global.camX + 100, -global.camY, 40, 20, global, mouse, "Editor"));
		if(levelStructure.isEmpty())
			prepareGrid();
		else{
			width = Integer.parseInt(levelStructure.get(1));
			height = Integer.parseInt(levelStructure.get(2));
			prepareGrid(levelStructure);
		}
	}
	
	public void tick(){
		windowMenu.tick(-global.camX, -global.camY);
		
		if(input.nextName.down){ // Set next level
			input.releseKey(input.nextName);
			nextLevelName = JOptionPane.showInputDialog("Set Next Level");
		}
		//Moving the grid
		if( input.right.down)
			global.camX -= 2;
		if(input.down.down)
			global.camY -= 2;
		if(input.left.down)
			global.camX += 2;
		if(input.up.down)
			global.camY += 2;
		
		//Set max index to not select out of raster image for tiles
		maxIndex = textureHandler.getMaxIndex();
		mouse.setMaxIndex(maxIndex);
		
		//Changing index with mousewheel
		if(mouse.mouseWheel <= 0){
			mouse.mouseWheel = 0;
			index = 0;
		}else if(mouse.mouseWheel >= maxIndex){
			mouse.mouseWheel = maxIndex;
			index = maxIndex;
		}
		
		index =(int) mouse.mouseWheel;
		
		//Changing what putting on the grid
		if(input.layerDown.down && selectedLayerIndex > 0){
			selectedLayerIndex -= 1;
			input.releseKey(input.layerDown);
		}
		if(input.layerUp.down && selectedLayerIndex < avaiableLayers.size() - 1){
			selectedLayerIndex += 1;
			input.releseKey(input.layerUp);
		}
		layerSelected = avaiableLayers.get(selectedLayerIndex);
		
		for(int i = 0; i < tiles.size(); i++){
			tiles.get(i).tick(layerSelected,index);
		}
	}
	
	public void render(Graphics2D g){
		renderBackground(g);
		renderMain(g);
		renderOverall(g);
		image = selectImageByLayer(layerSelected,index);
		g.drawImage(image,(int)mouse.x - 16,(int) mouse.y - 16, 32, 32,null);
		if(global.showEditorSupportWindow){
			g.setColor(new Color(1,1,1, 0.3f));
			g.fillRect(-global.camX + 10, -global.camY + 30, Game.WIDTH * Game.SCALE / 3, Game.HEIGHT * Game.SCALE);
			g.setColor(Color.white);
			if(levelName != null){
				g.drawString("Level Name: "+levelName, -global.camX + 30 , -global.camY + 10);
			}
			g.drawString("Level Width: "+width/32 +" tiles", -global.camX + 10 , -global.camY + 43);
			g.drawString("Level Height: "+height/32 + " tiles", -global.camX + 10 , -global.camY + 56);
			g.drawString("Layer Selected: " +layerSelected, -global.camX + 10, - global.camY + 69);
		    BufferedImage supportImage = textureHandler.rootImage(layerSelected);
			g.drawImage(supportImage, -global.camX + 10, -global.camY + 82, supportImage.getWidth(), supportImage.getHeight(), null);
			g.drawRect( 32 * (index) + (-global.camX + 10), -global.camY + 82, 32, 32);
		}
		windowMenu.render(g);
	}
	
	private void renderBackground(Graphics2D g){
		for(int i = 0; i < bgTiles.size(); i ++)
			bgTiles.get(i).render(g);
	}
	
	private void renderMain(Graphics2D g){
		for(int i = 0; i < tiles.size(); i++)
			tiles.get(i).render(g);
	}
	
	private void renderOverall(Graphics2D g){
		
	}
	
	private void prepareGrid(){
		tiles.clear();
		for(int i = 0; i < width; i += 32)
			for(int j = 0; j < height; j+= 32)
				tiles.add(new EmptyTile(i, j, mouse,this));
	}
	
	private void prepareGrid(ArrayList<String> levelStructure){
		tiles.clear();
		for(int i = 3; i < levelStructure.size();i+= 4){
			tmpTile = new EmptyTile(Integer.parseInt(levelStructure.get(i)),Integer.parseInt(levelStructure.get(i + 1)),mouse,this);
			index = Integer.parseInt(levelStructure.get(i + 3));
			tmpTile.setFilled(selectImageByLayer(levelStructure.get(i + 2),index));
			tmpTile.selectedLayer= levelStructure.get(i + 2);
			tmpTile.index = index;
			tiles.add(tmpTile);
		}
	}
	
	public void setMouse(MouseHandler mouse){
		this.mouse = mouse;
	}
	
	public void setInput(InputHandler input){
		this.input = input;
	}
	
	public void setGame(Game game){
		this.game = game;
		global = game.getGlobals();
	}
	
	public String getSelectedLayer(){
		return layerSelected;
	}
	
	public void exportLevel(String levelName){
		PrintWriter writer;
		try {
			writer = new PrintWriter("res/levels/"+ levelName +".lvl", "UTF-8");
			
			if(nextLevelName != null){ // If not specified a next Level Name, call itself
				writer.write(nextLevelName + "\n");
				writer.append(String.valueOf(width)+ "\n");
			}else{
				writer.write(levelName + "\n");
				writer.append(String.valueOf(width)+ "\n");
			}
			writer.append(String.valueOf(height) + "\n");
			for(int i = 0; i < tiles.size(); i ++){
				writer.append(String.valueOf(tiles.get(i).getX()) + "\n");
				writer.append(String.valueOf(tiles.get(i).getY()) + "\n");
				writer.append(String.valueOf(tiles.get(i).selectedLayer)+ "\n");
				writer.append(String.valueOf(tiles.get(i).index) + "\n");
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadLevel(String levelName){
		this.levelName=levelName;
		levelStructure = new LevelLoader().loadLevel(levelName);	
		prepareGrid(levelStructure);
	} 
	
	public TextureHandler getTextureHandler(){
		return textureHandler;
	}
	
	public BufferedImage selectImageByLayer(String layerSelected, int index){
		
		return textureHandler.grabImage(layerSelected, index);
	}
	
	public void setIndex(int index){
		this.maxIndex=index;
	}
	
	public void setPlayerTile(EmptyTile playerTile){
		this.playerTile = playerTile;
	}
	
	public void removePlayerTile(){
		playerTile.resetTile();
	}
	
	public boolean alreadyPlayerPlaced(){
		return playerTile!=null;
	}
	
	public void setWidth(int width){
		this.width = width;
		prepareGrid();
	}

	public void setHeight(int height) {
		this.height=height;
		prepareGrid();
	}
}