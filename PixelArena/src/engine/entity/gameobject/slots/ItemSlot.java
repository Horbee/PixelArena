package engine.entity.gameobject.slots;

import java.awt.Rectangle;

import engine.Game;
import engine.UI.GameUI;
import engine.entity.gameobject.container.Container;
import engine.graphics.Screen;
import engine.input.Mouse;

public class ItemSlot extends Slot {

	protected Container c;
	
	public ItemSlot(int x, int y, Container c, boolean fixed) {
		this.x = x;
		this.y = y;
		this.c = c;
		this.fixed = fixed;

		if (!fixed) rect = new Rectangle(x * Game.getScale(), y * Game.getScale(), 16 * Game.getScale(), 16 * Game.getScale());
		else rect = new Rectangle(x, y, 16, 16);

		this.normal_sprite = GameUI.sprite_Container_normal;
		this.moved_sprite = GameUI.sprite_Container_moved;
		this.clicked_sprite = GameUI.sprite_Container_clicked;
		this.sprite = normal_sprite;
	}


	public void update() {
		if (fixed) {
			if (new Rectangle(rect.x, rect.y, 16, 16).contains(Game.mousePosToGame) && Mouse.mouseClicked(Mouse.LEFT_BUTTON)) {
				sprite = clicked_sprite;
				if (item != null) {
					Game.game.level.getPlayer().charUI.cont_bag.addItem(item);
					removeItem();
				}
			} else if (rect.contains(Game.mousePosToGame)) {
				sprite = moved_sprite;
			} else {
				sprite = normal_sprite;
			}
		} else {
			if (rect.contains(Game.mousePosToScreen) && Mouse.mouseClicked(Mouse.LEFT_BUTTON)) {
				sprite = clicked_sprite;
			} else if (rect.contains(Game.mousePosToScreen)) {
				sprite = moved_sprite;
			} else {
				sprite = normal_sprite;
			}
		}
	}

	public void render(Screen screen) {
		screen.renderSprite(sprite, x, y, fixed);
		if (item != null) {
			screen.renderSprite(item.sprite, x + 1, y + 1, fixed);
		}
	}
	
	public void removeItem() {
		if (c != null) c.removeItem(item);
		this.item = null;
		free = true;
	}

}
