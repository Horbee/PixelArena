package engine.entity.mob.player;

import engine.Game;
import engine.graphics.AnimatedSprite;
import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.graphics.SpriteSheet;
import engine.input.Keyboard;
import engine.input.Mouse;

public class Archer extends Player {

	public AnimatedSprite up = new AnimatedSprite(SpriteSheet.archer_up, 32, 32, 3);
	public AnimatedSprite left = new AnimatedSprite(SpriteSheet.archer_left, 32, 32, 3);
	public AnimatedSprite right = new AnimatedSprite(SpriteSheet.archer_right, 32, 32, 3);
	public AnimatedSprite down = new AnimatedSprite(SpriteSheet.archer_down, 32, 32, 3);
	public AnimatedSprite bow_shoot = new AnimatedSprite(SpriteSheet.bow_shoot_sheet, 32, 32, 4);

	protected int arrowX = 0;
	protected boolean drawArrow = false;

	
	public Archer(int posX, int posY, Keyboard input) {
		super(posX, posY, input);
	
	}
	
	public void update() {
		updateShootingArrow();
	}
	
	protected void updateShootingArrow() {

		if (charUI.slot_weapon.hasItem) {
			if (Mouse.getButton() == 1) {
				if (fireRate <= 0 && Mouse.isMouseOnObject() == false) {
					bow_shoot.playOnce();
					if (arrowX < 12) arrowX++;
					drawArrow = true;
				}
			} else {
				bow_shoot.resetFrame();
				arrowX = 0;
				bow_shoot.oncePlayed = false;
				drawArrow = false;
			}

			if (Mouse.mouseReleased(1)) {
				System.out.println("MOST");
				double dx = Mouse.getMouseX() - Game.getWindowWidth() / 2;
				double dy = Mouse.getMouseY() - Game.getWindowHeight() / 2;
				double dir = Math.atan2(dy, dx);
				if (mana > 10) {
					shoot(posX, posY, dir);
					mana -= 10;
				}
				fireRate = charUI.slot_weapon.getItem().getFireRate();
			}
		}
	}
	
	public void render(Screen screen) {
		sprite = animSprite.getSprite();

		if (dir == 2) screen.renderSprite(Sprite.quiver, (int) posX + 10, (int) posY - 5, true);
		screen.renderCharacter((int) posX, (int) posY, this);
		if (dir == 0) screen.renderSprite(Sprite.quiver, (int) posX + 5, (int) posY, true);

		if (dir == 3) {
			screen.renderSprite(bow_shoot.getSprite(), (int) posX + 8, (int) posY + 8, true);
			if (drawArrow) screen.renderSprite(Sprite.flip(Sprite.proj_arrow), (int) (posX - 6 + arrowX), (int) (posY + 17), true);
		}

		if (dir == 1) {
			screen.renderSprite(Sprite.flip(bow_shoot.getSprite()), (int) posX - 10, (int) posY + 8, true);
			if (drawArrow) screen.renderSprite(Sprite.proj_arrow, (int) (posX - arrowX), (int) (posY + 17), true);
		}
	}

}
