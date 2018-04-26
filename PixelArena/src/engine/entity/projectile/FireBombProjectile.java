package engine.entity.projectile;

import engine.Game;
import engine.entity.Spawner;
import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.util.Util;

public class FireBombProjectile extends Projectile {

	private int time = 0;
	private double za, zz = 2.0;

	public FireBombProjectile(double posX, double posY, double dir) {
		super(posX, posY, dir);
		range = 200;
		speed = 4;
		sprite = Sprite.proj_firebomb;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}

	public void update() {
		if (!drawDmg) move();
		else {
			time++;
			posY--;
			if (time % 45 == 0) remove();
		}
	}

	public void render(Screen screen) {
		if (!drawDmg) screen.renderProjectile((int) posX, (int) posY - (int) zz, this);
		else Game.font.render((int) posX, (int) posY, "-" + Integer.toString(dmgDone), 0xffff0000, true, screen);
	}

	protected void move() {
		posX += nx;
		posY += ny;

		za -= 0.05;
		zz += za;

		if (zz < -50) {
			checkHit();
			remove();
		}

		if (level.gameobjectCollision(this, (int) posX, (int) posY - (int) zz)) {
			checkHit();
		}

		if (level.tileCollision((int) (posX + nx), (int) (posY + ny) - (int) zz, 14, 15, 15)) {
			checkHit();
		}

		/*if (distance() > range) {
			// checkHit();
		}*/
	}

	private void checkHit() {
		new Spawner((int) posX, (int) posY - 5 - (int) zz, Spawner.Type.FIREBOMBPARTICLE, 1, level);
		if (Util.getDistance(posX, posY - zz, level.getPlayer().getPosX(), level.getPlayer().getPosY()) < 50) {
			level.getPlayer().health -= 20;
			drawDmg = true;
			setDmgDone(20);
		} else {
			remove();
		}
	}
}
