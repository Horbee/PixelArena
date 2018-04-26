package engine.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import engine.entity.Entity;
import engine.entity.gameobject.GameObject;
import engine.entity.gameobject.ItemManager;
import engine.entity.gameobject.SolidArea;
import engine.entity.mob.Mob;
import engine.entity.mob.player.Mage;
import engine.entity.mob.player.Player;
import engine.entity.particles.Particle;
import engine.entity.projectile.FrostProjectile;
import engine.entity.projectile.Projectile;
import engine.graphics.Screen;
import engine.level.tile.AnimatedTile;
import engine.level.tile.Tile;
import engine.util.Util;
import engine.util.Vector2i;

public class Level {

	/*public static Level nexus = new TmxLevel("/data/level/nexusmini.xml"); 
	public static Level test = new TestLevel();*/
	
	protected int width, height;
	protected int[] tiles;
	protected int[] objects;

	public ItemManager im = new ItemManager();
	public boolean lightEnabled = false;
	public static boolean fadeOut, fadeIn;
	public static int brightness;

	public List<AnimatedTile> animatedTiles = new ArrayList<AnimatedTile>();

	public List<Entity> yBuffer = new ArrayList<Entity>();
	public List<Entity> entities = new ArrayList<Entity>();
	public List<GameObject> gameobjects = new ArrayList<GameObject>();
	public List<SolidArea> solidAreas = new ArrayList<SolidArea>();
	public List<Projectile> projectiles = new ArrayList<Projectile>();
	public List<Particle> particles = new ArrayList<Particle>();

	protected Comparator<Entity> depthSorter = new Comparator<Entity>() {
		public int compare(Entity e0, Entity e1) {
			if (e0.getPosY() > e1.getPosY()) return +1;
			else if (e0.getPosY() < e1.getPosY()) return -1;
			return 0;
		}
	};

	public Level() {
		loadLevel();
	}
	
	protected void loadLevel() {
	}
	
