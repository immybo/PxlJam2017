package model;

public abstract class Enemy extends Character {

    public Enemy(AABB aabb, int depth, double mass, Level level) {
        super(aabb, depth, mass, level);
    }

    @Override
    public int getMovementSpeed() {
        return 400;
    }
}
