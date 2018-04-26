package engine.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	public final int SIZE;
	public int width, height;
	private int x, y;
	protected SpriteSheet sheet;
	public int[] pixels;

	public static Sprite brick = new Sprite(32, 0, 0, SpriteSheet.tile_sheet1);
	public static Sprite brick2 = new Sprite(32, 0, 2, SpriteSheet.tile_sheet1);
	public static Sprite floor1 = new Sprite(32, 2, 0, SpriteSheet.tile_sheet1);
	public static Sprite floor2 = new Sprite(32, 3, 1, SpriteSheet.tile_sheet1);
	public static Sprite floor2_broken = new Sprite(32, 3, 0, SpriteSheet.tile_sheet1);
	public static Sprite floor3 = new Sprite(32, 1, 2, SpriteSheet.tile_sheet1);
	public static Sprite floor_wood = new Sprite(32, 1, 3, SpriteSheet.tile_sheet1);
	public static Sprite stone = new Sprite(32, 0, 3, SpriteSheet.tile_sheet1);
	public static Sprite wood1 = new Sprite(32, 2, 2, SpriteSheet.tile_sheet1);
	public static Sprite wood2 = new Sprite(32, 2, 3, SpriteSheet.tile_sheet1);
	public static Sprite brokenwood1 = new Sprite(32, 3, 2, SpriteSheet.tile_sheet1);
	public static Sprite brokenwood2 = new Sprite(32, 3, 3, SpriteSheet.tile_sheet1);
	public static Sprite door1 = new Sprite(32, 1, 1, SpriteSheet.tile_sheet1);
	public static Sprite door2 = new Sprite(32, 2, 1, SpriteSheet.tile_sheet1);
	public static Sprite elevator = new Sprite(32, 0, 1, SpriteSheet.tile_sheet1);
	public static Sprite danger = new Sprite(32, 1, 0, SpriteSheet.tile_sheet1);
	public static Sprite voidSprite = new Sprite(32, 0xff000000);

	public static Sprite particle_normal = new Sprite(3, 0xAAAAAA);
	public static Sprite particle_emerald = new Sprite(3, 0x37b10c);
	public static Sprite particle_emerald2 = new Sprite(3, 0x47de12);
	public static Sprite particle_shine = new Sprite(3, 0xfcfbb6);

	public static Sprite proj_green = new Sprite(16, 0, 0, SpriteSheet.proj_sheet);
	public static Sprite proj_fire = new Sprite(16, 1, 0, SpriteSheet.proj_sheet);
	public static Sprite proj_frost = new Sprite(16, 0, 2, SpriteSheet.proj_sheet);
	public static Sprite proj_fireball = new Sprite(16, 1, 2, SpriteSheet.proj_sheet);
	public static Sprite proj_firebomb = new Sprite(16, 3, 2, SpriteSheet.proj_sheet);
	public static Sprite proj_stun = new Sprite(16, 2, 2, SpriteSheet.proj_sheet);
	public static Sprite proj_curse = new Sprite(16, 0, 3, SpriteSheet.proj_sheet);
	public static Sprite proj_arrow = new Sprite(16, 32, 15, 3, 0, SpriteSheet.proj_sheet);

	public static Sprite quiver = new Sprite(32, 4, 8, SpriteSheet.char_sheet);
	public static Sprite bow_left = new Sprite(32, 3, 7, SpriteSheet.char_sheet);
	public static Sprite bow_right = new Sprite(32, 3, 8, SpriteSheet.char_sheet);
	public static Sprite char_man = new Sprite(32, 0, 0, SpriteSheet.char_sheet);
	public static Sprite char_robot = new Sprite(32, 4, 0, SpriteSheet.char_sheet);

	// SKILLS HERE
	public static Sprite skill_fireball = new Sprite(32, 11, 6, SpriteSheet.char_sheet);
	public static Sprite skill_slow = new Sprite(32, 11, 7, SpriteSheet.char_sheet);

	// objects
	public static Sprite candle_cata = new Sprite(32, 0, 7, SpriteSheet.gameobjects);
	public static Sprite cave = new Sprite(32, 6, 2, SpriteSheet.gameobjects);
	public static Sprite portal = new Sprite(32, 5, 3, SpriteSheet.gameobjects);
	public static Sprite chest = new Sprite(32, 6, 3, SpriteSheet.gameobjects);
	public static Sprite chest_open = new Sprite(32, 7, 3, SpriteSheet.gameobjects);
	public static Sprite block = new Sprite(32, 0, 0, SpriteSheet.gameobjects);
	public static Sprite block_cracked = new Sprite(32, 1, 0, SpriteSheet.gameobjects);
	public static Sprite oszlop3 = new Sprite(SpriteSheet.gameobjects, 4 * 32, 4 * 32, 32, 64);
	public static Sprite oszlop3_tort = new Sprite(32, 7, 4, SpriteSheet.gameobjects);
	public static Sprite tabla = new Sprite(32, 6, 4, SpriteSheet.gameobjects);
	public static Sprite tabla_questmark = new Sprite(32, 6, 5, SpriteSheet.gameobjects);
	public static Sprite lada = new Sprite(32, 1, 1, SpriteSheet.gameobjects);
	public static Sprite hordo = new Sprite(32, 2, 0, SpriteSheet.gameobjects);
	public static Sprite statue = new Sprite(32, 32, 64, 3, 0, SpriteSheet.gameobjects);
	public static Sprite gate = new Sprite(64, 64, 64, 2, 0, SpriteSheet.gameobjects);
	public static Sprite aura_tile = new Sprite(32, 32, 32, 5, 2, SpriteSheet.gameobjects);

	public static Sprite HUD_speaker_ON = new Sprite(32, 0, 2, SpriteSheet.HUD);
	public static Sprite HUD_speaker_OFF = new Sprite(32, 0, 3, SpriteSheet.HUD);

	public static Sprite HUD_char_off = new Sprite(32, 32, 22, 0, 2, SpriteSheet.interf);
	public static Sprite HUD_char_moved = new Sprite(32, 32, 22, 1, 2, SpriteSheet.interf);
	public static Sprite HUD_char_on = new Sprite(32, 32, 22, 2, 2, SpriteSheet.interf);

	public static Sprite HUD_menu_off = new Sprite(32, 32, 22, 0, 3, SpriteSheet.interf);
	public static Sprite HUD_menu_moved = new Sprite(32, 32, 22, 1, 3, SpriteSheet.interf);
	public static Sprite HUD_menu_on = new Sprite(32, 32, 22, 2, 3, SpriteSheet.interf);

	public static Sprite HUD_map_off = new Sprite(32, 32, 27, 0, 0, SpriteSheet.interf);
	public static Sprite HUD_map_moved = new Sprite(32, 32, 27, 1, 0, SpriteSheet.interf);
	public static Sprite HUD_map_on = new Sprite(32, 32, 27, 2, 0, SpriteSheet.interf);

	public static Sprite HUD_quest = new Sprite(32, 32, 32, 3, 1, SpriteSheet.HUD);
	public static Sprite HUD_status = new Sprite(32, 96, 32, 0, 0, SpriteSheet.HUD);
	public static Sprite HUD_health = new Sprite(SpriteSheet.HUD, 32, 64, 32, 5);
	public static Sprite HUD_mana = new Sprite(SpriteSheet.HUD, 32, 69, 32, 5);
	public static Sprite map_frame = new Sprite("/data/sprites/mapframe.png");
	public static Sprite map = new Sprite("/data/level/tutorial_tiles.png");
	public static Sprite character = new Sprite("/data/sprites/char.png");
	public static Sprite howto_sprite = new Sprite("/data/sprites/howto.png");

	public static Sprite label_grab = new Sprite("/data/sprites/grablabel.png");

	// NEXUS level
	public static Sprite nexus_door = new Sprite(SpriteSheet.nexus_sheet, 256, 288, 64, 64);
	public static Sprite nexus_door2 = new Sprite(SpriteSheet.nexus_sheet, 224, 192, 128, 64);
	public static Sprite nexus_door2_inverse = new Sprite(SpriteSheet.nexus_sheet, 352, 192, 128, 64);
	public static Sprite nexus_door2_left = new Sprite(SpriteSheet.nexus_sheet, 416, 256, 64, 128);
	public static Sprite nexus_door2_right = new Sprite(SpriteSheet.nexus_sheet, 352, 256, 64, 128);

	// MENU SPRITES
	public static Sprite title = new Sprite("/data/sprites/title.png");
	public static Sprite archer = new Sprite("/data/sprites/archer.png");
	public static Sprite mage = new Sprite("/data/sprites/mage.png");

	public Sprite(String path) {
		SIZE = -1;
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			pixels = new int[w * h];
			image.getRGB(0, 0, w, h, pixels, 0, w);
			this.width = w;
			this.height = h;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// LOADNIG SPRITES WITH GIVEN COORDINATES
	public Sprite(SpriteSheet sheet, int x, int y, int width, int height) {
		SIZE = -1;
		this.sheet = sheet;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];

		for (int yy = 0; yy < height; yy++) {
			for (int xx = 0; xx < width; xx++) {
				pixels[xx + yy * width] = sheet.pixels[(xx + this.x) + (yy + this.y) * sheet.getSheetWidth()];
			}
		}
	}

	protected Sprite(SpriteSheet sheet, int width, int height) {
		if (width == height)
			SIZE = width;
		else
			SIZE = -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;

	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.width = SIZE;
		this.height = SIZE;
		this.x = x;
		this.y = y;
		this.sheet = sheet;
		load();
	}

	public Sprite(int size, int color) {
		this.SIZE = size;
		this.height = SIZE;
		this.width = SIZE;
		pixels = new int[SIZE * SIZE];
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

	public Sprite(int width, int height, int color) {
		this.SIZE = width;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = color;
			}
		}
	}

	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}

	public Sprite(int size, int width, int height, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		this.x = x;
		this.y = y;
		this.sheet = sheet;
		load2();
	}

	private void load() {
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x * SIZE) + (y + this.y * SIZE) * sheet.getSheetWidth()];
			}
		}
	}

	private void load2() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x * width)
						+ (y + this.y * height) * sheet.getSheetWidth()];
			}
		}
	}

	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getSheetWidth() * sheet.getSheetHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amount];
		int current = 0;
		int pixels[] = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];

		for (int yp = 0; yp < sheet.getSheetHeight() / sheet.SPRITE_HEIGHT; yp++) {
			for (int xp = 0; xp < sheet.getSheetWidth() / sheet.SPRITE_WIDTH; xp++) {

				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getSheetWidth()];
					}
				}

				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}

		return sprites;
	}

	public static Sprite flip(Sprite sprite_in) {
		int[] pixels_out = new int[sprite_in.width * sprite_in.height];

		for (int y = 0; y < sprite_in.height; y++) {
			for (int x = 0; x < sprite_in.width; x++) {
				pixels_out[x + y * 32] = sprite_in.pixels[(31 - x) + y * sprite_in.width];
			}
		}
		return new Sprite(pixels_out, sprite_in.width, sprite_in.height);
	}

	public static Sprite scale(Sprite spriteIN, double scale) {
		Sprite s = new Sprite((int) (spriteIN.width * scale), (int) (spriteIN.height * scale), 0xff000000);

		for (int y = 0; y < s.height; y++) {
			for (int x = 0; x < s.width; x++) {
				int index = (int) (x / scale) + (int) (y / scale) * spriteIN.width;
				s.pixels[x + y * s.width] = spriteIN.pixels[index];
			}
		}

		return s;
	}

	public static Sprite rotate(Sprite sprite_in, int size, double radian, int centerX, int centerY) {
		int pixels_out[] = new int[size * size];
		// double rad = Math.toRadians(degree);
		double sin = Math.sin(radian);
		double cos = Math.cos(radian);

		for (int i = 0; i < pixels_out.length; i++) {
			pixels_out[i] = 0xffff00ff;
		}

		for (int y = 0; y < sprite_in.height; y++) {
			for (int x = 0; x < sprite_in.width; x++) {
				double rotatedX = (cos * (x - centerX) - sin * (y - centerY)) + centerX;
				double rotatedY = (sin * (x - centerX) + cos * (y - centerY)) + centerY;// +
																						// 10;
				// int ix = (int) Math.ceil(rotatedX);
				// int iy = (int) Math.ceil(rotatedY);
				if (rotatedX >= 0 && rotatedX < size && rotatedY >= 0 && rotatedY < size) {
					pixels_out[(int) (rotatedX) + (int) (rotatedY) * size] = sprite_in.pixels[x + y * size];
				}
			}
		}

		/*
		 * for (int y = 0; y < size; y++) { for (int x = 0; x < size; x++) { if
		 * (x + 1 == size) continue; //if (y + 1 == 15) continue; if
		 * ((pixels_out[x + y * size] == 0xffff00ff) && (pixels_out[(x + 1) + y
		 * * size] != 0xffff00ff)) { // pixels_out[x + y * 32] = pixels_out[(x +
		 * 1) + y * 32]; } } }
		 */

		Sprite sprite_out = new Sprite(pixels_out, size, size);
		return sprite_out;
	}

}
