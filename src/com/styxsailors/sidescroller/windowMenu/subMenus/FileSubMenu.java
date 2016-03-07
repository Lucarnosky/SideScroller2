package com.styxsailors.sidescroller.windowMenu.subMenus;

import java.awt.Graphics2D;

import com.styxsailors.sidescroller.gui.GuiButton;
import com.styxsailors.sidescroller.gui.GuiSubMenuBar;
import com.styxsailors.sidescroller.handler.MouseHandler;
import com.styxsailors.sidescroller.utility.Global;
import com.styxsailors.sidescroller.windowMenu.buttons.LoadLevelButton;
import com.styxsailors.sidescroller.windowMenu.buttons.SaveButton;

public class FileSubMenu extends GuiSubMenuBar{

	public FileSubMenu(int x, int y, int width, int height,Global global, MouseHandler mouse, String mainTitle) {
		super(x, y, width, height,global, mouse, mainTitle);
		init();
	}

	private void init(){
		addItem(new GuiButton(x, y +18 ,80, 16, global, mouse, "New"));
		addItem(new SaveButton(x, y +18 ,80, 16,global, mouse, "Save"));
		addItem(new LoadLevelButton(x, y + 18, 80, 16, global, mouse, "Load"));
		selected = false;
	}
	
	public void tick(int x, int y){
		super.tick(x,y);
		
	}
	
	public void render(Graphics2D g){
		super.render(g);
		
	}
}
