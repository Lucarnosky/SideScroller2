package com.styxsailors.sidescroller.handler;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import com.styxsailors.sidescroller.main.Game;
import com.styxsailors.sidescroller.utility.Global;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener{
	public double x, y;
	private Global global;
	public double mouseWheel = 0;
	private int maxIndex = 0;
	
	public class Button {
        public int presses, absorbs;
        public boolean down, clicked;

        public Button() {
        	buttons.add(this);
        }

        public void toggle(boolean pressed) {
                if (pressed != down) {
                        down = pressed;
                }
                if (pressed) {
                        presses++;
                }
        }

        public void tick() {
                if (absorbs < presses) {
                        absorbs++;
                        clicked = true;
                } else {
                        clicked = false;
                }
        }
	}
        public List<Button> buttons = new ArrayList<Button>();
        
        public Button left = new Button();
        public Button right = new Button();
        public Button middle = new Button();
        
        public void releaseAll() {
            for (int i = 0; i < buttons.size(); i++) {
            	buttons.get(i).down = false;
            }
        }
        
        public void releaseButton(Button button){
        	button.down = false;
        }
        
        public void tick() {
            for (int i = 0; i < buttons.size(); i++) {
            	buttons.get(i).tick();
            }
    		
        }
        
        public MouseHandler (Game game){
        	game.addMouseListener(this);
        	game.addMouseMotionListener(this);
        	game.addMouseWheelListener(this);
        	global = game.getGlobals();
        }
	
        private void toggle(MouseEvent me, boolean pressed){
        	if( SwingUtilities.isLeftMouseButton(me)) left.toggle(pressed);
        	if( SwingUtilities.isRightMouseButton(me)) right.toggle(pressed);
        	if( SwingUtilities.isMiddleMouseButton(me)) middle.toggle(pressed);
        }
        
	public void mouseDragged(MouseEvent me) {
		
	}

	
	public void mouseMoved(MouseEvent me) {
		PointerInfo a = MouseInfo.getPointerInfo();
		Point point = new Point(a.getLocation());
		SwingUtilities.convertPointFromScreen(point, me.getComponent());
		x= Math.round(( point.getX() - global.camX )/ global.scale);
		y= Math.round(( point.getY() - global.camY )/ global.scale);
		
	}

	
	public void mouseClicked(MouseEvent me) {
		//toggle(me,true);
		
	}

	
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseExited(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	
	public void mousePressed(MouseEvent me) {
		toggle(me,true);
		
	}

	
	public void mouseReleased(MouseEvent me) {
		toggle(me, false);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent me) {
		if(me.getWheelRotation() > 0 && mouseWheel > 0)
			mouseWheel -= 1;
		if (me.getWheelRotation() < 0 && mouseWheel < maxIndex)
			mouseWheel += 1;
	}
	
	public void setMaxIndex(int maxIndex){
		this.maxIndex = maxIndex;
	}

}
