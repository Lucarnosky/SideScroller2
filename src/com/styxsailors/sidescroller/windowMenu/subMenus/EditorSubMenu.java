package com.styxsailors.sidescroller.windowMenu.subMenus;

import java.awt.Graphics2D;

import com.styxsailors.sidescroller.gui.GuiSubMenuBar;
import com.styxsailors.sidescroller.handler.MouseHandler;
import com.styxsailors.sidescroller.utility.Global;
import com.styxsailors.sidescroller.windowMenu.buttons.ChangeHeightButton;
import com.styxsailors.sidescroller.windowMenu.buttons.ChangeWidthButton;
import com.styxsailors.sidescroller.windowMenu.buttons.ToggleEditorSupportWindowButton;

public class EditorSubMenu extends GuiSubMenuBar{

	public EditorSubMenu(int x, int y, int width, int height,Global global, MouseHandler mouse, String mainTitle) {
		super(x, y, width, height,global, mouse, mainTitle);
		init();
	}
	
	private void init(){
		addItem(new ChangeWidthButton(x, y, 100, height, global, mouse, ""));
		addItem(new ChangeHeightButton(x, y, 100, height, global, mouse, ""));
		addItem(new ToggleEditorSupportWindowButton(x, y, 100, height, global, mouse, ""));
	}
	
	public void tick(){
		super.tick(x, y);
	}
	
	public void render(Graphics2D g){
		super.render(g);
	}
}
