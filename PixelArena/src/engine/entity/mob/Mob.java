package engine.entity.mob;

import engine.entity.Entity;
import engine.entity.gameobject.GameObject;
import engine.entity.gameobject.Portal;
import engine.entity.gameobject.SolidArea;
import engine.entity.projectile.EnemyProjectile;
import engine.entity.projectile.Projectile;
import engine.graphics.AnimatedSprite;
import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.util.Util;
import engine.util.Vector2i;

public abstract class Mob extends Entity {

	public double speed = 1.0;

	public int health = -1;
	public int base_health = -1;
	public int max_health = -1; // Maximum élet ami összejön az alapéletbõl + kiegbõl
	public int mana = -1;
	public int base_mana = -1;
	public int max_mana = -1;
	public int hp_regen_time = 60;
	public int mana_regen_time = 10;
	public int defense = -1;
	public String dmg = "";

	protected int xa, ya;
	protected Sprite sprite;
	protected int dir = 0;
	protected int fireRate;
	protected AnimatedSprite animSprite = null;

	public void move(double xa, double ya) {
		/*
		 * if (xa != 0 && ya != 0) { move(xa, 0); move(0, ya); return; }
		 */

		if (xa > 0)
			dir = 1; // jobb
		if (xa < 0)
			dir = 3; // bal
		if (ya > 0)
			dir = 2; // le
		if (ya < 0)
			dir = 0; // fel

		while (xa != 0) {
			if (Math.abs(xa) > 1) {
				if (!collision(Util.myabs(xa), 0)) {
					posX += Util.myabs(xa);
				}
				xa -= Util.myabs(xa);
			} else {
				if (!collision(Util.myabs(xa), 0)) {
					posX += xa;
				}
				xa = 0;
			}
		}
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!collision(0, Util.myabs(ya))) {
					posY += Util.myabs(ya);
				}
				ya -= Util.myabs(ya);
			} else {
				if (!collision(0, Util.myabs(ya))) {
					posY += ya;
				}
				ya = 0;
			}
		}
	}

	protected void moveRandomly(int time) {
		if (time % (rnd.nextInt(40) + 30) == 0) {
			xa = rnd.nextInt(3) - 1;
			ya = rnd.nextInt(3) - 1;
			if (rnd.nextInt(3) == 0) {
				xa = 0;
				ya = 0;
			}
		}
	}

	protected void shootPlayer(double distanceIN) {
		if (fireRate > 0)
			fireRate--;
		double dx = level.getPlayer().getPosX() - posX;
		double dy = level.getPlayer().getPosY() - posY;
		double distance = Math.sqrt((dx * dx) + (dy * dy));
		if (distance > distanceIN)
			return;

		double dir = Math.atan2(dy, dx);
		if (fireRate <= 0) {
			shoot(posX, posY + 10, dir);
			fireRate = 10;
		}
	}

	public abstract void render(Screen screen);

	public abstract void update();

	protected void shoot(double x, double y, double dir) {
		// Game.audio.sfx_scratch.Play();
		Projectile p = new EnemyProjectile(x, y, dir);
		level.add(p);
	}

	private boolean collision(double xa, double ya) {
		boolean solid = false;
		// for Tiles
		for (int c = 0; c < 4; c++) {
			// int xt = ((posX + xa) + c % 2 * 12 + 8) >> 5;
			// int yt = ((posY + ya) + c / 2 * 12 + 17) >> 5;
			int xt = (int) ((posX + xa) + c % 2 * (getSprite().width - 1)) >> 5;
			int yt = (int) ((posY + ya) + c / 2 * (getSprite().height - 1)) >> 5;
			if (level.getTile(xt, yt).solid() == true)
				solid = true;
		}
		// for GameObjects
		for (int i = 0; i < level.gameobjects.size(); i++) {
			GameObject obj = level.gameobjects.get(i);
			if (obj instanceof Portal)
				continue;
			int ex = (int) obj.getPosX();
			int ey = (int) obj.getPosY();
			int ex2 = (int) obj.getPosX() + obj.currentSprite.width;
			int ey2 = (int) obj.getPosY() + obj.currentSprite.height;
			for (int c = 0; c < 4; c++) {
				int nx = (int) ((posX + xa) + c % 2 * 12 + 8);
				int ny = (int) ((posY + ya) + c / 2 * 12 + 17);
				if (nx > ex && nx < ex2 && ny > ey && ny < ey2) {
					solid = true;
				}
			}
		}

		// for SolidAreas
		for (SolidArea obj : level.solidAreas) {
			int ex = (int) obj.getPosX();
			int ey = (int) obj.getPosY();
			int ex2 = (int) ex + obj.getWidth();
			int ey2 = (int) ey + obj.getHeight();
			for (int c = 0; c < 4; c++) {
				int nx = (int) ((posX + xa) + c % 2 * 12 + 8);
				int ny = (int) ((posY + ya) + c / 2 * 12 + 17);
				if (nx > ex && nx < ex2 && ny > ey && ny < ey2) {
					solid = true;
				}
			}
		}

		return solid;
	}

	protected double getPlayerDistance() {
		return Util.getDistance(new Vector2i((int) posX, (int) posY),
				new Vector2i((int) level.getPlayer().getPosX(), (int) level.getPlayer().getPosY()));
	}

	public Sprite getSprite() {
		return sprite;
	}

}
