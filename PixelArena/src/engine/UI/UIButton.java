package engine.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import engine.input.Mouse;

public class UIButton extends UIComponent {

	private Font defaultFont = new Font("Helvetica", Font.PLAIN, 20);
	private Color bgColor;
	private String text;
	private BufferedImage image;
	
	private boolean inside = false;
	private boolean pressed = false;
	private UIButtonListener buttonListener;
	private UIActionListener actionListener;
	
	public UIButton(int x, int y, int w, int h, String text, UIActionListener actionListener) {
		super(x, y);
		this.w = w;
		this.h = h;
		this.bgColor = Color.DARK_GRAY;
		this.text = text;
		this.actionListener = actionListener;
		buttonListener = new UIButtonListener();
	}
	
	public UIButton(int x, int y, BufferedImage image, UIActionListener actionListener) {
		super(x, y);
		this.w = image.getWidth();
		this.h = image.getHeight();
		this.image = image;
		//this.bgColor = Color.DARK_GRAY;
		//this.text = text;
		this.actionListener = actionListener;
		buttonListener = new UIButtonListener();
	}

	public void setButtonListener(UIButtonListener buttonListener) {
		this.buttonListener = buttonListener;
	}

	public void setBgColor(Color color){
		bgColor = color;
	}
	
	public void setImage(BufferedImage image){
		this.image = image;
	}
	
	public void update() {
		Rectangle bounds = new Rectangle(getAbsX(), getAbsY(), w, h);
		if (bounds.contains(new Point(Mouse.getMouseX(), Mouse.getMouseY()))) {
			if(!inside){
				buttonListener.mouseEntered(this);
			}
			inside = true;		
			
			if(!pressed && Mouse.getButton() == MouseEvent.BUTTON1){
				buttonListener.pressed(this);
				pressed = true;
			}else if(pressed && Mouse.getButton() == MouseEvent.NOBUTTON){
				buttonListener.released(this);
				actionListener.action();
				pressed = false;
			}
		}else{
			if(inside) {
				buttonListener.mouseExited(this);
				pressed = false;
			}
			inside = false;				
		}
	}

	public void render(Graphics g) {
		if(image != null){
			g.drawImage(image, x + offsetX, y + offsetY, null);
		}else {
			g.setColor(bgColor);
			g.fillRect(x + offsetX, y + offsetY, w, h);
			g.setColor(Color.BLACK);
			g.setFont(defaultFont);
			g.drawString(text, x + offsetX + 5, y + offsetY + 20);			
		}
	}

}
