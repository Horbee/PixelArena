package engine.menu;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import engine.Game;
import engine.State;
import engine.entity.mob.player.Mage;
import engine.graphics.MyFont;
import engine.graphics.Screen;
import engine.graphics.Sprite;
import engine.input.Mouse;

public class Menu {

	Rectangle mage, archer, help_button;
	Point mousePoint;
	int mage_value, archer_value;
	private Color help_button_color;
	private boolean howtoplay_active = false;
	
	public Menu() {
		archer = new Rectangle(313 * 2, 122 * 2, 96 * 2, 96 * 2);
		mage = new Rectangle(92 * 2, 122 * 2, 96 * 2, 96 * 2);
		help_button = new Rectangle(14*2, 240*2, 65*2, 12*2);
		mousePoint = new Point(Mouse.getMouseX(), Mouse.getMouseY());
		help_button_color = Color.WHITE;
		archer_value = 5;
	}

	public void update() {
		mousePoint.setLocation(Mouse.getMouseX(), Mouse.getMouseY());
		
		if(help_button.contains(mousePoint)){
			help_button_color = Color.GRAY;
			if(Mouse.mouseClicked(Mouse.LEFT_BUTTON)){
				howtoplay_active = !howtoplay_active;
				return; // ne kattintsunk sokat
			}
		}else{
			help_button_color = Color.white;
		}
		
		if(howtoplay_active) return; // ha a how to play menut latjuk nem akarunk veletlen host valasztani
		
		if(mage.contains(mousePoint)){
			mage_value = 2;
			if(Mouse.getButton() == Mouse.LEFT_BUTTON){
				Game.sounds[0].play();
				//Game.level.setPlayer(new Mage(2 * 32, 2 * 32, Game.getKey()));
				State.setState(State.GAME);
			}
			
		}else{
			mage_value = 1;
		}
			
		
		//TODO: ha kesz van az archer akkor feloldani a kodot
		//if(archer.contains(mousePoint)){
			//archer_value = 2;		
		//}else {
			//archer_value = 1;
		//}
	}

	public void render(Screen screen) {
		screen.renderSprite(Sprite.title,(Game.width / 2) - 123, 20, false);

		if(howtoplay_active){//rendering How To Play Menu
			screen.drawText("How to play", (Game.width / 2) + 125 , 150, MyFont.flashrogersstraight, Color.WHITE, false);
			screen.drawText("Back", 80, 500, MyFont.flashrogersstraight_mini, help_button_color);
			screen.drawText("Goal", 700 , 200, MyFont.flashrogersstraight, Color.WHITE, false);
			screen.drawText("Controls", 150 , 200, MyFont.flashrogersstraight, Color.WHITE, false);
			screen.renderSprite(Sprite.howto_sprite, 30, 122, false);
		}else{ // Rendering normal MAin
			screen.renderSprite(Sprite.archer, 313, 122, false, archer_value);
			screen.renderSprite(Sprite.mage, 92, 122, false, mage_value);
			screen.drawText("Choose your hero", (Game.width / 2) + 110 , 150, MyFont.flashrogersstraight, Color.WHITE, false);
			screen.drawText("Not available yet", 580, 470, MyFont.flashrogersstraight, Color.gray);
			screen.drawText("How to play", 30, 500, MyFont.flashrogersstraight_mini, help_button_color);
		}
		
	}

}
