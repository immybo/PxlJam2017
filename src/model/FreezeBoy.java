package model;

public class FreezeBoy extends AbstractEntity implements Enemy {
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
        shoot();
    }

    private void shoot() {
        AABB bulletAABB = new AABB(this.getAABB().center, this.getAABB().get_size(), this.getVelocity(), null);
        Bullet bullet = new Bullet(bulletAABB, 0, 0, this.getLevel(), 10, new Vector(-10, 0));
        this.getLevel().addEntity(bullet);
    }
}
