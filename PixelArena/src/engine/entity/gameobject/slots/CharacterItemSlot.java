package engine.entity.gameobject.slots;

import java.awt.Color;
import java.awt.Rectangle;

import engine.Game;
import engine.UI.GameUI;
import engine.entity.gameobject.Item;
import engine.entity.gameobject.ItemManager;
import engine.graphics.Screen;
import engine.input.Mouse;

public class CharacterItemSlot extends Slot {

	public boolean hasItem = false;

	public CharacterItemSlot(int x, int y) {
		this.x = x;
		this.y = y;

		rect = new Rectangle(x * Game.getScale(), y * Game.getScale(), 16 * Game.getScale(), 16 * Game.getScale());

		this.normal_sprite = GameUI.sprite_char_Container_normal;
		this.moved_sprite = GameUI.sprite_char_Container_moved;
		this.clicked_sprite = GameUI.sprite_char_Container_clicked;
		this.sprite = normal_sprite;
	}

	public void update() {
		if (item != null) hasItem = true;
		else hasItem = false;

		// ennek elméletileg mindig false fixed-nek kell lennie
		if (rect.contains(Game.mousePosToScreen) && Mouse.mouseClicked(Mouse.LEFT_BUTTON)) {
			sprite = clicked_sprite;
			if (item != null) {
				Game.game.level.getPlayer().charUI.cont_bag.addItem(item);
				removeItem();
			}
		} else if (rect.contains(Game.mousePosToScreen)) {
			sprite = moved_sprite;
			moved = true;
		} else {
			moved = false;
			sprite = normal_sprite;
		}

	}

	public void render(Screen screen) {
		screen.renderSprite(sprite, x, y, false, false);
		if (item != null) {

			// Details are rendered on the top layer of the screen
			if (moved) {

				screen.drawText(item.name, x * Game.getScale() - (item.name.length() * 4 / 2), y * Game.getScale() + 50, screen.font2, Color.WHITE);

				String dmg = item.getMinDmg() + "-" + item.getMaxDmg();
				screen.drawText(dmg, x * Game.getScale() - 2, y * Game.getScale() - 2, screen.font2, Color.WHITE);
				String hp = "+" + item.getHp() + " HP";
				screen.drawText(hp, x * Game.getScale() - 50, y * Game.getScale() + 14, screen.font2, Color.GREEN);
				String fr = "FR: " + item.getFIRE_RATE();
				screen.drawText(fr, x * Game.getScale() + 40, y * Game.getScale() + 14, screen.font2, Color.ORANGE);
				screen.renderOnTop(x - (ItemManager.i_details.width / 2) + 6, y - (ItemManager.i_details.height / 2) + 12);
			}

			// Then we render the actual icon of the item
			screen.renderSprite(item.sprite, x + 1, y + 1, false, false);
		}
	}

	public void addItem(Item item) {
		if (this.item != null) {
			Game.game.level.getPlayer().charUI.cont_bag.addItem(this.item);
			removeItem();
		}
		this.item = item;
	}

	public void removeItem() {
		this.item = null;
	}

}
