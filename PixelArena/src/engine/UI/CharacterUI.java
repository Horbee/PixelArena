package engine.UI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import engine.entity.gameobject.GameObject;
import engine.entity.gameobject.container.CharacterContainer;
import engine.entity.gameobject.slots.CharacterItemSlot;
import engine.entity.mob.player.Player;
import engine.graphics.AnimatedSprite;
import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.graphics.SpriteSheet;

public class CharacterUI extends GameObject {

	private Sprite sprite;
	public boolean on = false;
	public CharacterContainer cont_bag;
	public CharacterItemSlot slot_weapon;
	public CharacterItemSlot slot_modifier;
	public CharacterItemSlot slot_head;
	public CharacterItemSlot slot_chest;
	public CharacterItemSlot slot_pants;
	public CharacterItemSlot slot_accessory;
	private AnimatedSprite charSprite = new AnimatedSprite(SpriteSheet.mage_down, 32, 32, 3, 10);
	
	public List<SkillUI> skill_uis = new ArrayList<SkillUI>();
		
	private Player player;

	public CharacterUI(int posX, int posY, Player p) {
		super(posX, posY);
		this.player = p;
		sprite = Sprite.character;
		cont_bag = new CharacterContainer(8, this, 15, 117, false);
		slot_weapon = new CharacterItemSlot(posX + 52, posY + 5);
		slot_modifier = new CharacterItemSlot(posX + 72, posY + 5);
		slot_head = new CharacterItemSlot(posX + 52, posY + 25);
		slot_chest = new CharacterItemSlot(posX + 72, posY + 25);
		slot_pants = new CharacterItemSlot(posX + 52, posY + 45);
		slot_accessory = new CharacterItemSlot(posX + 72, posY + 45);
		// cont.addItem(ItemManager.items.get(2));
	}

	public void update() {
		for (int i = 0; i < skill_uis.size(); i++) {
			skill_uis.get(i).update();
		}
		
		if (on) {

			// Slotos mouse movements
			cont_bag.update();
			slot_weapon.update();
			slot_modifier.update();
			slot_head.update();
			slot_chest.update();
			slot_pants.update();
			slot_accessory.update();

			// Animating the Character's avatar
			charSprite.setFrame(1);
			/*if (charSprite.oncePlayed == true) {
				timer++;
				if (timer % 120 == 0) {
					charSprite.oncePlayed = false;
					timer = 0;
				}
			} else charSprite.update();*/
		}
	}

	public void render(final Screen screen) {
		screen.renderSprite(new Sprite(34, 13, 0xff3b383b), (int) (player.getPosX() - 1), (int) (player.getPosY() - 16), false, true);
		double hp = player.health * 32 / player.max_health;
		screen.renderHUD(Sprite.HUD_health, (int) hp, (int) player.getPosX(), (int) (player.getPosY() - 15), true);
		double mana = player.mana * 32 / player.max_mana;
		screen.renderHUD(Sprite.HUD_mana, (int) mana, (int) (player.getPosX()), (int) (player.getPosY() - 9), true);

		// screen.renderSprite(Sprite.HUD_status, 1, 1, false, false);
		// screen.drawText(Integer.toString(player.health) + "/" + player.max_health, player.getPosX() + 100, player.getPosY()+163, screen.font, Color.ORANGE, true);
		// screen.drawText(Integer.toString(player.mana) + "/" + player.max_mana, 90, 55, screen.font, Color.RED);

		for (int i = 0; i < skill_uis.size(); i++) {
			skill_uis.get(i).render(screen);
		}
		
		if (on) {
			screen.renderSprite(sprite, (int) posX, (int) posY, false, false);
			cont_bag.render(screen);
			slot_weapon.render(screen);
			slot_modifier.render(screen);
			slot_head.render(screen);
			slot_chest.render(screen);
			slot_pants.render(screen);
			slot_accessory.render(screen);
			screen.drawText(player.health + " / " + player.mana, 780, 360, screen.font2, Color.WHITE);
			screen.drawText("Dmg: " + player.dmg, 870, 360, screen.font2, Color.WHITE);
			screen.drawText("Def: " + player.defense, 870, 385, screen.font2, Color.WHITE);
			screen.drawText("HP reg: " + player.hp_regen_time, 780, 385, screen.font2, Color.WHITE);
			screen.drawText("Speed: " + player.speed, 870, 410, screen.font2, Color.WHITE);
			screen.drawText("MP reg: " + player.mana_regen_time, 780, 410, screen.font2, Color.WHITE);
			screen.renderSprite(charSprite.getSprite(), (int) (posX + 12), (int) (posY + 20), false, false);
		}
	}

}
