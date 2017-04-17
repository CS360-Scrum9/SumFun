package model;

public class UntimedGamemode extends Gamemode {
	
	private static UntimedGamemode gamemode;
	
	private UntimedGamemode() {
		super();
	}

	public UntimedGamemode getGamemode() {
		 if (gamemode == null) {
			 gamemode = new UntimedGamemode();
		 }
		 return gamemode;
	}
}
