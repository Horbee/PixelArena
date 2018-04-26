package engine.entity.mob;

import java.util.List;

import engine.Game;
import engine.entity.Spawner;
import engine.entity.projectile.FrostProjectile;
import engine.graphics.AnimatedSprite;
import engine.graphics.Screen;
import engine.graphics.SpriteSheet;
import engine.level.Node;
import engine.util.Util;
import engine.util.Vector2i;

public class MrBone extends Mob {

	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.mrbone_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.mrbone_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.mrbone_right, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.mrbone_down, 32, 32, 3);

	private boolean walking = false;
	private int anim = 0;

	private List<Node> path = null;
	int time = 0;

	public MrBone(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
		health = 250 * Game.difficulty;
		animSprite = down;
	}

	public void update() {
		time++;
		// int xa = 0, ya = 0;

		if (!isDead) {

			if (walking) animSprite.update();
			else animSprite.setFrame(1);

			Vector2i start = new Vector2i((int) posX >> 5, (int) posY >> 5);
			// System.out.println("s.x: " + start.getX() + " s.y: " + start.getY());
			Vector2i goal = new Vector2i((int) (level.getPlayer().getPosX() + 16) >> 5, (int) (level.getPlayer().getPosY() + 16) >> 5);
			// System.out.println("g.x: " + goal.getX() + " g.y: " + goal.getY());
			double dist = Util.getDistance(new Vector2i((int) posX, (int) posY), new Vector2i((int) level.getPlayer().getPosX(), (int) level.getPlayer().getPosY()));
			// System.out.println(dist);

			shootPlayer(150);

			if (dist < 150) {
				if (time % (rnd.nextInt(60) + 60) == 0) frostSkill();
				if (time % 3 == 0) path = level.findPath(start, goal);
			} else {
				path = null;
			}

			if (path != null) {
				xa = 0;
				ya = 0;
				if (path.size() > 0) {
					if (path.get(path.size() - 1).tile.getX() << 5 < posX) xa--;
					if (path.get(path.size() - 1).tile.getX() << 5 > posX) xa++;
					if (path.get(path.size() - 1).tile.getY() << 5 < posY) ya--;
					if (path.get(path.size() - 1).tile.getY() << 5 > posY) ya++;
				}
			} else {
				moveRandomly(time);
			}

			if (anim < 7500) anim++;
			else anim = 0;

			if (ya < 0) {
				dir = 0;
				animSprite = up;
			} else if (ya > 0) {
				dir = 2;
				animSprite = down;
			}
			if (xa < 0) {
				dir = 3;
				animSprite = left;
			} else if (xa > 0) {
				dir = 1;
				animSprite = right;
			}

			if (xa != 0 || ya != 0) {
				move(xa, ya);
				walking = true;
			} else {
				walking = false;
				// animSprite = idle;
			}
			
			if (health < 0) {
				isDead = true;
			}
		}

		if (isDead) {
			new Spawner((int) posX + 16, (int) posY + 16, Spawner.Type.EMERALDPARTICLE, 20, level);
			remove();
		}
		sprite = animSprite.getSprite();
	}

	private void frostSkill() {
		for (int i = 0; i < 8; i++) {
			level.add(new FrostProjectile(posX, posY + 10, Math.toRadians(i * 45)));
		}
	}

	public void render(Screen screen) {
	/*	screen.renderSprite(new Sprite(100, 20, 0xff3b383b), Game.Width() / 2 - 50, 0, false);
		double hp = health * 93 / 100;
		screen.renderHUD(new Sprite(93, 15, 0xff00ff00), (int) hp, Game.Width() / 2 - 46, 3, false);
		screen.drawText("Mr. Bone", Game.getWindowWidth() / 2 - 35, 25, MyFont.diabloFontMini, Color.ORANGE);*/
		screen.renderCharacter((int) posX, (int) posY, this);
	}

}
