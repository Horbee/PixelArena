package engine.entity.projectile;

import engine.entity.Entity;
import engine.graphics.Sprite;
import engine.util.Util;

public class Projectile extends Entity {

	protected Sprite sprite;
	protected double posX, posY;
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected double nx, ny;
	protected double range, speed;
	protected boolean drawDmg = false;
	protected int dmgDone = 0;
	protected int time = 0;

	
	public Projectile(double posX, double posY, double dir) {
		xOrigin = posX;
		yOrigin = posY;
		angle = dir;
		this.posX = posX;
		this.posY = posY;

	}
	
	public int getDamage() {
		if (level.getPlayer().getWeapon() != null) {
			int min = level.getPlayer().getWeapon().getMinDmg();
			int max = level.getPlayer().getWeapon().getMaxDmg();
			int dmg = min + rnd.nextInt(max - min);
			dmg += level.getPlayer().dmgModifier;
			return dmg;
		}
		return 0;
	}

	protected double distance() {
		return Util.getDistance(xOrigin, yOrigin, posX, posY);
	}

	public Sprite getSprite() {
		return sprite;
	}

	public int getSpriteWidth() {
		return sprite.width;
	}

	public int getSpriteHeight() {
		return sprite.height;
	}

	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	public void setDmgDone(int dmgDone) {
		this.dmgDone = dmgDone;
	}
}
