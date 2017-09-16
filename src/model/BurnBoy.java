package model;

/**
 * Created by Karametua on 16/09/2017.
 */
public class BurnBoy extends Enemy {

    private double shootTimer = 0;

    public BurnBoy(AABB aabb, int depth, double mass, Level level) {
        super(aabb, depth, mass, level);
    }

    @Override
    public void tick(double dt) {
        shoot(dt);
        super.tick(dt);
    }

    private void shoot(double dt) {
        AABB bulletAABB = new AABB(this.getAABB().center, this.getAABB().extents.mult(0.1), this.getVelocity(), null);
        this.shootTimer += dt;
        if(this.shootTimer >= 1) {
            Bullet bullet = new Bullet(bulletAABB, 0, 0, this.getLevel(), 50, new Vector(-10, 0), false, StatusEffect.ON_FIRE);
            this.getLevel().addEntity(bullet);
            this.shootTimer = 0;
        }
    }
}
