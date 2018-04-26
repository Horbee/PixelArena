package engine.entity.gameobject;

import java.awt.Color;

import engine.Game;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public class Cave extends GameObject {

	private int levelIndex;
	
	public Cave(int posX, int posY, int levelIndex) {
		super(posX, posY);
		this.levelIndex = levelIndex;
		currentSprite = Sprite.cave;
	}

	public void update() {

		if (isSelected()) {
			System.out.println("Teleporting...");
			Game.changeLevel(levelIndex);
		}

	}

	public void render(Screen screen) {
		screen.renderSprite(Sprite.cave, (int) posX, (int) posY, true);
		screen.drawText(Integer.toString(Game.difficulty), (int) posX * 2 + 22, (int) posY * 2 + 60, screen.font_menu, Color.WHITE, true);
	}
}
