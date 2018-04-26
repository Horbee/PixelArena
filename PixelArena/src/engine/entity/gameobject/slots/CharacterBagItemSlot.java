package engine.entity.gameobject.slots;

import java.awt.Color;
import java.awt.Rectangle;

import engine.Game;
import engine.UI.GameUI;
import engine.entity.gameobject.ConsumableItem;
import engine.entity.gameobject.ItemManager;
import engine.entity.gameobject.container.CharacterContainer;
import engine.graphics.Screen;
import engine.input.Mouse;

public class CharacterBagItemSlot extends Slot {

	private CharacterContainer c;

	public CharacterBagItemSlot(int x, int y, CharacterContainer c) {
		this.x = x;
		this.y = y;
		this.c = c;

		rect = new Rectangle(x * Game.getScale(), y * Game.getScale(), 16 * Game.getScale(), 16 * Game.getScale());

		this.normal_sprite = GameUI.sprite_Container_normal;
		this.moved_sprite = GameUI.sprite_Container_moved;
		this.clicked_sprite = GameUI.sprite_Container_clicked;
		this.sprite = normal_sprite;
	}

	public void update() {

		if (rect.contains(Game.mousePosToScreen) && Mouse.mouseClicked(Mouse.LEFT_BUTTON)) {
			sprite = clicked_sprite;
			if (item != null) {
				if (item instanceof ConsumableItem) item.use();
				else Game.level.getPlayer().charUI.slot_weapon.addItem(item);
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
				String mana = "+" + item.getMana() + " MP";
				screen.drawText(mana, x * Game.getScale() - 50, y * Game.getScale() + 30, screen.font2, Color.GREEN);
				String fr = "FR: " + item.getFIRE_RATE();
				screen.drawText(fr, x * Game.getScale() + 40, y * Game.getScale() + 14, screen.font2, Color.ORANGE);
				screen.renderOnTop(x - (ItemManager.i_details.width / 2) + 6, y - (ItemManager.i_details.height / 2) + 12);
			}

			screen.renderSprite(item.sprite, x + 1, y + 1, false, false);
		}
	}

	public void removeItem() {
		if (c != null) c.removeItem(item);
		this.item = null;
		free = true;
	}

}
