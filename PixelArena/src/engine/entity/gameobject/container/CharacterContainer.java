package engine.entity.gameobject.container;

import java.util.ArrayList;
import java.util.List;

import engine.entity.gameobject.GameObject;
import engine.entity.gameobject.Item;
import engine.entity.gameobject.slots.CharacterBagItemSlot;
import engine.graphics.Screen;

public class CharacterContainer extends Container {

	public List<CharacterBagItemSlot> item_slots = new ArrayList<CharacterBagItemSlot>();

	public CharacterContainer(int slots, GameObject go, int xOffset, int yOffset, boolean fixed) {
		this.slots = slots;
		this.fixed = fixed;
		this.posX = (int)go.getPosX() + xOffset;
		this.posY = (int)go.getPosY() + yOffset;
		addSlot();
	}

	private void addSlot() {
		for (int i = 0; i < this.slots; i++) {
			if (i < 4) item_slots.add(new CharacterBagItemSlot(posX + i * 16, posY, this));
			else item_slots.add(new CharacterBagItemSlot(posX + ((i - 4) * 16), posY + 16, this));
		}
	}

	public void update() {
		for (int i = 0; i < item_slots.size(); i++) {
			item_slots.get(i).update();
		}
	}

	public void render(Screen screen) {
		for (int i = 0; i < item_slots.size(); i++) {
			item_slots.get(i).render(screen);
		}
	}

	public void addItem(Item item) {
		for (int i = 0; i < item_slots.size(); i++) {
			if (item_slots.get(i).free == true) {
				item_slots.get(i).addItem(item);
				break;
			}
		}
		itemCount++;
	}

	public void removeItem(Item item) {
		item_slots.remove(item);
		itemCount--;
	}

}
