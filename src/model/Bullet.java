package model;

public class Bullet extends AbstractEntity {
    private final int damage;

    public Bullet(AABB aabb, int depth, double mass, Level level, int damage, Vector velocity) {
        super(aabb, depth, mass, level);
        this.damage = damage;
        this.setVelocity(this.getVelocity().add(velocity));
    }

    public int getDamage() {
        return damage;
    }
}
