package engine.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import engine.State;
import engine.entity.gameobject.Cave;
import engine.entity.gameobject.Chest;
import engine.entity.gameobject.ItemManager;
import engine.entity.gameobject.MinorHealth;
import engine.entity.gameobject.MinorMana;
import engine.entity.gameobject.Portal;
import engine.entity.mob.Orc;
import engine.entity.mob.player.PlayerManager;
import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.graphics.SpriteSheet;

public class TestLevel extends Level {

	private int stage = 0;

	public TestLevel() {
		super();

		lightEnabled = false;

		// player = new Mage(2 * 32, 2 * 32, Game.getKey());
		add(PlayerManager.getPlayer());

		add(new Cave(3 * 32, 3 * 32, 1));

		Chest chest = new Chest(340, 128).addItem(ItemManager.items.get(0));
		chest.addItem(new MinorMana());
		chest.addItem(new MinorHealth());
		add(chest);

		add(new Orc(70, 500));
		add(new Orc(70, 500));
		// add(new Chest(340, 128).addItem(ItemManager.items.get(0)));
		// im.addItem("Emerald Staff", player.charUI.cont_bag);
		// im.addItem("Emerald Bow", player.charUI.cont_bag);
		// add(new Devil(70, 500));
		// add(new Boss(70, 500));
		// add(new MrBone(70, 500));

	}

	public void spawnPlayer() {
		getPlayer().setPosition(2 * 32, 2 * 32);

		// reset the level
		stage = 0;
		for (int i = 1; i < entities.size(); i++) {
			entities.remove(i);
		}
		add(new Orc(70, 500));
		add(new Orc(70, 500));
	}

	protected void loadLevel() {
		try {
			// LOADING TILES
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource("/data/level/test_level.png"));
			width = image.getWidth();
			height = image.getHeight();
			tiles = new int[width * height];
			image.getRGB(0, 0, width, height, tiles, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		super.update();
		getPlayer().charUI.update();

		if (entities.size() == 1) {
			switch (stage) {
			case 0:
				add(new Orc(70, 450));
				add(new Portal(70, 500, 1));
				add(new Portal(70, 550, 2));
				stage = 1;
				break;
			case 1:
				add(new Portal(70, 450, 3));
				stage = -1;
				break;
			}
		}
	}

	public void render(double xScroll, double yScroll, Screen screen) {
		super.render(xScroll, yScroll, screen);
		screen.renderSprite(Sprite.label_grab, 300, 100, true);

		renderEntities(screen);
		if (!(State.getState() == State.MENU))
			getPlayer().charUI.render(screen);
	}

}
