package engine.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UILabel extends UIComponent {

	public static Font defaultFont = new Font("Helvetica", Font.PLAIN, 32);
	private String text;
	
	public UILabel(int x, int y, String text) {
		super(x, y);
		this.text = text;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.setFont(defaultFont);
		g.drawString(text, x + offsetX, y + offsetY);
	}
	
}
