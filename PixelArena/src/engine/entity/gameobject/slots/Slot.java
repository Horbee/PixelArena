package engine.entity.gameobject.slots;

import java.awt.Rectangle;

import engine.entity.gameobject.Item;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public abstract class Slot {

	protected int x, y;
	protected Sprite sprite;
	protected Sprite normal_sprite, moved_sprite, clicked_sprite;
	protected Item item;
	protected Rectangle rect;
	protected boolean fixed;
	public boolean free = true;
	protected boolean moved = false;

	public abstract void update();
	public abstract void render(Screen screen);
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void addItem(Item item) {
		this.item = item;
		free = false;
	}

	public Item getItem() {
		return item;
	}

}
