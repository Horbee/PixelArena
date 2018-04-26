package engine.entity.gameobject;

import engine.entity.Spawner;
import engine.entity.gameobject.container.BagContainer;
import engine.entity.gameobject.container.Container;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public class Chest extends GameObject {

	public boolean opened = false;
	public Container c;

	public Chest(int posX, int posY) {
		super(posX, posY);
		currentSprite = Sprite.chest;
		c = new BagContainer(6, this, 25, -16, true);
	}

	public Chest addItem(Item item) {
		c.addItem(item);
		return this;
	}

	public Chest addRandomItem() {
		int id = rnd.nextInt(ItemManager.items.size());
		Item temp = ItemManager.items.get(id);
		c.addItem(temp);
		return this;
	}

	public void update() {
		new Spawner((int) posX, (int) posY, Spawner.Type.SHINEPARTICLE, 1, level); // IF NOT EMPTY IT SHINES
		c.update();

		if (c.getItemCount() == 0) remove();

		if (this.isSelected()) {
			opened = !opened;
		}

		if (opened) currentSprite = Sprite.chest_open;
		else currentSprite = Sprite.chest;
	}

	public void render(Screen screen) {
		screen.renderObject(this, (int) posX, (int) posY);
		if (opened) {
			c.render(screen);
		}
	}

}
