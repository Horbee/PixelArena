package engine.entity.mob;

import engine.Game;
import engine.entity.Spawner;
import engine.entity.gameobject.Chest;
import engine.entity.gameobject.MinorMana;
import engine.graphics.AnimatedSprite;
import engine.graphics.Screen;
import engine.graphics.SpriteSheet;

public class Orc extends Mob {

	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.orc_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.orc_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.orc_right, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.orc_down, 32, 32, 3);

	private boolean walking = false;
	private int anim = 0;

	int time = 0;

	public Orc(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		health = 100 * Game.difficulty;
		animSprite = down;
	}

	public void update() {
		time++;

		if (!isDead) {

			if (walking) animSprite.update();
			else animSprite.setFrame(1);

			shootPlayer(160);
			moveRandomly(time);

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
			if (rnd.nextInt(3) == 1) level.add(new Chest((int) posX, (int) posY).addItem(new MinorMana()));//.addRandomItem());
			new Spawner((int) posX + 16, (int) posY + 16, Spawner.Type.EMERALDPARTICLE, 20, level);
			remove();
		}

		sprite = animSprite.getSprite();
	}

	public void render(Screen screen) {
		screen.renderCharacter((int) posX, (int) posY, this);
	}

}
