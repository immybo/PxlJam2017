package model;

public class FreezeBoy extends Enemy {

    private double shootTimer = 0;

    public FreezeBoy(AABB aabb, int depth, double mass, Level level) {
        super(aabb, depth, mass, level);
    }

    @Override
    public int getHealth() {
        return 100;
    }

    @Override
    public int getMaxHealth() {
        return 100;
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
            Bullet bullet = new Bullet(bulletAABB, 0, 0, this.getLevel(), 10, new Vector(-10, 0), false, StatusEffect.FROZEN);
            this.getLevel().addEntity(bullet);
            this.shootTimer = 0;
        }
    }
}
