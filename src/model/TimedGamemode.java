package model;

public class TimedGamemode extends Gamemode {

	private static TimedGamemode gamemode;
	
	private TimedGamemode() {
		super();
	}

	public TimedGamemode getGamemode() {
		 if (gamemode == null)
			 gamemode = new TimedGamemode();
		 return gamemode;
	}
	
}
