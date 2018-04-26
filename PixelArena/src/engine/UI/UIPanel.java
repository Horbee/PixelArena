package engine.UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UIPanel {

	private List<UIComponent> components = new ArrayList<UIComponent>();
	private int x, y, w, h;
	private BufferedImage image;
	private Color color;
	
	public UIPanel(int x, int y, int w, int h) { //sz�n n�lk�l, igy nem renderel a felirat al� h�tteret
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public UIPanel(int x, int y, int w, int h, Color color) { //sz�nnel, hogy a feliratnak legyen h�ttere
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.color = color;
	}
	
	public UIPanel(int x, int y, int w, int h, BufferedImage image) { //sz�n n�lk�l, igy nem renderel a felirat al� h�tteret
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.image = image;
	}
	
	public void addComponent(UIComponent component){
		components.add(component);
	}
	
	public void update(){
		for(UIComponent component : components ){
			component.setOffset(this.x, this.y);
			component.update();
		}
	}

	public void render(Graphics g){
		if(color != null){
			g.setColor(color);
			g.fillRect(x, y, w, h);			
		}
		
		if(image != null){
			g.drawImage(image, x, y, w, h, null);
		}
		
		for(UIComponent component : components ){
			component.render(g);
		}
		
	}
	
}
