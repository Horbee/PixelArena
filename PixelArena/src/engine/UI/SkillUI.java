package engine.UI;

import java.awt.Color;
import java.awt.Rectangle;

import engine.Game;
import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.input.Mouse;

public class SkillUI {

	private int x, y;
	private Sprite image;
	private String label;
	private Rectangle rect;
	private boolean mouse_over;

	public SkillUI(int x, int y, Sprite image) {
		this.x = x;
		this.y = y;
		this.image = image;
		rect = new Rectangle(x * 2, y * 2, 16 * 2, 16 * 2);
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void update() {
		mouse_over = (rect.contains(Game.mousePosToScreen));
	}

	public void render(Screen screen) {
		screen.renderSprite(image, x, y, false);

		if (mouse_over) {
			String[] temp = label.split("\n");
			for (int i = 0; i < temp.length; i++) {
				screen.drawText(temp[i], Mouse.getMouseX() + 2, Mouse.getMouseY() + 2 + i * 12, screen.font2, Color.BLACK);
				screen.drawText(temp[i], Mouse.getMouseX(), Mouse.getMouseY() + i * 12, screen.font2, Color.ORANGE);
			}
		}
	}
}
