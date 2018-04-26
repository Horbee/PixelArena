package engine.entity.gameobject;

import engine.Game;
import engine.entity.Spawner;

public class MinorHealth extends ConsumableItem {
	
	
	public MinorHealth() {
		super(5);
		name = "Minor Health";
	}
	
	public void use() {
		System.out.println("item used");
		Game.sounds[4].play();
		Game.level.getPlayer().health += 100;
		new Spawner((int) Game.level.getPlayer().getPosX(), (int) Game.level.getPlayer().getPosY() + 16, Spawner.Type.HEALTHPARTICLE, 30, Game.level);

	}
}
