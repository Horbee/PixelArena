package engine.entity.gameobject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import engine.entity.gameobject.container.Container;
import engine.graphics.Sprite;
import engine.graphics.SpriteSheet;

public class ItemManager {

	public static List<Item> items = new ArrayList<Item>();
	protected final static Random rnd = new Random();

	// Item Sprites Here:
	public static SpriteSheet slot_sheet = new SpriteSheet("/data/sheets/itemscontainer.png", 16, 16);
	public static SpriteSheet item_sheet = new SpriteSheet("/data/sheets/items.png", 14, 14);
	public static Sprite[] item = Sprite.split(item_sheet);
	public static Sprite i_details = new Sprite("/data/sprites/details.png");

	public ItemManager() {
		// Reading Item Files;
		readItemFiles("/item/weapon.txt");
	}

	public static String readItemFiles(String path) {
		String result = "";
		ArrayList<String> lines = new ArrayList<String>();

		try {
			// String u = (ItemManager.class.getResource("/data/item/weapon.txt")).toString();
			// System.out.println(Game.class.getClassLoader().getResource("weapon.txt").getPath().replaceAll("%20", " "));
			// FileReader r = new FileReader(Game.class.getClassLoader().getResource("weapon.txt").getPath().replaceAll("%20", " "));
			// File f = new File((ItemManager.class.getResource(path)).toString());
			// String p = (f.toURI().toString()).split("file:/")[1];
			// String p = ((ItemManager.class.getResource(path)).toString());
			// p = p.replace("!", "");
			// Game.game.netText = p;
			// File f = new File(ItemManager.class.getResourceAsStream(path));
			InputStreamReader r = new InputStreamReader(ItemManager.class.getResourceAsStream(path));
			BufferedReader reader = new BufferedReader(r);
			// BufferedReader reader = new BufferedReader(new FileReader("c:/Program Files (x86)/eclipse/workspace/RainOfLead/src/res/item/weapon.txt"));
			String line;
			while ((line = reader.readLine()) != null) {
				if (!line.startsWith("!")) {
					result += line + "\n";
					lines.add(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String name = "";
		int ID = 0;
		int min_dmg = 0;
		int max_dmg = 0;
		int hp = 0;
		int mana = 0;
		int firerate = 0;
		Item.Type ptype = null;
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).toString().startsWith("Name:")) {
				name = lines.get(i).toString().substring(5, lines.get(i).toString().length());
				// System.out.println(name);
			}
			if (lines.get(i).toString().startsWith("SpriteID: ")) {
				ID = Integer.parseInt(lines.get(i).toString().substring(10, lines.get(i).toString().length()));
				// System.out.println(ID);
			}
			if (lines.get(i).toString().startsWith("Ptype: ")) {
				String s = lines.get(i).toString().split(" ")[1];// (7, lines.get(i).toString().length());
				if (s.equals("GreenProjectile")) {
					ptype = Item.Type.GREENPROJECTILE;
				} else if (s.equals("FireProjectile")) {
					ptype = Item.Type.FIREPROJECTILE;
				} else if (s.equals("ArrowProjectile")) {
					ptype = Item.Type.ARROWPROJECTILE;
				}
				// System.out.println(ID);
			}
			if (lines.get(i).toString().startsWith("DMG: ")) {
				// dmg = Integer.parseInt(lines.get(i).toString().substring(5, lines.get(i).toString().length()));
				min_dmg = Integer.parseInt(lines.get(i).toString().split(" ")[1]);
				max_dmg = Integer.parseInt(lines.get(i).toString().split(" ")[2]);
				// System.out.println(dmg);
			}
			if (lines.get(i).toString().startsWith("HP: ")) {
				hp = Integer.parseInt(lines.get(i).toString().substring(4, lines.get(i).toString().length()));
				// System.out.println(hp);
			}
			if (lines.get(i).toString().startsWith("FR: ")) {
				firerate = Integer.parseInt(lines.get(i).split(" ")[1]);
			}
			if (lines.get(i).toString().startsWith("Mana: ")) {
				mana = Integer.parseInt(lines.get(i).split(" ")[1]);
			}
			if (lines.get(i).toString().startsWith("#/")) {
				Item item = new Item(name, ID, min_dmg, max_dmg, firerate, hp, mana, ptype);
				items.add(item);
				// System.out.println("Item added to list: Name: " + name + " dmg: " + min_dmg + "-" + max_dmg + " FR: " + firerate + " hp: " + hp + " type: " + ptype);
			}
		}
		// System.out.println(items);
		return result;
	}

	public void addItem(int id, Container cont) {
		Item temp = items.get(id);
		cont.addItem(temp);
		// System.out.println("item got: " + items.get(id));
	}

	public void addItem(String name, Container cont) {
		for (int j = 0; j < items.size(); j++) {
			if (items.get(j).name.equals(name)) {
				cont.addItem(items.get(j));
				// System.out.println("item got: " + items.get(j));
			}
		}

	}

	public void addRandomItem(int num, Container cont) {
		for (int i = 0; i < num; i++) {
			int id = rnd.nextInt(items.size());
			Item temp = items.get(id);
			cont.addItem(temp);
			// System.out.println("Item added to container! id: " + items.get(id));
		}
	}

}
