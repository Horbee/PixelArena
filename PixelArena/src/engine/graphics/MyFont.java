package engine.graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import engine.error.MyError;

public class MyFont {

	public static Font flashrogersstraight, flashrogersstraight_mini, BradBunFont, diabloFont, diabloFontMini;
	public static SpriteSheet arial_sheet = new SpriteSheet("/data/sheets/fontsheet.png", 16, 16);
	public static SpriteSheet arial_sheet8x8 = new SpriteSheet("/data/sheets/fontsheet8x8.png", 8, 8);
	public static Sprite[] characters = Sprite.split(arial_sheet8x8);

	private static String charIndex = "ABCDEFGHIJKLM" + //
			"NOPQRSTUVWXYZ" + //
			"abcdefghijklm" + //
			"nopqrstuvwxyz" + //
			"0123456789.,'" + //
			"\"\";:!?@$%()-+";

	public MyFont() {
	}

	public static void loadAll() {
		flashrogersstraight = loadFont("/data/fonts/flashrogersstraight.ttf", 40);
		flashrogersstraight_mini = loadFont("/data/fonts/flashrogersstraight.ttf", 25);
		diabloFontMini = loadFont("/data/fonts/DIABLO_L.ttf", 15);
		diabloFont = loadFont("/data/fonts/DIABLO_L.ttf", 40);
		BradBunFont = loadFont("/data/fonts/BradBunR.ttf", 40);
	}

	private static Font loadFont(String path, int size) {
		Font result = null;
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			
			result = Font.createFont(Font.TRUETYPE_FONT, MyFont.class.getResourceAsStream(path));
			result = result.deriveFont(Font.PLAIN, size);
			ge.registerFont(result);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
			MyError.show("Font loading error! " + path);
		}
		return result;
	}

	public void render(int x, int y, String text, boolean fixed, Screen screen) {
		render(x, y, 0, text, 0x000000, fixed, screen);
	}

	public void render(int x, int y, String text, int color, boolean fixed, Screen screen) {
		render(x, y, 0, text, color, fixed, screen);
	}

	public void render(int x, int y, int spacing, String text, int color, boolean fixed, Screen screen) {
		int line = 0;
		int xOffset = 0;
		for (int i = 0; i < text.length(); i++) {
			int yOffset = 0;
			if (i != 0) xOffset += 8 + spacing;
			char currentChar = text.charAt(i);
			if (currentChar == 'g' || currentChar == 'y' || currentChar == 'q' || currentChar == 'p' || currentChar == 'j' || currentChar == ',') yOffset = 3;
			if (currentChar == '\n') {
				xOffset = 0;
				line++;
			}
			int index = charIndex.indexOf(currentChar);
			if (index == -1) continue;
			screen.renderTextCharacter(characters[index], x + xOffset, y + (line * 20) + yOffset, color, fixed);
		}
	}

}
