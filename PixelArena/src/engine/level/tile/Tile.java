package engine.level.tile;

import engine.graphics.AnimatedSprite;
import engine.graphics.Screen;
import engine.graphics.Sprite;

public class Tile {

	public int life = 60;

	public Sprite sprite;
	public int x, y;
	public boolean solid = false;

	public static Tile solid_empty = new BrickTile(null);;
	public static Tile brick = new BrickTile(Sprite.brick);
	public static Tile breakable = new BreakableBrickTile(Sprite.brick2);
	public static Tile floor1 = new FloorTile(Sprite.floor1);
	public static Tile floor2 = new FloorTile(Sprite.floor2);
	public static Tile floor2_broken = new FloorTile(Sprite.floor2_broken);
	public static Tile floor3 = new FloorTile(Sprite.floor3);
	public static Tile floor_wood = new FloorTile(Sprite.floor_wood);
	public static Tile fstone = new FloorTile(Sprite.stone);
	public static Tile wood1 = new FloorTile(Sprite.wood1);
	public static Tile wood2 = new FloorTile(Sprite.wood2);
	public static Tile brokenwood1 = new BrickTile(Sprite.brokenwood1);
	public static Tile brokenwood2 = new BrickTile(Sprite.brokenwood2);
	public static Tile door1 = new DoorTile(Sprite.door1);
	public static Tile door2 = new DoorTile(Sprite.door2);
	public static Tile elevator = new ElevatorTile(Sprite.elevator);
	public static Tile danger = new DangerTile(Sprite.danger);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	// UNIVERSAL COLORS HERE
	public static final int COl_WATER = 0xff103996;
			
	public static final int COl_SOLID_EMPTY = 0xff51463e;
	public static final int COl_BRICK = 0xff000000;
	public static final int COl_BRICK2 = 0xff615739;
	public static final int COl_FLOOR1 = 0xff3f3f4c;
	public static final int COl_FLOOR2 = 0xff97996d;
	public static final int COl_FLOOR2_BROKEN = 0xff2626ab;
	public static final int COl_FLOOR3 = 0xffcab278;
	public static final int COl_FLOOR_WOOD = 0xff542505;
	public static final int COl_FSTONE = 0xff898080;
	public static final int COl_WOOD1 = 0xff99a101;
	public static final int COl_WOOD2 = 0xff5d6108;
	public static final int COl_BROKENWOOD1 = 0xff73238d;
	public static final int COl_BROKENWOOD2 = 0xff31083e;
	public static final int COl_ELEVATOR = 0xff969c16;
	public static final int COl_DANGER = 0xfff3ff00;
	public static final int COl_DOOR1 = 0xff564e18;
	public static final int COl_DOOR2 = 0xff9b8c29;

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Tile(Sprite sprite, boolean solid) {
		this.sprite = sprite;
		this.solid = solid;
	}

	public Tile(AnimatedSprite animSprite) {
		this.sprite = animSprite.getSprite();
	}

	public void update() {
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x * 32, y * 32, this);
	}

	public boolean solid() {
		return solid;
	}

	public boolean breakable() {
		return false;
	}
}
