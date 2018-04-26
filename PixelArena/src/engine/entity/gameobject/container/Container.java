package engine.entity.gameobject.container;

import engine.entity.gameobject.Item;
import engine.graphics.Screen;

public abstract class Container {

	protected int itemCount = 0;
	protected int slots = 0;
	protected int posX, posY;
	protected boolean fixed;

	public abstract void addItem(Item item);
	public abstract void removeItem(Item item);
	public abstract void update();
	public abstract void render(Screen screen) ;

	public int getItemCount() {
		return itemCount;
	}

}
