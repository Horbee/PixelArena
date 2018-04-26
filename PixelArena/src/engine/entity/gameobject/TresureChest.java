package engine.entity.gameobject;

import engine.entity.Spawner;
import engine.entity.gameobject.container.BagContainer;
import engine.entity.gameobject.container.Container;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public class TresureChest extends GameObject{

	public boolean opened = false;
	public Container c;
	
	public TresureChest(int posX, int posY) {
		super(posX, posY);
		currentSprite = Sprite.lada;
		
		c = new BagContainer(6, this, 25, -16, true);
	}
	
	
	public void update() {
		c.update();
		
		
		if (c.getItemCount() > 0) { //IF NOT EMPTY IT SHINES
			new Spawner((int)posX, (int)posY, Spawner.Type.SHINEPARTICLE, 1, level);
		}
		
		
		
	}
	
	public void render(Screen screen) {
		screen.renderObject(this, (int)posX, (int)posY);
		if (opened) {
			c.render(screen);
		}
		//Debug.drawRect(screen, newRX,  newRY,  collRect.width,  collRect.height, true);
	}

}
