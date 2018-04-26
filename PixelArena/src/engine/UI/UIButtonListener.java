package engine.UI;

import java.awt.Color;

public class UIButtonListener {

	public void mouseEntered(UIButton button){
		button.setBgColor(Color.LIGHT_GRAY);
		System.out.println("Entered");
	}
	
	public void mouseExited(UIButton button){
		button.setBgColor(Color.DARK_GRAY);		
		System.out.println("exited");
	}
	
	public void pressed(UIButton button){
		button.setBgColor(Color.GRAY);		
		System.out.println("clicked");
	}
	
	public void released(UIButton button){
		System.out.println("released");		
	}
		
}
