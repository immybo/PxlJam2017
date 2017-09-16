package model;

public class LevelEditor extends Level{
	
	public LevelEditor(Level level) {
		super(level.levelFile);
		this.entities = level.entitiesToAdd;
		this.player = level.player;
	}

	@Override
	public void tick(float dt) {
		
	}
	
	@Override
	public Level restart() {
		return this;
	}

}
