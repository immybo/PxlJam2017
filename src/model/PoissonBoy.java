package model;

/**
 * Created by Karametua on 16/09/2017.
 */
public class PoissonBoy extends Enemy {
    public PoissonBoy(AABB aabb, int depth, double mass, Level level) {
        super(aabb, depth, mass, level);
        this.applyForce(new Vector(-100, 0));
    }

    @Override
    public void tick(double dt) {
        super.tick(dt);
    }
}
