package engine.entity.gameobject;

public abstract class ConsumableItem extends Item {

	public ConsumableItem(int spriteID) {
		super(spriteID);
	}
	
	public abstract void use();

}
