package engine.entity.gameobject;

import engine.Game;
import engine.entity.Spawner;

public class MinorMana extends ConsumableItem{

	public MinorMana() {
		super(6);
		name = "Minor Mana";
	}
	
	public void use() {
		System.out.println("item used");
		Game.sounds[4].play();
		Game.level.getPlayer().mana += 100;
		new Spawner((int) Game.level.getPlayer().getPosX(), (int) Game.level.getPlayer().getPosY() + 16, Spawner.Type.MANAPARTICLE, 30, Game.level);

	}

}
