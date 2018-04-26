package engine.entity.mob.player;

public class PlayerManager {

	private static Player player;
	
	public static void setPlayer(Player player_in) {
		player = player_in;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
}
