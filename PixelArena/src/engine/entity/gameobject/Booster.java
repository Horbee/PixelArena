package engine.entity.gameobject;

import engine.Game;
import engine.entity.Spawner;

public class Booster extends ConsumableItem {
	
	
	public Booster() {
		super(2);
		name = "Booster";
	}
	
	public void use() {
		System.out.println("booster used");
		Game.sounds[4].play();
		Game.level.getPlayer().dmgModifier += 50;
		new Spawner((int) Game.level.getPlayer().getPosX(), (int) Game.level.getPlayer().getPosY() + 16, Spawner.Type.HEALTHPARTICLE, 30, Game.level);

	}
}