	public void spawnPlayer() {
	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 5;
			int yt = (y - c / 2 * size + yOffset) >> 5;
			if (getTile(xt, yt).solid()) solid = true;
		}
		return solid;
	}

	public boolean entitiyCollision(Entity eIN, int x, int y) {
		for (int i = 1; i < entities.size(); i++) {
			Entity e = entities.get(i);
			int ex = (int) e.getPosX();
			int ey = (int) e.getPosY();
			int ex2 = (int) e.getPosX() + 32;
			int ey2 = (int) e.getPosY() + 32;
			for (int c = 0; c < 4; c++) {
				int nx = x + c % 2 * 16;
				int ny = y + c / 2 * 16;
				if (nx > ex && nx < ex2 && ny > ey && ny < ey2) {
					// e.isDead = true;
					int dmg = ((Projectile) eIN).getDamage();
					if (eIN instanceof FrostProjectile) {
						if (e instanceof Mob) ((Mob) e).speed = 0.5;
					} else {
						if (e instanceof Mob) ((Mob) e).health -= dmg;
					}
					((Projectile) eIN).setDmgDone(dmg);
					return true;
				}
			}
		}
		return false;
	}

	public boolean playerCollision(int x, int y) {
		Player player = getPlayer();
		int ex = (int) player.getPosX();
		int ey = (int) player.getPosY();
		int ex2 = (int) player.getPosX() + 32;
		int ey2 = (int) player.getPosY() + 32;
		for (int c = 0; c < 4; c++) {
			int nx = x + c % 2 * 16;
			int ny = y + c / 2 * 16;
			if (nx > ex && nx < ex2 && ny > ey && ny < ey2) {
				// player.isDead = true;
				return true;
			}
		}
		return false;
	}

	public boolean gameobjectCollision(Entity e, int x, int y) {
		for (int i = 0; i < gameobjects.size(); i++) {
			GameObject obj = gameobjects.get(i);
			int ex = (int) obj.getPosX();
			int ey = (int) obj.getPosY();
			int ex2 = (int) obj.getPosX() + obj.currentSprite.width;
			int ey2 = (int) obj.getPosY() + obj.currentSprite.height;
			for (int c = 0; c < 4; c++) {
				int nx = x + c % 2 * 16;
				int ny = y + c / 2 * 16;
				if (nx > ex && nx < ex2 && ny > ey && ny < ey2) {
					// if (e instanceof Projectile) ((Projectile) e).takeDamage(obj);
					obj.damage(10);
					return true;
				}
			}
		}
		return false;
	}

	public void render(double xScroll, double yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		double x0 = xScroll / 32;
		double x1 = (x0 + screen.width / 32) + 32;
		double y0 = yScroll / 32;
		double y1 = (yScroll + screen.height + 32) / 32; // System.out.println("Boundaries: " + x0 + "->" + x1 + ", " + y0 + "->" + y1);
		for (int y = (int) y0; y < y1; y++) {
			for (int x = (int) x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
				// if (getObject(x, y) != null) getObject(x, y).render(x, y, screen);
			}
		}
	}

	protected void renderEntities(Screen screen) {
		for (int i = 0; i < yBuffer.size(); i++) {
			yBuffer.get(i).render(screen);
		}
		for (int i = 0; i < particles.size(); i++) {
			Entity tempObject = particles.get(i);
			tempObject.render(screen);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			Entity tempObject = projectiles.get(i);
			tempObject.render(screen);
		}
	}

	public void update() {

		// System.out.println(0 / 2 * 16);

		yBuffer.clear();

		for (int i = 0; i < entities.size(); i++) {
			yBuffer.add(entities.get(i));
		}
		for (int i = 0; i < gameobjects.size(); i++) {
			yBuffer.add(gameobjects.get(i));
		}

		Collections.sort(yBuffer, depthSorter);
		// System.out.println(sortingList.size());

		for (int i = 0; i < animatedTiles.size(); i++) {
			animatedTiles.get(i).update();
		}

		for (int i = 0; i < entities.size(); i++) {
			Entity tempObject = entities.get(i);
			if (tempObject.isRemoved()) entities.remove(i);
			else tempObject.update(); // System.out.println(entities.size());
		}

		for (int i = 0; i < projectiles.size(); i++) {
			Entity tempObject = projectiles.get(i);
			tempObject.update();
			// System.out.println(projectiles.size());
		}

		for (int i = 0; i < particles.size(); i++) {
			Entity tempObject = particles.get(i);
			if (tempObject.isRemoved()) particles.remove(i);
			else tempObject.update();
			// System.out.println(projectiles.size());
		}

		for (int i = 0; i < gameobjects.size(); i++) {
			Entity tempObject = gameobjects.get(i);
			if (tempObject.isRemoved()) gameobjects.remove(i);
			else tempObject.update();
			// System.out.println(projectiles.size());
		}
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		if (tiles[x + y * width] == Tile.COl_SOLID_EMPTY) return Tile.solid_empty;
		if (tiles[x + y * width] == Tile.COl_BRICK) return Tile.brick;
		if (tiles[x + y * width] == Tile.COl_BRICK2) return Tile.breakable;
		if (tiles[x + y * width] == Tile.COl_DANGER) return Tile.danger;
		if (tiles[x + y * width] == Tile.COl_ELEVATOR) return Tile.elevator;
		if (tiles[x + y * width] == Tile.COl_FLOOR1) return Tile.floor1;
		if (tiles[x + y * width] == Tile.COl_FLOOR2) return Tile.floor2;
		if (tiles[x + y * width] == Tile.COl_FLOOR2_BROKEN) return Tile.floor2_broken;
		if (tiles[x + y * width] == Tile.COl_FLOOR3) return Tile.floor3;
		if (tiles[x + y * width] == Tile.COl_FLOOR_WOOD) return Tile.floor_wood;
		if (tiles[x + y * width] == Tile.COl_FSTONE) return Tile.fstone;
		if (tiles[x + y * width] == Tile.COl_WOOD1) return Tile.wood1;
		if (tiles[x + y * width] == Tile.COl_WOOD2) return Tile.wood2;
		if (tiles[x + y * width] == Tile.COl_BROKENWOOD1) return Tile.brokenwood1;
		if (tiles[x + y * width] == Tile.COl_BROKENWOOD2) return Tile.brokenwood2;
		if (tiles[x + y * width] == Tile.COl_DOOR1) return Tile.door1;
		if (tiles[x + y * width] == Tile.COl_DOOR2) return Tile.door2;
		return Tile.voidTile;
	}

	public GameObject getObject(int x, int y) {
		if (objects[x + y * width] == GameObject.COl_CATAGO_OSZLOP) return GameObject.oszlop;
		if (objects[x + y * width] == GameObject.COl_CATAGO_OSZLOPTORT) return GameObject.oszloptort;
		if (objects[x + y * width] == GameObject.COl_CATAGO_TABLA) return GameObject.tabla;
		if (objects[x + y * width] == GameObject.COl_CATAGO_TABLA_QUESTMARK) return GameObject.tabla_questmark;
		return null;
	}

	public void add(Entity e) {
		e.init(this);
		if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof SolidArea) {
			solidAreas.add((SolidArea) e);
		}else if (e instanceof GameObject) {
			gameobjects.add((GameObject) e);
		} else if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if(e instanceof Player) {
			if(entities.size() > 0 && entities.get(0) instanceof Player)
				entities.remove(0);
			entities.add(0, e);
		} else {
			entities.add(e);
		}
	}
	
	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, Util.getDistance(start, goal));
		openList.add(current);

		while (openList.size() > 0) {
			Collections.sort(openList, Util.nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}

			openList.remove(current);
			closedList.add(current);

			for (int i = 0; i < 9; i++) {
				if (i == 4) continue;
				if (i == 0 || i == 2 || i == 6 || i == 8) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if (at == null) continue;
				if (at.solid()) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				// double gCost = current.gCost + (Util.getDistance(current.tile, a) == 1 ? 1 : 0.95);
				double gCost = current.gCost + Util.getDistance(current.tile, a);
				double hCost = Util.getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (Util.vecInList(closedList, a) && gCost >= node.gCost) continue;
				if (!Util.vecInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}

	public Player getPlayer() {
		return (Player) entities.get(0);
	}

	public void removeEntity(Entity e) {
		this.entities.remove(e);
	}

}
