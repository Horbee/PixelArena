package engine.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import engine.Game;
import engine.State;
import engine.entity.gameobject.GameObject;
import engine.entity.gameobject.ItemManager;
import engine.entity.mob.Mob;
import engine.entity.mob.player.Player;
import engine.entity.projectile.Projectile;
import engine.interfaces.G_RenderBuffer;
import engine.interfaces.Screen_RenderBuffer;
import engine.level.tile.Tile;
import engine.util.Util;

public class Screen {

	public int width, height;
	public int[] pixels;
	private double xOffset, yOffset;
	private final int ALPHA_COL = 0xffff00ff;
	private double tileColorDarker = 5;
	

	public Font font = new Font("Helvetica", 0, 10);
	public Font font_menu = new Font("Helvetica", 1, 40);
	public Font font2 = new Font("Helvetica", Font.PLAIN, 15);

	public List<G_RenderBuffer> text_buffer = new LinkedList<G_RenderBuffer>();
	public List<Screen_RenderBuffer> top_layer = new LinkedList<Screen_RenderBuffer>();

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xff000000;
		}
	}

	public void render() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = 0xffff00ff;
			}
		}
	}

	public void renderCharacter(int posX, int posY, Mob mob) {
		posX -= xOffset;
		posY -= yOffset;
		for (int y = 0; y < mob.getSprite().height; y++) {
			int ya = y + posY;
			for (int x = 0; x < mob.getSprite().width; x++) {
				int xa = x + posX;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
				int col = mob.getSprite().pixels[x + y * mob.getSprite().width];
				if (col != ALPHA_COL) {
					if (Game.level.lightEnabled) col = Enviroment.makeDarker(col, 3);
					if (mob instanceof Player) {
						if (mob.speed < 2) col = Enviroment.GreyScale(col);
						if (((Player)mob).stunned) col = Enviroment.GreyScale(col);
						if (((Player)mob).cursed) col = Enviroment.CurseColor(col);
					}
					pixels[xa + ya * width] = col;
				}
			}
		}
	}

	public void renderSprite(Sprite sprite, int posX, int posY, boolean fixed) {
		if (fixed) {
			posX -= xOffset;
			posY -= yOffset;
		}

		for (int y = 0; y < sprite.height; y++) {
			int yAbsPos = y + posY;
			for (int x = 0; x < sprite.width; x++) {
				int xAbsPos = x + posX;
				if (xAbsPos < 0 || xAbsPos >= width || yAbsPos < 0 || yAbsPos >= height) continue;
				int col = sprite.pixels[x + y * sprite.width];
				if (col != ALPHA_COL) pixels[xAbsPos + yAbsPos * width] = col;
			}
		}
	}

	public void renderSprite(Sprite sprite, int posX, int posY, boolean lightOn, boolean fixed) {
		if (fixed) {
			posX -= xOffset;
			posY -= yOffset;
		}

		for (int y = 0; y < sprite.height; y++) {
			int yAbsPos = y + posY;
			for (int x = 0; x < sprite.width; x++) {
				int xAbsPos = x + posX;
				if (xAbsPos < 0 || xAbsPos >= width || yAbsPos < 0 || yAbsPos >= height) continue;
				int col = sprite.pixels[x + y * sprite.width];
				if (Game.level.lightEnabled && lightOn && col != ALPHA_COL) col = Enviroment.makeDarker(col, 3);
				if (col != ALPHA_COL) pixels[xAbsPos + yAbsPos * width] = col;
			}
		}
	}

	public void renderSprite(Sprite sprite, int posX, int posY, boolean fixed, int amount) {
		if (fixed) {
			posX -= xOffset;
			posY -= yOffset;
		}

		for (int y = 0; y < sprite.height; y++) {
			int yAbsPos = y + posY;
			for (int x = 0; x < sprite.width; x++) {
				int xAbsPos = x + posX;
				if (xAbsPos < 0 || xAbsPos >= width || yAbsPos < 0 || yAbsPos >= height) continue;
				int col = sprite.pixels[x + y * sprite.width];
				if (col != ALPHA_COL) col = Enviroment.makeDarker(col, amount);
				if (col != ALPHA_COL) pixels[xAbsPos + yAbsPos * width] = col;
			}
		}
	}

	public void renderHUD(Sprite sprite, int amount, int posX, int posY, boolean fixed) {
		if (amount > sprite.width) amount = sprite.width;

		if (fixed) {
			posX -= xOffset;
			posY -= yOffset;
		}

		for (int y = 0; y < sprite.height; y++) {
			int yAbsPos = y + posY;
			for (int x = 0; x < amount; x++) {
				int xAbsPos = x + posX;
				if (xAbsPos < 0 || xAbsPos >= this.width || yAbsPos < 0 || yAbsPos >= height) continue;
				int col = sprite.pixels[x + y * sprite.width];
				if (col != ALPHA_COL) pixels[xAbsPos + yAbsPos * this.width] = col;
			}
		}
	}

	public void renderLight(int posX, int posY, int radius, Light light) {
		if (light.fixed) {
			posX -= xOffset;
			posY -= yOffset;
		}

		for (int y = 0; y < radius * 2; y++) {
			int yAbsPos = y + posY;
			for (int x = 0; x < radius * 2; x++) {
				int xAbsPos = x + posX;

				if (xAbsPos < 0 || xAbsPos >= width || yAbsPos < 0 || yAbsPos >= height) continue;
				int d = (int) Math.sqrt((x - radius) * (x - radius) + (y - radius) * (y - radius));
				if (d < radius) {
					int col = pixels[xAbsPos + yAbsPos * width];
					int intens = d / 5;
					col = Enviroment.makeBrighter(col, light.intensity - intens);
					// col = Enviroment.changeBirghtness(col, -80);
					pixels[xAbsPos + yAbsPos * width] = col;
					// pixels[xAbsPos + yAbsPos * width] = 346346;
				}
			}
		}
	}

	public void renderTextCharacter(Sprite sprite, int posX, int posY, int color, boolean fixed) {
		if (fixed) {
			posX -= xOffset;
			posY -= yOffset;
		}

		for (int y = 0; y < sprite.height; y++) {
			int yAbsPos = y + posY;
			for (int x = 0; x < sprite.width; x++) {
				int xAbsPos = x + posX;
				if (xAbsPos < 0 || xAbsPos >= width || yAbsPos < 0 || yAbsPos >= height) continue;
				int col = sprite.pixels[x + y * sprite.width];
				if (col != ALPHA_COL) pixels[xAbsPos + yAbsPos * width] = color;
			}
		}
	}

	public void renderMiniMap(Sprite sprite, int posX, int posY, int xScroll, int yScroll, boolean fixed) {
		if (fixed) {
			posX -= xOffset;
			posY -= yOffset;
		}

		for (int y = 0; y < 89; y++) {
			int yAbsPos = y + posY;
			for (int x = 0; x < 89; x++) {
				int xAbsPos = x + posX;
				if (xAbsPos < 0 || xAbsPos >= width || yAbsPos < 0 || yAbsPos >= height) continue;
				int col = sprite.pixels[(int) ((x / 5 + xScroll) + (y / 5 + yScroll) * sprite.width)];
				if (col != ALPHA_COL) pixels[xAbsPos + yAbsPos * width] = col;
			}
		}

	}

	public void renderObject(GameObject object, int posX, int posY) {
		posX -= xOffset;
		posY -= yOffset;
		for (int y = 0; y < object.currentSprite.height; y++) {
			int yAbsPos = y + posY;
			for (int x = 0; x < object.currentSprite.width; x++) {
				int xAbsPos = x + posX;
				if (xAbsPos < 0 || xAbsPos >= width || yAbsPos < 0 || yAbsPos >= height) continue;
				int col = object.currentSprite.pixels[x + y * object.currentSprite.width];
				if (col != ALPHA_COL) pixels[xAbsPos + yAbsPos * width] = col;
			}
		}
	}

	public void renderTile(int posX, int posY, Tile tile) {
		posX -= xOffset;
		posY -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int yAbsPos = y + posY;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xAbsPos = x + posX;
				if (xAbsPos < -tile.sprite.SIZE || xAbsPos >= width || yAbsPos < 0 || yAbsPos >= height) break;
				if (xAbsPos < 0) xAbsPos = 0;
				int col = tile.sprite.pixels[x + y * tile.sprite.SIZE];
				
				if (State.getState() == State.MENU) col = Enviroment.makeDarker(col, tileColorDarker);
				
				
				//if (State.getState() == State.FADE_OUT || State.getState() == State.FADE_IN) col = Enviroment.makeDarker(col, );
				
				if (col != ALPHA_COL) pixels[xAbsPos + yAbsPos * width] = col;
			}
		}
	}
	
	public void setTileColorDarker(double value){
		tileColorDarker = value;
	}

	public void drawText(final String text, int x, int y, final Font font, final Color c, boolean fixed) {
		if (fixed) {
			x -= xOffset * 2;
			y -= yOffset * 2;
		}
		drawText(text, x, y, font, c);
	}

	public void drawText(final String text, final int x, final int y, final Font font, final Color c) {
		text_buffer.add(new G_RenderBuffer() {
			public void render(Graphics g) {
				g.setFont(font);
				g.setColor(c);
				g.drawString(text, x, y);
			}
		});

	}

	public void renderOnTop(final int posX, final int posY) {
		top_layer.add(new Screen_RenderBuffer() {
			public void render(Screen screen) {
				screen.renderSprite(ItemManager.i_details, posX, posY, false);
			}
		});
	}

	public void renderProjectile(int posX, int posY, Projectile p) {
		posX -= xOffset;
		posY -= yOffset;
		for (int y = 0; y < p.getSpriteHeight(); y++) {
			int yAbsPos = y + posY;
			for (int x = 0; x < p.getSpriteWidth(); x++) {
				int xAbsPos = x + posX;
				if (xAbsPos < -p.getSpriteWidth() || xAbsPos >= width || yAbsPos < 0 || yAbsPos >= height) break;
				if (xAbsPos < 0) xAbsPos = 0;
				int col = p.getSprite().pixels[x + y * p.getSpriteWidth()];
				if (col != ALPHA_COL) pixels[xAbsPos + yAbsPos * width] = col;
			}
		}
	}

	public void drawRect(int posX, int posY, int width, int height, int color, boolean fixed) {
		if (fixed) {
			posX -= xOffset;
			posY -= yOffset;
		}
		for (int x = posX; x < posX + width; x++) {
			if (x < 0 || x >= this.width || posY >= this.height) continue;
			if (posY > 0) pixels[x + posY * this.width] = color;
			if (posY + height >= this.height) continue;
			if (posY + height > 0) pixels[x + (posY + height) * this.width] = color;
		}
		for (int y = posY; y < posY + height; y++) {
			if (posX >= this.width || y < 0 || y >= this.height) continue;
			if (posX > 0) pixels[posX + y * this.width] = color;
			if (posX + width >= this.width) continue;
			if (posX + width > 0) pixels[(posX + width) + y * this.width] = color;
		}
	}

	public void setOffset(double xOffset, double yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}
