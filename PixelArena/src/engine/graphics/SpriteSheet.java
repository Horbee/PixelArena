package engine.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public int[] pixels;
	public final int SIZE;
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int sheetWidth, sheetHeight;
	private Sprite[] sprites;
	
	
	public static SpriteSheet tile_sheet1 = new SpriteSheet("/data/sheets/tilesheet1.png", 128, 128);
	public static SpriteSheet char_sheet = new SpriteSheet("/data/sheets/charctersheet.png", 416, 320);
	public static SpriteSheet monster_sheet = new SpriteSheet("/data/sheets/monster_sheet.png", 288, 384);
	public static SpriteSheet proj_sheet = new SpriteSheet("/data/sheets/projectilesheet.png", 128, 96);
	public static SpriteSheet HUD = new SpriteSheet("/data/sheets/HUD.png", 128, 128);
	public static SpriteSheet interf = new SpriteSheet("/data/sheets/interface.png", 128, 128);
	public static SpriteSheet gameobjects = new SpriteSheet("/data/sheets/gameobjects.png", 256, 256);

	public static SpriteSheet nexus_sheet= new SpriteSheet("/data/sheets/nexus_tiles.png", 32, 32);

	// animated SUBSPRITESHEETS here:

	public static SpriteSheet bow_shoot_sheet = new SpriteSheet(char_sheet, 5, 8, 4, 1, 32, 32);

	public static SpriteSheet archer_idle = new SpriteSheet(char_sheet, 0, 5, 5, 1, 32, 32);
	public static SpriteSheet archer_up = new SpriteSheet(char_sheet, 0, 9, 3, 1, 32, 32);
	public static SpriteSheet archer_down = new SpriteSheet(char_sheet, 0, 6, 3, 1, 32, 32);
	public static SpriteSheet archer_left = new SpriteSheet(char_sheet, 0, 7, 3, 1, 32, 32);
	public static SpriteSheet archer_right = new SpriteSheet(char_sheet, 0, 8, 3, 1, 32, 32);

	public static SpriteSheet player_up = new SpriteSheet(char_sheet, 0, 3, 3, 1, 32, 32);
	public static SpriteSheet player_down = new SpriteSheet(char_sheet, 0, 0, 3, 1, 32, 32);
	public static SpriteSheet player_left = new SpriteSheet(char_sheet, 0, 1, 3, 1, 32, 32);
	public static SpriteSheet player_right = new SpriteSheet(char_sheet, 0, 2, 3, 1, 32, 32);
	public static SpriteSheet player_idle = new SpriteSheet(char_sheet, 0, 4, 5, 1, 32, 32);
	
	public static SpriteSheet mage_up = new SpriteSheet(char_sheet, 10, 3, 3, 1, 32, 32);
	public static SpriteSheet mage_down = new SpriteSheet(char_sheet, 10, 0, 3, 1, 32, 32);
	public static SpriteSheet mage_left = new SpriteSheet(char_sheet, 10, 1, 3, 1, 32, 32);
	public static SpriteSheet mage_right = new SpriteSheet(char_sheet, 10, 2, 3, 1, 32, 32);

	public static SpriteSheet robot_up = new SpriteSheet(char_sheet, 3, 3, 3, 1, 32, 32);
	public static SpriteSheet robot_down = new SpriteSheet(char_sheet, 3, 0, 3, 1, 32, 32);
	public static SpriteSheet robot_left = new SpriteSheet(char_sheet, 3, 1, 3, 1, 32, 32);
	public static SpriteSheet robot_right = new SpriteSheet(char_sheet, 3, 2, 3, 1, 32, 32);
	public static SpriteSheet robot_dead = new SpriteSheet(char_sheet, 6, 0, 1, 3, 32, 32);

	public static SpriteSheet dummy_up = new SpriteSheet(char_sheet, 7, 3, 3, 1, 32, 32);
	public static SpriteSheet dummy_down = new SpriteSheet(char_sheet, 7, 0, 3, 1, 32, 32);
	public static SpriteSheet dummy_left = new SpriteSheet(char_sheet, 7, 1, 3, 1, 32, 32);
	public static SpriteSheet dummy_right = new SpriteSheet(char_sheet, 7, 2, 3, 1, 32, 32);

	public static SpriteSheet mrbone_up = new SpriteSheet(monster_sheet, 0, 3, 3, 1, 32, 32);
	public static SpriteSheet mrbone_down = new SpriteSheet(monster_sheet, 0, 0, 3, 1, 32, 32);
	public static SpriteSheet mrbone_left = new SpriteSheet(monster_sheet, 0, 1, 3, 1, 32, 32);
	public static SpriteSheet mrbone_right = new SpriteSheet(monster_sheet, 0, 2, 3, 1, 32, 32);
	
	public static SpriteSheet orc_up = new SpriteSheet(monster_sheet, 3, 3, 3, 1, 32, 32);
	public static SpriteSheet orc_down = new SpriteSheet(monster_sheet, 3, 0, 3, 1, 32, 32);
	public static SpriteSheet orc_left = new SpriteSheet(monster_sheet, 3, 1, 3, 1, 32, 32);
	public static SpriteSheet orc_right = new SpriteSheet(monster_sheet, 3, 2, 3, 1, 32, 32);
	
	public static SpriteSheet devil_up = new SpriteSheet(monster_sheet, 6, 3, 3, 1, 32, 32);
	public static SpriteSheet devil_down = new SpriteSheet(monster_sheet, 6, 0, 3, 1, 32, 32);
	public static SpriteSheet devil_left = new SpriteSheet(monster_sheet, 6, 1, 3, 1, 32, 32);
	public static SpriteSheet devil_right = new SpriteSheet(monster_sheet, 6, 2, 3, 1, 32, 32);
	
	public static SpriteSheet boss_up = new SpriteSheet(monster_sheet, 0, 5, 3, 1, 64, 64);
	public static SpriteSheet boss_down = new SpriteSheet(monster_sheet, 0, 2, 3, 1, 64, 64);
	public static SpriteSheet boss_left = new SpriteSheet(monster_sheet, 0, 3, 3, 1, 64, 64);
	public static SpriteSheet boss_right = new SpriteSheet(monster_sheet, 0, 4, 3, 1, 64, 64);
	
	public static SpriteSheet doorleft_opening = new SpriteSheet(gameobjects, 0, 2, 5, 1, 32, 32);
	public static SpriteSheet doorright_opening = new SpriteSheet(gameobjects, 0, 3, 5, 1, 32, 32);

	public static SpriteSheet fire_projectile_anim = new SpriteSheet(proj_sheet, 0, 1, 4, 1, 16, 16);
	public static SpriteSheet firebomb_projectile_anim = new SpriteSheet(proj_sheet, 0, 2, 4, 1, 32, 32);
	public static SpriteSheet curse_projectile_anim = new SpriteSheet(proj_sheet, 1, 3, 3, 1, 16, 16);

	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		this.SIZE = -1;
		this.SPRITE_WIDTH = width;
		this.SPRITE_HEIGHT = height;
		load();
	}

	// CREATING SUBSPRITE SHEETS FOR ANIMATION
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSizeWidth, int spriteSizeHeight) {
		int xx = x * spriteSizeWidth;
		int yy = y * spriteSizeHeight;
		int w = width * spriteSizeWidth;
		int h = height * spriteSizeHeight;
		if (width == height) SIZE = width;
		else SIZE = -1;
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		pixels = new int[w * h];
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
			}
		}

		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSizeWidth * spriteSizeHeight];
				for (int y0 = 0; y0 < spriteSizeHeight; y0++) {
					for (int x0 = 0; x0 < spriteSizeWidth; x0++) {
						spritePixels[x0 + y0 * spriteSizeWidth] = pixels[(x0 + xa * spriteSizeWidth) + (y0 + ya * spriteSizeWidth) * SPRITE_WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSizeWidth, spriteSizeHeight);
				sprites[frame++] = sprite;
			}
		}

	}

	public Sprite[] getSprites() {
		return sprites;
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			sheetWidth = image.getWidth();
			sheetHeight = image.getHeight();
			pixels = new int[sheetWidth * sheetHeight];
			image.getRGB(0, 0, sheetWidth, sheetHeight, pixels, 0, sheetWidth);
			System.out.println("SpriteSheet loaded: " + path);
		} catch (IOException e) {
			System.err.println("Error while loading SpriteSheet: " + path);
			e.printStackTrace();
			System.exit(1);
		}
	}

	public int getSheetWidth() {
		return sheetWidth;
	}

	public int getSheetHeight() {
		return sheetHeight;
	}

	public int[] getPixels() {
		return pixels;
	}

}
