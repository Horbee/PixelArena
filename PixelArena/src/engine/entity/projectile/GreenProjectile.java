package engine.entity.projectile;

import engine.Game;
import engine.entity.Spawner;
import engine.entity.gameobject.GameObject;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public class GreenProjectile extends Projectile {

	public GreenProjectile(double posX, double posY, double dir) {
		super(posX, posY, dir);
		range = 200;
		speed = 4.5;
		sprite = Sprite.proj_green;
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
		if (!drawDmg) screen.renderProjectile((int) posX, (int) posY, this);
		else Game.font.render((int) posX, (int) posY, "-" + Integer.toString(dmgDone), 0xffff0000, true, screen);
	}

	protected void move() {
		posX += nx;
		posY += ny;

		// if (level.gameobjectCollision(this, (int) posX, (int) posY)) {
		if (gameobjectCollision((int) posX, (int) posY)) {
			new Spawner((int) posX, (int) posY, Spawner.Type.EMERALDPARTICLE, 20, level);
			//new Spawner((int) posX, (int) posY, Spawner.Type.STONEPARTICLE, 10, level);
			// remove();
			drawDmg = true;
		}

		if (level.entitiyCollision(this, (int) posX, (int) posY)) {
			// remove();
			drawDmg = true;
		}

		if (level.tileCollision((int) (posX + nx), (int) (posY + ny), 14, 15, 15)) {
			new Spawner((int) posX, (int) posY, Spawner.Type.EMERALDPARTICLE, 20, level);
			remove();
		}

		if (distance() > range) remove();
	}

	private boolean gameobjectCollision(int x, int y) {
		for (int i = 0; i < level.gameobjects.size(); i++) {
			GameObject obj = level.gameobjects.get(i);
			int ex = (int) obj.getPosX();
			int ey = (int) obj.getPosY();
			int ex2 = (int) obj.getPosX() + obj.currentSprite.width;
			int ey2 = (int) obj.getPosY() + obj.currentSprite.height;
			for (int c = 0; c < 4; c++) {
				int nx = x + c % 2 * 16;
				int ny = y + c / 2 * 16;
				if (nx > ex && nx < ex2 && ny > ey && ny < ey2) {
					obj.damage(dmgDone = getDamage());
					return true;
				}
			}
		}
		return false;
	}
}
