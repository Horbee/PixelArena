package engine.entity.mob;

import engine.graphics.AnimatedSprite;
import engine.graphics.Screen;
import engine.graphics.SpriteSheet;

public class Dummy extends Mob {

	/*private AnimatedSprite up = new AnimatedSprite(SpriteSheet.robot_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.robot_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.robot_right, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.robot_down, 32, 32, 3);
	private AnimatedSprite dead = new AnimatedSprite(SpriteSheet.robot_dead, 32, 32, 3);*/

	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);

	private boolean walking = false;
	private int anim = 0;

	int time = 0;

	public Dummy(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		health = 100;
		animSprite = down;
	}

	public void update() {
		time++;

		if (!isDead) {

			if (walking) animSprite.update();
			else animSprite.setFrame(1);

			// moveRandomly(time);

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
		}

		if (isDead) {
			// animSprite = dead;
			// animSprite.playOnce();
			// if (animSprite.oncePlayed) {
			remove();
			// }
		}
		sprite  = animSprite.getSprite();

	}

	public void render(Screen screen) {
		screen.renderCharacter((int) posX, (int) posY, this);
		// Debug.drawRect(screen, rect.x, rect.y, rect.width, rect.height, true);
	}

}
