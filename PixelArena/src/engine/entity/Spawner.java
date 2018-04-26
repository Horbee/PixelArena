package engine.entity;

import engine.entity.particles.CurseParticle;
import engine.entity.particles.EmeraldParticle;
import engine.entity.particles.FireBombParticle;
import engine.entity.particles.FireParticle;
import engine.entity.particles.PowerupParticle;
import engine.entity.particles.ShineParticle;
import engine.entity.particles.StoneParticle;
import engine.level.Level;

public class Spawner extends Entity {

	public enum Type{
		MOB, STONEPARTICLE, EMERALDPARTICLE, FIREPARTICLE, CURSEPARTICLE, SHINEPARTICLE, FIREBOMBPARTICLE, MANAPARTICLE, HEALTHPARTICLE;
	}
	
	private Type type;
	
	public Spawner(int posX, int posY, Type type, int amount, Level level) {
		init(level);
		this.posX = posX;
		this.posY = posY;
		this.type = type;
		for (int i = 0; i < amount; i++) {
			if (this.type == Type.STONEPARTICLE) {
				level.add(new StoneParticle(posX, posY, 200));
			}
			if (this.type == Type.EMERALDPARTICLE) {
				level.add(new EmeraldParticle(posX, posY, 15));
			}
			if (this.type == Type.FIREPARTICLE) {
				level.add(new FireParticle(posX, posY, 15));
			}
			if (this.type == Type.CURSEPARTICLE) {
				level.add(new CurseParticle(posX, posY, 15));
			}
			if (this.type == Type.SHINEPARTICLE) {
				level.add(new ShineParticle(posX, posY, 15));
			}
			if (this.type == Type.FIREBOMBPARTICLE) {
				level.add(new FireBombParticle(posX, posY, 15));
			}
			if (this.type == Type.MANAPARTICLE) {
				level.add(new PowerupParticle(posX, posY, 15, 0xff0000ff));
			}
			if (this.type == Type.HEALTHPARTICLE) {
				level.add(new PowerupParticle(posX, posY, 15, 0xffff0000));
			}
		}
	}

	
}
