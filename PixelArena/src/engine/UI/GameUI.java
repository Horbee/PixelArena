package engine.UI;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import engine.Game;
import engine.entity.gameobject.ItemManager;
import engine.entity.mob.player.PlayerManager;
import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.input.Keyboard;

public class GameUI {

	// Container Sprite here:
	public static Sprite sprite_Container_normal = new Sprite(16, 16, 16, 0, 0, ItemManager.slot_sheet);
	public static Sprite sprite_Container_moved = new Sprite(16, 16, 16, 1, 0, ItemManager.slot_sheet);
	public static Sprite sprite_Container_clicked = new Sprite(16, 16, 16, 2, 0, ItemManager.slot_sheet);
	public static Sprite sprite_char_Container_normal = new Sprite(16, 16, 16, 3, 0, ItemManager.slot_sheet);
	public static Sprite sprite_char_Container_moved = new Sprite(16, 16, 16, 4, 0, ItemManager.slot_sheet);
	public static Sprite sprite_char_Container_clicked = new Sprite(16, 16, 16, 5, 0, ItemManager.slot_sheet);
	// Buttons and list:
	public static List<Button> buttons = new ArrayList<Button>();
	private Button bSpeaker;
	private Button bChar;

	public GameUI() {
		bChar = new Button(85, 0, Sprite.HUD_char_on, Sprite.HUD_char_off, Sprite.HUD_char_moved);
		addButton(bChar);
		
		bSpeaker = new Button(15, 0, Sprite.HUD_speaker_ON, Sprite.HUD_speaker_OFF, null);
		bSpeaker.on = true;
		addButton(bSpeaker);

	}

	private void addButton(Button b) {
		buttons.add(b);
	}

	public void update() {
		// System.out.println(input.character);
		bChar.setHotkey(Keyboard.keyTyped(KeyEvent.VK_C));

		if (bSpeaker.on) {
			if (!Game.AUDIO) Game.AUDIO = true;
		} else {
			if (Game.AUDIO) Game.AUDIO = false;
		}
		
		if (bChar.on) {
			if (!PlayerManager.getPlayer().charUI.on) Game.level.getPlayer().charUI.on = true;
		} else {
			if (PlayerManager.getPlayer().charUI.on) Game.level.getPlayer().charUI.on = false;
		}

		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).update();
		}

		/*Mouse.setMouseOnObject(false);

		for (int i = 0; i < buttons.size(); i++) {
			if (buttons.get(i).isMouseOn == true) {
				Mouse.setMouseOnObject(true);
				break;
			}

		}*/

	}

	public void render(Screen screen) {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).render(screen);
		}
	}
}
