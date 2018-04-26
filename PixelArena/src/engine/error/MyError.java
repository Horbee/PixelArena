package engine.error;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class MyError {

	public static void show(String msg){
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		
		JOptionPane.showMessageDialog(null, msg);
	}
	
}
