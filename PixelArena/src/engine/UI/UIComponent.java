package engine.UI;

import java.awt.Color;
import java.awt.Graphics;

public class UIComponent {

	public int x, y, w, h, offsetX, offsetY;
	public Color color;
	
	public UIComponent(int x, int y) {
		this.x = x; 
		this.y = y;
		color = new Color(0xff00ff);
	}
	
	public void update(){
		
	}
	
	public void render(Graphics g){
		
	}
	
	public void setOffset(int x, int y){
		offsetX = x;
		offsetY = y;
	}
	
	public int getAbsX(){
		return x + offsetX;
	}
	
	public int getAbsY(){
		return y + offsetY;
	}
}
