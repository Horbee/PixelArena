package engine.level;

import java.awt.Color;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import engine.State;
import engine.entity.gameobject.BigSimpleObject;
import engine.entity.gameobject.Booster;
import engine.entity.gameobject.Cave;
import engine.entity.gameobject.Chest;
import engine.entity.gameobject.ItemManager;
import engine.entity.gameobject.MinorHealth;
import engine.entity.gameobject.MinorMana;
import engine.entity.mob.player.Mage;
import engine.entity.mob.player.Player;
import engine.entity.mob.player.PlayerManager;
import engine.graphics.MyFont;
import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.graphics.SpriteSheet;
import engine.level.tile.Tile;

public class TmxLevel extends Level {

	private Sprite[] sprites;
	private HashMap<Integer, Tile> tileList;

	BigSimpleObject door_down;
	int chestY = 125;
	private int time = 0;

	public TmxLevel(String path) {
		loadLevel(path);
		// Setting the PLAYER in MENU

		add(new Cave(10 * 32, 9 * 32, 0));

		// add(PlayerManager.getPlayer());

		// add(new BigSimpleObject(Sprite.nexus_door, 608, 0));
		// add(new BigSimpleObject(Sprite.statue, 575, 190));
		// add(new BigSimpleObject(Sprite.statue, 675, 190));
		/*
		 * add(new BigSimpleObject(Sprite.nexus_door2_right, 8 * 32, 17 * 32)); add(new
		 * BigSimpleObject(Sprite.nexus_door2_left, 30 * 32, 17 * 32)); door_down = new
		 * BigSimpleObject(Sprite.nexus_door2_inverse, 18 * 32, 30 * 32);
		 * add(door_down);
		 */
		
		Chest chest = new Chest(625, 225);
		chest.addItem(new MinorMana());
		chest.addItem(new MinorHealth());
		chest.addItem(new Booster());
		add(chest);

		// add(new Orc(550, 1170));
		// add(new Orc(650, 1170));
	}

	public void spawnPlayer() {
		getPlayer().setPosition(10 * 32, 6 * 32);
		Chest chest = new Chest(625, chestY + 32);
		chest.addItem(new MinorMana());
		chest.addItem(new MinorHealth());
		chest.addItem(new Booster());
		add(chest);
	}

	protected void loadLevel(String xml_path) {

		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(this.getClass().getResourceAsStream(xml_path));
			doc.normalize();

			NodeList rootNodes = doc.getElementsByTagName("map");
			Node rootNode = rootNodes.item(0);
			width = Integer.parseInt(((Element) rootNode).getAttribute("width"));
			height = Integer.parseInt(((Element) rootNode).getAttribute("height"));
			tiles = new int[width * height];

			NodeList imageNodes = doc.getElementsByTagName("image");
			Node imageNode = imageNodes.item(0);
			String path = ((Element) imageNode).getAttribute("source");

			NodeList solidNodes = doc.getElementsByTagName("solid");
			Node solidNode = solidNodes.item(0);
			String solids = ((Element) solidNode).getTextContent();
			System.out.println(solids);

			/*
			 * NodeList solidArea = doc.getElementsByTagName("objectgroup"); Node solidNode
			 * = solidNodes.item(0); String solids = ((Element) solidNode).getTextContent();
			 * System.out.println(solids);
			 */

			NodeList dataNodes = doc.getElementsByTagName("data");
			Node dataNode = dataNodes.item(0);
			String ints = ((Element) dataNode).getTextContent();

			// System.out.println(ints);
			// System.exit(0);

			ints = ints.trim();
			String[] strArray = ints.split(",");
			for (int i = 0; i < strArray.length; i++) {
				if (strArray[i].startsWith("\n")) {
					strArray[i] = strArray[i].replace("\n", "");
				}
				tiles[i] = Integer.parseInt(strArray[i]);
			}

			loadSprites(new SpriteSheet("/data/sheets/" + path, 32, 32));
			generateTiles(solids);
			System.out.println("Tmx level loaded!");
		} catch (Exception e) {
			e.printStackTrace();
			// MyError.show("XML file error!", e);
		}

	}

	private void loadSprites(SpriteSheet sheet) {
		int num_X = sheet.getSheetWidth() / sheet.SPRITE_WIDTH;
		int num_Y = sheet.getSheetHeight() / sheet.SPRITE_HEIGHT;

		sprites = new Sprite[num_X * num_Y];
		for (int y = 0; y < num_Y; y++) {
			for (int x = 0; x < num_X; x++) {
				sprites[x + y * num_X] = new Sprite(32, x, y, sheet);
			}

		}
	}

	private void generateTiles(String solids) {
		String[] strArray = solids.split(",");
		int[] solid_id = new int[strArray.length];

		for (int i = 0; i < strArray.length; i++) {
			solid_id[i] = Integer.parseInt(strArray[i]);
		}

		for (int i = 0; i < solid_id.length; i++) {
			System.out.println(solid_id[i]);
		}

		tileList = new HashMap<Integer, Tile>();
		for (int i = 0; i < tiles.length; i++) {
			boolean solid = false;

			if (!tileList.containsKey(tiles[i])) {
				for (int j = 0; j < solid_id.length; j++) {
					if (solid_id[j] == tiles[i]) {
						solid = true;
						break;
					}
				}

				int spriteID = tiles[i] - 1;
				if (spriteID == -1) {
					tileList.put(tiles[i], Tile.voidTile);
				} else {
					tileList.put(tiles[i], new Tile(sprites[tiles[i] - 1], solid));

				}
			}
		}

	}

	public void update() {
		super.update();
		getPlayer().charUI.update();
		// System.out.println(player.getPosX() + ", " + player.getPosY());
		// System.out.println("GO: " + gameobjects.size());

		/*
		 * if (entities.size() == 1) { switch (stage) { case 0: add(new Orc(70, 450));
		 * add(new Portal(70, 500, 1)); add(new Portal(70, 550, 2)); stage = 1; break;
		 * case 1: add(new Portal(70, 450, 3)); stage = -1; break; } }
		 */

		if (State.getState() == State.GAME)
			time++;
		// System.out.println(player.getPosX() + ", " + player.getPosY());
	}

	public void render(double xScroll, double yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		double x0 = xScroll / 32;
		double x1 = x0 + (screen.width / 32) + 2;
		double y0 = yScroll / 32;
		double y1 = y0 + (screen.height + 32) + 2; // System.out.println("Boundaries:
													// " + x0 + "->" +
													// x1 + ", " + y0 +
													// "->" + y1);
		for (int y = (int) y0; y < y1; y++) {
			for (int x = (int) x0; x < x1; x++) {
				if (x < 0 || y < 0 || x >= width || y >= height)
					continue;
				tileList.get(tiles[x + y * width]).render(x, y, screen);
			}
		}
		if (!(State.getState() == State.MENU)) {
			if (time < 180) {
				screen.drawText("Press 'C'", 445, 65, MyFont.flashrogersstraight, Color.white, false);
				screen.drawText("Open the Chest with 'E'", 320, 100, MyFont.flashrogersstraight, Color.white, false);
			}
		}

		renderEntities(screen);

		if (!(State.getState() == State.MENU))
			getPlayer().charUI.render(screen);
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.voidTile;
		return tileList.get(tiles[x + y * width]);
	}

}
